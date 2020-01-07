package mod.nethertweaks.init;

import mod.nethertweaks.config.BlocksItems;
import mod.sfhcore.handler.BucketHandler;
import mod.sfhcore.util.ItemInfo;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class ModSmeltingNOreDict
{
	//NTM
	public static final String BLOOD = "listAllBlood";
	public static final String DISTILLED_WATER = "listAllDistilledWater";

	public static final String HELLFAYAH = "hellfayah";
	public static final String ORE_HELLFAYAH = "oreHellfayah";
	public static final String BLOCK_HELLFAYAH = "blockHellfayah";

	public static void init()
	{
		loadSmelting();
		oreRegistration();
	}

	private static void loadSmelting()
	{
		//Schmelz-Rezepte
		if(BlocksItems.enableHellfayah)		GameRegistry.addSmelting(ModBlocks.HELLFAYAH_ORE, new ItemStack(ModItems.HELLFAYAH), 2.0F);
		if(BlocksItems.enableJerky) 		GameRegistry.addSmelting(Items.ROTTEN_FLESH, new ItemStack(ModItems.COOKED_JERKY), 1.0F);
		if(BlocksItems.enableElderTree) 	GameRegistry.addSmelting(ModBlocks.ELDER_LOG, new ItemStack(Items.COAL, 1, 1), 0.0F);
		if(BlocksItems.enableCrucible)		GameRegistry.addSmelting(ModBlocks.UNFIRED_CRUCIBLE, new ItemStack(ModBlocks.CRUCIBLE), 1.0f);
		if(BlocksItems.enableWoodChippings)	GameRegistry.addSmelting(ModItems.WOOD_CHIPPINGS, new ItemStack(ModItems.ASH), 0.0F);
	}

	//Ore-Dictionary
	private static void oreRegistration()
	{
		if(BlocksItems.enableAsh) {
			OreDictionary.registerOre("dustAsh", ModItems.ASH);
			OreDictionary.registerOre("dye", ModItems.ASH);
			OreDictionary.registerOre("dyeLightGray", ModItems.ASH);
		}
		if(BlocksItems.enableMeanVine) {
			OreDictionary.registerOre("vine", ModBlocks.MEAN_VINE);
			OreDictionary.registerOre("string", ModItems.STRING);
		}
		if(BlocksItems.enableNetherrackGravel)OreDictionary.registerOre("gravel", ModBlocks.NETHERRACK_GRAVEL);
		if(BlocksItems.enableJerky)			  OreDictionary.registerOre("listAllmeatcooked", ModItems.COOKED_JERKY);
		if(BlocksItems.enableSalt) {
			OreDictionary.registerOre("itemSalt", ModItems.SALT);
			OreDictionary.registerOre("dustSalt", ModItems.SALT);

			if(BlocksItems.enableSaltBlock)
				OreDictionary.registerOre("blockSalt", new ItemStack(ModBlocks.BLOCK_OF_SALT, 1, 2));
		}
		if(BlocksItems.enableHellfayah) {
			OreDictionary.registerOre(HELLFAYAH, ModItems.HELLFAYAH);
			OreDictionary.registerOre(ORE_HELLFAYAH, new ItemStack(ModBlocks.HELLFAYAH_ORE, 1, 0));
			OreDictionary.registerOre(BLOCK_HELLFAYAH, new ItemStack(ModBlocks.BLOCK_OF_HELLFAYAH, 1, 1));
		}
		if(BlocksItems.enableElderTree)
		{
			OreDictionary.registerOre("plankWood", ModBlocks.ELDER_PLANKS);
			OreDictionary.registerOre("slabWood", ModBlocks.ELDER_SLAB);
			OreDictionary.registerOre("logWood", new ItemStack(ModBlocks.ELDER_LOG, 1, OreDictionary.WILDCARD_VALUE));
			OreDictionary.registerOre("treeSapling", ModBlocks.ELDER_SAPLING);
			OreDictionary.registerOre("treeLeaves", ModBlocks.ELDER_LEAVES);
		}
		if(BlocksItems.enableMushroomSpores)   OreDictionary.registerOre("listAllseed", new ItemStack(ModItems.MUSHROOM_SPORES));
		if(BlocksItems.enableGrassSeeds) 	  OreDictionary.registerOre("listAllseed", new ItemStack(ModItems.GRASS_SEEDS));
		if(BlocksItems.enableCactusSeeds) 	  OreDictionary.registerOre("listAllseed", new ItemStack(ModItems.CACTUS_SEEDS));

		//Vanilla/Forge Buckets
		if(BlocksItems.enableDistilledWater)
			OreDictionary.registerOre(DISTILLED_WATER,	FluidUtil.getFilledBucket(new FluidStack(ModFluids.FLUID_DISTILLED_WATER, 1000)));

		if(BlocksItems.enableStoneBucket)
		{
			OreDictionary.registerOre("listAllBucket", BucketHandler.getBucketFromFluid(null, "stone"));
			OreDictionary.registerOre("listAllWater", BucketHandler.getBucketFromFluid(FluidRegistry.WATER, "stone"));
			OreDictionary.registerOre("listAllLava", BucketHandler.getBucketFromFluid(FluidRegistry.LAVA, "stone"));

			if(BlocksItems.enableDistilledWater)
				OreDictionary.registerOre(DISTILLED_WATER, BucketHandler.getBucketFromFluid(ModFluids.FLUID_DISTILLED_WATER, "stone"));
		}
		
		if(BlocksItems.enableWoodBucket)
		{
			OreDictionary.registerOre("listAllBucket", BucketHandler.getBucketFromFluid(null, "wood"));
			OreDictionary.registerOre("listAllWater", BucketHandler.getBucketFromFluid(FluidRegistry.WATER, "wood"));

			if(BlocksItems.enableDistilledWater)
				OreDictionary.registerOre(DISTILLED_WATER, BucketHandler.getBucketFromFluid(ModFluids.FLUID_DISTILLED_WATER, "wood"));
		}
		
		if(BlocksItems.enableBlood && BlocksItems.enableWoodBucket && BlocksItems.enableWoodBucket)
			for(Fluid f : FluidRegistry.getBucketFluids())
				if(f.getName().equals("blood") || f.getName().equals("lifeEssence"))
				{
					OreDictionary.registerOre(BLOOD, FluidUtil.getFilledBucket(new FluidStack(f, 1000)));
					OreDictionary.registerOre(BLOOD, BucketHandler.getBucketFromFluid(f, "wood"));
					OreDictionary.registerOre(BLOOD, BucketHandler.getBucketFromFluid(f, "stone"));
				}

		OreDictionary.registerOre("listAllBucket", Items.BUCKET);
		OreDictionary.registerOre("listAllWater", Items.WATER_BUCKET);
	}
}
