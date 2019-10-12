package mod.nethertweaks.handler;

import static mod.nethertweaks.handler.ItemHandler.ITEMS;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
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
	public static final Block CRUCIBLE		 	   = new CrucibleStone(true);
	public static final Block UNFIRED_CRUCIBLE	   = new CrucibleStone(false);

	//Sieves
	public static final Block STONE_SIEVE		   = new Sieve(Material.ROCK);

	public static final Block OAK_SIEVE			   = new Sieve(Material.WOOD);
	public static final Block BIRCH_SIEVE		   = new Sieve(Material.WOOD);
	public static final Block SPRUCE_SIEVE		   = new Sieve(Material.WOOD);
	public static final Block JUNGLE_SIEVE		   = new Sieve(Material.WOOD);
	public static final Block ACACIA_SIEVE		   = new Sieve(Material.WOOD);
	public static final Block DARK_OAK_SIEVE	   = new Sieve(Material.WOOD);
	public static final Block ELDER_SIEVE		   = new Sieve(Material.WOOD);

	//Barrels
	public static final Block STONE_BARREL 		   = new Barrel(1, Material.ROCK);

	public static final Block OAK_BARREL		   = new Barrel(0, Material.WOOD);
	public static final Block BIRCH_BARREL		   = new Barrel(0, Material.WOOD);
	public static final Block SPRUCE_BARREL		   = new Barrel(0, Material.WOOD);
	public static final Block JUNGLE_BARREL		   = new Barrel(0, Material.WOOD);
	public static final Block ACACIA_BARREL		   = new Barrel(0, Material.WOOD);
	public static final Block DARK_OAK_BARREL	   = new Barrel(0, Material.WOOD);
	public static final Block ELDER_BARREL		   = new Barrel(0, Material.WOOD);

	//Blocks
	public static final Block DUST 				   = new CubeFalling(Material.SAND, 2.0F, 0.4F);
	public static final Block STWH 				   = new StwH();
	public static final Block MEAN_VINE			   = new MeanVine();
	public static final Block NETHERRACK_GRAVEL	   = new NetherrackGravel();
	public static final Block STONE_DOOR 		   = new CustomDoor(Material.ROCK, 10F, 1.5F);
	public static final Block ELDER_DOOR 		   = new CustomDoor(Material.WOOD, 10F, 1.5F);
	public static final Block HELLFAYAH_ORE		   = new Cube(Material.ROCK, 2.0F, 0.4F);
	public static final Block BLOCK_OF_HELLFAYAH   = new Cube(Material.ROCK, 10F, 5F);
	public static final Block BLOCK_OF_SALT		   = new Cube(Material.ROCK, 10F, 5F);

	//Tree
	public static final Block ELDER_LOG 		   = new ElderLog();
	public static final Block ELDER_LEAVES 		   = new ElderLeaves();
	public static final Block ELDER_PLANKS 		   = new CubeFireResistant(Material.WOOD, 10F, 1.5F);
	public static final Block ELDER_SAPLING 	   = new ElderSapling();

	public static final BlockSlabCommon ELDER_SLAB = (BlockSlabCommon) new BlockSlabCommon.Half(Material.WOOD).setResistance(10F).setHardness(2F);
	public static final BlockSlabCommon ELDER_SLAB_DOUBLE = (BlockSlabCommon) new BlockSlabCommon.Double(Material.WOOD).setResistance(10F).setHardness(2F);

	//END_OF_INITIALIZATION
	
	public static final List<Block> BLOCKS = new ArrayList();

	public BlockHandler()
	{
		register();
		addItemBlocks();
		registerTileEntities();
		MinecraftForge.EVENT_BUS.register(this);
	}

	private void register()
	{
		//Fluid Blocks
		if (BlocksItems.enableLiquidImpossibility)
			addBlock(FluidHandler.BLOCK_LIQUID_IMPOSSIBILITY, Constants.LIQUID_IMPOSSIBILITY);
		if(BlocksItems.enableDistilledWater)
			addBlock(FluidHandler.BLOCK_DISTILLED_WATER, Constants.DISTILLED_WATER);
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
		if(BlocksItems.enableSaltBlock) 		addBlock(BLOCK_OF_SALT, Constants.BLOCK_OF_SALT);
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
	
	private void addItemBlocks()
	{		
		//Regular Blocks
		if(BlocksItems.enableBarrel) {
			ITEMS.add(new ItemBlock(STONE_BARREL).setRegistryName(Constants.STONE_BARREL));
			ITEMS.add(new ItemBlock(OAK_BARREL).setRegistryName(Constants.OAK_BARREL));
			ITEMS.add(new ItemBlock(BIRCH_BARREL).setRegistryName(Constants.BIRCH_BARREL));
			ITEMS.add(new ItemBlock(SPRUCE_BARREL).setRegistryName(Constants.SPRUCE_BARREL));
			ITEMS.add(new ItemBlock(JUNGLE_BARREL).setRegistryName(Constants.JUNGLE_BARREL));
			ITEMS.add(new ItemBlock(ACACIA_BARREL).setRegistryName(Constants.ACACIA_BARREL));
			ITEMS.add(new ItemBlock(DARK_OAK_BARREL).setRegistryName(Constants.DARK_OAK_BARREL));

			if(BlocksItems.enableElderTree)
				ITEMS.add(new ItemBlock(ELDER_BARREL).setRegistryName(Constants.ELDER_BARREL));
		}
		if(BlocksItems.enableSieve) {
			ITEMS.add(new ItemBlock(STONE_SIEVE).setRegistryName(Constants.STONE_SIEVE));
			ITEMS.add(new ItemBlock(OAK_SIEVE).setRegistryName(Constants.OAK_SIEVE));
			ITEMS.add(new ItemBlock(BIRCH_SIEVE).setRegistryName(Constants.BIRCH_SIEVE));
			ITEMS.add(new ItemBlock(SPRUCE_SIEVE).setRegistryName(Constants.SPRUCE_SIEVE));
			ITEMS.add(new ItemBlock(JUNGLE_SIEVE).setRegistryName(Constants.JUNGLE_SIEVE));
			ITEMS.add(new ItemBlock(ACACIA_SIEVE).setRegistryName(Constants.ACACIA_SIEVE));
			ITEMS.add(new ItemBlock(DARK_OAK_SIEVE).setRegistryName(Constants.DARK_OAK_SIEVE));

			if(BlocksItems.enableElderTree)
				ITEMS.add(new ItemBlock(ELDER_SIEVE).setRegistryName(Constants.ELDER_SIEVE));
		}
		if(BlocksItems.enableAshBonePile)		ITEMS.add(new ItemBlock(ASH_BONE_PILE).setRegistryName(Constants.ASH_BONE_PILE));
		if(BlocksItems.enableFreezer)			ITEMS.add(new ItemBlock(FREEZER).setRegistryName(Constants.FREEZER));
		if(BlocksItems.enableHellmart)			ITEMS.add(new ItemBlock(HELLMART).setRegistryName(Constants.HELLMART));
		if(BlocksItems.enableCondenser) 		ITEMS.add(new ItemBlock(CONDENSER).setRegistryName(Constants.CONDENSER));
		if(BlocksItems.enableNetherrackFurnace)	ITEMS.add(new ItemBlock(NETHERRACK_FURNACE).setRegistryName(Constants.NETHERRACK_FURNACE));
		if(BlocksItems.enableCrucible) {
			ITEMS.add(new ItemBlock(UNFIRED_CRUCIBLE).setRegistryName(Constants.UNFIRED_CRUCIBLE));
			ITEMS.add(new ItemBlock(CRUCIBLE).setRegistryName(Constants.CRUCIBLE));
		}
		if(BlocksItems.enableHellfayah) {
			ITEMS.add(new ItemBlock(HELLFAYAH_ORE).setRegistryName(Constants.HELLFAYAH_ORE));
			ITEMS.add(new ItemBlock(BLOCK_OF_HELLFAYAH).setRegistryName(Constants.BLOCK_OF_HELLFAYAH));
		}
		if(BlocksItems.enableSaltBlock) 		ITEMS.add(new ItemBlock(BLOCK_OF_SALT).setRegistryName(Constants.BLOCK_OF_SALT));
		if(BlocksItems.enableDust) 				ITEMS.add(new ItemBlock(DUST).setRegistryName(Constants.DUST));
		if(BlocksItems.enableStwH) 				ITEMS.add(new ItemBlock(STWH).setRegistryName(Constants.STWH));
		if(BlocksItems.enableElderTree) {
			ITEMS.add(new ItemBlock(ELDER_SAPLING).setRegistryName(Constants.ELDER_SAPLING));
			ITEMS.add(new ItemBlock(ELDER_LOG).setRegistryName(Constants.ELDER_LOG));
			ITEMS.add(new ItemBlock(ELDER_LEAVES).setRegistryName(Constants.ELDER_LEAVES));
			ITEMS.add(new ItemBlock(ELDER_PLANKS).setRegistryName(Constants.ELDER_PLANKS));
		}
		if(BlocksItems.enableNetherrackGravel)	ITEMS.add(new ItemBlock(NETHERRACK_GRAVEL).setRegistryName(Constants.NETHERRACK_GRAVEL));
		if(BlocksItems.enableMeanVine) 			ITEMS.add(new ItemBlock(MEAN_VINE).setRegistryName(Constants.MEAN_VINE));
	}
		
	private void addBlock(Block block, String name)
	{
		block.setRegistryName(Constants.MOD_ID, name);
		block.setUnlocalizedName(Objects.requireNonNull(block.getRegistryName()).getResourcePath());
		block.setCreativeTab(Constants.TABNTM);
		
		BLOCKS.add(block);	
	}

	@SubscribeEvent
	public void registerBlocks(final RegistryEvent.Register<Block> event)
	{
		BLOCKS.forEach((block) ->
		{			
			event.getRegistry().register(block);
		});
	}

	private void registerTileEntities()
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
	
	private void registerTileEntity(final Class<? extends TileEntity> tileEntityClass, String name)
	{
		GameRegistry.registerTileEntity(tileEntityClass, new ResourceLocation(Constants.MOD_ID, name));
	}
}