package mod.nethertweaks.registry.registries;

import java.io.FileReader;
import java.util.List;

import javax.annotation.Nonnull;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import mod.nethertweaks.api.IFluidOnTopRegistry;
import mod.nethertweaks.registry.manager.NTMRegistryManager;
import mod.nethertweaks.registry.registries.base.BaseRegistryList;
import mod.nethertweaks.registry.registries.base.types.FluidFluidBlock;
import mod.sfhcore.json.CustomBlockInfoJson;
import mod.sfhcore.json.CustomItemInfoJson;
import mod.sfhcore.util.BlockInfo;
import mod.sfhcore.util.ItemInfo;
import net.minecraftforge.fluids.Fluid;

public class FluidOnTopRegistry extends BaseRegistryList<FluidFluidBlock> implements IFluidOnTopRegistry {
	public FluidOnTopRegistry() {
		super(
				new GsonBuilder()
				.setPrettyPrinting()
				.registerTypeAdapter(ItemInfo.class, new CustomItemInfoJson())
				.registerTypeAdapter(BlockInfo.class, new CustomBlockInfoJson())
				.create(),
				NTMRegistryManager.FLUID_ON_TOP_DEFAULT_REGISTRY_PROVIDERS
				);
	}

	@Override
	public void register(@Nonnull final Fluid fluidInBarrel, @Nonnull final Fluid fluidOnTop, @Nonnull final BlockInfo result) {
		registry.add(new FluidFluidBlock(fluidInBarrel.getName(), fluidOnTop.getName(), result));
	}

	@Override
	public void register(@Nonnull final Fluid fluidInBarrel, @Nonnull final Fluid fluidOnTop, @Nonnull final ItemInfo result) {
		if (result.hasBlock())
			registry.add(new FluidFluidBlock(fluidInBarrel.getName(), fluidOnTop.getName(), new BlockInfo(result.getItemStack())));
	}

	@Override
	public boolean isValidRecipe(final Fluid fluidInBarrel, final Fluid fluidOnTop) {
		if (fluidInBarrel == null || fluidOnTop == null)
			return false;
		for (FluidFluidBlock fBlock : registry)
			if (fBlock.getFluidInBarrel().equals(fluidInBarrel.getName()) &&
					fBlock.getFluidOnTop().equals(fluidOnTop.getName()))
				return true;

		return false;
	}

	@Override
	@Nonnull
	public BlockInfo getTransformedBlock(@Nonnull final Fluid fluidInBarrel, @Nonnull final Fluid fluidOnTop) {
		for (FluidFluidBlock fBlock : registry)
			if (fBlock.getFluidInBarrel().equals(fluidInBarrel.getName()) &&
					fBlock.getFluidOnTop().equals(fluidOnTop.getName()))
				return fBlock.getResult();

		return BlockInfo.EMPTY;
	}

	@Override
	protected void registerEntriesFromJSON(final FileReader fr) {
		List<FluidFluidBlock> gsonInput = gson.fromJson(fr, new TypeToken<List<FluidFluidBlock>>() {
		}.getType());
		registry.addAll(gsonInput);
	}

	@Override
	public List<?> getRecipeList() {
		// TODO Auto-generated method stub
		return null;
	}
}