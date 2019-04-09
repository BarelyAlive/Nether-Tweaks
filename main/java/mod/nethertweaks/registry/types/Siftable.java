package mod.nethertweaks.registry.types;

import mod.sfhcore.util.ItemInfo;

public class Siftable {
	
	public Siftable(ItemInfo info, float chance, int meshLevel)
	{
		this.drop = info;
		this.chance = chance;
		this.meshLevel = meshLevel;
	}

	
	private ItemInfo drop;
	
	public ItemInfo getDrop()
	{
		return drop;
	}
	
	private float chance;
	
	public float getChance()
	{
		return chance;
	}
	
	private int meshLevel;
	
	public int getMeshLevel()
	{
		return meshLevel;
	}
}
