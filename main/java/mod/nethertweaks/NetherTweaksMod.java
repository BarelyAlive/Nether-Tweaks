package mod.nethertweaks;
 
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import mod.nethertweaks.blocks.tile.*;
import mod.nethertweaks.capabilities.NTMCapabilities;
import mod.nethertweaks.client.renderers.*;
import mod.nethertweaks.compatibility.Compatibility;
import mod.nethertweaks.config.Config;
import mod.nethertweaks.entities.NTMEntities;
import mod.nethertweaks.entities.ProjectileStone;
import mod.nethertweaks.handler.*;
import mod.nethertweaks.registries.manager.NTMDefaultRecipes;
import mod.nethertweaks.registries.registries.BarrelModeRegistry;
import mod.nethertweaks.world.*;
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
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
 
@Mod(modid=NetherTweaksMod.MODID, name=NetherTweaksMod.MODNAME, version=NetherTweaksMod.VERSION, dependencies=NetherTweaksMod.DEPENDENCIES)
public class NetherTweaksMod
{
	public static final String MODID = "nethertweaksmod";
	public static final String MODNAME = "Nether Tweaks Mod";
	public static final String VERSION = "2.0.3";
	public static final String DEPENDENCIES = "required-after:sfhcore@[2.0.3];";
	
    @Instance(value=MODID)
    public static NetherTweaksMod instance;
    
    static
    {
    	FluidRegistry.enableUniversalBucket();
    	MessageHandler.init();
    }
    
    @Mod.EventBusSubscriber
    public static class OreRegistrationHandler
    {
    	@SubscribeEvent(priority = EventPriority.LOWEST)
    	public static void registerOres (RegistryEvent.Register<Item> event)
    	{
    		String[] ore_names = OreDictionary.getOreNames();
    		for (String ore_name : ore_names)
    		{
    			if (ore_name.startsWith("ore"))
    			{
    				String ore_raw_name = ore_name.substring(3);
    				if(!OreDictionary.doesOreNameExist("ingot" + ore_raw_name))
    				{
    					continue;
    				}
    				if (OreDictionary.getOres(ore_name).size() == 0)
    				{
    					continue;
    				}
    				if(OreDictionary.getOres(ore_name).get(0).getDisplayName().toLowerCase().equals("air"))
    				{
    					continue;
    				}
                	OreHandler.add(OreDictionary.getOres(ore_name).get(0).getItem(), 1);
    			}
    		}
    		OreHandler.register(event.getRegistry());
    	}
    	
        @SubscribeEvent(priority = EventPriority.LOWEST)
    	@SideOnly(Side.CLIENT)
        public static void registerItemHandlers(ColorHandlerEvent.Item event)
        {
        	OreHandler.registerItemHandlers(event);
        }
    	
    	@SubscribeEvent(priority = EventPriority.LOWEST)
    	@SideOnly(Side.CLIENT)
    	public static void registerOreModels(ModelRegistryEvent event)
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
        BucketNFluidHandler.init(event.getSide());
        
        GameRegistry.registerWorldGenerator(new WorldGeneratorNTM(), 1);
        
        MinecraftForge.EVENT_BUS.register(new WorldEvents());
    	MinecraftForge.EVENT_BUS.register(new HammerHandler());
    	MinecraftForge.EVENT_BUS.register(this);
    	
        if(event.getSide() == Side.CLIENT)
        {
        	OreHandler.disableOre("minecraft:redstone");
    		OreHandler.disableOre("minecraft:coal");
        }
    	// Disable all copper ores except all ores from thermal foundation
    	/*
    	OreHandler.disableOre("copper");
    	OreHandler.enableOre("thermalfoundation:ore");
    	*/
    	
        //GUI
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandlerNTM());
		
		if(event.getSide() == Side.CLIENT)
			RenderingRegistry.registerEntityRenderingHandler(ProjectileStone.class, new RenderProjectileStone.Factory());
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
    @SideOnly(Side.CLIENT)
    public void registerModels(ModelRegistryEvent event)
    {
    	ClientRegistry.bindTileEntitySpecialRenderer(TileCrucibleStone.class, new RenderCrucible());
    	ClientRegistry.bindTileEntitySpecialRenderer(TileSieve.class, new RenderSieve());
    	ClientRegistry.bindTileEntitySpecialRenderer(TileBarrel.class, new RenderBarrel());
    	ClientRegistry.bindTileEntitySpecialRenderer(TileAshBonePile.class, new RenderAshBonePile());
    }
}
