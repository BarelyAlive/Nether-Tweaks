package mod.nethertweaks.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.handler.BlockHandler;
import mod.nethertweaks.interfaces.INames;
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


public class ElderSlab extends BlockSlab
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
		
	/*
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(playerIn.getHeldItem(hand) != null && !this.isDouble())
		{
			if(ItemStack.areItemsEqual(playerIn.getActiveItemStack(), new ItemStack(BlockHandler.ELDERSLABHALF)));
			
			doDouble(worldIn, pos, state, hand, facing, hitX, hitY, hitZ);
			
			return true;
		}
		return false;//super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
	}
	
	private void doDouble(World worldIn, BlockPos pos, IBlockState state,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if(state.getValue(HALF) == BlockSlab.EnumBlockHalf.BOTTOM)
		{
			if(facing != EnumFacing.DOWN && (facing == EnumFacing.UP || (double)hitY <= 0.5D))
			{
				worldIn.setBlockState(pos, BlockHandler.ELDERSLABDOUBLE.getDefaultState());
			}
		}
		else if(state.getValue(HALF) == BlockSlab.EnumBlockHalf.TOP)
		{
			if(facing != EnumFacing.UP && (facing == EnumFacing.DOWN || (double)hitY >= 0.5D))
			{
				worldIn.setBlockState(pos, BlockHandler.ELDERSLABDOUBLE.getDefaultState());
			}
		}
	}
	*/

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta( int meta )
	{
		IBlockState iblockstate = this.getDefaultState().withProperty( VARIANT, Variant.DEFAULT );

		if( !this.isDouble() )
		{
			iblockstate = iblockstate.withProperty( HALF, ( meta & 8 ) == 0 ? BlockSlab.EnumBlockHalf.BOTTOM : BlockSlab.EnumBlockHalf.TOP );
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
			i |= 8;
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
			this.setUnlocalizedName(INames.ELDERSLAB);
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
			this.setUnlocalizedName(INames.ELDERSLABHALF);
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
	public boolean isDouble() {
		return false;
	}
}