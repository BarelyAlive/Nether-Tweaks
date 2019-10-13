package mod.nethertweaks.init;

import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.network.MessageBarrelModeUpdate;
import mod.nethertweaks.network.MessageCompostUpdate;
import mod.nethertweaks.network.MessageFluidUpdate;
import mod.nethertweaks.network.MessageHellmartBuy;
import mod.nethertweaks.network.MessageHellmartClosed;
import mod.nethertweaks.network.MessageMovementSpeed;
import mod.nethertweaks.network.MessageTeleportPlayer;
import mod.nethertweaks.network.MessageThirstStats;
import mod.nethertweaks.network.bonfire.MessageBonfireGetList;
import mod.nethertweaks.network.bonfire.MessageBonfireSetSpawnPoint;
import mod.nethertweaks.network.bonfire.MessageBonfireUpdate;
import mod.nethertweaks.network.bonfire.MessageLastSpawnUpdate;
import mod.sfhcore.network.MessageCheckLight;
import mod.sfhcore.network.MessageNBTUpdate;
import mod.sfhcore.network.NetworkHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.relauncher.Side;

public class ModMessages {
    // Start the IDs at 1 so any unregistered messages (ID 0) throw a more obvious exception when received
    private static int messageID = 1;

    public static void registerMessages() {

        registerMessage(MessageBarrelModeUpdate.MessageBarrelModeUpdateHandler.class, MessageBarrelModeUpdate.class, Side.CLIENT);
        registerMessage(MessageCompostUpdate.MessageCompostAmountUpdateHandler.class, MessageCompostUpdate.class, Side.CLIENT);
        registerMessage(MessageFluidUpdate.MessageFluidUpdateHandler.class, MessageFluidUpdate.class, Side.CLIENT);
        registerMessage(MessageNBTUpdate.MessageNBTUpdateHandler.class, MessageNBTUpdate.class, Side.CLIENT);
        registerMessage(MessageCheckLight.MessageCheckLightHandler.class, MessageCheckLight.class, Side.CLIENT);
        //Hellmart
        registerMessage(MessageHellmartBuy.MessageHellmartBuyHandler.class, MessageHellmartBuy.class, Side.SERVER);
        registerMessage(MessageHellmartClosed.MessageHellmartClosedHandler.class, MessageHellmartClosed.class, Side.CLIENT);
        //Bonfire
        registerMessage(MessageTeleportPlayer.MessageTeleportPlayerHandler.class, MessageTeleportPlayer.class, Side.SERVER);
        registerMessage(MessageBonfireSetSpawnPoint.MessageBonfireSetSpawnPointHandler.class, MessageBonfireSetSpawnPoint.class, Side.SERVER);
        registerMessage(MessageBonfireGetList.MessageWorldSaveDataHandler.class, MessageBonfireGetList.class, Side.CLIENT);
        registerMessage(MessageBonfireUpdate.MessageBonfireUpdateHandler.class, MessageBonfireUpdate.class, Side.SERVER);
        registerMessage(MessageBonfireUpdate.MessageBonfireUpdateHandler.class, MessageBonfireUpdate.class, Side.CLIENT);
        registerMessage(MessageLastSpawnUpdate.MessageLastSpawnUpdateHandler.class, MessageLastSpawnUpdate.class, Side.SERVER);
        registerMessage(MessageLastSpawnUpdate.MessageLastSpawnUpdateHandler.class, MessageLastSpawnUpdate.class, Side.CLIENT);
        //Thirst
        registerMessage(MessageThirstStats.Handler.class, MessageThirstStats.class, Side.CLIENT);
        registerMessage(MessageMovementSpeed.Handler.class, MessageMovementSpeed.class, Side.SERVER);
    }

    private static <REQ extends IMessage, REPLY extends IMessage> void registerMessage(final Class<? extends IMessageHandler<REQ, REPLY>> messageHandler, final Class<REQ> requestMessageType, final Side receivingSide) {
        NetworkHandler.INSTANCE.registerMessage(messageHandler, requestMessageType, messageID++, receivingSide);
    }
}
