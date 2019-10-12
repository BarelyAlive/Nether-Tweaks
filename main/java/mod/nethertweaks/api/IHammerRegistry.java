package mod.nethertweaks.api;

import java.util.List;
import java.util.Random;

import mod.nethertweaks.registry.registries.base.types.HammerReward;
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
	void register(IBlockState state, ItemStack reward, int miningLevel, float chance, float fortuneChance);

	void register(Block block, int meta, ItemStack reward, int miningLevel, float chance, float fortuneChance);
	void register(StackInfo stackInfo, ItemStack reward, int miningLevel, float chance, float fortuneChance);
	void register(ItemStack stack, HammerReward reward);
	void register(String name, ItemStack reward, int miningLevel, float chance, float fortuneChance);
	void register(Ingredient ingredient, HammerReward reward);

	List<ItemStack> getRewardDrops(Random random, IBlockState block, int miningLevel, int fortuneLevel);

	List<HammerReward> getRewards(IBlockState block);
	List<HammerReward> getRewards(Block block, int meta);
	List<HammerReward> getRewards(BlockInfo stackInfo);
	List<HammerReward> getRewards(Ingredient ingredient);

	boolean isRegistered(IBlockState block);
	boolean isRegistered(Block block);
	boolean isRegistered(BlockInfo stackInfo);
}