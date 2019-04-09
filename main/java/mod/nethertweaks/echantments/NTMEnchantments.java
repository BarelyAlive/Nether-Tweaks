package mod.nethertweaks.echantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

public class NTMEnchantments {
	public static EnchantmentEfficiency efficiency = new EnchantmentEfficiency();
	public static EnchantmentFortune fortune = new EnchantmentFortune();
	public static EnchantmentLuckOfTheSea luckOfTheSea = new EnchantmentLuckOfTheSea();
    
    @SubscribeEvent
    public void registerEnchantments(IForgeRegistry<Enchantment> reg) {
    	reg.register(efficiency);
    	reg.register(fortune);
    	reg.register(luckOfTheSea);
    }
    
}