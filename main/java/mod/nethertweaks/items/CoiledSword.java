package mod.nethertweaks.items;

import mod.nethertweaks.Constants;
import mod.nethertweaks.NetherTweaksMod;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;

public class CoiledSword extends Item{

	public CoiledSword() {
		super();
		setRegistryName(Constants.MODID, Constants.COILED_SWORD);
		setCreativeTab(NetherTweaksMod.TABNTM);
		setMaxDamage(1);
		setMaxStackSize(1);
	}

	@Override
	public boolean onEntityItemUpdate(final EntityItem entityItem) {
		entityItem.setEntityInvulnerable(true);
		entityItem.extinguish();
		return super.onEntityItemUpdate(entityItem);
	}
}
