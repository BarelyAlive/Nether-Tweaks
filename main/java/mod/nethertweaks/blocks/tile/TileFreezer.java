package mod.nethertweaks.blocks.tile;

import java.util.ArrayList;
import java.util.List;

import mod.nethertweaks.INames;
import mod.nethertweaks.blocks.Freezer;
import mod.nethertweaks.blocks.container.ContainerCondenser;
import mod.nethertweaks.blocks.container.ContainerFreezer;
import mod.nethertweaks.config.Config;
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
	public void update()
    {
    	if(world.isRemote) return;
    	
    	checkInputOutput();
    	fillFromItem();
    	
		NetworkHandler.sendNBTUpdate(this);
		
		if(!canFreeze())
		{
			this.setWorkTime(0);
			return;
		}
		
		work();
		
		if(getWorkTime() >= this.getMaxworkTime())
		{
			this.setWorkTime(0);
			freezeItem();
		}
	}
    
    private void checkInputOutput()
	{
		extractFromInventory(pos.up(), EnumFacing.DOWN);
    	insertToInventory(pos.north(), EnumFacing.SOUTH);
    	insertToInventory(pos.south(), EnumFacing.NORTH);
    	insertToInventory(pos.west(), EnumFacing.EAST);
    	insertToInventory(pos.east(), EnumFacing.WEST);
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
    }
    
	private void fillFromItem()
	{
		ItemStack input  = this.getStackInSlot(2).copy();
		ItemStack output = this.getStackInSlot(1).copy();
	
		if(input.isEmpty()) return;
	
		IFluidHandlerItem handler = FluidUtil.getFluidHandler(input);
	
		if(handler != null)
		{
    		FluidStack f = FluidUtil.tryFluidTransfer(this.getTank(), handler, Integer.MAX_VALUE, false);
    		if (f == null) return;
			
			FluidUtil.tryFluidTransfer(this.getTank(), handler, Integer.MAX_VALUE, true);
		
			System.out.println(handler.getContainer());
			
			if(output.isEmpty())
			{
				this.setInventorySlotContents(1, new ItemStack(handler.getContainer().getItem()));
				this.getStackInSlot(2).shrink(1);
				System.out.println(getStackInSlot(2));
			}
			if(ItemStack.areItemsEqual(output, handler.getContainer()))
			{
				this.getStackInSlot(1).grow(1);
				this.decrStackSize(2, 1);
			}
			//this.setInventorySlotContents(2, ItemStack.EMPTY);
		}
    	
		if(ItemStack.areItemStacksEqual(this.getStackInSlot(2), TankUtil.WATER_BOTTLE))
		{
			if(getTank().getFluidAmount() < getTank().getCapacity())
			{
       			this.getTank().fill(new FluidStack(FluidRegistry.WATER, 250), true);
       
       			if(output.isEmpty())
				setInventorySlotContents(1, new ItemStack(Items.GLASS_BOTTLE));
       
       			else if(ItemStack.areItemsEqual(output, new ItemStack(Items.GLASS_BOTTLE)))
				this.getStackInSlot(1).grow(1);
        		
				this.decrStackSize(2, 1);
			}
		}
	}
    
	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		IFluidHandlerItem handler = FluidUtil.getFluidHandler(stack);
		if(this.getStackInSlot(index).getCount() == this.getStackInSlot(index).getMaxStackSize()) return false;
		
		switch (index) {
		case 0:
			return false;
		case 1:
			return false;
		case 2:
			if(ItemStack.areItemStacksEqual(stack, TankUtil.WATER_BOTTLE)) return true;
			if(handler ==  null) return false;
			if(FluidUtil.tryFluidTransfer(this.getTank(), handler, Integer.MAX_VALUE, false) == null) return false;
		}
		
		return true;
	}
	
	@Override
	public boolean isItemValidForSlotToExtract(int index, ItemStack itemStack) {
		return index != 2;
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
