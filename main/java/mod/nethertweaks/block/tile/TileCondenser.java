package mod.nethertweaks.block.tile;

import java.util.Objects;

import mod.nethertweaks.barrel.modes.compost.BarrelModeCompost;
import mod.nethertweaks.block.container.ContainerCondenser;
import mod.nethertweaks.capabilities.CapabilityHeatManager;
import mod.nethertweaks.config.Config;
import mod.nethertweaks.init.ModBlocks;
import mod.nethertweaks.init.ModFluids;
import mod.nethertweaks.network.MessageBarrelModeUpdate;
import mod.nethertweaks.registry.manager.NTMRegistryManager;
import mod.nethertweaks.registry.registries.base.types.Dryable;
import mod.sfhcore.blocks.tiles.TileFluidInventory;
import mod.sfhcore.fluid.FluidTankSingle;
import mod.sfhcore.network.NetworkHandler;
import mod.sfhcore.util.BlockInfo;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;

public class TileCondenser extends TileFluidInventory
{
	private int fillTick = 0;
	private float temp = 20f;
	private int timer = 0;
	private int maxTimer = Config.cooldownCondenser;
	private float compostMeter = 0;
	private float maxCompost = Config.capacityCondenser;
	private static int maxHeatAmount = 0;

	public TileCondenser() {
		super(3, new FluidTankSingle(ModFluids.FLUID_DISTILLED_WATER, 0, Config.capacityCondenser));
		setMaxworkTime(Config.dryTimeCondenser);
		maxHeatAmount = NTMRegistryManager.HEAT_REGISTRY.getMaxHeatAmount();
	}

	@Override
	public void update()
	{
		if(world.isRemote) return;

		fillTick++;

		checkInputOutput();
		fillToItemSlot();
		fillToNeighborsTank();

		NetworkHandler.sendNBTUpdate(this);

		int rate = getMaxTemp() > 100 ? (int)Math.floor(getMaxTemp() / 100f) : 0;

		rate -= 10;
		rate *= -1;

		if(rate == 0)
			rate = 1;
		//heatNStuff
		if(timer < getMaxTimer() / rate)
			timer+=getHeatRate();
		else if(timer > getMaxTimer() / rate)
			timer--;
		setMaxTimer(Config.cooldownCondenser);

		final float incr = 0.01f * getHeatRate();

		if(getTemp() > getMaxTemp() + incr)
			setTemp(getTemp() - getMaxTemp() > 0f ? timer % 2 == 0 ? getMaxTemp() * ((float)timer / (getMaxTimer() / rate)) : getTemp() : getTemp());
		else if(getTemp() < getMaxTemp() + incr)
			setTemp(getMaxTemp() - getTemp() > 0f ? timer % 2 == 0 ? getMaxTemp() * ((float)timer / (getMaxTimer() / rate)) : getTemp() : getTemp());

		if(getTemp() > 100f)
			setMaxworkTime((int) (Config.dryTimeCondenser / (getTemp() / 100f)));
		else
			setMaxworkTime(Config.dryTimeCondenser);

		//ENDE

		if(canDry()) work();
		else setWorkTime(0);

		//WEnn alles durch ist
		if(getWorkTime() >= getMaxworkTime())
		{
			setWorkTime(0);
			dry();
		}
		if(fillTick >= 20) fillTick = 0;
	}

	private boolean canDry()
	{
		if(getTemp() < 100f) return false;
		if(getStackInSlot(0).isEmpty()) return false;
		if(!NTMRegistryManager.CONDENSER_REGISTRY.containsItem(getStackInSlot(0))) return false;
		final Dryable result = NTMRegistryManager.CONDENSER_REGISTRY.getItem(getStackInSlot(0));
		if(result == null) return false;
        return emptyRoom() >= result.getValue();
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

		if(fillTick == 20)
			if(world.getTileEntity(pos.up()) instanceof TileBarrel)
			{
				final TileBarrel barrel = (TileBarrel) world.getTileEntity(pos.up());
				if(barrel != null)
					if(barrel.getMode() == null || Objects.equals(barrel.getMode().getName(), "compost")) {
						float amount = 0;
						if(barrel.getMode() != null)
							amount = ((BarrelModeCompost) barrel.getMode()).getFillAmount();
						if(amount <= 1f && getCompostMeter() >= 100f)
						{
							if(barrel.getMode() == null) barrel.setMode("compost");

							((BarrelModeCompost) barrel.getMode()).setCompostState(Blocks.DIRT.getDefaultState());

							NetworkHandler.sendToAllAround(new MessageBarrelModeUpdate("compost", pos.up()), barrel);
							((BarrelModeCompost) barrel.getMode()).setFillAmount(amount + 0.1f);
							setCompostMeter(getCompostMeter() - 100f);
							NetworkHandler.sendNBTUpdate(barrel);
							barrel.markDirty();
							getWorld().setBlockState(pos.up(), world.getBlockState(pos.up()));
						}
					}
			}
	}

