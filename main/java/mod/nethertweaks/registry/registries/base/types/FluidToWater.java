package mod.nethertweaks.registry.registries.base.types;

public class FluidToWater
{
	public String getInputFluid() {
		return inputFluid;
	}
	public void setInputFluid(final String inputFluid) {
		this.inputFluid = inputFluid;
	}
	public int getPercOfWater() {
		return percOfWater;
	}
	@Override
	public String toString() {
		return "FluidToWater [inputFluid=" + inputFluid + ", percOfWater=" + percOfWater + "]";
	}

	public FluidToWater(final String inputFluid, final int percOfWater) {
		super();
		this.inputFluid = inputFluid;
		this.percOfWater = percOfWater;
	}

	String inputFluid;
	int percOfWater;
}
