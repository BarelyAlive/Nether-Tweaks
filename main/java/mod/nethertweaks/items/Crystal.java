package mod.nethertweaks.items;

import java.util.List;

import mod.nethertweaks.Constants;
import mod.nethertweaks.NetherTweaksMod;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Crystal extends Item
{
	public Crystal(final String name) {
		setCreativeTab(NetherTweaksMod.TAB);
		setRegistryName(Constants.MODID, name);
	}

	@Override
	public boolean hasEffect(final ItemStack stack) {
		return true;
	}

	@Override
	public void addInformation(final ItemStack stack, final World world, final List<String> tooltip, final ITooltipFlag flagIn)
	{
		String name = stack.getItem().getRegistryName().getResourcePath();

		switch (name) {
		case Constants.CRYSTAL_OF_LIGHT:
			tooltip.add("This crystal enchants water, making it resistant to temperature changes");
			break;
		default:
			break;
		}
	}
}
