package mod.nethertweaks.registries.manager;

import java.util.ArrayList;
import java.util.List;

import mod.nethertweaks.recipes.defaults.NTM;
import mod.nethertweaks.recipes.defaults.IRecipeDefaults;
import mod.nethertweaks.registries.registries.BarrelLiquidBlacklistRegistry;
import mod.nethertweaks.registries.registries.CompostRegistry;
import mod.nethertweaks.registries.registries.FluidBlockTransformerRegistry;
import mod.nethertweaks.registries.registries.FluidItemFluidRegistry;
import mod.nethertweaks.registries.registries.FluidOnTopRegistry;
import mod.nethertweaks.registries.registries.FluidTransformRegistry;
import mod.nethertweaks.registries.registries.HammerRegistry;
import mod.nethertweaks.registries.registries.MilkEntityRegistry;
import mod.nethertweaks.registries.registries.SieveRegistry;
import net.minecraftforge.fml.common.Loader;

public class CompatDefaultRecipes
{
    private static final List<IRecipeDefaults> MODS = new ArrayList<>();

    static {
        // TODO use config options to dynamically add mod support
        MODS.add(new NTM());
        MODS.add(new TinkersConstruct());
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
        for(IExNihiloCreatioModule module : ExNihiloCreatio.loadedModules){
            if(module instanceof IRecipeDefaults)
                MODS.add((IRecipeDefaults) module);
        }

    }

    public void registerCompost(CompostRegistry registry) {
        MODS.stream().filter(mod -> Loader.isModLoaded(mod.getMODID()))
                .forEach(mod -> mod.registerCompost(registry));
    }

    public void registerSieve(SieveRegistry registry) {
        MODS.stream().filter(mod -> Loader.isModLoaded(mod.getMODID()))
                .forEach(mod -> mod.registerSieve(registry));
    }

    public void registerHammer(HammerRegistry registry) {
        MODS.stream().filter(mod -> Loader.isModLoaded(mod.getMODID()))
                .forEach(mod -> mod.registerHammer(registry));
    }

    public void registerHeat(HeatRegistry registry) {
        MODS.stream().filter(mod -> Loader.isModLoaded(mod.getMODID()))
                .forEach(mod -> mod.registerHeat(registry));
    }

    public void registerBarrel(BarrelLiquidBlacklistRegistry registry) {
        MODS.stream().filter(mod -> Loader.isModLoaded(mod.getMODID()))
                .forEach(mod -> mod.registerBarrelLiquidBlacklist(registry));
    }

    public void registerFluidOnTop(FluidOnTopRegistry registry) {
        MODS.stream().filter(mod -> Loader.isModLoaded(mod.getMODID()))
                .forEach(mod -> mod.registerFluidOnTop(registry));
    }

    public void registerOreChunks(OreRegistry registry) {
        MODS.stream().filter(mod -> Loader.isModLoaded(mod.getMODID()))
                .forEach(mod -> mod.registerOreChunks(registry));
    }

    public void registerFluidTransform(FluidTransformRegistry registry) {
        MODS.stream().filter(mod -> Loader.isModLoaded(mod.getMODID()))
                .forEach(mod -> mod.registerFluidTransform(registry));
    }

    public void registerFluidBlockTransform(FluidBlockTransformerRegistry registry) {
        MODS.stream().filter(mod -> Loader.isModLoaded(mod.getMODID()))
                .forEach(mod -> mod.registerFluidBlockTransform(registry));
    }

    public void registerFluidItemFluid(FluidItemFluidRegistry registry) {
        MODS.stream().filter(mod -> Loader.isModLoaded(mod.getMODID()))
                .forEach(mod -> mod.registerFluidItemFluid(registry));
    }

    public void registerCrucibleStone(CrucibleRegistry registry) {
        MODS.stream().filter(mod -> Loader.isModLoaded(mod.getMODID()))
                .forEach(mod -> mod.registerCrucibleStone(registry));
    }

    public void registerMilk(MilkEntityRegistry registry) {
        MODS.stream().filter(mod -> Loader.isModLoaded(mod.getMODID()))
                .forEach(mod -> mod.registerMilk(registry));
    }
}
