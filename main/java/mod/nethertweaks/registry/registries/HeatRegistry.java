package mod.nethertweaks.registry.registries;

import java.io.FileReader;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Nonnull;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import mod.nethertweaks.api.IHeatRegistry;
import mod.nethertweaks.compatibility.HeatSourcesRecipe;
import mod.nethertweaks.registry.manager.NTMRegistryManager;
import mod.nethertweaks.registry.registries.base.BaseRegistryMap;
import mod.sfhcore.util.BlockInfo;
import mod.sfhcore.util.LogUtil;
import net.minecraft.item.ItemStack;

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

	public void register(final BlockInfo info, final int heatAmount) {
		if (info == null)
			return;

		if (registry.keySet().stream().anyMatch(entry -> entry.equals(info.toString()))) {
			LogUtil.error("Heat Entry for " + info.getItemStack().getItem().getRegistryName() + " with meta " + info.getItemStack().getMetadata() + " already exists, skipping.");
			return;
		}
		registry.put(info.toString(), heatAmount);
	}

	@Override
	public void register(@Nonnull final ItemStack stack, final int heatAmount) {
		register(new BlockInfo(stack), heatAmount);
	}

	@Override
	public int getHeatAmount(@Nonnull final ItemStack stack) {
		return this.getHeatAmount(new BlockInfo(stack));
	}

	@Override
	public int getHeatAmount(@Nonnull final BlockInfo info) {
		return this.getHeatAmount(info.toString());
	}

	public int getHeatAmount(final String name) {
		final String ingredient = registry.keySet().stream().filter(entry -> entry.equals(name)).findFirst().orElse(null);
		if (ingredient != null && !Objects.equals(ingredient, "")) return registry.get(ingredient);
		return 0;
	}

	public int getMaxHeatAmount() {
		int max = 0;
		for (final Map.Entry<String, Integer> entry : registry.entrySet())
			if (max < entry.getValue())
				max = entry.getValue();
		return max;
	}

	@Override
	public void register(@Nonnull final String name, final int heatAmount) {
		if (registry.keySet().stream().anyMatch(entry -> entry.equals(name)))
			LogUtil.error("Heat Ore Entry for " + name + " already exists, skipping.");
		else
			registry.put("ore:" + name, heatAmount);
	}

	@Override
	public void registerEntriesFromJSON(final FileReader fr) {
		final Map<String, Integer> gsonInput = gson.fromJson(fr, new TypeToken<Map<String, Integer>>() {
		}.getType());

		for (final Map.Entry<String, Integer> entry : gsonInput.entrySet())
			if (registry.keySet().stream().anyMatch(ingredient -> ingredient.equals(entry.getKey())))
				LogUtil.error("Heat JSON Entry for " + entry.getKey() + " already exists, skipping.");
			else
				register(entry.getKey(), entry.getValue());
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