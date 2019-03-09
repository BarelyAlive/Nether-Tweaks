package mod.nethertweaks.handler;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.UniversalBucket;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import io.netty.channel.nio.NioTask;
import mod.nethertweaks.BucketLoader;
import mod.nethertweaks.Config;
import mod.nethertweaks.Konstanten;
import mod.nethertweaks.blocks.NTMBlocks;
import mod.nethertweaks.items.NTMItems;


public class RecipeHandler {

	private final static char x = 'X';
	private final static char y = 'Y';
	private final static char z = 'Z';
	
	public static void loadRecipes(){
		//Crafting Rezepte
		
		if(Config.endTeleport){
			GameRegistry.addShapedRecipe(Konstanten.ENDERINFUSEDFRAME, "XYX", "Y Y", "XYX", x, Blocks.OBSIDIAN, y, Items.ENDER_PEARL);
			GameRegistry.addShapedRecipe(Konstanten.ENDBOX, "XXX", "XYX", "XXX", x, Blocks.END_STONE, y, Konstanten.ENDERINFUSEDFRAME);
			GameRegistry.addShapedRecipe(new ItemStack(NTMBlocks.blockEndTeleport), "XXX", "XYX", "XXX", x, Konstanten.ENDBOX, y, Konstanten.PORTALCORE);
			GameRegistry.addShapedRecipe(Konstanten.PORTALCORE, "XYX", "YZY", "XYX", x, new ItemStack(NTMItems.itemBase, 1, 5), y, Items.GOLD_INGOT, z, Items.NETHER_STAR);
		}
		
		GameRegistry.addShapelessRecipe(Konstanten.BLACKDYE, Items.POTIONITEM, Items.COAL);
		GameRegistry.addShapedRecipe(Konstanten.SALTBLOCK, "XX", "XX", x, Konstanten.SALT);
		GameRegistry.addShapelessRecipe(new ItemStack(Konstanten.SALT.getItem(), 4), Konstanten.SALTBLOCK);
		GameRegistry.addShapedRecipe(new ItemStack(Blocks.TORCH, 64), "X", "Y", x, Konstanten.HELLFAYAH, y, Items.STICK);
		if(Config.condenser){
			GameRegistry.addShapedRecipe(new ItemStack(NTMBlocks.blockCondenser, 1), "XXX", "XYX", "XXX", x, Blocks.NETHERRACK, y, Items.BUCKET);
			GameRegistry.addShapedRecipe(new ItemStack(NTMBlocks.blockCondenser, 1), "XXX", "XYX", "XXX", x, Blocks.NETHERRACK, y, BucketLoader.bucketStone);
			GameRegistry.addShapedRecipe(new ItemStack(NTMBlocks.blockCondenser, 1), "XXX", "XYX", "XXX", x, Blocks.NETHERRACK, y, BucketLoader.bucketWood);
		}
		GameRegistry.addShapedRecipe(new ItemStack(NTMItems.itemPickaxeNetherbrick), "XXX", " Y ", " Y ", x, Items.NETHERBRICK, y, Items.BONE);
		if(Config.netherrackFurnace)
			GameRegistry.addShapedRecipe(new ItemStack(NTMBlocks.blockNetherrackFurnace), "XXX", "X X", "XXX", x, Blocks.NETHERRACK);
		if(!Config.disableStairwaytoHeaven){
		}
		if(Config.bonfire){
			GameRegistry.addShapedRecipe(new ItemStack(NTMBlocks.bonfire, 1), " YZ", "XXX",x, Blocks.NETHERRACK, y, Items.IRON_SWORD, z, Items.FLINT_AND_STEEL);
			GameRegistry.addShapedRecipe(new ItemStack(NTMBlocks.bonfire, 1), " YZ", "XXX",x, Blocks.NETHERRACK, y, Items.IRON_SWORD, z, NTMItems.itemFlintAndBlaze);
		}
		GameRegistry.addShapedRecipe(new ItemStack(NTMItems.itemFlintAndBlaze), "X", "Y", x, Items.BLAZE_ROD, y, Items.FLINT);
		GameRegistry.addShapelessRecipe(new ItemStack(NTMBlocks.netherWood, 4), NTMBlocks.netherLog);
		GameRegistry.addShapedRecipe(Konstanten.SIEVEWEB, "XXX", "XXX", "XXX", x, NTMBlocks.blockMeanVine);
		
		GameRegistry.addShapedRecipe(new ItemStack(NTMBlocks.oreIronDust), "XX", "XX", x, new ItemStack(NTMItems.itemBase, 1, 16));
		GameRegistry.addShapedRecipe(new ItemStack(NTMBlocks.oreGoldDust), "XX", "XX", x, new ItemStack(NTMItems.itemBase, 1, 9));
		GameRegistry.addShapedRecipe(new ItemStack(NTMBlocks.oreIronSand), "XX", "XX", x, new ItemStack(NTMItems.itemBase, 1, 3));
		GameRegistry.addShapedRecipe(new ItemStack(NTMBlocks.oreGoldSand), "XX", "XX", x, new ItemStack(NTMItems.itemBase, 1, 2));

		GameRegistry.addShapedRecipe(new ItemStack(NTMBlocks.oreIronGravel), "XX", "XX", x, new ItemStack(NTMItems.itemBase, 1, 1));
		GameRegistry.addShapedRecipe(new ItemStack(NTMBlocks.oreGoldGravel), "XX", "XX", x, new ItemStack(NTMItems.itemBase, 1, 0));
		GameRegistry.addShapedRecipe(new ItemStack(NTMBlocks.itemDoorNTMObsidian), "XX", "XX", "XX", x, Blocks.OBSIDIAN);
		GameRegistry.addShapedRecipe(new ItemStack(NTMBlocks.netherSlab), "XXX", x, NTMBlocks.netherWood);
		GameRegistry.addShapedRecipe(Konstanten.CHARCOALBLOCK, "XXX", "XXX", "XXX", x, new ItemStack(Items.COAL, 1, 1));
		GameRegistry.addShapedRecipe(new ItemStack(NTMBlocks.blockFreezer), "XYZ", x, Items.REDSTONE, y, NTMBlocks.blockCondenser, z, 
		FluidUtil.getFilledBucket(new FluidStack(BucketLoader.fluidDemonWater, 1000)));
		GameRegistry.addShapedRecipe(new ItemStack(NTMBlocks.blockFreezer), "XYZ", x, Items.REDSTONE, y, NTMBlocks.blockCondenser, z, BucketLoader.bucketStoneDemonWater);
		GameRegistry.addShapedRecipe(new ItemStack(NTMBlocks.blockFreezer), "XYZ", x, Items.REDSTONE, y, NTMBlocks.blockCondenser, z, BucketLoader.bucketWoodDemonWater);
		
		//Schmelz-Rezepte
		
		GameRegistry.addSmelting(Konstanten.PATCHWORKFLESH, new ItemStack(Items.LEATHER), 1.0F);
		GameRegistry.addSmelting(Items.ROTTEN_FLESH, new ItemStack(NTMItems.itemCookedJerky), 1.0F);
		GameRegistry.addSmelting(NTMBlocks.netherLog, new ItemStack(Items.COAL, 1, 1), 0.0F);
		GameRegistry.addSmelting(Konstanten.POWDEROFLIGHT, new ItemStack(NTMItems.itemLightCrystal), 2.0F);
		GameRegistry.addSmelting(Konstanten.HELLFAYAHORE, Konstanten.HELLFAYAH, 2.0F); //1 weil soll gemined werden
		GameRegistry.addSmelting(NTMBlocks.oreIronGravel, new ItemStack(Items.IRON_INGOT), 2.0F);
		GameRegistry.addSmelting(NTMBlocks.oreGoldGravel, new ItemStack(Items.GOLD_INGOT), 2.0F);
		GameRegistry.addSmelting(NTMBlocks.oreIronDust, new ItemStack(Items.IRON_INGOT), 2.0F);
		GameRegistry.addSmelting(NTMBlocks.oreGoldDust, new ItemStack(Items.GOLD_INGOT), 2.0F);
		GameRegistry.addSmelting(NTMBlocks.oreIronSand, new ItemStack(Items.IRON_INGOT), 2.0F);
		GameRegistry.addSmelting(NTMBlocks.oreGoldSand, new ItemStack(Items.GOLD_INGOT), 2.0F);
	}
	
