package mod.nethertweaks.registries.registries;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import mod.nethertweaks.api.ICrucibleRegistry;
import mod.nethertweaks.compatibility.CrucibleRecipe;
import mod.nethertweaks.json.CustomIngredientJson;
import mod.nethertweaks.json.CustomMeltableJson;
import mod.nethertweaks.registries.ingredient.IngredientUtil;
import mod.nethertweaks.registries.ingredient.OreIngredientStoring;
import mod.nethertweaks.registries.manager.IDefaultRecipeProvider;
import mod.nethertweaks.registries.registries.base.BaseRegistryMap;
import mod.nethertweaks.registry.types.Meltable;
import mod.sfhcore.json.CustomBlockInfoJson;
import mod.sfhcore.util.BlockInfo;
import mod.sfhcore.util.LogUtil;
import mod.sfhcore.util.StackInfo;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class CrucibleRegistry extends BaseRegistryMap<Ingredient, Meltable> implements ICrucibleRegistry {
	public CrucibleRegistry(final List<? extends IDefaultRecipeProvider> defaultRecipeProviders) {
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

	@Override
	public void register(@Nonnull final StackInfo item, @Nonnull final Fluid fluid, final int amount) {
		register(item.getItemStack(), fluid, amount);
	}

	@Override
	public void register(@Nonnull final StackInfo item, @Nonnull final Meltable meltable) {
		register(item.getItemStack(), meltable);
	}


	@Override
	public void register(@Nonnull final String name, @Nonnull final Fluid fluid, final int amount, @Nonnull final BlockInfo block) {
		register(name, new Meltable(fluid.getName(), amount, block));
	}
	@Override
	public void register(@Nonnull final ItemStack stack, @Nonnull final Fluid fluid, final int amount) {
		register(stack, new Meltable(fluid.getName(), amount));
	}

	@Override
	public void register(@Nonnull final ItemStack stack, @Nonnull final Meltable meltable) {
		if (stack.isEmpty() || !FluidRegistry.isFluidRegistered(meltable.getFluid())) return;
		if (registry.keySet().stream().anyMatch(ingredient -> ingredient.test(stack)))
			LogUtil.warn("Crucible entry for " + stack.getDisplayName() + " with meta " + stack.getMetadata() + " already exists, skipping.");
		else register(CraftingHelper.getIngredient(stack), meltable);
	}

	@Override
	public void register(@Nonnull final String name, @Nonnull final Fluid fluid, final int amount) {
		register(name, new Meltable(fluid.getName(), amount));
	}

	@Override
	public void register(@Nonnull final String name, @Nonnull final Meltable meltable) {
		Ingredient ingredient = new OreIngredientStoring(name);
		if (!FluidRegistry.isFluidRegistered(meltable.getFluid()))
			return;

		if (registry.keySet().stream().anyMatch(entry -> IngredientUtil.ingredientEquals(entry, ingredient)))
			LogUtil.error("Crucible Ore Entry for " + name + " already exists, skipping.");
		else registry.put(ingredient, meltable);
	}

	@Override
	public boolean canBeMelted(@Nonnull final ItemStack stack) {
		return registry.keySet().stream().anyMatch(entry -> entry.test(stack));
	}

	@Override
	public boolean canBeMelted(@Nonnull final StackInfo info) {
		return canBeMelted(info.getItemStack());
	}

	@Override
	@Nonnull
	public Meltable getMeltable(@Nonnull final ItemStack stack) {
		Ingredient ingredient = registry.keySet().stream().filter(entry -> entry.test(stack)).findFirst().orElse(null);

		if (ingredient != null)
			return registry.get(ingredient);
		else
			return Meltable.getEMPTY();
	}

	@Override
	@Nonnull
	public Meltable getMeltable(@Nonnull final StackInfo info) {
		return getMeltable(info.getItemStack());
	}

	@Override
	@Nonnull
	public Meltable getMeltable(@Nonnull final Item item, final int meta) {
		return getMeltable(new ItemStack(item, meta));
	}

	@Override
	protected void registerEntriesFromJSON(final FileReader fr) {
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
				if(match.isEmpty()) {
					continue;
				} else {
					match.getItem();
				}
				ItemStack input = match.copy();
				input.setCount((int) Math.ceil(Fluid.BUCKET_VOLUME / entry.getValue().getAmount()));
				inputs.add(input);
			}
			// Empty oredicts can result in 0 inputs.
			if(inputs.size() > 0)
				outputMap.get(output).add(inputs);
		}
		// Split the recipe up into "pages"
		for(Map.Entry<Fluid, List<List<ItemStack>>> entry : outputMap.entrySet())
			for(int i = 0; i < entry.getValue().size(); i+=21)
				recipes.add(new CrucibleRecipe(entry.getKey(),
						entry.getValue().subList(i,  Math.min(i+21, entry.getValue().size()))));

		return recipes;
	}

}