package mod.nethertweaks.registry.types;

import javax.annotation.Nullable;

import mod.sfhcore.util.BlockInfo;
import mod.sfhcore.util.EntityInfo;
import net.minecraft.item.crafting.Ingredient;

public class FluidBlockTransformer
{
	private final String fluidName;
	private final Ingredient input;
	private final BlockInfo output;
	private EntityInfo toSpawn;
	private int spawnCount;
	private int spawnRange;

	public FluidBlockTransformer copy()
	{
        return new FluidBlockTransformer(fluidName, input, output, toSpawn.getName(), spawnCount, spawnRange);
	}

	public String getFluidName() {
		return fluidName;
	}

	public Ingredient getInput() {
		return input;
	}

	public BlockInfo getOutput() {
		return output;
	}

	public EntityInfo getToSpawn() {
		return toSpawn;
	}

	public int getSpawnCount() {
		return spawnCount;
	}

	public int getSpawnRange() {
		return spawnRange;
	}

	@Override
	public String toString() {
		return "FluidBlockTransformer [fluidName=" + fluidName + ", input=" + input + ", output=" + output
				+ ", toSpawn=" + toSpawn + ", spawnCount=" + spawnCount + ", spawnRange=" + spawnRange + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (fluidName == null ? 0 : fluidName.hashCode());
		result = prime * result + (input == null ? 0 : input.hashCode());
		result = prime * result + (output == null ? 0 : output.hashCode());
		result = prime * result + spawnCount;
		result = prime * result + spawnRange;
		result = prime * result + (toSpawn == null ? 0 : toSpawn.hashCode());
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
		if (spawnCount != other.spawnCount)
			return false;
		if (spawnRange != other.spawnRange)
			return false;
		if (toSpawn == null) {
            return other.toSpawn == null;
		} else return toSpawn.equals(other.toSpawn);
    }

	public FluidBlockTransformer(final String fluidName, final Ingredient input, final BlockInfo output, @Nullable final String entityName, final int spawnCount, final int spawnRange)
	{
		this.fluidName = fluidName;
		this.input = input;
		this.output = output;
		toSpawn = new EntityInfo(entityName);
		this.spawnCount = spawnCount;
		this.spawnRange = spawnRange;
	}

}
