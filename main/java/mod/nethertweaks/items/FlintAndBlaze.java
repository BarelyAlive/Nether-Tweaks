package mod.nethertweaks.items;

import java.util.List;

import mod.nethertweaks.Constants;
import mod.nethertweaks.NetherTweaksMod;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class FlintAndBlaze extends ItemFlintAndSteel{

	public FlintAndBlaze()
	{
		setRegistryName(Constants.MODID, Constants.FLINT_N_BLAZE);
		setMaxStackSize(1);
		setMaxDamage(256);
		setCreativeTab(NetherTweaksMod.TABNTM);
	}

	@Override
	public void addInformation(final ItemStack stack, final World worldIn, final List<String> tooltip, final ITooltipFlag flagIn)
	{
		tooltip.add("Totally LIT!");
	}
}