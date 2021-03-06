package mod.nethertweaks.registry.registries.base.types;

import mod.sfhcore.util.ItemInfo;

public class Siftable {

	public Siftable(final ItemInfo info, final float chance, final String meshLevel)
	{
		drop = info;
		this.chance = chance;
		this.meshLevel = meshLevel;
	}


	private final ItemInfo drop;

	public ItemInfo getDrop()
	{
		return drop;
	}

	private final float chance;

	public float getChance()
	{
		return chance;
	}

	private final String meshLevel;

	public String getMeshLevel()
	{
		return meshLevel;
	}
}
