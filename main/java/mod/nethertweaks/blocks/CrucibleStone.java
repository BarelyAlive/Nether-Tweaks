package mod.nethertweaks.blocks;

import javax.annotation.Nonnull;

import mod.nethertweaks.INames;
import mod.nethertweaks.blocks.tile.TileCrucibleStone;
import mod.nethertweaks.config.Config;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class CrucibleStone extends CrucibleBase implements INames
{
	private boolean fired;

    public boolean isFired() {
		return fired;
	}

	public CrucibleStone(boolean fired) {
        super((fired ? "" : "unfired_") + CRUCIBLE, Material.ROCK);
        this.setHardness(2.0f);
        this.fired = fired;
        
        this.setDefaultState(this.blockState.getBaseState().withProperty(THIN, Config.thinCrucibleModel));   //.withProperty(FIRED, fired);
    }

    @Override
    public TileEntity createTileEntity(@Nonnull World worldIn, @Nonnull IBlockState state) {
        if(this.fired) return new TileCrucibleStone();
        
        return null;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return this.fired;
    }

    @Override
    @Nonnull
    public BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, THIN);    //, FIRED);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return this.fired ? 1 : 0;
    }

    @Override
    public int damageDropped(IBlockState state) {
        return getMetaFromState(state);
    }
}
