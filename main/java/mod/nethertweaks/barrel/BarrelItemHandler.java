package mod.nethertweaks.barrel;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import mod.nethertweaks.block.tile.TileBarrel;
import mod.nethertweaks.network.MessageBarrelModeUpdate;
import mod.nethertweaks.registry.registries.BarrelModeRegistry;
import mod.nethertweaks.registry.registries.BarrelModeRegistry.TriggerType;
import mod.sfhcore.network.NetworkHandler;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class BarrelItemHandler extends ItemStackHandler {

	private final TileBarrel barrel;

	public BarrelItemHandler(final TileBarrel barrel) {
		super(1);
		this.barrel = barrel;
	}

	@Override
	protected int getStackLimit(final int slot, @Nonnull final ItemStack stack) {
		return 1;
	}

	@Override
	@Nonnull
	public ItemStack getStackInSlot(final int slot) {
		if (barrel.getMode() != null && barrel.getMode().getHandler(barrel) != null)
			return barrel.getMode().getHandler(barrel).getStackInSlot(slot);

		return ItemStack.EMPTY;
	}

	@Override
	public void setStackInSlot(final int slot, @Nonnull final ItemStack stack) {
		if (barrel.getMode() != null && barrel.getMode().isTriggerItemStack(stack)) {
			barrel.getMode().addItem(stack, barrel);
			barrel.markDirty();

			IBlockState state = barrel.getWorld().getBlockState(barrel.getPos());
			barrel.getWorld().setBlockState(barrel.getPos(), state);
		} else if (barrel.getMode() != null && barrel.getMode().getHandler(barrel) != null)
			barrel.getMode().getHandler(barrel).setStackInSlot(slot, stack);
		else if (barrel.getMode() == null) {
			List<IBarrelMode> modes = BarrelModeRegistry.getModes(TriggerType.ITEM);

			if (modes != null)
				for (IBarrelMode possibleMode : modes)
					if (possibleMode.isTriggerItemStack(stack)) {
						barrel.setMode(possibleMode.getName());
						NetworkHandler.sendToAllAround(new MessageBarrelModeUpdate(barrel.getMode().getName(), barrel.getPos()), barrel);

						barrel.getMode().addItem(stack, barrel);
						barrel.markDirty();

						IBlockState state = barrel.getWorld().getBlockState(barrel.getPos());
						barrel.getWorld().setBlockState(barrel.getPos(), state);
					}
		}
	}

	@Override
	@Nonnull
	public ItemStack extractItem(final int slot, final int amount, final boolean simulate) {
		if (barrel.getMode() != null && barrel.getMode().getHandler(barrel) != null)
			return barrel.getMode().getHandler(barrel).extractItem(slot, amount, simulate);

		return ItemStack.EMPTY;
	}

	@Override
	@Nonnull
	public ItemStack insertItem(final int slot, @Nonnull final ItemStack stack, final boolean simulate) {
		if (barrel.getMode() == null) {
			ArrayList<IBarrelMode> modes = BarrelModeRegistry.getModes(TriggerType.ITEM);

			if (modes == null)
				return stack;

			for (IBarrelMode possibleMode : modes)
				if (possibleMode.isTriggerItemStack(stack)) {
					if (!simulate) {
						barrel.setMode(possibleMode.getName());
						NetworkHandler.sendToAllAround(new MessageBarrelModeUpdate(barrel.getMode().getName(), barrel.getPos()), barrel);
						barrel.getMode().addItem(stack, barrel);
						barrel.markDirty();
						IBlockState state = barrel.getWorld().getBlockState(barrel.getPos());
						barrel.getWorld().setBlockState(barrel.getPos(), state);
					}

					ItemStack ret = stack.copy();
					ret.shrink(1);
					return ret;
				}

			return stack;
		} else if (barrel.getMode().getHandler(barrel) != null)
			return barrel.getMode().getHandler(barrel).insertItem(slot, stack, simulate);

		return stack;
	}

}
