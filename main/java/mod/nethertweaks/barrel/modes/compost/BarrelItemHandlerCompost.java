package mod.nethertweaks.barrel.modes.compost;

import mod.nethertweaks.blocks.tile.TileBarrel;
import mod.nethertweaks.registry.CompostRegistry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class BarrelItemHandlerCompost extends ItemStackHandler {
	
	TileBarrel barrel;
	
	public void setBarrel(TileBarrel barrel) {
		this.barrel = barrel;
	}

	public BarrelItemHandlerCompost(TileBarrel barrel)
	{
		super(1);
		this.barrel = barrel;
	}
	
	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate)
	{
		if (CompostRegistry.containsItem(stack))
		{
			BarrelModeCompost mode = (BarrelModeCompost) this.barrel.getMode();
			
			if (mode != null && mode.getFillAmount() < 1)
			{
				ItemStack toReturn = stack.copy();
				toReturn.setCount(toReturn.getCount() -1);;
				
				if (!simulate)
				{
					mode.addItem(stack, barrel);
				}
				
				return toReturn;
			}
		}
		
		return stack;
	}
	
	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate)
	{
		return null;
	}
}
