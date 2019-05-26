package mod.nethertweaks.registry.types;

import mod.sfhcore.texturing.Color;
import mod.sfhcore.util.BlockInfo;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;

public class Heat
{	
	//Heat Helper
	
	@Override
	public String toString() {
		return "Heat [item=" + item + ", value=" + value + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((item == null) ? 0 : item.hashCode());
		result = prime * result + value;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Heat other = (Heat) obj;
		if (item == null) {
			if (other.item != null)
				return false;
		} else if (!item.equals(other.item))
			return false;
		if (value != other.value)
			return false;
		return true;
	}

	private BlockInfo item;
	private int value;
	
	public static Heat getEMPTY() {
		return EMPTY;
	}

	public static void setEMPTY(Heat eMPTY) {
		EMPTY = eMPTY;
	}

	static Heat EMPTY = new Heat(new BlockInfo(ItemStack.EMPTY), 0);
	
	public Heat(BlockInfo stack, int value)
	{
		this.item = stack;
		this.value = value;
	}
	
	public BlockInfo getItem()
	{
		return this.item;
	}
	
	public int getValue()
	{
		return this.value;
	}
}
