package mod.nethertweaks.registry.manager;

import java.util.ArrayList;
import java.util.List;

import mod.nethertweaks.registry.manager.NTMDefaultRecipes.MilkEntityDefaults;
import mod.nethertweaks.registry.registries.BarrelLiquidBlacklistRegistry;
import mod.nethertweaks.registry.registries.CompostRegistry;
import mod.nethertweaks.registry.registries.CondenserRegistry;
import mod.nethertweaks.registry.registries.CrucibleRegistry;
import mod.nethertweaks.registry.registries.DrinkRegistry;
import mod.nethertweaks.registry.registries.DynOreRegistry;
import mod.nethertweaks.registry.registries.FluidBlockTransformerRegistry;
import mod.nethertweaks.registry.registries.FluidItemFluidRegistry;
import mod.nethertweaks.registry.registries.FluidOnTopRegistry;
import mod.nethertweaks.registry.registries.FluidTransformRegistry;
import mod.nethertweaks.registry.registries.HammerRegistry;
import mod.nethertweaks.registry.registries.HeatRegistry;
import mod.nethertweaks.registry.registries.HellmartRegistry;
import mod.nethertweaks.registry.registries.MilkEntityRegistry;
import mod.nethertweaks.registry.registries.SieveRegistry;

public final class NTMRegistryManager {

	//region >>>> DEFAULT RECIPE PROVIDERS
	public static final List<ISieveDefaultRegistryProvider> SIEVE_DEFAULT_REGISTRY_PROVIDERS = new ArrayList<>();
	public static final List<IHammerDefaultRegistryProvider> HAMMER_DEFAULT_REGISTRY_PROVIDERS = new ArrayList<>();
	public static final List<ICompostDefaultRegistryProvider> COMPOST_DEFAULT_REGISTRY_PROVIDERS = new ArrayList<>();
	public static final List<ICrucibleStoneDefaultRegistryProvider> CRUCIBLE_STONE_DEFAULT_REGISTRY_PROVIDERS = new ArrayList<>();
    public static final List<IDynOreDefaultRegistryProvider> DYN_ORE_DEFAULT_REGISTRY_PROVIDERS = new ArrayList<>();
	public static final List<IFluidBlockDefaultRegistryProvider> FLUID_BLOCK_DEFAULT_REGISTRY_PROVIDERS = new ArrayList<>();
	public static final List<IFluidOnTopDefaultRegistryProvider> FLUID_ON_TOP_DEFAULT_REGISTRY_PROVIDERS = new ArrayList<>();
	public static final List<IFluidTransformDefaultRegistryProvider> FLUID_TRANSFORM_DEFAULT_REGISTRY_PROVIDERS = new ArrayList<>();
	public static final List<IFluidItemFluidDefaultRegistryProvider> FLUID_ITEM_FLUID_DEFAULT_REGISTRY_PROVIDERS = new ArrayList<>();
	public static final List<IHeatDefaultRegistryProvider> HEAT_DEFAULT_REGISTRY_PROVIDERS = new ArrayList<>();
	public static final List<IMilkEntityDefaultRegistryProvider> MILK_ENTITY_DEFAULT_REGISTRY_PROVIDERS = new ArrayList<>();
	public static final List<IBarrelLiquidBlacklistDefaultRegistryProvider> BARREL_LIQUID_BLACKLIST_DEFAULT_REGISTRY_PROVIDERS = new ArrayList<>();
	public static final List<ICondenserDefaultRegistryProvider> CONDENSER_DEFAULT_REGISTRY_PROVIDERS = new ArrayList<>();
	public static final List<IDrinkDefaultRegistryProvider> DRINK_DEFAULT_REGISTRY_PROVIDERS = new ArrayList<>();
	public static final List<IHellmartDefaultRegistryProvider> HELLMART_DEFAULT_REGISTRY_PROVIDERS = new ArrayList<>();

	//endregion

