package mod.nethertweaks.items;
 
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import mod.nethertweaks.Config;
import mod.nethertweaks.INames;
import mod.nethertweaks.InventoryRenderHelper;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.blocks.BlockDemonWater;
import mod.nethertweaks.blocks.BlockDoorNTM;
import mod.nethertweaks.blocks.Bonfire;
import mod.nethertweaks.blocks.FluidDemonWater;
import mod.sfhcore.BucketHandler;
import mod.sfhcore.Constants;
import mod.sfhcore.Registry;
import mod.sfhcore.items.ItemThing;

public class NTMItems implements INames{
 
	//ITEMS
    public static Item seedGrass;
    public static Item mushroomSpores;
    public static Item itemCactusSeeds;
    public static Item itemFlintAndBlaze;
    public static Item itemSanctuaryCrystal;
    public static Item itemLightCrystal;
    
    //Multi ID Item
	public static Item itemBase;
	public static Item oreMaterial;
    
	//food
    public static CookedJerky itemCookedJerky;
         
    //Werkzeuge
    public static PickaxeNTM itemPickaxeNetherrack;
    public static PickaxeNTM itemPickaxeNetherbrick;
    public static Item itemHammerWood;
    public static Item itemHammerGold;
    public static Item itemHammerIron;
    public static Item itemHammerDiamond;
    public static Item itemHammerStone;
    
    //bucketloader
    //Fluids
	public static Fluid fluidDemonWater;
	public static Block blockDemonWater;
	
	//Material
	public static Material demonWater;
	
	//Buckets
	public static Item bucketDemonWater;
	public static Item bucketWood;
	public static Item bucketWoodWater;
	public static Item bucketWoodDemonWater;
	public static Item bucketWoodMilk;
	public static Item bucketStone;
	public static Item bucketStoneWater;
	public static Item bucketStoneDemonWater;
	public static Item bucketStoneLava;
	public static Item bucketStoneMilk;
    
                 
    public static void registerItems(){
    	
    	//Multi ID Item
		oreMaterial = Registry.registerItem(new ItemOreMaterial("OreMaterial"), "OreMaterial", Constants.ModIdNTM);
    	itemBase = Registry.registerItem(new ItemBase(), ITEMBASE, Constants.ModIdNTM);
        
    	itemCookedJerky = (CookedJerky) Registry.registerItem(new CookedJerky(6, 1.0F, true), COOKEDJERKY, Constants.ModIdNTM);
        seedGrass = Registry.registerItem(new Seed(), SEEDGRASS, Constants.ModIdNTM);
        mushroomSpores = Registry.registerItem(new Seed(), MUSHROOMSPORES, Constants.ModIdNTM);
        itemCactusSeeds = Registry.registerItem(new Seed(), CACTUSSEED, Constants.ModIdNTM);
        itemSanctuaryCrystal = Registry.registerItem(new Crystal(), SANCTUARYCRYSTAL, Constants.ModIdNTM);
        itemLightCrystal = Registry.registerItem(new Crystal(), LIGHTCRYSTAL, Constants.ModIdNTM);
        
        //Werkzeuge
        itemPickaxeNetherrack = (PickaxeNTM) Registry.registerItem(new PickaxeNTM(ToolMaterial.STONE), PICKAXENETHERRACK, Constants.ModIdNTM);
        itemPickaxeNetherbrick = (PickaxeNTM) Registry.registerItem(new PickaxeNTM(ToolMaterial.IRON), PICKAXENETHERBRICK, Constants.ModIdNTM);
         
        itemHammerWood = Registry.registerItem(new Hammer(2.0F, ToolMaterial.WOOD), HAMMERWOOD, Constants.ModIdNTM);
        itemHammerGold = Registry.registerItem(new Hammer(2.0F, ToolMaterial.GOLD), HAMMERGOLD, Constants.ModIdNTM);
        itemHammerIron = Registry.registerItem(new Hammer(4.0F, ToolMaterial.IRON), HAMMERIRON, Constants.ModIdNTM);
        itemHammerDiamond = Registry.registerItem(new Hammer(5.0F, ToolMaterial.DIAMOND), HAMMERDIAMOND, Constants.ModIdNTM);
        itemHammerStone = Registry.registerItem(new Hammer(3.0F, ToolMaterial.STONE), HAMMERSTONE, Constants.ModIdNTM);
        
        itemFlintAndBlaze = Registry.registerItem(new FlintAndBlaze(), FLINTNBLAZE, Constants.ModIdNTM);
        
    }
    
    public static void registerBuckets(){
		
		//Fluids
		fluidDemonWater = new FluidDemonWater();
		blockDemonWater = Registry.registerBlock(new BlockDemonWater(fluidDemonWater, Material.WATER), INames.DEMOMWATERBLOCK, Constants.ModIdNTM);
		fluidDemonWater.setUnlocalizedName(INames.DEMONWATERFLUID);
		InventoryRenderHelper.fluidRender(blockDemonWater);
		
		//Buckets
		FluidRegistry.addBucketForFluid(fluidDemonWater);
		
		MinecraftForge.EVENT_BUS.register(BucketHandler.INSTANCE);
				
		//Buckets
		
		bucketWood = Registry.registerItem(new BucketWood(Blocks.AIR, BUCKETWOOD).setMaxStackSize(16), BUCKETWOOD, Constants.ModIdNTM);		
		bucketWoodWater = Registry.registerItem(new BucketWood(Blocks.FLOWING_WATER, BUCKETWOODWATER), BUCKETWOODWATER, Constants.ModIdNTM);		
		bucketWoodDemonWater = Registry.registerItem(new BucketWood(blockDemonWater, BUCKETWOODDEMONWATER), BUCKETWOODDEMONWATER, Constants.ModIdNTM);		
		bucketWoodMilk = Registry.registerItem(new ItemThing(bucketWood, 1, null), BUCKETWOODMILK, Constants.ModIdNTM);
		
		bucketStone = Registry.registerItem(new BucketStone(Blocks.AIR, BUCKETSTONE).setMaxStackSize(16).setUnlocalizedName(BUCKETSTONE), BUCKETSTONE, Constants.ModIdNTM);	
		bucketStoneWater = Registry.registerItem(new BucketStone(Blocks.FLOWING_WATER, BUCKETSTONEWATER), BUCKETSTONEWATER, Constants.ModIdNTM);
		bucketStoneDemonWater = Registry.registerItem(new BucketStone(blockDemonWater, BUCKETSTONEDEMONWATER), BUCKETSTONEDEMONWATER, Constants.ModIdNTM);
		bucketStoneLava = Registry.registerItem(new BucketStone(Blocks.FLOWING_LAVA, BUCKETSTONELAVA), BUCKETSTONELAVA, Constants.ModIdNTM);		
		bucketStoneMilk = Registry.registerItem(new ItemThing(bucketStone, 1, null), BUCKETSTONEMILK, Constants.ModIdCHAUST);
		
		BucketHandler.INSTANCE.buckets.put(Block.REGISTRY.getObject(new ResourceLocation(Constants.ModIdNTM, DEMOMWATERBLOCK)), bucketWood);
		BucketHandler.INSTANCE.buckets.put(Block.REGISTRY.getObject(new ResourceLocation(Constants.ModIdNTM, DEMOMWATERBLOCK)), bucketStone);
		
	}
     
}