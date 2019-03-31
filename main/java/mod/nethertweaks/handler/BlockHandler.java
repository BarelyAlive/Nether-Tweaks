package mod.nethertweaks.handler;
 
import mod.nethertweaks.INames;
import mod.nethertweaks.Konstanten;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.blocks.*;
import mod.nethertweaks.blocks.enums.EnumBlockBasic;
import mod.nethertweaks.blocks.enums.EnumBlockOreNTM;
import mod.nethertweaks.blocks.tileentities.TileEntityBarrel;
import mod.nethertweaks.blocks.tileentities.TileEntityBonfire;
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
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;
 
public class BlockHandler {
	
	//Tile Entity
    public static final Block CONDENSER = new Condenser();
    public static final Block NETHERRACKFURNACE = new NetherrackFurnace();
    public static final Block BARREL = new Barrel();
    public static final Block BARRELSTONE = new BarrelStone();
    public static final Block FREEZER = new Freezer();
     
    //Blocks
    public static final Block DUST = new CubeFalling(1, Material.SAND, 0.4F, 0.3F, INames.DUST);
    public static final Block STWH = new EndTeleport();
    public static final Block NETHERSAPLING = new NetherSapling();
    public static final Block NETHERLOG = new NetherLog();
    public static final Block NETHERLEAVES = new NetherLeaves();
    public static final Block NETHERWOOD = new NetherWood();
    public static final Block NETHERSLAB = new NetherSlab(Material.WOOD);
    public static final Block BONFIRE = new Bonfire(Material.ROCK);
    public static final Block HOLYEARTH = new HolyEarth();
    public static final Block MEANVINE = new MeanVine();
    public static final Block SIEVE = new Sieve();
    public static final Block OREGOLDGRAVEL = new CubeFalling(1, Material.GROUND, 2.0f, 0.4f, INames.OREGOLDGRAVEL).setCreativeTab(NetherTweaksMod.tabNetherTweaksMod);
    public static final Block OREIRONGRAVEL = new CubeFalling(1, Material.GROUND, 2.0f, 0.4f, INames.OREIRONGRAVEL).setCreativeTab(NetherTweaksMod.tabNetherTweaksMod);
    public static final Block OREGOLDSAND = new CubeFalling(1, Material.SAND, 2.0f, 0.4f, INames.OREGOLDSAND).setCreativeTab(NetherTweaksMod.tabNetherTweaksMod);
    public static final Block OREIRONSAND = new CubeFalling(1, Material.SAND, 2.0f, 0.4f, INames.OREIRONSAND).setCreativeTab(NetherTweaksMod.tabNetherTweaksMod);
    public static final Block OREGOLDDUST = new CubeFalling(1, Material.SAND, 2.0f, 0.4f, INames.OREGOLDDUST).setCreativeTab(NetherTweaksMod.tabNetherTweaksMod);
    public static final Block OREIRONDUST = new CubeFalling(1, Material.SAND, 2.0f, 0.4f, INames.OREIRONDUST).setCreativeTab(NetherTweaksMod.tabNetherTweaksMod);
    
    public static final ItemBlock ITEMOREGRAVEL = new ItemBlockEnumFalling(new BlockEnumFalling(Material.GROUND, EnumBlockOreNTM.class, INames.OREGRAVEL));
    public static final ItemBlock ITEMORESAND = new ItemBlockEnumFalling(new BlockEnumFalling(Material.SAND, EnumBlockOreNTM.class, INames.ORESAND));
    public static final ItemBlock ITEMOREDUST = new ItemBlockEnumFalling(new BlockEnumFalling(Material.SAND, EnumBlockOreNTM.class, INames.OREDUST));
    
    public static final Block OREGRAVEL = ITEMOREGRAVEL.getBlock();
    public static final Block ORESAND = ITEMORESAND.getBlock();
    public static final Block OREDUST = ITEMOREDUST.getBlock();
    
    public static final ItemBlock ITEMBLOCKBASIC = new ItemBlockEnum(new BlockEnum(Material.ROCK, EnumBlockBasic.class, INames.BLOCKBASIC));
    public static final Block BLOCKBASIC = ITEMBLOCKBASIC.getBlock();
    
