package mod.nethertweaks.handler;

import mod.nethertweaks.INames;
import mod.nethertweaks.blocks.DistilledWater;
import mod.nethertweaks.blocks.LiquidImpossibility;
import mod.nethertweaks.config.BlocksItems;
import mod.nethertweaks.config.Config;
import mod.nethertweaks.fluid.FluidDistilledWater;
import mod.nethertweaks.fluid.FluidLiquidImpossibility;
import mod.sfhcore.handler.BucketHandler;
import mod.sfhcore.registries.RegisterFluid;
import net.minecraft.block.Block;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.relauncher.Side;

public class BucketNFluidHandler implements INames
{
	//Fluids
	public static final Fluid FLUIDLIQUIDIMPOSSIBILITY = new FluidLiquidImpossibility();
	public static final Block BLOCKLIQUIDIMPOSSIBILITY = new LiquidImpossibility();
	
	public static final Fluid FLUIDDISTILLEDWATER = new FluidDistilledWater();
	public static final Block BLOCKDISTILLEDWATER = new DistilledWater();

	public static void init(Side side)
	{
		registerFluids(side);
		registerBuckets();
	}

	public static void registerFluids(Side side)
	{
		if (BlocksItems.enableLiquidImpossibility)
		{
			RegisterFluid.register(FLUIDLIQUIDIMPOSSIBILITY, BLOCKLIQUIDIMPOSSIBILITY);
			if(side.isClient())
				RegisterFluid.initModel((mod.sfhcore.fluid.Fluid) FLUIDLIQUIDIMPOSSIBILITY, BLOCKLIQUIDIMPOSSIBILITY);
		}
		if(BlocksItems.enableDistilledWater)
		{
			RegisterFluid.register(FLUIDDISTILLEDWATER, BLOCKDISTILLEDWATER);
			if(side.isClient())
				RegisterFluid.initModel((mod.sfhcore.fluid.Fluid) FLUIDDISTILLEDWATER, BLOCKDISTILLEDWATER);
		}
	}

	public static void registerBuckets()
	{
		if(BlocksItems.enableWoodBucket)
			BucketHandler.addBucket("wood", "Wood", 505, 16, MODID, 0x80874633, TAB);
		if(BlocksItems.enableStoneBucket)
			BucketHandler.addBucket("stone", "Stone", -1, 16, MODID, 0x80778899, TAB);
	}
}
