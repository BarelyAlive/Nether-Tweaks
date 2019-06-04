package mod.nethertweaks.handler;
 
import mod.nethertweaks.INames;
import mod.nethertweaks.Konstanten;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.blocks.*;
import mod.nethertweaks.blocks.enums.EnumBlockBasic;
import mod.nethertweaks.blocks.tile.TileBarrel;
import mod.nethertweaks.blocks.tile.TileBonfire;
import mod.nethertweaks.blocks.tile.TileCondenser;
import mod.nethertweaks.blocks.tile.TileCrucibleStone;
import mod.nethertweaks.blocks.tile.TileFreezer;
import mod.nethertweaks.blocks.tile.TileHellmart;
import mod.nethertweaks.blocks.tile.TileNetherrackFurnace;
import mod.nethertweaks.blocks.tile.TileSieve;
import mod.nethertweaks.config.Config;
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
    public static final Block CONDENSER 		   = new Condenser();
    public static final Block NETHERRACK_FURNACE   = new NetherrackFurnace();
    public static final Block BARREL 			   = new Barrel(0, Material.WOOD);
    public static final Block BARREL_STONE 		   = new Barrel(1, Material.ROCK);
    public static final Block FREEZER 			   = new Freezer();
    public static final Block BONFIRE 			   = new Bonfire();
    public static final Block HELLMART 			   = new Hellmart();
    public static final Block CRUCIBLE		 	   = new CrucibleStone(true);
    public static final Block UNFIRED_CRUCIBLE	   = new CrucibleStone(false);
    
    //Blocks
    public static final Block DUST 				   = new CubeFalling(Material.SAND, 2.0F, 0.4F, TAB, new ResourceLocation(MODID, INames.DUST));
    public static final Block STWH 				   = new StwH();
    public static final Block SIEVE 			   = new Sieve();
    public static final Block MEAN_VINE			   = new MeanVine();
    public static final Block NETHERRACK_GRAVEL	   = new NetherrackGravel();
    public static final Block ELDER_LOG 		   = new ElderLog();
    public static final Block ELDER_LEAVES 		   = new ElderLeaves();
    public static final Block ELDER_PLANKS 		   = new ElderPlanks();
    public static final Block ELDER_SAPLING 	   = new ElderSapling();
    public static final Block ELDER_SLAB_HALF 	   = new ElderSlab.Half();
    public static final Block ELDER_SLAB_DOUBLE    = new ElderSlab.Double(ELDER_SLAB_HALF, new ElderSlab());
    public static final Block STONE_DOOR 		   = new CustomDoor(Material.ROCK, new ResourceLocation(MODID, INames.STONE_DOOR), 30.0F, 2.0F);
    public static final Block ELDER_DOOR 		   = new CustomDoor(Material.WOOD, new ResourceLocation(MODID, INames.ELDER_DOOR), 15.0F, 2.0F);
    public static final Block BLOCK_BASIC		   = new BlockEnum(Material.ROCK, EnumBlockBasic.class, new ResourceLocation(MODID, INames.BLOCK_BASIC), 17.5f, 3.5f, TAB);
    
    //ItemBlocks
    public static final ItemBlock ITEM_ELDER_SLAB  = new ItemBlockElderSlab();
    public static final ItemBlock ITEM_BLOCK_BASIC = new ItemBlockEnum(BLOCK_BASIC, TAB);

    public static void init()
    {
    	registerBlockTiles();
    	registerBlocks();
    }
    
    private static void registerBlockTiles()
    {
    	//Tile Entity
    	if(Config.enableBonfire) 			Registry.registerTileEntity(BONFIRE, TileBonfire.class);
    	if(Config.enableCondenser) 			Registry.registerTileEntity(CONDENSER, TileCondenser.class);
    	if(Config.enableNetherrackFurnace)	Registry.registerTileEntity(NETHERRACK_FURNACE, TileNetherrackFurnace.class);
    	if(Config.enableBarrel) {				
    										Registry.registerTileEntity(BARREL, TileBarrel.class);
    										Registry.registerTileEntity(BARREL_STONE, TileBarrel.class);
        }
    	if(Config.enableSieve) 				Registry.registerTileEntity(SIEVE, TileSieve.class);
    	if(Config.enableFreezer)			Registry.registerTileEntity(FREEZER, TileFreezer.class);
    	if(Config.enableHellmart)			Registry.registerTileEntity(HELLMART, TileHellmart.class);
    	if(Config.enableCrucible) {
    		Registry.registerTileEntity(UNFIRED_CRUCIBLE, TileCrucibleStone.class);
    		Registry.registerTileEntity(CRUCIBLE, TileCrucibleStone.class);
    	}
    }
        
    //Registering all the blocks
    private static void registerBlocks()
    {
    	if(Config.enableMultiBlock) 		Registry.registerBlock(ITEM_BLOCK_BASIC);
        if(Config.enableDust) 				Registry.registerBlock(DUST);
        if(Config.enableStwH) 				Registry.registerBlock(STWH);
        if(Config.enableElderTree) {
			/*I think, that no */			Registry.registerBlock(ELDER_SAPLING);
			/*one wants to use */			Registry.registerBlock(ELDER_LOG);
			/*just some of     */			Registry.registerBlock(ELDER_LEAVES);
			/*these and not the*/			Registry.registerBlock(ELDER_PLANKS);
			/*the whole tree   */			Registry.registerBlock(ELDER_SLAB_DOUBLE);
			/*Ddtd!			   */			Registry.registerBlock(ITEM_ELDER_SLAB);
		}
        if(Config.enableNetherrackGravel)	Registry.registerBlock(NETHERRACK_GRAVEL);
        if(Config.enableMeanVine) 			Registry.registerBlock(MEAN_VINE);
        if(Config.enableStoneDoor) 			Registry.registerBlock(STONE_DOOR);
        if(Config.enableElderDoor)			Registry.registerBlock(ELDER_DOOR);
    }
}