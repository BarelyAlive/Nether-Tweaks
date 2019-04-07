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
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemCactusGrabber extends ItemShears{

	public ItemCactusGrabber(ToolMaterial material) {
		setCreativeTab(NetherTweaksMod.tabNTM);
		setUnlocalizedName(INames.CACTUSGRABBER);
		setRegistryName("nethertweaksmod", INames.CACTUSGRABBER);
		setMaxStackSize(1);
	}
	
	/**
	 * Called when a Block is right-clicked with this Item
	 */
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		
		RayTraceResult rtr = new RayTraceResult(player);
		
		Block block = worldIn.getBlockState(rtr.getBlockPos()).getBlock();
		if(block == Blocks.CACTUS || block == Blocks.MELON_BLOCK){
			worldIn.setBlockToAir(rtr.getBlockPos());
			player.inventory.addItemStackToInventory(new ItemStack(block));
			
			if(!player.capabilities.isCreativeMode)
				player.getActiveItemStack().damageItem(1, player);

			return EnumActionResult.SUCCESS;
		}else{
			return EnumActionResult.FAIL;
		}
	}
}
