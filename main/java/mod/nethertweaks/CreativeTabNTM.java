package mod.nethertweaks;

import javax.annotation.Nonnull;

import mod.nethertweaks.handler.BlockHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

class CreativeTabNTM extends CreativeTabs
{
	public CreativeTabNTM() {
		super("tab_nether_tweaks_mod");
	}

	@Override
	@SideOnly(Side.CLIENT)
	@Nonnull
	public ItemStack getTabIconItem() {
		return new ItemStack(BlockHandler.CONDENSER);
	}
}
