package mod.nethertweaks.registry.registries;

import java.io.FileReader;
import java.util.List;

import javax.annotation.Nonnull;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import mod.nethertweaks.api.IFluidToWaterRegistry;
import mod.nethertweaks.registry.manager.NTMRegistryManager;
import mod.nethertweaks.registry.registries.base.BaseRegistryList;
import mod.nethertweaks.registry.registries.base.types.FluidToWater;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;

public class FluidToWaterRegistry extends BaseRegistryList<FluidToWater> implements IFluidToWaterRegistry {

	public FluidToWaterRegistry() {
		super(
				new GsonBuilder()
				.setPrettyPrinting()
				.create(),
				NTMRegistryManager.FLUID_TO_WATER_DEFAULT_REGISTRY_PROVIDERS
				);
	}

	@Override
	public void register(@Nonnull final String inputFluid, int percOfWater) {
		registry.add(new FluidToWater(inputFluid, percOfWater));
	}

	//ENDE
	
	@Override
	public boolean containsFluid(@Nonnull final ItemStack stack) {
		FluidStack f = FluidUtil.getFluidContained(stack);
		
		if(f != null)
			return registry.stream().anyMatch(entry -> entry.getInputFluid().equals(f.getFluid().getName()));
		else
			return false;
	}
	
	@Override
	public boolean containsFluid(@Nonnull final Fluid fluid) {
		return registry.stream().anyMatch(entry -> entry.getInputFluid().equals(fluid.getName()));
	}
	
	@Override
	@Nonnull
	public FluidToWater getFluid(@Nonnull final ItemStack stack) {
		FluidStack f = FluidUtil.getFluidContained(stack);
		
		if(f != null)
			return registry.stream().filter(entry -> entry.getInputFluid().equals(f.getFluid().getName())).findFirst().orElse(null);
		else
			return null;
	}
	
	@Override
	@Nonnull
	public FluidToWater getFluid(@Nonnull final Fluid fluid) {
		return registry.stream().filter(entry -> entry.getInputFluid().equals(fluid.getName())).findFirst().orElse(null);	
	}

	@Override
	protected void registerEntriesFromJSON(final FileReader fr) {
		final List<FluidToWater> gsonInput = gson.fromJson(fr, new TypeToken<List<FluidToWater>>() {
		}.getType());
		registry.addAll(gsonInput);
	}

	@Override
	public List<?> getRecipeList() {
		return null;
	}
}
