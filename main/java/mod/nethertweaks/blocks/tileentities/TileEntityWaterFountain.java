package mod.nethertweaks.blocks.tileentities;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import scala.Enumeration.Val;
import scala.Int;

public class TileEntityWaterFountain extends TileEntity implements IFluidHandler {
	
	private FluidStack fullStack2 = new FluidStack(FluidRegistry.WATER, Int.MaxValue());
	private FluidTank tank = new FluidTank(getFullStack2(), Int.MaxValue());
	
	  public TileEntityWaterFountain()
		{
		  FluidStack fullStack = new FluidStack(FluidRegistry.WATER, Int.MaxValue());
		}

@Override
public IFluidTankProperties[] getTankProperties() {
	// TODO Auto-generated method stub
	return null;
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
	// TODO Auto-generated method stub
	return new FluidStack(FluidRegistry.WATER, Int.MaxValue());
}

public FluidStack getFullStack2() {
	return fullStack2;
}

public void setFullStack2(FluidStack fullStack2) {
	this.fullStack2 = fullStack2;
}

}