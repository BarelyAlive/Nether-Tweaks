package mod.nethertweaks.api;

import javax.annotation.Nullable;

import mod.nethertweaks.registry.types.FluidItemFluid;
import mod.sfhcore.util.StackInfo;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;

public interface IFluidItemFluidRegistry extends IRegistryList<FluidItemFluid>
{
    public void register(String inputFluid, StackInfo reactant, String outputFluid);
    public void register(Fluid inputFluid, StackInfo reactant, Fluid outputFluid);

    public String getFluidForTransformation(Fluid fluid, ItemStack stack);
}
