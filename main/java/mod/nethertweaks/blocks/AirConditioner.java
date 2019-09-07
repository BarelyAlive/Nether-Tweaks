package mod.nethertweaks.blocks;

import mod.nethertweaks.INames;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.blocks.tile.TileAirConditioner;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class AirConditioner extends Block{

	public AirConditioner() {
		super(Material.IRON);

		setRegistryName(NetherTweaksMod.MODID, INames.AIR_CONDITIONER);
	}

	@Override
	public TileEntity createTileEntity(final World world, final IBlockState state) {
		return new TileAirConditioner();
	}
}
