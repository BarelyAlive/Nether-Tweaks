package mod.nethertweaks.blocks.container;

import mod.nethertweaks.blocks.tile.TileNetherrackFurnace;
import mod.sfhcore.blocks.container.ContainerBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceOutput;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerNetherrackFurnace extends ContainerBase {
	
	private IInventory tileEntity;
	
	public ContainerNetherrackFurnace(InventoryPlayer playerInv, TileNetherrackFurnace tileEntity2) {
		super(tileEntity2);
		tileEntity = tileEntity2;
		
		addSlotToContainer(new Slot(tileEntity, 0, 57, 17));
		addSlotToContainer(new SlotFurnaceOutput(playerInv.player, tileEntity, 1, 57, 53));

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot((IInventory) playerInv, j + i * 9 + 9, 9 + j * 18, 84 + i * 18));
			}
		}
		for(int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot((IInventory) playerInv, i, 9 + i * 18, 142));
		}

	}
	
}
