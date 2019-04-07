package mod.nethertweaks.items;

import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.interfaces.INames;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.common.util.EnumHelper;

public class PickaxeNTM extends ItemPickaxe{
	
	public PickaxeNTM(ToolMaterial material) {
		super(material);
		this.setUnlocalizedName(getNameByMaterial(material));
		setRegistryName("nethertweaksmod", getNameByMaterial(material));
		setCreativeTab(NetherTweaksMod.tabNTM);
		setMaxStackSize(1);
	}

	private String getNameByMaterial(ToolMaterial tool)
	{
		if(tool == ToolMaterial.STONE)
		{
			return INames.PICKAXENETHERRACK;
		}
		else
		{
			return INames.PICKAXENETHERBRICK;
		}
	}
}