    public static final Block DOORNTMSTONE = new BlockDoorCustom(Material.ROCK).setUnlocalizedName(INames.DOORNTMSTONE);
    public static final Block DOORNTMOBSIDIAN = new BlockDoorCustom(Material.IRON).setUnlocalizedName(INames.DOORNTMOBSIDIAN);
    
    public static final Item ITEMDOORNTMSTONE = new ItemDoor(DOORNTMSTONE, INames.ITEMDOORNTMSTONE, NetherTweaksMod.tabNetherTweaksMod);
    public static final Item ITEMDOORNTMOBSIDIAN = new ItemDoor(DOORNTMOBSIDIAN, INames.ITEMDOORNTMOBSIDIAN, NetherTweaksMod.tabNetherTweaksMod);
    
    public static void init() {
    	registerTileEntities();
    	registerBlocks();
    	registerItemBlocks();
    }
    
    private static void registerTileEntities()
    {
    	//Tile Entity
    	RegisterTileEntity.add(BONFIRE, new TileEntityBonfire());
        RegisterTileEntity.add(CONDENSER, new TileEntityCondenser(INames.TECONDENSER));
        RegisterTileEntity.add(NETHERRACKFURNACE, new TileEntityNetherrackFurnace(INames.TENETHERRACKFURNACE));
        RegisterTileEntity.add(BARREL, new TileEntityBarrel(INames.TEBARREL));
        RegisterTileEntity.add(BARRELSTONE, new TileEntityBarrel(INames.TEBARRELSTONE));
        RegisterTileEntity.add(SIEVE, new TileEntitySieve());
        RegisterTileEntity.add(FREEZER, new TileEntityFreezer(INames.TEFREEZER));
    }
        
    //Registering all the blocks
    private static void registerBlocks()
    {
    	Registry.registerBlock(ITEMBLOCKBASIC, 3, Constants.MOD);

        Registry.registerBlock(DUST, Constants.MOD);
        Registry.registerBlock(STWH, Constants.MOD);
        Registry.registerBlock(NETHERSAPLING, Constants.MOD);
        Registry.registerBlock(NETHERLOG, Constants.MOD);
        Registry.registerBlock(NETHERLEAVES, Constants.MOD);
        Registry.registerBlock(NETHERWOOD, Constants.MOD);
        Registry.registerBlock(NETHERSLAB, Constants.MOD);
        Registry.registerBlock(BONFIRE, Constants.MOD);
        Registry.registerBlock(HOLYEARTH, Constants.MOD);
        Registry.registerBlock(MEANVINE, Constants.MOD);
        
        Registry.registerBlock(DOORNTMSTONE, Constants.MOD);
        Registry.registerBlock(DOORNTMOBSIDIAN, Constants.MOD);
        
        
        //Ore
        
        Registry.registerBlock(OREGOLDGRAVEL, Constants.MOD);
        Registry.registerBlock(OREIRONGRAVEL, Constants.MOD);
        
        Registry.registerBlock(OREGOLDSAND, Constants.MOD);
        Registry.registerBlock(OREIRONSAND, Constants.MOD);
        
        Registry.registerBlock(OREGOLDDUST, Constants.MOD);
        Registry.registerBlock(OREIRONDUST, Constants.MOD);
        
        Registry.registerBlock(ITEMOREGRAVEL, 16, Constants.MOD);
        Registry.registerBlock(ITEMORESAND, 16, Constants.MOD);
        Registry.registerBlock(ITEMOREDUST, 16, Constants.MOD);
        
        Registry.registerBlock(CONDENSER, Constants.MOD);
        Registry.registerBlock(NETHERRACKFURNACE, Constants.MOD);
        Registry.registerBlock(BARREL, Constants.MOD);
        Registry.registerBlock(BARRELSTONE, Constants.MOD);
        Registry.registerBlock(SIEVE, Constants.MOD);
        Registry.registerBlock(FREEZER, Constants.MOD);      
    }
    
    //Just an extra method after the register block thing to prevent errors
    private static void registerItemBlocks()
    {
        Registry.registerItem(ITEMDOORNTMSTONE, Constants.MOD);
        Registry.registerItem(ITEMDOORNTMOBSIDIAN, Constants.MOD);
    }
}