package mod.nethertweaks.api;

import java.util.List;

import mod.nethertweaks.registry.types.FluidTransformer;
import mod.sfhcore.util.BlockInfo;

public interface IFluidTransformRegistry extends IRegistryMappedList<String, FluidTransformer>
{
    public void register(String inputFluid, String outputFluid, int duration, BlockInfo[] transformingBlocks, BlockInfo[] blocksToSpawn);
    public void register(FluidTransformer transformer);

    public boolean containsKey(String inputFluid);

    public FluidTransformer getFluidTransformer(String inputFluid, String outputFluid);

    public List<FluidTransformer> getFluidTransformers(String inputFluid);
}
