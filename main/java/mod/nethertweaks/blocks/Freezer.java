package mod.nethertweaks.blocks;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.blocks.tile.TileCondenser;
import mod.nethertweaks.blocks.tile.TileFreezer;
import mod.nethertweaks.blocks.tile.TileSieve;
import mod.nethertweaks.interfaces.INames;
import mod.sfhcore.blocks.CubeContainerHorizontal;
import mod.sfhcore.proxy.IVariantProvider;
import mod.sfhcore.util.TankUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.EnumFaceDirection;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.FluidTankProperties;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Freezer extends CubeContainerHorizontal{
	
    private static final PropertyDirection FACING = BlockHorizontal.FACING;
    private static boolean keepInventory;
	
	public Freezer() {
		super(Material.ROCK, new ResourceLocation(NetherTweaksMod.MODID, INames.FREEZER));
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		setResistance(17.5f);
		setHardness(3.5f);
		setCreativeTab(NetherTweaksMod.TABNTM);
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (!worldIn.isBlockLoaded(pos))
    		return false;
    	if(worldIn.isRemote)
    		return false;
    	if(playerIn.isSneaking())
    		return false;
    	  	
    	TileFreezer te = (TileFreezer) worldIn.getTileEntity(pos);
		if(!(te instanceof TileFreezer)) {
			return false;
		}
		
		//Fill from player-hand item
		try {
			ItemStack held = playerIn.getHeldItem(hand);
			FluidStack f = FluidUtil.getFluidContained(held);
			IFluidHandler freezer = FluidUtil.getFluidHandler(worldIn, pos, facing);
			IFluidHandler heldFH = FluidUtil.getFluidHandler(held);
			
			if(f.getFluid() == FluidRegistry.WATER)
			{
				System.out.println(worldIn.getBlockState(pos));
				FluidUtil.tryFluidTransfer(freezer, heldFH, f, true);
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		playerIn.openGui(NetherTweaksMod.instance, 2, worldIn, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
	    return new BlockStateContainer(this, new IProperty[] {FACING});
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
	    return new TileFreezer();
	}
}
