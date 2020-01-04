package mod.nethertweaks.init;

import mod.nethertweaks.Constants;
import mod.nethertweaks.block.Blood;
import mod.nethertweaks.block.Brine;
import mod.nethertweaks.block.DistilledWater;
import mod.nethertweaks.config.BlocksItems;
import mod.nethertweaks.fluids.FluidBlood;
import mod.nethertweaks.fluids.FluidBrine;
import mod.nethertweaks.fluids.FluidDistilledWater;
import mod.sfhcore.handler.BucketHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModFluids
{
	//Fluids
	public static final FluidBlood FLUID_BLOOD = new FluidBlood();
	public static final Blood BLOCK_BLOOD = new Blood();

	public static final FluidDistilledWater FLUID_DISTILLED_WATER = new FluidDistilledWater();
	public static final DistilledWater BLOCK_DISTILLED_WATER = new DistilledWater();

	public static final FluidBrine FLUID_BRINE = new FluidBrine();
	public static final Brine BLOCK_BRINE = new Brine();

	public static void preInit()
	{
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

	@SideOnly(Side.CLIENT)
    public static void initModels() {
        FLUID_BLOOD.initModel();
        FLUID_DISTILLED_WATER.initModel();
        FLUID_BRINE.initModel();
    }
}
