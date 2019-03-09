package mod.nethertweaks.handler;

import java.util.ArrayList;
import java.util.Iterator;

import mod.nethertweaks.blocks.NTMBlocks;
import mod.nethertweaks.compatibility.Ores;
import mod.nethertweaks.items.NTMItems;
import mod.nethertweaks.vars.SieveReward;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.oredict.OreDictionary;

public class NTMSieveHandler {
	public static ArrayList<SieveReward> rewards = new ArrayList<SieveReward>();
	
	public static void register(Block source, int sourceMeta, Item output, int outputMeta, int rarity)
	{
		SieveReward entry = new SieveReward(source, sourceMeta, output, outputMeta, rarity);
		
		if(source != null)
		{
			rewards.add(entry);
		}else
		{
			System.out.println("An item was added to the SieveRegistry which was not a block");
		}
	}
	
	public static void register(Block source, Item output, int outputMeta, int rarity)
	{
		SieveReward entry = new SieveReward(source, output, outputMeta, rarity);
		
		if(source != null)
		{
			rewards.add(entry);
		}else
		{
			System.out.println("An item was added to the SieveRegistry which was not a block");
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static ArrayList<SieveReward> getRewards(Block block, int meta)
	{
		ArrayList<SieveReward> rewardList = new ArrayList();

		Iterator<SieveReward> it = rewards.iterator();
		while(it.hasNext())
		{
			SieveReward reward = it.next();

			if (reward.source == block && reward.sourceMeta == meta)
			{
				rewardList.add(reward);
			}
		}
		
		return rewardList;
	}
	
	public static boolean Contains(Block block, int meta)
	{
		Iterator<SieveReward> it = rewards.iterator();
		while(it.hasNext())
		{
			SieveReward reward = it.next();

			if (reward.source == block && (reward.sourceMeta == meta || reward.ignoreMeta == true))
			{
				return true;
			}
		}
		
		return false;
	}
	
	public static boolean Contains(Block block)
	{
		Iterator<SieveReward> it = rewards.iterator();
		while(it.hasNext())
		{
			SieveReward reward = it.next();

			if (reward.source == block && reward.ignoreMeta == true)
			{
				return true;
			}
		}
		
		return false;
	}
	
	public static void load()
	{
		register(Blocks.WATER, 0, Items.PRISMARINE_SHARD, 0, 15);
		
		//Dirt!
		register(Blocks.DIRT, 0, Items.WHEAT_SEEDS, 0, 15);
		register(Blocks.DIRT, 0, NTMItems.seedGrass, 0, 15);
		register(Blocks.DIRT, 0, Items.MELON_SEEDS, 0, 32);
		register(Blocks.DIRT, 0, Items.PUMPKIN_SEEDS, 0, 32);
		register(Blocks.DIRT, 0, Item.getItemFromBlock(Blocks.REEDS), 0, 32);
		register(Blocks.DIRT, 0, Items.CARROT, 0, 64);
		register(Blocks.DIRT, 0, Items.POTATO, 0, 64);
		register(Blocks.DIRT, 0, Item.getItemFromBlock(Blocks.SAPLING), 0, 64);
		register(Blocks.DIRT, 0, Item.getItemFromBlock(Blocks.SAPLING), 1, 90);
		register(Blocks.DIRT, 0, Item.getItemFromBlock(Blocks.SAPLING), 2, 90);
		register(Blocks.DIRT, 0, Item.getItemFromBlock(Blocks.SAPLING), 3, 90);
		register(Blocks.DIRT, 0, Item.getItemFromBlock(Blocks.SAPLING), 4, 90);
		
		for(ItemStack sap : OreDictionary.getOres("treeSapling")){
			register(Blocks.DIRT, 0, sap.getItem(), sap.getItemDamage(), 90);
		}
		for(ItemStack sap : OreDictionary.getOres("vine")){
			register(Blocks.DIRT, 0, sap.getItem(), sap.getItemDamage(), 90);
		}

		//Gravel!
		register(Blocks.GRAVEL, 0, Items.FLINT, 0, 4);
		register(Blocks.GRAVEL, 0, Items.COAL, 0, 8);
		register(Blocks.GRAVEL, 0, Items.DYE, 4, 20); //Lapis Lazuli
		register(Blocks.GRAVEL, 0, Items.DIAMOND, 0, 128);
		register(Blocks.GRAVEL, 0, Items.EMERALD, 0, 150);
		
		register(Blocks.GRAVEL, 0, NTMItems.itemBase, 0, 11);
		register(Blocks.GRAVEL, 0, NTMItems.itemBase, 1, 5);

		
		//Sand!
		register(Blocks.SAND, 0, Items.DYE, 3, 32); //Cocoa beans
		register(Blocks.SAND, 0, NTMItems.itemCactusSeeds, 0, 32);
		register(Blocks.SAND, 0, NTMItems.mushroomSpores, 0, 128);
		
		register(Blocks.SAND, 0, NTMItems.itemBase, 2, 11);
		register(Blocks.SAND, 0, NTMItems.itemBase, 3, 5);
		
		//Dust!
		register(NTMBlocks.blockDust, 0, Items.DYE, 15, 5); //Bone Meal
		register(NTMBlocks.blockDust, 0, Items.REDSTONE, 0, 8);		
		register(NTMBlocks.blockDust, 0, Items.GUNPOWDER, 0, 15);
		
		register(NTMBlocks.blockDust, 0, NTMItems.itemBase, 0, 9);
		register(NTMBlocks.blockDust, 0, NTMItems.itemBase, 0, 18);
	}
}