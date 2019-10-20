package mod.nethertweaks.init;

import mod.nethertweaks.Constants;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.entities.EntityItemLava;
import mod.nethertweaks.entities.ProjectileStone;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class ModEntities
{
	public static void preInit()
	{
		EntityRegistry.registerModEntity(new ResourceLocation(Constants.MOD_ID, "Thrown Stone"), ProjectileStone.class, "Thrown Stone", 0, NetherTweaksMod.getInstance(), 64, 10, true);
		EntityRegistry.registerModEntity(new ResourceLocation(Constants.MOD_ID, "entityLava"), EntityItemLava.class, "entityLava", 1, NetherTweaksMod.getInstance(), 64, 10, true);
	}
}
