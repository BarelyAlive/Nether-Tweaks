package mod.nethertweaks;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import io.netty.channel.nio.NioTask;
import mod.nethertweaks.blocks.NTMBlocks;
import mod.nethertweaks.items.NTMItems;


public class RecipeLoader {

	public static void loadRecipes(){
		//Crafting Rezepte
		
		if(Config.endTeleport){
		
		/*Frame*/			GameRegistry.addShapedRecipe(new ItemStack(NTMItems.itemBase, 1, 8), "XYX", "Y Y", "XYX", 'X', Blocks.OBSIDIAN, 'Y', Items.ENDER_PEARL);
		/*EndPortal*/		GameRegistry.addShapedRecipe(new ItemStack(NTMItems.itemBase, 1, 4), "XXX", "XYX", "XXX", 'X', Blocks.END_STONE, 'Y', new ItemStack(NTMItems.itemBase, 1, 8));
		/*End portal*/		GameRegistry.addShapedRecipe(new ItemStack(NTMBlocks.blockEndTeleport), "XXX", "XYX", "XXX", 'X', new ItemStack(NTMItems.itemBase, 1, 4), 'Y', new ItemStack(NTMItems.itemBase, 1, 8));
		/*PortalCore*/		GameRegistry.addShapedRecipe(new ItemStack(NTMItems.itemBase, 1, 7), "XYX", "YZY", "XYX", 'X', new ItemStack(NTMItems.itemBase, 1, 5), 'Y', Items.GOLD_INGOT, 'Z', Items.NETHER_STAR);
		}
		
		/*BlackDye*/		GameRegistry.addShapelessRecipe(new ItemStack(NTMItems.itemBase, 16, 6), Items.POTIONITEM, Items.COAL);
		/*Salz-Block*/		GameRegistry.addShapedRecipe(new ItemStack(NTMBlocks.blockBasic, 1, 3), "XX", "XX", 'X', new ItemStack(NTMItems.itemBase, 1, 16));
		/*Reverse*/			GameRegistry.addShapelessRecipe(new ItemStack(NTMItems.itemBase, 3, 16), new ItemStack(NTMBlocks.blockBasic, 1, 3));
		/*Torch*/			GameRegistry.addShapedRecipe(new ItemStack(Blocks.TORCH, 64), "X", "Y", 'X', new ItemStack(NTMItems.itemBase, 1, 10), 'Y', Items.STICK);
		if(Config.condenser)GameRegistry.addShapedRecipe(new ItemStack(NTMBlocks.blockCondenser,1), "XXX", "XYX", "XXX", 'X', Blocks.NETHERRACK, 'Y', Items.BUCKET);
		if(Config.iwantvanillaWater == true){
		/*Condenser*/		GameRegistry.addShapedRecipe(new ItemStack(NTMBlocks.blockCondenser), "XXX", "XYX", "XXX", 'X', Blocks.NETHERRACK, 'Y', BucketLoader.itemBucketNTM);
		}
		/*NbrickPAxe*/		GameRegistry.addShapedRecipe(new ItemStack(NTMItems.itemPickaxeNetherbrick), "XXX", " Y ", " Y ", 'X', Items.NETHERBRICK, 'Y', Items.BONE);
		if(Config.netherrackFurnace)
							GameRegistry.addShapedRecipe(new ItemStack(NTMBlocks.blockNetherrackFurnace), "XXX", "X X", "XXX", 'X', Blocks.NETHERRACK);
		/*Dirt*/			GameRegistry.addShapedRecipe(new ItemStack(Blocks.DIRT), "XX", "XX", 'X', new ItemStack(NTMItems.itemBase, 1, 15));
		if(Config.disableStairwaytoHeaven == false){
		}
		if(Config.bonfire == false){
		/*Bonfire*/			GameRegistry.addShapedRecipe(new ItemStack(NTMBlocks.blockBonfire, 1), " YZ", "XXX",'X', Blocks.NETHERRACK, 'Y', Items.IRON_SWORD, 'Z', Items.FLINT_AND_STEEL);
		/*Bonfire*/			GameRegistry.addShapedRecipe(new ItemStack(NTMBlocks.blockBonfire, 1), " YZ", "XXX",'X', Blocks.NETHERRACK, 'Y', Items.IRON_SWORD, 'Z', NTMItems.itemFlintAndBlaze);
		}
		/*Flint and Blaze*/	GameRegistry.addShapedRecipe(new ItemStack(NTMItems.itemFlintAndBlaze), "X", "Y", 'X', Items.BLAZE_ROD, 'Y', Items.FLINT);
		if(Config.iwantvanillaWater == true){
		/*BucketNTM*/		GameRegistry.addShapedRecipe(new ItemStack(BucketLoader.itemBucketNTM), "X X", " X ", 'X', Items.IRON_INGOT);
		/*BucketNTM*/		GameRegistry.addShapelessRecipe(new ItemStack(BucketLoader.itemBucketNTM), Items.BUCKET);
		/*Reverse*/			GameRegistry.addShapelessRecipe(new ItemStack(Items.BUCKET), BucketLoader.itemBucketNTM);
		/*BucketWNTM*/		GameRegistry.addShapelessRecipe(new ItemStack(BucketLoader.itemBucketNTMWater), Items.WATER_BUCKET);
		/*Reverse*/			GameRegistry.addShapelessRecipe(new ItemStack(Items.WATER_BUCKET), BucketLoader.itemBucketNTMWater);
		/*BucketLNTM*/		GameRegistry.addShapelessRecipe(new ItemStack(BucketLoader.itemBucketNTMLava), Items.LAVA_BUCKET);
		/*Reverse*/			GameRegistry.addShapelessRecipe(new ItemStack(Items.LAVA_BUCKET), BucketLoader.itemBucketNTMLava);
		}
		/*ForWoodPl*/		GameRegistry.addShapelessRecipe(new ItemStack(NTMBlocks.blockNetherWood, 4), NTMBlocks.blockNetherLog);
		/*SancCrystal*/		GameRegistry.addShapedRecipe(new ItemStack(NTMItems.itemSanctuaryCrystal), "XYX", "YZY", "XYX", 'X', Items.GLOWSTONE_DUST, 'Y', Items.DIAMOND, 'Z', Blocks.GOLD_BLOCK);
		/*EnderCrystal*/	GameRegistry.addShapedRecipe(new ItemStack(NTMItems.itemBase, 1, 5), "XYX", "YZY", "XYX", 'X', Items.ENDER_EYE, 'Y', Items.DIAMOND, 'Z', Blocks.OBSIDIAN);
		/*SieveWeb*/		GameRegistry.addShapedRecipe(new ItemStack(NTMItems.itemBase, 1, 17), "XXX", "XXX", "XXX", 'X', NTMBlocks.blockMeanVine);
		
		/*IronDust*/		GameRegistry.addShapedRecipe(new ItemStack(NTMBlocks.oreIronDust), "XX", "XX", 'X', new ItemStack(NTMItems.itemBase, 1, 18));
		/*GoldDust*/		GameRegistry.addShapedRecipe(new ItemStack(NTMBlocks.oreGoldDust), "XX", "XX", 'X', new ItemStack(NTMItems.itemBase, 1, 9));
		/*IronSand*/		GameRegistry.addShapedRecipe(new ItemStack(NTMBlocks.oreIronSand), "XX", "XX", 'X', new ItemStack(NTMItems.itemBase, 1, 3));
		/*GoldSand*/		GameRegistry.addShapedRecipe(new ItemStack(NTMBlocks.oreGoldSand), "XX", "XX", 'X', new ItemStack(NTMItems.itemBase, 1, 2));

		/*IronGrav*/		GameRegistry.addShapedRecipe(new ItemStack(NTMBlocks.oreIronGravel), "XX", "XX", 'X', new ItemStack(NTMItems.itemBase, 1, 1));
		/*GoldGrav*/		GameRegistry.addShapedRecipe(new ItemStack(NTMBlocks.oreGoldGravel), "XX", "XX", 'X', new ItemStack(NTMItems.itemBase, 1, 0));
		
		//Schmelz-Rezepte
		
		/*EssFleisch*/		GameRegistry.addSmelting(Items.ROTTEN_FLESH, (new ItemStack(NTMItems.itemCookedJerky)), 1.0F);
		/*Charcoal*/		GameRegistry.addSmelting(NTMBlocks.blockNetherLog, (new ItemStack(Items.COAL, 1, 1)), 0.0F);
		/*LightCrystal*/	GameRegistry.addSmelting(new ItemStack(NTMItems.itemBase, 1, 12), (new ItemStack(NTMItems.itemLightCrystal)), 2.0F);
		/*Hellfayah*/		GameRegistry.addSmelting(new ItemStack(NTMBlocks.blockBasic, 1, 0), (new ItemStack (NTMItems.itemBase, 1, 10)), 2.0F);
		/*IronIngot*/		GameRegistry.addSmelting(NTMBlocks.oreIronGravel, (new ItemStack(Items.IRON_INGOT)), 2.0F);
		/*GoldIngot*/		GameRegistry.addSmelting(NTMBlocks.oreGoldGravel, (new ItemStack(Items.GOLD_INGOT)), 2.0F);
		/*IronIngot*/		GameRegistry.addSmelting(NTMBlocks.oreIronGravel, (new ItemStack(Items.IRON_INGOT)), 2.0F);
		/*GoldIngot*/		GameRegistry.addSmelting(NTMBlocks.oreGoldGravel, (new ItemStack(Items.GOLD_INGOT)), 2.0F);
		/*IronIngot*/		GameRegistry.addSmelting(NTMBlocks.oreIronDust, (new ItemStack(Items.IRON_INGOT)), 2.0F);
		/*GoldIngot*/		GameRegistry.addSmelting(NTMBlocks.oreGoldDust, (new ItemStack(Items.GOLD_INGOT)), 2.0F);
		/*IronIngot*/		GameRegistry.addSmelting(NTMBlocks.oreIronSand, (new ItemStack(Items.IRON_INGOT)), 2.0F);
		/*GoldIngot*/		GameRegistry.addSmelting(NTMBlocks.oreGoldSand, (new ItemStack(Items.GOLD_INGOT)), 2.0F);
		/*IronIngot*/		GameRegistry.addSmelting(NTMBlocks.oreIronGravel, (new ItemStack(Items.IRON_INGOT)), 2.0F);
		/*GoldIngot*/		GameRegistry.addSmelting(NTMBlocks.oreGoldGravel, (new ItemStack(Items.GOLD_INGOT)), 2.0F);
	}
	
