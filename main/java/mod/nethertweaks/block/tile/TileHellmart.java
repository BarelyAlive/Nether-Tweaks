package mod.nethertweaks.block.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileHellmart extends TileEntity {

	public TileHellmart() {
		super();
	}

	private int stockNum = 0;
	private final ItemStackHandler itemstackhandler = new ItemStackHandler();

	@Override
	public boolean hasCapability(final Capability<?> capability, final EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(final Capability<T> capability, final EnumFacing facing) {
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(itemstackhandler);
		return super.getCapability(capability, facing);
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TextComponentString("Hellmart");
	}

	@Override
	public void readFromNBT(final NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		stockNum = tagCompound.getInteger("StockNum");
		itemstackhandler.deserializeNBT((NBTTagCompound) tagCompound.getTag("Items"));
	}

	@Override
	public NBTTagCompound writeToNBT(final NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		tagCompound.setInteger("StockNum", stockNum);
		tagCompound.setTag("Items", itemstackhandler.serializeNBT());
		return tagCompound;
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		return writeToNBT(new NBTTagCompound());
	}

	public int getBrowsingInfo() {
		return stockNum;
	}

	public void setBrowsingInfo(final int stockNum) {
		this.stockNum = stockNum;
	}

	@Override
	public void invalidate() {
		updateContainingBlockInfo();
		super.invalidate();
	}

	public boolean canInteractWith(final EntityPlayer playerIn) {
		// If we are too far away from this tile entity you cannot use it
		return !isInvalid() && playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
	}

	public String getGuiID() {
		return "nethertweaksmod:hellmart";
	}
}