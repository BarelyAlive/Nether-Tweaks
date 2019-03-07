package mod.nethertweaks.blocks.tileentities;

import mod.nethertweaks.Config;
import mod.nethertweaks.blocks.Freezer;
import mod.nethertweaks.blocks.tileentities.TileEntityBarrel.BarrelMode;
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
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import scala.Int;

public class TileEntityFreezer extends TileNTMBase implements net.minecraftforge.fluids.capability.IFluidHandler {
	
	public TileEntityFreezer() {
		super(1, Config.freezeTimeFreezer);
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
			if(workTime == totalWorkTime) {
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
            if (this.inv[0] == null) return true;
            if (!this.inv[0].isItemEqual(itemstack)) return false;
            int result = inv[0].stackSize + itemstack.stackSize;
            return result <= getInventoryStackLimit() && result <= this.inv[0].getMaxStackSize(); //Forge BugFix: Make it respect stack sizes properly.
        }
    }

    public void freezeItem()
    {
        if (this.canFreeze() && !world.isRemote)
        {
        	ItemStack itemstack = new ItemStack(Blocks.ICE);

            if (this.inv[0] == null)
            {
                this.inv[0] = itemstack.copy();
                return;
            }
            else if (this.inv[0].getItem() == itemstack.getItem())
            {
                this.inv[0].stackSize += itemstack.stackSize; // Forge BugFix: Results may have multiple items
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
												worldObj.markBlockRangeForRenderUpdate(this.pos.getX(), this.pos.getY(), this.pos.getZ(), this.pos.getX(), this.pos.getY(), this.pos.getZ());
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