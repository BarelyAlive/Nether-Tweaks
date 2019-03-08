package mod.nethertweaks.blocks.container;

import mod.nethertweaks.blocks.tileentities.TileEntityBarrel;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerBarrel extends Container {
	
	private TileEntityBarrel tileEntity;
	
	public ContainerBarrel(InventoryPlayer inventoryPlayer, TileEntityBarrel te) {
		tileEntity = te;
		
		addSlotToContainer(new Slot(tileEntity, 0, 56, 18));
		addSlotToContainer(new Slot(tileEntity, 1, 56, 54));

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 85 + i * 18));
			}
		}
		for(int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 143));
		}

	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return tileEntity.isUsableByPlayer(player);
	}
	
}
