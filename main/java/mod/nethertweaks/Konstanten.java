package mod.nethertweaks;

import mod.nethertweaks.handler.BlockHandler;
import mod.nethertweaks.handler.ItemHandler;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;

public class Konstanten
{	
	//itemBase
	public static final ItemStack ENDBOX = new ItemStack(ItemHandler.ITEMBASE, 1, 0);
	public static final ItemStack PORTALCORE = new ItemStack(ItemHandler.ITEMBASE, 1, 1);
	public static final ItemStack ENDERINFUSEDFRAME = new ItemStack(ItemHandler.ITEMBASE, 1, 2);
	public static final ItemStack HELLFAYAH = new ItemStack(ItemHandler.ITEMBASE, 1, 3);
	public static final ItemStack POWDEROFLIGHT = new ItemStack(ItemHandler.ITEMBASE, 1, 4);
	public static final ItemStack SALT = new ItemStack(ItemHandler.ITEMBASE, 1, 5);
	public static final ItemStack STONEBAR = new ItemStack(ItemHandler.ITEMBASE, 1, 6);
	public static final ItemStack STRING = new ItemStack(ItemHandler.ITEMBASE, 1, 7);
	
	//Block BAsic
    public static final ItemStack HELLFAYAHORE = new ItemStack(BlockHandler.BLOCKBASIC, 1, 0);
    public static final ItemStack HELLFAYAHBLOCK = new ItemStack(BlockHandler.BLOCKBASIC, 1, 1);
    public static final ItemStack SALTBLOCK = new ItemStack(BlockHandler.BLOCKBASIC, 1, 2);
}
