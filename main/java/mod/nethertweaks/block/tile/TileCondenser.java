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
import mod.nethertweaks.registry.registries.base.types.FluidToWater;
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
	private int maxHeatAmount = NTMRegistryManager.HEAT_REGISTRY.getMaxHeatAmount();

	public TileCondenser() {
		super(3, new FluidTankSingle(ModFluids.FLUID_DISTILLED_WATER, 0, Config.capacityCondenser));
		setMaxworkTime(Config.dryTimeCondenser);
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

		rate = Math.max((rate - 10) * -1, 1);
		
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

	protected boolean canDry()
	{
		ItemStack input = getStackInSlot(0).copy();
		FluidStack f = FluidUtil.getFluidContained(input);

		final Dryable dryable = NTMRegistryManager.CONDENSER_REGISTRY.getItem(input);
		final FluidToWater fluidToWater = NTMRegistryManager.FLUID_TO_WATER_REGISTRY.getFluid(input);

		if(getTemp() >= 100f && !input.isEmpty())
			if(dryable != null && !dryable.equals(Dryable.getEMPTY()))
				return emptyRoom() >= dryable.getValue();
			else if(fluidToWater != null && f != null)
				return emptyRoom() >= fluidToWater.getPercOfWater() && f.amount >= 1000;

		return false;
    }

	protected void dry()
	{
		final ItemStack material = getStackInSlot(0).copy();
		int amount = 0;

		decrStackSize(0, 1);

		if(NTMRegistryManager.COMPOST_REGISTRY.containsItem(material))
		{
			final float waste = NTMRegistryManager.COMPOST_REGISTRY.getItem(material).getValue() * 1000;
			if(compostMeter <= getMaxCompost() - waste) compostMeter += waste;
		}
		if(NTMRegistryManager.FLUID_TO_WATER_REGISTRY.containsFluid(material))
		{
			//FluidContainer
			final IFluidHandlerItem handler = FluidUtil.getFluidHandler(material);

			if(handler != null)
			{
				amount = NTMRegistryManager.FLUID_TO_WATER_REGISTRY.getFluid(material).getPercOfWater();
				getTank().fill(new FluidStack(ModFluids.FLUID_DISTILLED_WATER, Math.max(amount, 0)), true);

				handler.drain(1000, true);

				setInventorySlotContents(0, handler.getContainer());
			}
		}
		else if(NTMRegistryManager.CONDENSER_REGISTRY.containsItem(material))
		{
			amount = NTMRegistryManager.CONDENSER_REGISTRY.getItem(material).getValue();
			if(amount > 0) getTank().fill(new FluidStack(ModFluids.FLUID_DISTILLED_WATER, amount), true);
		}
	}

	protected void fillToItemSlot()
	{
		final ItemStack input = getStackInSlot(2);
		final ItemStack output = getStackInSlot(1);

		final IFluidHandlerItem input_handler = FluidUtil.getFluidHandler(input);

		if(input_handler != null && FluidUtil.tryFluidTransfer(input_handler, getTank(), Integer.MAX_VALUE, true) != null)
		{
			final ItemStack container = input_handler.getContainer();
			int result = FluidUtil.getFluidHandler(container).fill(new FluidStack(ModFluids.FLUID_DISTILLED_WATER, Integer.MAX_VALUE), false);

			decrStackSize(2, 1);

			if(result == 0)
			{
				if(output.isEmpty())
					setInventorySlotContents(1, container);
				return;
			}

			setInventorySlotContents(2, container);
		}
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

	protected float getMaxTemp()
	{
		final int heat = getHeatRate();

		switch (heat) {
		case 0:	return 0;
		case 1:	return 100f;
		case 2:	return 250f;
		case 3:	return 600f;

		default:
			return 999f;
		}
	}

	protected void fillToNeighborsTank()
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

	protected int getHeatRate()
	{
		final BlockPos posBelow = pos.add(0, -1, 0);
		final IBlockState stateBelow = getWorld().getBlockState(posBelow);

		// Try to match metadata
		int heat = NTMRegistryManager.HEAT_REGISTRY.getHeatAmount(new BlockInfo(stateBelow));

		// Try to match without metadata
		if(heat == 0 && !Item.getItemFromBlock(stateBelow.getBlock()).getHasSubtypes())
			heat = NTMRegistryManager.HEAT_REGISTRY.getHeatAmount(new BlockInfo(stateBelow.getBlock()));

		final TileEntity tile = getWorld().getTileEntity(posBelow);

		if(tile != null && tile.hasCapability(CapabilityHeatManager.HEAT_CAPABILITY, EnumFacing.UP))
			heat = Math.max(heat, tile.getCapability(CapabilityHeatManager.HEAT_CAPABILITY, EnumFacing.UP).getHeatRate());

		if(world.provider.doesWaterVaporize())
			heat = Math.max(heat, 1);

		return heat;
	}

	@Override
	public boolean isItemValidForSlot(final int index, final ItemStack stack)
	{
		final IFluidHandlerItem handler = FluidUtil.getFluidHandler(stack);
		if(getStackInSlot(index).getCount() == getStackInSlot(index).getMaxStackSize()) return false;

		switch (index) {
		case 0: return NTMRegistryManager.CONDENSER_REGISTRY.containsItem(stack) || NTMRegistryManager.FLUID_TO_WATER_REGISTRY.containsFluid(stack);
		case 2:	return handler != null || FluidUtil.tryFluidTransfer(handler, getTank(), Integer.MAX_VALUE, false) != null;
		default:
			return false;
		}
	}

	@Override
	public boolean isItemValidForSlotToExtract(final int index, final ItemStack stack)
	{
		return index == 1;
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
	
	//GETTER & SETTER

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