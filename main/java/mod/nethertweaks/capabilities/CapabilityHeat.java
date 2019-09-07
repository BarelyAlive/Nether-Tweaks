package mod.nethertweaks.capabilities;

public class CapabilityHeat implements ICapabilityHeat
{
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + heatRate;
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CapabilityHeat other = (CapabilityHeat) obj;
		if (heatRate != other.heatRate)
			return false;
		return true;
	}

	int heatRate;

	@Override
	public int getHeatRate() {
		return heatRate;
	}

	@Override
	public void setHeatRate(final int heatRate) {
		this.heatRate = heatRate;
	}

	public CapabilityHeat() {

	}

	public CapabilityHeat(final int heatRate) {
		this.heatRate = heatRate;
	}
}
