package mod.nethertweaks.blocks;
 
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import mod.nethertweaks.INames;
import mod.nethertweaks.NetherTweaksMod;
import mod.sfhcore.blocks.CubeFacingXYZ;
import mod.sfhcore.proxy.IVariantProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
 
public class ElderLog extends CubeFacingXYZ{
        
    public ElderLog() {
    	super(Material.WOOD, new ResourceLocation(NetherTweaksMod.MODID, INames.ELDER_LOG));
    	setCreativeTab(NetherTweaksMod.TABNTM);
    	setHardness(2);
    	setResistance(10);
    	setSoundType(SoundType.WOOD);
	}
    
    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
    	int i = 4;
        int j = 5;

        if (world.isAreaLoaded(pos.add(-5, -5, -5), pos.add(5, 5, 5)))
        {
            for (BlockPos blockpos : BlockPos.getAllInBox(pos.add(-4, -4, -4), pos.add(4, 4, 4)))
            {
                IBlockState iblockstate = world.getBlockState(blockpos);

                if (iblockstate.getBlock().isLeaves(iblockstate, world, blockpos))
                {
                    iblockstate.getBlock().beginLeavesDecay(iblockstate, world, blockpos);
                }
            }
        }
    }

    @Override public boolean canSustainLeaves(IBlockState state, net.minecraft.world.IBlockAccess world, BlockPos pos){ return true; }
    @Override public boolean isWood(net.minecraft.world.IBlockAccess world, BlockPos pos){ return true; } 
    @Override public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face) {	return false; }
}