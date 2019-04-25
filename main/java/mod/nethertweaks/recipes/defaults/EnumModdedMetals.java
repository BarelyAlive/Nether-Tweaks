package mod.nethertweaks.recipes.defaults;

import org.apache.commons.lang3.text.WordUtils;

import mod.nethertweaks.util.OreDictUtil;
import mod.sfhcore.texturing.Color;
import mod.sfhcore.util.ItemInfo;

public enum EnumModdedMetals
{	
    ALUMINUM(new Color("BFBFBF")),
    ARDITE(new Color("FF751A")),
    COBALT(new Color("3333FF")),
    COPPER(new Color("FF9933")),
    LEAD(new Color("330066")),
    NICKEL(new Color("FFFFCC")),
    SILVER(new Color("F2F2F2")),
    TIN(new Color("E6FFF2")),
    TUNGSTEN(new Color("1C1C1C")),
    URANIUM(new Color("4E5B43")),
    ZINC(new Color("A59C74"));

	private Color color;

	public Color getColor() {
		return color;
	}

	private EnumModdedMetals(Color color)
    {
        this.color = color;
    }
	
	public String getRegistryName()
    {
        return this.name().toLowerCase();
    }

    private String getConcatableName()
    {
        return WordUtils.capitalize(getRegistryName());
    }

    public String getOreName()
    {
        return "ore" + getConcatableName();
    }

    private String getIngotName()
    {
        return "ingot" + getConcatableName();
    }

    private String getDustName()
    {
        return "dust" + getConcatableName();
    }

    public ItemInfo getIngot()
    {
        return OreDictUtil.getOreDictEntry(getIngotName());
    }

    public ItemInfo getDust()
    {
        return OreDictUtil.getOreDictEntry(getDustName());
    }
}