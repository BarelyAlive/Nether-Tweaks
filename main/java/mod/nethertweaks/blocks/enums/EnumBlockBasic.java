package mod.nethertweaks.blocks.enums;

import java.util.Locale;

import net.minecraft.util.IStringSerializable;

public enum EnumBlockBasic implements IStringSerializable
{
    ORE_HELLFAYAH,
    HELLFAYAH_BLOCK,
    BLOCKSALT;

    @Override
    public String toString()
    {
        return name().toLowerCase(Locale.ENGLISH);
    }

    @Override
    public String getName()
    {
        return this.toString();
    }
}
