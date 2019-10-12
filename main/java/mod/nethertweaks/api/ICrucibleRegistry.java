package mod.nethertweaks.api;

import mod.nethertweaks.registry.registries.base.types.Meltable;
import mod.sfhcore.util.BlockInfo;
import mod.sfhcore.util.StackInfo;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fluids.Fluid;

public interface ICrucibleRegistry extends IRegistryMap<Ingredient, Meltable> {

	void register(String name, Fluid fluid, int amount, BlockInfo block);
	void register(StackInfo item, Fluid fluid, int amount);
	void register(StackInfo item, Meltable meltable);
	void register(ItemStack stack, Fluid fluid, int amount);
	void register(ItemStack stack, Meltable meltable);
	void register(String name, Fluid fluid, int amount);
	void register(String name, Meltable meltable);

	boolean canBeMelted(ItemStack stack);
	boolean canBeMelted(StackInfo info);

	Meltable getMeltable(ItemStack stack);
	Meltable getMeltable(StackInfo info);
	Meltable getMeltable(Item item, int meta);
}
