package mod.nethertweaks.registries.registries;

import com.google.common.collect.Lists;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import mod.nethertweaks.Config;
import mod.nethertweaks.api.ISieveRegistry;
import mod.nethertweaks.blocks.Sieve;
import mod.nethertweaks.handler.ItemHandler;
import mod.nethertweaks.json.CustomIngredientJson;
import mod.nethertweaks.json.CustomItemInfoJson;
import mod.nethertweaks.registries.ingredient.IngredientUtil;
import mod.nethertweaks.registries.ingredient.OreIngredientStoring;
import mod.nethertweaks.registries.manager.NTMRegistryManager;
import mod.nethertweaks.registries.registries.base.BaseRegistryMap;
import mod.nethertweaks.registry.types.Siftable;
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
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

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

    public void register(@Nonnull ItemStack itemStack, @Nonnull StackInfo drop, float chance, int meshLevel) {
        if (itemStack.isEmpty()) {
            return;
        }
        if (drop instanceof ItemInfo)
            register(CraftingHelper.getIngredient(itemStack), new Siftable((ItemInfo)drop, chance, meshLevel));
        else
            register(CraftingHelper.getIngredient(itemStack), new Siftable(new ItemInfo(drop.getItemStack()), chance, meshLevel));
    }

    public void register(@Nonnull Item item, int meta, @Nonnull StackInfo drop, float chance, int meshLevel) {
        register(new ItemStack(item, 1, meta), drop, chance, meshLevel);
    }

    public void register(@Nonnull StackInfo item, @Nonnull StackInfo drop, float chance, int meshLevel) {
        register(item.getItemStack(), drop, chance, meshLevel);
    }

    public void register(@Nonnull Block block, int meta, @Nonnull StackInfo drop, float chance, int meshLevel) {
        register(new ItemStack(block, 1, meta), drop, chance, meshLevel);
    }

    public void register(@Nonnull IBlockState state, @Nonnull StackInfo drop, float chance, int meshLevel) {
        register(new ItemStack(state.getBlock(), 1, state.getBlock().getMetaFromState(state)), drop, chance, meshLevel);
    }

    public void register(@Nonnull ResourceLocation location, int meta, @Nonnull StackInfo drop, float chance, int meshLevel) {
        register(new ItemStack(ForgeRegistries.ITEMS.getValue(location), 1, meta), drop, chance, meshLevel);
    }

    public void register(@Nonnull String name, @Nonnull StackInfo drop, float chance, int meshLevel) {
        if (drop instanceof ItemInfo)
            register(new OreIngredientStoring(name), new Siftable((ItemInfo)drop, chance, meshLevel));
        else
            register(new OreIngredientStoring(name), new Siftable(new ItemInfo(drop.getItemStack()), chance, meshLevel));
    }

    public void register(@Nonnull Ingredient ingredient, @Nonnull Siftable drop) {
        if (ingredient == null)
            return;

        Ingredient search = registry.keySet().stream().filter(entry -> IngredientUtil.ingredientEquals(entry, ingredient)).findAny().orElse(null);
        if (search != null) {
            registry.get(search).add(drop);
        } else {
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
    @Nonnull
    public List<Siftable> getDrops(@Nonnull StackInfo stack) {
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
    @Nonnull
    public List<Siftable> getDrops(@Nonnull ItemStack stack) {
        List<Siftable> drops = new ArrayList<>();
        if (!stack.isEmpty())
            registry.entrySet().stream().filter(entry -> entry.getKey().test(stack)).forEach(entry -> drops.addAll(entry.getValue()));
        return drops;
    }

    @Nonnull
    public List<Siftable> getDrops(@Nonnull Ingredient ingredient) {
        List<Siftable> drops = new ArrayList<>();
        registry.entrySet().stream().filter(entry -> entry.getKey() == ingredient).forEach(entry -> drops.addAll(entry.getValue()));
        return drops;
    }

    @Nonnull
    public List<ItemStack> getRewardDrops(@Nonnull Random random, @Nonnull IBlockState block, int meshLevel, int fortuneLevel) {
        if (block == null) {
            return null;
        }

        List<ItemStack> drops = new ArrayList<>();

        getDrops(new BlockInfo(block)).forEach(siftable -> {
            if (canSieve(siftable.getMeshLevel(), meshLevel)) {
                int triesWithFortune = Math.max(random.nextInt(fortuneLevel + 2), 1);

                for (int i = 0; i < triesWithFortune; i++) {
                    if (random.nextDouble() < siftable.getChance()) {
                        drops.add(siftable.getDrop().getItemStack());
                    }
                }
            }
        });

        return drops;
    }

    public boolean canBeSifted(@Nonnull ItemStack stack) {
        return !stack.isEmpty() && registry.keySet().stream().anyMatch(entry -> entry.test(stack));
    }

    @Override
    public void registerEntriesFromJSON(FileReader fr) {
        HashMap<Ingredient, ArrayList<Siftable>> gsonInput = gson.fromJson(fr, new TypeToken<HashMap<Ingredient, ArrayList<Siftable>>>() {
        }.getType());

        for (Map.Entry<Ingredient, ArrayList<Siftable>> input : gsonInput.entrySet()) {
            Ingredient key = input.getKey();

            if (key != null && key != Ingredient.EMPTY) {
                for (Siftable siftable : input.getValue()) {
                    if (siftable.getDrop().isValid()) {
                        register(key, siftable);
                    }
                }
            }
        }
    }

    public static boolean canSieve(int dropLevel, Sieve.MeshType meshType){
        return canSieve(dropLevel, meshType.getID());
    }

    public static boolean canSieve(int dropLevel, int meshLevel){
        return Config.flattenSieveRecipes ? meshLevel >= dropLevel : meshLevel == dropLevel;
    }

	@Override
	public List<?> getRecipeList() {
		// TODO Auto-generated method stub
		return null;
	}
}