package mod.nethertweaks.registry.registries;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import mod.nethertweaks.api.IDynOreRegistry;
import mod.nethertweaks.config.Config;
import mod.nethertweaks.init.OreHandler;
import mod.nethertweaks.item.ItemChunk;
import mod.nethertweaks.json.CustomIngredientJson;
import mod.nethertweaks.registry.manager.NTMRegistryManager;
import mod.nethertweaks.registry.registries.base.BaseRegistryList;
import mod.nethertweaks.registry.types.DynOre;
import mod.sfhcore.json.CustomItemInfoJson;
import mod.sfhcore.util.ItemInfo;
import net.minecraft.item.crafting.Ingredient;

public class DynOreRegistry extends BaseRegistryList<DynOre> implements IDynOreRegistry
{

    private final List<DynOre> itemOreRegistry = new ArrayList<>();

	public DynOreRegistry()
	{
		super(
	        new GsonBuilder()
	                .setPrettyPrinting()
	                .registerTypeAdapter(Ingredient.class, new CustomIngredientJson())
	                .registerTypeAdapter(ItemInfo.class, new CustomItemInfoJson())
	                .enableComplexMapKeySerialization()
	                .create(),
			NTMRegistryManager.DYN_ORE_DEFAULT_REGISTRY_PROVIDERS
		);
	}

	@Override
	protected void registerEntriesFromJSON(final FileReader fr) {
        final List<DynOre> gsonInput = gson.fromJson(fr, new TypeToken<List<DynOre>>() {
        }.getType());
        for (final DynOre ore : gsonInput)
			register(ore);
	}

	@Override
	public List<?> getRecipeList() {
		return itemOreRegistry;
	}

	@Override
	public ItemChunk getOreItem(final String name) {
		// TODO Auto-generated method stub
		/*
        for (DynOre itemOre : itemOreRegistry) {
            if (itemOre.getName().equals(name)) {
                return ((ItemChunk)itemOre.getChunk().getItem());
            }
        }
        */

		return null;
	}

	@Override
	public boolean isRegistered(final String name) {
		/*
		if (this.getOreItem(name) != null)
		{
			return true;
		}
		*/
		return false;
	}

	public DynOre register(final String id, final String name, final ItemInfo ingot) {
		// TODO Auto-generated method stub
		return this.register(id, name, ingot, 1);
	}

	public DynOre register(final String id, final String name, final ItemInfo ingot, final int rarity) {
		// TODO Auto-generated method stub
		return this.register(id, name, ingot, rarity, 0x80000000);
	}

	@Override
	public DynOre register(final String id, final String name, final ItemInfo ingot, final int rarity, final int color) {
		// TODO Auto-generated method stub
		final DynOre ore = new DynOre(id, name, ingot, rarity, color);
		register(ore);
		return ore;
	}

	@Override
	public void register(final DynOre ore)
	{
		itemOreRegistry.add(ore);
		registry.add(ore);
	}

	@Override
    public void registerDefaults() {
        OreHandler.registerDynOreChunks(this);
	}

	@Override
	public void saveJson(File file) {
		file = new File(file, "DynamicOreRegistry.json");
		registerDefaults();
		if (!file.exists())
			if (Config.enableJSONLoading)
				try(FileWriter fw = new FileWriter(file)) {
		        	// TODO remove null again
		        	if (typeOfSource != null)
						gson.toJson(registry, typeOfSource, fw);
					else
						gson.toJson(registry, fw);
		    	} catch (final Exception e) {
		        	e.printStackTrace();
		    	}
	}

    @Override
	public void loadJson(File file) {
		file = new File(file, "DynamicOreRegistry.json");
        if (hasAlreadyBeenLoaded) clearRegistry();

        if (file.exists() && Config.enableJSONLoading)
			try(FileReader fr = new FileReader(file)) {
                registerEntriesFromJSON(fr);
            } catch (final Exception e) {
                e.printStackTrace();
            }

        hasAlreadyBeenLoaded = true;

	}
}
