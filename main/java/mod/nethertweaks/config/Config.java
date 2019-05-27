package mod.nethertweaks.config;

import java.io.File;
import java.util.List;

import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.items.Grabber;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class Config
{		
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
	public static boolean fakePlayersCanSieve;
	public static boolean hellworldFeatures;
	public static boolean flattenSieveRecipes;
	public static boolean hasteIncreaseSpeed;
	public static boolean enableSieveEfficiency;
	public static boolean enableSieveFortune;
	public static boolean enableSieveLuckOfTheSea;
	public static int sieveLuckOfTheSeaMaxLevel;
	public static int sieveSimilarRadius;
	public static int sieveFortuneMaxLevel;
	public static int sieveEfficiencyMaxLevel;
	
	//Barrel
	public static boolean shouldBarrelsFillWithRain;
	public static boolean enableBarrelTransformLighting;
	public static int compostingTicks;
	public static int woodBarrelMaxTemp;
	
	//Crucible
	public static boolean thinCrucibleModel;
	
	//Liquid Impossibility
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
	
	// Mod-Compatibility
	public static List<String> fluidList;
	public static int fillAmount;
	public static boolean enableMooFluid;
	public static boolean fluidListIsBlackList;
	public static boolean generalItemHandlerCompat;
	public static boolean dankNullIntegration;
	public static boolean preventUnidict;
	public static String chunkBaseOreDictName;
	public static String dustBaseOreDictName;
	public static String pieceBaseOreDictName;
	public static String ingotBaseOreDictName;
	public static String[] rubberSeed = {"techreborn:rubber_sapling", "ic2:sapling"};
	public static String[] oreDictPreferenceOrder = {"thermalfoundation", "immersiveengineering"};
	
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
	public static boolean enableString;
	
	public static void init()
	{
		Configuration config = new Configuration(new File(NetherTweaksMod.configDirectory, "NetherTweaksMod.cfg"));
		config.load();
		                
        burnTimeHellfayah 				= config.get("Tweak", "Burntime of Hellfayah", 12800).getInt();
        burnTimeHellfayahBlock 			= config.get("Tweak", "Burntime of Hellfayah blocks", 128000).getInt();
        burnTimeFurnace 				= config.get("Tweak", "Netherrack Furnace worktime in ticks", 1600).getInt();
        dryTimeCondenser 				= config.get("Tweak", "Condenser worktime in ticks", 2400).getInt();
        freezeTimeFreezer 				= config.get("Tweak", "Freezer worktime in ticks", 6000).getInt();
                        
        nethDim 						= config.get("World", "To which dimension shall the nether portal send you?", -1).getInt();
        endDim 							= config.get("World", "To which Dimension shall an end portal send you back?", -1).getInt();
        enableTeleport					= config.getBoolean("Mechanics", "Enable bonfire-to-bonfire teleport?", true, "Squares have edges, you know.");
        
        //Ore
		shouldOreDictOreChunks 			= config.get("Compatibilitiy", "Enable OreDict Ore Chunks?", true).getBoolean();
		shouldOreDictOreDusts 			= config.get("Compatibilitiy", "Enable OreDict Ore Dusts?", true).getBoolean();
        
        ///Sieve
        sievesAutoOutput 				= config.get("Sieving", "Sieve Auto output?", false).getBoolean();
        setFireToMacroUsers 			= config.get("Sieving", "Set fire to Macro Users", false).getBoolean();
        sieveSimilarRadius 				= config.get("Sieving", "Sieve Similar Radius", 2).getInt();
        fakePlayersCanSieve 			= config.get("Sieving", "Fake players can sieve", false).getBoolean();
        hellworldFeatures 				= config.get("Sieving", "Enable Hellowrld Features?", false).getBoolean();
        flattenSieveRecipes 			= config.get("Sieving", "If enabled all mesh tiers can obtain the same", false).getBoolean();
        hasteIncreaseSpeed 				= config.get("Sieving", "Does haste increase sieving speed?", true).getBoolean();
        enableSieveEfficiency 			= config.get("Sieving", "Enable sieve effiency enchanting?", true).getBoolean();
        sieveEfficiencyMaxLevel 		= config.get("Sieving", "Max Level for sieve efficiency enchanting", 5).getInt();
        enableSieveFortune 				= config.get("Sieving", "Enable sieve fortune enchanting?", true).getBoolean();
        sieveFortuneMaxLevel 			= config.get("Sieving", "Max Level for sieve fortune enchanting", 3).getInt();
        enableSieveLuckOfTheSea 		= config.get("Sieving", "Enable sieve luck of the sea enchanting?", true).getBoolean();
        sieveLuckOfTheSeaMaxLevel 		= config.get("Sieving", "Max Level for sieve luck of the sea enchanting", 3).getInt();
        
        //Barrel
        shouldBarrelsFillWithRain 		= config.get("Barrel", "Barrels fill with rain?", true).getBoolean();
        compostingTicks 				= config.get("Barrel", "Ticks to form Dirt", 600).getInt();
        enableBarrelTransformLighting 	= config.get("Barrel", "Enable Barrel transform lighting?", true).getBoolean();
        
        //Crucible
        thinCrucibleModel			 	= config.get("Crucible", "Do you want a hin crucible model?", true).getBoolean();
        
        //Liquid Impossibility
        doesLIVaporize 					= config.get("Fluid", "Does Liquid Impossibility vaporize?", false).getBoolean();
        spawnSkeleton 					= config.get("Fluid", "Can Liquid Impossibility transform wither skeletons into skeletons", true).getBoolean();
        spawnSlime 						= config.get("Fluid", "Can Liquid Impossibility transform magma slimes into slimes?", true).getBoolean();
        spawnWaterMobs					= config.get("Fluid", "Do water mobs spawn in the nether? (i.e. Liquid Impossibility)", true).getBoolean();
        
        isHellworld 					= config.get("WorldType", "Are you playing Hellworld?", true).getBoolean();
        normalDropPercent 				= config.get("World", "The normal drop percent chance outside Hellworld", 100).getInt();
        woodBarrelMaxTemp 				= config.get("World", "Hwo hot can a fluid be in a wodden barrel?", 301).getInt();
        grabberBlocks	 				= config.get("Misc", "Whick blocks should be tangible with the grabber?", Grabber.getTangible()).getStringList();
        
        //JSON
        enableJSONLoading 				= config.getBoolean("Enable use of JSON configuration?", "JSON", true, "Disable this if JSON Confugration causes problems");
        
        //Mod-Compatibility
        enableMooFluid 					= config.get("Compatibility", "Enable Moo-Fluids-Compatibility", true).getBoolean();
        fillAmount 						= config.get("FillAmount", "How many mB milk should be produced", 1000).getInt();
        rubberSeed 						= config.getStringList("The rubber saplings ntm should support", "Compatibility", rubberSeed, "Leaves are green, you know.");
        oreDictPreferenceOrder 			= config.getStringList("OreDict preference order", "Compatibility", oreDictPreferenceOrder, "Coffe has caffeine, you know.");
        generalItemHandlerCompat 		= config.get("GeneralItemHandlerCompat", "Use of greater Item-Capability?", false).getBoolean();
        dankNullIntegration 			= config.get("Compatibility", "Enable Dank-Null Integration?", true).getBoolean();
        preventUnidict 					= config.get("Compatibility", "Enable Unidictionary?", true).getBoolean();
    	chunkBaseOreDictName 			= config.get("ChunkBaseName", "Base Name for Chunks", "Chunk").getString();
    	dustBaseOreDictName 			= config.get("DustBaseName", "Base Name for Dusts", "Dust").getString();
    	pieceBaseOreDictName 			= config.get("PieceBaseName", "Base Name for Pieces", "Piece").getString();
    	ingotBaseOreDictName 			= config.get("IngotBaseName", "Base Name for Ingots", "Ingot").getString();
        
    	//BlockEnable
    	config.addCustomCategoryComment("Blocks", "Disabling one of these may break mechanics in NTM!");
    	enableMultiBlock 				= config.get("Blocks", "Enable Hellfayah Block, Ore & Salt?", true).getBoolean();
    	enableDust 						= config.get("Blocks", "Enable Dust?", true).getBoolean();
    	enableBarrel 					= config.get("Blocks", "Enable Barrel?", true).getBoolean();
    	enableBonfire 					= config.get("Blocks", "Enable Bonfire?", true).getBoolean();
    	enableCondenser 				= config.get("Blocks", "Enable Condenser?", true).getBoolean();
    	enableStwH 						= config.get("Blocks", "Enable Stairway to Heaven?", true).getBoolean();
    	enableFreezer 					= config.get("Blocks", "Enable Freezer?", true).getBoolean();
    	enableHellmart 					= config.get("Blocks", "Enable Hellmart?", true).getBoolean();
    	enableLiquidImpossibility 		= config.get("Blocks", "Enable Liquid Impossibility?", true).getBoolean();
    	enableMeanVine 					= config.get("Blocks", "Enable Mean Vines?", true).getBoolean();
    	enableElderTree 				= config.get("Blocks", "Enable Elder Tree?", true).getBoolean();
    	enableNetherrackFurnace 		= config.get("Blocks", "Enable Elderrack Furnace?", true).getBoolean();
    	enableNetherrackGravel			= config.get("Blocks", "Enable Netherrack Gravel?", true).getBoolean();
    	enableSieve 					= config.get("Blocks", "Enable Sieve?", true).getBoolean();
    	enableCrucible 					= config.get("Blocks", "Enable Crucible?", true).getBoolean();
    	
    	//ItemEnable
    	config.addCustomCategoryComment("Items", "Disabling one of these may break mechanics in NTM!");
    	enableMultiItem 				= config.get("Items", "Enable Multi Item?", true).getBoolean();
    	enableSeed 						= config.get("Items", "Enable Seeds?", true).getBoolean();
    	enableCrystalLight				= config.get("Items", "Enable Crystal of Light?", true).getBoolean();
    	enableCrystalEnder				= config.get("Items", "Enable Crystal of Light?", true).getBoolean();
    	enablePebbles 					= config.get("Items", "Enable Pebbles?", true).getBoolean();
    	enableMeshes 					= config.get("Items", "Enable Meshes?", true).getBoolean();
    	enableDolls 					= config.get("Items", "Enable Dolls?", true).getBoolean();
    	enableJerky 					= config.get("Items", "Enable Cooked Jerky?", true).getBoolean();
    	enablePickAxeNetherrack 		= config.get("Items", "Enable Netherrack Pickaxe?", true).getBoolean();
    	enablePickAxeNetherbrick 		= config.get("Items", "Enable Netherbrick Pickaxe?", true).getBoolean();
    	enableGrabber					= config.get("Items", "Enable Grabber?", true).getBoolean();
    	enableHammerWood 				= config.get("Items", "Enable Wood Hammer?", true).getBoolean();
    	enableHammerStone 				= config.get("Items", "Enable Stone Hammer?", true).getBoolean();
    	enableHammerGold 				= config.get("Items", "Enable Gold Hammer?", true).getBoolean();
    	enableHammerIron 				= config.get("Items", "Enable Iron Hammer?", true).getBoolean();
    	enableHammerDiamond 			= config.get("Items", "Enable Diamond Hammer?", true).getBoolean();
    	enableFlintNBlaze 				= config.get("Items", "Enable Flint & Blaze?", true).getBoolean();
    	enableStoneDoor 				= config.get("Items", "Enable Stone Door?", true).getBoolean();
    	enableElderDoor 				= config.get("Items", "Enable Elder Door?", true).getBoolean();
    	enableWoodBucket				= config.get("Items", "Enable Wood Bucket?", true).getBoolean();
    	enableStoneBucket				= config.get("Items", "Enable Stone Bucket?", true).getBoolean();
    	enableString					= config.get("Items", "Enable Mean Vine String?", true).getBoolean();
    	
        config.save();
	}

}
