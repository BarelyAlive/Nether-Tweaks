package mod.nethertweaks.blocks;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.handler.BucketNFluidHandler;
import mod.nethertweaks.interfaces.INames;
import mod.sfhcore.proxy.IVariantProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class BlockDemonWater extends BlockFluidClassic{

        public BlockDemonWater() {
                super(BucketNFluidHandler.FLUIDDEMONWATER, Material.WATER);
                setCreativeTab(NetherTweaksMod.tabNTM);
                setResistance(500.0F);
                setHardness(100.0F);
                setDensity(1000);
                setRegistryName(NetherTweaksMod.MODID, INames.DEMOMWATERBLOCK);
                setUnlocalizedName(INames.DEMOMWATERBLOCK);
                setLightLevel(3);
        }
        
        @Override
        public IBlockState getStateFromMeta(int meta)
        {
            return getBlockState().getBaseState().withProperty(LEVEL, meta);
        }
        
        @Override
        public Fluid getFluid() {
        return BucketNFluidHandler.FLUIDDEMONWATER;
        }
}
