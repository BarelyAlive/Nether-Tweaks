package mod.nethertweaks.items;

import java.util.List;

import mod.nethertweaks.INames;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Crystal extends Item implements INames{
		
	public Crystal(String name) {
		setCreativeTab(TAB);
		setRegistryName(MODID, name);
	}
	
	@Override
	public boolean hasEffect(ItemStack stack) {
		return true;
	}
	
	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flagIn)
	{
		String name = stack.getItem().getRegistryName().getResourcePath();
		
		switch (name) {
		case CRYSTAL_OF_LIGHT:
			tooltip.add("This crystal enchants water, making it resistant to temperature changes");
			break;
		default:
			break;
		}
	}
}
