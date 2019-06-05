package mod.nethertweaks.handler;

import mod.nethertweaks.Konstanten;
import mod.nethertweaks.config.Config;
import mod.sfhcore.handler.BucketHandler;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class SmeltingNOreDictHandler {

	public static void load() {
		loadSmelting();
		oreRegistration();
	}

	private static void loadSmelting()
	{
		//Schmelz-Rezepte
		if(Config.enableJerky) 								 GameRegistry.addSmelting(Items.ROTTEN_FLESH, new ItemStack(ItemHandler.COOKED_JERKY), 1.0F);
		if(Config.enableElderTree) 							 GameRegistry.addSmelting(BlockHandler.ELDER_LOG, new ItemStack(Items.COAL, 1, 1), 0.0F);
		if(Config.enableCrystalLight)						 GameRegistry.addSmelting(Konstanten.POWDER_OF_LIGHT, Konstanten.CRYSTAL_LIGHT, 2.0F);
		if(Config.enableMultiBlock && Config.enableMultiItem)GameRegistry.addSmelting(Konstanten.HELLFAYAH_ORE, Konstanten.HELLFAYAH, 2.0F);
		if(Config.enableCrucible)							 GameRegistry.addSmelting(BlockHandler.UNFIRED_CRUCIBLE, new ItemStack(BlockHandler.CRUCIBLE), 1.0f);
	}

	//Ore-Dictionary
	private static void oreRegistration()
    {
		if(Config.enableMeanVine) 		 OreDictionary.registerOre("vine", BlockHandler.MEAN_VINE);
		if(Config.enableNetherrackGravel)OreDictionary.registerOre("gravel", BlockHandler.NETHERRACK_GRAVEL);
		if(Config.enableJerky)			 OreDictionary.registerOre("listAllmeatcooked", ItemHandler.COOKED_JERKY);
		if(Config.enableMultiItem)
		{
			OreDictionary.registerOre("itemSalt", Konstanten.SALT);
			OreDictionary.registerOre("dustSalt", Konstanten.SALT);
			OreDictionary.registerOre("string", Konstanten.STRING);
			OreDictionary.registerOre("itemHellfayah", Konstanten.HELLFAYAH);
		}
		if(Config.enableMultiBlock)
		{
			OreDictionary.registerOre("oreHellfayah", new ItemStack(BlockHandler.BLOCK_BASIC, 1, 0));
			OreDictionary.registerOre("blockHellfayah", new ItemStack(BlockHandler.BLOCK_BASIC, 1, 1));
			OreDictionary.registerOre("blockSalt", new ItemStack(BlockHandler.BLOCK_BASIC, 1, 2));
		}
		if(Config.enableElderTree)
		{
			OreDictionary.registerOre("plankWood", BlockHandler.ELDER_PLANKS);
			OreDictionary.registerOre("slabWood", BlockHandler.ELDER_SLAB_HALF);
			OreDictionary.registerOre("logWood", BlockHandler.ELDER_LOG);
			OreDictionary.registerOre("treeSapling", BlockHandler.ELDER_SAPLING);
			OreDictionary.registerOre("treeLeaves", BlockHandler.ELDER_LEAVES);
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
			OreDictionary.registerOre("lighter", ItemHandler.FLINT_N_BLAZE);
		}
    }
}
