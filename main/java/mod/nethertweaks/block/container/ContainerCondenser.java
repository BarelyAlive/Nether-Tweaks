package mod.nethertweaks.block.container;

import mod.nethertweaks.block.tile.TileCondenser;
import mod.sfhcore.block.container.ContainerBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceOutput;

public class ContainerCondenser extends ContainerBase {

    public ContainerCondenser(final InventoryPlayer inventoryPlayer, final TileCondenser te) {
		super(te);

        addSlotToContainer(new Slot(te, 0, 26, 48));
		addSlotToContainer(new SlotFurnaceOutput(inventoryPlayer.player, te, 1, 80, 12));
		addSlotToContainer(new Slot(te, 2, 80, 48));

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
