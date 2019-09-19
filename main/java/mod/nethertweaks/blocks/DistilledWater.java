package mod.nethertweaks.blocks;

import mod.nethertweaks.Constants;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.handler.BucketNFluidHandler;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;


public class DistilledWater extends BlockFluidClassic
{
	public DistilledWater()
	{
		super(BucketNFluidHandler.FLUIDDISTILLEDWATER, Material.WATER);
		setRegistryName(NetherTweaksMod.MODID, Constants.DISTILLEDWATER);
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
		return BucketNFluidHandler.FLUIDDISTILLEDWATER;
	}


}
