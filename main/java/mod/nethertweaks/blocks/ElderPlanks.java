package mod.nethertweaks.blocks;

import mod.nethertweaks.Constants;
import mod.nethertweaks.NetherTweaksMod;
import mod.sfhcore.blocks.Cube;
import net.minecraft.block.material.Material;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class ElderPlanks extends Cube
{
	public ElderPlanks()
	{
		super(Material.WOOD, 10.0F, 2.0F, NetherTweaksMod.TABNTM, new ResourceLocation(Constants.MODID, Constants.ELDER_PLANKS));
	}

	@Override
	public boolean isFlammable(final IBlockAccess world, final BlockPos pos, final EnumFacing face) {
		return false;
	}
}
