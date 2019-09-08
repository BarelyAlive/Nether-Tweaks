package mod.nethertweaks.registry.types;

import mod.sfhcore.util.ItemInfo;
import net.minecraft.item.ItemStack;

public class DynOre {
	
	private String name;
	private ItemInfo chunk;
	private ItemInfo result;
	private int rarity;
	private int color;
	
	public DynOre (String name, ItemInfo chunk, ItemInfo result)
	{
		this(name, chunk, result, 1);
	}
	
	public DynOre (String name, ItemInfo chunk, ItemInfo result, int rarity)
	{
		this(name, chunk, result, rarity, 0x8000000);
	}
	
	public DynOre (String name, ItemInfo chunk, ItemInfo result, int rarity, int color) {
		this.name = name;
		this.chunk = chunk;
		this.result = result;
		this.rarity = rarity;
		this.color = color;
	}
	
	public String getName() {
		return this.name;
	}
	
	public ItemInfo getChunk() {
		return this.chunk;
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
		return this.color;
	}

}
