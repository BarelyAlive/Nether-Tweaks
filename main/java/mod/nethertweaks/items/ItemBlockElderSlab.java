package mod.nethertweaks.items;

import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.blocks.ElderSlab;
import mod.nethertweaks.handler.BlockHandler;
import mod.nethertweaks.interfaces.INames;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockSlab.EnumBlockHalf;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemBlockElderSlab extends ItemBlock{
	
	public ItemBlockElderSlab() {
		super(BlockHandler.ELDERSLABHALF);
		this.setUnlocalizedName("item_" + INames.ELDERSLAB);
		this.setRegistryName(NetherTweaksMod.MODID, INames.ELDERSLAB);
		this.setCreativeTab(NetherTweaksMod.tabNTM);
	}

	@SuppressWarnings("incomplete-switch")
	@Override
	public EnumActionResult onItemUseFirst(EntityPlayer player, World worldIn, BlockPos pos, EnumFacing facing, float hitX,
			float hitY, float hitZ, EnumHand hand) {
		IBlockState clicked = worldIn.getBlockState(pos);
		IBlockState slab = BlockHandler.ELDERSLABDOUBLE.getDefaultState();
		
		System.out.println(clicked);
		System.out.println(hitY);
		
		if(worldIn.isRemote)
			return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
		
		//wenn von oben auf ein vorhandenes slab gesetzt wird
		if (clicked.getBlock() instanceof ElderSlab)
		{
			if(clicked.getProperties().containsValue(EnumBlockHalf.BOTTOM) && facing == EnumFacing.UP)
			{
				worldIn.setBlockState(pos, slab);
			}
			//wenn von unten auf ein vorhandenes slab gesetzt wird
			if(clicked.getProperties().containsValue(EnumBlockHalf.TOP) && facing == EnumFacing.DOWN)
			{
				worldIn.setBlockState(pos, slab);
			}
		}
		else
		{
			if (hitY == 0)
			{
				clicked = worldIn.getBlockState(pos.down(1));
				if (clicked.getBlock() instanceof ElderSlab)
				{
					if(clicked.getProperties().containsValue(EnumBlockHalf.BOTTOM))
					{
						worldIn.setBlockState(pos.down(1), slab);
					}
				}
			}
			else if(hitY == 1)
			{
				clicked = worldIn.getBlockState(pos.up(1));
				if (clicked.getBlock() instanceof ElderSlab)
				{
					if(clicked.getProperties().containsValue(EnumBlockHalf.TOP))
					{
						worldIn.setBlockState(pos.up(1), slab);
					}
				}
			}
		}
		//wenn an die seite eines blocks gesetztw wird
		if (facing != EnumFacing.UP && facing != EnumFacing.DOWN) {
			//wenn an die seite eines blocks gesetzt wird welcher daneben ein bottom slab hat
				switch (facing) {
				case NORTH:
					if(worldIn.getBlockState(pos.north(1)).getProperties().containsValue(EnumBlockHalf.TOP) ||worldIn.getBlockState(pos.north(1)).getProperties().containsValue(EnumBlockHalf.BOTTOM))
						worldIn.setBlockState(pos.north(1), slab);
					break;
				case EAST:
					if(worldIn.getBlockState(pos.east(1)).getProperties().containsValue(EnumBlockHalf.TOP) ||worldIn.getBlockState(pos.east(1)).getProperties().containsValue(EnumBlockHalf.BOTTOM))
						worldIn.setBlockState(pos.east(1), slab);
					break;
				case SOUTH:
					if(worldIn.getBlockState(pos.south(1)).getProperties().containsValue(EnumBlockHalf.TOP) ||worldIn.getBlockState(pos.south(1)).getProperties().containsValue(EnumBlockHalf.BOTTOM))
						worldIn.setBlockState(pos.south(1), slab);
					break;
				case WEST:
					if(worldIn.getBlockState(pos.west(1)).getProperties().containsValue(EnumBlockHalf.TOP) ||worldIn.getBlockState(pos.north(1)).getProperties().containsValue(EnumBlockHalf.BOTTOM))
						worldIn.setBlockState(pos.west(1), slab);
					break;
				}
			}		
		return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}
}
