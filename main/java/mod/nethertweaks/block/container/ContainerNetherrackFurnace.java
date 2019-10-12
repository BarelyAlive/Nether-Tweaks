package mod.nethertweaks.block.container;

import mod.nethertweaks.block.tile.TileNetherrackFurnace;
import mod.sfhcore.blocks.container.ContainerBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceOutput;

public class ContainerNetherrackFurnace extends ContainerBase {

    public ContainerNetherrackFurnace(final InventoryPlayer inventoryPlayer, final TileNetherrackFurnace te) {
		super(te);

        addSlotToContainer(new Slot(te, 0, 56, 17));
		addSlotToContainer(new SlotFurnaceOutput(inventoryPlayer.player, te, 1, 56, 53));

		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 9; j++)
				addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
		for(int i = 0; i < 9; i++)
			addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
	}

	@Override
	public boolean canInteractWith(final EntityPlayer playerIn) {
		return true;
	}
}
