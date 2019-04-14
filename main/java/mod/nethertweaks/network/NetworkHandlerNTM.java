package mod.nethertweaks.network;

import mod.nethertweaks.NetherTweaksMod;
import mod.sfhcore.Constants;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkHandlerNTM {
	
	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Constants.MOD);
	private static int id = 0;
	
	public static void initPackets()
	{
		INSTANCE.registerMessage(MessageBarrelModeUpdate.MessageBarrelModeUpdateHandler.class, MessageBarrelModeUpdate.class, id++, Side.CLIENT);
		INSTANCE.registerMessage(MessageCompostUpdate.MessageCompostAmountUpdateHandler.class, MessageCompostUpdate.class, id++, Side.CLIENT);
		INSTANCE.registerMessage(MessageFluidLevelUpdate.MessageFluidLevelUpdateHandler.class, MessageFluidLevelUpdate.class, id++, Side.CLIENT);
		INSTANCE.registerMessage(MessageFluidUpdate.MessageFluidUpdateHandler.class, MessageFluidUpdate.class, id++, Side.CLIENT);
		INSTANCE.registerMessage(MessageNBTUpdate.MessageNBTUpdateHandler.class, MessageNBTUpdate.class, id++, Side.CLIENT);
		INSTANCE.registerMessage(MessageCheckLight.MessageCheckLightHandler.class, MessageCheckLight.class, id++, Side.CLIENT);
		INSTANCE.registerMessage(MessageHellmartBuy.MessageHellmartBuyHandler.class, MessageHellmartBuy.class, id++, Side.CLIENT);
		INSTANCE.registerMessage(MessageHellmartClosed.MessageHellmartClosedHandler.class, MessageHellmartClosed.class, id++, Side.CLIENT);
		INSTANCE.registerMessage(MessageHellmartBuy.MessageHellmartBuyHandler.class, MessageHellmartBuy.class, id++, Side.SERVER);
		INSTANCE.registerMessage(MessageHellmartClosed.MessageHellmartClosedHandler.class, MessageHellmartClosed.class, id++, Side.SERVER);
	}
	
	public static void sendToAllAround(IMessage message, TileEntity te, int range) 
	{
		BlockPos pos = te.getPos();
        INSTANCE.sendToAllAround(message, new TargetPoint(te.getWorld().provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), range));
    }
	
	public static void sendToAllAround(IMessage message, TileEntity te) 
	{
        sendToAllAround(message, te, 64);
    }
	
	public static void sendNBTUpdate(TileEntity te) {
		sendToAllAround(new MessageNBTUpdate(te), te);
	}
}
