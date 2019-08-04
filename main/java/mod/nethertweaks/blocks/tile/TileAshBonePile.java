package mod.nethertweaks.blocks.tile;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import mod.sfhcore.blocks.tiles.TileBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
