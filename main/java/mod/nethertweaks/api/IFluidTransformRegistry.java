package mod.nethertweaks.api;

import java.util.List;

import mod.nethertweaks.registry.registries.base.types.FluidTransformer;
import mod.sfhcore.util.BlockInfo;

public interface IFluidTransformRegistry extends IRegistryMappedList<String, FluidTransformer>
{
	void register(String inputFluid, String outputFluid, int duration, BlockInfo[] transformingBlocks, BlockInfo[] blocksToSpawn);
	void register(FluidTransformer transformer);

	boolean containsKey(String inputFluid);

	FluidTransformer getFluidTransformer(String inputFluid, String outputFluid);

	List<FluidTransformer> getFluidTransformers(String inputFluid);
}
