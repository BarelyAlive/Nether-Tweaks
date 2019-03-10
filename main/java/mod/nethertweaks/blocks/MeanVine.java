package mod.nethertweaks.blocks;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import mod.nethertweaks.INames;
import mod.nethertweaks.NetherTweaksMod;
import mod.sfhcore.proxy.IVariantProvider;
import net.minecraft.block.BlockVine;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class MeanVine extends BlockVine implements IVariantProvider{
	
	public MeanVine(){
		setCreativeTab(NetherTweaksMod.tabNetherTweaksMod);
		setUnlocalizedName(INames.MEANVINE);
	}

	@Override
	public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face) {
		return false;
	}
	
	/**
     * Triggered whenever an entity collides with this block (enters into the block). Args: world, x, y, z, entity
     */
    public void onEntityCollidedWithBlock(World p_149670_1_, int p_149670_2_, int p_149670_3_, int p_149670_4_, Entity entity)
    {
    	if(!(entity instanceof EntityItem)){
        entity.attackEntityFrom(DamageSource.CACTUS, 1.0F);
    	}
    }
    
    public List<Pair<Integer, String>> getVariants()
    {
        List<Pair<Integer, String>> ret = new ArrayList<Pair<Integer, String>>();        
        ret.add(new ImmutablePair<Integer, String>(0, "east=false,north=false,south=false,up=false,west=false"));
        ret.add(new ImmutablePair<Integer, String>(0, "east=false,north=false,south=true,up=false,west=false"));
        ret.add(new ImmutablePair<Integer, String>(0, "east=false,north=false,south=false,up=false,west=true"));
        ret.add(new ImmutablePair<Integer, String>(0, "east=false,north=true,south=false,up=false,west=false"));
        ret.add(new ImmutablePair<Integer, String>(0, "east=true,north=false,south=false,up=false,west=false"));
        ret.add(new ImmutablePair<Integer, String>(0, "east=true,north=true,south=false,up=false,west=false"));
        ret.add(new ImmutablePair<Integer, String>(0, "east=true,north=false,south=true,up=false,west=false"));
        ret.add(new ImmutablePair<Integer, String>(0, "east=false,north=false,south=true,up=false,west=true"));
        ret.add(new ImmutablePair<Integer, String>(0, "east=false,north=true,south=false,up=false,west=true"));
        ret.add(new ImmutablePair<Integer, String>(0, "east=true,north=false,south=false,up=false,west=true"));
        ret.add(new ImmutablePair<Integer, String>(0, "east=false,north=true,south=true,up=false,west=false"));
        ret.add(new ImmutablePair<Integer, String>(0, "east=true,north=true,south=true,up=false,west=false"));
        ret.add(new ImmutablePair<Integer, String>(0, "east=true,north=false,south=true,up=false,west=true"));
        ret.add(new ImmutablePair<Integer, String>(0, "east=false,north=true,south=true,up=false,west=true"));
        ret.add(new ImmutablePair<Integer, String>(0, "east=true,north=true,south=false,up=false,west=true"));
        ret.add(new ImmutablePair<Integer, String>(0, "east=true,north=true,south=true,up=false,west=true"));
        ret.add(new ImmutablePair<Integer, String>(0, "east=false,north=false,south=false,up=true,west=false"));
        ret.add(new ImmutablePair<Integer, String>(0, "east=false,north=false,south=true,up=true,west=false"));
        ret.add(new ImmutablePair<Integer, String>(0, "east=false,north=false,south=false,up=true,west=true"));
        ret.add(new ImmutablePair<Integer, String>(0, "east=false,north=true,south=false,up=true,west=false"));
        ret.add(new ImmutablePair<Integer, String>(0, "east=true,north=false,south=false,up=true,west=false"));
        ret.add(new ImmutablePair<Integer, String>(0, "east=true,north=true,south=false,up=true,west=false"));
        ret.add(new ImmutablePair<Integer, String>(0, "east=true,north=false,south=true,up=true,west=false"));
        ret.add(new ImmutablePair<Integer, String>(0, "east=false,north=false,south=true,up=true,west=true"));
        ret.add(new ImmutablePair<Integer, String>(0, "east=false,north=true,south=false,up=true,west=true"));
        ret.add(new ImmutablePair<Integer, String>(0, "east=true,north=false,south=false,up=true,west=true"));
        ret.add(new ImmutablePair<Integer, String>(0, "east=false,north=true,south=true,up=true,west=false"));
        ret.add(new ImmutablePair<Integer, String>(0, "east=true,north=true,south=true,up=true,west=false"));
        ret.add(new ImmutablePair<Integer, String>(0, "east=true,north=false,south=true,up=true,west=true"));
        ret.add(new ImmutablePair<Integer, String>(0, "east=false,north=true,south=true,up=true,west=true"));
        ret.add(new ImmutablePair<Integer, String>(0, "east=true,north=true,south=false,up=true,west=true"));
        ret.add(new ImmutablePair<Integer, String>(0, "east=true,north=true,south=true,up=true,west=true"));
        
        return ret;
    }
}
