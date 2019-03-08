package mod.nethertweaks.blocks;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import mod.nethertweaks.NTMItems;
import mod.nethertweaks.NetherTweaksMod;
import mod.sfhcore.proxy.IVariantProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockDemonWater extends BlockFluidClassic{

	
        public BlockDemonWater(Fluid fluid, Material material) {
                super(fluid, Material.WATER);
                setCreativeTab(NetherTweaksMod.tabNetherTweaksMod);
                setResistance(500.0F);
                setHardness(100.0F);
                setDensity(1000);
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
        return NTMItems.fluidDemonWater;
        }
}
