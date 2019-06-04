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

import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import mod.nethertweaks.INames;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.config.Config;
import mod.sfhcore.util.BlockInfo;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
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
	private static String[] tangible = new String[] {
			"minecraft:cactus", "minecraft:melon_block", "minecraft:web", "minecraft:fern", "minecraft:deadbush"};
	
	public static void setTangible(String[] tangible) {
		Grabber.tangible = tangible;
	}
	
	public static String[] getTangible() {
		return tangible;
	}
	
	public Grabber()
	{
		setCreativeTab(NetherTweaksMod.TABNTM);
		setRegistryName(NetherTweaksMod.MODID, INames.GRABBER);
		setTangible(Config.grabberBlocks);
		setMaxStackSize(1);
	}
	
	@Override
	public boolean itemInteractionForEntity(ItemStack itemstack, EntityPlayer player, EntityLivingBase entity,
			EnumHand hand) {
		if (entity.world.isRemote) return false;
		
        if (entity instanceof IShearable)
        {
            IShearable target = (IShearable)entity;
            BlockPos pos = new BlockPos(entity.posX, entity.posY, entity.posZ);
            if (target.isShearable(itemstack, entity.world, pos))
            {
                List<ItemStack> drops = target.onSheared(itemstack, entity.world, pos,
                        EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, itemstack));

                for(ItemStack stack : drops)
                {
                	player.addItemStackToInventory(stack);
                }
                if (!player.capabilities.isCreativeMode)
                	itemstack.damageItem(1, entity);
            }
            return true;
        }
        return false;
	}
	
	/**
	 * Called when a Block is right-clicked with this Item
	 */
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		BlockInfo info = new BlockInfo(worldIn.getBlockState(pos));
		Block block = info.getBlock();
		
		for (String name : tangible) {
			ResourceLocation loc = new ResourceLocation(name);
			if (loc.equals(block.getRegistryName()) || block instanceof IShearable) {
				if (!worldIn.isRemote) {
					worldIn.setBlockToAir(pos);
					player.inventory.addItemStackToInventory(info.getItemStack());
				}
				if (!player.capabilities.isCreativeMode)
					player.getActiveItemStack().damageItem(1, player);

				return EnumActionResult.SUCCESS;
			}
		}
		return EnumActionResult.FAIL;
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		tooltip.add("This tool allows you to directly transfer Blocks like Melons and Cacti to your inventory. "
				+ "Can be enchanted with fortune for more output when used for example on sheep");
	}
}
