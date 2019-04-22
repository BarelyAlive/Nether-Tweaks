package mod.nethertweaks.registry.types;

import mod.sfhcore.util.BlockInfo;

import javax.annotation.Nonnull;

public class Meltable {

	public static final Meltable EMPTY = new Meltable("", 0);

    private String fluid;

    private int amount;

    private BlockInfo textureOverride;

    public Meltable(String fluid, int amount, BlockInfo textureOverride) {
        this.fluid = fluid;
        this.amount = amount;
        this.textureOverride = textureOverride;
    }
    
    public Meltable(String fluid, int amount) {
        this.fluid = fluid;
        this.amount = amount;
        this.textureOverride = BlockInfo.EMPTY;
    }
    
    public String getFluid()
    {
    	return this.fluid;
    }

    public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public BlockInfo getTextureOverride() {
		return (textureOverride == null ? BlockInfo.EMPTY : this.textureOverride);
	}
	
    public Meltable setTextureOverride(BlockInfo textureOverride) {
        this.textureOverride = textureOverride;
        return this;
    }
    
    public Meltable copy() {
        return new Meltable(this.fluid, this.amount, this.textureOverride);
    }
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + amount;
		result = prime * result + ((fluid == null) ? 0 : fluid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Meltable other = (Meltable) obj;
		if (amount != other.amount)
			return false;
		if (fluid == null) {
			if (other.fluid != null)
				return false;
		} else if (!fluid.equals(other.fluid))
			return false;
		return true;
	}

	public static Meltable getEMPTY() {
		return EMPTY;
	}

}