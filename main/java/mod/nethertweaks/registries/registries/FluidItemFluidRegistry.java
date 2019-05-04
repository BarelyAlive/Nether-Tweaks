package mod.nethertweaks.registries.registries;

import java.io.FileReader;
import java.util.List;

import javax.annotation.Nonnull;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import mod.nethertweaks.api.IFluidItemFluidRegistry;
import mod.nethertweaks.registries.manager.NTMRegistryManager;
import mod.nethertweaks.registries.registries.base.BaseRegistryList;
import mod.nethertweaks.registry.types.FluidItemFluid;
import mod.sfhcore.json.CustomBlockInfoJson;
import mod.sfhcore.json.CustomItemInfoJson;
import mod.sfhcore.util.BlockInfo;
import mod.sfhcore.util.ItemInfo;
import mod.sfhcore.util.StackInfo;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;

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

    public void register(@Nonnull String inputFluid, @Nonnull StackInfo reactant, @Nonnull String outputFluid) {
        registry.add(new FluidItemFluid(inputFluid, reactant, outputFluid));
    }

    public void register(@Nonnull Fluid inputFluid, @Nonnull StackInfo reactant, @Nonnull Fluid outputFluid) {
        registry.add(new FluidItemFluid(inputFluid.getName(), reactant, outputFluid.getName()));
    }

    public String getFluidForTransformation(@Nonnull Fluid fluid, @Nonnull ItemStack stack) {
        ItemInfo info = new ItemInfo(stack);

        for (FluidItemFluid transformer : registry) {
            if (fluid.getName().equals(transformer.getInputFluid()) && info.equals(transformer.getReactant())) {
                return transformer.getOutput();
            }
        }

        return null;
    }

    @Override
    protected void registerEntriesFromJSON(FileReader fr) {
        List<FluidItemFluid> gsonInput = gson.fromJson(fr, new TypeToken<List<FluidItemFluid>>() {
        }.getType());
        registry.addAll(gsonInput);
    }

	@Override
	public List<?> getRecipeList() {
		return null;
	}
}
