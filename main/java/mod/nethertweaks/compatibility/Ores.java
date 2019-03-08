package mod.nethertweaks.compatibility;

import mod.nethertweaks.Config;
import mod.nethertweaks.INames;
import mod.nethertweaks.Konstanten;
import mod.nethertweaks.NTMBlocks;
import mod.nethertweaks.NTMItems;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.handler.NTMSieveHandler;
import mod.sfhcore.Constants;
import mod.sfhcore.Registry;
import mod.sfhcore.items.ItemThing;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ItemLayerModel.Loader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.OreDictionary.OreRegisterEvent;

public class Ores implements INames{

    public static Item rawSilicon = Item.REGISTRY.getObject(new ResourceLocation("galacticraftcore", "basicItem.rawSilicon"));
    public static Item crystalCertusQuartz = Item.REGISTRY.getObject(new ResourceLocation("appliedenergistics2", "crystalCertusQuartz"));
    public static Item blizzpowder = Item.REGISTRY.getObject(new ResourceLocation("thermalfoundation", "material"));
    public static Item ingotOsmium = Item.REGISTRY.getObject(new ResourceLocation("mekanism", "Ingot"));

    
    private static final char x = 'X';
    private static String XX = "XX";
	
	public static void registerRecipes(){
		//ore blocks
		//Nicht Erze
		if(blizzpowder != null) NTMSieveHandler.register(Blocks.SNOW, blizzpowder, 1025, 20);
		
		if(OreDictionary.doesOreNameExist("oreCopper") && Config.oreCopper && OreDictionary.getOres("oreCopper").size() > 0 && OreDictionary.getOres("ingotCopper").size() > 0){
			NTMSieveHandler.register(NTMBlocks.blockDust, 0, NTMItems.oreMaterial, 34, 20);
			NTMSieveHandler.register(Blocks.SAND, 0, NTMItems.oreMaterial, 17, 20);
			NTMSieveHandler.register(Blocks.GRAVEL, 0, NTMItems.oreMaterial, 0, 20);
			
			GameRegistry.addShapedRecipe(Konstanten.OREGRAVELCOPPER, XX, XX, x, Konstanten.CHUNKCOPPER);
			GameRegistry.addShapedRecipe(Konstanten.ORESANDCOPPER, XX, XX, x, Konstanten.CRUSHEDCOPPER);
			GameRegistry.addShapedRecipe(Konstanten.OREDUSTCOPPER, XX, XX, x, Konstanten.PILECOPPER);
			
			GameRegistry.addSmelting(Konstanten.OREGRAVELCOPPER, OreDictionary.getOres("ingotCopper").get(0), 2.0f);
            GameRegistry.addSmelting(Konstanten.ORESANDCOPPER, OreDictionary.getOres("ingotCopper").get(0), 2.0f);
            GameRegistry.addSmelting(Konstanten.OREDUSTCOPPER, OreDictionary.getOres("ingotCopper").get(0), 2.0f);
            
		}
		if(OreDictionary.doesOreNameExist("oreTin") && Config.oreTin && OreDictionary.getOres("oreTin").size() > 0 && OreDictionary.getOres("ingotTin").size() > 0){
			NTMSieveHandler.register(NTMBlocks.blockDust, 0, NTMItems.oreMaterial, 35, 20);
			NTMSieveHandler.register(Blocks.SAND, 0, NTMItems.oreMaterial, 18, 20);
			NTMSieveHandler.register(Blocks.GRAVEL, 0, NTMItems.oreMaterial, 1, 20);
			
			GameRegistry.addShapedRecipe(Konstanten.OREGRAVELTIN, XX, XX, x, Konstanten.CHUNKCOPPER);
			GameRegistry.addShapedRecipe(Konstanten.ORESANDTIN, XX, XX, x, Konstanten.CRUSHEDTIN);
			GameRegistry.addShapedRecipe(Konstanten.OREDUSTTIN, XX, XX, x, Konstanten.PILETIN);
			
            GameRegistry.addSmelting(Konstanten.OREGRAVELTIN, OreDictionary.getOres("ingotTin").get(0), 2.0f);
            GameRegistry.addSmelting(Konstanten.ORESANDTIN, OreDictionary.getOres("ingotTin").get(0), 2.0f);
            GameRegistry.addSmelting(Konstanten.OREDUSTTIN, OreDictionary.getOres("ingotTin").get(0), 2.0f);
            
		}
		if(OreDictionary.doesOreNameExist("oreLead") && Config.oreLead && OreDictionary.getOres("oreLead").size() > 0 && OreDictionary.getOres("ingotLead").size() > 0){
			NTMSieveHandler.register(NTMBlocks.blockDust, 0, NTMItems.oreMaterial, 36, 20);
			NTMSieveHandler.register(Blocks.SAND, 0, NTMItems.oreMaterial, 19, 20);
			NTMSieveHandler.register(Blocks.GRAVEL, 0, NTMItems.oreMaterial, 2, 20);
			
			GameRegistry.addShapedRecipe(Konstanten.OREGRAVELLEAD, XX, XX, x, Konstanten.CHUNKLEAD);
			GameRegistry.addShapedRecipe(Konstanten.ORESANDLEAD, XX, XX, x, Konstanten.CRUSHEDLEAD);
			GameRegistry.addShapedRecipe(Konstanten.OREDUSTLEAD, XX, XX, x, Konstanten.PILELEAD);

            GameRegistry.addSmelting(Konstanten.OREGRAVELLEAD, OreDictionary.getOres("ingotLead").get(0), 2.0f);
            GameRegistry.addSmelting(Konstanten.ORESANDLEAD, OreDictionary.getOres("ingotLead").get(0), 2.0f);
            GameRegistry.addSmelting(Konstanten.OREDUSTLEAD, OreDictionary.getOres("ingotLead").get(0), 2.0f);
            
		}
		if(OreDictionary.doesOreNameExist("oreNickel") && Config.oreNickel && OreDictionary.getOres("oreNickel").size() > 0 && OreDictionary.getOres("ingotNickel").size() > 0){
			NTMSieveHandler.register(NTMBlocks.blockDust, 0, NTMItems.oreMaterial, 37, 20);
			NTMSieveHandler.register(Blocks.SAND, 0, NTMItems.oreMaterial, 20, 20);
			NTMSieveHandler.register(Blocks.GRAVEL, 0, NTMItems.oreMaterial, 3, 20);
			
			GameRegistry.addShapedRecipe(Konstanten.OREGRAVELNICKEL, XX, XX, x, Konstanten.CHUNKNICKEL);
			GameRegistry.addShapedRecipe(Konstanten.ORESANDNICKEL, XX, XX, x, Konstanten.CRUSHEDNICKEL);
			GameRegistry.addShapedRecipe(Konstanten.OREDUSTNICKEL, XX, XX, x, Konstanten.PILENICKEL);

            GameRegistry.addSmelting(Konstanten.OREGRAVELNICKEL, OreDictionary.getOres("ingotNickel").get(0), 2.0f);
            GameRegistry.addSmelting(Konstanten.ORESANDNICKEL, OreDictionary.getOres("ingotNickel").get(0), 2.0f);
            GameRegistry.addSmelting(Konstanten.OREDUSTNICKEL, OreDictionary.getOres("ingotNickel").get(0), 2.0f);
		}
		if(OreDictionary.doesOreNameExist("orePlatinum") && Config.orePlatinum && OreDictionary.getOres("orePlatinum").size() > 0 && OreDictionary.getOres("ingotPlatinum").size() > 0){
			NTMSieveHandler.register(NTMBlocks.blockDust, 0, NTMItems.oreMaterial, 38, 20);
			NTMSieveHandler.register(Blocks.SAND, 0, NTMItems.oreMaterial, 21, 20);
			NTMSieveHandler.register(Blocks.GRAVEL, 0, NTMItems.oreMaterial, 4, 20);
			
			GameRegistry.addShapedRecipe(Konstanten.OREGRAVELPLATINUM, XX, XX, x, Konstanten.CHUNKPLATINUM);
			GameRegistry.addShapedRecipe(Konstanten.ORESANDPLATINUM, XX, XX, x, Konstanten.CRUSHEDPLATINUM);
			GameRegistry.addShapedRecipe(Konstanten.OREDUSTPLATINUM, XX, XX, x, Konstanten.PILEPLATINUM);

            GameRegistry.addSmelting(Konstanten.OREGRAVELPLATINUM, OreDictionary.getOres("ingotPlatinum").get(0), 2.0f);
            GameRegistry.addSmelting(Konstanten.ORESANDPLATINUM, OreDictionary.getOres("ingotPlatinum").get(0), 2.0f);
            GameRegistry.addSmelting(Konstanten.OREDUSTPLATINUM, OreDictionary.getOres("ingotPlatinum").get(0), 2.0f);
            
            
		}
		if(OreDictionary.doesOreNameExist("oreSilver") && Config.oreSilver && OreDictionary.getOres("oreSilver").size() > 0 && OreDictionary.getOres("ingotSilver").size() > 0){
			NTMSieveHandler.register(NTMBlocks.blockDust, 0, NTMItems.oreMaterial, 39, 20);
			NTMSieveHandler.register(Blocks.SAND, 0, NTMItems.oreMaterial, 22, 20);
			NTMSieveHandler.register(Blocks.GRAVEL, 0, NTMItems.oreMaterial, 5, 20);
			
			GameRegistry.addShapedRecipe(Konstanten.OREGRAVELSILVER, XX, XX, x, Konstanten.CHUNKSILVER);
			GameRegistry.addShapedRecipe(Konstanten.ORESANDSILVER, XX, XX, x, Konstanten.CRUSHEDSILVER);
			GameRegistry.addShapedRecipe(Konstanten.OREDUSTSILVER, XX, XX, x, Konstanten.PILESILVER);

			GameRegistry.addSmelting(Konstanten.OREGRAVELSILVER, OreDictionary.getOres("ingotSilver").get(0), 2.0f);
            GameRegistry.addSmelting(Konstanten.ORESANDSILVER, OreDictionary.getOres("ingotSilver").get(0), 2.0f);
            GameRegistry.addSmelting(Konstanten.OREDUSTSILVER, OreDictionary.getOres("ingotSilver").get(0), 2.0f);
            
            
		}
		if(OreDictionary.doesOreNameExist("oreUranium") && Config.oreUranium && OreDictionary.getOres("oreUranium").size() > 0 && OreDictionary.getOres("ingotUranium").size() > 0){
			NTMSieveHandler.register(NTMBlocks.blockDust, 0, NTMItems.oreMaterial, 40, 20);
			NTMSieveHandler.register(Blocks.SAND, 0, NTMItems.oreMaterial, 23, 20);
			NTMSieveHandler.register(Blocks.GRAVEL, 0, NTMItems.oreMaterial, 6, 20);

			GameRegistry.addShapedRecipe(Konstanten.OREDUSTURANIUM, XX, XX, x, Konstanten.PILEURANIUM);
			GameRegistry.addShapedRecipe(Konstanten.OREGRAVELURANIUM, XX, XX, x, Konstanten.CHUNKURANIUM);
			GameRegistry.addShapedRecipe(Konstanten.ORESANDURANIUM, XX, XX, x, Konstanten.CRUSHEDURANIUM);
			
	        GameRegistry.addSmelting(Konstanten.OREGRAVELOSMIUM, OreDictionary.getOres("ingotUranium").get(0), 2.0f);
	        GameRegistry.addSmelting(Konstanten.ORESANDOSMIUM, OreDictionary.getOres("ingotUranium").get(0), 2.0f);
	        GameRegistry.addSmelting(Konstanten.OREDUSTOSMIUM, OreDictionary.getOres("ingotUranium").get(0), 2.0f);
	        
		}
		if(net.minecraftforge.fml.common.Loader.isModLoaded("mekanism") || net.minecraftforge.fml.common.Loader.isModLoaded("Mekanism")){
			
			OreDictionary.registerOre("ingotOsmium", ingotOsmium);
			
			NTMSieveHandler.register(NTMBlocks.blockDust, 0, NTMItems.oreMaterial, 41, 20);
			NTMSieveHandler.register(Blocks.SAND, 0, NTMItems.oreMaterial, 24, 20);
			NTMSieveHandler.register(Blocks.GRAVEL, 0, NTMItems.oreMaterial, 7, 20);

			GameRegistry.addShapedRecipe(Konstanten.OREDUSTOSMIUM, XX, XX, x, Konstanten.PILEOSMIUM);
			GameRegistry.addShapedRecipe(Konstanten.OREGRAVELOSMIUM, XX, XX, x, Konstanten.CHUNKOSMIUM);
			GameRegistry.addShapedRecipe(Konstanten.ORESANDOSMIUM, XX, XX, x, Konstanten.CRUSHEDOSMIUM);
			
			GameRegistry.addSmelting(Konstanten.OREGRAVELDRACONIUM, new ItemStack(ingotOsmium, 1, 1), 2.0f);
            GameRegistry.addSmelting(Konstanten.ORESANDDRACONIUM, new ItemStack(ingotOsmium, 1, 1), 2.0f);
            GameRegistry.addSmelting(Konstanten.OREDUSTDRACONIUM, new ItemStack(ingotOsmium, 1, 1), 2.0f);
	        
		}else if(OreDictionary.doesOreNameExist("oreOsmium") && Config.oreOsmium && OreDictionary.getOres("oreOsmium").size() > 0 && OreDictionary.getOres("ingotOsmium").size() > 0){
			NTMSieveHandler.register(NTMBlocks.blockDust, 0, NTMItems.oreMaterial, 41, 20);
			NTMSieveHandler.register(Blocks.SAND, 0, NTMItems.oreMaterial, 24, 20);
			NTMSieveHandler.register(Blocks.GRAVEL, 0, NTMItems.oreMaterial, 7, 20);

			GameRegistry.addShapedRecipe(Konstanten.OREDUSTOSMIUM, XX, XX, x, Konstanten.PILEOSMIUM);
			GameRegistry.addShapedRecipe(Konstanten.OREGRAVELOSMIUM, XX, XX, x, Konstanten.CHUNKOSMIUM);
			GameRegistry.addShapedRecipe(Konstanten.ORESANDOSMIUM, XX, XX, x, Konstanten.CRUSHEDOSMIUM);
			
			GameRegistry.addSmelting(Konstanten.OREGRAVELDRACONIUM, OreDictionary.getOres("ingotOsmium").get(0), 2.0f);
            GameRegistry.addSmelting(Konstanten.ORESANDDRACONIUM, OreDictionary.getOres("ingotOsmium").get(0), 2.0f);
            GameRegistry.addSmelting(Konstanten.OREDUSTDRACONIUM, OreDictionary.getOres("ingotOsmium").get(0), 2.0f);
		}
		if(OreDictionary.doesOreNameExist("oreDraconium") && Config.oreDraconium && OreDictionary.getOres("oreDraconium").size() > 0 && OreDictionary.getOres("ingotDraconium").size() > 0){
			NTMSieveHandler.register(NTMBlocks.blockDust, 0, NTMItems.oreMaterial, 42, 20);
			NTMSieveHandler.register(Blocks.SAND, 0, NTMItems.oreMaterial, 25, 20);
			NTMSieveHandler.register(Blocks.GRAVEL, 0, NTMItems.oreMaterial, 8, 20);

			//Draconic Evo
			GameRegistry.addShapedRecipe(Konstanten.OREDUSTDRACONIUM, XX, XX, x, Konstanten.PILEDRACONIUM);
			GameRegistry.addShapedRecipe(Konstanten.OREGRAVELDRACONIUM, XX, XX, x, Konstanten.CHUNKDRACONIUM);
			GameRegistry.addShapedRecipe(Konstanten.ORESANDDRACONIUM, XX, XX, x, Konstanten.CRUSHEDDRACONIUM);
			
            GameRegistry.addSmelting(Konstanten.OREGRAVELDRACONIUM, OreDictionary.getOres("ingotDraconium").get(0), 2.0f);
            GameRegistry.addSmelting(Konstanten.ORESANDDRACONIUM, OreDictionary.getOres("ingotDraconium").get(0), 2.0f);
            GameRegistry.addSmelting(Konstanten.OREDUSTDRACONIUM, OreDictionary.getOres("ingotDraconium").get(0), 2.0f);
            
			
		}
		if(OreDictionary.doesOreNameExist("oreSulfur") && Config.oreSulfur && OreDictionary.getOres("oreSulfur").size() > 0 && OreDictionary.getOres("dustSulfur").size() > 0){
			NTMSieveHandler.register(NTMBlocks.blockDust, 0, NTMItems.oreMaterial, 43, 20);
			NTMSieveHandler.register(Blocks.SAND, 0, NTMItems.oreMaterial, 26, 20);
			NTMSieveHandler.register(Blocks.GRAVEL, 0, NTMItems.oreMaterial, 9, 20);
			
			//Sulfur
			GameRegistry.addShapedRecipe(Konstanten.OREDUSTSULFUR, XX, XX, x, Konstanten.PILESULFUR);
			GameRegistry.addShapedRecipe(Konstanten.OREGRAVELSULFUR, XX, XX, x, Konstanten.CHUNKSULFUR);
			GameRegistry.addShapedRecipe(Konstanten.ORESANDSULFUR, XX, XX, x, Konstanten.CRUSHEDSULFUR);

            GameRegistry.addSmelting(Konstanten.OREGRAVELSULFUR, OreDictionary.getOres("dustSulfur").get(0), 2.0f);
            GameRegistry.addSmelting(Konstanten.ORESANDSULFUR, OreDictionary.getOres("dustSulfur").get(0), 2.0f);
            GameRegistry.addSmelting(Konstanten.OREDUSTSULFUR, OreDictionary.getOres("dustSulfur").get(0), 2.0f);
            
            
		}
		if(OreDictionary.doesOreNameExist("oreAluminum") && Config.oreAluminum && OreDictionary.getOres("oreAluminum").size() > 0 && OreDictionary.getOres("ingotAluminum").size() > 0){
			NTMSieveHandler.register(NTMBlocks.blockDust, 0, NTMItems.oreMaterial, 44, 20);
			NTMSieveHandler.register(Blocks.SAND, 0, NTMItems.oreMaterial, 27, 20);
			NTMSieveHandler.register(Blocks.GRAVEL, 0, NTMItems.oreMaterial, 10, 20);
			
			//Aluminum
			GameRegistry.addShapedRecipe(Konstanten.OREDUSTALUMINUM, XX, XX, x, Konstanten.PILEALUMINUM);
			GameRegistry.addShapedRecipe(Konstanten.OREGRAVELALUMINUM, XX, XX, x, Konstanten.CHUNKALUMINUM);
			GameRegistry.addShapedRecipe(Konstanten.ORESANDALUMINUM, XX, XX, x, Konstanten.CRUSHEDALUMINUM);
			
            GameRegistry.addSmelting(Konstanten.OREGRAVELALUMINUM, OreDictionary.getOres("ingotAluminum").get(0), 2.0f);
            GameRegistry.addSmelting(Konstanten.ORESANDALUMINUM, OreDictionary.getOres("ingotAluminum").get(0), 2.0f);
            GameRegistry.addSmelting(Konstanten.OREDUSTALUMINUM, OreDictionary.getOres("ingotAluminum").get(0), 2.0f);
            
		}
		if(OreDictionary.doesOreNameExist("oreSilicon") && Config.oreSilicon && OreDictionary.getOres("oreSilicon").size() > 0 && OreDictionary.getOres("ingotSilicon").size() > 0){
			NTMSieveHandler.register(NTMBlocks.blockDust, 0, NTMItems.oreMaterial, 45, 20);
			NTMSieveHandler.register(Blocks.SAND, 0, NTMItems.oreMaterial, 28, 20);
			NTMSieveHandler.register(Blocks.GRAVEL, 0, NTMItems.oreMaterial, 11, 20);
			
			GameRegistry.addShapedRecipe(Konstanten.OREDUSTSILICON, XX, XX, x, Konstanten.PILESILICON);
			GameRegistry.addShapedRecipe(Konstanten.OREGRAVELSILICON, XX, XX, x, Konstanten.CHUNKSILICON);
			GameRegistry.addShapedRecipe(Konstanten.ORESANDSILICON, XX, XX, x, Konstanten.CRUSHEDSILICON);
			
            GameRegistry.addSmelting(Konstanten.OREGRAVELSILICON, OreDictionary.getOres("itemSilicon").get(0), 2.0f);
            GameRegistry.addSmelting(Konstanten.ORESANDSILICON, OreDictionary.getOres("itemSilicon").get(0), 2.0f);
            GameRegistry.addSmelting(Konstanten.OREDUSTSILICON, OreDictionary.getOres("itemSilicon").get(0), 2.0f);
            
		}
		if(OreDictionary.doesOreNameExist("oreAmber") && Config.oreAmber && OreDictionary.getOres("oreAmber").size() > 0 && OreDictionary.getOres("gemAmber").size() > 0){
			NTMSieveHandler.register(NTMBlocks.blockDust, 0, NTMItems.oreMaterial, 46, 20);
			NTMSieveHandler.register(Blocks.SAND, 0, NTMItems.oreMaterial, 29, 20);
			NTMSieveHandler.register(Blocks.GRAVEL, 0, NTMItems.oreMaterial, 12, 20);
			
			//Amber
			GameRegistry.addShapedRecipe(Konstanten.OREDUSTAMBER, XX, XX, x, Konstanten.PILEAMBER);
			GameRegistry.addShapedRecipe(Konstanten.OREGRAVELAMBER, XX, XX, x, Konstanten.CHUNKAMBER);
			GameRegistry.addShapedRecipe(Konstanten.ORESANDAMBER, XX, XX, x, Konstanten.CRUSHEDAMBER);

            GameRegistry.addSmelting(Konstanten.OREGRAVELAMBER, OreDictionary.getOres("gemAmber").get(0), 2.0f);
            GameRegistry.addSmelting(Konstanten.ORESANDAMBER, OreDictionary.getOres("gemAmber").get(0), 2.0f);
            GameRegistry.addSmelting(Konstanten.OREDUSTAMBER, OreDictionary.getOres("gemAmber").get(0), 2.0f);
            
		}
		if(OreDictionary.doesOreNameExist("oreCinnabar") && Config.oreCinnabar && OreDictionary.getOres("oreCinnabar").size() > 0 && OreDictionary.getOres("crystalCinnabar").size() > 0){
			NTMSieveHandler.register(NTMBlocks.blockDust, 0, NTMItems.oreMaterial, 47, 20);
			NTMSieveHandler.register(Blocks.SAND, 0, NTMItems.oreMaterial, 30, 20);
			NTMSieveHandler.register(Blocks.GRAVEL, 0, NTMItems.oreMaterial, 13, 20);
			
			//Cinnabar
			GameRegistry.addShapedRecipe(Konstanten.OREDUSTCINNABAR, XX, XX, x, Konstanten.PILECINNABAR);
			GameRegistry.addShapedRecipe(Konstanten.OREGRAVELCINNABAR, XX, XX, x, Konstanten.CHUNKCINNABAR);
			GameRegistry.addShapedRecipe(Konstanten.ORESANDCINNABAR, XX, XX, x, Konstanten.CRUSHEDCINNABAR);

            GameRegistry.addSmelting(Konstanten.OREGRAVELCINNABAR, OreDictionary.getOres("crystalCinnabar").get(0), 2.0f);
            GameRegistry.addSmelting(Konstanten.ORESANDCINNABAR, OreDictionary.getOres("crystalCinnabar").get(0), 2.0f);
            GameRegistry.addSmelting(Konstanten.OREDUSTCINNABAR, OreDictionary.getOres("crystalCinnabar").get(0), 2.0f);
            
            
		}
		if(OreDictionary.doesOreNameExist("oreCertusQuartz") && Config.oreCertusQuartz && OreDictionary.getOres("oreCertusQuartz").size() > 0 && OreDictionary.getOres("crystalCertusQuartz").size() > 0){
			NTMSieveHandler.register(NTMBlocks.blockDust, 0, NTMItems.oreMaterial, 48, 20);
			NTMSieveHandler.register(Blocks.SAND, 0, NTMItems.oreMaterial, 31, 20);
			NTMSieveHandler.register(Blocks.GRAVEL, 0, NTMItems.oreMaterial, 14, 20);
			
			//AppEng2
			GameRegistry.addShapedRecipe(Konstanten.OREDUSTCERTUSQUARTZ, XX, XX, x, Konstanten.PILECERTUSQUARTZ);
			GameRegistry.addShapedRecipe(Konstanten.OREGRAVELCERTUSQUARTZ, XX, XX, x, Konstanten.CHUNKCERTUSQUARTZ);
			GameRegistry.addShapedRecipe(Konstanten.ORESANDCERTUSQUARTZ, XX, XX, x, Konstanten.CRUSHEDCERTUSQUARTZ);

            GameRegistry.addSmelting(Konstanten.OREGRAVELCERTUSQUARTZ, OreDictionary.getOres("crystalCertusQuartz").get(0), 2.0f);
            GameRegistry.addSmelting(Konstanten.ORESANDCERTUSQUARTZ, OreDictionary.getOres("crystalCertusQuartz").get(0), 2.0f);
            GameRegistry.addSmelting(Konstanten.OREDUSTCERTUSQUARTZ, OreDictionary.getOres("crystalCertusQuartz").get(0), 2.0f);
                    
		}
		if(Config.oreSalt && OreDictionary.getOres("oreSalt").size() > 0 && OreDictionary.getOres("itemSalt").size() > 0){
			//Salt
			NTMSieveHandler.register(NTMBlocks.blockDust, 0, NTMItems.oreMaterial, 49, 20);
			NTMSieveHandler.register(Blocks.SAND, 0, NTMItems.oreMaterial, 32, 20);
			NTMSieveHandler.register(Blocks.GRAVEL, 0, NTMItems.oreMaterial, 15, 20);
			
			GameRegistry.addShapedRecipe(new ItemStack(NTMBlocks.oreDust, 1, 15), XX, XX, x, Konstanten.PILESALT);
			GameRegistry.addShapedRecipe(new ItemStack(NTMBlocks.oreGravel, 1, 15), XX, XX, x, Konstanten.CHUNKSALT);
			GameRegistry.addShapedRecipe(new ItemStack(NTMBlocks.oreSand, 1, 15), XX, XX, x, Konstanten.CRUSHEDSALT);
			
			/*
			GameRegistry.addSmelting(new ItemStack(NTMBlocks.oreGravel, 1, 15), new ItemStack(NTMItems.itemBase, 1, 16), 2.0f);
	        GameRegistry.addSmelting(new ItemStack(NTMBlocks.oreSand, 1, 15), new ItemStack(NTMItems.itemBase, 1, 16), 2.0f);
	        GameRegistry.addSmelting(new ItemStack(NTMBlocks.oreDust, 1, 15), new ItemStack(NTMItems.itemBase, 1, 16), 2.0f);
	        */
		}
		
		if(OreDictionary.doesOreNameExist("oreSaltpeter") && OreDictionary.getOres("oreSaltpeter").size() > 0 && OreDictionary.getOres("dustSaltpeter").size() > 0){
			NTMSieveHandler.register(NTMBlocks.blockDust, 0, NTMItems.oreMaterial, 50, 20);
			NTMSieveHandler.register(Blocks.SAND, 0, NTMItems.oreMaterial, 33, 20);
			NTMSieveHandler.register(Blocks.GRAVEL, 0, NTMItems.oreMaterial, 16, 20);
			
			//Saltpeter
			GameRegistry.addShapedRecipe(new ItemStack(NTMBlocks.oreDust, 1, 16), XX, XX, x, Konstanten.PILESALTPETER);
			GameRegistry.addShapedRecipe(new ItemStack(NTMBlocks.oreGravel, 1, 16), XX, XX, x, Konstanten.CHUNKSALTPETER);
			GameRegistry.addShapedRecipe(new ItemStack(NTMBlocks.oreSand, 1, 16), XX, XX, x, Konstanten.CRUSHEDSALTPETER);

            GameRegistry.addSmelting(new ItemStack(NTMBlocks.oreGravel, 1, 16), OreDictionary.getOres("dustSaltpeter").get(0), 2.0f);
            GameRegistry.addSmelting(new ItemStack(NTMBlocks.oreSand, 1, 16), OreDictionary.getOres("dustSaltpeter").get(0), 2.0f);
            GameRegistry.addSmelting(new ItemStack(NTMBlocks.oreDust, 1, 16), OreDictionary.getOres("dustSaltpeter").get(0), 2.0f);
            
		}
	}
	
