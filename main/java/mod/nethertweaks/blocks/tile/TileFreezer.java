package mod.nethertweaks.blocks.tile;

import java.util.ArrayList;
import java.util.List;

import mod.nethertweaks.Config;
import mod.nethertweaks.blocks.Freezer;
import mod.nethertweaks.blocks.container.ContainerCondenser;
import mod.nethertweaks.blocks.container.ContainerFreezer;
import mod.nethertweaks.interfaces.INames;
import mod.sfhcore.blocks.tiles.TileFluidInventory;
import mod.sfhcore.fluid.FluidTankSingle;
import mod.sfhcore.network.MessageNBTUpdate;
import mod.sfhcore.network.NetworkHandler;
import mod.sfhcore.util.ItemStackItemHandler;
import mod.sfhcore.util.ItemUtil;
import mod.sfhcore.util.TankUtil;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import scala.Int;

public class TileFreezer extends TileFluidInventory
{	
	final ItemStack ice = new ItemStack(Blocks.ICE, 1);
	
	public TileFreezer() {
		super(3, INames.TEFREEZER, new FluidTankSingle(FluidRegistry.WATER, 0, 16000));
		this.setMaxworkTime(Config.freezeTimeFreezer);
	}
	
    @Override
	public void update() {
    	if(this.world.isRemote) return;
    	
    	checkInputOutput();
    	fillFromItem();
    	
		NetworkHandler.sendNBTUpdate(this);
		if(!canFreeze()) {
			this.setWorkTime(0);
			return;
		}		
		
		work();
		
		if(getWorkTime() >= this.getMaxworkTime()) {
			setWorkTime(0);
			freezeItem();
		}
	}
    
    private void checkInputOutput()
	{
		extractFromInventory(pos.up(), EnumFacing.DOWN);
    	insertToInventory(pos.north(), EnumFacing.UP);
    	insertToInventory(pos.south(), EnumFacing.UP);
    	insertToInventory(pos.west(), EnumFacing.UP);
    	insertToInventory(pos.east(), EnumFacing.UP);
    	insertToInventory(pos.north(), EnumFacing.WEST);
    	insertToInventory(pos.south(), EnumFacing.WEST);
    	insertToInventory(pos.west(), EnumFacing.WEST);
    	insertToInventory(pos.east(), EnumFacing.WEST);
    	insertToInventory(pos.north(), EnumFacing.SOUTH);
    	insertToInventory(pos.south(), EnumFacing.SOUTH);
    	insertToInventory(pos.west(), EnumFacing.SOUTH);
    	insertToInventory(pos.east(), EnumFacing.SOUTH);
    	insertToInventory(pos.north(), EnumFacing.NORTH);
    	insertToInventory(pos.south(), EnumFacing.NORTH);
    	insertToInventory(pos.west(), EnumFacing.NORTH);
    	insertToInventory(pos.east(), EnumFacing.NORTH);
    	insertToInventory(pos.north(), EnumFacing.EAST);
    	insertToInventory(pos.south(), EnumFacing.EAST);
    	insertToInventory(pos.west(), EnumFacing.EAST);
    	insertToInventory(pos.east(), EnumFacing.EAST);
	}
	
	private boolean canFreeze()
    {
		if(!world.isBlockPowered(pos)) return false;
        if(this.getTank().getFluidAmount() < 1000) return false;
        if(this.getStackInSlot(0).getCount() == ice.getMaxStackSize()) return false;
        return true;
    }

    private void freezeItem()
    {
    	this.getTank().drain(1000, true);
    	
        if(this.getStackInSlot(0).isEmpty())	
            this.setInventorySlotContents(0, new ItemStack(ice.getItem()));
        
        else if(this.getStackInSlot(0).getCount() >= 1)   	
        	this.getStackInSlot(0).grow(1);
        
        fillFromItem();
        this.markDirty();
    }
    
	private void fillFromItem()
    {
    	ItemStack input = getStackInSlot(2);
    	ItemStack output = getStackInSlot(1);
    	ItemStack container = ItemStack.EMPTY;
    	if(input.isEmpty()) return;
    		container = new ItemStack(input.getItem().getContainerItem());    	
    	if(output.getCount() == output.getMaxStackSize()) return;
    	IFluidHandlerItem handler = FluidUtil.getFluidHandler(input);
    	
    	if(handler != null)
    	{
	    	if(FluidUtil.getFluidContained(input) == null) return;
	    	if(!output.isEmpty() && !input.isEmpty() && !ItemStack.areItemsEqual(container, output)) return;
	    	if(FluidUtil.tryFluidTransfer(this.getTank(), handler, Integer.MAX_VALUE, false) == null) return;
	    	
			FluidStack input_stack = FluidUtil.getFluidContained(input);
			
			if(FluidUtil.tryFluidTransfer(this.getTank(), handler, Integer.MAX_VALUE, true) != null)
			{
				if(!container.isEmpty())
				{
					if(!output.isEmpty())
					{
						getStackInSlot(1).grow(1);
						getStackInSlot(2).shrink(1);
					}
					else
					{
						setInventorySlotContents(1, container);
						getStackInSlot(2).shrink(1);
					}
				}
				else
				{
					if(output.isEmpty())
					{
						setInventorySlotContents(1, input);
						getStackInSlot(2).shrink(1);
					}
					else
					{
						getStackInSlot(1).grow(1);
						getStackInSlot(2).shrink(1);
					}
				}
			}
		}
		else if(ItemStack.areItemStacksEqual(getStackInSlot(2), TankUtil.WATER_BOTTLE))
		{
			if(getTank().getFluidAmount() < getTank().getCapacity() && (getTank().getFluid() == null || getTank().getFluid().getFluid() == FluidRegistry.WATER))
			{
                getTank().fill(new FluidStack(FluidRegistry.WATER, 250), true);
                
                if(output.isEmpty()) {
    				setInventorySlotContents(1, new ItemStack(Items.GLASS_BOTTLE));
    				getStackInSlot(2).shrink(1);
    			}
                else if(ItemStack.areItemsEqual(output, new ItemStack(Items.GLASS_BOTTLE)))
        		{
                	getStackInSlot(1).grow(1);
    				getStackInSlot(2).shrink(1);
        		}
            }
		}
    }
    
	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		IFluidHandlerItem handler = FluidUtil.getFluidHandler(stack);
		if(this.getStackInSlot(index).getCount() == this.getStackInSlot(index).getMaxStackSize()) return false;
		
		if(index == 0)
			return false;
		if(index == 1)
			return false;
		if(index == 2) {
			if(ItemStack.areItemStacksEqual(stack, TankUtil.WATER_BOTTLE)) return true;
			if(FluidUtil.getFluidHandler(stack) == null) return false;
			if(FluidUtil.tryFluidTransfer(this.getTank(), handler, Integer.MAX_VALUE, false) == null) return false;
		}
		return true;
	}
	
	@Override
	public boolean isItemValidForSlotToExtract(int index, ItemStack itemStack) {
		if(index == 0) return true;
		if(index == 1) return true;
		return false;
	}
	    
	public String getGuiID()
    {
        return "nethertweaksmod:gui_freezer";
    }

    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
    {
        return new ContainerFreezer(playerInventory, this);
    }
}
