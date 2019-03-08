package mod.nethertweaks.blocks;

import mod.nethertweaks.INames;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class FluidDemonWater extends Fluid{

	private static ResourceLocation still = new ResourceLocation("nethertweaksmod:blocks/blockDemonWater_still");
	private static ResourceLocation flowing = new ResourceLocation("nethertweaksmod:blocks/blockDemonWater_flow");
	
	public FluidDemonWater() {
		super(INames.DEMONWATERFLUID, still, flowing);
		setDensity(FluidRegistry.WATER.getDensity());
        setViscosity(FluidRegistry.WATER.getViscosity());
		FluidRegistry.registerFluid(this);
	}
	
	@Override
	public boolean doesVaporize(FluidStack fluidStack) {
		return false;
	}
}
