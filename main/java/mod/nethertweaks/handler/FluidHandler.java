package mod.nethertweaks.handler;

import mod.nethertweaks.Constants;
import mod.nethertweaks.blocks.DistilledWater;
import mod.nethertweaks.blocks.LiquidImpossibility;
import mod.nethertweaks.config.BlocksItems;
import mod.nethertweaks.fluid.FluidDistilledWater;
import mod.nethertweaks.fluid.FluidLiquidImpossibility;
import mod.nethertweaks.proxy.CommonProxy;
import mod.sfhcore.handler.BucketHandler;
import mod.sfhcore.helper.FluidStateMapper;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
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
			initModel(FLUID_LIQUID_IMPOSSIBILITY, BLOCK_LIQUID_IMPOSSIBILITY);
		if(BlocksItems.enableDistilledWater)
			initModel(FLUID_DISTILLED_WATER, BLOCK_DISTILLED_WATER);

		//Erst wenn die Fluids Models haben
		registerBuckets();
	}

	private static void initModel(final Fluid f, final Block b)
	{
		if(CommonProxy.isClient())
		{
			FluidStateMapper mapper = new FluidStateMapper(Constants.MODID, f);

			Item item = Item.getItemFromBlock(b);
			if (item != null) {
				ModelBakery.registerItemVariants(item);
				ModelLoader.setCustomMeshDefinition(item, mapper);
			}

			ModelLoader.setCustomStateMapper(b, mapper);
		}
	}

	private static void registerBuckets()
	{
		if(BlocksItems.enableWoodBucket)
			BucketHandler.addBucket("wood", "Wood", 505, 16, Constants.MODID, 0x80874633, Constants.TABNTM);
		if(BlocksItems.enableStoneBucket)
			BucketHandler.addBucket("stone", "Stone", -1, 16, Constants.MODID, 0x80778899, Constants.TABNTM);
	}
}
