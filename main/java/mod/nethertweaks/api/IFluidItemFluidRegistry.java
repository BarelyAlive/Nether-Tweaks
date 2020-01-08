package mod.nethertweaks.api;

import javax.annotation.Nonnull;

import mod.nethertweaks.registry.registries.base.types.FluidItemFluid;
import mod.sfhcore.util.StackInfo;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;

public interface IFluidItemFluidRegistry extends IRegistryList<FluidItemFluid>
{
	void register(String inputFluid, StackInfo reactant, String outputFluid);
	void register(Fluid inputFluid, StackInfo reactant, Fluid outputFluid);
	void register(@Nonnull final Fluid inputFluid, @Nonnull final StackInfo reactant, @Nonnull final Fluid outputFluid, final int transformTime);
	void register(@Nonnull final Fluid inputFluid, @Nonnull final StackInfo reactant, @Nonnull final Fluid outputFluid, final int transformTime, final boolean consumable);

	String getFluidForTransformation(Fluid fluid, ItemStack stack);
}
