package mod.nethertweaks.modules.moofluid;

import com.robrit.moofluids.common.entity.EntityFluidCow;

import net.minecraftforge.fluids.Fluid;

public class AbstractMooFluids implements IAbstractCow {
	private final EntityFluidCow cow;

	public AbstractMooFluids(final EntityFluidCow entity){
		cow = entity;
	}

	@Override
	public int getAvailableFluid() {
		// Fraction of a full bucket.
		return Fluid.BUCKET_VOLUME * (cow.getEntityTypeData().getMaxUseCooldown() - cow.getNextUseCooldown()) / cow.getEntityTypeData().getMaxUseCooldown() ;
	}

	@Override
	public int addCooldownEquivalent(final int millibuckets) {
		// Fraction of max cooldown added.
		final int timeAdded = cow.getEntityTypeData().getMaxUseCooldown() * millibuckets / Fluid.BUCKET_VOLUME;
		// Set Cooldown
		cow.setNextUseCooldown(cow.getNextUseCooldown() + timeAdded);

		return timeAdded;
	}

	@Override
	public Fluid getFluid() {
		return cow.getEntityFluid();
	}
}
