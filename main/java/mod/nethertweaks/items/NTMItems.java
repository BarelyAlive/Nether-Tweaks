package mod.nethertweaks.items;
 
import net.minecraft.block.material.Material;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemBlock;
import mod.nethertweaks.Constants;
import mod.nethertweaks.INames;
import mod.nethertweaks.blocks.Bonfire;
import mod.nethertweaks.blocks.itemblocks.ItemBlockNetherrackFurnace;
import mod.sfhcore.Registry;

public class NTMItems implements INames{
 
	//ITEMS
    public static Item seedGrass;
    public static Item mushroomSpores;
    public static Item itemCactusSeeds;
    public static Item itemFlintAndBlaze;
    public static Item itemSanctuaryCrystal;
    public static Item itemLightCrystal;
    
    //Multi ID Item
	public static Item itemBase;
    
	//food
    public static CookedJerky itemCookedJerky;
         
    //Werkzeuge
    public static PickaxeNTM itemPickaxeNetherrack;
    public static PickaxeNTM itemPickaxeNetherbrick;
    public static Item itemHammerWood;
    public static Item itemHammerGold;
    public static Item itemHammerIron;
    public static Item itemHammerDiamond;
    public static Item itemHammerStone;
    
                 
        public static void registerItems(){
        	
        	//Multi ID Item
        	itemBase = new ItemBase();
            
        	itemCookedJerky = (CookedJerky) Registry.registerItem(new CookedJerky(6, 1.0F, true), COOKEDJERKY, Constants.MOD);
            seedGrass = Registry.registerItem(new Seed(), SEEDGRASS, Constants.MOD);
            mushroomSpores = Registry.registerItem(new Seed(), MUSHROOMSPORES, Constants.MOD);
            itemCactusSeeds = Registry.registerItem(new Seed(), CACTUSSEED, Constants.MOD);
            itemSanctuaryCrystal = Registry.registerItem(new Crystal(), SANCTUARYCRYSTAL, Constants.MOD);
            itemLightCrystal = Registry.registerItem(new Crystal(), LIGHTCRYSTAL, Constants.MOD);
            
            //Werkzeuge
            itemPickaxeNetherrack = (PickaxeNTM) Registry.registerItem(new PickaxeNTM(ToolMaterial.STONE), PICKAXENETHERRACK, Constants.MOD);
            itemPickaxeNetherbrick = (PickaxeNTM) Registry.registerItem(new PickaxeNTM(ToolMaterial.IRON), PICKAXENETHERBRICK, Constants.MOD);
             
            itemHammerWood = Registry.registerItem(new Hammer(2.0F, ToolMaterial.WOOD), HAMMERWOOD, Constants.MOD);
            itemHammerGold = Registry.registerItem(new Hammer(2.0F, ToolMaterial.GOLD), HAMMERGOLD, Constants.MOD);
            itemHammerIron = Registry.registerItem(new Hammer(4.0F, ToolMaterial.IRON), HAMMERIRON, Constants.MOD);
            itemHammerDiamond = Registry.registerItem(new Hammer(5.0F, ToolMaterial.DIAMOND), HAMMERDIAMOND, Constants.MOD);
            itemHammerStone = Registry.registerItem(new Hammer(3.0F, ToolMaterial.STONE), HAMMERSTONE, Constants.MOD);
            
            itemFlintAndBlaze = Registry.registerItem(new FlintAndBlaze(), FLINTNBLAZE, Constants.MOD);
            
        }
     
}