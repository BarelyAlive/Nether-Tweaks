package mod.nethertweaks.config;

import java.io.File;
import java.util.List;

import mod.nethertweaks.NetherTweaksMod;
import net.minecraftforge.common.config.Configuration;

public class Config
{
	//int
	public static int burnTimeFurnace 		 = 1600;
	public static int burnTimeHellfayah 	 = 12800;
	public static int burnTimeHellfayahBlock = 128000;

	public static int durabilityHWood 	 = 64;
	public static int durabilityHGold 	 = 80;
	public static int durabilityHStone 	 = 160;
	public static int durabilityHIron 	 = 640;
	public static int durabilityHDiamond = 5120;

	public static int durabilityGWood 	 = 64;
	public static int durabilityGGold 	 = 80;
	public static int durabilityGStone 	 = 160;
	public static int durabilityGIron 	 = 640;
	public static int durabilityGDiamond = 5120;

	public static float exhaustMultiplierDefault = 1.0f;
	public static float exhaustMultiplierNighttime = 0.9f;

	public static boolean autoOutputItems  = true;
	public static boolean autoExtractItems = true;

	//Condenser & Freezer
	public static int fluidOutputAmount  = 200;

	//Freezer
	public static int freezeTimeFreezer  = 6000;
	public static int capacityFreezer  	 = 16000;
	public static int cooldownFreezer  	 = 18000;

	//Condenser
	public static int dryTimeCondenser   = 2400;
	public static int capacityCondenser  = 8000;
	public static int cooldownCondenser	 = 200000;

	//Sieve
	public static int sieveSimilarRadius 		  = 2;
	public static int sieveLuckOfTheSeaMaxLevel   = 3;
	public static int sieveFortuneMaxLevel 		  = 3;
	public static int sieveEfficiencyMaxLevel 	  = 5;
	public static int normalDropPercent 		  = 100;
	public static boolean setFireToMacroUsers 	  = true;
	public static boolean hellworldFeatures 	  = true;
	public static boolean hasteIncreaseSpeed 	  = true;
	public static boolean enableSieveEfficiency   = true;
	public static boolean enableSieveFortune 	  = true;
	public static boolean enableSieveLuckOfTheSea = true;
	public static boolean sievesAutoOutput 		  = false;
	public static boolean fakePlayersCanSieve 	  = false;
	public static boolean flattenSieveRecipes 	  = false;

	//Barrel
	public static int compostingTicks 	= 600;
	public static int woodBarrelMaxTemp = 301;
	public static boolean shouldBarrelsFillWithRain 	= true;
	public static boolean enableBarrelTransformLighting = true;

	//Crucible

	//Liquid Impossibility
	public static int temperatureLI = 0;
	public static int luminosityLI  = 15;
	public static int densityLI 	= 1000;
	public static int viscosityLI   = 1000;
	public static boolean doesLIVaporize = false;
	public static boolean spawnSkeletons = true;
	public static boolean spawnSlimes 	 = true;
	public static boolean spawnWaterMobs = true;

	//Mechanics
	public static boolean enableThirst	   = true;
	public static boolean useMetricSystem  = true;
	public static boolean enableTeleport   = true;
	public static boolean enableSaltRecipe = true;
	public static boolean waterSources   = true;
	public static String[] blacklistSalt = {"distilled_water"};
	public static String[] grabberBlocks = new String[] {"minecraft:cactus", "minecraft:melon_block", "minecraft:web", "minecraft:fern", "minecraft:deadbush"};

	//JSON
	public static boolean enableJSONLoading = true;

	// Mod-Compatibility
	public static List<String> fluidList;
	public static int fillAmount = 1000;
	public static boolean enableMooFluid 			= true;
	public static boolean preventUnidict 			= true;
	public static boolean dankNullIntegration 		= true;
	public static boolean fluidListIsBlackList 		= true;
	public static boolean enableHarvestcraft 		= false;
	public static boolean generalItemHandlerCompat  = false;
	public static String chunkBaseOreDictName = "Chunk";
	public static String dustBaseOreDictName  = "Dust";
	public static String pieceBaseOreDictName = "Piece";
	public static String ingotBaseOreDictName = "Ingot";
	public static String[] oreDictPreferenceOrder = {"thermalfoundation", "immersiveengineering"};

