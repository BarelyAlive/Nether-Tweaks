package mod.nethertweaks.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import mod.nethertweaks.INames;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.client.renderers.ChunkColorer;
import mod.nethertweaks.items.ItemChunk;
import mod.sfhcore.proxy.IVariantProvider;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

class OreInfo
{
	public ItemStack result;
	public ItemStack chunk;
	public int color;
	public int rarity;
}

public class OreHandler {

	private static Map<Item, Integer> ore_list = new HashMap<Item, Integer>();
	public static Map<String, ItemChunk> mod_chunks = new HashMap<String, ItemChunk>();
	private static List<String> disabled_chunks = new ArrayList<String>();
	private static List<String> enable_chunks = new ArrayList<String>();

	public static void add(Item stack, int rarity)
	{
		String reg_domain = stack.getRegistryName().getResourceDomain();
		String reg_path = stack.getRegistryName().getResourcePath();
		String reg = reg_domain + ":" + reg_path;
		String unlocalized_name = stack.getUnlocalizedName();
		String display_name = (new ItemStack(stack)).getDisplayName().toLowerCase();
		for (int i = 0; i < disabled_chunks.size(); i++)
		{
			String dis_name = disabled_chunks.get(i);
			if (reg.contains(dis_name) || unlocalized_name.contains(dis_name) || display_name.contains(dis_name))
			{
				if(enable_chunks.size() != 0)
				{
					for (int j = 0; j < enable_chunks.size(); j++)
					{
						String en_name = enable_chunks.get(j);
						if (!reg.contains(en_name) && !unlocalized_name.contains(en_name) && !display_name.contains(en_name))
						{
							return;
						}
					}
				}
				else
				{
					return;
				}
			}
		}

		if (!ore_list.containsKey(stack))
		{
			ore_list.put(stack, rarity);
		}
	}

	public static void disableOre(String name)
	{
		if (!disabled_chunks.contains(name))
		{
			disabled_chunks.add(name);
		}
	}

	public static void enableOre(String name)
	{
		if (!enable_chunks.contains(name))
		{
			enable_chunks.add(name);
		}
	}

	public static void remove(Item stack)
	{
		if (ore_list.containsKey(stack))
		{
			ore_list.remove(stack);
		}
	}

	public static void register(IForgeRegistry<Item> registry) {
		ItemChunk chunks;
		for(Map.Entry<Item, Integer> entry : ore_list.entrySet())
		{
			String mod_domain  = entry.getKey().getRegistryName().getResourceDomain();
			if(!mod_chunks.containsKey(mod_domain))
			{
				chunks = new ItemChunk();
				chunks.setRegistryName(INames.MODID, (mod_domain == "minecraft" ? INames.CHUNK : mod_domain + "_" + INames.CHUNK));
				chunks.setCreativeTab(NetherTweaksMod.TABNTM);
			}
			else
			{
				chunks = mod_chunks.get(mod_domain);
			}

			int[] ids = OreDictionary.getOreIDs(new ItemStack(entry.getKey()));
			if (ids.length != 0)
			{
				chunks.add(OreDictionary.getOreName(ids[0]).toLowerCase().substring(3), entry.getKey());
			}
			mod_chunks.put(mod_domain, chunks);
			chunks = null;
		}

		for(Map.Entry<String, ItemChunk> entry : mod_chunks.entrySet())
			registry.register(entry.getValue());
	}

	@SideOnly(Side.CLIENT)
	public static void registerItemHandlers(net.minecraftforge.client.event.ColorHandlerEvent.Item event) {
		for(Map.Entry<String, ItemChunk> entry : mod_chunks.entrySet())
			event.getItemColors().registerItemColorHandler(new mod.nethertweaks.client.renderers.ChunkColorer(), entry.getValue());
	}

	@SideOnly(Side.CLIENT)
	public static void registerModels(ModelRegistryEvent event) {
		for(Map.Entry<String, ItemChunk> entry : mod_chunks.entrySet())
		{
            for (Pair<Integer, String> variant : ((IVariantProvider)entry.getValue()).getVariants())
            {
                ModelLoader.setCustomModelResourceLocation(entry.getValue(), variant.getLeft(), new net.minecraft.client.renderer.block.model.ModelResourceLocation(INames.MODID + ":chunk", variant.getRight()));
            }
		}
	}

	public static void registerFurnaceRecipe()
	{
		for(Map.Entry<String, ItemChunk> entry : mod_chunks.entrySet())
		{
			for(int j = 0; j < entry.getValue().getMaxSubItems(); j++)
			{
				FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(entry.getValue(), 1, j), entry.getValue().getResult(j), 1.0f);
			}
		}
	}

}
