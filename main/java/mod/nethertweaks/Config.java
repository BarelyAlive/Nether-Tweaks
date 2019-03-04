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
	
	public static boolean oreGravelCopper;
	public static boolean oreGravelTin;
	public static boolean oreGravelLead;
	public static boolean oreGravelNickel;
	public static boolean oreGravelPlatinum;
	public static boolean oreGravelSilver;
	public static boolean oreGravelUranium;
	public static boolean oreGravelOsmium;
	public static boolean oreGravelDraconium;
	public static boolean oreGravelSulfur;
	public static boolean oreGravelAluminum;
	public static boolean oreGravelSilicon;
	public static boolean oreGravelAmber;
	public static boolean oreGravelCinnabar;
	public static boolean oreGravelCertusQuartz;
	public static boolean oreGravelSalt;
	
	public static boolean oreSandCopper;
	public static boolean oreSandTin;
	public static boolean oreSandLead;
	public static boolean oreSandNickel;
	public static boolean oreSandPlatinum;
	public static boolean oreSandSilver;
	public static boolean oreSandUranium;
	public static boolean oreSandOsmium;
	public static boolean oreSandDraconium;
	public static boolean oreSandSulfur;
	public static boolean oreSandAluminum;
	public static boolean oreSandSilicon;
	public static boolean oreSandAmber;
	public static boolean oreSandCinnabar;
	public static boolean oreSandCertusQuartz;
	public static boolean oreSandSalt;
	
	public static boolean oreDustCopper;
	public static boolean oreDustTin;
	public static boolean oreDustLead;
	public static boolean oreDustNickel;
	public static boolean oreDustPlatinum;
	public static boolean oreDustSilver;
	public static boolean oreDustUranium;
	public static boolean oreDustOsmium;
	public static boolean oreDustDraconium;
	public static boolean oreDustSulfur;
	public static boolean oreDustAluminum;
	public static boolean oreDustSilicon;
	public static boolean oreDustAmber;
	public static boolean oreDustCinnabar;
	public static boolean oreDustCertusQuartz;
	public static boolean oreDustSalt;
	
	//int
	public static int sieveDifficulty = 0;
	public static int StwtHDimension;
	public static int nethDim;
	
	private static final String disablePrefix = "Set to false to disable recipe for ";
	private static String disable(String name){
		return disablePrefix + name + ".";
	}
	
	public static void loadConfigs(FMLPreInitializationEvent event){
		Configuration Config = new Configuration(event.getSuggestedConfigurationFile());
        Config.load();
        iwantvanillaWater = Config.get("Tweak", "Is vanilla Water placable in the Nether?", false).getBoolean();
        easyWaterSource = Config.get("Recipes", "Easy Source of Water?", true).getBoolean();
        disableStairwaytoHeaven = Config.get("Recipes", "Disable Stairway to Heaven?", false).getBoolean();
        disableMaceDust = Config.get("Recipes", "Disable Dust Recipe in TE Pulverizer?", false).getBoolean();
        HolyEarth.EntityIDList = Config.get("Tweak", "Which mobs can be spawned by Blessed Earth?", HolyEarth.EntityIDList).getIntList();
        sieveDifficulty = Config.get("Mechanics", "Difficulty multiplier of the Sieve:", sieveDifficulty).getInt();
        StwtHDimension = Config.get("World", "To which dimension shall the Stairway to Heaven send you back?", 0).getInt();
        nethDim = Config.get("World", "To which dimension shall the nether portal send you back?", 0).getInt();
        endDim = Config.get("World", "To which Dimension shall an end portal send you back?", 0).getInt();
        burnTimeFurnace = Config.get("Tweak", "How long should the burntime of the Netherrack Furnace be?", 1600).getInt();

        //Blocks
        endTeleport = Config.get("Blocks", disable(INames.ENDTELEPORT), true).getBoolean();
        bonfire = Config.get("Blocks", disable(INames.BONFIRE), true).getBoolean();
        condenser = Config.get("Blocks", disable(INames.CONDENSER), true).getBoolean();
        netherrackFurnace = Config.get("Blocks", disable(INames.NETHERRACKFURNACE), true).getBoolean();
        barrel = Config.get("Blocks", disable(INames.BARREL), true).getBoolean();
        sieve = Config.get("Blocks", disable(INames.SIEVE), true).getBoolean();
        freezer = Config.get("Blocks", disable(INames.FREEZER), true).getBoolean();
        
        oreGravelCopper = Config.get("Blocks", disable("Enable the recipe of this material?"), true).getBoolean();
    	oreGravelTin = Config.get("Blocks", disable("Enable the recipe of this material?"), true).getBoolean();
    	oreGravelLead = Config.get("Blocks", disable("Enable the recipe of this material?"), true).getBoolean();
    	oreGravelNickel = Config.get("Blocks", "Enable the recipe of this material?", true).getBoolean();
    	oreGravelPlatinum = Config.get("Blocks", "Enable the recipe of this material?", true).getBoolean();
    	oreGravelSilver = Config.get("Blocks", "Enable the recipe of this material?", true).getBoolean();
    	oreGravelUranium = Config.get("Blocks", "Enable the recipe of this material?", true).getBoolean();
    	oreGravelOsmium = Config.get("Blocks", "Enable the recipe of this material?", true).getBoolean();
    	oreGravelDraconium = Config.get("Blocks", "Enable the recipe of this material?", true).getBoolean();
    	oreGravelSulfur = Config.get("Blocks", "Enable the recipe of this material?", true).getBoolean();
    	oreGravelAluminum = Config.get("Blocks", "Enable the recipe of this material?", true).getBoolean();
    	oreGravelSilicon = Config.get("Blocks", "Enable the recipe of this material?", true).getBoolean();
    	oreGravelAmber = Config.get("Blocks", "Enable the recipe of this material?", true).getBoolean();
    	oreGravelCinnabar = Config.get("Blocks", "Enable the recipe of this material?", true).getBoolean();
    	oreGravelCertusQuartz = Config.get("Blocks", "Enable the recipe of this material?", true).getBoolean();
    	oreGravelSalt = Config.get("Blocks", "Enable the recipe of this material?", true).getBoolean();
    	
    	oreSandCopper = Config.get("Blocks", "Enable the recipe of this material?", true).getBoolean();
    	oreSandTin = Config.get("Blocks", "Enable the recipe of this material?", true).getBoolean();
    	oreSandLead = Config.get("Blocks", "Enable the recipe of this material?", true).getBoolean();
    	oreSandNickel = Config.get("Blocks", "Enable the recipe of this material?", true).getBoolean();
    	oreSandPlatinum = Config.get("Blocks", "Enable the recipe of this material?", true).getBoolean();
    	oreSandSilver = Config.get("Blocks", "Enable the recipe of this material?", true).getBoolean();
    	oreSandUranium = Config.get("Blocks", "Enable the recipe of this material?", true).getBoolean();
    	oreSandOsmium = Config.get("Blocks", "Enable the recipe of this material?", true).getBoolean();
    	oreSandDraconium = Config.get("Blocks", "Enable the recipe of this material?", true).getBoolean();
    	oreSandSulfur = Config.get("Blocks", "Enable the recipe of this material?", true).getBoolean();
    	oreSandAluminum = Config.get("Blocks", "Enable the recipe of this material?", true).getBoolean();
    	oreSandSilicon = Config.get("Blocks", "Enable the recipe of this material?", true).getBoolean();
    	oreSandAmber = Config.get("Blocks", "Enable the recipe of this material?", true).getBoolean();
    	oreSandCinnabar = Config.get("Blocks", "Enable the recipe of this material?", true).getBoolean();
    	oreSandCertusQuartz = Config.get("Blocks", "Enable the recipe of this material?", true).getBoolean();
    	oreSandSalt = Config.get("Blocks", "Enable the recipe of this material?", true).getBoolean();
    	
    	oreDustCopper = Config.get("Blocks", "Enable the recipe of this material?", true).getBoolean();
    	oreDustTin = Config.get("Blocks", "Enable the recipe of this material?", true).getBoolean();
    	oreDustLead = Config.get("Blocks", "Enable the recipe of this material?", true).getBoolean();
    	oreDustNickel = Config.get("Blocks", "Enable the recipe of this material?", true).getBoolean();
    	oreDustPlatinum = Config.get("Blocks", "Enable the recipe of this material?", true).getBoolean();
    	oreDustSilver = Config.get("Blocks", "Enable the recipe of this material?", true).getBoolean();
    	oreDustUranium = Config.get("Blocks", "Enable the recipe of this material?", true).getBoolean();
    	oreDustOsmium = Config.get("Blocks", "Enable the recipe of this material?", true).getBoolean();
    	oreDustDraconium = Config.get("Blocks", "Enable the recipe of this material?", true).getBoolean();
    	oreDustSulfur = Config.get("Blocks", "Enable the recipe of this material?", true).getBoolean();
    	oreDustAluminum = Config.get("Blocks", "Enable the recipe of this material?", true).getBoolean();
    	oreDustSilicon = Config.get("Blocks", "Enable the recipe of this material?", true).getBoolean();
    	oreDustAmber = Config.get("Blocks", "Enable the recipe of this material?", true).getBoolean();
    	oreDustCinnabar = Config.get("Blocks", "Enable the recipe of this material?", true).getBoolean();
    	oreDustCertusQuartz = Config.get("Blocks", "Enable the recipe of this material?", true).getBoolean();
    	oreDustSalt = Config.get("Blocks", "Enable the recipe of this material?", true).getBoolean();

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
