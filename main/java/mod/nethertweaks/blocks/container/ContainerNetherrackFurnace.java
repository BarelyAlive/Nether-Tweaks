package mod.nethertweaks.blocks.container;

import mod.nethertweaks.blocks.tileentities.TileEntityNetherrackFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceOutput;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerNetherrackFurnace extends ContainerNTMBase {
	
	private TileEntityNetherrackFurnace tileEntity;
	
	public ContainerNetherrackFurnace(InventoryPlayer inventoryPlayer, TileEntityNetherrackFurnace te) {
		super(inventoryPlayer, te);
		tileEntity = te;
		
		addSlotToContainer(new Slot(tileEntity, 0, 57, 17));
		addSlotToContainer(new SlotFurnaceOutput(inventoryPlayer.player, tileEntity, 1, 57, 53));

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 9 + j * 18, 84 + i * 18));
			}
		}
		for(int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(inventoryPlayer, i, 9 + i * 18, 142));
		}

	}
	
}