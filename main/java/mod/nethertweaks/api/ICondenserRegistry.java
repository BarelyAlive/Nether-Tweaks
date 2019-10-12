package mod.nethertweaks.api;

import javax.annotation.Nullable;

import mod.nethertweaks.registry.registries.base.types.Dryable;
import mod.sfhcore.util.BlockInfo;
import mod.sfhcore.util.StackInfo;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

public interface ICondenserRegistry extends IRegistryMap<Ingredient, Dryable>
{
	void register(ItemStack itemStack, int value);
	void register(@Nullable Item item, int meta, int value);
	void register(BlockInfo block, int value);
	void register(StackInfo item, int value);
	void register(ResourceLocation location, int meta, int value);

	/**
	 * Registers a oredict for sifting with a dynamic color based on the itemColor
	 */
	void register(String name, int value);

	Dryable getItem(Item item, int meta);
	Dryable getItem(ItemStack stack);
	Dryable getItem(StackInfo info);

	boolean containsItem(Item item, int meta);
	boolean containsItem(ItemStack stack);
	boolean containsItem(StackInfo info);
}
