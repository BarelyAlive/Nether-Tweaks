package mod.nethertweaks.barrel.modes.block;

import mod.nethertweaks.blocks.tile.TileBarrel;
import mod.nethertweaks.network.MessageBarrelModeUpdate;
import mod.sfhcore.network.NetworkHandler;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class BarrelItemHandlerBlock extends ItemStackHandler
{
	private TileBarrel barrel;

	public void setBarrel(final TileBarrel barrel) {
		this.barrel = barrel;
	}

	public BarrelItemHandlerBlock(final TileBarrel barrel)
	{
		super(1);
		this.barrel = barrel;
	}

	@Override
	protected int getStackLimit(final int slot, final ItemStack stack)
	{
		return stack.getMaxStackSize();
	}

	@Override
	public ItemStack insertItem(final int slot, final ItemStack stack, final boolean simulate)
	{
		ItemStack returned = super.insertItem(slot, stack, simulate);

		if (!simulate)
			NetworkHandler.sendNBTUpdate(barrel);

		return returned;
	}

	@Override
	public ItemStack extractItem(final int slot, final int amount, final boolean simulate)
	{
		ItemStack ret = super.extractItem(slot, amount, simulate);

		checkEmpty();

		return ret;
	}

	@Override
	public void setStackInSlot(final int slot, final ItemStack stack)
	{
		super.setStackInSlot(slot, stack);

		checkEmpty();
	}

	private void checkEmpty()
	{
		if (getStackInSlot(0) == null && barrel != null)
		{
			barrel.setMode("null");
			NetworkHandler.sendToAllAround(new MessageBarrelModeUpdate("null", barrel.getPos()), barrel);
		}
	}
}
