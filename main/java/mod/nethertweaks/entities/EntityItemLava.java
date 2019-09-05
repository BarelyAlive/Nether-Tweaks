package mod.nethertweaks.entities;

import mod.nethertweaks.handler.ItemHandler;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityItemLava extends EntityItem {

	public EntityItemLava(World worldIn, double x, double y, double z, ItemStack stack) {
		super(worldIn, x, y, z, stack);
	}
	
	public EntityItemLava(World worldIn, double x, double y, double z) {
		super(worldIn, x, y, z);
	}
	
	public EntityItemLava(World worldIn) {
		super(worldIn);
	}
	
	@Override
	protected void setOnFireFromLava() {
		super.setOnFireFromLava();
			EntityItem coiledSword = new EntityItem(world, posX, posY, posZ, new ItemStack(ItemHandler.COILED_SWORD));
			this.world.spawnEntity(coiledSword);
	}
	
}
