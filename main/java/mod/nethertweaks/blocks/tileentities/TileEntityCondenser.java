package mod.nethertweaks.blocks.tileentities;

import mod.nethertweaks.BucketLoader;
import mod.nethertweaks.Dryable;
import mod.nethertweaks.RecipeLoader;
import mod.nethertweaks.blocks.NTMBlocks;
import mod.nethertweaks.handler.NTMDryHandler;
import mod.nethertweaks.items.NTMItems;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

public class TileEntityCondenser extends TileEntity implements IInventory {
	
	private ItemStack[] inv;
	public Item[] buckets = {Items.BUCKET};
	private boolean canDry = false;
	private int a = 0;
	public int dryTime;
	public int maxDryTime;
	private String field_145958_o;
	int asdf;
	
	public TileEntityCondenser() {
		inv = new ItemStack[2];
	}
	
	/**
     * Condenser is drying
     */
    public boolean isDrying()
    {
        return this.dryTime > 0;
    }
	
	public void update() {
		if(inv[0] != null && inv[1] != null){
			checkInv(inv[1].getItem(), inv[0].getItem(), inv[1].getItem().getMetadata(asdf));
		}
		if(canDry == true){
			dry(inv[1].getItem(), inv[0].getItem());
		}
	}
	
	public void dry(Item food, Item bucket){
		if(canDry == true) {
			a = 0;
			if(canDry == true) {
				a = inv[1].stackSize;
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
							int amount = NTMDryHandler.getItem(food, food.getDamage(inv[1])).value;
							decrStackSize(1, amount);
							setInventorySlotContents(0, new ItemStack(Items.WATER_BUCKET, 1));
						}
						if(bucket.getUnlocalizedName() == "BucketStone"){
							decrStackSize(0, 1);
							int amount = NTMDryHandler.getItem(food, food.getDamage(inv[1])).value;
							decrStackSize(1, amount);
							setInventorySlotContents(0, new ItemStack(Item.REGISTRY.getObject((new ResourceLocation("Chaust", "BucketStoneWater")))
							
, 1));	
						}
						if(bucket.getUnlocalizedName() == "BucketWood"){
							decrStackSize(0, 1);
							int amount = NTMDryHandler.getItem(food, food.getDamage(inv[1])).value;
							decrStackSize(1, amount);
							setInventorySlotContents(0, new ItemStack(Item.REGISTRY.getObject((new ResourceLocation("Chaust", "BucketWoodWater"))), 1));
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
	
	public void checkInv(Item food, Item bucket, int meta){
		boolean canDry1 = false;
		boolean canDry2 = false;
		canDry = false;
		if(inv[0].stackSize < 2){
			
			if(NTMDryHandler.containsItem(food, meta)){
				int wert = NTMDryHandler.getItem(food, meta).value;
				if(inv[1].stackSize >= wert){
				canDry1 = true;
				}
			}else{
				canDry1 = false;
				canDry2 = false;
				return;
			}
			
			for (int i = 0; i < buckets.length; ++i){
				if (buckets[i] == bucket){
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
		return worldObj.getTileEntity(pos) == this && player.getDistanceSq(pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F) < 64;
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
		this.dryTime = nbt.getShort("DryTime");
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
		nbt.setShort("DryTime", (short) this.dryTime);
		return nbt;
	}

	@Override
	public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
		return false;
	}
	
	public void func_145951_a(String p_145951_1_)
    {
        this.field_145958_o = p_145951_1_;
    }
	
	public int getBurnTimeRemainingScaled(int i) {
		return this.dryTime * i / this.maxDryTime;
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