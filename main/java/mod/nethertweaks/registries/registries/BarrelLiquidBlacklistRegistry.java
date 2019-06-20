package mod.nethertweaks.registries.registries;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import com.google.common.reflect.TypeToken;
import com.google.gson.GsonBuilder;

import mod.nethertweaks.api.IBarrelLiquidBlacklistRegistry;
import mod.nethertweaks.registries.manager.NTMRegistryManager;
import mod.nethertweaks.registries.registries.base.BaseRegistryMap;
import net.minecraftforge.fluids.Fluid;

public class BarrelLiquidBlacklistRegistry extends BaseRegistryMap<Integer, List<String>> implements IBarrelLiquidBlacklistRegistry {
    public BarrelLiquidBlacklistRegistry() {
        super(new GsonBuilder()
                        .setPrettyPrinting()
                        .create(),
                new com.google.gson.reflect.TypeToken<Map<Integer, List<String>>>() {
                }.getType(),
                NTMRegistryManager.BARREL_LIQUID_BLACKLIST_DEFAULT_REGISTRY_PROVIDERS);
    }

    @Override
	@SuppressWarnings("unchecked")
    public boolean isBlacklisted(int level, @Nonnull String fluid) {
        return level < 0 || registry.getOrDefault(level, Collections.EMPTY_LIST).contains(fluid);
    }

    @Override
	public void register(int level, @Nonnull String fluid) {
        List<String> list = registry.computeIfAbsent(level, k -> new ArrayList<>());

        list.add(fluid);
    }

    public void register(int level, Fluid fluid) {
        List<String> list = registry.computeIfAbsent(level, k -> new ArrayList<>());

        list.add(fluid.getName());
    }

    @Override
    protected void registerEntriesFromJSON(FileReader fr) {
        Map<Integer, List<String>> loaded = gson.fromJson(fr, new TypeToken<Map<Integer, List<String>>>() {
        }.getType());

        loaded.forEach((integer, strings) -> {
            for (String string : strings) {
                register(integer, string);
            }
        });
    }

	@Override
	public List<?> getRecipeList() {
		// TODO Auto-generated method stub
		return null;
	}    
}
