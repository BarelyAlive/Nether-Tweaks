package mod.nethertweaks.compatibility;

import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.config.Config;
import mod.nethertweaks.modules.MooFluidsEtc;
import net.minecraftforge.fml.common.Loader;

public class Compatibility
{
	public static void init()
	{
		if(Loader.isModLoaded("moofluids") ||
                Loader.isModLoaded("minimoos") ||
                Loader.isModLoaded("fluidcows"))
        	NetherTweaksMod.loadedModules.add(new MooFluidsEtc());
		/*
		if(Loader.isModLoaded("appliedenergistics2"))
            NetherTweaksMod.loadedModules.add(new AppliedEnergistics2());
        if(Loader.isModLoaded("forestry"))
            NetherTweaksMod.loadedModules.add(new Forestry());
        if(Loader.isModLoaded("tconstruct") && Config.doTinkersConstructCompat)
        	NetherTweaksMod.loadedModules.add(new TinkersConstruct());
        if(Loader.isModLoaded("oreberries") && Config.enableOreBerrySeeds)
        	NetherTweaksMod.loadedModules.add(new OreBerries());
        if(Loader.isModLoaded("magneticraft") && Config.magneticraftHammersCompat)
        	NetherTweaksMod.loadedModules.add(Magneticraft.INSTANCE);
        	*/
	}
}
