package mod.nethertweaks.registry.types;

public enum EnumOreSubtype
{
    PIECE(0),
    CHUNK(1),
    DUST(2),
    INGOT(3);
    
    private int meta;
    
    private EnumOreSubtype(int meta)
    {
    	this.meta = meta;
	}
}
