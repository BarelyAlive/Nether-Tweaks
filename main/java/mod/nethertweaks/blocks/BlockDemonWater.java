package mod.nethertweaks.blocks;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import mod.nethertweaks.BucketLoader;
import mod.nethertweaks.INames;
import mod.nethertweaks.NetherTweaksMod;
import mod.sfhcore.proxy.IVariantProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockDemonWater extends BlockFluidClassic implements IVariantProvider{

        public BlockDemonWater(Fluid fluid, Material material) {
                super(fluid, Material.WATER);
                setCreativeTab(NetherTweaksMod.tabNetherTweaksMod);
                setResistance(500.0F);
                setHardness(100.0F);
                setDensity(1000);
                setUnlocalizedName(INames.DEMOMWATERBLOCK);
                setLightLevel(3);
        }
        
        @Override
        public IBlockState getStateFromMeta(int meta)
        {
            return getBlockState().getBaseState().withProperty(LEVEL, meta);
        }

        @Override
        public boolean canDisplace(IBlockAccess world, BlockPos blockPos)
        {
            return !world.getBlockState(blockPos).getBlock().getMaterial(world.getBlockState(blockPos)).isLiquid() && super.canDisplace(world, blockPos);
        }

        @Override
        public boolean displaceIfPossible(World world, BlockPos blockPos)
        {
            return !world.getBlockState(blockPos).getBlock().getMaterial(world.getBlockState(blockPos)).isLiquid() && super.displaceIfPossible(world, blockPos);
        }

        @Override
        public BlockRenderLayer getBlockLayer()
        {
            return BlockRenderLayer.SOLID;
        }
        
        @Override
        public Fluid getFluid() {
        return BucketLoader.fluidDemonWater;
        }
        
        @Override
        public List<Pair<Integer, String>> getVariants()
        {
            List<Pair<Integer, String>> ret = new ArrayList<Pair<Integer, String>>();
                ret.add(new ImmutablePair<Integer, String>(0, "type=normal"));
            return ret;
        }
}
