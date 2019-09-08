package mod.nethertweaks.recipes.defaults;

import mod.nethertweaks.registries.registries.BarrelLiquidBlacklistRegistry;
import mod.nethertweaks.registries.registries.CompostRegistry;
import mod.nethertweaks.registries.registries.CondenserRegistry;
import mod.nethertweaks.registries.registries.CrucibleRegistry;
import mod.nethertweaks.registries.registries.DrinkRegistry;
import mod.nethertweaks.registries.registries.FluidBlockTransformerRegistry;
import mod.nethertweaks.registries.registries.FluidItemFluidRegistry;
import mod.nethertweaks.registries.registries.FluidOnTopRegistry;
import mod.nethertweaks.registries.registries.FluidTransformRegistry;
import mod.nethertweaks.registries.registries.HammerRegistry;
import mod.nethertweaks.registries.registries.HeatRegistry;
import mod.nethertweaks.registries.registries.HellmartRegistry;
import mod.nethertweaks.registries.registries.MilkEntityRegistry;
import mod.nethertweaks.registries.registries.OreRegistry;
import mod.nethertweaks.registries.registries.SieveRegistry;

public interface IRecipeDefaults
{
	String getMODID();

	default void registerCompost(final CompostRegistry registry) {}
	default void registerCondenser(final CondenserRegistry registry) {}
	default void registerDrink(final DrinkRegistry registry) {}
	default void registerHellmart(final HellmartRegistry registry) {}
	default void registerSieve(final SieveRegistry registry) {}
	default void registerHammer(final HammerRegistry registry) {}
	default void registerHeat(final HeatRegistry registry) {}
	default void registerBarrelLiquidBlacklist(final BarrelLiquidBlacklistRegistry registry) {}
	default void registerFluidOnTop(final FluidOnTopRegistry registry) {}
	default void registerOreChunks(final OreRegistry registry) {}
	default void registerFluidTransform(final FluidTransformRegistry registry) {}
	default void registerFluidBlockTransform(final FluidBlockTransformerRegistry registry) {}
	default void registerFluidItemFluid(final FluidItemFluidRegistry registry) {}
	default void registerCrucibleStone(final CrucibleRegistry registry) {}
	default void registerMilk(final MilkEntityRegistry registry) {}
}
