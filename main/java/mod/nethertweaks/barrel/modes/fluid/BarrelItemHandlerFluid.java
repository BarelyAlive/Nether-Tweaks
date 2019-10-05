package mod.nethertweaks.barrel.modes.fluid;

import javax.annotation.Nonnull;

import mod.nethertweaks.barrel.BarrelItemHandler;
import mod.nethertweaks.barrel.modes.mobspawn.BarrelModeMobSpawn;
import mod.nethertweaks.blocks.tile.TileBarrel;
import mod.nethertweaks.items.ItemDoll;
import mod.nethertweaks.network.MessageBarrelModeUpdate;
import mod.nethertweaks.registries.manager.NTMRegistryManager;
import mod.nethertweaks.registry.types.FluidBlockTransformer;
import mod.sfhcore.network.NetworkHandler;
import mod.sfhcore.util.BlockInfo;
import mod.sfhcore.util.EntityInfo;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.items.ItemStackHandler;

public class BarrelItemHandlerFluid extends ItemStackHandler {

    private TileBarrel barrel;

    public TileBarrel getBarrel() {
		return barrel;
	}

	public BarrelItemHandlerFluid(TileBarrel barrel) {
        super(1);
        this.barrel = barrel;
    }
	
    @Override
    @Nonnull
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        FluidTank tank = barrel.getTank();
        
        if (tank.getFluid() == null)
            return stack;
        
        BarrelItemHandler handler = barrel.getItemHandler();
        if (handler != null)
        {
        	if (!handler.getStackInSlot(0).isEmpty())
        	{
        		return stack;
        	}
        }

        // Fluid to block transformations
        if (NTMRegistryManager.FLUID_BLOCK_TRANSFORMER_REGISTRY.canBlockBeTransformedWithThisFluid(tank.getFluid().getFluid(), stack) && tank.getFluidAmount() == tank.getCapacity()) {
            FluidBlockTransformer transformer = NTMRegistryManager.FLUID_BLOCK_TRANSFORMER_REGISTRY.getTransformation(tank.getFluid().getFluid(), stack);

            if (transformer != null) {
                BlockInfo info = transformer.getOutput();
                int spawnCount = transformer.getSpawnCount();
                if (!simulate) {
                    tank.drain(tank.getCapacity(), true);
                    barrel.setMode("block");
                    NetworkHandler.sendToAllAround(new MessageBarrelModeUpdate("block", barrel.getPos()), barrel);

                    barrel.getMode().addItem(info.getItemStack(), barrel);
                    if(spawnCount > 0){
                        int spawnRange = transformer.getSpawnRange();
                        EntityInfo entityInfo = transformer.getToSpawn();
                        for(int i=0; i<spawnCount; i++){
                            entityInfo.spawnEntityNear(barrel.getPos(), spawnRange, barrel.getWorld());
                        }
                    }
                }

                ItemStack ret = stack.copy();
                ret.shrink(1);

                return ret.isEmpty() ? ItemStack.EMPTY : ret;
            }

        }

        // Fluid to Fluid transformations
        String fluidItemFluidOutput = NTMRegistryManager.FLUID_ITEM_FLUID_REGISTRY.getFluidForTransformation(tank.getFluid().getFluid(), stack);
        if (fluidItemFluidOutput != null && tank.getFluidAmount() == tank.getCapacity()) {
        	
            int spawnCount = NTMRegistryManager.FLUID_ITEM_FLUID_REGISTRY.getTransformTime(tank.getFluid().getFluid(), stack);
            boolean isConsumable = NTMRegistryManager.FLUID_ITEM_FLUID_REGISTRY.getConsumable(tank.getFluid().getFluid(), stack);
            
            if (!simulate) {
                FluidStack fstack = tank.drain(tank.getCapacity(), true);
                barrel.setMode("fluid");
                NetworkHandler.sendToAllAround(new MessageBarrelModeUpdate("fluid", barrel.getPos()), barrel);
                
                if(((BarrelModeFluid) barrel.getMode()).workTime != 0)
                {
                	return stack;
                }
                
                //barrel.getMode().addItem(new ItemStack(FluidRegistry.getFluidStack(fluidItemFluidOutput, tank.getCapacity()).getFluid().getBlock()), barrel);
                barrel.getMode().addItem(stack.copy(), barrel);
                if (spawnCount > 0)
                {
                	tank.fill(fstack, true);
                	barrel.getItemHandler().setStackInSlot(0, stack);
                	((BarrelModeFluid)barrel.getMode()).maxWorkTime = spawnCount;
                	NetworkHandler.sendNBTUpdate(barrel);
                }
            }
            ItemStack ret = stack.copy();
            ret.shrink(1);
            
            return !(isConsumable) ? stack : (ret.isEmpty() ? ItemStack.EMPTY : ret);
        }


        // Checks for mobspawn
        if (!stack.isEmpty() && tank.getFluidAmount() == tank.getCapacity() && stack.getItem() instanceof ItemDoll
                && ((ItemDoll) stack.getItem()).getSpawnFluid(stack) == tank.getFluid().getFluid()) {
            if (!simulate) {
                barrel.getTank().drain(Fluid.BUCKET_VOLUME, true);
                barrel.setMode("mobspawn");
                ((BarrelModeMobSpawn) barrel.getMode()).setDollStack(stack);
                NetworkHandler.sendNBTUpdate(barrel);
            }
            ItemStack ret = stack.copy();
            ret.shrink(1);

            return ret;
        }

        return stack;
    }

    @Override
    @Nonnull
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        return ItemStack.EMPTY;
    }

	public void setBarrel(TileBarrel barrel2) {
		this.barrel = barrel2;
	}
}