package mod.nethertweaks.network;

import static mod.sfhcore.util.Util.whiteColor;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import io.netty.buffer.ByteBuf;
import mod.nethertweaks.barrel.modes.compost.BarrelModeCompost;
import mod.nethertweaks.block.tile.TileBarrel;
import mod.sfhcore.client.color.ColorGetter;
import mod.sfhcore.texturing.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MessageCompostUpdate implements IMessage {
	public static final Charset CHARSET = StandardCharsets.UTF_8;


	private float fillAmount;
	private float compValue;
	private int x;
	private int y;
	private int z;
	private ItemStack stack;
	private Color color;
	private float progress;
	private boolean isFirst;

	public MessageCompostUpdate() {
	}

	public MessageCompostUpdate(final float fillAmount, final Color color, final ItemStack stack, final float progress, final float compValue, final BlockPos pos, final boolean isFirst) {
		this.compValue = compValue;
		x = pos.getX();
		y = pos.getY();
		z = pos.getZ();
		this.color = color;
		this.fillAmount = fillAmount;
		this.stack = stack;
		this.progress = progress;
		this.isFirst = isFirst;
	}

	@Override
	public void toBytes(final ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeFloat(fillAmount);
		buf.writeFloat(progress);
		buf.writeFloat(color.r);
		buf.writeFloat(color.g);
		buf.writeFloat(color.b);
		buf.writeFloat(color.a);
		buf.writeFloat(compValue);
		buf.writeBoolean(isFirst);
		final String itemName = Objects.requireNonNull(stack.getItem().getRegistryName()).toString();
		buf.writeInt(itemName.length());
		buf.writeCharSequence(stack.getItem().getRegistryName().toString(), CHARSET);
		buf.writeInt(stack.getMetadata());

	}

	@Override
	public void fromBytes(final ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		fillAmount = buf.readFloat();
		progress = buf.readFloat();
		color = new Color(buf.readFloat(),buf.readFloat(),buf.readFloat(),buf.readFloat());
		compValue = buf.readFloat();
		isFirst = buf.readBoolean();
		final int length = buf.readInt();
		final String name = buf.readCharSequence(length, Charset.defaultCharset()).toString();
		final int meta = buf.readInt();

		final Item item = Item.getByNameOrId(name);
		if (item != null)
			stack = new ItemStack(item, 1, meta);
		else
			stack = ItemStack.EMPTY;
	}

	@Override
	public String toString() {
		return "MessageCompostUpdate{" +
				"fillAmount=" + fillAmount +
				", compValue=" + compValue +
				", x=" + x +
				", y=" + y +
				", z=" + z +
				", stack=" + stack +
				", color=" + color +
				", progress=" + progress +
				'}';
	}

	public static class MessageCompostAmountUpdateHandler implements IMessageHandler<MessageCompostUpdate, IMessage> {
		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(final MessageCompostUpdate msg, final MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(new Runnable() {
				@Override
				@SideOnly(Side.CLIENT)
				public void run() {
					final TileEntity entity = Minecraft.getMinecraft().player.getEntityWorld().getTileEntity(new BlockPos(msg.x, msg.y, msg.z));

					if (entity instanceof TileBarrel) {
						final TileBarrel te = (TileBarrel) entity;
						if(te.getMode() == null || !(te.getMode() instanceof BarrelModeCompost))
							te.setMode("compost");
						final BarrelModeCompost mode = (BarrelModeCompost) te.getMode();
						mode.setFillAmount(msg.fillAmount);

						if (msg.stack.isEmpty() && msg.compValue == 0.0f && mode.getOriginalColor() != null)
							// Progress is being made
							mode.setColor(Color.average(mode.getOriginalColor(), whiteColor, msg.progress));
						else {
							// A new item is getting added or the Color has been desynced.
							Color compColor = msg.color;
							// Dynamic color on invalid_color
							if (compColor.equals(Color.INVALID_COLOR) && !msg.stack.isEmpty())
								compColor = ColorGetter.getColor(msg.stack);

							if (msg.fillAmount == 0 || msg.isFirst) {
								mode.setColor(compColor);
								mode.setOriginalColor(compColor);
							} else {
								final Color col = Color.average(mode.getColorForRender(), compColor, msg.compValue);
								mode.setColor(col);
								mode.setOriginalColor(col);
							}

						}

						mode.setProgress(msg.progress);
					}
				}
			});
			return null;
		}
	}

}