package mod.nethertweaks.handler;

import mod.nethertweaks.Constants;
import mod.nethertweaks.blocks.DistilledWater;
import mod.nethertweaks.blocks.LiquidImpossibility;
import mod.nethertweaks.config.BlocksItems;
import mod.nethertweaks.fluid.FluidDistilledWater;
import mod.nethertweaks.fluid.FluidLiquidImpossibility;
import mod.sfhcore.SFHCore;
import mod.sfhcore.handler.BucketHandler;
import net.minecraft.block.Block;
import net.minecraftforge.fluids.Fluid;

public class FluidHandler
{
	//Fluids
	public static final Fluid FLUID_LIQUID_IMPOSSIBILITY = new FluidLiquidImpossibility();
	public static final Block BLOCK_LIQUID_IMPOSSIBILITY = new LiquidImpossibility();

	public static final Fluid FLUID_DISTILLED_WATER = new FluidDistilledWater();
	public static final Block BLOCK_DISTILLED_WATER = new DistilledWater();

	public static void init()
	{
		if (BlocksItems.enableLiquidImpossibility)
			SFHCore.getProxy().initModel(FLUID_LIQUID_IMPOSSIBILITY, BLOCK_LIQUID_IMPOSSIBILITY);
		if(BlocksItems.enableDistilledWater)
			SFHCore.getProxy().initModel(FLUID_DISTILLED_WATER, BLOCK_DISTILLED_WATER);

		//Erst wenn die Fluids Models haben
		registerBuckets();
	}

	private static void registerBuckets()
	{
		if(BlocksItems.enableWoodBucket)
			BucketHandler.addBucket("wood", "Wood", 505, 16, Constants.MOD_ID, 0x80874633, Constants.TABNTM);
		if(BlocksItems.enableStoneBucket)
			BucketHandler.addBucket("stone", "Stone", -1, 16, Constants.MOD_ID, 0x80778899, Constants.TABNTM);
	}
}
