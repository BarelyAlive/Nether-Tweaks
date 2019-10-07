package mod.nethertweaks.registry.types;

import mod.sfhcore.util.StackInfo;

public class FluidItemFluid
{
	public String getInputFluid() {
		return inputFluid;
	}
	public void setInputFluid(final String inputFluid) {
		this.inputFluid = inputFluid;
	}
	public StackInfo getReactant() {
		return reactant;
	}
	public void setReactant(final StackInfo reactant) {
		this.reactant = reactant;
	}
	public String getOutput() {
		return output;
	}
	public void setOutput(final String output) {
		this.output = output;
	}
	public int getTransformTime() {
		return transformTime;
	}
	public void setOutput(final int transformTime) {
		this.transformTime = transformTime;
	}
	public boolean isConsumable() {
		return consume;
	}
	@Override
	public String toString() {
		return "FluidItemFluid [inputFluid=" + inputFluid + ", reactant=" + reactant + ", output=" + output + "]";
	}
	public FluidItemFluid(final String inputFluid, final StackInfo reactant, final String output) {
		this(inputFluid, reactant, output, 0);
	}

	public FluidItemFluid(final String inputFluid, final StackInfo reactant, final String output, final int transformTime) {
		this(inputFluid, reactant, output, 0, true);
	}

	public FluidItemFluid(final String inputFluid, final StackInfo reactant, final String output, final int transformTime, final boolean consume) {
		super();
		this.inputFluid = inputFluid;
		this.reactant = reactant;
		this.output = output;
		this.transformTime = transformTime;
		this.consume = consume;
	}

	String inputFluid = null;
	StackInfo reactant = null;
	String output = null;
	int transformTime = 0;
	boolean consume = true;
}
