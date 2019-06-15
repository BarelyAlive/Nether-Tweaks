package mod.nethertweaks.api;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import java.util.*;

import mod.nethertweaks.registry.types.Siftable;
import mod.sfhcore.util.StackInfo;

public interface ISieveRegistry extends IRegistryMappedList<Ingredient, Siftable> {

    void register(ItemStack itemStack, StackInfo drop, float chance, String  meshLevel);
    void register(Item item, int meta, StackInfo drop, float chance, String  meshLevel);
    void register(StackInfo item, StackInfo drop, float chance, String  meshLevel);
    void register(Block block, int meta, StackInfo drop, float chance, String  meshLevel);
    void register(IBlockState state, StackInfo drop, float chance, String  meshLevel);
    void register(ResourceLocation location, int meta, StackInfo drop, float chance, String  meshLevel);
    void register(String name, StackInfo drop, float chance, String  meshLevel);
    void register(Ingredient ingredient, Siftable drop);

    /**
     * Gets *all* possible drops from the sieve. It is up to the dropper to
     * check whether or not the drops should be dropped!
     *
     * @param stack The block to get the sieve drops for
     * @return ArrayList of [Siftable]
     * that could *potentially* be dropped.
     */
    List<Siftable> getDrops(StackInfo stack);

    /**
     * Gets *all* possible drops from the sieve. It is up to the dropper to
     * check whether or not the drops should be dropped!
     *
     * @param stack The ItemStack to get the sieve drops for
     * @return ArrayList of [Siftable]
     * that could *potentially* be dropped.
     */
    List<Siftable> getDrops(ItemStack stack);
    List<Siftable> getDrops(Ingredient ingredient);

    List<ItemStack> getRewardDrops(Random random, IBlockState block, String meshLevel, int fortuneLevel);
    boolean canBeSifted(ItemStack stack);
}
