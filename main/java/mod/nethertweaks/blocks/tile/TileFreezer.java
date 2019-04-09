package mod.nethertweaks.blocks.tile;

import java.util.ArrayList;
import java.util.List;

import mod.nethertweaks.Config;
import mod.nethertweaks.blocks.Freezer;
import mod.nethertweaks.blocks.container.ContainerCondenser;
import mod.nethertweaks.blocks.container.ContainerFreezer;
import mod.nethertweaks.blocks.tile.TileBarrel.BarrelMode;
import mod.sfhcore.helper.FluidHelper;
import mod.sfhcore.helper.StackUtils;
import mod.sfhcore.tileentities.TileEntityBase;
import mod.sfhcore.tileentities.TileEntityFluidBase;
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

public class TileFreezer extends TileEntityFluidBase implements net.minecraftforge.fluids.capability.IFluidHandler {
	
	private List<Fluid> lf = new ArrayList<Fluid>();
	
	public TileFreezer(String field) {
		super(3, field, 16000);
		maxworkTime = Config.freezeTimeFreezer;
		this.lf.add(FluidRegistry.WATER);
		setAcceptedFluids(lf);
	}
	
	World world;
	ItemStack ice = new ItemStack(Blocks.ICE, 1);
	
    @Override
	public void update() {
		world = getWorld();
		
		fillFromItem();
		
		if(canFreeze()) {
			this.workTime++;
			System.out.println(workTime);
			if(workTime >= this.maxworkTime) {
				workTime = 0;
				freezeItem();
			}
		}
	}
	
	private boolean canFreeze()
    {
        if (world.isBlockPowered(pos) && this.tank.amount >= 1000)
        {
        	return true;
        }
        return false;
    }

    public void freezeItem()
    {
    	this.tank.amount -= 1000;
        if (this.machineItemStacks.get(0).isEmpty())
        {
            this.setInventorySlotContents(0, new ItemStack(ice.getItem()));
            return;
        }
        else if (this.machineItemStacks.get(0).getItem() == ice.getItem())
        {
        	ItemStack output = this.getStackInSlot(0);
        	StackUtils.addToStackSize(output, 1);
        	this.setInventorySlotContents(0, output);
            return;
        }
    }
    
    private void fillFromItem(){
    	if(
    			!machineItemStacks.get(2).isEmpty() 
    		&& (
    				machineItemStacks.get(1).isEmpty() 
    			|| (
    					machineItemStacks.get(2).getItem().getContainerItem().equals(machineItemStacks.get(1).getItem()) 
    				&& this.machineItemStacks.get(1).getCount() < this.machineItemStacks.get(1).getMaxStackSize()
    				)
    			)
    		) {
    		FluidStack input_stack = FluidUtil.getFluidContained(machineItemStacks.get(2));
    		if (input_stack != null)
    		{
	    		IFluidHandlerItem input_handler = FluidUtil.getFluidHandler(machineItemStacks.get(2));
	    		if(input_stack.getFluid() == FluidRegistry.WATER){
	        		int accepted_amount = this.fill(input_stack, true);
	        		input_handler.drain(accepted_amount, true);
	    		}
	        	if(FluidUtil.getFluidContained(machineItemStacks.get(2)).amount == 1000) {
	        		if(machineItemStacks.get(1).isEmpty() || machineItemStacks.get(1).getCount() < machineItemStacks.get(1).getMaxStackSize())
	        		{
	        			StackUtils.substractFromStackSize(machineItemStacks.get(2), 1);
	        		}
	            	if(!machineItemStacks.get(1).isEmpty()) {
	            		StackUtils.addToStackSize(machineItemStacks.get(1), 1);
	            	}
	            	else
	            	{
	            		machineItemStacks.set(1, new ItemStack(Items.BUCKET));
	            	}
	        	}
    		}
    	}
    }

	@Override
	public IFluidTankProperties[] getTankProperties() {
		IFluidTankProperties[] prop = new IFluidTankProperties[fluid.amount];
		return prop;
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
	    
	    public String getGuiID()
    {
        return "nethertweaksmod:GuiFreezer";
    }

    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
    {
        return new ContainerFreezer(playerInventory, this);
    }
}