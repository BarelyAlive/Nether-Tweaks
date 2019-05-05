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
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.blocks.LiquidImpossibility;
import mod.nethertweaks.fluid.FluidLiquidImpossibility;
import mod.nethertweaks.interfaces.INames;
import mod.sfhcore.Constants;
import mod.sfhcore.handler.BucketHandler;
import mod.sfhcore.items.CustomBucket;
import mod.sfhcore.items.CustomBucketMilk;
import mod.sfhcore.registries.RegisterFluid;
import mod.sfhcore.registries.Registry;

public class BucketNFluidHandler
{
		//Fluids
		public static final Fluid FLUIDLIQUIDIMPOSSIBILITY = new FluidLiquidImpossibility();
		public static final Block BLOCKLIQUIDIMPOSSIBILITY = new LiquidImpossibility();
						
		//Buckets
		/*
		public static Item BUCKETWOOD = new CustomBucket(Blocks.AIR, new ResourceLocation(NetherTweaksMod.MODID, INames.BUCKETWOOD), ItemStack.EMPTY, NetherTweaksMod.TABNTM);
		public static Item BUCKETWOODWATER = new CustomBucket(Blocks.FLOWING_WATER, new ResourceLocation(NetherTweaksMod.MODID, INames.BUCKETWOODWATER), new ItemStack(BUCKETWOOD), NetherTweaksMod.TABNTM);
		public static Item BUCKETWOODLI = new CustomBucket(BLOCKLIQUIDIMPOSSIBILITY, new ResourceLocation(NetherTweaksMod.MODID, INames.BUCKETWOODLI), new ItemStack(BUCKETWOOD), NetherTweaksMod.TABNTM);
		public static Item BUCKETWOODMILK = new CustomBucketMilk(NetherTweaksMod.TABNTM, new ItemStack(BUCKETWOOD), new ResourceLocation(NetherTweaksMod.MODID, INames.BUCKETWOODMILK));
		
		public static Item BUCKETSTONE = new CustomBucket(Blocks.AIR, new ResourceLocation(NetherTweaksMod.MODID, INames.BUCKETSTONE), ItemStack.EMPTY, NetherTweaksMod.TABNTM);
		public static Item BUCKETSTONEWATER = new CustomBucket(Blocks.FLOWING_WATER, new ResourceLocation(NetherTweaksMod.MODID, INames.BUCKETSTONEWATER), new ItemStack(BUCKETSTONE), NetherTweaksMod.TABNTM);
		public static Item BUCKETSTONELAVA = new CustomBucket(Blocks.FLOWING_LAVA, new ResourceLocation(NetherTweaksMod.MODID, INames.BUCKETSTONELAVA), new ItemStack(BUCKETSTONE), NetherTweaksMod.TABNTM);
		public static Item BUCKETSTONELI = new CustomBucket(BLOCKLIQUIDIMPOSSIBILITY, new ResourceLocation(NetherTweaksMod.MODID, INames.BUCKETSTONELI), new ItemStack(BUCKETSTONE), NetherTweaksMod.TABNTM);
		public static Item BUCKETSTONEMILK = new CustomBucketMilk(NetherTweaksMod.TABNTM, new ItemStack(BUCKETSTONE), new ResourceLocation(NetherTweaksMod.MODID, INames.BUCKETSTONEMILK));
		*/

	public static void init()
	{
		registerFluids(); //1.
		registerBuckets(); //2.
	}
	
	private static void registerFluids()
	{
		if (Config.enableLiquidImpossibility)
			RegisterFluid.register(FLUIDLIQUIDIMPOSSIBILITY, BLOCKLIQUIDIMPOSSIBILITY);
	}
		
	private static void registerBuckets()
	{
		BucketHandler.addBucket("wood", 0x80778899, NetherTweaksMod.TABNTM);
		/*
		if (Config.enableStoneBucket) {
			//Stone
			Registry.registerItem(BUCKETSTONE);
			Registry.registerItem(BUCKETSTONEWATER);
			Registry.registerItem(BUCKETSTONELAVA);
			Registry.registerItem(BUCKETSTONELI);
			Registry.registerItem(BUCKETSTONEMILK);
		}
		if (Config.enableWoodBucket) {
			//Same with Wood
			Registry.registerItem(BUCKETWOOD);
			Registry.registerItem(BUCKETWOODWATER);
			Registry.registerItem(BUCKETWOODLI);
			Registry.registerItem(BUCKETWOODMILK);
		}
		*/
	}
}
