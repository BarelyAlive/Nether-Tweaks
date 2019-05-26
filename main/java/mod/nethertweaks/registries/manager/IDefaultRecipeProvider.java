package mod.nethertweaks.registries.manager;

import mod.nethertweaks.registries.registries.*;
import mod.nethertweaks.registries.registries.base.BaseRegistry;

public interface IDefaultRecipeProvider<T> {
    public void  registerRecipeDefaults(T registry);
}

interface IFluidBlockDefaultRegistryProvider extends IDefaultRecipeProvider<FluidBlockTransformerRegistry>{};
interface IFluidItemFluidDefaultRegistryProvider extends IDefaultRecipeProvider<FluidItemFluidRegistry>{};
interface IFluidOnTopDefaultRegistryProvider extends IDefaultRecipeProvider<FluidOnTopRegistry>{};
interface IFluidTransformDefaultRegistryProvider extends IDefaultRecipeProvider<FluidTransformRegistry>{};
interface IHammerDefaultRegistryProvider extends IDefaultRecipeProvider<HammerRegistry>{};
interface IHeatDefaultRegistryProvider extends IDefaultRecipeProvider<HeatRegistry>{};
interface IMilkEntityDefaultRegistryProvider extends IDefaultRecipeProvider<MilkEntityRegistry>{};
interface IOreDefaultRegistryProvider extends IDefaultRecipeProvider<OreRegistry>{};

interface ISieveDefaultRegistryProvider extends IDefaultRecipeProvider<SieveRegistry>{
void registerSieveRecipeDefaults();};

interface ICondenserDefaultRegistryProvider extends IDefaultRecipeProvider<CondenserRegistry>{};
interface IHellmartDefaultRegistryProvider extends IDefaultRecipeProvider<HellmartRegistry>{};
interface ICrucibleStoneDefaultRegistryProvider extends IDefaultRecipeProvider<CrucibleRegistry>{};
interface ICompostDefaultRegistryProvider extends IDefaultRecipeProvider<CompostRegistry>{};
interface IBarrelLiquidBlacklistDefaultRegistryProvider extends IDefaultRecipeProvider<BarrelLiquidBlacklistRegistry>{};
