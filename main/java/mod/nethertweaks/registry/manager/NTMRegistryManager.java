package mod.nethertweaks.registry.manager;

import java.util.ArrayList;
import java.util.List;

import mod.nethertweaks.registry.CondenserRegistry;
import mod.nethertweaks.registry.HammerRegistry;

public class NTMRegistryManager {
	
	public static final List<ISieveDefaultRegistryProvider> SIEVE_DEFAULT_REGISTRY_PROVIDERS = new ArrayList<>();
	public static final List<IHammerDefaultRegistryProvider> HAMMER_DEFAULT_REGISTRY_PROVIDERS = new ArrayList<>();
	public static final List<IOreDefaultRegistryProvider> ORE_DEFAULT_REGISTRY_PROVIDERS = new ArrayList<>();	
	public static final List<ICondenserDefaultRegistryProvider> CONDENSER_DEFAULT_REGISTRY_PROVIDER = new ArrayList<ICondenserDefaultRegistryProvider>();
	
	public static final SieveRegistry SIEVE_REGISTRY = new SieveRegistry();
	public static final HammerRegistry HAMMER_REGISTRY = new HammerRegistry();
	public static final OreRegistry ORE_REGISTRY = new OreRegistry();
	public static final CondenserRegistry CONDENSER_REGISTRY = new CondenserRegistry();
	
	public static void registerSieveDefaultRecipeHandler(ISieveDefaultRegistryProvider provider) {
        SIEVE_DEFAULT_REGISTRY_PROVIDERS.add(provider);
    }

    public static void registerHammerDefaultRecipeHandler(IHammerDefaultRegistryProvider provider) {
        HAMMER_DEFAULT_REGISTRY_PROVIDERS.add(provider);
    }
    
    public static void registerOreDefaultRecipeHandler(IOreDefaultRegistryProvider provider) {
        ORE_DEFAULT_REGISTRY_PROVIDERS.add(provider);
    }
    
    public static void registerCondenserDefaultRecipeHandler(ICondenserDefaultRegistryProvider provider) {
    	CONDENSER_DEFAULT_REGISTRY_PROVIDER.add(provider);
    }
}