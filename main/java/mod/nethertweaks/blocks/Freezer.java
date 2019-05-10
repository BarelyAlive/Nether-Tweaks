package mod.nethertweaks.blocks;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.blocks.tile.TileFreezer;
import mod.nethertweaks.blocks.tile.TileFreezer;
import mod.nethertweaks.blocks.tile.TileSieve;
import mod.nethertweaks.interfaces.INames;
import mod.sfhcore.SFHCore;
import mod.sfhcore.blocks.CubeContainerHorizontal;
import mod.sfhcore.blocks.tiles.TileFluidInventory;
import mod.sfhcore.network.MessageFluidTankContents;
import mod.sfhcore.network.MessageNBTUpdate;
import mod.sfhcore.network.NetworkHandler;
import mod.sfhcore.proxy.IVariantProvider;
import mod.sfhcore.util.*;
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
import net.minecraft.init.Items;
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
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.FluidTankProperties;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Freezer extends CubeContainerHorizontal{
	
    private static final PropertyDirection FACING = BlockHorizontal.FACING;
    private static boolean keepInventory;
    public final FluidStack WATER = new FluidStack(FluidRegistry.WATER, Integer.MAX_VALUE);
	
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
		if(!worldIn.isBlockLoaded(pos)) return false;
		TileFreezer te = (TileFreezer) worldIn.getTileEntity(pos);
		if(!(te instanceof TileFreezer)) return false;
    	//if(worldIn.isRemote) return true;
    	if(playerIn.isSneaking()) return false;
    	
    	IFluidHandlerItem handler = FluidUtil.getFluidHandler(playerIn.getHeldItem(hand));
    	boolean cap;
    	if(handler == null)
    		cap = playerIn.getHeldItem(hand).hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, facing);
    	IFluidHandler freezer = te.tank;
    	
		System.out.println((worldIn.isRemote ? "Client " : "Server ")  + te.tank.getFluidAmount());
    	
    			if(!worldIn.isRemote)
    			{
    				FluidUtil.tryFluidTransfer(te.tank, FluidUtil.getFluidHandler(playerIn.getHeldItem(hand)), WATER, true);
        			te.markDirtyClient();
    				//NetworkHandler.sendToServer(new MessageFluidTankContents(te.tank.getTankProperties(), te));
        			return true;
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
