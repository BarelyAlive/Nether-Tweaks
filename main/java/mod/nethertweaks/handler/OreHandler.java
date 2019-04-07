package mod.nethertweaks.handler;

import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.interfaces.INames;
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
	
    public static final Item rawSilicon = Item.REGISTRY.getObject(new ResourceLocation("galacticraftcore", "basicItem.rawSilicon"));
    public static final Item crystalCertusQuartz = Item.REGISTRY.getObject(new ResourceLocation("appliedenergistics2", "crystalCertusQuartz"));
    public static final Item blizzpowder = Item.REGISTRY.getObject(new ResourceLocation("thermalfoundation", "material"));
    
    public static final Block OREGRAVEL = new CubeFalling(16, Material.GROUND, 2.0F, 0.4f, NetherTweaksMod.tabNTM, new ResourceLocation("nethertweaksmod", INames.OREGRAVEL));
    public static final Block ORESAND = new CubeFalling(16, Material.SAND, 2.0F, 0.4f, NetherTweaksMod.tabNTM, new ResourceLocation("nethertweaksmod", INames.ORESAND));
    public static final Block OREDUST = new CubeFalling(16, Material.SAND, 2.0F, 0.4f, NetherTweaksMod.tabNTM, new ResourceLocation("nethertweaksmod", INames.OREDUST));
    
    public static final Item oreMaterial = new CustomItem(null, 64, NetherTweaksMod.tabNTM, true, 51, new ResourceLocation("nethertweaksmod", OREMATERIAL));
    
    public static void init() {
    	registerOres();
    	registerNames();
    	registerRecipes();
    }
    
	private static void registerOres(){
		
		Registry.registerBlock(OREGRAVEL);
		Registry.registerBlock(ORESAND);
		Registry.registerBlock(OREDUST);
		
		
		//Nicht Erze
		if(blizzpowder != null) SieveHandler.register(Blocks.SNOW, blizzpowder, 1025, 20);
		
		//Erze
		if(OreDictionary.doesOreNameExist("oreCopper")){
			
            SieveHandler.register(BlockHandler.DUST, 0, oreMaterial, 32, 20);
			SieveHandler.register(Blocks.SAND, 0, oreMaterial, 16, 20);
			SieveHandler.register(Blocks.GRAVEL, 0, oreMaterial, 0, 20);
            
		}
		if(OreDictionary.doesOreNameExist("oreTin")){
			
            SieveHandler.register(BlockHandler.DUST, 0, oreMaterial, 33, 20);
			SieveHandler.register(Blocks.SAND, 0, oreMaterial, 17, 20);
			SieveHandler.register(Blocks.GRAVEL, 0, oreMaterial, 1, 20);
            
		}
		if(OreDictionary.doesOreNameExist("oreLead")){
			
            SieveHandler.register(BlockHandler.DUST, 0, oreMaterial, 34, 20);
			SieveHandler.register(Blocks.SAND, 0, oreMaterial, 18, 20);
			SieveHandler.register(Blocks.GRAVEL, 0, oreMaterial, 2, 20);
            
		}
		if(OreDictionary.doesOreNameExist("oreNickel")){
			
            SieveHandler.register(BlockHandler.DUST, 0, oreMaterial, 35, 20);
			SieveHandler.register(Blocks.SAND, 0, oreMaterial, 19, 20);
			SieveHandler.register(Blocks.GRAVEL, 0, oreMaterial, 3, 20);
            
		}
		if(OreDictionary.doesOreNameExist("orePlatinum")){
			
            SieveHandler.register(BlockHandler.DUST, 0, oreMaterial, 36, 20);
			SieveHandler.register(Blocks.SAND, 0, oreMaterial, 20, 20);
			SieveHandler.register(Blocks.GRAVEL, 0, oreMaterial, 4, 20);
            
		}
		if(OreDictionary.doesOreNameExist("oreSilver")){
			
            SieveHandler.register(BlockHandler.DUST, 0, oreMaterial, 37, 20);
			SieveHandler.register(Blocks.SAND, 0, oreMaterial, 21, 20);
			SieveHandler.register(Blocks.GRAVEL, 0, oreMaterial, 5, 20);
            
		}
		if(OreDictionary.doesOreNameExist("oreUranium")){

	        SieveHandler.register(BlockHandler.DUST, 0, oreMaterial, 38, 20);
			SieveHandler.register(Blocks.SAND, 0, oreMaterial, 22, 20);
			SieveHandler.register(Blocks.GRAVEL, 0, oreMaterial, 6, 20);
	        
		}
		if(OreDictionary.doesOreNameExist("oreOsmium")){

	        SieveHandler.register(BlockHandler.DUST, 0, oreMaterial, 39, 20);
			SieveHandler.register(Blocks.SAND, 0, oreMaterial, 23, 20);
			SieveHandler.register(Blocks.GRAVEL, 0, oreMaterial, 7, 20);
	        
		}if(OreDictionary.doesOreNameExist("oreDraconium")){

			//Draconic Evo
            SieveHandler.register(BlockHandler.DUST, 0, oreMaterial, 40, 20);
			SieveHandler.register(Blocks.SAND, 0, oreMaterial, 24, 20);
			SieveHandler.register(Blocks.GRAVEL, 0, oreMaterial, 8, 20);
			
		}
		if(OreDictionary.doesOreNameExist("oreSulfur")){
			//Sulfur
            SieveHandler.register(BlockHandler.DUST, 0, oreMaterial, 41, 20);
			SieveHandler.register(Blocks.SAND, 0, oreMaterial, 25, 20);
			SieveHandler.register(Blocks.GRAVEL, 0, oreMaterial, 9, 20);
            
		}
		if(OreDictionary.doesOreNameExist("oreAluminum")){
			//Aluminum
            SieveHandler.register(BlockHandler.DUST, 0, oreMaterial, 42, 20);
			SieveHandler.register(Blocks.SAND, 0, oreMaterial, 26, 20);
			SieveHandler.register(Blocks.GRAVEL, 0, oreMaterial, 10, 20);
		}
		if(OreDictionary.doesOreNameExist("oreSilicon")){
			
            SieveHandler.register(BlockHandler.DUST, 0, oreMaterial, 43, 20);
			SieveHandler.register(Blocks.SAND, 0, oreMaterial, 27, 20);
			SieveHandler.register(Blocks.GRAVEL, 0, oreMaterial, 11, 20);
		}
		if(OreDictionary.doesOreNameExist("oreAmber")){
			//Amber
            SieveHandler.register(BlockHandler.DUST, 0, oreMaterial, 44, 20);
			SieveHandler.register(Blocks.SAND, 0, oreMaterial, 28, 20);
			SieveHandler.register(Blocks.GRAVEL, 0, oreMaterial, 12, 20);
		}
		if(OreDictionary.doesOreNameExist("oreCinnabar")){
			//Cinnabar
			SieveHandler.register(BlockHandler.DUST, 0, oreMaterial, 45, 20);
			SieveHandler.register(Blocks.SAND, 0, oreMaterial, 29, 20);
			SieveHandler.register(Blocks.GRAVEL, 0, oreMaterial, 13, 20);
		}
		if(OreDictionary.doesOreNameExist("oreCertusQuartz")){
			//AppEng2
            SieveHandler.register(BlockHandler.DUST, 0, oreMaterial, 46, 20);
			SieveHandler.register(Blocks.SAND, 0, oreMaterial, 30, 20);
			SieveHandler.register(Blocks.GRAVEL, 0, oreMaterial, 14, 20);
            
		}
		
		//Salt
        SieveHandler.register(BlockHandler.DUST, 0, oreMaterial, 47, 20);
		SieveHandler.register(Blocks.SAND, 0, oreMaterial, 31, 20);
		SieveHandler.register(Blocks.GRAVEL, 0, oreMaterial, 15, 20);
		
	}
	
	private static void registerRecipes(){
		//ore blocks
		
		if(OreDictionary.doesOreNameExist("oreCopper")){
			
			GameRegistry.addSmelting(new ItemStack(OREGRAVEL, 1, 0), OreDictionary.getOres("ingotCopper").get(0), 2.0F);
            GameRegistry.addSmelting(new ItemStack(ORESAND, 1, 0), OreDictionary.getOres("ingotCopper").get(0), 2.0F);
            GameRegistry.addSmelting(new ItemStack(OREDUST, 1, 0), OreDictionary.getOres("ingotCopper").get(0), 2.0F);
            
		}
		if(OreDictionary.doesOreNameExist("oreTin")){
			
            GameRegistry.addSmelting(new ItemStack(OREGRAVEL, 1, 1), OreDictionary.getOres("ingotTin").get(0), 2.0F);
            GameRegistry.addSmelting(new ItemStack(ORESAND, 1, 1), OreDictionary.getOres("ingotTin").get(0), 2.0F);
            GameRegistry.addSmelting(new ItemStack(OREDUST, 1, 1), OreDictionary.getOres("ingotTin").get(0), 2.0F);
            
		}
		if(OreDictionary.doesOreNameExist("oreLead")){

            GameRegistry.addSmelting(new ItemStack(OREGRAVEL, 1, 2), OreDictionary.getOres("ingotLead").get(0), 2.0F);
            GameRegistry.addSmelting(new ItemStack(ORESAND, 1, 2), OreDictionary.getOres("ingotLead").get(0), 2.0F);
            GameRegistry.addSmelting(new ItemStack(OREDUST, 1, 2), OreDictionary.getOres("ingotLead").get(0), 2.0F);
            
		}
		if(OreDictionary.doesOreNameExist("oreNickel")){

            GameRegistry.addSmelting(new ItemStack(OREGRAVEL , 1, 3), OreDictionary.getOres("ingotNickel").get(0), 2.0F);
            GameRegistry.addSmelting(new ItemStack(ORESAND, 1, 3), OreDictionary.getOres("ingotNickel").get(0), 2.0F);
            GameRegistry.addSmelting(new ItemStack(OREDUST, 1, 3), OreDictionary.getOres("ingotNickel").get(0), 2.0F);
		}
		if(OreDictionary.doesOreNameExist("orePlatinum")){

            GameRegistry.addSmelting(new ItemStack(OREGRAVEL, 1, 4), OreDictionary.getOres("ingotPlatinum").get(0), 2.0F);
            GameRegistry.addSmelting(new ItemStack(ORESAND, 1, 4), OreDictionary.getOres("ingotPlatinum").get(0), 2.0F);
            GameRegistry.addSmelting(new ItemStack(OREDUST, 1, 4), OreDictionary.getOres("ingotPlatinum").get(0), 2.0F);
            
		}
		if(OreDictionary.doesOreNameExist("oreSilver")){

			GameRegistry.addSmelting(new ItemStack(OREGRAVEL, 1, 5), OreDictionary.getOres("ingotSilver").get(0), 2.0F);
            GameRegistry.addSmelting(new ItemStack(ORESAND, 1, 5), OreDictionary.getOres("ingotSilver").get(0), 2.0F);
            GameRegistry.addSmelting(new ItemStack(OREDUST, 1, 5), OreDictionary.getOres("ingotSilver").get(0), 2.0F);
            
            
		}
		if(OreDictionary.doesOreNameExist("oreUranium")){
			
	        GameRegistry.addSmelting(new ItemStack(OREGRAVEL, 1, 7), OreDictionary.getOres("ingotUranium").get(0), 2.0F);
	        GameRegistry.addSmelting(new ItemStack(ORESAND, 1, 7), OreDictionary.getOres("ingotUranium").get(0), 2.0F);
	        GameRegistry.addSmelting(new ItemStack(OREDUST, 1, 7), OreDictionary.getOres("ingotUranium").get(0), 2.0F);
	        
	        
		}
		if(OreDictionary.doesOreNameExist("oreDraconium")){
			
            GameRegistry.addSmelting(new ItemStack(OREGRAVEL, 1, 8), OreDictionary.getOres("ingotDraconium").get(0), 2.0F);
            GameRegistry.addSmelting(new ItemStack(ORESAND, 1, 8), OreDictionary.getOres("ingotDraconium").get(0), 2.0F);
            GameRegistry.addSmelting(new ItemStack(OREDUST, 1, 8), OreDictionary.getOres("ingotDraconium").get(0), 2.0F);
            
			
		}
		if(OreDictionary.doesOreNameExist("oreSulfur")){

            GameRegistry.addSmelting(new ItemStack(OREGRAVEL, 1, 9), OreDictionary.getOres("dustSulfur").get(0), 2.0F);
            GameRegistry.addSmelting(new ItemStack(ORESAND, 1, 9), OreDictionary.getOres("dustSulfur").get(0), 2.0F);
            GameRegistry.addSmelting(new ItemStack(OREDUST, 1, 9), OreDictionary.getOres("dustSulfur").get(0), 2.0F);
            
            
		}
		if(OreDictionary.doesOreNameExist("oreAluminum")){
			
            GameRegistry.addSmelting(new ItemStack(OREGRAVEL, 1, 10), OreDictionary.getOres("ingotAluminum").get(0), 2.0F);
            GameRegistry.addSmelting(new ItemStack(ORESAND, 1, 10), OreDictionary.getOres("ingotAluminum").get(0), 2.0F);
            GameRegistry.addSmelting(new ItemStack(OREDUST, 1, 10), OreDictionary.getOres("ingotAluminum").get(0), 2.0F);
            
		}
		if(OreDictionary.doesOreNameExist("oreSilicon")){
			
            GameRegistry.addSmelting(new ItemStack(OREGRAVEL, 1, 11), OreDictionary.getOres("itemSilicon").get(0), 2.0F);
            GameRegistry.addSmelting(new ItemStack(ORESAND, 1, 11), OreDictionary.getOres("itemSilicon").get(0), 2.0F);
            GameRegistry.addSmelting(new ItemStack(OREDUST, 1, 11), OreDictionary.getOres("itemSilicon").get(0), 2.0F);
            
		}
		if(OreDictionary.doesOreNameExist("oreAmber")){

            GameRegistry.addSmelting(new ItemStack(OREGRAVEL, 1, 12), OreDictionary.getOres("gemAmber").get(0), 2.0F);
            GameRegistry.addSmelting(new ItemStack(ORESAND, 1, 12), OreDictionary.getOres("gemAmber").get(0), 2.0F);
            GameRegistry.addSmelting(new ItemStack(OREDUST, 1, 12), OreDictionary.getOres("gemAmber").get(0), 2.0F);
            
		}
		if(OreDictionary.doesOreNameExist("oreCinnabar")){

            GameRegistry.addSmelting(new ItemStack(OREGRAVEL, 1, 13), OreDictionary.getOres("crystalCinnabar").get(0), 2.0F);
            GameRegistry.addSmelting(new ItemStack(ORESAND, 1, 13), OreDictionary.getOres("crystalCinnabar").get(0), 2.0F);
            GameRegistry.addSmelting(new ItemStack(OREDUST, 1, 13), OreDictionary.getOres("crystalCinnabar").get(0), 2.0F);
            
            
		}
		if(OreDictionary.doesOreNameExist("oreCertusQuartz")){

            GameRegistry.addSmelting(new ItemStack(OREGRAVEL, 1, 14), OreDictionary.getOres("crystalCertusQuartz").get(0), 2.0F);
            GameRegistry.addSmelting(new ItemStack(ORESAND, 1, 14), OreDictionary.getOres("crystalCertusQuartz").get(0), 2.0F);
            GameRegistry.addSmelting(new ItemStack(OREDUST, 1, 14), OreDictionary.getOres("crystalCertusQuartz").get(0), 2.0F);
                    
		}
		
		//Salt
		
		GameRegistry.addSmelting(new ItemStack(OREGRAVEL, 1, 15), new ItemStack(ItemHandler.ITEMBASE, 1, 16), 2.0F);
        GameRegistry.addSmelting(new ItemStack(ORESAND, 1, 15), new ItemStack(ItemHandler.ITEMBASE, 1, 16), 2.0F);
        GameRegistry.addSmelting(new ItemStack(OREDUST, 1, 15), new ItemStack(ItemHandler.ITEMBASE, 1, 16), 2.0F);
		
	}
	
	private static void registerNames(){
    	
		if(OreDictionary.doesOreNameExist("oreCopper")){
	    	OreDictionary.registerOre("oreCopper", new ItemStack(OREGRAVEL, 1, 0));
	    	OreDictionary.registerOre("oreCopper", new ItemStack(ORESAND, 1, 0));
	    	OreDictionary.registerOre("oreCopper", new ItemStack(OREDUST, 1, 0));
		}
		if(OreDictionary.doesOreNameExist("oreTin")){
	    	OreDictionary.registerOre("oreTin", new ItemStack(OREGRAVEL, 1, 1));
	    	OreDictionary.registerOre("oreTin", new ItemStack(ORESAND, 1, 1));
	    	OreDictionary.registerOre("oreTin", new ItemStack(OREDUST, 1, 1));
    	}
    	if(OreDictionary.doesOreNameExist("oreLead")){
	    	OreDictionary.registerOre("oreLead", new ItemStack(OREGRAVEL, 1, 2));
	    	OreDictionary.registerOre("oreLead", new ItemStack(ORESAND, 1, 2));
	    	OreDictionary.registerOre("oreLead", new ItemStack(OREDUST, 1, 2));
    	}
    	if(OreDictionary.doesOreNameExist("oreNickel")){
	    	OreDictionary.registerOre("oreNickel", new ItemStack(OREGRAVEL , 1, 3));
	    	OreDictionary.registerOre("oreNickel", new ItemStack(ORESAND, 1, 3));
	    	OreDictionary.registerOre("oreNickel", new ItemStack(OREDUST, 1, 3));
    	}
    	if(OreDictionary.doesOreNameExist("orePlatinum")){
	    	OreDictionary.registerOre("orePlatinum", new ItemStack(OREGRAVEL, 1, 4));
	    	OreDictionary.registerOre("orePlatinum", new ItemStack(ORESAND, 1, 4));
	    	OreDictionary.registerOre("orePlatinum", new ItemStack(OREDUST, 1, 4));
    	}
    	if(OreDictionary.doesOreNameExist("oreSilver")){
	    	OreDictionary.registerOre("oreSilver", new ItemStack(OREGRAVEL, 1, 5));
	    	OreDictionary.registerOre("oreSilver", new ItemStack(ORESAND, 1, 5));
	    	OreDictionary.registerOre("oreSilver", new ItemStack(OREDUST, 1, 5));
    	}
    	if(OreDictionary.doesOreNameExist("oreUranium")){
	    	OreDictionary.registerOre("oreUranium", new ItemStack(OREGRAVEL, 1, 6));
	    	OreDictionary.registerOre("oreUranium", new ItemStack(ORESAND, 1, 6));
	    	OreDictionary.registerOre("oreUranium", new ItemStack(OREDUST, 1, 6));
    	}
    	if(OreDictionary.doesOreNameExist("oreOsmium")){
			OreDictionary.registerOre("oreOsmium", new ItemStack(OREGRAVEL, 1, 7));
			OreDictionary.registerOre("oreOsmium", new ItemStack(ORESAND, 1, 7));
			OreDictionary.registerOre("oreOsmium", new ItemStack(OREDUST, 1, 7));
    	}
		if(OreDictionary.doesOreNameExist("oreDraconium")){
	    	OreDictionary.registerOre("oreDraconium", new ItemStack(OREGRAVEL, 1, 8));
	    	OreDictionary.registerOre("oreDraconium", new ItemStack(ORESAND, 1, 8));
			OreDictionary.registerOre("oreDraconium", new ItemStack(OREDUST, 1, 8));
		}
    	if(OreDictionary.doesOreNameExist("oreSulfur")){
	    	OreDictionary.registerOre("oreSulfur", new ItemStack(OREGRAVEL, 1, 9));
	    	OreDictionary.registerOre("oreSulfur", new ItemStack(ORESAND, 1, 9));
			OreDictionary.registerOre("oreSulfur", new ItemStack(OREDUST, 1, 9));
    	}
    	if(OreDictionary.doesOreNameExist("oreAluminum")){
	    	OreDictionary.registerOre("oreAluminum", new ItemStack(OREGRAVEL, 1, 10));
	    	OreDictionary.registerOre("oreAluminum", new ItemStack(ORESAND, 1, 10));
			OreDictionary.registerOre("oreAluminum", new ItemStack(OREDUST, 1, 10));
    	}
    	if(OreDictionary.doesOreNameExist("oreSilicon")){
	    	OreDictionary.registerOre("oreSilicon", new ItemStack(OREGRAVEL, 1, 11));
	    	OreDictionary.registerOre("oreSilicon", new ItemStack(ORESAND, 1, 11));
			OreDictionary.registerOre("oreSilicon", new ItemStack(OREDUST, 1, 11));
    	}
    	if(OreDictionary.doesOreNameExist("oreAmber")){
	    	OreDictionary.registerOre("oreAmber", new ItemStack(OREGRAVEL, 1, 12));
	    	OreDictionary.registerOre("oreAmber", new ItemStack(ORESAND, 1, 12));
			OreDictionary.registerOre("oreAmber", new ItemStack(OREDUST, 1, 12));
    	}
    	if(OreDictionary.doesOreNameExist("oreCinnabar")){
	    	OreDictionary.registerOre("oreCinnabar", new ItemStack(OREGRAVEL, 1, 13));
	    	OreDictionary.registerOre("oreCinnabar", new ItemStack(ORESAND, 1, 13));
			OreDictionary.registerOre("oreCinnabar", new ItemStack(OREDUST, 1, 13));
    	}
    	if(OreDictionary.doesOreNameExist("oreCertusQuartz")){
	    	OreDictionary.registerOre("oreCertusQuartz", new ItemStack(OREGRAVEL, 1, 14));
	    	OreDictionary.registerOre("oreCertusQuartz", new ItemStack(ORESAND, 1, 14));
			OreDictionary.registerOre("oreCertusQuartz", new ItemStack(OREDUST, 1, 14));
    	}
    	if(OreDictionary.doesOreNameExist("oreSalt")){
	    	OreDictionary.registerOre("oreSalt", new ItemStack(OREGRAVEL, 1, 15));
	    	OreDictionary.registerOre("oreSalt", new ItemStack(ORESAND, 1, 15));
			OreDictionary.registerOre("oreSalt", new ItemStack(OREDUST, 1, 15));
    	}
    	if(rawSilicon != null){
    		OreDictionary.registerOre("itemSilicon", new ItemStack(rawSilicon, 1, 2));
    	}
    	if(crystalCertusQuartz != null){
    		OreDictionary.registerOre("crystalCertusQuartz", crystalCertusQuartz);
    	}
	}
}
