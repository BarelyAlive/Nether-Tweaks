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
	
	//Demon Water
	public static boolean spawnPig;
	public static boolean spawnSkeleton;
	public static boolean spawnSlime;
	public static boolean doesDMWVaporize = false;
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
	
	public static void loadConfigs(File file)
	{
		Configuration config = new Configuration(file);
		config.load();
		
		allowedDims = config.get("WorldType", "Allowed dimensions in Hellworld", allowedDims).getIntList();
                
        burnTimeHellfayah = config.get("Tweak", "Duration of Hellfayah", 12800).getInt();
        burnTimeHellfayahBlock = config.get("Tweak", "Duration of Hellfayah blocks", 128000).getInt();
        burnTimeFurnace = config.get("Tweak", "How long should the burntime of the Netherrack Furnace be?", 1600).getInt();
        dryTimeCondenser = config.get("Tweak", "How long should the drytime of the condenser be?", 2400).getInt();
        freezeTimeFreezer = config.get("Tweak", "How long should the freezetime of the freezer be?", 6000).getInt();
                        
        StwtHDimension = config.get("World", "To which dimension shall the Stairway to Heaven send you back?", 1).getInt();
        nethDim = config.get("World", "To which dimension shall the nether portal send you back?", -1).getInt();
        endDim = config.get("World", "To which Dimension shall an end portal send you back?", -1).getInt();
        
        //Ore
		shouldOreDictOreChunks = config.get("Compatibilitiy", "OreDictOreChunks", true).getBoolean();
		shouldOreDictOreDusts = config.get("Compatibilitiy", "OreDictOreDusts", true).getBoolean();
        
        ///Sieve
        sievesAutoOutput = config.get("Sieving", "sievesAutoOutput", false).getBoolean();
        setFireToMacroUsers = config.get("Sieving", "setFireToMacroUsers", false).getBoolean();
        sieveSimilarRadius = config.get("Sieving", "sieveSimilarRadius", 2).getInt();
        fakePlayersCanSieve = config.get("Mechanics", "fakePlayersCanSieve", false).getBoolean();
        hellworldFeatures = config.get("Sieving", "setFireToMacroUsers", false).getBoolean();
        flattenSieveRecipes = config.get("Sieving", "If enablednall mesh tiers can obtain the same", false).getBoolean();
        hasteIncreaseSpeed = config.get("Sieving", "Does haste increase sieving speed?", true).getBoolean();
        enableSieveEfficiency = config.get("Sieving", "Enable sieve effiency enchanting?", true).getBoolean();
        sieveEfficiencyMaxLevel = config.get("Sieving", "Max Level for sieve efficiency enchanting", 5).getInt();
        enableSieveFortune = config.get("Sieving", "Enable sieve fortune enchanting?", true).getBoolean();
        sieveFortuneMaxLevel = config.get("Sieving", "Max Level for sieve fortune enchanting", 3).getInt();
        enableSieveLuckOfTheSea = config.get("Sieving", "Enable sieve lock of the sea enchanting?", true).getBoolean();
        sieveLuckOfTheSeaMaxLevel = config.get("Sieving", "Max Level for sieve luck of the sea enchanting", 3).getInt();
        
        //Barrel
        shouldBarrelsFillWithRain = config.get("Mechanics", "barrelsFillWithRain", true).getBoolean();
        compostingTicks = config.get("Composting", "ticksToFormDirt", 600).getInt();
        enableBarrelTransformLighting = config.get("Misc", "enableBarrelTransformLighting", true).getBoolean();
        
        //Demon Water
        doesDMWVaporize = config.get("World", "Can Demon Water vaporize in nether?", false).getBoolean();
        spawnPig = config.get("Mobs", "Can Demon Water transform pigmans?", true).getBoolean();
        spawnSkeleton = config.get("Mobs", "Can Demon Water transform wither skeletons", true).getBoolean();
        spawnSlime = config.get("Mobs", "Can Demon Water transform magma slimes?", true).getBoolean();
        
        isHellworld = config.get("WorldType", "Are you playing Hellworld?", true).getBoolean();
        normalDropPercent = config.get("World", "The normal drop percent chance outside Hellworld", 100).getInt();
        woodBarrelMaxTemp = config.get("World", "Hwo hot can a fluid be in a wodden barrel?", 301).getInt();
        
        //JSON
        enableJSONLoading = config.get("JSON", "Enable use of JSON configuration?", true).getBoolean();
        
        // Mod-Compatibility
        enableMooFluid = config.get("MooFluids", "Enable Moo-Fluids-Compatibility", true).getBoolean();
        fillAmount = config.get("FillAmount", "How many mB milk should be produced", 1000).getInt();
        rubberSeed = config.getStringList("RubberSeeds", "The rubber saplings ntm should support", rubberSeed, "Uzelezz Comment");
        oreDictPreferenceOrder = config.getStringList("OreDict preference order", "Compat", oreDictPreferenceOrder, "Coffe has caffeine");
        generalItemHandlerCompat = config.get("GeneralItemHandlerCompat", "Use of greater Item-Capability?", false).getBoolean();
        dankNullIntegration = config.get("DankNullIntegration", "Enable Dank-Null Integration?", false).getBoolean();
        preventUnidict = config.get("PreventUnidict", "Enable Unidictionary?", true).getBoolean();
    	chunkBaseOreDictName = config.get("ChunkBaseName", "Base Name for Chunks", "Chunk").getString();
    	dustBaseOreDictName = config.get("DustBaseName", "Base Name for Dusts", "Dust").getString();
    	pieceBaseOreDictName = config.get("PieceBaseName", "Base Name for Pieces", "Piece").getString();
    	ingotBaseOreDictName = config.get("IngotBaseName", "Base Name for Ingots", "Ingot").getString();
        
        
        config.save();
	}

}
