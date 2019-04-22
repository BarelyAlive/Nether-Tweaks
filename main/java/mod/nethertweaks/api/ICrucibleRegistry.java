package mod.nethertweaks.api;

import mod.nethertweaks.registry.types.*;
import mod.sfhcore.util.*;
import net.minecraft.item.*;
import net.minecraft.item.crafting.*;
import net.minecraftforge.fluids.*;

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
