package mod.nethertweaks.handler;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
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
import mod.nethertweaks.Konstanten;
import mod.nethertweaks.config.Config;
import mod.sfhcore.handler.BucketHandler;
import mod.sfhcore.util.LogUtil;

public class SmeltingNOreDictHandler {

	public static void load() {
		loadSmelting();
		oreRegistration();
	}

	@Deprecated
	private static void loadSmelting()
	{
		//Schmelz-Rezepte
		if(Config.enableJerky) 								  GameRegistry.addSmelting(Items.ROTTEN_FLESH, new ItemStack(ItemHandler.COOKEDJERKY), 1.0F);
		if(Config.enableElderTree) 							  GameRegistry.addSmelting(BlockHandler.ELDERLOG, new ItemStack(Items.COAL, 1, 1), 0.0F);
		if(Config.enableCrystalLight)						  GameRegistry.addSmelting(Konstanten.POWDEROFLIGHT, new ItemStack(ItemHandler.CRYSTAL, 1, 0), 2.0F);
		if(Config.enableMultiBlock && Config.enableMultiItem) GameRegistry.addSmelting(Konstanten.HELLFAYAHORE, Konstanten.HELLFAYAH, 2.0F);
		if(Config.enableCrucible)							  GameRegistry.addSmelting(BlockHandler.UNFIRED_CRUCIBLE, new ItemStack(BlockHandler.CRUCIBLE), 2.0f);
	}

	//Ore-Dictionary
	private static void oreRegistration()
    {
		if(Config.enableMeanVine) 		 OreDictionary.registerOre("vine", BlockHandler.MEANVINE);
		if(Config.enableNetherrackGravel)OreDictionary.registerOre("gravel", BlockHandler.NETHERRACKGRAVEL);
		if(Config.enableJerky)			 OreDictionary.registerOre("listAllmeatcooked", ItemHandler.COOKEDJERKY);
		if(Config.enableMultiItem)
		{
			OreDictionary.registerOre("itemSalt", Konstanten.SALT);
			OreDictionary.registerOre("dustSalt", Konstanten.SALT);
			OreDictionary.registerOre("string", Konstanten.STRING);
			OreDictionary.registerOre("itemHellfayah", Konstanten.HELLFAYAH);
		}
		if(Config.enableMultiBlock)
		{
			OreDictionary.registerOre("oreHellfayah", new ItemStack(BlockHandler.BLOCKBASIC, 1, 0));
			OreDictionary.registerOre("blockHellfayah", new ItemStack(BlockHandler.BLOCKBASIC, 1, 1));
			OreDictionary.registerOre("blockSalt", new ItemStack(BlockHandler.BLOCKBASIC, 1, 2));
		}
		if(Config.enableElderTree)
		{
			OreDictionary.registerOre("plankWood", BlockHandler.ELDERPLANKS);
			OreDictionary.registerOre("slabWood", BlockHandler.ELDERSLABHALF);
			OreDictionary.registerOre("logWood", BlockHandler.ELDERLOG);
			OreDictionary.registerOre("treeSapling", BlockHandler.ELDERSAPLING);
			OreDictionary.registerOre("treeLeaves", BlockHandler.ELDERLEAVES);
		}
		if(Config.enableSeed)
		{
			for(int i = 1; i < 4; i++)
			OreDictionary.registerOre("listAllseed", new ItemStack(ItemHandler.SEED, 1, i));
		}
    	if(Config.enableLiquidImpossibility)	
			OreDictionary.registerOre("listAllLiquidImpossibility",	FluidUtil.getFilledBucket(new FluidStack(BucketNFluidHandler.FLUIDLIQUIDIMPOSSIBILITY, 1000)));
		
    	if(Config.enableStoneBucket)
		{	
			OreDictionary.registerOre("listAllBucket", BucketHandler.getBucketFromFluid(null, "stone"));
			OreDictionary.registerOre("listAllWater", BucketHandler.getBucketFromFluid(FluidRegistry.WATER, "stone"));
			OreDictionary.registerOre("listAllLava", BucketHandler.getBucketFromFluid(FluidRegistry.LAVA, "stone"));
			
			if(Config.enableLiquidImpossibility)
				OreDictionary.registerOre("listAllLiquidImpossibility", BucketHandler.getBucketFromFluid(BucketNFluidHandler.FLUIDLIQUIDIMPOSSIBILITY, "stone"));
		}
		if(Config.enableWoodBucket)
		{
			OreDictionary.registerOre("listAllBucket", BucketHandler.getBucketFromFluid(null, "wood"));
			OreDictionary.registerOre("listAllWater", BucketHandler.getBucketFromFluid(FluidRegistry.WATER, "wood"));
			
			if(Config.enableLiquidImpossibility)
				OreDictionary.registerOre("listAllLiquidImpossibility", BucketHandler.getBucketFromFluid(BucketNFluidHandler.FLUIDLIQUIDIMPOSSIBILITY, "wood"));
		}

		OreDictionary.registerOre("listAllBucket", Items.BUCKET);
		OreDictionary.registerOre("listAllWater", Items.WATER_BUCKET);

		if(Config.enableFlintNBlaze)
		{
			OreDictionary.registerOre("lighter", Items.FLINT_AND_STEEL);
			OreDictionary.registerOre("lighter", ItemHandler.FLINTANDBLAZE);
		}
    }
}
