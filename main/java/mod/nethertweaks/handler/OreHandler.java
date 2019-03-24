package mod.nethertweaks.handler;

import mod.nethertweaks.INames;
import mod.nethertweaks.NetherTweaksMod;
import mod.sfhcore.Constants;
import mod.sfhcore.Registry;
import mod.sfhcore.blocks.CubeFalling;
import mod.sfhcore.items.CustomItem;
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

public class OreHandler implements INames{
	
    public static Item rawSilicon = Item.REGISTRY.getObject(new ResourceLocation("GalacticraftCore", "basicItem.rawSilicon"));
    public static Item crystalCertusQuartz = Item.REGISTRY.getObject(new ResourceLocation("appliedenergistics2", "crystalCertusQuartz"));
    public static Item blizzpowder = Item.REGISTRY.getObject(new ResourceLocation("ThermalFoundation", "material"));
    
    public static Block oreGravel;
    public static Block oreSand;
    public static Block oreDust;
    
    public static Item oreMaterial;
    
    public static void init() {
    	registerOres();
    	registerNames();
    	registerRecipes();
    }
    
	private static void registerOres(){
		
		oreGravel = Registry.registerBlock(new CubeFalling(16, Material.GROUND, 2.0F, 0.4f, OREGRAVEL), Constants.MOD);
		oreSand = Registry.registerBlock(new CubeFalling(16, Material.SAND, 2.0F, 0.4f, ORESAND), Constants.MOD);
		oreDust = Registry.registerBlock(new CubeFalling(16, Material.SAND, 2.0F, 0.4f, OREDUST), Constants.MOD);
		
		oreMaterial = new CustomItem(null, 64, NetherTweaksMod.tabNetherTweaksMod, true, 51, OREMATERIAL);
		
		//Nicht Erze
		if(blizzpowder != null) NTMSieveHandler.register(Blocks.SNOW, blizzpowder, 1025, 20);
		
		//Erze
		if(OreDictionary.doesOreNameExist("oreCopper")){
			
            NTMSieveHandler.register(BlockHandler.dust, 0, oreMaterial, 32, 20);
			NTMSieveHandler.register(Blocks.SAND, 0, oreMaterial, 16, 20);
			NTMSieveHandler.register(Blocks.GRAVEL, 0, oreMaterial, 0, 20);
            
		}
		if(OreDictionary.doesOreNameExist("oreTin")){
			
            NTMSieveHandler.register(BlockHandler.dust, 0, oreMaterial, 33, 20);
			NTMSieveHandler.register(Blocks.SAND, 0, oreMaterial, 17, 20);
			NTMSieveHandler.register(Blocks.GRAVEL, 0, oreMaterial, 1, 20);
            
		}
		if(OreDictionary.doesOreNameExist("oreLead")){
			
            NTMSieveHandler.register(BlockHandler.dust, 0, oreMaterial, 34, 20);
			NTMSieveHandler.register(Blocks.SAND, 0, oreMaterial, 18, 20);
			NTMSieveHandler.register(Blocks.GRAVEL, 0, oreMaterial, 2, 20);
            
		}
		if(OreDictionary.doesOreNameExist("oreNickel")){
			
            NTMSieveHandler.register(BlockHandler.dust, 0, oreMaterial, 35, 20);
			NTMSieveHandler.register(Blocks.SAND, 0, oreMaterial, 19, 20);
			NTMSieveHandler.register(Blocks.GRAVEL, 0, oreMaterial, 3, 20);
            
		}
		if(OreDictionary.doesOreNameExist("orePlatinum")){
			
            NTMSieveHandler.register(BlockHandler.dust, 0, oreMaterial, 36, 20);
			NTMSieveHandler.register(Blocks.SAND, 0, oreMaterial, 20, 20);
			NTMSieveHandler.register(Blocks.GRAVEL, 0, oreMaterial, 4, 20);
            
		}
		if(OreDictionary.doesOreNameExist("oreSilver")){
			
            NTMSieveHandler.register(BlockHandler.dust, 0, oreMaterial, 37, 20);
			NTMSieveHandler.register(Blocks.SAND, 0, oreMaterial, 21, 20);
			NTMSieveHandler.register(Blocks.GRAVEL, 0, oreMaterial, 5, 20);
            
		}
		if(OreDictionary.doesOreNameExist("oreUranium")){

	        NTMSieveHandler.register(BlockHandler.dust, 0, oreMaterial, 38, 20);
			NTMSieveHandler.register(Blocks.SAND, 0, oreMaterial, 22, 20);
			NTMSieveHandler.register(Blocks.GRAVEL, 0, oreMaterial, 6, 20);
	        
		}
		if(OreDictionary.doesOreNameExist("oreOsmium")){

	        NTMSieveHandler.register(BlockHandler.dust, 0, oreMaterial, 39, 20);
			NTMSieveHandler.register(Blocks.SAND, 0, oreMaterial, 23, 20);
			NTMSieveHandler.register(Blocks.GRAVEL, 0, oreMaterial, 7, 20);
	        
		}if(OreDictionary.doesOreNameExist("oreDraconium")){

			//Draconic Evo
            NTMSieveHandler.register(BlockHandler.dust, 0, oreMaterial, 40, 20);
			NTMSieveHandler.register(Blocks.SAND, 0, oreMaterial, 24, 20);
			NTMSieveHandler.register(Blocks.GRAVEL, 0, oreMaterial, 8, 20);
			
		}
		if(OreDictionary.doesOreNameExist("oreSulfur")){
			//Sulfur
            NTMSieveHandler.register(BlockHandler.dust, 0, oreMaterial, 41, 20);
			NTMSieveHandler.register(Blocks.SAND, 0, oreMaterial, 25, 20);
			NTMSieveHandler.register(Blocks.GRAVEL, 0, oreMaterial, 9, 20);
            
		}
		if(OreDictionary.doesOreNameExist("oreAluminum")){
			//Aluminum
            NTMSieveHandler.register(BlockHandler.dust, 0, oreMaterial, 42, 20);
			NTMSieveHandler.register(Blocks.SAND, 0, oreMaterial, 26, 20);
			NTMSieveHandler.register(Blocks.GRAVEL, 0, oreMaterial, 10, 20);
		}
		if(OreDictionary.doesOreNameExist("oreSilicon")){
			
            NTMSieveHandler.register(BlockHandler.dust, 0, oreMaterial, 43, 20);
			NTMSieveHandler.register(Blocks.SAND, 0, oreMaterial, 27, 20);
			NTMSieveHandler.register(Blocks.GRAVEL, 0, oreMaterial, 11, 20);
		}
		if(OreDictionary.doesOreNameExist("oreAmber")){
			//Amber
            NTMSieveHandler.register(BlockHandler.dust, 0, oreMaterial, 44, 20);
			NTMSieveHandler.register(Blocks.SAND, 0, oreMaterial, 28, 20);
			NTMSieveHandler.register(Blocks.GRAVEL, 0, oreMaterial, 12, 20);
		}
		if(OreDictionary.doesOreNameExist("oreCinnabar")){
			//Cinnabar
			NTMSieveHandler.register(BlockHandler.dust, 0, oreMaterial, 45, 20);
			NTMSieveHandler.register(Blocks.SAND, 0, oreMaterial, 29, 20);
			NTMSieveHandler.register(Blocks.GRAVEL, 0, oreMaterial, 13, 20);
		}
		if(OreDictionary.doesOreNameExist("oreCertusQuartz")){
			//AppEng2
            NTMSieveHandler.register(BlockHandler.dust, 0, oreMaterial, 46, 20);
			NTMSieveHandler.register(Blocks.SAND, 0, oreMaterial, 30, 20);
			NTMSieveHandler.register(Blocks.GRAVEL, 0, oreMaterial, 14, 20);
            
		}
		
		//Salt
        NTMSieveHandler.register(BlockHandler.dust, 0, oreMaterial, 47, 20);
		NTMSieveHandler.register(Blocks.SAND, 0, oreMaterial, 31, 20);
		NTMSieveHandler.register(Blocks.GRAVEL, 0, oreMaterial, 15, 20);
		
	}
	
