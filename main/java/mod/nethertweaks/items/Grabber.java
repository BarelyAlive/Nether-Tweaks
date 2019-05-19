package mod.nethertweaks.items;

import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.interfaces.INames;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Grabber extends ItemShears{

	public Grabber()
	{
		setCreativeTab(NetherTweaksMod.TABNTM);
		setRegistryName(NetherTweaksMod.MODID, INames.GRABBER);
		setMaxStackSize(1);
	}
	
	/**
	 * Called when a Block is right-clicked with this Item
	 */
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{		
		IBlockState block = worldIn.getBlockState(pos);
		if(block == Blocks.CACTUS.getDefaultState() || block == Blocks.MELON_BLOCK.getDefaultState()){
			if (!worldIn.isRemote) {
				worldIn.setBlockToAir(pos);
				player.inventory.addItemStackToInventory(new ItemStack(block.getBlock()));
			}
			if(!player.capabilities.isCreativeMode)
				player.getActiveItemStack().damageItem(1, player);

			return EnumActionResult.SUCCESS;
		}else{
			return EnumActionResult.FAIL;
		}
	}
}
