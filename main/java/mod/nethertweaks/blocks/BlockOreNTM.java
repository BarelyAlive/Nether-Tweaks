/*
package mod.nethertweaks.blocks;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import mod.nethertweaks.INames;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.blocks.base.BlockEnum;
import mod.nethertweaks.blocks.base.BlockEnumFalling;
import mod.nethertweaks.blocks.enums.EnumBlockOreNTM;
import mod.sfhcore.proxy.IVariantProvider;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockOreNTM extends BlockEnumFalling<EnumBlockOreNTM> implements IVariantProvider{
		
	public BlockOreNTM(Material material) {
        super(material, EnumBlockOreNTM.class);
        this.setCreativeTab(NetherTweaksMod.tabNetherTweaksMod);
        this.setResistance(2.0F);
        this.setHardness(0.4F);
    }
    
	@Override
    public List<Pair<Integer, String>> getVariants()
    {
        List<Pair<Integer, String>> ret = new ArrayList<Pair<Integer, String>>();
        for (int i = 0; i < this.getTypes().length; i++)
            ret.add(new ImmutablePair<Integer, String>(i, "type=" + this.getTypes()[i]));
        return ret;
    }
}
*/
