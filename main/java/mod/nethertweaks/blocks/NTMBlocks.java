package mod.nethertweaks.blocks;
 
import mod.nethertweaks.Constants;
import mod.nethertweaks.INames;
import mod.nethertweaks.blocks.*;
import mod.nethertweaks.blocks.tileentities.TileEntityNetherrackFurnace;
import mod.sfhcore.Registry;
import mod.sfhcore.blocks.CubeFalling;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.registry.GameRegistry;
 
public class NTMBlocks implements INames{
	
		//Tile Entity
        public static Block blockCondenser;
        public static Block blockNetherrackFurnace;
        public static Block blockNetherrackFurnace_lit;
        public static Block blockBarrel;
        public static Block blockBarrelStone;
        public static Block blockFreezer;
         
        //Blocks
        public static Block blockDust;
        public static Block blockEndTeleport;
        public static Block blockNetherSapling;
        public static Block blockNetherLog;
        public static Block blockNetherLeav;
        public static Block blockNetherWood;
        public static Block blockBonfire;
        public static Block blockHolyEarth;
        public static Block blockSansidian;
        public static Block blockMeanVine;
        public static Block blockSieve;
        public static Block oreGoldGravel;
        public static Block oreIronGravel;
        public static Block oreGoldSand;
        public static Block oreIronSand;
        public static Block oreGoldDust;
        public static Block oreIronDust;
        
        public static Block blockBasic;
        
    public static void registerBlocks(){
		
        blockDust = Registry.registerBlock(new CubeFalling(Material.SAND, 0.4F, 0.3F), DUST, Constants.MOD);
        blockEndTeleport = Registry.registerBlock(new EndTeleport(), ENDTELEPORT, Constants.MOD);
        blockNetherSapling = Registry.registerBlock(new NetherSapling(), NETHERSAPLING, Constants.MOD);
        blockNetherLog = Registry.registerBlock(new NetherLog(), NETHERLOG, Constants.MOD);
        blockNetherLeav = Registry.registerBlock(new NetherLeaves(), NETHERLEAVES, Constants.MOD);
        blockNetherWood = Registry.registerBlock(new NetherWood(), NETHERWOOD, Constants.MOD);
        blockBonfire = Registry.registerBlock(new Bonfire(Material.ROCK), BONFIRE, Constants.MOD);
        blockHolyEarth = Registry.registerBlock(new HolyEarth(), HOLYEARTH, Constants.MOD);
        blockSansidian = Registry.registerBlock(new CubeFalling(Material.SAND, 2.5F, 0.5F), SANSIDIAN, Constants.MOD);
        blockMeanVine = Registry.registerBlock(new MeanVine(), MEANVINE, Constants.MOD);
        
        //Ore
        
        oreGoldGravel = Registry.registerBlock(new CubeFalling(Material.GROUND, 2.0f, 0.4f), OREGOLDGRAVEL, Constants.MOD);
        oreIronGravel = Registry.registerBlock(new CubeFalling(Material.GROUND, 2.0f, 0.4f), OREIRONGRAVEL, Constants.MOD);
        
        oreGoldSand = Registry.registerBlock(new CubeFalling(Material.SAND, 2.0f, 0.4f), OREGOLDSAND, Constants.MOD);
        oreIronSand = Registry.registerBlock(new CubeFalling(Material.SAND, 2.0f, 0.4f), OREIRONSAND, Constants.MOD);
        
        oreGoldDust = Registry.registerBlock(new CubeFalling(Material.SAND, 2.0f, 0.4f), OREGOLDDUST, Constants.MOD);
        oreIronDust = Registry.registerBlock(new CubeFalling(Material.SAND, 2.0f, 0.4f), OREIRONDUST, Constants.MOD);
         
        //Tile Entity
        blockCondenser = Registry.registerBlock(new Condenser(), CONDENSER, Constants.MOD);
        
        GameRegistry.registerTileEntity(TileEntityNetherrackFurnace.class, TENETHERRACKFURNACE);
        blockNetherrackFurnace = Registry.registerBlock(new NetherrackFurnace(false), NETHERRACKFURNACE, Constants.MOD);
        blockNetherrackFurnace_lit = Registry.registerBlock(new NetherrackFurnace(true), NETHERRACKFURNACE_LIT, Constants.MOD);
        
        blockBarrel = Registry.registerBlock(new Barrel(), BARREL, Constants.MOD);
        blockBarrelStone = Registry.registerBlock(new BarrelStone(), BARRELSTONE, Constants.MOD);
        blockSieve = Registry.registerBlock(new Sieve(), SIEVE, Constants.MOD);
        blockFreezer = Registry.registerBlock(new Freezer(), FREEZER, Constants.MOD);
    }
}