package mod.nethertweaks.blocks.container;

import mod.nethertweaks.blocks.tileentities.TileEntityFreezer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceOutput;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerFreezer extends ContainerNTMBase {
	
	private TileEntityFreezer tileEntity;
	
	public ContainerFreezer(InventoryPlayer inventoryPlayer, TileEntityFreezer te) {
		super(inventoryPlayer, te);
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
	
}
