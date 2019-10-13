package mod.nethertweaks.init;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import mod.nethertweaks.Constants;
import mod.nethertweaks.item.ItemChunk;
import mod.nethertweaks.registry.manager.NTMRegistryManager;
import mod.nethertweaks.registry.registries.DynOreRegistry;
import mod.nethertweaks.registry.types.DynOre;
import mod.sfhcore.util.ItemInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

class OreInfo
{
	public ItemStack result;
	public ItemStack chunk;
	public int color;
	public int rarity;
}

public class OreHandler {

	private static final Map<Item, Integer> ore_list = new HashMap<>();
	public static final Map<String, ItemChunk> mod_chunks = new HashMap<>();
	private static final List<String> disabled_chunks = new ArrayList<>();
	private static final List<String> enable_chunks = new ArrayList<>();

	public static void add(final Item stack, final int rarity)
	{
		String reg_domain = Objects.requireNonNull(stack.getRegistryName()).getResourceDomain();
		String reg_path = stack.getRegistryName().getResourcePath();
		String reg = reg_domain + ":" + reg_path;
		String unlocalized_name = stack.getUnlocalizedName();
		String display_name = new ItemStack(stack).getDisplayName().toLowerCase();
        for (String dis_name : disabled_chunks)
			if (reg.contains(dis_name) || unlocalized_name.contains(dis_name) || display_name.contains(dis_name))
                if (enable_chunks.size() != 0)
                    for (String en_name : enable_chunks) {
                        if (!reg.contains(en_name) && !unlocalized_name.contains(en_name) && !display_name.contains(en_name))
                            return;
                    }
                else
                    return;

		/*
		List<DynOre> registry = NTMRegistryManager.DYN_ORE_REGISTRY.getRegistry();

		if (!registry.isEmpty())
		{

			boolean founded = false;

			for(DynOre entry : registry)
			{
				if (entry.getResult().getItemStack().getItem().equals(stack))
				{
					System.out.println(stack);
					System.out.println(entry.getResult().getItemStack().getItem());
					founded = true;
					break;
				}
			}

			if (!founded)
			{
				return;
			}

		}
		*/

		if (!ore_list.containsKey(stack))
			ore_list.put(stack, rarity);
	}

	public static void disableOre(final String name)
	{
		if (!disabled_chunks.contains(name))
			disabled_chunks.add(name);
	}

	public static void enableOre(final String name)
	{
		if (!enable_chunks.contains(name))
			enable_chunks.add(name);
	}

	public static void remove(final Item stack)
	{
        ore_list.remove(stack);
	}

	public static void register(final IForgeRegistry<Item> registry) {
		ItemChunk chunks;
		ArrayList<DynOre> list = (ArrayList<DynOre>) NTMRegistryManager.DYN_ORE_REGISTRY.getRegistry();

		for(Map.Entry<Item, Integer> entry : ore_list.entrySet())
		{
			String[] name_array = new ItemStack(entry.getKey()).getDisplayName().split(" ");
			List<String> name_list = new ArrayList<>(Arrays.asList(name_array));
			name_list.remove(name_list.size() - 1);
			String mod_domain  = String.join("_", name_list);
			mod_domain = mod_domain.toLowerCase();
			DynOre found_entry = null;

			if (!list.isEmpty())
			{

				for (DynOre entry_ore : list)
					if (entry_ore.getID().equals(mod_domain))
					{
						found_entry = entry_ore;
						break;
					}
				if (found_entry == null)
					continue;
			}

			if(!mod_chunks.containsKey(mod_domain))
			{
				chunks = new ItemChunk();
				chunks.setRegistryName(Constants.MOD_ID, Constants.CHUNK + "_" + mod_domain);
				chunks.setCreativeTab(Constants.TABNTM);
				chunks.setResult(mod_domain, entry.getKey());
				if (found_entry != null)
				{
					chunks.setColor(found_entry.getColor());
					chunks.setResult(mod_domain, found_entry.getResult().getItem());
					chunks.setDisplayName(found_entry.getName());
				}
				mod_chunks.put(mod_domain, chunks);
			}
			chunks = null;
		}

		for(Map.Entry<String, ItemChunk> entry : mod_chunks.entrySet())
			registry.register(entry.getValue());
	}

	@SideOnly(Side.CLIENT)
	public static void registerItemHandlers(final net.minecraftforge.client.event.ColorHandlerEvent.Item event) {
		for(Map.Entry<String, ItemChunk> entry : mod_chunks.entrySet())
			event.getItemColors().registerItemColorHandler(new mod.nethertweaks.client.renderers.ChunkColorer(), entry.getValue());
	}

	@SideOnly(Side.CLIENT)
	public static void registerModels(final ModelRegistryEvent event) {
		for(Map.Entry<String, ItemChunk> entry : mod_chunks.entrySet())
			ModelLoader.setCustomModelResourceLocation(entry.getValue(), 0, new net.minecraft.client.renderer.block.model.ModelResourceLocation(Constants.MOD_ID + ":chunk"));
	}

	public static void registerFurnaceRecipe()
	{
		for(Map.Entry<String, ItemChunk> entry : mod_chunks.entrySet())
			FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(entry.getValue(), 1), entry.getValue().getResult(), 1.0f);
	}

	public static void registerDynOreChunks(final DynOreRegistry registry) {
		for(Map.Entry<String, ItemChunk> entry : mod_chunks.entrySet())
		{
	        try {
	        	ItemStack result = entry.getValue().getResult();
	        	if(result.isEmpty())
					continue;
	        	if (entry.getValue().getColor() == 0)
	        	{
		        	IBakedModel res = Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getItemModel(result);
		        	for(int i = 0; i < res.getParticleTexture().getFrameCount(); i++)
						if(res.getParticleTexture().getFrameTextureData(0)[0].length == 256)
							entry.getValue().setColor(res.getParticleTexture().getFrameTextureData(0)[i][140]);
	        	}
	        	mod_chunks.put(entry.getKey(), entry.getValue());
			} catch (Exception e) {
	        	e.printStackTrace();
			}
			registry.register(entry.getKey(), new ItemStack(entry.getValue()).getDisplayName(), new ItemInfo(entry.getValue().getResult()), 1, entry.getValue().getColor());
		}
	}
}
