package mod.nethertweaks.api;

import mod.nethertweaks.registry.types.Milkable;
import net.minecraft.entity.Entity;
import net.minecraftforge.fluids.Fluid;

public interface IMilkEntityRegistry extends IRegistryList<Milkable> {

	void register(Entity entityOnTop, Fluid result, int amount, int coolDown);
	void register(String entityOnTop, String result, int amount, int coolDown);

	boolean isValidRecipe(Entity entityOnTop);
	boolean isValidRecipe(String entityOnTop);

	Milkable getMilkable(Entity entityOnTop);

	String getResult(Entity entityOnTop);

	int getAmount(Entity entityOnTop);

	int getCoolDown(Entity entityOnTop);
}
