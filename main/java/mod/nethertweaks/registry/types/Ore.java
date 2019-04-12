package mod.nethertweaks.registry.types;

import mod.sfhcore.texturing.Color;
import mod.sfhcore.util.ItemInfo;

public class Ore {
	
	
	private String name;	
	public Color color;	
	public ItemInfo result;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public ItemInfo getResult() {
		return result;
	}

	public void setResult(ItemInfo result) {
		this.result = result;
	}

	public Ore(String name, Color color, ItemInfo result) {
		this.name = name;
		this.color = color;
		this.result = result;
	}

}
