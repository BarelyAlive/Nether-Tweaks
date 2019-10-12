package mod.nethertweaks.blocks;

import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.blocks.tile.TileHellmart;
import mod.nethertweaks.handler.GuiHandler;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Hellmart extends BlockContainer
{
	public Hellmart()
	{
		super(Material.ROCK);
		setSoundType(SoundType.STONE);
		setResistance(2F);
		setHardness(3.5F);
	}

	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.SOLID;
	}

	@Override
	public EnumBlockRenderType getRenderType(final IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public boolean onBlockActivated(final World world, final BlockPos pos, final IBlockState state, final EntityPlayer player, final EnumHand hand,
			final EnumFacing side, final float hitX, final float hitY, final float hitZ)
	{
		if(!world.isBlockLoaded(pos)) return false;
		if(world.isRemote) return true;
		if(player.isSneaking()) return false;

		TileHellmart te = (TileHellmart) world.getTileEntity(pos);
		if(te == null) return false;

        player.openGui(NetherTweaksMod.getInstance(), GuiHandler.ID_HELLMART, world, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}

	@Override
	public boolean hasTileEntity(final IBlockState state) {
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(final World world, final int meta) {
		return new TileHellmart();
	}
}