	public static void init()
	{
		Configuration config = new Configuration(new File(NetherTweaksMod.configDirectory, "NetherTweaksMod.cfg"));
		config.load();

		burnTimeHellfayah 				= config.get("Tweak", "Burntime of Hellfayah", 12800).getInt();
		burnTimeHellfayahBlock 			= config.get("Tweak", "Burntime of Hellfayah blocks", 128000).getInt();
		burnTimeFurnace 				= config.get("Tweak", "Netherrack Furnace worktime in ticks", 1600).getInt();
		grabberBlocks	 				= config.get("Tweak", "Whick blocks should be tangible with the grabber?", grabberBlocks).getStringList();

		autoExtractItems				= config.get("Tweak", "Enable machine's auto item extract from inventorys above", true).getBoolean();
		autoOutputItems					= config.get("Tweak", "Enable machine's auto item output to inventorys at the sides", true).getBoolean();

		fluidOutputAmount 				= config.getInt("Condenser & Freezer max. fluid auto transfer rate in mB/Sec", "Tweak", 200, 0, Integer.MAX_VALUE, "0 disables it");

		capacityFreezer					= config.getInt("Freezer fluid capacity in mb", "Tweak", 16000, 1, Integer.MAX_VALUE, "");
		freezeTimeFreezer 				= config.getInt("Freezer worktime in ticks", "Tweak", 6000, 1, Integer.MAX_VALUE, "");
		cooldownFreezer 				= config.getInt("Freezer cooldown time in ticks", "Tweak", 18000, 1, Integer.MAX_VALUE, "");

		dryTimeCondenser 				= config.getInt("Condenser worktime in ticks", "Tweak", 2400, 1, Integer.MAX_VALUE, "");
		capacityCondenser 				= config.getInt("Condenser fluid capacity in mb", "Tweak", 8000, 1, Integer.MAX_VALUE, "");
		cooldownCondenser 				= config.getInt("Condenser heat up time in ticks", "Tweak", 200000, 1, Integer.MAX_VALUE, "");

		durabilityHWood					= config.getInt("Durability for Wood Hammer", "Tweak", 64, 1, Integer.MAX_VALUE, "");
		durabilityHGold					= config.getInt("Durability for Gold Hammer", "Tweak", 80, 1, Integer.MAX_VALUE, "");
		durabilityHStone				= config.getInt("Durability for Stone Hammer", "Tweak", 160, 1, Integer.MAX_VALUE, "");
		durabilityHIron					= config.getInt("Durability for Iron Hammer", "Tweak", 640, 1, Integer.MAX_VALUE, "");
		durabilityHDiamond				= config.getInt("Durability for Diamond Hammer", "Tweak", 5120, 1, Integer.MAX_VALUE, "");

		durabilityGWood					= config.getInt("Durability for Wood Grabber", "Tweak", 64, 1, Integer.MAX_VALUE, "");
		durabilityGGold					= config.getInt("Durability for Gold Grabber", "Tweak", 80, 1, Integer.MAX_VALUE, "");
		durabilityGStone				= config.getInt("Durability for Stone Grabber", "Tweak", 160, 1, Integer.MAX_VALUE, "");
		durabilityGIron					= config.getInt("Durability for Iron Grabber", "Tweak", 640, 1, Integer.MAX_VALUE, "");
		durabilityGDiamond				= config.getInt("Durability for Diamond Grabber", "Tweak", 5120, 1, Integer.MAX_VALUE, "");

		exhaustMultiplierDefault		= (float) config.get("Tweak", "General thirst multiplier", 1f).getDouble();
		exhaustMultiplierNighttime		= (float) config.get("Tweak", "Thirst multiplier at night", 0.9f).getDouble();

		enableSaltRecipe				= config.get("World", "Enable salt in-world-crafting?", true).getBoolean();

		enableThirst					= config.get("Mechanics", "Enable thirst?", true).getBoolean();
		useMetricSystem					= config.get("Mechanics", "Use metric System?", true, "").getBoolean();
		enableTeleport					= config.get("Mechanics", "Enable bonfire-to-bonfire teleport?", true, "").getBoolean();
		waterSources					= config.get("Mechanics", "Allow water sources?", false, "").getBoolean();
		blacklistSalt					= config.get("Mechanics", "Fluids that sould not work for the salt recipe", blacklistSalt, "Example: minecraft:water").getStringList();

		///Sieve
		sieveSimilarRadius 				= config.get("Sieving", "Sieve Similar Radius", 2).getInt();
		sieveFortuneMaxLevel 			= config.get("Sieving", "Max Level for sieve fortune enchanting?", 3).getInt();
		sieveEfficiencyMaxLevel 		= config.get("Sieving", "Max Level for sieve efficiency enchanting?", 5).getInt();
		sieveLuckOfTheSeaMaxLevel 		= config.get("Sieving", "Max Level for sieve luck of the sea enchanting?", 3).getInt();
		normalDropPercent 				= config.getInt("The normal drop percent chance with any sieve mesh?", "Sieving", 100, 1, Integer.MAX_VALUE, "");
		sievesAutoOutput 				= config.get("Sieving", "Sieve Auto output?", false).getBoolean();
		fakePlayersCanSieve 			= config.get("Sieving", "Fake players can sieve?", false).getBoolean();
		setFireToMacroUsers 			= config.get("Sieving", "Set fire to Macro Users?", false).getBoolean();
		hellworldFeatures 				= config.get("Sieving", "Enable Hellworld features?", false).getBoolean();
		enableSieveFortune 				= config.get("Sieving", "Enable sieve fortune enchanting?", true).getBoolean();
		enableSieveEfficiency 			= config.get("Sieving", "Enable sieve effiency enchanting?", true).getBoolean();
		hasteIncreaseSpeed 				= config.get("Sieving", "Does haste increase sieving speed?", true).getBoolean();
		enableSieveLuckOfTheSea 		= config.get("Sieving", "Enable sieve luck of the sea enchanting?", true).getBoolean();
		flattenSieveRecipes 			= config.get("Sieving", "If enabled all mesh tiers can obtain the same", false).getBoolean();

		//Barrel
		compostingTicks 				= config.get("Barrel", "Ticks to form Dirt", 600).getInt();
		woodBarrelMaxTemp 				= config.get("Barrel", "How hot can a fluid be in a wooden barrel (in Kelvin)?", 301).getInt();
		shouldBarrelsFillWithRain 		= config.get("Barrel", "Barrels fill with rain?", true).getBoolean();
		enableBarrelTransformLighting 	= config.get("Barrel", "Enable Barrel transform lighting?", true).getBoolean();

		//Crucible

		//Liquid Impossibility
		densityLI						= config.getInt("Density for Liquid Impossibility", "Fluid", 1000, 0, Integer.MAX_VALUE, "");
		luminosityLI					= config.getInt("Luminosity for Liquid Impossibility", "Fluid", 15, 0, 15, "");
		viscosityLI						= config.getInt("Viscosity for Liquid Impossibility", "Fluid", 1000, 0, Integer.MAX_VALUE, "");
		temperatureLI					= config.getInt("Temperatur of Liquid Impossibility in Kelvin", "Fluid", 0, 0, Integer.MAX_VALUE, "");
		doesLIVaporize 					= config.get("Fluid", "Does Liquid Impossibility vaporize?", false).getBoolean();
		spawnSkeletons 					= config.get("Fluid", "Can Liquid Impossibility transform wither skeletons into skeletons", true).getBoolean();
		spawnSlimes 					= config.get("Fluid", "Can Liquid Impossibility transform magma slimes into slimes?", true).getBoolean();
		spawnWaterMobs					= config.get("Fluid", "Do water mobs spawn in the nether? (i.e. Liquid Impossibility)", true).getBoolean();

		//JSON
		enableJSONLoading 				= config.getBoolean("Enable use of JSON configuration?", "JSON", true, "Disable this if JSON configuration causes problems");

		//Mod-Compatibility
		fluidListIsBlackList			= config.get("Compatibility", "Is the Moo-Fluids-List a blacklist?", true).getBoolean();
		enableMooFluid 					= config.get("Compatibility", "Enable Moo-Fluids-Compatibility?", true).getBoolean();
		enableHarvestcraft				= config.get("Compatibility", "Enable sifting of Harvestcraft-Saplings?", false, "Disabled by default, cuz HC wants you to buy or find its saplings").getBoolean();
		fillAmount 						= config.get("FillAmount", "How many mB milk should be produced?", 1000).getInt();
		oreDictPreferenceOrder 			= config.getStringList("OreDict preference order", "Compatibility", oreDictPreferenceOrder, "");
		generalItemHandlerCompat 		= config.get("GeneralItemHandlerCompat", "Use of greater Item-Capability?", false).getBoolean();
		dankNullIntegration 			= config.get("Compatibility", "Enable Dank-Null Integration?", true).getBoolean();
		preventUnidict 					= config.get("Compatibility", "Enable Unidictionary?", true).getBoolean();
		chunkBaseOreDictName 			= config.get("ChunkBaseName", "Base Name for Chunks", "Chunk").getString();
		dustBaseOreDictName 			= config.get("DustBaseName", "Base Name for Dusts", "Dust").getString();
		pieceBaseOreDictName 			= config.get("PieceBaseName", "Base Name for Pieces", "Piece").getString();
		ingotBaseOreDictName 			= config.get("IngotBaseName", "Base Name for Ingots", "Ingot").getString();

		BlocksItems.load(config);

		config.save();
	}
}
