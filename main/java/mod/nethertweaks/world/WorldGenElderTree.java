package mod.nethertweaks.world;

import java.util.Random;

import mod.nethertweaks.init.ModBlocks;
import net.minecraft.block.BlockVine;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class WorldGenElderTree extends WorldGenAbstractTree
{
	private static final IBlockState DEFAULT_TRUNK = ModBlocks.ELDER_LOG.getDefaultState();
	private static final IBlockState DEFAULT_LEAF = ModBlocks.ELDER_LEAVES.getDefaultState();
	/** The minimum height of a generated tree. */
	private final int minTreeHeight;
	/** True if this tree should grow Vines. */
	private final boolean vinesGrow;
	/** The metadata value of the wood to use in tree generation. */
	private final IBlockState metaWood;
	/** The metadata value of the leaves to use in tree generation. */
	private final IBlockState metaLeaves;

	public WorldGenElderTree(final boolean notify)
	{
		this(notify, 4, DEFAULT_TRUNK, DEFAULT_LEAF, true);
	}

	public WorldGenElderTree(final boolean notify, final int minTreeHeight, final IBlockState metaWood, final IBlockState metaLeaves, final boolean vinesGrow)
	{
		super(false);
		this.minTreeHeight = minTreeHeight;
		this.metaWood = metaWood;
		this.metaLeaves = metaLeaves;
		this.vinesGrow = vinesGrow;
	}

	//have to override this to prevent vanilla from generating dirt
	@Override
	protected void setDirtAt(final World world, final BlockPos pos)
	{
		if (world.getBlockState(pos) != Blocks.NETHERRACK.getDefaultState())
			setBlockAndNotifyAdequately(world, pos, Blocks.NETHERRACK.getDefaultState());
	}

	@Override
	public boolean generate(final World world, final Random rand, final BlockPos position)
	{
		final int i = rand.nextInt(3) + minTreeHeight;
		boolean flag = true;

		if (position.getY() >= 1 && position.getY() + i + 1 <= world.getHeight())
		{
			for (int j = position.getY(); j <= position.getY() + 1 + i; ++j)
			{
				int k = 1;

				if (j == position.getY())
					k = 0;

				if (j >= position.getY() + 1 + i - 2)
					k = 2;

				final BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

				for (int l = position.getX() - k; l <= position.getX() + k && flag; ++l)
					for (int i1 = position.getZ() - k; i1 <= position.getZ() + k && flag; ++i1)
						if (j >= 0 && j < world.getHeight())
						{
							if (!isReplaceable(world,blockpos$mutableblockpos.setPos(l, j, i1)))
								flag = false;
						} else
							flag = false;
			}

			if (!flag)
				return false;
			else
			{
				IBlockState state = world.getBlockState(position.down());

				if (state.getBlock().canSustainPlant(state, world, position.down(), net.minecraft.util.EnumFacing.UP, ModBlocks.ELDER_SAPLING) && position.getY() < world.getHeight() - i - 1)
				{
					setDirtAt(world, position.down());
					final int k2 = 3;
					final int l2 = 0;

					for (int i3 = position.getY() - 3 + i; i3 <= position.getY() + i; ++i3)
					{
						final int i4 = i3 - (position.getY() + i);
						final int j1 = 1 - i4 / 2;

						for (int k1 = position.getX() - j1; k1 <= position.getX() + j1; ++k1)
						{
							final int l1 = k1 - position.getX();

							for (int i2 = position.getZ() - j1; i2 <= position.getZ() + j1; ++i2)
							{
								final int j2 = i2 - position.getZ();

								if (Math.abs(l1) != j1 || Math.abs(j2) != j1 || rand.nextInt(2) != 0 && i4 != 0)
								{
									final BlockPos blockpos = new BlockPos(k1, i3, i2);
									state = world.getBlockState(blockpos);

									if (state.getBlock().isAir(state, world, blockpos) || state.getBlock().isLeaves(state, world, blockpos) || state.getMaterial() == Material.VINE)
										setBlockAndNotifyAdequately(world, blockpos, metaLeaves);
								}
							}
						}
					}

					for (int j3 = 0; j3 < i; ++j3)
					{
						final BlockPos upN = position.up(j3);
						state = world.getBlockState(upN);

						if (state.getBlock().isAir(state, world, upN) || state.getBlock().isLeaves(state, world, upN) || state.getMaterial() == Material.VINE)
						{
							setBlockAndNotifyAdequately(world, position.up(j3), metaWood);

							if (vinesGrow && j3 > 0)
							{
								if (rand.nextInt(3) > 0 && world.isAirBlock(position.add(-1, j3, 0)))
									addVine(world, position.add(-1, j3, 0), BlockVine.EAST);

								if (rand.nextInt(3) > 0 && world.isAirBlock(position.add(1, j3, 0)))
									addVine(world, position.add(1, j3, 0), BlockVine.WEST);

								if (rand.nextInt(3) > 0 && world.isAirBlock(position.add(0, j3, -1)))
									addVine(world, position.add(0, j3, -1), BlockVine.SOUTH);

								if (rand.nextInt(3) > 0 && world.isAirBlock(position.add(0, j3, 1)))
									addVine(world, position.add(0, j3, 1), BlockVine.NORTH);
							}
						}
					}

					if (vinesGrow)
						for (int k3 = position.getY() - 3 + i; k3 <= position.getY() + i; ++k3)
						{
							final int j4 = k3 - (position.getY() + i);
							final int k4 = 2 - j4 / 2;
							final BlockPos.MutableBlockPos blockpos$mutableblockpos1 = new BlockPos.MutableBlockPos();

							for (int l4 = position.getX() - k4; l4 <= position.getX() + k4; ++l4)
								for (int i5 = position.getZ() - k4; i5 <= position.getZ() + k4; ++i5)
								{
									blockpos$mutableblockpos1.setPos(l4, k3, i5);

									state = world.getBlockState(blockpos$mutableblockpos1);
									if (state.getBlock().isLeaves(state, world, blockpos$mutableblockpos1))
									{
										final BlockPos blockpos2 = blockpos$mutableblockpos1.west();
										final BlockPos blockpos3 = blockpos$mutableblockpos1.east();
										final BlockPos blockpos4 = blockpos$mutableblockpos1.north();
										final BlockPos blockpos1 = blockpos$mutableblockpos1.south();

										if (rand.nextInt(4) == 0 && world.isAirBlock(blockpos2))
											addHangingVine(world, blockpos2, BlockVine.EAST);

										if (rand.nextInt(4) == 0 && world.isAirBlock(blockpos3))
											addHangingVine(world, blockpos3, BlockVine.WEST);

										if (rand.nextInt(4) == 0 && world.isAirBlock(blockpos4))
											addHangingVine(world, blockpos4, BlockVine.SOUTH);

										if (rand.nextInt(4) == 0 && world.isAirBlock(blockpos1))
											addHangingVine(world, blockpos1, BlockVine.NORTH);
									}
								}
						}

					return true;
				} else
					return false;
			}
		} else
			return false;
	}

	private void addVine(final World world, final BlockPos pos, final PropertyBool prop)
	{
		setBlockAndNotifyAdequately(world, pos, ModBlocks.MEAN_VINE.getDefaultState().withProperty(prop, true));
	}

	private void addHangingVine(final World world, BlockPos pos, final PropertyBool prop)
	{
		addVine(world, pos, prop);
		int i = 4;

		for (pos = pos.down(); world.isAirBlock(pos) && i > 0; --i)
		{
			addVine(world, pos, prop);
			pos = pos.down();
		}
	}
}