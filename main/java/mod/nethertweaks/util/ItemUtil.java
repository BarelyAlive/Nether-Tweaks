package mod.nethertweaks.util;

import javax.annotation.Nullable;

import mod.nethertweaks.interfaces.IHammer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemUtil extends mod.sfhcore.util.ItemUtil
{
	public static boolean isHammer(@Nullable ItemStack stack)
	{
	    if (stack == null)
	        return false;
	
	    if (stack.getItem() == Items.AIR)
	        return false;
	
	    if (stack.getItem() instanceof IHammer)
	        return ((IHammer) stack.getItem()).isHammer(stack);
	
	    return false;
	}
	
	public boolean isHammer(Item item)
	{
	    return isHammer(new ItemStack(item));
	}
}
