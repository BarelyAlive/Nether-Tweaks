package mod.nethertweaks.api;

import javax.annotation.Nullable;

import mod.nethertweaks.registry.types.FluidBlockTransformer;
import mod.sfhcore.util.BlockInfo;
import mod.sfhcore.util.EntityInfo;
import mod.sfhcore.util.StackInfo;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fluids.Fluid;

public interface IFluidBlockTransformerRegistry extends IRegistryList<FluidBlockTransformer>
{
    public void register(@Nullable Fluid fluid, StackInfo inputBlock, StackInfo outputBlock, String entityName);
    public void register(Fluid fluid, String oredict, StackInfo outputBlock, @Nullable String entityName);
    public void register(String fluid, Ingredient input, StackInfo outputBlock, @Nullable String entityName, int spawnCount, int spawnRange);

    public boolean canBlockBeTransformedWithThisFluid(Fluid fluid, ItemStack stack);
    public BlockInfo getBlockForTransformation(Fluid fluid, ItemStack stack);
    public int getSpawnCountForTransformation(Fluid fluid, ItemStack stack);
    public int getSpawnRangeForTransformation(Fluid fluid, ItemStack stack);

    public FluidBlockTransformer getTransformation(Fluid fluid, ItemStack stack);
    public EntityInfo getSpawnForTransformation(Fluid fluid, ItemStack stack);
}
