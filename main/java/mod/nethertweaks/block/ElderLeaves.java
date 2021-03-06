package mod.nethertweaks.block;

import java.util.List;
import java.util.Objects;
import java.util.Random;

import javax.annotation.Nullable;

import mod.nethertweaks.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ElderLeaves extends BlockLeaves implements IShearable
{
	public static final PropertyBool DECAYABLE = PropertyBool.create("decayable");
	public static final PropertyBool CHECK_DECAY = PropertyBool.create("check_decay");
	int[] surroundings;

	public ElderLeaves()
	{
		super();
		setDefaultState(blockState.getBaseState().withProperty(CHECK_DECAY, false).withProperty(DECAYABLE, true));
		setTickRandomly(true);
		setHardness(0.2F);
		setLightOpacity(1);
		setSoundType(SoundType.PLANT);
	}

	@Override
	public void updateTick(final World world, final BlockPos pos, final IBlockState state, final Random rand)
	{
		if(world.isRemote)
			if(leavesFancy != Minecraft.getMinecraft().gameSettings.fancyGraphics)
			{
				leavesFancy = Minecraft.getMinecraft().gameSettings.fancyGraphics;
				world.setBlockState(pos, state, 3);
			}

		if (state.getValue(CHECK_DECAY) && state.getValue(DECAYABLE))
		{
			final int i = 4;
			final int j = 5;
			final int k = pos.getX();
			final int l = pos.getY();
			final int i1 = pos.getZ();
			final int j1 = 32;
			final int k1 = 1024;
			final int l1 = 16;

			if (surroundings == null)
				surroundings = new int[32768];

			if (world.isAreaLoaded(new BlockPos(k - 5, l - 5, i1 - 5), new BlockPos(k + 5, l + 5, i1 + 5)))
			{
				final BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

				for (int i2 = -4; i2 <= 4; ++i2)
					for (int j2 = -4; j2 <= 4; ++j2)
						for (int k2 = -4; k2 <= 4; ++k2)
						{
							final IBlockState iblockstate = world.getBlockState(blockpos$mutableblockpos.setPos(k + i2, l + j2, i1 + k2));
							final Block block = iblockstate.getBlock();

							final int i3 = (i2 + 16) * 1024 + (j2 + 16) * 32 + k2 + 16;
							if (!block.canSustainLeaves(iblockstate, world, blockpos$mutableblockpos.setPos(k + i2, l + j2, i1 + k2)))
							{
								if (block.isLeaves(iblockstate, world, blockpos$mutableblockpos.setPos(k + i2, l + j2, i1 + k2)))
									surroundings[i3] = -2;
								else
									surroundings[i3] = -1;
							} else
								surroundings[i3] = 0;
						}

				for (int i3 = 1; i3 <= 4; ++i3)
					for (int j3 = -4; j3 <= 4; ++j3)
						for (int k3 = -4; k3 <= 4; ++k3)
							for (int l3 = -4; l3 <= 4; ++l3) {
								final int i2 = (j3 + 16) * 1024 + (k3 + 16) * 32 + l3 + 16;
								if (surroundings[i2] == i3 - 1)
								{
									final int i4 = (j3 + 16 - 1) * 1024 + (k3 + 16) * 32 + l3 + 16;
									if (surroundings[i4] == -2)
										surroundings[i4] = i3;

									final int i5 = (j3 + 16 + 1) * 1024 + (k3 + 16) * 32 + l3 + 16;
									if (surroundings[i5] == -2)
										surroundings[i5] = i3;

									final int i6 = (j3 + 16) * 1024 + (k3 + 16 - 1) * 32 + l3 + 16;
									if (surroundings[i6] == -2)
										surroundings[i6] = i3;

									final int i7 = (j3 + 16) * 1024 + (k3 + 16 + 1) * 32 + l3 + 16;
									if (surroundings[i7] == -2)
										surroundings[i7] = i3;

									if (surroundings[i2 - 1] == -2)
										surroundings[i2 - 1] = i3;

									final int i8 = (j3 + 16) * 1024 + (k3 + 16) * 32 + l3 + 16 + 1;
									if (surroundings[i8] == -2)
										surroundings[i8] = i3;
								}
							}
			}

			final int l2 = surroundings[16912];

			if (l2 >= 0)
				world.setBlockState(pos, state.withProperty(CHECK_DECAY, Boolean.FALSE), 4);
			else
				destroy(world, pos);
		}
	}

	private void destroy(final World world, final BlockPos pos)
	{
		dropBlockAsItem(world, pos, world.getBlockState(pos), 0);
		world.setBlockToAir(pos);
	}

	@Override
	public int quantityDropped(final Random random)
	{
		return random.nextInt(20) == 0 ? 1 : 0;
	}

	/**
	 * Get the Item that this Block should drop when harvested.
	 */
	@Nullable
	@Override
	public Item getItemDropped(final IBlockState state, final Random rand, final int fortune)
	{
		return Item.getItemFromBlock(ModBlocks.ELDER_SAPLING);
	}

	@Override
	public void dropBlockAsItemWithChance(final World world, final BlockPos pos, final IBlockState state, final float chance, final int fortune)
	{
		super.dropBlockAsItemWithChance(world, pos, state, chance, fortune);
	}

	@Override
	protected int getSaplingDropChance(final IBlockState state)
	{
		return 20;
	}

	@Override
	public boolean isOpaqueCube(final IBlockState state)
	{
		return !leavesFancy;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public BlockRenderLayer getBlockLayer()
	{
		return leavesFancy ? BlockRenderLayer.CUTOUT_MIPPED : BlockRenderLayer.SOLID;
	}

	@Override
	public boolean isTranslucent(final IBlockState state) {
		return true;
	}

	@Override
	public BlockPlanks.EnumType getWoodType(final int meta) {
		return null;
	}

	@Override public boolean isShearable(final ItemStack item, final IBlockAccess world, final BlockPos pos){ return true; }
	@Override public boolean isLeaves(final IBlockState state, final IBlockAccess world, final BlockPos pos){ return true; }

	@Override
	public void beginLeavesDecay(final IBlockState state, final World world, final BlockPos pos)
	{
		if (!state.getValue(CHECK_DECAY))
			world.setBlockState(pos, state.withProperty(CHECK_DECAY, true), 1);
	}

	@Override
	public java.util.List<ItemStack> getDrops(final IBlockAccess world, final BlockPos pos, final IBlockState state, final int fortune)
	{
		final java.util.List<ItemStack> ret = new java.util.ArrayList<>();
		final Random rand = world instanceof World ? ((World)world).rand : new Random();
		int chance = getSaplingDropChance(state);

		if (fortune > 0)
		{
			chance -= 2 << fortune;
			if (chance < 10) chance = 10;
		}

		if (rand.nextInt(chance) == 0)
			ret.add(new ItemStack(Objects.requireNonNull(getItemDropped(state, rand, fortune)), 1, damageDropped(state)));

		chance = 200;
		if (fortune > 0)
		{
			chance -= 10 << fortune;
			if (chance < 40) chance = 40;
		}

		captureDrops(true);
		ret.addAll(captureDrops(false));
		return ret;
	}


	@SideOnly(Side.CLIENT)
	@Override
	public boolean shouldSideBeRendered(final IBlockState blockState, final IBlockAccess blockAccess, final BlockPos pos, final EnumFacing side)
	{
		return leavesFancy || blockAccess.getBlockState(pos.offset(side)).getBlock() != this;//super.shouldSideBeRendered(blockState, blockAccess, pos, side);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, CHECK_DECAY, DECAYABLE);
	}

	@Override
	public IBlockState getStateFromMeta(final int meta) {
		return getDefaultState().withProperty(CHECK_DECAY, meta != 0);
	}

	@Override
	public int getMetaFromState(final IBlockState state) {

		final boolean cd = state.getValue(CHECK_DECAY);

		if(!cd) return 0;
		return 1;

	}

	@Override
	public List<ItemStack> onSheared(final ItemStack item, final IBlockAccess world, final BlockPos pos, final int fortune) {
		final java.util.List<ItemStack> ret = new java.util.ArrayList<>();
		ret.add(new ItemStack(ModBlocks.ELDER_LEAVES));

		return ret;
	}
}
