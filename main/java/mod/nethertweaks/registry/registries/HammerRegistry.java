package mod.nethertweaks.registry.registries;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Nonnull;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import mod.nethertweaks.api.IHammerRegistry;
import mod.nethertweaks.json.CustomHammerRewardJson;
import mod.nethertweaks.json.CustomIngredientJson;
import mod.nethertweaks.registry.ingredient.IngredientUtil;
import mod.nethertweaks.registry.ingredient.OreIngredientStoring;
import mod.nethertweaks.registry.manager.NTMRegistryManager;
import mod.nethertweaks.registry.registries.base.BaseRegistryMap;
import mod.nethertweaks.registry.registries.base.types.HammerReward;
import mod.sfhcore.json.CustomItemStackJson;
import mod.sfhcore.util.BlockInfo;
import mod.sfhcore.util.StackInfo;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.crafting.CraftingHelper;

public class HammerRegistry extends BaseRegistryMap<Ingredient, List<HammerReward>> implements IHammerRegistry {

	public HammerRegistry() {
		super(
				new GsonBuilder()
				.setPrettyPrinting()
				.registerTypeAdapter(ItemStack.class,  new CustomItemStackJson())
				.registerTypeAdapter(Ingredient.class,  new CustomIngredientJson())
				.registerTypeAdapter(OreIngredientStoring.class,  new CustomIngredientJson())
				.registerTypeAdapter(HammerReward.class,  new CustomHammerRewardJson())
				.enableComplexMapKeySerialization()
				.create(),
				new com.google.gson.reflect.TypeToken<Map<Ingredient, List<HammerReward>>>() {}.getType(),
				NTMRegistryManager.HAMMER_DEFAULT_REGISTRY_PROVIDERS
				);
	}

	@Override
	public void registerEntriesFromJSON(final FileReader fr) {
		HashMap<String, ArrayList<HammerReward>> gsonInput = gson.fromJson(fr, new TypeToken<HashMap<String, ArrayList<HammerReward>>>() {
		}.getType());

		for (Map.Entry<String, ArrayList<HammerReward>> s : gsonInput.entrySet()) {
			Ingredient ingredient = IngredientUtil.parseFromString(s.getKey());

			Ingredient search = registry.keySet().stream().filter(entry -> IngredientUtil.ingredientEquals(ingredient, entry)).findAny().orElse(null);
			if (search != null)
				registry.get(search).addAll(s.getValue());
			else {
				NonNullList<HammerReward> drops = NonNullList.create();
				drops.addAll(s.getValue());
				registry.put(ingredient, drops);
			}
		}
	}

	/**
	 * Adds a new Hammer recipe for use with Ex Nihilo hammers.
	 *
	 * @param state         The blocks state to add
	 * @param reward        Reward
	 * @param miningLevel   Mining level of hammer. 0 = Wood/Gold, 1 = Stone, 2 = Iron, 3 = Diamond. Can be higher, but will need corresponding tool material.
	 * @param chance        Chance of drop
	 * @param fortuneChance Chance of drop per level of fortune
	 */
	@Override
	public void register(@Nonnull final IBlockState state, @Nonnull final ItemStack reward, final int miningLevel, final float chance, final float fortuneChance) {
		register(new ItemStack(state.getBlock(), 1, state.getBlock().getMetaFromState(state)), new HammerReward(reward, miningLevel, chance, fortuneChance));
	}

	@Override
	public void register(@Nonnull final Block block, final int meta, @Nonnull final ItemStack reward, final int miningLevel, final float chance, final float fortuneChance) {
		register(new ItemStack(block, 1, meta), new HammerReward(reward, miningLevel, chance, fortuneChance));
	}

	@Override
	public void register(@Nonnull final StackInfo stackInfo, @Nonnull final ItemStack reward, final int miningLevel, final float chance, final float fortuneChance) {
		register(stackInfo.getItemStack(), new HammerReward(reward, miningLevel, chance, fortuneChance));
	}

	@Override
	public void register(@Nonnull final ItemStack stack, @Nonnull final HammerReward reward) {
		if (stack.isEmpty())
			return;
		Ingredient ingredient = CraftingHelper.getIngredient(stack);
		register(ingredient, reward);
	}

	@Override
	public void register(@Nonnull final String name, @Nonnull final ItemStack reward, final int miningLevel, final float chance, final float fortuneChance) {
		Ingredient ingredient = new OreIngredientStoring(name);
		register(ingredient, new HammerReward(reward, miningLevel, chance, fortuneChance));
	}

	@Override
	public void register(@Nonnull final Ingredient ingredient, @Nonnull final HammerReward reward) {
		Ingredient search = registry.keySet().stream().filter(entry -> IngredientUtil.ingredientEquals(ingredient, entry)).findAny().orElse(null);

		if (search != null)
			registry.get(search).add(reward);
		else {
			NonNullList<HammerReward> drops = NonNullList.create();
			drops.add(reward);
			registry.put(ingredient, drops);
		}
	}

	@Override
	@Nonnull
	public NonNullList<ItemStack> getRewardDrops(@Nonnull final Random random, @Nonnull final IBlockState block, final int miningLevel, final int fortuneLevel) {
		NonNullList<ItemStack> rewards = NonNullList.create();

		for (HammerReward reward : getRewards(block))
			if (miningLevel >= reward.getMiningLevel())
				if (random.nextFloat() <= reward.getChance() + reward.getFortuneChance() * fortuneLevel)
					rewards.add(reward.getItemStack().copy());

		return rewards;
	}

	@Override
	@Nonnull
	public NonNullList<HammerReward> getRewards(@Nonnull final IBlockState block) {
		return getRewards(new BlockInfo(block));
	}

	@Override
	@Nonnull
	public NonNullList<HammerReward> getRewards(@Nonnull final Block block, final int meta) {
		return getRewards(new BlockInfo(block, meta));
	}

	@Override
	@Nonnull
	public NonNullList<HammerReward> getRewards(@Nonnull final BlockInfo stackInfo) {
		NonNullList<HammerReward> drops = NonNullList.create();
		if (stackInfo.isValid())
			registry.entrySet().stream().filter(entry -> entry.getKey().test(stackInfo.getItemStack())).forEach(entry -> drops.addAll(entry.getValue()));
		return drops;
	}

	@Override
	@Nonnull
	public NonNullList<HammerReward> getRewards(@Nonnull final Ingredient ingredient) {
		NonNullList<HammerReward> drops = NonNullList.create();
		registry.entrySet().stream().filter(entry -> entry.getKey() == ingredient).forEach(entry -> drops.addAll(entry.getValue()));
		return drops;
	}

	@Override
	public boolean isRegistered(@Nonnull final IBlockState block) {
		return isRegistered(new BlockInfo(block));
	}

	@Override
	public boolean isRegistered(@Nonnull final Block block) {
		return isRegistered(new BlockInfo(block.getDefaultState()));
	}

	/**
	 * Just so that tinkers complement doesn't crash
	 */
	@Deprecated
	public boolean registered(final Block block) {
		return isRegistered(new BlockInfo(block.getDefaultState()));
	}

	@Override
	public boolean isRegistered(@Nonnull final BlockInfo stackInfo) {
		System.out.println(stackInfo);
		return registry.keySet().stream().anyMatch(ingredient -> ingredient.test(stackInfo.getItemStack()));
	}

	@Override
	public List<?> getRecipeList() {
		// TODO Auto-generated method stub
		return null;
	}
}
