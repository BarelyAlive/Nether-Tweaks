package mod.nethertweaks.modules;

import java.util.HashSet;
import java.util.Set;

import mod.nethertweaks.api.INetherTweaksModModule;
import mod.nethertweaks.config.Config;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

public class MooFluidsEtc implements INetherTweaksModModule {

	private static final Set<Fluid> fluidSet = new HashSet<>();
	private static boolean loaded = false;

	public MooFluidsEtc(){
		loaded = true;
	}

	public static boolean isLoaded()
	{
		return loaded;
	}

	@Override
	public String getMODID() {
		return "minimoofluidcow";
	}

	@Override
	public void postInitServer(final FMLPostInitializationEvent event){
		for(final String s : Config.fluidList)
			if(FluidRegistry.isFluidRegistered(s))
				fluidSet.add(FluidRegistry.getFluid(s));
	}

	public static boolean fluidIsAllowed(final Fluid fluid){
		return fluidSet.contains(fluid) != Config.fluidListIsBlackList;
	}
}