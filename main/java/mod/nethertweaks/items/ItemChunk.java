package mod.nethertweaks.items;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.config.Config;
import mod.sfhcore.proxy.IVariantProvider;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.translation.I18n;

public class ItemChunk extends Item {

	private Item results;
	private String ore_name = "";
	
	public ItemChunk() {
		super();
		setCreativeTab(NetherTweaksMod.TABNTM);
		setHasSubtypes(true);
	}
	
	public void setResult(String ore_name, Item stack)
	{
			results = stack;
			this.ore_name = ore_name;
	}
	
	public ItemStack getContainedBlock()
	{
		if (results == null)
			return ItemStack.EMPTY;
		return new ItemStack(this.results);
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
	public String getItemStackDisplayName(ItemStack stack) {
		if (stack.isEmpty())
		{
			return "";
		}
		else if (!(stack.getItem() instanceof ItemChunk))
		{
			return I18n.translateToLocal(this.getUnlocalizedNameInefficiently(stack) + ".name").trim();
		}
		else
		{
			return ((ItemChunk) stack.getItem()).getLocalizedName(stack);
		}
	}

	public String getLocalizedName(ItemStack stack)
	{
		String name = this.getResult().getDisplayName();
		String[] name_split = name.split(" ");
		if (name_split[(name_split.length - 1)].toLowerCase().equals("ingot"))
		{
			name = "";
			for(int i = 0; i < (name_split.length - 1); i++)
			{
				name += name_split[i];
				name += " ";
			}
		}
		else
		{
			name += " ";
		}
		return name + this.getLocalizedName();
	}
	
	public String getLocalizedName()
	{
		return Config.dustBaseOreDictName;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "tile." + stack.getDisplayName().toLowerCase().replace(' ', '_');
	}
	
}
