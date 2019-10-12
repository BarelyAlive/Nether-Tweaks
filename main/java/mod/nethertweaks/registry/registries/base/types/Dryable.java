package mod.nethertweaks.registry.registries.base.types;

import net.minecraft.item.ItemStack;

public class Dryable
{
	//Dry Helper

	@Override
	public String toString() {
		return "Dryable [item=" + item + ", value=" + value + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (item == null ? 0 : item.hashCode());
		result = prime * result + value;
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
		Dryable other = (Dryable) obj;
		if (item == null) {
			if (other.item != null)
				return false;
		} else if (!item.equals(other.item))
			return false;
        return value == other.value;
    }

	private final ItemStack item;
	private final int value;

	public static Dryable getEMPTY() {
		return EMPTY;
	}

	public static void setEMPTY(final Dryable eMPTY) {
		EMPTY = eMPTY;
	}

	static Dryable EMPTY = new Dryable(ItemStack.EMPTY, 0);

	public Dryable(final ItemStack stack, final int value)
	{
		item = stack;
		this.value = value;
	}

	public ItemStack getItem()
	{
		return item;
	}

	public int getValue()
	{
		return value;
	}
}
