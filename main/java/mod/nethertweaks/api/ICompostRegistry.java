package mod.nethertweaks.api;

import javax.annotation.Nullable;

import mod.nethertweaks.registry.types.Compostable;
import mod.sfhcore.texturing.Color;
import mod.sfhcore.util.BlockInfo;
import mod.sfhcore.util.StackInfo;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

public interface ICompostRegistry extends IRegistryMap<Ingredient, Compostable>
{
	void register(ItemStack itemStack, float value, BlockInfo state, Color color);
	void register(@Nullable Item item, int meta, float value, BlockInfo state, Color color);
	void register(Block block, int meta, float value, BlockInfo state, Color color);
	void register(StackInfo item, float value, BlockInfo state, Color color);
	void register(ResourceLocation location, int meta, float value, BlockInfo state, Color color);
	void register(String name, float value, BlockInfo state, Color color);

	/**
	 * Registers a oredict for sifting with a dynamic color based on the itemColor
	 */
	void register(String name, float value, BlockInfo state);

	Compostable getItem(Item item, int meta);
	Compostable getItem(ItemStack stack);
	Compostable getItem(StackInfo info);

	boolean containsItem(Item item, int meta);
	boolean containsItem(ItemStack stack);
	boolean containsItem(StackInfo info);
}
