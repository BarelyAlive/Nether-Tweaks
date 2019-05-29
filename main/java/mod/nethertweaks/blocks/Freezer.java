package mod.nethertweaks.blocks;

import mod.nethertweaks.INames;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.blocks.tile.TileFreezer;
import mod.nethertweaks.handler.GuiHandlerNTM;
import mod.sfhcore.blocks.CubeContainerHorizontal;
import mod.sfhcore.blocks.tiles.TileBase;
import mod.sfhcore.network.MessageNBTUpdate;
import mod.sfhcore.network.NetworkHandler;
import mod.sfhcore.util.TankUtil;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import p455w0rdslib.util.ChunkUtils;

public class Freezer extends CubeContainerHorizontal
{
    private static final PropertyDirection FACING = BlockHorizontal.FACING;
	
	public Freezer()
	{
		super(Material.ROCK, new ResourceLocation(NetherTweaksMod.MODID, INames.FREEZER));
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		setResistance(17.5f);
		setHardness(3.5f);
		setCreativeTab(NetherTweaksMod.TABNTM);
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if(!world.isBlockLoaded(pos)) return false;
		TileFreezer te = (TileFreezer) world.getTileEntity(pos);
		if(te == null) return false;
		if(!(te instanceof TileFreezer)) return false;
    	if(world.isRemote) return true;
    	if(player.isSneaking()) return false;
    	
		if(TankUtil.drainWaterFromBottle(te, player, te.getTank())) return true;
    	
    	IFluidHandlerItem item = FluidUtil.getFluidHandler(player.getHeldItem(hand));
    	
    	if (item != null) {
			FluidUtil.interactWithFluidHandler(player, hand, te.getTank());
			return true;
		}
		player.openGui(NetherTweaksMod.instance, GuiHandlerNTM.idFreezer, world, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
	    return new BlockStateContainer(this, new IProperty[] {FACING});
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
	    return new TileFreezer();
	}
}