package mod.nethertweaks.registry.registries;

import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Nonnull;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import mod.nethertweaks.api.IHellmartRegistry;
import mod.nethertweaks.json.CustomHellmartDataJson;
import mod.nethertweaks.json.CustomIngredientJson;
import mod.nethertweaks.registry.ingredient.IngredientUtil;
import mod.nethertweaks.registry.ingredient.OreIngredientStoring;
import mod.nethertweaks.registry.manager.NTMRegistryManager;
import mod.nethertweaks.registry.registries.base.BaseRegistryMap;
import mod.nethertweaks.registry.registries.base.types.HellmartData;
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

public class HellmartRegistry extends BaseRegistryMap<Ingredient, HellmartData> implements IHellmartRegistry
{
	protected final Map<Ingredient, HellmartData> buyRegistry = new HashMap<>();

	public HellmartRegistry() {
		super(
				new GsonBuilder()
				.setPrettyPrinting()
				.registerTypeAdapter(ItemInfo.class, new CustomItemInfoJson())
				.registerTypeAdapter(StackInfo.class, new CustomItemInfoJson())
				.registerTypeAdapter(BlockInfo.class, new CustomBlockInfoJson())
				.registerTypeAdapter(Ingredient.class, new CustomIngredientJson())
				.registerTypeAdapter(OreIngredientStoring.class, new CustomIngredientJson())
				.registerTypeAdapter(HellmartData.class, new CustomHellmartDataJson())
				.enableComplexMapKeySerialization()
				.create(),
				new TypeToken<Map<Ingredient, HellmartData>>() {
				}.getType(),
				NTMRegistryManager.HELLMART_DEFAULT_REGISTRY_PROVIDERS
				);
	}

	@Override
	public void registerEntriesFromJSON(final FileReader fr)
	{
		final Map<String, HellmartData> gsonInput = gson.fromJson(fr, new TypeToken<Map<String, HellmartData>>() {
		}.getType());

		for (final Map.Entry<String, HellmartData> entry : gsonInput.entrySet()) {
			final Ingredient ingr = IngredientUtil.parseFromString(entry.getKey());

			if (registry.keySet().stream().anyMatch(ingredient -> IngredientUtil.ingredientEquals(ingredient, ingr)))
				LogUtil.error("HellmartData JSON Entry for " + entry.getKey() + " already exists, skipping.");
			else
				register(ingr, entry.getValue());
		}
	}

	@Override
	public Map<Ingredient, HellmartData> getRegistry() {
		//noinspection unchecked
		final Map<Ingredient, HellmartData> map = (HashMap) ((HashMap) registry).clone();
		map.putAll(buyRegistry);
		return map;
	}

	@Override
	public List<?> getRecipeList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void register(@Nonnull final ItemStack itemStack, @Nonnull final ItemStack currency, final int price) {
		if (itemStack.isEmpty())
			return;

		final Ingredient ingredient = Ingredient.fromStacks(itemStack);

		if (registry.keySet().stream().anyMatch(entry -> entry.test(itemStack))) {
			LogUtil.error("Dry Entry for " + itemStack.getItem().getRegistryName() + " with meta " + itemStack.getMetadata() + " already exists, skipping.");
			return;
		}
		final HellmartData buyable = new HellmartData(itemStack, currency, price);
		register(ingredient, buyable);
	}

	@Override
	public void register(@Nonnull final ItemInfo product, @Nonnull final ItemInfo currency, final int price) {
		register(product.getItemStack(), currency.getItemStack(), price);
	}

	@Override
	public void register(@Nonnull final ResourceLocation location, @Nonnull final ResourceLocation currency, final int price) {
		register(new ItemStack(Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(location))), new ItemStack(Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(currency))), price);
	}

	@Override
	public void register(@Nonnull final String name, @Nonnull final String currency, final int price) {
		final Ingredient ingredient = new OreIngredientStoring(name);
		final Ingredient ingredient2 = new OreIngredientStoring(currency);

		if (buyRegistry.keySet().stream().anyMatch(entry -> IngredientUtil.ingredientEquals(entry, ingredient)))
			LogUtil.error("Compost Ore Entry for " + name + " already exists, skipping.");
		else
			for(final ItemStack stack : ingredient.getMatchingStacks())
			{
				final ItemStack[] curry = ingredient2.getMatchingStacks();

				final HellmartData buyable = new HellmartData(stack, curry[0], price);
				register(ingredient, buyable);
			}
	}

	@Override
	public HellmartData getItem(final ItemStack stack) {
		Ingredient ingredient = registry.keySet().stream().filter(entry -> entry.test(stack)).findFirst().orElse(null);
		if (ingredient != null) return registry.get(ingredient);
		ingredient = buyRegistry.keySet().stream().filter(entry -> entry.test(stack)).findFirst().orElse(null);
		if (ingredient != null) return buyRegistry.get(ingredient);
		else return HellmartData.getEMPTY();
	}

	@Override
	public HellmartData getItem(@Nonnull final StackInfo info) {
		return getItem(info.getItemStack());
	}

	@Override
	public boolean containsItem(@Nonnull final Item item, final int meta) {
		return containsItem(new ItemStack(item, 1, meta));
	}

	@Override
	public boolean containsItem(@Nonnull final ItemStack stack) {
		return registry.keySet().stream().anyMatch(entry -> entry.test(stack)) || buyRegistry.keySet().stream().anyMatch(entry -> entry.test(stack));
	}

	@Override
	public boolean containsItem(@Nonnull final StackInfo info) {
		return containsItem(info.getItemStack());
	}
}