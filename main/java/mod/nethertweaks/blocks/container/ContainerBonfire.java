package mod.nethertweaks.blocks.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class ContainerBonfire extends Container {
	public ContainerBonfire() {
	}

	@Override
	public boolean canInteractWith(final EntityPlayer par1EntityPlayer) {
		return true;
	}
}
