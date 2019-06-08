package mod.nethertweaks;

import mod.nethertweaks.handler.BlockHandler;
import mod.nethertweaks.handler.ItemHandler;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;

public class Konstanten
{	
	//itemBase
	public static final ItemStack ENDBOX = new ItemStack(ItemHandler.ITEM_BASE, 1, 0);
	public static final ItemStack PORTAL_CORE = new ItemStack(ItemHandler.ITEM_BASE, 1, 1);
	public static final ItemStack ENDER_INFUSED_FRAME = new ItemStack(ItemHandler.ITEM_BASE, 1, 2);
	public static final ItemStack HELLFAYAH = new ItemStack(ItemHandler.ITEM_BASE, 1, 3);
	public static final ItemStack POWDER_OF_LIGHT = new ItemStack(ItemHandler.ITEM_BASE, 1, 4);
	public static final ItemStack SALT = new ItemStack(ItemHandler.ITEM_BASE, 1, 5);
	public static final ItemStack STONE_BAR = new ItemStack(ItemHandler.ITEM_BASE, 1, 6);
	public static final ItemStack STRING = new ItemStack(ItemHandler.ITEM_BASE, 1, 7);
	public static final ItemStack PORCELAIN_CLAY = new ItemStack(ItemHandler.ITEM_BASE, 1, 8);
	
	//Block BAsic
    public static final ItemStack HELLFAYAH_ORE = new ItemStack(BlockHandler.BLOCK_BASIC, 1, 0);
    public static final ItemStack HELLFAYAH_BLOCK = new ItemStack(BlockHandler.BLOCK_BASIC, 1, 1);
    public static final ItemStack SALTB_LOCK = new ItemStack(BlockHandler.BLOCK_BASIC, 1, 2);
    
    public static final ItemStack CRYSTAL_LIGHT = new ItemStack(ItemHandler.CRYSTAL, 1, 0);
    public static final ItemStack CRYSTAL_ENDER = new ItemStack(ItemHandler.CRYSTAL, 1, 1);
    
    public static final ItemStack MUSHROOM_SPORES = new ItemStack(ItemHandler.SEED, 1, 0);
    public static final ItemStack SEED_GRASS = new ItemStack(ItemHandler.SEED, 1, 1);
    public static final ItemStack SEED_CACTUS = new ItemStack(ItemHandler.SEED, 1, 2);
}
