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
import mod.nethertweaks.blocks.NTMBlocks;
import mod.nethertweaks.blocks.gui.GuiHandler;
import mod.nethertweaks.compatibility.MinefactoryReloaded;
import mod.nethertweaks.compatibility.Ores;
import mod.nethertweaks.compatibility.ThermalExpansion;
import mod.nethertweaks.handler.NTMCompostHandler;
import mod.nethertweaks.handler.NTMDryHandler;
import mod.nethertweaks.handler.NTMSieveHandler;
import mod.nethertweaks.handler.NetherTweaksModFuelHandler;
import mod.nethertweaks.handler.RecipeHandler;
import mod.nethertweaks.items.NTMItems;
import mod.nethertweaks.world.WorldGeneratorNetherTweaksMod;
import mod.nethertweaks.world.WorldHandler;
import mod.nethertweaks.world.WorldTypeHellworld;
import mod.sfhcore.Constants;
 
@Mod(modid=Constants.MOD, name=Constants.MOD, version=Constants.NTMVersion, dependencies=Constants.DEPENDENCY_CORE)
public class NetherTweaksMod {
     
    @Instance(value=Constants.MOD)
    public static NetherTweaksMod instance;
    
    //Creative Tabs
    public static CreativeTabs tabNetherTweaksMod = new CreativeTabs("tabNetherTweaksMod"){
         
        @Override
        @SideOnly(Side.CLIENT)
        public ItemStack getTabIconItem() {
            return new ItemStack(NTMItems.itemCookedJerky);
            }
        };
        
    static {
    	FluidRegistry.enableUniversalBucket();
    }
    
    public WorldHandler whNTM = new WorldHandler();
    public WorldType Hellworld = new WorldTypeHellworld("Hellworld");
         
    @EventHandler
    public void PreInit(FMLPreInitializationEvent event){
        
        //Config
        Config.loadConfigs(event);
        //Registry
        NTMBlocks.registerBlocks();
        NTMItems.registerItems();
        BucketLoader.registerBuckets();
        if(event.getSide().isClient())
        	BucketLoader.doHelper();
        NTMCompostHandler.load();
        NTMDryHandler.load();
        NTMSieveHandler.load();
        MinecraftForge.TERRAIN_GEN_BUS.register(whNTM);
        MinecraftForge.EVENT_BUS.register(whNTM);
    }
    
    @EventHandler
    public void load(FMLInitializationEvent event){
        //Block & Item recipes
        RecipeHandler.loadRecipes();
        RecipeHandler.oreRegistration();
        RecipeHandler.addOreRecipes();
        //handler recipes
        NTMCompostHandler.load();
        NTMDryHandler.load();
        NTMSieveHandler.load();
    }
     
    @EventHandler
    public void PostInit(FMLPostInitializationEvent event){
    	//mod ores
        Ores.registerNames();
        Ores.registerRecipes();
        GameRegistry.registerFuelHandler(new NetherTweaksModFuelHandler());
        new GuiHandler();
        GameRegistry.registerWorldGenerator(new WorldGeneratorNetherTweaksMod(), 1);
    }
     
}