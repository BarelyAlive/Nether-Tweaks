package mod.nethertweaks.registries.registries;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import mod.nethertweaks.api.ICrucibleRegistry;
import mod.nethertweaks.compatibility.CrucibleRecipe;
import mod.nethertweaks.json.CustomBlockInfoJson;
import mod.nethertweaks.json.CustomIngredientJson;
import mod.nethertweaks.json.CustomMeltableJson;
import mod.nethertweaks.registries.ingredient.IngredientUtil;
import mod.nethertweaks.registries.ingredient.OreIngredientStoring;
import mod.nethertweaks.registries.manager.IDefaultRecipeProvider;
import mod.nethertweaks.registries.registries.base.BaseRegistryMap;
import mod.nethertweaks.registry.types.Meltable;
import mod.sfhcore.util.BlockInfo;
import mod.sfhcore.util.LogUtil;
import mod.sfhcore.util.StackInfo;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import java.io.FileReader;
import java.util.*;

public class CrucibleRegistry extends BaseRegistryMap<Ingredient, Meltable> implements ICrucibleRegistry {
    public CrucibleRegistry(List<? extends IDefaultRecipeProvider> defaultRecipeProviders) {
        super(
                new GsonBuilder()
                        .setPrettyPrinting()
                        .registerTypeAdapter(BlockInfo.class, CustomBlockInfoJson.INSTANCE)
                        .registerTypeAdapter(Ingredient.class, CustomIngredientJson.INSTANCE)
                        .registerTypeAdapter(OreIngredientStoring.class, CustomIngredientJson.INSTANCE)
                        .registerTypeAdapter(Meltable.class, CustomMeltableJson.INSTANCE)
                        .enableComplexMapKeySerialization()
                        .create(),
                new TypeToken<Map<Ingredient, Meltable>>() {
                }.getType(),
                defaultRecipeProviders
        );
    }

    public void register(@Nonnull StackInfo item, @Nonnull Fluid fluid, int amount) {
        register(item.getItemStack(), fluid, amount);
    }

    public void register(@Nonnull StackInfo item, @Nonnull Meltable meltable) {
        register(item.getItemStack(), meltable);
    }


    @Override
    public void register(@Nonnull String name, @Nonnull Fluid fluid, int amount, @Nonnull BlockInfo block) {
        register(name, new Meltable(fluid.getName(), amount, block));
    }
    public void register(@Nonnull ItemStack stack, @Nonnull Fluid fluid, int amount) {
        register(stack, new Meltable(fluid.getName(), amount));
    }

    public void register(@Nonnull ItemStack stack, @Nonnull Meltable meltable) {
        if (stack.isEmpty() || !FluidRegistry.isFluidRegistered(meltable.getFluid())) return;
        if (registry.keySet().stream().anyMatch(ingredient -> ingredient.test(stack)))
            LogUtil.warn("Crucible entry for " + stack.getDisplayName() + " with meta " + stack.getMetadata() + " already exists, skipping.");
        else register(CraftingHelper.getIngredient(stack), meltable);
    }

    public void register(@Nonnull String name, @Nonnull Fluid fluid, int amount) {
        register(name, new Meltable(fluid.getName(), amount));
    }

    public void register(@Nonnull String name, @Nonnull Meltable meltable) {
        Ingredient ingredient = new OreIngredientStoring(name);
        if (!FluidRegistry.isFluidRegistered(meltable.getFluid()))
            return;

        if (registry.keySet().stream().anyMatch(entry -> IngredientUtil.ingredientEquals(entry, ingredient)))
            LogUtil.error("Crucible Ore Entry for " + name + " already exists, skipping.");
        else registry.put(ingredient, meltable);
    }

    public boolean canBeMelted(@Nonnull ItemStack stack) {
        return registry.keySet().stream().anyMatch(entry -> entry.test(stack));
    }

    public boolean canBeMelted(@Nonnull StackInfo info) {
        return canBeMelted(info.getItemStack());
    }

    @Nonnull
    public Meltable getMeltable(@Nonnull ItemStack stack) {
        Ingredient ingredient = registry.keySet().stream().filter(entry -> entry.test(stack)).findFirst().orElse(null);

        if (ingredient != null) {
            return registry.get(ingredient);
        } else {
            return Meltable.getEMPTY();
        }
    }

    @Nonnull
    public Meltable getMeltable(@Nonnull StackInfo info) {
        return getMeltable(info.getItemStack());
    }

    @Nonnull
    public Meltable getMeltable(@Nonnull Item item, int meta) {
        return getMeltable(new ItemStack(item, meta));
    }

    @Override
    protected void registerEntriesFromJSON(FileReader fr) {
        Map<String, Meltable> gsonInput = gson.fromJson(fr, new TypeToken<Map<String, Meltable>>() {
        }.getType());

        gsonInput.forEach((key, value) -> {
            Ingredient ingredient = IngredientUtil.parseFromString(key);

            if (registry.keySet().stream().anyMatch(entry -> IngredientUtil.ingredientEquals(ingredient, entry)))
                LogUtil.error("Compost JSON Entry for " + Arrays.toString(ingredient.getMatchingStacks()) + " already exists, skipping.");

            registry.put(ingredient, value);
        });
    }

    @Override
    public Map<Ingredient, Meltable> getRegistry() {
        return registry;
    }

    @Override
    public List<CrucibleRecipe> getRecipeList() {
        List<CrucibleRecipe> recipes = Lists.newLinkedList();

        Map<Fluid, List<List<ItemStack>>> outputMap = new HashMap<>();
        for(Map.Entry<Ingredient, Meltable> entry: getRegistry().entrySet()){
            Fluid output = FluidRegistry.getFluid(entry.getValue().getFluid());
            Ingredient ingredient = entry.getKey();
            if(output == null || ingredient == null)
                continue;
            // Initialize new outputs
            if(!outputMap.containsKey(output)){
                List<List<ItemStack>> inputs = new ArrayList<>();
                outputMap.put(output, inputs);
            }
            // Collect all the potential itemstacks which match this ingredient
            List<ItemStack> inputs = new ArrayList<>();
            for(ItemStack match : ingredient.getMatchingStacks()){
                if(match.isEmpty() || match.getItem() == null)
                    continue;
                ItemStack input = match.copy();
                input.setCount((int) Math.ceil(Fluid.BUCKET_VOLUME / entry.getValue().getAmount()));
                inputs.add(input);
            }
            // Empty oredicts can result in 0 inputs.
            if(inputs.size() > 0)
                outputMap.get(output).add(inputs);
        }
        // Split the recipe up into "pages"
        for(Map.Entry<Fluid, List<List<ItemStack>>> entry : outputMap.entrySet()){
            for(int i = 0; i < entry.getValue().size(); i+=21){
                recipes.add(new CrucibleRecipe(entry.getKey(),
                        entry.getValue().subList(i,  Math.min(i+21, entry.getValue().size()))));
            }
        }

        return recipes;
    }

}