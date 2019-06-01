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

	private Map<String, ItemStack> chunks = new HashMap<String, ItemStack>();
	
	public ItemChunk() {
		super();
		setCreativeTab(NetherTweaksMod.TABNTM);
		setHasSubtypes(true);
	}
	
	public void add(String identifier, ItemStack stack)
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
	
	public ItemStack getResult(int i)
	{
		Collection<ItemStack> entrys = chunks.values();
		ItemStack[] entry_array = entrys.toArray(new ItemStack[0]);
		if (entry_array.length <= i)
			return ItemStack.EMPTY;
		return FurnaceRecipes.instance().getSmeltingResult(entry_array[i]);
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
		for(Map.Entry<String, ItemStack> entry : chunks.entrySet())
		{
			l.add(new ImmutablePair<Integer, String>(i, entry.getKey()));
			i++;
		}
		return l;
	}

}
