package mod.nethertweaks.handler;
 
import mod.nethertweaks.INames;
import mod.nethertweaks.Konstanten;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.blocks.*;
import mod.nethertweaks.blocks.enums.EnumBlockBasic;
import mod.nethertweaks.blocks.enums.EnumBlockOreNTM;
import mod.nethertweaks.blocks.tileentities.TileEntityBarrel;
import mod.nethertweaks.blocks.tileentities.TileEntityCondenser;
import mod.nethertweaks.blocks.tileentities.TileEntityFreezer;
import mod.nethertweaks.blocks.tileentities.TileEntityNetherrackFurnace;
import mod.nethertweaks.blocks.tileentities.TileEntitySieve;
import mod.sfhcore.Constants;
import mod.sfhcore.Registry;
import mod.sfhcore.blocks.BlockDoorCustom;
import mod.sfhcore.blocks.CubeFalling;
import mod.sfhcore.blocks.base.BlockEnum;
import mod.sfhcore.blocks.base.BlockEnumFalling;
import mod.sfhcore.blocks.itemblocks.ItemBlockEnum;
import mod.sfhcore.blocks.itemblocks.ItemBlockEnumFalling;
import mod.sfhcore.handler.RegisterTileEntity;
import mod.sfhcore.items.block.ItemDoor;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
 
public class BlockHandler implements INames{
	
		//Tile Entity
        public static Block condenser;
        public static Block netherrackfurnace;
        public static Block netherrackfurnace_lit;
        public static Block barrel;
        public static Block barrelstone;
        public static Block freezer;
         
        //Blocks
        public static Block dust;
        public static Block endteleport;
        public static Block netherSapling;
        public static Block netherLog;
        public static Block netherLeaves;
        public static Block netherWood;
        public static Block netherSlab;
        public static Block bonfire;
        public static Block holyearth;
        public static Block sansidian;
        public static Block meanvine;
        public static Block sieve;
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
    
