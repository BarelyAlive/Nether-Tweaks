package mod.nethertweaks.init;

import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.registry.manager.NTMDefaultRecipes;
import mod.nethertweaks.registry.manager.NTMRegistryManager;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

public class OreRegistrationHandler
{
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void registerOres (final RegistryEvent.Register<Item> event)
	{
		NTMRegistryManager.DYN_ORE_REGISTRY.loadJson(NetherTweaksMod.configDirectory);
		NTMRegistryManager.registerDynOreDefaultRecipeHandler(new NTMDefaultRecipes.DynOreDefaults());
		String[] ore_names = OreDictionary.getOreNames();
		for (String ore_name : ore_names)
			if (ore_name.startsWith("ore"))
			{
				String ore_raw_name = ore_name.substring(3);
				if(!OreDictionary.doesOreNameExist("ingot" + ore_raw_name))
					continue;
				if (OreDictionary.getOres(ore_name).size() == 0)
					continue;
				if(OreDictionary.getOres(ore_name).get(0).getDisplayName().toLowerCase().equals("air"))
					continue;
            	OreHandler.add(OreDictionary.getOres(ore_name).get(0).getItem(), 1);
			}

		// Reading JSON out
		// Processing JSON and Disable CHunks here
		// Add Chunks if not enabled and Disabled
		// Rewrite of new JSON possible?


		OreHandler.register(event.getRegistry());
	}

    @SubscribeEvent(priority = EventPriority.LOWEST)
	@SideOnly(Side.CLIENT)
    public void registerItemHandlers(final ColorHandlerEvent.Item event)
    {
    	OreHandler.registerItemHandlers(event);
    }

	@SubscribeEvent(priority = EventPriority.LOWEST)
	@SideOnly(Side.CLIENT)
	public void registerOreModels(final ModelRegistryEvent event)
	{
		OreHandler.registerModels(event);
	}
}