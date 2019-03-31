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
import scala.tools.nsc.doc.model.ModelFactory.NonTemplateMemberImpl;
import io.netty.channel.nio.NioTask;
import mod.nethertweaks.Config;
import mod.nethertweaks.Konstanten;


public class RecipeHandler {
	
	public static void load() {
		loadSmelting();
		oreRegistration();
	}
	
	public static void loadSmelting(){
		
		//Schmelz-Rezepte
		
		GameRegistry.addSmelting(Items.ROTTEN_FLESH, new ItemStack(ItemHandler.COOKEDJERKY), 1.0F);
		GameRegistry.addSmelting(BlockHandler.NETHERLOG, new ItemStack(Items.COAL, 1, 1), 0.0F);
		GameRegistry.addSmelting(Konstanten.POWDEROFLIGHT, new ItemStack(ItemHandler.LIGHTCRYSTAL), 2.0F);
		GameRegistry.addSmelting(Konstanten.HELLFAYAHORE, Konstanten.HELLFAYAH, 2.0F); //1 weil soll gemined werden
		GameRegistry.addSmelting(BlockHandler.OREIRONGRAVEL, new ItemStack(Items.IRON_INGOT), 2.0F);
		GameRegistry.addSmelting(BlockHandler.OREGOLDGRAVEL, new ItemStack(Items.GOLD_INGOT), 2.0F);
		GameRegistry.addSmelting(BlockHandler.OREIRONDUST, new ItemStack(Items.IRON_INGOT), 2.0F);
		GameRegistry.addSmelting(BlockHandler.OREGOLDDUST, new ItemStack(Items.GOLD_INGOT), 2.0F);
		GameRegistry.addSmelting(BlockHandler.OREIRONSAND, new ItemStack(Items.IRON_INGOT), 2.0F);
		GameRegistry.addSmelting(BlockHandler.OREGOLDSAND, new ItemStack(Items.GOLD_INGOT), 2.0F);
	}
	
	//Ore-Dictionary
	public static void oreRegistration()
    {
		OreDictionary.registerOre("treeSapling", BlockHandler.NETHERSAPLING);
		OreDictionary.registerOre("treeLeaves", BlockHandler.NETHERLEAVES);
		OreDictionary.registerOre("vine", BlockHandler.MEANVINE);
    	OreDictionary.registerOre("itemSalt", Konstanten.SALT);
    	OreDictionary.registerOre("dustSalt", Konstanten.SALT);
    	OreDictionary.registerOre("itemHellfayah", Konstanten.HELLFAYAH);
    	OreDictionary.registerOre("oreHellfayah", new ItemStack(BlockHandler.BLOCKBASIC, 1, 0));
    	OreDictionary.registerOre("blockHellfayah", new ItemStack(BlockHandler.BLOCKBASIC, 1, 1));
    	OreDictionary.registerOre("plankWood", new ItemStack(BlockHandler.NETHERWOOD));
    	OreDictionary.registerOre("slabWood", new ItemStack(BlockHandler.NETHERSLAB));
    	OreDictionary.registerOre("logWood", new ItemStack(BlockHandler.NETHERLOG));
    	OreDictionary.registerOre("oreGold", BlockHandler.OREGOLDDUST);
    	OreDictionary.registerOre("oreGold", BlockHandler.OREGOLDGRAVEL);
    	OreDictionary.registerOre("oreGold", BlockHandler.OREGOLDSAND);
    	OreDictionary.registerOre("oreIron", BlockHandler.OREIRONDUST);
    	OreDictionary.registerOre("oreIron", BlockHandler.OREIRONGRAVEL);
    	OreDictionary.registerOre("oreIron", BlockHandler.OREIRONSAND);
    	OreDictionary.registerOre("bucketDW", BucketNFluidHandler.bucketStoneDemonWater);
    	OreDictionary.registerOre("bucketDW", BucketNFluidHandler.bucketWoodDemonWater);
    	OreDictionary.registerOre("bucketDW", BucketNFluidHandler.bucketDemonWater);
    	OreDictionary.registerOre("bucketWater", BucketNFluidHandler.bucketStoneWater);
    	OreDictionary.registerOre("bucketWater", BucketNFluidHandler.bucketWoodWater);
    	OreDictionary.registerOre("bucketWater", Items.WATER_BUCKET);
    	OreDictionary.registerOre("bucket", BucketNFluidHandler.bucketStone);
    	OreDictionary.registerOre("bucket", BucketNFluidHandler.bucketWood);
    	OreDictionary.registerOre("bucket", Items.BUCKET);
    	OreDictionary.registerOre("lighter", Items.FLINT_AND_STEEL);
    	OreDictionary.registerOre("lighter", ItemHandler.FLINTANDBLAZE);
    }
   
