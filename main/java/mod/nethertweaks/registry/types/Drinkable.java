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

	public void setItem(ItemStack item) {
		this.item = item;
	}

	public void setThirstReplenish(int thirstReplenish) {
		this.thirstReplenish = thirstReplenish;
	}

	public void setSaturationReplenish(float saturationReplenish) {
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

	static Drinkable EMPTY = new Drinkable(ItemStack.EMPTY, 0, 0, 0);

	public Drinkable(final ItemStack stack, final int thirstReplenish, final float saturationReplenish, final float poisonChance)
	{
		item = stack;
		this.thirstReplenish = thirstReplenish;
		this.saturationReplenish = saturationReplenish;
		this.setPoisonChance(poisonChance);
	}

	public float getPoisonChance() {
		return poisonChance;
	}

	public void setPoisonChance(float poisonChance) {
		this.poisonChance = poisonChance;
	}
}
