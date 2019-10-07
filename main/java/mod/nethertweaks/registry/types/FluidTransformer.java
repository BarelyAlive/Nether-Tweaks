package mod.nethertweaks.registry.types;

import java.util.Arrays;

import mod.sfhcore.util.BlockInfo;

public class FluidTransformer {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(blocksToSpawn);
		result = prime * result + duration;
		result = prime * result + (inputFluid == null ? 0 : inputFluid.hashCode());
		result = prime * result + (outputFluid == null ? 0 : outputFluid.hashCode());
		result = prime * result + Arrays.hashCode(transformingBlocks);
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
		FluidTransformer other = (FluidTransformer) obj;
		if (!Arrays.equals(blocksToSpawn, other.blocksToSpawn))
			return false;
		if (duration != other.duration)
			return false;
		if (inputFluid == null) {
			if (other.inputFluid != null)
				return false;
		} else if (!inputFluid.equals(other.inputFluid))
			return false;
		if (outputFluid == null) {
			if (other.outputFluid != null)
				return false;
		} else if (!outputFluid.equals(other.outputFluid))
			return false;
        return Arrays.equals(transformingBlocks, other.transformingBlocks);
    }

	private String inputFluid;
	private String outputFluid;
	private BlockInfo[] transformingBlocks;
	private BlockInfo[] blocksToSpawn;
	private int duration;

	public String getInputFluid() {
		return inputFluid;
	}

	public String getOutputFluid() {
		return outputFluid;
	}

	public BlockInfo[] getTransformingBlocks() {
		return transformingBlocks;
	}

	public BlockInfo[] getBlocksToSpawn() {
		return blocksToSpawn;
	}

	public int getDuration() {
		return duration;
	}

	public FluidTransformer(final String inputFluid, final String outputFluid, final int duration, final BlockInfo[] transformingBlocks, final BlockInfo[] blocksToSpawn) {
		this.inputFluid = inputFluid;
		this.outputFluid = outputFluid;
		this.transformingBlocks = transformingBlocks;
		this.blocksToSpawn = blocksToSpawn;
		this.duration = duration;
	}

}