    private static void addOreRecipes()
    {
    	/*
    	GameRegistry.addRecipe(new ShapedOreRecipe(Konstanten.SALTBLOCK, "XX", "XX", x, "dustSalt"));
    	GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockHandler.sansidian, 2, 0), " X ", "XYX", " X ", x, "dustObsidian", y, "sand"));
    	GameRegistry.addRecipe(new ShapedOreRecipe(BucketNFluidHandler.bucketStone, "X X", " X ", x, "cobblestone"));
    	GameRegistry.addRecipe(new ShapedOreRecipe(BucketNFluidHandler.bucketWood, "X X", " Y ", x, "plankWood", y, "slabWood"));
    	GameRegistry.addRecipe(new ShapedOreRecipe(Konstanten.POWDEROFLIGHT, "XYX", "XZX", "XYX", x, Items.GLOWSTONE_DUST, y, "itemSalt", z, "sand"));
		
    	if(Config.barrel){
			GameRegistry.addRecipe(new ShapedOreRecipe(BlockHandler.blockBarrel, "X X", "X X", "XXX", x, "plankWood"));
	    	GameRegistry.addRecipe(new ShapedOreRecipe(BlockHandler.blockBarrelStone, "X X", "X X", "XXX", x, "cobblestone"));
	    }
    	
		GameRegistry.addRecipe(new ShapelessOreRecipe(Konstanten.SALT, "bucketWater"));
		
		if(!Config.disableHammers){
			GameRegistry.addRecipe(new ShapedOreRecipe(ItemHandler.itemHammerWood, " X ", "XY ", "  Y", x, "plankWood", y, "stickWood"));
			GameRegistry.addRecipe(new ShapedOreRecipe(ItemHandler.itemHammerStone, " X ", "XY ", "  Y", x, "cobblestone", y, Konstanten.STONEBAR));
			GameRegistry.addRecipe(new ShapedOreRecipe(ItemHandler.itemHammerIron, " X ", "XY ", "  Y", x, Items.IRON_INGOT, y, Konstanten.STONEBAR));
			GameRegistry.addRecipe(new ShapedOreRecipe(ItemHandler.itemHammerDiamond, " X ", "XY ", "  Y", x, "gemDiamond", y, Konstanten.STONEBAR));
			GameRegistry.addRecipe(new ShapedOreRecipe(ItemHandler.itemHammerGold, " X ", "XY ", "  Y", x, Items.GOLD_INGOT, y, Konstanten.STONEBAR));
		}
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemHandler.itemSanctuaryCrystal), "XYX", "YZY", "XYX", x,  Blocks.GOLD_BLOCK, y, "gemDiamond", z, ItemHandler.itemLightCrystal));
		GameRegistry.addRecipe(new ShapedOreRecipe(Konstanten.ENDERCRYSTAL, "XYX", "YZY", "XYX", x, Items.ENDER_EYE, y, "gemDiamond", z, Blocks.OBSIDIAN));
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemHandler.itemPickaxeNetherrack, "XXX", " Y ", " Y ", x, Blocks.NETHERRACK, y, "stickWood"));
		
		if(Config.sieve)
			GameRegistry.addRecipe(new ShapedOreRecipe(BlockHandler.blockSieve, "XYX", "Z Z", "Z Z", x, "cobblestone", y, Konstanten.SIEVEWEB, z, Konstanten.STONEBAR));
		
		GameRegistry.addRecipe(new ShapelessOreRecipe(Konstanten.HELLFAYAHBLOCK, "itemHellfayah", "itemHellfayah", "itemHellfayah", "itemHellfayah", "itemHellfayah", "itemHellfayah", "itemHellfayah", "itemHellfayah", "itemHellfayah"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Konstanten.HELLFAYAH.getItem(), 9), "blockHellfayah"));		
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockHandler.waterFountain), "XYX", "X X", "XYX", x, Blocks.COBBLESTONE, y, "bucketWater"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockHandler.itemDoorNTMStone), "XX", "XX", "XX", x, "cobblestone"));
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemHandler.itemCactusGrabber, "XYX", " X ", " X ", x, "stickWood", y, Items.FLINT));
		GameRegistry.addRecipe(new ShapedOreRecipe(Konstanten.STONEBAR, "X ", " X", x, "cobblestone"));
		GameRegistry.addRecipe(new ShapedOreRecipe(Konstanten.STONEBAR, " X", "X ", x, "cobblestone"));
		*/

    }
}
