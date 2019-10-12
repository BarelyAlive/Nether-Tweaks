package mod.nethertweaks.registry.manager;

import javax.annotation.Nonnull;

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

public class NTMDefaultRecipes
{
	private static final CompatDefaultRecipes compat = new CompatDefaultRecipes();

	public static void registerDefaults()
	{
		NTMRegistryManager.registerSieveDefaultRecipeHandler(new SieveDefaults());
		NTMRegistryManager.registerHammerDefaultRecipeHandler(new HammerDefaults());
		NTMRegistryManager.registerCompostDefaultRecipeHandler(new CompostDefaults());
		NTMRegistryManager.registerCondenserDefaultRecipeHandler(new CondenserDefaults());
		NTMRegistryManager.registerDrinkDefaultRecipeHandler(new DrinkDefaults());
		NTMRegistryManager.registerHellmartDefaultRecipeHandler(new HellmartDefaults());
		NTMRegistryManager.registerHeatDefaultRecipeHandler(new HeatDefaults());
		NTMRegistryManager.registerOreDefaultRecipeHandler(new OreDefaults());
		NTMRegistryManager.registerBarrelLiquidBlacklistDefaultHandler(new BarrelLiquidBlacklistDefaults());
		NTMRegistryManager.registerFluidOnTopDefaultRecipeHandler(new FluidOnTopDefaults());
		NTMRegistryManager.registerFluidTransformDefaultRecipeHandler(new FluidTransformDefaults());
		NTMRegistryManager.registerFluidBlockDefaultRecipeHandler(new FluidBlockTransformDefaults());
		NTMRegistryManager.registerFluidItemFluidDefaultHandler(new FluidItemFluidDefaults());
		NTMRegistryManager.registerCrucibleStoneDefaultRecipeHandler(new CrucibleStoneDefaults());
		NTMRegistryManager.registerMilkEntityDefaultRecipeHandler(new MilkEntityDefaults());
	}

	private static class CompostDefaults implements ICompostDefaultRegistryProvider {
		@Override
		public void registerRecipeDefaults(@Nonnull final CompostRegistry registry) {
			compat.registerCompost(registry);
		}
	}

	private static class CondenserDefaults implements ICondenserDefaultRegistryProvider {
		@Override
		public void registerRecipeDefaults(@Nonnull final CondenserRegistry registry) {
			compat.registerCondenser(registry);
		}
	}

	private static class DrinkDefaults implements IDrinkDefaultRegistryProvider {
		@Override
		public void registerRecipeDefaults(@Nonnull final DrinkRegistry registry) {
			compat.registerDrink(registry);
		}
	}

	private static class HellmartDefaults implements IHellmartDefaultRegistryProvider {
		@Override
		public void registerRecipeDefaults(@Nonnull final HellmartRegistry registry) {
			compat.registerHellmart(registry);
		}
	}

	private static class SieveDefaults implements ISieveDefaultRegistryProvider {
		@Override
		public void registerRecipeDefaults(@Nonnull final SieveRegistry registry) {
			compat.registerSieve(registry);
		}

		@Override
		public void registerSieveRecipeDefaults() {
		}
	}

	private static class HammerDefaults implements IHammerDefaultRegistryProvider {
		@Override
		public void registerRecipeDefaults(@Nonnull final HammerRegistry registry) {
			compat.registerHammer(registry);
		}
	}

	private static class HeatDefaults implements IHeatDefaultRegistryProvider {
		@Override
		public void registerRecipeDefaults(@Nonnull final HeatRegistry registry) {
			compat.registerHeat(registry);
		}
	}

	private static class BarrelLiquidBlacklistDefaults implements IBarrelLiquidBlacklistDefaultRegistryProvider {
		@Override
		public void registerRecipeDefaults(@Nonnull final BarrelLiquidBlacklistRegistry registry) {
			compat.registerBarrel(registry);
		}
	}

	private static class FluidOnTopDefaults implements IFluidOnTopDefaultRegistryProvider {
		@Override
		public void registerRecipeDefaults(@Nonnull final FluidOnTopRegistry registry) {
			compat.registerFluidOnTop(registry);
		}
	}

	private static class OreDefaults implements IOreDefaultRegistryProvider {
		@Override
		public void registerRecipeDefaults(@Nonnull final OreRegistry registry) {
			compat.registerOreChunks(registry);
		}
	}

	private static class FluidTransformDefaults implements IFluidTransformDefaultRegistryProvider {
		@Override
		public void registerRecipeDefaults(@Nonnull final FluidTransformRegistry registry) {
			compat.registerFluidTransform(registry);
		}
	}

	private static class FluidBlockTransformDefaults implements IFluidBlockDefaultRegistryProvider {
		@Override
		public void registerRecipeDefaults(@Nonnull final FluidBlockTransformerRegistry registry) {
			compat.registerFluidBlockTransform(registry);
		}
	}

	private static class FluidItemFluidDefaults implements IFluidItemFluidDefaultRegistryProvider {
		@Override
		public void registerRecipeDefaults(@Nonnull final FluidItemFluidRegistry registry) {
			compat.registerFluidItemFluid(registry);
		}
	}

	private static class CrucibleStoneDefaults implements ICrucibleStoneDefaultRegistryProvider {

		@Override
		public void registerRecipeDefaults(@Nonnull final CrucibleRegistry registry) {
			compat.registerCrucibleStone(registry);
		}
	}

	public static class MilkEntityDefaults implements IMilkEntityDefaultRegistryProvider {
		@Override
		public void registerRecipeDefaults(@Nonnull final MilkEntityRegistry registry) {
			compat.registerMilk(registry);
		}
	}
}
