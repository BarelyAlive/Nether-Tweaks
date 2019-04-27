package mod.nethertweaks.handler;

import mod.nethertweaks.network.MessageBarrelModeUpdate;
import mod.nethertweaks.network.MessageCompostUpdate;
import mod.nethertweaks.network.MessageFluidLevelUpdate;
import mod.nethertweaks.network.MessageFluidUpdate;
import mod.nethertweaks.network.MessageHellmartBuy;
import mod.nethertweaks.network.MessageHellmartClosed;
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
		NetworkHandler.registerMessage(MessageFluidLevelUpdate.MessageFluidLevelUpdateHandler.class, MessageFluidLevelUpdate.class, Side.CLIENT);
		NetworkHandler.registerMessage(MessageFluidUpdate.MessageFluidUpdateHandler.class, MessageFluidUpdate.class, Side.CLIENT);
		NetworkHandler.registerMessage(MessageNBTUpdate.MessageNBTUpdateHandler.class, MessageNBTUpdate.class, Side.CLIENT);
		NetworkHandler.registerMessage(MessageCheckLight.MessageCheckLightHandler.class, MessageCheckLight.class, Side.CLIENT);
		NetworkHandler.registerMessage(MessageHellmartBuy.MessageHellmartBuyHandler.class, MessageHellmartBuy.class, Side.CLIENT);
		NetworkHandler.registerMessage(MessageHellmartClosed.MessageHellmartClosedHandler.class, MessageHellmartClosed.class, Side.CLIENT);
	}
}