	//Ore-Dictionary
	public static void oreRegistration()
    {
    	OreDictionary.registerOre("itemSalt", new ItemStack(NTMItems.itemBase, 1, 16));
    	OreDictionary.registerOre("dustObsidian", new ItemStack(NTMItems.itemBase, 1, 14));
    	OreDictionary.registerOre("ingotSteel", new ItemStack(NTMItems.itemBase, 1, 11));
    	OreDictionary.registerOre("itemHellfayah", new ItemStack(NTMItems.itemBase, 1, 10));
    	OreDictionary.registerOre("oreHellfayah", new ItemStack(NTMBlocks.blockBasic, 1, 0));
    	OreDictionary.registerOre("blockHellfayah", new ItemStack (NTMBlocks.blockBasic, 1, 2));
    	OreDictionary.registerOre("plankWood", new ItemStack(NTMBlocks.blockNetherWood));
    	OreDictionary.registerOre("logWood", new ItemStack(NTMBlocks.blockNetherLog));
    	OreDictionary.registerOre("treeSapling", new ItemStack(NTMBlocks.blockNetherSapling));
    	OreDictionary.registerOre("treeLeaves", new ItemStack(NTMBlocks.blockNetherLeav));
    	OreDictionary.registerOre("dustSalt", new ItemStack(NTMItems.itemBase, 1, 16));
    	OreDictionary.registerOre("oreGold", NTMBlocks.oreGoldDust);
    	OreDictionary.registerOre("oreGold", NTMBlocks.oreGoldGravel);
    	OreDictionary.registerOre("oreGold", NTMBlocks.oreGoldSand);
    	OreDictionary.registerOre("oreIron", NTMBlocks.oreIronDust);
    	OreDictionary.registerOre("oreIron", NTMBlocks.oreIronGravel);
    	OreDictionary.registerOre("oreIron", NTMBlocks.oreIronSand);
    }
   
