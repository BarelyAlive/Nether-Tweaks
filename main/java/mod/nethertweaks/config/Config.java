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
	public static int endDim  = -1;
	public static int nethDim = -1;
	public static int burnTimeFurnace 		 = 1600;
	public static int freezeTimeFreezer 	 = 6000;
	public static int burnTimeHellfayah 	 = 12800;
	public static int burnTimeHellfayahBlock = 128000;
	
	public static boolean autoOutputItems  = true;
	public static boolean autoExtractItems = true;
	
	//Ore
	public static boolean shouldOreDictOreChunks = true;
	public static boolean shouldOreDictOreDusts  = true;
		
	//Condenser
	public static int dryTimeCondenser  	 = 2400;
	public static int fluidOutputAmount  	 = 200;
	
	//Sieve
	public static int sieveSimilarRadius 		  = 2; 
	public static int sieveLuckOfTheSeaMaxLevel   = 3;
	public static int sieveFortuneMaxLevel 		  = 3;
	public static int sieveEfficiencyMaxLevel 	  = 5;
	public static int normalDropPercent 		  = 100;
	public static boolean setFireToMacroUsers 	  = true;
	public static boolean hellworldfeatures 	  = true;
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
	public static boolean thinCrucibleModel = true;
	
	//Liquid Impossibility
	public static boolean doesLIVaporize = false;
	public static boolean spawnSkeleton  = true;
	public static boolean spawnSlime 	 = true;
	public static boolean spawnWaterMobs = true;
	
	public static boolean isHellworld;
	public static boolean enableTeleport = true;
	public static String[] grabberBlocks = Grabber.getTangible();
	
	//JSON
	public static boolean enableJSONLoading = true;
	
	// Mod-Compatibility
	public static List<String> fluidList;
	public static int fillAmount = 1000;
	public static boolean enableMooFluid = true;
	public static boolean fluidListIsBlackList;
	public static boolean generalItemHandlerCompat = false;
	public static boolean dankNullIntegration = true;
	public static boolean preventUnidict = true;
	public static String chunkBaseOreDictName = "Chunk";
	public static String dustBaseOreDictName  = "Dust";
	public static String pieceBaseOreDictName = "Piece";
	public static String ingotBaseOreDictName = "Ingot";
	public static String[] rubberSeed = {"techreborn:rubber_sapling", "ic2:sapling"};
	public static String[] oreDictPreferenceOrder = {"thermalfoundation", "immersiveengineering"};
	
	//BlockEnable
	public static boolean enableMultiBlock 			= true;
	public static boolean enableDust 				= true;
	public static boolean enableBarrel 				= true;
	public static boolean enableBonfire 			= true;
	public static boolean enableCondenser 			= true;
	public static boolean enableStwH 				= true;
	public static boolean enableFreezer 			= true;
	public static boolean enableHellmart 			= true;
	public static boolean enableLiquidImpossibility = true;
	public static boolean enableMeanVine 			= true;
	public static boolean enableElderTree 			= true;
	public static boolean enableNetherrackFurnace 	= true;
	public static boolean enableNetherrackGravel 	= true;
	public static boolean enableSieve 				= true;
	public static boolean enableCrucible 			= true;
	
	//ItemEnable
	public static boolean enableMultiItem 			= true;
	public static boolean enableSeed 				= true;
	public static boolean enableCrystalLight 		= true;
	public static boolean enableCrystalEnder 		= true;
	public static boolean enablePebbles 			= true;
	public static boolean enableMeshes 				= true;
	public static boolean enableDolls 				= true;
	public static boolean enableJerky 				= true;
	public static boolean enablePickAxeNetherrack 	= true;
	public static boolean enablePickAxeNetherbrick 	= true;
	public static boolean enableGrabber 			= true;
	public static boolean enableHammerWood 			= true;
	public static boolean enableHammerStone 		= true;
	public static boolean enableHammerGold 			= true;
	public static boolean enableHammerIron 			= true;
	public static boolean enableHammerDiamond 		= true;
	public static boolean enableFlintNBlaze 		= true;
	public static boolean enableStoneDoor 			= true;
	public static boolean enableElderDoor 			= true;
	public static boolean enableWoodBucket 			= true;
	public static boolean enableStoneBucket 		= true;
	public static boolean enableString 				= true;
	
	public static void init()
	{
		Configuration config = new Configuration(new File(NetherTweaksMod.configDirectory, "NetherTweaksMod.cfg"));
		config.load();
		                
        burnTimeHellfayah 				= config.get("Tweak", "Burntime of Hellfayah", 12800).getInt();
        burnTimeHellfayahBlock 			= config.get("Tweak", "Burntime of Hellfayah blocks", 128000).getInt();
        burnTimeFurnace 				= config.get("Tweak", "Netherrack Furnace worktime in ticks", 1600).getInt();
        freezeTimeFreezer 				= config.get("Tweak", "Freezer worktime in ticks", 6000).getInt();
        
        autoExtractItems				= config.get("Tweak", "Enable machine's auto item extract from inventorys above", true).getBoolean();
        autoOutputItems					= config.get("Tweak", "Enable machine's auto item output to inventorys at the sides", true).getBoolean();
        
        dryTimeCondenser 				= config.get("Tweak", "Condenser worktime in ticks", 2400).getInt();
        fluidOutputAmount 				= config.get("Tweak", "Condenser max. fluid auto output in mB/Sec", 200, "0 disables it").getInt();
                        
        nethDim 						= config.get("World", "To which dimension shall the nether portal send you?", -1).getInt();
        endDim 							= config.get("World", "To which Dimension shall an end portal send you back?", -1).getInt();
        enableTeleport					= config.getBoolean("Mechanics", "Enable bonfire-to-bonfire teleport?", true, "Squares have edges, you know.");
        
        //Ore
		shouldOreDictOreChunks 			= config.get("Compatibilitiy", "Enable OreDict Ore Chunks?", true).getBoolean();
		shouldOreDictOreDusts 			= config.get("Compatibilitiy", "Enable OreDict Ore Dusts?", true).getBoolean();
        
        ///Sieve
        sieveSimilarRadius 				= config.get("Sieving", "Sieve Similar Radius", 2).getInt();
        sieveFortuneMaxLevel 			= config.get("Sieving", "Max Level for sieve fortune enchanting?", 3).getInt();
        sieveEfficiencyMaxLevel 		= config.get("Sieving", "Max Level for sieve efficiency enchanting?", 5).getInt();
        sieveLuckOfTheSeaMaxLevel 		= config.get("Sieving", "Max Level for sieve luck of the sea enchanting?", 3).getInt();
        normalDropPercent 				= config.get("Sieving", "The normal drop percent chance outside Hellworld", 100).getInt();
        sievesAutoOutput 				= config.get("Sieving", "Sieve Auto output?", false).getBoolean();
        fakePlayersCanSieve 			= config.get("Sieving", "Fake players can sieve", false).getBoolean();
        setFireToMacroUsers 			= config.get("Sieving", "Set fire to Macro Users", false).getBoolean();
        hellworldfeatures 				= config.get("Sieving", "Enable Hellowrld features?", false).getBoolean();
        enableSieveFortune 				= config.get("Sieving", "Enable sieve fortune enchanting?", true).getBoolean();
        enableSieveEfficiency 			= config.get("Sieving", "Enable sieve effiency enchanting?", true).getBoolean();
        hasteIncreaseSpeed 				= config.get("Sieving", "Does haste increase sieving speed?", true).getBoolean();
        enableSieveLuckOfTheSea 		= config.get("Sieving", "Enable sieve luck of the sea enchanting?", true).getBoolean();
        flattenSieveRecipes 			= config.get("Sieving", "If enabled all mesh tiers can obtain the same", false).getBoolean();
        
        //Barrel
        compostingTicks 				= config.get("Barrel", "Ticks to form Dirt", 600).getInt();
        woodBarrelMaxTemp 				= config.get("Barrel", "How hot can a fluid be in a wodden barrel (in Kelvin)?", 301).getInt();
        shouldBarrelsFillWithRain 		= config.get("Barrel", "Barrels fill with rain?", true).getBoolean();
        enableBarrelTransformLighting 	= config.get("Barrel", "Enable Barrel transform lighting?", true).getBoolean();
        
        //Crucible
        thinCrucibleModel			 	= config.get("Crucible", "Do you want a hin crucible model?", true).getBoolean();
        
        //Liquid Impossibility
        doesLIVaporize 					= config.get("Fluid", "Does Liquid Impossibility vaporize?", false).getBoolean();
        spawnSkeleton 					= config.get("Fluid", "Can Liquid Impossibility transform wither skeletons into skeletons", true).getBoolean();
        spawnSlime 						= config.get("Fluid", "Can Liquid Impossibility transform magma slimes into slimes?", true).getBoolean();
        spawnWaterMobs					= config.get("Fluid", "Do water mobs spawn in the nether? (i.e. Liquid Impossibility)", true).getBoolean();
        
        isHellworld 					= config.get("WorldType", "Are you playing Hellworld?", true).getBoolean();
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
