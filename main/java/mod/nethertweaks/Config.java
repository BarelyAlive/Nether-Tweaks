package mod.nethertweaks;

import mod.nethertweaks.blocks.HolyEarth;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class Config {
	
	public static int endDim;
	//Booleans
	public static boolean isEndPortalCraftable;
	public static boolean easyWaterSource;
	public static boolean iwantvanillaWater;
	public static boolean disableStairwaytoHeaven;
	public static boolean disableBonfire;
	public static boolean disableMaceDust;
	public static boolean disableHammers;
	public static int burnTimeFurnace;
	//Blöcke
	public static boolean endTeleport;
	public static boolean bonfire;
	public static boolean sansidian;
	public static boolean condenser;
	public static boolean netherrackFurnace;
	public static boolean barrel;
	public static boolean sieve;
	public static boolean freezer;
	
	public static boolean flintnblaze;
	public static boolean sancCrystal;
	public static boolean lightCrystal;
	public static boolean nRackPick;
	public static boolean nBrickPick;
	public static boolean cookJerky;
	
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
	
	public static void loadConfigs(FMLPreInitializationEvent event){
		Configuration Config = new Configuration(event.getSuggestedConfigurationFile());
        Config.load();
        iwantvanillaWater = Config.get("Tweak", "Is vanilla Water placable in the Nether?", false).getBoolean();
        burnTimeFurnace = Config.get("Tweak", "How long should the burntime of the Netherrack Furnace be?", 1600).getInt();
        HolyEarth.EntityIDList = Config.get("Tweak", "Which mobs can be spawned by Blessed Earth?", HolyEarth.EntityIDList).getIntList();
        sieveDifficulty = Config.get("Tweak", "Difficulty multiplier of the Sieve:", sieveDifficulty).getInt();

        easyWaterSource = Config.get("Recipes", "Easy Source of Water?", true).getBoolean();
        disableStairwaytoHeaven = Config.get("Recipes", "Disable Stairway to Heaven?", false).getBoolean();
        disableMaceDust = Config.get("Recipes", "Disable Dust Recipe in TE Pulverizer?", false).getBoolean();
                
        StwtHDimension = Config.get("World", "To which dimension shall the Stairway to Heaven send you back?", 0).getInt();
        nethDim = Config.get("World", "To which dimension shall the nether portal send you back?", 0).getInt();
        endDim = Config.get("World", "To which Dimension shall an end portal send you back?", 0).getInt();

        //Blocks
        endTeleport = Config.get("Blocks", "Set to false to disable Stairway to Heaven recipe?", true).getBoolean();
        bonfire = Config.get("Blocks", "Set to false to disable Bonfire recipe?", true).getBoolean();
        sansidian = Config.get("Blocks", "Set to false to disable sansidian recipe?", true).getBoolean();
        condenser = Config.get("Blocks", "Set to false to disable condenser recipe?", true).getBoolean();
        netherrackFurnace = Config.get("Blocks", "Set to false to disable Netherrack Furnace recipe?", true).getBoolean();
        barrel = Config.get("Blocks", "Set to false to disable Barrel recipe?", true).getBoolean();
        sieve = Config.get("Blocks", "Set to false to disable Sieve recipe?", true).getBoolean();
        freezer = Config.get("Blocks", "Set to false to disable Freezer recipe?", true).getBoolean();
        
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
        disableHammers = Config.get("Items", "Disable Hammers?", false).getBoolean();
        
        flintnblaze = Config.get("Items", "Set to false to disable Flint and Blaze recipe?", true).getBoolean();
        sancCrystal = Config.get("Items", "Set to false to disable Sanctuary Crystal recipe?", true).getBoolean();
        lightCrystal = Config.get("Items", "Set to false to disable Light Crystal recipe?", true).getBoolean();
        nRackPick = Config.get("Items", "Set to false to disable Netherrack Pickaxe recipe?", true).getBoolean();
        nBrickPick = Config.get("Items", "Set to false to disable Netherbrick Pickaxe recipe?", true).getBoolean();
        cookJerky = Config.get("Items", "Set to false to disable Cooked Jerky recipe?", true).getBoolean();
		
        Config.save();
	}

}
