package mod.nethertweaks.handler;
 
import mod.nethertweaks.INames;
import mod.nethertweaks.blocks.AshBonePile;
import mod.nethertweaks.blocks.Barrel;
import mod.nethertweaks.blocks.BlockSlabCommon;
import mod.nethertweaks.blocks.Condenser;
import mod.nethertweaks.blocks.CrucibleStone;
import mod.nethertweaks.blocks.ElderLeaves;
import mod.nethertweaks.blocks.ElderLog;
import mod.nethertweaks.blocks.ElderPlanks;
import mod.nethertweaks.blocks.ElderSapling;
import mod.nethertweaks.blocks.Freezer;
import mod.nethertweaks.blocks.Hellmart;
import mod.nethertweaks.blocks.MeanVine;
import mod.nethertweaks.blocks.NetherrackFurnace;
import mod.nethertweaks.blocks.NetherrackGravel;
import mod.nethertweaks.blocks.Sieve;
import mod.nethertweaks.blocks.StwH;
import mod.nethertweaks.blocks.tile.TileAshBonePile;
import mod.nethertweaks.blocks.tile.TileBarrel;
import mod.nethertweaks.blocks.tile.TileCondenser;
import mod.nethertweaks.blocks.tile.TileCrucibleStone;
import mod.nethertweaks.blocks.tile.TileFreezer;
import mod.nethertweaks.blocks.tile.TileHellmart;
import mod.nethertweaks.blocks.tile.TileNetherrackFurnace;
import mod.nethertweaks.blocks.tile.TileSieve;
import mod.nethertweaks.config.Config;
import mod.sfhcore.blocks.Cube;
import mod.sfhcore.blocks.CubeFalling;
import mod.sfhcore.blocks.CustomDoor;
import mod.sfhcore.registries.Registry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
 
public class BlockHandler implements INames
{
	//Tile Entity
	public static final Block ASH_BONE_PILE		   = new AshBonePile();
    public static final Block CONDENSER 		   = new Condenser();
    public static final Block NETHERRACK_FURNACE   = new NetherrackFurnace();
    public static final Block FREEZER 			   = new Freezer();
    public static final Block HELLMART 			   = new Hellmart();
    public static final Block CRUCIBLE		 	   = new CrucibleStone(true);
    public static final Block UNFIRED_CRUCIBLE	   = new CrucibleStone(false);
    
    //Sieves
    public static final Block STONE_SIEVE		   = new Sieve(INames.STONE_SIEVE, Material.ROCK);
    
    public static final Block OAK_SIEVE			   = new Sieve(INames.OAK_SIEVE, Material.WOOD);
    public static final Block BIRCH_SIEVE		   = new Sieve(INames.BIRCH_SIEVE, Material.WOOD);
    public static final Block SPRUCE_SIEVE		   = new Sieve(INames.SPRUCE_SIEVE, Material.WOOD);
    public static final Block JUNGLE_SIEVE		   = new Sieve(INames.JUNGLE_SIEVE, Material.WOOD);
    public static final Block ACACIA_SIEVE		   = new Sieve(INames.ACACIA_SIEVE, Material.WOOD);
    public static final Block DARK_OAK_SIEVE	   = new Sieve(INames.DARK_OAK_SIEVE, Material.WOOD);
    public static final Block ELDER_SIEVE		   = new Sieve(INames.ELDER_SIEVE, Material.WOOD);
    
    //Barrels
    public static final Block STONE_BARREL 		   = new Barrel(1, Material.ROCK, INames.STONE_BARREL);
    
    public static final Block OAK_BARREL		   = new Barrel(0, Material.WOOD, INames.OAK_BARREL);
    public static final Block BIRCH_BARREL		   = new Barrel(0, Material.WOOD, INames.BIRCH_BARREL);
    public static final Block SPRUCE_BARREL		   = new Barrel(0, Material.WOOD, INames.SPRUCE_BARREL);
    public static final Block JUNGLE_BARREL		   = new Barrel(0, Material.WOOD, INames.JUNGLE_BARREL);
    public static final Block ACACIA_BARREL		   = new Barrel(0, Material.WOOD, INames.ACACIA_BARREL);
    public static final Block DARK_OAK_BARREL	   = new Barrel(0, Material.WOOD, INames.DARK_OAK_BARREL);
    public static final Block ELDER_BARREL		   = new Barrel(0, Material.WOOD, INames.ELDER_BARREL);
    
    //Blocks
    public static final Block DUST 				   = new CubeFalling(Material.SAND, 2.0F, 0.4F, TAB, new ResourceLocation(MODID, INames.DUST));
    public static final Block STWH 				   = new StwH();
    public static final Block MEAN_VINE			   = new MeanVine();
    public static final Block NETHERRACK_GRAVEL	   = new NetherrackGravel();
    public static final Block STONE_DOOR 		   = new CustomDoor(Material.ROCK, new ResourceLocation(MODID, INames.STONE_DOOR), 30.0F, 2.0F);
    public static final Block ELDER_DOOR 		   = new CustomDoor(Material.WOOD, new ResourceLocation(MODID, INames.ELDER_DOOR), 15.0F, 2.0F);
    public static final Block HELLFAYAH_ORE		   = new Cube(Material.ROCK, 17.5F, 3.5F, TAB, new ResourceLocation(MODID, INames.HELLFAYAH_ORE));
    public static final Block BLOCK_OF_HELLFAYAH   = new Cube(Material.ROCK, 17.5F, 3.5F, TAB, new ResourceLocation(MODID, INames.BLOCK_OF_HELLFAYAH));
    public static final Block BLOCK_OF_SALT		   = new Cube(Material.ROCK, 17.5F, 3.5F, TAB, new ResourceLocation(MODID, INames.BLOCK_OF_SALT));
    public static final Block ELDER_LOG 		   = new ElderLog();
    public static final Block ELDER_LEAVES 		   = new ElderLeaves();
    public static final Block ELDER_PLANKS 		   = new ElderPlanks();
    public static final Block ELDER_SAPLING 	   = new ElderSapling();
    
