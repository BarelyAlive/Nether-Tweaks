package mod.nethertweaks.registries.manager;

import java.util.ArrayList;
import java.util.List;

import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.recipes.defaults.IRecipeDefaults;
import mod.nethertweaks.recipes.defaults.NTM;
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
import mod.sfhcore.modules.ISFHCoreModule;
import net.minecraftforge.fml.common.Loader;

class CompatDefaultRecipes
{
	private static final List<IRecipeDefaults> MODS = new ArrayList<>();

	public CompatDefaultRecipes()
	{
		MODS.add(new NTM());
	}

	static {
		// TODO use config options to dynamically add mod support
		/*
        MODS.add(new IntegratedDynamics());
        MODS.add(new Mekanism());
        MODS.add(new BigReactors());
        MODS.add(new ActuallyAdditions());
        MODS.add(new EnderIO());
        MODS.add(new DraconicEvolution());
        MODS.add(new ThermalFoundation());
        MODS.add(new Forestry());
        MODS.add(new MoreBees());
        MODS.add(new ExtraBees());
        MODS.add(new MagicBees());
        MODS.add(new BinniesBotany());
		 */
		for(ISFHCoreModule module : NetherTweaksMod.LOADED_MODULES)
			if(module instanceof IRecipeDefaults)
				MODS.add((IRecipeDefaults) module);

	}

	public void registerCompost(final CompostRegistry registry) {
		MODS.stream().filter(mod -> Loader.isModLoaded(mod.getMODID()))
		.forEach(mod -> mod.registerCompost(registry));
	}

	public void registerCondenser(final CondenserRegistry registry) {
		MODS.stream().filter(mod -> Loader.isModLoaded(mod.getMODID()))
		.forEach(mod -> mod.registerCondenser(registry));
	}

	public void registerDrink(final DrinkRegistry registry) {
		MODS.stream().filter(mod -> Loader.isModLoaded(mod.getMODID()))
		.forEach(mod -> mod.registerDrink(registry));
	}

	public void registerHellmart(final HellmartRegistry registry) {
		MODS.stream().filter(mod -> Loader.isModLoaded(mod.getMODID()))
		.forEach(mod -> mod.registerHellmart(registry));
	}

	public void registerSieve(final SieveRegistry registry) {
		MODS.stream().filter(mod -> Loader.isModLoaded(mod.getMODID()))
		.forEach(mod -> mod.registerSieve(registry));
	}

	public void registerHammer(final HammerRegistry registry) {
		MODS.stream().filter(mod -> Loader.isModLoaded(mod.getMODID()))
		.forEach(mod -> mod.registerHammer(registry));
	}

	public void registerHeat(final HeatRegistry registry) {
		MODS.stream().filter(mod -> Loader.isModLoaded(mod.getMODID()))
		.forEach(mod -> mod.registerHeat(registry));
	}

	public void registerBarrel(final BarrelLiquidBlacklistRegistry registry) {
		MODS.stream().filter(mod -> Loader.isModLoaded(mod.getMODID()))
		.forEach(mod -> mod.registerBarrelLiquidBlacklist(registry));
	}

	public void registerFluidOnTop(final FluidOnTopRegistry registry) {
		MODS.stream().filter(mod -> Loader.isModLoaded(mod.getMODID()))
		.forEach(mod -> mod.registerFluidOnTop(registry));
	}

	public void registerOreChunks(final OreRegistry registry) {
		MODS.stream().filter(mod -> Loader.isModLoaded(mod.getMODID()))
		.forEach(mod -> mod.registerOreChunks(registry));
	}

	public void registerFluidTransform(final FluidTransformRegistry registry) {
		MODS.stream().filter(mod -> Loader.isModLoaded(mod.getMODID()))
		.forEach(mod -> mod.registerFluidTransform(registry));
	}

	public void registerFluidBlockTransform(final FluidBlockTransformerRegistry registry) {
		MODS.stream().filter(mod -> Loader.isModLoaded(mod.getMODID()))
		.forEach(mod -> mod.registerFluidBlockTransform(registry));
	}

	public void registerFluidItemFluid(final FluidItemFluidRegistry registry) {
		MODS.stream().filter(mod -> Loader.isModLoaded(mod.getMODID()))
		.forEach(mod -> mod.registerFluidItemFluid(registry));
	}

	public void registerCrucibleStone(final CrucibleRegistry registry) {
		MODS.stream().filter(mod -> Loader.isModLoaded(mod.getMODID()))
		.forEach(mod -> mod.registerCrucibleStone(registry));
	}

	public void registerMilk(final MilkEntityRegistry registry) {
		MODS.stream().filter(mod -> Loader.isModLoaded(mod.getMODID()))
		.forEach(mod -> mod.registerMilk(registry));
	}
}
