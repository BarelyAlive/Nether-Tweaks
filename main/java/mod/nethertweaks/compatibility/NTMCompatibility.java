package mod.nethertweaks.compatibility;

import mod.sfhcore.Constants;
import mod.nethertweaks.handler.BlockHandler;
import mod.nethertweaks.handler.ItemHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.oredict.ShapedOreRecipe;

@Mod(modid="ntmcompatibility", name="NTM Compatibility", version=Constants.NTMVersion, dependencies=Constants.MODCORE)
public class NTMCompatibility {
	
	@Mod.Instance
	public NTMCompatibility instance;
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent evt) {
		MinefactoryReloaded.loadCompatibility();
		ThermalExpansion.loadCompatibility();
		Ores.registerOres();
        Ores.registerNames();
        Ores.registerRecipes();
	}
}
