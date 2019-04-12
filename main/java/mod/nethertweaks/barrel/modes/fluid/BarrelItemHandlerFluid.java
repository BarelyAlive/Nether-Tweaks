package mod.nethertweaks.barrel.modes.fluid;

import mod.nethertweaks.barrel.modes.mobspawn.BarrelModeMobSpawn;
import mod.nethertweaks.blocks.tile.TileBarrel;
import mod.nethertweaks.items.ItemDoll;
import mod.nethertweaks.network.MessageBarrelModeUpdate;
import mod.nethertweaks.network.NetworkHandlerNTM;
import mod.nethertweaks.registry.FluidBlockTransformerRegistry;
import mod.sfhcore.util.ItemInfo;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.items.ItemStackHandler;

public class BarrelItemHandlerFluid extends ItemStackHandler {
	
	private TileBarrel barrel;
	
	public void setBarrel(TileBarrel barrel) {
		this.barrel = barrel;
	}

	public BarrelItemHandlerFluid(TileBarrel barrel) {
		super(1);
		this.barrel = barrel;
	}
    
    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate)
    {
        FluidTank tank = barrel.getTank();
        
        if (tank.getFluid() == null)
            return stack;
        
        if (FluidBlockTransformerRegistry.canBlockBeTransformedWithThisFluid(tank.getFluid().getFluid(), stack) && tank.getFluidAmount() == tank.getCapacity())
        {
            ItemInfo info = FluidBlockTransformerRegistry.getBlockForTransformation(tank.getFluid().getFluid(), stack);
            
            if (info != null)
            {               
                if (!simulate)
                {
                    tank.drain(tank.getCapacity(), true);
                    barrel.setMode("block");
                    NetworkHandlerNTM.sendToAllAround(new MessageBarrelModeUpdate("block", barrel.getPos()), barrel);
                    
                    barrel.getMode().addItem(info.getItemStack(), barrel);
                }
                
                if(stack.getItem().hasContainerItem(stack))
                {
                    
                }

                ItemStack ret = stack.copy();
                ret.setCount(ret.getCount() -1);
                
                return ret.getCount() == 0 ? null : ret;
            }
            
        }
        
        if (stack != null && tank.getFluidAmount() == tank.getCapacity() && stack.getItem() instanceof ItemDoll
				&& ((ItemDoll) stack.getItem()).getSpawnFluid(stack) == tank.getFluid().getFluid()) {
			if (!simulate) {
				barrel.getTank().drain(Fluid.BUCKET_VOLUME, true);
				barrel.setMode("mobspawn");				
				((BarrelModeMobSpawn) barrel.getMode()).setDollStack(stack);
				NetworkHandlerNTM.sendNBTUpdate(barrel);
			}
			ItemStack ret = stack.copy();
			ret.setCount(ret.getCount() -1);
			
			return ret;
		}
        
        return stack;
    }
    
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate)
    {
        return null;
    }
}