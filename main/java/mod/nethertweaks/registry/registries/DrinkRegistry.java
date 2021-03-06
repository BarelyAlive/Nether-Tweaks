package mod.nethertweaks.registry.registries;

import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Nonnull;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import mod.nethertweaks.api.IDrinkRegistry;
import mod.nethertweaks.json.CustomDrinkableJson;
import mod.nethertweaks.json.CustomIngredientJson;
import mod.nethertweaks.registry.ingredient.IngredientUtil;
import mod.nethertweaks.registry.ingredient.OreIngredientStoring;
import mod.nethertweaks.registry.manager.NTMRegistryManager;
import mod.nethertweaks.registry.registries.base.BaseRegistryMap;
import mod.nethertweaks.registry.registries.base.types.Drinkable;
import mod.sfhcore.json.CustomItemInfoJson;
import mod.sfhcore.util.ItemInfo;
import mod.sfhcore.util.LogUtil;
import mod.sfhcore.util.StackInfo;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class DrinkRegistry extends BaseRegistryMap<Ingredient, Drinkable> implements IDrinkRegistry
{
	protected final Map<Ingredient, Drinkable> drinkRegistry = new HashMap<>();

	public DrinkRegistry() {
		super(
				new GsonBuilder()
				.setPrettyPrinting()
				.registerTypeAdapter(Ingredient.class, new CustomIngredientJson())
				.registerTypeAdapter(OreIngredientStoring.class, new CustomIngredientJson())
				.registerTypeAdapter(Drinkable.class, new CustomDrinkableJson())
				.registerTypeAdapter(ItemInfo.class, new CustomItemInfoJson())
				.enableComplexMapKeySerialization()
				.create(),
				new TypeToken<Map<Ingredient, Drinkable>>() {
				}.getType(),
				NTMRegistryManager.DRINK_DEFAULT_REGISTRY_PROVIDERS
				);
	}

	@Override
	public void registerEntriesFromJSON(final FileReader fr)
	{
		final Map<String, Drinkable> gsonInput = gson.fromJson(fr, new TypeToken<Map<String, Drinkable>>() {
		}.getType());

		for (final Map.Entry<String, Drinkable> entry : gsonInput.entrySet()) {
			final Ingredient ingr = IngredientUtil.parseFromString(entry.getKey());

			if (registry.keySet().stream().anyMatch(ingredient -> IngredientUtil.ingredientEquals(ingredient, ingr)))
				LogUtil.error("Drinkable JSON Entry for " + entry.getKey() + " already exists, skipping.");
			else
				register(ingr, entry.getValue());
		}
	}

	@Override
	public Map<Ingredient, Drinkable> getRegistry() {
		//noinspection unchecked
		final Map<Ingredient, Drinkable> map = (HashMap) ((HashMap) registry).clone();
		map.putAll(drinkRegistry);
		return map;
	}

	@Override
	public List<?> getRecipeList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void register(@Nonnull final ItemStack itemStack, final int thirstReplenish, final float saturationReplenish, final float poisonChance) {
		if (itemStack.isEmpty())
			return;

		final Ingredient ingredient = Ingredient.fromStacks(itemStack);

		if (registry.keySet().stream().anyMatch(entry -> entry.test(itemStack))) {
			LogUtil.error("Drinkable Entry for " + itemStack.getItem().getRegistryName() + " with meta " + itemStack.getMetadata() + " already exists, skipping.");
			return;
		}
		final Drinkable dryable = new Drinkable(itemStack, thirstReplenish, saturationReplenish, poisonChance);
		register(ingredient, dryable);
	}

	@Override
	public void register(@Nonnull final Item item, final int meta, final int thirstReplenish, final float saturationReplenish, final float poisonChance) {
		register(new ItemStack(item, 1, meta), thirstReplenish, saturationReplenish,  poisonChance);
	}

	@Override
	public void register(@Nonnull final StackInfo item, final int thirstReplenish, final float saturationReplenish, final float poisonChance) {
		register(item.getItemStack(), thirstReplenish, saturationReplenish,  poisonChance);
	}

	@Override
	public void register(@Nonnull final ResourceLocation location, final int meta, final int thirstReplenish, final float saturationReplenish, final float poisonChance) {
		register(Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(location)), meta, thirstReplenish, saturationReplenish,  poisonChance);
	}

	@Override
	public void register(@Nonnull final String name, final int thirstReplenish, final float saturationReplenish, final float poisonChance) {
		final Ingredient ingredient = new OreIngredientStoring(name);

		if (drinkRegistry.keySet().stream().anyMatch(entry -> IngredientUtil.ingredientEquals(entry, ingredient)))
			LogUtil.error("Drink Entry for " + name + " already exists, skipping.");
		else
			for(final ItemStack stack : ingredient.getMatchingStacks())
			{
				final Drinkable dryable = new Drinkable(stack, thirstReplenish, saturationReplenish, poisonChance);
				register(ingredient, dryable);
			}
	}

	@Override
	public Drinkable getItem(@Nonnull final Item item, final int meta) {
		return getItem(new ItemStack(item, meta));
	}

	@Override
	public Drinkable getItem(final ItemStack stack) {
		Ingredient ingredient = registry.keySet().stream().filter(entry -> entry.test(stack)).findFirst().orElse(null);
		if (ingredient != null) return registry.get(ingredient);
		ingredient = drinkRegistry.keySet().stream().filter(entry -> entry.test(stack)).findFirst().orElse(null);
		if (ingredient != null) return drinkRegistry.get(ingredient);
		else return Drinkable.getEMPTY();
	}

	@Override
	public Drinkable getItem(@Nonnull final StackInfo info) {
		return getItem(info.getItemStack());
	}

	@Override
	public boolean containsItem(@Nonnull final Item item, final int meta) {
		return containsItem(new ItemStack(item, 1, meta));
	}

	@Override
	public boolean containsItem(@Nonnull final ItemStack stack) {
		return registry.keySet().stream().anyMatch(entry -> entry.test(stack)) || drinkRegistry.keySet().stream().anyMatch(entry -> entry.test(stack));
	}

	@Override
	public boolean containsItem(@Nonnull final StackInfo info) {
		return containsItem(info.getItemStack());
	}
}