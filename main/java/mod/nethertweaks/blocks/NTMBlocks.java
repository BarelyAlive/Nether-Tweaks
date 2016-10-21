package mod.nethertweaks.blocks;
 
import mod.nethertweaks.INames;
import mod.nethertweaks.blocks.*;
import mod.nethertweaks.blocks.tileentities.TileEntityNetherrackFurnace;
import mod.sfhcore.Constants;
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
		
        blockDust = Registry.registerBlock(new CubeFalling(Material.SAND, 0.4F, 0.3F), DUST, Constants.ModIdNTM);
        blockEndTeleport = Registry.registerBlock(new EndTeleport(), ENDTELEPORT, Constants.ModIdNTM);
        blockNetherSapling = Registry.registerBlock(new NetherSapling(), NETHERSAPLING, Constants.ModIdNTM);
        blockNetherLog = Registry.registerBlock(new NetherLog(), NETHERLOG, Constants.ModIdNTM);
        blockNetherLeav = Registry.registerBlock(new NetherLeaves(), NETHERLEAVES, Constants.ModIdNTM);
        blockNetherWood = Registry.registerBlock(new NetherWood(), NETHERWOOD, Constants.ModIdNTM);
        blockBonfire = Registry.registerBlock(new Bonfire(Material.ROCK), BONFIRE, Constants.ModIdNTM);
        blockHolyEarth = Registry.registerBlock(new HolyEarth(), HOLYEARTH, Constants.ModIdNTM);
        blockSansidian = Registry.registerBlock(new CubeFalling(Material.SAND, 2.5F, 0.5F), SANSIDIAN, Constants.ModIdNTM);
        blockMeanVine = Registry.registerBlock(new MeanVine(), MEANVINE, Constants.ModIdNTM);
        
        //Ore
        
        oreGoldGravel = Registry.registerBlock(new CubeFalling(Material.GROUND, 2.0f, 0.4f), OREGOLDGRAVEL, Constants.ModIdNTM);
        oreIronGravel = Registry.registerBlock(new CubeFalling(Material.GROUND, 2.0f, 0.4f), OREIRONGRAVEL, Constants.ModIdNTM);
        
        oreGoldSand = Registry.registerBlock(new CubeFalling(Material.SAND, 2.0f, 0.4f), OREGOLDSAND, Constants.ModIdNTM);
        oreIronSand = Registry.registerBlock(new CubeFalling(Material.SAND, 2.0f, 0.4f), OREIRONSAND, Constants.ModIdNTM);
        
        oreGoldDust = Registry.registerBlock(new CubeFalling(Material.SAND, 2.0f, 0.4f), OREGOLDDUST, Constants.ModIdNTM);
        oreIronDust = Registry.registerBlock(new CubeFalling(Material.SAND, 2.0f, 0.4f), OREIRONDUST, Constants.ModIdNTM);
         
        //Tile Entity
        blockCondenser = Registry.registerBlock(new Condenser(), CONDENSER, Constants.ModIdNTM);
        
        GameRegistry.registerTileEntity(TileEntityNetherrackFurnace.class, TENETHERRACKFURNACE);
        blockNetherrackFurnace = Registry.registerBlock(new NetherrackFurnace(false), NETHERRACKFURNACE, Constants.ModIdNTM);
        blockNetherrackFurnace_lit = Registry.registerBlock(new NetherrackFurnace(true), NETHERRACKFURNACE_LIT, Constants.ModIdNTM);
        
        blockBarrel = Registry.registerBlock(new Barrel(), BARREL, Constants.ModIdNTM);
        blockBarrelStone = Registry.registerBlock(new BarrelStone(), BARRELSTONE, Constants.ModIdNTM);
        blockSieve = Registry.registerBlock(new Sieve(), SIEVE, Constants.ModIdNTM);
        blockFreezer = Registry.registerBlock(new Freezer(), FREEZER, Constants.ModIdNTM);
    }
}