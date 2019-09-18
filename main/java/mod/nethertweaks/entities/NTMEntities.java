package mod.nethertweaks.entities;

import mod.nethertweaks.Constants;
import mod.nethertweaks.NetherTweaksMod;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class NTMEntities
{
	public static void init()
	{
		EntityRegistry.registerModEntity(new ResourceLocation(Constants.MODID, "Thrown Stone"), ProjectileStone.class, "Thrown Stone", 0, NetherTweaksMod.getInstance(), 64, 10, true);
		EntityRegistry.registerModEntity(new ResourceLocation(Constants.MODID, "entityLava"), EntityItemLava.class, "entityLava", 1, NetherTweaksMod.getInstance(), 64, 10, true);
	}
}
