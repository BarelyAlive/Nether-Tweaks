package mod.nethertweaks.api;

import javax.annotation.Nonnull;

import mod.nethertweaks.registry.types.FluidFluidBlock;
import mod.sfhcore.util.BlockInfo;
import mod.sfhcore.util.ItemInfo;
import net.minecraftforge.fluids.Fluid;

public interface IFluidOnTopRegistry extends IRegistryList<FluidFluidBlock> {
	void register(Fluid fluidInBarrel, Fluid fluidOnTop, BlockInfo result);
	void register(Fluid fluidInBarrel, Fluid fluidOnTop, ItemInfo result);

	boolean isValidRecipe(@Nonnull Fluid fluidInBarrel, @Nonnull Fluid fluidOnTop);

	BlockInfo getTransformedBlock(Fluid fluidInBarrel, Fluid fluidOnTop);
}
