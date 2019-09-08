package mod.nethertweaks.handler;

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
import net.minecraftforge.fml.relauncher.Side;

public class MessageHandler
{
	public static void init()
	{
		NetworkHandler.registerMessage(MessageBarrelModeUpdate.MessageBarrelModeUpdateHandler.class, MessageBarrelModeUpdate.class, Side.CLIENT);
		NetworkHandler.registerMessage(MessageCompostUpdate.MessageCompostAmountUpdateHandler.class, MessageCompostUpdate.class, Side.CLIENT);
		NetworkHandler.registerMessage(MessageFluidUpdate.MessageFluidUpdateHandler.class, MessageFluidUpdate.class, Side.CLIENT);
		NetworkHandler.registerMessage(MessageNBTUpdate.MessageNBTUpdateHandler.class, MessageNBTUpdate.class, Side.CLIENT);
		NetworkHandler.registerMessage(MessageCheckLight.MessageCheckLightHandler.class, MessageCheckLight.class, Side.CLIENT);
		NetworkHandler.registerMessage(MessageHellmartBuy.MessageHellmartBuyHandler.class, MessageHellmartBuy.class, Side.SERVER);
		NetworkHandler.registerMessage(MessageHellmartClosed.MessageHellmartClosedHandler.class, MessageHellmartClosed.class, Side.CLIENT);
		NetworkHandler.registerMessage(MessageTeleportPlayer.MessageTeleportPlayerHandler.class, MessageTeleportPlayer.class, Side.SERVER);
		NetworkHandler.registerMessage(MessageBonfireSetSpawnPoint.MessageBonfireSetSpawnPointHandler.class, MessageBonfireSetSpawnPoint.class, Side.SERVER);
		NetworkHandler.registerMessage(MessageBonfireGetList.MessageWorldSaveDataHandler.class, MessageBonfireGetList.class, Side.CLIENT);
		NetworkHandler.registerMessage(MessageBonfireUpdate.MessageBonfireUpdateHandler.class, MessageBonfireUpdate.class, Side.SERVER);
		NetworkHandler.registerMessage(MessageBonfireUpdate.MessageBonfireUpdateHandler.class, MessageBonfireUpdate.class, Side.CLIENT);
		NetworkHandler.registerMessage(MessageLastSpawnUpdate.MessageLastSpawnUpdateHandler.class, MessageLastSpawnUpdate.class, Side.SERVER);
		NetworkHandler.registerMessage(MessageLastSpawnUpdate.MessageLastSpawnUpdateHandler.class, MessageLastSpawnUpdate.class, Side.CLIENT);

		NetworkHandler.registerMessage(MessageThirstStats.Handler.class, MessageThirstStats.class, Side.CLIENT);
		NetworkHandler.registerMessage(MessageMovementSpeed.Handler.class, MessageMovementSpeed.class, Side.SERVER);
	}
}
