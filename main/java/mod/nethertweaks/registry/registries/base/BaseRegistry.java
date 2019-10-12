package mod.nethertweaks.registry.registries.base;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Nonnull;

import com.google.gson.Gson;

import mod.nethertweaks.api.IRegistry;
import mod.nethertweaks.config.Config;
import mod.nethertweaks.registry.manager.IDefaultRecipeProvider;

public abstract class BaseRegistry<RegType> implements IRegistry<RegType> {
	protected final Gson gson;
	private final List<? extends IDefaultRecipeProvider> defaultRecipeProviders;
	protected boolean hasAlreadyBeenLoaded = false;
	protected final RegType registry;
	protected final Type typeOfSource;

	@Override
	public RegType getRegistry() {
		return registry;
	}

	public Type getTypeOfSource() {
		return typeOfSource;
	}

	public BaseRegistry(final Gson gson, final RegType registry, final Type typeOfSource, @Nonnull final List<? extends IDefaultRecipeProvider> defaultRecipeProviders) {
		this.gson = gson;
		this.registry = registry;
		this.typeOfSource = typeOfSource;
		this.defaultRecipeProviders = defaultRecipeProviders;
	}

	public void saveJson(final File file) {
		try(FileWriter fw = new FileWriter(file)) {
			// TODO remove null again
			if (typeOfSource != null)
				gson.toJson(registry, typeOfSource, fw);
			else
				gson.toJson(registry, fw);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadJson(final File file) {
		if (hasAlreadyBeenLoaded) clearRegistry();

		if (file.exists() && Config.enableJSONLoading)
			try(FileReader fr = new FileReader(file)) {
				registerEntriesFromJSON(fr);

			} catch (Exception e) {
				e.printStackTrace();
			}
		else {
			registerDefaults();
			if (Config.enableJSONLoading)
				saveJson(file);
		}

		hasAlreadyBeenLoaded = true;
	}

	protected abstract void registerEntriesFromJSON(FileReader fr);

	public void registerDefaults() {
		defaultRecipeProviders.forEach(recipeProvider -> recipeProvider.registerRecipeDefaults(this));
	}

	public abstract List<?> getRecipeList();
	@Override
	public abstract void clearRegistry();
}
