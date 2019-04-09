package mod.nethertweaks.registry.types;

import mod.nethertweaks.Config;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class HammerReward {
	
	private ItemStack stack;
	
	private int miningLevel;
	
	private float chance;
	
	private float fortuneChance;
	
	public HammerReward(ItemStack stack, int miningLevel, float chance, float fortuneChance)
	{
		this.stack = stack;
		this.miningLevel = miningLevel;
		this.chance = chance;
		this.fortuneChance = fortuneChance;
	}
	
	public ItemStack getItemStack()
	{
		return stack;
	}
	
	public int getMiningLevel()
	{
		return miningLevel;
	}
	
	public float getChance()
	{
		return chance;
	}
	
	public float getFortuneChance()
	{
		return fortuneChance;
	}
}