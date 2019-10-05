package mod.nethertweaks.network.bonfire;

import io.netty.buffer.ByteBuf;
import mod.nethertweaks.world.BonfireInfo;
import mod.nethertweaks.world.WorldSpawnLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageBonfireSetSpawnPoint implements IMessage
{
	private BlockPos pos = new BlockPos(0, 0, 0);
	private BlockPos spawnPos = new BlockPos(0, 0, 0);

	public MessageBonfireSetSpawnPoint() {
	}

	public MessageBonfireSetSpawnPoint(final BlockPos pos, final BlockPos spawnPos) {
		this.pos = pos;
	}

	@Override
	public void toBytes(final ByteBuf buf)
	{
		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());
		buf.writeInt(spawnPos.getX());
		buf.writeInt(spawnPos.getY());
		buf.writeInt(spawnPos.getZ());
	}

	@Override
	public void fromBytes(final ByteBuf buf)
	{
		pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
		spawnPos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
	}

	public static class MessageBonfireSetSpawnPointHandler implements IMessageHandler<MessageBonfireSetSpawnPoint, IMessage>
	{

		@Override
		public IMessage onMessage(final MessageBonfireSetSpawnPoint msg, final MessageContext ctx) {
			BonfireInfo info = WorldSpawnLocation.bonfire_info.get(msg.pos);

			if(info == null)
				return null;

			info.setSpawnPos(msg.spawnPos);

			WorldSpawnLocation.bonfire_info.put(msg.pos, info);
			return null;
		}

	}

}
