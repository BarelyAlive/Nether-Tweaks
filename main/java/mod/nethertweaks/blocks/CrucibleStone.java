package mod.nethertweaks.blocks;

import javax.annotation.Nonnull;

import mod.nethertweaks.blocks.tile.TileCrucibleStone;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class CrucibleStone extends CrucibleBase
{
	private final boolean fired;

	public boolean isFired() {
		return fired;
	}

	public CrucibleStone(final boolean fired) {
		super(Material.ROCK);
		setResistance(10F);
		setHardness(2.0F);
		this.fired = fired;

	}

	@Override
	public TileEntity createTileEntity(@Nonnull final World worldIn, @Nonnull final IBlockState state) {
		return fired ? new TileCrucibleStone() : null;
	}

	@Override
	public boolean hasTileEntity(final IBlockState state) {
		return fired;
	}
}
