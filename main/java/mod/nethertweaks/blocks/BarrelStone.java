package mod.nethertweaks.blocks;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import mod.nethertweaks.INames;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.blocks.tileentities.TileEntityBarrel;
import mod.sfhcore.proxy.SFHCoreClientProxy;

public class BarrelStone extends Barrel{

	public BarrelStone() {
		super(Material.ROCK);
		setCreativeTab(NetherTweaksMod.tabNetherTweaksMod);
		setResistance(30.0f);
		setHardness(2.0f);
		setUnlocalizedName(INames.BARRELSTONE);
		GameRegistry.registerTileEntity(TileEntityBarrel.class, INames.TEBARRELSTONE);
	}
}
