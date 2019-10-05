package mod.nethertweaks.blocks;

import mod.nethertweaks.Constants;
import net.minecraft.block.BlockGravel;
import net.minecraft.block.SoundType;

public class NetherrackGravel extends BlockGravel
{
	public NetherrackGravel()
	{
		setSoundType(SoundType.GROUND);
		setRegistryName(Constants.MODID, Constants.NETHERRACK_GRAVEL);
		setResistance(2.0f);
		setHardness(0.4f);
	}
}
