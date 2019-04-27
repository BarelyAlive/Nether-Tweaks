package mod.nethertweaks.registries.registries;

import java.io.FileReader;
import java.util.List;

import javax.annotation.Nonnull;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import mod.nethertweaks.api.IFluidOnTopRegistry;
import mod.nethertweaks.registries.manager.NTMRegistryManager;
import mod.nethertweaks.registries.registries.base.BaseRegistryList;
import mod.nethertweaks.registry.types.FluidFluidBlock;
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

    public void register(@Nonnull Fluid fluidInBarrel, @Nonnull Fluid fluidOnTop, @Nonnull BlockInfo result) {
        registry.add(new FluidFluidBlock(fluidInBarrel.getName(), fluidOnTop.getName(), result));
    }

    public void register(@Nonnull Fluid fluidInBarrel, @Nonnull Fluid fluidOnTop, @Nonnull ItemInfo result) {
        if (result.hasBlock())
            registry.add(new FluidFluidBlock(fluidInBarrel.getName(), fluidOnTop.getName(), new BlockInfo(result.getItemStack())));
    }

    public boolean isValidRecipe(Fluid fluidInBarrel, Fluid fluidOnTop) {
        if (fluidInBarrel == null || fluidOnTop == null)
            return false;
        for (FluidFluidBlock fBlock : registry) {
            if (fBlock.getFluidInBarrel().equals(fluidInBarrel.getName()) &&
                    fBlock.getFluidOnTop().equals(fluidOnTop.getName()))
                return true;
        }

        return false;
    }

    @Nonnull
    public BlockInfo getTransformedBlock(@Nonnull Fluid fluidInBarrel, @Nonnull Fluid fluidOnTop) {
        for (FluidFluidBlock fBlock : registry) {
            if (fBlock.getFluidInBarrel().equals(fluidInBarrel.getName()) &&
                    fBlock.getFluidOnTop().equals(fluidOnTop.getName()))
                return fBlock.getResult();
        }

        return BlockInfo.EMPTY;
    }

    @Override
    protected void registerEntriesFromJSON(FileReader fr) {
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