package mod.nethertweaks.registries.registries;

import java.io.FileReader;
import java.util.*;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import mod.nethertweaks.api.IHeatRegistry;
import mod.nethertweaks.compatibility.HeatSourcesRecipe;
import mod.nethertweaks.json.CustomBlockInfoJson;
import mod.nethertweaks.registries.manager.NTMRegistryManager;
import mod.nethertweaks.registries.registries.base.BaseRegistryMap;
import mod.sfhcore.util.BlockInfo;
import net.minecraft.item.ItemStack;

public class HeatRegistry extends BaseRegistryMap<BlockInfo, Integer> implements IHeatRegistry {

    public HeatRegistry() {
        super(
                new GsonBuilder()
                        .setPrettyPrinting()
                        .registerTypeAdapter(BlockInfo.class, CustomBlockInfoJson.INSTANCE)
                        .create(),
                new com.google.gson.reflect.TypeToken<Map<BlockInfo, Integer>>() {}.getType(),
                NTMRegistryManager.HEAT_DEFAULT_REGISTRY_PROVIDERS
        );
    }

    public void register(BlockInfo info, int heatAmount) {
        registry.put(info, heatAmount);
    }

    public void register(@Nonnull ItemStack stack, int heatAmount) {
        register(new BlockInfo(stack), heatAmount);
    }

    public int getHeatAmount(@Nonnull ItemStack stack) {
        return registry.get(new BlockInfo(stack));
    }

    public int getHeatAmount(@Nonnull BlockInfo info) {
        return registry.getOrDefault(info, 0);
    }

    @Override
    public void registerEntriesFromJSON(FileReader fr) {
        HashMap<String, Integer> gsonInput = gson.fromJson(fr, new TypeToken<HashMap<String, Integer>>() {
        }.getType());

        for (Map.Entry<String, Integer> entry : gsonInput.entrySet()) {
            registry.put(new BlockInfo(entry.getKey()), entry.getValue());
        }
    }

    @Override
    public List<HeatSourcesRecipe> getRecipeList() {
        List<HeatSourcesRecipe> heatSources = Lists.newLinkedList();
        getRegistry().forEach((key, value) -> heatSources.add(new HeatSourcesRecipe(key, value)));
        return heatSources;
    }
}