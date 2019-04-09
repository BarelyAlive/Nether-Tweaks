package mod.nethertweaks.blocks;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.blocks.tile.TileBarrel;
import mod.nethertweaks.blocks.tile.TileNetherrackFurnace;
import mod.nethertweaks.interfaces.INames;
import mod.sfhcore.handler.RegisterTileEntity;
import mod.sfhcore.proxy.SFHCoreClientProxy;

public class BarrelStone extends Barrel{

	public BarrelStone() {
		super(Material.ROCK);
		setCreativeTab(NetherTweaksMod.tabNTM);
		setHardness(4.0f);
		setUnlocalizedName(INames.BARRELSTONE);
		setRegistryName(NetherTweaksMod.MODID, INames.BARRELSTONE);
	}
}
