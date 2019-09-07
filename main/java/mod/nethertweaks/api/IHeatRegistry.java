package mod.nethertweaks.api;

import mod.sfhcore.util.BlockInfo;
import net.minecraft.item.ItemStack;

public interface IHeatRegistry extends IRegistryMap<String, Integer> {

	void register (ItemStack stack, int heatAmount);
	void register (String name, int heatAmount);

	int getHeatAmount(ItemStack stack);
	int getHeatAmount(BlockInfo info);

}
