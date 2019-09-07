package mod.nethertweaks.api;

import net.minecraft.item.ItemStack;

public interface IHammer {

	boolean isHammer(ItemStack stack);

	int getMiningLevel(ItemStack stack);

}
