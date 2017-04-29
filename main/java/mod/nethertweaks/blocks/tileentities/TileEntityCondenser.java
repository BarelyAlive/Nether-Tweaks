package mod.nethertweaks.blocks.tileentities;

import javax.annotation.Nullable;

import mod.nethertweaks.Dryable;
import mod.nethertweaks.INames;
import mod.nethertweaks.RecipeLoader;
import mod.nethertweaks.blocks.NTMBlocks;
import mod.nethertweaks.handler.NTMDryHandler;
import mod.nethertweaks.items.NTMItems;
import net.minecraft.block.Block;
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

public class TileEntityCondenser extends TileEntity implements ITickable, ISidedInventory {
	
	private static final int[] SLOTS_TOP = new int[] {1};
    private static final int[] SLOTS_BOTTOM = new int[] {0, 1};
    private static final int[] SLOTS_SIDES = new int[] {1};
    private ItemStack[] condenserItemStacks = new ItemStack[2];
	public Item[] buckets = {Items.BUCKET, NTMItems.bucketStone, NTMItems.bucketWood};
	private boolean canDry = false;
	private int a = 0;
	public int dryTime;
	public int maxDryTime;
	private String field_145958_o;
	int asdf;
	
	public TileEntityCondenser() {
		condenserItemStacks = new ItemStack[2];
	}
	
	/**
     * Condenser is drying
     */
    public boolean isDrying()
    {
        return this.dryTime > 0;
    }
	
    @Override
    public void update() {
    	checkInv();
		if(canDry == true){
			dry(condenserItemStacks[1].getItem(), condenserItemStacks[0].getItem());
		}
	}
	
	public void dry(Item food, Item bucket){
		if(canDry == true) {
			a = 0;
			if(canDry == true) {
				a = condenserItemStacks[1].stackSize;
				checkHeatSource();
			} 
			else {
				return;
			}
			if(a > 15) {
				if(canDry == true) {
					dryTime++;
					if(dryTime == maxDryTime)
					{
						if(bucket.equals(Items.BUCKET)){
							decrStackSize(0, 1);
							int amount = NTMDryHandler.getItem(food, food.getDamage(condenserItemStacks[1])).value;
							decrStackSize(1, amount);
							setInventorySlotContents(0, new ItemStack(Items.WATER_BUCKET, 1));
						}
						if(bucket == NTMItems.bucketStone){
							decrStackSize(0, 1);
							int amount = NTMDryHandler.getItem(food, food.getDamage(condenserItemStacks[1])).value;
							decrStackSize(1, amount);
							setInventorySlotContents(0, new ItemStack(NTMItems.bucketStoneWater, 1));
								
						}
						if(bucket == NTMItems.bucketWood){
							decrStackSize(0, 1);
							int amount = NTMDryHandler.getItem(food, food.getDamage(condenserItemStacks[1])).value;
							decrStackSize(1, amount);
							setInventorySlotContents(0, new ItemStack(NTMItems.bucketWoodWater, 1));
						}
						canDry = false;
						dryTime = 0;
					}
				}
			}
		}
		return;
	}
	
	public int checkHeatSource(){
		World world = getWorld();
		Block block = world.getBlockState(pos.add(0, -1, 0)).getBlock();
		if(block .equals(Blocks.FIRE)){
			maxDryTime = 1800;
		}
		if(block.equals(Blocks.LAVA)){
			maxDryTime = 1200;
		}
		else{
			maxDryTime = 2400;
		}
		return maxDryTime;
	}
	
	public void checkInv(){
		boolean canDry1 = false;
		boolean canDry2 = false;
		canDry = false;
		if(condenserItemStacks[0] != null && condenserItemStacks[1] != null){
			if(condenserItemStacks[0].stackSize < 2){
				
				if(NTMDryHandler.containsItem(condenserItemStacks[1].getItem(), condenserItemStacks[1].getItemDamage())){
					int wert = NTMDryHandler.getItem(condenserItemStacks[1].getItem(), condenserItemStacks[1].getItemDamage()).value;
					if(condenserItemStacks[1].stackSize >= wert){
					canDry1 = true;
					}
				}else{
					canDry1 = false;
					canDry2 = false;
					return;
				}
				
				for (int i = 0; i < buckets.length; ++i){
					if (buckets[i] == condenserItemStacks[0].getItem()){
						canDry2 = true;
						break;
					}
				}
			}else
			{
				canDry = false;
			}
			if(canDry1 && canDry2){
				canDry = true;
			}else
			{
				canDry = false;
			}
			return;
		}
	}
	
