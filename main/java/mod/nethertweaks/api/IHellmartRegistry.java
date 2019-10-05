package mod.nethertweaks.api;

import mod.nethertweaks.registry.types.HellmartData;
import mod.sfhcore.util.ItemInfo;
import mod.sfhcore.util.StackInfo;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

public interface IHellmartRegistry extends IRegistryMap<Ingredient, HellmartData>
{
	void register(ItemStack product, ItemStack currency, int price);
	void register(ItemInfo product, ItemInfo currency, int price);
	void register(ResourceLocation location, ResourceLocation currency, int price);

	/**
	 * Registers a oredict for sifting with a dynamic color based on the itemColor
	 */
	void register(String name, String currency, int price);

	HellmartData getItem(ItemStack stack);
	HellmartData getItem(StackInfo info);

	boolean containsItem(Item item, int meta);
	boolean containsItem(ItemStack stack);
	boolean containsItem(StackInfo info);
}
