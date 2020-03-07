package mod.nethertweaks.api;

import javax.annotation.Nonnull;

import mod.nethertweaks.registry.registries.base.types.FluidToWater;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;

public interface IFluidToWaterRegistry extends IRegistryList<FluidToWater>
{
	void register(String inputFluid, int percOfWater);

	FluidToWater getFluid(Fluid fluid);
	FluidToWater getFluid(@Nonnull ItemStack stack);

	boolean containsFluid(Fluid fluid);
	boolean containsFluid(ItemStack stack);
}
