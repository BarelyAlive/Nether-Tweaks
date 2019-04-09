package mod.nethertweaks.registry.types;

import mod.sfhcore.texturing.Color;
import mod.sfhcore.util.ItemInfo;

public class Ore {
	
	
	private String name;
	
	public String getName()
	{
		return name;
	}
	
	private Color color;
	
	public String getColor()
	{
		return name;
	}
	
	private ItemInfo result;
	
	public ItemInfo getResult()
	{
		return result;
	}
	
	public Ore(String name, Color color, ItemInfo result) {
		this.name = name;
		this.color = color;
		this.result = result;
	}

}
