package mod.nethertweaks.blocks;

import java.util.Random;

import mod.nethertweaks.INames;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.blocks.tile.TileNetherrackFurnace;
import mod.nethertweaks.handler.GuiHandler;
import mod.sfhcore.blocks.CubeContainerHorizontal;
import mod.sfhcore.network.MessageCheckLight;
import mod.sfhcore.network.NetworkHandler;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class NetherrackFurnace extends CubeContainerHorizontal {

	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final PropertyBool ISBURNING = PropertyBool.create("is_burning");

	public NetherrackFurnace()
	{
		super(Material.ROCK, new ResourceLocation(NetherTweaksMod.MODID, INames.NETHERRACK_FURNACE));
		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(ISBURNING, false));
		setResistance(17.5F);
		setHardness(3.5F);
		setCreativeTab(NetherTweaksMod.TABNTM);
	}

	@Override
	public boolean onBlockActivated(final World world, final BlockPos pos, final IBlockState state, final EntityPlayer player, final EnumHand hand,
			final EnumFacing side, final float hitX, final float hitY, final float hitZ)
	{
		if(!world.isBlockLoaded(pos)) return false;
		if(world.isRemote) return true;
		if(player.isSneaking()) return false;

		TileEntity te = world.getTileEntity(pos);
		if(te == null) return false;
		if(!(te instanceof TileNetherrackFurnace))
			return false;

		player.openGui(NetherTweaksMod.getInstance(), GuiHandler.ID_FURNACE, world, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}

	@Override
	public int getLightValue(final IBlockState state, final IBlockAccess world, final BlockPos pos)
	{
		return state.getValue(ISBURNING) ? 12 : 0;
	}

	public static void setState(final boolean active, final World world, final BlockPos pos)
	{
		IBlockState b = world.getBlockState(pos);
		if(b == Blocks.AIR.getDefaultState()) return;
		TileEntity tileentity = world.getTileEntity(pos);
		if(tileentity == null) return;
		TileNetherrackFurnace furnace = (TileNetherrackFurnace) tileentity;

		//Only do this, if something has changed
		if(active && !b.getValue(ISBURNING)) {
			b = b.withProperty(ISBURNING, true);
			world.setBlockState(pos, b, 3);
			validate(world, pos, furnace);
		}
		else if(!active && b.getValue(ISBURNING)) {
			b = b.withProperty(ISBURNING, false);
			world.setBlockState(pos, b, 3);
			validate(world, pos, furnace);
		}
	}

	private static void validate(final World world, final BlockPos pos, final TileNetherrackFurnace furnace)
	{
		if (furnace != null)
		{
			furnace.validate();
			world.setTileEntity(pos, furnace);
			NetworkHandler.sendToAllAround(new MessageCheckLight(pos), furnace);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings("incomplete-switch")
	public void randomDisplayTick(final IBlockState stateIn, final World world, final BlockPos pos, final Random rand)
	{
		if (stateIn.getValue(ISBURNING))
		{
			EnumFacing enumfacing = stateIn.getValue(FACING);
			double d0 = pos.getX() + 0.5D;
			double d1 = pos.getY() + rand.nextDouble() * 6.0D / 16.0D;
			double d2 = pos.getZ() + 0.5D;
			double d3 = 0.52D;
			double d4 = rand.nextDouble() * 0.6D - 0.3D;

			if (rand.nextDouble() < 0.1D)
				world.playSound(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);

			switch (enumfacing)
			{
			case WEST:
				world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 - d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D, new int[0]);
				world.spawnParticle(EnumParticleTypes.FLAME, d0 - d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D, new int[0]);
				break;
			case EAST:
				world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D, new int[0]);
				world.spawnParticle(EnumParticleTypes.FLAME, d0 + d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D, new int[0]);
				break;
			case NORTH:
				world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 - d3, 0.0D, 0.0D, 0.0D, new int[0]);
				world.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 - d3, 0.0D, 0.0D, 0.0D, new int[0]);
				break;
			case SOUTH:
				world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 + d3, 0.0D, 0.0D, 0.0D, new int[0]);
				world.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 + d3, 0.0D, 0.0D, 0.0D, new int[0]);
				break;
			}
		}
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {FACING, ISBURNING});
	}

	@Override
	public TileEntity createNewTileEntity(final World world, final int meta) {
		return new TileNetherrackFurnace();
	}
}