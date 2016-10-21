package mod.nethertweaks.compatibility;

import mod.sfhcore.Constants;
import mod.nethertweaks.blocks.NTMBlocks;
import mod.nethertweaks.items.NTMItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.oredict.ShapedOreRecipe;

@Mod(modid="NTMCompatibility", name="NTMCompatibility", version=Constants.ModIdNTM, dependencies=Constants.ModIdNTM)
public class NTMCompatibility {
	
	@Mod.Instance
	public NTMCompatibility instance;
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent evt) {
		MinefactoryReloaded.loadCompatibility();
		ThermalExpansion.loadCompatibility();
		Chaust.loadCompatibility();
		Ores.registerOres();
        Ores.registerNames();
        Ores.registerRecipes();
	}
}
