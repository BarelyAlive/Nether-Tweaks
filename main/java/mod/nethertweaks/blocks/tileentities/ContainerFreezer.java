package mod.nethertweaks.blocks.tileentities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceOutput;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerFreezer extends Container {
	
	private TileEntityFreezer tileEntity;
	private int lastfreezeTime;
	
	public ContainerFreezer(InventoryPlayer inventoryPlayer, TileEntityFreezer te) {
		tileEntity = te;
		
		addSlotToContainer(new SlotFurnaceOutput(inventoryPlayer.player, tileEntity, 0, 57, 17));

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 9 + j * 18, 84 + i * 18));
			}
		}
		for(int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(inventoryPlayer, i, 9 + i * 18, 142));
		}

	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return tileEntity.isUseableByPlayer(player);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
		ItemStack stack = null;
		Slot slotObject = (Slot) inventorySlots.get(slot);
		
		// null checks and checks if the item can be stacked(maxStackSize > 1)
		if(slotObject != null && slotObject.getHasStack()) {
			ItemStack stackInSlot = slotObject.getStack();
			stack = stackInSlot.copy();
			
			//merges the item into player inventory since its in the tileEntity
			if(slot < tileEntity.getSizeInventory()) {
				if(!this.mergeItemStack(stackInSlot, tileEntity.getSizeInventory(), 36+tileEntity.getSizeInventory(), true)) {
					return null;
				}
			}
			//places it into the tileEntity is possible since its in the player inventory
			else if(!this.mergeItemStack(stackInSlot, 0, tileEntity.getSizeInventory(), false)) {
				return null;
			}
			
			if(stackInSlot.stackSize == 0) {
				slotObject.putStack(null);
			} else {
				slotObject.onSlotChanged();
			}
			
			if(stackInSlot.stackSize == stack.stackSize) {
				return null;
			}
			slotObject.onPickupFromSlot(player, stackInSlot);
		}
		return stack;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void updateProgressBar(int slot, int newValue) {
		// TODO Auto-generated method stub
		super.updateProgressBar(slot, newValue);
		
		if(slot == 0) this.tileEntity.freezeTime = newValue;
	}
}
