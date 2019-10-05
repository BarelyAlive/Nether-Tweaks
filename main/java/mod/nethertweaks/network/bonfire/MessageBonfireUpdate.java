package mod.nethertweaks.network.bonfire;

import java.nio.charset.Charset;
import java.util.List;
import java.util.UUID;

import io.netty.buffer.ByteBuf;
import mod.nethertweaks.world.BonfireInfo;
import mod.nethertweaks.world.WorldSpawnLocation;
import mod.sfhcore.network.NetworkHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class MessageBonfireUpdate implements IMessage {

	private UpdateStatus status;
	private BlockPos pos;
	private BonfireInfo info;
	
	public MessageBonfireUpdate() {}
	
	public MessageBonfireUpdate(UpdateStatus status, BlockPos pos, BonfireInfo info) {
		this.status = status;
		this.pos = pos;
		this.info = info;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
		if (buf.readBoolean())
		{
			info = null;
		}
		else
		{
			info = new BonfireInfo(new UUID(buf.readLong(), buf.readLong()), buf.readInt());
			
			info.setSpawnPos(new BlockPos(buf.readInt(), buf.readInt(), buf.readInt()));
			int length = buf.readInt();
			info.setName(buf.readCharSequence(length, Charset.defaultCharset()).toString());
			info.isPublic(buf.readBoolean());
			
			length = buf.readInt();
			for(int j = 0; j < length; j++)
			{
				info.addPlayer(new UUID(buf.readLong(), buf.readLong()));
			}
		}
		
		int status_int = buf.readInt();
		
		status = (status_int == 1 ? UpdateStatus.ADD : (status_int == 2 ? UpdateStatus.UPDATE : (status_int == 3 ? UpdateStatus.REMOVE : UpdateStatus.UPDATE)));
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.pos.getX());
		buf.writeInt(this.pos.getY());
		buf.writeInt(this.pos.getZ());
		
		buf.writeBoolean(info == null);
		if (info != null)
		{
			buf.writeLong(info.getOwner().getMostSignificantBits());
			buf.writeLong(info.getOwner().getLeastSignificantBits());
			buf.writeInt(info.getDimension());
			BlockPos spawnpos = info.getSpawnPos();
			if (spawnpos != null)
			{
				buf.writeInt(spawnpos.getX());
				buf.writeInt(spawnpos.getY());
				buf.writeInt(spawnpos.getZ());
			}
			else
			{
				buf.writeInt(0);
				buf.writeInt(0);
				buf.writeInt(0);
			}
			buf.writeInt(info.getName().length());
			buf.writeCharSequence(info.getName(), Charset.forName("UTF-8"));
			buf.writeBoolean(info.isPublic());
			List<UUID> player_list = info.getLastPlayerSpawn();
			buf.writeInt(player_list.size());
			for(int i = 0; i < player_list.size(); i++)
			{
				buf.writeLong(player_list.get(i).getMostSignificantBits());
				buf.writeLong(player_list.get(i).getLeastSignificantBits());
			}
		}
		
		buf.writeInt(status == UpdateStatus.ADD ? 1 : (status == UpdateStatus.UPDATE ? 2 : (status == UpdateStatus.REMOVE ? 3 : 0)));
	}

	public static class MessageBonfireUpdateHandler implements IMessageHandler<MessageBonfireUpdate, IMessage>
	{
		@Override
		public IMessage onMessage(MessageBonfireUpdate msg, MessageContext ctx) {
			if (msg.status == UpdateStatus.ADD || msg.status == UpdateStatus.UPDATE)
				WorldSpawnLocation.bonfire_info.put(msg.pos, msg.info);
			else if (msg.status == UpdateStatus.REMOVE)
				WorldSpawnLocation.bonfire_info.remove(msg.pos);
			
			if (ctx.side == Side.SERVER)
				NetworkHandler.INSTANCE.sendToAll(new MessageBonfireUpdate(msg.status, msg.pos, WorldSpawnLocation.bonfire_info.containsKey(msg.pos) ? WorldSpawnLocation.bonfire_info.get(msg.pos) : null));
			return null;
		}
	}
	
}
