package mod.nethertweaks.network;

import io.netty.buffer.ByteBuf;
import mod.nethertweaks.block.tile.TileBarrel;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MessageFluidUpdate implements IMessage {

	private String fluidName;
	private int fillAmount;
	private int x, y, z;

	public MessageFluidUpdate() {
	}

	public MessageFluidUpdate(final FluidStack fluid, final BlockPos pos) {
		x = pos.getX();
		y = pos.getY();
		z = pos.getZ();
		if (fluid == null) {
			fillAmount = 0;
			fluidName = "null";
		} else {
			fillAmount = fluid.amount;
			fluidName = fluid.getFluid().getName();
		}
	}

	@Override
	public void toBytes(final ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeInt(fillAmount);
		ByteBufUtils.writeUTF8String(buf, fluidName);
	}

	@Override
	public void fromBytes(final ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		fillAmount = buf.readInt();
		fluidName = ByteBufUtils.readUTF8String(buf);
	}

	public static class MessageFluidUpdateHandler implements IMessageHandler<MessageFluidUpdate, IMessage> {
		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(final MessageFluidUpdate msg, final MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(new Runnable() {
				@Override
				@SideOnly(Side.CLIENT)
				public void run() {
					final TileEntity entity = Minecraft.getMinecraft().player.getEntityWorld().getTileEntity(new BlockPos(msg.x, msg.y, msg.z));
					if (entity instanceof TileBarrel) {
						final TileBarrel te = (TileBarrel) entity;
						final Fluid fluid = FluidRegistry.getFluid(msg.fluidName);
						final FluidStack f = fluid == null ? null : new FluidStack(fluid, msg.fillAmount);
						te.getTank().setFluid(f);
					}
				}
			});
			return null;
		}
	}
}