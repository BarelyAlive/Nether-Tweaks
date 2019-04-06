package mod.nethertweaks.handler;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDynamicLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidRegistry.FluidRegisterEvent;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.UniversalBucket;
import net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import mod.nethertweaks.Config;
import mod.nethertweaks.INames;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.blocks.BlockDemonWater;
import mod.nethertweaks.blocks.FluidDemonWater;
import mod.sfhcore.Constants;
import mod.sfhcore.Registry;
import mod.sfhcore.helper.FluidHelper;
import mod.sfhcore.helper.InventoryRenderHelper;
import mod.sfhcore.items.CustomBucket;
import mod.sfhcore.items.CustomBucketMilk;


public class BucketNFluidHandler {

		//Fluids
		public static Fluid fluidDemonWater;
		public static Block blockDemonWater;
				
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

	public static void init() {
		registerBuckets();
		doHelper();
	}
		
	private static void registerBuckets(){
		
		//Fluids
		fluidDemonWater = new FluidDemonWater();
		FluidRegistry.registerFluid(fluidDemonWater);
		blockDemonWater = Registry.registerBlock(new BlockDemonWater(fluidDemonWater, Material.WATER));
		FluidRegistry.addBucketForFluid(fluidDemonWater);
		
		//Buckets
		bucketDemonWater = new CustomBucket(blockDemonWater, INames.BUCKETDEMONWATER, new ItemStack(Items.BUCKET));
		bucketDemonWater = Registry.registerItem(bucketDemonWater);
		
		//Regular Buckets
		bucketStone = Registry.registerItem(new CustomBucket(Blocks.AIR, INames.BUCKETSTONE, null));
		
		bucketStoneWater = Registry.registerItem(new CustomBucket(Blocks.FLOWING_WATER, INames.BUCKETSTONEWATER, new ItemStack(bucketStone)));
		
		bucketStoneLava = Registry.registerItem(new CustomBucket(Blocks.FLOWING_LAVA, INames.BUCKETSTONELAVA, new ItemStack(bucketStone)));
				
		bucketStoneDemonWater = Registry.registerItem(new CustomBucket(blockDemonWater, INames.BUCKETSTONEDEMONWATER, new ItemStack(bucketStone)));
				
		bucketStoneMilk = Registry.registerItem(new CustomBucketMilk(NetherTweaksMod.tabNetherTweaksMod, new ItemStack(bucketStone), INames.BUCKETSTONEMILK));
				
		//Same with Wood
		bucketWood = Registry.registerItem(new CustomBucket(Blocks.AIR, INames.BUCKETWOOD, null));
		
		bucketWoodWater = Registry.registerItem(new CustomBucket(Blocks.FLOWING_WATER, INames.BUCKETWOODWATER, new ItemStack(bucketWood)));
		
		bucketWoodDemonWater = Registry.registerItem(new CustomBucket(blockDemonWater, INames.BUCKETWOODDEMONWATER, new ItemStack(bucketWood)));
		
		bucketWoodMilk = Registry.registerItem(new CustomBucketMilk(NetherTweaksMod.tabNetherTweaksMod, new ItemStack(bucketWood), INames.BUCKETWOODMILK));		
	}
	
	@SideOnly(Side.CLIENT)
	private static void doHelper(){
		InventoryRenderHelper.fluidRender(blockDemonWater, "nethertweaksmod");
    }
}
