package mod.nethertweaks.items;

import mod.nethertweaks.INames;
import mod.sfhcore.helper.NameHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Seed extends Item implements INames
{
	public Seed(final String name)
	{
		setCreativeTab(TAB);
		setRegistryName(MODID, name);
	}

	/**
	 * Called when a Block is right-clicked with this Item
	 */
	@Override
	public EnumActionResult onItemUse(final EntityPlayer player, final World world, final BlockPos pos, final EnumHand hand, final EnumFacing facing, final float hitX, final float hitY, final float hitZ)
	{
		Block block = world.getBlockState(pos).getBlock();
		String name = NameHelper.getName(player.getHeldItem(hand));

		if (!world.isRemote)
			switch (name) {
			case MUSHROOM_SPORES:
				if (block.equals(Blocks.DIRT) || block.equals(Blocks.GRASS))
					world.setBlockState(pos, Blocks.MYCELIUM.getDefaultState());
				break;
			case GRASS_SEEDS:
				if (block.equals(Blocks.DIRT))
					world.setBlockState(pos, Blocks.GRASS.getDefaultState());
				break;
			case CACTUS_SEEDS:
				if (Blocks.CACTUS.canPlaceBlockAt(world, pos.up()))
					world.setBlockState(pos.up(), Blocks.CACTUS.getDefaultState());
				break;
			case SUGARCANE_SEEDS:
				if (Blocks.REEDS.canPlaceBlockAt(world, pos.up()))
					world.setBlockState(pos.up(), Blocks.REEDS.getDefaultState());
				break;
			default:
				break;
			}
		return EnumActionResult.SUCCESS;
	}
}
