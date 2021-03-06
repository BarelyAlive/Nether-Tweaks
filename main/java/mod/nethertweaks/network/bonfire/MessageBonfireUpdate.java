package mod.nethertweaks.network.bonfire;

import java.util.List;
import java.util.UUID;

import io.netty.buffer.ByteBuf;
import mod.nethertweaks.world.BonfireInfo;
import mod.nethertweaks.world.WorldSpawnLocation;
import mod.sfhcore.network.NetworkHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class MessageBonfireUpdate implements IMessage {

	private UpdateStatus status;
	private BlockPos pos;
	private BonfireInfo info;

	public MessageBonfireUpdate() {}

	public MessageBonfireUpdate(final UpdateStatus status, final BlockPos pos, final BonfireInfo info) {
		this.status = status;
		this.pos = pos;
		this.info = info;
	}

	@Override
	public void fromBytes(final ByteBuf buf) {
		pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
		if (buf.readBoolean())
			info = null;
		else
		{
			info = new BonfireInfo(new UUID(buf.readLong(), buf.readLong()), buf.readInt());

			info.setSpawnPos(new BlockPos(buf.readInt(), buf.readInt(), buf.readInt()));
			info.setName(ByteBufUtils.readUTF8String(buf));
			info.isPublic(buf.readBoolean());

			final int length = buf.readInt();
			for(int j = 0; j < length; j++)
				info.addPlayer(new UUID(buf.readLong(), buf.readLong()));
		}

		final int status_int = buf.readInt();

		status = status_int == 1 ? UpdateStatus.ADD : status_int == 2 ? UpdateStatus.UPDATE : status_int == 3 ? UpdateStatus.REMOVE : UpdateStatus.UPDATE;
	}

	@Override
	public void toBytes(final ByteBuf buf) {
		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());

		buf.writeBoolean(info == null);
		if (info != null)
		{
			buf.writeLong(info.getOwner().getMostSignificantBits());
			buf.writeLong(info.getOwner().getLeastSignificantBits());
			buf.writeInt(info.getDimension());
			final BlockPos spawnpos = info.getSpawnPos();
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
			ByteBufUtils.writeUTF8String(buf, info.getName());
			buf.writeBoolean(info.isPublic());
			final List<UUID> player_list = info.getLastPlayerSpawn();
			buf.writeInt(player_list.size());
            for (final UUID uuid : player_list) {
                buf.writeLong(uuid.getMostSignificantBits());
                buf.writeLong(uuid.getLeastSignificantBits());
            }
		}

		buf.writeInt(status == UpdateStatus.ADD ? 1 : status == UpdateStatus.UPDATE ? 2 : status == UpdateStatus.REMOVE ? 3 : 0);
	}

	public static class MessageBonfireUpdateHandler implements IMessageHandler<MessageBonfireUpdate, IMessage>
	{
		@Override
		public IMessage onMessage(final MessageBonfireUpdate msg, final MessageContext ctx) {
			if (msg.status == UpdateStatus.ADD || msg.status == UpdateStatus.UPDATE)
				WorldSpawnLocation.getBonfireInfo().put(msg.pos, msg.info);
			else if (msg.status == UpdateStatus.REMOVE)
				WorldSpawnLocation.getBonfireInfo().remove(msg.pos);

			if (ctx.side == Side.SERVER)
				NetworkHandler.INSTANCE.sendToAll(new MessageBonfireUpdate(msg.status, msg.pos, WorldSpawnLocation.getBonfireInfo().getOrDefault(msg.pos, null)));
			return null;
		}
	}

}
