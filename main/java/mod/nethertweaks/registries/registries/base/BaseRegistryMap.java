package mod.nethertweaks.registries.registries.base;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import mod.nethertweaks.api.IRegistryMap;
import mod.nethertweaks.registries.manager.IDefaultRecipeProvider;

public abstract class BaseRegistryMap<K, V> extends BaseRegistry<Map<K, V>> implements IRegistryMap<K, V> {

	public BaseRegistryMap(final Gson gson, final Type typeOfSource, final List<? extends IDefaultRecipeProvider> defaultRecipeProviders) {
		super(gson, new HashMap<>(), typeOfSource, defaultRecipeProviders);
	}

	@Override
	public void register(final K key, final V value) {
		registry.put(key, value);
	}

	@Override
	public void clearRegistry() {
		registry.clear();
	}

}
