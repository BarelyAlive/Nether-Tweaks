package mod.nethertweaks.api;

import mod.nethertweaks.registry.types.Meltable;
import mod.sfhcore.util.BlockInfo;
import mod.sfhcore.util.StackInfo;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fluids.Fluid;

public interface ICrucibleRegistry extends IRegistryMap<Ingredient, Meltable> {

	public void register(String name, Fluid fluid, int amount, BlockInfo block);
	public void register(StackInfo item, Fluid fluid, int amount);
	public void register(StackInfo item, Meltable meltable);
	public void register(ItemStack stack, Fluid fluid, int amount);
	public void register(ItemStack stack, Meltable meltable);
	public void register(String name, Fluid fluid, int amount);
	public void register(String name, Meltable meltable);

	public boolean canBeMelted(ItemStack stack);
	public boolean canBeMelted(StackInfo info);
	
	public Meltable getMeltable(ItemStack stack);
	public Meltable getMeltable(StackInfo info);
	public Meltable getMeltable(Item item, int meta);
}
