package mod.nethertweaks.blocks.tileentities;

import mod.nethertweaks.Config;
import mod.nethertweaks.blocks.Freezer;
import mod.nethertweaks.blocks.tileentities.TileEntityBarrel.BarrelMode;
import mod.sfhcore.helper.StackUtils;
import mod.sfhcore.tileentities.TileEntityBase;
import mod.sfhcore.tileentities.TileEntityFluidBase;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import scala.Int;

public class TileEntityFreezer extends TileEntityFluidBase implements net.minecraftforge.fluids.capability.IFluidHandler {
	
	public TileEntityFreezer(String field) {
		super(1, field, 16000);
		maxworkTime = Config.freezeTimeFreezer;
	}

	public static final int MAX_CAPACITY = 16000;
	float volume;
	public FluidStack fluid;
	public FluidTank tank = new FluidTank(fluid, (int) volume);
	World world;
	
    @Override
	public void update() {
		world = getWorld();
		
		if(canFreeze()) {
			workTime++;
			if(workTime == maxworkTime) {
				freezeItem();
				workTime = 0;
			}
		}
	}
	
	private boolean canFreeze()
    {
        if (world.getRedstonePower(pos, EnumFacing.DOWN) == 0)
        {
            return false;
        }
        else
        {
            ItemStack itemstack = new ItemStack(Blocks.ICE);
            if (this.machineItemStacks.get(1).getItem().getContainerItem(machineItemStacks.get(0)) == null) return true;
            if (!this.machineItemStacks.get(1).getItem().getContainerItem(machineItemStacks.get(0)).isItemEqual(itemstack)) return false;
            int result = machineItemStacks.get(1).getItem().getContainerItem(machineItemStacks.get(0)).getCount() + itemstack.getCount();
            return result <= getInventoryStackLimit() && result <= this.machineItemStacks.get(1).getItem().getContainerItem(machineItemStacks.get(0)).getCount(); //Forge BugFix: Make it respect stack sizes properly.
        }
    }

    public void freezeItem()
    {
        if (this.canFreeze() && !world.isRemote)
        {
        	ItemStack itemstack = new ItemStack(Blocks.ICE);

            if (this.machineItemStacks.get(1).getItem().getContainerItem(machineItemStacks.get(0)) == null)
            {
                this.machineItemStacks.get(1).getItem().getContainerItem(machineItemStacks.set(0, itemstack.copy()));
                return;
            }
            else if (this.machineItemStacks.get(1).getItem().getContainerItem(machineItemStacks.get(0)).getItem() == itemstack.getItem())
            {
                StackUtils.addToStackSize(machineItemStacks.get(1).getItem().getContainerItem(machineItemStacks.get(0)), itemstack.getCount());// Forge BugFix: Results may have multiple items
                return;
            }
        }
        
    }

	@Override
	public IFluidTankProperties[] getTankProperties() {
		IFluidTankProperties[] prop = new IFluidTankProperties[fluid.amount];
		return prop;
		}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		//Simulate the fill to see if there is room for incoming liquids.
				int capacity = MAX_CAPACITY - fluid.amount;

				if (!doFill)
				{
					if (fluid.amount == 0)
					{
						return resource.amount;
					}

					if (resource.getFluid() == fluid.getFluid())
					{
						if (capacity >= resource.amount)
						{
							return resource.amount;
						}else
						{
							return capacity;
						}
					}
				}else
					//Really fill the barrel.
				{
					if (fluid.amount == 0)
					{
						if (resource.getFluid() != fluid.getFluid())
						{
							fluid =  new FluidStack(resource.getFluid(),resource.amount);
						}else
						{
							fluid.amount = resource.amount;
						}
						volume = (float)fluid.amount / (float)MAX_CAPACITY;
												world.markBlockRangeForRenderUpdate(this.pos.getX(), this.pos.getY(), this.pos.getZ(), this.pos.getX(), this.pos.getY(), this.pos.getZ());
						//needsUpdate = true;
						return resource.amount;
					}

					if (resource.getFluid() == fluid.getFluid())
					{
						if (capacity >= resource.amount)
						{
							fluid.amount += resource.amount;
							volume = (float)fluid.amount / (float)MAX_CAPACITY;
							return resource.amount;
						}else
						{
							fluid.amount = MAX_CAPACITY;
							volume = 1.0f;
							//needsUpdate = true;
							return capacity;
						}
					}
				}

				return 0;
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		if (resource.getFluid() == FluidRegistry.WATER)
		      return new FluidStack(FluidRegistry.WATER, resource.amount);
		    else return null;
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		return new FluidStack(FluidRegistry.WATER, maxDrain);
	}
	
}