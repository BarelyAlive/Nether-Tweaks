package mod.nethertweaks.api;

import java.lang.reflect.Method;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class AddSievable {

	public static void register(Block source, int sourceMeta, Item output, int outputMeta, int rarity)
	{

	    Class NTMCompostHandler = null;
	    Method register = null;
	    Object[] parameters = { source, sourceMeta, output, outputMeta, rarity };
	    try
	    {
	      NTMCompostHandler = Class.forName("mod.nethertweaks.handler.NTMSieveHandler");
	      if (NTMCompostHandler != null)
	      {
	        register = NTMCompostHandler.getDeclaredMethod("register", new Class[] { Block.class, Integer.TYPE, Item.class, Integer.TYPE, Integer.TYPE });
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
