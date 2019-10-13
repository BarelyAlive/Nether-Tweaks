package mod.nethertweaks.registry.types;

import mod.sfhcore.util.ItemInfo;
import net.minecraft.item.ItemStack;

public class DynOre {
	
	private String id;
	private String name;
	private ItemInfo result;
	private int rarity;
	private int color;
	
	public DynOre (String id, String name, ItemInfo result)
	{
		this(id, name, result, 1);
	}
	
	public DynOre (String id, String name, ItemInfo result, int rarity)
	{
		this(id, name, result, rarity, 0x8000000);
	}
	
	public DynOre (String id, String name, ItemInfo result, int rarity, int color) {
		this.id = id;
		this.name = name;
		this.result = result;
		this.rarity = rarity;
		this.color = color;
	}
	
	public String getID() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public ItemInfo getResult() {
		return this.result;
	}
	
	public int getRarity()
	{
		return this.rarity;
	}
	
	public int getColor()
	{
		return ((this.color & 0x00FFFFFF) | 0x7F000000);
	}

}
