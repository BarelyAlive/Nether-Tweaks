package mod.nethertweaks.items;

import mod.nethertweaks.Constants;
import mod.nethertweaks.NetherTweaksMod;
import net.minecraft.item.ItemPickaxe;
import net.minecraftforge.common.util.EnumHelper;

public class PickaxeNTM extends ItemPickaxe{
	
	public PickaxeNTM(ToolMaterial material) {
		super(material);
		setCreativeTab(NetherTweaksMod.tabNetherTweaksMod);
		setMaxStackSize(1);
	}

}