	//Ore-Dictionary
	public static void oreRegistration()
    {
		OreDictionary.registerOre("treeSapling", NTMBlocks.netherSapling);
		OreDictionary.registerOre("treeLeaves", NTMBlocks.netherLeaves);
		OreDictionary.registerOre("vine", NTMBlocks.blockMeanVine);
    	OreDictionary.registerOre("itemSalt", Konstanten.SALT);
    	OreDictionary.registerOre("dustSalt", Konstanten.SALT);
    	OreDictionary.registerOre("dustObsidian", Konstanten.OBSIDIANDUST);
    	OreDictionary.registerOre("itemHellfayah", Konstanten.HELLFAYAH);
    	OreDictionary.registerOre("oreHellfayah", Konstanten.HELLFAYAHORE);
    	OreDictionary.registerOre("blockHellfayah", Konstanten.HELLFAYAHBLOCK);
    	OreDictionary.registerOre("plankWood", new ItemStack(NTMBlocks.netherWood));
    	OreDictionary.registerOre("slabWood", new ItemStack(NTMBlocks.netherSlab));
    	OreDictionary.registerOre("logWood", new ItemStack(NTMBlocks.netherLog));
    	OreDictionary.registerOre("oreGold", NTMBlocks.oreGoldDust);
    	OreDictionary.registerOre("oreGold", NTMBlocks.oreGoldGravel);
    	OreDictionary.registerOre("oreGold", NTMBlocks.oreGoldSand);
    	OreDictionary.registerOre("oreIron", NTMBlocks.oreIronDust);
    	OreDictionary.registerOre("oreIron", NTMBlocks.oreIronGravel);
    	OreDictionary.registerOre("oreIron", NTMBlocks.oreIronSand);
    	OreDictionary.registerOre("bucketWater", BucketLoader.bucketStoneWater);
    	OreDictionary.registerOre("bucketWater", BucketLoader.bucketWoodWater);
    	OreDictionary.registerOre("bucketWater", Items.WATER_BUCKET);
    }
   
