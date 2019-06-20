package mod.nethertweaks.modules;

import java.util.HashSet;
import java.util.Set;

import mod.nethertweaks.config.Config;
import mod.sfhcore.modules.ISFHCoreModule;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

public class MooFluidsEtc implements ISFHCoreModule {
	
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
    public void postInitServer(FMLPostInitializationEvent event){
        for(String s : Config.fluidList){
            if(FluidRegistry.isFluidRegistered(s))
                fluidSet.add(FluidRegistry.getFluid(s));
        }
    }

    public static boolean fluidIsAllowed(Fluid fluid){
        return fluidSet.contains(fluid) != Config.fluidListIsBlackList;
    }
}