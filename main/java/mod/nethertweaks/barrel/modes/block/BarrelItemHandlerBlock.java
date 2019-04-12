package mod.nethertweaks.barrel.modes.block;

import mod.nethertweaks.blocks.tile.TileBarrel;
import mod.nethertweaks.network.MessageBarrelModeUpdate;
import mod.nethertweaks.network.NetworkHandlerNTM;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class BarrelItemHandlerBlock extends ItemStackHandler
{
    private TileBarrel barrel;
    
    public void setBarrel(TileBarrel barrel) {
		this.barrel = barrel;
	}

	public BarrelItemHandlerBlock(TileBarrel barrel)
    {
        super(1);
        this.barrel = barrel;
    }
    
    protected int getStackLimit(int slot, ItemStack stack)
    {
        return stack.getMaxStackSize();
    }
    
    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate)
    {
        ItemStack returned = super.insertItem(slot, stack, simulate);
        
        if (!simulate)
        {
            NetworkHandlerNTM.sendNBTUpdate(barrel);
        }
        
        return returned;
    }
    
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate)
    {
        ItemStack ret = super.extractItem(slot, amount, simulate);
        
        checkEmpty();
        
        return ret;
    }
    
    @Override
    public void setStackInSlot(int slot, ItemStack stack)
    {
        super.setStackInSlot(slot, stack);
        
        checkEmpty();
    }
    
    private void checkEmpty()
    {
        if (getStackInSlot(0) == null && barrel != null)
        {
            barrel.setMode("null");
            NetworkHandlerNTM.sendToAllAround(new MessageBarrelModeUpdate("null", barrel.getPos()), barrel);
        }
    }
}
