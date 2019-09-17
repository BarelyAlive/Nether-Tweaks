package mod.nethertweaks.items;

import mod.nethertweaks.Constants;
import mod.nethertweaks.NetherTweaksMod;
import mod.sfhcore.helper.NameHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ItemMesh extends Item{

	public ItemMesh(final String type) {
		super();
		this.setRegistryName(new ResourceLocation(NetherTweaksMod.MODID, type));
		setMaxStackSize(1);
		setCreativeTab(NetherTweaksMod.TABNTM);
	}

	@Override
	public int getItemEnchantability(final ItemStack stack)
	{
		switch(NameHelper.getName(stack))
		{
		case Constants.MESH_STRING:
			return 15;
		case Constants.MESH_FLINT:
			return 7;
		case Constants.MESH_IRON:
			return 14;
		case Constants.MESH_DIAMOND:
			return 10;
		default:
			return 0;
		}
	}

	@Override
	public String getUnlocalizedName(final ItemStack stack) {
		return "item." + getRegistryName().getResourcePath();

	}

	@Override
	public boolean isEnchantable(final ItemStack stack)
	{
		return true;
	}

	@Override
	public boolean isBookEnchantable(final ItemStack stack, final ItemStack book)
	{
		return stack.getCount() == 1;
	}
}
