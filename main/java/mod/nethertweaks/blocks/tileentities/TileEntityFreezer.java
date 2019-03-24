package mod.nethertweaks.blocks.tileentities;

import mod.nethertweaks.Config;
import mod.nethertweaks.blocks.Freezer;
import mod.nethertweaks.blocks.container.ContainerCondenser;
import mod.nethertweaks.blocks.container.ContainerFreezer;
import mod.nethertweaks.blocks.tileentities.TileEntityBarrel.BarrelMode;
import mod.sfhcore.helper.FluidHelper;
import mod.sfhcore.helper.StackUtils;
import mod.sfhcore.tileentities.TileEntityBase;
import mod.sfhcore.tileentities.TileEntityFluidBase;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
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
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import scala.Int;

public class TileEntityFreezer extends TileEntityFluidBase implements net.minecraftforge.fluids.capability.IFluidHandler {
	
	private static final int MAX_CAPACITY = 16000;
	private int amount;
	private FluidStack fluid = new FluidStack(FluidRegistry.WATER, 0);
	private int mb;
	public FluidTank tank = new FluidTank(fluid, mb);
	
	public TileEntityFreezer(String field) {
		super(1, field, 16000);
		maxworkTime = Config.freezeTimeFreezer;
	}
	
	World world;
	ItemStack ice = new ItemStack(Blocks.ICE, 1);
	
    @Override
	public void update() {
		world = getWorld();
		
		fillFromItem();
		
		if(canFreeze()) {
			workTime++;
			if(workTime == maxworkTime) {
				freezeItem();
				workTime = 0;
			}
		}
	}
	
	private boolean canFreeze()
    {
        if (world.getRedstonePower(pos, EnumFacing.DOWN) > 0 && tank.getCapacity() >= 1000)
        {
        	tank.drain(1000, true);
        	return true;
        }
        return false;
    }

    public void freezeItem()
    {
        if (!world.isRemote)
        {
            if (this.machineItemStacks.get(0).isEmpty())
            {
                this.machineItemStacks.add(0, ice);
                return;
            }
            else if (this.machineItemStacks.get(0).getItem() == ice.getItem())
            {
                StackUtils.addToStackSize(ice, 1);// Forge BugFix: Results may have multiple items
                return;
            }
        }
        
    }
    
    private void fillFromItem(){
    	if(FluidUtil.getFluidContained(machineItemStacks.get(2)).getFluid() == FluidRegistry.WATER){
    		FluidUtil.tryFluidTransfer(tank, FluidUtil.getFluidHandler(machineItemStacks.get(2)), 16000, true);
    	}
    	if(FluidUtil.getFluidContained(machineItemStacks.get(2)).amount == 0) {
    		if(machineItemStacks.get(1).isEmpty() || machineItemStacks.get(1).getCount() < machineItemStacks.get(1).getMaxStackSize())
    		StackUtils.substractFromStackSize(machineItemStacks.get(2), 1);
    		StackUtils.addToStackSize(machineItemStacks.get(1), 1);
    	}
    }

	@Override
	public IFluidTankProperties[] getTankProperties() {
		IFluidTankProperties[] prop = new IFluidTankProperties[fluid.amount];
		return prop;
		}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		//Simulate the fill to see if there is room for incoming liquids.
				int capacity = MAX_CAPACITY - fluid.amount;

				if (!doFill)
				{
					if (fluid.amount == 0)
					{
						return resource.amount;
					}

					if (resource.getFluid() == fluid.getFluid())
					{
						if (capacity >= resource.amount)
						{
							return resource.amount;
						}else
						{
							return capacity;
						}
					}
				}else
					//Really fill
				{
					if (fluid.amount == 0)
					{
						if (resource.getFluid() != fluid.getFluid())
						{
							fluid =  new FluidStack(resource.getFluid(),resource.amount);
						}else
						{
							fluid.amount = resource.amount;
						}
						mb = fluid.amount / MAX_CAPACITY;
						return resource.amount;
					}

					if (resource.getFluid() == fluid.getFluid())
					{
						if (capacity >= resource.amount)
						{
							fluid.amount += resource.amount;
							mb = fluid.amount / MAX_CAPACITY;
							return resource.amount;
						}else
						{
							fluid.amount = MAX_CAPACITY;
							mb = MAX_CAPACITY;
							return capacity;
						}
					}
				}

				return 0;
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		if (resource.getFluid() == FluidRegistry.WATER)
		      return new FluidStack(FluidRegistry.WATER, resource.amount);
		    else return null;
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		return new FluidStack(FluidRegistry.WATER, maxDrain);
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
	
	//standard stuff
	
		/**
	     * Don't rename this method to canInteractWith due to conflicts with Container
	     */
	    public boolean isUsableByPlayer(EntityPlayer player)
	    {
	        if (this.world.getTileEntity(this.pos) != this)
	        {
	            return false;
	        }
	        else
	        {
	            return player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
	        }
	    }

	    public void openInventory(EntityPlayer player)
	    {
	    }

	    public void closeInventory(EntityPlayer player)
	    {
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