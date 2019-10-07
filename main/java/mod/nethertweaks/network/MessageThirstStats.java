package mod.nethertweaks.network;

import io.netty.buffer.ByteBuf;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.modules.thirst.ThirstStats;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageThirstStats implements IMessage {

	private int thirstLevel;
	private float saturation, exhaustion;
	private boolean poisoned;

	public MessageThirstStats() {}

	public MessageThirstStats(final ThirstStats stats) {
		thirstLevel = stats.thirstLevel;
		saturation = stats.saturation;
		exhaustion = stats.exhaustion;
		poisoned = stats.poisoned;
	}

	@Override
	public void fromBytes(final ByteBuf buf) {
		thirstLevel = buf.readInt();
		saturation = buf.readFloat();
		exhaustion = buf.readFloat();
		poisoned = buf.readBoolean();
	}

	@Override
	public void toBytes(final ByteBuf buf) {
		buf.writeInt(thirstLevel);
		buf.writeFloat(saturation);
		buf.writeFloat(exhaustion);
		buf.writeBoolean(poisoned);
	}

	private void handleClientSide() {
		ThirstStats stats = NetherTweaksMod.getClientProxy().clientStats;
		stats.thirstLevel = thirstLevel;
		stats.saturation = saturation;
		stats.exhaustion = exhaustion;
		stats.poisoned = poisoned;
	}

	public static class Handler implements IMessageHandler<MessageThirstStats, IMessage> {
		@Override
		public IMessage onMessage(final MessageThirstStats message, final MessageContext ctx) {
			message.handleClientSide();
			return null;
		}
	}
}
