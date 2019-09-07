package mod.nethertweaks.entities;

import javax.annotation.Nonnull;

import mod.nethertweaks.handler.ItemHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ProjectileStone extends EntityThrowable {

	private ItemStack stack;

	public void setStack(final ItemStack stack) {
		this.stack = stack;
	}

	public ProjectileStone(final World worldIn) {
		super(worldIn);
	}

	public ProjectileStone(final World worldIn, final EntityLivingBase thrower) {
		super(worldIn, thrower);
	}

	public ProjectileStone(final World worldIn, final double x, final double y, final double z) {
		super(worldIn, x, y, z);
	}

	@Override
	protected void onImpact(@Nonnull final RayTraceResult result) {
		if (result.entityHit != null)
			result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, getThrower()), 1.0F);
		else if (!getEntityWorld().isRemote) {
			setDead();

			if (stack != null)
				getEntityWorld().spawnEntity(new EntityItem(getEntityWorld(), posX, posY, posZ, stack));
		}

		for (int j = 0; j < 8; ++j)
			getEntityWorld().spawnParticle(EnumParticleTypes.BLOCK_CRACK, posX, posY, posZ, 0.0D, 0.0D, 0.0D, Block.getStateId(Blocks.STONE.getDefaultState()));
	}

	@Override
	public void writeEntityToNBT(final NBTTagCompound tag) {
		super.writeEntityToNBT(tag);

		NBTTagCompound stackTag = new NBTTagCompound();
		stack.writeToNBT(stackTag);

		tag.setTag("pebbleStack", stackTag);
	}

	@Override
	public void readEntityFromNBT(final NBTTagCompound tag) {
		super.readEntityFromNBT(tag);

		if (tag.hasKey("pebbleStack"))
			stack = new ItemStack((NBTTagCompound) tag.getTag("pebbleStack"));
		else
			stack = new ItemStack(ItemHandler.PEBBLE_STONE);
	}
}