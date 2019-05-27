package mod.nethertweaks.registry.types;

import mod.nethertweaks.config.Config;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class HammerReward {
	
	private ItemStack stack;
	
	private int miningLevel;
	
	private float chance;
	
	private double fortuneChance;
	
	public HammerReward(ItemStack stack, int miningLevel, float chance, double fortuneChance2)
	{
		this.stack = stack;
		this.miningLevel = miningLevel;
		this.chance = chance;
		this.fortuneChance = fortuneChance2;
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
	
	public double getFortuneChance()
	{
		return fortuneChance;
	}
}