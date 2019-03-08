package mod.nethertweaks;
 
import mod.nethertweaks.blocks.Barrel;
import mod.nethertweaks.blocks.BarrelStone;
import mod.nethertweaks.blocks.BlockBasic;
import mod.nethertweaks.blocks.BlockDoorNTM;
import mod.nethertweaks.blocks.BlockOreNTM;
import mod.nethertweaks.blocks.Bonfire;
import mod.nethertweaks.blocks.Condenser;
import mod.nethertweaks.blocks.EndTeleport;
import mod.nethertweaks.blocks.Freezer;
import mod.nethertweaks.blocks.HolyEarth;
import mod.nethertweaks.blocks.MeanVine;
import mod.nethertweaks.blocks.NetherLeaves;
import mod.nethertweaks.blocks.NetherLog;
import mod.nethertweaks.blocks.NetherSapling;
import mod.nethertweaks.blocks.NetherSlab;
import mod.nethertweaks.blocks.NetherWood;
import mod.nethertweaks.blocks.NetherrackFurnace;
import mod.nethertweaks.blocks.Sieve;
import mod.nethertweaks.blocks.WaterFountain;
import mod.nethertweaks.blocks.tileentities.TileEntityNetherrackFurnace;
import mod.nethertweaks.items.ItemDoor;
import mod.sfhcore.Constants;
import mod.sfhcore.Registry;
import mod.sfhcore.blocks.CubeFalling;
import mod.sfhcore.blocks.itemblocks.ItemBlockEnum;
import mod.sfhcore.blocks.itemblocks.ItemBlockEnumFalling;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
 
public class NTMBlocks implements INames{
	
		//Tile Entity
        public static Block blockCondenser;
        public static Block blockNetherrackFurnace;
        public static Block blockNetherrackFurnace_lit;
        public static Block blockBarrel;
        public static Block blockBarrelStone;
        public static Block blockFreezer;
        public static Block waterFountain;
         
        //Blocks
        public static Block blockDust;
        public static Block endteleport;
        public static Block netherSapling;
        public static Block netherLog;
        public static Block netherLeaves;
        public static Block netherWood;
        public static Block netherSlab;
        public static Block bonfire;
        public static Block holyearth;
        public static Block sansidian;
        public static Block meanVine;
        public static Block blockSieve;
        public static Block oreGoldGravel;
        public static Block oreIronGravel;
        public static Block oreGoldSand;
        public static Block oreIronSand;
        public static Block oreGoldDust;
        public static Block oreIronDust;
        public static Block oreGravel;
        public static Block oreSand;
        public static Block oreDust;
        public static Block doorNTMStone;
        public static Block doorNTMObsidian;
        
        public static Item itemDoorNTMStone;
        public static Item itemDoorNTMObsidian;
        
        public static Block blockBasic;
        
