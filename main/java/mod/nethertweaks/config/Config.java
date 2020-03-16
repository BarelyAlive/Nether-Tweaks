package mod.nethertweaks.config;

import static mod.nethertweaks.NetherTweaksMod.configDirectory;

import java.io.File;
import java.util.List;

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
	public static int fluidTransferAmount  = 200;

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

	//Blood
	public static int temperatureBlood = 0;
	public static int luminosityBlood  = 15;
	public static int densityBlood	   = 1000;
	public static int viscosityBlood   = 1000;
	public static boolean doesLIVaporize = false;
	public static boolean spawnSkeletons = true;
	public static boolean spawnSlimes 	 = true;
	public static boolean spawnWaterMobs = true;

	//Mechanics
	public static boolean enableThirst	   = true;
	public static boolean useMetricSystem  = true;
	public static boolean enableTeleport   = true;
	public static boolean enableSaltRecipe = true;
	public static boolean waterSources     = false;
	public static String[] blacklistSalt = {"distilled_water"};
	public static String[] grabberBlocks = {"minecraft:cactus", "minecraft:melon_block", "minecraft:web", "minecraft:fern", "minecraft:deadbush"};

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

	public static void preInit()
	{
		final Configuration config = new Configuration(new File(configDirectory, "NetherTweaksMod.cfg"));
		config.load();

		final String tweaks 	= "Tweaks";
		final String world 		= "World";
		final String mechanics 	= "Mechanics";
		final String sieving 	= "Sieving";
		final String barrel 	= "Barrel";
		final String fluids 	= "Fluids";
		final String json 		= "JSON";
		final String compatibility = "Compatibility";

		burnTimeHellfayah 			  = config.get(tweaks, "Burntime of Hellfayah", 12800).getInt();
		burnTimeHellfayahBlock 		  = config.get(tweaks, "Burntime of Hellfayah blocks", 128000).getInt();
		burnTimeFurnace 			  = config.get(tweaks, "Netherrack Furnace worktime in ticks", 1600).getInt();
		grabberBlocks	 			  = config.get(tweaks, "Whick blocks should be tangible with the grabber?", grabberBlocks).getStringList();

		autoExtractItems			  = config.get(tweaks, "Enable machine's auto item extract from inventorys above", true).getBoolean();
		autoOutputItems				  = config.get(tweaks, "Enable machine's auto item output to inventorys at the sides", true).getBoolean();

		fluidTransferAmount			  = config.getInt("Condenser & Freezer max. fluid auto transfer rate in mB/Sec", tweaks, 200, 0, Integer.MAX_VALUE, "0 disables it");

		capacityFreezer				  = config.getInt("Freezer fluid capacity in mb", tweaks, 16000, 1, Integer.MAX_VALUE, "");
		freezeTimeFreezer 			  = config.getInt("Freezer worktime in ticks", tweaks, 6000, 1, Integer.MAX_VALUE, "");
		cooldownFreezer 			  = config.getInt("Freezer cooldown time in ticks", tweaks, 18000, 1, Integer.MAX_VALUE, "");

		dryTimeCondenser 			  = config.getInt("Condenser worktime in ticks", tweaks, 2400, 1, Integer.MAX_VALUE, "");
		capacityCondenser 			  = config.getInt("Condenser fluid capacity in mb", tweaks, 8000, 1, Integer.MAX_VALUE, "");
		cooldownCondenser 			  = config.getInt("Condenser heat up time in ticks", tweaks, 200000, 1, Integer.MAX_VALUE, "");

		durabilityHWood				  = config.getInt("Durability for Wood Hammer", tweaks, 64, 1, Integer.MAX_VALUE, "");
		durabilityHGold				  = config.getInt("Durability for Gold Hammer", tweaks, 80, 1, Integer.MAX_VALUE, "");
		durabilityHStone			  = config.getInt("Durability for Stone Hammer", tweaks, 160, 1, Integer.MAX_VALUE, "");
		durabilityHIron				  = config.getInt("Durability for Iron Hammer", tweaks, 640, 1, Integer.MAX_VALUE, "");
		durabilityHDiamond			  = config.getInt("Durability for Diamond Hammer", tweaks, 5120, 1, Integer.MAX_VALUE, "");

		durabilityGWood				  = config.getInt("Durability for Wood Grabber", tweaks, 64, 1, Integer.MAX_VALUE, "");
		durabilityGGold				  = config.getInt("Durability for Gold Grabber", tweaks, 80, 1, Integer.MAX_VALUE, "");
		durabilityGStone			  = config.getInt("Durability for Stone Grabber", tweaks, 160, 1, Integer.MAX_VALUE, "");
		durabilityGIron				  = config.getInt("Durability for Iron Grabber", tweaks, 640, 1, Integer.MAX_VALUE, "");
		durabilityGDiamond			  = config.getInt("Durability for Diamond Grabber", tweaks, 5120, 1, Integer.MAX_VALUE, "");

		exhaustMultiplierDefault	  = (float) config.get(tweaks, "General thirst multiplier", 1.0d).getDouble();
		exhaustMultiplierNighttime	  = (float) config.get(tweaks, "Thirst multiplier at night", 0.9d).getDouble();

		enableSaltRecipe			  = config.get(world, "Enable salt in-world-crafting?", true).getBoolean();

		enableThirst				  = config.get(mechanics, "Enable thirst?", true).getBoolean();
		useMetricSystem				  = config.get(mechanics, "Use metric System?", true, "").getBoolean();
		enableTeleport				  = config.get(mechanics, "Enable bonfire-to-bonfire teleport?", true, "").getBoolean();
		waterSources				  = config.get(mechanics, "Allow water sources?", false, "").getBoolean();
		blacklistSalt				  = config.get(mechanics, "Fluids that should not work for the salt recipe", blacklistSalt, "Example: minecraft:water").getStringList();

		///Sieve
		sieveSimilarRadius 			  = config.get(sieving, "Sieve Similar Radius", 2).getInt();
		sieveFortuneMaxLevel 		  = config.get(sieving, "Max Level for sieve fortune enchanting?", 3).getInt();
		sieveEfficiencyMaxLevel 	  = config.get(sieving, "Max Level for sieve efficiency enchanting?", 5).getInt();
		sieveLuckOfTheSeaMaxLevel 	  = config.get(sieving, "Max Level for sieve luck of the sea enchanting?", 3).getInt();
		sievesAutoOutput 			  = config.get(sieving, "Sieve Auto output?", false).getBoolean();
		fakePlayersCanSieve 		  = config.get(sieving, "Fake players can sieve?", false).getBoolean();
		setFireToMacroUsers 		  = config.get(sieving, "Set fire to Macro Users?", false).getBoolean();
		enableSieveFortune 			  = config.get(sieving, "Enable sieve fortune enchanting?", true).getBoolean();
		enableSieveEfficiency 		  = config.get(sieving, "Enable sieve effiency enchanting?", true).getBoolean();
		hasteIncreaseSpeed 			  = config.get(sieving, "Does haste increase sieving speed?", true).getBoolean();
		enableSieveLuckOfTheSea 	  = config.get(sieving, "Enable sieve luck of the sea enchanting?", true).getBoolean();
		flattenSieveRecipes 		  = config.get(sieving, "If enabled all mesh tiers can obtain the same", false).getBoolean();
		normalDropPercent 			  = config.getInt("The normal drop percent chance with any sieve mesh?", sieving, 100, 1, Integer.MAX_VALUE, "");

		//Barrel
		compostingTicks 			  = config.get(barrel, "Ticks to form Dirt", 600).getInt();
		woodBarrelMaxTemp 			  = config.get(barrel, "How hot can a fluid be in a wooden barrel (in Kelvin)?", 374).getInt();
		shouldBarrelsFillWithRain 	  = config.get(barrel, "Barrels fill with rain?", true).getBoolean();
		enableBarrelTransformLighting = config.get(barrel, "Enable Barrel transform lighting?", true).getBoolean();

		//Crucible

		//Blood
		densityBlood				  = config.getInt("Density for Blood", fluids, 1000, 0, Integer.MAX_VALUE, "");
		luminosityBlood				  = config.getInt("Luminosity for Blood", fluids, 0, 0, 15, "");
		viscosityBlood				  = config.getInt("Viscosity for Blood", fluids, 1000, 0, Integer.MAX_VALUE, "");
		temperatureBlood			  = config.getInt("Temperatur of Blood (in Kelvin)", fluids, 309, 0, Integer.MAX_VALUE, "");
		doesLIVaporize 				  = config.get(fluids, "Does Blood vaporize?", false).getBoolean();
		spawnSkeletons 				  = config.get(fluids, "Can Blood transform wither skeletons into skeletons", true).getBoolean();
		spawnSlimes 				  = config.get(fluids, "Can Blood transform magma slimes into slimes?", true).getBoolean();
		spawnWaterMobs				  = config.get(fluids, "Do water mobs spawn in the nether? (i.e. Blood)", true).getBoolean();

		//JSON
		enableJSONLoading 			  = config.getBoolean("Enable use of JSON configuration?", json, true, "Disable this if JSON configuration causes problems");

		//Mod-Compatibility
		fluidListIsBlackList		  = config.get(compatibility, "Is the Moo-Fluids-List a blacklist?", true).getBoolean();
		enableMooFluid 				  = config.get(compatibility, "Enable Moo-Fluids-Compatibility?", true).getBoolean();
		enableHarvestcraft			  = config.get(compatibility, "Enable sifting of Harvestcraft-Saplings?", false, "Disabled by default, cuz HC wants you to buy or find its saplings").getBoolean();
		fillAmount 					  = config.get("FillAmount", "How many mB milk should be produced?", 1000).getInt();
		generalItemHandlerCompat 	  = config.get("GeneralItemHandlerCompat", "Use of greater Item-Capability?", false).getBoolean();
		dankNullIntegration 		  = config.get(compatibility, "Enable Dank-Null Integration?", true).getBoolean();
		preventUnidict 				  = config.get(compatibility, "Enable Unidictionary?", true).getBoolean();

		BlocksItems.load(config);

		config.save();
	}
}
