package mod.nethertweaks.recipes.defaults;

import mod.nethertweaks.registries.registries.*;

public interface IRecipeDefaults
{
	 String getMODID();
	
	 default void registerCompost(CompostRegistry registry) {}
	 default void registerSieve(SieveRegistry registry) {}
	 default void registerHammer(HammerRegistry registry) {}
	 default void registerHeat(HeatRegistry registry) {}
	 default void registerBarrelLiquidBlacklist(BarrelLiquidBlacklistRegistry registry) {}
	 default void registerFluidOnTop(FluidOnTopRegistry registry) {}
	 default void registerOreChunks(OreRegistry registry) {}
	 default void registerFluidTransform(FluidTransformRegistry registry) {}
	 default void registerFluidBlockTransform(FluidBlockTransformerRegistry registry) {}
	 default void registerFluidItemFluid(FluidItemFluidRegistry registry) {}
	 default void registerCrucibleStone(CrucibleRegistry registry) {}
	 default void registerMilk(MilkEntityRegistry registry) {}
}
