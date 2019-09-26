package mod.nethertweaks.handler;

import java.util.ArrayList;
import java.util.List;

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
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockHandler
{
	//Tile Entity
	public static final Block ASH_BONE_PILE		   = new AshBonePile();
	public static final Block CONDENSER 		   = new Condenser();
	public static final Block NETHERRACK_FURNACE   = new NetherrackFurnace();
	public static final Block FREEZER 			   = new Freezer();
	public static final Block HELLMART 			   = new Hellmart();
	public static final Block CRUCIBLE		 	   = new CrucibleStone(Constants.CRUCIBLE, true);
	public static final Block UNFIRED_CRUCIBLE	   = new CrucibleStone(Constants.UNFIRED_CRUCIBLE, false);

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
	public static final Block DUST 				   = new CubeFalling(Material.SAND, 2.0F, 0.4F, Constants.TABNTM, new ResourceLocation(Constants.MODID, Constants.DUST));
	public static final Block STWH 				   = new StwH();
	public static final Block MEAN_VINE			   = new MeanVine();
	public static final Block NETHERRACK_GRAVEL	   = new NetherrackGravel();
	public static final Block STONE_DOOR 		   = new CustomDoor(Material.ROCK, new ResourceLocation(Constants.MODID, Constants.STONE_DOOR), 30.0F, 2.0F);
	public static final Block ELDER_DOOR 		   = new CustomDoor(Material.WOOD, new ResourceLocation(Constants.MODID, Constants.ELDER_DOOR), 15.0F, 2.0F);
	public static final Block HELLFAYAH_ORE		   = new Cube(Material.ROCK, 17.5F, 3.5F, Constants.TABNTM, new ResourceLocation(Constants.MODID, Constants.HELLFAYAH_ORE));
	public static final Block BLOCK_OF_HELLFAYAH   = new Cube(Material.ROCK, 17.5F, 3.5F, Constants.TABNTM, new ResourceLocation(Constants.MODID, Constants.BLOCK_OF_HELLFAYAH));
	public static final Block BLOCK_OF_SALT		   = new Cube(Material.ROCK, 17.5F, 3.5F, Constants.TABNTM, new ResourceLocation(Constants.MODID, Constants.BLOCK_OF_SALT));

	//Tree
	public static final Block ELDER_LOG 		   = new ElderLog();
	public static final Block ELDER_LEAVES 		   = new ElderLeaves();
	public static final Block ELDER_PLANKS 		   = new ElderPlanks();
	public static final Block ELDER_SAPLING 	   = new ElderSapling();

	public static final BlockSlabCommon ELDER_SLAB = (BlockSlabCommon) new BlockSlabCommon.Half(Constants.ELDER_SLAB, Material.WOOD).setCreativeTab(Constants.TABNTM).setResistance(10.0F).setHardness(2.0F);
	public static final BlockSlabCommon ELDER_SLAB_DOUBLE = (BlockSlabCommon) new BlockSlabCommon.Double(Constants.ELDER_SLAB_DOUBLE, Material.WOOD).setResistance(10.0F).setHardness(2.0F);

	private static List<Block> blocks = new ArrayList();
	
	public static List<Block> getBlocks() {
		return blocks;
	}
	
	public BlockHandler()
	{
		addBlocks();
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	private void addBlocks()
	{
		//FluidBlocks
		if (BlocksItems.enableLiquidImpossibility)
			blocks.add(FluidHandler.BLOCKLIQUIDIMPOSSIBILITY);
		if(BlocksItems.enableDistilledWater)
			blocks.add(FluidHandler.BLOCKDISTILLEDWATER);
		if(BlocksItems.enableBarrel) {
			blocks.add(STONE_BARREL);
			blocks.add(OAK_BARREL);
			blocks.add(BIRCH_BARREL);
			blocks.add(SPRUCE_BARREL);
			blocks.add(JUNGLE_BARREL);
			blocks.add(ACACIA_BARREL);
			blocks.add(DARK_OAK_BARREL);
			blocks.add(ELDER_BARREL);
		}
		if(BlocksItems.enableSieve) {
			blocks.add(STONE_SIEVE);
			blocks.add(OAK_SIEVE);
			blocks.add(BIRCH_SIEVE);
			blocks.add(SPRUCE_SIEVE);
			blocks.add(JUNGLE_SIEVE);
			blocks.add(ACACIA_SIEVE);
			blocks.add(DARK_OAK_SIEVE);
			blocks.add(ELDER_SIEVE);
		}
		if(BlocksItems.enableAshBonePile)		blocks.add(ASH_BONE_PILE);
		if(BlocksItems.enableFreezer)			blocks.add(FREEZER);
		if(BlocksItems.enableHellmart)			blocks.add(HELLMART);
		if(BlocksItems.enableCondenser) 		blocks.add(CONDENSER);
		if(BlocksItems.enableNetherrackFurnace)	blocks.add(NETHERRACK_FURNACE);
		if(BlocksItems.enableCrucible) {
			blocks.add(UNFIRED_CRUCIBLE);
			blocks.add(CRUCIBLE);
		}
		if(BlocksItems.enableHellfayahOre) 		blocks.add(HELLFAYAH_ORE);
		if(BlocksItems.enableHellfayahBlock) 	blocks.add(BLOCK_OF_HELLFAYAH);
		if(BlocksItems.enableSaltBlock) 		blocks.add(BLOCK_OF_SALT);
		if(BlocksItems.enableDust) 				blocks.add(DUST);
		if(BlocksItems.enableStwH) 				blocks.add(STWH);
		if(BlocksItems.enableElderTree) {
			blocks.add(ELDER_SAPLING);
			blocks.add(ELDER_LOG);
			blocks.add(ELDER_LEAVES);
			blocks.add(ELDER_PLANKS);
			blocks.add(ELDER_SLAB);
			blocks.add(ELDER_SLAB_DOUBLE);
		}
		if(BlocksItems.enableNetherrackGravel)	blocks.add(NETHERRACK_GRAVEL);
		if(BlocksItems.enableMeanVine) 			blocks.add(MEAN_VINE);
		if(BlocksItems.enableStoneDoor) 		blocks.add(STONE_DOOR);
		if(BlocksItems.enableElderDoor)			blocks.add(ELDER_DOOR);
	}

	@SubscribeEvent
	public void registerBlocks(final RegistryEvent.Register<Block> event)
	{
		for (Block block : blocks)
			event.getRegistry().register(block);

		GameRegistry.registerTileEntity(TileBarrel.class, Constants.TE_BARREL);
		GameRegistry.registerTileEntity(TileSieve.class, Constants.TE_SIEVE);
		GameRegistry.registerTileEntity(TileAshBonePile.class, Constants.TE_ASH_BONE_PILE);
		GameRegistry.registerTileEntity(TileFreezer.class, Constants.TE_FREEZER);
		GameRegistry.registerTileEntity(TileHellmart.class, Constants.TE_HELLMART);
		GameRegistry.registerTileEntity(TileCondenser.class, Constants.TE_CONDENSER);
		GameRegistry.registerTileEntity(TileNetherrackFurnace.class, Constants.TE_NETHERRACKFURNACE);
		GameRegistry.registerTileEntity(TileCrucibleStone.class, Constants.TE_CRUCIBLE);
	}
}