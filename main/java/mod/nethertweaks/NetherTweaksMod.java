package mod.nethertweaks;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import mod.nethertweaks.capabilities.NTMCapabilities;
import mod.nethertweaks.compatibility.Compatibility;
import mod.nethertweaks.config.Config;
import mod.nethertweaks.entities.NTMEntities;
import mod.nethertweaks.handler.BlockHandler;
import mod.nethertweaks.handler.FluidHandler;
import mod.nethertweaks.handler.GuiHandler;
import mod.nethertweaks.handler.HammerHandler;
import mod.nethertweaks.handler.ItemHandler;
import mod.nethertweaks.handler.JsonRecipeHandler;
import mod.nethertweaks.handler.MessageHandler;
import mod.nethertweaks.handler.OreHandler;
import mod.nethertweaks.handler.OreRegistrationHandler;
import mod.nethertweaks.handler.SmeltingNOreDictHandler;
import mod.nethertweaks.proxy.ClientProxy;
import mod.nethertweaks.proxy.CommonProxy;
import mod.nethertweaks.registries.manager.NTMDefaultRecipes;
import mod.nethertweaks.registries.registries.BarrelModeRegistry;
import mod.nethertweaks.world.EventHook;
import mod.nethertweaks.world.Hellworld;
import mod.nethertweaks.world.WorldGeneratorNTM;
import mod.sfhcore.modules.ISFHCoreModule;
import mod.sfhcore.util.LogUtil;
import net.minecraftforge.common.MinecraftForge;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid=Constants.MOD_ID, name=Constants.MOD_NAME, version=Constants.VERSION, dependencies=Constants.DEPENDENCIES, acceptedMinecraftVersions=Constants.MC_VERSION)
public class NetherTweaksMod
{
	public static final Gson gsonInstance = new Gson();
	public static File configDirectory;
	public static final List<ISFHCoreModule> LOADED_MODULES = new ArrayList<>();

	@Instance(value=Constants.MOD_ID)
	private static NetherTweaksMod instance;

	public static NetherTweaksMod getInstance() {
		return instance;
	}

	static
	{
		FluidRegistry.enableUniversalBucket();
		MessageHandler.init();
	}

	@SidedProxy(clientSide=Constants.CLIENT_PROXY, serverSide=Constants.SERVER_PROXY, modId=Constants.MOD_ID)
	private static CommonProxy proxy;

	public static CommonProxy getProxy() {
		return proxy;
	}

	@SideOnly(Side.CLIENT)
	public static ClientProxy getClientProxy() {
		return (ClientProxy) proxy;
	}

	@Mod.EventHandler
	public void PreInit(final FMLPreInitializationEvent event)
	{
		configDirectory = new File(event.getModConfigurationDirectory(), Constants.MOD_ID);

		LogUtil.setup(Constants.MOD_ID, configDirectory);

		Config.init();

		Compatibility.init();

		new BlockHandler();
		new ItemHandler();

		FluidHandler.init();
		NTMCapabilities.init();
		NTMEntities.init();
		new Hellworld(); //makes it register itself

		GameRegistry.registerWorldGenerator(new WorldGeneratorNTM(), 1);

		MinecraftForge.EVENT_BUS.register(new OreRegistrationHandler());
		MinecraftForge.EVENT_BUS.register(new EventHook());
		MinecraftForge.EVENT_BUS.register(new HammerHandler());

		getProxy().preInit();
	}

	@Mod.EventHandler
	public void load(final FMLInitializationEvent event)
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
		new SmeltingNOreDictHandler();
		
		getProxy().init();
	}

	@Mod.EventHandler
	public void PostInit(final FMLPostInitializationEvent event)
	{
		OreHandler.registerFurnaceRecipe();
		//Mobs
		EventHook.addWaterMobs();

		BarrelModeRegistry.registerDefaults();
		NTMDefaultRecipes.registerDefaults();
		JsonRecipeHandler.loadJasonVorhees(configDirectory);
	}

	@Mod.EventHandler
	public void onServerStopped(final FMLServerStoppedEvent event) {
		getProxy().loadedPlayers.clear();
	}
}
