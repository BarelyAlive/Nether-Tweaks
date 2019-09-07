package mod.nethertweaks.capabilities;

import net.minecraftforge.common.capabilities.CapabilityManager;

public class NTMCapabilities
{
	public static void init()
	{
		CapabilityManager.INSTANCE.register(ICapabilityHeat.class, CapabilityHeatManager.INSTANCE, CapabilityHeatManager.INSTANCE);
	}
}
