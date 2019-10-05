package mod.nethertweaks.blocks;

import mod.nethertweaks.Constants;
import mod.nethertweaks.handler.FluidHandler;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;


public class DistilledWater extends BlockFluidClassic
{
	public DistilledWater()
	{
		super(FluidHandler.FLUID_DISTILLED_WATER, Material.WATER);
		setRegistryName(Constants.MODID, Constants.DISTILLED_WATER);
		setUnlocalizedName(Constants.DISTILLED_WATER);
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
		return FluidHandler.FLUID_DISTILLED_WATER;
	}


}
