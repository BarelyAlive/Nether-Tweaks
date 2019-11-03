package mod.nethertweaks;

import net.minecraft.item.Item;

public class Dryable {
	
	//Dry Helper
	
	public String unlocalizedName;
	public Item item;
	public int meta;
	public int value;
	
	public Dryable(Item item, int meta, int value)
	{
		this.item = item;
		this.meta = meta;
		this.value = value;
	}

}
