package mod.nethertweaks.registry.manager;

import java.util.ArrayList;
import java.util.List;

import mod.nethertweaks.registry.CondenserRegistry;
import mod.nethertweaks.registry.HammerRegistry;

public class NTMRegistryManager {
	
	private static ArrayList<ISieveDefaultRegistryProvider> defaultSieveRecipeHandlers = new ArrayList<ISieveDefaultRegistryProvider>();
	private static ArrayList<IHammerDefaultRegistryProvider> defaultHammerRecipeHandlers = new ArrayList<IHammerDefaultRegistryProvider>();
	private static ArrayList<ICompostDefaultRegistryProvider> defaultCompostRecipeHandlers = new ArrayList<ICompostDefaultRegistryProvider>();
	private static ArrayList<IFluidBlockDefaultRegistryProvider> defaultFluidBlockRecipeHandlers = new ArrayList<IFluidBlockDefaultRegistryProvider>();
	private static ArrayList<IFluidTransformDefaultRegistryProvider> defaultFluidTransformRecipeHandlers = new ArrayList<IFluidTransformDefaultRegistryProvider>();
	private static ArrayList<IFluidOnTopDefaultRegistryProvider> defaultFluidOnTopRecipeHandlers = new ArrayList<IFluidOnTopDefaultRegistryProvider>();
	private static ArrayList<IOreDefaultRegistryProvider> defaultOreRecipeHandlers = new ArrayList<IOreDefaultRegistryProvider>();
	private static ArrayList<ICondenserDefaultRegistryProvider> defaultCondenserRecipeHandlers = new ArrayList<ICondenserDefaultRegistryProvider>();
	
	public static ArrayList<ICondenserDefaultRegistryProvider> getDefaultCondenserRecipeHandlers() {
		return defaultCondenserRecipeHandlers;
	}

	public static ArrayList<ISieveDefaultRegistryProvider> getDefaultSieveRecipeHandlers() {
		return defaultSieveRecipeHandlers;
	}

	public static ArrayList<IHammerDefaultRegistryProvider> getDefaultHammerRecipeHandlers() {
		return defaultHammerRecipeHandlers;
	}

	public static ArrayList<ICompostDefaultRegistryProvider> getDefaultCompostRecipeHandlers() {
		return defaultCompostRecipeHandlers;
	}

	public static ArrayList<IFluidBlockDefaultRegistryProvider> getDefaultFluidBlockRecipeHandlers() {
		return defaultFluidBlockRecipeHandlers;
	}

	public static ArrayList<IFluidTransformDefaultRegistryProvider> getDefaultFluidTransformRecipeHandlers() {
		return defaultFluidTransformRecipeHandlers;
	}

	public static ArrayList<IFluidOnTopDefaultRegistryProvider> getDefaultFluidOnTopRecipeHandlers() {
		return defaultFluidOnTopRecipeHandlers;
	}

	public static ArrayList<IOreDefaultRegistryProvider> getDefaultOreRecipeHandlers() {
		return defaultOreRecipeHandlers;
	}

	public static void registerSieveDefaultRecipeHandler(ISieveDefaultRegistryProvider provider) {
		defaultSieveRecipeHandlers.add(provider);
	}
	
	public static void registerHammerDefaultRecipeHandler(IHammerDefaultRegistryProvider provider) {
		defaultHammerRecipeHandlers.add(provider);
	}
	
	public static void registerCompostDefaultRecipeHandler(ICompostDefaultRegistryProvider provider) {
		defaultCompostRecipeHandlers.add(provider);
	}
	
	public static void registerFluidBlockDefaultRecipeHandler(IFluidBlockDefaultRegistryProvider provider) {
		defaultFluidBlockRecipeHandlers.add(provider);
	}
	
	public static void registerFluidTransformDefaultRecipeHandler(IFluidTransformDefaultRegistryProvider provider) {
		defaultFluidTransformRecipeHandlers.add(provider);
	}

	public static void registerFluidOnTopDefaultRecipeHandler(IFluidOnTopDefaultRegistryProvider provider) {
		defaultFluidOnTopRecipeHandlers.add(provider);
	}
	
	public static void registerOreDefaultRecipeHandler(IOreDefaultRegistryProvider provider) {
		defaultOreRecipeHandlers.add(provider);
	}
	
	public static void registerCondenserDefaultRecipeHandler(ICondenserDefaultRegistryProvider provider) {
		defaultCondenserRecipeHandlers.add(provider);
	}
}