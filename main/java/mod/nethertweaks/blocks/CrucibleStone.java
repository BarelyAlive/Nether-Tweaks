package mod.nethertweaks.blocks;

import javax.annotation.Nonnull;

import mod.nethertweaks.Constants;
import mod.nethertweaks.blocks.tile.TileCrucibleStone;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class CrucibleStone extends CrucibleBase
{
	private boolean fired;

	public boolean isFired() {
		return fired;
	}

	public CrucibleStone(final boolean fired) {
		super((fired ? "" : "unfired_") + Constants.CRUCIBLE, Material.ROCK);
		setHardness(2.0f);
		this.fired = fired;

	}

	@Override
	public TileEntity createTileEntity(@Nonnull final World worldIn, @Nonnull final IBlockState state) {
		if(fired) return new TileCrucibleStone();

		return null;
	}

	@Override
	public boolean hasTileEntity(final IBlockState state) {
		return fired;
	}
}
