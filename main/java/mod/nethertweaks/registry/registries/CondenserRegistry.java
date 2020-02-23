package mod.nethertweaks.registry.registries;

import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import mod.nethertweaks.api.ICondenserRegistry;
import mod.nethertweaks.json.CustomDryableJson;
import mod.nethertweaks.json.CustomIngredientJson;
import mod.nethertweaks.registry.ingredient.IngredientUtil;
import mod.nethertweaks.registry.ingredient.OreIngredientStoring;
import mod.nethertweaks.registry.manager.NTMRegistryManager;
import mod.nethertweaks.registry.registries.base.BaseRegistryMap;
import mod.nethertweaks.registry.registries.base.types.Dryable;
import mod.sfhcore.json.CustomBlockInfoJson;
import mod.sfhcore.json.CustomItemInfoJson;
import mod.sfhcore.util.BlockInfo;
import mod.sfhcore.util.ItemInfo;
import mod.sfhcore.util.LogUtil;
import mod.sfhcore.util.StackInfo;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class CondenserRegistry extends BaseRegistryMap<Ingredient, Dryable> implements ICondenserRegistry
{
	protected final Map<Ingredient, Dryable> dryRegistry = new HashMap<>();

	public CondenserRegistry() {
		super(
				new GsonBuilder()
				.setPrettyPrinting()
				.registerTypeAdapter(Ingredient.class, new CustomIngredientJson())
				.registerTypeAdapter(OreIngredientStoring.class, new CustomIngredientJson())
				.registerTypeAdapter(Dryable.class, new CustomDryableJson())
				.registerTypeAdapter(ItemInfo.class, new CustomItemInfoJson())
				.registerTypeAdapter(BlockInfo.class, new CustomBlockInfoJson())
				.enableComplexMapKeySerialization()
				.create(),
				new TypeToken<Map<Ingredient, Dryable>>() {
				}.getType(),
				NTMRegistryManager.CONDENSER_DEFAULT_REGISTRY_PROVIDERS
				);
	}

	@Override
	public void registerEntriesFromJSON(final FileReader fr)
	{
		final Map<String, Dryable> gsonInput = gson.fromJson(fr, new TypeToken<Map<String, Dryable>>() {
		}.getType());

		for (final Map.Entry<String, Dryable> entry : gsonInput.entrySet()) {
			final Ingredient ingr = IngredientUtil.parseFromString(entry.getKey());

			if (registry.keySet().stream().anyMatch(ingredient -> IngredientUtil.ingredientEquals(ingredient, ingr)))
				LogUtil.error("Dryable JSON Entry for " + entry.getKey() + " already exists, skipping.");
			else
				register(ingr, entry.getValue());
		}
	}

	@Override
	public Map<Ingredient, Dryable> getRegistry() {
		//noinspection unchecked
		final Map<Ingredient, Dryable> map = (HashMap) ((HashMap) registry).clone();
		map.putAll(dryRegistry);
		return map;
	}

	@Override
	public List<?> getRecipeList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void register(@Nonnull final ItemStack itemStack, final int value) {
		if (itemStack.isEmpty())
			return;

		final Ingredient ingredient = Ingredient.fromStacks(itemStack);

		if (registry.keySet().stream().anyMatch(entry -> entry.test(itemStack))) {
			LogUtil.error("Dry Entry for " + itemStack.getItem().getRegistryName() + " with meta " + itemStack.getMetadata() + " already exists, skipping.");
			return;
		}
		final Dryable dryable = new Dryable(itemStack, value);
		register(ingredient, dryable);
	}

	@Override
	public void register(@Nonnull final BlockInfo info, final int value) {
		register(info.getItemStack(), value);
	}

	@Override
	public void register(@Nonnull final Item item, final int meta, final int value) {
		register(new ItemStack(item, 1, meta), value);
	}

	@Override
	public void register(@Nonnull final StackInfo item, final int value) {
		register(item.getItemStack(), value);
	}

	@Override
	public void register(@Nonnull final ResourceLocation location, final int meta, final int value) {
		register(ForgeRegistries.ITEMS.getValue(location), meta, value);
	}

	@Override
	public void register(@Nonnull final String name, final int value) {
		final Ingredient ingredient = new OreIngredientStoring(name);

		if (dryRegistry.keySet().stream().anyMatch(entry -> IngredientUtil.ingredientEquals(entry, ingredient)))
			LogUtil.error("Dry Ore Entry for " + name + " already exists, skipping.");
		else
			for(final ItemStack stack : ingredient.getMatchingStacks())
			{
				final Dryable dryable = new Dryable(stack, value);
				register(ingredient, dryable);
			}
	}

	@Override
	public Dryable getItem(@Nonnull final Item item, final int meta) {
		return getItem(new ItemStack(item, meta));
	}

	@Override
	public Dryable getItem(final ItemStack stack) {
		Ingredient ingredient = registry.keySet().stream().filter(entry -> entry.test(stack)).findFirst().orElse(null);
		if (ingredient != null) return registry.get(ingredient);
		ingredient = dryRegistry.keySet().stream().filter(entry -> entry.test(stack)).findFirst().orElse(null);
		if (ingredient != null) return dryRegistry.get(ingredient);
		else return Dryable.getEMPTY();
	}

	@Override
	public Dryable getItem(@Nonnull final StackInfo info) {
		return getItem(info.getItemStack());
	}

	@Override
	public boolean containsItem(@Nonnull final Item item, final int meta) {
		return containsItem(new ItemStack(item, 1, meta));
	}

	@Override
	public boolean containsItem(@Nonnull final ItemStack stack) {
		return registry.keySet().stream().anyMatch(entry -> entry.test(stack)) || dryRegistry.keySet().stream().anyMatch(entry -> entry.test(stack));
	}

	@Override
	public boolean containsItem(@Nonnull final StackInfo info) {
		return containsItem(info.getItemStack());
	}
}