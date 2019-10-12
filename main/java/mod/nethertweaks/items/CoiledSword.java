package mod.nethertweaks.items;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;

public class CoiledSword extends Item{

	public CoiledSword() {
		super();
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
