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
	public Seed(String name)
	{
		setCreativeTab(TAB);
		setRegistryName(MODID, name);
	}

	/**
	 * Called when a Block is right-clicked with this Item
	 */
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		Block block = worldIn.getBlockState(pos).getBlock();
		String name = NameHelper.getName(player.getHeldItem(hand));
		
		switch (name) {
		case MUSHROOM_SPORES:
			if(block == Blocks.DIRT || block == Blocks.GRASS){
				if(!worldIn.isRemote)
					worldIn.setBlockState(pos, Blocks.MYCELIUM.getDefaultState());
			}
			break;
		case GRASS_SEEDS:
			if(block == Blocks.DIRT){
				if(!worldIn.isRemote)
					worldIn.setBlockState(pos, Blocks.GRASS.getDefaultState());
			}
			break;
		case CACTUS_SEEDS:
			if(Blocks.CACTUS.canPlaceBlockAt(worldIn, pos.up()))
				if(!worldIn.isRemote)
					worldIn.setBlockState(pos.add(0, 1, 0), Blocks.CACTUS.getDefaultState());
			break;
		case SUGARCANE_SEEDS:
			if(Blocks.REEDS.canPlaceBlockAt(worldIn, pos.up()))
				if(!worldIn.isRemote)
					worldIn.setBlockState(pos.add(0, 1, 0), Blocks.REEDS.getDefaultState());
			break;
		default:
			break;
		}
		
		return EnumActionResult.SUCCESS;
	}
}
