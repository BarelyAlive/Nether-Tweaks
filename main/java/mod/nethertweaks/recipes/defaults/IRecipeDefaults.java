package mod.nethertweaks.recipes.defaults;

import mod.nethertweaks.registries.registries.BarrelLiquidBlacklistRegistry;
import mod.nethertweaks.registries.registries.CompostRegistry;
import mod.nethertweaks.registries.registries.FluidBlockTransformerRegistry;
import mod.nethertweaks.registries.registries.FluidItemFluidRegistry;
import mod.nethertweaks.registries.registries.FluidOnTopRegistry;
import mod.nethertweaks.registries.registries.FluidTransformRegistry;
import mod.nethertweaks.registries.registries.HammerRegistry;
import mod.nethertweaks.registries.registries.MilkEntityRegistry;
import mod.nethertweaks.registries.registries.OreRegistry;
import mod.nethertweaks.registries.registries.SieveRegistry;

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
