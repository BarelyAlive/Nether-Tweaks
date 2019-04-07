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
import mod.sfhcore.blocks.itemblocks.ItemDoor;
import mod.sfhcore.handler.RegisterTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
 
public class BlockHandler implements INames{
		
	//Tile Entity
    public static final Block CONDENSER = new Condenser();
    public static final Block NETHERRACKFURNACE = new NetherrackFurnace();
    public static final Block BARREL = new Barrel();
    public static final Block BARRELSTONE = new BarrelStone();
    public static final Block FREEZER = new Freezer();
    public static final Block BONFIRE = new Bonfire();
     
    //Blocks
    public static final Block DUST = new CubeFalling(Material.SAND, 0.4F, 0.3F, NetherTweaksMod.tabNTM, new ResourceLocation(NTM, INames.DUST));
    public static final Block STWH = new EndTeleport();
    public static final Block NETHERSAPLING = new NetherSapling();
    public static final Block NETHERLOG = new NetherLog();
    public static final Block NETHERLEAVES = new NetherLeaves();
    public static final Block NETHERWOOD = new NetherWood();
    public static final Block NETHERSLAB = new NetherSlab();
    public static final Block HOLYEARTH = new HolyEarth();
    public static final Block MEANVINE = new MeanVine();
    public static final Block SIEVE = new Sieve();
    
    public static final Block OREGOLDGRAVEL = new CubeFalling(Material.GROUND, 2.0f, 0.4f, NetherTweaksMod.tabNTM, new ResourceLocation(NTM, INames.OREGOLDGRAVEL));
    public static final Block OREIRONGRAVEL = new CubeFalling(Material.GROUND, 2.0f, 0.4f, NetherTweaksMod.tabNTM, new ResourceLocation(NTM, INames.OREIRONGRAVEL));
    public static final Block OREGOLDSAND = new CubeFalling(Material.SAND, 2.0f, 0.4f, NetherTweaksMod.tabNTM, new ResourceLocation(NTM, INames.OREGOLDSAND));
    public static final Block OREIRONSAND = new CubeFalling(Material.SAND, 2.0f, 0.4f, NetherTweaksMod.tabNTM, new ResourceLocation(NTM, INames.OREIRONSAND));
    public static final Block OREGOLDDUST = new CubeFalling(Material.SAND, 2.0f, 0.4f, NetherTweaksMod.tabNTM, new ResourceLocation(NTM, INames.OREGOLDDUST));
    public static final Block OREIRONDUST = new CubeFalling(Material.SAND, 2.0f, 0.4f, NetherTweaksMod.tabNTM, new ResourceLocation(NTM, INames.OREIRONDUST));
    
    public static final Block OREGRAVEL = new BlockEnumFalling(Material.GROUND, EnumBlockOreNTM.class, new ResourceLocation(NTM, INames.OREGRAVEL), NetherTweaksMod.tabNTM);
    public static final Block ORESAND = new BlockEnumFalling(Material.SAND, EnumBlockOreNTM.class, new ResourceLocation(NTM, INames.ORESAND), NetherTweaksMod.tabNTM);
    public static final Block OREDUST = new BlockEnumFalling(Material.SAND, EnumBlockOreNTM.class, new ResourceLocation(NTM, INames.OREDUST), NetherTweaksMod.tabNTM);
    
    public static final ItemBlock ITEMOREGRAVEL = new ItemBlockEnumFalling((BlockEnumFalling) OREGRAVEL);
    public static final ItemBlock ITEMORESAND = new ItemBlockEnumFalling((BlockEnumFalling) ORESAND);
    public static final ItemBlock ITEMOREDUST = new ItemBlockEnumFalling((BlockEnumFalling) OREDUST);
    
    public static final Block BLOCKBASIC = new BlockEnum(Material.ROCK, EnumBlockBasic.class, new ResourceLocation(NTM, INames.BLOCKBASIC), NetherTweaksMod.tabNTM);
    public static final ItemBlock ITEMBLOCKBASIC = new ItemBlockEnum((BlockEnum) BLOCKBASIC);
    
    public static final Block DOORNTMSTONE = new BlockDoorCustom(Material.ROCK, NetherTweaksMod.tabNTM, new ResourceLocation(NTM, INames.DOORNTMSTONE));
    public static final Block DOORNTMOBSIDIAN = new BlockDoorCustom(Material.IRON, NetherTweaksMod.tabNTM, new ResourceLocation(NTM, INames.DOORNTMOBSIDIAN));
    
    public static final Item ITEMDOORNTMSTONE = new ItemDoor(DOORNTMSTONE, new ResourceLocation(NTM, INames.ITEMDOORNTMSTONE), NetherTweaksMod.tabNTM);
    public static final Item ITEMDOORNTMOBSIDIAN = new ItemDoor(DOORNTMOBSIDIAN, new ResourceLocation(NTM, INames.ITEMDOORNTMOBSIDIAN), NetherTweaksMod.tabNTM);
    
    public static void init()
    {
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
    	Registry.registerBlock(ITEMBLOCKBASIC);
        Registry.registerBlock(DUST);
        Registry.registerBlock(STWH);
        Registry.registerBlock(NETHERSAPLING);
        Registry.registerBlock(NETHERLOG);
        Registry.registerBlock(NETHERLEAVES);
        Registry.registerBlock(NETHERWOOD);
        Registry.registerBlock(NETHERSLAB);
        Registry.registerBlock(BONFIRE);
        Registry.registerBlock(HOLYEARTH);
        Registry.registerBlock(MEANVINE);       
        Registry.registerBlock(DOORNTMSTONE);
        Registry.registerBlock(DOORNTMOBSIDIAN);
        Registry.registerBlock(OREGOLDGRAVEL);
        Registry.registerBlock(OREIRONGRAVEL);        
        Registry.registerBlock(OREGOLDSAND);
        Registry.registerBlock(OREIRONSAND);        
        Registry.registerBlock(OREGOLDDUST);
        Registry.registerBlock(OREIRONDUST);
        Registry.registerBlock(ITEMOREGRAVEL);
        Registry.registerBlock(ITEMORESAND);
        Registry.registerBlock(ITEMOREDUST);   
        Registry.registerBlock(CONDENSER);
        Registry.registerBlock(NETHERRACKFURNACE);
        Registry.registerBlock(BARREL);
        Registry.registerBlock(BARRELSTONE);
        Registry.registerBlock(SIEVE);
        Registry.registerBlock(FREEZER);      
    }
    
    //Just an extra method after the register block thing to prevent errors
    private static void registerItemBlocks()
    {
        Registry.registerItem(ITEMDOORNTMSTONE);
        Registry.registerItem(ITEMDOORNTMOBSIDIAN);
    }
}