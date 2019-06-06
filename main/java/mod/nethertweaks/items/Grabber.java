package mod.nethertweaks.items;

import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import mod.nethertweaks.INames;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.config.Config;
import mod.sfhcore.util.BlockInfo;
import net.minecraft.block.Block;
import net.minecraft.block.BlockVine;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Grabber extends ItemShears
{
	private static List<String> tangibleList = new ArrayList<String>();
	
	private void setTangible(String[] blocks)
	{
		for(String entry : blocks) tangibleList.add(entry);
	}
	
	public static List<String> getTangible() {
		return tangibleList;
	}
	
	public Grabber(int durability, ToolMaterial material)
	{
		setCreativeTab(NetherTweaksMod.TABNTM);
		setNameFromMaterial(material);
		setTangible(Config.grabberBlocks);
		setMaxDamage(durability);
		setMaxStackSize(1);
	}
	
	@Override
	public boolean itemInteractionForEntity(ItemStack itemstack, EntityPlayer player, EntityLivingBase entity,
			EnumHand hand) {
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
            {
            	if (!player.inventory.addItemStackToInventory(stack.copy()))
            	{
					EntityItem item = new EntityItem(world, playerPos.getX(), playerPos.getY(), playerPos.getZ(), stack.copy());
					world.spawnEntity(item);
				}
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
	public EnumActionResult onItemUse(EntityPlayer player, World world, final BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		//Grab vines except the highest one
		final BlockInfo info = new BlockInfo(world.getBlockState(pos));
		final Block block = info.getBlock();
		final BlockPos playerPos = player.getPosition();
		
		if(block instanceof BlockVine)
		{
			BlockPos posi = pos;
			int count = 0;
			
			while (world.getBlockState(posi).getBlock() instanceof BlockVine) {
				if (world.getBlockState(posi).getBlock() instanceof BlockVine) {
					posi = posi.add(0, -1, 0);
				}
			}
			
			posi = posi.add(0, 1, 0);
			
			while (world.getBlockState(posi).getBlock() instanceof BlockVine) {
				if (world.getBlockState(posi).getBlock() instanceof BlockVine) {
					posi = posi.add(0, 1, 0);
					count++;
				}
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
					player.getActiveItemStack().damageItem(1, player);
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
					player.getActiveItemStack().damageItem(1, player);

				return EnumActionResult.SUCCESS;
			}
		}
		return EnumActionResult.FAIL;
	}
	
	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flagIn)
	{
		tooltip.add("This tool allows you to directly transfer Blocks like Melons and Cacti to your inventory. "
				+ "Can be enchanted with fortune for more output when used for example on sheep");
	}
	
	private void setNameFromMaterial(ToolMaterial material)
	{
		switch (material) {
		case WOOD:
			setRegistryName(NetherTweaksMod.MODID, INames.GRABBER_WOOD);
			break;
		case GOLD:
			setRegistryName(NetherTweaksMod.MODID, INames.GRABBER_GOLD);
			break;
		case STONE:
			setRegistryName(NetherTweaksMod.MODID, INames.GRABBER_STONE);
			break;
		case IRON:
			setRegistryName(NetherTweaksMod.MODID, INames.GRABBER_IRON);
			break;
		case DIAMOND:
			setRegistryName(NetherTweaksMod.MODID, INames.GRABBER_DIAMOND);
			break;
		default:
			break;
		}
	}
}
