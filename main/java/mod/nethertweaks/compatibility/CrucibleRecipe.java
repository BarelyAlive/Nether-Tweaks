package mod.nethertweaks.compatibility;

import java.util.List;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;

public class CrucibleRecipe implements IRecipeWrapper {
	private final List<List<ItemStack>> inputs;
	private final ItemStack output;

	public CrucibleRecipe(final Fluid fluid, final List<List<ItemStack>> inputs) {
		output = FluidUtil.getFilledBucket(new FluidStack(fluid, Fluid.BUCKET_VOLUME));
		this.inputs = inputs;
	}

	@Override
	public void getIngredients(@Nonnull final IIngredients ingredients) {
		ingredients.setInputLists(VanillaTypes.ITEM, inputs);
		ingredients.setOutput(VanillaTypes.ITEM, output);
	}

	public  List<List<ItemStack>> getInputs() {
		return inputs;
	}

	public ItemStack getFluid()
	{
		return output;
	}

	@Override
	public void drawInfo(@Nonnull final Minecraft minecraft, final int recipeWidth, final int recipeHeight, final int mouseX, final int mouseY) {
	}

	@Override
	@Nonnull
	public List<String> getTooltipStrings(final int mouseX, final int mouseY) {
		return Lists.newArrayList();
	}

	@Override
	public boolean handleClick(@Nonnull final Minecraft minecraft, final int mouseX, final int mouseY, final int mouseButton) {
		return false;
	}

}