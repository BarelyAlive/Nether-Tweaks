package mod.nethertweaks.items;

import mod.nethertweaks.INames;
import mod.nethertweaks.NetherTweaksMod;
import mod.sfhcore.items.ItemThing;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class Seed extends ItemThing{
	
public Seed() {
		super(null, 64, NetherTweaksMod.tabNetherTweaksMod, false);
	}

@Override
public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn,
		EnumHand hand) {
	RayTraceResult rtr = new RayTraceResult(playerIn);
	Block block = worldIn.getBlockState(rtr.getBlockPos()).getBlock();
	BlockPos pos = rtr.getBlockPos();
	
	if(this.getUnlocalizedName() == INames.MUSHROOMSPORES){
	
			if(block == Blocks.DIRT || block == Blocks.GRASS){
	
				worldIn.setBlockState(pos, Blocks.MYCELIUM.getDefaultState());
			}
	
		}
	
		if(this.getUnlocalizedName() == INames.SEEDGRASS){
		
			if(block == Blocks.DIRT){
				worldIn.setBlockState(pos, Blocks.GRASS.getDefaultState());
			}
		}
		
		if(this.getUnlocalizedName() == "ItemCactusSeeds"){
			Block block1 = worldIn.getBlockState(pos.add(1, 1, 0)).getBlock();
			Block block2 = worldIn.getBlockState(pos.add(0, 1, 1)).getBlock();
			Block block3 = worldIn.getBlockState(pos.add(-1, 1, 0)).getBlock();
			Block block4 = worldIn.getBlockState(pos.add(0, 1, -1)).getBlock();
			Block block5 = worldIn.getBlockState(pos.add(0, 1, 0)).getBlock();
			if(block == Blocks.SAND){
				if(block1.equals(Blocks.AIR) && block2.equals(Blocks.AIR) && block3.equals(Blocks.AIR) && block4.equals(Blocks.AIR)
						&& block5.equals(Blocks.AIR)){
					worldIn.setBlockState(pos.add(0, 1, 0), Blocks.CACTUS.getDefaultState());
				}
			}
		}
	
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
}
}
