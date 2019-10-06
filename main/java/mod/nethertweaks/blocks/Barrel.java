package mod.nethertweaks.blocks;

import javax.annotation.Nonnull;

import mod.nethertweaks.Constants;
import mod.nethertweaks.barrel.modes.block.BarrelModeBlock;
import mod.nethertweaks.barrel.modes.compost.BarrelModeCompost;
import mod.nethertweaks.barrel.modes.fluid.BarrelModeFluid;
import mod.nethertweaks.barrel.modes.fluid.BarrelModeFluidTransform;
import mod.nethertweaks.blocks.tile.TileBarrel;
import mod.nethertweaks.config.Config;
import mod.sfhcore.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class Barrel extends Block implements ITileEntityProvider
{
	private final AxisAlignedBB boundingBox = new AxisAlignedBB(0.0625f, 0, 0.0625f, 0.9375f, 1f, 0.9375f);
	private final int tier;

	public Barrel(final int tier, final Material material, final String name) {
		super(material);
		this.tier = tier;
		setHardness(2.0f);
		setRegistryName(Constants.MODID, name);
	}

	@Override
	public void breakBlock(@Nonnull final World worldIn, @Nonnull final BlockPos pos, @Nonnull final IBlockState state) {
		TileEntity te = worldIn.getTileEntity(pos);
		if (te instanceof TileBarrel) {
			TileBarrel barrel = (TileBarrel) te;

			if (barrel.getMode() != null && barrel.getMode().getName().equals("block")) {
				IItemHandler barrelCap = barrel.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
				if (barrelCap != null) {
					ItemStack stack = barrelCap.getStackInSlot(0);
					if (!stack.isEmpty())
						Util.dropItemInWorld(te, null, stack, 0);
				}
			}
		}

		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public int getLightValue(@Nonnull final IBlockState state, final IBlockAccess world, @Nonnull final BlockPos pos) {
		TileEntity te = world.getTileEntity(pos);

		if(!(te instanceof TileBarrel)) return super.getLightValue(state, world, pos);
		if(!Config.enableBarrelTransformLighting) return super.getLightValue(state, world, pos);

		TileBarrel tile = (TileBarrel) te;
		if (tile.getMode() instanceof BarrelModeBlock) {
			BarrelModeBlock mode = (BarrelModeBlock) tile.getMode();

			if (mode.getBlock() != null)
				return Block.getBlockFromItem(mode.getBlock().getItem()).getStateFromMeta(mode.getBlock().getMeta()).getLightValue();
		} else if (tile.getMode() instanceof BarrelModeFluid) {
			BarrelModeFluid mode = (BarrelModeFluid) tile.getMode();

			if (mode.getFluidHandler(tile).getFluidAmount() > 0)
				return Util.getLightValue(mode.getFluidHandler(tile).getFluid());
		} else if (tile.getMode() instanceof BarrelModeCompost) {
			BarrelModeCompost mode = (BarrelModeCompost) tile.getMode();

			if (mode.getCompostState() != null) {
				int value = mode.getCompostState().getLightValue() / 2;

				return Math.round(Util.weightedAverage((float) value / 2, value, mode.getProgress()));
			}
		} else if (tile.getMode() instanceof BarrelModeFluidTransform) {
			BarrelModeFluidTransform mode = (BarrelModeFluidTransform) tile.getMode();

			int inputValue = Util.getLightValue(mode.getInputStack());
			int outputValue = Util.getLightValue(mode.getOutputStack());

			return Math.round(Util.weightedAverage(outputValue, inputValue, mode.getProgress()));
		}

		return super.getLightValue(state, world, pos);
	}

	@Override
	public boolean onBlockActivated(final World worldIn, final BlockPos pos, final IBlockState state, final EntityPlayer playerIn, final EnumHand hand, final EnumFacing facing, final float hitX, final float hitY, final float hitZ)
	{
		if(!worldIn.isBlockLoaded(pos)) return false;
		if(worldIn.isRemote) return true;
		TileEntity te = worldIn.getTileEntity(pos);
		if(!(te instanceof TileBarrel)) return false;
		if (((TileBarrel) te).getMode() != null)
			if (((TileBarrel) te).getMode().getName().equals("fluid"))
				if(((BarrelModeFluid)((TileBarrel) te).getMode()).workTime > 0)
					return true;
		return ((TileBarrel) te).onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
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
	public TileEntity createNewTileEntity(@Nonnull final World worldIn, final int meta) {
		return new TileBarrel(this);
	}

	@Override
	@Nonnull
	@Deprecated
	public AxisAlignedBB getBoundingBox(final IBlockState state, final IBlockAccess source, final BlockPos pos) {
		return boundingBox;
	}

	@Override
	public boolean isTopSolid(final IBlockState state) {
		return false;
	}

	@Override
	@Deprecated
	public boolean isFullCube(final IBlockState state) {
		return false;
	}

	public int getTier() {
		return tier;
	}

	// Barrels will attempt to milk entities
	@Override
	public void onEntityWalk(final World worldIn, final BlockPos pos, final Entity entityIn) {
		TileEntity te = worldIn.getTileEntity(pos);
		if (!(te instanceof TileBarrel)) return;
		((TileBarrel) te).entityOnTop(worldIn, entityIn);
	}
}