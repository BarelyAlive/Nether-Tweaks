package mod.nethertweaks.config;

import net.minecraftforge.common.config.Configuration;

public class BlocksItems
{
	public static boolean
	//General
	enableElderTree 		  = true,
	enableHellfayah 		  = true,
	enableSalt	 			  = true,

	//BlockEnable
	enableSaltBlock 		  = true,
	enableDust 				  = true,
	enableAshBonePile		  = true,
	enableBarrel			  = true,
	enableBonfire 			  = true,
	enableCondenser 		  = true,
	enableStwH 				  = true,
	enableFreezer 			  = true,
	enableHellmart 			  = true,
	enableBlood 			  = true,
	enableBrine 			  = true,
	enableDistilledWater	  = true,
	enableMeanVine 			  = true,
	enableNetherrackFurnace   = true,
	enableNetherrackGravel 	  = true,
	enableSieve 			  = true,
	enableCrucible 			  = true,

	//ItemEnable
	enableStoneBar 			  = true,
	enablePortalCore 		  = true,
	enableEndBox	 		  = true,
	enableEnderInfusedFrame	  = true,
	enablePorcelainClay		  = true,
	enablePowderOfLight		  = true,
	enableMushroomSpores	  = true,
	enableGrassSeeds		  = true,
	enableCactusSeeds		  = true,
	enableSugarcaneSeeds	  = true,
	enableKitchenKnife 	  = true,
	enableAsh			 	  = true,
	enableWoodChippings 	  = true,
	enableCoiledSword 		  = true,
	enablePebbleStone		  = true,
	enablePebbleGranite		  = true,
	enablePebbleDiorite		  = true,
	enablePebbleAndesite	  = true,
	enableStringMeshes		  = true,
	enableFlintMeshes		  = true,
	enableIronMeshes		  = true,
	enableDiamondMeshes		  = true,
	enableDollBat			  = true,
	enableDollChicken		  = true,
	enableDollCow			  = true,
	enableDollDonkey		  = true,
	enableDollHorse			  = true,
	enableDollRedMooshroom	  = true,
	enableDollMule			  = true,
	enableDollOcelot		  = true,
	enableDollParrot		  = true,
	enableDollRabbit		  = true,
	enableDollSheep			  = true,
	enableDollLlama			  = true,
	enableDollPolarBear		  = true,
	enableDollWolf			  = true,
	enableDollVillager		  = true,
	enableDollPig			  = true,
	enableJerky 			  = true,
	enableHammerWood 		  = true,
	enableHammerStone 		  = true,
	enableHammerGold 		  = true,
	enableHammerIron 		  = true,
	enableHammerDiamond 	  = true,
	enableGrabberWood		  = true,
	enableGrabberStone 		  = true,
	enableGrabberGold		  = true,
	enableGrabberIron		  = true,
	enableGrabberDiamond 	  = true,
	enableFlintNBlaze 		  = true,
	enableStoneDoor 		  = true,
	enableWoodBucket 		  = true,
	enableStoneBucket 		  = true;

