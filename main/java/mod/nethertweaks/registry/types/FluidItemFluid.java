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
	public int getTransformTime() {
		return this.transformTime;
	}
	public void setOutput(int transformTime) {
		this.transformTime = transformTime;
	}
	public boolean isConsumable() {
		return this.consume;
	}
	@Override
	public String toString() {
		return "FluidItemFluid [inputFluid=" + inputFluid + ", reactant=" + reactant + ", output=" + output + "]";
	}
	public FluidItemFluid(String inputFluid, StackInfo reactant, String output) {
		this(inputFluid, reactant, output, 0);
	}
	
	public FluidItemFluid(String inputFluid, StackInfo reactant, String output, int transformTime) {
		this(inputFluid, reactant, output, 0, true);
	}
	
	public FluidItemFluid(String inputFluid, StackInfo reactant, String output, int transformTime, boolean consume) {
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
