package mod.nethertweaks.handler;

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
		if(Config.enableJerky) 								 	GameRegistry.addSmelting(Items.ROTTEN_FLESH, new ItemStack(ItemHandler.COOKED_JERKY), 1.0F);
		if(Config.enableElderTree) 								GameRegistry.addSmelting(BlockHandler.ELDER_LOG, new ItemStack(Items.COAL, 1, 1), 0.0F);
		if(Config.enableCrystalLight)						 	GameRegistry.addSmelting(ItemHandler.POWDER_OF_LIGHT, new ItemStack(ItemHandler.CRYSTAL_OF_LIGHT), 2.0F);
		if(Config.enableHellfayahOre && Config.enableHellfayah) GameRegistry.addSmelting(BlockHandler.HELLFAYAH_ORE, new ItemStack(ItemHandler.HELLFAYAH), 2.0F);
		if(Config.enableCrucible)							 	GameRegistry.addSmelting(BlockHandler.UNFIRED_CRUCIBLE, new ItemStack(BlockHandler.CRUCIBLE), 1.0f);
		if(Config.enableWoodChippings)							GameRegistry.addSmelting(ItemHandler.WOOD_CHIPPINGS, new ItemStack(ItemHandler.ASH), 0.0F);
	}

	//Ore-Dictionary
	private static void oreRegistration()
    {
		if(Config.enableAsh) {
										  OreDictionary.registerOre("dustAsh", ItemHandler.ASH);
										  OreDictionary.registerOre("dye", ItemHandler.ASH);
										  OreDictionary.registerOre("dyeLightGray", ItemHandler.ASH);
		}
		if(Config.enableMeanVine) 		  OreDictionary.registerOre("vine", BlockHandler.MEAN_VINE);
		if(Config.enableNetherrackGravel) OreDictionary.registerOre("gravel", BlockHandler.NETHERRACK_GRAVEL);
		if(Config.enableJerky)			  OreDictionary.registerOre("listAllmeatcooked", ItemHandler.COOKED_JERKY);
		if(Config.enableSalt)	 		  OreDictionary.registerOre("itemSalt", ItemHandler.SALT);
		if(Config.enableSalt)			  OreDictionary.registerOre("dustSalt", ItemHandler.SALT);
		if(Config.enableString)		  	  OreDictionary.registerOre("string", ItemHandler.STRING);
		if(Config.enableHellfayah)		  OreDictionary.registerOre("itemHellfayah", ItemHandler.HELLFAYAH);
		if(Config.enableHellfayahOre) 	  OreDictionary.registerOre("oreHellfayah", new ItemStack(BlockHandler.HELLFAYAH_ORE, 1, 0));
		if(Config.enableHellfayahBlock)   OreDictionary.registerOre("blockHellfayah", new ItemStack(BlockHandler.BLOCK_OF_HELLFAYAH, 1, 1));
		if(Config.enableSaltBlock)		  OreDictionary.registerOre("blockSalt", new ItemStack(BlockHandler.BLOCK_OF_SALT, 1, 2));
		if(Config.enableElderTree)
		{
										  OreDictionary.registerOre("plankWood", BlockHandler.ELDER_PLANKS);
										  OreDictionary.registerOre("slabWood", BlockHandler.ELDER_SLAB);
										  OreDictionary.registerOre("logWood", new ItemStack(BlockHandler.ELDER_LOG, 1, OreDictionary.WILDCARD_VALUE));
										  OreDictionary.registerOre("treeSapling", BlockHandler.ELDER_SAPLING);
										  OreDictionary.registerOre("treeLeaves", BlockHandler.ELDER_LEAVES);
		}
		if(Config.enableMushroomSpores)   OreDictionary.registerOre("listAllseed", new ItemStack(ItemHandler.MUSHROOM_SPORES));
		if(Config.enableGrassSeeds) 	  OreDictionary.registerOre("listAllseed", new ItemStack(ItemHandler.GRASS_SEEDS));
		if(Config.enableCactusSeeds) 	  OreDictionary.registerOre("listAllseed", new ItemStack(ItemHandler.CACTUS_SEEDS));
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
