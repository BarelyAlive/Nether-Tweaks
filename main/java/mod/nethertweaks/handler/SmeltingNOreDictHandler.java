package mod.nethertweaks.handler;

import mod.nethertweaks.config.BlocksItems;
import mod.sfhcore.handler.BucketHandler;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class SmeltingNOreDictHandler
{
	public SmeltingNOreDictHandler()
	{
		loadSmelting();
		oreRegistration();
	}

	private static void loadSmelting()
	{
		//Schmelz-Rezepte
		if(BlocksItems.enableHellfayah)		GameRegistry.addSmelting(BlockHandler.HELLFAYAH_ORE, new ItemStack(ItemHandler.HELLFAYAH), 2.0F);
		if(BlocksItems.enableJerky) 		GameRegistry.addSmelting(Items.ROTTEN_FLESH, new ItemStack(ItemHandler.COOKED_JERKY), 1.0F);
		if(BlocksItems.enableElderTree) 	GameRegistry.addSmelting(BlockHandler.ELDER_LOG, new ItemStack(Items.COAL, 1, 1), 0.0F);
		if(BlocksItems.enableCrystalLight)	GameRegistry.addSmelting(ItemHandler.POWDER_OF_LIGHT, new ItemStack(ItemHandler.CRYSTAL_OF_LIGHT), 2.0F);
		if(BlocksItems.enableCrucible)		GameRegistry.addSmelting(BlockHandler.UNFIRED_CRUCIBLE, new ItemStack(BlockHandler.CRUCIBLE), 1.0f);
		if(BlocksItems.enableWoodChippings)	GameRegistry.addSmelting(ItemHandler.WOOD_CHIPPINGS, new ItemStack(ItemHandler.ASH), 0.0F);
	}

	//Ore-Dictionary
	private static void oreRegistration()
	{
		if(BlocksItems.enableAsh) {
			OreDictionary.registerOre("dustAsh", ItemHandler.ASH);
			OreDictionary.registerOre("dye", ItemHandler.ASH);
			OreDictionary.registerOre("dyeLightGray", ItemHandler.ASH);
		}
		if(BlocksItems.enableMeanVine) 		  OreDictionary.registerOre("vine", BlockHandler.MEAN_VINE);
		if(BlocksItems.enableNetherrackGravel)OreDictionary.registerOre("gravel", BlockHandler.NETHERRACK_GRAVEL);
		if(BlocksItems.enableJerky)			  OreDictionary.registerOre("listAllmeatcooked", ItemHandler.COOKED_JERKY);
		if(BlocksItems.enableSalt)	 		  OreDictionary.registerOre("itemSalt", ItemHandler.SALT);
		if(BlocksItems.enableSalt)			  OreDictionary.registerOre("dustSalt", ItemHandler.SALT);
		if(BlocksItems.enableString)		  OreDictionary.registerOre("string", ItemHandler.STRING);
		if(BlocksItems.enableHellfayah) {
			OreDictionary.registerOre("hellfayah", ItemHandler.HELLFAYAH);
			OreDictionary.registerOre("oreHellfayah", new ItemStack(BlockHandler.HELLFAYAH_ORE, 1, 0));
			OreDictionary.registerOre("blockHellfayah", new ItemStack(BlockHandler.BLOCK_OF_HELLFAYAH, 1, 1));
		}
		if(BlocksItems.enableSaltBlock)		  OreDictionary.registerOre("blockSalt", new ItemStack(BlockHandler.BLOCK_OF_SALT, 1, 2));
		if(BlocksItems.enableElderTree)
		{
			OreDictionary.registerOre("plankWood", BlockHandler.ELDER_PLANKS);
			OreDictionary.registerOre("slabWood", BlockHandler.ELDER_SLAB);
			OreDictionary.registerOre("logWood", new ItemStack(BlockHandler.ELDER_LOG, 1, OreDictionary.WILDCARD_VALUE));
			OreDictionary.registerOre("treeSapling", BlockHandler.ELDER_SAPLING);
			OreDictionary.registerOre("treeLeaves", BlockHandler.ELDER_LEAVES);
		}
		if(BlocksItems.enableMushroomSpores)   OreDictionary.registerOre("listAllseed", new ItemStack(ItemHandler.MUSHROOM_SPORES));
		if(BlocksItems.enableGrassSeeds) 	  OreDictionary.registerOre("listAllseed", new ItemStack(ItemHandler.GRASS_SEEDS));
		if(BlocksItems.enableCactusSeeds) 	  OreDictionary.registerOre("listAllseed", new ItemStack(ItemHandler.CACTUS_SEEDS));

		//Vanilla/Forge Buckets
		if(BlocksItems.enableLiquidImpossibility)
			OreDictionary.registerOre("listAllLiquidImpossibility",	FluidUtil.getFilledBucket(new FluidStack(FluidHandler.FLUID_LIQUID_IMPOSSIBILITY, 1000)));
		if(BlocksItems.enableDistilledWater)
			OreDictionary.registerOre("listAllDistilledWater",	FluidUtil.getFilledBucket(new FluidStack(FluidHandler.FLUID_DISTILLED_WATER, 1000)));

		if(BlocksItems.enableStoneBucket)
		{
			OreDictionary.registerOre("listAllBucket", BucketHandler.getBucketFromFluid(null, "stone"));
			OreDictionary.registerOre("listAllWater", BucketHandler.getBucketFromFluid(FluidRegistry.WATER, "stone"));
			OreDictionary.registerOre("listAllLava", BucketHandler.getBucketFromFluid(FluidRegistry.LAVA, "stone"));

			if(BlocksItems.enableLiquidImpossibility)
				OreDictionary.registerOre("listAllLiquidImpossibility", BucketHandler.getBucketFromFluid(FluidHandler.FLUID_LIQUID_IMPOSSIBILITY, "stone"));
			if(BlocksItems.enableDistilledWater)
				OreDictionary.registerOre("listAllDistilledWater", BucketHandler.getBucketFromFluid(FluidHandler.FLUID_DISTILLED_WATER, "stone"));
		}
		if(BlocksItems.enableWoodBucket)
		{
			OreDictionary.registerOre("listAllBucket", BucketHandler.getBucketFromFluid(null, "wood"));
			OreDictionary.registerOre("listAllWater", BucketHandler.getBucketFromFluid(FluidRegistry.WATER, "wood"));

			if(BlocksItems.enableLiquidImpossibility)
				OreDictionary.registerOre("listAllLiquidImpossibility", BucketHandler.getBucketFromFluid(FluidHandler.FLUID_LIQUID_IMPOSSIBILITY, "wood"));
			if(BlocksItems.enableDistilledWater)
				OreDictionary.registerOre("listAllDistilledWater", BucketHandler.getBucketFromFluid(FluidHandler.FLUID_DISTILLED_WATER, "wood"));
		}

		OreDictionary.registerOre("listAllBucket", Items.BUCKET);
		OreDictionary.registerOre("listAllWater", Items.WATER_BUCKET);

		if(BlocksItems.enableFlintNBlaze)
		{
			OreDictionary.registerOre("lighter", Items.FLINT_AND_STEEL);
			OreDictionary.registerOre("lighter", ItemHandler.FLINT_N_BLAZE);
		}
	}
}
