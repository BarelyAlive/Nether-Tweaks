package mod.nethertweaks;

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
import net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import mod.nethertweaks.blocks.BlockDemonWater;
import mod.nethertweaks.blocks.NTMBlocks;
import mod.nethertweaks.handler.BucketHandler;
import mod.nethertweaks.items.NTMItems;
import mod.sfhcore.Registry;
import mod.sfhcore.items.BucketCustomMilk;
import mod.sfhcore.items.CustomBucket;


public class BucketLoader {

		//Fluids
		public static Fluid fluidDemonWater;
		public static Block blockDemonWater;
		
		//Material
		public static Material demonWater;
		
		//Buckets
		public static Item bucketDemonWater;
		public static Item itemBucketNTM;
		public static Item itemBucketNTMWater;
		public static Item itemBucketNTMDemonWater;
		public static Item itemBucketNTMLava;
		public static Item bucketWood;
		public static Item bucketWoodWater;
		public static Item bucketWoodDemonWater;
		public static Item bucketWoodMilk;
		public static Item bucketStone;
		public static Item bucketStoneWater;
		public static Item bucketStoneLava;
		public static Item bucketStoneDemonWater;
		public static Item bucketStoneMilk;
		
		private ResourceLocation still = new ResourceLocation(Constants.TEX + "DemonWater_still");
		private ResourceLocation flow  = new ResourceLocation(Constants.TEX + "DemonWater_flow");

	
	public void registerBuckets(){
		
		//Fluids
		fluidDemonWater = new Fluid(INames.DEMONWATERFLUID, still, flow).setUnlocalizedName(INames.DEMONWATERFLUID);
		FluidRegistry.registerFluid(fluidDemonWater);
		blockDemonWater = Registry.registerBlock(new BlockDemonWater(fluidDemonWater, Material.WATER), INames.DEMOMWATERBLOCK, Constants.MOD);
		
		//Buckets
		
		//Demon Water if necessary
		
		if(Config.iwantvanillaWater == false){
		FluidRegistry.enableUniversalBucket();
		FluidRegistry.addBucketForFluid(fluidDemonWater);
		}
		
		if(Config.iwantvanillaWater == false){
			BucketHandler.INSTANCE.buckets.put(blockDemonWater, bucketDemonWater);
		}
		if(Config.iwantvanillaWater == true){
			BucketHandler.INSTANCE.buckets.put(blockDemonWater, itemBucketNTMDemonWater);
			BucketHandler.INSTANCE.buckets.put(Blocks.FLOWING_WATER, itemBucketNTMWater);
			BucketHandler.INSTANCE.buckets.put(Blocks.FLOWING_LAVA, itemBucketNTMLava);
			BucketHandler.INSTANCE.buckets.put(Blocks.AIR, BucketLoader.itemBucketNTM);
		}
		
		//Regular Buckets
		
		bucketStoneWater = Registry.registerItem(new CustomBucket(Blocks.FLOWING_WATER, INames.BUCKETSTONEWATER, new ItemStack(bucketStone)), INames.BUCKETSTONEWATER, Constants.MOD);
		bucketStoneWater.setUnlocalizedName(INames.BUCKETSTONEWATER).setContainerItem(BucketLoader.bucketStone);
		
		bucketStoneWater.setRegistryName(INames.BUCKETSTONEWATER);
		bucketStoneWater.registerItems();
		
		bucketStoneLava = Registry.registerItem(new CustomBucket(Blocks.FLOWING_LAVA, INames.BUCKETSTONELAVA, new ItemStack(bucketStone)), INames.BUCKETSTONELAVA, Constants.MOD);
		bucketStoneLava.setUnlocalizedName(INames.BUCKETSTONELAVA).setContainerItem(BucketLoader.bucketStone);
		
		bucketStoneLava.setRegistryName(INames.BUCKETSTONELAVA);
		bucketStoneLava.registerItems();
		
		bucketStoneDemonWater = Registry.registerItem(new CustomBucket(blockDemonWater, INames.BUCKETSTONEDMW, new ItemStack(bucketStone)), INames.BUCKETSTONEDMW, Constants.MOD);
		bucketStoneDemonWater.setUnlocalizedName(INames.BUCKETSTONEDMW).setContainerItem(BucketLoader.bucketStone);
		
		bucketStoneDemonWater.setRegistryName(INames.BUCKETSTONEDMW);
		bucketStoneDemonWater.registerItems();
		
		bucketStoneMilk = Registry.registerItem(new BucketCustomMilk(NetherTweaksMod.tabNetherTweaksMod, new ItemStack(bucketStone)), INames.BUCKETSTONEDMW, Constants.MOD);
		bucketStoneMilk.setUnlocalizedName(INames.BUCKETSTONEMILK).setContainerItem(BucketLoader.bucketStone);
		
		bucketStoneMilk.setRegistryName(INames.BUCKETSTONEMILK);
		bucketStoneMilk.registerItems();
		
		//Same with Wood
		
		bucketWoodWater = Registry.registerItem(new CustomBucket(Blocks.FLOWING_WATER, INames.BUCKETWOODWATER, new ItemStack(bucketWood)), INames.BUCKETWOODWATER, Constants.MOD);
		bucketWoodWater.setUnlocalizedName(INames.BUCKETWOODWATER).setContainerItem(BucketLoader.bucketWood);
		
		bucketWoodWater.setRegistryName(INames.BUCKETWOODWATER);
		bucketWoodWater.registerItems();
		
		bucketWoodDemonWater = Registry.registerItem(new CustomBucket(blockDemonWater, INames.BUCKETWOODDMW, new ItemStack(bucketWood)), INames.BUCKETWOODDMW, Constants.MOD);
		bucketWoodDemonWater.setUnlocalizedName(INames.BUCKETWOODDMW).setContainerItem(BucketLoader.bucketWood);
		
		bucketWoodDemonWater.setRegistryName(INames.BUCKETWOODDMW);
		bucketWoodDemonWater.registerItems();
		
		bucketWoodMilk = Registry.registerItem(new BucketCustomMilk(NetherTweaksMod.tabNetherTweaksMod, new ItemStack(bucketWood)), INames.BUCKETWOODDMW, Constants.MOD);
		bucketWoodMilk.setUnlocalizedName(INames.BUCKETWOODMILK).setContainerItem(BucketLoader.bucketWood);
		
		bucketWoodMilk.setRegistryName(INames.BUCKETWOODMILK);
		bucketWoodMilk.registerItems();
		
		MinecraftForge.EVENT_BUS.register(BucketHandler.INSTANCE);
		
	}
}
