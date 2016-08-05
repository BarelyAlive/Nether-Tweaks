package mod.nethertweaks.blocks;

import java.util.List;

import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.blocks.itemblocks.ItemBlockBasic;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockBasic extends Block{
		
	public BlockBasic(String unlocalizedName, Material material) {
        super(material);
        this.setUnlocalizedName(unlocalizedName);
        this.setCreativeTab(NetherTweaksMod.tabNetherTweaksMod);
        this.setSoundType(SoundType.STONE);
    }

    
    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
        for (int i = 0; i < 3; i ++) {
            list.add(new ItemStack(item, 1, i));
        }
    }
}
