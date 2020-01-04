package mod.nethertweaks.block;

import mod.nethertweaks.init.ModFluids;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class Brine extends BlockFluidClassic
{
	public Brine()
	{
		super(ModFluids.FLUID_BRINE, Material.WATER);
		setLightLevel(0);
		setTemperature(FluidRegistry.WATER.getTemperature());
		setDensity(1025);
	}

	@Override
	public IBlockState getStateFromMeta(final int meta)
	{
		return getBlockState().getBaseState().withProperty(LEVEL, meta);
	}

	@Override
	public Fluid getFluid() {
		return ModFluids.FLUID_BRINE;
	}
}