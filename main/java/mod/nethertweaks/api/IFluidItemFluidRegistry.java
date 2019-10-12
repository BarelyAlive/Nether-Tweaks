package mod.nethertweaks.api;

import mod.nethertweaks.registry.registries.base.types.FluidItemFluid;
import mod.sfhcore.util.StackInfo;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;

public interface IFluidItemFluidRegistry extends IRegistryList<FluidItemFluid>
{
	void register(String inputFluid, StackInfo reactant, String outputFluid);
	void register(Fluid inputFluid, StackInfo reactant, Fluid outputFluid);

	String getFluidForTransformation(Fluid fluid, ItemStack stack);
}
