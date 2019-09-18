package mod.nethertweaks.handler;

import mod.nethertweaks.Constants;
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
import mod.nethertweaks.config.BlocksItems;
import mod.sfhcore.blocks.Cube;
import mod.sfhcore.blocks.CubeFalling;
import mod.sfhcore.blocks.CustomDoor;
import mod.sfhcore.registries.Registry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;

public class BlockHandler
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
	public static final Block STONE_SIEVE		   = new Sieve(Material.ROCK, Constants.STONE_SIEVE);

	public static final Block OAK_SIEVE			   = new Sieve(Material.WOOD, Constants.OAK_SIEVE);
	public static final Block BIRCH_SIEVE		   = new Sieve(Material.WOOD, Constants.BIRCH_SIEVE);
	public static final Block SPRUCE_SIEVE		   = new Sieve(Material.WOOD, Constants.SPRUCE_SIEVE);
	public static final Block JUNGLE_SIEVE		   = new Sieve(Material.WOOD, Constants.JUNGLE_SIEVE);
	public static final Block ACACIA_SIEVE		   = new Sieve(Material.WOOD, Constants.ACACIA_SIEVE);
	public static final Block DARK_OAK_SIEVE	   = new Sieve(Material.WOOD, Constants.DARK_OAK_SIEVE);
	public static final Block ELDER_SIEVE		   = new Sieve(Material.WOOD, Constants.ELDER_SIEVE);

	//Barrels
	public static final Block STONE_BARREL 		   = new Barrel(1, Material.ROCK, Constants.STONE_BARREL);

	public static final Block OAK_BARREL		   = new Barrel(0, Material.WOOD, Constants.OAK_BARREL);
	public static final Block BIRCH_BARREL		   = new Barrel(0, Material.WOOD, Constants.BIRCH_BARREL);
	public static final Block SPRUCE_BARREL		   = new Barrel(0, Material.WOOD, Constants.SPRUCE_BARREL);
	public static final Block JUNGLE_BARREL		   = new Barrel(0, Material.WOOD, Constants.JUNGLE_BARREL);
	public static final Block ACACIA_BARREL		   = new Barrel(0, Material.WOOD, Constants.ACACIA_BARREL);
	public static final Block DARK_OAK_BARREL	   = new Barrel(0, Material.WOOD, Constants.DARK_OAK_BARREL);
	public static final Block ELDER_BARREL		   = new Barrel(0, Material.WOOD, Constants.ELDER_BARREL);

	//Blocks
	public static final Block DUST 				   = new CubeFalling(Material.SAND, 2.0F, 0.4F, Constants.TAB, new ResourceLocation(Constants.MODID, Constants.DUST));
	public static final Block STWH 				   = new StwH();
	public static final Block MEAN_VINE			   = new MeanVine();
	public static final Block NETHERRACK_GRAVEL	   = new NetherrackGravel();
	public static final Block STONE_DOOR 		   = new CustomDoor(Material.ROCK, new ResourceLocation(Constants.MODID, Constants.STONE_DOOR), 30.0F, 2.0F);
	public static final Block ELDER_DOOR 		   = new CustomDoor(Material.WOOD, new ResourceLocation(Constants.MODID, Constants.ELDER_DOOR), 15.0F, 2.0F);
	public static final Block HELLFAYAH_ORE		   = new Cube(Material.ROCK, 17.5F, 3.5F, Constants.TAB, new ResourceLocation(Constants.MODID, Constants.HELLFAYAH_ORE));
	public static final Block BLOCK_OF_HELLFAYAH   = new Cube(Material.ROCK, 17.5F, 3.5F, Constants.TAB, new ResourceLocation(Constants.MODID, Constants.BLOCK_OF_HELLFAYAH));
	public static final Block BLOCK_OF_SALT		   = new Cube(Material.ROCK, 17.5F, 3.5F, Constants.TAB, new ResourceLocation(Constants.MODID, Constants.BLOCK_OF_SALT));

	//Tree
	public static final Block ELDER_LOG 		   = new ElderLog();
	public static final Block ELDER_LEAVES 		   = new ElderLeaves();
	public static final Block ELDER_PLANKS 		   = new ElderPlanks();
	public static final Block ELDER_SAPLING 	   = new ElderSapling();

	public static final BlockSlabCommon ELDER_SLAB = (BlockSlabCommon) new BlockSlabCommon.Half(Constants.ELDER_SLAB, Material.WOOD).setCreativeTab(Constants.TAB).setResistance(10.0F).setHardness(2.0F);
	public static final BlockSlabCommon ELDER_SLAB_DOUBLE = (BlockSlabCommon) new BlockSlabCommon.Double(Constants.ELDER_SLAB_DOUBLE, Material.WOOD).setResistance(10.0F).setHardness(2.0F);

	public static void init()
	{
		registerBlockTiles();
		registerBlocks();
	}

	private static void registerBlockTiles()
	{
		//Tile Entity
		if(BlocksItems.enableBarrelStone)		Registry.registerTileEntity(STONE_BARREL, TileBarrel.class);
		if(BlocksItems.enableBarrelWood) {
			Registry.registerTileEntity(OAK_BARREL, TileBarrel.class);
			Registry.registerTileEntity(BIRCH_BARREL, TileBarrel.class);
			Registry.registerTileEntity(SPRUCE_BARREL, TileBarrel.class);
			Registry.registerTileEntity(JUNGLE_BARREL, TileBarrel.class);
			Registry.registerTileEntity(ACACIA_BARREL, TileBarrel.class);
			Registry.registerTileEntity(DARK_OAK_BARREL, TileBarrel.class);
			Registry.registerTileEntity(ELDER_BARREL, TileBarrel.class);
		}
		if(BlocksItems.enableSieve) {
			Registry.registerTileEntity(STONE_SIEVE, TileSieve.class);
			Registry.registerTileEntity(OAK_SIEVE, TileSieve.class);
			Registry.registerTileEntity(BIRCH_SIEVE, TileSieve.class);
			Registry.registerTileEntity(SPRUCE_SIEVE, TileSieve.class);
			Registry.registerTileEntity(JUNGLE_SIEVE, TileSieve.class);
			Registry.registerTileEntity(ACACIA_SIEVE, TileSieve.class);
			Registry.registerTileEntity(DARK_OAK_SIEVE, TileSieve.class);
			Registry.registerTileEntity(ELDER_SIEVE, TileSieve.class);
		}
		if(BlocksItems.enableAshBonePile)		Registry.registerTileEntity(ASH_BONE_PILE, TileAshBonePile.class);
		if(BlocksItems.enableFreezer)			Registry.registerTileEntity(FREEZER, TileFreezer.class);
		if(BlocksItems.enableHellmart)			Registry.registerTileEntity(HELLMART, TileHellmart.class);
		if(BlocksItems.enableCondenser) 		Registry.registerTileEntity(CONDENSER, TileCondenser.class);
		if(BlocksItems.enableNetherrackFurnace)	Registry.registerTileEntity(NETHERRACK_FURNACE, TileNetherrackFurnace.class);
		if(BlocksItems.enableCrucible) {
			Registry.registerTileEntity(UNFIRED_CRUCIBLE, TileCrucibleStone.class);
			Registry.registerTileEntity(CRUCIBLE, TileCrucibleStone.class);
		}
	}

	//Registering all the blocks
	private static void registerBlocks()
	{
		if(BlocksItems.enableHellfayahOre) 		Registry.registerBlock(HELLFAYAH_ORE);
		if(BlocksItems.enableHellfayahBlock) 	Registry.registerBlock(BLOCK_OF_HELLFAYAH);
		if(BlocksItems.enableSaltBlock) 		Registry.registerBlock(BLOCK_OF_SALT);
		if(BlocksItems.enableDust) 				Registry.registerBlock(DUST);
		if(BlocksItems.enableStwH) 				Registry.registerBlock(STWH);
		if(BlocksItems.enableElderTree) {
			Registry.registerBlock(ELDER_SAPLING);
			Registry.registerBlock(ELDER_LOG);
			Registry.registerBlock(ELDER_LEAVES);
			Registry.registerBlock(ELDER_PLANKS);
			Registry.registerBlock(ELDER_SLAB);
			Registry.registerBlock(ELDER_SLAB_DOUBLE);
		}
		if(BlocksItems.enableNetherrackGravel)	Registry.registerBlock(NETHERRACK_GRAVEL);
		if(BlocksItems.enableMeanVine) 			Registry.registerBlock(MEAN_VINE);
		if(BlocksItems.enableStoneDoor) 		Registry.registerBlock(STONE_DOOR);
		if(BlocksItems.enableElderDoor)			Registry.registerBlock(ELDER_DOOR);
	}
}