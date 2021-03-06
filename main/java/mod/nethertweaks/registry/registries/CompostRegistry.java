package mod.nethertweaks.registry.registries;

import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import mod.nethertweaks.api.ICompostRegistry;
import mod.nethertweaks.json.CustomColorJson;
import mod.nethertweaks.json.CustomCompostableJson;
import mod.nethertweaks.json.CustomIngredientJson;
import mod.nethertweaks.registry.ingredient.IngredientUtil;
import mod.nethertweaks.registry.ingredient.OreIngredientStoring;
import mod.nethertweaks.registry.manager.NTMRegistryManager;
import mod.nethertweaks.registry.registries.base.BaseRegistryMap;
import mod.nethertweaks.registry.registries.base.types.Compostable;
import mod.sfhcore.json.CustomBlockInfoJson;
import mod.sfhcore.texturing.Color;
import mod.sfhcore.util.BlockInfo;
import mod.sfhcore.util.LogUtil;
import mod.sfhcore.util.StackInfo;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class CompostRegistry extends BaseRegistryMap<Ingredient, Compostable> implements ICompostRegistry
{
	protected final Map<Ingredient, Compostable> oreRegistry = new HashMap<>();

	public CompostRegistry() {
		super(
				new GsonBuilder()
				.setPrettyPrinting()
				.registerTypeAdapter(Ingredient.class, new CustomIngredientJson())
				.registerTypeAdapter(OreIngredientStoring.class, new CustomIngredientJson())
				.registerTypeAdapter(Compostable.class, new CustomCompostableJson())
				.registerTypeAdapter(Color.class, new CustomColorJson())
				.registerTypeAdapter(BlockInfo.class, new CustomBlockInfoJson())
				.enableComplexMapKeySerialization()
				.create(),
				new TypeToken<Map<Ingredient, Compostable>>() {
				}.getType(),
				NTMRegistryManager.COMPOST_DEFAULT_REGISTRY_PROVIDERS
				);
	}

	@Override
	public void register(@Nonnull final ItemStack itemStack, final float value, @Nonnull final BlockInfo state, @Nonnull final Color color) {
		if (itemStack.isEmpty())
			return;

		final Ingredient ingredient = CraftingHelper.getIngredient(itemStack);

		if (registry.keySet().stream().anyMatch(entry -> entry.test(itemStack))) {
			LogUtil.error("Compost Entry for " + itemStack.getItem().getRegistryName() + " with meta " + itemStack.getMetadata() + " already exists, skipping.");
			return;
		}
		final Compostable compostable = new Compostable(value, color, state);
		register(ingredient, compostable);
	}

	@Override
	public void register(final Item item, final int meta, final float value, @Nonnull final BlockInfo state, @Nonnull final Color color) {
		register(new ItemStack(item, 1, meta), value, state, color);
	}

	@Override
	public void register(@Nonnull final Block block, final int meta, final float value, @Nonnull final BlockInfo state, @Nonnull final Color color) {
		register(new ItemStack(block, 1, meta), value, state, color);
	}

	@Override
	public void register(@Nonnull final StackInfo item, final float value, @Nonnull final BlockInfo state, @Nonnull final Color color) {
		register(item.getItemStack(), value, state, color);
	}

	@Override
	public void register(@Nonnull final ResourceLocation location, final int meta, final float value, @Nonnull final BlockInfo state, @Nonnull final Color color) {
		register(ForgeRegistries.ITEMS.getValue(location), meta, value, state, color);
	}

	@Override
	public void register(@Nonnull final String name, final float value, @Nonnull final BlockInfo state, @Nonnull final Color color) {
		final Ingredient ingredient = new OreIngredientStoring(name);
		final Compostable compostable = new Compostable(value, color, state);

		if (oreRegistry.keySet().stream().anyMatch(entry -> IngredientUtil.ingredientEquals(entry, ingredient)))
			LogUtil.error("Compost Ore Entry for " + name + " already exists, skipping.");
		else
			register(ingredient, compostable);
	}

	/**
	 * Registers a oredict for sifting with a dynamic color based on the itemColor
	 */
	@Override
	public void register(@Nonnull final String name, final float value, @Nonnull final BlockInfo state) {
		register(name, value, state, Color.INVALID_COLOR);
	}

	@Override
	@Nonnull
	public Compostable getItem(@Nonnull final Item item, final int meta) {
		return getItem(new ItemStack(item, meta));
	}

	@Override
	@Nonnull
	public Compostable getItem(@Nonnull final ItemStack stack) {
		Ingredient ingredient = registry.keySet().stream().filter(entry -> entry.test(stack)).findFirst().orElse(null);
		if (ingredient != null) return registry.get(ingredient);
		ingredient = oreRegistry.keySet().stream().filter(entry -> entry.test(stack)).findFirst().orElse(null);
		if (ingredient != null) return oreRegistry.get(ingredient);
		else return Compostable.getEMPTY();
	}

	@Override
	@Nonnull
	public Compostable getItem(@Nonnull final StackInfo info) {
		return getItem(info.getItemStack());
	}

	@Override
	public boolean containsItem(@Nonnull final Item item, final int meta) {
		return containsItem(new ItemStack(item, meta));
	}

	@Override
	public boolean containsItem(@Nonnull final ItemStack stack) {
		return registry.keySet().stream().anyMatch(entry -> entry.test(stack)) || oreRegistry.keySet().stream().anyMatch(entry -> entry.test(stack));
	}

	@Override
	public boolean containsItem(@Nonnull final StackInfo info) {
		return containsItem(info.getItemStack());
	}

	@Override
	public void registerEntriesFromJSON(final FileReader fr) {
		final Map<String, Compostable> gsonInput = gson.fromJson(fr, new TypeToken<Map<String, Compostable>>() {
		}.getType());

		for (final Map.Entry<String, Compostable> entry : gsonInput.entrySet()) {
			final Ingredient ingr = IngredientUtil.parseFromString(entry.getKey());

			if (registry.keySet().stream().anyMatch(ingredient -> IngredientUtil.ingredientEquals(ingredient, ingr)))
				LogUtil.error("Compost JSON Entry for " + entry.getKey() + " already exists, skipping.");
			else
				register(ingr, entry.getValue());
		}
	}

	@Override
	public Map<Ingredient, Compostable> getRegistry() {
		//noinspection unchecked
		final Map<Ingredient, Compostable> map = (HashMap) ((HashMap) registry).clone();
		map.putAll(oreRegistry);
		return map;
	}

	@Override
	public List<?> getRecipeList() {
		// TODO Auto-generated method stub
		return null;
	}
}