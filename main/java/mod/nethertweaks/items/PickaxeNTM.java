package mod.nethertweaks.items;

import mod.nethertweaks.INames;
import mod.nethertweaks.NetherTweaksMod;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.common.util.EnumHelper;

public class PickaxeNTM extends ItemPickaxe{
	
	public PickaxeNTM(ToolMaterial material) {
		super(material);
		this.setUnlocalizedName(getNameByMaterial(material));
		setRegistryName("nethertweaksmod", getNameByMaterial(material));
		setCreativeTab(NetherTweaksMod.tabNetherTweaksMod);
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
