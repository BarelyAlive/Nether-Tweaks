package mod.nethertweaks.handler;

import java.util.*;

import net.minecraft.item.ItemStack;

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
	
	
	
}