	@Override
	public int getSizeInventory() {
		return condenserItemStacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return condenserItemStacks[slot];
	}
	
	/**
     * Removes up to a specified number of items from an inventory slot and returns them in a new stack.
     */
    @Nullable
    public ItemStack decrStackSize(int index, int count)
    {
        return ItemStackHelper.getAndSplit(this.condenserItemStacks, index, count);
    }

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		condenserItemStacks[slot] = stack;
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
		return worldObj.getTileEntity(pos) == this && player.getDistanceSq(pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F) < 64;
	}
	
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		NBTTagList tagList = (NBTTagList) nbt.getTag("Inventory");
		for(int i = 0; i < tagList.tagCount(); i++) {
			NBTTagCompound tag = (NBTTagCompound) tagList.getCompoundTagAt(i);
			byte slot = tag.getByte("Slot");
			if(slot >= 0 && slot < condenserItemStacks.length) {
				condenserItemStacks[slot] = ItemStack.loadItemStackFromNBT(tag);
			}
		}
		this.dryTime = nbt.getShort("DryTime");
	}

	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		NBTTagList itemList = new NBTTagList();
		for(int i = 0; i < condenserItemStacks.length; i++) {
			ItemStack stack = condenserItemStacks[i];
			if(stack != null) {
				NBTTagCompound tag = new NBTTagCompound();
				tag.setByte("Slot", (byte) i);
				stack.writeToNBT(tag);
				itemList.appendTag(tag);
			}
		}
		nbt.setTag("Inventory", itemList);
		nbt.setShort("DryTime", (short) this.dryTime);
		return nbt;
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack p_94041_2_) {
		if (index == 0)
        {
            return false;
        }
        else if (index != 1)
        {
            return true;
        }
		return false;
	}
	
	public void func_145951_a(String p_145951_1_)
    {
        this.field_145958_o = p_145951_1_;
    }
	
	public int getBurnTimeRemainingScaled(int i) {
		return this.dryTime * i / this.maxDryTime;
	}

    public void openInventory(EntityPlayer player)
    {
    }

    public void closeInventory(EntityPlayer player)
    {
    }

    public int[] getSlotsForFace(EnumFacing side)
    {
        return side == EnumFacing.DOWN ? SLOTS_BOTTOM : (side == EnumFacing.UP ? SLOTS_TOP : SLOTS_SIDES);
    }

    /**
     * Returns true if automation can insert the given item in the given slot from the given side.
     */
    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction)
    {
        return this.isItemValidForSlot(index, itemStackIn);
    }

    /**
     * Returns true if automation can extract the given item in the given slot from the given side.
     */
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction)
    {
        if (direction == EnumFacing.DOWN && index == 1)
        {
            Item item = stack.getItem();

            if (item != Items.WATER_BUCKET && item != Items.BUCKET)
            {
                return false;
            }
        }

        return true;
    }

    public String getGuiID()
    {
        return "mod.nethertweaks:netherrackFurnace";
    }

    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
    {
        return new ContainerFurnace(playerInventory, this);
    }

    public int getField(int id)
    {
        switch (id)
        {
            case 0:
                return this.dryTime;
            case 1:
                return this.maxDryTime;
            default:
                return 0;
        }
    }

    public void setField(int id, int value)
    {
        switch (id)
        {
            case 0:
                this.dryTime = value;
                break;
            case 1:
                this.maxDryTime = value;
        }
    }

    public int getFieldCount()
    {
        return 4;
    }

    public void clear()
    {
        for (int i = 0; i < this.condenserItemStacks.length; ++i)
        {
            this.condenserItemStacks[i] = null;
        }
    }

	@Override
	public ItemStack removeStackFromSlot(int index) {
		// TODO Auto-generated method stub
		return null;
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
}