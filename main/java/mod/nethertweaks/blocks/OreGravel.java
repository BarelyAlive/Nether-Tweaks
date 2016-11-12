package mod.nethertweaks.blocks;

import java.util.List;

import mod.nethertweaks.INames;
import mod.nethertweaks.NetherTweaksMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class OreGravel extends BlockFalling{
		
	public OreGravel(Material material) {
        super(material);
        this.setCreativeTab(NetherTweaksMod.tabNetherTweaksMod);
        this.setResistance(2.0F);
        this.setHardness(0.4F);
    }

	/*
    @Override
    public void registerBlockIcons(IIconRegister iconRegister) {
    	
    	icons[0] = iconRegister.registerIcon(Constants.TEX + "OreCopperGravel");
		icons[1] = iconRegister.registerIcon(Constants.TEX + "OreTinGravel");
		icons[2] = iconRegister.registerIcon(Constants.TEX + "OreLeadGravel");
 		icons[3] = iconRegister.registerIcon(Constants.TEX + "OreNickelGravel");
		icons[4] = iconRegister.registerIcon(Constants.TEX + "OrePlatinumGravel");
        icons[5] = iconRegister.registerIcon(Constants.TEX + "OreSilverGravel");
        icons[6] = iconRegister.registerIcon(Constants.TEX + "OreUraniumGravel");
        icons[7] = iconRegister.registerIcon(Constants.TEX + "OreOsmiumGravel");
        icons[8] = iconRegister.registerIcon(Constants.TEX + "OreDraconiumGravel");      
        icons[9] = iconRegister.registerIcon(Constants.TEX + "OreSulfurGravel");
		icons[10] = iconRegister.registerIcon(Constants.TEX + "OreAluminumGravel");
 		icons[11] = iconRegister.registerIcon(Constants.TEX + "OreSiliconGravel");
		icons[12] = iconRegister.registerIcon(Constants.TEX + "OreAmberGravel");
        icons[13] = iconRegister.registerIcon(Constants.TEX + "OreCinnabarGravel");
        icons[14] = iconRegister.registerIcon(Constants.TEX + "OreCertusQuartzGravel");
        icons[15] = iconRegister.registerIcon(Constants.TEX + "OreSaltGravel");
    }
	*/
    
    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
        for (int i = 0; i < 16; i ++) {
            list.add(new ItemStack(item, 1, i));
        }
    }
}
