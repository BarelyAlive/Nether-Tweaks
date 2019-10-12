package mod.nethertweaks.registry.registries.base.types;

import java.util.Map;

import mod.sfhcore.texturing.Color;
import mod.sfhcore.util.ItemInfo;

public class Ore {


	private final String name;
	private final Color color;
	private final ItemInfo result;
	private final ItemInfo dustResult;

	public ItemInfo getDustResult() {
		return dustResult;
	}

	private final String oredictName;
	private final Map<String, String> translations;

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

	public Ore(final String name, final Color color, final ItemInfo result, final ItemInfo dustResult, final Map<String, String> translations, final String oredictName) {
		this.name = name;
		this.color = color;
		this.result = result;
		this.dustResult = dustResult;
		this.oredictName = oredictName;
		this.translations = translations;
	}

}
