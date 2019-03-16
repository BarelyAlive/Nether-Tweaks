package mod.nethertweaks.handler;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidRegistry.FluidRegisterEvent;
import net.minecraftforge.fluids.UniversalBucket;
import net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import mod.nethertweaks.Config;
import mod.nethertweaks.INames;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.blocks.BlockDemonWater;
import mod.sfhcore.Constants;
import mod.sfhcore.Registry;
import mod.sfhcore.helper.InventoryRenderHelper;
import mod.sfhcore.items.CustomBucket;
import mod.sfhcore.items.CustomBucketMilk;


public class BucketNFluidHandler {

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
		public static Item bucketStoneLava;
		public static Item bucketStoneDemonWater;
		public static Item bucketStoneMilk;
		
		private static ResourceLocation still = new ResourceLocation(Constants.TEX + "DemonWater_still");
		private static ResourceLocation flow  = new ResourceLocation(Constants.TEX + "DemonWater_flow");

	
	public static void registerBuckets(){
		
		//Fluids
		fluidDemonWater = new Fluid(INames.DEMONWATERFLUID, still, flow).setUnlocalizedName(INames.DEMONWATERFLUID);
		FluidRegistry.registerFluid(fluidDemonWater);
		blockDemonWater = Registry.registerBlock(new BlockDemonWater(fluidDemonWater, Material.WATER), Constants.MOD);
		
		//Buckets
		
		//Demon Water if necessary
		
		if(Config.iwantvanillaWater == false){
		FluidRegistry.addBucketForFluid(fluidDemonWater);
		bucketDemonWater = UniversalBucket.getItemFromBlock(blockDemonWater);
		}
		
		//Regular Buckets
		
		bucketStoneWater = Registry.registerItem(new CustomBucket(Blocks.FLOWING_WATER, INames.BUCKETSTONEWATER, new ItemStack(bucketStone)), Constants.MOD);
		
		bucketStoneLava = Registry.registerItem(new CustomBucket(Blocks.FLOWING_LAVA, INames.BUCKETSTONELAVA, new ItemStack(bucketStone)), Constants.MOD);
				
		bucketStoneDemonWater = Registry.registerItem(new CustomBucket(blockDemonWater, INames.BUCKETSTONEDEMONWATER, new ItemStack(bucketStone)), Constants.MOD);
				
		bucketStoneMilk = Registry.registerItem(new CustomBucketMilk(NetherTweaksMod.tabNetherTweaksMod, new ItemStack(bucketStone)), Constants.MOD);
				
		//Same with Wood
		
		bucketWoodWater = Registry.registerItem(new CustomBucket(Blocks.FLOWING_WATER, INames.BUCKETWOODWATER, new ItemStack(bucketWood)), Constants.MOD);
		
		bucketWoodDemonWater = Registry.registerItem(new CustomBucket(blockDemonWater, INames.BUCKETSTONEDEMONWATER, new ItemStack(bucketWood)), Constants.MOD);
		
		bucketWoodMilk = Registry.registerItem(new CustomBucketMilk(NetherTweaksMod.tabNetherTweaksMod, new ItemStack(bucketWood)), Constants.MOD);		
	}
	
	public static void doHelper(){
		InventoryRenderHelper.fluidRender(BucketNFluidHandler.blockDemonWater);
    }
}
