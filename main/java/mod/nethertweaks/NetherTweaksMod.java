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
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import mod.nethertweaks.blocks.HolyEarth;
import mod.nethertweaks.compatibility.MinefactoryReloaded;
import mod.nethertweaks.handler.BlockHandler;
import mod.nethertweaks.handler.BucketNFluidHandler;
import mod.nethertweaks.handler.GuiLoadHandler;
import mod.nethertweaks.handler.ItemHandler;
import mod.nethertweaks.handler.NTMCompostHandler;
import mod.nethertweaks.handler.NTMDryHandler;
import mod.nethertweaks.handler.NTMSieveHandler;
import mod.nethertweaks.handler.OreHandler;
import mod.nethertweaks.handler.RecipeHandler;
import mod.nethertweaks.world.WorldGeneratorNetherTweaksMod;
import mod.nethertweaks.world.WorldHandler;
import mod.nethertweaks.world.WorldTypeHellworld;
import mod.sfhcore.Constants;
import mod.sfhcore.handler.GuiHandler;
 
@Mod(modid="nethertweaksmod", name="Nether Tweaks Mod", version=Constants.NTMVersion, dependencies="required-after:sfhcore;")
public class NetherTweaksMod {
     
    @Instance(value=Constants.MOD)
    public static NetherTweaksMod instance;
    
    //Creative Tabs
    public static CreativeTabs tabNetherTweaksMod = new CreativeTabs("tab_nether_tweaks_mod"){
         
        @Override
        @SideOnly(Side.CLIENT)
        public ItemStack getTabIconItem() {
            return Konstanten.HELLFAYAH;
            }
        };
        
    static {
    	FluidRegistry.enableUniversalBucket();
    }
    
    public WorldHandler whNTM = new WorldHandler();
    public WorldType Hellworld = new WorldTypeHellworld("hellworld");
         
    @EventHandler
    public void PreInit(FMLPreInitializationEvent event){
        
        //Config
        Config.loadConfigs(event);
        //Registry
        BlockHandler.registerBlocks();
        ItemHandler.registerItems();
        BucketNFluidHandler.registerBuckets();
        if(event.getSide().isClient())
        	BucketNFluidHandler.doHelper();
        MinecraftForge.TERRAIN_GEN_BUS.register(whNTM);
        MinecraftForge.EVENT_BUS.register(whNTM);
        GameRegistry.registerWorldGenerator(new WorldGeneratorNetherTweaksMod(), 1);
    }
    
    @EventHandler
    public void load(FMLInitializationEvent event){
        //will likely be remove
        RecipeHandler.loadSmelting();
        //handler recipes
        NTMCompostHandler.load();
        NTMDryHandler.load();
        NTMSieveHandler.load();       
        //needs to be checked
        RecipeHandler.oreRegistration();
        OreHandler.init();
    }
     
    @EventHandler
    public void PostInit(FMLPostInitializationEvent event){
    	//mod ores
        GuiLoadHandler.addGuiToHandler();
        //Compatibility
        MinefactoryReloaded.loadCompatibility();
    }     
}