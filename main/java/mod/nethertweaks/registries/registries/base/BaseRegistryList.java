package mod.nethertweaks.registries.registries.base;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import mod.nethertweaks.registries.manager.IDefaultRecipeProvider;

public abstract class BaseRegistryList<V> extends BaseRegistry<List<V>> {

	protected BaseRegistryList(final Gson gson, final List<? extends IDefaultRecipeProvider> defaultRecipeProviders) {
		super(gson, new ArrayList<>(), null,  defaultRecipeProviders);
	}

	@Override
	public void clearRegistry() {
		registry.clear();
	}

	protected void register(final V value) {
		registry.add(value);
	}
}
