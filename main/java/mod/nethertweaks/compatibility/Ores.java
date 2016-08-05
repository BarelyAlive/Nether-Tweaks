package mod.nethertweaks.compatibility;

import mod.nethertweaks.Config;
import mod.nethertweaks.Constants;
import mod.nethertweaks.INames;
import mod.nethertweaks.blocks.NTMBlocks;
import mod.nethertweaks.blocks.OreDust;
import mod.nethertweaks.blocks.OreGravel;
import mod.nethertweaks.blocks.OreSand;
import mod.nethertweaks.handler.NTMSieveHandler;
import mod.nethertweaks.items.ItemOreMaterial;
import mod.nethertweaks.items.NTMItems;
import mod.sfhcore.Registry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.OreDictionary.OreRegisterEvent;

public class Ores implements INames{
	
    public static Block oreSaltpeterGravel;
    public static Block oreSaltpeterSand;
    public static Block oreSaltpeterDust;
    public static Item rawSilicon = Item.REGISTRY.getObject(new ResourceLocation("GalacticraftCore", "basicItem.rawSilicon"));
    public static Item crystalCertusQuartz = Item.REGISTRY.getObject(new ResourceLocation("appliedenergistics2", "crystalCertusQuartz"));
    public static Item blizzpowder = Item.REGISTRY.getObject(new ResourceLocation("ThermalFoundation", "material"));
    
    public static Block oreGravel;
    public static Block oreSand;
    public static Block oreDust;
    
    public static Item oreMaterial;
    
	public static void registerOres(){
		
		oreGravel = Registry.registerBlock(new OreGravel(Material.GROUND), OREGRAVEL, Constants.MOD);
		oreSand = Registry.registerBlock(new OreSand(Material.SAND), ORESAND, Constants.MOD);
		oreDust = Registry.registerBlock(new OreDust(Material.SAND), OREDUST, Constants.MOD);
		
		oreMaterial = Registry.registerItem(new ItemOreMaterial("OreMaterial"), "OreMaterial", Constants.MOD);
		
		//Nicht Erze
		if(blizzpowder != null) NTMSieveHandler.register(Blocks.SNOW, blizzpowder, 1025, 20);
		
	}
	
