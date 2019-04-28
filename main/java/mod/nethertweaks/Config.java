package mod.nethertweaks;

import java.io.File;
import java.util.List;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class Config {
			
	//int
	public static int endDim;
	public static int StwtHDimension;
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
	
	//Liquid Impossibility
	public static boolean spawnPig;
	public static boolean spawnSkeleton;
	public static boolean spawnSlime;
	public static boolean doesLIVaporize = false;
	public static boolean healAnimals;
	
	public static int[] allowedDims = {-1, 1};
	public static boolean isHellworld;
	
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
	public static boolean enableElderLeaves;
	public static boolean enableElderLog;
	public static boolean enableNetherrackFurnace;
	public static boolean enableElderSapling;
	public static boolean enableElderSlab;
	public static boolean enableNetherrackGravel;
	public static boolean enableElderWood;
	public static boolean enableSieve;
	
	//ItemEnable
	public static boolean enableMultiItem;
	public static boolean enableSeed;
	public static boolean enableCrystals;
	public static boolean enablePebbles;
	public static boolean enableMeshes;
	public static boolean enableDolls;
	public static boolean enableJerky;
	public static boolean enablePickAxeNetherrack;
	public static boolean enablePickAxeNetherbrick;
	public static boolean enableHammerWood;
	public static boolean enableHammerStone;
	public static boolean enableHammerGold;
	public static boolean enableHammerIron;
	public static boolean enableHammerDiamond;
	public static boolean enableFlintNBlaze;
	public static boolean enableStoneDoor;
	
	public static void loadConfigs()
	{
		Configuration config = new Configuration(new File(NetherTweaksMod.configDirectory, "NetherTweaksMod.cfg"));
		config.load();
		
		allowedDims 					= config.get("WorldType", "Allowed dimensions in Hellworld", allowedDims).getIntList();
                
        burnTimeHellfayah 				= config.get("Tweak", "Duration of Hellfayah", 12800).getInt();
        burnTimeHellfayahBlock 			= config.get("Tweak", "Duration of Hellfayah blocks", 128000).getInt();
        burnTimeFurnace 				= config.get("Tweak", "How long should the burntime of the Netherrack Furnace be?", 1600).getInt();
        dryTimeCondenser 				= config.get("Tweak", "How long should the drytime of the condenser be?", 2400).getInt();
        freezeTimeFreezer 				= config.get("Tweak", "How long should the freezetime of the freezer be?", 6000).getInt();
                        
        StwtHDimension 					= config.get("World", "To which dimension shall the Stairway to Heaven send you back?", 1).getInt();
        nethDim 						= config.get("World", "To which dimension shall the nether portal send you back?", -1).getInt();
        endDim 							= config.get("World", "To which Dimension shall an end portal send you back?", -1).getInt();
        
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
        enableSieveLuckOfTheSea 		= config.get("Sieving", "Enable sieve lock of the sea enchanting?", true).getBoolean();
        sieveLuckOfTheSeaMaxLevel 		= config.get("Sieving", "Max Level for sieve luck of the sea enchanting", 3).getInt();
        
        //Barrel
        shouldBarrelsFillWithRain 		= config.get("Mechanics", "barrelsFillWithRain", true).getBoolean();
        compostingTicks 				= config.get("Composting", "ticksToFormDirt", 600).getInt();
        enableBarrelTransformLighting 	= config.get("Misc", "enableBarrelTransformLighting", true).getBoolean();
        
        //Liquid Impossibility
        doesLIVaporize 					= config.get("World", "Can Liquid Impossibility vaporize?", false).getBoolean();
        spawnPig 						= config.get("Mobs", "Can Liquid Impossibility transform pigmans?", true).getBoolean();
        spawnSkeleton 					= config.get("Mobs", "Can Liquid Impossibility transform wither skeletons", true).getBoolean();
        spawnSlime 						= config.get("Mobs", "Can Liquid Impossibility transform magma slimes?", true).getBoolean();
        
        isHellworld 					= config.get("WorldType", "Are you playing Hellworld?", true).getBoolean();
        normalDropPercent 				= config.get("World", "The normal drop percent chance outside Hellworld", 100).getInt();
        woodBarrelMaxTemp 				= config.get("World", "Hwo hot can a fluid be in a wodden barrel?", 301).getInt();
        
        //JSON
        enableJSONLoading 				= config.get("JSON", "Enable use of JSON configuration?", true).getBoolean();
        
        // Mod-Compatibility
        enableMooFluid 					= config.get("MooFluids", "Enable Moo-Fluids-Compatibility", true).getBoolean();
        fillAmount 						= config.get("FillAmount", "How many mB milk should be produced", 1000).getInt();
        rubberSeed 						= config.getStringList("RubberSeeds", "The rubber saplings ntm should support", rubberSeed, "Leaves are green");
        oreDictPreferenceOrder 			= config.getStringList("OreDict preference order", "Compat", oreDictPreferenceOrder, "Coffe has caffeine");
        generalItemHandlerCompat 		= config.get("GeneralItemHandlerCompat", "Use of greater Item-Capability?", false).getBoolean();
        dankNullIntegration 			= config.get("DankNullIntegration", "Enable Dank-Null Integration?", false).getBoolean();
        preventUnidict 					= config.get("PreventUnidict", "Enable Unidictionary?", true).getBoolean();
    	chunkBaseOreDictName 			= config.get("ChunkBaseName", "Base Name for Chunks", "Chunk").getString();
    	dustBaseOreDictName 			= config.get("DustBaseName", "Base Name for Dusts", "Dust").getString();
    	pieceBaseOreDictName 			= config.get("PieceBaseName", "Base Name for Pieces", "Piece").getString();
    	ingotBaseOreDictName 			= config.get("IngotBaseName", "Base Name for Ingots", "Ingot").getString();
        
    	//BlockEnable
    	enableMultiBlock 				= config.getBoolean("Enable Hellfayah Block, Ore & Salt?", "Blocks", true, "Disabling this may break mechanics in NTM!");
    	enableDust 						= config.getBoolean("Enable Dust?", "Blocks", true, "Disabling this may break mechanics in NTM!");
    	enableBarrel 					= config.getBoolean("Enable Barrel?", "Blocks", true, "Disabling this may break mechanics in NTM!");
    	enableBonfire 					= config.getBoolean("Enable Bonfire?", "Blocks", true, "Disabling this may break mechanics in NTM!");
    	enableCondenser 				= config.getBoolean("Enable Condenser?", "Blocks", true, "Disabling this may break mechanics in NTM!");
    	enableStwH 						= config.getBoolean("Enable StwH?", "Blocks", true, "Disabling this may break mechanics in NTM!");
    	enableFreezer 					= config.getBoolean("Enable Freezer?", "Blocks", true, "Disabling this may break mechanics in NTM!");
    	enableHellmart 					= config.getBoolean("Enable Hellmart?", "Blocks", true, "Disabling this may break mechanics in NTM!");
    	enableLiquidImpossibility 		= config.getBoolean("Enable Liquid Impossibility?", "Blocks", true, "Disabling this may break mechanics in NTM!");
    	enableMeanVine 					= config.getBoolean("Enable Mean Vines?", "Blocks", true, "Disabling this may break mechanics in NTM!");
    	enableElderLeaves 				= config.getBoolean("Enable Elder Leaves?", "Blocks", true, "Disabling this may break mechanics in NTM!");
    	enableElderLog 					= config.getBoolean("Enable Elder Logs?", "Blocks", true, "Disabling this may break mechanics in NTM!");
    	enableNetherrackFurnace 		= config.getBoolean("Enable Elderrack Furnace?", "Blocks", true, "Disabling this may break mechanics in NTM!");
    	enableElderSapling 				= config.getBoolean("Enable Elder Sapling?", "Blocks", true, "Disabling this may break mechanics in NTM!");
    	enableElderSlab 				= config.getBoolean("Enable Elder Slab?", "Blocks", true, "Disabling this may break mechanics in NTM!");
    	enableNetherrackGravel			= config.getBoolean("Enable Netherrack Gravel?", "Blocks", true, "Disabling this may break mechanics in NTM!");
    	enableElderWood 				= config.getBoolean("Enable Elder Planks?", "Blocks", true, "Disabling this may break mechanics in NTM!");
    	enableSieve 					= config.getBoolean("Enable Sieve?", "Blocks", true, "Disabling this may break mechanics in NTM!");
    	
    	//ItemEnable
    	enableMultiItem 				= config.getBoolean("Enable Multi Item?", "Items", true, "Disabling this may break mechanics in NTM!");
    	enableSeed 						= config.getBoolean("Enable Seeds?", "Items", true, "Disabling this may break mechanics in NTM!");
    	enableCrystals 					= config.getBoolean("Enable Crystals?", "Items", true, "Disabling this may break mechanics in NTM!");
    	enablePebbles 					= config.getBoolean("Enable Pebbles?", "Items", true, "Disabling this may break mechanics in NTM!");
    	enableMeshes 					= config.getBoolean("Enable Meshes?", "Items", true, "Disabling this may break mechanics in NTM!");
    	enableDolls 					= config.getBoolean("Enable Dolls?", "Items", true, "Disabling this may break mechanics in NTM!");
    	enableJerky 					= config.getBoolean("Enable Cooked Jerky?", "Items", true, "Disabling this may break mechanics in NTM!");
    	enablePickAxeNetherrack 		= config.getBoolean("Enable Netherrack Pickaxe?", "Items", true, "Disabling this may break mechanics in NTM!");
    	enablePickAxeNetherbrick 		= config.getBoolean("Enable Netherbrick Pickaxe?", "Items", true, "Disabling this may break mechanics in NTM!");
    	enableHammerWood 				= config.getBoolean("Enable Wood Hammer?", "Items", true, "Disabling this may break mechanics in NTM!");
    	enableHammerStone 				= config.getBoolean("Enable Stone Hammer?", "Items", true, "Disabling this may break mechanics in NTM!");
    	enableHammerGold 				= config.getBoolean("Enable Gold Hammer?", "Items", true, "Disabling this may break mechanics in NTM!");
    	enableHammerIron 				= config.getBoolean("Enable Iron Hammer?", "Items", true, "Disabling this may break mechanics in NTM!");
    	enableHammerDiamond 			= config.getBoolean("Enable Diamond Hammer?", "Items", true, "Disabling this may break mechanics in NTM!");
    	enableFlintNBlaze 				= config.getBoolean("Enable Flint & Blaze?", "Items", true, "Disabling this may break mechanics in NTM!");
    	enableStoneDoor 				= config.getBoolean("Enable Stone Door?", "Items", true, "Disabling this may break mechanics in NTM!");
    	
        config.save();
	}

}
