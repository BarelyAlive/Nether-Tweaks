package mod.nethertweaks.compatibility;

import java.util.*;

import javax.annotation.*;

import com.google.common.collect.Lists;

import mezz.jei.api.ingredients.*;
import mezz.jei.api.recipe.*;

import net.minecraft.client.*;
import net.minecraft.item.*;
import net.minecraftforge.fluids.*;

public class CrucibleRecipe implements IRecipeWrapper {
    private final List<List<ItemStack>> inputs;
    private final ItemStack output;

    public CrucibleRecipe(Fluid fluid, List<List<ItemStack>> inputs) {
        this.output = FluidUtil.getFilledBucket(new FluidStack(fluid, Fluid.BUCKET_VOLUME));
        this.inputs = inputs;
    }

    @Override
    public void getIngredients(@Nonnull IIngredients ingredients) {
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
    public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
    }

    @Override
    @Nonnull
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        return Lists.newArrayList();
    }

    @Override
    public boolean handleClick(@Nonnull Minecraft minecraft, int mouseX, int mouseY, int mouseButton) {
        return false;
    }

}