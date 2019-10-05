package mod.nethertweaks.registry.types;

import net.minecraft.item.ItemStack;

public class HammerReward {

	private ItemStack stack;

	private int miningLevel;

	private float chance;

	private double fortuneChance;

	public HammerReward(final ItemStack stack, final int miningLevel, final float chance, final double fortuneChance2)
	{
		this.stack = stack;
		this.miningLevel = miningLevel;
		this.chance = chance;
		fortuneChance = fortuneChance2;
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