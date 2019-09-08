package mod.nethertweaks.blocks;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import mod.nethertweaks.INames;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.blocks.tile.TileAshBonePile;
import mod.nethertweaks.handler.GuiHandler;
import mod.nethertweaks.handler.ItemHandler;
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
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class AshBonePile extends CubeContainerHorizontal
{
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final PropertyBool LIT = PropertyBool.create("lit");

	public AshBonePile() {
		super(Material.SAND, new ResourceLocation(NetherTweaksMod.MODID, INames.ASH_BONE_PILE));
		setTickRandomly(true);
		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(LIT, false));
		setHardness(0.8F);
		setCreativeTab(NetherTweaksMod.TABNTM);
		setSoundType(SoundType.SAND);
	}

	private BlockPos testPosition(final World world, final BlockPos destination)
	{
		if (!world.isRemote) {
			boolean north = world.isAirBlock(destination.north()) && world.isAirBlock(destination.north().up()) && world.isSideSolid(destination.north().down(), EnumFacing.UP);
			boolean east = world.isAirBlock(destination.east()) && world.isAirBlock(destination.east().up()) && world.isSideSolid(destination.east().down(), EnumFacing.UP);
			boolean south = world.isAirBlock(destination.south()) && world.isAirBlock(destination.south().up()) && world.isSideSolid(destination.south().down(), EnumFacing.UP);
			boolean west = world.isAirBlock(destination.west()) && world.isAirBlock(destination.west().up()) && world.isSideSolid(destination.west().down(), EnumFacing.UP);

			boolean northEast = world.isAirBlock(destination.north().east()) && world.isAirBlock(destination.north().east().up()) && world.isSideSolid(destination.north().east().down(), EnumFacing.UP);
			boolean southEast = world.isAirBlock(destination.east().south()) && world.isAirBlock(destination.east().south().up()) && world.isSideSolid(destination.east().south().down(), EnumFacing.UP);
			boolean southWest = world.isAirBlock(destination.south().west()) && world.isAirBlock(destination.south().west().up()) && world.isSideSolid(destination.south().west().down(), EnumFacing.UP);
			boolean northWest = world.isAirBlock(destination.west().north()) && world.isAirBlock(destination.west().north().up()) && world.isSideSolid(destination.west().north().down(), EnumFacing.UP);

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

		BlockPos resultPos = testPosition(world, pos);

		if(resultPos != null && !world.isRemote)
			if (world.getBlockState(pos).getValue(LIT) == true)
				if (WorldSpawnLocation.bonfire_info.containsKey(pos))
				{
					BonfireInfo info = WorldSpawnLocation.bonfire_info.get(pos);
					if(info != null)
					{
						info.setSpawnPos(resultPos);
						NetworkHandler.sendToServer(new MessageBonfireUpdate(UpdateStatus.UPDATE, pos, info));
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

		TileAshBonePile te = (TileAshBonePile) world.getTileEntity(pos);

		if (world.getBlockState(pos).getValue(LIT) == false)
		{
			if(player.getHeldItem(hand).getItem() == ItemHandler.COILED_SWORD)
			{
				if (!player.capabilities.isCreativeMode)
					player.setHeldItem(hand, ItemStack.EMPTY);
				world.setBlockState(pos, state.withProperty(LIT, true), 3);
				te.isLit(true);
				if (!WorldSpawnLocation.bonfire_info.containsKey(pos))
				{
					BlockPos resultPos = testPosition(world, pos);
					BonfireInfo info = new BonfireInfo(player.getUniqueID(), world.provider.getDimension());
					if(resultPos != null)
						info.setSpawnPos(resultPos);
					WorldSpawnLocation.bonfire_info.put(pos, info);
					NetworkHandler.INSTANCE.sendToServer(new MessageBonfireUpdate(UpdateStatus.ADD, pos, info));
				}
			}
		} else
			player.openGui(NetherTweaksMod.getInstance(), GuiHandler.ID_BONFIRE, world, pos.getX(), pos.getY(), pos.getZ());

		return world.getBlockState(pos).getValue(LIT);
	}

	@Override
	public int getMetaFromState(final IBlockState state) {
		if (state.getValue(LIT))
			return 1;
		else
			return 0;
	}

	@Override
	public IBlockState getStateFromMeta(final int meta) {
		return getDefaultState().withProperty(LIT, meta == 0 ? false : true);
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
			drops.add(new ItemStack(ItemHandler.COILED_SWORD));

		drops.add(new ItemStack(ItemHandler.ASH, 4));
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
		return new BlockStateContainer(this, new IProperty[] {FACING, LIT});
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
		if (WorldSpawnLocation.bonfire_info.containsKey(pos) && !world.isRemote)
		{
			BonfireInfo binfo = WorldSpawnLocation.bonfire_info.get(pos);

			List<UUID> player_list = binfo.getLastPlayerSpawn();
			if (player_list.size() != 0)
				for(UUID entry : player_list)
					if (WorldSpawnLocation.lastSpawnLocations.containsKey(entry))
					{
						EntityPlayer player = world.getPlayerEntityByUUID(entry);
						player.sendMessage(new TextComponentString(player.getName() + "'s point of rest is lost!"));
						WorldSpawnLocation.lastSpawnLocations.remove(entry);
						NetworkHandler.sendToServer(new MessageLastSpawnUpdate(UpdateStatus.REMOVE, null, entry));
					}
			WorldSpawnLocation.bonfire_info.remove(pos);
			NetworkHandler.sendToServer(new MessageBonfireUpdate(UpdateStatus.REMOVE, pos, null));
		}
	}

	@Override
	public TileEntity createNewTileEntity(final World p_149915_1_, final int p_149915_2_) {
		return new TileAshBonePile();
	}

	@Override public boolean isTopSolid(final IBlockState state) { return false; }
	@Override public boolean isTranslucent(final IBlockState state) {	return true; }
	@Override @Deprecated public boolean isFullCube(final IBlockState state) { return false; }
	@Override @Deprecated public boolean isFullBlock(final IBlockState state) { return false; }
	@Override @Deprecated public boolean isOpaqueCube(final IBlockState state) { return false; }


}
