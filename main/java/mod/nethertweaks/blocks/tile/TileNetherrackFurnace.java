package mod.nethertweaks.blocks.tile;

import javax.annotation.Nullable;

import mod.nethertweaks.Config;
import mod.nethertweaks.blocks.NetherrackFurnace;
import mod.nethertweaks.blocks.container.ContainerNetherrackFurnace;
import mod.nethertweaks.handler.BlockHandler;
import mod.nethertweaks.interfaces.INames;
import mod.sfhcore.helper.StackUtils;
import mod.sfhcore.tileentities.TEBaseInventory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerFurnace;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.walkers.ItemStackDataLists;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileNetherrackFurnace extends TEBaseInventory{
    
	
    public TileNetherrackFurnace() {
		super(2, INames.TENETHERRACKFURNACE);
		this.maxworkTime = Config.burnTimeFurnace;
	}

	@Override
    public void update() {
		if (canSmelt())
		{
            ++this.workTime;
            if (this.workTime >= maxworkTime && this.world.isRemote)
            {
            	this.workTime = 0;
            }
		}
		
        if (!this.world.isRemote)
        {
            if (canSmelt())
            {
                if (this.workTime >= maxworkTime)
                {
                    smeltItem();
                    this.workTime = 0;
                }
            }
            else
            {
            	this.workTime = 0;
            }
            NetherrackFurnace.setState(isWorking(), this.world, this.pos);
        }
    }
    
    public boolean checkHeatSource(){ 
		World world = getWorld();
		IBlockState block = world.getBlockState(this.pos.add(0, -1, 0));
		if (world.isBlockLoaded(pos)) {
			if (block.getMaterial() == Material.FIRE) {
				return true;
			}
			if (block.getMaterial() == Material.LAVA) {
				if (block instanceof BlockFluidClassic) {
					int lavatime = this.maxworkTime / 10 * 8;
					int lavaheat = FluidRegistry.LAVA.getTemperature();
					int blockheat = BlockFluidClassic.getTemperature(world, this.pos);
					if (this.maxworkTime > lavatime) {
						int heattime = lavatime * Math.floorDiv(lavaheat, blockheat);
						if (lavatime > heattime) {
							this.maxworkTime = heattime;
						} else {
							this.maxworkTime = lavatime;
						}
					}
				}
				return true;
			} 
		}
		return false;
	}

    /**
     * Returns true if the furnace can smelt an item, i.e. has a source item, destination stack isn't full, etc.
     * Aaaaaaaaaaaaaaaaaaaand if there's a source of heat beneath it
     */
    private boolean canSmelt()
    {
    	if(!checkHeatSource()) {
    		return false;
    	}
    	if (((ItemStack)this.machineItemStacks.get(0)).isEmpty())
        {
            return false;
        }
        else
        {
            ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(this.machineItemStacks.get(0));

            if (itemstack.isEmpty())
            {
                return false;
            }
            else
            {
                ItemStack itemstack1 = this.machineItemStacks.get(1);

                if (itemstack1.isEmpty())
                {
                    return true;
                }
                else if (!itemstack1.isItemEqual(itemstack))
                {
                    return false;
                }
                else if (itemstack1.getCount() + itemstack.getCount() <= this.getInventoryStackLimit() && itemstack1.getCount() + itemstack.getCount() <= itemstack1.getMaxStackSize())  // Forge fix: make furnace respect stack sizes in furnace recipes
                {
                    return true;
                }
                else
                {
                    return itemstack1.getCount() + itemstack.getCount() <= itemstack.getMaxStackSize(); // Forge fix: make furnace respect stack sizes in furnace recipes
                }
            }
        }
    }

    /**
     * Turn one item from the furnace source stack into the appropriate smelted item in the furnace result stack
     */
    public void smeltItem()
    {
        ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(this.machineItemStacks.get(0));
        
        if (this.machineItemStacks.get(1).isEmpty())
        {
            this.machineItemStacks.set(1, itemstack.copy());
        }
        else if (this.machineItemStacks.get(1).getItem() == itemstack.getItem())
        {
            StackUtils.addToStackSize(machineItemStacks.get(1), itemstack.getCount());
        }

        if (this.machineItemStacks.get(0).getCount() <= 0)
        {
            this.machineItemStacks.set(0, ItemStack.EMPTY);
        }
        else
        {
            StackUtils.substractFromStackSize(this.machineItemStacks.get(0), 1);
        }
    }
    
    @Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.workTime = compound.getInteger("worktime");
		ItemStackHelper.loadAllItems(compound, this.machineItemStacks);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("worktime", this.workTime);
		ItemStackHelper.saveAllItems(compound, this.machineItemStacks);
		return compound;
	}
	
	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		ItemStack slot;
		ItemStack result;
		IFluidHandlerItem handler;
		switch(index)
		{
		case 0:
			slot = this.getStackInSlot(1);
			if ((slot.getCount() + stack.getCount()) > slot.getMaxStackSize())
			{
				return false;
			}
			/*
			result = FurnaceRecipes.instance().getSmeltingResult(stack);
			if (result == ItemStack.EMPTY)
			{
				return false;
			}
			*/
			return true;
		}
		return false;
	}
	
	@Override
	public boolean isItemValidForSlotToExtract(int index, ItemStack itemStack) {
		if (index == 1)
		{
			return true;
		}
		return false;
	}
	    
	@Override
    public String getGuiID()
    {
        return "nethertweaksmod:GuiNetherrackFurnace";
    }
    
    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
    {
        return new ContainerNetherrackFurnace(playerInventory, this);
    }
}