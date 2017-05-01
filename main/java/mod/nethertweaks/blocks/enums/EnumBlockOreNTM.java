package mod.nethertweaks.blocks.enums;

import java.util.Locale;

import net.minecraft.util.IStringSerializable;

public enum EnumBlockOreNTM implements IStringSerializable
{
    COPPER,
    TIN,
    LEAD,
    NICKEL,
    PLATINUM,
    SILVER,
    URANIUM,
    OSMIUM,
    DRACONIUM,
    SULFUR,
    ALUMINUM,
    SILICON,
    AMBER,
    CINNABAR,
    CERTUSQUARTZ,
    SALT;

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
