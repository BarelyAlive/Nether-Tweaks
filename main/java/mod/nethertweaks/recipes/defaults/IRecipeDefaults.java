package mod.nethertweaks.recipes.defaults;

import mod.nethertweaks.registry.registries.BarrelLiquidBlacklistRegistry;
import mod.nethertweaks.registry.registries.CompostRegistry;
import mod.nethertweaks.registry.registries.CondenserRegistry;
import mod.nethertweaks.registry.registries.CrucibleRegistry;
import mod.nethertweaks.registry.registries.DrinkRegistry;
import mod.nethertweaks.registry.registries.FluidBlockTransformerRegistry;
import mod.nethertweaks.registry.registries.FluidItemFluidRegistry;
import mod.nethertweaks.registry.registries.FluidOnTopRegistry;
import mod.nethertweaks.registry.registries.FluidTransformRegistry;
import mod.nethertweaks.registry.registries.HammerRegistry;
import mod.nethertweaks.registry.registries.HeatRegistry;
import mod.nethertweaks.registry.registries.HellmartRegistry;
import mod.nethertweaks.registry.registries.MilkEntityRegistry;
import mod.nethertweaks.registry.registries.OreRegistry;
import mod.nethertweaks.registry.registries.SieveRegistry;

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
