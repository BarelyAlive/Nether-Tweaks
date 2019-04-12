package mod.nethertweaks.registry;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import mod.nethertweaks.json.CustomItemInfoJson;
import mod.nethertweaks.registry.manager.IFluidOnTopDefaultRegistryProvider;
import mod.nethertweaks.registry.manager.NTMRegistryManager;
import mod.nethertweaks.registry.types.FluidFluidBlock;
import mod.sfhcore.util.ItemInfo;
import net.minecraft.init.Blocks;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class FluidOnTopRegistry
{
    private static ArrayList<FluidFluidBlock> registry = new ArrayList<>();
    private static List<FluidFluidBlock> externalRegistry = new ArrayList<>();

    public static ArrayList<FluidFluidBlock> getRegistry() {
		return registry;
	}

	public static List<FluidFluidBlock> getExternalRegistry() {
		return externalRegistry;
	}

	public static void register(Fluid fluidInBarrel, Fluid fluidOnTop, ItemInfo result)
    {
        registerInternal(fluidInBarrel, fluidOnTop, result);
        externalRegistry.add(new FluidFluidBlock(fluidInBarrel.getName(), fluidOnTop.getName(), result));
    }
    
    private static void registerInternal(Fluid fluidInBarrel, Fluid fluidOnTop, ItemInfo result)
    {
        registry.add(new FluidFluidBlock(fluidInBarrel.getName(), fluidOnTop.getName(), result));
    }
	
	public static boolean isValidRecipe(Fluid fluidInBarrel, Fluid fluidOnTop) {
		if (fluidInBarrel == null || fluidOnTop == null)
			return false;
		for (FluidFluidBlock fBlock : registry) {
			if (fBlock.getFluidInBarrel().equals(fluidInBarrel.getName()) &&
					fBlock.getFluidOnTop().equals(fluidOnTop.getName()))
				return true;
		}
		
		return false;
	}
	
	public static ItemInfo getTransformedBlock(Fluid fluidInBarrel, Fluid fluidOnTop) {
		for (FluidFluidBlock fBlock : registry) {
			if (fBlock.getFluidInBarrel().equals(fluidInBarrel.getName()) &&
					fBlock.getFluidOnTop().equals(fluidOnTop.getName()))
				return fBlock.getResult();
		}
		
		return null;
	}
    
    private static Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(ItemInfo.class, new CustomItemInfoJson()).create();
    
    public static void loadJson(File file)
	{
	    registry.clear();
	    
		if (file.exists())
		{
			try 
			{
				FileReader fr = new FileReader(file);
				List<FluidFluidBlock> gsonInput = gson.fromJson(fr, new TypeToken<List<FluidFluidBlock>>(){}.getType());
				
				registry.addAll(gsonInput);
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		else
		{
			registerDefaults();
			saveJson(file);
		}
		
		registry.addAll(externalRegistry);
	}
	
	public static void saveJson(File file)
	{
		try
		{
			FileWriter fw = new FileWriter(file);
			gson.toJson(registry, fw);
			
			fw.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public static void registerDefaults()
	{
		for (IFluidOnTopDefaultRegistryProvider provider : NTMRegistryManager.getDefaultFluidOnTopRecipeHandlers()) {
			provider.registerFluidOnTopRecipeDefaults();
		}
		
	}
}
