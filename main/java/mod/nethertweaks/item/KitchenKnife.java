package mod.nethertweaks.item;

import java.util.List;
import java.util.Objects;

import mod.nethertweaks.Constants;
import mod.nethertweaks.init.ModFluids;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class KitchenKnife extends Item
{
	public final transient DamageSource blood_lead = new DamageBloodLead();

	public KitchenKnife() {}

	@Override
	public EnumActionResult onItemUse(final EntityPlayer player, final World worldIn, final BlockPos pos, final EnumHand hand,
			final EnumFacing facing, final float hitX, final float hitY, final float hitZ) {

		IFluidHandler handler = FluidUtil.getFluidHandler(worldIn, pos, facing);

		if (player.getHealth() >= 2.0F)
			if (handler != null && handler.fill(new FluidStack(ModFluids.FLUID_BLOOD, 200), true) > 0) {
				player.attackEntityFrom(blood_lead, 1.0f);

				return EnumActionResult.SUCCESS;
			}

		return EnumActionResult.FAIL;
	}

	@Override
	public void addInformation(final ItemStack stack, final World world, final List<String> tooltip, final ITooltipFlag flagIn)
	{
		final String name = Objects.requireNonNull(stack.getItem().getRegistryName()).getResourcePath();

        if (Constants.KITCHEN_KNIFE.equals(name))
			tooltip.add("Cutting edge tool!");
	}

	public static class DamageBloodLead extends DamageSource {
		public DamageBloodLead() {
			super("blood_lead");
			setDamageBypassesArmor();
			setDamageIsAbsolute();
		}

		@Override
		public ITextComponent getDeathMessage(final EntityLivingBase entity) {
			if(entity instanceof EntityPlayer) {
				final EntityPlayer player = (EntityPlayer)entity;
				return new TextComponentString(player.getDisplayName() + " looks a bit pale now!");
			}
			return super.getDeathMessage(entity);
		}
	}
}
