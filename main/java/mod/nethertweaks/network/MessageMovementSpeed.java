package mod.nethertweaks.network;

import java.util.UUID;

import io.netty.buffer.ByteBuf;
import mod.nethertweaks.modules.thirst.ThirstStats;
import mod.nethertweaks.proxy.ServerProxy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class MessageMovementSpeed implements IMessage {

	private UUID uuid;
	private int ms = 0;

	public MessageMovementSpeed() {}

	public MessageMovementSpeed(final EntityPlayer player, final ThirstStats stats) {
		uuid = player.getUniqueID();
		ms = stats.getMovementSpeed(player);
	}

	@Override
	public void fromBytes(final ByteBuf buf) {
		uuid = UUID.fromString(ByteBufUtils.readUTF8String(buf));
		ms = buf.readInt();
	}

	@Override
	public void toBytes(final ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, uuid.toString());
		buf.writeInt(ms);
	}

	public void handleServerSide() {
		ServerProxy.getStatsByUUID(uuid).movementSpeed = ms;
	}

	public static class Handler implements IMessageHandler<MessageMovementSpeed, IMessage> {
		@Override
		public IMessage onMessage(final MessageMovementSpeed message, final MessageContext ctx) {
			if (ctx.side == Side.SERVER)
				message.handleServerSide();
			return null;
		}
	}
}
