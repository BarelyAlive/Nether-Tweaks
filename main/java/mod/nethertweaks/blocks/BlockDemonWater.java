package mod.nethertweaks.blocks;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import mod.nethertweaks.INames;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.handler.BucketNFluidHandler;
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
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

class FluidDemonWater extends Fluid{

	private static ResourceLocation still = new ResourceLocation("nethertweaksmod:blocks/block_demon_water_still");
	private static ResourceLocation flowing = new ResourceLocation("nethertweaksmod:blocks/block_demon_water_flow");
	
	public FluidDemonWater() {
		super(INames.DEMONWATERFLUID, still, flowing);
		setDensity(2000);
        setViscosity(2000);
		FluidRegistry.registerFluid(this);
		setUnlocalizedName(INames.DEMONWATERFLUID);
	}
}

public class BlockDemonWater extends BlockFluidClassic{

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
        public BlockRenderLayer getBlockLayer()
        {
            return BlockRenderLayer.SOLID;
        }
        
        @Override
        public Fluid getFluid() {
        return BucketNFluidHandler.fluidDemonWater;
        }
}
