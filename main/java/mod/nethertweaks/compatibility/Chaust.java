package mod.nethertweaks.compatibility;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import mod.chaust.ChaustItems;
import mod.chaust.items.*;
import mod.nethertweaks.BucketLoader;
import mod.nethertweaks.INames;


public class Chaust {

	public static Item bucketWoodDemonWater;
	public static Item bucketStoneDemonWater;
	
	static void loadCompatibility(){
		
		bucketWoodDemonWater = new BucketWood(BucketLoader.blockDemonWater, INames.BUCKETWOODDMW).setMaxStackSize(1);
		bucketWoodDemonWater.setContainerItem(ChaustItems.bucketWood);
				
		bucketStoneDemonWater = new BucketStone(BucketLoader.blockDemonWater, INames.BUCKETSTONEDMW).setMaxStackSize(1);
		bucketStoneDemonWater.setContainerItem(ChaustItems.bucketStone);
	
		FluidContainerRegistry.registerFluidContainer(BucketLoader.fluidDemonWater, new ItemStack(bucketWoodDemonWater), new ItemStack(ChaustItems.bucketWood));
		FluidContainerRegistry.registerFluidContainer(BucketLoader.fluidDemonWater, new ItemStack(bucketStoneDemonWater), new ItemStack(ChaustItems.bucketStone));
	
		}
}
