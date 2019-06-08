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
	
	private TileNetherrackFurnace tileEntity;
	
	public ContainerNetherrackFurnace(InventoryPlayer inventoryPlayer, TileNetherrackFurnace te) {
		super(te);
		tileEntity = te;
		
		addSlotToContainer(new Slot(tileEntity, 0, 56, 17));
		addSlotToContainer(new SlotFurnaceOutput(inventoryPlayer.player, tileEntity, 1, 56, 53));

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
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}
}
