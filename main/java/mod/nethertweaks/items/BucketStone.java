package mod.nethertweaks.items;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import mod.nethertweaks.INames;
import mod.nethertweaks.NetherTweaksMod;
import mod.sfhcore.proxy.IVariantProvider;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.common.registry.GameRegistry;


public class BucketStone extends Item implements IFluidHandler, IVariantProvider{
	
	private Block isFull;
	
	public BucketStone(Block containedBlock, String unlocalizedName){
        this.isFull = containedBlock;
		setUnlocalizedName(INames.BUCKETSTONE);
		setCreativeTab(NetherTweaksMod.tabNetherTweaksMod);
	}

	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
        boolean flag = this.isFull == Blocks.AIR;
        RayTraceResult raytraceresult = this.rayTrace(worldIn, playerIn, flag);
        ActionResult<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onBucketUse(playerIn, worldIn, itemStackIn, raytraceresult);
        if (ret != null) return ret;

        if (raytraceresult == null)
        {
            return new ActionResult(EnumActionResult.PASS, itemStackIn);
        }
        else if (raytraceresult.typeOfHit != RayTraceResult.Type.BLOCK)
        {
            return new ActionResult(EnumActionResult.PASS, itemStackIn);
        }
        else
        {
            BlockPos blockpos = raytraceresult.getBlockPos();

            if (!worldIn.isBlockModifiable(playerIn, blockpos))
            {
                return new ActionResult(EnumActionResult.FAIL, itemStackIn);
            }
            else if (flag)
            {
                if (!playerIn.canPlayerEdit(blockpos.offset(raytraceresult.sideHit), raytraceresult.sideHit, itemStackIn))
                {
                    return new ActionResult(EnumActionResult.FAIL, itemStackIn);
                }
                else
                {
                    IBlockState iblockstate = worldIn.getBlockState(blockpos);
                    Material material = iblockstate.getMaterial();

                    if (material == Material.WATER && iblockstate.getBlock() == Blocks.WATER && ((Integer)iblockstate.getValue(BlockLiquid.LEVEL)).intValue() == 0)
                    {
                        worldIn.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 11);
                        playerIn.addStat(StatList.getObjectUseStats(this));
                        playerIn.playSound(SoundEvents.ITEM_BUCKET_FILL, 1.0F, 1.0F);
                        return new ActionResult(EnumActionResult.SUCCESS, this.fillBucket(itemStackIn, playerIn, NTMItems.bucketStoneWater));
                    }
                    else if (material == Material.WATER && iblockstate.getBlock() == NTMItems.blockDemonWater && ((Integer)iblockstate.getValue(BlockLiquid.LEVEL)).intValue() == 0)
                    {
                        worldIn.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 11);
                        playerIn.addStat(StatList.getObjectUseStats(this));
                        playerIn.playSound(SoundEvents.ITEM_BUCKET_FILL, 1.0F, 1.0F);
                        return new ActionResult(EnumActionResult.SUCCESS, this.fillBucket(itemStackIn, playerIn, NTMItems.bucketStoneDemonWater));
                    }
                    else if (material == Material.LAVA && ((Integer)iblockstate.getValue(BlockLiquid.LEVEL)).intValue() == 0)
                    {
                        playerIn.playSound(SoundEvents.ITEM_BUCKET_FILL_LAVA, 1.0F, 1.0F);
                        worldIn.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 11);
                        playerIn.addStat(StatList.getObjectUseStats(this));
                        return new ActionResult(EnumActionResult.SUCCESS, this.fillBucket(itemStackIn, playerIn, NTMItems.bucketStoneLava));
                    }
                    else
                    {
                        return new ActionResult(EnumActionResult.FAIL, itemStackIn);
                    }
                }
            }
            else
            {
                boolean flag1 = worldIn.getBlockState(blockpos).getBlock().isReplaceable(worldIn, blockpos);
                BlockPos blockpos1 = flag1 && raytraceresult.sideHit == EnumFacing.UP ? blockpos : blockpos.offset(raytraceresult.sideHit);

                if (!playerIn.canPlayerEdit(blockpos1, raytraceresult.sideHit, itemStackIn))
                {
                    return new ActionResult(EnumActionResult.FAIL, itemStackIn);
                }
                else if (this.tryPlaceContainedLiquid(playerIn, worldIn, blockpos1))
                {
                    playerIn.addStat(StatList.getObjectUseStats(this));
                    return !playerIn.capabilities.isCreativeMode ? new ActionResult(EnumActionResult.SUCCESS, new ItemStack(NTMItems.bucketStone)) : new ActionResult(EnumActionResult.SUCCESS, itemStackIn);
                }
                else
                {
                    return new ActionResult(EnumActionResult.FAIL, itemStackIn);
                }
            }
        }
    }

    private ItemStack fillBucket(ItemStack emptyBuckets, EntityPlayer player, Item fullBucket)
    {
        if (player.capabilities.isCreativeMode)
        {
            return emptyBuckets;
        }
        else if (--emptyBuckets.stackSize <= 0)
        {
            return new ItemStack(fullBucket);
        }
        else
        {
            if (!player.inventory.addItemStackToInventory(new ItemStack(fullBucket)))
            {
                player.dropItem(new ItemStack(fullBucket), false);
            }

            return emptyBuckets;
        }
    }

    public boolean tryPlaceContainedLiquid(@Nullable EntityPlayer worldIn, World pos, BlockPos posIn)
    {
        if (this.isFull == Blocks.AIR)
        {
            return false;
        }
        else
        {
            IBlockState iblockstate = pos.getBlockState(posIn);
            Material material = iblockstate.getMaterial();
            boolean flag = !material.isSolid();
            boolean flag1 = iblockstate.getBlock().isReplaceable(pos, posIn);

            if (!pos.isAirBlock(posIn) && !flag && !flag1)
            {
                return false;
            }
            else
            {
                if (pos.provider.doesWaterVaporize() && this.isFull == Blocks.FLOWING_WATER)
                {
                    int l = posIn.getX();
                    int i = posIn.getY();
                    int j = posIn.getZ();
                    pos.playSound(worldIn, posIn, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F + (pos.rand.nextFloat() - pos.rand.nextFloat()) * 0.8F);

                    for (int k = 0; k < 8; ++k)
                    {
                        pos.spawnParticle(EnumParticleTypes.SMOKE_LARGE, (double)l + Math.random(), (double)i + Math.random(), (double)j + Math.random(), 0.0D, 0.0D, 0.0D, new int[0]);
                    }
                }
                else
                {
                    if (!pos.isRemote && (flag || flag1) && !material.isLiquid())
                    {
                        pos.destroyBlock(posIn, true);
                    }

                    SoundEvent soundevent = this.isFull == Blocks.FLOWING_LAVA ? SoundEvents.ITEM_BUCKET_EMPTY_LAVA : SoundEvents.ITEM_BUCKET_EMPTY;
                    pos.playSound(worldIn, posIn, soundevent, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    pos.setBlockState(posIn, this.isFull.getDefaultState(), 11);
                }

                return true;
            }
        }
    }

    @Override
    public net.minecraftforge.common.capabilities.ICapabilityProvider initCapabilities(ItemStack stack, net.minecraft.nbt.NBTTagCompound nbt) {
        return new net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper(stack);
    }

	@Override
	public IFluidTankProperties[] getTankProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		return 1000;
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		if(this.isFull == Blocks.FLOWING_LAVA)
			return new FluidStack(FluidRegistry.LAVA, 1000);
			if(this.isFull == Blocks.FLOWING_WATER)
				return new FluidStack(FluidRegistry.WATER, 1000);
			return null;
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		if(this.isFull == Blocks.FLOWING_LAVA)
			return new FluidStack(FluidRegistry.LAVA, 1000);
			if(this.isFull == Blocks.FLOWING_WATER)
				return new FluidStack(FluidRegistry.WATER, 1000);
			return null;
	}
	
	@Override
    public List<Pair<Integer, String>> getVariants()
    {
        List<Pair<Integer, String>> ret = new ArrayList<Pair<Integer, String>>();
            ret.add(new ImmutablePair<Integer, String>(0, "type=normal"));
        return ret;
    }
	
}