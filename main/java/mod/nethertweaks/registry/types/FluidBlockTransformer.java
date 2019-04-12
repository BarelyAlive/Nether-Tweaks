package mod.nethertweaks.registry.types;

import mod.sfhcore.util.ItemInfo;

public class FluidBlockTransformer {
	
	private String fluidName;
	private ItemInfo input;
	private ItemInfo output;
	
	public String getFluidName() {
		return fluidName;
	}
	public ItemInfo getInput() {
		return input;
	}
	public ItemInfo getOutput() {
		return output;
	}
	
	public FluidBlockTransformer(String fluidName, ItemInfo input, ItemInfo output) {
		super();
		this.fluidName = fluidName;
		this.input = input;
		this.output = output;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fluidName == null) ? 0 : fluidName.hashCode());
		result = prime * result + ((input == null) ? 0 : input.hashCode());
		result = prime * result + ((output == null) ? 0 : output.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FluidBlockTransformer other = (FluidBlockTransformer) obj;
		if (fluidName == null) {
			if (other.fluidName != null)
				return false;
		} else if (!fluidName.equals(other.fluidName))
			return false;
		if (input == null) {
			if (other.input != null)
				return false;
		} else if (!input.equals(other.input))
			return false;
		if (output == null) {
			if (other.output != null)
				return false;
		} else if (!output.equals(other.output))
			return false;
		return true;
	}
}
