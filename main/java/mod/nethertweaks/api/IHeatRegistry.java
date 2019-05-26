package mod.nethertweaks.api;

import mod.nethertweaks.registry.types.Heat;
import mod.sfhcore.util.BlockInfo;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

public interface IHeatRegistry extends IRegistryMap<Ingredient, Heat> {

	public void register (ItemStack stack, int heatAmount);
	public void register (String name, int heatAmount);
	
	public int getHeatAmount(ItemStack stack);
	public int getHeatAmount(BlockInfo info);
	
}