    public static final BlockSlabCommon ELDER_SLAB = (BlockSlabCommon) new BlockSlabCommon.Half(INames.ELDER_SLAB, Material.WOOD).setCreativeTab(TAB).setResistance(10.0F).setHardness(2.0F);
    public static final BlockSlabCommon ELDER_SLAB_DOUBLE = (BlockSlabCommon) new BlockSlabCommon.Double(INames.ELDER_SLAB_DOUBLE, Material.WOOD).setResistance(10.0F).setHardness(2.0F);
    
    public static void init()
    {
    	registerBlockTiles();
    	registerBlocks();
    }
    
    private static void registerBlockTiles()
    {
    	//Tile Entity
        if(Config.enableAshBonePile)		Registry.registerTileEntity(ASH_BONE_PILE, TileAshBonePile.class);
        
    	if(Config.enableSieve) 				Registry.registerTileEntity(STONE_SIEVE, TileSieve.class);
    	
    	if(Config.enableSieve) 				Registry.registerTileEntity(OAK_SIEVE, TileSieve.class);
    	if(Config.enableSieve) 				Registry.registerTileEntity(BIRCH_SIEVE, TileSieve.class);
    	if(Config.enableSieve) 				Registry.registerTileEntity(SPRUCE_SIEVE, TileSieve.class);
    	if(Config.enableSieve) 				Registry.registerTileEntity(JUNGLE_SIEVE, TileSieve.class);
    	if(Config.enableSieve) 				Registry.registerTileEntity(ACACIA_SIEVE, TileSieve.class);
    	if(Config.enableSieve) 				Registry.registerTileEntity(DARK_OAK_SIEVE, TileSieve.class);
    	if(Config.enableSieve) 				Registry.registerTileEntity(ELDER_SIEVE, TileSieve.class);
    	
    	if(Config.enableFreezer)			Registry.registerTileEntity(FREEZER, TileFreezer.class);
    	if(Config.enableHellmart)			Registry.registerTileEntity(HELLMART, TileHellmart.class);
    	if(Config.enableCondenser) 			Registry.registerTileEntity(CONDENSER, TileCondenser.class);
    	if(Config.enableNetherrackFurnace)	Registry.registerTileEntity(NETHERRACK_FURNACE, TileNetherrackFurnace.class);
    	
    	if(Config.enableBarrelStone)		Registry.registerTileEntity(STONE_BARREL, TileBarrel.class);
    	
    	if(Config.enableBarrelWood)			Registry.registerTileEntity(OAK_BARREL, TileBarrel.class);
    	if(Config.enableBarrelWood)			Registry.registerTileEntity(BIRCH_BARREL, TileBarrel.class);
    	if(Config.enableBarrelWood)			Registry.registerTileEntity(SPRUCE_BARREL, TileBarrel.class);
    	if(Config.enableBarrelWood)			Registry.registerTileEntity(JUNGLE_BARREL, TileBarrel.class);
    	if(Config.enableBarrelWood)			Registry.registerTileEntity(ACACIA_BARREL, TileBarrel.class);
    	if(Config.enableBarrelWood)			Registry.registerTileEntity(DARK_OAK_BARREL, TileBarrel.class);
    	if(Config.enableBarrelWood)			Registry.registerTileEntity(ELDER_BARREL, TileBarrel.class);
    	if(Config.enableCrucible) {
								    		Registry.registerTileEntity(UNFIRED_CRUCIBLE, TileCrucibleStone.class);
								    		Registry.registerTileEntity(CRUCIBLE, TileCrucibleStone.class);
    	}
    }
        
    //Registering all the blocks
    private static void registerBlocks()
    {
    	if(Config.enableHellfayahOre) 		Registry.registerBlock(HELLFAYAH_ORE);
    	if(Config.enableHellfayahBlock) 	Registry.registerBlock(BLOCK_OF_HELLFAYAH);
    	if(Config.enableSaltBlock) 			Registry.registerBlock(BLOCK_OF_SALT);
        if(Config.enableDust) 				Registry.registerBlock(DUST);
        if(Config.enableStwH) 				Registry.registerBlock(STWH);
        if(Config.enableElderTree) {
			/*I think, that no  */			Registry.registerBlock(ELDER_SAPLING);
			/*one wants to use  */			Registry.registerBlock(ELDER_LOG);
			/*just some of      */			Registry.registerBlock(ELDER_LEAVES);
			/*these and not the */			Registry.registerBlock(ELDER_PLANKS);
			/*whole tree, right?*/			Registry.registerBlock(ELDER_SLAB);
			/*RIGHT?			*/			Registry.registerBlock(ELDER_SLAB_DOUBLE);
		}
        if(Config.enableNetherrackGravel)	Registry.registerBlock(NETHERRACK_GRAVEL);
        if(Config.enableMeanVine) 			Registry.registerBlock(MEAN_VINE);
        if(Config.enableStoneDoor) 			Registry.registerBlock(STONE_DOOR);
        if(Config.enableElderDoor)			Registry.registerBlock(ELDER_DOOR);
    }
}