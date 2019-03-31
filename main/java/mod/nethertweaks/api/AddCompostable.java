package mod.nethertweaks.api;

import java.lang.reflect.Method;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class AddCompostable {

	public static void register(Item item, int meta, float value)
	{

	    Class NTMCompostHandler = null;
	    Method register = null;
	    Object[] parameters = { item, meta, value };
	    try
	    {
	      NTMCompostHandler = Class.forName("mod.nethertweaks.handler.NTMCompostHandler");
	      if (NTMCompostHandler != null)
	      {
	        register = NTMCompostHandler.getDeclaredMethod("register", new Class[] { Item.class, Integer.TYPE, Float.TYPE });
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
