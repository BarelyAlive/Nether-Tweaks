package mod.nethertweaks.fluid;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import mod.nethertweaks.handler.BucketNFluidHandler;
import mod.nethertweaks.interfaces.INames;
import mod.sfhcore.proxy.IVariantProvider;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FluidDemonWater extends Fluid{

	public static ResourceLocation still = new ResourceLocation("nethertweaksmod:blocks/demon_water_still");
	public static ResourceLocation flow = new ResourceLocation("nethertweaksmod:blocks/demon_water_flow");
	
	public FluidDemonWater() {
		super(INames.DEMONWATERFLUID, still, flow);
		setDensity(2000);
        setViscosity(2000);
		setUnlocalizedName(INames.DEMONWATERFLUID);
		setLuminosity(FluidRegistry.LAVA.getLuminosity());
		FluidRegistry.registerFluid(this);
	}
	
	@Override
	public boolean doesVaporize(FluidStack fluidStack) {
		return false;
	}
	
	@SideOnly(Side.CLIENT)
	public void initModel() {
		Block block = BucketNFluidHandler.BLOCKDEMONWATER;
		
		FluidStateMapper mapper = new FluidStateMapper(this);
		
		Item item = Item.getItemFromBlock(block);
		if (item != null) {
			ModelLoader.registerItemVariants(item);
			ModelLoader.setCustomMeshDefinition(item, mapper);
		}
		
		ModelLoader.setCustomStateMapper(block, mapper);		
	}
}
