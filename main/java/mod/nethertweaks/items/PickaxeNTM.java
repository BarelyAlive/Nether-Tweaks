package mod.nethertweaks.items;

import mod.nethertweaks.INames;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.config.Config;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.common.util.EnumHelper;

public class PickaxeNTM extends ItemPickaxe{
	
	public PickaxeNTM(ToolMaterial material) {
		super(material);
		this.setRegistryName(NetherTweaksMod.MODID, getNameByMaterial(material));
		this.setCreativeTab(NetherTweaksMod.TABNTM);
		this.setMaxStackSize(1);
	}

	private String getNameByMaterial(ToolMaterial tool)
	{
		if(tool == ToolMaterial.STONE)
		{
			setMaxDamage(Config.durabilityPickRack);
			return INames.PICKAXENETHERRACK;
		}
		else
		{
			setMaxDamage(Config.durabilityPickBrick);
			return INames.PICKAXENETHERBRICK;
		}
	}
}
