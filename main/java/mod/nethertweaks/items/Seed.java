package mod.nethertweaks.items;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import mod.nethertweaks.Config;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.blocks.Sieve.MeshType;
import mod.nethertweaks.interfaces.INames;
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

public class Seed extends CustomItem
{
	private final List<IPlantable> plants = new ArrayList<>();
	
	public Seed()
	{
		super(null, 64, NetherTweaksMod.TABNTM, true, 4, new ResourceLocation(NetherTweaksMod.MODID, INames.SEED));
		
		for(String s : Config.rubberSeed){
            Block block = Block.getBlockFromName(s);
            if(block instanceof IPlantable)
                addPlant((IPlantable) block);
		}
	}
	
	public void addPlant(IPlantable plant){
        plants.add(plant);
	}

	/**
	 * Called when a Block is right-clicked with this Item
	 */
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		Block block = worldIn.getBlockState(pos).getBlock();
		
		switch (player.getActiveItemStack().getItemDamage()) {
		case 0:
			if(block == Blocks.DIRT || block == Blocks.GRASS){
				worldIn.setBlockState(pos, Blocks.MYCELIUM.getDefaultState());
			}
			break;
		case 1:
			if(block == Blocks.DIRT){
				worldIn.setBlockState(pos, Blocks.GRASS.getDefaultState());
			}
			break;
		case 2:
			Block block1 = worldIn.getBlockState(pos.add(1, 1, 0)).getBlock();
			Block block2 = worldIn.getBlockState(pos.add(0, 1, 1)).getBlock();
			Block block3 = worldIn.getBlockState(pos.add(-1, 1, 0)).getBlock();
			Block block4 = worldIn.getBlockState(pos.add(0, 1, -1)).getBlock();
			Block block5 = worldIn.getBlockState(pos.add(0, 1, 0)).getBlock();
			if(block == Blocks.SAND)
			{
				if( block1.equals(Blocks.AIR)
				 && block2.equals(Blocks.AIR)
				 && block3.equals(Blocks.AIR)
				 && block4.equals(Blocks.AIR)
				 && block5.equals(Blocks.AIR)
				)
				{
					worldIn.setBlockState(pos.add(0, 1, 0), Blocks.CACTUS.getDefaultState());
				}
			}
			break;
		case 3:
			if (!facing.equals(EnumFacing.UP))
	            return EnumActionResult.PASS;

	        ItemStack stack = player.getHeldItem(hand);

	        if (player.canPlayerEdit(pos, facing, stack) && player.canPlayerEdit(pos.add(0, 1, 0), facing, stack)) {
	            IBlockState soil = worldIn.getBlockState(pos);

	            // Filter Plants to just ones that can be planted on this block.
	            List<IPlantable> validPlants = plants.stream().filter(p -> soil.getBlock().canSustainPlant(soil, worldIn, pos, EnumFacing.UP, p)).collect(Collectors.toList());
	            final IBlockState plant = validPlants.get(worldIn.rand.nextInt(validPlants.size())).getPlant(worldIn, pos);

	            if (worldIn.isAirBlock(pos.add(0, 1, 0))) {
	                worldIn.setBlockState(pos.add(0, 1, 0), plant);
	                if(!player.isCreative())
	                    stack.shrink(1);
	                return EnumActionResult.SUCCESS;
	            }
	        }
			break;
		default:
			break;
		}
		
		return EnumActionResult.SUCCESS;
	}
}
