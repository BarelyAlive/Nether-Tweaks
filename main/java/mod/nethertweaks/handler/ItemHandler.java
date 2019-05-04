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
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import mod.nethertweaks.Config;
import mod.nethertweaks.Konstanten;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.blocks.Bonfire;
import mod.nethertweaks.interfaces.INames;
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
import mod.sfhcore.handler.CustomFuelHandler;
import mod.sfhcore.items.CustomItem;
import mod.sfhcore.registries.Registry;

public class ItemHandler implements INames{
	 
	//ITEMS
    public static final Item SEED = new Seed();
    public static final Item FLINTANDBLAZE = new FlintAndBlaze();
    public static final Item CRYSTAL = new Crystal();
    
    //NEW
    public static final Item PEBBLE = new ItemPebble();
    public static final Item MESH = new ItemMesh();
    public static final Item DOLL = new ItemDoll();
    
    //Multi ID Item
	public static final Item ITEMBASE = new CustomItem(null, 64, NetherTweaksMod.tabNTM, true, 8, new ResourceLocation(NTM, INames.ITEMBASE));
    
	//Food
    public static final Item COOKEDJERKY = new ItemFood(6, 1.2F, true).setRegistryName(NetherTweaksMod.MODID, INames.COOKEDJERKY).setCreativeTab(NetherTweaksMod.tabNTM);
    
    //Werkzeuge
    public static final Item PICKAXENETHERRACK = new PickaxeNTM(ToolMaterial.STONE);
    public static final Item PICKAXENETHERBRICK = new PickaxeNTM(ToolMaterial.IRON);
    public static final Item GRABBER = new Grabber();
    public static final Item HAMMERWOOD = new HammerBase(INames.HAMMERWOOD, 64, ToolMaterial.WOOD);
    public static final Item HAMMERGOLD = new HammerBase(INames.HAMMERGOLD, 64, ToolMaterial.GOLD);
    public static final Item HAMMERSTONE = new HammerBase(INames.HAMMERSTONE, 128, ToolMaterial.STONE);
    public static final Item HAMMERIRON = new HammerBase(INames.HAMMERIRON, 512, ToolMaterial.IRON);
    public static final Item HAMMERDIAMOND = new HammerBase(INames.HAMMERDIAMOND, 4096, ToolMaterial.DIAMOND);
    
    //itemblocks
    public static final Item ITEMSTONEDOOR = new ItemDoor(NetherTweaksMod.tabNTM, new ResourceLocation(NetherTweaksMod.MODID, INames.DOORNTMSTONE));  
    
	public static void init()
	{
		registerItems();
		addItemBurnTime();
	}
	
    private static void registerItems()
    {
    	//Multi ID Item
    	if(Config.enableMultiItem)  		Registry.registerItem(ITEMBASE);
    	if(Config.enableSeed) 				Registry.registerItem(SEED);
    	if(Config.enableCrystalLight || Config.enableCrystalEnder)
    										Registry.registerItem(CRYSTAL);
        if(Config.enablePebbles)  			Registry.registerItem(PEBBLE);
        if(Config.enableMeshes)  			Registry.registerItem(MESH);
        if(Config.enableDolls)  			Registry.registerItem(DOLL);
        
        //Werkzeuge
        if(Config.enablePickAxeNetherrack)  Registry.registerItem(PICKAXENETHERRACK);
        if(Config.enablePickAxeNetherbrick) Registry.registerItem(PICKAXENETHERBRICK);
        if(Config.enableGrabber)			Registry.registerItem(GRABBER);
        if(Config.enableFlintNBlaze)  		Registry.registerItem(FLINTANDBLAZE);        
        if(Config.enableHammerWood)  		Registry.registerItem(HAMMERWOOD);
        if(Config.enableHammerGold)  		Registry.registerItem(HAMMERGOLD);
        if(Config.enableHammerIron)  		Registry.registerItem(HAMMERIRON);
        if(Config.enableHammerDiamond)  	Registry.registerItem(HAMMERDIAMOND);
        if(Config.enableHammerStone)  		Registry.registerItem(HAMMERSTONE);
        
        //Food
    	if(Config.enableJerky)  			Registry.registerItem(COOKEDJERKY);
    	
        //Doors
        if(Config.enableStoneDoor)  		Registry.registerItem(ITEMSTONEDOOR);
    }
    
    private static void addItemBurnTime()
    {
    	CustomFuelHandler.addFuelBurnTime(new ItemStack(BucketNFluidHandler.BUCKETSTONELAVA), 18000);
    	CustomFuelHandler.addFuelBurnTime(new ItemStack(ItemHandler.ITEMBASE, 1, 3), Config.burnTimeHellfayah);
    	CustomFuelHandler.addFuelBurnTime(new ItemStack(BlockHandler.ITEMBLOCKBASIC, 1, 1), Config.burnTimeHellfayahBlock);
    }
}