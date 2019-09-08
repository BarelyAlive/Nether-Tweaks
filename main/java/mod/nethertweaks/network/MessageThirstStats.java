package mod.nethertweaks.network;

import io.netty.buffer.ByteBuf;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.common.logic.ThirstStats;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageThirstStats implements IMessage {

    private int thirstLevel;
    private float saturation, exhaustion;
    private boolean poisoned;

    public MessageThirstStats() {}

    public MessageThirstStats(ThirstStats stats) {
        this.thirstLevel = stats.thirstLevel;
        this.saturation = stats.saturation;
        this.exhaustion = stats.exhaustion;
        this.poisoned = stats.poisoned;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        thirstLevel = buf.readInt();
        saturation = buf.readFloat();
        exhaustion = buf.readFloat();
        poisoned = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.thirstLevel);
        buf.writeFloat(this.saturation);
        buf.writeFloat(this.exhaustion);
        buf.writeBoolean(this.poisoned);
    }

    public void handleClientSide() {
        ThirstStats stats = NetherTweaksMod.getClientProxy().clientStats;
        stats.thirstLevel = this.thirstLevel;
        stats.saturation = this.saturation;
        stats.exhaustion = this.exhaustion;
        stats.poisoned = this.poisoned;
    }

    public static class Handler implements IMessageHandler<MessageThirstStats, IMessage> {
        @Override
        public IMessage onMessage(MessageThirstStats message, MessageContext ctx) {
            message.handleClientSide();
            return null;
        }
    }
}
