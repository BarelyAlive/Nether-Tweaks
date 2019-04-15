package mod.nethertweaks;

import java.io.File;

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
	
	//Ore
	public static boolean shouldOreDictOreChunks;
	public static boolean shouldOreDictOreDusts;
	
	//Sieve
	public static boolean sievesAutoOutput;
	public static boolean setFireToMacroUsers;
	public static int sieveSimilarRadius;
	public static boolean fakePlayersCanSieve;
	public static boolean hellworldFeatures;
	
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
	
	public static void loadConfigs(File file)
	{
		Configuration config = new Configuration(file);
		config.load();
                
        burnTimeHellfayah = config.get("Tweak", "Duration of Hellfayah", 12800).getInt();
        burnTimeHellfayahBlock = config.get("Tweak", "Duration of Hellfayah blocks", 128000).getInt();
        burnTimeFurnace = config.get("Tweak", "How long should the burntime of the Netherrack Furnace be?", 1600).getInt();
        dryTimeCondenser = config.get("Tweak", "How long should the drytime of the condenser be?", 2400).getInt();
        freezeTimeFreezer = config.get("Tweak", "How long should the freezetime of the freezer be?", 6000).getInt();
                
        StwtHDimension = config.get("World", "To which dimension shall the Stairway to Heaven send you back?", 0).getInt();
        nethDim = config.get("World", "To which dimension shall the nether portal send you back?", 0).getInt();
        endDim = config.get("World", "To which Dimension shall an end portal send you back?", 0).getInt();
        
        //Ore
		shouldOreDictOreChunks = config.get("Compatibilitiy", "OreDictOreChunks", true).getBoolean();
		shouldOreDictOreDusts = config.get("Compatibilitiy", "OreDictOreDusts", true).getBoolean();
        
        ///Sieve
        sievesAutoOutput = config.get("Sieving", "sievesAutoOutput", false).getBoolean();
        setFireToMacroUsers = config.get("Sieving", "setFireToMacroUsers", false).getBoolean();
        sieveSimilarRadius = config.get("Sieving", "sieveSimilarRadius", 2).getInt();
        fakePlayersCanSieve = config.get("Mechanics", "fakePlayersCanSieve", false).getBoolean();
        hellworldFeatures = config.get("Sieving", "setFireToMacroUsers", false).getBoolean();
        
        //Barrel
        shouldBarrelsFillWithRain = config.get("Mechanics", "barrelsFillWithRain", true).getBoolean();
        compostingTicks = config.get("Composting", "ticksToFormDirt", 600).getInt();
        enableBarrelTransformLighting = config.get("Misc", "enableBarrelTransformLighting", true).getBoolean();
        
        //Demon Water
        doesDMWVaporize = config.get("World", "Can Demon Water vaporize in nether?", false).getBoolean();
        spawnPig = config.get("Mobs", "Can Demon Water transform pigmans?", true).getBoolean();
        spawnSkeleton = config.get("Mobs", "Can Demon Water transform wither skeletons", true).getBoolean();
        spawnSlime = config.get("Mobs", "Can Demon Water transform magma slimes?", true).getBoolean();
        healAnimals = config.get("Mobs", "Can Demon Water heal animals?", true).getBoolean();
        
        config.save();
	}

}
