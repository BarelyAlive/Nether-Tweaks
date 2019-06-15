package mod.nethertweaks;
 
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.ricardothecoder.yac.library.fluids.FluidBase;

import mod.nethertweaks.blocks.tile.TileBarrel;
import mod.nethertweaks.blocks.tile.TileCrucibleStone;
import mod.nethertweaks.blocks.tile.TileSieve;
import mod.nethertweaks.capabilities.NTMCapabilities;
import mod.nethertweaks.client.renderers.RenderBarrel;
import mod.nethertweaks.client.renderers.RenderCrucible;
import mod.nethertweaks.client.renderers.RenderProjectileStone;
import mod.nethertweaks.client.renderers.RenderSieve;
import mod.nethertweaks.compatibility.Compatibility;
import mod.nethertweaks.config.Config;
import mod.nethertweaks.entities.NTMEntities;
import mod.nethertweaks.entities.ProjectileStone;
import mod.nethertweaks.handler.BlockHandler;
import mod.nethertweaks.handler.BucketNFluidHandler;
import mod.nethertweaks.handler.GuiHandlerNTM;
import mod.nethertweaks.handler.HammerHandler;
import mod.nethertweaks.handler.ItemHandler;
import mod.nethertweaks.handler.JsonRecipeHandler;
import mod.nethertweaks.handler.MessageHandler;
import mod.nethertweaks.handler.OreHandler;
import mod.nethertweaks.handler.SmeltingNOreDictHandler;
import mod.nethertweaks.modules.MooFluidsEtc;
import mod.nethertweaks.registries.manager.NTMDefaultRecipes;
import mod.nethertweaks.registries.manager.NTMRegistryManager;
import mod.nethertweaks.registries.registries.BarrelLiquidBlacklistRegistry;
import mod.nethertweaks.registries.registries.BarrelModeRegistry;
import mod.nethertweaks.registries.registries.CompostRegistry;
import mod.nethertweaks.registries.registries.CondenserRegistry;
import mod.nethertweaks.registries.registries.FluidBlockTransformerRegistry;
import mod.nethertweaks.registries.registries.FluidOnTopRegistry;
import mod.nethertweaks.registries.registries.FluidTransformRegistry;
import mod.nethertweaks.registries.registries.HammerRegistry;
import mod.nethertweaks.registries.registries.HellmartRegistry;
import mod.nethertweaks.registries.registries.OreRegistry;
import mod.nethertweaks.registries.registries.SieveRegistry;
import mod.nethertweaks.registry.types.FluidBlockTransformer;
import mod.nethertweaks.world.WorldGeneratorNTM;
import mod.nethertweaks.world.WorldEvents;
import mod.nethertweaks.world.WorldTypeHellworld;
import mod.sfhcore.Constants;
import mod.sfhcore.blocks.CustomDoor;
import mod.sfhcore.blocks.itemblocks.ItemDoor;
import mod.sfhcore.modules.ISFHCoreModule;
import mod.sfhcore.network.NetworkHandler;
import mod.sfhcore.util.LogUtil;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.world.WorldType;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.ForgeChunkManager.LoadingCallback;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fluids.capability.wrappers.FluidBlockWrapper;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.RegistryManager;
 
@Mod(modid=NetherTweaksMod.MODID, name=NetherTweaksMod.MODNAME, version=NetherTweaksMod.VERSION, dependencies=NetherTweaksMod.DEPENDENCIES)
public class NetherTweaksMod
{   
	public static final String MODID = "nethertweaksmod";
	public static final String MODNAME = "Nether Tweaks Mod";
	public static final String VERSION = "2.0.0";
	public static final String DEPENDENCIES = "required-after:sfhcore@[2.0.0];";
	
    @Instance(value=MODID)
    public static NetherTweaksMod instance;
    
    static
    {   
    	FluidRegistry.enableUniversalBucket();
    	MessageHandler.init();
    }
    
    @Mod.EventBusSubscriber
    public static class BucketRegistrationHandler
    {
    	@SubscribeEvent(priority = EventPriority.LOWEST)
    	public static void registerBuckets (RegistryEvent.Register<Item> event)
    	{
    		String[] ore_names = OreDictionary.getOreNames();
    		for (String ore_name : ore_names)
    		{
    			if (ore_name.startsWith("ore"))
    			{
    				if(!OreDictionary.doesOreNameExist("ingot" + ore_name.substring(3)))
    				{
    					continue;
    				}
                	OreHandler.add(OreDictionary.getOres(ore_name).get(0), 1);
    			}
    		}
    		OreHandler.register(event.getRegistry());
    	}
    	
        @SubscribeEvent(priority = EventPriority.LOWEST)
        public static void registerItemHandlers(ColorHandlerEvent.Item event)
        {
        	OreHandler.registerItemHandlers(event);
        }
    	
    	@SubscribeEvent(priority = EventPriority.LOWEST)
    	public static void registerBucketModels(ModelRegistryEvent event)
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
    public void PreInit(FMLPreInitializationEvent event)
    {
    	configDirectory = new File(event.getModConfigurationDirectory(), MODID);
    	
    	LogUtil.setup(MODID, configDirectory);

    	Config.init();
    	
    	Compatibility.init();
    	
    	NTMCapabilities.init();
    	NTMEntities.init();

        ItemHandler.init();    	
        BlockHandler.init();
        BucketNFluidHandler.init();
        
        GameRegistry.registerWorldGenerator(new WorldGeneratorNTM(BlockHandler.BLOCK_BASIC.getDefaultState(), 16, 16), 1);
        
        MinecraftForge.EVENT_BUS.register(new WorldEvents());
    	MinecraftForge.EVENT_BUS.register(new HammerHandler());
    	MinecraftForge.EVENT_BUS.register(this);
    	
    	OreHandler.disableOre("minecraft:redstone");
    	OreHandler.disableOre("minecraft:coal");
    	// Disable all copper ores except all ores from thermal foundation
    	/*
    	OreHandler.disableOre("copper");
    	OreHandler.enableOre("thermalfoundation:ore");
    	*/
    	
        //GUI
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandlerNTM());	
    }
    
    @Mod.EventHandler
    public void load(FMLInitializationEvent event)
    {

 	SmeltingNOreDictHandler.load();

    }
     
    @Mod.EventHandler
    public void PostInit(FMLPostInitializationEvent event)
    {
		OreHandler.registerFurnaceRecipe();
    	//Mobs
    	if(Config.spawnWaterMobs) WorldEvents.addWaterMobs();
    	        
		BarrelModeRegistry.registerDefaults();
    	
		NTMDefaultRecipes.registerDefaults();
		
    	JsonRecipeHandler.loadJasonVorhees(configDirectory);
    }
    
    @SubscribeEvent
    public void registerModels(ModelRegistryEvent event)
    {
    	ClientRegistry.bindTileEntitySpecialRenderer(TileCrucibleStone.class, new RenderCrucible());
    	ClientRegistry.bindTileEntitySpecialRenderer(TileSieve.class, new RenderSieve());
    	ClientRegistry.bindTileEntitySpecialRenderer(TileBarrel.class, new RenderBarrel());
    	
    	RenderingRegistry.registerEntityRenderingHandler(ProjectileStone.class, new RenderProjectileStone.Factory());
    }
}
