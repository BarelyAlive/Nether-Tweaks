package mod.nethertweaks.network;

import io.netty.buffer.ByteBuf;
import mod.nethertweaks.blocks.tile.TileHellmart;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.items.CapabilityItemHandler;

public class MessageHellmartClosed implements IMessage{
	private int x;
	private int y;
	private int z;

	public MessageHellmartClosed() {}

	public MessageHellmartClosed(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.x = buf.readInt();
		this.y = buf.readInt();
		this.z = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.x);
		buf.writeInt(this.y);
		buf.writeInt(this.z);
	}
	

	public static class MessageHellmartClosedHandler implements IMessageHandler<MessageHellmartClosed, IMessage>
    {
		@Override
		public IMessage onMessage(MessageHellmartClosed message, MessageContext ctx) {
			EntityPlayerMP player = ctx.getServerHandler().player;
			player.getServerWorld().addScheduledTask(() -> {
				TileEntity tile_entity = player.world.getTileEntity(new BlockPos(message.x, message.y, message.z));
				if((tile_entity instanceof TileHellmart)) {
					TileHellmart tileEntityMarket = (TileHellmart) tile_entity;
	
					if(!tileEntityMarket.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).getStackInSlot(0)
							.isEmpty()) {
						player.entityDropItem(tileEntityMarket
								.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).getStackInSlot(0), 1.0F);
						tileEntityMarket.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).getStackInSlot(0)
								.setCount(0);
					}
				}
	
				final IBlockState state = player.world.getBlockState(new BlockPos(message.x, message.y, message.z));
				player.world.notifyBlockUpdate(new BlockPos(message.x, message.y, message.z), state, state, 3);
			});
			return null;
		}
    }
}
