package mod.nethertweaks.modules.thirst;

import java.lang.reflect.Field;
import java.util.Random;

import mod.nethertweaks.config.Config;
import mod.nethertweaks.network.MessageThirstStats;
import mod.sfhcore.network.NetworkHandler;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.biome.Biome;

public class ThirstStats {

	public static final int POISON_DURATION = 60 * 20;

	public int thirstLevel;
	public float saturation;
	public float exhaustion;
	public int thirstTimer;
	public boolean poisoned;
	public int poisonTimer;

	public int movementSpeed;

	public transient int lastThirstLevel;
	public transient float lastSaturation;
	public transient boolean lastPoisoned;

	public final transient Random random = new Random();
	public final transient DamageSource thirstDmgSource = new DamageThirst();

	public transient Field foodTimer;

	public ThirstStats() {
		lastThirstLevel = -1; // Trigger a refresh when this class is loaded.
		resetStats();
	}

	public void update(final EntityPlayer player)
	{
		//KILL-SWITCH
		if(!Config.enableThirst) return;

		if(exhaustion > 5.0f) {
			exhaustion -= 5.0f;
			if(saturation > 0.0f)
				saturation = Math.max(saturation - 1.0f, 0);
			else if(player.world.getDifficulty() != EnumDifficulty.PEACEFUL)
				thirstLevel = Math.max(thirstLevel - 1, 0);
		}

		if(thirstLevel <= 6) {
			player.setSprinting(false);
			if(thirstLevel == 0)
				if(thirstTimer++ > 200)
					if(player.getHealth() > 10.0f || player.world.getDifficulty() == EnumDifficulty.HARD || player.world.getDifficulty() == EnumDifficulty.NORMAL && player.getHealth() > 1.0f) {
						thirstTimer = 0;
						player.attackEntityFrom(thirstDmgSource, 1);
					}
		}

		final int ms = player.isRiding() ? 10 : movementSpeed;
		final float exhaustMultiplier = exhaustionMultiplier(player);

		if(player.isInsideOfMaterial(Material.WATER) || player.isInWater())
			addExhaustion(0.03f * ms * 0.003f * exhaustMultiplier);
		else if(player.onGround) {
			if(player.isSprinting())
				addExhaustion(0.06f * ms * 0.018f * exhaustMultiplier);
			else
				addExhaustion(0.01f * ms * 0.018f * exhaustMultiplier);
		} else if(!player.isRiding())
			if(player.isSprinting())
				addExhaustion(0.06f * ms * 0.025f * exhaustMultiplier);
			else
				addExhaustion(0.01f * ms * 0.025f * exhaustMultiplier);

		if(poisoned && thirstLevel > 0)
			if(poisonTimer++ < POISON_DURATION) {
				if(player.getHealth() > 1.0f && player.world.getDifficulty() != EnumDifficulty.PEACEFUL && thirstTimer++ > 200) {
					thirstTimer = 0;
					player.attackEntityFrom(thirstDmgSource, 1);
				}
			} else {
				poisoned = false;
				poisonTimer = 0;
			}

		if(foodTimer == null)
			try {
				foodTimer = player.getFoodStats().getClass().getDeclaredField("foodTimer");
				foodTimer.setAccessible(true);
			} catch (final NoSuchFieldException e) {
				e.printStackTrace();
			}

		if(thirstLevel < 16 || poisoned)
			try {
				foodTimer.setInt(player.getFoodStats().getFoodLevel(), 0);
			} catch (final IllegalAccessException | NullPointerException e) {
				e.printStackTrace();
			}

		// Only send packet update if the thirst level or saturation has changed.
		if(lastThirstLevel != thirstLevel || lastSaturation != saturation || lastPoisoned != poisoned) {
			NetworkHandler.INSTANCE.sendTo(new MessageThirstStats(this), (EntityPlayerMP) player);
			lastThirstLevel = thirstLevel;
			lastSaturation = saturation;
			lastPoisoned = poisoned;
		}

		//		if(Keyboard.isKeyDown(Keyboard.KEY_J))
		//			thirstLevel = Math.max(thirstLevel - 1, 0);
		//		else if(Keyboard.isKeyDown(Keyboard.KEY_K))
		//			thirstLevel = Math.min(thirstLevel + 1, 20);
	}

	private float exhaustionMultiplier(final EntityPlayer player)
	{
		float exhaustMultiplier = Config.exhaustMultiplierDefault;
		final Biome biome = player.world.getBiomeForCoordsBody(player.getPosition());
		final float temp = biome.getDefaultTemperature();

		if(temp > 0.8f)
			exhaustMultiplier *= Math.min(temp / 0.8f, 2.0f);

		//Night multiplier nur da anwenden wo ein Tag/Nacht-Rhytmus vorhanden ist
		if(!player.world.isDaytime() && player.world.provider.hasSkyLight())
			exhaustMultiplier *= Config.exhaustMultiplierNighttime;

		return exhaustMultiplier;
	}

	public void addStats(final int heal, final float sat) {
		thirstLevel = Math.min(thirstLevel + heal, 20);
		saturation = Math.min(saturation + heal * sat * 2.0f, thirstLevel);
	}

	public void addExhaustion(final float exhaustion) {
		this.exhaustion = Math.min(this.exhaustion + exhaustion, 40.0f);
	}

	public void attemptToPoison(final float chance) {
		if(random.nextFloat() < chance)
			poisoned = true;
	}

	public boolean canDrink() {
		return thirstLevel < 20;
	}

	public int getMovementSpeed(final EntityPlayer player) {
		final double x = player.posX - player.prevPosX;
		final double y = player.posY - player.prevPosY;
		final double z = player.posZ - player.prevPosZ;
		return (int) Math.round(100.0d * Math.sqrt(x*x + y*y + z*z));
	}

	public void resetStats() {
		thirstLevel = 20;
		saturation = 5f;
		exhaustion = 0f;
		poisoned = false;
		poisonTimer = 0;
	}

	public static class DamageThirst extends DamageSource {
		public DamageThirst() {
			super("thirst");
			setDamageBypassesArmor();
			setDamageIsAbsolute();
		}

		@Override
		public ITextComponent getDeathMessage(final EntityLivingBase entity) {
			if(entity instanceof EntityPlayer) {
				final EntityPlayer player = (EntityPlayer)entity;
				return new TextComponentString(player.getDisplayName() + "'s body is now made up of 0% water!");
			}
			return super.getDeathMessage(entity);
		}
	}
}
