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
    
        @SubscribeEvent       
        public void registerItems(RegistryEvent.Register<Item> event){
        	
        	//Multi ID Item
        	itemBase = new ItemThing(null, 64, NetherTweaksMod.tabNetherTweaksMod, true);
            
        	itemCookedJerky = (CookedJerky) Registry.registerItem(new CookedJerky(6, 1.0F, true), COOKEDJERKY, Constants.MOD);
            seedGrass = new Seed(SEEDGRASS, Constants.MOD);
            mushroomSpores = new Seed(MUSHROOMSPORES, Constants.MOD);
            itemCactusSeeds = new Seed(CACTUSSEED, Constants.MOD);
            itemSanctuaryCrystal = new Crystal(SANCTUARYCRYSTAL, Constants.MOD);
            itemLightCrystal = new Crystal(LIGHTCRYSTAL, Constants.MOD);
            
            //Werkzeuge
            itemPickaxeNetherrack = (PickaxeNTM) new PickaxeNTM(ToolMaterial.STONE), PICKAXENETHERRACK, Constants.MOD);
            itemPickaxeNetherbrick = (PickaxeNTM) new PickaxeNTM(ToolMaterial.IRON), PICKAXENETHERBRICK, Constants.MOD);
             
            itemHammerWood = new Hammer(2.0F, ToolMaterial.WOOD), HAMMERWOOD, Constants.MOD);
            itemHammerGold = new Hammer(2.0F, ToolMaterial.GOLD), HAMMERGOLD, Constants.MOD);
            itemHammerIron = new Hammer(4.0F, ToolMaterial.IRON), HAMMERIRON, Constants.MOD);
            itemHammerDiamond = new Hammer(5.0F, ToolMaterial.DIAMOND), HAMMERDIAMOND, Constants.MOD);
            itemHammerStone = new Hammer(3.0F, ToolMaterial.STONE), HAMMERSTONE, Constants.MOD);
            
            itemFlintAndBlaze = new FlintAndBlaze(), FLINTNBLAZE, Constants.MOD);
            
            event.getRegistry().registerAll();
            
        }
     
}