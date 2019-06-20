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
import mod.nethertweaks.INames;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.blocks.LiquidImpossibility;
import mod.nethertweaks.config.Config;
import mod.nethertweaks.fluid.FluidLiquidImpossibility;
import mod.sfhcore.Constants;
import mod.sfhcore.handler.BucketHandler;
import mod.sfhcore.items.CustomBucket;
import mod.sfhcore.items.CustomBucketMilk;
import mod.sfhcore.registries.RegisterFluid;
import mod.sfhcore.registries.Registry;
import mod.sfhcore.util.BlockInfo;

public class BucketNFluidHandler implements INames
{
	//Fluids
	public static final Fluid FLUIDLIQUIDIMPOSSIBILITY = new FluidLiquidImpossibility();
	public static final Block BLOCKLIQUIDIMPOSSIBILITY = new LiquidImpossibility();

	public static void init(Side side)
	{
		registerFluids(side);
		registerBuckets();
	}
	
	public static void registerFluids(Side side)
	{
		if (Config.enableLiquidImpossibility)
		{
			RegisterFluid.register(FLUIDLIQUIDIMPOSSIBILITY, BLOCKLIQUIDIMPOSSIBILITY);
			if(side == Side.CLIENT)
				RegisterFluid.initModel((mod.sfhcore.fluid.Fluid) FLUIDLIQUIDIMPOSSIBILITY, BLOCKLIQUIDIMPOSSIBILITY);
		}
	}

	public static void registerBuckets()
	{
		if(Config.enableWoodBucket)
			BucketHandler.addBucket("wood", "Wood", 505, 16, MODID, 0x80874633, TAB);
		if(Config.enableStoneBucket)
			BucketHandler.addBucket("stone", "Stone", -1, 16, MODID, 0x80778899, TAB);
	}
}
