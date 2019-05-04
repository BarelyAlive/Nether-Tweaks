package mod.nethertweaks.handler;
 
import mod.nethertweaks.Config;
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
import mod.nethertweaks.items.ItemBlockElderSlab;
import mod.sfhcore.Constants;
import mod.sfhcore.blocks.CubeFalling;
import mod.sfhcore.blocks.CustomDoor;
import mod.sfhcore.blocks.base.BlockEnum;
import mod.sfhcore.blocks.base.BlockEnumFalling;
import mod.sfhcore.blocks.itemblocks.ItemBlockEnum;
import mod.sfhcore.blocks.itemblocks.ItemBlockEnumFalling;
import mod.sfhcore.blocks.itemblocks.ItemDoor;
import mod.sfhcore.registries.Registry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSlab;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
 
public class BlockHandler implements INames
{	
	//Tile Entity
    public static final Block CONDENSER = new Condenser();
    public static final Block NETHERRACKFURNACE = new NetherrackFurnace();
    public static final Block BARREL = new Barrel(0, Material.WOOD);
    public static final Block BARRELSTONE = new Barrel(1, Material.ROCK);
    public static final Block FREEZER = new Freezer();
    public static final Block BONFIRE = new Bonfire();
    public static final Block HELLMART = new Hellmart();
     
    //Blocks
    public static final Block DUST = new CubeFalling(Material.SAND, 2.0F, 0.4F, NetherTweaksMod.tabNTM, new ResourceLocation(NTM, INames.DUST));
    public static final Block STWH = new StwH();
    public static final Block ELDERSAPLING = new ElderSapling();
    public static final Block ELDERLOG = new ElderLog();
    public static final Block ELDERLEAVES = new ElderLeaves();
    public static final Block ELDERPLANKS = new ElderPlanks();
    public static final Block ELDERSLABHALF = new ElderSlab.Half();
    public static final Block ELDERSLABDOUBLE = new ElderSlab.Double(ELDERSLABHALF, new ElderSlab());
    public static final Block NETHERRACKGRAVEL = new CubeFalling(Material.SAND, 2.0F, 0.4F, NetherTweaksMod.tabNTM, new ResourceLocation(NTM, INames.NETHERRACKGRAVEL));
    public static final Block MEANVINE = new MeanVine();
    public static final Block SIEVE = new Sieve();
    public static final Block STONEDOOR = new CustomDoor(Material.ROCK, new ResourceLocation(NetherTweaksMod.MODID, INames.DOORNTMSTONE), 30.0F, 2.0F);
    public static final Block BLOCKBASIC = new BlockEnum(Material.ROCK, EnumBlockBasic.class, new ResourceLocation(NTM, INames.BLOCKBASIC), 20.0f, 2.0f, NetherTweaksMod.tabNTM);
    
    //ItemBlocks
    public static final ItemBlock ITEMBLOCKBASIC = new ItemBlockEnum(BLOCKBASIC, NetherTweaksMod.tabNTM);
    public static final ItemBlock ITEMELDERSLAB = new ItemBlockElderSlab();

    public static void init()
    {
    	registerTileEntities();
    	registerBlocks();
    }
    
    private static void registerTileEntities()
    {
    	//Tile Entity
    	if(Config.enableBonfire) 			Registry.registerTileEntity(BONFIRE, TileBonfire.class);
    	if(Config.enableCondenser) 			Registry.registerTileEntity(CONDENSER, TileCondenser.class);
    	if(Config.enableNetherrackFurnace)	Registry.registerTileEntity(NETHERRACKFURNACE, TileNetherrackFurnace.class);
    	if(Config.enableBarrel)				Registry.registerTileEntity(BARREL, TileBarrel.class);
    	if(Config.enableSieve)				Registry.registerTileEntity(SIEVE, TileSieve.class);
    	if(Config.enableFreezer)			Registry.registerTileEntity(FREEZER, TileFreezer.class);
    	if(Config.enableHellmart)			Registry.registerTileEntity(HELLMART, TileHellmart.class);
    }
        
    //Registering all the blocks
    private static void registerBlocks()
    {
    	if(Config.enableMultiBlock) 		Registry.registerBlock(ITEMBLOCKBASIC);
        if(Config.enableDust) 				Registry.registerBlock(DUST);
        if(Config.enableStwH) 				Registry.registerBlock(STWH);
        if(Config.enableElderTree)
        {
			/*I think, that no */			Registry.registerBlock(ELDERSAPLING);
			/*one wants to use */			Registry.registerBlock(ELDERLOG);
			/*just some of     */			Registry.registerBlock(ELDERLEAVES);
			/*these and not the*/			Registry.registerBlock(ELDERPLANKS);
			/*the whole tree   */			Registry.registerBlock(ELDERSLABDOUBLE);
			/*Ddtd!			   */			Registry.registerBlock(ITEMELDERSLAB);
		}
        if(Config.enableNetherrackGravel)	Registry.registerBlock(NETHERRACKGRAVEL);
		if(Config.enableBonfire) 			Registry.registerBlock(BONFIRE);
        if(Config.enableMeanVine) 			Registry.registerBlock(MEANVINE);
        if(Config.enableStoneDoor) 			Registry.registerBlock(STONEDOOR);
        if(Config.enableCondenser) 			Registry.registerBlock(CONDENSER);
        if(Config.enableNetherrackFurnace)  Registry.registerBlock(NETHERRACKFURNACE);
        if(Config.enableBarrel)
        {
        									Registry.registerBlock(BARREL);
        									Registry.registerBlock(BARRELSTONE);
        }
        if(Config.enableSieve) 				Registry.registerBlock(SIEVE);
        if(Config.enableFreezer) 			Registry.registerBlock(FREEZER);
        if(Config.enableHellmart) 			Registry.registerBlock(HELLMART);
    }
}