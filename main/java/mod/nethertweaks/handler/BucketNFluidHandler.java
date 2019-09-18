package mod.nethertweaks.handler;

import mod.nethertweaks.Constants;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.blocks.DistilledWater;
import mod.nethertweaks.blocks.LiquidImpossibility;
import mod.nethertweaks.config.BlocksItems;
import mod.nethertweaks.fluid.FluidDistilledWater;
import mod.nethertweaks.fluid.FluidLiquidImpossibility;
import mod.sfhcore.handler.BucketHandler;
import mod.sfhcore.registries.RegisterFluid;
import net.minecraft.block.Block;
import net.minecraftforge.fluids.Fluid;

public class BucketNFluidHandler
{
	//Fluids
	public static final Fluid FLUIDLIQUIDIMPOSSIBILITY = new FluidLiquidImpossibility();
	public static final Block BLOCKLIQUIDIMPOSSIBILITY = new LiquidImpossibility();

	public static final Fluid FLUIDDISTILLEDWATER = new FluidDistilledWater();
	public static final Block BLOCKDISTILLEDWATER = new DistilledWater();

	public static void registerFluids()
	{
		if(BlocksItems.enableLiquidImpossibility)
			RegisterFluid.register(FLUIDLIQUIDIMPOSSIBILITY, BLOCKLIQUIDIMPOSSIBILITY);
		if(BlocksItems.enableDistilledWater)
			RegisterFluid.register(FLUIDDISTILLEDWATER, BLOCKDISTILLEDWATER);
	}

	public static void registerFluidModels()
	{
		if(BlocksItems.enableLiquidImpossibility)
			RegisterFluid.initModel((mod.sfhcore.fluid.Fluid) FLUIDLIQUIDIMPOSSIBILITY, BLOCKLIQUIDIMPOSSIBILITY);
		if(BlocksItems.enableDistilledWater)
			RegisterFluid.initModel((mod.sfhcore.fluid.Fluid) FLUIDDISTILLEDWATER, BLOCKDISTILLEDWATER);
	}

	public static void registerBuckets()
	{
		if(BlocksItems.enableWoodBucket)
			BucketHandler.addBucket("wood", "Wood", 505, 16, Constants.MODID, 0x80874633, NetherTweaksMod.TAB);
		if(BlocksItems.enableStoneBucket)
			BucketHandler.addBucket("stone", "Stone", -1, 16, Constants.MODID, 0x80778899, NetherTweaksMod.TAB);
	}
}
