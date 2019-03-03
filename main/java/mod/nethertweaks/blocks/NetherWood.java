package mod.nethertweaks.blocks;

import java.util.List;

import mod.nethertweaks.INames;
import mod.nethertweaks.NetherTweaksMod;
import mod.sfhcore.blocks.Cube;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class NetherWood extends Cube {
	
	public static final String[] Wood = new String[] {"ForTre"};
    
	public NetherWood() {
		super(Material.WOOD, 2.0F, 10.0F);
        setCreativeTab(NetherTweaksMod.tabNetherTweaksMod);
	}
	
	@Override
	public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face) {
		return false;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tabs,
			List list) {
		for(int i = 0; i < Wood.length; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}
	
}
