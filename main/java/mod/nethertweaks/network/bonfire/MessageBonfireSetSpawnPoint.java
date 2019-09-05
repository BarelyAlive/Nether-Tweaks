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
	
	public MessageBonfireSetSpawnPoint(BlockPos pos, BlockPos spawnPos) {
		this.pos = pos;
	}
	
	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(this.pos.getX());
		buf.writeInt(this.pos.getY());
		buf.writeInt(this.pos.getZ());
		buf.writeInt(this.spawnPos.getX());
		buf.writeInt(this.spawnPos.getY());
		buf.writeInt(this.spawnPos.getZ());
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
		this.spawnPos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
	}
	
	public static class MessageBonfireSetSpawnPointHandler implements IMessageHandler<MessageBonfireSetSpawnPoint, IMessage>
	{

		@Override
		public IMessage onMessage(MessageBonfireSetSpawnPoint msg, MessageContext ctx) {
			BonfireInfo info = WorldSpawnLocation.bonfire_info.get(msg.pos);
			
			if(info == null)
			{
				return null;
			}
			
			info.setSpawnPos(msg.spawnPos);
			
			WorldSpawnLocation.bonfire_info.put(msg.pos, info);
			return null;
		}
		
	}
	
}
