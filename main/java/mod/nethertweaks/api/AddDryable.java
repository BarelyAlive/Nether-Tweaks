package mod.nethertweaks.api;

import java.lang.reflect.Method;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class AddDryable {

	public static void register(Item item, int meta, int value)
	{

	    Class NTMDryHandler = null;
	    Method register = null;
	    Object[] parameters = { item, meta, value };
	    try
	    {
	      NTMDryHandler = Class.forName("mod.nethertweaks.handler.NTMDryHandler");
	      if (NTMDryHandler != null)
	      {
	        register = NTMDryHandler.getDeclaredMethod("register", new Class[] { Item.class, Integer.TYPE, Integer.TYPE });
	        register.setAccessible(true);
	      }
	      if (register != null) {
	        register.invoke(null, parameters);
	      }
	    }
	    catch (Exception ex)
	    {
	      System.out.println("Failed!");
	    }
	}
}
