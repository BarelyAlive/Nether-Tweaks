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
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import mod.nethertweaks.blocks.BlockDemonWater;
import mod.nethertweaks.items.BucketDemonWater;
import mod.sfhcore.BucketHandler;
import mod.sfhcore.Constants;
import mod.sfhcore.Registry;


public class BucketLoader {

		//Fluids
		public static Fluid fluidDemonWater;
		public static Block blockDemonWater;
		
		//Material
		public static Material demonWater;
		
		//Buckets
		public static Item bucketDemonWater;
		
	
	public static void registerBuckets(){
		
		//Fluids
		fluidDemonWater = new Fluid("FluidDemonWater", null, null).setUnlocalizedName(INames.DEMONWATERFLUID);
		FluidRegistry.registerFluid(fluidDemonWater);
		blockDemonWater = Registry.registerBlock(new BlockDemonWater(fluidDemonWater, Material.WATER), INames.DEMOMWATERBLOCK, Constants.ModIdNTM);
		fluidDemonWater.setUnlocalizedName(blockDemonWater.getUnlocalizedName());
		
		//Buckets
		if(Config.iwantvanillaWater == false){
		bucketDemonWater = new BucketDemonWater(blockDemonWater);
		bucketDemonWater.setUnlocalizedName(INames.BUCKETDEMONWATER).setContainerItem(Items.BUCKET);
		GameRegistry.registerItem(bucketDemonWater, "BucketDemonWater");
		}
		
		BucketHandler.INSTANCE.buckets.put(blockDemonWater, bucketDemonWater);
		
		MinecraftForge.EVENT_BUS.register(BucketHandler.INSTANCE);
		
	}
}
