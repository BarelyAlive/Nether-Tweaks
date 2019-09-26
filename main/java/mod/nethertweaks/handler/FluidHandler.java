package mod.nethertweaks.handler;

import mod.nethertweaks.Constants;
import mod.nethertweaks.blocks.DistilledWater;
import mod.nethertweaks.blocks.LiquidImpossibility;
import mod.nethertweaks.config.BlocksItems;
import mod.nethertweaks.fluid.FluidDistilledWater;
import mod.nethertweaks.fluid.FluidLiquidImpossibility;
import mod.nethertweaks.proxy.CommonProxy;
import mod.sfhcore.helper.FluidStateMapper;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.Fluid;

public class FluidHandler
{
	//Fluids
	public static final Fluid FLUIDLIQUIDIMPOSSIBILITY = new FluidLiquidImpossibility();
	public static final Block BLOCKLIQUIDIMPOSSIBILITY = new LiquidImpossibility();

	public static final Fluid FLUIDDISTILLEDWATER = new FluidDistilledWater();
	public static final Block BLOCKDISTILLEDWATER = new DistilledWater();

	public FluidHandler()
	{
		init();
	}
	
	private void init()
	{
		if (BlocksItems.enableLiquidImpossibility)
			initModel(FLUIDLIQUIDIMPOSSIBILITY, BLOCKLIQUIDIMPOSSIBILITY);
		if(BlocksItems.enableDistilledWater)
			initModel(FLUIDDISTILLEDWATER, BLOCKDISTILLEDWATER);
	}

	private void initModel(final Fluid f, final Block b)
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
}
