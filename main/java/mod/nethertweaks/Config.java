package mod.nethertweaks;

import mod.nethertweaks.blocks.HolyEarth;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class Config {
	
	public static int endDim;
	//Booleans
	public static boolean isEndPortalCraftable;
	public static boolean disableStairwaytoHeaven;
	public static boolean disableHammers;
	
	//Items
	public static boolean lightCrystal;
	public static boolean cookJerky;
	
	//Ores
	public static boolean oreCopper;
	public static boolean oreTin;
	public static boolean oreLead;
	public static boolean oreNickel;
	public static boolean orePlatinum;
	public static boolean oreSilver;
	public static boolean oreUranium;
	public static boolean oreOsmium;
	public static boolean oreDraconium;
	public static boolean oreSulfur;
	public static boolean oreAluminum;
	public static boolean oreSilicon;
	public static boolean oreAmber;
	public static boolean oreCinnabar;
	public static boolean oreCertusQuartz;
	public static boolean oreSalt;
	
	//int
	public static int sieveDifficulty = 0;
	public static int StwtHDimension;
	public static int nethDim;
	public static int burnTimeFurnace;
	public static int dryTimeCondenser;
	public static int freezeTimeFreezer;
	public static int burnTimeHellfayah;
	public static int burnTimeHellfayahBlock;
	public static boolean iwantvanillaWater;
	
	public static void loadConfigs(FMLPreInitializationEvent event){
		Configuration Config = new Configuration(event.getSuggestedConfigurationFile());
        Config.load();
        
        iwantvanillaWater = Config.get("Blocks", "Is water placable in the nether?", false).getBoolean();
        
        burnTimeHellfayah = Config.get("Tweak", "Duration of Hellfayah", 12800).getInt();
        burnTimeHellfayahBlock = Config.get("Tweak", "Duration of Hellfayah blocks", 128000).getInt();
        burnTimeFurnace = Config.get("Tweak", "How long should the burntime of the Netherrack Furnace be?", 1600).getInt();
        dryTimeCondenser = Config.get("Tweak", "How long should the drytime of the condenser be?", 2400).getInt();
        freezeTimeFreezer = Config.get("Tweak", "How long should the freezetime of the freezer be?", 6000).getInt();
        HolyEarth.EntityIDList = Config.get("Tweak", "Which mobs can be spawned by Blessed Earth (non water)?", HolyEarth.EntityIDList).getIntList();
        HolyEarth.EntityWaterID = Config.get("Tweak", "Which mobs can be spawned by Blessed Earth (water only)?", HolyEarth.EntityWaterID).getIntList();
        sieveDifficulty = Config.get("Tweak", "Difficulty multiplier of the Sieve:", sieveDifficulty).getInt();
                
        StwtHDimension = Config.get("World", "To which dimension shall the Stairway to Heaven send you back?", 0).getInt();
        nethDim = Config.get("World", "To which dimension shall the nether portal send you back?", 0).getInt();
        endDim = Config.get("World", "To which Dimension shall an end portal send you back?", 0).getInt();

        //Blocks        
        oreCopper = Config.get("Blocks", "Set to false to disable Copper Ore recipe?", true).getBoolean();
    	oreTin = Config.get("Blocks", "Set to false to disable Tin Ore recipe?", true).getBoolean();
    	oreLead = Config.get("Blocks", "Set to false to disable Lead recipe?", true).getBoolean();
    	oreNickel = Config.get("Blocks", "Set to false to disable Nickel Ore recipe?", true).getBoolean();
    	orePlatinum = Config.get("Blocks", "Set to false to disable Platinum Ore recipe?", true).getBoolean();
    	oreSilver = Config.get("Blocks", "Set to false to disable Silver Ore recipe?", true).getBoolean();
    	oreUranium = Config.get("Blocks", "Set to false to disable Uranium Ore recipe?", true).getBoolean();
    	oreOsmium = Config.get("Blocks", "Set to false to disable Osmium Ore recipe?", true).getBoolean();
    	oreDraconium = Config.get("Blocks", "Set to false to disable Draconium Ore recipe?", true).getBoolean();
    	oreSulfur = Config.get("Blocks", "Set to false to disable Sulfur Ore recipe?", true).getBoolean();
    	oreAluminum = Config.get("Blocks", "Set to false to disable Aluminum Ore recipe?", true).getBoolean();
    	oreSilicon = Config.get("Blocks", "Set to false to disable Silicon Ore recipe?", true).getBoolean();
    	oreAmber = Config.get("Blocks", "Set to false to disable Amber Ore recipe?", true).getBoolean();
    	oreCinnabar = Config.get("Blocks", "Set to false to disable Cinnabar Ore recipe?", true).getBoolean();
    	oreCertusQuartz = Config.get("Blocks", "Set to false to disable Certus Quartz Ore recipe?", true).getBoolean();
    	oreSalt = Config.get("Blocks", "Set to false to disable Salt Ore recipe?", true).getBoolean();
    	
        //Items
        lightCrystal = Config.get("Items", "Set to false to disable Light Crystal recipe?", true).getBoolean();
        cookJerky = Config.get("Items", "Set to false to disable Cooked Jerky recipe?", true).getBoolean();
		
        Config.save();
	}

}
