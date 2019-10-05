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
import mod.sfhcore.blocks.CubeFireResistant;
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
	public static final Block DUST 				   = new CubeFalling(Material.SAND, 2.0F, 0.4F, new ResourceLocation(Constants.MODID, Constants.DUST));
	public static final Block STWH 				   = new StwH();
	public static final Block MEAN_VINE			   = new MeanVine();
	public static final Block NETHERRACK_GRAVEL	   = new NetherrackGravel();
	public static final Block STONE_DOOR 		   = new CustomDoor(Material.ROCK, new ResourceLocation(Constants.MODID, Constants.STONE_DOOR), 30.0F, 2.0F);
	public static final Block ELDER_DOOR 		   = new CustomDoor(Material.WOOD, new ResourceLocation(Constants.MODID, Constants.ELDER_DOOR), 15.0F, 2.0F);
	public static final Block HELLFAYAH_ORE		   = new Cube(Material.ROCK, 17.5F, 3.5F, new ResourceLocation(Constants.MODID, Constants.HELLFAYAH_ORE));
	public static final Block BLOCK_OF_HELLFAYAH   = new Cube(Material.ROCK, 17.5F, 3.5F, new ResourceLocation(Constants.MODID, Constants.BLOCK_OF_HELLFAYAH));
	public static final Block BLOCK_OF_SALT		   = new Cube(Material.ROCK, 17.5F, 3.5F, new ResourceLocation(Constants.MODID, Constants.BLOCK_OF_SALT));

	//Tree
	public static final Block ELDER_LOG 		   = new ElderLog();
	public static final Block ELDER_LEAVES 		   = new ElderLeaves();
	public static final Block ELDER_PLANKS 		   = new CubeFireResistant(Material.WOOD, 10.0F, 2.0F, new ResourceLocation(Constants.MODID, Constants.ELDER_PLANKS));
	public static final Block ELDER_SAPLING 	   = new ElderSapling();

	public static final BlockSlabCommon ELDER_SLAB = (BlockSlabCommon) new BlockSlabCommon.Half(Constants.ELDER_SLAB, Material.WOOD).setCreativeTab(Constants.TABNTM).setResistance(10.0F).setHardness(2.0F);
	public static final BlockSlabCommon ELDER_SLAB_DOUBLE = (BlockSlabCommon) new BlockSlabCommon.Double(Constants.ELDER_SLAB_DOUBLE, Material.WOOD).setResistance(10.0F).setHardness(2.0F);

	public static final List<Block> BLOCKS = new ArrayList();

	public BlockHandler()
	{
		addBlocks();
		registerTileEntities();
		MinecraftForge.EVENT_BUS.register(this);
	}

	private void addBlocks()
	{
		//Fluid Blocks
		if (BlocksItems.enableLiquidImpossibility)
			BLOCKS.add(FluidHandler.BLOCK_LIQUID_IMPOSSIBILITY);
		if(BlocksItems.enableDistilledWater)
			BLOCKS.add(FluidHandler.BLOCK_DISTILLED_WATER);
		//Regular Blocks
		if(BlocksItems.enableBarrel) {
			BLOCKS.add(STONE_BARREL);
			BLOCKS.add(OAK_BARREL);
			BLOCKS.add(BIRCH_BARREL);
			BLOCKS.add(SPRUCE_BARREL);
			BLOCKS.add(JUNGLE_BARREL);
			BLOCKS.add(ACACIA_BARREL);
			BLOCKS.add(DARK_OAK_BARREL);

			if(BlocksItems.enableElderTree)
				BLOCKS.add(ELDER_BARREL);
		}
		if(BlocksItems.enableSieve) {
			BLOCKS.add(STONE_SIEVE);
			BLOCKS.add(OAK_SIEVE);
			BLOCKS.add(BIRCH_SIEVE);
			BLOCKS.add(SPRUCE_SIEVE);
			BLOCKS.add(JUNGLE_SIEVE);
			BLOCKS.add(ACACIA_SIEVE);
			BLOCKS.add(DARK_OAK_SIEVE);

			if(BlocksItems.enableElderTree)
				BLOCKS.add(ELDER_SIEVE);
		}
		if(BlocksItems.enableAshBonePile)		BLOCKS.add(ASH_BONE_PILE);
		if(BlocksItems.enableFreezer)			BLOCKS.add(FREEZER);
		if(BlocksItems.enableHellmart)			BLOCKS.add(HELLMART);
		if(BlocksItems.enableCondenser) 		BLOCKS.add(CONDENSER);
		if(BlocksItems.enableNetherrackFurnace)	BLOCKS.add(NETHERRACK_FURNACE);
		if(BlocksItems.enableCrucible) {
			BLOCKS.add(UNFIRED_CRUCIBLE);
			BLOCKS.add(CRUCIBLE);
		}
		if(BlocksItems.enableHellfayah) {
			BLOCKS.add(HELLFAYAH_ORE);
			BLOCKS.add(BLOCK_OF_HELLFAYAH);
		}
		if(BlocksItems.enableSaltBlock) 		BLOCKS.add(BLOCK_OF_SALT);
		if(BlocksItems.enableDust) 				BLOCKS.add(DUST);
		if(BlocksItems.enableStwH) 				BLOCKS.add(STWH);
		if(BlocksItems.enableElderTree) {
			BLOCKS.add(ELDER_SAPLING);
			BLOCKS.add(ELDER_LOG);
			BLOCKS.add(ELDER_LEAVES);
			BLOCKS.add(ELDER_PLANKS);
			BLOCKS.add(ELDER_SLAB);
			BLOCKS.add(ELDER_SLAB_DOUBLE);
			BLOCKS.add(ELDER_DOOR);
		}
		if(BlocksItems.enableNetherrackGravel)	BLOCKS.add(NETHERRACK_GRAVEL);
		if(BlocksItems.enableMeanVine) 			BLOCKS.add(MEAN_VINE);
		if(BlocksItems.enableStoneDoor) 		BLOCKS.add(STONE_DOOR);
	}

	@SubscribeEvent
	public void registerBlocks(final RegistryEvent.Register<Block> event)
	{
		BLOCKS.forEach((block) ->
		{
			block.setUnlocalizedName(block.getRegistryName().getResourcePath());
			block.setCreativeTab(Constants.TABNTM);
			
			event.getRegistry().register(block);
		});
	}

	private void registerTileEntities()
	{
		GameRegistry.registerTileEntity(TileBarrel.class, Constants.TE_BARREL);
		GameRegistry.registerTileEntity(TileSieve.class, Constants.TE_SIEVE);
		GameRegistry.registerTileEntity(TileAshBonePile.class, Constants.TE_ASH_BONE_PILE);
		GameRegistry.registerTileEntity(TileFreezer.class, Constants.TE_FREEZER);
		GameRegistry.registerTileEntity(TileHellmart.class, Constants.TE_HELLMART);
		GameRegistry.registerTileEntity(TileCondenser.class, Constants.TE_CONDENSER);
		GameRegistry.registerTileEntity(TileNetherrackFurnace.class, Constants.TE_NETHERRACK_FURNACE);
		GameRegistry.registerTileEntity(TileCrucibleStone.class, Constants.TE_CRUCIBLE);
	}
}