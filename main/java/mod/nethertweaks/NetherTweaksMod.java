package mod.nethertweaks;
 
import java.io.File;

import mod.nethertweaks.blocks.tile.TileBarrel;
import mod.nethertweaks.blocks.tile.TileSieve;
import mod.nethertweaks.client.renderers.RenderBarrel;
import mod.nethertweaks.client.renderers.RenderSieve;
import mod.nethertweaks.entities.NTMEntities;
import mod.nethertweaks.handler.BlockHandler;
import mod.nethertweaks.handler.BucketNFluidHandler;
import mod.nethertweaks.handler.GuiHandlerNTM;
import mod.nethertweaks.handler.HandlerHammer;
import mod.nethertweaks.handler.ItemHandler;
import mod.nethertweaks.handler.OreHandler;
import mod.nethertweaks.handler.RecipeHandler;
import mod.nethertweaks.network.NetworkHandlerNTM;
import mod.nethertweaks.registry.BarrelLiquidBlacklistRegistry;
import mod.nethertweaks.registry.BarrelModeRegistry;
import mod.nethertweaks.registry.CompostRegistry;
import mod.nethertweaks.registry.CondenserRegistry;
import mod.nethertweaks.registry.FluidBlockTransformerRegistry;
import mod.nethertweaks.registry.FluidOnTopRegistry;
import mod.nethertweaks.registry.FluidTransformRegistry;
import mod.nethertweaks.registry.HammerRegistry;
import mod.nethertweaks.registry.HellmartRegistry;
import mod.nethertweaks.registry.SieveRegistry;
import mod.nethertweaks.registry.manager.NTMDefaultRecipes;
import mod.nethertweaks.world.WorldGeneratorNTM;
import mod.nethertweaks.world.WorldHandler;
import mod.nethertweaks.world.WorldTypeHellworld;
import mod.sfhcore.Constants;
import mod.sfhcore.blocks.CustomDoor;
import mod.sfhcore.blocks.itemblocks.ItemDoor;
import mod.sfhcore.util.LogUtil;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.world.WorldType;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.RegistryManager;
 
@Mod(modid=NetherTweaksMod.MODID, name="Nether Tweaks Mod", version=Constants.NTMVersion, dependencies="required-after:sfhcore;")
public class NetherTweaksMod {
    
	public static final String MODID = "nethertweaksmod";
	
    @Instance(value=Constants.MOD)
    public static NetherTweaksMod instance;
    
    static
    {      
    	FluidRegistry.enableUniversalBucket();
    }
    
    public static NTMDefaultRecipes defaultRecipes;
    public static File configDirectory;
    
    //Creative Tabs
    public static CreativeTabs tabNTM = new CreativeTabs("tab_nether_tweaks_mod"){
         
        @Override
        @SideOnly(Side.CLIENT)
        public ItemStack getTabIconItem() {
            return Konstanten.HELLFAYAH;
            }
        };
    
    public WorldType Hellworld = new WorldTypeHellworld("hellworld");
         
    @Mod.EventHandler
    public void PreInit(FMLPreInitializationEvent event){    	
    	configDirectory = new File(event.getModConfigurationDirectory(), NetherTweaksMod.MODID);
    	configDirectory.mkdirs();
    	
    	LogUtil.setup(MODID, configDirectory);
    	
    	Config.loadConfigs(new File(configDirectory, "NetherTweaksMod.cfg"));
    	
        BlockHandler.init();
        BucketNFluidHandler.init();
        ItemHandler.init();
        NTMEntities.init();
        
        GameRegistry.registerWorldGenerator(new WorldGeneratorNTM(BlockHandler.BLOCKBASIC.getDefaultState(), 16, 16), 1);
    	
        MinecraftForge.EVENT_BUS.register(new WorldHandler());
    	MinecraftForge.EVENT_BUS.register(new HandlerHammer());
    	MinecraftForge.EVENT_BUS.register(this);
    	
        //GUI
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandlerNTM());		
		NetworkHandlerNTM.initPackets();
		
    }
    
    @Mod.EventHandler
    public void load(FMLInitializationEvent event){
    	loadJasons();
        //needs to be checked
        RecipeHandler.load();
        
    	defaultRecipes = new NTMDefaultRecipes();
		BarrelModeRegistry.registerDefaults();

    }
     
    @Mod.EventHandler
    public void PostInit(FMLPostInitializationEvent event){
    	//Ores from other mods
        OreHandler.init();
        ((ItemDoor)ItemHandler.ITEMSTONEDOOR).setDoor(BlockHandler.STONEDOOR);
        ((CustomDoor)BlockHandler.STONEDOOR).setDoor(ItemHandler.ITEMSTONEDOOR);
    }
    
    @SubscribeEvent
    public void registerModels(ModelRegistryEvent event)
    {
    	//Mobs
    	WorldHandler.addWaterMobs();
    	
    	ClientRegistry.bindTileEntitySpecialRenderer(TileSieve.class, new RenderSieve());
    	ClientRegistry.bindTileEntitySpecialRenderer(TileBarrel.class, new RenderBarrel());
    }
    
    private static void loadJasons()
    {
    	CompostRegistry.loadJson(new File(configDirectory, "CompostRegistry.json"));
		CompostRegistry.recommendAllFood(new File(configDirectory, "RecommendedFoodRegistry.json"));
		HammerRegistry.loadJson(new File(configDirectory, "HammerRegistry.json"));
		FluidBlockTransformerRegistry.loadJson(new File(configDirectory, "FluidBlockTransformerRegistry.json"));
		FluidOnTopRegistry.loadJson(new File(configDirectory, "FluidOnTopRegistry.json"));
		SieveRegistry.loadJson(new File(configDirectory, "SieveRegistry.json"));
		FluidTransformRegistry.loadJson(new File(configDirectory, "FluidTransformRegistry.json"));
		BarrelLiquidBlacklistRegistry.loadJson(new File(configDirectory, "BarrelLiquidBlacklistRegistry.json"));
		CondenserRegistry.loadJson(new File(configDirectory, "CondenserRegistry.json"));
		HellmartRegistry.loadJson(new File(configDirectory, "HellmartRegistry.json"));
    }
}