	private static void registerRecipes(){
		//ore blocks
		
		if(OreDictionary.doesOreNameExist("oreCopper")){
			
			GameRegistry.addSmelting(new ItemStack(oreGravel, 1, 0), OreDictionary.getOres("ingotCopper").get(0), 2.0F);
            GameRegistry.addSmelting(new ItemStack(oreSand, 1, 0), OreDictionary.getOres("ingotCopper").get(0), 2.0F);
            GameRegistry.addSmelting(new ItemStack(oreDust, 1, 0), OreDictionary.getOres("ingotCopper").get(0), 2.0F);
            
		}
		if(OreDictionary.doesOreNameExist("oreTin")){
			
            GameRegistry.addSmelting(new ItemStack(oreGravel, 1, 1), OreDictionary.getOres("ingotTin").get(0), 2.0F);
            GameRegistry.addSmelting(new ItemStack(oreSand, 1, 1), OreDictionary.getOres("ingotTin").get(0), 2.0F);
            GameRegistry.addSmelting(new ItemStack(oreDust, 1, 1), OreDictionary.getOres("ingotTin").get(0), 2.0F);
            
		}
		if(OreDictionary.doesOreNameExist("oreLead")){

            GameRegistry.addSmelting(new ItemStack(oreGravel, 1, 2), OreDictionary.getOres("ingotLead").get(0), 2.0F);
            GameRegistry.addSmelting(new ItemStack(oreSand, 1, 2), OreDictionary.getOres("ingotLead").get(0), 2.0F);
            GameRegistry.addSmelting(new ItemStack(oreDust, 1, 2), OreDictionary.getOres("ingotLead").get(0), 2.0F);
            
		}
		if(OreDictionary.doesOreNameExist("oreNickel")){

            GameRegistry.addSmelting(new ItemStack(oreGravel , 1, 3), OreDictionary.getOres("ingotNickel").get(0), 2.0F);
            GameRegistry.addSmelting(new ItemStack(oreSand, 1, 3), OreDictionary.getOres("ingotNickel").get(0), 2.0F);
            GameRegistry.addSmelting(new ItemStack(oreDust, 1, 3), OreDictionary.getOres("ingotNickel").get(0), 2.0F);
		}
		if(OreDictionary.doesOreNameExist("orePlatinum")){

            GameRegistry.addSmelting(new ItemStack(oreGravel, 1, 4), OreDictionary.getOres("ingotPlatinum").get(0), 2.0F);
            GameRegistry.addSmelting(new ItemStack(oreSand, 1, 4), OreDictionary.getOres("ingotPlatinum").get(0), 2.0F);
            GameRegistry.addSmelting(new ItemStack(oreDust, 1, 4), OreDictionary.getOres("ingotPlatinum").get(0), 2.0F);
            
		}
		if(OreDictionary.doesOreNameExist("oreSilver")){

			GameRegistry.addSmelting(new ItemStack(oreGravel, 1, 5), OreDictionary.getOres("ingotSilver").get(0), 2.0F);
            GameRegistry.addSmelting(new ItemStack(oreSand, 1, 5), OreDictionary.getOres("ingotSilver").get(0), 2.0F);
            GameRegistry.addSmelting(new ItemStack(oreDust, 1, 5), OreDictionary.getOres("ingotSilver").get(0), 2.0F);
            
            
		}
		if(OreDictionary.doesOreNameExist("oreUranium")){
			
	        GameRegistry.addSmelting(new ItemStack(oreGravel, 1, 7), OreDictionary.getOres("ingotUranium").get(0), 2.0F);
	        GameRegistry.addSmelting(new ItemStack(oreSand, 1, 7), OreDictionary.getOres("ingotUranium").get(0), 2.0F);
	        GameRegistry.addSmelting(new ItemStack(oreDust, 1, 7), OreDictionary.getOres("ingotUranium").get(0), 2.0F);
	        
	        
		}
		if(OreDictionary.doesOreNameExist("oreDraconium")){
			
            GameRegistry.addSmelting(new ItemStack(oreGravel, 1, 8), OreDictionary.getOres("ingotDraconium").get(0), 2.0F);
            GameRegistry.addSmelting(new ItemStack(oreSand, 1, 8), OreDictionary.getOres("ingotDraconium").get(0), 2.0F);
            GameRegistry.addSmelting(new ItemStack(oreDust, 1, 8), OreDictionary.getOres("ingotDraconium").get(0), 2.0F);
            
			
		}
		if(OreDictionary.doesOreNameExist("oreSulfur")){

            GameRegistry.addSmelting(new ItemStack(oreGravel, 1, 9), OreDictionary.getOres("dustSulfur").get(0), 2.0F);
            GameRegistry.addSmelting(new ItemStack(oreSand, 1, 9), OreDictionary.getOres("dustSulfur").get(0), 2.0F);
            GameRegistry.addSmelting(new ItemStack(oreDust, 1, 9), OreDictionary.getOres("dustSulfur").get(0), 2.0F);
            
            
		}
		if(OreDictionary.doesOreNameExist("oreAluminum")){
			
            GameRegistry.addSmelting(new ItemStack(oreGravel, 1, 10), OreDictionary.getOres("ingotAluminum").get(0), 2.0F);
            GameRegistry.addSmelting(new ItemStack(oreSand, 1, 10), OreDictionary.getOres("ingotAluminum").get(0), 2.0F);
            GameRegistry.addSmelting(new ItemStack(oreDust, 1, 10), OreDictionary.getOres("ingotAluminum").get(0), 2.0F);
            
		}
		if(OreDictionary.doesOreNameExist("oreSilicon")){
			
            GameRegistry.addSmelting(new ItemStack(oreGravel, 1, 11), OreDictionary.getOres("itemSilicon").get(0), 2.0F);
            GameRegistry.addSmelting(new ItemStack(oreSand, 1, 11), OreDictionary.getOres("itemSilicon").get(0), 2.0F);
            GameRegistry.addSmelting(new ItemStack(oreDust, 1, 11), OreDictionary.getOres("itemSilicon").get(0), 2.0F);
            
		}
		if(OreDictionary.doesOreNameExist("oreAmber")){

            GameRegistry.addSmelting(new ItemStack(oreGravel, 1, 12), OreDictionary.getOres("gemAmber").get(0), 2.0F);
            GameRegistry.addSmelting(new ItemStack(oreSand, 1, 12), OreDictionary.getOres("gemAmber").get(0), 2.0F);
            GameRegistry.addSmelting(new ItemStack(oreDust, 1, 12), OreDictionary.getOres("gemAmber").get(0), 2.0F);
            
		}
		if(OreDictionary.doesOreNameExist("oreCinnabar")){

            GameRegistry.addSmelting(new ItemStack(oreGravel, 1, 13), OreDictionary.getOres("crystalCinnabar").get(0), 2.0F);
            GameRegistry.addSmelting(new ItemStack(oreSand, 1, 13), OreDictionary.getOres("crystalCinnabar").get(0), 2.0F);
            GameRegistry.addSmelting(new ItemStack(oreDust, 1, 13), OreDictionary.getOres("crystalCinnabar").get(0), 2.0F);
            
            
		}
		if(OreDictionary.doesOreNameExist("oreCertusQuartz")){

            GameRegistry.addSmelting(new ItemStack(oreGravel, 1, 14), OreDictionary.getOres("crystalCertusQuartz").get(0), 2.0F);
            GameRegistry.addSmelting(new ItemStack(oreSand, 1, 14), OreDictionary.getOres("crystalCertusQuartz").get(0), 2.0F);
            GameRegistry.addSmelting(new ItemStack(oreDust, 1, 14), OreDictionary.getOres("crystalCertusQuartz").get(0), 2.0F);
                    
		}
		
		//Salt
		
		GameRegistry.addSmelting(new ItemStack(oreGravel, 1, 15), new ItemStack(ItemHandler.itemBase, 1, 16), 2.0F);
        GameRegistry.addSmelting(new ItemStack(oreSand, 1, 15), new ItemStack(ItemHandler.itemBase, 1, 16), 2.0F);
        GameRegistry.addSmelting(new ItemStack(oreDust, 1, 15), new ItemStack(ItemHandler.itemBase, 1, 16), 2.0F);
		
	}
	
