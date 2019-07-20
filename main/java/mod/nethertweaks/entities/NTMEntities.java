package mod.nethertweaks.entities;

import mod.nethertweaks.NetherTweaksMod;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class NTMEntities
{
    public static void init()
    {
        EntityRegistry.registerModEntity(new ResourceLocation(NetherTweaksMod.MODID, "Thrown Stone"), ProjectileStone.class, "Thrown Stone", 0, NetherTweaksMod.instance, 64, 10, true);
        EntityRegistry.registerModEntity(new ResourceLocation(NetherTweaksMod.MODID, "entityLava"), EntityItemLava.class, "entityLava", 1, NetherTweaksMod.instance, 64, 10, true);
    }
}
