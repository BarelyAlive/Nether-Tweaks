package mod.nethertweaks.blocks.container;

import mod.nethertweaks.blocks.tile.TileHellmart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerBonfire extends Container {
	public ContainerBonfire() {
	}

	public boolean canInteractWith(EntityPlayer par1EntityPlayer) {
		return true;
	}
}
