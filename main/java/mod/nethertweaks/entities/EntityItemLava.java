package mod.nethertweaks.entities;

import mod.nethertweaks.init.ModItems;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityItemLava extends EntityItem {

	public EntityItemLava(final World worldIn, final double x, final double y, final double z, final ItemStack stack) {
		super(worldIn, x, y, z, stack);
	}

	public EntityItemLava(final World worldIn, final double x, final double y, final double z) {
		super(worldIn, x, y, z);
	}

	public EntityItemLava(final World worldIn) {
		super(worldIn);
	}

	@Override
	protected void setOnFireFromLava() {
		super.setOnFireFromLava();
		EntityItem coiledSword = new EntityItem(world, posX, posY, posZ, new ItemStack(ModItems.COILED_SWORD));
		world.spawnEntity(coiledSword);
	}

}
