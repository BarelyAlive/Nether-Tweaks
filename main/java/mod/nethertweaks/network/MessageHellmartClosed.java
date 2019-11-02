package mod.nethertweaks.network;

import java.util.Objects;

import io.netty.buffer.ByteBuf;
import mod.nethertweaks.block.tile.TileHellmart;
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

	public MessageHellmartClosed(final int x, final int y, final int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public void fromBytes(final ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
	}

	@Override
	public void toBytes(final ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
	}


	public static class MessageHellmartClosedHandler implements IMessageHandler<MessageHellmartClosed, IMessage>
	{
		@Override
		public IMessage onMessage(final MessageHellmartClosed message, final MessageContext ctx) {
			final EntityPlayerMP player = ctx.getServerHandler().player;
			player.getServerWorld().addScheduledTask(() -> {
				final TileEntity tile_entity = player.world.getTileEntity(new BlockPos(message.x, message.y, message.z));
				if(tile_entity instanceof TileHellmart) {
					final TileHellmart tileEntityMarket = (TileHellmart) tile_entity;

					if(!Objects.requireNonNull(tileEntityMarket.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)).getStackInSlot(0)
							.isEmpty()) {
						player.entityDropItem(Objects.requireNonNull(tileEntityMarket
                                .getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)).getStackInSlot(0), 1.0F);
						Objects.requireNonNull(tileEntityMarket.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)).getStackInSlot(0)
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
