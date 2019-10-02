package mod.nethertweaks.handler;

import java.util.ArrayList;
import java.util.List;

import mod.nethertweaks.Constants;
import mod.nethertweaks.config.BlocksItems;
import mod.nethertweaks.config.Config;
import mod.nethertweaks.items.Ash;
import mod.nethertweaks.items.Canteen;
import mod.nethertweaks.items.CoiledSword;
import mod.nethertweaks.items.CrystalOfLight;
import mod.nethertweaks.items.FlintAndBlaze;
import mod.nethertweaks.items.Grabber;
import mod.nethertweaks.items.Hammer;
import mod.nethertweaks.items.ItemDoll;
import mod.nethertweaks.items.ItemMesh;
import mod.nethertweaks.items.ItemPebble;
import mod.nethertweaks.items.Seed;
import mod.sfhcore.blocks.CustomDoor;
import mod.sfhcore.blocks.itemblocks.ItemDoor;
import mod.sfhcore.handler.CustomFuelHandler;
import mod.sfhcore.items.CustomItem;
import mod.sfhcore.util.ItemInfo;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
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
	public static final Item CRYSTAL_OF_LIGHT 	 = new CrystalOfLight(Constants.CRYSTAL_OF_LIGHT);

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

	public static final List<Item> ITEMS = new ArrayList();

	public ItemHandler()
	{
		addItems();
		addItemBurnTime();
		MinecraftForge.EVENT_BUS.register(this);
	}

	private void addItems()
	{
		//Items
		if(BlocksItems.enableStoneBar)	  		ITEMS.add(STONE_BAR);
		if(BlocksItems.enablePortalCore)  		ITEMS.add(PORTAL_CORE);
		if(BlocksItems.enableEndBox)		  	ITEMS.add(END_BOX);
		if(BlocksItems.enableSalt)		  		ITEMS.add(SALT);
		if(BlocksItems.enableHellfayah)  		ITEMS.add(HELLFAYAH);
		if(BlocksItems.enableEnderInfusedFrame)	ITEMS.add(ENDER_INFUSED_FRAME);
		if(BlocksItems.enableString)		  	ITEMS.add(STRING);
		if(BlocksItems.enablePorcelainClay) 	ITEMS.add(PORCELAIN_CLAY);
		if(BlocksItems.enablePowderOfLight) 	ITEMS.add(POWDER_OF_LIGHT);
		if(BlocksItems.enableAsh) 				ITEMS.add(ASH);
		if(BlocksItems.enableWoodChippings)		ITEMS.add(WOOD_CHIPPINGS);
		if(BlocksItems.enableCoiledSword)		ITEMS.add(COILED_SWORD);
		if(BlocksItems.enableMushroomSpores)	ITEMS.add(MUSHROOM_SPORES);
		if(BlocksItems.enableGrassSeeds)		ITEMS.add(GRASS_SEEDS);
		if(BlocksItems.enableCactusSeeds)		ITEMS.add(CACTUS_SEEDS);
		if(BlocksItems.enableSugarcaneSeeds)	ITEMS.add(SUGARCANE_SEEDS);
		if(BlocksItems.enableCrystalLight)		ITEMS.add(CRYSTAL_OF_LIGHT);
		if(BlocksItems.enablePebbleStone)		ITEMS.add(PEBBLE_STONE);
		if(BlocksItems.enablePebbleGranite)		ITEMS.add(PEBBLE_GRANITE);
		if(BlocksItems.enablePebbleDiorite)		ITEMS.add(PEBBLE_DIORITE);
		if(BlocksItems.enablePebbleAndesite)	ITEMS.add(PEBBLE_ANDESITE);
		if(BlocksItems.enableStringMeshes)		ITEMS.add(MESH_STRING);
		if(BlocksItems.enableFlintMeshes) 		ITEMS.add(MESH_FLINT);
		if(BlocksItems.enableIronMeshes)  		ITEMS.add(MESH_IRON);
		if(BlocksItems.enableDiamondMeshes)		ITEMS.add(MESH_DIAMOND);
		if(BlocksItems.enableDollBat)  			ITEMS.add(DOLL_BAT);
		if(BlocksItems.enableDollChicken)  		ITEMS.add(DOLL_CHICKEN);
		if(BlocksItems.enableDollCow)  			ITEMS.add(DOLL_COW);
		if(BlocksItems.enableDollDonkey)  		ITEMS.add(DOLL_DONKEY);
		if(BlocksItems.enableDollHorse)  		ITEMS.add(DOLL_HORSE);
		if(BlocksItems.enableDollLlama)  		ITEMS.add(DOLL_LLAMA);
		if(BlocksItems.enableDollMule)  		ITEMS.add(DOLL_MULE);
		if(BlocksItems.enableDollOcelot)  		ITEMS.add(DOLL_OCELOT);
		if(BlocksItems.enableDollParrot)  		ITEMS.add(DOLL_PARROT);
		if(BlocksItems.enableDollPig)  			ITEMS.add(DOLL_PIG);
		if(BlocksItems.enableDollPolarBear)  	ITEMS.add(DOLL_POLAR_BEAR);
		if(BlocksItems.enableDollRabbit)  		ITEMS.add(DOLL_RABBIT);
		if(BlocksItems.enableDollRedMooshroom)	ITEMS.add(DOLL_RED_MOOSHROOM);
		if(BlocksItems.enableDollSheep)  		ITEMS.add(DOLL_SHEEP);
		if(BlocksItems.enableDollVillager)		ITEMS.add(DOLL_VILLAGER);
		if(BlocksItems.enableDollWolf)  		ITEMS.add(DOLL_WOLF);
		if(BlocksItems.enableGrabberWood)		ITEMS.add(GRABBER_WOOD);
		if(BlocksItems.enableGrabberGold)		ITEMS.add(GRABBER_GOLD);
		if(BlocksItems.enableGrabberStone)		ITEMS.add(GRABBER_STONE);
		if(BlocksItems.enableGrabberIron)		ITEMS.add(GRABBER_IRON);
		if(BlocksItems.enableGrabberDiamond)	ITEMS.add(GRABBER_DIAMOND);
		if(BlocksItems.enableFlintNBlaze)  		ITEMS.add(FLINT_N_BLAZE);
		if(BlocksItems.enableHammerWood)  		ITEMS.add(HAMMER_WOOD);
		if(BlocksItems.enableHammerGold)  		ITEMS.add(HAMMER_GOLD);
		if(BlocksItems.enableHammerStone)  		ITEMS.add(HAMMER_STONE);
		if(BlocksItems.enableHammerIron)  		ITEMS.add(HAMMER_IRON);
		if(BlocksItems.enableHammerDiamond)  	ITEMS.add(HAMMER_DIAMOND);
		if(BlocksItems.enableElderTree)			ITEMS.add(ITEM_ELDER_SLAB);
		if(BlocksItems.enableJerky)  			ITEMS.add(COOKED_JERKY);
		if(BlocksItems.enableCanteen)  			ITEMS.add(CANTEEN);
		if(BlocksItems.enableStoneDoor)  		ITEMS.add(ITEM_STONE_DOOR);
		if(BlocksItems.enableElderDoor)			ITEMS.add(ITEM_ELDER_DOOR);

		//Blocks
		for(Block block : BlockHandler.BLOCKS)
			if(!(block instanceof BlockSlab) && !(block instanceof CustomDoor))
				ITEMS.add(new ItemBlock(block).setRegistryName(block.getRegistryName()));
	}

	@SubscribeEvent
	public void registerItems(final RegistryEvent.Register<Item> event)
	{
		for(Item item : ITEMS)
			event.getRegistry().register(item);
	}

	private void addItemBurnTime()
	{
		if(BlocksItems.enableWoodChippings)
			CustomFuelHandler.addFuelBurnTime(new ItemInfo(WOOD_CHIPPINGS), 100);
		if(BlocksItems.enableHellfayah) {
			CustomFuelHandler.addFuelBurnTime(new ItemInfo(HELLFAYAH), Config.burnTimeHellfayah);
			CustomFuelHandler.addFuelBurnTime(new ItemInfo(BlockHandler.BLOCK_OF_HELLFAYAH), Config.burnTimeHellfayahBlock);
		}
	}
}
