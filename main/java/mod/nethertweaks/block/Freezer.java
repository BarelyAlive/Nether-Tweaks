package mod.nethertweaks.block;

import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.block.tile.TileFreezer;
import mod.nethertweaks.init.GuiHandler;
import mod.sfhcore.blocks.CubeContainerHorizontal;
import mod.sfhcore.util.TankUtil;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;

public class Freezer extends CubeContainerHorizontal
{
	private static final PropertyDirection FACING = BlockHorizontal.FACING;

	public Freezer()
	{
		super(Material.ROCK);
		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		setResistance(2f);
		setHardness(3.5f);
	}

	@Override
	public boolean onBlockActivated(final World world, final BlockPos pos, final IBlockState state, final EntityPlayer player,
			final EnumHand hand, final EnumFacing facing, final float hitX, final float hitY, final float hitZ)
	{
		if(!world.isBlockLoaded(pos)) return false;
		final TileFreezer te = (TileFreezer) world.getTileEntity(pos);
		if(te == null) return false;
		if(world.isRemote) return true;
		if(player.isSneaking()) return false;

		boolean success;
		success = TankUtil.drainWaterFromBottle(te, player, te.getTank());
		if(success) return true;

		final IFluidHandlerItem item = FluidUtil.getFluidHandler(player.getHeldItem(hand));

		if (item != null) {
			success = FluidUtil.interactWithFluidHandler(player, hand, te.getTank());
			if(success) return true;
		}
		player.openGui(NetherTweaksMod.getInstance(), GuiHandler.ID_FREEZER, world, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, FACING);
	}

	@Override
	public TileEntity createNewTileEntity(final World world, final int meta) {
		return new TileFreezer();
	}
}