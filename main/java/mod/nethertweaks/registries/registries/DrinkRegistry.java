package mod.nethertweaks.registries.registries;

import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import mod.nethertweaks.api.IDrinkRegistry;
import mod.nethertweaks.json.CustomDrinkableJson;
import mod.nethertweaks.json.CustomIngredientJson;
import mod.nethertweaks.registries.ingredient.IngredientUtil;
import mod.nethertweaks.registries.ingredient.OreIngredientStoring;
import mod.nethertweaks.registries.manager.NTMRegistryManager;
import mod.nethertweaks.registries.registries.base.BaseRegistryMap;
import mod.nethertweaks.registry.types.Drinkable;
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
		Map<String, Drinkable> gsonInput = gson.fromJson(fr, new TypeToken<Map<String, Drinkable>>() {
		}.getType());

		for (Map.Entry<String, Drinkable> entry : gsonInput.entrySet()) {
			Ingredient ingr = IngredientUtil.parseFromString(entry.getKey());

			if (registry.keySet().stream().anyMatch(ingredient -> IngredientUtil.ingredientEquals(ingredient, ingr)))
				LogUtil.error("Drinkable JSON Entry for " + entry.getKey() + " already exists, skipping.");
						else
							register(ingr, entry.getValue());
		}
	}

	@Override
	public Map<Ingredient, Drinkable> getRegistry() {
		//noinspection unchecked
		Map<Ingredient, Drinkable> map = (HashMap) ((HashMap) registry).clone();
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

		Ingredient ingredient = Ingredient.fromStacks(itemStack);

		if (registry.keySet().stream().anyMatch(entry -> entry.test(itemStack))) {
			LogUtil.error("Dry Entry for " + itemStack.getItem().getRegistryName() + " with meta " + itemStack.getMetadata() + " already exists, skipping.");
			return;
		}
		Drinkable dryable = new Drinkable(itemStack, thirstReplenish, saturationReplenish, poisonChance);
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
		register(ForgeRegistries.ITEMS.getValue(location), meta, thirstReplenish, saturationReplenish,  poisonChance);
	}

	@Override
	public void register(@Nonnull final String name, final int thirstReplenish, final float saturationReplenish, final float poisonChance) {
		Ingredient ingredient = new OreIngredientStoring(name);

		if (drinkRegistry.keySet().stream().anyMatch(entry -> IngredientUtil.ingredientEquals(entry, ingredient)))
			LogUtil.error("Drink Entry for " + name + " already exists, skipping.");
		else
			for(ItemStack stack : ingredient.getMatchingStacks())
			{
				Drinkable dryable = new Drinkable(stack, thirstReplenish, saturationReplenish, poisonChance);
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