package mod.nethertweaks.handler;
 
import net.minecraft.block.material.Material;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.util.ResourceLocation;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemFood;
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
    public static final Item COOKEDJERKY = new ItemFood(6, 1.2F, true)
    		.setUnlocalizedName(INames.COOKEDJERKY).setRegistryName(NetherTweaksMod.MODID, INames.COOKEDJERKY).setCreativeTab(NetherTweaksMod.tabNTM);
    
    //Werkzeuge
    public static final Item PICKAXENETHERRACK = new PickaxeNTM(ToolMaterial.STONE);
    public static final Item PICKAXENETHERBRICK = new PickaxeNTM(ToolMaterial.IRON);
    public static final Item HAMMERWOOD = new HammerBase(new ResourceLocation(NTM, INames.HAMMERWOOD), 64, ToolMaterial.WOOD);
    public static final Item HAMMERGOLD = new HammerBase(new ResourceLocation(NTM, INames.HAMMERGOLD), 64, ToolMaterial.GOLD);
    public static final Item HAMMERSTONE = new HammerBase(new ResourceLocation(NTM, INames.HAMMERSTONE), 128, ToolMaterial.STONE);
    public static final Item HAMMERIRON = new HammerBase(new ResourceLocation(NTM, INames.HAMMERIRON), 512, ToolMaterial.IRON);
    public static final Item HAMMERDIAMOND = new HammerBase(new ResourceLocation(NTM, INames.HAMMERDIAMOND), 4096, ToolMaterial.DIAMOND);
    
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
    	Registry.registerItem(ITEMBASE);
        Registry.registerItem(SEED);
        Registry.registerItem(CRYSTAL);
        Registry.registerItem(PEBBLE);
        Registry.registerItem(MESH);
        Registry.registerItem(DOLL);

    	Registry.registerItem(COOKEDJERKY);
        
        //Werkzeuge
        Registry.registerItem(PICKAXENETHERRACK);
        Registry.registerItem(PICKAXENETHERBRICK);
        
        Registry.registerItem(HAMMERWOOD);
        Registry.registerItem(HAMMERGOLD);
        Registry.registerItem(HAMMERIRON);
        Registry.registerItem(HAMMERDIAMOND);
        Registry.registerItem(HAMMERSTONE);
        
        Registry.registerItem(FLINTANDBLAZE);        
        Registry.registerItem(ITEMSTONEDOOR);
    }
    
    private static void addItemBurnTime()
    {
    	CustomFuelHandler.addFuelBurnTime(new ItemStack(BucketNFluidHandler.BUCKETSTONELAVA), 18000);
    	CustomFuelHandler.addFuelBurnTime(new ItemStack(ItemHandler.ITEMBASE, 1, 3), Config.burnTimeHellfayah);
    	CustomFuelHandler.addFuelBurnTime(new ItemStack(BlockHandler.ITEMBLOCKBASIC, 1, 1), Config.burnTimeHellfayahBlock);
    }
}