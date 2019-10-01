package mod.nethertweaks.fluid;

import mod.nethertweaks.Constants;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class FluidDistilledWater extends Fluid{

	public static final ResourceLocation STILL = new ResourceLocation("nethertweaksmod:blocks/distilled_water_still");
	public static final ResourceLocation FLOW = new ResourceLocation("nethertweaksmod:blocks/distilled_water_flow");

	public FluidDistilledWater()
	{
		super(Constants.DISTILLED_WATER, STILL, FLOW);

		setTemperature(FluidRegistry.WATER.getTemperature());
		setDensity(FluidRegistry.WATER.getDensity());
		setViscosity(FluidRegistry.WATER.getViscosity());
		setLuminosity(FluidRegistry.WATER.getLuminosity());

		FluidRegistry.registerFluid(this);
		FluidRegistry.addBucketForFluid(this);
	}

	@Override
	public SoundEvent getEmptySound(final FluidStack stack) {
		return SoundEvents.ITEM_BUCKET_FILL;
	}

	@Override
	public boolean doesVaporize(final FluidStack fluidStack) {
		return true;
	}
}
