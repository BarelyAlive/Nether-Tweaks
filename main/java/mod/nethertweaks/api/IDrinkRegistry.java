package mod.nethertweaks.api;

import javax.annotation.Nullable;

import mod.nethertweaks.registry.types.Drinkable;
import mod.sfhcore.util.StackInfo;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

public interface IDrinkRegistry extends IRegistryMap<Ingredient, Drinkable>
{
	void register(ItemStack itemStack, int thirstReplenish, float saturationReplenish, float poisonChance);
	void register(@Nullable Item item, int meta, int thirstReplenish, float saturationReplenish, float poisonChance);
	void register(StackInfo item, int thirstReplenish, float saturationReplenish, float poisonChance);
	void register(ResourceLocation location, int meta, int thirstReplenish, float saturationReplenish, float poisonChance);

	/**
	 * Registers a oredict for sifting with a dynamic color based on the itemColor
	 */
	void register(String name, int thirstReplenish, float saturationReplenish, float poisonChance);

	Drinkable getItem(Item item, int meta);
	Drinkable getItem(ItemStack stack);
	Drinkable getItem(StackInfo info);

	boolean containsItem(Item item, int meta);
	boolean containsItem(ItemStack stack);
	boolean containsItem(StackInfo info);
}
