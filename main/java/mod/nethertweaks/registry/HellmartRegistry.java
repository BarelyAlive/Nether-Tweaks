package mod.nethertweaks.registry;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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

import mod.nethertweaks.items.*;
import mod.nethertweaks.json.CustomHellmartDataJson;
import mod.nethertweaks.json.CustomItemStackJson;
import mod.nethertweaks.registry.manager.ICondenserDefaultRegistryProvider;
import mod.nethertweaks.registry.manager.IHammerDefaultRegistryProvider;
import mod.nethertweaks.registry.manager.IHellmartDefaultRegistryProvider;
import mod.nethertweaks.registry.manager.NTMRegistryManager;
import mod.nethertweaks.registry.types.HellmartData;
import mod.nethertweaks.registry.types.Dryable;
import mod.nethertweaks.registry.types.HammerReward;
import mod.nethertweaks.registry.types.HellmartData;
import mod.sfhcore.util.ItemInfo;

public class HellmartRegistry {
	
	private static HashMap<String, List<HellmartData>> registry = new HashMap<String, List<HellmartData>>();
	private static Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(HellmartData.class, new CustomHellmartDataJson()).create();

	public static void loadJson(File file)
	{
		registry.clear();

		if (file.exists())
		{
			try
			{
				FileReader fr = new FileReader(file);
				HashMap<String, ArrayList<HellmartData>> gsonInput = gson.fromJson(fr, new TypeToken<HashMap<String, ArrayList<HellmartData>>>(){}.getType());

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

	public static void saveJson(File file)
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
	private enum CurrencyType {
		DEFAULT, SEEDS, SAPLING, ANIMAL
	}

	private static final ArrayList<HellmartData> items = new ArrayList<HellmartData>();

	private static void registerItems(HellmartData data) {
		items.add(data);
	}

	public static HellmartData getData(int i) {
		return items.get(i);
	}

	public static int getSize() {
		return items.size();
	}
	

	private static ItemStack getCurrency(int config, CurrencyType currencyType) {
		switch(config) {
			case 1:
				return new ItemStack(Items.DIAMOND);
			case 2:
				return new ItemStack(Items.GOLD_INGOT);
			case 3:
				return new ItemStack(Items.GOLD_NUGGET);
			case 4:
				return new ItemStack(Items.IRON_INGOT);
			case 5:
				if(currencyType.equals(CurrencyType.ANIMAL))
					return new ItemStack(Items.EGG);
				if(currencyType.equals(CurrencyType.SEEDS))
					return new ItemStack(Items.WHEAT_SEEDS);
				if(currencyType.equals(CurrencyType.SAPLING))
					return new ItemStack(Blocks.SAPLING);
				else
					return null;
			case 6:
				return new ItemStack(Items.APPLE);
			case 7:
				return new ItemStack(Items.DYE);
			case 0:
			default:
				return new ItemStack(Items.EMERALD);
		}
	}
	
	public static void register(String key, HellmartData data)
	{	
		List<HellmartData> rewards = registry.get(key);

		if (rewards == null)
		{
			rewards = new ArrayList<>();
		}

		rewards.add(new HellmartData(data.getItem(), data.getCurrency(), data.getPrice()));
		
		registry.put(key, rewards);
	}
	
	public static boolean containsItem(String id)
	{
		return registry.containsKey(id);
	}
	
	public static List<HellmartData> getHellmartDataList(String id)
	{
		return registry.getOrDefault(id, Collections.EMPTY_LIST);
	}
	
	public static HellmartData getHellmartData(ItemStack stack)
	{
		List<HellmartData> rewards = getHellmartDataList(stack.getItem().getRegistryName().toString());

		for (HellmartData reward : rewards)
		{
			return reward;
		}

		return null;
	}
	
	public static void registerDefaults()
	{
		for (IHellmartDefaultRegistryProvider provider : NTMRegistryManager.getDefaultHellmartRecipeHandlers()) {
			provider.registerHellmartRecipeDefaults();
		}
	}
}