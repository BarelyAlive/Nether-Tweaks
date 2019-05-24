package mod.nethertweaks.registries.registries;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Nonnull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import mod.nethertweaks.api.ICondenserRegistry;
import mod.nethertweaks.items.*;
import mod.nethertweaks.json.CustomDryableJson;
import mod.nethertweaks.json.CustomFluidBlockTransformerJson;
import mod.nethertweaks.json.CustomIngredientJson;
import mod.nethertweaks.registries.ingredient.IngredientUtil;
import mod.nethertweaks.registries.ingredient.OreIngredientStoring;
import mod.nethertweaks.registries.manager.*;
import mod.nethertweaks.registries.registries.base.BaseRegistryList;
import mod.nethertweaks.registries.registries.base.BaseRegistryMap;
import mod.nethertweaks.registry.types.Compostable;
import mod.nethertweaks.registry.types.Dryable;
import mod.nethertweaks.registry.types.FluidBlockTransformer;
import mod.nethertweaks.registry.types.HammerReward;
import mod.sfhcore.json.CustomBlockInfoJson;
import mod.sfhcore.json.CustomEntityInfoJson;
import mod.sfhcore.json.CustomItemInfoJson;
import mod.sfhcore.json.CustomItemStackJson;
import mod.sfhcore.texturing.Color;
import mod.sfhcore.util.BlockInfo;
import mod.sfhcore.util.EntityInfo;
import mod.sfhcore.util.ItemInfo;
import mod.sfhcore.util.LogUtil;
import mod.sfhcore.util.StackInfo;

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
                        .enableComplexMapKeySerialization()
                        .create(),
                new TypeToken<Map<Ingredient, Dryable>>() {
                }.getType(),
                NTMRegistryManager.CONDENSER_DEFAULT_REGISTRY_PROVIDERS
        );
	}

	@Override
    public void registerEntriesFromJSON(FileReader fr)
	{
		Map<String, Dryable> gsonInput = gson.fromJson(fr, new TypeToken<Map<String, Dryable>>() {
        }.getType());

        for (Map.Entry<String, Dryable> entry : gsonInput.entrySet()) {
            Ingredient ingr = IngredientUtil.parseFromString(entry.getKey());

            if (registry.keySet().stream().anyMatch(ingredient -> IngredientUtil.ingredientEquals(ingredient, ingr)))
                LogUtil.error("Dryable JSON Entry for " + entry.getKey() + " already exists, skipping.");
            else
                register(ingr, entry.getValue());
        }
	}

	@Override
	public List<?> getRecipeList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void register(@Nonnull ItemStack itemStack, int value) {
		if (itemStack.isEmpty())
            return;

        Ingredient ingredient = CraftingHelper.getIngredient(itemStack);
        
        if (registry.keySet().stream().anyMatch(entry -> entry.test(itemStack))) {
            LogUtil.error("Dry Entry for " + itemStack.getItem().getRegistryName() + " with meta " + itemStack.getMetadata() + " already exists, skipping.");
            return;
        }
        Dryable dryable = new Dryable(itemStack, value);
        register(ingredient, dryable);
	}

	@Override
	public void register(@Nonnull Item item, int meta, int value) {
		register(new ItemStack(item, 1, meta), value);
	}

	@Override
	public void register(@Nonnull Block block, int meta, int value) {
		register(new ItemStack(block, 1, meta), value);
	}

	@Override
	public void register(@Nonnull StackInfo item, int value) {
		register(item.getItemStack(), value);
	}

	@Override
	public void register(@Nonnull ResourceLocation location, int meta, int value) {
		register(ForgeRegistries.ITEMS.getValue(location), meta, value);
	}

	@Override
	public void register(@Nonnull String name, int value) {
		register(name, value);
	}

	@Override
	public Dryable getItem(@Nonnull Item item, int meta) {
		return getItem(new ItemStack(item, meta));
	}

	@Override
	public Dryable getItem(ItemStack stack) {
		Ingredient ingredient = registry.keySet().stream().filter(entry -> entry.test(stack)).findFirst().orElse(null);
        if (ingredient != null) return registry.get(ingredient);
        ingredient = dryRegistry.keySet().stream().filter(entry -> entry.test(stack)).findFirst().orElse(null);
        if (ingredient != null) return dryRegistry.get(ingredient);
        else return Dryable.getEMPTY();
	}

	@Override
	public Dryable getItem(@Nonnull StackInfo info) {
		return getItem(info.getItemStack());
	}

	@Override
	public boolean containsItem(@Nonnull Item item, int meta) {
		return containsItem(new ItemStack(item, meta));
	}

	@Override
	public boolean containsItem(@Nonnull ItemStack stack) {
		return registry.keySet().stream().anyMatch(entry -> entry.test(stack)) || dryRegistry.keySet().stream().anyMatch(entry -> entry.test(stack));
	}

	@Override
	public boolean containsItem(@Nonnull StackInfo info) {
		return containsItem(info.getItemStack());
	}
}