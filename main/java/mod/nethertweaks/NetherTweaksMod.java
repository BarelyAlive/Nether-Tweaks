package mod.nethertweaks;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import mod.nethertweaks.blocks.tile.TileAshBonePile;
import mod.nethertweaks.blocks.tile.TileBarrel;
import mod.nethertweaks.blocks.tile.TileCrucibleStone;
import mod.nethertweaks.blocks.tile.TileSieve;
import mod.nethertweaks.capabilities.NTMCapabilities;
import mod.nethertweaks.client.logic.ClientProxy;
import mod.nethertweaks.client.renderers.RenderAshBonePile;
import mod.nethertweaks.client.renderers.RenderBarrel;
import mod.nethertweaks.client.renderers.RenderCrucible;
import mod.nethertweaks.client.renderers.RenderSieve;
import mod.nethertweaks.common.logic.CommonProxy;
import mod.nethertweaks.compatibility.Compatibility;
import mod.nethertweaks.config.Config;
import mod.nethertweaks.entities.NTMEntities;
import mod.nethertweaks.handler.BlockHandler;
import mod.nethertweaks.handler.BucketNFluidHandler;
import mod.nethertweaks.handler.HammerHandler;
import mod.nethertweaks.handler.ItemHandler;
import mod.nethertweaks.handler.JsonRecipeHandler;
import mod.nethertweaks.handler.MessageHandler;
import mod.nethertweaks.handler.OreHandler;
import mod.nethertweaks.handler.SmeltingNOreDictHandler;
import mod.nethertweaks.registries.manager.NTMDefaultRecipes;
import mod.nethertweaks.registries.registries.BarrelModeRegistry;
import mod.nethertweaks.world.WorldEvents;
import mod.nethertweaks.world.WorldGeneratorNTM;
import mod.nethertweaks.world.WorldTypeHellworld;
import mod.sfhcore.modules.ISFHCoreModule;
import mod.sfhcore.util.LogUtil;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.world.WorldType;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

@Mod(modid=NetherTweaksMod.MODID, name=NetherTweaksMod.MODNAME, version=NetherTweaksMod.VERSION, dependencies=NetherTweaksMod.DEPENDENCIES)
public class NetherTweaksMod
{
	public static final String MODID = "nethertweaksmod";
	public static final String MODNAME = "Nether Tweaks Mod";
	public static final String VERSION = "2.1.0";
	public static final String DEPENDENCIES = "required-after:sfhcore@[2.0.3];";

	@Instance(value=MODID)
	private static NetherTweaksMod instance;

	public static NetherTweaksMod getInstance() {
		return instance;
	}

	static
	{
		FluidRegistry.enableUniversalBucket();
		MessageHandler.init();
	}

	@SidedProxy(clientSide="mod.nethertweaks.client.logic.ClientProxy", serverSide="mod.nethertweaks.common.logic.CommonProxy", modId=MODID)
	private static CommonProxy commonProxy;

	public static CommonProxy getProxy() {
		return commonProxy;
	}

	@SideOnly(Side.CLIENT)
	public static ClientProxy getClientProxy() {
		return (ClientProxy) commonProxy;
	}

	public static Gson gsonInstance = new Gson();

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

	// List of loaded modules
	public static final List<ISFHCoreModule> loadedModules = new ArrayList<>();
	//Creative Tabs
	public static final CreativeTabs TABNTM = new CreativeTabNTM();

	public static File configDirectory;
	public WorldType Hellworld = new WorldTypeHellworld();

	@Mod.EventHandler
	public void PreInit(final FMLPreInitializationEvent event)
	{
		configDirectory = new File(event.getModConfigurationDirectory(), MODID);

		LogUtil.setup(MODID, configDirectory);

		Config.init();

		Compatibility.init();

		NTMCapabilities.init();
		NTMEntities.init();

		ItemHandler.init();
		BlockHandler.init();
		BucketNFluidHandler.init(event.getSide());

		GameRegistry.registerWorldGenerator(new WorldGeneratorNTM(), 1);

		MinecraftForge.EVENT_BUS.register(new WorldEvents());
		MinecraftForge.EVENT_BUS.register(new HammerHandler());
		MinecraftForge.EVENT_BUS.register(this);

		// Disable all copper ores except all ores from thermal foundation
		/*
    	OreHandler.disableOre("copper");
    	OreHandler.enableOre("thermalfoundation:ore");
		 */

		getProxy().preInit();
	}

	@Mod.EventHandler
	public void load(final FMLInitializationEvent event)
	{
		SmeltingNOreDictHandler.load();
		getProxy().init();
	}

	@Mod.EventHandler
	public void PostInit(final FMLPostInitializationEvent event)
	{
		OreHandler.registerFurnaceRecipe();
		//Mobs
		if(Config.spawnWaterMobs) WorldEvents.addWaterMobs();

		BarrelModeRegistry.registerDefaults();
		NTMDefaultRecipes.registerDefaults();
		JsonRecipeHandler.loadJasonVorhees(configDirectory);
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void registerModels(final ModelRegistryEvent event)
	{
		ClientRegistry.bindTileEntitySpecialRenderer(TileCrucibleStone.class, new RenderCrucible());
		ClientRegistry.bindTileEntitySpecialRenderer(TileSieve.class, new RenderSieve());
		ClientRegistry.bindTileEntitySpecialRenderer(TileBarrel.class, new RenderBarrel());
		ClientRegistry.bindTileEntitySpecialRenderer(TileAshBonePile.class, new RenderAshBonePile());
	}

	@Mod.EventHandler
	public void onServerStopped(final FMLServerStoppedEvent event) {
		getProxy().loadedPlayers.clear();
	}
}
