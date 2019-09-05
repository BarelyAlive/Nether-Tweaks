package mod.nethertweaks.blocks.tile;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;

public class TileAirConditioner extends TileEntity implements ITickable
{
	private EnergyStorage energy = new EnergyStorage(30000, 100, 0, 200);
	private boolean active = false;
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public void update() {
		if(world.isRemote) return;
		
		if(energy.getEnergyStored() >= 5)
		{
			setActive(true);
			energy.extractEnergy(5, true);
		}
		else
			setActive(false);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == CapabilityEnergy.ENERGY && facing != EnumFacing.DOWN) return (T) energy;
		return super.getCapability(capability, facing);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityEnergy.ENERGY && facing != EnumFacing.DOWN;
	}
}
