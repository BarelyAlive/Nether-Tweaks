package mod.nethertweaks.blocks.tileentities;

import javax.annotation.Nullable;

import mod.nethertweaks.Config;
import mod.nethertweaks.blocks.NTMBlocks;
import mod.nethertweaks.blocks.NetherrackFurnace;
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
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityNetherrackFurnace extends TileNTMBase {
    
    public TileEntityNetherrackFurnace() {
		super(2, Config.burnTimeFurnace);
	}


	@Override
    public void update() {
    	boolean flag = this.isWorking();
        boolean flag1 = false;

        if (!world.isRemote)
        {
            checkHeatSource();
            if (!isWorking() && canSmelt())
            {
            	if(workTime == 0)
            		workTime++;
            	world.notifyBlockUpdate(pos, NTMBlocks.blockNetherrackFurnace_lit.getDefaultState(), NTMBlocks.blockNetherrackFurnace_lit.getDefaultState(), 3);
                if (isWorking())
                {
                    flag1 = true;


                        if (inv[0].getCount() == 0)
                        {
                            inv[0] = inv[1].getItem().getContainerItem(inv[0]);
                        }
                }
            }

            if (isWorking() && canSmelt())
            {
                ++workTime;

                if (workTime == totalWorkTime)
                {
                    workTime = 0;
                    smeltItem();
                    flag1 = true;
                }
            }
            else
            {
                workTime = 0;
            }
        }
            else if (!isWorking() && workTime > 0)
            {
                workTime = MathHelper.clamp_int(workTime - 2, 0, totalWorkTime);
            }else if(isWorking() && !canSmelt()){
            	workTime = 0;
            }

            if (flag != isWorking())
            {
                flag1 = true;
                NetherrackFurnace.setState(isWorking(), world, pos);
            }
    } 
    
   
    
    public boolean checkHeatSource(){
		World world = getWorld();
		Block block = world.getBlockState(pos.add(0, -1, 0)).getBlock();
		if(block.getDefaultState().getMaterial() == Material.FIRE){
			return true;
		}
		if(block.getDefaultState().getMaterial() == Material.LAVA){
			return true;
		}
		else{
			return false;
		}
	}

    /**
     * Returns true if the furnace can smelt an item, i.e. has a source item, destination stack isn't full, etc.
     */
    private boolean canSmelt()
    {
        if (this.inv[0] == null)
        {
            return false;
        }
        else
        {
            ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(this.inv[0]);
            if (itemstack == null) return false;
            if (this.inv[1] == null) return true;
            if (!this.inv[1].isItemEqual(itemstack)) return false;
            int result = inv[1].getCount() + itemstack.getCount();
            return result <= getInventoryStackLimit() && result <= this.inv[1].getMaxStackSize(); //Forge BugFix: Make it respect stack sizes properly.
        }
    }

    /**
     * Turn one item from the furnace source stack into the appropriate smelted item in the furnace result stack
     */
    public void smeltItem()
    {
        if (canSmelt())
        {
            ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(this.inv[0]);

            if (this.inv[1] == null)
            {
                this.inv[1] = itemstack.copy();
            }
            else if (this.inv[1].getItem() == itemstack.getItem())
            {
                this.inv[1].getCount() += itemstack.getCount(); // Forge BugFix: Results may have multiple items
            }

            this.inv[0].setCount(this.inv[]-1);

            if (this.inv[0].getCount() <= 0)
            {
                this.inv[0] = null;
            }
        }
    }
    
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
    {
        return new ContainerNetherrackFurnace(playerInventory, this);
    }
	
}