package mod.nethertweaks.enchantments;

import mod.nethertweaks.Constants;
import mod.nethertweaks.config.Config;
import mod.nethertweaks.items.ItemMesh;
import mod.sfhcore.registries.Registry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class EnchantmentEfficiency extends Enchantment
{
	public static final EnchantmentEfficiency EFFICIENCY = new EnchantmentEfficiency();

	public EnchantmentEfficiency() {
		super(Rarity.COMMON, EnumEnchantmentType.DIGGER, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
		setName("sieve_efficiency");
		this.setRegistryName(Constants.MODID, "sieve_efficiency");

		if(Config.enableSieveEfficiency)
			Registry.registerEnchantment(this);
	}

	@Override
	public boolean canApplyAtEnchantingTable(final ItemStack stack) {
		return stack.getItem() instanceof ItemMesh;
	}

	/**
	 * Returns the minimal value of enchantability needed on the enchantment level passed.
	 */
	@Override
	public int getMinEnchantability(final int enchantmentLevel) {
		return 1 + 10 * (enchantmentLevel - 1);
	}

	/**
	 * Returns the maximum value of enchantability needed on the enchantment level passed.
	 */
	@Override
	public int getMaxEnchantability(final int enchantmentLevel) {
		return super.getMinEnchantability(enchantmentLevel) + 50;
	}

	/**
	 * Returns the maximum level that the enchantment can have.
	 */
	@Override
	public int getMaxLevel() {
		return Config.sieveEfficiencyMaxLevel;
	}
}
