package mod.nethertweaks.items;
 
import net.minecraft.block.material.Material;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import mod.nethertweaks.Constants;
import mod.nethertweaks.INames;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.blocks.Bonfire;
import mod.nethertweaks.blocks.itemblocks.ItemBlockNetherrackFurnace;
import mod.sfhcore.Registry;
import mod.sfhcore.items.ItemThing;

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
        	itemBase = Registry.registerItem(new ItemThing(null, 64, NetherTweaksMod.tabNetherTweaksMod, true, 51, INames.ITEMBASE), 51, Constants.MOD);
            
        	itemCookedJerky = (CookedJerky) Registry.registerItem(new CookedJerky(6, 1.0F, true), COOKEDJERKY);
            seedGrass = Registry.registerItem(new Seed(), Constants.MOD);
            mushroomSpores = Registry.registerItem(new Seed(), Constants.MOD);
            itemCactusSeeds = Registry.registerItem(new Seed(), Constants.MOD);
            itemSanctuaryCrystal = Registry.registerItem(new Crystal(), Constants.MOD);
            itemLightCrystal = Registry.registerItem(new Crystal(), Constants.MOD);
            
            //Werkzeuge
            itemPickaxeNetherrack = (PickaxeNTM) Registry.registerItem(new PickaxeNTM(ToolMaterial.STONE), Constants.MOD);
            itemPickaxeNetherbrick = (PickaxeNTM) Registry.registerItem(new PickaxeNTM(ToolMaterial.IRON), Constants.MOD);
             
            itemHammerWood = Registry.registerItem(new Hammer(2.0F, ToolMaterial.WOOD), Constants.MOD);
            itemHammerGold = Registry.registerItem(new Hammer(2.0F, ToolMaterial.GOLD), Constants.MOD);
            itemHammerIron = Registry.registerItem(new Hammer(4.0F, ToolMaterial.IRON), Constants.MOD);
            itemHammerDiamond = Registry.registerItem(new Hammer(5.0F, ToolMaterial.DIAMOND), Constants.MOD);
            itemHammerStone = Registry.registerItem(new Hammer(3.0F, ToolMaterial.STONE), Constants.MOD);
            
            itemFlintAndBlaze = Registry.registerItem(new FlintAndBlaze(), Constants.MOD);
                        
        }
     
}