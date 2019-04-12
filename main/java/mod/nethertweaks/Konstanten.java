package mod.nethertweaks;

import mod.nethertweaks.handler.BlockHandler;
import mod.nethertweaks.handler.ItemHandler;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class Konstanten {
	
	//itemBase
	public static final ItemStack GOLDCHUNK = new ItemStack(ItemHandler.ITEMBASE, 1, 0);
	public static final ItemStack IRONCHUNK = new ItemStack(ItemHandler.ITEMBASE, 1, 1);
	public static final ItemStack CRUSHEDGOLD = new ItemStack(ItemHandler.ITEMBASE, 1, 2);
	public static final ItemStack CRUSHEDIRON = new ItemStack(ItemHandler.ITEMBASE, 1, 3);
	public static final ItemStack ENDBOX = new ItemStack(ItemHandler.ITEMBASE, 1, 4);
	public static final ItemStack PORTALCORE = new ItemStack(ItemHandler.ITEMBASE, 1, 5);
	public static final ItemStack ENDERINFUSEDFRAME = new ItemStack(ItemHandler.ITEMBASE, 1, 6);
	public static final ItemStack GOLDPILE = new ItemStack(ItemHandler.ITEMBASE, 1, 7);
	public static final ItemStack HELLFAYAH = new ItemStack(ItemHandler.ITEMBASE, 1, 8);
	public static final ItemStack POWDEROFLIGHT = new ItemStack(ItemHandler.ITEMBASE, 1, 9);
	public static final ItemStack SALT = new ItemStack(ItemHandler.ITEMBASE, 1, 10);
	public static final ItemStack IRONPILE = new ItemStack(ItemHandler.ITEMBASE, 1, 11);
	public static final ItemStack STONEBAR = new ItemStack(ItemHandler.ITEMBASE, 1, 12);
	
	
	//ItemStack Ore Material
	public static final ItemStack CHUNKCOPPER = new ItemStack(ItemHandler.OREMATERIAL, 1, 0);
	public static final ItemStack CHUNKTIN = new ItemStack(ItemHandler.OREMATERIAL, 1, 1);
	public static final ItemStack CHUNKLEAD = new ItemStack(ItemHandler.OREMATERIAL, 1, 2);
	public static final ItemStack CHUNKNICKEL = new ItemStack(ItemHandler.OREMATERIAL, 1, 3);
	public static final ItemStack CHUNKPLATINUM = new ItemStack(ItemHandler.OREMATERIAL, 1, 4);
	public static final ItemStack CHUNKSILVER = new ItemStack(ItemHandler.OREMATERIAL, 1, 5);
	public static final ItemStack CHUNKURANIUM = new ItemStack(ItemHandler.OREMATERIAL, 1, 6);
	public static final ItemStack CHUNKOSMIUM = new ItemStack(ItemHandler.OREMATERIAL, 1, 7);
	public static final ItemStack CHUNKDRACONIUM = new ItemStack(ItemHandler.OREMATERIAL, 1, 8);
	public static final ItemStack CHUNKSULFUR = new ItemStack(ItemHandler.OREMATERIAL, 1, 9);
	public static final ItemStack CHUNKALUMINUM = new ItemStack(ItemHandler.OREMATERIAL, 1, 10);
	public static final ItemStack CHUNKSILICON = new ItemStack(ItemHandler.OREMATERIAL, 1, 11);
	public static final ItemStack CHUNKAMBER = new ItemStack(ItemHandler.OREMATERIAL, 1, 12);
	public static final ItemStack CHUNKCINNABAR = new ItemStack(ItemHandler.OREMATERIAL, 1, 13);
	public static final ItemStack CHUNKCERTUSQUARTZ = new ItemStack(ItemHandler.OREMATERIAL, 1, 14);
	public static final ItemStack CHUNKSALT = new ItemStack(ItemHandler.OREMATERIAL, 1,15);
	
	public static final ItemStack CRUSHEDCOPPER = new ItemStack(ItemHandler.OREMATERIAL, 1, 16);
	public static final ItemStack CRUSHEDTIN = new ItemStack(ItemHandler.OREMATERIAL, 1, 17);
	public static final ItemStack CRUSHEDLEAD = new ItemStack(ItemHandler.OREMATERIAL, 1, 18);
	public static final ItemStack CRUSHEDNICKEL = new ItemStack(ItemHandler.OREMATERIAL, 1, 19);
	public static final ItemStack CRUSHEDPLATINUM = new ItemStack(ItemHandler.OREMATERIAL, 1, 20);
	public static final ItemStack CRUSHEDSILVER = new ItemStack(ItemHandler.OREMATERIAL, 1, 21);
	public static final ItemStack CRUSHEDURANIUM = new ItemStack(ItemHandler.OREMATERIAL, 1, 22);
	public static final ItemStack CRUSHEDOSMIUM = new ItemStack(ItemHandler.OREMATERIAL, 1, 23);
	public static final ItemStack CRUSHEDDRACONIUM = new ItemStack(ItemHandler.OREMATERIAL, 1, 24);
	public static final ItemStack CRUSHEDSULFUR = new ItemStack(ItemHandler.OREMATERIAL, 1, 25);
	public static final ItemStack CRUSHEDALUMINUM = new ItemStack(ItemHandler.OREMATERIAL, 1, 26);
	public static final ItemStack CRUSHEDSILICON = new ItemStack(ItemHandler.OREMATERIAL, 1, 27);
	public static final ItemStack CRUSHEDAMBER = new ItemStack(ItemHandler.OREMATERIAL, 1, 28);
	public static final ItemStack CRUSHEDCINNABAR = new ItemStack(ItemHandler.OREMATERIAL, 1, 29);
	public static final ItemStack CRUSHEDCERTUSQUARTZ = new ItemStack(ItemHandler.OREMATERIAL, 1, 30);
	public static final ItemStack CRUSHEDSALT = new ItemStack(ItemHandler.OREMATERIAL, 1, 32);
	
	public static final ItemStack PILECOPPER = new ItemStack(ItemHandler.OREMATERIAL, 1, 33);
	public static final ItemStack PILETIN = new ItemStack(ItemHandler.OREMATERIAL, 1, 34);
	public static final ItemStack PILELEAD = new ItemStack(ItemHandler.OREMATERIAL, 1, 35);
	public static final ItemStack PILENICKEL = new ItemStack(ItemHandler.OREMATERIAL, 1, 36);
	public static final ItemStack PILEPLATINUM = new ItemStack(ItemHandler.OREMATERIAL, 1, 37);
	public static final ItemStack PILESILVER = new ItemStack(ItemHandler.OREMATERIAL, 1, 38);
	public static final ItemStack PILEURANIUM = new ItemStack(ItemHandler.OREMATERIAL, 1, 39);
	public static final ItemStack PILEOSMIUM = new ItemStack(ItemHandler.OREMATERIAL, 1, 40);
	public static final ItemStack PILEDRACONIUM = new ItemStack(ItemHandler.OREMATERIAL, 1, 41);
	public static final ItemStack PILESULFUR = new ItemStack(ItemHandler.OREMATERIAL, 1, 42);
	public static final ItemStack PILEALUMINUM = new ItemStack(ItemHandler.OREMATERIAL, 1, 43);
	public static final ItemStack PILESILICON = new ItemStack(ItemHandler.OREMATERIAL, 1, 44);
	public static final ItemStack PILEAMBER = new ItemStack(ItemHandler.OREMATERIAL, 1, 45);
	public static final ItemStack PILECINNABAR = new ItemStack(ItemHandler.OREMATERIAL, 1, 46);
	public static final ItemStack PILECERTUSQUARTZ = new ItemStack(ItemHandler.OREMATERIAL, 1, 47);
	public static final ItemStack PILESALT = new ItemStack(ItemHandler.OREMATERIAL, 1, 48);
	
	//Block BAsic
    public static final ItemStack HELLFAYAHORE = new ItemStack(BlockHandler.BLOCKBASIC, 1, 0);
    public static final ItemStack HELLFAYAHBLOCK = new ItemStack(BlockHandler.BLOCKBASIC, 1, 1);
    public static final ItemStack SALTBLOCK = new ItemStack(BlockHandler.BLOCKBASIC, 1, 2);
    
    //Ore blocks
    public static final ItemStack OREGRAVELCOPPER = new ItemStack(BlockHandler.OREGRAVEL, 1, 0);
	public static final ItemStack OREGRAVELTIN = new ItemStack(BlockHandler.OREGRAVEL, 1, 1);
	public static final ItemStack OREGRAVELLEAD = new ItemStack(BlockHandler.OREGRAVEL, 1, 2);
	public static final ItemStack OREGRAVELNICKEL = new ItemStack(BlockHandler.OREGRAVEL, 1, 3);
	public static final ItemStack OREGRAVELPLATINUM = new ItemStack(BlockHandler.OREGRAVEL, 1, 4);
	public static final ItemStack OREGRAVELSILVER = new ItemStack(BlockHandler.OREGRAVEL, 1, 5);
	public static final ItemStack OREGRAVELURANIUM = new ItemStack(BlockHandler.OREGRAVEL, 1, 6);
	public static final ItemStack OREGRAVELOSMIUM = new ItemStack(BlockHandler.OREGRAVEL, 1, 7);
	public static final ItemStack OREGRAVELDRACONIUM = new ItemStack(BlockHandler.OREGRAVEL, 1, 8);
	public static final ItemStack OREGRAVELSULFUR = new ItemStack(BlockHandler.OREGRAVEL, 1, 9);
	public static final ItemStack OREGRAVELALUMINUM = new ItemStack(BlockHandler.OREGRAVEL, 1, 10);
	public static final ItemStack OREGRAVELSILICON = new ItemStack(BlockHandler.OREGRAVEL, 1, 11);
	public static final ItemStack OREGRAVELAMBER = new ItemStack(BlockHandler.OREGRAVEL, 1, 12);
	public static final ItemStack OREGRAVELCINNABAR = new ItemStack(BlockHandler.OREGRAVEL, 1, 13);
	public static final ItemStack OREGRAVELCERTUSQUARTZ = new ItemStack(BlockHandler.OREGRAVEL, 1, 14);
	public static final ItemStack OREGRAVELSALT = new ItemStack(BlockHandler.OREGRAVEL, 1,15);
	
	public static final ItemStack ORESANDCOPPER = new ItemStack(BlockHandler.ORESAND, 1, 0);
	public static final ItemStack ORESANDTIN = new ItemStack(BlockHandler.ORESAND, 1, 1);
	public static final ItemStack ORESANDLEAD = new ItemStack(BlockHandler.ORESAND, 1, 2);
	public static final ItemStack ORESANDNICKEL = new ItemStack(BlockHandler.ORESAND, 1, 3);
	public static final ItemStack ORESANDPLATINUM = new ItemStack(BlockHandler.ORESAND, 1, 4);
	public static final ItemStack ORESANDSILVER = new ItemStack(BlockHandler.ORESAND, 1, 5);
	public static final ItemStack ORESANDURANIUM = new ItemStack(BlockHandler.ORESAND, 1, 6);
	public static final ItemStack ORESANDOSMIUM = new ItemStack(BlockHandler.ORESAND, 1, 7);
	public static final ItemStack ORESANDDRACONIUM = new ItemStack(BlockHandler.ORESAND, 1, 8);
	public static final ItemStack ORESANDSULFUR = new ItemStack(BlockHandler.ORESAND, 1, 9);
	public static final ItemStack ORESANDALUMINUM = new ItemStack(BlockHandler.ORESAND, 1, 10);
	public static final ItemStack ORESANDSILICON = new ItemStack(BlockHandler.ORESAND, 1, 11);
	public static final ItemStack ORESANDAMBER = new ItemStack(BlockHandler.ORESAND, 1, 12);
	public static final ItemStack ORESANDCINNABAR = new ItemStack(BlockHandler.ORESAND, 1, 13);
	public static final ItemStack ORESANDCERTUSQUARTZ = new ItemStack(BlockHandler.ORESAND, 1, 14);
	public static final ItemStack ORESANDSALT = new ItemStack(BlockHandler.ORESAND, 1, 15);
	
	public static final ItemStack OREDUSTCOPPER = new ItemStack(BlockHandler.OREDUST, 1, 0);
	public static final ItemStack OREDUSTTIN = new ItemStack(BlockHandler.OREDUST, 1, 1);
	public static final ItemStack OREDUSTLEAD = new ItemStack(BlockHandler.OREDUST, 1, 2);
	public static final ItemStack OREDUSTNICKEL = new ItemStack(BlockHandler.OREDUST, 1, 3);
	public static final ItemStack OREDUSTPLATINUM = new ItemStack(BlockHandler.OREDUST, 1, 4);
	public static final ItemStack OREDUSTSILVER = new ItemStack(BlockHandler.OREDUST, 1, 5);
	public static final ItemStack OREDUSTURANIUM = new ItemStack(BlockHandler.OREDUST, 1, 6);
	public static final ItemStack OREDUSTOSMIUM = new ItemStack(BlockHandler.OREDUST, 1, 7);
	public static final ItemStack OREDUSTDRACONIUM = new ItemStack(BlockHandler.OREDUST, 1, 8);
	public static final ItemStack OREDUSTSULFUR = new ItemStack(BlockHandler.OREDUST, 1, 9);
	public static final ItemStack OREDUSTALUMINUM = new ItemStack(BlockHandler.OREDUST, 1, 10);
	public static final ItemStack OREDUSTSILICON = new ItemStack(BlockHandler.OREDUST, 1, 11);
	public static final ItemStack OREDUSTAMBER = new ItemStack(BlockHandler.OREDUST, 1, 12);
	public static final ItemStack OREDUSTCINNABAR = new ItemStack(BlockHandler.OREDUST, 1, 13);
	public static final ItemStack OREDUSTCERTUSQUARTZ = new ItemStack(BlockHandler.OREDUST, 1, 14);
	public static final ItemStack OREDUSTSALT = new ItemStack(BlockHandler.OREDUST, 1, 15);
}
