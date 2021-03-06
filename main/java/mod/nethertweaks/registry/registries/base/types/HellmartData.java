package mod.nethertweaks.registry.registries.base.types;

import net.minecraft.item.ItemStack;

public class HellmartData
{
	@Override
	public String toString() {
		return "HellmartData [item=" + item + ", currency=" + currency + ", price=" + price + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (currency == null ? 0 : currency.hashCode());
		result = prime * result + (item == null ? 0 : item.hashCode());
		result = prime * result + price;
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
		final HellmartData other = (HellmartData) obj;
		if (currency == null) {
			if (other.currency != null)
				return false;
		} else if (!currency.equals(other.currency))
			return false;
		if (item == null) {
			if (other.item != null)
				return false;
		} else if (!item.equals(other.item))
			return false;
        return price == other.price;
    }

	private final ItemStack item;
	private final ItemStack currency;
	private final int price;

	public static HellmartData getEMPTY() {
		return EMPTY;
	}

	public static void setEMPTY(final HellmartData eMPTY) {
		EMPTY = eMPTY;
	}

	static HellmartData EMPTY = new HellmartData(ItemStack.EMPTY, ItemStack.EMPTY, 0);

	public HellmartData(final ItemStack item, final ItemStack currency, final int price) {
		this.item = item;
		this.currency = currency;
		this.price = price;
	}

	public ItemStack getItem() {
		return item;
	}

	public ItemStack getCurrency() {
		return currency;
	}

	public int getPrice() {
		return price;
	}
}