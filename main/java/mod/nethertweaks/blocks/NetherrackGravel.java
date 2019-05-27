package mod.nethertweaks.blocks;

import mod.nethertweaks.INames;
import mod.nethertweaks.NetherTweaksMod;
import net.minecraft.block.BlockGravel;
import net.minecraft.block.SoundType;

public class NetherrackGravel extends BlockGravel{

	public NetherrackGravel()
	{
		setSoundType(SoundType.GROUND);
		setRegistryName(NetherTweaksMod.MODID, INames.NETHERRACKGRAVEL);
		setCreativeTab(NetherTweaksMod.TABNTM);
		setResistance(2.0f);
		setHardness(0.4f);
	}
}
