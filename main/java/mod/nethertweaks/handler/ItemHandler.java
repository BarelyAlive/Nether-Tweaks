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
import mod.nethertweaks.blocks.itemblocks.ItemBlockNetherrackFurnace;
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

public class ItemHandler implements INames{
 
	//ITEMS
    public static Item seedGrass;
    public static Item mushroomSpores;
    public static Item itemCactusSeeds;
    public static Item itemFlintAndBlaze;
    public static Item itemSanctuaryCrystal;
    public static Item itemLightCrystal;
    
    //Multi ID Item
	public static Item itemBase;
	public static Item oreMaterial;
    
	//food
    public static CookedJerky itemCookedJerky;
         
    //Werkzeuge
    public static Item itemPickaxeNetherrack;
    public static Item itemPickaxeNetherbrick;
    public static Item itemHammerWood;
    public static Item itemHammerGold;
    public static Item itemHammerIron;
    public static Item itemHammerDiamond;
    public static Item itemHammerStone;
    
    	
        public static void registerItems(){
        	
        	//Multi ID Item
        	itemBase = Registry.registerItem(new CustomItem(null, 64, NetherTweaksMod.tabNetherTweaksMod, true, 20, INames.ITEMBASE), Constants.MOD);
    		oreMaterial = Registry.registerItem(new CustomItem(null, 64, NetherTweaksMod.tabNetherTweaksMod, true, 51, INames.OREMATERIAL), Constants.MOD);
            
        	itemCookedJerky = (CookedJerky) Registry.registerItem(new CookedJerky(6, 1.0F, true), COOKEDJERKY);
            seedGrass = Registry.registerItem(new Seed(INames.SEEDGRASS), Constants.MOD);
            mushroomSpores = Registry.registerItem(new Seed(INames.MUSHROOMSPORES), Constants.MOD);
            itemCactusSeeds = Registry.registerItem(new Seed(INames.CACTUSSEED), Constants.MOD);
            itemSanctuaryCrystal = Registry.registerItem(new Crystal(INames.SANCTUARYCRYSTAL), Constants.MOD);
            itemLightCrystal = Registry.registerItem(new Crystal(INames.LIGHTCRYSTAL), Constants.MOD);
            
            //Werkzeuge
            itemPickaxeNetherrack = Registry.registerItem(new PickaxeNTM(ToolMaterial.STONE).setUnlocalizedName(INames.PICKAXENETHERRACK), Constants.MOD);
            itemPickaxeNetherbrick = Registry.registerItem(new PickaxeNTM(ToolMaterial.IRON).setUnlocalizedName(INames.PICKAXENETHERBRICK), Constants.MOD);
             
            itemHammerWood = Registry.registerItem(new Hammer(2.0F, ToolMaterial.WOOD).setUnlocalizedName(INames.HAMMERWOOD), Constants.MOD);
            itemHammerGold = Registry.registerItem(new Hammer(2.0F, ToolMaterial.GOLD).setUnlocalizedName(INames.HAMMERGOLD), Constants.MOD);
            itemHammerIron = Registry.registerItem(new Hammer(4.0F, ToolMaterial.IRON).setUnlocalizedName(INames.HAMMERIRON), Constants.MOD);
            itemHammerDiamond = Registry.registerItem(new Hammer(5.0F, ToolMaterial.DIAMOND).setUnlocalizedName(INames.HAMMERDIAMOND), Constants.MOD);
            itemHammerStone = Registry.registerItem(new Hammer(3.0F, ToolMaterial.STONE).setUnlocalizedName(INames.HAMMERSTONE), Constants.MOD);
            
            itemFlintAndBlaze = Registry.registerItem(new FlintAndBlaze(), Constants.MOD);
            
            addItemBurnTime();          
        }
        
        public static void addItemBurnTime() {
        	CustomFuelhandler.addFuelBurnTime(new ItemStack(BucketNFluidHandler.bucketStoneLava), 20000);
        	CustomFuelhandler.addFuelBurnTime(Konstanten.HELLFAYAH, 20000);
        	CustomFuelhandler.addFuelBurnTime(Konstanten.HELLFAYAHBLOCK, 20000);
        }
     
}