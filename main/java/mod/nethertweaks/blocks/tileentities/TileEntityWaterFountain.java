package mod.nethertweaks.blocks.tileentities;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fluids.capability.FluidTankProperties;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import scala.Enumeration.Val;
import scala.Int;

public class TileEntityWaterFountain extends TileEntity implements IFluidHandler, IFluidTank{
	
	private FluidStack fullStack2 = new FluidStack(FluidRegistry.WATER, Int.MaxValue());
	private FluidTank tank = new FluidTank(getFullStack2(), Int.MaxValue());
	
	  public TileEntityWaterFountain()
		{
		  FluidStack fullStack = new FluidStack(FluidRegistry.WATER, Int.MaxValue());
		  tank.setCanDrain(true);
		  tank.setCanFill(false);
		}

	@Override
	public IFluidTankProperties[] getTankProperties() {
		
		return tank.getTankProperties();
	}
	
	@Override
	public int fill(FluidStack resource, boolean doFill) {
		return 0;
	}
	
	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		if(resource.getFluid() == FluidRegistry.WATER)
		return new FluidStack(FluidRegistry.WATER, Int.MaxValue());
		return null;
	}
	
	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		return new FluidStack(FluidRegistry.WATER, maxDrain);
	}
	
	public FluidStack getFullStack2() {
		return fullStack2;
	}
	
	public void setFullStack2(FluidStack fullStack2) {
		this.fullStack2 = fullStack2;
	}

	@Override
	public FluidStack getFluid() {
		return getFullStack2();
	}

	@Override
	public int getFluidAmount() {
		return Integer.MAX_VALUE;
	}

	@Override
	public int getCapacity() {
		return Integer.MAX_VALUE;
	}

	@Override
	public FluidTankInfo getInfo() {
		return this.tank.getInfo();
	}

}