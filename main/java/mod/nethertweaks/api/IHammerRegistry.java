package mod.nethertweaks.api;

import java.util.List;
import java.util.Random;

import mod.nethertweaks.registry.types.HammerReward;
import mod.sfhcore.util.BlockInfo;
import mod.sfhcore.util.StackInfo;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

public interface IHammerRegistry extends IRegistryMappedList<Ingredient, HammerReward>
{
    /**
     * Adds a new Hammer recipe for use with Ex Nihilo hammers.
     *
     * @param state         The blocks state to add
     * @param reward        Reward
     * @param miningLevel   Mining level of hammer. 0 = Wood/Gold, 1 = Stone, 2 = Iron, 3 = Diamond. Can be higher, but will need corresponding tool material.
     * @param chance        Chance of drop
     * @param fortuneChance Chance of drop per level of fortune
     */
    public void register(IBlockState state, ItemStack reward, int miningLevel, float chance, float fortuneChance);

    public void register(Block block, int meta, ItemStack reward, int miningLevel, float chance, float fortuneChance);
    public void register(StackInfo stackInfo, ItemStack reward, int miningLevel, float chance, float fortuneChance);
    public void register(ItemStack stack, HammerReward reward);
    public void register(String name, ItemStack reward, int miningLevel, float chance, float fortuneChance);
    public void register(Ingredient ingredient, HammerReward reward);

    public List<ItemStack> getRewardDrops(Random random, IBlockState block, int miningLevel, int fortuneLevel);

    public List<HammerReward> getRewards(IBlockState block);
    public List<HammerReward> getRewards(Block block, int meta);
    public List<HammerReward> getRewards(BlockInfo stackInfo);
    public List<HammerReward> getRewards(Ingredient ingredient);

    public boolean isRegistered(IBlockState block);
    public boolean isRegistered(Block block);
    public boolean isRegistered(BlockInfo stackInfo);
}