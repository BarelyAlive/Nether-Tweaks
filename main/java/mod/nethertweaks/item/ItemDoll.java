package mod.nethertweaks.item;

import java.util.Objects;

import mod.nethertweaks.Constants;
import mod.nethertweaks.init.ModFluids;
import mod.sfhcore.helper.NameHelper;
import net.minecraft.entity.monster.EntityPolarBear;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityDonkey;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityMule;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityParrot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;

public class ItemDoll extends Item
{
	public ItemDoll(){}

	public Fluid getSpawnFluid(final ItemStack stack) {
		return ModFluids.FLUID_LIQUID_IMPOSSIBILITY;
	}

	/**
	 * Spawns the mob in the world at position
	 * @param stack The Doll Stack
	 * @param pos Blockpos
	 * @return true if spawn is successful
	 */
	public boolean spawnMob(final ItemStack stack, final World world, final BlockPos pos)
	{
		String name = NameHelper.getName(stack);

		switch (name) {
		case Constants.DOLL_BAT:
			EntityBat bat = new EntityBat(world);
			bat.setPosition(pos.getX(), pos.getY()+1, pos.getZ());

			return world.spawnEntity(bat);
		case Constants.DOLL_CHICKEN:
			EntityChicken chick = new EntityChicken(world);
			chick.setPosition(pos.getX(), pos.getY()+1, pos.getZ());

			return world.spawnEntity(chick);
		case Constants.DOLL_COW:
			EntityCow cow = new EntityCow(world);
			cow.setPosition(pos.getX(), pos.getY()+1, pos.getZ());

			return world.spawnEntity(cow);
		case Constants.DOLL_DONKEY:
			EntityDonkey donk = new EntityDonkey(world);
			donk.setPosition(pos.getX(), pos.getY()+1, pos.getZ());

			return world.spawnEntity(donk);
		case Constants.DOLL_HORSE:
			EntityHorse horst = new EntityHorse(world);
			horst.setPosition(pos.getX(), pos.getY()+1, pos.getZ());

			return world.spawnEntity(horst);
		case Constants.DOLL_RED_MOOSHROOM:
			EntityMooshroom moo = new EntityMooshroom(world);
			moo.setPosition(pos.getX(), pos.getY()+1, pos.getZ());

			return world.spawnEntity(moo);
		case Constants.DOLL_MULE:
			EntityMule mule = new EntityMule(world);
			mule.setPosition(pos.getX(), pos.getY()+1, pos.getZ());

			return world.spawnEntity(mule);
		case Constants.DOLL_OCELOT:
			EntityOcelot revolver_ocelot = new EntityOcelot(world);
			revolver_ocelot.setPosition(pos.getX(), pos.getY()+1, pos.getZ());

			return world.spawnEntity(revolver_ocelot);
		case Constants.DOLL_PARROT:
			EntityParrot parrot = new EntityParrot(world);
			parrot.setPosition(pos.getX(), pos.getY()+1, pos.getZ());

			return world.spawnEntity(parrot);
		case Constants.DOLL_RABBIT:
			EntityRabbit rabbit = new EntityRabbit(world);
			rabbit.setPosition(pos.getX(), pos.getY()+1, pos.getZ());

			return world.spawnEntity(rabbit);
		case Constants.DOLL_SHEEP:
			EntitySheep sheep = new EntitySheep(world);
			sheep.setPosition(pos.getX(), pos.getY()+1, pos.getZ());

			return world.spawnEntity(sheep);
		case Constants.DOLL_LLAMA:
			EntityLlama llama = new EntityLlama(world);
			llama.setPosition(pos.getX(), pos.getY()+1, pos.getZ());

			return world.spawnEntity(llama);
		case Constants.DOLL_POLAR_BEAR:
			EntityPolarBear pobear = new EntityPolarBear(world);
			pobear.setPosition(pos.getX(), pos.getY()+1, pos.getZ());

			return world.spawnEntity(pobear);
		case Constants.DOLL_WOLF:
			EntityWolf wolf = new EntityWolf(world);
			wolf.setPosition(pos.getX(), pos.getY()+1, pos.getZ());

			return world.spawnEntity(wolf);
		case Constants.DOLL_VILLAGER:
			EntityVillager derp = new EntityVillager(world);
			derp.setPosition(pos.getX(), pos.getY()+1, pos.getZ());

			return world.spawnEntity(derp);
		case Constants.DOLL_PIG:
			EntityPig pig = new EntityPig(world);
			pig.setPosition(pos.getX(), pos.getY()+1, pos.getZ());

			return world.spawnEntity(pig);
		default:
			return false;
		}
	}

	@Override
	public String getUnlocalizedName(final ItemStack stack) {
		return "item." + Objects.requireNonNull(getRegistryName()).getResourcePath();
	}
}