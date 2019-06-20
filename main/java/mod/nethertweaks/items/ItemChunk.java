package mod.nethertweaks.items;

import java.util.*;
import java.util.Map.Entry;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import mod.nethertweaks.INames;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.config.Config;
import mod.sfhcore.proxy.IVariantProvider;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.*;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.translation.I18n;

public class ItemChunk extends Item implements IVariantProvider {

	private Map<String, Item> chunks = new HashMap<String, Item>();
	
	public ItemChunk() {
		super();
		setCreativeTab(NetherTweaksMod.TABNTM);
		setHasSubtypes(true);
	}
	
	public void add(String identifier, Item stack)
	{
		if (! chunks.containsKey(identifier))
		{
			chunks.put(identifier, stack);
		}
	}
	
	public int getMaxSubItems()
	{
		return chunks.size();
	}
	
	public ItemStack getContainedBlock(int i)
	{
		Collection<Item> entrys = chunks.values();
		Item[] entry_array = entrys.toArray(new Item[0]);
		if (entry_array.length <= i)
			return ItemStack.EMPTY;
		return new ItemStack(entry_array[i]);
	}
	
	public String getOreName(int i)
	{
		Set<String> entrys = chunks.keySet();
		String[] entry_array = entrys.toArray(new String[0]);
		if (entry_array.length <= i)
			return "";
		return entry_array[i];
	}
	
	public ItemStack getResult(int i)
	{
		return FurnaceRecipes.instance().getSmeltingResult(this.getContainedBlock(i));
	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (tab.equals(this.getCreativeTab())) {
			for (int i = 0; i < chunks.size(); i++)
				items.add(new ItemStack(this, 1, i));
		}
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
		String name = this.getResult(stack.getItemDamage()).getDisplayName();
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
	
	@Override
	public List<Pair<Integer, String>> getVariants() {
		List<Pair<Integer, String>> l = new ArrayList<Pair<Integer,String>>();
		int i = 0;
		for(Map.Entry<String, Item> entry : chunks.entrySet())
		{
			l.add(new ImmutablePair<Integer, String>(i, entry.getKey()));
			i++;
		}
		return l;
	}

}
