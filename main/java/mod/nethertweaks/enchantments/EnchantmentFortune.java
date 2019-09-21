package mod.nethertweaks.enchantments;

import mod.nethertweaks.Constants;
import mod.nethertweaks.config.Config;
import mod.nethertweaks.items.ItemMesh;
import mod.sfhcore.registries.Registry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class EnchantmentFortune extends Enchantment
{
	public static final EnchantmentFortune FORTUNE = new EnchantmentFortune();

	public EnchantmentFortune() {
		super(Rarity.RARE, EnumEnchantmentType.DIGGER, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
		setName("sieve_fortune");
		this.setRegistryName(Constants.MODID, "sieve_fortune");

		if(Config.enableSieveFortune)
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
		return 15 + (enchantmentLevel - 1) * 9;
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
		return Config.sieveFortuneMaxLevel;
	}
}
