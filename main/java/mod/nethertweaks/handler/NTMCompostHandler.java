package mod.nethertweaks.handler;

import java.util.Hashtable;

import mod.nethertweaks.api.Compostable;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class NTMCompostHandler {

	//Compost Registry
	
	public static Hashtable<String, Compostable> entries = new Hashtable<String, Compostable>();
		
		//Value is how much it fills a barrel. 0.01 = 1%, 1 = 100%;
		public static void register(Item item, int meta, float value)
		{
			Compostable entry = new Compostable(item, meta, value);
			entries.put(item + ":" + meta, entry);
		}
	
	public static boolean containsItem(Item item, int meta)
	{
		return entries.containsKey(item + ":" + meta);
	}
	
	
	public static Compostable getItem(Item item, int meta)
	{
		return entries.get(item + ":" + meta);
	}
	
	public static void load()
	{
		//manuelle vanilla Registrierung -.-
		for(int i = 0; i < 6; i++){
			register(Item.getItemFromBlock(Blocks.SAPLING), i, 0.125f);
		}
		for(int i = 0; i < 4; i++){
			register(Item.getItemFromBlock(Blocks.LEAVES), i, 0.125f);
		}
		for(int i = 0; i < 2; i++){
			register(Item.getItemFromBlock(Blocks.LEAVES2), i, 0.125f);
		}
		
		//automatische ore dic Regsistrierung
		for(ItemStack sap : OreDictionary.getOres("treeSapling")){
			register(sap.getItem(), sap.getItemDamage(), 0.125f);
		}
		
		for(ItemStack sap : OreDictionary.getOres("treeLeaves")){
			register(sap.getItem(), sap.getItemDamage(), 0.125f);
		}
		for(ItemStack sap : OreDictionary.getOres("vine")){
			register(sap.getItem(), sap.getItemDamage(), 0.125f);
		}
		
		//rotten flesh
		register(Items.ROTTEN_FLESH, 0, 0.10f);
		//cookedJerky
		register(ItemHandler.COOKEDJERKY, 0, 0.2f);
		
		//spider eye
		register(Items.SPIDER_EYE, 0, 0.08f);
		
		//wheat
		register(Items.WHEAT, 0, 0.08f);
		register(Item.getItemFromBlock(Blocks.HAY_BLOCK), 0, 0.72f);
		//bread
		register(Items.BREAD, 0, 0.16f);
		
		//dandelion
		register(Item.getItemFromBlock(Blocks.YELLOW_FLOWER), 0, 0.10f);
		//poppy
		register(Item.getItemFromBlock(Blocks.RED_FLOWER), 0, 0.10f);
		//blue orchid
		register(Item.getItemFromBlock(Blocks.RED_FLOWER), 1, 0.10f);
		//allium
		register(Item.getItemFromBlock(Blocks.RED_FLOWER), 2, 0.10f);
		//azure bluet
		register(Item.getItemFromBlock(Blocks.RED_FLOWER), 3, 0.10f);
		//red_tulip
		register(Item.getItemFromBlock(Blocks.RED_FLOWER), 4, 0.10f);
		//orange tulip
		register(Item.getItemFromBlock(Blocks.RED_FLOWER), 5, 0.10f);
		//white tulip
		register(Item.getItemFromBlock(Blocks.RED_FLOWER), 6, 0.10f);
		//pink tulip
		register(Item.getItemFromBlock(Blocks.RED_FLOWER), 7, 0.10f);
		//oxeye daisy
		register(Item.getItemFromBlock(Blocks.RED_FLOWER), 8, 0.10f);
		
		//sunflower
		register(Item.getItemFromBlock(Blocks.DOUBLE_PLANT), 0, 0.10f);
		//lilac
		register(Item.getItemFromBlock(Blocks.DOUBLE_PLANT), 1, 0.10f);
		//rose bush
		register(Item.getItemFromBlock(Blocks.DOUBLE_PLANT), 4, 0.10f);
		//peony
		register(Item.getItemFromBlock(Blocks.DOUBLE_PLANT), 5, 0.10f);
		
		//mushroom_brown
		register(Item.getItemFromBlock(Blocks.BROWN_MUSHROOM), 0, 0.10f);
		//mushroom_red
		register(Item.getItemFromBlock(Blocks.RED_MUSHROOM), 0, 0.10f);
		
		//pumpkin pie
		register(Items.PUMPKIN_PIE, 0, 0.16f);
		
		//pork
		register(Items.PORKCHOP, 0, 0.2f);
		//cooked pork
		register(Items.COOKED_PORKCHOP, 0, 0.2f);
		
		//beef
		register(Items.BEEF, 0, 0.2f);
		//cooked beef
		register(Items.COOKED_BEEF, 0, 0.2f);
		
		//chicken
		register(Items.CHICKEN, 0, 0.2f);
		//cooked chicken
		register(Items.COOKED_CHICKEN, 0, 0.2f);
		
		//Rabbit
		register(Items.RABBIT_STEW, 0, 0.2f);
		register(Items.RABBIT, 0, 0.2f);
		
		//Sheep
		register(Items.MUTTON, 0, 0.2f);
		register(Items.COOKED_MUTTON, 0, 0.2f);
		
		//fish
		register(Items.FISH, 0, 0.15f);
		//cooked fish
		register(Items.COOKED_FISH, 0, 0.15f);
		
		//salmon
		register(Items.FISH, 1, 0.15f);
		//cooked salmon
		register(Items.COOKED_FISH, 1, 0.15f);
		
		//clownfish
		register(Items.FISH, 2, 0.15f);
		//blowfish
		register(Items.FISH, 3, 0.15f);
		
		//apple
		register(Items.APPLE, 0, 0.10f);
		
		register(Items.CHORUS_FRUIT, 0, 0.10f);
		register(Items.CHORUS_FRUIT_POPPED, 0, 0.10f);
		
		//melon slice
		register(Items.MELON, 0, 0.04f);
		//melon
		register(Item.getItemFromBlock(Blocks.MELON_BLOCK), 0, 1.0f / 6);
		//pumpkin
		register(Item.getItemFromBlock(Blocks.PUMPKIN), 0, 1.0f / 6);
		//jack o lantern
		register(Item.getItemFromBlock(Blocks.LIT_PUMPKIN), 0, 1.0f / 6);
		//cactus
		register(Item.getItemFromBlock(Blocks.CACTUS), 0, 0.10f);
		
		//carrot
		register(Items.CARROT, 0, 0.08f);
		//potato
		register(Items.POTATO, 0, 0.08f);
		//baked potato
		register(Items.BAKED_POTATO, 0, 0.08f);
		//poison potato
		register(Items.POISONOUS_POTATO, 0, 0.08f);
		
		//waterlily
		register(Item.getItemFromBlock(Blocks.WATERLILY), 0, 0.10f);
		//vine
		register(Item.getItemFromBlock(Blocks.VINE), 0, 0.10f);
		//tall grass
		register(Item.getItemFromBlock(Blocks.TALLGRASS), 1, 0.08f);
		//egg
		register(Items.EGG, 0, 0.08f);
		//netherwart
		register(Items.NETHER_WART, 0, 0.10f);
		//sugar cane
		register(Items.REEDS, 0, 0.08f);
		//string
		register(Items.STRING, 0, 0.04f);
	
	}
}
