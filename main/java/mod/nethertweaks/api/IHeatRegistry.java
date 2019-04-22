package mod.nethertweaks.api;

import mod.sfhcore.util.BlockInfo;
import net.minecraft.item.ItemStack;

public interface IHeatRegistry extends IRegistryMap<BlockInfo, Integer> {

	public void register (ItemStack stack, int heatAmount);
	
	public int getHeatAmount(ItemStack stack);
	public int getHeatAmount(BlockInfo info);
	
}
