package mod.nethertweaks.fluid;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import mod.nethertweaks.Config;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.handler.BucketNFluidHandler;
import mod.nethertweaks.interfaces.INames;
import mod.sfhcore.blocks.Fluid;
import mod.sfhcore.helper.FluidStateMapper;
import mod.sfhcore.proxy.IVariantProvider;
import net.minecraft.block.Block;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FluidLiquidImpossibility extends Fluid{

	public static final ResourceLocation STILL = new ResourceLocation("nethertweaksmod:blocks/liquid_impossibility_still");
	public static final ResourceLocation FLOW = new ResourceLocation("nethertweaksmod:blocks/liquid_impossibility_flow");
	
	public FluidLiquidImpossibility()
	{
		super(INames.FLUIDLIQUIDIMPOSSIBILITY, STILL, FLOW);
		
		setDensity(FluidRegistry.WATER.getDensity());
		setTemperature(0);
        setViscosity(FluidRegistry.WATER.getViscosity());
		setLuminosity(FluidRegistry.LAVA.getLuminosity());
	}
	
	@Override
	public SoundEvent getEmptySound(FluidStack stack) {
		return SoundEvents.ITEM_BUCKET_FILL;
	}
	
	@Override
	public boolean doesVaporize(FluidStack fluidStack) {
		return Config.doesLIVaporize;
	}
}
