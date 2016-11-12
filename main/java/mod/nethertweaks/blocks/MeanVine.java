package mod.nethertweaks.blocks;

import mod.nethertweaks.INames;
import mod.nethertweaks.NetherTweaksMod;
import net.minecraft.block.BlockVine;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class MeanVine extends BlockVine{
	
	public MeanVine(){
		setCreativeTab(NetherTweaksMod.tabNetherTweaksMod);
		setUnlocalizedName(INames.MEANVINE);
	}

	@Override
	public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face) {
		return false;
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}
	
	/**
     * Triggered whenever an entity collides with this block (enters into the block). Args: world, x, y, z, entity
     */
    public void onEntityCollidedWithBlock(World p_149670_1_, int p_149670_2_, int p_149670_3_, int p_149670_4_, Entity entity)
    {
    	if(!(entity instanceof EntityItem)){
        entity.attackEntityFrom(DamageSource.cactus, 1.0F);
    	}
    }
}
