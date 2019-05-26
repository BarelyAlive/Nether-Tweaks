package mod.nethertweaks.registries.registries;

import java.io.FileReader;
import java.util.*;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import mod.nethertweaks.api.IHeatRegistry;
import mod.nethertweaks.compatibility.HeatSourcesRecipe;
import mod.nethertweaks.json.CustomHeatJson;
import mod.nethertweaks.json.CustomIngredientJson;
import mod.nethertweaks.registries.ingredient.IngredientUtil;
import mod.nethertweaks.registries.ingredient.OreIngredientStoring;
import mod.nethertweaks.registries.manager.NTMRegistryManager;
import mod.nethertweaks.registries.registries.base.BaseRegistryMap;
import mod.nethertweaks.registry.types.Compostable;
import mod.nethertweaks.registry.types.Dryable;
import mod.nethertweaks.registry.types.Heat;
import mod.sfhcore.json.CustomBlockInfoJson;
import mod.sfhcore.util.BlockInfo;
import mod.sfhcore.util.LogUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.CraftingHelper;

public class HeatRegistry extends BaseRegistryMap<String, Integer> implements IHeatRegistry {
	
    public HeatRegistry() {
        super(
                new GsonBuilder()
                        .setPrettyPrinting()
                        /*
                        .registerTypeAdapter(Ingredient.class, new CustomIngredientJson())
                        .registerTypeAdapter(OreIngredientStoring.class, new CustomIngredientJson())
                        .registerTypeAdapter(Heat.class, new CustomHeatJson())
                        .registerTypeAdapter(BlockInfo.class, new CustomBlockInfoJson())
                        */
                        .enableComplexMapKeySerialization()
                        .create(),
                new TypeToken<Map<String, Integer>>() {
                }.getType(),
                NTMRegistryManager.HEAT_DEFAULT_REGISTRY_PROVIDERS
        );
    }

    public void register(BlockInfo info, int heatAmount) {
    	if (info == null)
    		return;

        if (registry.keySet().stream().anyMatch(entry -> entry.equals(info.toString()))) {
            LogUtil.error("Heat Entry for " + info.getItemStack().getItem().getRegistryName() + " with meta " + info.getItemStack().getMetadata() + " already exists, skipping.");
            return;
        }
        registry.put(info.toString(), heatAmount);
    }

    public void register(@Nonnull ItemStack stack, int heatAmount) {
        register(new BlockInfo(stack), heatAmount);
    }

	public int getHeatAmount(@Nonnull ItemStack stack) {
		return this.getHeatAmount(new BlockInfo(stack));
    }

    public int getHeatAmount(@Nonnull BlockInfo info) {
        return this.getHeatAmount(info.toString());
    }
    
    public int getHeatAmount(String name) {
        String ingredient = registry.keySet().stream().filter(entry -> entry.equals(name)).findFirst().orElse(null);
        if (ingredient != null && ingredient != "") return registry.get(ingredient);
    	return 0;
    }
    
    @Override
	public void register(@Nonnull String name, int heatAmount) {
        if (registry.keySet().stream().anyMatch(entry -> entry.equals(name)))
            LogUtil.error("Heat Ore Entry for " + name + " already exists, skipping.");
        else
            registry.put("ore:" + name, heatAmount);
	}

    @Override
    public void registerEntriesFromJSON(FileReader fr) {
        Map<String, Integer> gsonInput = gson.fromJson(fr, new TypeToken<Map<String, Integer>>() {
        }.getType());

        for (Map.Entry<String, Integer> entry : gsonInput.entrySet()) {
            if (registry.keySet().stream().anyMatch(ingredient -> ingredient.equals(entry.getKey())))
                LogUtil.error("Heat JSON Entry for " + entry.getKey() + " already exists, skipping.");
            else
            {
            	System.out.println(entry.getKey());
                register(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public List<HeatSourcesRecipe> getRecipeList() {
    	/*
        List<HeatSourcesRecipe> heatSources = Lists.newLinkedList();
        getRegistry().forEach((key, value) -> heatSources.add(new HeatSourcesRecipe(value.get, value.getValue())));
        return heatSources;
        */
    	return null;
    }
}