package mod.nethertweaks.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import mod.nethertweaks.INames;
import mod.nethertweaks.NetherTweaksMod;
import mod.sfhcore.proxy.IVariantProvider;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class NetherSlab extends Block implements IVariantProvider{

	public static final PropertyEnum<NetherSlab.EnumBlockHalf> HALF = PropertyEnum.<NetherSlab.EnumBlockHalf>create("half", NetherSlab.EnumBlockHalf.class);
    protected static final AxisAlignedBB AABB_BOTTOM_HALF = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);
    protected static final AxisAlignedBB AABB_TOP_HALF = new AxisAlignedBB(0.0D, 0.5D, 0.0D, 1.0D, 1.0D, 1.0D);
	
	public NetherSlab() {
		super(Material.WOOD);
        this.setDefaultState(this.blockState.getBaseState().withProperty(HALF, EnumBlockHalf.BOTTOM));
		this.fullBlock = this.isDouble();
        this.setLightOpacity(255);
        this.setUnlocalizedName(INames.NETHERSLAB);
        setRegistryName("nethertweaksmod", INames.NETHERSLAB);
		this.setCreativeTab(NetherTweaksMod.tabNTM);
	}
	
	@Override
    public IBlockState getStateFromMeta(int meta)
    {
		if(meta == 1){
			return this.getDefaultState().withProperty(HALF, NetherSlab.EnumBlockHalf.TOP);
		}else{
			return this.getDefaultState().withProperty(HALF, NetherSlab.EnumBlockHalf.BOTTOM);
		}
    }

    @Override
    public int getMetaFromState(IBlockState state) {
    	
    	if(state.getValue(HALF) == NetherSlab.EnumBlockHalf.TOP) {
    		return 1;
    	} else {
    		return 0;
    	}
    }
	
	@Override
    protected boolean canSilkHarvest()
    {
        return false;
    }

	@Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return this.isDouble() ? FULL_BLOCK_AABB : (state.getValue(HALF) == NetherSlab.EnumBlockHalf.TOP ? AABB_TOP_HALF : AABB_BOTTOM_HALF);
    }

    /**
     * Used to determine ambient occlusion and culling when rebuilding chunks for render
     */
	@Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return this.isDouble();
    }

    @Override
    public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face)
    {
        if (net.minecraftforge.common.ForgeModContainer.disableStairSlabCulling)
            return super.doesSideBlockRendering(state, world, pos, face);

        if ( state.isOpaqueCube() )
            return true;

        EnumBlockHalf side = state.getValue(HALF);
        return (side == EnumBlockHalf.TOP && face == EnumFacing.UP) || (side == EnumBlockHalf.BOTTOM && face == EnumFacing.DOWN);
    }

    /**
     * Called by ItemBlocks just before a block is actually set in the world, to allow for adjustments to the
     * IBlockstate
     */
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        IBlockState iblockstate = super.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer).withProperty(HALF, NetherSlab.EnumBlockHalf.BOTTOM);

        if (this.isDouble())
        {
            return iblockstate;
        }
        else
        {
            return facing != EnumFacing.DOWN && (facing == EnumFacing.UP || (double)hitY <= 0.5D) ? iblockstate : iblockstate.withProperty(HALF, NetherSlab.EnumBlockHalf.TOP);
        }
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    @Override
    public int quantityDropped(Random random)
    {
        return this.isDouble() ? 2 : 1;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return this.isDouble();
    }

    @SideOnly(Side.CLIENT)
    protected static boolean isHalfSlab(IBlockState state)
    {
        return true;
    }

    /**
     * Returns the slab block name with the type associated with it
     */
    
    public boolean isDouble() {
		return false;
	}

    public IProperty<?> getVariantProperty() {
		return null;
	}

    public static enum EnumBlockHalf implements IStringSerializable
    {
        TOP("top"),
        BOTTOM("bottom");

        private final String name;

        private EnumBlockHalf(String name)
        {
            this.name = name;
        }

        public String toString()
        {
            return this.name;
        }

        public String getName()
        {
            return this.name;
        }
    }
    
    @Override
    public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face) {
    	return false;
    }
    
    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {HALF});
    }
    
    public List<Pair<Integer, String>> getVariants()
    {
        List<Pair<Integer, String>> ret = new ArrayList<Pair<Integer, String>>();
        ret.add(new ImmutablePair<Integer, String>(0, "inventory"));
        return ret;
    }
}