	public static void registerNames(){
    	
		if(OreDictionary.doesOreNameExist("oreCopper") && Config.oreCopper && OreDictionary.doesOreNameExist("oreCopper") && OreDictionary.getOres("oreCopper").size() > 0 && OreDictionary.getOres("ingotCopper").size() > 0){
	    	OreDictionary.registerOre("oreCopper", Konstanten.OREDUSTCOPPER);
	    	OreDictionary.registerOre("oreCopper", Konstanten.OREGRAVELCOPPER);
	    	OreDictionary.registerOre("oreCopper", Konstanten.ORESANDCOPPER);
		}
		if(OreDictionary.doesOreNameExist("oreTin") && Config.oreTin && OreDictionary.getOres("oreTin").size() > 0 && OreDictionary.getOres("ingotTin").size() > 0){
	    	OreDictionary.registerOre("oreTin", Konstanten.OREDUSTTIN);
	    	OreDictionary.registerOre("oreTin", Konstanten.OREGRAVELTIN);
	    	OreDictionary.registerOre("oreTin", Konstanten.ORESANDTIN);
    	}
		if(OreDictionary.doesOreNameExist("oreLead") && Config.oreLead && OreDictionary.getOres("oreLead").size() > 0 && OreDictionary.getOres("ingotLead").size() > 0){
	    	OreDictionary.registerOre("oreLead", Konstanten.OREDUSTLEAD);
	    	OreDictionary.registerOre("oreLead", Konstanten.OREGRAVELLEAD);
	    	OreDictionary.registerOre("oreLead", Konstanten.ORESANDLEAD);
    	}
    	if(OreDictionary.doesOreNameExist("oreNickel")){
	    	OreDictionary.registerOre("oreNickel", Konstanten.OREDUSTNICKEL);
	    	OreDictionary.registerOre("oreNickel", Konstanten.OREGRAVELNICKEL);
	    	OreDictionary.registerOre("oreNickel", Konstanten.ORESANDNICKEL);
    	}
    	if(OreDictionary.doesOreNameExist("orePlatinum")){
	    	OreDictionary.registerOre("orePlatinum", Konstanten.OREDUSTPLATINUM);
	    	OreDictionary.registerOre("orePlatinum", Konstanten.OREGRAVELPLATINUM);
	    	OreDictionary.registerOre("orePlatinum", Konstanten.ORESANDPLATINUM);
    	}
    	if(OreDictionary.doesOreNameExist("oreSilver")){
	    	OreDictionary.registerOre("oreSilver", Konstanten.OREDUSTSILVER);
	    	OreDictionary.registerOre("oreSilver", Konstanten.OREGRAVELSILVER);
	    	OreDictionary.registerOre("oreSilver", Konstanten.ORESANDSILVER);
    	}
    	if(OreDictionary.doesOreNameExist("oreUranium")){
	    	OreDictionary.registerOre("oreUranium", Konstanten.OREDUSTURANIUM);
	    	OreDictionary.registerOre("oreUranium", Konstanten.OREGRAVELURANIUM);
	    	OreDictionary.registerOre("oreUranium", Konstanten.ORESANDURANIUM);
    	}
    	if(OreDictionary.doesOreNameExist("oreOsmium")){
			OreDictionary.registerOre("oreOsmium", Konstanten.OREGRAVELOSMIUM);
			OreDictionary.registerOre("oreOsmium", Konstanten.ORESANDOSMIUM);
			OreDictionary.registerOre("oreOsmium", Konstanten.OREDUSTOSMIUM);
    	}
		if(OreDictionary.doesOreNameExist("oreDraconium")){
			OreDictionary.registerOre("oreDraconium", Konstanten.OREDUSTDRACONIUM);
	    	OreDictionary.registerOre("oreDraconium", Konstanten.OREGRAVELDRACONIUM);
	    	OreDictionary.registerOre("oreDraconium", Konstanten.ORESANDDRACONIUM);
		}
    	if(OreDictionary.doesOreNameExist("oreSulfur")){
			OreDictionary.registerOre("oreSulfur", Konstanten.OREDUSTSULFUR);
	    	OreDictionary.registerOre("oreSulfur", Konstanten.OREGRAVELSULFUR);
	    	OreDictionary.registerOre("oreSulfur", Konstanten.ORESANDSULFUR);
    	}
    	if(OreDictionary.doesOreNameExist("oreAluminum")){
			OreDictionary.registerOre("oreAluminum", Konstanten.OREDUSTALUMINUM);
	    	OreDictionary.registerOre("oreAluminum", Konstanten.OREGRAVELALUMINUM);
	    	OreDictionary.registerOre("oreAluminum", Konstanten.ORESANDALUMINUM);
    	}
    	if(OreDictionary.doesOreNameExist("oreSilicon")){
			OreDictionary.registerOre("oreSilicon", Konstanten.OREDUSTSILICON);
	    	OreDictionary.registerOre("oreSilicon", Konstanten.OREGRAVELSILICON);
	    	OreDictionary.registerOre("oreSilicon", Konstanten.ORESANDSILICON);
    	}
    	if(OreDictionary.doesOreNameExist("oreAmber")){
			OreDictionary.registerOre("oreAmber", Konstanten.OREDUSTAMBER);
	    	OreDictionary.registerOre("oreAmber", Konstanten.OREGRAVELAMBER);
	    	OreDictionary.registerOre("oreAmber", Konstanten.ORESANDAMBER);
    	}
    	if(OreDictionary.doesOreNameExist("oreCinnabar")){
			OreDictionary.registerOre("oreCinnabar", Konstanten.OREDUSTCINNABAR);
	    	OreDictionary.registerOre("oreCinnabar", Konstanten.OREGRAVELCINNABAR);
	    	OreDictionary.registerOre("oreCinnabar", Konstanten.ORESANDCINNABAR);
    	}
    	if(OreDictionary.doesOreNameExist("oreCertusQuartz")){
			OreDictionary.registerOre("oreCertusQuartz", Konstanten.OREDUSTCERTUSQUARTZ);
	    	OreDictionary.registerOre("oreCertusQuartz", Konstanten.OREGRAVELCERTUSQUARTZ);
	    	OreDictionary.registerOre("oreCertusQuartz", Konstanten.ORESANDCERTUSQUARTZ);
    	}
    	if(OreDictionary.doesOreNameExist("oreSalt")){
			OreDictionary.registerOre("oreSalt", Konstanten.OREDUSTSALT);
	    	OreDictionary.registerOre("oreSalt", Konstanten.OREGRAVELSALT);
	    	OreDictionary.registerOre("oreSalt", Konstanten.ORESANDSALT);
    	}
    	if(OreDictionary.doesOreNameExist("oreSaltpeter")){
			OreDictionary.registerOre("oreSaltpeter", Konstanten.OREDUSTSALTPETER);
	    	OreDictionary.registerOre("oreSaltpeter", Konstanten.OREGRAVELSALTPETER);
	    	OreDictionary.registerOre("oreSaltpeter", Konstanten.ORESANDSALTPETER);
    	}
    	if(rawSilicon != null){
    		OreDictionary.registerOre("itemSilicon", new ItemStack(rawSilicon, 1, 2));
    	}
    	if(crystalCertusQuartz != null){
    		OreDictionary.registerOre("crystalCertusQuartz", crystalCertusQuartz);
    	}
	}
}
