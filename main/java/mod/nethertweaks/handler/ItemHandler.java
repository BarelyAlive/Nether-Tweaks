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
import mod.sfhcore.handler.CustomFuelHandler;
import mod.sfhcore.items.CustomItem;
import mod.sfhcore.registries.Registry;
import mod.sfhcore.util.ItemInfo;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemSlab;
import net.minecraft.util.ResourceLocation;

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
	public static final Item ITEM_ELDER_SLAB  	 = new ItemSlab(BlockHandler.ELDER_SLAB, BlockHandler.ELDER_SLAB, BlockHandler.ELDER_SLAB_DOUBLE).setRegistryName(Constants.MODID, "item_" + Constants.ELDER_SLAB);

	public static void init()
	{
		registerItems();
		addItemBurnTime();
	}

	private static void registerItems()
	{
		//Crafting Components
		if(BlocksItems.enableStoneBar)	  		Registry.registerItem(STONE_BAR);
		if(BlocksItems.enablePortalCore)  		Registry.registerItem(PORTAL_CORE);
		if(BlocksItems.enableEndBox)		  	Registry.registerItem(END_BOX);
		if(BlocksItems.enableSalt)		  		Registry.registerItem(SALT);
		if(BlocksItems.enableHellfayah)  		Registry.registerItem(HELLFAYAH);
		if(BlocksItems.enableEnderInfusedFrame)	Registry.registerItem(ENDER_INFUSED_FRAME);
		if(BlocksItems.enableString)		  	Registry.registerItem(STRING);
		if(BlocksItems.enablePorcelainClay) 	Registry.registerItem(PORCELAIN_CLAY);
		if(BlocksItems.enablePowderOfLight) 	Registry.registerItem(POWDER_OF_LIGHT);
		if(BlocksItems.enableAsh) 				Registry.registerItem(ASH);
		if(BlocksItems.enableWoodChippings)		Registry.registerItem(WOOD_CHIPPINGS);
		if(BlocksItems.enableCoiledSword)		Registry.registerItem(COILED_SWORD);

		//Seeds
		if(BlocksItems.enableMushroomSpores)	Registry.registerItem(MUSHROOM_SPORES);
		if(BlocksItems.enableGrassSeeds)		Registry.registerItem(GRASS_SEEDS);
		if(BlocksItems.enableCactusSeeds)		Registry.registerItem(CACTUS_SEEDS);
		if(BlocksItems.enableSugarcaneSeeds)	Registry.registerItem(SUGARCANE_SEEDS);

		//Crystals
		if(BlocksItems.enableCrystalLight)		Registry.registerItem(CRYSTAL_OF_LIGHT);

		//Pebbles
		if(BlocksItems.enablePebbleStone)		Registry.registerItem(PEBBLE_STONE);
		if(BlocksItems.enablePebbleGranite)		Registry.registerItem(PEBBLE_GRANITE);
		if(BlocksItems.enablePebbleDiorite)		Registry.registerItem(PEBBLE_DIORITE);
		if(BlocksItems.enablePebbleAndesite)	Registry.registerItem(PEBBLE_ANDESITE);

		//Meshes
		if(BlocksItems.enableStringMeshes)		Registry.registerItem(MESH_STRING);
		if(BlocksItems.enableFlintMeshes) 		Registry.registerItem(MESH_FLINT);
		if(BlocksItems.enableIronMeshes)  		Registry.registerItem(MESH_IRON);
		if(BlocksItems.enableDiamondMeshes)		Registry.registerItem(MESH_DIAMOND);

		//Dolls
		if(BlocksItems.enableDollBat)  			Registry.registerItem(DOLL_BAT);
		if(BlocksItems.enableDollChicken)  		Registry.registerItem(DOLL_CHICKEN);
		if(BlocksItems.enableDollCow)  			Registry.registerItem(DOLL_COW);
		if(BlocksItems.enableDollDonkey)  		Registry.registerItem(DOLL_DONKEY);
		if(BlocksItems.enableDollHorse)  		Registry.registerItem(DOLL_HORSE);
		if(BlocksItems.enableDollLlama)  		Registry.registerItem(DOLL_LLAMA);
		if(BlocksItems.enableDollMule)  		Registry.registerItem(DOLL_MULE);
		if(BlocksItems.enableDollOcelot)  		Registry.registerItem(DOLL_OCELOT);
		if(BlocksItems.enableDollParrot)  		Registry.registerItem(DOLL_PARROT);
		if(BlocksItems.enableDollPig)  			Registry.registerItem(DOLL_PIG);
		if(BlocksItems.enableDollPolarBear)  	Registry.registerItem(DOLL_POLAR_BEAR);
		if(BlocksItems.enableDollRabbit)  		Registry.registerItem(DOLL_RABBIT);
		if(BlocksItems.enableDollRedMooshroom)	Registry.registerItem(DOLL_RED_MOOSHROOM);
		if(BlocksItems.enableDollSheep)  		Registry.registerItem(DOLL_SHEEP);
		if(BlocksItems.enableDollVillager)		Registry.registerItem(DOLL_VILLAGER);
		if(BlocksItems.enableDollWolf)  		Registry.registerItem(DOLL_WOLF);

		//Werkzeuge
		if(BlocksItems.enableGrabberWood)		Registry.registerItem(GRABBER_WOOD);
		if(BlocksItems.enableGrabberGold)		Registry.registerItem(GRABBER_GOLD);
		if(BlocksItems.enableGrabberStone)		Registry.registerItem(GRABBER_STONE);
		if(BlocksItems.enableGrabberIron)		Registry.registerItem(GRABBER_IRON);
		if(BlocksItems.enableGrabberDiamond)	Registry.registerItem(GRABBER_DIAMOND);
		if(BlocksItems.enableFlintNBlaze)  		Registry.registerItem(FLINT_N_BLAZE);
		if(BlocksItems.enableHammerWood)  		Registry.registerItem(HAMMER_WOOD);
		if(BlocksItems.enableHammerGold)  		Registry.registerItem(HAMMER_GOLD);
		if(BlocksItems.enableHammerStone)  		Registry.registerItem(HAMMER_STONE);
		if(BlocksItems.enableHammerIron)  		Registry.registerItem(HAMMER_IRON);
		if(BlocksItems.enableHammerDiamond)  	Registry.registerItem(HAMMER_DIAMOND);

		//Tree
		if(BlocksItems.enableElderTree)			Registry.registerItem(ITEM_ELDER_SLAB);

		//Food & Drinks
		if(BlocksItems.enableJerky)  			Registry.registerItem(COOKED_JERKY);
		if(BlocksItems.enableCanteen)  			Registry.registerItem(CANTEEN);

		//Doors
		if(BlocksItems.enableStoneDoor)  		Registry.registerItem(ITEM_STONE_DOOR);
		if(BlocksItems.enableElderDoor)			Registry.registerItem(ITEM_ELDER_DOOR);
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