    public static void init() {
    	registerBlocks();
    	registerItemBlocks();
    }
        
        
    //Registering all the blocks
    private static void registerBlocks(){
		
    	blockBasic = Registry.registerBlock(new ItemBlockEnum(new BlockEnum(Material.ROCK, EnumBlockBasic.class, BLOCKBASIC)), 3, Constants.MOD);

        dust = Registry.registerBlock(new CubeFalling(1, Material.SAND, 0.4F, 0.3F, DUST), Constants.MOD);
        endteleport = Registry.registerBlock(new EndTeleport().setUnlocalizedName(ENDTELEPORT).setRegistryName(Constants.MOD, ENDTELEPORT), Constants.MOD);
        netherSapling = Registry.registerBlock(new NetherSapling(), Constants.MOD);
        netherLog = Registry.registerBlock(new NetherLog(), Constants.MOD);
        netherLeaves = Registry.registerBlock(new NetherLeaves(), Constants.MOD);
        netherWood = Registry.registerBlock(new NetherWood(), Constants.MOD);
        netherSlab = Registry.registerBlock(new NetherSlab(Material.WOOD), Constants.MOD);
        bonfire = Registry.registerBlock(new Bonfire(Material.ROCK), Constants.MOD);
        holyearth = Registry.registerBlock(new HolyEarth(), Constants.MOD);
        sansidian = Registry.registerBlock(new CubeFalling(16, Material.SAND, 2.5F, 0.5F, SANSIDIAN), Constants.MOD);
        meanvine = Registry.registerBlock(new MeanVine(), Constants.MOD);
        
        doorNTMStone = Registry.registerBlock(new BlockDoorCustom(Material.ROCK).setUnlocalizedName(DOORNTMSTONE), Constants.MOD);
        doorNTMObsidian = Registry.registerBlock(new BlockDoorCustom(Material.IRON).setUnlocalizedName(DOORNTMOBSIDIAN), Constants.MOD);
        
        
        //Ore
        
        oreGoldGravel = Registry.registerBlock(new CubeFalling(1, Material.GROUND, 2.0f, 0.4f, OREGOLDGRAVEL).setCreativeTab(NetherTweaksMod.tabNetherTweaksMod), Constants.MOD);
        oreIronGravel = Registry.registerBlock(new CubeFalling(1, Material.GROUND, 2.0f, 0.4f, OREIRONGRAVEL).setCreativeTab(NetherTweaksMod.tabNetherTweaksMod), Constants.MOD);
        
        oreGoldSand = Registry.registerBlock(new CubeFalling(1, Material.SAND, 2.0f, 0.4f, OREGOLDSAND).setCreativeTab(NetherTweaksMod.tabNetherTweaksMod), Constants.MOD);
        oreIronSand = Registry.registerBlock(new CubeFalling(1, Material.SAND, 2.0f, 0.4f, OREIRONSAND).setCreativeTab(NetherTweaksMod.tabNetherTweaksMod), Constants.MOD);
        
        oreGoldDust = Registry.registerBlock(new CubeFalling(1, Material.SAND, 2.0f, 0.4f, OREGOLDDUST).setCreativeTab(NetherTweaksMod.tabNetherTweaksMod), Constants.MOD);
        oreIronDust = Registry.registerBlock(new CubeFalling(1, Material.SAND, 2.0f, 0.4f, OREIRONDUST).setCreativeTab(NetherTweaksMod.tabNetherTweaksMod), Constants.MOD);
        
        oreGravel = Registry.registerBlock(new ItemBlockEnumFalling(new BlockEnumFalling(Material.GROUND, EnumBlockOreNTM.class, OREGRAVEL)), 16, Constants.MOD);
        oreSand = Registry.registerBlock(new ItemBlockEnumFalling(new BlockEnumFalling(Material.SAND, EnumBlockOreNTM.class, ORESAND)), 16, Constants.MOD);
        oreDust = Registry.registerBlock(new ItemBlockEnumFalling(new BlockEnumFalling(Material.SAND, EnumBlockOreNTM.class, OREDUST)), 16, Constants.MOD);

         
        //Tile Entity
        condenser = new Condenser();
        netherrackfurnace = new NetherrackFurnace();
        barrel = new Barrel();
        barrelstone = new BarrelStone();
        sieve = new Sieve();
        freezer = new Freezer();
        
        RegisterTileEntity.add(condenser, new TileEntityCondenser(TECONDENSER));
        RegisterTileEntity.add(netherrackfurnace, new TileEntityNetherrackFurnace(TENETHERRACKFURNACE));
        RegisterTileEntity.add(barrel, new TileEntityBarrel(TEBARREL));
        RegisterTileEntity.add(barrelstone, new TileEntityBarrel(TEBARRELSTONE));
        RegisterTileEntity.add(sieve, new TileEntitySieve());
        RegisterTileEntity.add(freezer, new TileEntityFreezer(TEFREEZER));
        
        condenser = Registry.registerBlock(condenser, Constants.MOD);
        netherrackfurnace = Registry.registerBlock(netherrackfurnace, Constants.MOD);
        barrel = Registry.registerBlock(barrel, Constants.MOD);
        barrelstone = Registry.registerBlock(barrelstone, Constants.MOD);
        sieve = Registry.registerBlock(sieve, Constants.MOD);
        freezer = Registry.registerBlock(freezer, Constants.MOD);
        
    }
    
    //Just an extra method after the register block thing to prevent errors
    private static void registerItemBlocks(){
    	
    	//itemDoors
        itemDoorNTMStone = Registry.registerItem(new ItemDoor(doorNTMStone, ITEMDOORNTMSTONE, NetherTweaksMod.tabNetherTweaksMod), Constants.MOD);
        itemDoorNTMObsidian = Registry.registerItem(new ItemDoor(doorNTMObsidian, ITEMDOORNTMOBSIDIAN, NetherTweaksMod.tabNetherTweaksMod), Constants.MOD);
    	
    }
}