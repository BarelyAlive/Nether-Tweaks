package mod.nethertweaks.items;

import mod.nethertweaks.Constants;
import mod.nethertweaks.config.Config;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.text.translation.I18n;

public class ItemChunk extends Item {

	private Item results;
	private String ore_name = "";

	public ItemChunk() {
		super();
		setHasSubtypes(true);
	}

	public void setResult(final String ore_name, final Item stack)
	{
		results = stack;
		this.ore_name = ore_name;
	}

	public ItemStack getContainedBlock()
	{
		if (results == null)
			return ItemStack.EMPTY;
		return new ItemStack(results);
	}

	public String getOreName()
	{
		if (ore_name != "" && results == null)
			return "";
		return ore_name;
	}

	public ItemStack getResult()
	{
		return FurnaceRecipes.instance().getSmeltingResult(new ItemStack(results));
	}

	@Override
	public String getItemStackDisplayName(final ItemStack stack) {
		if (stack.isEmpty())
			return "";
		else if (!(stack.getItem() instanceof ItemChunk))
			return I18n.translateToLocal(getUnlocalizedNameInefficiently(stack) + ".name").trim();
		else
			return ((ItemChunk) stack.getItem()).getLocalizedName(stack);
	}

	public String getLocalizedName(final ItemStack stack)
	{
		String name = getResult().getDisplayName();
		String[] name_split = name.split(" ");
		if (name_split[name_split.length - 1].toLowerCase().equals("ingot"))
		{
			name = "";
			for(int i = 0; i < name_split.length - 1; i++)
			{
				name += name_split[i];
				name += " ";
			}
		} else
			name += " ";
		return name + this.getLocalizedName();
	}

	public String getLocalizedName()
	{
		return Config.dustBaseOreDictName;
	}

	@Override
	public String getUnlocalizedName(final ItemStack stack) {
		return "tile." + stack.getDisplayName().toLowerCase().replace(' ', '_');
	}

}
