package mod.nethertweaks.registries.registries;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;

import mod.nethertweaks.barrel.IBarrelMode;
import mod.nethertweaks.barrel.modes.block.BarrelModeBlock;
import mod.nethertweaks.barrel.modes.compost.BarrelModeCompost;
import mod.nethertweaks.barrel.modes.fluid.BarrelModeFluid;
import mod.nethertweaks.barrel.modes.fluid.BarrelModeFluidTransform;
import mod.nethertweaks.barrel.modes.mobspawn.BarrelModeMobSpawn;

public class BarrelModeRegistry {

	public enum TriggerType
	{
		ITEM, FLUID, TICK, NONE
	}

	private static EnumMap<TriggerType, ArrayList<IBarrelMode>> barrelModes = new EnumMap<>(TriggerType.class);

	private static HashMap<String, IBarrelMode> barrelModeNames = new HashMap<>();

	public static void registerBarrelMode(final IBarrelMode mode, final TriggerType type)
	{
		ArrayList<IBarrelMode> list = barrelModes.get(type);
		if (list == null)
			list = new ArrayList<>();

		list.add(mode);
		barrelModes.put(type, list);

		barrelModeNames.put(mode.getName(), mode);
	}

	public static ArrayList<IBarrelMode> getModes(final TriggerType type)
	{
		return barrelModes.get(type);
	}

	public static void registerDefaults()
	{
		registerBarrelMode(new BarrelModeCompost(), TriggerType.ITEM);
		registerBarrelMode(new BarrelModeFluid(), TriggerType.FLUID);
		registerBarrelMode(new BarrelModeBlock(), TriggerType.NONE);
		registerBarrelMode(new BarrelModeFluidTransform(), TriggerType.NONE);
		registerBarrelMode(new BarrelModeMobSpawn(), TriggerType.NONE);
	}

	public static IBarrelMode getModeByName(final String name)
	{
		return barrelModeNames.get(name);
	}

}