    public static void registerBlocks(){
    	
    	blockBasic = Registry.registerBlock(new ItemBlockEnum(new BlockBasic(BLOCKBASIC, Material.ROCK)), BLOCKBASIC, 5, Constants.ModIdNTM);
        blockDust = Registry.registerBlock(new CubeFalling(Material.SAND, 0.4F, 0.3F, NetherTweaksMod.tabNetherTweaksMod), DUST, Constants.ModIdNTM).setCreativeTab(NetherTweaksMod.tabNetherTweaksMod);
        endteleport = Registry.registerBlock(new EndTeleport(), ENDTELEPORT, Constants.ModIdNTM);
        netherSapling = Registry.registerBlock(new NetherSapling(), NETHERSAPLING, Constants.ModIdNTM);
        netherLog = Registry.registerBlock(new NetherLog(), NETHERLOG, Constants.ModIdNTM);
        netherLeaves = Registry.registerBlock(new NetherLeaves(), NETHERLEAVES, Constants.ModIdNTM);
        netherWood = Registry.registerBlock(new NetherWood(), NETHERWOOD, Constants.ModIdNTM);
        netherSlab = Registry.registerBlock(new NetherSlab(Material.WOOD), NETHERSLAB, Constants.ModIdNTM);
        bonfire = Registry.registerBlock(new Bonfire(Material.ROCK), BONFIRE, Constants.ModIdNTM);
        holyearth = Registry.registerBlock(new HolyEarth(), HOLYEARTH, Constants.ModIdNTM);
        sansidian = Registry.registerBlock(new CubeFalling(Material.SAND, 2.5F, 0.5F, NetherTweaksMod.tabNetherTweaksMod), SANSIDIAN, Constants.ModIdNTM).setCreativeTab(NetherTweaksMod.tabNetherTweaksMod);
        meanVine = Registry.registerBlock(new MeanVine(), MEANVINE, Constants.ModIdNTM);
        doorNTMStone = Registry.registerBlock(new BlockDoorNTM(Material.ROCK), DOORNTMSTONE, Constants.ModIdNTM);
        doorNTMObsidian = Registry.registerBlock(new BlockDoorNTM(Material.IRON), DOORNTMOBSIDIAN, Constants.ModIdNTM);
        
        //itemDoors
        itemDoorNTMStone = Registry.registerItem(new ItemDoor(doorNTMStone, DOORNTMSTONE), ITEMDOORNTMSTONE, Constants.ModIdNTM);
        itemDoorNTMObsidian = Registry.registerItem(new ItemDoor(doorNTMObsidian, DOORNTMOBSIDIAN), ITEMDOORNTMOBSIDIAN, Constants.ModIdNTM);
        
        //Ore
        
        oreGoldGravel = Registry.registerBlock(new CubeFalling(Material.GROUND, 2.0f, 0.4f, NetherTweaksMod.tabNetherTweaksMod), OREGOLDGRAVEL, Constants.ModIdNTM).setCreativeTab(NetherTweaksMod.tabNetherTweaksMod);
        oreIronGravel = Registry.registerBlock(new CubeFalling(Material.GROUND, 2.0f, 0.4f, NetherTweaksMod.tabNetherTweaksMod), OREIRONGRAVEL, Constants.ModIdNTM).setCreativeTab(NetherTweaksMod.tabNetherTweaksMod);
        
        oreGoldSand = Registry.registerBlock(new CubeFalling(Material.SAND, 2.0f, 0.4f, NetherTweaksMod.tabNetherTweaksMod), OREGOLDSAND, Constants.ModIdNTM).setCreativeTab(NetherTweaksMod.tabNetherTweaksMod);
        oreIronSand = Registry.registerBlock(new CubeFalling(Material.SAND, 2.0f, 0.4f, NetherTweaksMod.tabNetherTweaksMod), OREIRONSAND, Constants.ModIdNTM).setCreativeTab(NetherTweaksMod.tabNetherTweaksMod);
        
        oreGoldDust = Registry.registerBlock(new CubeFalling(Material.SAND, 2.0f, 0.4f, NetherTweaksMod.tabNetherTweaksMod), OREGOLDDUST, Constants.ModIdNTM).setCreativeTab(NetherTweaksMod.tabNetherTweaksMod);
        oreIronDust = Registry.registerBlock(new CubeFalling(Material.SAND, 2.0f, 0.4f, NetherTweaksMod.tabNetherTweaksMod), OREIRONDUST, Constants.ModIdNTM).setCreativeTab(NetherTweaksMod.tabNetherTweaksMod);
        
        oreGravel = Registry.registerBlock(new ItemBlockEnumFalling(new BlockOreNTM(Material.GROUND)), OREGRAVEL, 16, Constants.ModIdNTM);
        oreSand = Registry.registerBlock(new ItemBlockEnumFalling(new BlockOreNTM(Material.SAND)), ORESAND, 16, Constants.ModIdNTM);
        oreDust = Registry.registerBlock(new ItemBlockEnumFalling(new BlockOreNTM(Material.SAND)), OREDUST, 16, Constants.ModIdNTM);

        //Tile Entity
        blockCondenser = Registry.registerBlock(new Condenser(), CONDENSER, Constants.ModIdNTM);
        
        GameRegistry.registerTileEntity(TileEntityNetherrackFurnace.class, TENETHERRACKFURNACE);
        blockNetherrackFurnace = Registry.registerBlock(new NetherrackFurnace(false), NETHERRACKFURNACE, Constants.ModIdNTM);
        blockNetherrackFurnace_lit = Registry.registerBlock(new NetherrackFurnace(true), NETHERRACKFURNACE_LIT, Constants.ModIdNTM);
        
        blockBarrel = Registry.registerBlock(new Barrel(), BARREL, Constants.ModIdNTM);
        blockBarrelStone = Registry.registerBlock(new BarrelStone(), BARRELSTONE, Constants.ModIdNTM);
        blockSieve = Registry.registerBlock(new Sieve(), SIEVE, Constants.ModIdNTM);
        blockFreezer = Registry.registerBlock(new Freezer(), FREEZER, Constants.ModIdNTM);
        waterFountain = Registry.registerBlock(new WaterFountain(), WATERFOUNTAIN, Constants.ModIdNTM);
    }
}