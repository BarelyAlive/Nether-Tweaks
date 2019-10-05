package mod.nethertweaks.registry.types;

import mod.sfhcore.util.BlockInfo;

public class Meltable {

	public static final Meltable EMPTY = new Meltable("", 0);

	private String fluid;

	private int amount;

	private BlockInfo textureOverride;

	public Meltable(final String fluid, final int amount, final BlockInfo textureOverride) {
		this.fluid = fluid;
		this.amount = amount;
		this.textureOverride = textureOverride;
	}

	public Meltable(final String fluid, final int amount) {
		this.fluid = fluid;
		this.amount = amount;
		textureOverride = BlockInfo.EMPTY;
	}

	public String getFluid()
	{
		return fluid;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(final int amount) {
		this.amount = amount;
	}

	public BlockInfo getTextureOverride() {
		return textureOverride == null ? BlockInfo.EMPTY : textureOverride;
	}

	public Meltable setTextureOverride(final BlockInfo textureOverride) {
		this.textureOverride = textureOverride;
		return this;
	}

	public Meltable copy() {
		return new Meltable(fluid, amount, textureOverride);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + amount;
		result = prime * result + (fluid == null ? 0 : fluid.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
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