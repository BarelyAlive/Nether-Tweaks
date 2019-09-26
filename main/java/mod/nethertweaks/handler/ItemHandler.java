package mod.nethertweaks.handler;

import java.util.ArrayList;
import java.util.List;

import mod.nethertweaks.Constants;
import mod.nethertweaks.config.BlocksItems;
import mod.nethertweaks.config.Config;
import mod.nethertweaks.items.Ash;
import mod.nethertweaks.items.Canteen;
import mod.nethertweaks.items.CoiledSword;
import mod.nethertweaks.items.Crystal;
import mod.nethertweaks.items.FlintAndBlaze;
import mod.nethertweaks.items.Grabber;
import mod.nethertweaks.items.Hammer;
import mod.nethertweaks.items.ItemDoll;
import mod.nethertweaks.items.ItemMesh;
import mod.nethertweaks.items.ItemPebble;
import mod.nethertweaks.items.Seed;
import mod.sfhcore.blocks.itemblocks.ItemDoor;
import mod.sfhcore.handler.BucketHandler;
import mod.sfhcore.handler.CustomFuelHandler;
import mod.sfhcore.items.CustomItem;
import mod.sfhcore.util.ItemInfo;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemSlab;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ItemHandler
{
	//SEEDS
	public static final Item MUSHROOM_SPORES  	 = new Seed(Constants.MUSHROOM_SPORES);
	public static final Item GRASS_SEEDS	  	 = new Seed(Constants.GRASS_SEEDS);
	public static final Item CACTUS_SEEDS	  	 = new Seed(Constants.CACTUS_SEEDS);
	public static final Item SUGARCANE_SEEDS  	 = new Seed(Constants.SUGARCANE_SEEDS);

	//CRYSTALS
	public static final Item CRYSTAL_OF_LIGHT 	 = new Crystal(Constants.CRYSTAL_OF_LIGHT);

	//MESHES
	public static final Item MESH_STRING	  	 = new ItemMesh(Constants.MESH_STRING);
	public static final Item MESH_FLINT		  	 = new ItemMesh(Constants.MESH_FLINT);
	public static final Item MESH_IRON		  	 = new ItemMesh(Constants.MESH_IRON);
	public static final Item MESH_DIAMOND	  	 = new ItemMesh(Constants.MESH_DIAMOND);

	//DOLLS
	public static final Item DOLL_BAT		  	 = new ItemDoll(Constants.DOLL_BAT);
	public static final Item DOLL_CHICKEN	  	 = new ItemDoll(Constants.DOLL_CHICKEN);
	public static final Item DOLL_COW		  	 = new ItemDoll(Constants.DOLL_COW);
	public static final Item DOLL_DONKEY	  	 = new ItemDoll(Constants.DOLL_DONKEY);
	public static final Item DOLL_HORSE		  	 = new ItemDoll(Constants.DOLL_HORSE);
	public static final Item DOLL_RED_MOOSHROOM	 = new ItemDoll(Constants.DOLL_RED_MOOSHROOM);
	public static final Item DOLL_MULE		  	 = new ItemDoll(Constants.DOLL_MULE);
	public static final Item DOLL_OCELOT	  	 = new ItemDoll(Constants.DOLL_OCELOT);
	public static final Item DOLL_PARROT	  	 = new ItemDoll(Constants.DOLL_PARROT);
	public static final Item DOLL_RABBIT	  	 = new ItemDoll(Constants.DOLL_RABBIT);
	public static final Item DOLL_SHEEP		  	 = new ItemDoll(Constants.DOLL_SHEEP);
	public static final Item DOLL_LLAMA		  	 = new ItemDoll(Constants.DOLL_LLAMA);
	public static final Item DOLL_POLAR_BEAR  	 = new ItemDoll(Constants.DOLL_POLAR_BEAR);
	public static final Item DOLL_WOLF		  	 = new ItemDoll(Constants.DOLL_WOLF);
	public static final Item DOLL_VILLAGER	  	 = new ItemDoll(Constants.DOLL_VILLAGER);
	public static final Item DOLL_PIG		  	 = new ItemDoll(Constants.DOLL_PIG);

	//PEBBLES
	public static final Item PEBBLE_STONE	  	 = new ItemPebble(Constants.PEBBLE_STONE);
	public static final Item PEBBLE_GRANITE	  	 = new ItemPebble(Constants.PEBBLE_GRANITE);
	public static final Item PEBBLE_ANDESITE  	 = new ItemPebble(Constants.PEBBLE_DIORITE);
	public static final Item PEBBLE_DIORITE	  	 = new ItemPebble(Constants.PEBBLE_ANDESITE);

	//Crafting Components
	public static final Item STONE_BAR 		  	 = new CustomItem(64, Constants.TABNTM, new ResourceLocation(Constants.MODID, Constants.STONE_BAR));
	public static final Item PORTAL_CORE	  	 = new CustomItem(64, Constants.TABNTM, new ResourceLocation(Constants.MODID, Constants.PORTAL_CORE));
	public static final Item END_BOX 		  	 = new CustomItem(64, Constants.TABNTM, new ResourceLocation(Constants.MODID, Constants.END_BOX));
	public static final Item SALT	 		  	 = new CustomItem(64, Constants.TABNTM, new ResourceLocation(Constants.MODID, Constants.SALT));
	public static final Item HELLFAYAH 		  	 = new CustomItem(64, Constants.TABNTM, new ResourceLocation(Constants.MODID, Constants.HELLFAYAH));
	public static final Item ENDER_INFUSED_FRAME = new CustomItem(64, Constants.TABNTM, new ResourceLocation(Constants.MODID, Constants.ENDER_INFUSED_FRAME));
	public static final Item STRING 		  	 = new CustomItem(64, Constants.TABNTM, new ResourceLocation(Constants.MODID, Constants.STRING));
	public static final Item PORCELAIN_CLAY	  	 = new CustomItem(64, Constants.TABNTM, new ResourceLocation(Constants.MODID, Constants.PORCELAIN_CLAY));
	public static final Item POWDER_OF_LIGHT  	 = new CustomItem(64, Constants.TABNTM, new ResourceLocation(Constants.MODID, Constants.POWDER_OF_LIGHT));
	public static final Item ASH			  	 = new Ash();
	public static final Item COILED_SWORD	  	 = new CoiledSword();
	public static final Item WOOD_CHIPPINGS	  	 = new CustomItem(64, Constants.TABNTM, new ResourceLocation(Constants.MODID, Constants.WOOD_CHIPPINGS));

	//Tools
	public static final Item GRABBER_WOOD 	  	 = new Grabber(Config.durabilityGWood, ToolMaterial.WOOD);
	public static final Item GRABBER_GOLD 	  	 = new Grabber(Config.durabilityGGold, ToolMaterial.GOLD);
	public static final Item GRABBER_STONE 	  	 = new Grabber(Config.durabilityGStone, ToolMaterial.STONE);
	public static final Item GRABBER_IRON 	  	 = new Grabber(Config.durabilityGIron, ToolMaterial.IRON);
	public static final Item GRABBER_DIAMOND  	 = new Grabber(Config.durabilityGDiamond, ToolMaterial.DIAMOND);

	public static final Item HAMMER_WOOD	  	 = new Hammer(Config.durabilityHWood, ToolMaterial.WOOD);
	public static final Item HAMMER_GOLD 	  	 = new Hammer(Config.durabilityHGold, ToolMaterial.GOLD);
	public static final Item HAMMER_STONE	  	 = new Hammer(Config.durabilityHStone, ToolMaterial.STONE);
	public static final Item HAMMER_IRON 	  	 = new Hammer(Config.durabilityHIron, ToolMaterial.IRON);
	public static final Item HAMMER_DIAMOND	  	 = new Hammer(Config.durabilityHDiamond, ToolMaterial.DIAMOND);

	public static final Item FLINT_N_BLAZE 	  	 = new FlintAndBlaze();

	//Food & DRINKS
	public static final Item COOKED_JERKY 	  	 = new ItemFood(6, 1.2F, true).setRegistryName(Constants.MODID, Constants.COOKED_JERKY).setCreativeTab(Constants.TABNTM);
	public static final Item CANTEEN	 	  	 = new Canteen();

	//ItemBlocks
	public static final Item ITEM_STONE_DOOR  	 = new ItemDoor(Constants.TABNTM, new ResourceLocation(Constants.MODID, Constants.STONE_DOOR));
	public static final Item ITEM_ELDER_DOOR  	 = new ItemDoor(Constants.TABNTM, new ResourceLocation(Constants.MODID, Constants.ELDER_DOOR));
	public static final Item ITEM_ELDER_SLAB  	 = new ItemSlab(BlockHandler.ELDER_SLAB, BlockHandler.ELDER_SLAB, BlockHandler.ELDER_SLAB_DOUBLE).setRegistryName(Constants.MODID, Constants.ELDER_SLAB);

	private static List<Item> items = new ArrayList();
	
	public static List<Item> getItems() {
		return items;
	}
	
	public ItemHandler()
	{
		addItems();
		registerBuckets();
		addItemBurnTime();
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	private void addItems()
	{
		//Items
		if(BlocksItems.enableStoneBar)	  		items.add(STONE_BAR);
		if(BlocksItems.enablePortalCore)  		items.add(PORTAL_CORE);
		if(BlocksItems.enableEndBox)		  	items.add(END_BOX);
		if(BlocksItems.enableSalt)		  		items.add(SALT);
		if(BlocksItems.enableHellfayah)  		items.add(HELLFAYAH);
		if(BlocksItems.enableEnderInfusedFrame)	items.add(ENDER_INFUSED_FRAME);
		if(BlocksItems.enableString)		  	items.add(STRING);
		if(BlocksItems.enablePorcelainClay) 	items.add(PORCELAIN_CLAY);
		if(BlocksItems.enablePowderOfLight) 	items.add(POWDER_OF_LIGHT);
		if(BlocksItems.enableAsh) 				items.add(ASH);
		if(BlocksItems.enableWoodChippings)		items.add(WOOD_CHIPPINGS);
		if(BlocksItems.enableCoiledSword)		items.add(COILED_SWORD);
		if(BlocksItems.enableMushroomSpores)	items.add(MUSHROOM_SPORES);
		if(BlocksItems.enableGrassSeeds)		items.add(GRASS_SEEDS);
		if(BlocksItems.enableCactusSeeds)		items.add(CACTUS_SEEDS);
		if(BlocksItems.enableSugarcaneSeeds)	items.add(SUGARCANE_SEEDS);
		if(BlocksItems.enableCrystalLight)		items.add(CRYSTAL_OF_LIGHT);
		if(BlocksItems.enablePebbleStone)		items.add(PEBBLE_STONE);
		if(BlocksItems.enablePebbleGranite)		items.add(PEBBLE_GRANITE);
		if(BlocksItems.enablePebbleDiorite)		items.add(PEBBLE_DIORITE);
		if(BlocksItems.enablePebbleAndesite)	items.add(PEBBLE_ANDESITE);
		if(BlocksItems.enableStringMeshes)		items.add(MESH_STRING);
		if(BlocksItems.enableFlintMeshes) 		items.add(MESH_FLINT);
		if(BlocksItems.enableIronMeshes)  		items.add(MESH_IRON);
		if(BlocksItems.enableDiamondMeshes)		items.add(MESH_DIAMOND);
		if(BlocksItems.enableDollBat)  			items.add(DOLL_BAT);
		if(BlocksItems.enableDollChicken)  		items.add(DOLL_CHICKEN);
		if(BlocksItems.enableDollCow)  			items.add(DOLL_COW);
		if(BlocksItems.enableDollDonkey)  		items.add(DOLL_DONKEY);
		if(BlocksItems.enableDollHorse)  		items.add(DOLL_HORSE);
		if(BlocksItems.enableDollLlama)  		items.add(DOLL_LLAMA);
		if(BlocksItems.enableDollMule)  		items.add(DOLL_MULE);
		if(BlocksItems.enableDollOcelot)  		items.add(DOLL_OCELOT);
		if(BlocksItems.enableDollParrot)  		items.add(DOLL_PARROT);
		if(BlocksItems.enableDollPig)  			items.add(DOLL_PIG);
		if(BlocksItems.enableDollPolarBear)  	items.add(DOLL_POLAR_BEAR);
		if(BlocksItems.enableDollRabbit)  		items.add(DOLL_RABBIT);
		if(BlocksItems.enableDollRedMooshroom)	items.add(DOLL_RED_MOOSHROOM);
		if(BlocksItems.enableDollSheep)  		items.add(DOLL_SHEEP);
		if(BlocksItems.enableDollVillager)		items.add(DOLL_VILLAGER);
		if(BlocksItems.enableDollWolf)  		items.add(DOLL_WOLF);
		if(BlocksItems.enableGrabberWood)		items.add(GRABBER_WOOD);
		if(BlocksItems.enableGrabberGold)		items.add(GRABBER_GOLD);
		if(BlocksItems.enableGrabberStone)		items.add(GRABBER_STONE);
		if(BlocksItems.enableGrabberIron)		items.add(GRABBER_IRON);
		if(BlocksItems.enableGrabberDiamond)	items.add(GRABBER_DIAMOND);
		if(BlocksItems.enableFlintNBlaze)  		items.add(FLINT_N_BLAZE);
		if(BlocksItems.enableHammerWood)  		items.add(HAMMER_WOOD);
		if(BlocksItems.enableHammerGold)  		items.add(HAMMER_GOLD);
		if(BlocksItems.enableHammerStone)  		items.add(HAMMER_STONE);
		if(BlocksItems.enableHammerIron)  		items.add(HAMMER_IRON);
		if(BlocksItems.enableHammerDiamond)  	items.add(HAMMER_DIAMOND);
		if(BlocksItems.enableElderTree)			items.add(ITEM_ELDER_SLAB);
		if(BlocksItems.enableJerky)  			items.add(COOKED_JERKY);
		if(BlocksItems.enableCanteen)  			items.add(CANTEEN);
		if(BlocksItems.enableStoneDoor)  		items.add(ITEM_STONE_DOOR);
		if(BlocksItems.enableElderDoor)			items.add(ITEM_ELDER_DOOR);

		//Blocks
		if(BlocksItems.enableBarrel) {
			items.add(new ItemBlock(BlockHandler.STONE_BARREL).setRegistryName(Constants.MODID, Constants.STONE_BARREL));
			items.add(new ItemBlock(BlockHandler.OAK_BARREL).setRegistryName(Constants.MODID, Constants.OAK_BARREL));
			items.add(new ItemBlock(BlockHandler.BIRCH_BARREL).setRegistryName(Constants.MODID, Constants.BIRCH_BARREL));
			items.add(new ItemBlock(BlockHandler.SPRUCE_BARREL).setRegistryName(Constants.MODID, Constants.SPRUCE_BARREL));
			items.add(new ItemBlock(BlockHandler.JUNGLE_BARREL).setRegistryName(Constants.MODID, Constants.JUNGLE_BARREL));
			items.add(new ItemBlock(BlockHandler.ACACIA_BARREL).setRegistryName(Constants.MODID, Constants.ACACIA_BARREL));
			items.add(new ItemBlock(BlockHandler.DARK_OAK_BARREL).setRegistryName(Constants.MODID, Constants.DARK_OAK_BARREL));
			items.add(new ItemBlock(BlockHandler.ELDER_BARREL).setRegistryName(Constants.MODID, Constants.ELDER_BARREL));
		}
		if(BlocksItems.enableSieve) {
			items.add(new ItemBlock(BlockHandler.STONE_SIEVE).setRegistryName(Constants.MODID, Constants.STONE_SIEVE));
			items.add(new ItemBlock(BlockHandler.OAK_SIEVE).setRegistryName(Constants.MODID, Constants.OAK_SIEVE));
			items.add(new ItemBlock(BlockHandler.BIRCH_SIEVE).setRegistryName(Constants.MODID, Constants.BIRCH_SIEVE));
			items.add(new ItemBlock(BlockHandler.SPRUCE_SIEVE).setRegistryName(Constants.MODID, Constants.SPRUCE_SIEVE));
			items.add(new ItemBlock(BlockHandler.JUNGLE_SIEVE).setRegistryName(Constants.MODID, Constants.JUNGLE_SIEVE));
			items.add(new ItemBlock(BlockHandler.ACACIA_SIEVE).setRegistryName(Constants.MODID, Constants.ACACIA_SIEVE));
			items.add(new ItemBlock(BlockHandler.DARK_OAK_SIEVE).setRegistryName(Constants.MODID, Constants.DARK_OAK_SIEVE));
			items.add(new ItemBlock(BlockHandler.ELDER_SIEVE).setRegistryName(Constants.MODID, Constants.ELDER_SIEVE));
		}
		if(BlocksItems.enableAshBonePile)		items.add(new ItemBlock(BlockHandler.ASH_BONE_PILE).setRegistryName(Constants.MODID, Constants.ASH_BONE_PILE));
		if(BlocksItems.enableFreezer)			items.add(new ItemBlock(BlockHandler.FREEZER).setRegistryName(Constants.MODID, Constants.FREEZER));
		if(BlocksItems.enableHellmart)			items.add(new ItemBlock(BlockHandler.HELLMART).setRegistryName(Constants.MODID, Constants.HELLMART));
		if(BlocksItems.enableCondenser) 		items.add(new ItemBlock(BlockHandler.CONDENSER).setRegistryName(Constants.MODID, Constants.CONDENSER));
		if(BlocksItems.enableNetherrackFurnace)	items.add(new ItemBlock(BlockHandler.NETHERRACK_FURNACE).setRegistryName(Constants.MODID, Constants.NETHERRACK_FURNACE));
		if(BlocksItems.enableCrucible) {
			items.add(new ItemBlock(BlockHandler.UNFIRED_CRUCIBLE).setRegistryName(Constants.MODID, "unfired_" + Constants.CRUCIBLE));
			items.add(new ItemBlock(BlockHandler.CRUCIBLE).setRegistryName(Constants.MODID, Constants.CRUCIBLE));
		}
		if(BlocksItems.enableHellfayahOre) 		items.add(new ItemBlock(BlockHandler.HELLFAYAH_ORE).setRegistryName(Constants.MODID, Constants.HELLFAYAH_ORE));
		if(BlocksItems.enableHellfayahBlock) 	items.add(new ItemBlock(BlockHandler.BLOCK_OF_HELLFAYAH).setRegistryName(Constants.MODID, Constants.BLOCK_OF_HELLFAYAH));
		if(BlocksItems.enableSaltBlock) 		items.add(new ItemBlock(BlockHandler.BLOCK_OF_SALT).setRegistryName(Constants.MODID, Constants.BLOCK_OF_SALT));
		if(BlocksItems.enableDust) 				items.add(new ItemBlock(BlockHandler.DUST).setRegistryName(Constants.MODID, Constants.DUST));
		if(BlocksItems.enableStwH) 				items.add(new ItemBlock(BlockHandler.STWH).setRegistryName(Constants.MODID, Constants.STWH));
		if(BlocksItems.enableElderTree) {
			items.add(new ItemBlock(BlockHandler.ELDER_SAPLING).setRegistryName(Constants.MODID, Constants.ELDER_SAPLING));
			items.add(new ItemBlock(BlockHandler.ELDER_LOG).setRegistryName(Constants.MODID, Constants.ELDER_LOG));
			items.add(new ItemBlock(BlockHandler.ELDER_LEAVES).setRegistryName(Constants.MODID, Constants.ELDER_LEAVES));
			items.add(new ItemBlock(BlockHandler.ELDER_PLANKS).setRegistryName(Constants.MODID, Constants.ELDER_PLANKS));
		}
		if(BlocksItems.enableNetherrackGravel)	items.add(new ItemBlock(BlockHandler.NETHERRACK_GRAVEL).setRegistryName(Constants.MODID, Constants.NETHERRACK_GRAVEL));
		if(BlocksItems.enableMeanVine) 			items.add(new ItemBlock(BlockHandler.MEAN_VINE).setRegistryName(Constants.MODID, Constants.MEAN_VINE));
	}

	@SubscribeEvent
	public void registerItems(final RegistryEvent.Register<Item> event)
	{
		for(Item item : items)
			event.getRegistry().register(item);
	}

	private void registerBuckets()
	{
		if(BlocksItems.enableWoodBucket)
			BucketHandler.addBucket("wood", "Wood", 505, 16, Constants.MODID, 0x80874633, Constants.TABNTM);
		if(BlocksItems.enableStoneBucket)
			BucketHandler.addBucket("stone", "Stone", -1, 16, Constants.MODID, 0x80778899, Constants.TABNTM);
	}

	private void addItemBurnTime()
	{
		if(BlocksItems.enableWoodChippings)
			CustomFuelHandler.addFuelBurnTime(new ItemInfo(WOOD_CHIPPINGS), 100);
		if(BlocksItems.enableHellfayah)
			CustomFuelHandler.addFuelBurnTime(new ItemInfo(HELLFAYAH), Config.burnTimeHellfayah);
		if(BlocksItems.enableHellfayahBlock)
			CustomFuelHandler.addFuelBurnTime(new ItemInfo(BlockHandler.BLOCK_OF_HELLFAYAH), Config.burnTimeHellfayahBlock);
	}
}
