package mod.nethertweaks.modules.moofluid;

import net.minecraftforge.fluids.Fluid;

public interface IAbstractCow {

	// Returns the maximum amount of fluid that can be generated
	// by the cow based on how much of the cooldown remains
	int getAvailableFluid();
	// Adds an amount of time equivalent to the provided fluid
	// volume to the Cow's cooldown. Returns the number of ticks added.
	int addCooldownEquivalent(int millibuckets);
	// Return the fluid generated by the cow
	Fluid getFluid();

}
