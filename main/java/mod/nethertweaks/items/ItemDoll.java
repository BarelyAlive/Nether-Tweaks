package mod.nethertweaks.items;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.handler.BucketNFluidHandler;
import mod.sfhcore.proxy.IVariantProvider;
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

public class ItemDoll extends Item implements IVariantProvider
{	
	public ItemDoll(String type)
	{		
		setRegistryName(NetherTweaksMod.MODID, type);		
		setCreativeTab(NetherTweaksMod.TABNTM);
	}
	
	public Fluid getSpawnFluid(ItemStack stack) {
		return BucketNFluidHandler.FLUIDLIQUIDIMPOSSIBILITY;
	}
	
	/**
	 * Spawns the mob in the world at position
	 * @param stack The Doll Stack
	 * @param pos Blockpos
	 * @return true if spawn is successful
	 */
	public boolean spawnMob(ItemStack stack, World world, BlockPos pos)
	{
		String name = stack.getItem().getRegistryName().getResourcePath();
		
		switch (name) {
		case "doll_bat":
			EntityBat bat = new EntityBat(world);
			bat.setPosition(pos.getX(), pos.getY()+1, pos.getZ());

			return world.spawnEntity(bat);		
		case "doll_chicken":
			EntityChicken chick = new EntityChicken(world);
			chick.setPosition(pos.getX(), pos.getY()+1, pos.getZ());

			return world.spawnEntity(chick);
		case "doll_cow":
			EntityCow cow = new EntityCow(world);
			cow.setPosition(pos.getX(), pos.getY()+1, pos.getZ());

			return world.spawnEntity(cow);
		case "doll_donkey":
			EntityDonkey donk = new EntityDonkey(world);
			donk.setPosition(pos.getX(), pos.getY()+1, pos.getZ());

			return world.spawnEntity(donk);
		case "doll_horse":
			EntityHorse horst = new EntityHorse(world);
			horst.setPosition(pos.getX(), pos.getY()+1, pos.getZ());

			return world.spawnEntity(horst);
		case "doll_mooshroom":
			EntityMooshroom moo = new EntityMooshroom(world);
			moo.setPosition(pos.getX(), pos.getY()+1, pos.getZ());

			return world.spawnEntity(moo);
		case "doll_mule":
			EntityMule mule = new EntityMule(world);
			mule.setPosition(pos.getX(), pos.getY()+1, pos.getZ());

			return world.spawnEntity(mule);
		case "doll_ocelot":
			EntityOcelot revolver_ocelot = new EntityOcelot(world);
			revolver_ocelot.setPosition(pos.getX(), pos.getY()+1, pos.getZ());

			return world.spawnEntity(revolver_ocelot);
		case "doll_parrot":
			EntityParrot parrot = new EntityParrot(world);
			parrot.setPosition(pos.getX(), pos.getY()+1, pos.getZ());

			return world.spawnEntity(parrot);
		case "doll_rabbit":
			EntityRabbit rabbit = new EntityRabbit(world);
			rabbit.setPosition(pos.getX(), pos.getY()+1, pos.getZ());

			return world.spawnEntity(rabbit);
		case "doll_sheep":
			EntitySheep sheep = new EntitySheep(world);
			sheep.setPosition(pos.getX(), pos.getY()+1, pos.getZ());

			return world.spawnEntity(sheep);
		case "doll_llama":
			EntityLlama llama = new EntityLlama(world);
			llama.setPosition(pos.getX(), pos.getY()+1, pos.getZ());

			return world.spawnEntity(llama);
		case "doll_polar_bear":
			EntityPolarBear pobear = new EntityPolarBear(world);
			pobear.setPosition(pos.getX(), pos.getY()+1, pos.getZ());

			return world.spawnEntity(pobear);
		case "doll_wolf":
			EntityWolf wolf = new EntityWolf(world);
			wolf.setPosition(pos.getX(), pos.getY()+1, pos.getZ());
			
			return world.spawnEntity(wolf);
		case "doll_villager":
			EntityVillager derp = new EntityVillager(world);
			derp.setPosition(pos.getX(), pos.getY()+1, pos.getZ());
			
			return world.spawnEntity(derp);
		case "doll_pig":
			EntityPig pig = new EntityPig(world);
			pig.setPosition(pos.getX(), pos.getY()+1, pos.getZ());

			return world.spawnEntity(pig);
		default:
			return false;
		}
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "item." + getRegistryName().getResourcePath();
	}

	@Override
	public List<Pair<Integer, String>> getVariants()
    {
        List<Pair<Integer, String>> ret = new ArrayList<Pair<Integer, String>>();
        
		ret.add(new ImmutablePair<Integer, String>(0, "inventory"));
		
        return ret;
    }
}