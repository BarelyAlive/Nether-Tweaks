package mod.nethertweaks.vars;

import mod.nethertweaks.Config;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class SieveReward {
	public Block source;
	public int sourceMeta;
	public boolean ignoreMeta;
	public Item item;
	public int meta;
	public int rarity;
	
	public SieveReward(Block source, int sourceMeta, Item item, int meta, int rarity)
	{
		this.source = source;
		this.sourceMeta = sourceMeta;
		this.ignoreMeta = false;
		this.item = item;
		this.meta = meta;
		this.rarity = calculateRarity(rarity);
	}
	
	public SieveReward(Block source, Item item, int meta, int rarity)
	{
		this.source = source;
		this.sourceMeta = 0;
		this.ignoreMeta = true;
		this.item = item;
		this.meta = meta;
		this.rarity = calculateRarity(rarity);
	}
	
	private static int calculateRarity(int base)
	{
		int multiplier = Config.sieveDifficulty + 1;
		int rarity = (base * multiplier) + (int)((float)multiplier / 2.0f);

		return rarity;
	}

}
