package mod.nethertweaks.network;

import io.netty.buffer.ByteBuf;
import mod.nethertweaks.blocks.tile.TileHellmart;
import mod.nethertweaks.registries.manager.NTMRegistryManager;
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

	public MessageHellmartBuy(final int itemNum, final int x, final int y, final int z, final boolean shouldClear) {
		this.itemNum = itemNum;
		this.x = x;
		this.y = y;
		this.z = z;
		this.shouldClear = shouldClear;
	}

	@Override
	public void fromBytes(final ByteBuf buf) {
		itemNum = buf.readInt();
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		shouldClear = buf.readBoolean();
	}

	@Override
	public void toBytes(final ByteBuf buf) {
		buf.writeInt(itemNum);
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeBoolean(shouldClear);
	}

	public static class MessageHellmartBuyHandler implements IMessageHandler<MessageHellmartBuy, IMessage>
	{
		@Override
		public IMessage onMessage(final MessageHellmartBuy message, final MessageContext ctx) {
			final EntityPlayerMP player = ctx.getServerHandler().player;
			System.out.println(player);
			player.getServerWorld().addScheduledTask(() -> {
				final TileEntity tile_entity = player.world.getTileEntity(new BlockPos(message.x, message.y, message.z));
				if(tile_entity instanceof TileHellmart) {
					final TileHellmart tileEntityMarket = (TileHellmart) tile_entity;

					HellmartData[] dataz = NTMRegistryManager.HELLMART_REGISTRY.getRegistry().values().toArray(new HellmartData[0]);
					final HellmartData data = dataz[message.itemNum];

					final int price = data.getPrice();

					if(message.shouldClear)
						tileEntityMarket.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).getStackInSlot(0)
						.setCount(0);
					else
						tileEntityMarket.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).getStackInSlot(0)
						.shrink(price);

					final EntityItem var14 =
							new EntityItem(player.world, player.posX, player.posY + 1.0D, player.posZ, data.getItem().copy());
					player.world.spawnEntity(var14);
				}
			});
			return null;
		}
	}
}
