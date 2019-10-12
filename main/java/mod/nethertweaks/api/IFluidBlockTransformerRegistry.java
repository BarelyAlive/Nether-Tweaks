package mod.nethertweaks.api;

import javax.annotation.Nullable;

import mod.nethertweaks.registry.registries.base.types.FluidBlockTransformer;
import mod.sfhcore.util.BlockInfo;
import mod.sfhcore.util.EntityInfo;
import mod.sfhcore.util.StackInfo;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fluids.Fluid;

public interface IFluidBlockTransformerRegistry extends IRegistryList<FluidBlockTransformer>
{
	void register(@Nullable Fluid fluid, StackInfo inputBlock, StackInfo outputBlock, String entityName);
	void register(Fluid fluid, String oredict, StackInfo outputBlock, @Nullable String entityName);
	void register(String fluid, Ingredient input, StackInfo outputBlock, @Nullable String entityName, int spawnCount, int spawnRange);

	boolean canBlockBeTransformedWithThisFluid(Fluid fluid, ItemStack stack);
	BlockInfo getBlockForTransformation(Fluid fluid, ItemStack stack);
	int getSpawnCountForTransformation(Fluid fluid, ItemStack stack);
	int getSpawnRangeForTransformation(Fluid fluid, ItemStack stack);

	FluidBlockTransformer getTransformation(Fluid fluid, ItemStack stack);
	EntityInfo getSpawnForTransformation(Fluid fluid, ItemStack stack);
}
