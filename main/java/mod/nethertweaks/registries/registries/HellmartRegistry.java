package mod.nethertweaks.registries.registries;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
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
import mod.nethertweaks.api.IHellmartRegistry;
import mod.nethertweaks.items.*;
import mod.nethertweaks.json.CustomDryableJson;
import mod.nethertweaks.json.CustomHellmartDataJson;
import mod.nethertweaks.json.CustomIngredientJson;
import mod.nethertweaks.registries.ingredient.IngredientUtil;
import mod.nethertweaks.registries.ingredient.OreIngredientStoring;
import mod.nethertweaks.registries.manager.IDefaultRecipeProvider;
import mod.nethertweaks.registries.manager.NTMRegistryManager;
import mod.nethertweaks.registries.registries.base.BaseRegistryList;
import mod.nethertweaks.registries.registries.base.BaseRegistryMap;
import mod.nethertweaks.registry.types.HellmartData;
import mod.nethertweaks.registry.types.Dryable;
import mod.nethertweaks.registry.types.HammerReward;
import mod.nethertweaks.registry.types.HellmartData;
import mod.sfhcore.json.CustomBlockInfoJson;
import mod.sfhcore.json.CustomEntityInfoJson;
import mod.sfhcore.json.CustomItemInfoJson;
import mod.sfhcore.util.BlockInfo;
import mod.sfhcore.util.EntityInfo;
import mod.sfhcore.util.ItemInfo;
import mod.sfhcore.util.LogUtil;
import mod.sfhcore.util.StackInfo;

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
                        .registerTypeAdapter(EntityInfo.class, new CustomEntityInfoJson())
                        .registerTypeAdapter(HellmartData.class, new CustomHellmartDataJson())
                        .enableComplexMapKeySerialization()
                        .create(),
                new TypeToken<Map<Ingredient, HellmartData>>() {
                }.getType(),
                NTMRegistryManager.HELLMART_DEFAULT_REGISTRY_PROVIDERS
        );
	}

	@Override
    public void registerEntriesFromJSON(FileReader fr)
	{
		Map<String, HellmartData> gsonInput = gson.fromJson(fr, new TypeToken<Map<String, HellmartData>>() {
        }.getType());

        for (Map.Entry<String, HellmartData> entry : gsonInput.entrySet()) {
            Ingredient ingr = IngredientUtil.parseFromString(entry.getKey());

            if (registry.keySet().stream().anyMatch(ingredient -> IngredientUtil.ingredientEquals(ingredient, ingr)))
                LogUtil.error("HellmartData JSON Entry for " + entry.getKey() + " already exists, skipping.");
            else
                register(ingr, entry.getValue());
        }
	}
	
	@Override
    public Map<Ingredient, HellmartData> getRegistry() {
        //noinspection unchecked
        Map<Ingredient, HellmartData> map = (HashMap) ((HashMap) registry).clone();
        map.putAll(buyRegistry);
        return map;
    }

	@Override
	public List<?> getRecipeList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void register(@Nonnull ItemStack itemStack, @Nonnull ItemStack currency, int price) {
		if (itemStack.isEmpty())
            return;

        Ingredient ingredient = Ingredient.fromStacks(itemStack);;
        
        if (registry.keySet().stream().anyMatch(entry -> entry.test(itemStack))) {
            LogUtil.error("Dry Entry for " + itemStack.getItem().getRegistryName() + " with meta " + itemStack.getMetadata() + " already exists, skipping.");
            return;
        }
        HellmartData buyable = new HellmartData(itemStack, currency, price);
        register(ingredient, buyable);
	}

	@Override
	public void register(@Nonnull ResourceLocation location, @Nonnull ResourceLocation currency, int price) {
		register(ForgeRegistries.ITEMS.getValue(location), ForgeRegistries.ITEMS.getValue(currency), price);
	}

	@Override
	public void register(@Nonnull String name, @Nonnull String currency, int price) {
        Ingredient ingredient = new OreIngredientStoring(name);
        Ingredient ingredient2 = new OreIngredientStoring(currency);

        if (buyRegistry.keySet().stream().anyMatch(entry -> IngredientUtil.ingredientEquals(entry, ingredient)))
            LogUtil.error("Compost Ore Entry for " + name + " already exists, skipping.");
        else
        {
        	for(ItemStack stack : ingredient.getMatchingStacks())
        	{
        		ItemStack[] curry = ingredient2.getMatchingStacks();
        		
                HellmartData buyable = new HellmartData(stack, curry[0], price);
        		register(ingredient, buyable);
        	}
        }       
	}

	@Override
	public HellmartData getItem(ItemStack stack) {
		Ingredient ingredient = registry.keySet().stream().filter(entry -> entry.test(stack)).findFirst().orElse(null);
        if (ingredient != null) return registry.get(ingredient);
        ingredient = buyRegistry.keySet().stream().filter(entry -> entry.test(stack)).findFirst().orElse(null);
        if (ingredient != null) return buyRegistry.get(ingredient);
        else return HellmartData.getEMPTY();
	}

	@Override
	public HellmartData getItem(@Nonnull StackInfo info) {
		return getItem(info.getItemStack());
	}

	@Override
	public boolean containsItem(@Nonnull Item item, int meta) {
		return containsItem(new ItemStack(item, 1, meta));
	}

	@Override
	public boolean containsItem(@Nonnull ItemStack stack) {
		return registry.keySet().stream().anyMatch(entry -> entry.test(stack)) || buyRegistry.keySet().stream().anyMatch(entry -> entry.test(stack));
	}

	@Override
	public boolean containsItem(@Nonnull StackInfo info) {
		return containsItem(info.getItemStack());
	}

	@Override
	public void register(Item product, Item currency, int price) {
		// TODO Auto-generated method stub
		
	}
	
}