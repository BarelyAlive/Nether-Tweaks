package mod.nethertweaks.api;

import javax.annotation.Nullable;

import mod.nethertweaks.registry.types.HellmartData;
import mod.sfhcore.util.StackInfo;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

public interface IHellmartRegistry extends IRegistryMap<Ingredient, HellmartData>
{
    public void register(ItemStack product, ItemStack currency, int price);
    public void register(Item product, Item currency, int price);
    public void register(ResourceLocation location, ResourceLocation currency, int price);

    /**
     * Registers a oredict for sifting with a dynamic color based on the itemColor
     */
    public void register(String name, String currency, int price);

    public HellmartData getItem(ItemStack stack);
    public HellmartData getItem(StackInfo info);

    public boolean containsItem(Item item, int meta);
    public boolean containsItem(ItemStack stack);
    public boolean containsItem(StackInfo info);
}
