package mod.nethertweaks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import mod.nethertweaks.blocks.BlockDemonWater;
import mod.nethertweaks.handler.BucketHandler;
import mod.nethertweaks.items.BucketDemonWater;
import mod.sfhcore.Registry;


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
		
	
	public static void registerBuckets(){
		
		//Fluids
		fluidDemonWater = new Fluid("FluidDemonWater", null, null).setUnlocalizedName(INames.DEMONWATERFLUID);
		FluidRegistry.registerFluid(fluidDemonWater);
		blockDemonWater = Registry.registerBlock(new BlockDemonWater(fluidDemonWater, Material.WATER), INames.DEMOMWATERBLOCK, Constants.MOD);
		fluidDemonWater.setUnlocalizedName(blockDemonWater.getUnlocalizedName());
		
		//Buckets
		if(Config.iwantvanillaWater == false){
		bucketDemonWater = new BucketDemonWater(blockDemonWater);
		bucketDemonWater.setUnlocalizedName(INames.BUCKETDEMONWATER).setContainerItem(Items.BUCKET);
		
		bucketDemonWater.setRegistryName("BucketDemonWater");
		bucketDemonWater.registerItems();
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
		
		MinecraftForge.EVENT_BUS.register(BucketHandler.INSTANCE);
		
	}
}
