package mod.nethertweaks.registries.registries;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import org.apache.commons.io.IOUtils;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import mod.nethertweaks.api.IFluidTransformRegistry;
import mod.nethertweaks.registries.manager.NTMRegistryManager;
import mod.nethertweaks.registries.registries.base.BaseRegistryMap;
import mod.nethertweaks.registry.types.FluidTransformer;
import mod.sfhcore.json.CustomBlockInfoJson;
import mod.sfhcore.json.CustomItemInfoJson;
import mod.sfhcore.util.BlockInfo;
import mod.sfhcore.util.ItemInfo;

public class FluidTransformRegistry extends BaseRegistryMap<String, List<FluidTransformer>> implements IFluidTransformRegistry {
	public FluidTransformRegistry() {
		super(new GsonBuilder()
				.setPrettyPrinting()
				.registerTypeAdapter(ItemInfo.class, new CustomItemInfoJson())
				.registerTypeAdapter(BlockInfo.class, new CustomBlockInfoJson())
				.create(),
				new com.google.gson.reflect.TypeToken<Map<String, List<FluidTransformer>>>() {}.getType(),
				NTMRegistryManager.FLUID_TRANSFORM_DEFAULT_REGISTRY_PROVIDERS);
	}

	@Override
	public void register(@Nonnull final String inputFluid, @Nonnull final String outputFluid, final int duration, @Nonnull final BlockInfo[] transformingBlocks, @Nonnull final BlockInfo[] blocksToSpawn) {
		register(new FluidTransformer(inputFluid, outputFluid, duration, transformingBlocks, blocksToSpawn));
	}

	@Override
	public void register(@Nonnull final FluidTransformer transformer) {
		List<FluidTransformer> list = registry.get(transformer.getInputFluid());

		if (list == null)
			list = new ArrayList<>();

		list.add(transformer);
		registry.put(transformer.getInputFluid(), list);
	}

	@Override
	public boolean containsKey(@Nonnull final String inputFluid) {
		return registry.containsKey(inputFluid);
	}

	@Override
	public FluidTransformer getFluidTransformer(@Nonnull final String inputFluid, @Nonnull final String outputFluid) {
		if (registry.containsKey(inputFluid))
			for (FluidTransformer transformer : registry.get(inputFluid))
				if (transformer.getInputFluid().equals(inputFluid) && transformer.getOutputFluid().equals(outputFluid))
					return transformer;
		return null;
	}

	@Override
	@Nonnull
	public List<FluidTransformer> getFluidTransformers(@Nonnull final String inputFluid) {
		return registry.get(inputFluid);
	}

	@Override
	protected void registerEntriesFromJSON(final FileReader fr) {
		List<FluidTransformer> gsonInput = gson.fromJson(fr, new TypeToken<List<FluidTransformer>>() {
		}.getType());

		for (FluidTransformer transformer : gsonInput)
			register(transformer);
	}

	/**
	 * Overridden as I don't want the registry to get saved directly,
	 * rather a List that equals the contents of the registry
	 */
	@Override
	public void saveJson(final File file) {
		FileWriter fw = null;
		try {
			fw = new FileWriter(file);

			gson.toJson(getFluidTransformers(), fw);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(fw);
		}
	}

	public List<FluidTransformer> getFluidTransformers() {
		List<FluidTransformer> fluidTransformers = new ArrayList<>();
		for (List<FluidTransformer> transformers : registry.values())
			fluidTransformers.addAll(transformers);
		return fluidTransformers;
	}

	@Override
	public List<?> getRecipeList() {
		// TODO Auto-generated method stub
		return null;
	}
}