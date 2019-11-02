package mod.nethertweaks.block;

import java.util.Random;

import mod.nethertweaks.world.WorldGenElderTree;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;


public class ElderSapling extends BlockBush implements IPlantable, IGrowable
{
	public static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, 1);
	protected static final AxisAlignedBB SAPLING_AABB = new AxisAlignedBB(0.09999999403953552D, 0.0D, 0.09999999403953552D, 0.8999999761581421D, 0.800000011920929D, 0.8999999761581421D);

	public ElderSapling()
	{
		setSoundType(SoundType.PLANT);
		setDefaultState(blockState.getBaseState().withProperty(STAGE, 0));
	}

	@Override
	public AxisAlignedBB getBoundingBox(final IBlockState state, final IBlockAccess source, final BlockPos pos) {
		return SAPLING_AABB;
	}

	@Override
	public void updateTick(final World world, final BlockPos pos, final IBlockState state, final Random rand) {
		if (!world.isRemote)
		{
			super.updateTick(world, pos, state, rand);

			if (rand.nextInt(7) == 0)
				this.grow(world, pos, state, rand);
		}
	}

	public void grow(final World world, final BlockPos pos, final IBlockState state, final Random rand)
	{
		if (state.getValue(STAGE) == 0)
			world.setBlockState(pos, state.cycleProperty(STAGE), 4);
		else
			generateTree(world, pos, state, rand);
	}

	public void generateTree(final World world, final BlockPos pos, final IBlockState state, final Random rand)
	{
		if (!net.minecraftforge.event.terraingen.TerrainGen.saplingGrowTree(world, rand, pos)) return;
		final WorldGenerator worldgenerator = new WorldGenElderTree(true);
		final int i = 0;
		final int j = 0;
		final boolean flag = false;

		worldgenerator.generate(world, rand, pos);

		final IBlockState iblockstate2 = Blocks.AIR.getDefaultState();

		world.setBlockState(pos, iblockstate2, 4);

		if (!worldgenerator.generate(world, rand, pos.add(i, 0, j)))
			world.setBlockState(pos, state, 4);
	}

	@Override
	public boolean canGrow(final World world, final BlockPos pos, final IBlockState state, final boolean isClient) {
		return true;
	}

	@Override
	public boolean canUseBonemeal(final World world, final Random rand, final BlockPos pos, final IBlockState state) {
		return world.rand.nextFloat() < 0.45D;
	}

	@Override
	public void grow(final World world, final Random rand, final BlockPos pos, final IBlockState state) {
		this.grow(world, pos, state, rand);
	}

	@Override
	public IBlockState getStateFromMeta(final int meta) {
		return getDefaultState().withProperty(STAGE, (meta & 8) >> 3);
	}

	@Override
	public int getMetaFromState(final IBlockState state) {
		int i = 0;
		i = i | state.getValue(STAGE) << 3;
		return i;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, STAGE);
	}

	@Override
	protected boolean canSustainBush(final IBlockState state) {
		return state.getBlock() == Blocks.NETHERRACK;
	}

	@Override
	public EnumPlantType getPlantType(final IBlockAccess world, final BlockPos pos) {
		return EnumPlantType.Nether;
	}
}