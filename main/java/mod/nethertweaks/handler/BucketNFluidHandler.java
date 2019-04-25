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
import mod.sfhcore.handler.Registry;
import mod.sfhcore.items.CustomBucket;
import mod.sfhcore.items.CustomBucketMilk;


public class BucketNFluidHandler {

		//Fluids
		public static final Fluid FLUIDLIQUIDIMPOSSIBILITY = new FluidLiquidImpossibility();
		public static final Block BLOCKLIQUIDIMPOSSIBILITY = new LiquidImpossibility();
						
		//Buckets
		public static Item BUCKETWOOD = new CustomBucket(Blocks.AIR, new ResourceLocation(NetherTweaksMod.MODID, INames.BUCKETWOOD), ItemStack.EMPTY, NetherTweaksMod.tabNTM);
		public static Item BUCKETWOODWATER = new CustomBucket(Blocks.FLOWING_WATER, new ResourceLocation(NetherTweaksMod.MODID, INames.BUCKETWOODWATER), new ItemStack(BUCKETWOOD), NetherTweaksMod.tabNTM);
		public static Item BUCKETWOODDMW = new CustomBucket(BLOCKLIQUIDIMPOSSIBILITY, new ResourceLocation(NetherTweaksMod.MODID, INames.BUCKETWOODLI), new ItemStack(BUCKETWOOD), NetherTweaksMod.tabNTM);
		public static Item BUCKETWOODMILK = new CustomBucketMilk(NetherTweaksMod.tabNTM, new ItemStack(BUCKETWOOD), new ResourceLocation(NetherTweaksMod.MODID, INames.BUCKETWOODMILK));
		
		public static Item BUCKETSTONE = new CustomBucket(Blocks.AIR, new ResourceLocation(NetherTweaksMod.MODID, INames.BUCKETSTONE), ItemStack.EMPTY, NetherTweaksMod.tabNTM);
		public static Item BUCKETSTONEWATER = new CustomBucket(Blocks.FLOWING_WATER, new ResourceLocation(NetherTweaksMod.MODID, INames.BUCKETSTONEWATER), new ItemStack(BUCKETSTONE), NetherTweaksMod.tabNTM);
		public static Item BUCKETSTONELAVA = new CustomBucket(Blocks.FLOWING_LAVA, new ResourceLocation(NetherTweaksMod.MODID, INames.BUCKETSTONELAVA), new ItemStack(BUCKETSTONE), NetherTweaksMod.tabNTM);
		public static Item BUCKETSTONEDMW = new CustomBucket(BLOCKLIQUIDIMPOSSIBILITY, new ResourceLocation(NetherTweaksMod.MODID, INames.BUCKETSTONELI), new ItemStack(BUCKETSTONE), NetherTweaksMod.tabNTM);
		public static Item BUCKETSTONEMILK = new CustomBucketMilk(NetherTweaksMod.tabNTM, new ItemStack(BUCKETSTONE), new ResourceLocation(NetherTweaksMod.MODID, INames.BUCKETSTONEMILK));

	public static void init()
	{
		registerFluids(); //1.
		registerBuckets(); //2.
	}
	
	private static void registerFluids()
	{
		//Fluids
		Registry.registerBlock(BLOCKLIQUIDIMPOSSIBILITY);
		((FluidLiquidImpossibility) FLUIDLIQUIDIMPOSSIBILITY).initModel();
		FluidRegistry.addBucketForFluid(FLUIDLIQUIDIMPOSSIBILITY);
	}
		
	private static void registerBuckets()
	{			
		//Stone
		Registry.registerItem(BUCKETSTONE);		
		Registry.registerItem(BUCKETSTONEWATER);
		Registry.registerItem(BUCKETSTONELAVA);
		Registry.registerItem(BUCKETSTONEDMW);				
		Registry.registerItem(BUCKETSTONEMILK);
				
		//Same with Wood
		Registry.registerItem(BUCKETWOOD);		
		Registry.registerItem(BUCKETWOODWATER);	
		Registry.registerItem(BUCKETWOODDMW);
		Registry.registerItem(BUCKETWOODMILK);
	}
}
