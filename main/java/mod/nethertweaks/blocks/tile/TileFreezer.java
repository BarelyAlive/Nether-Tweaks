package mod.nethertweaks.blocks.tile;

import java.util.ArrayList;
import java.util.List;

import mod.nethertweaks.Config;
import mod.nethertweaks.blocks.Freezer;
import mod.nethertweaks.blocks.container.ContainerCondenser;
import mod.nethertweaks.blocks.container.ContainerFreezer;
import mod.nethertweaks.interfaces.INames;
import mod.sfhcore.blocks.tiles.TileFluidInventory;
import mod.sfhcore.network.MessageNBTUpdate;
import mod.sfhcore.network.NetworkHandler;
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
	private List<Fluid> lf = new ArrayList<Fluid>();
	final ItemStack ice = new ItemStack(Blocks.ICE, 1);
	
	public TileFreezer() {
		super(3, INames.TEFREEZER, 16000);
		this.setWorkTime(Config.freezeTimeFreezer);
		this.lf.add(FluidRegistry.WATER);
		setAcceptedFluids(lf);
	}
	
    @Override
	public void update() {
    	if (this.world.isRemote) return;
    	
    	fillFromItem();  
    	
		if(!canFreeze()) {
			this.setWorkTime(0);
			return;
		}		
		
		work();
		NetworkHandler.sendNBTUpdate(this);
		
		if(getWorkTime() >= this.getMaxworkTime()) {
			setWorkTime(0);
			freezeItem();
		}
	}
	
	private boolean canFreeze()
    {
		if(!world.isBlockPowered(pos)) return false;
        if (this.getTank().getFluidAmount() < 1000) return false;
        if(this.getStackInSlot(0).getCount() == ice.getMaxStackSize()) return false;
        return true;
    }

    private void freezeItem()
    {
    	this.getTank().drain(1000, true);
    	
        if (this.getStackInSlot(0).isEmpty())
        	
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
    	if(!input.isEmpty())
    		container = new ItemStack(input.getItem().getContainerItem());
    	
    	if(output.getCount() == output.getMaxStackSize()) return;
    	if(input.isEmpty()) return;
    	if(FluidUtil.getFluidHandler(input) == null) return;
    	if(FluidUtil.getFluidContained(input) == null) return;
    	if(FluidUtil.getFluidContained(input).getFluid() == null) return;
    	if(!hasAcceptedFluids(FluidUtil.getFluidContained(input).getFluid())) return;
    	if(this.emptyRoom() == 0) return;
    	if(!output.isEmpty() && !input.isEmpty() && !ItemStack.areItemsEqual(container, output)) return;
    	if(FluidUtil.getFluidHandler(input).drain(this.emptyRoom(), false).amount == 0) return;
    	
		FluidStack input_stack = FluidUtil.getFluidContained(input);
		IFluidHandlerItem input_handler = FluidUtil.getFluidHandler(input);
		
		if(FluidUtil.tryFluidTransfer(this.getTank(), input_handler, this.emptyRoom(), true) == null) return;
		
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
    
	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		ItemStack slot;
		IFluidHandlerItem handler;
		switch(index)
		{
		case 2:
			if(FluidUtil.getFluidHandler(stack) == null) return false;
			if(FluidUtil.getFluidContained(stack).getFluid() != FluidRegistry.WATER) return false;
			if(!this.getStackInSlot(2).isEmpty()) return false;
		}
		return true;
	}
	
	@Override
	public boolean isItemValidForSlotToExtract(int index, ItemStack itemStack) {
		if (index != 2) return true;
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
