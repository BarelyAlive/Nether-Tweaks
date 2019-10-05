package mod.nethertweaks.blocks.tile;

import mod.sfhcore.blocks.tiles.TileBase;
import net.minecraft.nbt.NBTTagCompound;

public class TileAshBonePile extends TileBase  {
	boolean is_lit;
	
	public TileAshBonePile() {
		is_lit = false;
	}
	
	public boolean isLit()
	{
		return this.is_lit;
	}
	
	public void isLit(boolean is_lit)
	{
		this.is_lit = is_lit;
		this.markDirtyClient();
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setBoolean("isLit", this.is_lit);
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		this.is_lit = compound.getBoolean("isLit");
		super.readFromNBT(compound);
	}
}
