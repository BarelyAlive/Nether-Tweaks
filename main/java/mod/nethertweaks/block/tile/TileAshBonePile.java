package mod.nethertweaks.block.tile;

import mod.sfhcore.blocks.tiles.TileBase;
import net.minecraft.nbt.NBTTagCompound;

public class TileAshBonePile extends TileBase  {
	boolean is_lit;

	public TileAshBonePile() {
		is_lit = false;
	}

	public boolean isLit()
	{
		return is_lit;
	}

	public void isLit(final boolean is_lit)
	{
		this.is_lit = is_lit;
		if (!world.isRemote)
			markDirtyClient();
	}

	@Override
	public NBTTagCompound writeToNBT(final NBTTagCompound compound) {
		compound.setBoolean("isLit", is_lit);
		return super.writeToNBT(compound);
	}

	@Override
	public void readFromNBT(final NBTTagCompound compound) {
		is_lit = compound.getBoolean("isLit");
		super.readFromNBT(compound);
	}
}
