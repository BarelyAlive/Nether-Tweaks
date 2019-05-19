package mod.nethertweaks;

import java.io.File;
import java.util.List;

import mod.nethertweaks.items.Grabber;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class Config {
			
	//int
	public static int endDim;
	public static int nethDim;
	public static int burnTimeFurnace;
	public static int dryTimeCondenser;
	public static int freezeTimeFreezer;
	public static int burnTimeHellfayah;
	public static int burnTimeHellfayahBlock;
	public static int normalDropPercent;
	
	//Ore
	public static boolean shouldOreDictOreChunks;
	public static boolean shouldOreDictOreDusts;
	
	//Sieve
	public static boolean sievesAutoOutput;
	public static boolean setFireToMacroUsers;
	public static int sieveSimilarRadius;
	public static boolean fakePlayersCanSieve;
	public static boolean hellworldFeatures;
	public static boolean flattenSieveRecipes;
	public static boolean hasteIncreaseSpeed;
	public static boolean enableSieveEfficiency;
	public static int sieveEfficiencyMaxLevel;
	public static boolean enableSieveFortune;
	public static int sieveFortuneMaxLevel;
	public static boolean enableSieveLuckOfTheSea;
	public static int sieveLuckOfTheSeaMaxLevel;
	
	//Barrel
	public static boolean shouldBarrelsFillWithRain;
	public static boolean enableBarrelTransformLighting;
	public static int compostingTicks;
	
	//Crucible
	public static boolean thinCrucibleModel;
	
	//Liquid Impossibility
	public static boolean spawnPig;
	public static boolean spawnSkeleton;
	public static boolean spawnSlime;
	public static boolean doesLIVaporize = false;
	public static boolean healAnimals;
	public static boolean spawnWaterMobs;
	
	public static int[] allowedDims = {-1, 1};
	public static boolean isHellworld;
	public static boolean enableTeleport;
	public static String[] grabberBlocks;
	
	//JSON
	public static boolean enableJSONLoading;
	public static int woodBarrelMaxTemp;
	
	// Mod-Compatibility
	public static boolean enableMooFluid;
	public static List<String> fluidList;
	public static boolean fluidListIsBlackList;
	public static int fillAmount;
	public static String[] rubberSeed = {"techreborn:rubber_sapling", "ic2:sapling"};
	public static String[] oreDictPreferenceOrder = {"thermalfoundation", "immersiveengineering"};
	public static boolean generalItemHandlerCompat;
	public static boolean dankNullIntegration;
	public static boolean preventUnidict;
	public static String chunkBaseOreDictName;
	public static String dustBaseOreDictName;
	public static String pieceBaseOreDictName;
	public static String ingotBaseOreDictName;
	
	//BlockEnable
	public static boolean enableMultiBlock;
	public static boolean enableDust;
	public static boolean enableBarrel;
	public static boolean enableBonfire;
	public static boolean enableCondenser;
	public static boolean enableStwH;
	public static boolean enableFreezer;
	public static boolean enableHellmart;
	public static boolean enableLiquidImpossibility;
	public static boolean enableMeanVine;
	public static boolean enableElderTree;
	public static boolean enableNetherrackFurnace;
	public static boolean enableNetherrackGravel;
	public static boolean enableSieve;
	public static boolean enableCrucible;
	
	//ItemEnable
	public static boolean enableMultiItem;
	public static boolean enableSeed;
	public static boolean enableCrystalLight;
	public static boolean enableCrystalEnder;
	public static boolean enablePebbles;
	public static boolean enableMeshes;
	public static boolean enableDolls;
	public static boolean enableJerky;
	public static boolean enablePickAxeNetherrack;
	public static boolean enablePickAxeNetherbrick;
	public static boolean enableGrabber;
	public static boolean enableHammerWood;
	public static boolean enableHammerStone;
	public static boolean enableHammerGold;
	public static boolean enableHammerIron;
	public static boolean enableHammerDiamond;
	public static boolean enableFlintNBlaze;
	public static boolean enableStoneDoor;
	public static boolean enableElderDoor;
	public static boolean enableWoodBucket;
	public static boolean enableStoneBucket;
	
	public static void init()
	{
		Configuration config = new Configuration(new File(NetherTweaksMod.configDirectory, "NetherTweaksMod.cfg"));
		config.load();
		
		allowedDims 					= config.get("WorldType", "Allowed dimensions in Hellworld", allowedDims).getIntList();
                
        burnTimeHellfayah 				= config.get("Tweak", "Duration of Hellfayah", 12800).getInt();
        burnTimeHellfayahBlock 			= config.get("Tweak", "Duration of Hellfayah blocks", 128000).getInt();
        burnTimeFurnace 				= config.get("Tweak", "Netherrack Furnace worktime in ticks.", 1600).getInt();
        dryTimeCondenser 				= config.get("Tweak", "Condenser worktime in ticks.", 2400).getInt();
        freezeTimeFreezer 				= config.get("Tweak", "Freezer worktime in ticks.", 6000).getInt();
                        
        nethDim 						= config.get("World", "To which dimension shall the nether portal send you?", -1).getInt();
        endDim 							= config.get("World", "To which Dimension shall an end portal send you back?", -1).getInt();
        enableTeleport					= config.getBoolean("Mechanics", "Enable bonfire-to-bonfire teleport?", true, "Squares have edges, you know.");
        
        //Ore
		shouldOreDictOreChunks 			= config.get("Compatibilitiy", "OreDictOreChunks", true).getBoolean();
		shouldOreDictOreDusts 			= config.get("Compatibilitiy", "OreDictOreDusts", true).getBoolean();
        
        ///Sieve
        sievesAutoOutput 				= config.get("Sieving", "sievesAutoOutput", false).getBoolean();
        setFireToMacroUsers 			= config.get("Sieving", "setFireToMacroUsers", false).getBoolean();
        sieveSimilarRadius 				= config.get("Sieving", "sieveSimilarRadius", 2).getInt();
        fakePlayersCanSieve 			= config.get("Mechanics", "fakePlayersCanSieve", false).getBoolean();
        hellworldFeatures 				= config.get("Sieving", "setFireToMacroUsers", false).getBoolean();
        flattenSieveRecipes 			= config.get("Sieving", "If enablednall mesh tiers can obtain the same", false).getBoolean();
        hasteIncreaseSpeed 				= config.get("Sieving", "Does haste increase sieving speed?", true).getBoolean();
        enableSieveEfficiency 			= config.get("Sieving", "Enable sieve effiency enchanting?", true).getBoolean();
        sieveEfficiencyMaxLevel 		= config.get("Sieving", "Max Level for sieve efficiency enchanting", 5).getInt();
        enableSieveFortune 				= config.get("Sieving", "Enable sieve fortune enchanting?", true).getBoolean();
        sieveFortuneMaxLevel 			= config.get("Sieving", "Max Level for sieve fortune enchanting", 3).getInt();
        enableSieveLuckOfTheSea 		= config.get("Sieving", "Enable sieve luck of the sea enchanting?", true).getBoolean();
        sieveLuckOfTheSeaMaxLevel 		= config.get("Sieving", "Max Level for sieve luck of the sea enchanting", 3).getInt();
        
        //Barrel
        shouldBarrelsFillWithRain 		= config.get("Mechanics", "barrelsFillWithRain", true).getBoolean();
        compostingTicks 				= config.get("Composting", "ticksToFormDirt", 600).getInt();
        enableBarrelTransformLighting 	= config.get("Misc", "enableBarrelTransformLighting", true).getBoolean();
        
        //Crucible
        thinCrucibleModel			 	= config.get("Misc", "Do you want a hin crucible model?", true).getBoolean();
        
        //Liquid Impossibility
        doesLIVaporize 					= config.get("Fluid", "Does Liquid Impossibility vaporize?", false).getBoolean();
        spawnPig 						= config.get("Fluid", "Can Liquid Impossibility transform pigmans into pigs?", true).getBoolean();
        spawnSkeleton 					= config.get("Fluid", "Can Liquid Impossibility transform wither skeletons into skeletons", true).getBoolean();
        spawnSlime 						= config.get("Fluid", "Can Liquid Impossibility transform magma slimes into slimes?", true).getBoolean();
        spawnWaterMobs					= config.getBoolean("Do water mobs spawn in the nether? (i.e. Liquid Impossibility)", "Fluid", true, "Water is wet, you know.");
        
        isHellworld 					= config.get("WorldType", "Are you playing Hellworld?", true).getBoolean();
        normalDropPercent 				= config.get("World", "The normal drop percent chance outside Hellworld", 100).getInt();
        woodBarrelMaxTemp 				= config.get("World", "Hwo hot can a fluid be in a wodden barrel?", 301).getInt();
        grabberBlocks	 				= config.get("Misc", "Whick blocks should be tangible with the grabber?", Grabber.getTangible()).getStringList();
        
        //JSON
        enableJSONLoading 				= config.get("JSON", "Enable use of JSON configuration?", true).getBoolean();
        
        // Mod-Compatibility
        enableMooFluid 					= config.get("MooFluids", "Enable Moo-Fluids-Compatibility", true).getBoolean();
        fillAmount 						= config.get("FillAmount", "How many mB milk should be produced", 1000).getInt();
        rubberSeed 						= config.getStringList("RubberSeeds", "The rubber saplings ntm should support", rubberSeed, "Leaves are green, you know.");
        oreDictPreferenceOrder 			= config.getStringList("OreDict preference order", "Compat", oreDictPreferenceOrder, "Coffe has caffeine, you know.");
        generalItemHandlerCompat 		= config.get("GeneralItemHandlerCompat", "Use of greater Item-Capability?", false).getBoolean();
        dankNullIntegration 			= config.get("DankNullIntegration", "Enable Dank-Null Integration?", true).getBoolean();
        preventUnidict 					= config.get("PreventUnidict", "Enable Unidictionary?", true).getBoolean();
    	chunkBaseOreDictName 			= config.get("ChunkBaseName", "Base Name for Chunks", "Chunk").getString();
    	dustBaseOreDictName 			= config.get("DustBaseName", "Base Name for Dusts", "Dust").getString();
    	pieceBaseOreDictName 			= config.get("PieceBaseName", "Base Name for Pieces", "Piece").getString();
    	ingotBaseOreDictName 			= config.get("IngotBaseName", "Base Name for Ingots", "Ingot").getString();
        
    	//BlockEnable
    	config.addCustomCategoryComment("Blocks", "Disabling one of these may break mechanics in NTM!");
    	enableMultiBlock 				= config.getBoolean("Enable Hellfayah Block, Ore & Salt?", "Blocks", true, "");
    	enableDust 						= config.getBoolean("Enable Dust?", "Blocks", true, "");
    	enableBarrel 					= config.getBoolean("Enable Barrel?", "Blocks", true, "");
    	enableBonfire 					= config.getBoolean("Enable Bonfire?", "Blocks", true, "");
    	enableCondenser 				= config.getBoolean("Enable Condenser?", "Blocks", true, "");
    	enableStwH 						= config.getBoolean("Enable StwH?", "Blocks", true, "");
    	enableFreezer 					= config.getBoolean("Enable Freezer?", "Blocks", true, "");
    	enableHellmart 					= config.getBoolean("Enable Hellmart?", "Blocks", true, "");
    	enableLiquidImpossibility 		= config.getBoolean("Enable Liquid Impossibility?", "Blocks", true, "");
    	enableMeanVine 					= config.getBoolean("Enable Mean Vines?", "Blocks", true, "");
    	enableElderTree 				= config.getBoolean("Enable Elder Tree?", "Blocks", true, "");
    	enableNetherrackFurnace 		= config.getBoolean("Enable Elderrack Furnace?", "Blocks", true, "");
    	enableNetherrackGravel			= config.getBoolean("Enable Netherrack Gravel?", "Blocks", true, "");
    	enableSieve 					= config.getBoolean("Enable Sieve?", "Blocks", true, "");
    	enableCrucible 					= config.getBoolean("Enable Crucible?", "Blocks", true, "");
    	
    	//ItemEnable
    	config.addCustomCategoryComment("Items", "Disabling one of these may break mechanics in NTM!");
    	enableMultiItem 				= config.getBoolean("Enable Multi Item?", "Items", true, "");
    	enableSeed 						= config.getBoolean("Enable Seeds?", "Items", true, "");
    	enableCrystalLight				= config.getBoolean("Enable Crystal of Light?", "Items", true, "");
    	enableCrystalEnder				= config.getBoolean("Enable Crystal of Light?", "Items", true, "");
    	enablePebbles 					= config.getBoolean("Enable Pebbles?", "Items", true, "");
    	enableMeshes 					= config.getBoolean("Enable Meshes?", "Items", true, "");
    	enableDolls 					= config.getBoolean("Enable Dolls?", "Items", true, "");
    	enableJerky 					= config.getBoolean("Enable Cooked Jerky?", "Items", true, "");
    	enablePickAxeNetherrack 		= config.getBoolean("Enable Netherrack Pickaxe?", "Items", true, "");
    	enablePickAxeNetherbrick 		= config.getBoolean("Enable Netherbrick Pickaxe?", "Items", true, "");
    	enableGrabber					= config.getBoolean("Enable Grabber?", "Items", true, "");
    	enableHammerWood 				= config.getBoolean("Enable Wood Hammer?", "Items", true, "");
    	enableHammerStone 				= config.getBoolean("Enable Stone Hammer?", "Items", true, "");
    	enableHammerGold 				= config.getBoolean("Enable Gold Hammer?", "Items", true, "");
    	enableHammerIron 				= config.getBoolean("Enable Iron Hammer?", "Items", true, "");
    	enableHammerDiamond 			= config.getBoolean("Enable Diamond Hammer?", "Items", true, "");
    	enableFlintNBlaze 				= config.getBoolean("Enable Flint & Blaze?", "Items", true, "");
    	enableStoneDoor 				= config.getBoolean("Enable Stone Door?", "Items", true, "");
    	enableElderDoor 				= config.getBoolean("Enable Elder Door?", "Items", true, "");
    	enableWoodBucket				= config.getBoolean("Enable Wood Bucket?", "Items", true, "");
    	enableStoneBucket				= config.getBoolean("Enable Stone Bucket?", "Items", true, "");
    	
        config.save();
	}

}
