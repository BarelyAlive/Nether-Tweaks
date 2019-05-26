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

public class HeatRegistry extends BaseRegistryMap<Ingredient, Heat> implements IHeatRegistry {
	
	protected final Map<Ingredient, Heat> heatRegistry = new HashMap<>();

    public HeatRegistry() {
        super(
                new GsonBuilder()
                        .setPrettyPrinting()
                        .registerTypeAdapter(Ingredient.class, new CustomIngredientJson())
                        .registerTypeAdapter(OreIngredientStoring.class, new CustomIngredientJson())
                        .registerTypeAdapter(Heat.class, new CustomHeatJson())
                        .registerTypeAdapter(BlockInfo.class, new CustomBlockInfoJson())
                        .enableComplexMapKeySerialization()
                        .create(),
                new TypeToken<Map<Ingredient, Heat>>() {
                }.getType(),
                NTMRegistryManager.HEAT_DEFAULT_REGISTRY_PROVIDERS
        );
    }

    public void register(BlockInfo info, int heatAmount) {
    	if (info == null)
    		return;

    	if (info.getItemStack().isEmpty())
            return;

        Ingredient ingredient = CraftingHelper.getIngredient(info.getItemStack());
        
        if (registry.keySet().stream().anyMatch(entry -> entry.test(info.getItemStack()))) {
            LogUtil.error("Heat Entry for " + info.getItemStack().getItem().getRegistryName() + " with meta " + info.getItemStack().getMetadata() + " already exists, skipping.");
            return;
        }
        Heat heat = new Heat(info, heatAmount);
        register(ingredient, heat);
    }

    public void register(@Nonnull ItemStack stack, int heatAmount) {
        register(new BlockInfo(stack), heatAmount);
    }

	public int getHeatAmount(@Nonnull ItemStack stack) {
        Ingredient ingredient = registry.keySet().stream().filter(entry -> entry.test(stack)).findFirst().orElse(null);
        if (ingredient != null) return registry.get(ingredient).getValue();
        ingredient = heatRegistry.keySet().stream().filter(entry -> entry.test(stack)).findFirst().orElse(null);
        if (ingredient != null) return heatRegistry.get(ingredient).getValue();
        else return Heat.getEMPTY().getValue();
    }

    public int getHeatAmount(@Nonnull BlockInfo info) {
        return this.getHeatAmount(info.getItemStack());
    }
    
    @Override
	public void register(@Nonnull String name, int heatAmount) {
        Ingredient ingredient = new OreIngredientStoring(name);
        
        if (heatRegistry.keySet().stream().anyMatch(entry -> IngredientUtil.ingredientEquals(entry, ingredient)))
            LogUtil.error("Heat Ore Entry for " + name + " already exists, skipping.");
        else
        	for(ItemStack stack : ingredient.getMatchingStacks())
        	{
        		Heat compostable = new Heat(new BlockInfo(stack), heatAmount);
            	register(ingredient, compostable);
        	}
	}

    @Override
    public void registerEntriesFromJSON(FileReader fr) {
        Map<String, Heat> gsonInput = gson.fromJson(fr, new TypeToken<Map<String, Heat>>() {
        }.getType());

        for (Map.Entry<String, Heat> entry : gsonInput.entrySet()) {
            Ingredient ingr = IngredientUtil.parseFromString(entry.getKey());

            if (registry.keySet().stream().anyMatch(ingredient -> IngredientUtil.ingredientEquals(ingredient, ingr)))
                LogUtil.error("Heat JSON Entry for " + entry.getKey() + " already exists, skipping.");
            else
            {
                register(ingr, entry.getValue());
            }
        }
    }

    @Override
    public List<HeatSourcesRecipe> getRecipeList() {
        List<HeatSourcesRecipe> heatSources = Lists.newLinkedList();
        getRegistry().forEach((key, value) -> heatSources.add(new HeatSourcesRecipe(value.getItem(), value.getValue())));
        return heatSources;
    }
}