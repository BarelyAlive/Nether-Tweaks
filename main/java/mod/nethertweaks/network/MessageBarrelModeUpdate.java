package mod.nethertweaks.network;

import io.netty.buffer.ByteBuf;
import mod.nethertweaks.block.tile.TileBarrel;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageBarrelModeUpdate implements IMessage {

	public MessageBarrelModeUpdate(){}

	private String modeName;
	private int x, y, z;
	public MessageBarrelModeUpdate(final String modeName, final BlockPos pos)
	{
		x = pos.getX();
		y = pos.getY();
		z = pos.getZ();
		this.modeName = modeName;
	}

	@Override
	public void toBytes(final ByteBuf buf)
	{
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		ByteBufUtils.writeUTF8String(buf, modeName);
	}

	@Override
	public void fromBytes(final ByteBuf buf)
	{
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		modeName = ByteBufUtils.readUTF8String(buf);
	}

	public static class MessageBarrelModeUpdateHandler implements IMessageHandler<MessageBarrelModeUpdate, IMessage>
	{
		@Override
		public IMessage onMessage(final MessageBarrelModeUpdate msg, final MessageContext ctx)
		{
			Minecraft.getMinecraft().addScheduledTask(() -> {
				final TileEntity entity =  Minecraft.getMinecraft().player.world.getTileEntity(new BlockPos(msg.x, msg.y, msg.z));
				if (entity instanceof TileBarrel)
				{
					final TileBarrel te = (TileBarrel) entity;
					te.setMode(msg.modeName);
				}
			});
			return null;
		}
	}
}
