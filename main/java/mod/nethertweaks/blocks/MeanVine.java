package mod.nethertweaks.blocks;

import mod.nethertweaks.Constants;
import mod.nethertweaks.NetherTweaksMod;
import net.minecraft.block.BlockVine;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class MeanVine extends BlockVine{

	public MeanVine(){
		setCreativeTab(NetherTweaksMod.TAB);
		setRegistryName(Constants.MODID, Constants.MEAN_VINE);
	}

	@Override
	public boolean isFlammable(final IBlockAccess world, final BlockPos pos, final EnumFacing face) {
		return false;
	}

	@Override
	public void onEntityCollidedWithBlock(final World worldIn, final BlockPos pos, final IBlockState state, final Entity entityIn) {
		if(!(entityIn instanceof EntityItem))
			entityIn.attackEntityFrom(DamageSource.CACTUS, 1.0F);
	}
}
