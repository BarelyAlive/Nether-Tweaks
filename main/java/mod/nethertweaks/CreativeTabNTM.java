package mod.nethertweaks;

import javax.annotation.Nonnull;

import mod.nethertweaks.handler.ItemHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CreativeTabNTM extends CreativeTabs
{
	public CreativeTabNTM() {
		super("tab_nether_tweaks_mod");
	}

	@Override
    @SideOnly(Side.CLIENT)
	@Nonnull
	public ItemStack getTabIconItem() {
		return new ItemStack(ItemHandler.HELLFAYAH);
	}

	@Override
	public boolean hasSearchBar() {
		return true;
	}
}
