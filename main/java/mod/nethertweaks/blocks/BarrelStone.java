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
import mod.nethertweaks.blocks.tileentities.TileEntityNetherrackFurnace;
import mod.sfhcore.handler.RegisterTileEntity;
import mod.sfhcore.proxy.SFHCoreClientProxy;

public class BarrelStone extends Barrel{

	public BarrelStone() {
		super(Material.ROCK);
		setCreativeTab(NetherTweaksMod.tabNTM);
		setHardness(4.0f);
		//setBlockBounds(0.1F, 0.0F, 0.1F, 0.9F, 1.0F, 0.9F);
		setUnlocalizedName(INames.BARRELSTONE);
		setRegistryName("nethertweaksmod", INames.BARRELSTONE);
	}
}
