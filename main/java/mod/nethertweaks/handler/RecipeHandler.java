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
	
	public static void loadSmelting()
	{
		//Schmelz-Rezepte		
		GameRegistry.addSmelting(Items.ROTTEN_FLESH, new ItemStack(ItemHandler.COOKEDJERKY), 1.0F);
		GameRegistry.addSmelting(BlockHandler.NETHERLOG, new ItemStack(Items.COAL, 1, 1), 0.0F);
		GameRegistry.addSmelting(Konstanten.POWDEROFLIGHT, new ItemStack(ItemHandler.CRYSTAL, 1, 0), 2.0F);
		GameRegistry.addSmelting(Konstanten.HELLFAYAHORE, Konstanten.HELLFAYAH, 2.0F); //1 weil soll gemined werden
		GameRegistry.addSmelting(BlockHandler.OREIRONDUST, new ItemStack(Items.IRON_INGOT), 2.0F);
		GameRegistry.addSmelting(BlockHandler.OREGOLDDUST, new ItemStack(Items.GOLD_INGOT), 2.0F);
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
    	OreDictionary.registerOre("oreIron", BlockHandler.OREIRONDUST);
    	OreDictionary.registerOre("listAllDMW", BucketNFluidHandler.BUCKETSTONEDMW);
    	OreDictionary.registerOre("listAllDMW", BucketNFluidHandler.BUCKETWOODDMW);
    	OreDictionary.registerOre("listAllWater", BucketNFluidHandler.BUCKETSTONEWATER);
    	OreDictionary.registerOre("listAllWater", BucketNFluidHandler.BUCKETWOODWATER);
    	OreDictionary.registerOre("listAllWater", Items.WATER_BUCKET);
    	OreDictionary.registerOre("listAllBucket", BucketNFluidHandler.BUCKETSTONE);
    	OreDictionary.registerOre("listAllBucket", BucketNFluidHandler.BUCKETWOOD);
    	OreDictionary.registerOre("listAllBucket", Items.BUCKET);
    	OreDictionary.registerOre("lighter", Items.FLINT_AND_STEEL);
    	OreDictionary.registerOre("lighter", ItemHandler.FLINTANDBLAZE);
    }
   
    private static void addOreRecipes()
    {
    	/*
    	GameRegistry.addRecipe(new ShapedOreRecipe(BucketNFluidHandler.bucketStone, "X X", " X ", x, "cobblestone"));
    	GameRegistry.addRecipe(new ShapedOreRecipe(BucketNFluidHandler.bucketWood, "X X", " Y ", x, "plankWood", y, "slabWood"));
    	GameRegistry.addRecipe(new ShapedOreRecipe(Konstanten.POWDEROFLIGHT, "XYX", "XZX", "XYX", x, Items.GLOWSTONE_DUST, y, "itemSalt", z, "sand"));
    	
		GameRegistry.addRecipe(new ShapelessOreRecipe(Konstanten.SALT, "bucketWater"));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(Konstanten.ENDERCRYSTAL, "XYX", "YZY", "XYX", x, Items.ENDER_EYE, y, "gemDiamond", z, Blocks.OBSIDIAN));
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemHandler.itemPickaxeNetherrack, "XXX", " Y ", " Y ", x, Blocks.NETHERRACK, y, "stickWood"));
				
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
