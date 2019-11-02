package mod.nethertweaks.registry.manager;

import java.util.ArrayList;
import java.util.List;

import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.init.OreHandler;
import mod.nethertweaks.recipes.defaults.IRecipeDefaults;
import mod.nethertweaks.recipes.defaults.NTM;
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
import mod.sfhcore.modules.ISFHCoreModule;
import net.minecraftforge.fml.common.Loader;

public class CompatDefaultRecipes
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
		for(final ISFHCoreModule module : NetherTweaksMod.LOADED_MODULES)
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

    public void registerDynOreChunks(final DynOreRegistry registry) {
    	OreHandler.registerDynOreChunks(registry);
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
