package mod.nethertweaks.blocks;

import mod.nethertweaks.Constants;
import mod.nethertweaks.config.Config;
import mod.nethertweaks.handler.FluidHandler;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving.SpawnPlacementType;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;


public class LiquidImpossibility extends BlockFluidClassic
{
	public LiquidImpossibility()
	{
		super(FluidHandler.FLUID_LIQUID_IMPOSSIBILITY, Material.WATER);
		setRegistryName(Constants.MOD_ID, Constants.LIQUID_IMPOSSIBILITY);
		setLightLevel(Config.luminosityLI);
		setTemperature(Config.temperatureLI);
		setDensity(Config.densityLI);
		setBlockUnbreakable();
	}

	@Override
	public boolean canSpawnInBlock() {
		return true;
	}

	@Override
	public boolean canCreatureSpawn(final IBlockState state, final IBlockAccess world, final BlockPos pos, final SpawnPlacementType type)
	{
        return type.equals(SpawnPlacementType.IN_WATER) && state.equals(getDefaultState());
    }

	@Override
	public void onEntityCollidedWithBlock(final World world, final BlockPos pos, final IBlockState state, final Entity entity)
	{
		if (world.isRemote) return;
		if (entity.isDead) return;

		if (Config.spawnSkeletons)
			if (entity instanceof EntityWitherSkeleton && ((EntityWitherSkeleton) entity).getAttackTarget() == null)
			{
				EntitySkeleton skeleton = new EntitySkeleton(world);
				skeleton.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
				skeleton.setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), entity.rotationYaw, entity.rotationPitch);
				skeleton.setNoAI(skeleton.isAIDisabled());

				if (skeleton.hasCustomName()) {
					skeleton.setCustomNameTag(skeleton.getCustomNameTag());
					skeleton.setAlwaysRenderNameTag(skeleton.getAlwaysRenderNameTag());
				}
				skeleton.setHealth(skeleton.getMaxHealth());

				world.spawnEntity(skeleton);
				entity.setDead();
			}

		if (Config.spawnSlimes)
			if (entity instanceof EntityMagmaCube && ((EntityMagmaCube) entity).getAttackTarget() == null)
			{

				EntitySlime slime = new EntitySlime(world);
				slime.setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), entity.rotationYaw, entity.rotationPitch);
				slime.setNoAI(slime.isAIDisabled());

				if (slime.hasCustomName()) {
					slime.setCustomNameTag(slime.getCustomNameTag());
					slime.setAlwaysRenderNameTag(slime.getAlwaysRenderNameTag());
				}
				slime.setHealth(slime.getMaxHealth());

				world.spawnEntity(slime);
				entity.setDead();
			}
	}

	@Override
	public IBlockState getStateFromMeta(final int meta)
	{
		return getBlockState().getBaseState().withProperty(LEVEL, meta);
	}

	@Override
	public Fluid getFluid() {
		return FluidHandler.FLUID_LIQUID_IMPOSSIBILITY;
	}


}
