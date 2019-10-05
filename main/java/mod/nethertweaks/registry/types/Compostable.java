package mod.nethertweaks.registry.types;

import mod.sfhcore.texturing.Color;
import mod.sfhcore.util.BlockInfo;

public class Compostable
{
	@Override
	public String toString() {
		return "Compostable [value=" + value + ", color=" + color + ", compostBlock=" + compostBlock + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (color == null ? 0 : color.hashCode());
		result = prime * result + (compostBlock == null ? 0 : compostBlock.hashCode());
		result = prime * result + Float.floatToIntBits(value);
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Compostable other = (Compostable) obj;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		if (compostBlock == null) {
			if (other.compostBlock != null)
				return false;
		} else if (!compostBlock.equals(other.compostBlock))
			return false;
		if (Float.floatToIntBits(value) != Float.floatToIntBits(other.value))
			return false;
		return true;
	}

	public Compostable copy()
	{
		Compostable cp = new Compostable(value, color, compostBlock);
		return cp;
	}

	private float value = 0;
	private Color color = null;
	private BlockInfo compostBlock;

	public float getValue() {
		return value;
	}

	public void setValue(final float value) {
		this.value = value;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(final Color color) {
		this.color = color;
	}

	public BlockInfo getCompostBlock() {
		return compostBlock;
	}

	public void setCompostBlock(final BlockInfo compostBlock) {
		this.compostBlock = compostBlock;
	}

	public static Compostable getEMPTY() {
		return EMPTY;
	}

	public static void setEMPTY(final Compostable eMPTY) {
		EMPTY = eMPTY;
	}

	static Compostable EMPTY = new Compostable(0f, new Color(0), BlockInfo.EMPTY);

	public Compostable(final float value, final Color color, final BlockInfo compostBlock)
	{
		this.value = value;
		this.color = color;
		this.compostBlock = compostBlock;
	}
}
