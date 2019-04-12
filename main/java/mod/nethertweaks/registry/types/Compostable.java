package mod.nethertweaks.registry.types;

import mod.sfhcore.texturing.Color;
import mod.sfhcore.util.ItemInfo;
import net.minecraft.item.Item;

public class Compostable {
	
	private float value;
	private Color color;
	public Color getColor() {
		return color;
	}

	private ItemInfo compostBlock;
	
	public float getValue() {
		return value;
	}

	public ItemInfo getCompostBlock() {
		return compostBlock;
	}

	public Compostable(float value, Color color, ItemInfo compostBlock) {
		super();
		this.value = value;
		this.color = color;
		this.compostBlock = compostBlock;
	}
}
