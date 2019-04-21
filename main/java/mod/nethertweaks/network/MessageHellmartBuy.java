package mod.nethertweaks.network;

import io.netty.buffer.ByteBuf;
import mod.nethertweaks.blocks.tile.TileHellmart;
import mod.nethertweaks.registries.registries.HellmartRegistry;
import mod.nethertweaks.registry.types.HellmartData;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.items.CapabilityItemHandler;

public class MessageHellmartBuy implements IMessage{
	private int itemNum;
	private int x;
	private int y;
	private int z;
	private boolean shouldClear;

	public MessageHellmartBuy() {}

	public MessageHellmartBuy(int itemNum, int x, int y, int z, boolean shouldClear) {
		this.itemNum = itemNum;
		this.x = x;
		this.y = y;
		this.z = z;
		this.shouldClear = shouldClear;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.itemNum = buf.readInt();
		this.x = buf.readInt();
		this.y = buf.readInt();
		this.z = buf.readInt();
		this.shouldClear = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.itemNum);
		buf.writeInt(this.x);
		buf.writeInt(this.y);
		buf.writeInt(this.z);
		buf.writeBoolean(this.shouldClear);
	}

	public static class MessageHellmartBuyHandler implements IMessageHandler<MessageHellmartBuy, IMessage>
    {
		@Override
		public IMessage onMessage(MessageHellmartBuy message, MessageContext ctx) {
			final EntityPlayerMP player = ctx.getServerHandler().player;
			player.getServerWorld().addScheduledTask(() -> {
				final TileEntity tile_entity = player.world.getTileEntity(new BlockPos(message.x, message.y, message.z));
				if((tile_entity instanceof TileHellmart)) {
					final TileHellmart tileEntityMarket = (TileHellmart) tile_entity;
					final HellmartData data = HellmartRegistry.getData(message.itemNum);
					final int price = data.getPrice();
	
					if(message.shouldClear) {
						tileEntityMarket.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).getStackInSlot(0)
								.setCount(0);
					}
					else {
						tileEntityMarket.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).getStackInSlot(0)
								.shrink(price);
					}
	
					final EntityItem var14 =
							new EntityItem(player.world, player.posX, player.posY + 1.0D, player.posZ, data.getItem().copy());
					player.world.spawnEntity(var14);
				}
			});
			return null;
		}
    }
}
