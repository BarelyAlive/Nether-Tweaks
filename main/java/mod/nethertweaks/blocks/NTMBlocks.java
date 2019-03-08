package mod.nethertweaks.blocks;
 
import mod.nethertweaks.Constants;
import mod.nethertweaks.INames;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.blocks.*;
import mod.nethertweaks.blocks.tileentities.TileEntityNetherrackFurnace;
import mod.sfhcore.Registry;
import mod.sfhcore.blocks.BlockDoorCustom;
import mod.sfhcore.blocks.CubeFalling;
import mod.sfhcore.blocks.itemblocks.ItemBlockEnumFalling;
import mod.sfhcore.items.block.ItemDoor;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
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
        
        public static Block doorNTMStone;
        public static Block doorNTMObsidian;
        
        public static Item itemDoorNTMStone;
        public static Item itemDoorNTMObsidian;
        
        public static Block blockBasic;
        
    public static void registerBlocks(){
		
        blockDust = Registry.registerBlock(new CubeFalling(16, Material.SAND, 0.4F, 0.3F, DUST), Constants.MOD);
        blockEndTeleport = Registry.registerBlock(new EndTeleport(), Constants.MOD);
        blockNetherSapling = Registry.registerBlock(new NetherSapling(), Constants.MOD);
        blockNetherLog = Registry.registerBlock(new NetherLog(), Constants.MOD);
        blockNetherLeav = Registry.registerBlock(new NetherLeaves(), Constants.MOD);
        blockNetherWood = Registry.registerBlock(new NetherWood(), Constants.MOD);
        blockBonfire = Registry.registerBlock(new Bonfire(Material.ROCK), Constants.MOD);
        blockHolyEarth = Registry.registerBlock(new HolyEarth(), Constants.MOD);
        blockSansidian = Registry.registerBlock(new CubeFalling(16, Material.SAND, 2.5F, 0.5F, SANSIDIAN), Constants.MOD);
        blockMeanVine = Registry.registerBlock(new MeanVine(), Constants.MOD);
        
        doorNTMStone = Registry.registerBlock(new BlockDoorCustom(Material.ROCK).setUnlocalizedName(DOORNTMSTONE), Constants.MOD);
        doorNTMObsidian = Registry.registerBlock(new BlockDoorCustom(Material.IRON).setUnlocalizedName(DOORNTMOBSIDIAN), Constants.MOD);
        
        //itemDoors
        itemDoorNTMStone = Registry.registerItem(new ItemDoor(doorNTMStone, ITEMDOORNTMSTONE, NetherTweaksMod.tabNetherTweaksMod), Constants.MOD);
        itemDoorNTMObsidian = Registry.registerItem(new ItemDoor(doorNTMObsidian, ITEMDOORNTMOBSIDIAN, NetherTweaksMod.tabNetherTweaksMod), Constants.MOD);
        
        
        //Ore
        
        oreGoldGravel = Registry.registerBlock(new CubeFalling(16, Material.GROUND, 2.0f, 0.4f, OREGOLDGRAVEL), Constants.MOD);
        oreIronGravel = Registry.registerBlock(new CubeFalling(16, Material.GROUND, 2.0f, 0.4f, OREIRONGRAVEL), Constants.MOD);
        
        oreGoldSand = Registry.registerBlock(new CubeFalling(16, Material.SAND, 2.0f, 0.4f, OREGOLDSAND), Constants.MOD);
        oreIronSand = Registry.registerBlock(new CubeFalling(16, Material.SAND, 2.0f, 0.4f, OREIRONSAND), Constants.MOD);
        
        oreGoldDust = Registry.registerBlock(new CubeFalling(16, Material.SAND, 2.0f, 0.4f, OREGOLDDUST), Constants.MOD);
        oreIronDust = Registry.registerBlock(new CubeFalling(16, Material.SAND, 2.0f, 0.4f, OREIRONDUST), Constants.MOD);
        
        oreGravel = Registry.registerBlock(new ItemBlockEnumFalling(new BlockOreNTM(Material.GROUND)), OREGRAVEL, 16, Constants.MOD);
        oreSand = Registry.registerBlock(new ItemBlockEnumFalling(new BlockOreNTM(Material.SAND)), ORESAND, 16, Constants.MOD);
        oreDust = Registry.registerBlock(new ItemBlockEnumFalling(new BlockOreNTM(Material.SAND)), OREDUST, 16, Constants.MOD);

         
        //Tile Entity
        blockCondenser = Registry.registerBlock(new Condenser(), Constants.MOD);
        
        GameRegistry.registerTileEntity(TileEntityNetherrackFurnace.class, TENETHERRACKFURNACE);
        blockNetherrackFurnace = Registry.registerBlock(new NetherrackFurnace(false), Constants.MOD);
        blockNetherrackFurnace_lit = Registry.registerBlock(new NetherrackFurnace(true), Constants.MOD);
        
        blockBarrel = Registry.registerBlock(new Barrel(), Constants.MOD);
        blockBarrelStone = Registry.registerBlock(new BarrelStone(), Constants.MOD);
        blockSieve = Registry.registerBlock(new Sieve(), Constants.MOD);
        blockFreezer = Registry.registerBlock(new Freezer(), Constants.MOD);
    }
}