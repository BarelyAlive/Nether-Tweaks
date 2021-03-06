package mod.nethertweaks.block;

import mod.sfhcore.blocks.CubeFacingXYZ;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class ElderLog extends CubeFacingXYZ{

	public ElderLog() {
		super(Material.WOOD);
		setResistance(10F);
		setHardness(2F);
		setSoundType(SoundType.WOOD);
	}

	@Override
	public void breakBlock(final World world, final BlockPos pos, final IBlockState state) {
		final int i = 4;
		final int j = 5;

		if (world.isAreaLoaded(pos.add(-5, -5, -5), pos.add(5, 5, 5)))
			for (final BlockPos blockpos : BlockPos.getAllInBox(pos.add(-4, -4, -4), pos.add(4, 4, 4)))
			{
				final IBlockState iblockstate = world.getBlockState(blockpos);

				if (iblockstate.getBlock().isLeaves(iblockstate, world, blockpos))
					iblockstate.getBlock().beginLeavesDecay(iblockstate, world, blockpos);
			}
	}

	@Override
	public boolean canSustainLeaves(final IBlockState state, final net.minecraft.world.IBlockAccess world, final BlockPos pos){ return true; }
	@Override public boolean isWood(final net.minecraft.world.IBlockAccess world, final BlockPos pos){ return true; }
	@Override public boolean isFlammable(final IBlockAccess world, final BlockPos pos, final EnumFacing face) {	return false; }
}