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
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
 
public class NetherLog extends Block implements IVariantProvider{
     
    public static final PropertyEnum<NetherLog.EnumAxis> LOG_AXIS = PropertyEnum.<NetherLog.EnumAxis>create("axis", NetherLog.EnumAxis.class);
    
    public NetherLog() {
    	super(Material.WOOD);
    	this.setDefaultState(this.blockState.getBaseState().withProperty(LOG_AXIS, NetherLog.EnumAxis.Y));
    	setCreativeTab(NetherTweaksMod.tabNetherTweaksMod);
    	setHardness(2);
    	setResistance(10);
    	setSoundType(SoundType.WOOD);
    	setUnlocalizedName(INames.NETHERLOG);
	}
     
    @Override
    protected BlockStateContainer createBlockState() {
    	return new BlockStateContainer(this, new IProperty[] { LOG_AXIS });
    }
    
    /**
     * Called by ItemBlocks just before a block is actually set in the world, to allow for adjustments to the
     * IBlockstate
     */
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getStateFromMeta(meta).withProperty(LOG_AXIS, NetherLog.EnumAxis.fromFacingAxis(facing.getAxis()));
    }
    
    @Override
    public IBlockState getStateFromMeta(int meta) {
    	// TODO Auto-generated method stub
    	return getDefaultState().withProperty(LOG_AXIS, meta == 1 ? EnumAxis.X : (meta == 2 ? EnumAxis.Y : (meta == 3 ? EnumAxis.Z : EnumAxis.NONE)));
    }
    
    @Override
    public int getMetaFromState(IBlockState state) {
    	
    	EnumAxis type = (EnumAxis) state.getValue(LOG_AXIS);
    	
    	if(type.getName().equals("none")) return 0;
    	if(type.getName().equals("x")) return 1;
    	if(type.getName().equals("y")) return 2;
    	if(type.getName().equals("z")) return 3;
    	
    	return 0;
    }
    
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        int i = 4;
        int j = 5;

        if (worldIn.isAreaLoaded(pos.add(-5, -5, -5), pos.add(5, 5, 5)))
        {
            for (BlockPos blockpos : BlockPos.getAllInBox(pos.add(-4, -4, -4), pos.add(4, 4, 4)))
            {
                IBlockState iblockstate = worldIn.getBlockState(blockpos);

                if (iblockstate.getBlock().isLeaves(iblockstate, worldIn, blockpos))
                {
                    iblockstate.getBlock().beginLeavesDecay(iblockstate, worldIn, blockpos);
                }
            }
        }
    } 

    /**
     * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     */
    public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        switch (rot)
        {
            case COUNTERCLOCKWISE_90:
            case CLOCKWISE_90:

                switch ((NetherLog.EnumAxis)state.getValue(LOG_AXIS))
                {
                    case X:
                        return state.withProperty(LOG_AXIS, NetherLog.EnumAxis.Z);
                    case Z:
                        return state.withProperty(LOG_AXIS, NetherLog.EnumAxis.X);
                    default:
                        return state;
                }

            default:
                return state;
        }
    }

    @Override public boolean canSustainLeaves(IBlockState state, net.minecraft.world.IBlockAccess world, BlockPos pos){ 
    	return true; 
    }

    public static enum EnumAxis implements IStringSerializable
    {
        X("x"),
        Y("y"),
        Z("z"),
        NONE("none");

        private final String name;

        private EnumAxis(String name)
        {
            this.name = name;
        }

        public String toString()
        {
            return this.name;
        }

        public static NetherLog.EnumAxis fromFacingAxis(EnumFacing.Axis axis)
        {
            switch (axis)
            {
                case X:
                    return X;
                case Y:
                    return Y;
                case Z:
                    return Z;
                default:
                    return NONE;
            }
        }

        public String getName()
        {
            return this.name;
        }
    }
    
    @Override
    public boolean isWood(IBlockAccess world, BlockPos pos) {
    	return true;
    }
    
    @Override
    public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face) {
    	return false;
    }
    
    @Override
    public List<Pair<Integer, String>> getVariants()
    {
        List<Pair<Integer, String>> ret = new ArrayList<Pair<Integer, String>>();
            ret.add(new ImmutablePair<Integer, String>(0, "inventory"));
        return ret;
    }
}