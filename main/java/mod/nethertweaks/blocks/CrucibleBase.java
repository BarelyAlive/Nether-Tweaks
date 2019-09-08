package mod.nethertweaks.blocks;

import javax.annotation.Nonnull;

import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.blocks.tile.TileCrucibleBase;
import mod.sfhcore.util.TankUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

public abstract class CrucibleBase extends Block
{
	public CrucibleBase(final String name, final Material material) {
		super(material);
		setRegistryName(name);
		setCreativeTab(NetherTweaksMod.TABNTM);
	}

	@Override
	public abstract TileEntity createTileEntity(@Nonnull World worldIn, @Nonnull IBlockState state);

	@Override
	public int damageDropped(final IBlockState state) {
		return getMetaFromState(state);
	}

	@Override
	@Nonnull
	public ItemStack getPickBlock(@Nonnull final IBlockState state, final RayTraceResult target, @Nonnull final World world, @Nonnull final BlockPos pos, final EntityPlayer player) {
		return new ItemStack(Item.getItemFromBlock(this), 1, getMetaFromState(world.getBlockState(pos)));
	}

	@Override
	public boolean onBlockActivated(final World world, final BlockPos pos, final IBlockState state, final EntityPlayer player, final EnumHand hand, final EnumFacing side, final float hitX, final float hitY, final float hitZ)
	{
		if(!world.isBlockLoaded(pos)) return false;
		if (world.isRemote) return true;
		if(player.isSneaking()) return false;

		TileCrucibleBase te = (TileCrucibleBase) world.getTileEntity(pos);

		if (te != null) {
			if(!player.getHeldItem(hand).isEmpty())
			{
				if(TankUtil.drainWaterIntoBottle(te, player, (FluidTank) te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side)))
					return true;
			}
			
			IFluidHandler fluidHandler = te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side);
			return te.onBlockActivated(world, pos, state, player, hand, side, hitX, hitY, hitZ, fluidHandler);
		} else
			return true;
	}

	@Override
	@Deprecated
	public boolean isFullBlock(final IBlockState state) {
		return false;
	}

	@Override
	@Deprecated
	public boolean isOpaqueCube(final IBlockState state) {
		return false;
	}

	@Override
	@Deprecated
	public boolean isFullCube(final IBlockState state) {
		return false;
	}
}
