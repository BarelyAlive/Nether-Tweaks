package mod.nethertweaks.items;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import mod.nethertweaks.INames;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.blocks.Sieve.MeshType;
import mod.nethertweaks.config.Config;
import mod.sfhcore.items.CustomItem;
import mod.sfhcore.proxy.IVariantProvider;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

public class Seed extends Item implements INames
{
	public Seed(String name)
	{
		setCreativeTab(TAB);
		setRegistryName(MODID, name);
	}

	/**
	 * Called when a Block is right-clicked with this Item
	 */
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		Block block = worldIn.getBlockState(pos).getBlock();
		String name = player.getHeldItem(hand).getItem().getRegistryName().getResourcePath();
		
		switch (name) {
		case MUSHROOM_SPORES:
			if(block == Blocks.DIRT || block == Blocks.GRASS){
				if(!worldIn.isRemote)
					worldIn.setBlockState(pos, Blocks.MYCELIUM.getDefaultState());
			}
			break;
		case GRASS_SEEDS:
			if(block == Blocks.DIRT){
				if(!worldIn.isRemote)
					worldIn.setBlockState(pos, Blocks.GRASS.getDefaultState());
			}
			break;
		case CACTUS_SEEDS:
			if(Blocks.CACTUS.canPlaceBlockAt(worldIn, pos.up()))
				if(!worldIn.isRemote)
					worldIn.setBlockState(pos.add(0, 1, 0), Blocks.CACTUS.getDefaultState());
			break;
		case SUGARCANE_SEEDS:
			if(Blocks.REEDS.canPlaceBlockAt(worldIn, pos.up()))
				if(!worldIn.isRemote)
					worldIn.setBlockState(pos.add(0, 1, 0), Blocks.REEDS.getDefaultState());
			break;
		default:
			break;
		}
		
		return EnumActionResult.SUCCESS;
	}
}
