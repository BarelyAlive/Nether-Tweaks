package mod.nethertweaks.handler;

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
	public static final Item STONE_BAR 		  	 = new CustomItem(64, Constants.TAB, new ResourceLocation(Constants.MODID, Constants.STONE_BAR));
	public static final Item PORTAL_CORE	  	 = new CustomItem(64, Constants.TAB, new ResourceLocation(Constants.MODID, Constants.PORTAL_CORE));
	public static final Item END_BOX 		  	 = new CustomItem(64, Constants.TAB, new ResourceLocation(Constants.MODID, Constants.END_BOX));
	public static final Item SALT	 		  	 = new CustomItem(64, Constants.TAB, new ResourceLocation(Constants.MODID, Constants.SALT));
	public static final Item HELLFAYAH 		  	 = new CustomItem(64, Constants.TAB, new ResourceLocation(Constants.MODID, Constants.HELLFAYAH));
	public static final Item ENDER_INFUSED_FRAME = new CustomItem(64, Constants.TAB, new ResourceLocation(Constants.MODID, Constants.ENDER_INFUSED_FRAME));
	public static final Item STRING 		  	 = new CustomItem(64, Constants.TAB, new ResourceLocation(Constants.MODID, Constants.STRING));
	public static final Item PORCELAIN_CLAY	  	 = new CustomItem(64, Constants.TAB, new ResourceLocation(Constants.MODID, Constants.PORCELAIN_CLAY));
	public static final Item POWDER_OF_LIGHT  	 = new CustomItem(64, Constants.TAB, new ResourceLocation(Constants.MODID, Constants.POWDER_OF_LIGHT));
	public static final Item ASH			  	 = new Ash();
	public static final Item COILED_SWORD	  	 = new CoiledSword();
	public static final Item WOOD_CHIPPINGS	  	 = new CustomItem(64, Constants.TAB, new ResourceLocation(Constants.MODID, Constants.WOOD_CHIPPINGS));

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
	public static final Item COOKED_JERKY 	  	 = new ItemFood(6, 1.2F, true).setRegistryName(Constants.MODID, Constants.COOKED_JERKY).setCreativeTab(Constants.TAB);
	public static final Item CANTEEN	 	  	 = new Canteen();

	//ItemBlocks
	public static final Item ITEM_STONE_DOOR  	 = new ItemDoor(Constants.TAB, new ResourceLocation(Constants.MODID, Constants.STONE_DOOR));
	public static final Item ITEM_ELDER_DOOR  	 = new ItemDoor(Constants.TAB, new ResourceLocation(Constants.MODID, Constants.ELDER_DOOR));
	public static final Item ITEM_ELDER_SLAB  	 = new ItemSlab(BlockHandler.ELDER_SLAB, BlockHandler.ELDER_SLAB, BlockHandler.ELDER_SLAB_DOUBLE).setRegistryName(Constants.MODID, Constants.ELDER_SLAB);

	public static void init() {
		registerBuckets();
		addItemBurnTime();
	}
	
	@SubscribeEvent
    public void registerItems(RegistryEvent.Register<Item> event)
    {
    	//Crafting Components
		if(BlocksItems.enableStoneBar)	  		event.getRegistry().register(STONE_BAR);
		if(BlocksItems.enablePortalCore)  		event.getRegistry().register(PORTAL_CORE);
		if(BlocksItems.enableEndBox)		  	event.getRegistry().register(END_BOX);
		if(BlocksItems.enableSalt)		  		event.getRegistry().register(SALT);
		if(BlocksItems.enableHellfayah)  		event.getRegistry().register(HELLFAYAH);
		if(BlocksItems.enableEnderInfusedFrame)	event.getRegistry().register(ENDER_INFUSED_FRAME);
		if(BlocksItems.enableString)		  	event.getRegistry().register(STRING);
		if(BlocksItems.enablePorcelainClay) 	event.getRegistry().register(PORCELAIN_CLAY);
		if(BlocksItems.enablePowderOfLight) 	event.getRegistry().register(POWDER_OF_LIGHT);
		if(BlocksItems.enableAsh) 				event.getRegistry().register(ASH);
		if(BlocksItems.enableWoodChippings)		event.getRegistry().register(WOOD_CHIPPINGS);
		if(BlocksItems.enableCoiledSword)		event.getRegistry().register(COILED_SWORD);

		//Seeds
		if(BlocksItems.enableMushroomSpores)	event.getRegistry().register(MUSHROOM_SPORES);
		if(BlocksItems.enableGrassSeeds)		event.getRegistry().register(GRASS_SEEDS);
		if(BlocksItems.enableCactusSeeds)		event.getRegistry().register(CACTUS_SEEDS);
		if(BlocksItems.enableSugarcaneSeeds)	event.getRegistry().register(SUGARCANE_SEEDS);

		//Crystals
		if(BlocksItems.enableCrystalLight)		event.getRegistry().register(CRYSTAL_OF_LIGHT);

		//Pebbles
		if(BlocksItems.enablePebbleStone)		event.getRegistry().register(PEBBLE_STONE);
		if(BlocksItems.enablePebbleGranite)		event.getRegistry().register(PEBBLE_GRANITE);
		if(BlocksItems.enablePebbleDiorite)		event.getRegistry().register(PEBBLE_DIORITE);
		if(BlocksItems.enablePebbleAndesite)	event.getRegistry().register(PEBBLE_ANDESITE);

		//Meshes
		if(BlocksItems.enableStringMeshes)		event.getRegistry().register(MESH_STRING);
		if(BlocksItems.enableFlintMeshes) 		event.getRegistry().register(MESH_FLINT);
		if(BlocksItems.enableIronMeshes)  		event.getRegistry().register(MESH_IRON);
		if(BlocksItems.enableDiamondMeshes)		event.getRegistry().register(MESH_DIAMOND);

		//Dolls
		if(BlocksItems.enableDollBat)  			event.getRegistry().register(DOLL_BAT);
		if(BlocksItems.enableDollChicken)  		event.getRegistry().register(DOLL_CHICKEN);
		if(BlocksItems.enableDollCow)  			event.getRegistry().register(DOLL_COW);
		if(BlocksItems.enableDollDonkey)  		event.getRegistry().register(DOLL_DONKEY);
		if(BlocksItems.enableDollHorse)  		event.getRegistry().register(DOLL_HORSE);
		if(BlocksItems.enableDollLlama)  		event.getRegistry().register(DOLL_LLAMA);
		if(BlocksItems.enableDollMule)  		event.getRegistry().register(DOLL_MULE);
		if(BlocksItems.enableDollOcelot)  		event.getRegistry().register(DOLL_OCELOT);
		if(BlocksItems.enableDollParrot)  		event.getRegistry().register(DOLL_PARROT);
		if(BlocksItems.enableDollPig)  			event.getRegistry().register(DOLL_PIG);
		if(BlocksItems.enableDollPolarBear)  	event.getRegistry().register(DOLL_POLAR_BEAR);
		if(BlocksItems.enableDollRabbit)  		event.getRegistry().register(DOLL_RABBIT);
		if(BlocksItems.enableDollRedMooshroom)	event.getRegistry().register(DOLL_RED_MOOSHROOM);
		if(BlocksItems.enableDollSheep)  		event.getRegistry().register(DOLL_SHEEP);
		if(BlocksItems.enableDollVillager)		event.getRegistry().register(DOLL_VILLAGER);
		if(BlocksItems.enableDollWolf)  		event.getRegistry().register(DOLL_WOLF);

		//Werkzeuge
		if(BlocksItems.enableGrabberWood)		event.getRegistry().register(GRABBER_WOOD);
		if(BlocksItems.enableGrabberGold)		event.getRegistry().register(GRABBER_GOLD);
		if(BlocksItems.enableGrabberStone)		event.getRegistry().register(GRABBER_STONE);
		if(BlocksItems.enableGrabberIron)		event.getRegistry().register(GRABBER_IRON);
		if(BlocksItems.enableGrabberDiamond)	event.getRegistry().register(GRABBER_DIAMOND);
		if(BlocksItems.enableFlintNBlaze)  		event.getRegistry().register(FLINT_N_BLAZE);
		if(BlocksItems.enableHammerWood)  		event.getRegistry().register(HAMMER_WOOD);
		if(BlocksItems.enableHammerGold)  		event.getRegistry().register(HAMMER_GOLD);
		if(BlocksItems.enableHammerStone)  		event.getRegistry().register(HAMMER_STONE);
		if(BlocksItems.enableHammerIron)  		event.getRegistry().register(HAMMER_IRON);
		if(BlocksItems.enableHammerDiamond)  	event.getRegistry().register(HAMMER_DIAMOND);

		//Tree
		if(BlocksItems.enableElderTree)			event.getRegistry().register(ITEM_ELDER_SLAB);

		//Food & Drinks
		if(BlocksItems.enableJerky)  			event.getRegistry().register(COOKED_JERKY);
		if(BlocksItems.enableCanteen)  			event.getRegistry().register(CANTEEN);

		//Doors
		if(BlocksItems.enableStoneDoor)  		event.getRegistry().register(ITEM_STONE_DOOR);
		if(BlocksItems.enableElderDoor)			event.getRegistry().register(ITEM_ELDER_DOOR);
		
		//Blocks
		if(BlocksItems.enableBarrelStone)		
			event.getRegistry().register(new ItemBlock(BlockHandler.STONE_BARREL).setRegistryName(Constants.MODID, Constants.STONE_BARREL));
		if(BlocksItems.enableBarrelWood) {
			event.getRegistry().register(new ItemBlock(BlockHandler.OAK_BARREL).setRegistryName(Constants.MODID, Constants.OAK_BARREL));
			event.getRegistry().register(new ItemBlock(BlockHandler.BIRCH_BARREL).setRegistryName(Constants.MODID, Constants.BIRCH_BARREL));
			event.getRegistry().register(new ItemBlock(BlockHandler.SPRUCE_BARREL).setRegistryName(Constants.MODID, Constants.SPRUCE_BARREL));
			event.getRegistry().register(new ItemBlock(BlockHandler.JUNGLE_BARREL).setRegistryName(Constants.MODID, Constants.JUNGLE_BARREL));
			event.getRegistry().register(new ItemBlock(BlockHandler.ACACIA_BARREL).setRegistryName(Constants.MODID, Constants.ACACIA_BARREL));
			event.getRegistry().register(new ItemBlock(BlockHandler.DARK_OAK_BARREL).setRegistryName(Constants.MODID, Constants.DARK_OAK_BARREL));
			event.getRegistry().register(new ItemBlock(BlockHandler.ELDER_BARREL).setRegistryName(Constants.MODID, Constants.ELDER_BARREL));
		}
		if(BlocksItems.enableSieve) {
			event.getRegistry().register(new ItemBlock(BlockHandler.STONE_SIEVE).setRegistryName(Constants.MODID, Constants.STONE_SIEVE));
			event.getRegistry().register(new ItemBlock(BlockHandler.OAK_SIEVE).setRegistryName(Constants.MODID, Constants.OAK_SIEVE));
			event.getRegistry().register(new ItemBlock(BlockHandler.BIRCH_SIEVE).setRegistryName(Constants.MODID, Constants.BIRCH_SIEVE));
			event.getRegistry().register(new ItemBlock(BlockHandler.SPRUCE_SIEVE).setRegistryName(Constants.MODID, Constants.SPRUCE_SIEVE));
			event.getRegistry().register(new ItemBlock(BlockHandler.JUNGLE_SIEVE).setRegistryName(Constants.MODID, Constants.JUNGLE_SIEVE));
			event.getRegistry().register(new ItemBlock(BlockHandler.ACACIA_SIEVE).setRegistryName(Constants.MODID, Constants.ACACIA_SIEVE));
			event.getRegistry().register(new ItemBlock(BlockHandler.DARK_OAK_SIEVE).setRegistryName(Constants.MODID, Constants.DARK_OAK_SIEVE));
			event.getRegistry().register(new ItemBlock(BlockHandler.ELDER_SIEVE).setRegistryName(Constants.MODID, Constants.ELDER_SIEVE));
		}
		if(BlocksItems.enableAshBonePile)		event.getRegistry().register(new ItemBlock(BlockHandler.ASH_BONE_PILE).setRegistryName(Constants.MODID, Constants.ASH_BONE_PILE));
		if(BlocksItems.enableFreezer)			event.getRegistry().register(new ItemBlock(BlockHandler.FREEZER).setRegistryName(Constants.MODID, Constants.FREEZER));
		if(BlocksItems.enableHellmart)			event.getRegistry().register(new ItemBlock(BlockHandler.HELLMART).setRegistryName(Constants.MODID, Constants.HELLMART));
		if(BlocksItems.enableCondenser) 		event.getRegistry().register(new ItemBlock(BlockHandler.CONDENSER).setRegistryName(Constants.MODID, Constants.CONDENSER));
		if(BlocksItems.enableNetherrackFurnace)	event.getRegistry().register(new ItemBlock(BlockHandler.NETHERRACK_FURNACE).setRegistryName(Constants.MODID, Constants.NETHERRACK_FURNACE));
		if(BlocksItems.enableCrucible) {
			event.getRegistry().register(new ItemBlock(BlockHandler.UNFIRED_CRUCIBLE).setRegistryName(Constants.MODID, "unfired_" + Constants.CRUCIBLE));
			event.getRegistry().register(new ItemBlock(BlockHandler.CRUCIBLE).setRegistryName(Constants.MODID, Constants.CRUCIBLE));
		}
	
		if(BlocksItems.enableHellfayahOre) 		event.getRegistry().register(new ItemBlock(BlockHandler.HELLFAYAH_ORE).setRegistryName(Constants.MODID, Constants.HELLFAYAH_ORE));
		if(BlocksItems.enableHellfayahBlock) 	event.getRegistry().register(new ItemBlock(BlockHandler.BLOCK_OF_HELLFAYAH).setRegistryName(Constants.MODID, Constants.BLOCK_OF_HELLFAYAH));
		if(BlocksItems.enableSaltBlock) 		event.getRegistry().register(new ItemBlock(BlockHandler.BLOCK_OF_SALT).setRegistryName(Constants.MODID, Constants.BLOCK_OF_SALT));
		if(BlocksItems.enableDust) 				event.getRegistry().register(new ItemBlock(BlockHandler.DUST).setRegistryName(Constants.MODID, Constants.DUST));
		if(BlocksItems.enableStwH) 				event.getRegistry().register(new ItemBlock(BlockHandler.STWH).setRegistryName(Constants.MODID, Constants.STWH));
		if(BlocksItems.enableElderTree) {
			event.getRegistry().register(new ItemBlock(BlockHandler.ELDER_SAPLING).setRegistryName(Constants.MODID, Constants.ELDER_SAPLING));
			event.getRegistry().register(new ItemBlock(BlockHandler.ELDER_LOG).setRegistryName(Constants.MODID, Constants.ELDER_LOG));
			event.getRegistry().register(new ItemBlock(BlockHandler.ELDER_LEAVES).setRegistryName(Constants.MODID, Constants.ELDER_LEAVES));
			event.getRegistry().register(new ItemBlock(BlockHandler.ELDER_PLANKS).setRegistryName(Constants.MODID, Constants.ELDER_PLANKS));
			event.getRegistry().register(new ItemBlock(BlockHandler.ELDER_SLAB).setRegistryName(Constants.MODID, Constants.ELDER_SLAB));
			event.getRegistry().register(new ItemBlock(BlockHandler.ELDER_SLAB_DOUBLE).setRegistryName(Constants.MODID, Constants.ELDER_SLAB_DOUBLE));
		}
		if(BlocksItems.enableNetherrackGravel)	event.getRegistry().register(new ItemBlock(BlockHandler.NETHERRACK_GRAVEL).setRegistryName(Constants.MODID, Constants.NETHERRACK_GRAVEL));
		if(BlocksItems.enableMeanVine) 			event.getRegistry().register(new ItemBlock(BlockHandler.MEAN_VINE).setRegistryName(Constants.MODID, Constants.MEAN_VINE));
		if(BlocksItems.enableStoneDoor) 		event.getRegistry().register(new ItemBlock(BlockHandler.STONE_DOOR).setRegistryName(Constants.MODID, Constants.STONE_DOOR));
		if(BlocksItems.enableElderDoor)			event.getRegistry().register(new ItemBlock(BlockHandler.ELDER_DOOR).setRegistryName(Constants.MODID, Constants.ELDER_DOOR));
    }
	
	private static void registerBuckets()
	{
		if(BlocksItems.enableWoodBucket)
			BucketHandler.addBucket("wood", "Wood", 505, 16, Constants.MODID, 0x80874633, Constants.TAB);
		if(BlocksItems.enableStoneBucket)
			BucketHandler.addBucket("stone", "Stone", -1, 16, Constants.MODID, 0x80778899, Constants.TAB);
	}

	private static void addItemBurnTime()
	{
		if(BlocksItems.enableWoodChippings)
			CustomFuelHandler.addFuelBurnTime(new ItemInfo(WOOD_CHIPPINGS), 100);
		if(BlocksItems.enableHellfayah)
			CustomFuelHandler.addFuelBurnTime(new ItemInfo(HELLFAYAH), Config.burnTimeHellfayah);
		if(BlocksItems.enableHellfayahBlock)
			CustomFuelHandler.addFuelBurnTime(new ItemInfo(BlockHandler.BLOCK_OF_HELLFAYAH), Config.burnTimeHellfayahBlock);
	}
}
