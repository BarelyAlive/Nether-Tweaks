package mod.nethertweaks.registry.manager;

import java.util.ArrayList;

public class RegistryManager {
		
	private static ArrayList<IHammerDefaultRegistryProvider> defaultHammerRecipeHandlers = new ArrayList<IHammerDefaultRegistryProvider>();
	private static ArrayList<ICondenserDefaultRegistryProvider> defaultCondenserRecipeHandlers = new ArrayList<ICondenserDefaultRegistryProvider>();
	private static ArrayList<ISieveDefaultRegistryProvider> defaultSieveRecipeHandlers = new ArrayList<ISieveDefaultRegistryProvider>();
	private static ArrayList<IOreDefaultRegistryProvider> defaultOreRecipeHandlers = new ArrayList<IOreDefaultRegistryProvider>();
	
	public static ArrayList<IHammerDefaultRegistryProvider> getDefaultHammerRecipeHandlers()
	{
		return defaultHammerRecipeHandlers;
	}
	
	public static ArrayList<ICondenserDefaultRegistryProvider> getDefaultCondenserRecipeHandlers()
	{
		return defaultCondenserRecipeHandlers;
	}
	
	public static ArrayList<ISieveDefaultRegistryProvider> getDefaultSieveRecipeHandlers()
	{
		return defaultSieveRecipeHandlers;
	}
	
	public static ArrayList<IOreDefaultRegistryProvider> getDefaultOreRecipeHandlers()
	{
		return defaultOreRecipeHandlers;
	}
	
	public static void registerHammerDefaultRecipeHandler(IHammerDefaultRegistryProvider provider) {
		defaultHammerRecipeHandlers.add(provider);
	}
	
	public static void registerCondenserDefaultRecipeHandler(ICondenserDefaultRegistryProvider provider) {
		defaultCondenserRecipeHandlers.add(provider);
	}
	
	public static void registerSieveDefaultRecipeHandler(ISieveDefaultRegistryProvider provider) {
		defaultSieveRecipeHandlers.add(provider);
	}
	
	public static void registerOreDefaultRecipeHandler(IOreDefaultRegistryProvider provider) {
		defaultOreRecipeHandlers.add(provider);
	}
}