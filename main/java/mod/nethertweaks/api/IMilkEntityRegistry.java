package mod.nethertweaks.api;

import mod.nethertweaks.registry.types.Milkable;
import net.minecraft.entity.Entity;
import net.minecraftforge.fluids.Fluid;

public interface IMilkEntityRegistry extends IRegistryList<Milkable> {

    public void register(Entity entityOnTop, Fluid result, int amount, int coolDown);
    public void register(String entityOnTop, String result, int amount, int coolDown);

    public boolean isValidRecipe(Entity entityOnTop);
    public boolean isValidRecipe(String entityOnTop);

    public Milkable getMilkable(Entity entityOnTop);

    public String getResult(Entity entityOnTop);

    public int getAmount(Entity entityOnTop);

    public int getCoolDown(Entity entityOnTop);
}
