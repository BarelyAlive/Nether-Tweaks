package mod.nethertweaks.config;

import net.minecraftforge.common.config.Configuration;

public class BlocksItems
{
	//General
	public static boolean enableElderTree 		  = true;
	public static boolean enableHellfayah 		  = true;
	public static boolean enableSalt	 			  = true;

	public static boolean//BlockEnable
	enableSaltBlock 		  = true;
	public static boolean enableDust 				  = true;
	public static boolean enableAshBonePile		  = true;
	public static boolean enableBarrel			  = true;
	private static boolean enableBonfire 			  = true;
	public static boolean enableCondenser 		  = true;
	public static boolean enableStwH 				  = true;
	public static boolean enableFreezer 			  = true;
	public static boolean enableHellmart 			  = true;
	public static boolean enableLiquidImpossibility = true;
	public static boolean enableDistilledWater	  = true;
	public static boolean enableMeanVine 			  = true;
	public static boolean enableNetherrackFurnace   = true;
	public static boolean enableNetherrackGravel 	  = true;
	public static boolean enableSieve 			  = true;
	public static boolean enableCrucible 			  = true;

	public static boolean//ItemEnable
	enableStoneBar 			  = true;
	public static boolean enablePortalCore 		  = true;
	public static boolean enableEndBox	 		  = true;
	public static boolean enableEnderInfusedFrame	  = true;
	public static boolean enableString	 		  = true;
	public static boolean enablePorcelainClay		  = true;
	public static boolean enablePowderOfLight		  = true;
	public static boolean enableMushroomSpores	  = true;
	public static boolean enableGrassSeeds		  = true;
	public static boolean enableCactusSeeds		  = true;
	public static boolean enableSugarcaneSeeds	  = true;
	public static boolean enableCrystalLight 		  = true;
	public static boolean enableAsh			 	  = true;
	public static boolean enableWoodChippings 	  = true;
	public static boolean enableCoiledSword 		  = true;
	public static boolean enablePebbleStone		  = true;
	public static boolean enablePebbleGranite		  = true;
	public static boolean enablePebbleDiorite		  = true;
	public static boolean enablePebbleAndesite	  = true;
	public static boolean enableStringMeshes		  = true;
	public static boolean enableFlintMeshes		  = true;
	public static boolean enableIronMeshes		  = true;
	public static boolean enableDiamondMeshes		  = true;
	public static boolean enableDollBat			  = true;
	public static boolean enableDollChicken		  = true;
	public static boolean enableDollCow			  = true;
	public static boolean enableDollDonkey		  = true;
	public static boolean enableDollHorse			  = true;
	public static boolean enableDollRedMooshroom	  = true;
	public static boolean enableDollMule			  = true;
	public static boolean enableDollOcelot		  = true;
	public static boolean enableDollParrot		  = true;
	public static boolean enableDollRabbit		  = true;
	public static boolean enableDollSheep			  = true;
	public static boolean enableDollLlama			  = true;
	public static boolean enableDollPolarBear		  = true;
	public static boolean enableDollWolf			  = true;
	public static boolean enableDollVillager		  = true;
	public static boolean enableDollPig			  = true;
	public static boolean enableJerky 			  = true;
	public static boolean enableHammerWood 		  = true;
	public static boolean enableHammerStone 		  = true;
	public static boolean enableHammerGold 		  = true;
	public static boolean enableHammerIron 		  = true;
	public static boolean enableHammerDiamond 	  = true;
	public static boolean enableGrabberWood		  = true;
	public static boolean enableGrabberStone 		  = true;
	public static boolean enableGrabberGold		  = true;
	public static boolean enableGrabberIron		  = true;
	public static boolean enableGrabberDiamond 	  = true;
	public static boolean enableFlintNBlaze 		  = true;
	public static boolean enableStoneDoor 		  = true;
	public static boolean enableWoodBucket 		  = true;
	public static boolean enableStoneBucket 		  = true;

	static void load(final Configuration config)
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
		enableString 					= config.get("Items", "Enable String?", true).getBoolean();
		enablePorcelainClay				= config.get("Items", "Enable Porcelain Clay?", true).getBoolean();
		enablePowderOfLight				= config.get("Items", "Enable Powder of Light?", true).getBoolean();
		enableMushroomSpores			= config.get("Items", "Enable Mushroom Spores?", true).getBoolean();
		enableGrassSeeds				= config.get("Items", "Enable Grass Seeds?", true).getBoolean();
		enableCactusSeeds				= config.get("Items", "Enable Cactus Seeds?", true).getBoolean();
		enableSugarcaneSeeds			= config.get("Items", "Enable Sugarcane Seeds?", true).getBoolean();
		enableCrystalLight				= config.get("Items", "Enable Crystal of Light?", true).getBoolean();
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
