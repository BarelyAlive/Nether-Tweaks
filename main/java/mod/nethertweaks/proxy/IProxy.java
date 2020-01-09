package mod.nethertweaks.proxy;

import net.minecraftforge.fml.common.FMLCommonHandler;

public interface IProxy
{
	void preInit();
	void init();
	void postInit();

	static boolean isClient()
	{
		return FMLCommonHandler.instance().getSide().isClient();
	}
}
