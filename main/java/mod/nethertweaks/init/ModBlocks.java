package mod.nethertweaks.init;

import static mod.nethertweaks.init.ModItems.ITEMS;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import mod.nethertweaks.Constants;
import mod.nethertweaks.block.AshBonePile;
import mod.nethertweaks.block.Barrel;
import mod.nethertweaks.block.BlockSlabCommon;
import mod.nethertweaks.block.Condenser;
import mod.nethertweaks.block.CrucibleStone;
import mod.nethertweaks.block.ElderLeaves;
import mod.nethertweaks.block.ElderLog;
import mod.nethertweaks.block.ElderSapling;
import mod.nethertweaks.block.Freezer;
import mod.nethertweaks.block.Hellmart;
import mod.nethertweaks.block.MeanVine;
import mod.nethertweaks.block.NetherrackFurnace;
import mod.nethertweaks.block.NetherrackGravel;
import mod.nethertweaks.block.Sieve;
import mod.nethertweaks.block.StwH;
import mod.nethertweaks.block.tile.TileAshBonePile;
import mod.nethertweaks.block.tile.TileBarrel;
import mod.nethertweaks.block.tile.TileCondenser;
import mod.nethertweaks.block.tile.TileCrucibleStone;
import mod.nethertweaks.block.tile.TileFreezer;
import mod.nethertweaks.block.tile.TileHellmart;
import mod.nethertweaks.block.tile.TileNetherrackFurnace;
import mod.nethertweaks.block.tile.TileSieve;
import mod.nethertweaks.config.BlocksItems;
import mod.sfhcore.blocks.Cube;
import mod.sfhcore.blocks.CubeFalling;
import mod.sfhcore.blocks.CubeFireResistant;
import mod.sfhcore.blocks.CustomDoor;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSlab;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks
{
	//Tile Entity
	public static final AshBonePile ASH_BONE_PILE  			 = new AshBonePile();
	public static final Condenser CONDENSER 	  			 = new Condenser();
	public static final NetherrackFurnace NETHERRACK_FURNACE = new NetherrackFurnace();
	public static final Freezer FREEZER 			   		 = new Freezer();
	public static final Hellmart HELLMART 			   		 = new Hellmart();
	public static final CrucibleStone CRUCIBLE		 	   	 = new CrucibleStone(true);
	public static final CrucibleStone UNFIRED_CRUCIBLE	   	 = new CrucibleStone(false);

	//Sieves
	public static final Sieve STONE_SIEVE		   			 = new Sieve(Material.ROCK);

	public static final Sieve OAK_SIEVE			   			 = new Sieve(Material.WOOD);
	public static final Sieve BIRCH_SIEVE					 = new Sieve(Material.WOOD);
	public static final Sieve SPRUCE_SIEVE		   			 = new Sieve(Material.WOOD);
	public static final Sieve JUNGLE_SIEVE		   			 = new Sieve(Material.WOOD);
	public static final Sieve ACACIA_SIEVE		   			 = new Sieve(Material.WOOD);
	public static final Sieve DARK_OAK_SIEVE	   			 = new Sieve(Material.WOOD);
	public static final Sieve ELDER_SIEVE		   			 = new Sieve(Material.WOOD);

	//Barrels
	public static final Barrel STONE_BARREL 	   			 = new Barrel(1, Material.ROCK);

	public static final Barrel OAK_BARREL		   			 = new Barrel(0, Material.WOOD);
	public static final Barrel BIRCH_BARREL		   			 = new Barrel(0, Material.WOOD);
	public static final Barrel SPRUCE_BARREL	   			 = new Barrel(0, Material.WOOD);
	public static final Barrel JUNGLE_BARREL	   			 = new Barrel(0, Material.WOOD);
	public static final Barrel ACACIA_BARREL	   			 = new Barrel(0, Material.WOOD);
	public static final Barrel DARK_OAK_BARREL	   			 = new Barrel(0, Material.WOOD);
	public static final Barrel ELDER_BARREL		   			 = new Barrel(0, Material.WOOD);

	//Blocks
	public static final Block DUST 				   			 = new CubeFalling(Material.SAND, 2.0F, 0.4F);
	public static final StwH STWH 				   			 = new StwH();
	public static final MeanVine MEAN_VINE			  		 = new MeanVine();
	public static final NetherrackGravel NETHERRACK_GRAVEL	 = new NetherrackGravel();
	public static final Block STONE_DOOR 		   			 = new CustomDoor(Material.ROCK, 10F, 1.5F);
	public static final Block ELDER_DOOR 		   			 = new CustomDoor(Material.WOOD, 10F, 1.5F);
	public static final Block HELLFAYAH_ORE		   			 = new Cube(Material.ROCK, 2.0F, 0.4F);
	public static final Block BLOCK_OF_HELLFAYAH   			 = new Cube(Material.ROCK, 10F, 5F);
	public static final Block BLOCK_OF_SALT		   			 = new Cube(Material.ROCK, 10F, 5F);

	//Tree
	public static final ElderLog ELDER_LOG 		   			 = new ElderLog();
	public static final ElderLeaves ELDER_LEAVES 		   	 = new ElderLeaves();
	public static final Block ELDER_PLANKS 		   			 = new CubeFireResistant(Material.WOOD, 10F, 1.5F);
	public static final ElderSapling ELDER_SAPLING 	   		 = new ElderSapling();

	public static final BlockSlabCommon ELDER_SLAB = (BlockSlabCommon) new BlockSlabCommon.Half(Material.WOOD).setResistance(10F).setHardness(2F);
	public static final BlockSlabCommon ELDER_SLAB_DOUBLE = (BlockSlabCommon) new BlockSlabCommon.Double(Material.WOOD).setResistance(10F).setHardness(2F);
	
	//ItemBlocks
	public static final ItemSlab ITEM_ELDER_SLAB = new ItemSlab(ELDER_SLAB, ELDER_SLAB, ELDER_SLAB_DOUBLE);

	//END_OF_INITIALIZATION

	public static final List<Block> BLOCKS = new ArrayList();

	public static void preInit()
	{
		register();
		addItemBlocks();
		registerTileEntities();
	}

	private static void register()
	{
		//Fluid Blocks
		if (BlocksItems.enableLiquidImpossibility)
			addBlock(ModFluids.BLOCK_LIQUID_IMPOSSIBILITY, Constants.LIQUID_IMPOSSIBILITY);
		if(BlocksItems.enableDistilledWater)
			addBlock(ModFluids.BLOCK_DISTILLED_WATER, Constants.DISTILLED_WATER);
		//Regular Blocks
		if(BlocksItems.enableBarrel) {
			addBlock(STONE_BARREL, Constants.STONE_BARREL);
			addBlock(OAK_BARREL, Constants.OAK_BARREL);
			addBlock(BIRCH_BARREL, Constants.BIRCH_BARREL);
			addBlock(SPRUCE_BARREL, Constants.SPRUCE_BARREL);
			addBlock(JUNGLE_BARREL, Constants.JUNGLE_BARREL);
			addBlock(ACACIA_BARREL, Constants.ACACIA_BARREL);
			addBlock(DARK_OAK_BARREL, Constants.DARK_OAK_BARREL);

			if(BlocksItems.enableElderTree)
				addBlock(ELDER_BARREL, Constants.ELDER_BARREL);
		}
		if(BlocksItems.enableSieve) {
			addBlock(STONE_SIEVE, Constants.STONE_SIEVE);
			addBlock(OAK_SIEVE, Constants.OAK_SIEVE);
			addBlock(BIRCH_SIEVE, Constants.BIRCH_SIEVE);
			addBlock(SPRUCE_SIEVE, Constants.SPRUCE_SIEVE);
			addBlock(JUNGLE_SIEVE, Constants.JUNGLE_SIEVE);
			addBlock(ACACIA_SIEVE, Constants.ACACIA_SIEVE);
			addBlock(DARK_OAK_SIEVE, Constants.DARK_OAK_SIEVE);

			if(BlocksItems.enableElderTree)
				addBlock(ELDER_SIEVE, Constants.ELDER_SIEVE);
		}
		if(BlocksItems.enableAshBonePile)		addBlock(ASH_BONE_PILE, Constants.ASH_BONE_PILE);
		if(BlocksItems.enableFreezer)			addBlock(FREEZER, Constants.FREEZER);
		if(BlocksItems.enableHellmart)			addBlock(HELLMART, Constants.HELLMART);
		if(BlocksItems.enableCondenser) 		addBlock(CONDENSER, Constants.CONDENSER);
		if(BlocksItems.enableNetherrackFurnace)	addBlock(NETHERRACK_FURNACE, Constants.NETHERRACK_FURNACE);
		if(BlocksItems.enableCrucible) {
			addBlock(UNFIRED_CRUCIBLE, Constants.UNFIRED_CRUCIBLE);
			addBlock(CRUCIBLE, Constants.CRUCIBLE);
		}
		if(BlocksItems.enableHellfayah) {
			addBlock(HELLFAYAH_ORE, Constants.HELLFAYAH_ORE);
			addBlock(BLOCK_OF_HELLFAYAH, Constants.BLOCK_OF_HELLFAYAH);
		}
		if(BlocksItems.enableSalt && BlocksItems.enableSaltBlock)
			addBlock(BLOCK_OF_SALT, Constants.BLOCK_OF_SALT);
		if(BlocksItems.enableDust) 				addBlock(DUST, Constants.DUST);
		if(BlocksItems.enableStwH) 				addBlock(STWH, Constants.STWH);
		if(BlocksItems.enableElderTree) {
			addBlock(ELDER_SAPLING, Constants.ELDER_SAPLING);
			addBlock(ELDER_LOG, Constants.ELDER_LOG);
			addBlock(ELDER_LEAVES, Constants.ELDER_LEAVES);
			addBlock(ELDER_PLANKS, Constants.ELDER_PLANKS);
			addBlock(ELDER_SLAB, Constants.ELDER_SLAB);
			addBlock(ELDER_SLAB_DOUBLE, Constants.ELDER_SLAB_DOUBLE);
			addBlock(ELDER_DOOR, Constants.ELDER_DOOR);
		}
		if(BlocksItems.enableNetherrackGravel)	addBlock(NETHERRACK_GRAVEL, Constants.NETHERRACK_GRAVEL);
		if(BlocksItems.enableMeanVine) 			addBlock(MEAN_VINE, Constants.MEAN_VINE);
		if(BlocksItems.enableStoneDoor) 		addBlock(STONE_DOOR, Constants.STONE_DOOR);
	}

	private static void addItemBlocks()
	{
		addItemBlock(STONE_BARREL);
		addItemBlock(OAK_BARREL);
		addItemBlock(BIRCH_BARREL);
		addItemBlock(SPRUCE_BARREL);
		addItemBlock(JUNGLE_BARREL);
		addItemBlock(ACACIA_BARREL);
		addItemBlock(DARK_OAK_BARREL);
		addItemBlock(ELDER_BARREL);
		addItemBlock(STONE_SIEVE);
		addItemBlock(OAK_SIEVE);
		addItemBlock(BIRCH_SIEVE);
		addItemBlock(SPRUCE_SIEVE);
		addItemBlock(JUNGLE_SIEVE);
		addItemBlock(ACACIA_SIEVE);
		addItemBlock(DARK_OAK_SIEVE);
		addItemBlock(ELDER_SIEVE);
		addItemBlock(ASH_BONE_PILE);
		addItemBlock(FREEZER);
		addItemBlock(HELLMART);
		addItemBlock(CONDENSER);
		addItemBlock(NETHERRACK_FURNACE);
		addItemBlock(UNFIRED_CRUCIBLE);
		addItemBlock(CRUCIBLE);
		addItemBlock(HELLFAYAH_ORE);
		addItemBlock(BLOCK_OF_HELLFAYAH);
		addItemBlock(BLOCK_OF_SALT);
		addItemBlock(DUST);
		addItemBlock(STWH);
		addItemBlock(ELDER_SAPLING);
		addItemBlock(ELDER_LOG);
		addItemBlock(ELDER_LEAVES);
		addItemBlock(ELDER_PLANKS);
		addItemBlock(NETHERRACK_GRAVEL);
		addItemBlock(MEAN_VINE);
		
		addItemBlock(ITEM_ELDER_SLAB, Constants.ELDER_SLAB);
	}

	private static void addBlock(final Block block, final String name)
	{
		block.setRegistryName(Constants.MOD_ID, name);
		block.setUnlocalizedName(Objects.requireNonNull(block.getRegistryName()).getResourcePath());
		block.setCreativeTab(Constants.TABNTM);

		BLOCKS.add(block);
	}

	private static void addItemBlock(final Block block)
	{
		if(BLOCKS.contains(block))
			ITEMS.add(new ItemBlock(block).setRegistryName(block.getRegistryName()));
	}
	
	private static void addItemBlock(final ItemBlock block, String name)
	{
		if(BLOCKS.contains(block.getBlock()))
		{
			block.setRegistryName(Constants.MOD_ID, name);
			block.setUnlocalizedName(name);
			block.setCreativeTab(Constants.TABNTM);
			
			ITEMS.add(block);
		}
	}

	@SubscribeEvent
	public void registerBlocks(final RegistryEvent.Register<Block> event)
	{
		BLOCKS.forEach((block) ->
		{
			event.getRegistry().register(block);
		});
	}

	private static void registerTileEntities()
	{
		registerTileEntity(TileBarrel.class, Constants.TE_BARREL);
		registerTileEntity(TileSieve.class, Constants.TE_SIEVE);
		registerTileEntity(TileAshBonePile.class, Constants.TE_ASH_BONE_PILE);
		registerTileEntity(TileFreezer.class, Constants.TE_FREEZER);
		registerTileEntity(TileHellmart.class, Constants.TE_HELLMART);
		registerTileEntity(TileCondenser.class, Constants.TE_CONDENSER);
		registerTileEntity(TileNetherrackFurnace.class, Constants.TE_NETHERRACK_FURNACE);
		registerTileEntity(TileCrucibleStone.class, Constants.TE_CRUCIBLE);
	}

	private static void registerTileEntity(final Class<? extends TileEntity> tileEntityClass, final String name)
	{
		GameRegistry.registerTileEntity(tileEntityClass, new ResourceLocation(Constants.MOD_ID, name));
	}
}