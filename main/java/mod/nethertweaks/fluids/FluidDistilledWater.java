package mod.nethertweaks.fluids;

import mod.nethertweaks.Constants;
import mod.sfhcore.helper.FluidStateMapper;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FluidDistilledWater extends Fluid{

	public static final ResourceLocation STILL = new ResourceLocation("nethertweaksmod:blocks/distilled_water_still");
	public static final ResourceLocation FLOW = new ResourceLocation("nethertweaksmod:blocks/distilled_water_flow");

	public FluidDistilledWater()
	{
		super(Constants.DISTILLED_WATER, STILL, FLOW);

		setTemperature(FluidRegistry.WATER.getTemperature());
		setDensity(FluidRegistry.WATER.getDensity());
		setViscosity(FluidRegistry.WATER.getViscosity());
		setLuminosity(FluidRegistry.WATER.getLuminosity());

		FluidRegistry.registerFluid(this);
		FluidRegistry.addBucketForFluid(this);
	}

	@Override
	public SoundEvent getEmptySound(final FluidStack stack) {
		return SoundEvents.ITEM_BUCKET_FILL;
	}

	@Override
	public boolean doesVaporize(final FluidStack fluidStack) {
		return true;
	}
	
	@SideOnly(Side.CLIENT)
    public void initModel() {
        Block block = this.getBlock();

        FluidStateMapper mapper = new FluidStateMapper(Constants.MOD_ID, this);

        Item item = Item.getItemFromBlock(block);
        if (item != Items.AIR) {
            ModelLoader.registerItemVariants(item);
            ModelLoader.setCustomMeshDefinition(item, mapper);
        }

        ModelLoader.setCustomStateMapper(block, mapper);
    }
}
