package mod.nethertweaks;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import mod.nethertweaks.capabilities.ModCapabilities;
import mod.nethertweaks.compatibility.Compatibility;
import mod.nethertweaks.config.Config;
import mod.nethertweaks.init.GuiHandler;
import mod.nethertweaks.init.ModBlocks;
import mod.nethertweaks.init.ModEntities;
import mod.nethertweaks.init.ModFluids;
import mod.nethertweaks.init.ModItems;
import mod.nethertweaks.init.ModJsonRecipes;
import mod.nethertweaks.init.ModMessages;
import mod.nethertweaks.init.ModSmeltingNOreDict;
import mod.nethertweaks.init.OreHandler;
import mod.nethertweaks.proxy.IProxy;
import mod.nethertweaks.proxy.ServerProxy;
import mod.nethertweaks.registry.manager.NTMDefaultRecipes;
import mod.nethertweaks.registry.registries.BarrelModeRegistry;
import mod.nethertweaks.world.EventHook;
import mod.nethertweaks.world.Hellworld;
import mod.nethertweaks.world.WorldGeneratorNTM;
import mod.sfhcore.modules.ISFHCoreModule;
import mod.sfhcore.util.LogUtil;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid=Constants.MOD_ID, name=Constants.MOD_NAME, version=Constants.VERSION, dependencies=Constants.DEPENDENCIES, acceptedMinecraftVersions=Constants.MC_VERSION)
public class NetherTweaksMod
{
	public static File configDirectory;
	public static final Gson GSON_INSTANCE = new Gson();
	public static final List<ISFHCoreModule> LOADED_MODULES = new ArrayList<>();

	@Instance(value=Constants.MOD_ID)
	private static NetherTweaksMod instance;

	public static NetherTweaksMod getInstance() {
		return instance;
	}

	static
	{
		FluidRegistry.enableUniversalBucket();
	}

	@SidedProxy(clientSide=Constants.CLIENT_PROXY, serverSide=Constants.SERVER_PROXY, modId=Constants.MOD_ID)
	private static IProxy proxy;

	public static IProxy getProxy() {
		return proxy;
	}

	@Mod.EventHandler
	public void preInit(final FMLPreInitializationEvent event)
	{
		configDirectory = new File(event.getModConfigurationDirectory(), Constants.MOD_ID);
		Config.preInit();

		LogUtil.setup(Constants.MOD_ID, configDirectory);

		Compatibility.preInit();

		ModMessages.registerMessages();
		ModBlocks.preInit();
		ModItems.preInit();

		ModFluids.preInit();
		ModCapabilities.preInit();
		ModEntities.preInit();
		new Hellworld(); //makes it register itself

		GameRegistry.registerWorldGenerator(new WorldGeneratorNTM(), 1);

		EventHook.preInit();

		getProxy().preInit();
	}

	@Mod.EventHandler
	public void init(final FMLInitializationEvent event)
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
		ModSmeltingNOreDict.init();
		ModItems.init();

		getProxy().init();
	}

	@Mod.EventHandler
	public void postInit(final FMLPostInitializationEvent event)
	{
		OreHandler.registerFurnaceRecipe();
		//Mobs
		EventHook.postInit();

		BarrelModeRegistry.registerDefaults();
		NTMDefaultRecipes.registerDefaults();
		ModJsonRecipes.postInit(configDirectory);

		getProxy().postInit();
	}

	@Mod.EventHandler
	public void onServerStopped(final FMLServerStoppedEvent event) {
		ServerProxy.LOADED_PLAYERS.clear();
	}
}
