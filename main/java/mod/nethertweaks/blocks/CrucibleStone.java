package mod.nethertweaks.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import mod.nethertweaks.Config;
import mod.nethertweaks.blocks.tile.TileCrucibleStone;
import mod.nethertweaks.interfaces.INames;
import mod.sfhcore.proxy.IVariantProvider;

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
        if (this.fired)
            return new TileCrucibleStone();

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
