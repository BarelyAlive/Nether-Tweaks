package mod.nethertweaks;
 
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.WorldType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.terraingen.WorldTypeEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.File;

import mod.nethertweaks.blocks.HolyEarth;
import mod.nethertweaks.compatibility.MinefactoryReloaded;
import mod.nethertweaks.handler.BlockHandler;
import mod.nethertweaks.handler.BucketNFluidHandler;
import mod.nethertweaks.handler.GuiHandlerNTM;
import mod.nethertweaks.handler.HandlerHammer;
import mod.nethertweaks.handler.ItemHandler;
import mod.nethertweaks.handler.CompostHandler;
import mod.nethertweaks.handler.SieveHandler;
import mod.nethertweaks.handler.OreHandler;
import mod.nethertweaks.handler.RecipeHandler;
import mod.nethertweaks.network.NetworkHandlerNTM;
import mod.nethertweaks.network.NetworkNTM;
import mod.nethertweaks.registry.DryRegistry;
import mod.nethertweaks.registry.HammerRegistry;
import mod.nethertweaks.registry.manager.NTMDefaultRecipes;
import mod.nethertweaks.world.WorldGeneratorNTM;
import mod.nethertweaks.world.WorldHandler;
import mod.nethertweaks.world.WorldTypeHellworld;
import mod.sfhcore.Constants;
import mod.sfhcore.handler.GuiHandler;
import mod.sfhcore.util.LogUtil;
 
@Mod(modid="nethertweaksmod", name="Nether Tweaks Mod", version=Constants.NTMVersion, dependencies="required-after:sfhcore;")
public class NetherTweaksMod {
     
    @Instance(value=Constants.MOD)
    public static NetherTweaksMod instance;
    
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("nethertweaksmod");
    static
    {
    	// Network
        int id = 0;
        INSTANCE.registerMessage(NetworkHandlerNTM.class, NetworkNTM.class, id++, Side.SERVER);
        
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
    
    public WorldHandler whNTM = new WorldHandler();
    public WorldType Hellworld = new WorldTypeHellworld("hellworld");
         
    @Mod.EventHandler
    public void PreInit(FMLPreInitializationEvent event){
    	LogUtil.setup();
    	
    	configDirectory = new File(event.getModConfigurationDirectory(), "nethertweaksmod");
    	configDirectory.mkdirs();
    	
    	Config.loadConfigs(new File(configDirectory, "NetherTweaksMod.cfg"));
    	
        BlockHandler.init();
        ItemHandler.init();
        BucketNFluidHandler.init();
        MinecraftForge.EVENT_BUS.register(whNTM);
        GameRegistry.registerWorldGenerator(new WorldGeneratorNTM(BlockHandler.BLOCKBASIC.getDefaultState(), 16, 16), 1);
        
    	defaultRecipes = new NTMDefaultRecipes();
    	
    	MinecraftForge.EVENT_BUS.register(new HandlerHammer());
    	
        //GUI
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandlerNTM());
    }
    
    @Mod.EventHandler
    public void load(FMLInitializationEvent event){
    	loadConfigs();
        //handler recipes
        CompostHandler.load();
        SieveHandler.load();       
        //needs to be checked
        RecipeHandler.load();
    }
     
    @Mod.EventHandler
    public void PostInit(FMLPostInitializationEvent event){
    	//Ores from other mods
        OreHandler.init();
        //Compatibility
        MinefactoryReloaded.loadCompatibility();
    }
    
    public static void loadConfigs()
    {
    	DryRegistry.loadJson(new File(configDirectory, "DryRegistry.json"));
    	HammerRegistry.loadJson(new File(configDirectory, "HammerRegistry.json"));
    }
}