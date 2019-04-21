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
    public void register(ItemStack itemStack, float value, BlockInfo state, Color color);
    public void register(@Nullable Item item, int meta, float value, BlockInfo state, Color color);
    public void register(Block block, int meta, float value, BlockInfo state, Color color);
    public void register(StackInfo item, float value, BlockInfo state, Color color);
    public void register(ResourceLocation location, int meta, float value, BlockInfo state, Color color);
    public void register(String name, float value, BlockInfo state, Color color);

    /**
     * Registers a oredict for sifting with a dynamic color based on the itemColor
     */
    public void register(String name, float value, BlockInfo state);

    public Compostable getItem(Item item, int meta);
    public Compostable getItem(ItemStack stack);
    public Compostable getItem(StackInfo info);

    public boolean containsItem(Item item, int meta);
    public boolean containsItem(ItemStack stack);
    public boolean containsItem(StackInfo info);
}
