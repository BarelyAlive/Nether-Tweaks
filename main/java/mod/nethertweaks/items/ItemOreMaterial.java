package mod.nethertweaks.items;

import java.util.List;

import mod.nethertweaks.Constants;
import mod.nethertweaks.NetherTweaksMod;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemOreMaterial extends Item{
	
	public ItemOreMaterial(String unlocalizedName) {
        super();
        this.setHasSubtypes(true);
        this.setUnlocalizedName(unlocalizedName);
        this.setCreativeTab(NetherTweaksMod.tabNetherTweaksMod);
	}
	
	/*
	@Override
	public void registerIcons(IIconRegister reg){
	    
	    icons[0] = reg.registerIcon(Constants.TEX + "ChunkCopper");
		icons[1] = reg.registerIcon(Constants.TEX + "ChunkTin");
		icons[2] = reg.registerIcon(Constants.TEX + "ChunkLead");
 		icons[3] = reg.registerIcon(Constants.TEX + "ChunkNickel");
		icons[4] = reg.registerIcon(Constants.TEX + "ChunkPlatinum");
        icons[5] = reg.registerIcon(Constants.TEX + "ChunkSilver");
        icons[6] = reg.registerIcon(Constants.TEX + "ChunkUranium");
        icons[7] = reg.registerIcon(Constants.TEX + "ChunkOsmium");
        icons[8] = reg.registerIcon(Constants.TEX + "ChunkDraconium");      
        icons[9] = reg.registerIcon(Constants.TEX + "ChunkSulfur");
		icons[10] = reg.registerIcon(Constants.TEX + "ChunkAluminum");
 		icons[11] = reg.registerIcon(Constants.TEX + "ChunkSilicon");
		icons[12] = reg.registerIcon(Constants.TEX + "ChunkAmber");
        icons[13] = reg.registerIcon(Constants.TEX + "ChunkCinnabar");
        icons[14] = reg.registerIcon(Constants.TEX + "ChunkCertusQuartz");
        icons[15] = reg.registerIcon(Constants.TEX + "ChunkSalt");
        icons[16] = reg.registerIcon(Constants.TEX + "ChunkSaltpeter");
        
        icons[17] = reg.registerIcon(Constants.TEX + "CrushedCopper");
		icons[18] = reg.registerIcon(Constants.TEX + "CrushedTin");
		icons[19] = reg.registerIcon(Constants.TEX + "CrushedLead");
 		icons[20] = reg.registerIcon(Constants.TEX + "CrushedNickel");
		icons[21] = reg.registerIcon(Constants.TEX + "CrushedPlatinum");
        icons[22] = reg.registerIcon(Constants.TEX + "CrushedSilver");
        icons[23] = reg.registerIcon(Constants.TEX + "CrushedUranium");
        icons[24] = reg.registerIcon(Constants.TEX + "CrushedOsmium");
        icons[25] = reg.registerIcon(Constants.TEX + "CrushedDraconium");      
        icons[26] = reg.registerIcon(Constants.TEX + "CrushedSulfur");
		icons[27] = reg.registerIcon(Constants.TEX + "CrushedAluminum");
 		icons[28] = reg.registerIcon(Constants.TEX + "CrushedSilicon");
		icons[29] = reg.registerIcon(Constants.TEX + "CrushedAmber");
        icons[30] = reg.registerIcon(Constants.TEX + "CrushedCinnabar");
        icons[31] = reg.registerIcon(Constants.TEX + "CrushedCertusQuartz");
        icons[32] = reg.registerIcon(Constants.TEX + "CrushedSalt");
        icons[33] = reg.registerIcon(Constants.TEX + "CrushedSaltpeter");
        
        icons[34] = reg.registerIcon(Constants.TEX + "PileCopper");
		icons[35] = reg.registerIcon(Constants.TEX + "PileTin");
		icons[36] = reg.registerIcon(Constants.TEX + "PileLead");
 		icons[37] = reg.registerIcon(Constants.TEX + "PileNickel");
		icons[38] = reg.registerIcon(Constants.TEX + "PilePlatinum");
        icons[39] = reg.registerIcon(Constants.TEX + "PileSilver");
        icons[40] = reg.registerIcon(Constants.TEX + "PileUranium");
        icons[41] = reg.registerIcon(Constants.TEX + "PileOsmium");
        icons[42] = reg.registerIcon(Constants.TEX + "PileDraconium");      
        icons[43] = reg.registerIcon(Constants.TEX + "PileSulfur");
		icons[44] = reg.registerIcon(Constants.TEX + "PileAluminum");
 		icons[45] = reg.registerIcon(Constants.TEX + "PileSilicon");
		icons[46] = reg.registerIcon(Constants.TEX + "PileAmber");
        icons[47] = reg.registerIcon(Constants.TEX + "PileCinnabar");
        icons[48] = reg.registerIcon(Constants.TEX + "PileCertusQuartz");
        icons[49] = reg.registerIcon(Constants.TEX + "PileSalt");
        icons[50] = reg.registerIcon(Constants.TEX + "PileSaltpeter");
	}
	*/

	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list) {
	    for (int i = 0; i < 51; i ++) {
	        list.add(new ItemStack(item, 1, i));
	    }
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
	    return this.getUnlocalizedName() + "_" + stack.getItemDamage();
	}
}
