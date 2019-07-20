package mod.nethertweaks.blocks;

import java.util.Random;

import mod.nethertweaks.INames;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.handler.BlockHandler;
import mod.nethertweaks.handler.ItemHandler;
import mod.sfhcore.blocks.CubeContainerHorizontal;
import mod.sfhcore.blocks.CubeFacingHorizontal;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class AshBonePile extends CubeContainerHorizontal
{
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final PropertyBool LIT = PropertyBool.create("lit");
	
	public AshBonePile() {
		super(Material.SAND, new ResourceLocation(NetherTweaksMod.MODID, INames.ASH_BONE_PILE));
		this.setTickRandomly(true);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(LIT, false));
		setHardness(0.8F);
		setCreativeTab(NetherTweaksMod.TABNTM);
		setSoundType(SoundType.SAND);
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if(!world.isBlockLoaded(pos)) return false;
		if(world.isRemote) return true;
		if(player.isSneaking()) return false;
		
		if(player.getHeldItem(hand).getItem() == ItemHandler.COILED_SWORD)
		{
			if (!player.capabilities.isCreativeMode) {
				player.setHeldItem(hand, ItemStack.EMPTY);
			}
			world.setBlockState(pos, state.withProperty(LIT, true));
		}
		
		return world.getBlockState(pos).getValue(LIT);
	}
	
	@Override
    public int getLightValue(IBlockState state)
	{
        return state.getValue(LIT) ? 8 : 0;
	}
	
	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state,
			int fortune)
	{
		if(state.getValue(LIT))
			drops.add(new ItemStack(ItemHandler.COILED_SWORD));
		
		drops.add(new ItemStack(ItemHandler.ASH, 4));
		drops.add(new ItemStack(Items.BONE, 2));
	}
	
	@Override
    public int tickRate(World worldIn) {
        return 20;
    }

    @Override
    public boolean getTickRandomly() {
        return true;
    }
    
    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {}

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {FACING, LIT});
    }
    
	@Override public boolean isTopSolid(IBlockState state) { return false; }
    @Override public boolean isTranslucent(IBlockState state) {	return true; }
    @Override @Deprecated public boolean isFullCube(IBlockState state) { return false; }
    @Override @Deprecated public boolean isFullBlock(IBlockState state) { return false; }
    @Override @Deprecated public boolean isOpaqueCube(IBlockState state) { return false; }
}
