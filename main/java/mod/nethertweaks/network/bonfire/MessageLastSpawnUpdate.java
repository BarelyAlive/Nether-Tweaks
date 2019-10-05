package mod.nethertweaks.network.bonfire;

import java.util.UUID;

import io.netty.buffer.ByteBuf;
import mod.nethertweaks.world.WorldSpawnLocation;
import mod.sfhcore.network.NetworkHandler;
import mod.sfhcore.vars.PlayerPosition;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class MessageLastSpawnUpdate implements IMessage {

	private UpdateStatus status;
	private PlayerPosition pos;
	private UUID info;
	
	public MessageLastSpawnUpdate() {}
	
	public MessageLastSpawnUpdate(UpdateStatus status, PlayerPosition pos, UUID info) {
		this.status = status;
		this.pos = pos;
		this.info = info;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		
		if (buf.readBoolean())
		{
			pos = null;
		}
		else
		{
			pos = new PlayerPosition(new BlockPos(buf.readInt(), buf.readInt(), buf.readInt()), buf.readFloat(), buf.readFloat());
		}
		
		if (buf.readBoolean())
		{
			info = null;
		}
		else
		{
			info = new UUID(buf.readLong(), buf.readLong());
		}
		
		int status_int = buf.readInt();
		
		status = (status_int == 1 ? UpdateStatus.ADD : (status_int == 2 ? UpdateStatus.UPDATE : (status_int == 3 ? UpdateStatus.REMOVE : UpdateStatus.UPDATE)));
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(pos == null);
		if (pos != null)
		{
			buf.writeInt(this.pos.getPos().getX());
			buf.writeInt(this.pos.getPos().getY());
			buf.writeInt(this.pos.getPos().getZ());
			buf.writeFloat(this.pos.getYaw());
			buf.writeFloat(this.pos.getAng());
		}
		
		buf.writeBoolean(info == null);
		if (info != null)
		{
			buf.writeLong(info.getMostSignificantBits());
			buf.writeLong(info.getLeastSignificantBits());
		}
		
		buf.writeInt(status == UpdateStatus.ADD ? 1 : (status == UpdateStatus.UPDATE ? 2 : (status == UpdateStatus.REMOVE ? 3 : 0)));
	}

	public static class MessageLastSpawnUpdateHandler implements IMessageHandler<MessageLastSpawnUpdate, IMessage>
	{
		@Override
		public IMessage onMessage(MessageLastSpawnUpdate msg, MessageContext ctx) {
			if (msg.status == UpdateStatus.ADD || msg.status == UpdateStatus.UPDATE)
				WorldSpawnLocation.lastSpawnLocations.put(msg.info, msg.pos);
			else if (msg.status == UpdateStatus.REMOVE)
				WorldSpawnLocation.lastSpawnLocations.remove(msg.info);
			
			if (ctx.side == Side.SERVER)
				NetworkHandler.INSTANCE.sendToAll(new MessageLastSpawnUpdate(msg.status, msg.pos, msg.info));
			return null;
		}
	}
	
}
