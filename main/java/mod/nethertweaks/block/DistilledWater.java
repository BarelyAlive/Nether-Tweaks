package mod.nethertweaks.block;

import mod.nethertweaks.init.ModFluids;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class DistilledWater extends BlockFluidClassic
{
	public DistilledWater()
	{
		super(ModFluids.FLUID_DISTILLED_WATER, Material.WATER);
		setLightLevel(0);
		setTemperature(FluidRegistry.WATER.getTemperature());
		setDensity(FluidRegistry.WATER.getDensity());
	}

	@Override
	public IBlockState getStateFromMeta(final int meta)
	{
		return getBlockState().getBaseState().withProperty(LEVEL, meta);
	}

	@Override
	public Fluid getFluid() {
		return ModFluids.FLUID_DISTILLED_WATER;
	}
}
