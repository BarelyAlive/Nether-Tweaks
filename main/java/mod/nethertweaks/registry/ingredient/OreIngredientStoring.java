package mod.nethertweaks.registry.ingredient;

import net.minecraftforge.oredict.OreIngredient;

/**
 * Exactly the same as OreIngredient, but it still has the name in it so we can store it to JSON, YAML or whatever.
 */
public class OreIngredientStoring extends OreIngredient {
	private final String oreName;

	public OreIngredientStoring(final String ore) {
		super(ore);
		oreName = ore;
	}

	public String getOreName() {
		return oreName;
	}
}
