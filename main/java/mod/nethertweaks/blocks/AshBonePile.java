package mod.nethertweaks.blocks;

import mod.nethertweaks.INames;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.handler.BlockHandler;
import mod.nethertweaks.handler.ItemHandler;
import mod.sfhcore.blocks.CubeFacingHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AshBonePile extends CubeFacingHorizontal
{
	public AshBonePile() {
		super(Material.SAND, new ResourceLocation(NetherTweaksMod.MODID, INames.ASH_BONE_PILE));
		setResistance(2.0F);
		setHardness(0.4F);
		setCreativeTab(NetherTweaksMod.TABNTM);
		setSoundType(SoundType.SAND);
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if(!world.isBlockLoaded(pos)) return false;
		if(world.isRemote) return true;
		if(playerIn.isSneaking()) return false;
		
		if(playerIn.getHeldItem(hand).getItem() == ItemHandler.COILED_SWORD)
		{
			playerIn.setHeldItem(hand, ItemStack.EMPTY);
			world.setBlockState(pos, BlockHandler.BONFIRE.getDefaultState());
			return true;
		}
		
		return super.onBlockActivated(world, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@Override
    public boolean isTopSolid(IBlockState state) {
        return false;
    }

    @Override
    @Deprecated
    public boolean isFullCube(IBlockState state) {
        return false;
    }
    
    @Override
    @Deprecated
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

    @Override
    @Deprecated
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }
    
    @Override
    public boolean isTranslucent(IBlockState state) {
    	return true;
    }
}
