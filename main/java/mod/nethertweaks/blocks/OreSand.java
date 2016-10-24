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

public class OreSand extends BlockFalling{
		
	public OreSand(Material material) {
        super(material);
        this.setCreativeTab(NetherTweaksMod.tabNetherTweaksMod);
        this.setResistance(2.0F);
        this.setHardness(0.4F);
    }

	/*
    @Override
    public void registerBlockIcons(IIconRegister iconRegister) {
    	
    	icons[0] = iconRegister.registerIcon(Constants.TEX + "OreCopperSand");
		icons[1] = iconRegister.registerIcon(Constants.TEX + "OreTinSand");
		icons[2] = iconRegister.registerIcon(Constants.TEX + "OreLeadSand");
 		icons[3] = iconRegister.registerIcon(Constants.TEX + "OreNickelSand");
		icons[4] = iconRegister.registerIcon(Constants.TEX + "OrePlatinumSand");
        icons[5] = iconRegister.registerIcon(Constants.TEX + "OreSilverSand");
        icons[6] = iconRegister.registerIcon(Constants.TEX + "OreUraniumSand");
        icons[7] = iconRegister.registerIcon(Constants.TEX + "OreOsmiumSand");
        icons[8] = iconRegister.registerIcon(Constants.TEX + "OreDraconiumSand");      
        icons[9] = iconRegister.registerIcon(Constants.TEX + "OreSulfurSand");
		icons[10] = iconRegister.registerIcon(Constants.TEX + "OreAluminumSand");
 		icons[11] = iconRegister.registerIcon(Constants.TEX + "OreSiliconSand");
		icons[12] = iconRegister.registerIcon(Constants.TEX + "OreAmberSand");
        icons[13] = iconRegister.registerIcon(Constants.TEX + "OreCinnabarSand");
        icons[14] = iconRegister.registerIcon(Constants.TEX + "OreCertusQuartzSand");
        icons[15] = iconRegister.registerIcon(Constants.TEX + "OreSaltSand");
    }
	*/
    
    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
        for (int i = 0; i < 16; i ++) {
            list.add(new ItemStack(item, 1, i));
        }
    }
    
    @Override
    public String getUnlocalizedName() {
    	return INames.ORESAND + "_" + this.getMetaFromState(getDefaultState());
    }
}
