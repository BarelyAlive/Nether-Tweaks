package mod.nethertweaks.items;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import mod.nethertweaks.Constants;
import mod.nethertweaks.config.Config;
import mod.sfhcore.util.BlockInfo;
import net.minecraft.block.Block;
import net.minecraft.block.BlockVine;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

public class Grabber extends ItemShears
{
	private static List<String> tangibleList = new ArrayList<>();

	private void setTangible(final String[] blocks)
	{
		for(String entry : blocks) tangibleList.add(entry);
	}

	public static List<String> getTangible() {
		return tangibleList;
	}

	public Grabber(final int durability, final ToolMaterial material)
	{
		setCreativeTab(Constants.TABNTM);
		setTangible(Config.grabberBlocks);
		setMaxDamage(durability);
		setMaxStackSize(1);
		setRegistryName(new ResourceLocation(Constants.MODID, getName(material)));
		setUnlocalizedName(getName(material));
	}

	@Override
	public boolean itemInteractionForEntity(final ItemStack itemstack, final EntityPlayer player, final EntityLivingBase entity,
			final EnumHand hand) {
		final World world = player.getEntityWorld();
		final BlockPos playerPos = player.getPosition();

		if (world.isRemote) return false;
		if (!(entity instanceof IShearable)) return false;

		IShearable target = (IShearable)entity;
		BlockPos pos = new BlockPos(entity.posX, entity.posY, entity.posZ);
		if (target.isShearable(itemstack, world, pos))
		{
			List<ItemStack> drops = target.onSheared(itemstack, world, pos,
					EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, itemstack));

			for(ItemStack stack : drops)
				if (!player.inventory.addItemStackToInventory(stack.copy()))
				{
					EntityItem item = new EntityItem(world, playerPos.getX(), playerPos.getY(), playerPos.getZ(), stack.copy());
					world.spawnEntity(item);
				}
			if (!player.capabilities.isCreativeMode)
				itemstack.damageItem(1, entity);
		}
		return true;
	}

	/**
	 * Called when a Block is right-clicked with this Item
	 */
	@Override
	public EnumActionResult onItemUse(final EntityPlayer player, final World world, final BlockPos pos, final EnumHand hand, final EnumFacing facing, final float hitX, final float hitY, final float hitZ)
	{
		//Grab vines except the highest one
		final BlockInfo info = new BlockInfo(world.getBlockState(pos));
		final Block block = info.getBlock();
		final BlockPos playerPos = player.getPosition();

		if(block instanceof BlockVine)
		{
			BlockPos posi = pos;
			int count = 0;

			while (world.getBlockState(posi).getBlock() instanceof BlockVine)
				if (world.getBlockState(posi).getBlock() instanceof BlockVine)
					posi = posi.add(0, -1, 0);

			posi = posi.add(0, 1, 0);

			while (world.getBlockState(posi).getBlock() instanceof BlockVine)
				if (world.getBlockState(posi).getBlock() instanceof BlockVine) {
					posi = posi.add(0, 1, 0);
					count++;
				}

			posi = posi.add(0, -2, 0);
			count--;

			for(int i = 0; i < count; i++)
			{
				if (!world.isRemote) {
					world.setBlockToAir(posi);
					if (!player.inventory.addItemStackToInventory(new ItemStack(block, 1))) {
						EntityItem item = new EntityItem(world, playerPos.getX(), playerPos.getY(), playerPos.getZ(), info.getItemStack());
						world.spawnEntity(item);
					}
					posi = posi.add(0, -1, 0);
				}
				if (!player.capabilities.isCreativeMode)
					player.getHeldItem(hand).damageItem(1, player);
			}
			return EnumActionResult.SUCCESS;
		}

		//Pick up blocks like melons from the list
		for (String name : tangibleList) {
			final ResourceLocation loc = new ResourceLocation(name);
			if (loc.equals(block.getRegistryName()) || block instanceof IShearable) {
				if (!world.isRemote) {
					world.setBlockToAir(pos);
					if (!player.inventory.addItemStackToInventory(new ItemStack(block, 1))) {
						EntityItem item = new EntityItem(world, playerPos.getX(), playerPos.getY(), playerPos.getZ(), info.getItemStack());
						world.spawnEntity(item);
					}
				}
				if (!player.capabilities.isCreativeMode)
					player.getHeldItem(hand).damageItem(1, player);

				return EnumActionResult.SUCCESS;
			}
		}
		return EnumActionResult.FAIL;
	}

	@Override
	public void addInformation(final ItemStack stack, final World world, final List<String> tooltip, final ITooltipFlag flagIn)
	{
		if (Keyboard.isKeyDown(Keyboard.KEY_J))
			tooltip.add("This tool allows you to directly transfer Blocks like Melons and Cacti to your inventory. "
					+ "Also serves as shears.");
		else
			tooltip.add("Hold [J] for more info!");

	}

	private String getName(final ToolMaterial m)
	{
		return "grabber_" + m.name();
	}
}
