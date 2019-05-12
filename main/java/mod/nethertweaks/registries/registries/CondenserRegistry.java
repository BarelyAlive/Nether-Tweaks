package mod.nethertweaks.registries.registries;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
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
import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import mod.nethertweaks.api.ICondenserDefaultRegistryProvider;
import mod.nethertweaks.items.*;
import mod.nethertweaks.json.CustomDryableJson;
import mod.nethertweaks.json.CustomFluidBlockTransformerJson;
import mod.nethertweaks.json.CustomIngredientJson;
import mod.nethertweaks.registries.ingredient.OreIngredientStoring;
import mod.nethertweaks.registries.manager.*;
import mod.nethertweaks.registries.registries.base.BaseRegistryList;
import mod.nethertweaks.registry.types.Dryable;
import mod.nethertweaks.registry.types.FluidBlockTransformer;
import mod.nethertweaks.registry.types.HammerReward;
import mod.sfhcore.json.CustomBlockInfoJson;
import mod.sfhcore.json.CustomEntityInfoJson;
import mod.sfhcore.json.CustomItemInfoJson;
import mod.sfhcore.util.BlockInfo;
import mod.sfhcore.util.EntityInfo;
import mod.sfhcore.util.ItemInfo;
import mod.sfhcore.util.StackInfo;

public class CondenserRegistry extends BaseRegistryList<Dryable> implements ICondenserDefaultRegistryProvider {
	
	public CondenserRegistry() {
        super(
                new GsonBuilder()
                        .setPrettyPrinting()
                        .registerTypeAdapter(ItemInfo.class, new CustomItemInfoJson())
                        .registerTypeAdapter(StackInfo.class, new CustomItemInfoJson())
                        .registerTypeAdapter(BlockInfo.class, new CustomBlockInfoJson())
                        .registerTypeAdapter(Ingredient.class, new CustomIngredientJson())
                        .registerTypeAdapter(OreIngredientStoring.class, new CustomIngredientJson())
                        .registerTypeAdapter(EntityInfo.class, new CustomEntityInfoJson())
                        .registerTypeAdapter(Dryable.class, new CustomDryableJson())
                        .create(),
                NTMRegistryManager.CONDENSER_DEFAULT_REGISTRY_PROVIDERS
        );
	}

	private static HashMap<ItemInfo, List<Dryable>> registry = new HashMap<ItemInfo, List<Dryable>>();
	private static Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(Dryable.class, new CustomDryableJson()).create();

	public void loadJson(File file)
	{
		registry.clear();

		if (file.exists())
		{
			try
			{
				FileReader fr = new FileReader(file);
				HashMap<String, ArrayList<Dryable>> gsonInput = gson.fromJson(fr, new TypeToken<HashMap<String, ArrayList<Dryable>>>(){}.getType());

				Iterator<String> it = gsonInput.keySet().iterator();

				while (it.hasNext())
				{
					String s = (String) it.next();
					ItemInfo stack = new ItemInfo(s);
					registry.put(stack, gsonInput.get(s));
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			registerDefaults();
			saveJson(file);
		}
	}

	public void saveJson(File file)
	{
		try
		{
			FileWriter fw = new FileWriter(file);

			gson.toJson(registry, fw);

			fw.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	//Value is how much it needs to get 1B of water
	public static void register(ItemStack item, int value)
	{	
		ItemInfo key = new ItemInfo(item);
		List<Dryable> rewards = registry.get(key);

		if (rewards == null)
		{
			rewards = new ArrayList<>();
		}

		rewards.add(new Dryable(item.getItem(), item.getItemDamage(), value));
		
		registry.put(key, rewards);
	}
	
	public static boolean containsItem(ItemStack stack)
	{
		return registry.containsKey(new ItemInfo(stack));
	}

	public static boolean containsItem(Item item, int meta)
	{
		return registry.containsKey(new ItemInfo(item, meta));
	}
	
	public static List<Dryable> getDryableList(ItemStack stack)
	{
		return registry.getOrDefault(new ItemInfo(stack), Collections.EMPTY_LIST);
	}
	
	public static Dryable getDryable(ItemStack stack)
	{
		List<Dryable> rewards = getDryableList(stack);

		for (Dryable reward : rewards)
		{
			return reward;
		}

		return null;
	}

	@Override
	public void registerCondenserRecipeDefaults() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void registerEntriesFromJSON(FileReader fr) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<?> getRecipeList() {
		// TODO Auto-generated method stub
		return null;
	}
}