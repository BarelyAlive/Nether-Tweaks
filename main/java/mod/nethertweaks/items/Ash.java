package mod.nethertweaks.items;

import mod.nethertweaks.INames;
import mod.nethertweaks.NetherTweaksMod;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Ash extends Item
{
	public Ash() {
		setCreativeTab(NetherTweaksMod.TABNTM);
		setRegistryName(new ResourceLocation(NetherTweaksMod.MODID, INames.ASH));
	}

	@Override
	public EnumActionResult onItemUse(final EntityPlayer player, final World worldIn, final BlockPos pos, final EnumHand hand, final EnumFacing facing, final float hitX, final float hitY, final float hitZ)
	{
		ItemStack itemstack = player.getHeldItem(hand);

		if (!player.canPlayerEdit(pos.offset(facing), facing, itemstack))
			return EnumActionResult.FAIL;
		else if (applyBonemeal(itemstack, worldIn, pos, player, hand))
		{
			if (!worldIn.isRemote)
				worldIn.playEvent(2005, pos, 0);

			return EnumActionResult.SUCCESS;
		}
		return EnumActionResult.PASS;
	}

	public static boolean applyBonemeal(final ItemStack stack, final World worldIn, final BlockPos target)
	{
		if (worldIn instanceof net.minecraft.world.WorldServer)
			return applyBonemeal(stack, worldIn, target, net.minecraftforge.common.util.FakePlayerFactory.getMinecraft((net.minecraft.world.WorldServer)worldIn), null);
		return false;
	}

	public static boolean applyBonemeal(final ItemStack stack, final World worldIn, final BlockPos target, final EntityPlayer player, @javax.annotation.Nullable final EnumHand hand)
	{
		IBlockState iblockstate = worldIn.getBlockState(target);

		int hook = net.minecraftforge.event.ForgeEventFactory.onApplyBonemeal(player, worldIn, target, iblockstate, stack, hand);
		if (hook != 0) return hook > 0;

		if (iblockstate.getBlock() instanceof IGrowable)
		{
			IGrowable igrowable = (IGrowable)iblockstate.getBlock();

			if (igrowable.canGrow(worldIn, target, iblockstate, worldIn.isRemote))
			{
				if (!worldIn.isRemote)
				{
					if (igrowable.canUseBonemeal(worldIn, worldIn.rand, target, iblockstate))
						igrowable.grow(worldIn, worldIn.rand, target, iblockstate);

					stack.shrink(1);
				}

				return true;
			}
		}

		return false;
	}

	@SideOnly(Side.CLIENT)
	public static void spawnBonemealParticles(final World worldIn, final BlockPos pos, int amount)
	{
		if (amount == 0)
			amount = 15;

		IBlockState iblockstate = worldIn.getBlockState(pos);

		if (iblockstate.getMaterial() != Material.AIR)
			for (int i = 0; i < amount; ++i)
			{
				double d0 = itemRand.nextGaussian() * 0.02D;
				double d1 = itemRand.nextGaussian() * 0.02D;
				double d2 = itemRand.nextGaussian() * 0.02D;
				worldIn.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, pos.getX() + itemRand.nextFloat(), pos.getY() + itemRand.nextFloat() * iblockstate.getBoundingBox(worldIn, pos).maxY, pos.getZ() + itemRand.nextFloat(), d0, d1, d2);
			}
		else
			for (int i1 = 0; i1 < amount; ++i1)
			{
				double d0 = itemRand.nextGaussian() * 0.02D;
				double d1 = itemRand.nextGaussian() * 0.02D;
				double d2 = itemRand.nextGaussian() * 0.02D;
				worldIn.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, pos.getX() + itemRand.nextFloat(), pos.getY() + (double)itemRand.nextFloat() * 1.0f, pos.getZ() + itemRand.nextFloat(), d0, d1, d2, new int[0]);
			}
	}

	@Override
	public boolean itemInteractionForEntity(final ItemStack stack, final EntityPlayer playerIn, final EntityLivingBase target, final EnumHand hand)
	{
		if (target instanceof EntitySheep)
		{
			EntitySheep entitysheep = (EntitySheep)target;
			EnumDyeColor enumdyecolor = EnumDyeColor.SILVER;

			if (!entitysheep.getSheared() && entitysheep.getFleeceColor() != enumdyecolor)
			{
				entitysheep.setFleeceColor(enumdyecolor);
				stack.shrink(1);
			}

			return true;
		} else
			return false;
	}
}