    public static void addOreRecipes()
    {
    	GameRegistry.addRecipe(new ShapedOreRecipe(Konstanten.SALTBLOCK, "XX", "XX", x, "dustSalt"));
    	GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(NTMBlocks.sansidian, 2, 0), " X ", "XYX", " X ", x, "dustObsidian", y, "sand"));
    	GameRegistry.addRecipe(new ShapedOreRecipe(BucketLoader.bucketStone, "X X", " X ", x, "cobblestone"));
    	GameRegistry.addRecipe(new ShapedOreRecipe(BucketLoader.bucketWood, "X X", " Y ", x, "plankWood", y, "slabWood"));
    	GameRegistry.addRecipe(new ShapedOreRecipe(Konstanten.POWDEROFLIGHT, "XYX", "XZX", "XYX", x, Items.GLOWSTONE_DUST, y, "itemSalt", z, "sand"));
		
    	if(Config.barrel){
			GameRegistry.addRecipe(new ShapedOreRecipe(NTMBlocks.blockBarrel, "X X", "X X", "XXX", x, "plankWood"));
	    	GameRegistry.addRecipe(new ShapedOreRecipe(NTMBlocks.blockBarrelStone, "X X", "X X", "XXX", x, "cobblestone"));
	    }
    	
		GameRegistry.addRecipe(new ShapelessOreRecipe(Konstanten.SALT, "bucketWater"));
		
		if(!Config.disableHammers){
			GameRegistry.addRecipe(new ShapedOreRecipe(NTMItems.itemHammerWood, " X ", "XY ", "  Y", x, "plankWood", y, "stickWood"));
			GameRegistry.addRecipe(new ShapedOreRecipe(NTMItems.itemHammerStone, " X ", "XY ", "  Y", x, "cobblestone", y, Konstanten.STONEBAR));
			GameRegistry.addRecipe(new ShapedOreRecipe(NTMItems.itemHammerIron, " X ", "XY ", "  Y", x, Items.IRON_INGOT, y, Konstanten.STONEBAR));
			GameRegistry.addRecipe(new ShapedOreRecipe(NTMItems.itemHammerDiamond, " X ", "XY ", "  Y", x, "gemDiamond", y, Konstanten.STONEBAR));
			GameRegistry.addRecipe(new ShapedOreRecipe(NTMItems.itemHammerGold, " X ", "XY ", "  Y", x, Items.GOLD_INGOT, y, Konstanten.STONEBAR));
		}
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(NTMItems.itemSanctuaryCrystal), "XYX", "YZY", "XYX", x,  Blocks.GOLD_BLOCK, y, "gemDiamond", z, NTMItems.itemLightCrystal));
		GameRegistry.addRecipe(new ShapedOreRecipe(Konstanten.ENDERCRYSTAL, "XYX", "YZY", "XYX", x, Items.ENDER_EYE, y, "gemDiamond", z, Blocks.OBSIDIAN));
		GameRegistry.addRecipe(new ShapedOreRecipe(NTMItems.itemPickaxeNetherrack, "XXX", " Y ", " Y ", x, Blocks.NETHERRACK, y, "stickWood"));
		
		if(Config.sieve)
			GameRegistry.addRecipe(new ShapedOreRecipe(NTMBlocks.blockSieve, "XYX", "Z Z", "Z Z", x, "cobblestone", y, Konstanten.SIEVEWEB, z, Konstanten.STONEBAR));
		
		GameRegistry.addRecipe(new ShapelessOreRecipe(Konstanten.HELLFAYAHBLOCK, "itemHellfayah", "itemHellfayah", "itemHellfayah", "itemHellfayah", "itemHellfayah", "itemHellfayah", "itemHellfayah", "itemHellfayah", "itemHellfayah"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Konstanten.HELLFAYAH.getItem(), 9), "blockHellfayah"));		
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(NTMBlocks.waterFountain), "XYX", "X X", "XYX", x, Blocks.COBBLESTONE, y, "bucketWater"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(NTMBlocks.itemDoorNTMStone), "XX", "XX", "XX", x, "cobblestone"));
		GameRegistry.addRecipe(new ShapedOreRecipe(NTMItems.itemCactusGrabber, "XYX", " X ", " X ", x, "stickWood", y, Items.FLINT));
		GameRegistry.addRecipe(new ShapedOreRecipe(Konstanten.STONEBAR, "X ", " X", x, "cobblestone"));
		GameRegistry.addRecipe(new ShapedOreRecipe(Konstanten.STONEBAR, " X", "X ", x, "cobblestone"));

    }
}
