package mod.nethertweaks.registry.registries;

import java.io.FileReader;
import java.util.List;

import javax.annotation.Nonnull;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import mod.nethertweaks.api.IFluidItemFluidRegistry;
import mod.nethertweaks.registry.manager.NTMRegistryManager;
import mod.nethertweaks.registry.registries.base.BaseRegistryList;
import mod.nethertweaks.registry.registries.base.types.FluidItemFluid;
import mod.sfhcore.json.CustomBlockInfoJson;
import mod.sfhcore.json.CustomItemInfoJson;
import mod.sfhcore.util.BlockInfo;
import mod.sfhcore.util.ItemInfo;
import mod.sfhcore.util.StackInfo;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.oredict.OreDictionary;

public class FluidItemFluidRegistry extends BaseRegistryList<FluidItemFluid> implements IFluidItemFluidRegistry {

	public FluidItemFluidRegistry() {
		super(
				new GsonBuilder()
				.setPrettyPrinting()
				.registerTypeAdapter(ItemInfo.class, new CustomItemInfoJson())
				.registerTypeAdapter(StackInfo.class, new CustomItemInfoJson())
				.registerTypeAdapter(BlockInfo.class, new CustomBlockInfoJson())
				.create(),
				NTMRegistryManager.FLUID_ITEM_FLUID_DEFAULT_REGISTRY_PROVIDERS
				);
	}

	@Override
	public void register(@Nonnull final String inputFluid, @Nonnull final StackInfo reactant, @Nonnull final String outputFluid) {
		registry.add(new FluidItemFluid(inputFluid, reactant, outputFluid));
	}

	@Override
	public void register(@Nonnull final Fluid inputFluid, @Nonnull final StackInfo reactant, @Nonnull final Fluid outputFluid) {
		registry.add(new FluidItemFluid(inputFluid.getName(), reactant, outputFluid.getName()));
	}

	@Override
	public void register(@Nonnull final Fluid inputFluid, @Nonnull final StackInfo reactant, @Nonnull final Fluid outputFluid, final int transformTime) {
		registry.add(new FluidItemFluid(inputFluid.getName(), reactant, outputFluid.getName(), transformTime));
	}

	@Override
	public void register(@Nonnull final Fluid inputFluid, @Nonnull final StackInfo reactant, @Nonnull final Fluid outputFluid, final int transformTime, final boolean consumable) {
		registry.add(new FluidItemFluid(inputFluid.getName(), reactant, outputFluid.getName(), transformTime, consumable));
	}

	//ORE-DICT
	@Override
	public void register(@Nonnull final String inputFluid, @Nonnull final String reactant, @Nonnull final String outputFluid) {
		for(ItemStack stack : OreDictionary.getOres(reactant))
			registry.add(new FluidItemFluid(inputFluid, new ItemInfo(stack), outputFluid));
	}

	@Override
	public void register(@Nonnull final Fluid inputFluid, @Nonnull final String reactant, @Nonnull final Fluid outputFluid) {
		for(ItemStack stack : OreDictionary.getOres(reactant))
			registry.add(new FluidItemFluid(inputFluid.getName(), new ItemInfo(stack), outputFluid.getName()));
	}

	@Override
	public void register(@Nonnull final Fluid inputFluid, @Nonnull final String reactant, @Nonnull final Fluid outputFluid, final int transformTime) {
		for(ItemStack stack : OreDictionary.getOres(reactant))
			registry.add(new FluidItemFluid(inputFluid.getName(), new ItemInfo(stack), outputFluid.getName(), transformTime));
	}

	@Override
	public void register(@Nonnull final Fluid inputFluid, @Nonnull final String reactant, @Nonnull final Fluid outputFluid, final int transformTime, final boolean consumable) {
		for(ItemStack stack : OreDictionary.getOres(reactant))
			registry.add(new FluidItemFluid(inputFluid.getName(), new ItemInfo(stack), outputFluid.getName(), transformTime, consumable));
	}

	//ENDE

	@Override
	public String getFluidForTransformation(@Nonnull final Fluid fluid, @Nonnull final ItemStack stack) {
		final ItemInfo info = new ItemInfo(stack);

		for (final FluidItemFluid transformer : registry)
			if (fluid.getName().equals(transformer.getInputFluid()) && info.equals(transformer.getReactant()))
				return transformer.getOutput();

		return null;
	}

	public int getTransformTime(@Nonnull final Fluid fluid, @Nonnull final ItemStack stack) {
		final ItemInfo info = new ItemInfo(stack);

		for (final FluidItemFluid transformer : registry)
			if (fluid.getName().equals(transformer.getInputFluid()) && info.equals(transformer.getReactant()))
				return transformer.getTransformTime();

		return 0;
	}

	public boolean getConsumable(@Nonnull final Fluid fluid, @Nonnull final ItemStack stack) {
		final ItemInfo info = new ItemInfo(stack);

		for (final FluidItemFluid transformer : registry)
			if (fluid.getName().equals(transformer.getInputFluid()) && info.equals(transformer.getReactant()))
				return transformer.isConsumable();

		return false;
	}

	@Override
	protected void registerEntriesFromJSON(final FileReader fr) {
		final List<FluidItemFluid> gsonInput = gson.fromJson(fr, new TypeToken<List<FluidItemFluid>>() {
		}.getType());
		registry.addAll(gsonInput);
	}

	@Override
	public List<?> getRecipeList() {
		return null;
	}
}
