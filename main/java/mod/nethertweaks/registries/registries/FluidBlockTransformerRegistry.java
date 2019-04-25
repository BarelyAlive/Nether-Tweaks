package mod.nethertweaks.registries.registries;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import mod.nethertweaks.api.IFluidBlockTransformerRegistry;
import mod.nethertweaks.json.CustomBlockInfoJson;
import mod.nethertweaks.json.CustomEntityInfoJson;
import mod.nethertweaks.json.CustomFluidBlockTransformerJson;
import mod.nethertweaks.json.CustomIngredientJson;
import mod.nethertweaks.json.CustomItemInfoJson;
import mod.nethertweaks.registries.ingredient.OreIngredientStoring;
import mod.nethertweaks.registries.manager.NTMRegistryManager;
import mod.nethertweaks.registries.registries.base.BaseRegistryList;
import mod.nethertweaks.registry.types.FluidBlockTransformer;
import mod.sfhcore.util.BlockInfo;
import mod.sfhcore.util.EntityInfo;
import mod.sfhcore.util.ItemInfo;
import mod.sfhcore.util.LogUtil;
import mod.sfhcore.util.StackInfo;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

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

    public void register(Fluid fluid, StackInfo inputBlock, StackInfo outputBlock) {
        register(fluid, inputBlock, outputBlock, null);
    }

    public void register(Fluid fluid, @Nonnull StackInfo inputBlock, @Nonnull StackInfo outputBlock, String entityName) {
        if (fluid == null){
            LogUtil.error("Fluid is null, this may not happen!");
            for (StackTraceElement stackTraceElement : Thread.currentThread().getStackTrace()) {
                LogUtil.warn(stackTraceElement);
            }

            return;
        }


        register(fluid.getName(), Ingredient.fromStacks(inputBlock.getItemStack()), outputBlock, entityName, entityName == null ? 0 : 4, entityName == null ? 0 : 4);
    }

    public void register(Fluid fluid, String oredict, StackInfo outputBlock) {
        register(fluid, oredict, outputBlock, null);
    }

    public void register(@Nonnull Fluid fluid, @Nonnull String oredict, @Nonnull StackInfo outputBlock, String entityName) {
        register(fluid.getName(), new OreIngredientStoring(oredict), outputBlock, entityName, entityName == null ? 0 : 4, entityName == null ? 0 : 4);
    }

    /**
     * Main register function
     */
    public void register(@Nonnull String fluid, @Nonnull Ingredient input, @Nonnull StackInfo outputBlock, @Nullable String entityName, int spawnCount, int spawnRange) {
        if (outputBlock.hasBlock()) {
            register(new FluidBlockTransformer(fluid, input, new BlockInfo(outputBlock.getBlockState()), entityName, spawnCount, spawnRange));
        } else {
            LogUtil.error("Item " + outputBlock.toString() + "  has no block version!");
        }
    }


    public boolean canBlockBeTransformedWithThisFluid(@Nonnull Fluid fluid, @Nonnull ItemStack stack) {
        for (FluidBlockTransformer transformer : registry) {
            if (fluid.getName().equals(transformer.getFluidName()) && transformer.getInput().apply(stack))
                return true;
        }

        return false;
    }

    @Nonnull
    public BlockInfo getBlockForTransformation(@Nonnull Fluid fluid, @Nonnull ItemStack stack) {
        for (FluidBlockTransformer transformer : registry) {
            if (fluid.getName().equals(transformer.getFluidName()) && transformer.getInput().apply(stack)) {
                return transformer.getOutput();
            }
        }

        return BlockInfo.EMPTY;
    }

    public int getSpawnCountForTransformation(@Nonnull Fluid fluid, @Nonnull ItemStack stack) {
        for (FluidBlockTransformer transformer : registry) {
            if (fluid.getName().equals(transformer.getFluidName()) && transformer.getInput().apply(stack)) {
                return transformer.getSpawnCount();
            }
        }

        return 0;
    }

    public int getSpawnRangeForTransformation(@Nonnull Fluid fluid, @Nonnull ItemStack stack) {
        for (FluidBlockTransformer transformer : registry) {
            if (fluid.getName().equals(transformer.getFluidName()) && transformer.getInput().apply(stack)) {
                return transformer.getSpawnRange();
            }
        }

        return 0;
    }

    public FluidBlockTransformer getTransformation(@Nonnull Fluid fluid, @Nonnull ItemStack stack) {
        for (FluidBlockTransformer transformer : registry) {
            if (fluid.getName().equals(transformer.getFluidName()) && transformer.getInput().apply(stack)) {
                return transformer;
            }
        }

        return null;
    }

    public EntityInfo getSpawnForTransformation(@Nonnull Fluid fluid, @Nonnull ItemStack stack) {
        for (FluidBlockTransformer transformer : registry) {
            if (fluid.getName().equals(transformer.getFluidName()) && transformer.getInput().apply(stack)) {
                return transformer.getToSpawn();
            }
        }

        return null;
    }

    @Override
    protected void registerEntriesFromJSON(FileReader fr) {
        List<FluidBlockTransformer> gsonInput = gson.fromJson(fr, new TypeToken<List<FluidBlockTransformer>>() {
        }.getType());
        registry.addAll(gsonInput);
    }

	@Override
	public List<?> getRecipeList() {
		// TODO Auto-generated method stub
		return null;
	}
}