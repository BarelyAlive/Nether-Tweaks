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
	enableLiquidImpossibility = true,
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
	enableCrystalOfLight 		  = true,
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

	protected static void load(final Configuration config)
	{
		//BlockEnable
		config.addCustomCategoryComment("Blocks", "Disabling one of these may break mechanics in NTM!");
		enableSaltBlock 				= config.get("Blocks", "Enable Block of Salt?", true).getBoolean();
		enableDust 						= config.get("Blocks", "Enable Dust?", true).getBoolean();
		enableAshBonePile				= config.get("Blocks", "Enable Ash Bone Pile?", true).getBoolean();
		enableBarrel					= config.get("Blocks", "Enable Barrels?", true).getBoolean();
		enableBonfire 					= config.get("Blocks", "Enable Bonfire?", true).getBoolean();
		enableCondenser 				= config.get("Blocks", "Enable Condenser?", true).getBoolean();
		enableStwH 						= config.get("Blocks", "Enable Stairway to Heaven?", true).getBoolean();
		enableFreezer 					= config.get("Blocks", "Enable Freezer?", true).getBoolean();
		enableHellmart 					= config.get("Blocks", "Enable Hellmart?", true).getBoolean();
		enableDistilledWater	 		= config.get("Blocks", "Enable Distilled Water?", true,
				"If you deactivate this fluid, Condensers will produce vanilla water.").getBoolean();
		enableLiquidImpossibility 		= config.get("Blocks", "Enable Liquid Impossibility?", true).getBoolean();
		enableMeanVine 					= config.get("Blocks", "Enable Mean Vines?", true).getBoolean();
		enableElderTree 				= config.get("Blocks", "Enable Elder Tree?", true).getBoolean();
		enableNetherrackFurnace 		= config.get("Blocks", "Enable Elderrack Furnace?", true).getBoolean();
		enableNetherrackGravel			= config.get("Blocks", "Enable Netherrack Gravel?", true).getBoolean();
		enableSieve 					= config.get("Blocks", "Enable Sieve?", true).getBoolean();
		enableCrucible 					= config.get("Blocks", "Enable Crucible?", true).getBoolean();

		//ItemEnable
		config.addCustomCategoryComment("Items", "Disabling one of these may break mechanics in NTM!");
		enableStoneBar	 				= config.get("Items", "Enable Stone Bar?", true).getBoolean();
		enablePortalCore 				= config.get("Items", "Enable Portal Core?", true).getBoolean();
		enableEndBox 					= config.get("Items", "Enable End Box?", true).getBoolean();
		enableSalt 						= config.get("Items", "Enable Salt?", true).getBoolean();
		enableHellfayah 				= config.get("Items", "Enable Hellfayah?", true).getBoolean();
		enableEnderInfusedFrame			= config.get("Items", "Enable Ender Infused Frame?", true).getBoolean();
		enablePorcelainClay				= config.get("Items", "Enable Porcelain Clay?", true).getBoolean();
		enablePowderOfLight				= config.get("Items", "Enable Powder of Light?", true).getBoolean();
		enableMushroomSpores			= config.get("Items", "Enable Mushroom Spores?", true).getBoolean();
		enableGrassSeeds				= config.get("Items", "Enable Grass Seeds?", true).getBoolean();
		enableCactusSeeds				= config.get("Items", "Enable Cactus Seeds?", true).getBoolean();
		enableSugarcaneSeeds			= config.get("Items", "Enable Sugarcane Seeds?", true).getBoolean();
		enableCrystalOfLight				= config.get("Items", "Enable Crystal of Light?", true).getBoolean();
		enableAsh						= config.get("Items", "Enable Ash?", true).getBoolean();
		enableWoodChippings				= config.get("Items", "Enable Wood Chippings?", true).getBoolean();
		enableCoiledSword				= config.get("Items", "Enable Coiled Sword?", true).getBoolean();
		enablePebbleStone 				= config.get("Items", "Enable Stone Pebbles?", true).getBoolean();
		enablePebbleGranite 			= config.get("Items", "Enable Granite Pebbles?", true).getBoolean();
		enablePebbleDiorite 			= config.get("Items", "Enable Diorite Pebbles?", true).getBoolean();
		enablePebbleAndesite			= config.get("Items", "Enable Andesite Pebbles?", true).getBoolean();
		enableStringMeshes 				= config.get("Items", "Enable String Meshes?", true).getBoolean();
		enableFlintMeshes 				= config.get("Items", "Enable Flint Meshes?", true).getBoolean();
		enableIronMeshes 				= config.get("Items", "Enable Iron Meshes?", true).getBoolean();
		enableDiamondMeshes				= config.get("Items", "Enable Diamond Meshes?", true).getBoolean();
		enableDollBat 					= config.get("Items", "Enable Bat Doll?", true).getBoolean();
		enableDollChicken 				= config.get("Items", "Enable Chicken Doll?", true).getBoolean();
		enableDollCow 					= config.get("Items", "Enable Cow Doll?", true).getBoolean();
		enableDollDonkey 				= config.get("Items", "Enable Donkey Doll?", true).getBoolean();
		enableDollHorse 				= config.get("Items", "Enable Horse Doll?", true).getBoolean();
		enableDollLlama 				= config.get("Items", "Enable Llama Doll?", true).getBoolean();
		enableDollMule 					= config.get("Items", "Enable Mule Doll?", true).getBoolean();
		enableDollOcelot 				= config.get("Items", "Enable Ocelot Doll?", true).getBoolean();
		enableDollParrot 				= config.get("Items", "Enable Parrot Doll?", true).getBoolean();
		enableDollPig 					= config.get("Items", "Enable Pig Doll?", true).getBoolean();
		enableDollPolarBear 			= config.get("Items", "Enable Polar Bear Doll?", true).getBoolean();
		enableDollRabbit 				= config.get("Items", "Enable Rabbit Doll?", true).getBoolean();
		enableDollRedMooshroom			= config.get("Items", "Enable Red Mooshroom Doll?", true).getBoolean();
		enableDollSheep 				= config.get("Items", "Enable Sheep Doll?", true).getBoolean();
		enableDollVillager				= config.get("Items", "Enable Villager Doll?", true).getBoolean();
		enableDollWolf 					= config.get("Items", "Enable Wolf Doll?", true).getBoolean();
		enableJerky 					= config.get("Items", "Enable Cooked Jerky?", true).getBoolean();
		enableHammerWood 				= config.get("Items", "Enable Wood Hammer?", true).getBoolean();
		enableHammerStone 				= config.get("Items", "Enable Stone Hammer?", true).getBoolean();
		enableHammerGold 				= config.get("Items", "Enable Gold Hammer?", true).getBoolean();
		enableHammerIron 				= config.get("Items", "Enable Iron Hammer?", true).getBoolean();
		enableHammerDiamond 			= config.get("Items", "Enable Diamond Hammer?", true).getBoolean();
		enableGrabberWood 				= config.get("Items", "Enable Wood Grabber?", true).getBoolean();
		enableGrabberStone 				= config.get("Items", "Enable Stone Grabber?", true).getBoolean();
		enableGrabberGold 				= config.get("Items", "Enable Gold Grabber?", true).getBoolean();
		enableGrabberIron 				= config.get("Items", "Enable Iron Grabber?", true).getBoolean();
		enableGrabberDiamond 			= config.get("Items", "Enable Diamond Grabber?", true).getBoolean();
		enableFlintNBlaze 				= config.get("Items", "Enable Flint & Blaze?", true).getBoolean();
		enableStoneDoor 				= config.get("Items", "Enable Stone Door?", true).getBoolean();
		enableWoodBucket				= config.get("Items", "Enable Wood Bucket?", true).getBoolean();
		enableStoneBucket				= config.get("Items", "Enable Stone Bucket?", true).getBoolean();
	}
}
