package mod.nethertweaks.registry.types;

import mod.sfhcore.texturing.Color;
import mod.sfhcore.util.ItemInfo;

public class Ore {
	
	
	private String name;
	
	public String getName()
	{
		return name;
	}
	
	public Color color;
	
	public ItemInfo result;
	
	public Ore(String name, Color color, ItemInfo result) {
		this.name = name;
		this.color = color;
		this.result = result;
	}

}
