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
import mod.nethertweaks.blocks.BlockDemonWater;
import mod.nethertweaks.fluid.FluidDemonWater;
import mod.nethertweaks.interfaces.INames;
import mod.sfhcore.Constants;
import mod.sfhcore.Registry;
import mod.sfhcore.helper.FluidHelper;
import mod.sfhcore.helper.InventoryRenderHelper;
import mod.sfhcore.items.CustomBucket;
import mod.sfhcore.items.CustomBucketMilk;


public class BucketNFluidHandler {

		//Fluids
		public static final Fluid FLUIDDEMONWATER = new FluidDemonWater();
		public static final Block BLOCKDEMONWATER = new BlockDemonWater();
				
		//Buckets
		public static final Item BUCKETWOOD = new CustomBucket(Blocks.AIR, new ResourceLocation(NetherTweaksMod.MODID, INames.BUCKETWOOD), null);
		public static final Item BUCKETWOODWATER = new CustomBucket(Blocks.FLOWING_WATER, new ResourceLocation(NetherTweaksMod.MODID, INames.BUCKETWOODWATER), new ItemStack(BUCKETWOOD));
		public static final Item BUCKETWOODDEMONWATER = new CustomBucket(BLOCKDEMONWATER, new ResourceLocation(NetherTweaksMod.MODID, INames.BUCKETWOODDEMONWATER), new ItemStack(BUCKETWOOD));
		public static final Item BUCKETWOODMILK = new CustomBucketMilk(NetherTweaksMod.tabNTM, new ItemStack(BUCKETWOOD), new ResourceLocation(NetherTweaksMod.MODID, INames.BUCKETWOODMILK));
		public static final Item BUCKETSTONE = new CustomBucket(Blocks.AIR, new ResourceLocation(NetherTweaksMod.MODID, INames.BUCKETSTONE), null);
		public static final Item BUCKETSTONEWATER = new CustomBucket(Blocks.FLOWING_WATER, new ResourceLocation(NetherTweaksMod.MODID, INames.BUCKETSTONEWATER), new ItemStack(BUCKETSTONE));
		public static final Item BUCKETSTONELAVA = new CustomBucket(Blocks.FLOWING_LAVA, new ResourceLocation(NetherTweaksMod.MODID, INames.BUCKETSTONELAVA), new ItemStack(BUCKETSTONE));
		public static final Item BUCKETSTONEDMW = new CustomBucket(BLOCKDEMONWATER, new ResourceLocation(NetherTweaksMod.MODID, INames.BUCKETSTONEDEMONWATER), new ItemStack(BUCKETSTONE));
		public static final Item BUCKETSTONEMILK = new CustomBucketMilk(NetherTweaksMod.tabNTM, new ItemStack(BUCKETSTONE), new ResourceLocation(NetherTweaksMod.MODID, INames.BUCKETSTONEMILK));

	public static void init() {
		registerBuckets();
		doHelper();
	}
		
	private static void registerBuckets(){
		
		//Fluids
		Registry.registerBlock(BLOCKDEMONWATER);
		FluidRegistry.addBucketForFluid(FLUIDDEMONWATER);
				
		//Regular Buckets
		Registry.registerItem(BUCKETSTONE);		
		Registry.registerItem(BUCKETSTONEWATER);
		Registry.registerItem(BUCKETSTONELAVA);
		Registry.registerItem(BUCKETSTONEDMW);				
		Registry.registerItem(BUCKETSTONEMILK);
				
		//Same with Wood
		Registry.registerItem(BUCKETWOOD);		
		Registry.registerItem(BUCKETWOODWATER);	
		Registry.registerItem(BUCKETWOODDEMONWATER);
		Registry.registerItem(BUCKETWOODMILK);		
	}
	
	@SideOnly(Side.CLIENT)
	private static void doHelper(){
		InventoryRenderHelper.fluidRender(BLOCKDEMONWATER, NetherTweaksMod.MODID);
    }
}
