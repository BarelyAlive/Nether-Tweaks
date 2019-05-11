package mod.nethertweaks.registries.registries.base;

import com.google.gson.Gson;

import mod.nethertweaks.Config;
import mod.nethertweaks.api.IRegistry;
import mod.nethertweaks.registries.manager.IDefaultRecipeProvider;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.List;

public abstract class BaseRegistry<RegType> implements IRegistry<RegType> {
    protected final Gson gson;
    private final List<? extends IDefaultRecipeProvider> defaultRecipeProviders;
    protected boolean hasAlreadyBeenLoaded = false;
    protected RegType registry;
    protected Type typeOfSource;

    public RegType getRegistry() {
		return registry;
	}

	public Type getTypeOfSource() {
		return typeOfSource;
	}

	public BaseRegistry(Gson gson, RegType registry, Type typeOfSource, @Nonnull List<? extends IDefaultRecipeProvider> defaultRecipeProviders) {
        this.gson = gson;
        this.registry = registry;
        this.typeOfSource = typeOfSource;
        this.defaultRecipeProviders = defaultRecipeProviders;
    }

    public void saveJson(File file) {
        try(FileWriter fw = new FileWriter(file)) {
            // TODO remove null again
            if (typeOfSource != null) {
                gson.toJson(registry, typeOfSource, fw);
            } else {
                gson.toJson(registry, fw);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadJson(File file) {
        if (hasAlreadyBeenLoaded) clearRegistry();

        if (file.exists() && Config.enableJSONLoading) {
            try(FileReader fr = new FileReader(file)) {
                registerEntriesFromJSON(fr);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            registerDefaults();
            if (Config.enableJSONLoading) {
                saveJson(file);
            }
        }

        hasAlreadyBeenLoaded = true;
    }

    protected abstract void registerEntriesFromJSON(FileReader fr);

    public void registerDefaults() {
        defaultRecipeProviders.forEach(recipeProvider -> recipeProvider.registerRecipeDefaults(this));
    }

    public abstract List<?> getRecipeList();
    public abstract void clearRegistry();
}
