package mod.nethertweaks.registry.manager;

import java.util.ArrayList;

public class RegistryManager {
		
	private static ArrayList<IHammerDefaultRegistryProvider> defaultHammerRecipeHandlers = new ArrayList<IHammerDefaultRegistryProvider>();
	private static ArrayList<ICondenserDefaultRegistryProvider> defaultCondenserRecipeHandlers = new ArrayList<ICondenserDefaultRegistryProvider>();
	
	public static ArrayList<IHammerDefaultRegistryProvider> getDefaultHammerRecipeHandlers()
	{
		return defaultHammerRecipeHandlers;
	}
	
	public static ArrayList<ICondenserDefaultRegistryProvider> getDefaultCondenserRecipeHandlers()
	{
		return defaultCondenserRecipeHandlers;
	}
	
	public static void registerHammerDefaultRecipeHandler(IHammerDefaultRegistryProvider provider) {
		defaultHammerRecipeHandlers.add(provider);
	}
	
	public static void registerCondenserDefaultRecipeHandler(ICondenserDefaultRegistryProvider provider) {
		defaultCondenserRecipeHandlers.add(provider);
	}
}