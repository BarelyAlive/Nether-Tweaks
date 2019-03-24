package mod.nethertweaks.blocks.tileentities;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import mod.nethertweaks.Config;
import mod.nethertweaks.INames;
import mod.nethertweaks.blocks.container.ContainerCondenser;
import mod.nethertweaks.handler.BucketNFluidHandler;
import mod.nethertweaks.handler.NTMDryHandler;
import mod.sfhcore.helper.FluidHelper;
import mod.sfhcore.helper.StackUtils;
import mod.sfhcore.tileentities.TileEntityBase;
import mod.sfhcore.tileentities.TileEntityFluidBase;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.entity.layers.LayerWolfCollar;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerFurnace;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public class TileEntityCondenser extends TileEntityFluidBase implements net.minecraftforge.fluids.capability.IFluidHandler {
		
	private static final int MAX_CAPACITY = 16000;
	private int amount;
	private FluidStack fluid = new FluidStack(FluidRegistry.WATER, 0);
	private int mb;
	public FluidTank tank = new FluidTank(fluid, mb);
	private List<Fluid> lf = new ArrayList<Fluid>();
	
    public TileEntityCondenser(String field) {
		super(3, field, 16000);
		this.maxworkTime = Config.dryTimeCondenser;
		this.lf.add(FluidRegistry.WATER);
		setAcceptedFluids(lf);
	}

	@Override
    public void update() {
		if(!world.isRemote) {
			drainFromItem();
			if(checkInv() && workTime < maxworkTime) {
				workTime++;
			}
			dry(machineItemStacks.get(1).getItem(), machineItemStacks.get(0).getItem());
		}
	}
	
	public void dry(Item material, Item bucket){
		if(workTime == maxworkTime)
		{
			tank.fill(new FluidStack(FluidRegistry.WATER, amount), true);
			workTime = 0;
		}
	return;
	}
	
	public boolean checkHeatSource(){
		World world = getWorld();
		Block block = world.getBlockState(pos.add(0, -1, 0)).getBlock();
		if(block.getDefaultState().getMaterial() == Material.FIRE){
			maxworkTime = ((maxworkTime / 10) * 9);
			return true;
		}
		if(block.getDefaultState().getMaterial() == Material.LAVA){
			if(block instanceof BlockFluidClassic) {
				int lavatime = ((maxworkTime / 10) * 8);
				int lavaheat = FluidRegistry.LAVA.getTemperature();
				int blockheat = BlockFluidClassic.getTemperature(world, pos);
				if(maxworkTime > lavatime) {
					int heattime = lavatime * Math.floorDiv(lavaheat, blockheat);
					if(lavatime > heattime) {
						maxworkTime = heattime;
					}else {
						maxworkTime = lavatime;			
					}
				}
			}
			return true;
		}
		return false;
	}
	
	public boolean checkInv(){
		if(NTMDryHandler.containsItem(machineItemStacks.get(0))) {
			amount = NTMDryHandler.getItem(machineItemStacks.get(1).getItem(), machineItemStacks.get(1).getItemDamage()).value;
			StackUtils.substractFromStackSize(machineItemStacks.get(0), 1);
			checkHeatSource();
			return true;
		}
		workTime = 0;
		return false;
	}
	
	private void drainFromItem(){
		if(FluidUtil.getFluidHandler(machineItemStacks.get(1)) != null) {
			FluidUtil.tryFluidTransfer(FluidUtil.getFluidHandler(machineItemStacks.get(2)), tank, Integer.MAX_VALUE, true);
			if(machineItemStacks.get(2).isEmpty()) {
				StackUtils.substractFromStackSize(machineItemStacks.get(1), 1);
				machineItemStacks.add(2, machineItemStacks.get(1));
			}
		}
    }

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.mb = compound.getInteger("volume");
		this.workTime = compound.getInteger("worktime");
		ItemStackHelper.loadAllItems(compound, this.machineItemStacks);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("volume", this.mb);
		compound.setInteger("worktime", workTime);
		ItemStackHelper.saveAllItems(compound, this.machineItemStacks);
		return compound;
	}
    
	@Override
	public IFluidTankProperties[] getTankProperties() {
		IFluidTankProperties[] prop = new IFluidTankProperties[fluid.amount];
		return prop;
		}
	
    public String getGuiID()
    {
        return "nethertweaksmod:GuiCondenser";
    }

    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
    {
        return new ContainerCondenser(playerInventory, this);
    }
}