	public static final CompostRegistry COMPOST_REGISTRY = new CompostRegistry();
	public static final SieveRegistry SIEVE_REGISTRY = new SieveRegistry();
	public static final HammerRegistry HAMMER_REGISTRY = new HammerRegistry();
	public static final HeatRegistry HEAT_REGISTRY = new HeatRegistry();
	public static final DynOreRegistry DYN_ORE_REGISTRY = new DynOreRegistry();
	public static final BarrelLiquidBlacklistRegistry BARREL_LIQUID_BLACKLIST_REGISTRY = new BarrelLiquidBlacklistRegistry();
	public static final FluidOnTopRegistry FLUID_ON_TOP_REGISTRY = new FluidOnTopRegistry();
	public static final FluidTransformRegistry FLUID_TRANSFORM_REGISTRY = new FluidTransformRegistry();
	public static final FluidBlockTransformerRegistry FLUID_BLOCK_TRANSFORMER_REGISTRY = new FluidBlockTransformerRegistry();
	public static final FluidItemFluidRegistry FLUID_ITEM_FLUID_REGISTRY = new FluidItemFluidRegistry();
	public static final CrucibleRegistry CRUCIBLE_STONE_REGISTRY = new CrucibleRegistry(CRUCIBLE_STONE_DEFAULT_REGISTRY_PROVIDERS);
	public static final MilkEntityRegistry MILK_ENTITY_REGISTRY = new MilkEntityRegistry();
	public static final CondenserRegistry CONDENSER_REGISTRY = new CondenserRegistry();
	public static final DrinkRegistry DRINK_REGISTRY = new DrinkRegistry();
	public static final HellmartRegistry HELLMART_REGISTRY = new HellmartRegistry();

	//region >>>> DEFAULT RECIPE REGISTERS

	public static void registerSieveDefaultRecipeHandler(final ISieveDefaultRegistryProvider provider) {
		SIEVE_DEFAULT_REGISTRY_PROVIDERS.add(provider);
	}

	public static void registerHammerDefaultRecipeHandler(final IHammerDefaultRegistryProvider provider) {
		HAMMER_DEFAULT_REGISTRY_PROVIDERS.add(provider);
	}

	public static void registerCompostDefaultRecipeHandler(final ICompostDefaultRegistryProvider provider) {
		COMPOST_DEFAULT_REGISTRY_PROVIDERS.add(provider);
	}

	public static void registerCrucibleStoneDefaultRecipeHandler(final ICrucibleStoneDefaultRegistryProvider provider) {
		CRUCIBLE_STONE_DEFAULT_REGISTRY_PROVIDERS.add(provider);
	}

    public static void registerDynOreDefaultRecipeHandler(final IDynOreDefaultRegistryProvider provider) {
    	DYN_ORE_DEFAULT_REGISTRY_PROVIDERS.add(provider);
    }

	public static void registerFluidBlockDefaultRecipeHandler(final IFluidBlockDefaultRegistryProvider provider) {
		FLUID_BLOCK_DEFAULT_REGISTRY_PROVIDERS.add(provider);
	}

	public static void registerFluidTransformDefaultRecipeHandler(final IFluidTransformDefaultRegistryProvider provider) {
		FLUID_TRANSFORM_DEFAULT_REGISTRY_PROVIDERS.add(provider);
	}

	public static void registerFluidItemFluidDefaultHandler(final IFluidItemFluidDefaultRegistryProvider provider) {
		FLUID_ITEM_FLUID_DEFAULT_REGISTRY_PROVIDERS.add(provider);
	}

	public static void registerFluidOnTopDefaultRecipeHandler(final IFluidOnTopDefaultRegistryProvider provider) {
		FLUID_ON_TOP_DEFAULT_REGISTRY_PROVIDERS.add(provider);
	}

	public static void registerHeatDefaultRecipeHandler(final IHeatDefaultRegistryProvider provider) {
		HEAT_DEFAULT_REGISTRY_PROVIDERS.add(provider);
	}

	public static void registerBarrelLiquidBlacklistDefaultHandler(final IBarrelLiquidBlacklistDefaultRegistryProvider provider) {
		BARREL_LIQUID_BLACKLIST_DEFAULT_REGISTRY_PROVIDERS.add(provider);
	}

	public static void registerMilkEntityDefaultRecipeHandler(final MilkEntityDefaults milkEntityDefaults) {
		MILK_ENTITY_DEFAULT_REGISTRY_PROVIDERS.add(milkEntityDefaults);
	}

	public static void registerCondenserDefaultRecipeHandler(final ICondenserDefaultRegistryProvider provider) {
		CONDENSER_DEFAULT_REGISTRY_PROVIDERS.add(provider);
	}

	public static void registerDrinkDefaultRecipeHandler(final IDrinkDefaultRegistryProvider provider) {
		DRINK_DEFAULT_REGISTRY_PROVIDERS.add(provider);
	}

	public static void registerHellmartDefaultRecipeHandler(final IHellmartDefaultRegistryProvider provider) {
		HELLMART_DEFAULT_REGISTRY_PROVIDERS.add(provider);
	}

	//endregion
}