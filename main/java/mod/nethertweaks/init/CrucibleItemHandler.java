package mod.nethertweaks.init;

import javax.annotation.Nonnull;

import mod.nethertweaks.block.tile.TileCrucibleBase;
import mod.nethertweaks.registry.registries.CrucibleRegistry;
import mod.nethertweaks.registry.registries.base.types.Meltable;
import mod.sfhcore.util.ItemInfo;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class CrucibleItemHandler extends ItemStackHandler {

	protected final TileCrucibleBase te;
	protected final CrucibleRegistry crucibleRegistry;

	public CrucibleItemHandler(final TileCrucibleBase te, final CrucibleRegistry crucibleRegistry) {
		super(1);
		this.te = te;
		this.crucibleRegistry = crucibleRegistry;
	}

	@Override
	@Nonnull
	public ItemStack insertItem(final int slot, @Nonnull final ItemStack stack, final boolean simulate) {
		if (crucibleRegistry.canBeMelted(stack)) {
			final Meltable meltable = crucibleRegistry.getMeltable(stack);
			final int totalSolidAmount = meltable.getAmount() + meltable.getAmount() * getStackInSlot(0).getCount() + te.getSolidAmount();
			final int allowedSolidAmount = meltable.getAmount() * TileCrucibleBase.MAX_ITEMS;

			if (totalSolidAmount <= allowedSolidAmount) {
				te.setCurrentItem(new ItemInfo(stack));

				return super.insertItem(slot, stack, simulate);
			}
		}

		return stack;
	}

	@Override
	@Nonnull
	public ItemStack extractItem(final int slot, final int amount, final boolean simulate) {
		return ItemStack.EMPTY;
	}

	@Override
	protected int getStackLimit(final int slot, @Nonnull final ItemStack stack) {
		return te.getSolidAmount() > 0 ? TileCrucibleBase.MAX_ITEMS - 1 : TileCrucibleBase.MAX_ITEMS;
	}

	@Override
	protected void onContentsChanged(final int slot) {
		te.markDirtyClient();
	}
}
