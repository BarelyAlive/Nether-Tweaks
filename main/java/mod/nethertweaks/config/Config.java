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
	public static int burnTimeHellfayah 	 = 12800;
	public static int burnTimeHellfayahBlock = 128000;
	
	public static int durabilityPickRack  = 132;
	public static int durabilityPickBrick = 251;
	
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
	
	public static boolean autoOutputItems  = true;
	public static boolean autoExtractItems = true;
	
	//Ore
	public static boolean shouldOreDictOreChunks = true;
	public static boolean shouldOreDictOreDusts  = true;
	
	//Freezer
	public static int freezeTimeFreezer  = 6000;
	public static int capacityFreezer  	 = 16000;
		
	//Condenser
	public static int fluidOutputAmount  	 = 200;
	public static int dryTimeCondenser  	 = 2400;
	public static int capacityCondenser  	 = 16000;
	
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
	public static int temperatureLI = 0;
	public static int luminosityLI  = 15;
	public static int densityLI 	= 1000;
	public static int viscosityLI   = 1000;
	public static boolean doesLIVaporize = false;
	public static boolean spawnSkeleton  = true;
	public static boolean spawnSlime 	 = true;
	public static boolean spawnWaterMobs = true;
	
	public static boolean isHellworld 	 = true;
	public static boolean enableTeleport = true;
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
	public static boolean enableHammerWood 			= true;
	public static boolean enableHammerStone 		= true;
	public static boolean enableHammerGold 			= true;
	public static boolean enableHammerIron 			= true;
	public static boolean enableHammerDiamond 		= true;
	public static boolean enableGrabberWood			= true;
	public static boolean enableGrabberStone 		= true;
	public static boolean enableGrabberGold			= true;
	public static boolean enableGrabberIron			= true;
	public static boolean enableGrabberDiamond 		= true;
	public static boolean enableFlintNBlaze 		= true;
	public static boolean enableStoneDoor 			= true;
	public static boolean enableElderDoor 			= true;
	public static boolean enableWoodBucket 			= true;
	public static boolean enableStoneBucket 		= true;
	
	public static void init()
	{
		Configuration config = new Configuration(new File(NetherTweaksMod.configDirectory, "NetherTweaksMod.cfg"));
		config.load();
		                
        burnTimeHellfayah 				= config.get("Tweak", "Burntime of Hellfayah", 12800).getInt();
        burnTimeHellfayahBlock 			= config.get("Tweak", "Burntime of Hellfayah blocks", 128000).getInt();
        burnTimeFurnace 				= config.get("Tweak", "Netherrack Furnace worktime in ticks", 1600).getInt();
        grabberBlocks	 				= config.get("Tweak", "Whick blocks should be tangible with the grabber?", Grabber.getTangible().toArray(new String[0])).getStringList();
        
        autoExtractItems				= config.get("Tweak", "Enable machine's auto item extract from inventorys above", true).getBoolean();
        autoOutputItems					= config.get("Tweak", "Enable machine's auto item output to inventorys at the sides", true).getBoolean();
        
        capacityFreezer					= config.getInt("Freezer fluid capacity in mb", "Tweak", 16000, 1, Integer.MAX_VALUE, "");
        freezeTimeFreezer 				= config.getInt("Freezer worktime in ticks", "Tweak", 6000, 1, Integer.MAX_VALUE, "");
        
        dryTimeCondenser 				= config.getInt("Condenser worktime in ticks", "Tweak", 2400, 1, Integer.MAX_VALUE, "");
        capacityCondenser 				= config.getInt("Condenser fluid capacity in mb", "Tweak", 16000, 1, Integer.MAX_VALUE, "");
        fluidOutputAmount 				= config.getInt("Condenser max. fluid auto output in mB/Sec", "Tweak", 200, 1, Integer.MAX_VALUE, "0 disables it");
        
        durabilityPickRack				= config.getInt("Durability for Netherrack Pickaxe", "Tweak", 132, 1, Integer.MAX_VALUE, "");
        durabilityPickBrick				= config.getInt("Durability for Netherbrick Pickaxe", "Tweak", 251, 1, Integer.MAX_VALUE, "");
        
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
        
        nethDim 						= config.get("World", "To which dimension shall the nether portal send you?", -1).getInt();
        endDim 							= config.get("World", "To which Dimension shall an end portal send you back?", -1).getInt();
        enableTeleport					= config.get("Mechanics", "Enable bonfire-to-bonfire teleport?", true, "").getBoolean();
        
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
        thinCrucibleModel			 	= config.get("Crucible", "Do you want a thin crucible model?", true).getBoolean();
        
        //Liquid Impossibility
        densityLI						= config.getInt("Density for Liquid Impossibility", "Fluid", 1000, 0, Integer.MAX_VALUE, "");
        luminosityLI					= config.getInt("Luminosity for Liquid Impossibility", "Fluid", 15, 0, 15, "");
        viscosityLI						= config.getInt("Viscosity for Liquid Impossibility", "Fluid", 1000, 0, Integer.MAX_VALUE, "");
        temperatureLI					= config.getInt("Temperatur of Liquid Impossibility in Kelvin", "Fluid", 0, 0, Integer.MAX_VALUE, ""); 
        doesLIVaporize 					= config.get("Fluid", "Does Liquid Impossibility vaporize?", false).getBoolean();
        spawnSkeleton 					= config.get("Fluid", "Can Liquid Impossibility transform wither skeletons into skeletons", true).getBoolean();
        spawnSlime 						= config.get("Fluid", "Can Liquid Impossibility transform magma slimes into slimes?", true).getBoolean();
        spawnWaterMobs					= config.get("Fluid", "Do water mobs spawn in the nether? (i.e. Liquid Impossibility)", true).getBoolean();
        
        isHellworld 					= config.get("WorldType", "Are you playing Hellworld?", true).getBoolean();
        
        //JSON
        enableJSONLoading 				= config.getBoolean("Enable use of JSON configuration?", "JSON", true, "Disable this if JSON configuration causes problems");
        
        //Mod-Compatibility
        fluidListIsBlackList			= config.get("Compatibility", "Is the Moo-Fluids-List a blacklist?", true).getBoolean();
        enableMooFluid 					= config.get("Compatibility", "Enable Moo-Fluids-Compatibility?", true).getBoolean();
        enableHarvestcraft				= config.get("Compatibility", "Enable Harvestcraft-Spaling_Sifting?", false, "Disabled by default, cuz HC wants you to buy or find its saplings").getBoolean();
        fillAmount 						= config.get("FillAmount", "How many mB milk should be produced?", 1000).getInt();
        oreDictPreferenceOrder 			= config.getStringList("OreDict preference order", "Compatibility", oreDictPreferenceOrder, "");
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
    	enableElderDoor 				= config.get("Items", "Enable Elder Door?", true).getBoolean();
    	enableWoodBucket				= config.get("Items", "Enable Wood Bucket?", true).getBoolean();
    	enableStoneBucket				= config.get("Items", "Enable Stone Bucket?", true).getBoolean();
    	
        config.save();
	}
}
