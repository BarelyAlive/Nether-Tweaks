package mod.nethertweaks.registry.registries;

import java.io.FileReader;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import mod.nethertweaks.api.IFluidBlockTransformerRegistry;
import mod.nethertweaks.json.CustomFluidBlockTransformerJson;
import mod.nethertweaks.json.CustomIngredientJson;
import mod.nethertweaks.registry.ingredient.OreIngredientStoring;
import mod.nethertweaks.registry.manager.NTMRegistryManager;
import mod.nethertweaks.registry.registries.base.BaseRegistryList;
import mod.nethertweaks.registry.registries.base.types.FluidBlockTransformer;
import mod.sfhcore.json.CustomBlockInfoJson;
import mod.sfhcore.json.CustomEntityInfoJson;
import mod.sfhcore.json.CustomItemInfoJson;
import mod.sfhcore.util.BlockInfo;
import mod.sfhcore.util.EntityInfo;
import mod.sfhcore.util.ItemInfo;
import mod.sfhcore.util.LogUtil;
import mod.sfhcore.util.StackInfo;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fluids.Fluid;

public class FluidBlockTransformerRegistry extends BaseRegistryList<FluidBlockTransformer> implements IFluidBlockTransformerRegistry {

	public FluidBlockTransformerRegistry() {
		super(
				new GsonBuilder()
				.setPrettyPrinting()
				.registerTypeAdapter(ItemInfo.class, new CustomItemInfoJson())
				.registerTypeAdapter(StackInfo.class, new CustomItemInfoJson())
				.registerTypeAdapter(BlockInfo.class, new CustomBlockInfoJson())
				.registerTypeAdapter(Ingredient.class, new CustomIngredientJson())
				.registerTypeAdapter(OreIngredientStoring.class, new CustomIngredientJson())
				.registerTypeAdapter(EntityInfo.class, new CustomEntityInfoJson())
				.registerTypeAdapter(FluidBlockTransformer.class, new CustomFluidBlockTransformerJson())
				.create(),
				NTMRegistryManager.FLUID_BLOCK_DEFAULT_REGISTRY_PROVIDERS
				);
	}

	public void register(final Fluid fluid, final StackInfo inputBlock, final StackInfo outputBlock) {
		register(fluid, inputBlock, outputBlock, null);
	}

	@Override
	public void register(final Fluid fluid, @Nonnull final StackInfo inputBlock, @Nonnull final StackInfo outputBlock, final String entityName) {
		if (fluid == null){
			LogUtil.error("Fluid is null, this may not happen!");
			for (final StackTraceElement stackTraceElement : Thread.currentThread().getStackTrace())
				LogUtil.warn(stackTraceElement);

			return;
		}


		register(fluid.getName(), Ingredient.fromStacks(inputBlock.getItemStack()), outputBlock, entityName, entityName == null ? 0 : 4, entityName == null ? 0 : 4);
	}

	public void register(final Fluid fluid, final String oredict, final StackInfo outputBlock) {
		register(fluid, oredict, outputBlock, null);
	}

	@Override
	public void register(@Nonnull final Fluid fluid, @Nonnull final String oredict, @Nonnull final StackInfo outputBlock, final String entityName) {
		register(fluid.getName(), new OreIngredientStoring(oredict), outputBlock, entityName, entityName == null ? 0 : 4, entityName == null ? 0 : 4);
	}

	/**
	 * Main register function
	 */
	@Override
	public void register(@Nonnull final String fluid, @Nonnull final Ingredient input, @Nonnull final StackInfo outputBlock, @Nullable final String entityName, final int spawnCount, final int spawnRange) {
		if (outputBlock.hasBlock())
			register(new FluidBlockTransformer(fluid, input, new BlockInfo(outputBlock.getBlockState()), entityName, spawnCount, spawnRange));
		else
			LogUtil.error("Item " + outputBlock.toString() + "  has no block version!");
	}


	@Override
	public boolean canBlockBeTransformedWithThisFluid(@Nonnull final Fluid fluid, @Nonnull final ItemStack stack) {
		for (final FluidBlockTransformer transformer : registry)
			if (fluid.getName().equals(transformer.getFluidName()) && transformer.getInput().apply(stack))
				return true;

		return false;
	}

	@Override
	@Nonnull
	public BlockInfo getBlockForTransformation(@Nonnull final Fluid fluid, @Nonnull final ItemStack stack) {
		for (final FluidBlockTransformer transformer : registry)
			if (fluid.getName().equals(transformer.getFluidName()) && transformer.getInput().apply(stack))
				return transformer.getOutput();

		return BlockInfo.EMPTY;
	}

	@Override
	public int getSpawnCountForTransformation(@Nonnull final Fluid fluid, @Nonnull final ItemStack stack) {
		for (final FluidBlockTransformer transformer : registry)
			if (fluid.getName().equals(transformer.getFluidName()) && transformer.getInput().apply(stack))
				return transformer.getSpawnCount();

		return 0;
	}

	@Override
	public int getSpawnRangeForTransformation(@Nonnull final Fluid fluid, @Nonnull final ItemStack stack) {
		for (final FluidBlockTransformer transformer : registry)
			if (fluid.getName().equals(transformer.getFluidName()) && transformer.getInput().apply(stack))
				return transformer.getSpawnRange();

		return 0;
	}

	@Override
	public FluidBlockTransformer getTransformation(@Nonnull final Fluid fluid, @Nonnull final ItemStack stack) {
		for (final FluidBlockTransformer transformer : registry)
			if (fluid.getName().equals(transformer.getFluidName()) && transformer.getInput().apply(stack))
				return transformer;

		return null;
	}

	@Override
	public EntityInfo getSpawnForTransformation(@Nonnull final Fluid fluid, @Nonnull final ItemStack stack) {
		for (final FluidBlockTransformer transformer : registry)
			if (fluid.getName().equals(transformer.getFluidName()) && transformer.getInput().apply(stack))
				return transformer.getToSpawn();

		return null;
	}

	@Override
	protected void registerEntriesFromJSON(final FileReader fr) {
		final List<FluidBlockTransformer> gsonInput = gson.fromJson(fr, new TypeToken<List<FluidBlockTransformer>>() {
		}.getType());
		registry.addAll(gsonInput);
	}

	@Override
	public List<?> getRecipeList() {
		// TODO Auto-generated method stub
		return null;
	}
}