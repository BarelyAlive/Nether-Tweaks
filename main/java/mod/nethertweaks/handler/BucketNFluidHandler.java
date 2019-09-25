package mod.nethertweaks.handler;

import mod.nethertweaks.blocks.DistilledWater;
import mod.nethertweaks.blocks.LiquidImpossibility;
import mod.nethertweaks.config.BlocksItems;
import mod.nethertweaks.fluid.FluidDistilledWater;
import mod.nethertweaks.fluid.FluidLiquidImpossibility;
import mod.sfhcore.helper.NameHelper;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BucketNFluidHandler
{
	//Fluids
	public static final Fluid FLUIDLIQUIDIMPOSSIBILITY = new FluidLiquidImpossibility();
	public static final Block BLOCKLIQUIDIMPOSSIBILITY = new LiquidImpossibility();

	public static final Fluid FLUIDDISTILLEDWATER = new FluidDistilledWater();
	public static final Block BLOCKDISTILLEDWATER = new DistilledWater();

	public static void init(final Side side)
	{
		if (BlocksItems.enableLiquidImpossibility)
		{
			register(FLUIDLIQUIDIMPOSSIBILITY, BLOCKLIQUIDIMPOSSIBILITY);
			if(side.isClient())
				initModel(FLUIDLIQUIDIMPOSSIBILITY, BLOCKLIQUIDIMPOSSIBILITY);
		}
		if(BlocksItems.enableDistilledWater)
		{
			register(FLUIDDISTILLEDWATER, BLOCKDISTILLEDWATER);
			if(side.isClient())
				initModel(FLUIDDISTILLEDWATER, BLOCKDISTILLEDWATER);
		}
	}

	public static void register(final net.minecraftforge.fluids.Fluid f, final Block b)
	{
		f.setUnlocalizedName(f.getName());
		FluidRegistry.addBucketForFluid(f);
	}

	@SideOnly(Side.CLIENT)
	public static void initModel(final Fluid f, final Block b)
	{
		mod.sfhcore.helper.FluidStateMapper mapper = new mod.sfhcore.helper.FluidStateMapper(NameHelper.getModID(b), f);

		Item item = Item.getItemFromBlock(b);
		if (item != null) {
			ModelBakery.registerItemVariants(item);
			ModelLoader.setCustomMeshDefinition(item, mapper);
		}

		ModelLoader.setCustomStateMapper(b, mapper);
	}
}