	public static void registerRecipes(){
		//ore blocks
		
		if(OreDictionary.doesOreNameExist("oreCopper") && Config.oreCopper){
			NTMSieveHandler.register(NTMBlocks.blockDust, 0, oreMaterial, 34, 20);
			NTMSieveHandler.register(Blocks.SAND, 0, oreMaterial, 17, 20);
			NTMSieveHandler.register(Blocks.GRAVEL, 0, oreMaterial, 0, 20);
			
			GameRegistry.addShapedRecipe(new ItemStack(oreGravel, 1, 0), "XX", "XX", 'X', new ItemStack(oreMaterial, 1, 0));
			GameRegistry.addShapedRecipe(new ItemStack(oreSand, 1, 0), "XX", "XX", 'X', new ItemStack(oreMaterial, 1, 17));
			GameRegistry.addShapedRecipe(new ItemStack(oreDust, 1, 0), "XX", "XX", 'X', new ItemStack(oreMaterial, 1, 34));
			
			GameRegistry.addSmelting(new ItemStack(oreGravel, 1, 0), OreDictionary.getOres("ingotCopper").get(0), 2.0f);
            GameRegistry.addSmelting(new ItemStack(oreSand, 1, 0), OreDictionary.getOres("ingotCopper").get(0), 2.0f);
            GameRegistry.addSmelting(new ItemStack(oreDust, 1, 0), OreDictionary.getOres("ingotCopper").get(0), 2.0f);
            
		}
		if(OreDictionary.doesOreNameExist("oreTin") && Config.oreTin){
			NTMSieveHandler.register(NTMBlocks.blockDust, 0, oreMaterial, 35, 20);
			NTMSieveHandler.register(Blocks.SAND, 0, oreMaterial, 18, 20);
			NTMSieveHandler.register(Blocks.GRAVEL, 0, oreMaterial, 1, 20);
			
			GameRegistry.addShapedRecipe(new ItemStack(oreGravel, 1, 1), "XX", "XX", 'X', new ItemStack(oreMaterial, 1, 1));
			GameRegistry.addShapedRecipe(new ItemStack(oreSand, 1, 1), "XX", "XX", 'X', new ItemStack(oreMaterial, 1, 18));
			GameRegistry.addShapedRecipe(new ItemStack(oreDust, 1, 1), "XX", "XX", 'X', new ItemStack(oreMaterial, 1, 35));
			
            GameRegistry.addSmelting(new ItemStack(oreGravel, 1, 1), OreDictionary.getOres("ingotTin").get(0), 2.0f);
            GameRegistry.addSmelting(new ItemStack(oreSand, 1, 1), OreDictionary.getOres("ingotTin").get(0), 2.0f);
            GameRegistry.addSmelting(new ItemStack(oreDust, 1, 1), OreDictionary.getOres("ingotTin").get(0), 2.0f);
            
		}
		if(OreDictionary.doesOreNameExist("oreLead") && Config.oreLead){
			NTMSieveHandler.register(NTMBlocks.blockDust, 0, oreMaterial, 36, 20);
			NTMSieveHandler.register(Blocks.SAND, 0, oreMaterial, 19, 20);
			NTMSieveHandler.register(Blocks.GRAVEL, 0, oreMaterial, 2, 20);
			
			GameRegistry.addShapedRecipe(new ItemStack(oreGravel, 1, 2), "XX", "XX", 'X', new ItemStack(oreMaterial, 1, 2));
			GameRegistry.addShapedRecipe(new ItemStack(oreSand, 1, 2), "XX", "XX", 'X', new ItemStack(oreMaterial, 1, 19));
			GameRegistry.addShapedRecipe(new ItemStack(oreDust, 1, 2), "XX", "XX", 'X', new ItemStack(oreMaterial, 1, 36));

            GameRegistry.addSmelting(new ItemStack(oreGravel, 1, 2), OreDictionary.getOres("ingotLead").get(0), 2.0f);
            GameRegistry.addSmelting(new ItemStack(oreSand, 1, 2), OreDictionary.getOres("ingotLead").get(0), 2.0f);
            GameRegistry.addSmelting(new ItemStack(oreDust, 1, 2), OreDictionary.getOres("ingotLead").get(0), 2.0f);
            
		}
		if(OreDictionary.doesOreNameExist("oreNickel") && Config.oreNickel){
			NTMSieveHandler.register(NTMBlocks.blockDust, 0, oreMaterial, 37, 20);
			NTMSieveHandler.register(Blocks.SAND, 0, oreMaterial, 20, 20);
			NTMSieveHandler.register(Blocks.GRAVEL, 0, oreMaterial, 3, 20);
			
			GameRegistry.addShapedRecipe(new ItemStack(oreGravel , 1, 3), "XX", "XX", 'X', new ItemStack(oreMaterial, 1, 3));
			GameRegistry.addShapedRecipe(new ItemStack(oreSand, 1, 3), "XX", "XX", 'X', new ItemStack(oreMaterial, 1, 20));
			GameRegistry.addShapedRecipe(new ItemStack(oreDust, 1, 3), "XX", "XX", 'X', new ItemStack(oreMaterial, 1, 37));

            GameRegistry.addSmelting(new ItemStack(oreGravel , 1, 3), OreDictionary.getOres("ingotNickel").get(0), 2.0f);
            GameRegistry.addSmelting(new ItemStack(oreSand, 1, 3), OreDictionary.getOres("ingotNickel").get(0), 2.0f);
            GameRegistry.addSmelting(new ItemStack(oreDust, 1, 3), OreDictionary.getOres("ingotNickel").get(0), 2.0f);
		}
		if(OreDictionary.doesOreNameExist("orePlatinum") && Config.orePlatinum){
			NTMSieveHandler.register(NTMBlocks.blockDust, 0, oreMaterial, 38, 20);
			NTMSieveHandler.register(Blocks.SAND, 0, oreMaterial, 21, 20);
			NTMSieveHandler.register(Blocks.GRAVEL, 0, oreMaterial, 4, 20);
			
			GameRegistry.addShapedRecipe(new ItemStack(oreGravel, 1, 4), "XX", "XX", 'X', new ItemStack(oreMaterial, 1, 4));
			GameRegistry.addShapedRecipe(new ItemStack(oreSand, 1, 4), "XX", "XX", 'X', new ItemStack(oreMaterial, 1, 21));
			GameRegistry.addShapedRecipe(new ItemStack(oreDust, 1, 4), "XX", "XX", 'X', new ItemStack(oreMaterial, 1, 38));

            GameRegistry.addSmelting(new ItemStack(oreGravel, 1, 4), OreDictionary.getOres("ingotPlatinum").get(0), 2.0f);
            GameRegistry.addSmelting(new ItemStack(oreSand, 1, 4), OreDictionary.getOres("ingotPlatinum").get(0), 2.0f);
            GameRegistry.addSmelting(new ItemStack(oreDust, 1, 4), OreDictionary.getOres("ingotPlatinum").get(0), 2.0f);
            
            
		}
		if(OreDictionary.doesOreNameExist("oreSilver") && Config.oreSilver){
			NTMSieveHandler.register(NTMBlocks.blockDust, 0, oreMaterial, 39, 20);
			NTMSieveHandler.register(Blocks.SAND, 0, oreMaterial, 22, 20);
			NTMSieveHandler.register(Blocks.GRAVEL, 0, oreMaterial, 5, 20);
			
			GameRegistry.addShapedRecipe(new ItemStack(oreGravel, 1, 5), "XX", "XX", 'X', new ItemStack(oreMaterial, 1, 5));
			GameRegistry.addShapedRecipe(new ItemStack(oreSand, 1, 5), "XX", "XX", 'X', new ItemStack(oreMaterial, 1, 22));
			GameRegistry.addShapedRecipe(new ItemStack(oreDust, 1, 5), "XX", "XX", 'X', new ItemStack(oreMaterial, 1, 39));

			GameRegistry.addSmelting(new ItemStack(oreGravel, 1, 5), OreDictionary.getOres("ingotSilver").get(0), 2.0f);
            GameRegistry.addSmelting(new ItemStack(oreSand, 1, 5), OreDictionary.getOres("ingotSilver").get(0), 2.0f);
            GameRegistry.addSmelting(new ItemStack(oreDust, 1, 5), OreDictionary.getOres("ingotSilver").get(0), 2.0f);
            
            
		}
		if(OreDictionary.doesOreNameExist("oreUranium") && Config.oreUranium){
			NTMSieveHandler.register(NTMBlocks.blockDust, 0, oreMaterial, 40, 20);
			NTMSieveHandler.register(Blocks.SAND, 0, oreMaterial, 23, 20);
			NTMSieveHandler.register(Blocks.GRAVEL, 0, oreMaterial, 6, 20);

			GameRegistry.addShapedRecipe(new ItemStack(oreDust,1 , 6), "XX", "XX", 'X', new ItemStack(oreMaterial, 1, 40));
			GameRegistry.addShapedRecipe(new ItemStack(oreGravel, 1, 6), "XX", "XX", 'X', new ItemStack(oreMaterial, 1, 6));
			GameRegistry.addShapedRecipe(new ItemStack(oreSand, 1, 6), "XX", "XX", 'X', new ItemStack(oreMaterial, 1, 23));
			
	        GameRegistry.addSmelting(new ItemStack(oreGravel, 1, 7), OreDictionary.getOres("ingotUranium").get(0), 2.0f);
	        GameRegistry.addSmelting(new ItemStack(oreSand, 1, 7), OreDictionary.getOres("ingotUranium").get(0), 2.0f);
	        GameRegistry.addSmelting(new ItemStack(oreDust, 1, 7), OreDictionary.getOres("ingotUranium").get(0), 2.0f);
	        
		}
		if(OreDictionary.doesOreNameExist("oreOsmium") && Config.oreOsmium){
			NTMSieveHandler.register(NTMBlocks.blockDust, 0, oreMaterial, 41, 20);
			NTMSieveHandler.register(Blocks.SAND, 0, oreMaterial, 24, 20);
			NTMSieveHandler.register(Blocks.GRAVEL, 0, oreMaterial, 7, 20);

			GameRegistry.addShapedRecipe(new ItemStack(oreDust, 1, 7), "XX", "XX", 'X', new ItemStack(oreMaterial, 1, 41));
			GameRegistry.addShapedRecipe(new ItemStack(oreGravel, 1, 7), "XX", "XX", 'X', new ItemStack(oreMaterial, 1, 7));
			GameRegistry.addShapedRecipe(new ItemStack(oreSand, 1, 7), "XX", "XX", 'X', new ItemStack(oreMaterial, 1, 24));
	        
		}
		if(OreDictionary.doesOreNameExist("oreDraconium") && Config.oreDraconium){
			NTMSieveHandler.register(NTMBlocks.blockDust, 0, oreMaterial, 42, 20);
			NTMSieveHandler.register(Blocks.SAND, 0, oreMaterial, 25, 20);
			NTMSieveHandler.register(Blocks.GRAVEL, 0, oreMaterial, 8, 20);

			//Draconic Evo
			GameRegistry.addShapedRecipe(new ItemStack(oreDust, 1, 8), "XX", "XX", 'X', new ItemStack(oreMaterial, 1, 42));
			GameRegistry.addShapedRecipe(new ItemStack(oreGravel, 1, 8), "XX", "XX", 'X', new ItemStack(oreMaterial, 1, 8));
			GameRegistry.addShapedRecipe(new ItemStack(oreSand, 1, 8), "XX", "XX", 'X', new ItemStack(oreMaterial, 1, 26));
			
            GameRegistry.addSmelting(new ItemStack(oreGravel, 1, 8), OreDictionary.getOres("ingotDraconium").get(0), 2.0f);
            GameRegistry.addSmelting(new ItemStack(oreSand, 1, 8), OreDictionary.getOres("ingotDraconium").get(0), 2.0f);
            GameRegistry.addSmelting(new ItemStack(oreDust, 1, 8), OreDictionary.getOres("ingotDraconium").get(0), 2.0f);
            
			
		}
		if(OreDictionary.doesOreNameExist("oreSulfur") && Config.oreSulfur){
			NTMSieveHandler.register(NTMBlocks.blockDust, 0, oreMaterial, 43, 20);
			NTMSieveHandler.register(Blocks.SAND, 0, oreMaterial, 26, 20);
			NTMSieveHandler.register(Blocks.GRAVEL, 0, oreMaterial, 9, 20);
			
			//Sulfur
			GameRegistry.addShapedRecipe(new ItemStack(oreDust, 1, 9), "XX", "XX", 'X', new ItemStack(oreMaterial, 1, 43));
			GameRegistry.addShapedRecipe(new ItemStack(oreGravel, 1, 9), "XX", "XX", 'X', new ItemStack(oreMaterial, 1, 9));
			GameRegistry.addShapedRecipe(new ItemStack(oreSand, 1, 9), "XX", "XX", 'X', new ItemStack(oreMaterial, 1, 26));

            GameRegistry.addSmelting(new ItemStack(oreGravel, 1, 9), OreDictionary.getOres("dustSulfur").get(0), 2.0f);
            GameRegistry.addSmelting(new ItemStack(oreSand, 1, 9), OreDictionary.getOres("dustSulfur").get(0), 2.0f);
            GameRegistry.addSmelting(new ItemStack(oreDust, 1, 9), OreDictionary.getOres("dustSulfur").get(0), 2.0f);
            
            
		}
		if(OreDictionary.doesOreNameExist("oreAluminum") && Config.oreAluminum){
			NTMSieveHandler.register(NTMBlocks.blockDust, 0, oreMaterial, 44, 20);
			NTMSieveHandler.register(Blocks.SAND, 0, oreMaterial, 27, 20);
			NTMSieveHandler.register(Blocks.GRAVEL, 0, oreMaterial, 10, 20);
			
			//Aluminum
			GameRegistry.addShapedRecipe(new ItemStack(oreDust, 1, 10), "XX", "XX", 'X', new ItemStack(oreMaterial, 1, 44));
			GameRegistry.addShapedRecipe(new ItemStack(oreGravel, 1, 10), "XX", "XX", 'X', new ItemStack(oreMaterial, 1, 10));
			GameRegistry.addShapedRecipe(new ItemStack(oreSand, 1, 10), "XX", "XX", 'X', new ItemStack(oreMaterial, 1, 27));
			
            GameRegistry.addSmelting(new ItemStack(oreGravel, 1, 10), OreDictionary.getOres("ingotAluminum").get(0), 2.0f);
            GameRegistry.addSmelting(new ItemStack(oreSand, 1, 10), OreDictionary.getOres("ingotAluminum").get(0), 2.0f);
            GameRegistry.addSmelting(new ItemStack(oreDust, 1, 10), OreDictionary.getOres("ingotAluminum").get(0), 2.0f);
            
		}
		if(OreDictionary.doesOreNameExist("oreSilicon") && Config.oreSilicon){
			NTMSieveHandler.register(NTMBlocks.blockDust, 0, oreMaterial, 45, 20);
			NTMSieveHandler.register(Blocks.SAND, 0, oreMaterial, 28, 20);
			NTMSieveHandler.register(Blocks.GRAVEL, 0, oreMaterial, 11, 20);
			
			GameRegistry.addShapedRecipe(new ItemStack(oreDust, 1, 11), "XX", "XX", 'X', new ItemStack(oreMaterial, 1, 45));
			GameRegistry.addShapedRecipe(new ItemStack(oreGravel, 1, 11), "XX", "XX", 'X', new ItemStack(oreMaterial, 1, 11));
			GameRegistry.addShapedRecipe(new ItemStack(oreSand, 1, 11), "XX", "XX", 'X', new ItemStack(oreMaterial, 1, 28));
			
            GameRegistry.addSmelting(new ItemStack(oreGravel, 1, 11), OreDictionary.getOres("itemSilicon").get(0), 2.0f);
            GameRegistry.addSmelting(new ItemStack(oreSand, 1, 11), OreDictionary.getOres("itemSilicon").get(0), 2.0f);
            GameRegistry.addSmelting(new ItemStack(oreDust, 1, 11), OreDictionary.getOres("itemSilicon").get(0), 2.0f);
            
		}
		if(OreDictionary.doesOreNameExist("oreAmber") && Config.oreAmber){
			NTMSieveHandler.register(NTMBlocks.blockDust, 0, oreMaterial, 46, 20);
			NTMSieveHandler.register(Blocks.SAND, 0, oreMaterial, 29, 20);
			NTMSieveHandler.register(Blocks.GRAVEL, 0, oreMaterial, 12, 20);
			
			//Amber
			GameRegistry.addShapedRecipe(new ItemStack(oreDust, 1, 12), "XX", "XX", 'X', new ItemStack(oreMaterial, 1, 46));
			GameRegistry.addShapedRecipe(new ItemStack(oreGravel, 1, 12), "XX", "XX", 'X', new ItemStack(oreMaterial, 1, 12));
			GameRegistry.addShapedRecipe(new ItemStack(oreSand, 1, 12), "XX", "XX", 'X', new ItemStack(oreMaterial, 1, 29));

            GameRegistry.addSmelting(new ItemStack(oreGravel, 1, 12), OreDictionary.getOres("gemAmber").get(0), 2.0f);
            GameRegistry.addSmelting(new ItemStack(oreSand, 1, 12), OreDictionary.getOres("gemAmber").get(0), 2.0f);
            GameRegistry.addSmelting(new ItemStack(oreDust, 1, 12), OreDictionary.getOres("gemAmber").get(0), 2.0f);
            
		}
		if(OreDictionary.doesOreNameExist("oreCinnabar") && Config.oreCinnabar){
			NTMSieveHandler.register(NTMBlocks.blockDust, 0, oreMaterial, 47, 20);
			NTMSieveHandler.register(Blocks.SAND, 0, oreMaterial, 30, 20);
			NTMSieveHandler.register(Blocks.GRAVEL, 0, oreMaterial, 13, 20);
			
			//Cinnabar
			GameRegistry.addShapedRecipe(new ItemStack(oreDust, 1, 13), "XX", "XX", 'X', new ItemStack(oreMaterial, 1, 47));
			GameRegistry.addShapedRecipe(new ItemStack(oreGravel, 1, 13), "XX", "XX", 'X', new ItemStack(oreMaterial, 1, 13));
			GameRegistry.addShapedRecipe(new ItemStack(oreSand, 1, 13), "XX", "XX", 'X', new ItemStack(oreMaterial, 1, 30));

            GameRegistry.addSmelting(new ItemStack(oreGravel, 1, 13), OreDictionary.getOres("crystalCinnabar").get(0), 2.0f);
            GameRegistry.addSmelting(new ItemStack(oreSand, 1, 13), OreDictionary.getOres("crystalCinnabar").get(0), 2.0f);
            GameRegistry.addSmelting(new ItemStack(oreDust, 1, 13), OreDictionary.getOres("crystalCinnabar").get(0), 2.0f);
            
            
		}
		if(OreDictionary.doesOreNameExist("oreCertusQuartz") && Config.oreCertusQuartz){
			NTMSieveHandler.register(NTMBlocks.blockDust, 0, oreMaterial, 48, 20);
			NTMSieveHandler.register(Blocks.SAND, 0, oreMaterial, 31, 20);
			NTMSieveHandler.register(Blocks.GRAVEL, 0, oreMaterial, 14, 20);
			
			//AppEng2
			GameRegistry.addShapedRecipe(new ItemStack(oreDust, 1, 14), "XX", "XX", 'X', new ItemStack(oreMaterial, 1, 48));
			GameRegistry.addShapedRecipe(new ItemStack(oreGravel, 1, 14), "XX", "XX", 'X', new ItemStack(oreMaterial, 1, 14));
			GameRegistry.addShapedRecipe(new ItemStack(oreSand, 1, 14), "XX", "XX", 'X', new ItemStack(oreMaterial, 1, 31));

            GameRegistry.addSmelting(new ItemStack(oreGravel, 1, 14), OreDictionary.getOres("crystalCertusQuartz").get(0), 2.0f);
            GameRegistry.addSmelting(new ItemStack(oreSand, 1, 14), OreDictionary.getOres("crystalCertusQuartz").get(0), 2.0f);
            GameRegistry.addSmelting(new ItemStack(oreDust, 1, 14), OreDictionary.getOres("crystalCertusQuartz").get(0), 2.0f);
                    
		}
		if(Config.oreSalt){
			//Salt
			NTMSieveHandler.register(NTMBlocks.blockDust, 0, oreMaterial, 49, 20);
			NTMSieveHandler.register(Blocks.SAND, 0, oreMaterial, 32, 20);
			NTMSieveHandler.register(Blocks.GRAVEL, 0, oreMaterial, 15, 20);
			
			GameRegistry.addShapedRecipe(new ItemStack(oreDust, 1, 15), "XX", "XX", 'X', new ItemStack(oreMaterial, 1, 49));
			GameRegistry.addShapedRecipe(new ItemStack(oreGravel, 1, 15), "XX", "XX", 'X', new ItemStack(oreMaterial, 1, 15));
			GameRegistry.addShapedRecipe(new ItemStack(oreSand, 1, 15), "XX", "XX", 'X', new ItemStack(oreMaterial, 1, 32));
			
			/*
			GameRegistry.addSmelting(new ItemStack(oreGravel, 1, 15), new ItemStack(NTMItems.itemBase, 1, 16), 2.0f);
	        GameRegistry.addSmelting(new ItemStack(oreSand, 1, 15), new ItemStack(NTMItems.itemBase, 1, 16), 2.0f);
	        GameRegistry.addSmelting(new ItemStack(oreDust, 1, 15), new ItemStack(NTMItems.itemBase, 1, 16), 2.0f);
	        */
		}
		
		if(OreDictionary.doesOreNameExist("oreSaltpeter")){
			NTMSieveHandler.register(NTMBlocks.blockDust, 0, oreMaterial, 50, 20);
			NTMSieveHandler.register(Blocks.SAND, 0, oreMaterial, 33, 20);
			NTMSieveHandler.register(Blocks.GRAVEL, 0, oreMaterial, 16, 20);
			
			//Saltpeter
			GameRegistry.addShapedRecipe(new ItemStack(oreDust, 1, 16), "XX", "XX", 'X', new ItemStack(oreMaterial, 1, 50));
			GameRegistry.addShapedRecipe(new ItemStack(oreGravel, 1, 16), "XX", "XX", 'X', new ItemStack(oreMaterial, 1, 16));
			GameRegistry.addShapedRecipe(new ItemStack(oreSand, 1, 16), "XX", "XX", 'X', new ItemStack(oreMaterial, 1, 33));

            GameRegistry.addSmelting(oreSaltpeterGravel, OreDictionary.getOres("dustSaltpeter").get(0), 2.0f);
            GameRegistry.addSmelting(oreSaltpeterSand, OreDictionary.getOres("dustSaltpeter").get(0), 2.0f);
            GameRegistry.addSmelting(oreSaltpeterDust, OreDictionary.getOres("dustSaltpeter").get(0), 2.0f);
            
		}
	}
	
