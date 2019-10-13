package mod.nethertweaks.registry.types;

import mod.sfhcore.util.ItemInfo;

public class DynOre {

	private String id;
	private String name;
	private ItemInfo result;
	private int rarity;
	private int color;

	public DynOre (final String id, final String name, final ItemInfo result)
	{
		this(id, name, result, 1);
	}

	public DynOre (final String id, final String name, final ItemInfo result, final int rarity)
	{
		this(id, name, result, rarity, 0x8000000);
	}

	public DynOre (final String id, final String name, final ItemInfo result, final int rarity, final int color) {
		this.id = id;
		this.name = name;
		this.result = result;
		this.rarity = rarity;
		this.color = color;
	}

	public String getID() {
		return id;
	}

	public String getName() {
		return name;
	}

	public ItemInfo getResult() {
		return result;
	}

	public int getRarity()
	{
		return rarity;
	}

	public int getColor()
	{
		return color & 0x00FFFFFF | 0x7F000000;
	}

}