    public static void addOreRecipes()
    {
    	GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(NTMBlocks.blockBasic, 1, 3), "XX", "XX", 'X', "dustSalt"));
    	GameRegistry.addRecipe(new ShapedOreRecipe(NTMBlocks.blockSansidian, " X ", "XYX", " X ", 'X', "dustObsidian", 'Y', Blocks.SAND));
		
    	if(Config.barrel){
		GameRegistry.addRecipe(new ShapedOreRecipe(NTMBlocks.blockBarrel, "X X", "X X", "XXX", 'X', "plankWood"));
    	GameRegistry.addRecipe(new ShapedOreRecipe(NTMBlocks.blockBarrelStone, "X X", "X X", "XXX", 'X', "cobblestone"));
		GameRegistry.addRecipe(new ShapedOreRecipe(NTMBlocks.blockBarrelStone, "X X", "X X", "XXX", 'X', "stone"));
    	}
    	
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(NTMItems.itemBase, 1, 16), "waterBucket"));
		
		if(Config.disableHammers){
		GameRegistry.addRecipe(new ShapedOreRecipe(NTMItems.itemHammerWood, " X ", "XY ", "  Y", 'X', "plankWood", 'Y', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(NTMItems.itemHammerStone, " X ", "XY ", "  Y", 'X', "cobblestone", 'Y', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(NTMItems.itemHammerIron, " X ", "XY ", "  Y", 'X', Items.IRON_INGOT, 'Y', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(NTMItems.itemHammerDiamond, " X ", "XY ", "  Y", 'X', Items.DIAMOND, 'Y', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(NTMItems.itemHammerGold, " X ", "XY ", "  Y", 'X', Items.GOLD_INGOT, 'Y', "stickWood"));
		}
		GameRegistry.addRecipe(new ShapedOreRecipe(NTMItems.itemPickaxeNetherrack, "XXX", " Y ", " Y ", 'X', Blocks.NETHERRACK, 'Y', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(NTMItems.itemBase, 1, 12), "XYX", "XZX", "XYX", 'X', Items.GLOWSTONE_DUST, 'Y', "dustSalt", 'Z', Blocks.SAND));
		if(Config.sieve)
		GameRegistry.addRecipe(new ShapedOreRecipe(NTMBlocks.blockSieve, "XYX", "Z Z", "Z Z", 'X', "plankWood", 'Y', new ItemStack(NTMItems.itemBase, 1, 17), 'Z', "stickWood"));
		/*HellfayahBLock*/	GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(NTMBlocks.blockBasic, 1, 2), "itemHellfayah", "itemHellfayah", "itemHellfayah", "itemHellfayah", "itemHellfayah", "itemHellfayah", "itemHellfayah", "itemHellfayah", "itemHellfayah"));
		/*Reverse*/			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(NTMItems.itemBase, 9, 10), "blockHellfayah"));		
    }
}