	public static void registerNames(){
    	
		if(OreDictionary.doesOreNameExist("oreCopper")){
	    	OreDictionary.registerOre("oreCopper", new ItemStack(oreDust, 1, 0));
	    	OreDictionary.registerOre("oreCopper", new ItemStack(oreGravel, 1, 0));
	    	OreDictionary.registerOre("oreCopper", new ItemStack(oreSand, 1, 0));
		}
		if(OreDictionary.doesOreNameExist("oreTin")){
	    	OreDictionary.registerOre("oreTin", new ItemStack(oreDust, 1, 1));
	    	OreDictionary.registerOre("oreTin", new ItemStack(oreGravel, 1, 1));
	    	OreDictionary.registerOre("oreTin", new ItemStack(oreSand, 1, 1));
    	}
    	if(OreDictionary.doesOreNameExist("oreLead")){
	    	OreDictionary.registerOre("oreLead", new ItemStack(oreDust, 1, 2));
	    	OreDictionary.registerOre("oreLead", new ItemStack(oreGravel, 1, 2));
	    	OreDictionary.registerOre("oreLead", new ItemStack(oreSand, 1, 2));
    	}
    	if(OreDictionary.doesOreNameExist("oreNickel")){
	    	OreDictionary.registerOre("oreNickel", new ItemStack(oreDust, 1, 3));
	    	OreDictionary.registerOre("oreNickel", new ItemStack(oreGravel , 1, 3));
	    	OreDictionary.registerOre("oreNickel", new ItemStack(oreSand, 1, 3));
    	}
    	if(OreDictionary.doesOreNameExist("orePlatinum")){
	    	OreDictionary.registerOre("orePlatinum", new ItemStack(oreDust, 1, 4));
	    	OreDictionary.registerOre("orePlatinum", new ItemStack(oreGravel, 1, 4));
	    	OreDictionary.registerOre("orePlatinum", new ItemStack(oreSand, 1, 4));
    	}
    	if(OreDictionary.doesOreNameExist("oreSilver")){
	    	OreDictionary.registerOre("oreSilver", new ItemStack(oreDust, 1, 5));
	    	OreDictionary.registerOre("oreSilver", new ItemStack(oreGravel, 1, 5));
	    	OreDictionary.registerOre("oreSilver", new ItemStack(oreSand, 1, 5));
    	}
    	if(OreDictionary.doesOreNameExist("oreUranium")){
	    	OreDictionary.registerOre("oreUranium", new ItemStack(oreDust, 1, 6));
	    	OreDictionary.registerOre("oreUranium", new ItemStack(oreGravel, 1, 6));
	    	OreDictionary.registerOre("oreUranium", new ItemStack(oreSand, 1, 6));
    	}
    	if(OreDictionary.doesOreNameExist("oreOsmium")){
			OreDictionary.registerOre("oreOsmium", new ItemStack(oreGravel, 1, 7));
			OreDictionary.registerOre("oreOsmium", new ItemStack(oreSand, 1, 7));
			OreDictionary.registerOre("oreOsmium", new ItemStack(oreDust, 1, 7));
    	}
		if(OreDictionary.doesOreNameExist("oreDraconium")){
			OreDictionary.registerOre("oreDraconium", new ItemStack(oreDust, 1, 8));
	    	OreDictionary.registerOre("oreDraconium", new ItemStack(oreGravel, 1, 8));
	    	OreDictionary.registerOre("oreDraconium", new ItemStack(oreSand, 1, 8));
		}
    	if(OreDictionary.doesOreNameExist("oreSulfur")){
			OreDictionary.registerOre("oreSulfur", new ItemStack(oreDust, 1, 9));
	    	OreDictionary.registerOre("oreSulfur", new ItemStack(oreGravel, 1, 9));
	    	OreDictionary.registerOre("oreSulfur", new ItemStack(oreSand, 1, 9));
    	}
    	if(OreDictionary.doesOreNameExist("oreAluminum")){
			OreDictionary.registerOre("oreAluminum", new ItemStack(oreDust, 1, 10));
	    	OreDictionary.registerOre("oreAluminum", new ItemStack(oreGravel, 1, 10));
	    	OreDictionary.registerOre("oreAluminum", new ItemStack(oreSand, 1, 10));
    	}
    	if(OreDictionary.doesOreNameExist("oreSilicon")){
			OreDictionary.registerOre("oreSilicon", new ItemStack(oreDust, 1, 11));
	    	OreDictionary.registerOre("oreSilicon", new ItemStack(oreGravel, 1, 11));
	    	OreDictionary.registerOre("oreSilicon", new ItemStack(oreSand, 1, 11));
    	}
    	if(OreDictionary.doesOreNameExist("oreAmber")){
			OreDictionary.registerOre("oreAmber", new ItemStack(oreDust, 1, 12));
	    	OreDictionary.registerOre("oreAmber", new ItemStack(oreGravel, 1, 12));
	    	OreDictionary.registerOre("oreAmber", new ItemStack(oreSand, 1, 12));
    	}
    	if(OreDictionary.doesOreNameExist("oreCinnabar")){
			OreDictionary.registerOre("oreCinnabar", new ItemStack(oreDust, 1, 13));
	    	OreDictionary.registerOre("oreCinnabar", new ItemStack(oreGravel, 1, 13));
	    	OreDictionary.registerOre("oreCinnabar", new ItemStack(oreSand, 1, 13));
    	}
    	if(OreDictionary.doesOreNameExist("oreCertusQuartz")){
			OreDictionary.registerOre("oreCertusQuartz", new ItemStack(oreDust, 1, 14));
	    	OreDictionary.registerOre("oreCertusQuartz", new ItemStack(oreGravel, 1, 14));
	    	OreDictionary.registerOre("oreCertusQuartz", new ItemStack(oreSand, 1, 14));
    	}
    	if(OreDictionary.doesOreNameExist("oreSalt")){
			OreDictionary.registerOre("oreSalt", new ItemStack(oreDust, 1, 15));
	    	OreDictionary.registerOre("oreSalt", new ItemStack(oreGravel, 1, 15));
	    	OreDictionary.registerOre("oreSalt", new ItemStack(oreSand, 1, 15));
    	}
    	if(OreDictionary.doesOreNameExist("oreSaltpeter")){
			OreDictionary.registerOre("oreSaltpeter", oreSaltpeterDust);
	    	OreDictionary.registerOre("oreSaltpeter", oreSaltpeterGravel);
	    	OreDictionary.registerOre("oreSaltpeter", oreSaltpeterSand);
    	}
    	if(rawSilicon != null){
    		OreDictionary.registerOre("itemSilicon", new ItemStack(rawSilicon, 1, 2));
    	}
    	if(crystalCertusQuartz != null){
    		OreDictionary.registerOre("crystalCertusQuartz", crystalCertusQuartz);
    	}
	}
}
