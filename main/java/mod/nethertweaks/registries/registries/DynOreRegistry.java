package mod.nethertweaks.registries.registries;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import mod.nethertweaks.api.IDynOreRegistry;
import mod.nethertweaks.config.Config;
import mod.nethertweaks.handler.OreHandler;
import mod.nethertweaks.items.ItemChunk;
import mod.nethertweaks.items.ItemOre;
import mod.nethertweaks.json.CustomColorJson;
import mod.nethertweaks.json.CustomCompostableJson;
import mod.nethertweaks.json.CustomIngredientJson;
import mod.nethertweaks.registries.ingredient.OreIngredientStoring;
import mod.nethertweaks.registries.manager.IDefaultRecipeProvider;
import mod.nethertweaks.registries.manager.NTMRegistryManager;
import mod.nethertweaks.registries.registries.base.BaseRegistryList;
import mod.nethertweaks.registry.types.Compostable;
import mod.nethertweaks.registry.types.DynOre;
import mod.nethertweaks.registry.types.Ore;
import mod.sfhcore.json.CustomBlockInfoJson;
import mod.sfhcore.json.CustomItemInfoJson;
import mod.sfhcore.texturing.Color;
import mod.sfhcore.util.BlockInfo;
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
	protected void registerEntriesFromJSON(FileReader fr) {
        List<DynOre> gsonInput = gson.fromJson(fr, new TypeToken<List<DynOre>>() {
        }.getType());
        for (DynOre ore : gsonInput) {
            register(ore);
        }
	}

	@Override
	public List<?> getRecipeList() {
		return this.itemOreRegistry;
	}

	@Override
	public ItemChunk getOreItem(String name) {
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
	public boolean isRegistered(String name) {
		/*
		if (this.getOreItem(name) != null)
		{
			return true;
		}
		*/
		return false;
	}

	public DynOre register(String id, String name, ItemInfo ingot) {
		// TODO Auto-generated method stub
		return this.register(id, name, ingot, 1);
	}
	
	public DynOre register(String id, String name, ItemInfo ingot, int rarity) {
		// TODO Auto-generated method stub
		return this.register(id, name, ingot, rarity, 0x80000000);
	}
	
	@Override
	public DynOre register(String id, String name, ItemInfo ingot, int rarity, int color) {
		// TODO Auto-generated method stub
		DynOre ore = new DynOre(id, name, ingot, rarity, color);
		register(ore);
		return ore;
	}
	
	public void register(DynOre ore)
	{
		this.itemOreRegistry.add(ore);
		this.registry.add(ore);
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
		{
        	if (Config.enableJSONLoading) {
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
		}
	}

    @Override
	public void loadJson(File file) {
		file = new File(file, "DynamicOreRegistry.json");
        if (hasAlreadyBeenLoaded) clearRegistry();

        if (file.exists() && Config.enableJSONLoading) {
            try(FileReader fr = new FileReader(file)) {
                registerEntriesFromJSON(fr);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        hasAlreadyBeenLoaded = true;

	}
}
