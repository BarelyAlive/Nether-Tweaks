package mod.nethertweaks.item;

import java.util.Objects;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.text.translation.I18n;

public class ItemChunk extends Item {

	private Item results;
	private String ore_name = "";
	private String display_name = "";
	private int color = 0;

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
		if (!Objects.equals(ore_name, "") && results == null)
			return "";
		return ore_name;
	}

	public ItemStack getResult()
	{
		final ItemStack result = FurnaceRecipes.instance().getSmeltingResult(new ItemStack(results));
		return result.isEmpty() ? new ItemStack(results) : result;
	}

	public void setColor(final int color) {
		this.color = color;
	}

	public int getColor()
	{
		return color;
	}

	public void setDisplayName(final String name)
	{
		display_name = name;
	}

	private String getDisplayName()
	{
		return display_name;
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
		if (display_name == "")
		{
			String name = getResult().getDisplayName();
			final String[] name_split = name.split(" ");
			if (name_split[name_split.length - 1].toLowerCase().equals("ingot"))
			{
	            final StringBuilder nameBuilder = new StringBuilder();
	            for(int i = 0; i < name_split.length - 1; i++)
				{
					nameBuilder.append(name_split[i]);
					nameBuilder.append(" ");
				}
	            name = nameBuilder.toString();
	        } else
				name += " ";
			return name + this.getLocalizedName();
		} else
			return display_name;
	}

	public String getLocalizedName()
	{
		return "Chunk";
	}

	@Override
	public String getUnlocalizedName(final ItemStack stack) {
		return "tile." + stack.getDisplayName().toLowerCase().replace(' ', '_');
	}
}
