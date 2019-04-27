package mod.nethertweaks.registries.registries;

import java.io.FileReader;
import java.util.ArrayList;
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
import mod.nethertweaks.registries.ingredient.IngredientUtil;
import mod.nethertweaks.registries.ingredient.OreIngredientStoring;
import mod.nethertweaks.registries.manager.NTMRegistryManager;
import mod.nethertweaks.registries.registries.base.BaseRegistryMap;
import mod.nethertweaks.registry.types.Compostable;
import mod.sfhcore.json.CustomBlockInfoJson;
import mod.sfhcore.texturing.Color;
import mod.sfhcore.util.BlockInfo;
import mod.sfhcore.util.LogUtil;
import mod.sfhcore.util.StackInfo;
import mod.sfhcore.util.Util;
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

    public void register(@Nonnull ItemStack itemStack, float value, @Nonnull BlockInfo state, @Nonnull Color color) {
        if (itemStack.isEmpty())
            return;

        Ingredient ingredient = CraftingHelper.getIngredient(itemStack);

        if (registry.keySet().stream().anyMatch(entry -> entry.test(itemStack))) {
            LogUtil.error("Compost Entry for " + itemStack.getItem().getRegistryName() + " with meta " + itemStack.getMetadata() + " already exists, skipping.");
            return;
        }
        Compostable compostable = new Compostable(value, color, state);
        register(ingredient, compostable);
    }

    public void register(Item item, int meta, float value, @Nonnull BlockInfo state, @Nonnull Color color) {
        register(new ItemStack(item, 1, meta), value, state, color);
    }

    public void register(@Nonnull Block block, int meta, float value, @Nonnull BlockInfo state, @Nonnull Color color) {
        register(new ItemStack(block, 1, meta), value, state, color);
    }

    public void register(@Nonnull StackInfo item, float value, @Nonnull BlockInfo state, @Nonnull Color color) {
        register(item.getItemStack(), value, state, color);
    }

    public void register(@Nonnull ResourceLocation location, int meta, float value, @Nonnull BlockInfo state, @Nonnull Color color) {
        register(ForgeRegistries.ITEMS.getValue(location), meta, value, state, color);
    }

    public void register(@Nonnull String name, float value, @Nonnull BlockInfo state, @Nonnull Color color) {
        Ingredient ingredient = new OreIngredientStoring(name);
        Compostable compostable = new Compostable(value, color, state);

        if (oreRegistry.keySet().stream().anyMatch(entry -> IngredientUtil.ingredientEquals(entry, ingredient)))
            LogUtil.error("Compost Ore Entry for " + name + " already exists, skipping.");
        else
            register(ingredient, compostable);
    }

    /**
     * Registers a oredict for sifting with a dynamic color based on the itemColor
     */
    public void register(@Nonnull String name, float value, @Nonnull BlockInfo state) {
        register(name, value, state, Color.INVALID_COLOR);
    }

    @Nonnull
    public Compostable getItem(@Nonnull Item item, int meta) {
        return getItem(new ItemStack(item, meta));
    }

    @Nonnull
    public Compostable getItem(@Nonnull ItemStack stack) {
        Ingredient ingredient = registry.keySet().stream().filter(entry -> entry.test(stack)).findFirst().orElse(null);
        if (ingredient != null) return registry.get(ingredient);
        ingredient = oreRegistry.keySet().stream().filter(entry -> entry.test(stack)).findFirst().orElse(null);
        if (ingredient != null) return oreRegistry.get(ingredient);
        else return Compostable.getEMPTY();
    }

    @Nonnull
    public Compostable getItem(@Nonnull StackInfo info) {
        return getItem(info.getItemStack());
    }

    public boolean containsItem(@Nonnull Item item, int meta) {
        return containsItem(new ItemStack(item, meta));
    }

    public boolean containsItem(@Nonnull ItemStack stack) {
        return registry.keySet().stream().anyMatch(entry -> entry.test(stack)) || oreRegistry.keySet().stream().anyMatch(entry -> entry.test(stack));
    }

    public boolean containsItem(@Nonnull StackInfo info) {
        return containsItem(info.getItemStack());
    }

    @Override
    public void registerEntriesFromJSON(FileReader fr) {
        Map<String, Compostable> gsonInput = gson.fromJson(fr, new TypeToken<Map<String, Compostable>>() {
        }.getType());

        for (Map.Entry<String, Compostable> entry : gsonInput.entrySet()) {
            Ingredient ingr = IngredientUtil.parseFromString(entry.getKey());

            if (registry.keySet().stream().anyMatch(ingredient -> IngredientUtil.ingredientEquals(ingredient, ingr)))
                LogUtil.error("Compost JSON Entry for " + entry.getKey() + " already exists, skipping.");
            else
                register(ingr, entry.getValue());
        }
    }

    @Override
    public Map<Ingredient, Compostable> getRegistry() {
        //noinspection unchecked
        Map<Ingredient, Compostable> map = (HashMap) ((HashMap) registry).clone();
        map.putAll(oreRegistry);
        return map;
    }

	@Override
	public List<?> getRecipeList() {
		// TODO Auto-generated method stub
		return null;
	}
}