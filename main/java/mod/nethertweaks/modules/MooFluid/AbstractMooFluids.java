package mod.nethertweaks.modules.MooFluid;

import com.robrit.moofluids.common.entity.EntityFluidCow;

import net.minecraftforge.fluids.Fluid;

public class AbstractMooFluids implements IAbstractCow {
    private final EntityFluidCow cow;

    public AbstractMooFluids(EntityFluidCow entity){
        cow = entity;
    }

    @Override
    public int getAvailableFluid() {
        // Fraction of a full bucket.
        return Fluid.BUCKET_VOLUME * (cow.getEntityTypeData().getMaxUseCooldown() - cow.getNextUseCooldown()) / cow.getEntityTypeData().getMaxUseCooldown() ;
    }

    @Override
    public int addCooldownEquivalent(int millibuckets) {
        // Fraction of max cooldown added.
        int timeAdded = cow.getEntityTypeData().getMaxUseCooldown() * millibuckets / Fluid.BUCKET_VOLUME;
        // Set Cooldown
        cow.setNextUseCooldown(cow.getNextUseCooldown() + timeAdded);

        return timeAdded;
    }

    @Override
    public Fluid getFluid() {
        return cow.getEntityFluid();
}
}