	private float getMaxTemp()
	{
		final int heat = getHeatRate();

		switch (heat) {
		case 0:
			return 0;
		case 1:
			return 100f;
		case 2:
			return 250f;
		case 3:
			return 600f;

		default:
			return getMaxPossibleTemp();
		}
	}

	private float getMaxPossibleTemp()
	{
		return 999f;
	}

	private void fillToNeighborsTank()
	{
		if(fillTick == 20) {
			final FluidStack water = new FluidStack(ModFluids.FLUID_DISTILLED_WATER, Config.fluidTransferAmount);
			if(getTank().getFluidAmount() != 0 && Config.fluidTransferAmount > 0) {
				final BlockPos north = getPos().north();
				final BlockPos east = getPos().east();
				final BlockPos south = getPos().south();
				final BlockPos west = getPos().west();

				//Check FluidHandler
				final IFluidHandler hnorth = FluidUtil.getFluidHandler(world, north, EnumFacing.SOUTH);
				final IFluidHandler heast = FluidUtil.getFluidHandler(world, east, EnumFacing.WEST);
				final IFluidHandler hsouth = FluidUtil.getFluidHandler(world, south, EnumFacing.NORTH);
				final IFluidHandler hwest = FluidUtil.getFluidHandler(world, west, EnumFacing.EAST);

				if(hnorth != null && world.getBlockState(north).getBlock() != ModBlocks.CONDENSER)
					FluidUtil.tryFluidTransfer(hnorth, getTank(), water, true);
				if(heast != null && world.getBlockState(east).getBlock() != ModBlocks.CONDENSER)
					FluidUtil.tryFluidTransfer(heast, getTank(), water, true);
				if(hsouth != null && world.getBlockState(south).getBlock() != ModBlocks.CONDENSER)
					FluidUtil.tryFluidTransfer(hsouth, getTank(), water, true);
				if(hwest != null && world.getBlockState(west).getBlock() != ModBlocks.CONDENSER)
					FluidUtil.tryFluidTransfer(hwest, getTank(), water, true);
			}
		}
	}

	private void dry()
	{
		final ItemStack material = getStackInSlot(0).copy();

		if(NTMRegistryManager.COMPOST_REGISTRY.containsItem(material))
		{
			final float waste = NTMRegistryManager.COMPOST_REGISTRY.getItem(material).getValue() * 1000;
			if(compostMeter <= getMaxCompost() - waste) compostMeter += waste;
		}
		if(NTMRegistryManager.CONDENSER_REGISTRY.containsItem(material))
		{
			final int amount = NTMRegistryManager.CONDENSER_REGISTRY.getItem(material).getValue();
			if(amount > 0) getTank().fill(new FluidStack(ModFluids.FLUID_DISTILLED_WATER, amount), true);

			final IFluidHandlerItem handler = FluidUtil.getFluidHandler(material);

			if(handler != null)
			{
				int drainable =  Math.min(FluidUtil.getFluidContained(material).amount, 1000);
				handler.drain(drainable, true);
				setInventorySlotContents(0, handler.getContainer());
			}
			else
				decrStackSize(0, 1);
		}
	}

