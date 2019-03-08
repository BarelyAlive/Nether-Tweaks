package mod.nethertweaks.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import mod.nethertweaks.Konstanten;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.blocks.enums.EnumBlockBasic;
import mod.nethertweaks.blocks.enums.EnumBlockOreNTM;
import mod.sfhcore.blocks.base.BlockEnum;
import mod.sfhcore.proxy.IVariantProvider;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockBasic extends BlockEnum<EnumBlockBasic> implements IVariantProvider{
		
	public BlockBasic(String unlocalizedName, Material material) {
        super(material, EnumBlockBasic.class);
        setResistance(17.5F);
        setHardness(3.5F);
        this.setUnlocalizedName(unlocalizedName);
        this.setCreativeTab(NetherTweaksMod.tabNetherTweaksMod);
        this.setSoundType(SoundType.STONE);
    }
	
	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		if(this.getMetaFromState(state) == 0)
		{
			List<ItemStack> ret = new ArrayList<ItemStack>();
			ret.add(new ItemStack(Konstanten.HELLFAYAH.getItem(), 2, Konstanten.HELLFAYAH.getItemDamage()));
			return ret;
		}
		
		return super.getDrops(world, pos, state, fortune);
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
