package mod.nethertweaks.modules.moofluid;

import ftblag.fluidcows.entity.EntityFluidCow;
import ftblag.fluidcows.gson.FCConfig;
import net.minecraftforge.fluids.Fluid;

public class AbstractFluidCow implements IAbstractCow {
	private final EntityFluidCow cow;
	private final int maxCooldown;

	public AbstractFluidCow(final EntityFluidCow entity){
		cow = entity;
		maxCooldown = FCConfig.getWorldCD(cow.fluid.getName());
	}

	@Override
	public int getAvailableFluid() {
		// Fraction of a full bucket.
		return Fluid.BUCKET_VOLUME * (maxCooldown - cow.getCD()) / maxCooldown ;
	}

	@Override
	public int addCooldownEquivalent(final int millibuckets) {
		// Fraction of max cooldown added.
		final int timeAdded = maxCooldown * millibuckets / Fluid.BUCKET_VOLUME;
		// Set Cooldown
		cow.updateCD(cow.getCD() + timeAdded);
		return timeAdded;
	}

	@Override
	public Fluid getFluid() {
		return cow.fluid;
	}
}
