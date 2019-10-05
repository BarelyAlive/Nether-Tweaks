package mod.nethertweaks.fluid;

import mod.nethertweaks.Constants;
import mod.nethertweaks.config.Config;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class FluidLiquidImpossibility extends Fluid{

	public static final ResourceLocation STILL = new ResourceLocation("nethertweaksmod:blocks/liquid_impossibility_still");
	public static final ResourceLocation FLOW = new ResourceLocation("nethertweaksmod:blocks/liquid_impossibility_flow");

	public FluidLiquidImpossibility()
	{
		super(Constants.LIQUID_IMPOSSIBILITY, STILL, FLOW);

		setTemperature(Config.temperatureLI);
		setDensity(Config.densityLI);
		setViscosity(Config.viscosityLI);
		setLuminosity(Config.luminosityLI);
		
		FluidRegistry.registerFluid(this);
		FluidRegistry.addBucketForFluid(this);
	}

	@Override
	public SoundEvent getEmptySound(final FluidStack stack) {
		return SoundEvents.ITEM_BUCKET_FILL;
	}

	@Override
	public boolean doesVaporize(final FluidStack fluidStack) {
		return Config.doesLIVaporize;
	}
}
