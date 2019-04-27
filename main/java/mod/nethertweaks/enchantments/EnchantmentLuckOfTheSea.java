package mod.nethertweaks.enchantments;

import mod.nethertweaks.Config;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.items.ItemMesh;
import mod.sfhcore.registries.Registry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class EnchantmentLuckOfTheSea extends Enchantment
{
	public static final EnchantmentLuckOfTheSea LUCK_OF_THE_SEA = new EnchantmentLuckOfTheSea();

    public EnchantmentLuckOfTheSea() {
        super(Rarity.RARE, EnumEnchantmentType.DIGGER, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
        this.setName("sieve_luck_of_the_sea");
        this.setRegistryName(NetherTweaksMod.MODID, "sieve_luck_of_the_sea");

        if(Config.enableSieveLuckOfTheSea)
            Registry.registerEnchantment(this);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return stack.getItem() instanceof ItemMesh;
    }

    /**
     * Returns the minimal value of enchantability needed on the enchantment level passed.
     */
    @Override
    public int getMinEnchantability(int enchantmentLevel) {
        return 15 + (enchantmentLevel - 1) * 9;
    }

    /**
     * Returns the maximum value of enchantability needed on the enchantment level passed.
     */
    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        return super.getMinEnchantability(enchantmentLevel) + 50;
    }

    /**
     * Returns the maximum level that the enchantment can have.
     */
    @Override
    public int getMaxLevel() {
        return Config.sieveLuckOfTheSeaMaxLevel;
    }
}
