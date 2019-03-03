package mod.nethertweaks.blocks;
 
import java.util.List;

import mod.nethertweaks.INames;
import mod.nethertweaks.NetherTweaksMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
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
 
public class NetherLog extends BlockLog {
     
    public static final String[] Logs = new String[] {"ForTre"};
    
    public NetherLog() {
    	setCreativeTab(NetherTweaksMod.tabNetherTweaksMod);
    	setLightLevel(0.0F);
    	setSoundType(SoundType.WOOD);
    	setUnlocalizedName(INames.NETHERLOG);
	}
     
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tabs,
            List list) {
        for(int i = 0; i < Logs.length; i++) {
            list.add(new ItemStack(item, 1, i));
        }
    }
    
    @Override
    public boolean isWood(IBlockAccess world, BlockPos pos) {
    	return true;
    }
    
    @Override
    public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face) {
    	return false;
    }
     
}