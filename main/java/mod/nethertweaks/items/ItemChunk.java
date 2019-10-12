package mod.nethertweaks.items;

import mod.nethertweaks.config.Config;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.text.translation.I18n;

import java.util.Objects;

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
		ItemStack result = FurnaceRecipes.instance().getSmeltingResult(new ItemStack(results));
		return (result.isEmpty() ? new ItemStack(results) : result);
	}
	
	public void setColor(int color) {
		this.color = color;
	}
	
	public int getColor()
	{
		return color;
	}
	
	public void setDisplayName(String name)
	{
		this.display_name = name;
	}
	
	private String getDisplayName()
	{
		return this.display_name;
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
		if (this.display_name == "")
		{
			String name = this.getResult().getDisplayName();
			String[] name_split = name.split(" ");
			if (name_split[(name_split.length - 1)].toLowerCase().equals("ingot"))
			{
	            StringBuilder nameBuilder = new StringBuilder();
	            for(int i = 0; i < name_split.length - 1; i++)
				{
					nameBuilder.append(name_split[i]);
					nameBuilder.append(" ");
				}
	            name = nameBuilder.toString();
	        } else
				name += " ";
			return name + this.getLocalizedName();
		}
		else
		{
			return this.display_name;
		}
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
