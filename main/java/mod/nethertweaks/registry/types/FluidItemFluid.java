package mod.nethertweaks.registry.types;

import mod.sfhcore.util.StackInfo;

public class FluidItemFluid
{
	public String getInputFluid() {
		return inputFluid;
	}
	public void setInputFluid(String inputFluid) {
		this.inputFluid = inputFluid;
	}
	public StackInfo getReactant() {
		return reactant;
	}
	public void setReactant(StackInfo reactant) {
		this.reactant = reactant;
	}
	public String getOutput() {
		return output;
	}
	public void setOutput(String output) {
		this.output = output;
	}
	@Override
	public String toString() {
		return "FluidItemFluid [inputFluid=" + inputFluid + ", reactant=" + reactant + ", output=" + output + "]";
	}
	public FluidItemFluid(String inputFluid, StackInfo reactant, String output) {
		super();
		this.inputFluid = inputFluid;
		this.reactant = reactant;
		this.output = output;
	}
	String inputFluid = null;
	StackInfo reactant = null;
	String output = null;
}
