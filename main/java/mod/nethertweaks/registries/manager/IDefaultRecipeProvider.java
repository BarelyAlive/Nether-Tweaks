package mod.nethertweaks.registries.manager;

import mod.nethertweaks.registries.registries.BarrelLiquidBlacklistRegistry;
import mod.nethertweaks.registries.registries.CompostRegistry;
import mod.nethertweaks.registries.registries.CondenserRegistry;
import mod.nethertweaks.registries.registries.CrucibleRegistry;
import mod.nethertweaks.registries.registries.DynOreRegistry;
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

public interface IDefaultRecipeProvider<T> {
    public void  registerRecipeDefaults(T registry);
}

interface IDynOreDefaultRegistryProvider extends IDefaultRecipeProvider<DynOreRegistry>{};
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
