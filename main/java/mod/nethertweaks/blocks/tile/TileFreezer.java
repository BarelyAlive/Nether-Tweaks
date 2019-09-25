package mod.nethertweaks.blocks.tile;

import mod.nethertweaks.Constants;
import mod.nethertweaks.blocks.container.ContainerFreezer;
import mod.nethertweaks.config.Config;
import mod.sfhcore.blocks.tiles.TileFluidInventory;
import mod.sfhcore.fluid.FluidTankSingle;
import mod.sfhcore.network.NetworkHandler;
import mod.sfhcore.util.TankUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;

public class TileFreezer extends TileFluidInventory
{
	private ItemStack ice = new ItemStack(Blocks.ICE, 1);
	private float temp = 20f;
	private int timer = 0;
	private int maxTimer = Config.cooldownFreezer;

	private ItemStack ice()
	{
		return ice.copy();
	}

	public TileFreezer() {
		super(3, Constants.TE_FREEZER, new FluidTankSingle(FluidRegistry.WATER, 0, Config.capacityFreezer));
		setMaxworkTime(Config.freezeTimeFreezer);
	}

	@Override
	public void update()
	{
		if(world.isRemote) return;

		checkInputOutput();
		fillFromItem();
		NetworkHandler.sendNBTUpdate(this);

		if(!world.isBlockPowered(pos))
		{
			if(timer > 0) timer--;
			if(getWorld().provider.doesWaterVaporize())
				if(getTemp() < 100f) setTemp(getTemp() + 0.025f);
		}
		else
		{
			if(timer < maxTimer) timer++;
			setTemp(20f - 50f * ((float)timer / (float)maxTimer));
		}

		if(getTemp() < 0)
			setMaxworkTime((int) (Config.freezeTimeFreezer * (1 - getTemp() / -90f )));
		else
			setMaxworkTime(Config.freezeTimeFreezer);

		if(canFreeze())	work();
		else setWorkTime(0);

		if(getWorkTime() >= getMaxworkTime())
		{
			setWorkTime(0);
			freezeItem();
		}
	}

	private void checkInputOutput()
	{
		if(Config.autoExtractItems)
			extractFromInventory(pos.up(), EnumFacing.DOWN);
		if(Config.autoOutputItems) {
			insertToInventory(pos.north(), EnumFacing.SOUTH);
			insertToInventory(pos.south(), EnumFacing.NORTH);
			insertToInventory(pos.west(), EnumFacing.EAST);
			insertToInventory(pos.east(), EnumFacing.WEST);
		}
	}

	private boolean canFreeze()
	{
		if(getTemp() > 0) return false;
		if(getTank().getFluidAmount() < 1000) return false;
		if(getStackInSlot(0).getCount() == ice().getMaxStackSize()) return false;
		return true;
	}

	private void freezeItem()
	{
		getTank().drain(1000, true);

		if(getStackInSlot(0).isEmpty())
			setInventorySlotContents(0, new ItemStack(ice().getItem()));

		else if(getStackInSlot(0).getCount() >= 1)
			getStackInSlot(0).grow(1);

		fillFromItem();
	}

	private void fillFromItem()
	{
		ItemStack input  = getStackInSlot(2);
		ItemStack output = getStackInSlot(1);

		if(input.isEmpty()) return;
		if(output.getCount() == output.getMaxStackSize()) return;

		ItemStack copy = input.copy();

		IFluidHandlerItem handler = FluidUtil.getFluidHandler(copy);

		if(handler != null)
		{
			FluidStack f = FluidUtil.tryFluidTransfer(getTank(), handler, Integer.MAX_VALUE, false);
			if (f == null) return;

			//Z.b. der leere bucket bei nem Wassereimer
			ItemStack containerItem = handler.getContainer();

			if (!output.isEmpty() && !ItemStack.areItemsEqual(output, containerItem)) return;

			FluidUtil.tryFluidTransfer(getTank(), handler, Integer.MAX_VALUE, true);

			//Das veränderte Fluid Item
			ItemStack container = handler.getContainer();
			container.setCount(output.getCount()+1);

			setInventorySlotContents(1, container);
			getStackInSlot(2).shrink(1);
		}

		if(ItemStack.areItemStacksEqual(getStackInSlot(2), TankUtil.WATER_BOTTLE))
			if(emptyRoom() >= 250)
			{
				getTank().fill(new FluidStack(FluidRegistry.WATER, 250), true);

				ItemStack bottles = new ItemStack(Items.GLASS_BOTTLE, output.getCount()+1);

				setInventorySlotContents(1, bottles);
				decrStackSize(2, 1);
			}
	}

	@Override
	public boolean isItemValidForSlot(final int index, final ItemStack stack)
	{
		IFluidHandlerItem handler = FluidUtil.getFluidHandler(stack);
		if(getStackInSlot(index).getCount() == getStackInSlot(index).getMaxStackSize()) return false;

		switch (index) {
		case 0: return false;
		case 1: return false;
		case 2:
			if(ItemStack.areItemStacksEqual(stack, TankUtil.WATER_BOTTLE)) return true;
			if(handler ==  null) return false;
			if(FluidUtil.tryFluidTransfer(getTank(), handler, Integer.MAX_VALUE, false) == null) return false;
		}

		return true;
	}

	@Override
	public boolean isItemValidForSlotToExtract(final int index, final ItemStack itemStack) {
		return index != 2;
	}

	@Override
	public String getGuiID()
	{
		return "nethertweaksmod:gui_freezer";
	}

	@Override
	public Container createContainer(final InventoryPlayer playerInventory, final EntityPlayer playerIn)
	{
		return new ContainerFreezer(playerInventory, this);
	}	

	@Override
	public NBTTagCompound writeToNBT(final NBTTagCompound compound) {
		compound.setFloat("temperature", getTemp());
		compound.setInteger("timer", timer);
		return super.writeToNBT(compound);
	}

	@Override
	public void readFromNBT(final NBTTagCompound compound) {
		super.readFromNBT(compound);
		setTemp(compound.getFloat("temperature"));
		timer = compound.getInteger("timer");
	}

	public float getTemp() {
		return temp;
	}

	public void setTemp(final float temp) {
		this.temp = temp;
	}
}
