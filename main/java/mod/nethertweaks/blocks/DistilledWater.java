package mod.nethertweaks.blocks;

import mod.nethertweaks.INames;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.config.Config;
import mod.nethertweaks.handler.BucketNFluidHandler;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving.SpawnPlacementType;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;


public class DistilledWater extends BlockFluidClassic
{
    public DistilledWater()
    {
        super(BucketNFluidHandler.FLUIDDISTILLEDWATER, Material.WATER);
        setRegistryName(NetherTweaksMod.MODID, INames.DISTILLEDWATER);
        setLightLevel(0);
        setTemperature(FluidRegistry.WATER.getTemperature());
        setDensity(FluidRegistry.WATER.getDensity());
    }
    
    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return getBlockState().getBaseState().withProperty(LEVEL, meta);
    }
    
    @Override
    public Fluid getFluid() {
    return BucketNFluidHandler.FLUIDDISTILLEDWATER;
    }
    
    
}