	private void fillToItemSlot()
	{
		final ItemStack input = getStackInSlot(2).copy();
		final ItemStack output = getStackInSlot(1).copy();

		if(input.isEmpty() || !output.isEmpty()) return;
		if(getTank().getFluidAmount() == 0) return;

		input.setCount(1);

		final IFluidHandlerItem input_handler = FluidUtil.getFluidHandler(input);

		if(input_handler != null)
		{
			final FluidStack f = FluidUtil.tryFluidTransfer(input_handler, getTank(), Integer.MAX_VALUE, false);
			if(f == null) return;

			FluidUtil.tryFluidTransfer(input_handler, getTank(), Integer.MAX_VALUE, true);

			final ItemStack container = input_handler.getContainer();

			setInventorySlotContents(1, container);
			decrStackSize(2, 1);
		}
	}

	private int getHeatRate() {
		final BlockPos posBelow = pos.add(0, -1, 0);
		final IBlockState stateBelow = getWorld().getBlockState(posBelow);

		// Try to match metadata
		int heat = NTMRegistryManager.HEAT_REGISTRY.getHeatAmount(new BlockInfo(stateBelow));

		// Try to match without metadata
		if(!Item.getItemFromBlock(stateBelow.getBlock()).getHasSubtypes())
			heat = Math.max(heat, NTMRegistryManager.HEAT_REGISTRY.getHeatAmount(new BlockInfo(stateBelow.getBlock())));

		final TileEntity tile = getWorld().getTileEntity(posBelow);

		if(tile != null && tile.hasCapability(CapabilityHeatManager.HEAT_CAPABILITY, EnumFacing.UP))
			heat = Math.max(heat, tile.getCapability(CapabilityHeatManager.HEAT_CAPABILITY, EnumFacing.UP).getHeatRate());

		if(world.provider.doesWaterVaporize()) heat = Math.max(heat, 1);

		return heat;
	}

	@Override
	public boolean isItemValidForSlot(final int index, final ItemStack stack)
	{
		final IFluidHandlerItem handler = FluidUtil.getFluidHandler(stack);
		if(getStackInSlot(index).getCount() == getStackInSlot(index).getMaxStackSize()) return false;

		switch (index) {
		case 0: return NTMRegistryManager.CONDENSER_REGISTRY.containsItem(stack);
		case 1: return false;
		case 2:
			if(handler == null) return false;
			if(FluidUtil.tryFluidTransfer(handler, getTank(), Integer.MAX_VALUE, false) == null) return false;
		}

		return true;
	}

	@Override
	public boolean isItemValidForSlotToExtract(final int index, final ItemStack itemStack)
	{
		switch (index) {
		case 0: return !NTMRegistryManager.CONDENSER_REGISTRY.containsItem(itemStack);
		case 1: return true;
		}
		
		return false;
	}

	@Override
	public String getGuiID()
	{
		return "nethertweaksmod:gui_condenser";
	}

	@Override
	public Container createContainer(final InventoryPlayer playerInventory, final EntityPlayer playerIn)
	{
		return new ContainerCondenser(playerInventory, this);
	}

	@Override
	public void readFromNBT(final NBTTagCompound nbt) {
		fillTick = nbt.getInteger("fillTick");
		setTemp(nbt.getFloat("temperature"));
		timer = nbt.getInteger("timer");
		setMaxTimer(nbt.getInteger("maxTimer"));
		setCompostMeter(nbt.getFloat("compostMeter"));
		super.readFromNBT(nbt);
	}

	@Override
	public NBTTagCompound writeToNBT(final NBTTagCompound nbt) {
		nbt.setInteger("fillTick", fillTick);
		nbt.setFloat("temperature", getTemp());
		nbt.setInteger("timer", timer);
		nbt.setInteger("maxTimer", getMaxTimer());
		nbt.setFloat("compostMeter", getCompostMeter());
		return super.writeToNBT(nbt);
	}

	public float getTemp() {
		return temp;
	}

	public void setTemp(float temp) {
		temp *= 100;
		final int itemp = (int)temp;
		this.temp = (float)itemp / 100;
	}

	public int getMaxTimer() {
		return maxTimer;
	}

	public void setMaxTimer(final int maxTimer) {
		this.maxTimer = maxTimer;
	}

	public float getCompostMeter() {
		return compostMeter;
	}

	public void setCompostMeter(final float compostMeter) {
		this.compostMeter = compostMeter;
	}

	public float getMaxCompost() {
		return maxCompost;
	}

	public void setMaxCompost(final float maxCompost) {
		this.maxCompost = maxCompost;
	}
}