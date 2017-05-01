package mod.nethertweaks.items;

import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import mod.nethertweaks.NetherTweaksMod;
import mod.sfhcore.proxy.IVariantProvider;
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

public class ItemCactusGrabber extends ItemShears implements IVariantProvider{

	public ItemCactusGrabber(ToolMaterial material) {
		setCreativeTab(NetherTweaksMod.tabNetherTweaksMod);
		setUnlocalizedName("ItemCactusGrabber");
		setMaxStackSize(1);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn,
			EnumHand hand) {
		
		RayTraceResult rtr = new RayTraceResult(playerIn);
		
		Block block = worldIn.getBlockState(rtr.getBlockPos()).getBlock();
		if(block == Blocks.CACTUS || block == Blocks.MELON_BLOCK){
			worldIn.setBlockToAir(rtr.getBlockPos());
			playerIn.inventory.addItemStackToInventory(new ItemStack(block));
			
			if(!playerIn.capabilities.isCreativeMode)
				itemStackIn.damageItem(1, playerIn);

	        return new ActionResult(EnumActionResult.SUCCESS, itemStackIn);

		}else{
			return new ActionResult(EnumActionResult.FAIL, itemStackIn);
		}
	}
	
	@Override
    public List<Pair<Integer, String>> getVariants()
    {
        List<Pair<Integer, String>> ret = new ArrayList<Pair<Integer, String>>();
            ret.add(new ImmutablePair<Integer, String>(0, "type=normal"));
        return ret;
    }
}
