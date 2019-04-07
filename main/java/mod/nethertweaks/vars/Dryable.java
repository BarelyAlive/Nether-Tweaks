package mod.nethertweaks.vars;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class Dryable {
	
	//Dry Helper
	
	private Item item;
	private int value;
	
	public Dryable(Item stack, int meta, int value)
	{
		this.item = stack;
		this.value = value;
	}
	
	public Item getItem()
	{
		return this.item;
	}
	
	public int getValue()
	{
		return this.value;
	}
}
