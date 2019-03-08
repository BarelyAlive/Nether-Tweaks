package mod.nethertweaks.blocks;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import ibxm.Player;
import mod.nethertweaks.INames;
import mod.nethertweaks.NTMItems;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.blocks.tileentities.TileEntityWaterFountain;
import mod.sfhcore.helper.FluidHelper;
import mod.sfhcore.helper.Tools;
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
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
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
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.IFluidContainerItem;
import net.minecraftforge.fluids.UniversalBucket;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fluids.capability.ItemFluidContainer;
import net.minecraftforge.fml.common.registry.GameRegistry;
import scala.Int;
 
public class WaterFountain extends Block implements IVariantProvider{
    
	private FluidStack fullStack2 = new FluidStack(FluidRegistry.WATER, Int.MaxValue());
	private FluidTank tank = new FluidTank(getFullStack2(), Int.MaxValue());

    public WaterFountain() {
        super(Material.ROCK);
        setResistance(30.0f);
		setHardness(2.0f);
        setCreativeTab(NetherTweaksMod.tabNetherTweaksMod);
        GameRegistry.registerTileEntity(TileEntityWaterFountain.class, INames.TEWATERFOUNTAIN);
    }
     
    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player,
    		EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
    	TileEntityWaterFountain wf = (TileEntityWaterFountain) world.getTileEntity(pos);

    	if(!Tools.checkHandEmpty(player)){
    		if(heldItem.getItem() == Items.BUCKET){
        		player.inventory.clearMatchingItems(Items.BUCKET, 0, 1, null);
        		player.inventory.addItemStackToInventory(new ItemStack(Items.WATER_BUCKET, 1));
        		return true;
        	}
        	if(heldItem.getItem() == NTMItems.bucketStone){
        		player.inventory.clearMatchingItems(NTMItems.bucketStone, 0, 1, null);
        		player.inventory.addItemStackToInventory(new ItemStack(NTMItems.bucketStoneWater, 1));
        		return true;
        	}
        	if(heldItem.getItem() == NTMItems.bucketWood){
        		player.inventory.clearMatchingItems(NTMItems.bucketWood, 0, 1, null);
        		player.inventory.addItemStackToInventory(new ItemStack(NTMItems.bucketWoodWater, 1));
        		return true;
        	}
    	    FluidHelper.fillContainer(heldItem, FluidRegistry.WATER, 1000);
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
	
	public FluidStack getFullStack2() {
		return fullStack2;
	}
	
	public void setFullStack2(FluidStack fullStack2) {
		this.fullStack2 = fullStack2;
	}
}