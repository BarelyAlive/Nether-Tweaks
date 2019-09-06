package mod.nethertweaks.items;

import mod.nethertweaks.INames;
import mod.nethertweaks.NetherTweaksMod;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemSword;

public class CoiledSword extends ItemSword{

	public CoiledSword() {
		super(ToolMaterial.IRON);
		setRegistryName(NetherTweaksMod.MODID, INames.COILED_SWORD);
		setCreativeTab(NetherTweaksMod.TABNTM);
		setMaxDamage(1);
		setMaxStackSize(1);
	}
	
	@Override
	public boolean onEntityItemUpdate(EntityItem entityItem) {
		entityItem.setEntityInvulnerable(true);
		entityItem.extinguish();
		return super.onEntityItemUpdate(entityItem);
	}
}
