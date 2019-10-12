package mod.nethertweaks.registry.registries;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import javax.annotation.Nonnull;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import mod.nethertweaks.api.ISieveRegistry;
import mod.nethertweaks.block.Sieve;
import mod.nethertweaks.block.Sieve.MeshType;
import mod.nethertweaks.config.Config;
import mod.nethertweaks.json.CustomIngredientJson;
import mod.nethertweaks.registry.ingredient.IngredientUtil;
import mod.nethertweaks.registry.ingredient.OreIngredientStoring;
import mod.nethertweaks.registry.manager.NTMRegistryManager;
import mod.nethertweaks.registry.registries.base.BaseRegistryMap;
import mod.nethertweaks.registry.registries.base.types.Siftable;
import mod.sfhcore.json.CustomItemInfoJson;
import mod.sfhcore.util.BlockInfo;
import mod.sfhcore.util.ItemInfo;
import mod.sfhcore.util.StackInfo;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class SieveRegistry extends BaseRegistryMap<Ingredient, List<Siftable>> implements ISieveRegistry {

	public SieveRegistry() {
		super(
				new GsonBuilder()
				.setPrettyPrinting()
				.registerTypeAdapter(Ingredient.class, new CustomIngredientJson())
				.registerTypeAdapter(OreIngredientStoring.class, new CustomIngredientJson())
				.registerTypeAdapter(ItemInfo.class, new CustomItemInfoJson())
				.enableComplexMapKeySerialization()
				.create(),
				new com.google.gson.reflect.TypeToken<Map<Ingredient, List<Siftable>>>() {}.getType(),
				NTMRegistryManager.SIEVE_DEFAULT_REGISTRY_PROVIDERS
				);
	}

	@Override
	public void register(@Nonnull final ItemStack itemStack, @Nonnull final StackInfo drop, final float chance, final String meshLevel) {
		if (itemStack.isEmpty())
			return;
		if (drop instanceof ItemInfo)
			register(CraftingHelper.getIngredient(itemStack), new Siftable((ItemInfo)drop, chance, meshLevel));
		else
			register(CraftingHelper.getIngredient(itemStack), new Siftable(new ItemInfo(drop.getItemStack()), chance, meshLevel));
	}

	@Override
	public void register(@Nonnull final Item item, final int meta, @Nonnull final StackInfo drop, final float chance, final String meshLevel) {
		register(new ItemStack(item, 1, meta), drop, chance, meshLevel);
	}

	@Override
	public void register(@Nonnull final StackInfo item, @Nonnull final StackInfo drop, final float chance, final String meshLevel) {
		register(item.getItemStack(), drop, chance, meshLevel);
	}

	@Override
	public void register(@Nonnull final Block block, final int meta, @Nonnull final StackInfo drop, final float chance, final String meshLevel) {
		register(new ItemStack(block, 1, meta), drop, chance, meshLevel);
	}

	@Override
	public void register(@Nonnull final IBlockState state, @Nonnull final StackInfo drop, final float chance, final String meshLevel) {
		register(new ItemStack(state.getBlock(), 1, state.getBlock().getMetaFromState(state)), drop, chance, meshLevel);
	}

	@Override
	public void register(@Nonnull final ResourceLocation location, final int meta, @Nonnull final StackInfo drop, final float chance, final String meshLevel) {
		register(new ItemStack(Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(location)), 1, meta), drop, chance, meshLevel);
	}

	@Override
	public void register(@Nonnull final String name, @Nonnull final StackInfo drop, final float chance, final String meshLevel) {
		if (drop instanceof ItemInfo)
			register(new OreIngredientStoring(name), new Siftable((ItemInfo)drop, chance, meshLevel));
		else
			register(new OreIngredientStoring(name), new Siftable(new ItemInfo(drop.getItemStack()), chance, meshLevel));
	}

	@Override
	public void register(@Nonnull final Ingredient ingredient, @Nonnull final Siftable drop) {

		Ingredient search = registry.keySet().stream().filter(entry -> IngredientUtil.ingredientEquals(entry, ingredient)).findAny().orElse(null);
		if (search != null)
			registry.get(search).add(drop);
		else {
			NonNullList<Siftable> drops = NonNullList.create();
			drops.add(drop);
			super.register(ingredient, drops);
		}
	}

	/**
	 * Gets *all* possible drops from the sieve. It is up to the dropper to
	 * check whether or not the drops should be dropped!
	 *
	 * @param stack The block to get the sieve drops for
	 * @return ArrayList of {@linkplain Siftable}
	 * that could *potentially* be dropped.
	 */
	@Override
	@Nonnull
	public List<Siftable> getDrops(@Nonnull final StackInfo stack) {
		return getDrops(stack.getItemStack());
	}

	/**
	 * Gets *all* possible drops from the sieve. It is up to the dropper to
	 * check whether or not the drops should be dropped!
	 *
	 * @param stack The ItemStack to get the sieve drops for
	 * @return ArrayList of {@linkplain Siftable}
	 * that could *potentially* be dropped.
	 */
	@Override
	@Nonnull
	public List<Siftable> getDrops(@Nonnull final ItemStack stack) {
		List<Siftable> drops = new ArrayList<>();
		if (!stack.isEmpty())
			registry.entrySet().stream().filter(entry -> entry.getKey().test(stack)).forEach(entry -> drops.addAll(entry.getValue()));
		return drops;
	}

	@Override
	@Nonnull
	public List<Siftable> getDrops(@Nonnull final Ingredient ingredient) {
		List<Siftable> drops = new ArrayList<>();
		registry.entrySet().stream().filter(entry -> entry.getKey() == ingredient).forEach(entry -> drops.addAll(entry.getValue()));
		return drops;
	}

	@Override
	@Nonnull
	public List<ItemStack> getRewardDrops(@Nonnull final Random random, @Nonnull final IBlockState block, final String meshLevel, final int fortuneLevel) {

		List<ItemStack> drops = new ArrayList<>();

		getDrops(new BlockInfo(block)).forEach(siftable -> {
			if (canSieve(siftable.getMeshLevel(), MeshType.getMeshTypeByID(meshLevel))) {
				int triesWithFortune = Math.max(random.nextInt(fortuneLevel + 2), 1);

				for (int i = 0; i < triesWithFortune; i++)
					if (random.nextDouble() < siftable.getChance())
						drops.add(siftable.getDrop().getItemStack());
			}
		});

		return drops;
	}

	@Override
	public boolean canBeSifted(@Nonnull final ItemStack stack) {
		return !stack.isEmpty() && registry.keySet().stream().anyMatch(entry -> entry.test(stack));
	}

	@Override
	public void registerEntriesFromJSON(final FileReader fr) {
		HashMap<Ingredient, ArrayList<Siftable>> gsonInput = gson.fromJson(fr, new TypeToken<HashMap<Ingredient, ArrayList<Siftable>>>() {
		}.getType());

		for (Map.Entry<Ingredient, ArrayList<Siftable>> input : gsonInput.entrySet()) {
			Ingredient key = input.getKey();

			if (key != null && key != Ingredient.EMPTY)
				for (Siftable siftable : input.getValue())
					if (siftable.getDrop().isValid())
						register(key, siftable);
		}
	}

	public static boolean canSieve(final String dropLevel, final Sieve.MeshType meshType){
		return canSieve(MeshType.getMeshTypeByID(dropLevel).getID(), meshType.getID());
	}

	public static boolean canSieve(final int dropLevel, final int meshLevel){
		return Config.flattenSieveRecipes ? meshLevel >= dropLevel : meshLevel == dropLevel;
	}

	@Override
	public List<?> getRecipeList() {
		// TODO Auto-generated method stub
		return null;
	}
}