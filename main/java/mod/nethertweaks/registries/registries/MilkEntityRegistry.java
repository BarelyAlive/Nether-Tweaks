package mod.nethertweaks.registries.registries;

import java.io.FileReader;
import java.util.List;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import mod.nethertweaks.api.IMilkEntityRegistry;
import mod.nethertweaks.json.CustomBlockInfoJson;
import mod.nethertweaks.json.CustomItemInfoJson;
import mod.nethertweaks.registries.manager.NTMRegistryManager;
import mod.nethertweaks.registries.registries.base.BaseRegistryList;
import mod.nethertweaks.registry.types.Milkable;
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

    public void register(@Nonnull Entity entityOnTop, @Nonnull Fluid result, int amount, int coolDown) {
        registry.add(new Milkable(entityOnTop.getName(), result.getName(), amount, coolDown));
    }

    public void register(@Nonnull String entityOnTop, @Nonnull String result, int amount, int coolDown) {
        registry.add(new Milkable(entityOnTop, result, amount, coolDown));
    }

    public boolean isValidRecipe(Entity entityOnTop) {
        return entityOnTop != null && this.isValidRecipe(entityOnTop.getName());
    }

    public boolean isValidRecipe(String entityOnTop) {
        if (entityOnTop == null) {
            return false;
        }
        for (Milkable milk : registry) {
            if (milk.getEntityOnTop().equals(entityOnTop)) {
                return true;
            }
        }
        return false;
    }

    public Milkable getMilkable(Entity entityOnTop) {
        // Returns the entire milkable object instead of having use multiple functions
        if (entityOnTop == null) {
            return null;
        }
        for (Milkable milk : registry) {
            if (milk.getEntityOnTop().equals(entityOnTop.getName())) {
                return milk;
            }
        }
        return null;
    }

    public String getResult(@Nonnull Entity entityOnTop) {
        for (Milkable milk : registry) {
            if (milk.getEntityOnTop().equals(entityOnTop.getName())) {
                return milk.getResult();
            }
        }
        return null;
    }

    public int getAmount(@Nonnull Entity entityOnTop) {
        for (Milkable milk : registry) {
            if (milk.getEntityOnTop().equals(entityOnTop.getName())) {
                return milk.getAmount();
            }
        }
        return 0;
    }

    public int getCoolDown(@Nonnull Entity entityOnTop) {
        for (Milkable milk : registry) {
            if (milk.getEntityOnTop().equals(entityOnTop.getName())) {
                return milk.getCoolDown();
            }
        }
        return 0;
    }

    @Override
    public void registerEntriesFromJSON(FileReader fr) {
        List<Milkable> gsonInput = gson.fromJson(fr, new TypeToken<List<Milkable>>() {
        }.getType());
        registry.addAll(gsonInput);
    }
}
