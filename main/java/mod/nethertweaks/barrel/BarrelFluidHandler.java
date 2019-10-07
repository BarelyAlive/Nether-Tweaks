package mod.nethertweaks.barrel;

import javax.annotation.Nullable;

import mod.nethertweaks.blocks.tile.TileBarrel;
import mod.nethertweaks.network.MessageBarrelModeUpdate;
import mod.nethertweaks.network.MessageFluidUpdate;
import mod.nethertweaks.registries.manager.NTMRegistryManager;
import mod.nethertweaks.registries.registries.BarrelModeRegistry;
import mod.nethertweaks.registries.registries.BarrelModeRegistry.TriggerType;
import mod.sfhcore.network.NetworkHandler;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class BarrelFluidHandler extends FluidTank {

	private TileBarrel barrel;

	public BarrelFluidHandler(final TileBarrel barrel) {
		this(Fluid.BUCKET_VOLUME);
		this.barrel = barrel;
	}

	private BarrelFluidHandler(final int capacity) {
		super(capacity);
	}

	public BarrelFluidHandler(@Nullable final FluidStack fluidStack, final int capacity) {
		super(fluidStack, capacity);
	}

	public BarrelFluidHandler(final Fluid fluid, final int amount, final int capacity) {
		super(fluid, amount, capacity);
	}

	@Override
	public boolean canFillFluidType(final FluidStack fluid) {
		if (fluid == null || fluid.getFluid() == null || NTMRegistryManager.BARREL_LIQUID_BLACKLIST_REGISTRY.isBlacklisted(barrel.getTier(), fluid.getFluid().getName()))
			return false;

		for (IBarrelMode mode : BarrelModeRegistry.getModes(TriggerType.FLUID))
			if (mode.isTriggerFluidStack(fluid))
				return true;
		return false;
	}

	@Override
	public boolean canFill() {
		return barrel.getMode() == null || barrel.getMode().canFillWithFluid(barrel);
	}

	@Override
	public int fill(final FluidStack resource, final boolean doFill) {
		if (barrel.getMode() != null && !barrel.getMode().canFillWithFluid(barrel))
			return 0;

		int amount = super.fill(resource, doFill);
		if (amount > 0) {
			NetworkHandler.sendToAllAround(new MessageFluidUpdate(fluid, barrel.getPos()), barrel);
			if (fluid != null && barrel.getMode() == null) {
				barrel.setMode("fluid");
				NetworkHandler.sendToAllAround(new MessageBarrelModeUpdate(barrel.getMode().getName(), barrel.getPos()), barrel);
			}
		}
		return amount;
	}

	@Override
	public FluidStack drain(final FluidStack resource, final boolean doDrain) {
		FluidStack stack = super.drain(resource, doDrain);
		if (stack != null && stack.amount > 0)
			NetworkHandler.sendToAllAround(new MessageFluidUpdate(fluid, barrel.getPos()), barrel);
		if (fluid == null && barrel.getMode() != null && barrel.getMode().getName().equals("fluid")) {
			barrel.setMode("null");
			NetworkHandler.sendToAllAround(new MessageBarrelModeUpdate("null", barrel.getPos()), barrel);
		}
		return stack;
	}

	@Override
	public FluidStack drain(final int maxDrain, final boolean doDrain) {
		FluidStack stack = super.drain(maxDrain, doDrain);
		if (stack != null && stack.amount > 0)
			NetworkHandler.sendToAllAround(new MessageFluidUpdate(fluid, barrel.getPos()), barrel);
		if (fluid == null && barrel.getMode() != null && barrel.getMode().getName().equals("fluid")) {
			barrel.setMode("null");
			NetworkHandler.sendToAllAround(new MessageBarrelModeUpdate("null", barrel.getPos()), barrel);
		}
		return stack;
	}

	@Override
	protected void onContentsChanged() {

	}

	public void setBarrel(final TileBarrel barrel) {
		this.barrel = barrel;
	}

}
