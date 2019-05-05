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


public class SmeltingNOreDictHandler {
	
	public static void load() {
		loadSmelting();
		oreRegistration();
	}
	
	@Deprecated
	private static void loadSmelting()
	{
		//Schmelz-Rezepte		
		if(Config.enableJerky) 									GameRegistry.addSmelting(Items.ROTTEN_FLESH, new ItemStack(ItemHandler.COOKEDJERKY), 1.0F);
		if(Config.enableElderTree) 								GameRegistry.addSmelting(BlockHandler.ELDERLOG, new ItemStack(Items.COAL, 1, 1), 0.0F);
		if(Config.enableCrystalLight)							GameRegistry.addSmelting(Konstanten.POWDEROFLIGHT, new ItemStack(ItemHandler.CRYSTAL, 1, 0), 2.0F);
		if(Config.enableMultiBlock && Config.enableMultiItem) 	GameRegistry.addSmelting(Konstanten.HELLFAYAHORE, Konstanten.HELLFAYAH, 2.0F);
	}
	
	//Ore-Dictionary
	private static void oreRegistration()
    {
		if(Config.enableElderTree) 		OreDictionary.registerOre("treeSapling", BlockHandler.ELDERSAPLING);
		if(Config.enableElderTree) 		OreDictionary.registerOre("treeLeaves", BlockHandler.ELDERLEAVES);
		if(Config.enableMeanVine) 		OreDictionary.registerOre("vine", BlockHandler.MEANVINE);
		if(Config.enableMultiItem) 		OreDictionary.registerOre("itemSalt", Konstanten.SALT);
		if(Config.enableMultiItem) 		OreDictionary.registerOre("dustSalt", Konstanten.SALT);
		if(Config.enableMultiItem) 		OreDictionary.registerOre("itemHellfayah", Konstanten.HELLFAYAH);
		if(Config.enableMultiBlock) 	OreDictionary.registerOre("oreHellfayah", new ItemStack(BlockHandler.BLOCKBASIC, 1, 0));
		if(Config.enableMultiBlock) 	OreDictionary.registerOre("blockHellfayah", new ItemStack(BlockHandler.BLOCKBASIC, 1, 1));
		if(Config.enableElderTree) 		OreDictionary.registerOre("plankWood", new ItemStack(BlockHandler.ELDERPLANKS));
		if(Config.enableElderTree) 		OreDictionary.registerOre("slabWood", new ItemStack(BlockHandler.ELDERSLABHALF));
		if(Config.enableElderTree) 		OreDictionary.registerOre("logWood", new ItemStack(BlockHandler.ELDERLOG));
		
    	if (Config.enableLiquidImpossibility)
    	{
    		/*
			OreDictionary.registerOre("listAllLiquidImpossibility", BucketNFluidHandler.BUCKETSTONELI);
			OreDictionary.registerOre("listAllLiquidImpossibility", BucketNFluidHandler.BUCKETWOODLI);
			*/
			OreDictionary.registerOre("listAllLiquidImpossibility",
			FluidUtil.getFilledBucket(new FluidStack(BucketNFluidHandler.FLUIDLIQUIDIMPOSSIBILITY, 1000)));
		}
		if (Config.enableStoneBucket && Config.enableWoodBucket)
		{
			/*
			OreDictionary.registerOre("listAllWater", BucketNFluidHandler.BUCKETSTONEWATER);
			OreDictionary.registerOre("listAllWater", BucketNFluidHandler.BUCKETWOODWATER);
			*/
			OreDictionary.registerOre("listAllWater", Items.WATER_BUCKET);
			/*
			OreDictionary.registerOre("listAllBucket", BucketNFluidHandler.BUCKETSTONE);
			OreDictionary.registerOre("listAllBucket", BucketNFluidHandler.BUCKETWOOD);
			*/
			OreDictionary.registerOre("listAllBucket", Items.BUCKET);
		}
		if (Config.enableFlintNBlaze)
		{
			OreDictionary.registerOre("lighter", Items.FLINT_AND_STEEL);
			OreDictionary.registerOre("lighter", ItemHandler.FLINTANDBLAZE);
		}
    }
}
