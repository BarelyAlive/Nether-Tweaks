package mod.nethertweaks.block;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.block.tile.TileAshBonePile;
import mod.nethertweaks.init.GuiHandler;
import mod.nethertweaks.init.ModItems;
import mod.nethertweaks.network.bonfire.MessageBonfireUpdate;
import mod.nethertweaks.network.bonfire.MessageLastSpawnUpdate;
import mod.nethertweaks.network.bonfire.UpdateStatus;
import mod.nethertweaks.world.BonfireInfo;
import mod.nethertweaks.world.WorldSpawnLocation;
import mod.sfhcore.blocks.CubeContainerHorizontal;
import mod.sfhcore.network.NetworkHandler;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AshBonePile extends CubeContainerHorizontal
{
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final PropertyBool LIT = PropertyBool.create("lit");

	public AshBonePile() {
		super(Material.SAND);
		setTickRandomly(true);
		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(LIT, false));
		setHardness(0.8F);
		setSoundType(SoundType.SAND);
	}

	private BlockPos testPosition(final World world, final BlockPos destination)
	{
		if (!world.isRemote) {
			final boolean north = world.isAirBlock(destination.north()) && world.isAirBlock(destination.north().up()) && world.isSideSolid(destination.north().down(), EnumFacing.UP);
			final boolean east = world.isAirBlock(destination.east()) && world.isAirBlock(destination.east().up()) && world.isSideSolid(destination.east().down(), EnumFacing.UP);
			final boolean south = world.isAirBlock(destination.south()) && world.isAirBlock(destination.south().up()) && world.isSideSolid(destination.south().down(), EnumFacing.UP);
			final boolean west = world.isAirBlock(destination.west()) && world.isAirBlock(destination.west().up()) && world.isSideSolid(destination.west().down(), EnumFacing.UP);

			final boolean northEast = world.isAirBlock(destination.north().east()) && world.isAirBlock(destination.north().east().up()) && world.isSideSolid(destination.north().east().down(), EnumFacing.UP);
			final boolean southEast = world.isAirBlock(destination.east().south()) && world.isAirBlock(destination.east().south().up()) && world.isSideSolid(destination.east().south().down(), EnumFacing.UP);
			final boolean southWest = world.isAirBlock(destination.south().west()) && world.isAirBlock(destination.south().west().up()) && world.isSideSolid(destination.south().west().down(), EnumFacing.UP);
			final boolean northWest = world.isAirBlock(destination.west().north()) && world.isAirBlock(destination.west().north().up()) && world.isSideSolid(destination.west().north().down(), EnumFacing.UP);

			if(north) return destination.north();
			if(east) return destination.east();
			if(south) return destination.south();
			if(west) return destination.west();

			if(northEast) return destination.north().east();
			if(southEast) return destination.south().east();
			if(southWest) return destination.south().west();
			if(northWest) return destination.north().west();
		}

		return null;
	}

	@Override
	public void updateTick(final World world, final BlockPos pos, final IBlockState state, final Random rand) {
		super.updateTick(world, pos, state, rand);

		final BlockPos resultPos = testPosition(world, pos);

		if(resultPos != null && !world.isRemote)
			if (world.getBlockState(pos).getValue(LIT))
				if (WorldSpawnLocation.getBonfireInfo().containsKey(pos))
				{
					final BonfireInfo info = WorldSpawnLocation.getBonfireInfo().get(pos);
					if(info != null)
					{
						info.setSpawnPos(resultPos);
						if (world.isRemote)
							NetworkHandler.INSTANCE.sendToServer(new MessageBonfireUpdate(UpdateStatus.UPDATE, pos, info));
					}
				}
	}

	@Override
	public boolean onBlockActivated(final World world, final BlockPos pos, final IBlockState state, final EntityPlayer player,
			final EnumHand hand, final EnumFacing facing, final float hitX, final float hitY, final float hitZ)
	{
		if(!world.isBlockLoaded(pos)) return false;
		if(world.isRemote) return true;
		if(player.isSneaking()) return false;

		final TileAshBonePile te = (TileAshBonePile) world.getTileEntity(pos);

		if (!world.getBlockState(pos).getValue(LIT))
		{
			if(player.getHeldItem(hand).getItem() == ModItems.COILED_SWORD)
			{
				if (!player.capabilities.isCreativeMode)
					player.setHeldItem(hand, ItemStack.EMPTY);
				world.setBlockState(pos, state.withProperty(LIT, true), 3);
				Objects.requireNonNull(te).isLit(true);
				if (!WorldSpawnLocation.getBonfireInfo().containsKey(pos))
				{
					final BlockPos resultPos = testPosition(world, pos);
					final BonfireInfo info = new BonfireInfo(EntityPlayer.getUUID(player.getGameProfile()), world.provider.getDimension());
					if(resultPos != null)
						info.setSpawnPos(resultPos);
					WorldSpawnLocation.getBonfireInfo().put(pos, info);
					NetworkHandler.INSTANCE.sendToAll(new MessageBonfireUpdate(UpdateStatus.ADD, pos, info));
				}
			}
		} else
			player.openGui(NetherTweaksMod.getInstance(), GuiHandler.ID_BONFIRE, world, pos.getX(), pos.getY(), pos.getZ());

		return world.getBlockState(pos).getValue(LIT);
	}

	@Override
	public int getMetaFromState(final IBlockState state) {
		return state.getValue(LIT) ? 1 : 0;
	}

	@Override
	public IBlockState getStateFromMeta(final int meta) {
		return getDefaultState().withProperty(LIT, meta != 0);
	}

	@Override
	public int getLightValue(final IBlockState state)
	{
		return state.getValue(LIT) ? 8 : 0;
	}

	@Override
	public void getDrops(final NonNullList<ItemStack> drops, final IBlockAccess world, final BlockPos pos, final IBlockState state,
			final int fortune)
	{
		if(state.getValue(LIT))
			drops.add(new ItemStack(ModItems.COILED_SWORD));

		drops.add(new ItemStack(ModItems.ASH, 4));
		drops.add(new ItemStack(Items.BONE, 2));
	}

	@Override
	public int tickRate(final World worldIn) {
		return 20;
	}

	@Override
	public boolean getTickRandomly() {
		return true;
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, FACING, LIT);
	}
	@Override
	public void onBlockHarvested(final World world, final BlockPos pos, final IBlockState state, final EntityPlayer player)
	{
		super.onBlockHarvested(world, pos, state, player);
		onBlockDestroy(world, pos);
	}


	@Override
	public void onBlockDestroyedByExplosion(final World world, final BlockPos pos, final Explosion explosionIn) {
		super.onBlockDestroyedByExplosion(world, pos, explosionIn);
		onBlockDestroy(world, pos);
	}

	@Override
	public void onBlockDestroyedByPlayer(final World world, final BlockPos pos, final IBlockState state) {
		super.onBlockDestroyedByPlayer(world, pos, state);
		onBlockDestroy(world, pos);
	}

	private void onBlockDestroy(final World world, final BlockPos pos)
	{
		if (WorldSpawnLocation.getBonfireInfo().containsKey(pos))
		{
			final BonfireInfo binfo = WorldSpawnLocation.getBonfireInfo().get(pos);

			final List<UUID> player_list = binfo.getLastPlayerSpawn();
			if (player_list.size() != 0)
				for(final UUID entry : player_list)
					if (WorldSpawnLocation.getLastSpawnLocations().containsKey(entry))
					{
						final EntityPlayer player = world.getPlayerEntityByUUID(entry);
						Objects.requireNonNull(player).sendMessage(new TextComponentString(player.getName() + "'s point of rest is lost!"));
						WorldSpawnLocation.getLastSpawnLocations().remove(entry);
						if (world.isRemote)
							NetworkHandler.INSTANCE.sendToServer(new MessageLastSpawnUpdate(UpdateStatus.REMOVE, null, entry));
					}
			WorldSpawnLocation.getBonfireInfo().remove(pos);
			if (world.isRemote)
				NetworkHandler.INSTANCE.sendToServer(new MessageBonfireUpdate(UpdateStatus.REMOVE, pos, null));
		}
	}

	private int l = 0;
	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(final IBlockState stateIn, final World world, final BlockPos pos, final Random rand) {
		if (stateIn.getValue(LIT)) {
			l++;
			if (rand.nextDouble() < 0.1D)
				world.playSound(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D,
						SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
			if (l == 1) {
				world.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D,
						rand.nextDouble() % 0.04D, rand.nextDouble() % 0.08D, rand.nextDouble() % 0.04D);
				world.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D,
						-(rand.nextDouble() % 0.04D), rand.nextDouble() % 0.08D, rand.nextDouble() % 0.04D);
				world.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D,
						rand.nextDouble() % 0.04D, rand.nextDouble() % 0.08D, rand.nextDouble() % 0.04D);
				world.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D,
						-(rand.nextDouble() % 0.04D), rand.nextDouble() % 0.08D, rand.nextDouble() % 0.04D);
				world.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D,
						rand.nextDouble() % 0.04D, rand.nextDouble() % 0.08D, -(rand.nextDouble() % 0.04D));
				world.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D,
						-(rand.nextDouble() % 0.04D), rand.nextDouble() % 0.08D, -(rand.nextDouble() % 0.04D));
				world.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D,
						rand.nextDouble() % 0.04D, rand.nextDouble() % 0.08D, -(rand.nextDouble() % 0.04D));
				world.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D,
						-(rand.nextDouble() % 0.04D), rand.nextDouble() % 0.08D, -(rand.nextDouble() % 0.04D));
				l = 0;
			}
		}
		super.randomDisplayTick(stateIn, world, pos, rand);
	}

	@Override
	public TileEntity createNewTileEntity(final World world, final int meta) {
		return new TileAshBonePile();
	}

	@Override public boolean isTopSolid(final IBlockState state) { return false; }
	@Override public boolean isTranslucent(final IBlockState state) { return true; }
	@Override @Deprecated public boolean isFullCube(final IBlockState state) { return false; }
	@Override @Deprecated public boolean isFullBlock(final IBlockState state) { return false; }
	@Override @Deprecated public boolean isOpaqueCube(final IBlockState state) { return false; }


}
