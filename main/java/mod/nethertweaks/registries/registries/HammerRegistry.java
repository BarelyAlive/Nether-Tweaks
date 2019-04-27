package mod.nethertweaks.registries.registries;

import com.google.common.collect.Lists;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import mod.nethertweaks.api.IHammerRegistry;
import mod.nethertweaks.json.CustomHammerRewardJson;
import mod.nethertweaks.json.CustomIngredientJson;
import mod.nethertweaks.registries.ingredient.IngredientUtil;
import mod.nethertweaks.registries.ingredient.OreIngredientStoring;
import mod.nethertweaks.registries.manager.NTMRegistryManager;
import mod.nethertweaks.registries.registries.base.BaseRegistryMap;
import mod.nethertweaks.registry.types.HammerReward;
import mod.sfhcore.json.CustomItemStackJson;
import mod.sfhcore.util.BlockInfo;
import mod.sfhcore.util.StackInfo;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.crafting.CraftingHelper;

import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

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
    public void registerEntriesFromJSON(FileReader fr) {
        HashMap<String, ArrayList<HammerReward>> gsonInput = gson.fromJson(fr, new TypeToken<HashMap<String, ArrayList<HammerReward>>>() {
        }.getType());

        for (Map.Entry<String, ArrayList<HammerReward>> s : gsonInput.entrySet()) {
            Ingredient ingredient = IngredientUtil.parseFromString(s.getKey());


            Ingredient search = registry.keySet().stream().filter(entry -> IngredientUtil.ingredientEquals(ingredient, entry)).findAny().orElse(null);
            if (search != null) {
                registry.get(search).addAll(s.getValue());
            } else {
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
    public void register(@Nonnull IBlockState state, @Nonnull ItemStack reward, int miningLevel, float chance, float fortuneChance) {
        register(new ItemStack(state.getBlock(), 1, state.getBlock().getMetaFromState(state)), new HammerReward(reward, miningLevel, chance, fortuneChance));
    }

    public void register(@Nonnull Block block, int meta, @Nonnull ItemStack reward, int miningLevel, float chance, float fortuneChance) {
        register(new ItemStack(block, 1, meta), new HammerReward(reward, miningLevel, chance, fortuneChance));
    }

    public void register(@Nonnull StackInfo stackInfo, @Nonnull ItemStack reward, int miningLevel, float chance, float fortuneChance) {
        register(stackInfo.getItemStack(), new HammerReward(reward, miningLevel, chance, fortuneChance));
    }

    public void register(@Nonnull ItemStack stack, @Nonnull HammerReward reward) {
        if (stack.isEmpty())
            return;
        Ingredient ingredient = CraftingHelper.getIngredient(stack);
        register(ingredient, reward);
    }

    public void register(@Nonnull String name, @Nonnull ItemStack reward, int miningLevel, float chance, float fortuneChance) {
        Ingredient ingredient = new OreIngredientStoring(name);
        register(ingredient, new HammerReward(reward, miningLevel, chance, fortuneChance));
    }

    public void register(@Nonnull Ingredient ingredient, @Nonnull HammerReward reward) {
        Ingredient search = registry.keySet().stream().filter(entry -> IngredientUtil.ingredientEquals(ingredient, entry)).findAny().orElse(null);

        if (search != null) {
            registry.get(search).add(reward);
        } else {
            NonNullList<HammerReward> drops = NonNullList.create();
            drops.add(reward);
            registry.put(ingredient, drops);
        }
    }

    @Nonnull
    public NonNullList<ItemStack> getRewardDrops(@Nonnull Random random, @Nonnull IBlockState block, int miningLevel, int fortuneLevel) {
        NonNullList<ItemStack> rewards = NonNullList.create();

        for (HammerReward reward : getRewards(block)) {
            if (miningLevel >= reward.getMiningLevel()) {
                if (random.nextFloat() <= reward.getChance() + (reward.getFortuneChance() * fortuneLevel)) {
                    rewards.add(reward.getItemStack().copy());
                }
            }
        }

        return rewards;
    }

    @Nonnull
    public NonNullList<HammerReward> getRewards(@Nonnull IBlockState block) {
        return getRewards(new BlockInfo(block));
    }

    @Nonnull
    public NonNullList<HammerReward> getRewards(@Nonnull Block block, int meta) {
        return getRewards(new BlockInfo(block, meta));
    }

    @Nonnull
    public NonNullList<HammerReward> getRewards(@Nonnull BlockInfo stackInfo) {
        NonNullList<HammerReward> drops = NonNullList.create();
        if (stackInfo.isValid())
            registry.entrySet().stream().filter(entry -> entry.getKey().test(stackInfo.getItemStack())).forEach(entry -> drops.addAll(entry.getValue()));
        return drops;
    }

    @Nonnull
    public NonNullList<HammerReward> getRewards(@Nonnull Ingredient ingredient) {
        NonNullList<HammerReward> drops = NonNullList.create();
        registry.entrySet().stream().filter(entry -> entry.getKey() == ingredient).forEach(entry -> drops.addAll(entry.getValue()));
        return drops;
    }

    public boolean isRegistered(@Nonnull IBlockState block) {
        return isRegistered(new BlockInfo(block));
    }

    public boolean isRegistered(@Nonnull Block block) {
        return isRegistered(new BlockInfo(block.getDefaultState()));
    }

    /**
     * Just so that tinkers complement doesn't crash
     */
    @Deprecated
    public boolean registered(Block block) {
        return isRegistered(new BlockInfo(block.getDefaultState()));
    }

    public boolean isRegistered(@Nonnull BlockInfo stackInfo) {
        return registry.keySet().stream().anyMatch(ingredient -> ingredient.test(stackInfo.getItemStack()));
    }

	@Override
	public List<?> getRecipeList() {
		// TODO Auto-generated method stub
		return null;
	}
}
