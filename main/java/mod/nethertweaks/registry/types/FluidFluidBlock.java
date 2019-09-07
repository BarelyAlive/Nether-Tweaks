package mod.nethertweaks.registry.types;

import mod.sfhcore.util.BlockInfo;

public class FluidFluidBlock {

	private String fluidInBarrel;
	private String fluidOnTop;
	private BlockInfo result;

	public String getFluidInBarrel() {
		return fluidInBarrel;
	}

	public String getFluidOnTop() {
		return fluidOnTop;
	}

	public BlockInfo getResult() {
		return result;
	}

	public FluidFluidBlock(final String fluidInBarrel, final String fluidOnTop, final BlockInfo result) {
		super();
		this.fluidInBarrel = fluidInBarrel;
		this.fluidOnTop = fluidOnTop;
		this.result = result;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (fluidInBarrel == null ? 0 : fluidInBarrel.hashCode());
		result = prime * result + (fluidOnTop == null ? 0 : fluidOnTop.hashCode());
		result = prime * result + (this.result == null ? 0 : this.result.hashCode());
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
		FluidFluidBlock other = (FluidFluidBlock) obj;
		if (fluidInBarrel == null) {
			if (other.fluidInBarrel != null)
				return false;
		} else if (!fluidInBarrel.equals(other.fluidInBarrel))
			return false;
		if (fluidOnTop == null) {
			if (other.fluidOnTop != null)
				return false;
		} else if (!fluidOnTop.equals(other.fluidOnTop))
			return false;
		if (result == null) {
			if (other.result != null)
				return false;
		} else if (!result.equals(other.result))
			return false;
		return true;
	}
}