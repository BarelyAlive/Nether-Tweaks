package mod.nethertweaks.capabilities;

import net.minecraftforge.common.capabilities.CapabilityManager;

public class ModCapabilities
{
	public static void preInit()
	{
		CapabilityManager.INSTANCE.register(ICapabilityHeat.class, CapabilityHeatManager.INSTANCE, CapabilityHeatManager.INSTANCE);
	}
}
