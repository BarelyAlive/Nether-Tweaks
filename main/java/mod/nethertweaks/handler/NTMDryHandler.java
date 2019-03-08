package mod.nethertweaks.handler;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Hashtable;

import mod.nethertweaks.NTMBlocks;
import mod.nethertweaks.items.*;
import mod.nethertweaks.vars.Dryable;

public class NTMDryHandler {
	
	public static Hashtable<String, Dryable> entries = new Hashtable<String, Dryable>();
	
	//Value is how much it needs to get 1B of water
	public static void register(Item item, int meta, int value)
	{
		Dryable entry = new Dryable(item, meta, value);
		entries.put(item + ":" + meta, entry);
	}

	public static boolean containsItem(Item item, int meta)
	{
		return entries.containsKey(item + ":" + meta);
	}
	
	public static Dryable getItem(Item item, int meta)
	{
		return entries.get(item + ":" + meta);
	}
	
	public static void load() {
		
		register(Items.ROTTEN_FLESH, 0, 9);
		register(Items.APPLE, 0, 24);
		register(Items.CHORUS_FRUIT, 0, 24);
		register(Items.CHORUS_FRUIT_POPPED, 0, 24);
		register(Items.CARROT, 0, 24);
		register(Items.EGG, 0, 24);	
		//fish
		register(Items.FISH, 0, 16);
		//cooked fish
		register(Items.COOKED_FISH, 0, 16);
		
		//salmon
		register(Items.FISH, 1, 16);
		//cooked salmon
		register(Items.COOKED_FISH, 1, 16);
			
		//clownfish
		register(Items.FISH, 2, 16);
		//blowfish
		register(Items.FISH, 3, 16);	
		
		register(Item.getItemFromBlock(Blocks.BROWN_MUSHROOM), 0, 16);
		register(Item.getItemFromBlock(Blocks.RED_MUSHROOM), 0, 16);
		register(Items.MELON, 0, 9);
		register(Item.getItemFromBlock(Blocks.MELON_BLOCK), 0, 1);	
		register(Items.POISONOUS_POTATO, 0, 24);
		register(Items.PORKCHOP, 0, 16);
		register(Items.COOKED_PORKCHOP, 0, 16);	
		register(Items.POTATO, 0, 24);	
		register(Items.BAKED_POTATO, 0, 24);
		register(Items.BEEF, 0, 16);	
		register(Items.COOKED_BEEF, 0, 16);	
		register(Items.CHICKEN, 0, 16);	
		register(Items.COOKED_CHICKEN, 0, 16);
		register(Items.RABBIT_STEW, 0, 1);
		register(Items.RABBIT, 0, 16);
		register(Items.COOKED_RABBIT, 0, 16);
		register(Items.MUTTON, 0, 16);
		register(Items.COOKED_MUTTON, 0, 16);
		register(Items.MAGMA_CREAM, 0, 9);	
		register(Items.SLIME_BALL, 0, 9);	
		register(Item.getItemFromBlock(Blocks.PUMPKIN), 0, 4);	
		register(Item.getItemFromBlock(Blocks.CACTUS), 0, 1);
		
		for(int i = 0; i < 6; i++){
			register(Item.getItemFromBlock(Blocks.SAPLING), i, 8);
		}
		for(int i = 0; i < 4; i++){
			register(Item.getItemFromBlock(Blocks.LEAVES), i, 8);
		}
		for(int i = 0; i < 2; i++){
			register(Item.getItemFromBlock(Blocks.LEAVES2), i, 8);
		}
		
		for(ItemStack sap : OreDictionary.getOres("treeSapling")){
			register(sap.getItem(), sap.getItemDamage(), 8);
		}
		
		for(ItemStack sap : OreDictionary.getOres("treeLeaves")){
			register(sap.getItem(), sap.getItemDamage(), 8);
		}
		for(ItemStack sap : OreDictionary.getOres("vine")){
			register(sap.getItem(), sap.getItemDamage(), 8);
		}
	}

}
