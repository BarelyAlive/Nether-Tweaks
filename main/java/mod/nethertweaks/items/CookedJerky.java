package mod.nethertweaks.items;

import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.interfaces.INames;
import net.minecraft.item.ItemFood;

public class CookedJerky extends ItemFood{

	public CookedJerky() {
		super(6, 1.0F, true);
		setCreativeTab(NetherTweaksMod.tabNTM);
		setUnlocalizedName(INames.COOKEDJERKY);
		setRegistryName("nethertweaksmod", INames.COOKEDJERKY);
	}

}
