package mod.nethertweaks.handler;

import java.io.File;

import mod.nethertweaks.registries.manager.NTMRegistryManager;
import mod.nethertweaks.registries.registries.CondenserRegistry;
import mod.nethertweaks.registries.registries.HellmartRegistry;

public class JsonRecipeHandler
{
	public static void loadJasonVorhees(File f)
    {
		NTMRegistryManager.COMPOST_REGISTRY.loadJson(new File(f, "CompostRegistry.json"));
        NTMRegistryManager.SIEVE_REGISTRY.loadJson(new File(f, "SieveRegistry.json"));
        NTMRegistryManager.CONDENSER_REGISTRY.loadJson(new File(f, "CondenserRegistry.json"));
        NTMRegistryManager.HELLMART_REGISTRY.loadJson(new File(f, "HellmartRegistry.json"));
        NTMRegistryManager.HAMMER_REGISTRY.loadJson(new File(f, "HammerRegistry.json"));
        NTMRegistryManager.HEAT_REGISTRY.loadJson(new File(f, "HeatRegistry.json"));
        NTMRegistryManager.BARREL_LIQUID_BLACKLIST_REGISTRY.loadJson(new File(f, "BarrelLiquidBlacklistRegistry.json"));
        NTMRegistryManager.FLUID_ON_TOP_REGISTRY.loadJson(new File(f, "FluidOnTopRegistry.json"));
        NTMRegistryManager.FLUID_TRANSFORM_REGISTRY.loadJson(new File(f, "FluidTransformRegistry.json"));
        NTMRegistryManager.FLUID_BLOCK_TRANSFORMER_REGISTRY.loadJson(new File(f, "FluidBlockTransformerRegistry.json"));
        NTMRegistryManager.FLUID_ITEM_FLUID_REGISTRY.loadJson(new File(f, "FluidItemFluidRegistry.json"));
        NTMRegistryManager.CRUCIBLE_STONE_REGISTRY.loadJson(new File(f, "CrucibleRegistryStone.json"));
        NTMRegistryManager.MILK_ENTITY_REGISTRY.loadJson(new File(f, "MilkEntityRegistry.json"));
    }
}
