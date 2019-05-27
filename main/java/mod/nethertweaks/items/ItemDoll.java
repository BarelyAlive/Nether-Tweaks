package mod.nethertweaks.items;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import mod.nethertweaks.INames;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.blocks.Sieve.MeshType;
import mod.nethertweaks.handler.BucketNFluidHandler;
import mod.sfhcore.proxy.IVariantProvider;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityEnderman;
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
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemDoll extends Item implements IVariantProvider{
	
	public static final String BAT = "bat";
	public static final String CHICKEN = "chicken";
	public static final String COW = "cow";
	public static final String DONKEY = "donkey";
	public static final String HORSE = "horse";
	public static final String RED_MOOSHROOM = "red_mooshroom";
	public static final String MULE = "mule";
	public static final String OCELOT = "ocelot";
	public static final String PARROT = "parrot";
	public static final String RABBIT = "rabbit";
	public static final String SHEEP = "sheep";
	public static final String LLAMA = "llama";
	public static final String POLAR_BEAR = "polar_bear";
	public static final String WOLF = "wolf";
	public static final String VILLAGER = "villager";
	
	private static ArrayList<String> names = new ArrayList<String>();
	
	public ItemDoll()
	{		
		super();
		setRegistryName(NetherTweaksMod.MODID, INames.DOLL);
		
		setCreativeTab(NetherTweaksMod.TABNTM);
		setHasSubtypes(true);
		
		names.add(BAT);
		names.add(CHICKEN);
		names.add(COW);
		names.add(DONKEY);
		names.add(HORSE);
		names.add(RED_MOOSHROOM);
		names.add(MULE);
		names.add(OCELOT);
		names.add(PARROT);
		names.add(RABBIT);
		names.add(SHEEP);
		names.add(LLAMA);
		names.add(POLAR_BEAR);
		names.add(WOLF);
		names.add(VILLAGER);
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
		int dmg = stack.getItemDamage();
		
		switch (dmg) {
		case 0:
			EntityBat bat = new EntityBat(world);
			bat.setPosition(pos.getX(), pos.getY()+1, pos.getZ());

			return world.spawnEntity(bat);		
		case 1:
			EntityChicken chick = new EntityChicken(world);
			chick.setPosition(pos.getX(), pos.getY()+1, pos.getZ());

			return world.spawnEntity(chick);
		case 2:
			EntityCow cow = new EntityCow(world);
			cow.setPosition(pos.getX(), pos.getY()+1, pos.getZ());

			return world.spawnEntity(cow);
		case 3:
			EntityDonkey donk = new EntityDonkey(world);
			donk.setPosition(pos.getX(), pos.getY()+1, pos.getZ());

			return world.spawnEntity(donk);
		case 4:
			EntityHorse horst = new EntityHorse(world);
			horst.setPosition(pos.getX(), pos.getY()+1, pos.getZ());

			return world.spawnEntity(horst);
		case 5:
			EntityMooshroom moo = new EntityMooshroom(world);
			moo.setPosition(pos.getX(), pos.getY()+1, pos.getZ());

			return world.spawnEntity(moo);
		case 6:
			EntityMule mule = new EntityMule(world);
			mule.setPosition(pos.getX(), pos.getY()+1, pos.getZ());

			return world.spawnEntity(mule);
		case 7:
			EntityOcelot revolver_ocelot = new EntityOcelot(world);
			revolver_ocelot.setPosition(pos.getX(), pos.getY()+1, pos.getZ());

			return world.spawnEntity(revolver_ocelot);
		case 8:
			EntityParrot parrot = new EntityParrot(world);
			parrot.setPosition(pos.getX(), pos.getY()+1, pos.getZ());

			return world.spawnEntity(parrot);
		case 9:
			EntityRabbit rabbit = new EntityRabbit(world);
			rabbit.setPosition(pos.getX(), pos.getY()+1, pos.getZ());

			return world.spawnEntity(rabbit);
		case 10:
			EntitySheep sheep = new EntitySheep(world);
			sheep.setPosition(pos.getX(), pos.getY()+1, pos.getZ());

			return world.spawnEntity(sheep);
		case 11:
			EntityLlama llama = new EntityLlama(world);
			llama.setPosition(pos.getX(), pos.getY()+1, pos.getZ());

			return world.spawnEntity(llama);
		case 12:
			EntityPolarBear pobear = new EntityPolarBear(world);
			pobear.setPosition(pos.getX(), pos.getY()+1, pos.getZ());

			return world.spawnEntity(pobear);
		case 13:
			EntityWolf wolf = new EntityWolf(world);
			wolf.setPosition(pos.getX(), pos.getY()+1, pos.getZ());
		case 14:
			EntityVillager derp = new EntityVillager(world);
			derp.setPosition(pos.getX(), pos.getY()+1, pos.getZ());

			return world.spawnEntity(derp);
		default:
			return false;
		}
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return getUnlocalizedName() + "_" + names.get(stack.getItemDamage());
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (tab.equals(this.getCreativeTab())) {
			for (int i = 0; i < names.size(); i++)
				items.add(new ItemStack(this, 1, i));
		}
	}

	@Override
	public List<Pair<Integer, String>> getVariants()
    {
        List<Pair<Integer, String>> ret = new ArrayList<Pair<Integer, String>>();
        
        for(int i = 0; i < names.size(); i++)
		{
			ret.add(new ImmutablePair<Integer, String>(i, "type=" + names.get(i)));
		}
        return ret;
    }
}