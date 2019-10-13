package mod.nethertweaks.registry.manager;

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
import mod.nethertweaks.registry.registries.OreRegistry;
import mod.nethertweaks.registry.registries.SieveRegistry;

public interface IDefaultRecipeProvider<T> {
	void  registerRecipeDefaults(T registry);
}

interface IDynOreDefaultRegistryProvider extends IDefaultRecipeProvider<DynOreRegistry>{}
interface IFluidBlockDefaultRegistryProvider extends IDefaultRecipeProvider<FluidBlockTransformerRegistry>{}
interface IFluidItemFluidDefaultRegistryProvider extends IDefaultRecipeProvider<FluidItemFluidRegistry>{}
interface IFluidOnTopDefaultRegistryProvider extends IDefaultRecipeProvider<FluidOnTopRegistry>{}
interface IFluidTransformDefaultRegistryProvider extends IDefaultRecipeProvider<FluidTransformRegistry>{}
interface IHammerDefaultRegistryProvider extends IDefaultRecipeProvider<HammerRegistry>{}
interface IHeatDefaultRegistryProvider extends IDefaultRecipeProvider<HeatRegistry>{}
interface IMilkEntityDefaultRegistryProvider extends IDefaultRecipeProvider<MilkEntityRegistry>{}
interface IOreDefaultRegistryProvider extends IDefaultRecipeProvider<OreRegistry>{}

interface ISieveDefaultRegistryProvider extends IDefaultRecipeProvider<SieveRegistry>{
	void registerSieveRecipeDefaults();}

interface ICondenserDefaultRegistryProvider extends IDefaultRecipeProvider<CondenserRegistry>{}
interface IDrinkDefaultRegistryProvider extends IDefaultRecipeProvider<DrinkRegistry>{}
interface IHellmartDefaultRegistryProvider extends IDefaultRecipeProvider<HellmartRegistry>{}
interface ICrucibleStoneDefaultRegistryProvider extends IDefaultRecipeProvider<CrucibleRegistry>{}
interface ICompostDefaultRegistryProvider extends IDefaultRecipeProvider<CompostRegistry>{}
interface IBarrelLiquidBlacklistDefaultRegistryProvider extends IDefaultRecipeProvider<BarrelLiquidBlacklistRegistry>{}
