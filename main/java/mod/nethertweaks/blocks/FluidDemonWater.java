package mod.nethertweaks.blocks;

import mod.nethertweaks.INames;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class FluidDemonWater extends Fluid{

	public static ResourceLocation still = new ResourceLocation("nethertweaksmod:blocks/block_demon_water_still");
	public static ResourceLocation flow = new ResourceLocation("nethertweaksmod:blocks/block_demon_water_flow");
	
	public FluidDemonWater() {
		super(INames.DEMONWATERFLUID, still, flow);
		setDensity(2000);
        setViscosity(2000);
		FluidRegistry.registerFluid(this);
		setUnlocalizedName(INames.DEMONWATERFLUID);
	}
}
