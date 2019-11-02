package mod.nethertweaks.network;

import io.netty.buffer.ByteBuf;
import mod.nethertweaks.block.tile.TileBarrel;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MessageFluidLevelUpdate implements IMessage {

	private int fillAmount;
	private int x, y, z;

	public MessageFluidLevelUpdate() {
	}

	public MessageFluidLevelUpdate(final int fillAmount, final BlockPos pos) {
		x = pos.getX();
		y = pos.getY();
		z = pos.getZ();
		this.fillAmount = fillAmount;
	}

	@Override
	public void toBytes(final ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeInt(fillAmount);
	}

	@Override
	public void fromBytes(final ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		fillAmount = buf.readInt();
	}

	public static class MessageFluidLevelUpdateHandler implements IMessageHandler<MessageFluidLevelUpdate, IMessage> {
		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(final MessageFluidLevelUpdate msg, final MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(new Runnable() {
				@Override
				@SideOnly(Side.CLIENT)
				public void run() {
					final TileEntity entity = Minecraft.getMinecraft().player.getEntityWorld().getTileEntity(new BlockPos(msg.x, msg.y, msg.z));
					if (entity instanceof TileBarrel) {
						final TileBarrel te = (TileBarrel) entity;
						final FluidStack f = te.getTank().getFluid();
						if (f != null) {
							f.amount = msg.fillAmount;
							te.getTank().setFluid(f);
						}
					}
				}
			});
			return null;
		}
	}
}