package mod.nethertweaks.block;

import mod.nethertweaks.config.Config;
import mod.nethertweaks.init.ModFluids;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class Blood extends BlockFluidClassic
{
	public Blood()
	{
		super(ModFluids.FLUID_BLOOD, Material.WATER);
		setLightLevel(Config.luminosityBlood);
		setTemperature(Config.temperatureBlood);
		setDensity(Config.densityBlood);
	}

	@Override
	public boolean canSpawnInBlock() {
		return true;
	}

//	@Override
//	public boolean canCreatureSpawn(final IBlockState state, final IBlockAccess world, final BlockPos pos, final SpawnPlacementType type)
//	{
//        return type.equals(SpawnPlacementType.IN_WATER) && state.equals(getDefaultState());
//  }

	@Override
	public void onEntityCollidedWithBlock(final World world, final BlockPos pos, final IBlockState state, final Entity entity)
	{
		if (world.isRemote) return;
		if (entity.isDead) return;

		if(entity instanceof EntityAnimal)
		{
			EntityAnimal animal = (EntityAnimal) entity;

			animal.addPotionEffect(new PotionEffect(Potion.getPotionById(19), 10 * 20));
		}
		if(entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) entity;

			player.addPotionEffect(new PotionEffect(Potion.getPotionById(19), 10 * 20));
		}

		if (Config.spawnSkeletons)
			if (entity instanceof EntityWitherSkeleton && ((EntityWitherSkeleton) entity).getAttackTarget() == null)
			{
				final EntitySkeleton skeleton = new EntitySkeleton(world);
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

				final EntitySlime slime = new EntitySlime(world);
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
		return ModFluids.FLUID_BLOOD;
	}
}
