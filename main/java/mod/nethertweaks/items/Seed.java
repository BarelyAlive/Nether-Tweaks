package mod.nethertweaks.items;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import mod.nethertweaks.INames;
import mod.nethertweaks.NTMItems;
import mod.nethertweaks.NetherTweaksMod;
import mod.sfhcore.items.ItemThing;
import mod.sfhcore.proxy.IVariantProvider;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class Seed extends ItemThing implements IVariantProvider{
	
	public Seed() {
		super(null, 64, NetherTweaksMod.tabNetherTweaksMod);
	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		
		Block block = worldIn.getBlockState(pos).getBlock();
		
		if(stack.getItem() == NTMItems.mushroomSpores){
				if(block == Blocks.DIRT || block == Blocks.GRASS){
					worldIn.setBlockState(pos, Blocks.MYCELIUM.getDefaultState());
					return EnumActionResult.SUCCESS;
				}
		
			}
		
		if(stack.getItem() == NTMItems.seedGrass){
		
			if(block == Blocks.DIRT){
				worldIn.setBlockState(pos, Blocks.GRASS.getDefaultState());
				return EnumActionResult.SUCCESS;
			}
		}
			
		if(stack.getItem() == NTMItems.itemCactusSeeds){
			if(Blocks.CACTUS.canPlaceBlockAt(worldIn, pos.add(0, 1, 0))){
				worldIn.setBlockState(pos.add(0, 1, 0), Blocks.CACTUS.getDefaultState());
				return EnumActionResult.SUCCESS;
			}
		}
			return EnumActionResult.FAIL;		
	}
	
		
		
	@Override
    public List<Pair<Integer, String>> getVariants()
    {
        List<Pair<Integer, String>> ret = new ArrayList<Pair<Integer, String>>();
            ret.add(new ImmutablePair<Integer, String>(0, "type=normal"));
        return ret;
    }

}
