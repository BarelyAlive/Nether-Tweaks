package mod.nethertweaks.registries.manager;

import java.util.ArrayList;
import java.util.List;

import mod.nethertweaks.registries.manager.NTMDefaultRecipes.MilkEntityDefaults;
import mod.nethertweaks.registries.registries.BarrelLiquidBlacklistRegistry;
import mod.nethertweaks.registries.registries.CompostRegistry;
import mod.nethertweaks.registries.registries.CondenserRegistry;
import mod.nethertweaks.registries.registries.CrucibleRegistry;
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

public final class NTMRegistryManager {

    //region >>>> DEFAULT RECIPE PROVIDERS
    public static final List<ISieveDefaultRegistryProvider> SIEVE_DEFAULT_REGISTRY_PROVIDERS = new ArrayList<>();
    public static final List<IHammerDefaultRegistryProvider> HAMMER_DEFAULT_REGISTRY_PROVIDERS = new ArrayList<>();
    public static final List<ICompostDefaultRegistryProvider> COMPOST_DEFAULT_REGISTRY_PROVIDERS = new ArrayList<>();
    public static final List<ICrucibleStoneDefaultRegistryProvider> CRUCIBLE_STONE_DEFAULT_REGISTRY_PROVIDERS = new ArrayList<>();
    public static final List<IFluidBlockDefaultRegistryProvider> FLUID_BLOCK_DEFAULT_REGISTRY_PROVIDERS = new ArrayList<>();
    public static final List<IFluidOnTopDefaultRegistryProvider> FLUID_ON_TOP_DEFAULT_REGISTRY_PROVIDERS = new ArrayList<>();
    public static final List<IFluidTransformDefaultRegistryProvider> FLUID_TRANSFORM_DEFAULT_REGISTRY_PROVIDERS = new ArrayList<>();
    public static final List<IFluidItemFluidDefaultRegistryProvider> FLUID_ITEM_FLUID_DEFAULT_REGISTRY_PROVIDERS = new ArrayList<>();
    public static final List<IHeatDefaultRegistryProvider> HEAT_DEFAULT_REGISTRY_PROVIDERS = new ArrayList<>();
    public static final List<IOreDefaultRegistryProvider> ORE_DEFAULT_REGISTRY_PROVIDERS = new ArrayList<>();
    public static final List<IMilkEntityDefaultRegistryProvider> MILK_ENTITY_DEFAULT_REGISTRY_PROVIDERS = new ArrayList<>();
    public static final List<IBarrelLiquidBlacklistDefaultRegistryProvider> BARREL_LIQUID_BLACKLIST_DEFAULT_REGISTRY_PROVIDERS = new ArrayList<>();
    public static final List<ICondenserDefaultRegistryProvider> CONDENSER_DEFAULT_REGISTRY_PROVIDERS = new ArrayList<>();
    public static final List<IHellmartDefaultRegistryProvider> HELLMART_DEFAULT_REGISTRY_PROVIDERS = new ArrayList<>();
    
    //endregion

    public static final CompostRegistry COMPOST_REGISTRY = new CompostRegistry();
    public static final SieveRegistry SIEVE_REGISTRY = new SieveRegistry();
    public static final HammerRegistry HAMMER_REGISTRY = new HammerRegistry();
    public static final HeatRegistry HEAT_REGISTRY = new HeatRegistry();
    public static final OreRegistry ORE_REGISTRY = new OreRegistry();
    public static final BarrelLiquidBlacklistRegistry BARREL_LIQUID_BLACKLIST_REGISTRY = new BarrelLiquidBlacklistRegistry();
    public static final FluidOnTopRegistry FLUID_ON_TOP_REGISTRY = new FluidOnTopRegistry();
    public static final FluidTransformRegistry FLUID_TRANSFORM_REGISTRY = new FluidTransformRegistry();
    public static final FluidBlockTransformerRegistry FLUID_BLOCK_TRANSFORMER_REGISTRY = new FluidBlockTransformerRegistry();
    public static final FluidItemFluidRegistry FLUID_ITEM_FLUID_REGISTRY = new FluidItemFluidRegistry();
    public static final CrucibleRegistry CRUCIBLE_STONE_REGISTRY = new CrucibleRegistry(CRUCIBLE_STONE_DEFAULT_REGISTRY_PROVIDERS);
    public static final MilkEntityRegistry MILK_ENTITY_REGISTRY = new MilkEntityRegistry();
    public static final CondenserRegistry CONDENSER_REGISTRY = new CondenserRegistry();
    public static final HellmartRegistry HELLMART_REGISTRY = new HellmartRegistry();

