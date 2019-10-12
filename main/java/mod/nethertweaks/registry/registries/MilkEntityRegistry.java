package mod.nethertweaks.registry.registries;

import java.io.FileReader;
import java.util.List;

import javax.annotation.Nonnull;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import mod.nethertweaks.api.IMilkEntityRegistry;
import mod.nethertweaks.registry.manager.NTMRegistryManager;
import mod.nethertweaks.registry.registries.base.BaseRegistryList;
import mod.nethertweaks.registry.registries.base.types.Milkable;
import mod.sfhcore.json.CustomBlockInfoJson;
import mod.sfhcore.json.CustomItemInfoJson;
import mod.sfhcore.util.BlockInfo;
import mod.sfhcore.util.ItemInfo;
import net.minecraft.entity.Entity;
import net.minecraftforge.fluids.Fluid;

public class MilkEntityRegistry extends BaseRegistryList<Milkable> implements IMilkEntityRegistry {
	public MilkEntityRegistry() {
		super(
				new GsonBuilder()
				.setPrettyPrinting()
				.registerTypeAdapter(ItemInfo.class, new CustomItemInfoJson())
				.registerTypeAdapter(BlockInfo.class, new CustomBlockInfoJson())
				.create(),
				NTMRegistryManager.MILK_ENTITY_DEFAULT_REGISTRY_PROVIDERS
				);
	}

	@Override
	public void register(@Nonnull final Entity entityOnTop, @Nonnull final Fluid result, final int amount, final int coolDown) {
		registry.add(new Milkable(entityOnTop.getName(), result.getName(), amount, coolDown));
	}

	@Override
	public void register(@Nonnull final String entityOnTop, @Nonnull final String result, final int amount, final int coolDown) {
		registry.add(new Milkable(entityOnTop, result, amount, coolDown));
	}

	@Override
	public boolean isValidRecipe(final Entity entityOnTop) {
		return entityOnTop != null && this.isValidRecipe(entityOnTop.getName());
	}

	@Override
	public boolean isValidRecipe(final String entityOnTop) {
		if (entityOnTop == null)
			return false;
		for (Milkable milk : registry)
			if (milk.getEntityOnTop().equals(entityOnTop))
				return true;
		return false;
	}

	@Override
	public Milkable getMilkable(final Entity entityOnTop) {
		// Returns the entire milkable object instead of having use multiple functions
		if (entityOnTop == null)
			return null;
		for (Milkable milk : registry)
			if (milk.getEntityOnTop().equals(entityOnTop.getName()))
				return milk;
		return null;
	}

	@Override
	public String getResult(@Nonnull final Entity entityOnTop) {
		for (Milkable milk : registry)
			if (milk.getEntityOnTop().equals(entityOnTop.getName()))
				return milk.getResult();
		return null;
	}

	@Override
	public int getAmount(@Nonnull final Entity entityOnTop) {
		for (Milkable milk : registry)
			if (milk.getEntityOnTop().equals(entityOnTop.getName()))
				return milk.getAmount();
		return 0;
	}

	@Override
	public int getCoolDown(@Nonnull final Entity entityOnTop) {
		for (Milkable milk : registry)
			if (milk.getEntityOnTop().equals(entityOnTop.getName()))
				return milk.getCoolDown();
		return 0;
	}

	@Override
	public void registerEntriesFromJSON(final FileReader fr) {
		List<Milkable> gsonInput = gson.fromJson(fr, new TypeToken<List<Milkable>>() {
		}.getType());
		registry.addAll(gsonInput);
	}

	@Override
	public List<?> getRecipeList() {
		// TODO Auto-generated method stub
		return null;
	}
}
