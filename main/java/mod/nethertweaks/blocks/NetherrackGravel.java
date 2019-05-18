package mod.nethertweaks.blocks;

import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.interfaces.INames;
import net.minecraft.block.BlockGravel;

public class NetherrackGravel extends BlockGravel{

	public NetherrackGravel()
	{
		setRegistryName(NetherTweaksMod.MODID, INames.NETHERRACKGRAVEL);
		setCreativeTab(NetherTweaksMod.TABNTM);
		setResistance(2.0f);
		setHardness(0.4f);
	}
}
