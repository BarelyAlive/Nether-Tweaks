package mod.nethertweaks;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import mod.nethertweaks.handler.MessageHandler;
import mod.nethertweaks.handler.OreHandler;
import mod.nethertweaks.proxy.ClientProxy;
import mod.nethertweaks.proxy.CommonProxy;
import mod.nethertweaks.world.Hellworld;
import mod.sfhcore.modules.ISFHCoreModule;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.world.WorldType;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

@Mod(modid=Constants.MODID, name=Constants.MODNAME, version=Constants.VERSION, dependencies=Constants.DEPENDENCIES)
public class NetherTweaksMod
{
	public WorldType Hellworld = new Hellworld();
	public static Gson gsonInstance = new Gson();
	public static File configDirectory;
	public static final CreativeTabs TAB = new CreativeTabNTM();
	public static final List<ISFHCoreModule> loadedModules = new ArrayList<>();

	@Instance(value=Constants.MODID)
	private static NetherTweaksMod instance;

	public static NetherTweaksMod getInstance() {
		return instance;
	}

	static
	{
		FluidRegistry.enableUniversalBucket();
		MessageHandler.init();
	}

	@SidedProxy(clientSide=Constants.CLIENT_PROXY, serverSide=Constants.COMMON_PROXY, modId=Constants.MODID)
	private static CommonProxy commonProxy;

	public static CommonProxy getProxy() {
		return commonProxy;
	}

	@SideOnly(Side.CLIENT)
	public static ClientProxy getClientProxy() {
		return (ClientProxy) commonProxy;
	}

	@Mod.EventBusSubscriber
	public static class OreRegistrationHandler
	{
		@SubscribeEvent(priority = EventPriority.LOWEST)
		public static void registerOres (final RegistryEvent.Register<Item> event)
		{
			String[] ore_names = OreDictionary.getOreNames();
			for (String ore_name : ore_names)
				if (ore_name.startsWith("ore"))
				{
					String ore_raw_name = ore_name.substring(3);
					if(!OreDictionary.doesOreNameExist("ingot" + ore_raw_name))
						continue;
					if (OreDictionary.getOres(ore_name).size() == 0)
						continue;
					if(OreDictionary.getOres(ore_name).get(0).getDisplayName().toLowerCase().equals("air"))
						continue;
					OreHandler.add(OreDictionary.getOres(ore_name).get(0).getItem(), 1);
				}
			OreHandler.register(event.getRegistry());
		}

		@SubscribeEvent(priority = EventPriority.LOWEST)
		@SideOnly(Side.CLIENT)
		public static void registerItemHandlers(final ColorHandlerEvent.Item event)
		{
			OreHandler.registerItemHandlers(event);
		}

		@SubscribeEvent(priority = EventPriority.LOWEST)
		@SideOnly(Side.CLIENT)
		public static void registerOreModels(final ModelRegistryEvent event)
		{
			OreHandler.registerModels(event);
		}
	}

	@Mod.EventHandler
	public void PreInit(final FMLPreInitializationEvent event)
	{
		configDirectory = new File(event.getModConfigurationDirectory(), Constants.MODID);

		if(event.getSide().isClient())
			getClientProxy().preInit();
		else
			getProxy().preInit();
	}

	@Mod.EventHandler
	public void load(final FMLInitializationEvent event)
	{
		if(event.getSide().isClient())
			getClientProxy().init();
		else
			getProxy().init();
	}

	@Mod.EventHandler
	public void PostInit(final FMLPostInitializationEvent event)
	{
		if(event.getSide().isClient())
			getClientProxy().postInit();
		else
			getProxy().postInit();
	}

	@Mod.EventHandler
	public void onServerStopped(final FMLServerStoppedEvent event) {
		getProxy().loadedPlayers.clear();
	}
}