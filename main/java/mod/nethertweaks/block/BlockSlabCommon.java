package mod.nethertweaks.block;

import java.util.Random;

import mod.nethertweaks.init.ModBlocks;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class BlockSlabCommon extends BlockSlab
{
	public static final PropertyEnum<Variant> VARIANT = PropertyEnum.create("variant", Variant.class);

	public BlockSlabCommon(final Material material) {
		super(material);

		final IBlockState iblockstate = blockState.getBaseState().withProperty(VARIANT, Variant.DEFAULT);

		if(!isDouble())
			iblockstate.withProperty(HALF, BlockSlab.EnumBlockHalf.BOTTOM);

		setDefaultState(iblockstate);
		useNeighborBrightness = !isDouble();
	}

	@Override
	public String getUnlocalizedName(final int meta) {
		return super.getUnlocalizedName();
	}

	@Override
	public IProperty<?> getVariantProperty() {
		return VARIANT;
	}

	@Override
	public Comparable<?> getTypeForItem(final ItemStack stack) {
		return Variant.DEFAULT;
	}

	@Override
	public Item getItemDropped(final IBlockState state, final Random rand, final int fortune) {
		return Item.getItemFromBlock(ModBlocks.ELDER_SLAB);
	}


	@Override
	public ItemStack getItem(final World worldIn, final BlockPos pos, final IBlockState state) {
		return new ItemStack(ModBlocks.ELDER_SLAB);
	}

	@Override
	public final IBlockState getStateFromMeta(final int meta) {
		IBlockState blockstate = blockState.getBaseState().withProperty(VARIANT, Variant.DEFAULT);

		if(!isDouble())
			blockstate = blockstate.withProperty(HALF, (meta&8)!=0?EnumBlockHalf.TOP:EnumBlockHalf.BOTTOM);

		return blockstate;
	}

	@Override
	public final int getMetaFromState(final IBlockState state) {
		int meta = 0;

		if(!isDouble()&& state.getValue(HALF)==EnumBlockHalf.TOP)
			meta |= 8;

		return meta;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		if(!isDouble())
			return new BlockStateContainer(this, VARIANT, HALF);
		return new BlockStateContainer(this, VARIANT);
	}

	public static class Double extends BlockSlabCommon
	{

		public Double(final Material material) {
			super(material);
		}

		@Override
		public boolean isDouble() {
			return true;
		}

	}

	public static class Half extends BlockSlabCommon
	{

		public Half(final Material material) {
			super(material);
		}

		@Override
		public boolean isDouble() {
			return false;
		}

	}

	public enum Variant implements IStringSerializable
	{
		DEFAULT;

		@Override
		public String getName() {
			return "default";
		}

	}
}