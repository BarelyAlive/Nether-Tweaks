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
	private static List<Fluid> lf = new ArrayList<Fluid>();
	final ItemStack ice = new ItemStack(Blocks.ICE, 1);
	
	static
	{
		lf.add(FluidRegistry.WATER);
	}
	
	public TileFreezer() {
		super(3, INames.TEFREEZER, 16000);
		this.maxworkTime = Config.freezeTimeFreezer;
		this.lf.add(FluidRegistry.WATER);
		setAcceptedFluids(lf);
	}
	
    @Override
	public void update() {
    	if (this.world.isRemote) return;
    	fillFromItem();
		markDirtyClient();
		if(!canFreeze()) {
			this.workTime = 0;
			return;
		}
		
		this.workTime++;
		if(workTime >= this.maxworkTime) {
			workTime = 0;
			freezeItem();
		}
	}
	
	private boolean canFreeze()
    {
		if(!world.isBlockPowered(pos)) return false;
        if (this.tank.amount < 1000) return false;
        if(this.getStackInSlot(0).getCount() == ice.getMaxStackSize()) return false;
        return true;
    }

    private void freezeItem()
    {
    	this.drain(1000, true);
    	
        if (this.machineItemStacks.get(0).isEmpty())
        	
            this.setInventorySlotContents(0, new ItemStack(ice.getItem()));
        
        else if(this.getStackInSlot(0).getCount() >= 1)
        	
        	this.getStackInSlot(0).grow(1);
        fillFromItem();
    }
    
	private void fillFromItem()
    {
    	ItemStack input = machineItemStacks.get(2);
    	ItemStack output = machineItemStacks.get(1);
    	ItemStack container = ItemStack.EMPTY;
    	if(!input.isEmpty())
    	container = new ItemStack(input.getItem().getContainerItem());
    	
    	if(output.getCount() == output.getMaxStackSize()) return;
    	if(input.isEmpty()) return;
    	if(FluidUtil.getFluidHandler(input) == null) return;
    	if(!hasAcceptedFluids(FluidUtil.getFluidContained(input).getFluid())) return;
    	if(this.fillable() == 0) return;
    	if(!output.isEmpty() && !input.isEmpty() && !ItemStack.areItemsEqual(container, output)) return;
    	if(FluidUtil.getFluidHandler(input).drain(this.fillable(), false).amount >  this.fillable()) return;
    	
		FluidStack input_stack = FluidUtil.getFluidContained(input);
		IFluidHandlerItem input_handler = FluidUtil.getFluidHandler(input);
		
		if(FluidUtil.tryFluidTransfer(this, input_handler, this.fillable(), true) == null) return;
		
		if(!container.isEmpty())
		{
			if(!output.isEmpty())
			{
				machineItemStacks.get(1).grow(1);
				machineItemStacks.get(2).shrink(1);
			}
			else
			{
				machineItemStacks.set(1, container);
				machineItemStacks.get(2).shrink(1);
			}
		}
		else
		{
			if(output.isEmpty())
			{
				machineItemStacks.set(1, input);
				machineItemStacks.get(2).shrink(1);
			}
			else
			{
				machineItemStacks.get(1).grow(1);
				machineItemStacks.get(2).shrink(1);
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
		if (index < 2)
		{
			return true;
		}
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