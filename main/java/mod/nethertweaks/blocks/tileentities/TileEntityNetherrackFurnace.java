package mod.nethertweaks.blocks.tileentities;

import javax.annotation.Nullable;

import mod.nethertweaks.Config;
import mod.nethertweaks.blocks.NetherrackFurnace;
import mod.nethertweaks.blocks.container.ContainerNetherrackFurnace;
import mod.nethertweaks.handler.BlockHandler;
import mod.sfhcore.helper.StackUtils;
import mod.sfhcore.tileentities.TileEntityBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.material.Material;
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
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityNetherrackFurnace extends TileEntityBase{
    
    public TileEntityNetherrackFurnace(String field) {
		super(2, field);
		maxworkTime = Config.burnTimeFurnace;
	}

	@Override
    public void update() {
		
        if (!world.isRemote)
        {
            if (canSmelt())
            {
                workTime++;
                if (workTime == maxworkTime)
                {
                    workTime = 0;
                    smeltItem();
                }
            }
            else
            {
                workTime = 0;
            }
        }

        NetherrackFurnace.setState(isWorking(), world, pos);
    } 
    
   
    
    public boolean checkHeatSource(){
		World world = getWorld();
		Block block = world.getBlockState(pos.add(0, -1, 0)).getBlock();
		if(block.getDefaultState().getMaterial() == Material.FIRE){
			return true;
		}
		if(block.getDefaultState().getMaterial() == Material.LAVA){
			if(block instanceof BlockFluidClassic) {
				int lavatime = maxworkTime / 10 * 8;
				int lavaheat = FluidRegistry.LAVA.getTemperature();
				int blockheat = BlockFluidClassic.getTemperature(world, pos);
				if(maxworkTime > lavatime) {
					int heattime = lavatime * Math.floorDiv(lavaheat, blockheat);
					if(lavatime > heattime) {
						maxworkTime = heattime;
					}else {
						maxworkTime = lavatime;			
					}
				}
			}
			return true;
		}
		return false;
	}

    /**
     * Returns true if the furnace can smelt an item, i.e. has a source item, destination stack isn't full, etc.
     */
    private boolean canSmelt()
    {
    	if(!checkHeatSource()) {
    		return false;
    	}
        if (this.machineItemStacks.get(0).isEmpty())
        {
            return false;
        }
        else
        {
            ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(this.machineItemStacks.get(0));
            if (itemstack.isEmpty()) return false;
            if (this.machineItemStacks.get(1).isEmpty()) return true;
            if (!this.machineItemStacks.get(1).isItemEqual(itemstack)) return false;
            int result = machineItemStacks.get(1).getCount() + itemstack.getCount();
            return result <= getInventoryStackLimit() && result <= this.machineItemStacks.get(1).getMaxStackSize();
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
            StackUtils.addToStackSize(machineItemStacks.get(1), itemstack.getCount()); // Forge BugFix: Results may have multiple items
        }

        StackUtils.substractFromStackSize(this.machineItemStacks.get(0), 1);

        if (this.machineItemStacks.get(0).getCount() <= 0)
        {
            this.machineItemStacks.set(0, ItemStack.EMPTY);
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
		compound.setInteger("worktime", workTime);
		ItemStackHelper.saveAllItems(compound, this.machineItemStacks);
		return compound;
	}
	    
    public String getGuiID()
    {
        return "nethertweaksmod:GuiNetherrackFurnace";
    }
    
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
    {
        return new ContainerNetherrackFurnace(playerInventory, this);
    }
	
}