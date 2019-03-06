package mod.nethertweaks.blocks.tileentities;

import mod.nethertweaks.blocks.Freezer;
import mod.nethertweaks.blocks.tileentities.TileEntityBarrel.BarrelMode;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import scala.Int;

public class TileEntityFreezer extends TileEntity implements IInventory, net.minecraftforge.fluids.capability.IFluidHandler {
	
	private ItemStack[] inv;
	private String field_145958_o;
	public static final int maxfreezeTime = 100;
	public int freezeTime;
	public static final int MAX_CAPACITY = 16000;
	float volume;
	public FluidStack fluid;
	public FluidTank tank = new FluidTank(fluid, (int) volume);
	World world;
	Block bl;
	EntityPlayer player;
	
	public TileEntityFreezer() {
		inv = new ItemStack[2];
		freezeTime = 0;
		volume = 0;
		fluid = new FluidStack(FluidRegistry.WATER, 0);
	}
	
	/**
     * Freezer isfreezeing
     */
    public boolean isFreezing()
    {
        return this.freezeTime > 0;
    }
	
	public void update() {
		world = getWorld();
		
		if(world.getRedstonePower(pos, EnumFacing.DOWN) > 0 && canFreeze()) {
			freezeTime++;
			if(freezeTime == maxfreezeTime) {
				freezeItem();
				fluid.amount -= 1000;
				freezeTime = 0;
			}
		}
			freezeTime = 0;
	}
	
	private boolean canFreeze()
    {
        if (this.fluid.amount < 1000)
        {
            return false;
        }
        else
        {
            ItemStack itemstack = new ItemStack(Blocks.ICE);
            if (this.inv[0] == null) return true;
            if (!this.inv[0].isItemEqual(itemstack)) return false;
            int result = inv[0].stackSize + itemstack.stackSize;
            return result <= getInventoryStackLimit() && result <= this.inv[0].getMaxStackSize(); //Forge BugFix: Make it respect stack sizes properly.
        }
    }

    public void freezeItem()
    {
        if (this.canFreeze())
        {
        	ItemStack itemstack = new ItemStack(Blocks.ICE);

            if (this.inv[0] == null)
            {
                this.inv[0] = itemstack.copy();
            }
            else if (this.inv[0].getItem() == itemstack.getItem())
            {
                this.inv[0].stackSize += itemstack.stackSize; // Forge BugFix: Results may have multiple items
            }
        }
        
    }
    
	@Override
	public int getSizeInventory() {
		return inv.length;
	}
	
	@Override
	public ItemStack getStackInSlot(int slot) {
		return inv[slot];
	}
	
	@Override
	public ItemStack decrStackSize(int slot, int amt) {
		ItemStack stack = getStackInSlot(slot);
		if(stack != null) {
			if(stack.stackSize <= amt) {
				setInventorySlotContents(slot, null);
			}
			else {
				stack = stack.splitStack(amt);
				if(stack.stackSize == 0) {
					setInventorySlotContents(slot, null);
				}
			}
		}
		return stack;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		inv[slot] = stack;
		if(stack != null && stack.stackSize >= getInventoryStackLimit()) {
			stack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return world.getTileEntity(pos) == this && player.getDistanceSq(pos.getX() + 0.5d, pos.getY() + 0.5d, pos.getZ() + 0.5d) < 64;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		NBTTagList tagList = (NBTTagList) nbt.getTag("Inventory");
		for(int i = 0; i < tagList.tagCount(); i++) {
			NBTTagCompound tag = (NBTTagCompound) tagList.getCompoundTagAt(i);
			byte slot = tag.getByte("Slot");
			if(slot >= 0 && slot < inv.length) {
				inv[slot] = ItemStack.loadItemStackFromNBT(tag);
			}
		}
		this.freezeTime = nbt.getShort("freezeTime");
		this.fluid.amount = nbt.getShort("fluid");
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		NBTTagList itemList = new NBTTagList();
		for(int i = 0; i < inv.length; i++) {
			ItemStack stack = inv[i];
			if(stack != null) {
				NBTTagCompound tag = new NBTTagCompound();
				tag.setByte("Slot", (byte) i);
				stack.writeToNBT(tag);
				itemList.appendTag(tag);
			}
		}
		nbt.setTag("Inventory", itemList);
		nbt.setInteger("fluid", fluid.amount);
		nbt.setShort("freezeTime", (short)this.freezeTime);
		return nbt;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemstack) {
		return false;
	}

	public int getFreezeTimeRemainingScaled(int i) {
		return this.freezeTime * i / this.maxfreezeTime;
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasCustomName() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ITextComponent getDisplayName() {
		// TODO Auto-generated method stub
		return null;
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
					//Really fill the barrel.
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
						volume = (float)fluid.amount / (float)MAX_CAPACITY;
												world.markBlockRangeForRenderUpdate(this.pos.getX(), this.pos.getY(), this.pos.getZ(), this.pos.getX(), this.pos.getY(), this.pos.getZ());
						//needsUpdate = true;
						return resource.amount;
					}

					if (resource.getFluid() == fluid.getFluid())
					{
						if (capacity >= resource.amount)
						{
							fluid.amount += resource.amount;
							volume = (float)fluid.amount / (float)MAX_CAPACITY;
							return resource.amount;
						}else
						{
							fluid.amount = MAX_CAPACITY;
							volume = 1.0f;
							//needsUpdate = true;
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
	public ItemStack removeStackFromSlot(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void openInventory(EntityPlayer player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeInventory(EntityPlayer player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getField(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getFieldCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}
	
}