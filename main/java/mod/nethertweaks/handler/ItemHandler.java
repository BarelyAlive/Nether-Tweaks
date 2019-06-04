package mod.nethertweaks.handler;

import net.minecraft.block.material.Material;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.util.ResourceLocation;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import mod.nethertweaks.INames;
import mod.nethertweaks.Konstanten;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.blocks.Bonfire;
import mod.nethertweaks.config.Config;
import mod.nethertweaks.items.Crystal;
import mod.nethertweaks.items.FlintAndBlaze;
import mod.nethertweaks.items.Grabber;
import mod.nethertweaks.items.HammerBase;
import mod.nethertweaks.items.ItemDoll;
import mod.nethertweaks.items.ItemMesh;
import mod.nethertweaks.items.ItemPebble;
import mod.nethertweaks.items.PickaxeNTM;
import mod.nethertweaks.items.Seed;
import mod.sfhcore.Constants;
import mod.sfhcore.SFHCore;
import mod.sfhcore.blocks.CustomDoor;
import mod.sfhcore.blocks.itemblocks.ItemDoor;
import mod.sfhcore.handler.BucketHandler;
import mod.sfhcore.handler.CustomFuelHandler;
import mod.sfhcore.items.CustomItem;
import mod.sfhcore.registries.Registry;

public class ItemHandler implements INames
{
	//ITEMS
    public static final Item SEED 			  = new Seed();
    public static final Item MESH 			  = new ItemMesh();
    public static final Item DOLL 			  = new ItemDoll();
    public static final Item PEBBLE 		  = new ItemPebble();
    public static final Item CRYSTAL 		  = new Crystal();
    public static final Item FLINT_N_BLAZE 	  = new FlintAndBlaze();

    //Multi ID Item
	public static final Item ITEM_BASE 		  = new CustomItem(null, 64, NetherTweaksMod.TABNTM, true, 9, new ResourceLocation(NTM, INames.ITEM_BASE));

	//Food
    public static final Item COOKED_JERKY 	  = new ItemFood(6, 1.2F, true).setRegistryName(NetherTweaksMod.MODID, INames.COOKED_JERKY).setCreativeTab(NetherTweaksMod.TABNTM);

    //Werkzeuge
    public static final Item GRABBER_WOOD 	  = new Grabber(ToolMaterial.WOOD, 96);
    public static final Item GRABBER_GOLD 	  = new Grabber(ToolMaterial.GOLD, 120);
    public static final Item GRABBER_STONE 	  = new Grabber(ToolMaterial.STONE, 240);
    public static final Item GRABBER_IRON 	  = new Grabber(ToolMaterial.IRON, 960);
    public static final Item GRABBER_DIAMOND  = new Grabber(ToolMaterial.DIAMOND, 7680);
    public static final Item PICK_NETHERRACK  = new PickaxeNTM(ToolMaterial.STONE);
    public static final Item PICK_NETHERBRICK = new PickaxeNTM(ToolMaterial.IRON);
    public static final Item HAMMER_WOOD	  = new HammerBase(Config.durabilityHWood, ToolMaterial.WOOD);
    public static final Item HAMMER_GOLD 	  = new HammerBase(Config.durabilityHGold, ToolMaterial.GOLD);
    public static final Item HAMMER_STONE	  = new HammerBase(Config.durabilityHStone, ToolMaterial.STONE);
    public static final Item HAMMER_IRON 	  = new HammerBase(Config.durabilityHIron, ToolMaterial.IRON);
    public static final Item HAMMER_DIAMOND	  = new HammerBase(Config.durabilityHDiamond, ToolMaterial.DIAMOND);

    //itemblocks
    public static final Item ITEM_STONE_DOOR  = new ItemDoor(NetherTweaksMod.TABNTM, new ResourceLocation(NetherTweaksMod.MODID, INames.STONE_DOOR));
    public static final Item ITEM_ELDER_DOOR  = new ItemDoor(NetherTweaksMod.TABNTM, new ResourceLocation(NetherTweaksMod.MODID, INames.ELDER_DOOR));

	public static void init()
	{
		registerItems();
		addItemBurnTime();
	}

    private static void registerItems()
    {
    	//Multi ID Item
    	if(Config.enableMultiItem)  		Registry.registerItem(ITEM_BASE);
    	if(Config.enableSeed) 				Registry.registerItem(SEED);
    	if(Config.enableCrystalLight || Config.enableCrystalEnder)
    										Registry.registerItem(CRYSTAL);
        if(Config.enablePebbles)  			Registry.registerItem(PEBBLE);
        if(Config.enableMeshes)  			Registry.registerItem(MESH);
        if(Config.enableDolls)  			Registry.registerItem(DOLL);

        //Werkzeuge
        if(Config.enableGrabber)			Registry.registerItem(GRABBER_WOOD);
        if(Config.enableFlintNBlaze)  		Registry.registerItem(FLINT_N_BLAZE);
        if(Config.enablePickAxeNetherrack)  Registry.registerItem(PICK_NETHERRACK);
        if(Config.enablePickAxeNetherbrick) Registry.registerItem(PICK_NETHERBRICK);
        if(Config.enableHammerWood)  		Registry.registerItem(HAMMER_WOOD);
        if(Config.enableHammerGold)  		Registry.registerItem(HAMMER_GOLD);
        if(Config.enableHammerStone)  		Registry.registerItem(HAMMER_STONE);
        if(Config.enableHammerIron)  		Registry.registerItem(HAMMER_IRON);
        if(Config.enableHammerDiamond)  	Registry.registerItem(HAMMER_DIAMOND);

        //Food
    	if(Config.enableJerky)  			Registry.registerItem(COOKED_JERKY);

        //Doors
        if(Config.enableStoneDoor)  		Registry.registerItem(ITEM_STONE_DOOR);
        if(Config.enableElderDoor)			Registry.registerItem(ITEM_ELDER_DOOR);
    }

    private static void addItemBurnTime()
    {
    	if(Config.enableStoneBucket)
    		CustomFuelHandler.addFuelBurnTime(new ItemStack(BucketHandler.getBucketFromFluid(FluidRegistry.LAVA, "stone")), 20000);
    	if(Config.enableMultiItem)
    		CustomFuelHandler.addFuelBurnTime(new ItemStack(ItemHandler.ITEM_BASE, 1, 3), Config.burnTimeHellfayah);
    	if(Config.enableMultiBlock)
    		CustomFuelHandler.addFuelBurnTime(new ItemStack(BlockHandler.ITEM_BLOCK_BASIC, 1, 1), Config.burnTimeHellfayahBlock);
    }
}