	static void load(final Configuration config)
	{
		final String blocks = "Blocks";
		final String items = "Items";

		//BlockEnable
		config.addCustomCategoryComment(blocks, "Disabling one of these may break mechanics in NTM!");
		enableSaltBlock 				= config.get(blocks, "Enable Block of Salt?", true).getBoolean();
		enableDust 						= config.get(blocks, "Enable Dust?", true).getBoolean();
		enableAshBonePile				= config.get(blocks, "Enable Ash Bone Pile?", true).getBoolean();
		enableBarrel					= config.get(blocks, "Enable Barrels?", true).getBoolean();
		enableBonfire 					= config.get(blocks, "Enable Bonfire?", true).getBoolean();
		enableCondenser 				= config.get(blocks, "Enable Condenser?", true).getBoolean();
		enableStwH 						= config.get(blocks, "Enable Stairway to Heaven?", true).getBoolean();
		enableFreezer 					= config.get(blocks, "Enable Freezer?", true).getBoolean();
		enableHellmart 					= config.get(blocks, "Enable Hellmart?", true).getBoolean();
		enableDistilledWater	 		= config.get(blocks, "Enable Distilled Water?", true,
				"If you deactivate this fluid, Condensers will produce vanilla water.").getBoolean();
		enableBlood 					= config.get(blocks, "Enable Blood?", true).getBoolean();
		enableBrine 					= config.get(blocks, "Enable Brine?", true).getBoolean();
		enableMeanVine 					= config.get(blocks, "Enable Mean Vines?", true).getBoolean();
		enableElderTree 				= config.get(blocks, "Enable Elder Tree?", true).getBoolean();
		enableNetherrackFurnace 		= config.get(blocks, "Enable Elderrack Furnace?", true).getBoolean();
		enableNetherrackGravel			= config.get(blocks, "Enable Netherrack Gravel?", true).getBoolean();
		enableSieve 					= config.get(blocks, "Enable Sieve?", true).getBoolean();
		enableCrucible 					= config.get(blocks, "Enable Crucible?", true).getBoolean();

		//ItemEnable
		config.addCustomCategoryComment(items, "Disabling one of these may break mechanics in NTM!");
		enableStoneBar	 				= config.get(items, "Enable Stone Bar?", true).getBoolean();
		enablePortalCore 				= config.get(items, "Enable Portal Core?", true).getBoolean();
		enableEndBox 					= config.get(items, "Enable End Box?", true).getBoolean();
		enableSalt 						= config.get(items, "Enable Salt?", true).getBoolean();
		enableHellfayah 				= config.get(items, "Enable Hellfayah?", true).getBoolean();
		enableEnderInfusedFrame			= config.get(items, "Enable Ender Infused Frame?", true).getBoolean();
		enablePorcelainClay				= config.get(items, "Enable Porcelain Clay?", true).getBoolean();
		enablePowderOfLight				= config.get(items, "Enable Powder of Light?", true).getBoolean();
		enableMushroomSpores			= config.get(items, "Enable Mushroom Spores?", true).getBoolean();
		enableGrassSeeds				= config.get(items, "Enable Grass Seeds?", true).getBoolean();
		enableCactusSeeds				= config.get(items, "Enable Cactus Seeds?", true).getBoolean();
		enableSugarcaneSeeds			= config.get(items, "Enable Sugarcane Seeds?", true).getBoolean();
		enableKitchenKnife			= config.get(items, "Enable Crystal of Light?", true).getBoolean();
		enableAsh						= config.get(items, "Enable Ash?", true).getBoolean();
		enableWoodChippings				= config.get(items, "Enable Wood Chippings?", true).getBoolean();
		enableCoiledSword				= config.get(items, "Enable Coiled Sword?", true).getBoolean();
		enablePebbleStone 				= config.get(items, "Enable Stone Pebbles?", true).getBoolean();
		enablePebbleGranite 			= config.get(items, "Enable Granite Pebbles?", true).getBoolean();
		enablePebbleDiorite 			= config.get(items, "Enable Diorite Pebbles?", true).getBoolean();
		enablePebbleAndesite			= config.get(items, "Enable Andesite Pebbles?", true).getBoolean();
		enableStringMeshes 				= config.get(items, "Enable String Meshes?", true).getBoolean();
		enableFlintMeshes 				= config.get(items, "Enable Flint Meshes?", true).getBoolean();
		enableIronMeshes 				= config.get(items, "Enable Iron Meshes?", true).getBoolean();
		enableDiamondMeshes				= config.get(items, "Enable Diamond Meshes?", true).getBoolean();
		enableDollBat 					= config.get(items, "Enable Bat Doll?", true).getBoolean();
		enableDollChicken 				= config.get(items, "Enable Chicken Doll?", true).getBoolean();
		enableDollCow 					= config.get(items, "Enable Cow Doll?", true).getBoolean();
		enableDollDonkey 				= config.get(items, "Enable Donkey Doll?", true).getBoolean();
		enableDollHorse 				= config.get(items, "Enable Horse Doll?", true).getBoolean();
		enableDollLlama 				= config.get(items, "Enable Llama Doll?", true).getBoolean();
		enableDollMule 					= config.get(items, "Enable Mule Doll?", true).getBoolean();
		enableDollOcelot 				= config.get(items, "Enable Ocelot Doll?", true).getBoolean();
		enableDollParrot 				= config.get(items, "Enable Parrot Doll?", true).getBoolean();
		enableDollPig 					= config.get(items, "Enable Pig Doll?", true).getBoolean();
		enableDollPolarBear 			= config.get(items, "Enable Polar Bear Doll?", true).getBoolean();
		enableDollRabbit 				= config.get(items, "Enable Rabbit Doll?", true).getBoolean();
		enableDollRedMooshroom			= config.get(items, "Enable Red Mooshroom Doll?", true).getBoolean();
		enableDollSheep 				= config.get(items, "Enable Sheep Doll?", true).getBoolean();
		enableDollVillager				= config.get(items, "Enable Villager Doll?", true).getBoolean();
		enableDollWolf 					= config.get(items, "Enable Wolf Doll?", true).getBoolean();
		enableJerky 					= config.get(items, "Enable Cooked Jerky?", true).getBoolean();
		enableHammerWood 				= config.get(items, "Enable Wood Hammer?", true).getBoolean();
		enableHammerStone 				= config.get(items, "Enable Stone Hammer?", true).getBoolean();
		enableHammerGold 				= config.get(items, "Enable Gold Hammer?", true).getBoolean();
		enableHammerIron 				= config.get(items, "Enable Iron Hammer?", true).getBoolean();
		enableHammerDiamond 			= config.get(items, "Enable Diamond Hammer?", true).getBoolean();
		enableGrabberWood 				= config.get(items, "Enable Wood Grabber?", true).getBoolean();
		enableGrabberStone 				= config.get(items, "Enable Stone Grabber?", true).getBoolean();
		enableGrabberGold 				= config.get(items, "Enable Gold Grabber?", true).getBoolean();
		enableGrabberIron 				= config.get(items, "Enable Iron Grabber?", true).getBoolean();
		enableGrabberDiamond 			= config.get(items, "Enable Diamond Grabber?", true).getBoolean();
		enableFlintNBlaze 				= config.get(items, "Enable Flint & Blaze?", true).getBoolean();
		enableStoneDoor 				= config.get(items, "Enable Stone Door?", true).getBoolean();
		enableWoodBucket				= config.get(items, "Enable Wood Bucket?", true).getBoolean();
		enableStoneBucket				= config.get(items, "Enable Stone Bucket?", true).getBoolean();
	}
}
