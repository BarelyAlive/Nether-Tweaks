package mod.nethertweaks;

import java.io.File;

import mod.nethertweaks.blocks.HolyEarth;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class Config {
	
	public static int endDim;
	//Booleans
	public static boolean isEndPortalCraftable;
	public static boolean disableStairwaytoHeaven;
		
	//int
	public static int StwtHDimension;
	public static int nethDim;
	public static int burnTimeFurnace;
	public static int dryTimeCondenser;
	public static int freezeTimeFreezer;
	public static int burnTimeHellfayah;
	public static int burnTimeHellfayahBlock;
	public static boolean iwantvanillaWater;
	
	//Ore
	public static boolean shouldOreDictOreChunks;
	public static boolean shouldOreDictOreDusts;
	
	//Sieve
	public static boolean sievesAutoOutput;
	public static boolean setFireToMacroUsers;
	public static int sieveSimilarRadius;
	public static boolean fakePlayersCanSieve;
	public static boolean skyblockFeatures;
	
	//Barrel
	public static boolean shouldBarrelsFillWithRain;
	public static boolean enableBarrelTransformLighting;
	public static int compostingTicks;
	
	public static void loadConfigs(File file){
		Configuration config = new Configuration(file);
		config.load();
        
        iwantvanillaWater = config.get("Blocks", "Is water placable in the nether?", false).getBoolean();
        
        burnTimeHellfayah = config.get("Tweak", "Duration of Hellfayah", 12800).getInt();
        burnTimeHellfayahBlock = config.get("Tweak", "Duration of Hellfayah blocks", 128000).getInt();
        burnTimeFurnace = config.get("Tweak", "How long should the burntime of the Netherrack Furnace be?", 1600).getInt();
        dryTimeCondenser = config.get("Tweak", "How long should the drytime of the condenser be?", 2400).getInt();
        freezeTimeFreezer = config.get("Tweak", "How long should the freezetime of the freezer be?", 6000).getInt();
        HolyEarth.EntityIDList = config.get("Tweak", "Which mobs can be spawned by Blessed Earth (non water)?", HolyEarth.EntityIDList).getIntList();
        HolyEarth.EntityWaterID = config.get("Tweak", "Which mobs can be spawned by Blessed Earth (water only)?", HolyEarth.EntityWaterID).getIntList();
                
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
        skyblockFeatures = config.get("Sieving", "setFireToMacroUsers", false).getBoolean();
        
        //Barrel
        shouldBarrelsFillWithRain = config.get("Mechanics", "barrelsFillWithRain", true).getBoolean();
        compostingTicks = config.get("Composting", "ticksToFormDirt", 600).getInt();
        enableBarrelTransformLighting = config.get("Misc", "enableBarrelTransformLighting", true).getBoolean();
        
        config.save();
	}

}
