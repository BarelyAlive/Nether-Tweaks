package mod.nethertweaks.init;

import java.util.ArrayList;
import java.util.List;

import mod.nethertweaks.Constants;
import mod.nethertweaks.config.BlocksItems;
import mod.nethertweaks.config.Config;
import mod.nethertweaks.item.Ash;
import mod.nethertweaks.item.CoiledSword;
import mod.nethertweaks.item.Grabber;
import mod.nethertweaks.item.Hammer;
import mod.nethertweaks.item.ItemDoll;
import mod.nethertweaks.item.ItemMesh;
import mod.nethertweaks.item.ItemPebble;
import mod.nethertweaks.item.KitchenKnife;
import mod.nethertweaks.item.Seed;
import mod.sfhcore.handler.ModFuelHandler;
import mod.sfhcore.items.ItemDoor;
import mod.sfhcore.util.ItemInfo;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemFood;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModItems
{
	//SEEDS
	public static final Seed MUSHROOM_SPORES  	 	 = new Seed();
	public static final Seed GRASS_SEEDS	  	 	 = new Seed();
	public static final Seed CACTUS_SEEDS	  	 	 = new Seed();
	public static final Seed SUGARCANE_SEEDS  	 	 = new Seed();

	//CRYSTALS
	public static final Item KITCHEN_KNIFE 	 	= new KitchenKnife();

	//MESHES
	public static final ItemMesh MESH_STRING	  	 = new ItemMesh();
	public static final ItemMesh MESH_FLINT		  	 = new ItemMesh();
	public static final ItemMesh MESH_IRON		  	 = new ItemMesh();
	public static final ItemMesh MESH_DIAMOND	  	 = new ItemMesh();

	//DOLLS
	public static final ItemDoll DOLL_BAT		  	 = new ItemDoll();
	public static final ItemDoll DOLL_CHICKEN	  	 = new ItemDoll();
	public static final ItemDoll DOLL_COW		  	 = new ItemDoll();
	public static final ItemDoll DOLL_DONKEY	  	 = new ItemDoll();
	public static final ItemDoll DOLL_HORSE		  	 = new ItemDoll();
	public static final ItemDoll DOLL_RED_MOOSHROOM	 = new ItemDoll();
	public static final ItemDoll DOLL_MULE		  	 = new ItemDoll();
	public static final ItemDoll DOLL_OCELOT	  	 = new ItemDoll();
	public static final ItemDoll DOLL_PARROT	  	 = new ItemDoll();
	public static final ItemDoll DOLL_RABBIT	  	 = new ItemDoll();
	public static final ItemDoll DOLL_SHEEP		  	 = new ItemDoll();
	public static final ItemDoll DOLL_LLAMA		  	 = new ItemDoll();
	public static final ItemDoll DOLL_POLAR_BEAR  	 = new ItemDoll();
	public static final ItemDoll DOLL_WOLF		  	 = new ItemDoll();
	public static final ItemDoll DOLL_VILLAGER	  	 = new ItemDoll();
	public static final ItemDoll DOLL_PIG		  	 = new ItemDoll();

	//PEBBLES
	public static final ItemPebble PEBBLE_STONE	  	 = new ItemPebble();
	public static final ItemPebble PEBBLE_GRANITE	 = new ItemPebble();
	public static final ItemPebble PEBBLE_ANDESITE   = new ItemPebble();
	public static final ItemPebble PEBBLE_DIORITE	 = new ItemPebble();

	//Crafting Components
	public static final Item STONE_BAR 		  	 	 = new Item();
	public static final Item PORTAL_CORE	  	 	 = new Item();
	public static final Item END_BOX 		  	 	 = new Item();
	public static final Item SALT	 		  	 	 = new Item();
	public static final Item HELLFAYAH 		  	 	 = new Item();
	public static final Item ENDER_INFUSED_FRAME 	 = new Item();
	public static final Item STRING 		  	 	 = new Item();
	public static final Item PORCELAIN_CLAY	  	 	 = new Item();
	public static final Item WOOD_CHIPPINGS	  	 	 = new Item();
	public static final Ash ASH			  	 	 	 = new Ash();
	public static final CoiledSword COILED_SWORD 	 = new CoiledSword();

	//Tools
	public static final Grabber GRABBER_WOOD 	  	 = new Grabber(Config.durabilityGWood, ToolMaterial.WOOD);
	public static final Grabber GRABBER_GOLD 	  	 = new Grabber(Config.durabilityGGold, ToolMaterial.GOLD);
	public static final Grabber GRABBER_STONE 	  	 = new Grabber(Config.durabilityGStone, ToolMaterial.STONE);
	public static final Grabber GRABBER_IRON 	  	 = new Grabber(Config.durabilityGIron, ToolMaterial.IRON);
	public static final Grabber GRABBER_DIAMOND  	 = new Grabber(Config.durabilityGDiamond, ToolMaterial.DIAMOND);

	public static final Hammer HAMMER_WOOD	  	 	 = new Hammer(Config.durabilityHWood, ToolMaterial.WOOD);
	public static final Hammer HAMMER_GOLD 	  	 	 = new Hammer(Config.durabilityHGold, ToolMaterial.GOLD);
	public static final Hammer HAMMER_STONE	  	 	 = new Hammer(Config.durabilityHStone, ToolMaterial.STONE);
	public static final Hammer HAMMER_IRON 	  	 	 = new Hammer(Config.durabilityHIron, ToolMaterial.IRON);
	public static final Hammer HAMMER_DIAMOND	  	 = new Hammer(Config.durabilityHDiamond, ToolMaterial.DIAMOND);

	public static final Item FLINT_N_BLAZE	 		 = new ItemFlintAndSteel().setMaxDamage(256);

	//Food & DRINKS
	public static final ItemFood COOKED_JERKY 	  	 = new ItemFood(6, 1.2F, true);

	//"Block-Placer"
	public static final ItemDoor ITEM_STONE_DOOR 	 = new ItemDoor();
	public static final ItemDoor ITEM_ELDER_DOOR 	 = new ItemDoor();

	//END_OF_INITIALIZATION

	public static final List<Item> ITEMS = new ArrayList();

	public static void preInit()
	{
		register();
	}

	public static void init()
	{
		addItemBurnTime();
	}

	private static void register()
	{
		//Items
		if(BlocksItems.enableStoneBar)	  		addItem(STONE_BAR, Constants.STONE_BAR);
		if(BlocksItems.enablePortalCore)  		addItem(PORTAL_CORE, Constants.PORTAL_CORE);
		if(BlocksItems.enableEndBox)		  	addItem(END_BOX, Constants.END_BOX);
		if(BlocksItems.enableSalt)		  		addItem(SALT, Constants.SALT);
		if(BlocksItems.enableHellfayah)  		addItem(HELLFAYAH, Constants.HELLFAYAH);
		if(BlocksItems.enableEnderInfusedFrame)	addItem(ENDER_INFUSED_FRAME, Constants.ENDER_INFUSED_FRAME);
		if(BlocksItems.enableMeanVine)			addItem(STRING, Constants.STRING);
		if(BlocksItems.enablePorcelainClay) 	addItem(PORCELAIN_CLAY, Constants.PORCELAIN_CLAY);
		if(BlocksItems.enableAsh) 				addItem(ASH, Constants.ASH);
		if(BlocksItems.enableWoodChippings)		addItem(WOOD_CHIPPINGS, Constants.WOOD_CHIPPINGS);
		if(BlocksItems.enableCoiledSword)		addItem(COILED_SWORD, Constants.COILED_SWORD);
		if(BlocksItems.enableMushroomSpores)	addItem(MUSHROOM_SPORES, Constants.MUSHROOM_SPORES);
		if(BlocksItems.enableGrassSeeds)		addItem(GRASS_SEEDS, Constants.GRASS_SEEDS);
		if(BlocksItems.enableCactusSeeds)		addItem(CACTUS_SEEDS, Constants.CACTUS_SEEDS);
		if(BlocksItems.enableSugarcaneSeeds)	addItem(SUGARCANE_SEEDS, Constants.SUGARCANE_SEEDS);
		if(BlocksItems.enableKitchenKnife)		addItem(KITCHEN_KNIFE, Constants.KITCHEN_KNIFE);
		if(BlocksItems.enablePebbleStone)		addItem(PEBBLE_STONE, Constants.PEBBLE_STONE);
		if(BlocksItems.enablePebbleGranite)		addItem(PEBBLE_GRANITE, Constants.PEBBLE_GRANITE);
		if(BlocksItems.enablePebbleDiorite)		addItem(PEBBLE_DIORITE, Constants.PEBBLE_DIORITE);
		if(BlocksItems.enablePebbleAndesite)	addItem(PEBBLE_ANDESITE, Constants.PEBBLE_ANDESITE);
		if(BlocksItems.enableStringMeshes)		addItem(MESH_STRING, Constants.MESH_STRING);
		if(BlocksItems.enableFlintMeshes) 		addItem(MESH_FLINT, Constants.MESH_FLINT);
		if(BlocksItems.enableIronMeshes)  		addItem(MESH_IRON, Constants.MESH_IRON);
		if(BlocksItems.enableDiamondMeshes)		addItem(MESH_DIAMOND, Constants.MESH_DIAMOND);
		if(BlocksItems.enableDollBat)  			addItem(DOLL_BAT, Constants.DOLL_BAT);
		if(BlocksItems.enableDollChicken)  		addItem(DOLL_CHICKEN, Constants.DOLL_CHICKEN);
		if(BlocksItems.enableDollCow)  			addItem(DOLL_COW, Constants.DOLL_COW);
		if(BlocksItems.enableDollDonkey)  		addItem(DOLL_DONKEY, Constants.DOLL_DONKEY);
		if(BlocksItems.enableDollHorse)  		addItem(DOLL_HORSE, Constants.DOLL_HORSE);
		if(BlocksItems.enableDollLlama)  		addItem(DOLL_LLAMA, Constants.DOLL_LLAMA);
		if(BlocksItems.enableDollMule)  		addItem(DOLL_MULE, Constants.DOLL_MULE);
		if(BlocksItems.enableDollOcelot)  		addItem(DOLL_OCELOT, Constants.DOLL_OCELOT);
		if(BlocksItems.enableDollParrot)  		addItem(DOLL_PARROT, Constants.DOLL_PARROT);
		if(BlocksItems.enableDollPig)  			addItem(DOLL_PIG, Constants.DOLL_PIG);
		if(BlocksItems.enableDollPolarBear)  	addItem(DOLL_POLAR_BEAR, Constants.DOLL_POLAR_BEAR);
		if(BlocksItems.enableDollRabbit)  		addItem(DOLL_RABBIT, Constants.DOLL_RABBIT);
		if(BlocksItems.enableDollRedMooshroom)	addItem(DOLL_RED_MOOSHROOM, Constants.DOLL_RED_MOOSHROOM);
		if(BlocksItems.enableDollSheep)  		addItem(DOLL_SHEEP, Constants.DOLL_SHEEP);
		if(BlocksItems.enableDollVillager)		addItem(DOLL_VILLAGER, Constants.DOLL_VILLAGER);
		if(BlocksItems.enableDollWolf)  		addItem(DOLL_WOLF, Constants.DOLL_WOLF);
		if(BlocksItems.enableGrabberWood)		addItem(GRABBER_WOOD, Constants.GRABBER_WOOD);
		if(BlocksItems.enableGrabberGold)		addItem(GRABBER_GOLD, Constants.GRABBER_GOLD);
		if(BlocksItems.enableGrabberStone)		addItem(GRABBER_STONE, Constants.GRABBER_STONE);
		if(BlocksItems.enableGrabberIron)		addItem(GRABBER_IRON, Constants.GRABBER_IRON);
		if(BlocksItems.enableGrabberDiamond)	addItem(GRABBER_DIAMOND, Constants.GRABBER_DIAMOND);
		if(BlocksItems.enableFlintNBlaze)  		addItem(FLINT_N_BLAZE, Constants.FLINT_N_BLAZE);
		if(BlocksItems.enableHammerWood)  		addItem(HAMMER_WOOD, Constants.HAMMER_WOOD);
		if(BlocksItems.enableHammerGold)  		addItem(HAMMER_GOLD, Constants.HAMMER_GOLD);
		if(BlocksItems.enableHammerStone)  		addItem(HAMMER_STONE, Constants.HAMMER_STONE);
		if(BlocksItems.enableHammerIron)  		addItem(HAMMER_IRON, Constants.HAMMER_IRON);
		if(BlocksItems.enableHammerDiamond)  	addItem(HAMMER_DIAMOND, Constants.HAMMER_DIAMOND);
		if(BlocksItems.enableJerky)  			addItem(COOKED_JERKY, Constants.COOKED_JERKY);
		if(BlocksItems.enableStoneDoor)  		addItem(ITEM_STONE_DOOR, Constants.STONE_DOOR);
		if(BlocksItems.enableElderTree)			addItem(ITEM_ELDER_DOOR, Constants.ELDER_DOOR);
	}

	private static void addItem(final Item item, final String name)
	{
		item.setRegistryName(Constants.MOD_ID, name);
		item.setUnlocalizedName(item.getRegistryName().getResourcePath());
		item.setCreativeTab(Constants.TABNTM);

		ITEMS.add(item);
	}

	@SubscribeEvent
	public void registerItems(final RegistryEvent.Register<Item> event)
	{
		ITEMS.forEach((item) ->
		{
			event.getRegistry().register(item);
		});
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void registerModels(final ModelRegistryEvent event)
	{
		for(final Item item : ITEMS)
			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));

		ModFluids.initModels();
	}

	private static void addItemBurnTime()
	{
		if(BlocksItems.enableWoodChippings)
			ModFuelHandler.addFuelBurnTime(new ItemInfo(WOOD_CHIPPINGS), 100);
		if(BlocksItems.enableHellfayah) {
			ModFuelHandler.addFuelBurnTime(new ItemInfo(HELLFAYAH), Config.burnTimeHellfayah);
			ModFuelHandler.addFuelBurnTime(new ItemInfo(ModBlocks.BLOCK_OF_HELLFAYAH), Config.burnTimeHellfayahBlock);
		}
	}
}
