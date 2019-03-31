package mod.nethertweaks.api;

import net.minecraft.item.Item;

public class Compostable {
	
	//compost Helper
	
	public String unlocalizedName;
	public Item item;
	public int meta;
	public float value;
	
	public Compostable(Item item, int meta, float value)
	{
		this.item = item;
		this.meta = meta;
		this.value = value;
	}

}
