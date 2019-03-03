package mod.nethertweaks.items;

import java.util.List;

import mod.nethertweaks.Constants;
import mod.nethertweaks.NetherTweaksMod;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemBase extends Item{
	
	public ItemBase() {
        super();
        this.setHasSubtypes(true);
        this.setCreativeTab(NetherTweaksMod.tabNetherTweaksMod);
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		for (int i = 0; i < 20; i ++) {
	        items.add(items.get(i));
	    }
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
	    return this.getUnlocalizedName() + "_" + stack.getItemDamage();
	}
}
