package mod.nethertweaks.barrel.modes.compost;

import javax.annotation.Nonnull;

import mod.nethertweaks.block.tile.TileBarrel;
import mod.nethertweaks.registry.manager.NTMRegistryManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class BarrelItemHandlerCompost extends ItemStackHandler {

	TileBarrel barrel;

	public void setBarrel(final TileBarrel barrel) {
		this.barrel = barrel;
	}

	public BarrelItemHandlerCompost(final TileBarrel barrel) {
		super(1);
		this.barrel = barrel;
	}

	@Override
	@Nonnull
	public ItemStack insertItem(final int slot, @Nonnull final ItemStack stack, final boolean simulate) {
		if (NTMRegistryManager.COMPOST_REGISTRY.containsItem(stack)) {
			BarrelModeCompost mode = (BarrelModeCompost) barrel.getMode();

			if (mode != null && mode.getFillAmount() < 1) {
				ItemStack toReturn = stack.copy();
				toReturn.shrink(1);

				if (!simulate)
					mode.addItem(stack, barrel);

				return toReturn;
			}
		}

		return stack;
	}

	@Override
	@Nonnull
	public ItemStack extractItem(final int slot, final int amount, final boolean simulate) {
		return ItemStack.EMPTY;
	}
}