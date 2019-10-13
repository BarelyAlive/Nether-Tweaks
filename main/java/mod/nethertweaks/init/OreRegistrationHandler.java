package mod.nethertweaks.init;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import mod.nethertweaks.CreativeTabNTM;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.capabilities.NTMCapabilities;
import mod.nethertweaks.compatibility.Compatibility;
import mod.nethertweaks.config.Config;
import mod.nethertweaks.entities.NTMEntities;
import mod.nethertweaks.registries.manager.NTMDefaultRecipes;
import mod.nethertweaks.registries.manager.NTMRegistryManager;
import mod.nethertweaks.registries.registries.BarrelModeRegistry;
import mod.nethertweaks.world.WorldGeneratorNTM;
import mod.sfhcore.modules.ISFHCoreModule;
import mod.sfhcore.util.LogUtil;
import net.minecraft.item.Item;
import net.minecraft.world.WorldType;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

@Mod.EventBusSubscriber
public class OreRegistrationHandler
{
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void registerOres (RegistryEvent.Register<Item> event)
	{
		NTMRegistryManager.DYN_ORE_REGISTRY.loadJson(NetherTweaksMod.configDirectory);
		NTMRegistryManager.registerDynOreDefaultRecipeHandler(new NTMDefaultRecipes.DynOreDefaults());
		String[] ore_names = OreDictionary.getOreNames();
		for (String ore_name : ore_names)
		{
			if (ore_name.startsWith("ore"))
			{
				String ore_raw_name = ore_name.substring(3);
				if(!OreDictionary.doesOreNameExist("ingot" + ore_raw_name))
				{
					continue;
				}
				if (OreDictionary.getOres(ore_name).size() == 0)
				{
					continue;
				}
				if(OreDictionary.getOres(ore_name).get(0).getDisplayName().toLowerCase().equals("air"))
				{
					continue;
				}
            	OreHandler.add(OreDictionary.getOres(ore_name).get(0).getItem(), 1);
			}
		}
		
		// Reading JSON out
		// Processing JSON and Disable CHunks here
		// Add Chunks if not enabled and Disabled
		// Rewrite of new JSON possible?
		
		
		OreHandler.register(event.getRegistry());
	}
	
    @SubscribeEvent(priority = EventPriority.LOWEST)
	@SideOnly(Side.CLIENT)
    public static void registerItemHandlers(ColorHandlerEvent.Item event)
    {
    	OreHandler.registerItemHandlers(event);
    }
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	@SideOnly(Side.CLIENT)
	public static void registerOreModels(ModelRegistryEvent event)
	{
		OreHandler.registerModels(event);
	}
}