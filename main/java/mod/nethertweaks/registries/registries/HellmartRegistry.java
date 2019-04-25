package mod.nethertweaks.registries.registries;

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

import mod.nethertweaks.api.ICondenserDefaultRegistryProvider;
import mod.nethertweaks.api.IHellmartDefaultRegistryProvider;
import mod.nethertweaks.items.*;
import mod.nethertweaks.json.CustomHellmartDataJson;
import mod.nethertweaks.json.CustomItemStackJson;
import mod.nethertweaks.registries.manager.NTMRegistryManager;
import mod.nethertweaks.registry.types.HellmartData;
import mod.nethertweaks.registry.types.Dryable;
import mod.nethertweaks.registry.types.HammerReward;
import mod.nethertweaks.registry.types.HellmartData;
import mod.sfhcore.util.ItemInfo;

public class HellmartRegistry {
	
	private static HashMap<Integer, List<HellmartData>> registry = new HashMap<Integer, List<HellmartData>>();
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
					
					registry.put(Integer.parseInt(s), gsonInput.get(s));
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

	private static void registerItems(int item, List<HellmartData> data) {
		registry.put(item, data);
	}

	public static HellmartData getData(int i) {
		return registry.get(i).get(0);
	}

	public static int getSize() {
		return registry.size();
	}
	
	public static void register(int key, HellmartData data)
	{	
		List<HellmartData> rewards = registry.get(key);

		if (rewards == null)
		{
			rewards = new ArrayList<>();
		}

		rewards.add(new HellmartData(data.getItem(), data.getCurrency(), data.getPrice()));
		
		registry.put(key, rewards);
	}
	
	public static List<HellmartData> getHellmartDataList(ItemStack stack)
	{
		return registry.getOrDefault(new ItemInfo(stack), Collections.EMPTY_LIST);
	}
	
	public static HellmartData getHellmartData(ItemStack stack)
	{
		List<HellmartData> rewards = getHellmartDataList(stack);

		for (HellmartData reward : rewards)
		{
			return reward;
		}

		return null;
	}
	
	public static void registerDefaults()
	{
		for (IHellmartDefaultRegistryProvider provider : NTMRegistryManager.HELLMART_DEFAULT_REGISTRY_PROVIDERS) {
			provider.registerHellmartRecipeDefaults();
		}
	}
}