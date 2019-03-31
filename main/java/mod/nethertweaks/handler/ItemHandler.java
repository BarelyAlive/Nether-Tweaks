package mod.nethertweaks.handler;
 
import net.minecraft.block.material.Material;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import mod.nethertweaks.INames;
import mod.nethertweaks.Konstanten;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.blocks.Bonfire;
import mod.nethertweaks.items.CookedJerky;
import mod.nethertweaks.items.Crystal;
import mod.nethertweaks.items.FlintAndBlaze;
import mod.nethertweaks.items.Hammer;
import mod.nethertweaks.items.PickaxeNTM;
import mod.nethertweaks.items.Seed;
import mod.sfhcore.Constants;
import mod.sfhcore.Registry;
import mod.sfhcore.SFHCore;
import mod.sfhcore.handler.CustomFuelhandler;
import mod.sfhcore.items.CustomItem;

public class ItemHandler{
 
	//ITEMS
    public static final Item SEEDGRASS = new Seed(INames.SEEDGRASS);
    public static final Item MUSHROOMSPORES = new Seed(INames.MUSHROOMSPORES);
    public static final Item CACTUSSEEDS = new Seed(INames.CACTUSSEED);
    public static final Item FLINTANDBLAZE = new FlintAndBlaze();
    public static final Item SANCTUARYCRYSTAL = new Crystal(INames.SANCTUARYCRYSTAL);
    public static final Item LIGHTCRYSTAL = new Crystal(INames.LIGHTCRYSTAL);
    public static final Item ENDERCRYSTAl = new Crystal(INames.ENDERCRYSTAL);
    
    //Multi ID Item
	public static final Item ITEMBASE = new CustomItem(null, 64, NetherTweaksMod.tabNetherTweaksMod, true, 15, INames.ITEMBASE);
	public static final Item OREMATERIAL = new CustomItem(null, 64, NetherTweaksMod.tabNetherTweaksMod, true, 51, INames.OREMATERIAL);
    
	//food
    public static final Item COOKEDJERKY = new CookedJerky(6, 1.0F, true);
         
    //Werkzeuge
    public static final Item PICKAXENETHERRACK = new PickaxeNTM(ToolMaterial.STONE);
    public static final Item PICKAXENETHERBRICK = new PickaxeNTM(ToolMaterial.IRON);
    public static final Item HAMMERWOOD = new Hammer(2.0F, ToolMaterial.WOOD);
    public static final Item HAMMERGOLD = new Hammer(2.0F, ToolMaterial.GOLD);
    public static final Item HAMMERIRON = new Hammer(4.0F, ToolMaterial.IRON);
    public static final Item HAMMERDIAMOND = new Hammer(5.0F, ToolMaterial.DIAMOND);
    public static final Item HAMMERSTONE = new Hammer(3.0F, ToolMaterial.STONE);
    
    
    	public static void init()
    	{
    		registerItems();
    		addItemBurnTime();
    	}
    	
        private static void registerItems()
        {
        	//Multi ID Item
        	Registry.registerItem(ITEMBASE, Constants.MOD);
    		Registry.registerItem(OREMATERIAL, Constants.MOD);
            
        	Registry.registerItem(COOKEDJERKY, Constants.MOD);
            Registry.registerItem(SEEDGRASS, Constants.MOD);
            Registry.registerItem(MUSHROOMSPORES, Constants.MOD);
            Registry.registerItem(CACTUSSEEDS, Constants.MOD);
            Registry.registerItem(SANCTUARYCRYSTAL, Constants.MOD);
            Registry.registerItem(LIGHTCRYSTAL, Constants.MOD);
            Registry.registerItem(ENDERCRYSTAl, Constants.MOD);
            
            //Werkzeuge
            Registry.registerItem(PICKAXENETHERRACK, Constants.MOD);
            Registry.registerItem(PICKAXENETHERBRICK, Constants.MOD);
             
            Registry.registerItem(HAMMERWOOD, Constants.MOD);
            Registry.registerItem(HAMMERGOLD, Constants.MOD);
            Registry.registerItem(HAMMERIRON, Constants.MOD);
            Registry.registerItem(HAMMERDIAMOND, Constants.MOD);
            Registry.registerItem(HAMMERSTONE, Constants.MOD);
            
            Registry.registerItem(FLINTANDBLAZE, Constants.MOD); 
        }
        
        private static void addItemBurnTime()
        {
        	CustomFuelhandler.addFuelBurnTime(new ItemStack(BucketNFluidHandler.bucketStoneLava), 18000);
        	CustomFuelhandler.addFuelBurnTime(Konstanten.HELLFAYAH, 6400);
        	CustomFuelhandler.addFuelBurnTime(Konstanten.HELLFAYAHBLOCK, 64000);
        }
}