	private static void registerNames(){
    	
		if(OreDictionary.doesOreNameExist("oreCopper")){
	    	OreDictionary.registerOre("oreCopper", new ItemStack(oreGravel, 1, 0));
	    	OreDictionary.registerOre("oreCopper", new ItemStack(oreSand, 1, 0));
	    	OreDictionary.registerOre("oreCopper", new ItemStack(oreDust, 1, 0));
		}
		if(OreDictionary.doesOreNameExist("oreTin")){
	    	OreDictionary.registerOre("oreTin", new ItemStack(oreGravel, 1, 1));
	    	OreDictionary.registerOre("oreTin", new ItemStack(oreSand, 1, 1));
	    	OreDictionary.registerOre("oreTin", new ItemStack(oreDust, 1, 1));
    	}
    	if(OreDictionary.doesOreNameExist("oreLead")){
	    	OreDictionary.registerOre("oreLead", new ItemStack(oreGravel, 1, 2));
	    	OreDictionary.registerOre("oreLead", new ItemStack(oreSand, 1, 2));
	    	OreDictionary.registerOre("oreLead", new ItemStack(oreDust, 1, 2));
    	}
    	if(OreDictionary.doesOreNameExist("oreNickel")){
	    	OreDictionary.registerOre("oreNickel", new ItemStack(oreGravel , 1, 3));
	    	OreDictionary.registerOre("oreNickel", new ItemStack(oreSand, 1, 3));
	    	OreDictionary.registerOre("oreNickel", new ItemStack(oreDust, 1, 3));
    	}
    	if(OreDictionary.doesOreNameExist("orePlatinum")){
	    	OreDictionary.registerOre("orePlatinum", new ItemStack(oreGravel, 1, 4));
	    	OreDictionary.registerOre("orePlatinum", new ItemStack(oreSand, 1, 4));
	    	OreDictionary.registerOre("orePlatinum", new ItemStack(oreDust, 1, 4));
    	}
    	if(OreDictionary.doesOreNameExist("oreSilver")){
	    	OreDictionary.registerOre("oreSilver", new ItemStack(oreGravel, 1, 5));
	    	OreDictionary.registerOre("oreSilver", new ItemStack(oreSand, 1, 5));
	    	OreDictionary.registerOre("oreSilver", new ItemStack(oreDust, 1, 5));
    	}
    	if(OreDictionary.doesOreNameExist("oreUranium")){
	    	OreDictionary.registerOre("oreUranium", new ItemStack(oreGravel, 1, 6));
	    	OreDictionary.registerOre("oreUranium", new ItemStack(oreSand, 1, 6));
	    	OreDictionary.registerOre("oreUranium", new ItemStack(oreDust, 1, 6));
    	}
    	if(OreDictionary.doesOreNameExist("oreOsmium")){
			OreDictionary.registerOre("oreOsmium", new ItemStack(oreGravel, 1, 7));
			OreDictionary.registerOre("oreOsmium", new ItemStack(oreSand, 1, 7));
			OreDictionary.registerOre("oreOsmium", new ItemStack(oreDust, 1, 7));
    	}
		if(OreDictionary.doesOreNameExist("oreDraconium")){
	    	OreDictionary.registerOre("oreDraconium", new ItemStack(oreGravel, 1, 8));
	    	OreDictionary.registerOre("oreDraconium", new ItemStack(oreSand, 1, 8));
			OreDictionary.registerOre("oreDraconium", new ItemStack(oreDust, 1, 8));
		}
    	if(OreDictionary.doesOreNameExist("oreSulfur")){
	    	OreDictionary.registerOre("oreSulfur", new ItemStack(oreGravel, 1, 9));
	    	OreDictionary.registerOre("oreSulfur", new ItemStack(oreSand, 1, 9));
			OreDictionary.registerOre("oreSulfur", new ItemStack(oreDust, 1, 9));
    	}
    	if(OreDictionary.doesOreNameExist("oreAluminum")){
	    	OreDictionary.registerOre("oreAluminum", new ItemStack(oreGravel, 1, 10));
	    	OreDictionary.registerOre("oreAluminum", new ItemStack(oreSand, 1, 10));
			OreDictionary.registerOre("oreAluminum", new ItemStack(oreDust, 1, 10));
    	}
    	if(OreDictionary.doesOreNameExist("oreSilicon")){
	    	OreDictionary.registerOre("oreSilicon", new ItemStack(oreGravel, 1, 11));
	    	OreDictionary.registerOre("oreSilicon", new ItemStack(oreSand, 1, 11));
			OreDictionary.registerOre("oreSilicon", new ItemStack(oreDust, 1, 11));
    	}
    	if(OreDictionary.doesOreNameExist("oreAmber")){
	    	OreDictionary.registerOre("oreAmber", new ItemStack(oreGravel, 1, 12));
	    	OreDictionary.registerOre("oreAmber", new ItemStack(oreSand, 1, 12));
			OreDictionary.registerOre("oreAmber", new ItemStack(oreDust, 1, 12));
    	}
    	if(OreDictionary.doesOreNameExist("oreCinnabar")){
	    	OreDictionary.registerOre("oreCinnabar", new ItemStack(oreGravel, 1, 13));
	    	OreDictionary.registerOre("oreCinnabar", new ItemStack(oreSand, 1, 13));
			OreDictionary.registerOre("oreCinnabar", new ItemStack(oreDust, 1, 13));
    	}
    	if(OreDictionary.doesOreNameExist("oreCertusQuartz")){
	    	OreDictionary.registerOre("oreCertusQuartz", new ItemStack(oreGravel, 1, 14));
	    	OreDictionary.registerOre("oreCertusQuartz", new ItemStack(oreSand, 1, 14));
			OreDictionary.registerOre("oreCertusQuartz", new ItemStack(oreDust, 1, 14));
    	}
    	if(OreDictionary.doesOreNameExist("oreSalt")){
	    	OreDictionary.registerOre("oreSalt", new ItemStack(oreGravel, 1, 15));
	    	OreDictionary.registerOre("oreSalt", new ItemStack(oreSand, 1, 15));
			OreDictionary.registerOre("oreSalt", new ItemStack(oreDust, 1, 15));
    	}
    	if(rawSilicon != null){
    		OreDictionary.registerOre("itemSilicon", new ItemStack(rawSilicon, 1, 2));
    	}
    	if(crystalCertusQuartz != null){
    		OreDictionary.registerOre("crystalCertusQuartz", crystalCertusQuartz);
    	}
	}
}