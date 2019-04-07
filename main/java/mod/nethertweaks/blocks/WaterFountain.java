package mod.nethertweaks.blocks;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import ibxm.Player;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.blocks.tileentities.TileEntityFreezer;
import mod.nethertweaks.blocks.tileentities.TileEntityWaterFountain;
import mod.nethertweaks.interfaces.INames;
import mod.sfhcore.handler.RegisterTileEntity;
import mod.sfhcore.helper.FluidHelper;
import mod.sfhcore.helper.StackUtils;
import mod.sfhcore.proxy.IVariantProvider;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.capability.ItemFluidContainer;
import net.minecraftforge.fml.common.registry.GameRegistry;
 
public class WaterFountain extends Block implements IVariantProvider{
    
    public WaterFountain() {
        super(Material.ROCK);
        setResistance(30.0f);
		setHardness(2.0f);
        setCreativeTab(NetherTweaksMod.tabNTM);
        setUnlocalizedName(INames.WATERFOUNTAIN);
        setRegistryName("nethertweaksmod", INames.WATERFOUNTAIN);
    }
    
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
    		EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
    	if (worldIn.isBlockLoaded(pos)) {
			TileEntityWaterFountain wf = (TileEntityWaterFountain) worldIn.getTileEntity(pos);
			ItemStack heldItem = StackUtils.getPlayerHandStack(playerIn, hand);
			if (heldItem != null) {
				if (heldItem.getItem() instanceof ItemFluidContainer) {
					ItemStack filled = FluidHelper.fillContainer(heldItem, wf.getFullStack2());
					if (filled != null) {
						int a = FluidHelper.getFluidForFilledItem(heldItem.getItem()).amount;
						if (playerIn.capabilities.isCreativeMode) {
							wf.drain(a, true);
						} else if (heldItem.getCount() == 1) {
							StackUtils.substractFromStackSize(heldItem, 1);
							playerIn.inventory.addItemStackToInventory(filled);
							wf.drain(a, true);
						} else if (playerIn.inventory.hasItemStack(filled)) {
							StackUtils.substractFromStackSize(heldItem, 1);
							playerIn.inventory.addItemStackToInventory(filled);
							wf.drain(a, true);
						}
						return true;
					}
				}
			}
			return true;
		}
		return false;
    }
    
    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
    	TileEntityWaterFountain wf = (TileEntityWaterFountain) worldIn.getTileEntity(pos);
    	super.onBlockAdded(worldIn, pos, state);
    }
    
    @Override
    public boolean canPlaceTorchOnTop(IBlockState state, IBlockAccess world, BlockPos pos) {
    	return false;
    }

    @Override
    public boolean hasTileEntity() {
    	return true;
    }
    
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityWaterFountain();
	}

    public List<Pair<Integer, String>> getVariants()
    {
        List<Pair<Integer, String>> ret = new ArrayList<Pair<Integer, String>>();
        ret.add(new ImmutablePair<Integer, String>(0, "normal"));
        return ret;
    }
}