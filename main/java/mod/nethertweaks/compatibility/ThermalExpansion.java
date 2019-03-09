package mod.nethertweaks.compatibility;

import java.io.PrintStream;
import java.lang.reflect.Method;

import mod.nethertweaks.Config;
import mod.nethertweaks.handler.BlockHandler;
import mod.nethertweaks.handler.ItemHandler;
import mod.nethertweaks.handler.RecipeHandler;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.OreDictionary.OreRegisterEvent;

public class ThermalExpansion {
	
	 public static void loadCompatibility()
	 {
		 /*
		 //Pulverizer
	     addPulverizerRecipe(800, new ItemStack(Blocks.dirt), new ItemStack(NTMItems.itemPileDirt, 4), null, 10, true); 
	     addPulverizerRecipe(3200, new ItemStack(NTMBlocks.oreHellfayah), new ItemStack(NTMItems.itemHellfayah, 2), new ItemStack(Blocks.netherrack), 5, true); 
	     if(Config.disableMaceDust == false)
	     addPulverizerRecipe(2400, new ItemStack(Blocks.sand), new ItemStack(NTMBlocks.blockDust, 1), null, 10, true);
	     addPulverizerRecipe(3200, new ItemStack(Blocks.obsidian), new ItemStack(NTMItems.itemObsidianDust, 4), null, 10, true);  

	     //Ores
	     addPulverizerRecipe(3200, new ItemStack(Ores.oreSiliconGravel), new ItemStack(Ores.rawSilicon, 2), new ItemStack(Blocks.gravel), 10, true); 
	     addPulverizerRecipe(3200, new ItemStack(Ores.oreSiliconSand), new ItemStack(Ores.rawSilicon, 2), new ItemStack(Blocks.sand), 10, true);
	     addPulverizerRecipe(3200, new ItemStack(Ores.oreSiliconDust), new ItemStack(Ores.rawSilicon, 2), new ItemStack(NTMBlocks.blockDust), 10, true);
	     addPulverizerRecipe(3200, new ItemStack(Ores.oreCertusQuartzGravel), new ItemStack(Ores.crystalCertusQuartz, 2), new ItemStack(Blocks.gravel), 10, true); 
	     addPulverizerRecipe(3200, new ItemStack(Ores.oreCertusQuartzSand), new ItemStack(Ores.crystalCertusQuartz, 2), new ItemStack(Blocks.sand), 10, true);
	     addPulverizerRecipe(3200, new ItemStack(Ores.oreCertusQuartzDust), new ItemStack(Ores.crystalCertusQuartz, 2), new ItemStack(NTMBlocks.blockDust), 10, true);
	     
	     //Induction-Smelter
	     addAlloyRecipe(8000, "dustObsidian", 4, "blockSand", 1, (new ItemStack(NTMBlocks.blockRefinedGlass)));
	     addAlloyRecipe(8000, "dustCharcoal", 4, "dustIron", 1, (new ItemStack(NTMItems.itemIngotSteel)));
	     addAlloyRecipe(8000, "dustCharcoal", 4, "ingotIron", 1, (new ItemStack(NTMItems.itemIngotSteel)));
	     addAlloyRecipe(4000, "itemHellfayah", 1, "dustIron", 1, (new ItemStack(NTMItems.itemIngotSteel)));
	     addAlloyRecipe(4000, "itemHellfayah", 1, "ingotIron", 1, (new ItemStack(NTMItems.itemIngotSteel)));
	     */
	 }
	 
	 public static void addPulverizerRecipe(int energy, ItemStack input, ItemStack primaryOutput, ItemStack secondaryOutput, int secondaryChance, boolean overwrite)
	  {
	    Class pulverizer = null;
	    Method addRecipe = null;
	    Object[] parameters = { Integer.valueOf(energy), input, primaryOutput, secondaryOutput, Integer.valueOf(secondaryChance), Boolean.valueOf(overwrite) };
	    try
	    {
	      pulverizer = Class.forName("cofh.thermalexpansion.util.crafting.PulverizerManager");
	      if (pulverizer != null)
	      {
	        addRecipe = pulverizer.getDeclaredMethod("addRecipe", new Class[] { Integer.TYPE, ItemStack.class, ItemStack.class, ItemStack.class, ItemStack.class, Integer.TYPE, Boolean.TYPE });
	        addRecipe.setAccessible(true);
	      }
	      if (addRecipe != null) {
	        addRecipe.invoke(null, parameters);
	      }
	    }
	    catch (Exception ex)
	    {
	      System.out.println("Failed!");
	    }
	  }
	 
	 public static void addAlloyRecipe(int paramInt1, String paramString1, int paramInt2, String paramString2, int paramInt3, ItemStack paramItemStack)
	  {
	    Class smelter = null;
	    Method addRecipe = null;
	    Object[] parameters = { Integer.valueOf(paramInt1), paramInt1, Integer.valueOf(paramInt2), paramString2, Integer.valueOf(paramInt3), paramItemStack};
	    try
	    {
	      smelter = Class.forName("cofh.thermalexpansion.util.crafting.SmelterManager");
	      if (smelter != null)
	      {
	        addRecipe = smelter.getDeclaredMethod("addRecipe", new Class[] { Integer.TYPE, ItemStack.class, ItemStack.class, ItemStack.class, ItemStack.class, Integer.TYPE, Boolean.TYPE });
	        addRecipe.setAccessible(true);
	      }
	      if (addRecipe != null) {
	        addRecipe.invoke(null, parameters);
	      }
	    }
	    catch (Exception ex)
	    {
	      System.out.println("Failed!");
	    }
	  }
}

