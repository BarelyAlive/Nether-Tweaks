package mod.nethertweaks.item;

import java.util.List;
import java.util.Objects;

import mod.nethertweaks.Constants;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class CrystalOfLight extends Item
{
	public CrystalOfLight() {}

	@Override
	public boolean hasEffect(final ItemStack stack) {
		return true;
	}

	@Override
	public void addInformation(final ItemStack stack, final World world, final List<String> tooltip, final ITooltipFlag flagIn)
	{
		String name = Objects.requireNonNull(stack.getItem().getRegistryName()).getResourcePath();

        if (Constants.CRYSTAL_OF_LIGHT.equals(name)) {
            tooltip.add("This crystal enchants water, making it resistant to temperature changes");
        }
	}
}
