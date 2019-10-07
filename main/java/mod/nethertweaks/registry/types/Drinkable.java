package mod.nethertweaks.registry.types;

import net.minecraft.item.ItemStack;

public class Drinkable
{
	private ItemStack item;
	private int thirstReplenish;
	private float saturationReplenish;
	private float poisonChance;

	public ItemStack getItem() {
		return item;
	}

	public void setItem(final ItemStack item) {
		this.item = item;
	}

	public void setThirstReplenish(final int thirstReplenish) {
		this.thirstReplenish = thirstReplenish;
	}

	public void setSaturationReplenish(final float saturationReplenish) {
		this.saturationReplenish = saturationReplenish;
	}

	public int getThirstReplenish() {
		return thirstReplenish;
	}

	public float getSaturationReplenish() {
		return saturationReplenish;
	}

	public static Drinkable getEMPTY() {
		return EMPTY;
	}

	public static void setEMPTY(final Drinkable eMPTY) {
		EMPTY = eMPTY;
	}

	private static Drinkable EMPTY = new Drinkable(ItemStack.EMPTY, 0, 0, 0);

	public Drinkable(final ItemStack stack, final int thirstReplenish, final float saturationReplenish, final float poisonChance)
	{
		item = stack;
		this.thirstReplenish = thirstReplenish;
		this.saturationReplenish = saturationReplenish;
		setPoisonChance(poisonChance);
	}

	public float getPoisonChance() {
		return poisonChance;
	}

	private void setPoisonChance(final float poisonChance) {
		this.poisonChance = poisonChance;
	}
}
