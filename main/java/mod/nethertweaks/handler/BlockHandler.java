package mod.nethertweaks.handler;
 
import mod.nethertweaks.Konstanten;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.blocks.*;
import mod.nethertweaks.blocks.enums.EnumBlockBasic;
import mod.nethertweaks.blocks.enums.EnumBlockOreNTM;
import mod.nethertweaks.blocks.tile.TileBarrel;
import mod.nethertweaks.blocks.tile.TileBonfire;
import mod.nethertweaks.blocks.tile.TileCondenser;
import mod.nethertweaks.blocks.tile.TileFreezer;
import mod.nethertweaks.blocks.tile.TileHellmart;
import mod.nethertweaks.blocks.tile.TileNetherrackFurnace;
import mod.nethertweaks.blocks.tile.TileSieve;
import mod.nethertweaks.interfaces.INames;
import mod.sfhcore.Constants;
import mod.sfhcore.handler.Registry;
import mod.sfhcore.blocks.CubeFalling;
import mod.sfhcore.blocks.CustomDoor;
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
 
public class BlockHandler implements INames
{	
	//Tile Entity
    public static final Block CONDENSER = new Condenser();
    public static final Block NETHERRACKFURNACE = new NetherrackFurnace();
    public static final Block BARREL = new BlockBarrel(0, Material.WOOD);
    public static final Block BARRELSTONE = new BlockBarrel(1, Material.ROCK);
    public static final Block FREEZER = new Freezer();
    public static final Block BONFIRE = new Bonfire();
    public static final Block HELLMART = new Hellmart();
     
    //Blocks
    public static final Block DUST = new CubeFalling(Material.SAND, 2.0F, 0.4F, NetherTweaksMod.tabNTM, new ResourceLocation(NTM, INames.DUST));
    public static final Block STWH = new EndTeleport();
    public static final Block NETHERSAPLING = new NetherSapling();
    public static final Block NETHERLOG = new NetherLog();
    public static final Block NETHERLEAVES = new NetherLeaves();
    public static final Block NETHERWOOD = new NetherWood();
    public static final Block NETHERSLAB = new NetherSlab();
    public static final Block MEANVINE = new MeanVine();
    public static final Block SIEVE = new Sieve();
    
    public static final Block BLOCKBASIC = new BlockEnum(Material.ROCK, EnumBlockBasic.class, new ResourceLocation(NTM, INames.BLOCKBASIC), 20.0f, 2.0f);
    public static final ItemBlock ITEMBLOCKBASIC = new ItemBlockEnum((BlockEnum) BLOCKBASIC, NetherTweaksMod.tabNTM);
        
    public static Block STONEDOOR = new CustomDoor(Material.ROCK, new ResourceLocation(NetherTweaksMod.MODID, INames.DOORNTMSTONE), 30.0F, 2.0F);
    
    public static void init()
    {
    	registerTileEntities();
    	registerBlocks();
    }
    
    private static void registerTileEntities()
    {
    	//Tile Entity
    	RegisterTileEntity.add(BONFIRE, TileBonfire.class);
        RegisterTileEntity.add(CONDENSER, TileCondenser.class);
        RegisterTileEntity.add(NETHERRACKFURNACE, TileNetherrackFurnace.class);
        RegisterTileEntity.add(BARREL, TileBarrel.class);
        RegisterTileEntity.add(SIEVE, TileSieve.class);
        RegisterTileEntity.add(FREEZER, TileFreezer.class);
        RegisterTileEntity.add(HELLMART, TileHellmart.class);
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
        Registry.registerBlock(MEANVINE);
        Registry.registerBlock(STONEDOOR);  
        Registry.registerBlock(CONDENSER);
        Registry.registerBlock(NETHERRACKFURNACE);
        Registry.registerBlock(BARREL);
        Registry.registerBlock(BARRELSTONE);
        Registry.registerBlock(SIEVE);
        Registry.registerBlock(FREEZER);
        Registry.registerBlock(HELLMART);
    }
}