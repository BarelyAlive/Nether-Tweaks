package mod.nethertweaks.registry;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import mod.nethertweaks.json.CustomItemStackJson;
import mod.nethertweaks.registry.manager.IHammerDefaultRegistryProvider;
import mod.nethertweaks.registry.manager.RegistryManager;
import mod.nethertweaks.vars.HammerReward;
import mod.sfhcore.util.ItemInfo;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;

public class HammerRegistry
{
	private static HashMap<ItemInfo, List<HammerReward>> registry = new HashMap<ItemInfo, List<HammerReward>>();
	private static Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(ItemStack.class, new CustomItemStackJson()).create();

	public static void loadJson(File file)
	{
		registry.clear();

		if (file.exists())
		{
			try
			{
				FileReader fr = new FileReader(file);
				HashMap<String, ArrayList<HammerReward>> gsonInput = gson.fromJson(fr, new TypeToken<HashMap<String, ArrayList<HammerReward>>>(){}.getType());

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

	/**
	 * Adds a new Hammer recipe for use with Ex Nihilo hammers.
	 * 
	 * @param state
	 *            Block State
	 * @param reward
	 *            Reward
	 * @param miningLevel
	 *            Mining level of hammer. 0 = Wood/Gold, 1 = Stone, 2 = Iron, 3 = Diamond. Can be higher, but will need corresponding tool material.
	 * @param chance
	 *            Chance of drop
	 * @param fortuneChance
	 *            Chance of drop per level of fortune
	 */
	public static void register(IBlockState state, ItemStack reward, int miningLevel, float chance, float fortuneChance)
	{
		register(state, reward, miningLevel, chance, fortuneChance, false);
	}

	public static void register(IBlockState state, ItemStack reward, int miningLevel, float chance, float fortuneChance, boolean wildcard) {
		ItemInfo key = new ItemInfo(state);
		if (wildcard)
			key.setMeta(-1);

		List<HammerReward> rewards = registry.get(key);

		if (rewards == null)
		{
			rewards = new ArrayList<>();
		}

		rewards.add(new HammerReward(reward, miningLevel, chance, fortuneChance));
		registry.put(key, rewards);
	}

	public static List<ItemStack> getRewardDrops(Random random, IBlockState block, int miningLevel, int fortuneLevel)
	{
		List<ItemStack> rewards = new ArrayList<ItemStack>();

		for (HammerReward reward : getRewards(block))
		{
			if (miningLevel >= reward.getMiningLevel())
			{
				if (random.nextFloat() <= reward.getChance() + (reward.getFortuneChance() * fortuneLevel))
				{
					rewards.add(reward.getItemStack().copy());
				}
			}
		}

		return rewards;
	}

	public static List<HammerReward> getRewards(IBlockState block)
	{
		return registry.getOrDefault(new ItemInfo(block), Collections.EMPTY_LIST);
	}

	public static boolean registered(Block block)
	{
		return registry.containsKey(new ItemInfo(block.getDefaultState()));
	}

	public static void registerDefaults()
	{
		for (IHammerDefaultRegistryProvider provider : RegistryManager.getDefaultHammerRecipeHandlers()) {
			provider.registerHammerRecipeDefaults();
		}
	}
}
