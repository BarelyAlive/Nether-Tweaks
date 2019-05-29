package mod.nethertweaks.handler;

import java.util.*;

import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.WorldProviderSurface;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;

class OreInfo
{
	public ItemStack result;
	public ItemStack chunk;
	public int color;
	public int rarity;
}

public class OreHandler {

	private static Map<ItemStack, Integer> ore_list = new HashMap<ItemStack, Integer>();
	
	public static void add(ItemStack stack, int rarity)
	{
		if (!ore_list.containsKey(stack))
		{
			ore_list.put(stack, rarity);
		}
	}
	
	public static void remove(ItemStack stack)
	{
		if (ore_list.containsKey(stack))
		{
			ore_list.remove(stack);
		}
	}
	
	public static void register(IForgeRegistry<Item> registry) {
	}

	public static void registerItemHandlers(net.minecraftforge.client.event.ColorHandlerEvent.Item event) {
	}

	public static void registerModels(ModelRegistryEvent event) {
	}
	
}
