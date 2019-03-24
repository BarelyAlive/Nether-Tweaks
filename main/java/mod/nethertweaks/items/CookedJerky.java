package mod.nethertweaks.items;

import mod.nethertweaks.INames;
import mod.nethertweaks.NetherTweaksMod;
import net.minecraft.item.ItemFood;

public class CookedJerky extends ItemFood{

	public CookedJerky(int healAmount, float saturationModifier, boolean Hundefutter) {
		super(healAmount, saturationModifier, Hundefutter);
		setCreativeTab(NetherTweaksMod.tabNetherTweaksMod);
		setUnlocalizedName(INames.COOKEDJERKY);
		setRegistryName(INames.COOKEDJERKY);
	}

}
