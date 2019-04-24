package mod.nethertweaks.registry.types;

import java.util.Map;

import mod.sfhcore.texturing.Color;
import mod.sfhcore.util.ItemInfo;

public class Ore {
	
	
	private String name;	
	private Color color;	
	private ItemInfo result;
	private ItemInfo dustResult;
	
	public ItemInfo getDustResult() {
		return dustResult;
	}

	private String oredictName;
	private Map<String, String> translations;

	public Map<String, String> getTranslations() {
		return translations;
	}

	public String getName() {
		return name;
	}

	public Color getColor() {
		return color;
	}

	public ItemInfo getResult() {
		return result;
	}

	public String getOredictName() {
		return oredictName;
	}

	public Ore(String name, Color color, ItemInfo result, ItemInfo dustResult, Map<String, String> translations, String oredictName) {
		this.name = name;
		this.color = color;
		this.result = result;
		this.dustResult = dustResult;
		this.oredictName = oredictName;
		this.translations = translations;
	}

}
