package mod.nethertweaks;
 
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.world.WorldType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.terraingen.WorldTypeEvent;
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
import mod.nethertweaks.blocks.tileentities.GuiHandler;
import mod.nethertweaks.compatibility.MinefactoryReloaded;
import mod.nethertweaks.compatibility.Ores;
import mod.nethertweaks.compatibility.ThermalExpansion;
import mod.nethertweaks.handler.NTMCompostHandler;
import mod.nethertweaks.handler.NTMDryHandler;
import mod.nethertweaks.handler.NTMSieveHandler;
import mod.nethertweaks.handler.NetherTweaksModFuelHandler;
import mod.nethertweaks.items.NTMItems;
import mod.nethertweaks.world.WorldGeneratorNetherTweaksMod;
import mod.nethertweaks.world.WorldHandler;
import mod.nethertweaks.world.WorldTypeHellworld;
import mod.sfhcore.Constants;
 
@Mod(modid=Constants.ModIdNTM, name=Constants.ModIdNTM, version=Constants.NTMVersion, dependencies=Constants.DepSFH)
public class NetherTweaksMod {
     
    @Instance(value=Constants.ModIdNTM)
    public static NetherTweaksMod instance;
    
    //Creative Tabs
    public static CreativeTabs tabNetherTweaksMod = new CreativeTabs(Constants.TABNTM){
         
        @Override
        @SideOnly(Side.CLIENT)
        public Item getTabIconItem() {
            return NTMItems.itemCookedJerky;
            }
        };
    
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
        NTMCompostHandler.load();
        NTMDryHandler.load();
        NTMSieveHandler.load();
        MinecraftForge.TERRAIN_GEN_BUS.register(whNTM);
        MinecraftForge.EVENT_BUS.register(whNTM);
    }
    
    @EventHandler
    public void load(FMLInitializationEvent event){
        GameRegistry.registerFuelHandler(new NetherTweaksModFuelHandler());
        RecipeLoader.loadRecipes();
        RecipeLoader.oreRegistration();
        RecipeLoader.addOreRecipes();
        new GuiHandler();
    }
     
    @EventHandler
    public void PostInit(FMLPostInitializationEvent event){
        GameRegistry.registerWorldGenerator(new WorldGeneratorNetherTweaksMod(), 1);
    }
     
}