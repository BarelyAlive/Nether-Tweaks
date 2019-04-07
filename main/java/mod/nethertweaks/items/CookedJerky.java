package mod.nethertweaks.items;

import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.interfaces.INames;
import net.minecraft.item.ItemFood;

public class CookedJerky extends ItemFood{

	public CookedJerky(int healAmount, float saturationModifier, boolean Hundefutter) {
		super(healAmount, saturationModifier, Hundefutter);
		setCreativeTab(NetherTweaksMod.tabNTM);
		setUnlocalizedName(INames.COOKEDJERKY);
		setRegistryName("nethertweaksmod", INames.COOKEDJERKY);
	}

}
