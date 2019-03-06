package mod.nethertweaks.blocks;

import mod.nethertweaks.BucketLoader;
import mod.nethertweaks.INames;
import mod.nethertweaks.NetherTweaksMod;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockDemonWater extends BlockFluidClassic {

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
        public boolean canDisplace(IBlockAccess world, BlockPos pos) {
            if (world.getBlockState(pos).getMaterial().isLiquid()) return false;
        return super.canDisplace(world, pos);
        }
        
        @Override
        public boolean displaceIfPossible(World world, BlockPos pos) {
        	if (world.getBlockState(pos).getMaterial().isLiquid()) return false;
        return super.displaceIfPossible(world, pos);
        }
        
        @Override
        public Fluid getFluid() {
        return BucketLoader.fluidDemonWater;
        }
        
        
}
