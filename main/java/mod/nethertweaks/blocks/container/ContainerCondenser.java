package mod.nethertweaks.blocks.container;

import mod.nethertweaks.blocks.tile.TileCondenser;
import mod.sfhcore.blocks.container.ContainerBase;
import mod.sfhcore.blocks.container.CustomSlot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceOutput;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerCondenser extends ContainerBase {
	
	private TileCondenser tileEntity;
	
	public ContainerCondenser(InventoryPlayer inventoryPlayer, TileCondenser te) {
		super(te);
		tileEntity = te;
		
		addSlotToContainer(new Slot(tileEntity, 0, 26, 48));
		addSlotToContainer(new CustomSlot(tileEntity, 1, 80, 30, 1));

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}
		for(int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
		}

	}
	
	@Override
	public void updateProgressBar(int id, int data) {
		this.tileentity.setField(id, data);
	}
}
