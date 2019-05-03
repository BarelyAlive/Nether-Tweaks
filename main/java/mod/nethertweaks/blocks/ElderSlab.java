package mod.nethertweaks.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.handler.BlockHandler;
import mod.nethertweaks.interfaces.INames;
import mod.sfhcore.proxy.IVariantProvider;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


public class ElderSlab extends BlockSlab implements IVariantProvider
{

	static final PropertyEnum<ElderSlab.Variant> VARIANT = PropertyEnum.create( "variant", Variant.class );

	public ElderSlab()
	{
		super( BlockHandler.ELDERPLANKS.getMaterial( BlockHandler.ELDERPLANKS.getDefaultState() ) );
		this.setHardness( BlockHandler.ELDERPLANKS.getBlockHardness( BlockHandler.ELDERPLANKS.getDefaultState(), null, null ) );
		this.setResistance( BlockHandler.ELDERPLANKS.getExplosionResistance( null ) * 5.0F / 3.0F );

		IBlockState iblockstate = this.blockState.getBaseState();

		if( !this.isDouble() )
		{
			iblockstate = iblockstate.withProperty( HALF, BlockSlab.EnumBlockHalf.BOTTOM );
		}

		this.setDefaultState( iblockstate.withProperty( VARIANT, Variant.DEFAULT ) );
		this.useNeighborBrightness = true;
		if(!this.isDouble())
		{
			this.setCreativeTab(NetherTweaksMod.tabNTM);
		}
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta( int meta )
	{
		IBlockState iblockstate = this.getDefaultState().withProperty( VARIANT, Variant.DEFAULT );

		if( !this.isDouble() )
		{
			iblockstate = iblockstate.withProperty( HALF, ( meta & 1 ) == 0 ? BlockSlab.EnumBlockHalf.BOTTOM : BlockSlab.EnumBlockHalf.TOP );
		}

		return iblockstate;
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState( IBlockState state )
	{
		int i = 0;

		if( !this.isDouble() && state.getValue( HALF ) == BlockSlab.EnumBlockHalf.TOP )
		{
			return 1;
		}

		return i;
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return this.isDouble() ? new BlockStateContainer( this, VARIANT ) : new BlockStateContainer( this, HALF, VARIANT );
	}

	@Override
	@Nullable
	public Item getItemDropped( IBlockState state, Random rand, int fortune )
	{
		return Item.getItemFromBlock( this );
	}

	@Override
	public ItemStack getItem( World worldIn, BlockPos pos, IBlockState state )
	{
		return new ItemStack( this, 1, 0 );
	}

	@Override
	public String getUnlocalizedName( int meta )
	{
		return this.getUnlocalizedName();
	}

	@Override
	public IProperty<?> getVariantProperty()
	{
		return VARIANT;
	}

	@Override
	public Comparable<?> getTypeForItem( ItemStack stack )
	{
		return Variant.DEFAULT;
	}

	public static class Double extends ElderSlab
	{

		private final Block halfSlabBlock;

		public Double( Block halfSlabBlock, Block block )
		{
			super();
			this.halfSlabBlock = halfSlabBlock;
			this.setRegistryName(NetherTweaksMod.MODID, INames.ELDERSLAB);
		}

		@Override
		public boolean isDouble()
		{
			return true;
		}

		@Override
		@Nullable
		public Item getItemDropped( IBlockState state, Random rand, int fortune )
		{
			return Item.getItemFromBlock( this.halfSlabBlock );
		}

		@Override
		public ItemStack getItem( World worldIn, BlockPos pos, IBlockState state )
		{
			return new ItemStack( this.halfSlabBlock, 1, 0 );
		}

	}

	public static class Half extends ElderSlab
	{

		public Half()
		{
			super();
			this.setRegistryName(NetherTweaksMod.MODID, INames.ELDERSLABHALF);
		}

		@Override
		public boolean isDouble()
		{
			return false;
		}
	}

	public enum Variant implements IStringSerializable
	{
		DEFAULT;

		@Override
		public String getName()
		{
			return "default";
		}
	}

	@Override
	public boolean isDouble()
	{
		return false;
	}

	@Override
	public List<Pair<Integer, String>> getVariants()
	{
		List<Pair<Integer, String>> ret = new ArrayList<Pair<Integer, String>>();
        if (!this.isDouble()) {
			ret.add(new ImmutablePair<Integer, String>(0, "half=bottom,variant=default"));
			ret.add(new ImmutablePair<Integer, String>(0, "half=top,variant=default"));
		}
        else
        	ret.add(new ImmutablePair<Integer, String>(0, "variant=default"));
		return ret;
	}
}