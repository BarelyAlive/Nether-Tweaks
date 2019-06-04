package mod.nethertweaks.api;

import javax.annotation.Nullable;

import mod.nethertweaks.registry.types.Dryable;
import mod.sfhcore.texturing.Color;
import mod.sfhcore.util.BlockInfo;
import mod.sfhcore.util.StackInfo;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

public interface ICondenserRegistry extends IRegistryMap<Ingredient, Dryable>
{
    public void register(ItemStack itemStack, int value);
    public void register(@Nullable Item item, int meta, int value);
    public void register(BlockInfo block, int value);
    public void register(StackInfo item, int value);
    public void register(ResourceLocation location, int meta, int value);

    /**
     * Registers a oredict for sifting with a dynamic color based on the itemColor
     */
    public void register(String name, int value);

    public Dryable getItem(Item item, int meta);
    public Dryable getItem(ItemStack stack);
    public Dryable getItem(StackInfo info);

    public boolean containsItem(Item item, int meta);
    public boolean containsItem(ItemStack stack);
    public boolean containsItem(StackInfo info);
}