    //region >>>> DEFAULT RECIPE REGISTERS

    public static void registerSieveDefaultRecipeHandler(ISieveDefaultRegistryProvider provider) {
        SIEVE_DEFAULT_REGISTRY_PROVIDERS.add(provider);
    }

    public static void registerHammerDefaultRecipeHandler(IHammerDefaultRegistryProvider provider) {
        HAMMER_DEFAULT_REGISTRY_PROVIDERS.add(provider);
    }

    public static void registerCompostDefaultRecipeHandler(ICompostDefaultRegistryProvider provider) {
        COMPOST_DEFAULT_REGISTRY_PROVIDERS.add(provider);
    }

    public static void registerCrucibleStoneDefaultRecipeHandler(ICrucibleStoneDefaultRegistryProvider provider) {
        CRUCIBLE_STONE_DEFAULT_REGISTRY_PROVIDERS.add(provider);
    }

    public static void registerFluidBlockDefaultRecipeHandler(IFluidBlockDefaultRegistryProvider provider) {
        FLUID_BLOCK_DEFAULT_REGISTRY_PROVIDERS.add(provider);
    }

    public static void registerFluidTransformDefaultRecipeHandler(IFluidTransformDefaultRegistryProvider provider) {
        FLUID_TRANSFORM_DEFAULT_REGISTRY_PROVIDERS.add(provider);
    }

    public static void registerFluidItemFluidDefaultHandler(IFluidItemFluidDefaultRegistryProvider provider) {
        FLUID_ITEM_FLUID_DEFAULT_REGISTRY_PROVIDERS.add(provider);
    }

    public static void registerFluidOnTopDefaultRecipeHandler(IFluidOnTopDefaultRegistryProvider provider) {
        FLUID_ON_TOP_DEFAULT_REGISTRY_PROVIDERS.add(provider);
    }

    public static void registerHeatDefaultRecipeHandler(IHeatDefaultRegistryProvider provider) {
        HEAT_DEFAULT_REGISTRY_PROVIDERS.add(provider);
    }

    public static void registerOreDefaultRecipeHandler(IOreDefaultRegistryProvider provider) {
        ORE_DEFAULT_REGISTRY_PROVIDERS.add(provider);
    }

    public static void registerBarrelLiquidBlacklistDefaultHandler(IBarrelLiquidBlacklistDefaultRegistryProvider provider) {
        BARREL_LIQUID_BLACKLIST_DEFAULT_REGISTRY_PROVIDERS.add(provider);
    }

	public static void registerMilkEntityDefaultRecipeHandler(MilkEntityDefaults milkEntityDefaults) {
		MILK_ENTITY_DEFAULT_REGISTRY_PROVIDERS.add(milkEntityDefaults);
	}

	public static void registerCondenserDefaultRecipeHandler(ICondenserDefaultRegistryProvider provider) {
		CONDENSER_DEFAULT_REGISTRY_PROVIDERS.add(provider);
	}

	public static void registerHellmartDefaultRecipeHandler(IHellmartDefaultRegistryProvider provider) {
		HELLMART_DEFAULT_REGISTRY_PROVIDERS.add(provider);
	}
	
    //endregion
}