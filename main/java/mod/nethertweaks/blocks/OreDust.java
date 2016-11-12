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

public class OreDust extends BlockFalling{
	
	
	public OreDust(Material material) {
        super(material);
        this.setCreativeTab(NetherTweaksMod.tabNetherTweaksMod);
        this.setResistance(2.0F);
        this.setHardness(0.4F);
    }

	/*
    @Override
    public void registerBlockIcons(IIconRegister iconRegister) {
    	
    	icons[0] = iconRegister.registerIcon(Constants.TEX + "OreCopperDust");
		icons[1] = iconRegister.registerIcon(Constants.TEX + "OreTinDust");
		icons[2] = iconRegister.registerIcon(Constants.TEX + "OreLeadDust");
 		icons[3] = iconRegister.registerIcon(Constants.TEX + "OreNickelDust");
		icons[4] = iconRegister.registerIcon(Constants.TEX + "OrePlatinumDust");
        icons[5] = iconRegister.registerIcon(Constants.TEX + "OreSilverDust");
        icons[6] = iconRegister.registerIcon(Constants.TEX + "OreUraniumDust");
        icons[7] = iconRegister.registerIcon(Constants.TEX + "OreOsmiumDust");
        icons[8] = iconRegister.registerIcon(Constants.TEX + "OreDraconiumDust");      
        icons[9] = iconRegister.registerIcon(Constants.TEX + "OreSulfurDust");
		icons[10] = iconRegister.registerIcon(Constants.TEX + "OreAluminumDust");
 		icons[11] = iconRegister.registerIcon(Constants.TEX + "OreSiliconDust");
		icons[12] = iconRegister.registerIcon(Constants.TEX + "OreAmberDust");
        icons[13] = iconRegister.registerIcon(Constants.TEX + "OreCinnabarDust");
        icons[14] = iconRegister.registerIcon(Constants.TEX + "OreCertusQuartzDust");
        icons[15] = iconRegister.registerIcon(Constants.TEX + "OreSaltDust");
    }
	*/
    
    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
        for (int i = 0; i < 16; i ++) {
            list.add(new ItemStack(item, 1, i));
        }
    }
}
