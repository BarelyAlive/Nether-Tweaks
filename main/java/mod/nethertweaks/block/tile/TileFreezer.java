package mod.nethertweaks.block.tile;

import mod.nethertweaks.block.Barrel;
import mod.nethertweaks.block.container.ContainerFreezer;
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
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;

public class TileFreezer extends TileFluidInventory
{
	private int fillTick = 0;
	private float temp = 20f;
	private int timer = 0;
	private final int maxTimer = Config.cooldownFreezer;

	public TileFreezer() {
		super(3, new FluidTankSingle(FluidRegistry.WATER, 0, Config.capacityFreezer));
		setMaxworkTime(Config.freezeTimeFreezer);
	}

	@Override
	public void update()
	{
		if(world.isRemote) return;

		fillTick++;

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
		if(fillTick >= 20) fillTick = 0;
	}

	protected void checkInputOutput()
	{
		if(Config.autoExtractItems)
			extractFromInventory(pos.up(), EnumFacing.DOWN);
		if(Config.autoOutputItems) {
			insertToInventory(pos.north(), EnumFacing.SOUTH);
			insertToInventory(pos.south(), EnumFacing.NORTH);
			insertToInventory(pos.west(), EnumFacing.EAST);
			insertToInventory(pos.east(), EnumFacing.WEST);
		}

		if (fillTick == 20) {
			final FluidStack water = new FluidStack(FluidRegistry.WATER, Config.fluidTransferAmount);
			if (Config.fluidTransferAmount > 0) {
				final BlockPos up = getPos().up();

				//Check FluidHandler
				final IFluidHandler hup = FluidUtil.getFluidHandler(world, up, EnumFacing.DOWN);

				if (hup != null && world.getBlockState(up).getBlock() instanceof Barrel)
					FluidUtil.tryFluidTransfer(getTank(), hup, water, true);
			}
		}
	}

	protected boolean canFreeze()
	{
		return getTemp() <= 0 &&
				getTank().getFluidAmount() >= 1000 &&
				getStackInSlot(0).getCount() < 64;
    }

	protected void freezeItem()
	{
		getTank().drain(1000, true);

		if(getStackInSlot(0).isEmpty())
			setInventorySlotContents(0, new ItemStack(Blocks.ICE));
		else
			getStackInSlot(0).grow(1);
	}

	protected void fillFromItem()
	{
		final ItemStack input  = getStackInSlot(2);
		final ItemStack output = getStackInSlot(1);

		if (!input.isEmpty() && output.getCount() != output.getMaxStackSize())
		{
			final IFluidHandlerItem handler = FluidUtil.getFluidHandler(input);
			
			if (handler != null && FluidUtil.getFluidContained(input) != null)
			{	
				if(FluidUtil.tryFluidTransfer(getTank(), handler, Integer.MAX_VALUE, true) != null)
				{		
					//Das veränderte Fluid Item
					final ItemStack container = handler.getContainer();
					
					decrStackSize(2, 1);
					
					if (FluidUtil.getFluidContained(container) != null)
					{
						if(!output.isEmpty())
							setInventorySlotContents(2, container);
					}
					else if(output.getMaxStackSize() < output.getCount() && ItemStack.areItemsEqual(container, output))
						getStackInSlot(1).grow(1);
					else
						setInventorySlotContents(2, container);
				}
			}
			else if (ItemStack.areItemStacksEqual(getStackInSlot(2), TankUtil.WATER_BOTTLE))
				if (emptyRoom() >= 250) {
					getTank().fill(new FluidStack(FluidRegistry.WATER, 250), true);

					setInventorySlotContents(1, new ItemStack(Items.GLASS_BOTTLE, output.getCount() + 1));
					decrStackSize(2, 1);
				} 
		}
		
		if(output.isEmpty() && !input.isEmpty())
		{
			decrStackSize(2, 1);
			setInventorySlotContents(1, input);
		}
	}

	@Override
	public boolean isItemValidForSlot(final int index, final ItemStack stack)
	{
		FluidStack f = FluidUtil.getFluidContained(stack);
		if(getStackInSlot(index).getCount() == getStackInSlot(index).getMaxStackSize()) return false;

		return index == 2 && (ItemStack.areItemStacksEqual(stack, TankUtil.WATER_BOTTLE) ||
				f != null && f.getFluid() == FluidRegistry.WATER);
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
	public void readFromNBT(final NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		fillTick = nbt.getInteger("fillTick");
		setTemp(nbt.getFloat("temperature"));
		timer = nbt.getInteger("timer");
	}

	@Override
	public NBTTagCompound writeToNBT(final NBTTagCompound nbt) {
		nbt.setInteger("fillTick", fillTick);
		nbt.setFloat("temperature", getTemp());
		nbt.setInteger("timer", timer);
		return super.writeToNBT(nbt);
	}

	public float getTemp() {
		return temp;
	}

	public void setTemp(final float temp) {
		this.temp = temp;
	}
}
