package mod.nethertweaks.handler;

import mod.nethertweaks.INames;
import mod.nethertweaks.blocks.LiquidImpossibility;
import mod.nethertweaks.config.Config;
import mod.nethertweaks.fluid.FluidLiquidImpossibility;
import mod.sfhcore.handler.BucketHandler;
import mod.sfhcore.registries.RegisterFluid;
import net.minecraft.block.Block;
import net.minecraftforge.fluids.Fluid;

public class BucketNFluidHandler implements INames
{
	//Fluids
	public static final Fluid FLUIDLIQUIDIMPOSSIBILITY = new FluidLiquidImpossibility();
	public static final Block BLOCKLIQUIDIMPOSSIBILITY = new LiquidImpossibility();

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
		if(Config.enableWoodBucket)
			BucketHandler.addBucket("wood", "Wood", 505, 16, MODID, 0x80874633, TAB);
		if(Config.enableStoneBucket)
			BucketHandler.addBucket("stone", "Stone", -1, 16, MODID, 0x80778899, TAB);
	}
}
