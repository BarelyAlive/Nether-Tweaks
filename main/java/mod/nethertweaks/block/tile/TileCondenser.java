package mod.nethertweaks.block.tile;

import java.util.Objects;

import mod.nethertweaks.barrel.modes.compost.BarrelModeCompost;
import mod.nethertweaks.block.container.ContainerCondenser;
import mod.nethertweaks.capabilities.CapabilityHeatManager;
import mod.nethertweaks.config.BlocksItems;
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
import mod.sfhcore.util.TankUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
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

	private static Fluid distilled()
	{
		return BlocksItems.enableDistilledWater ? ModFluids.FLUID_DISTILLED_WATER : FluidRegistry.WATER;
	}

	public TileCondenser() {
		super(3, new FluidTankSingle(distilled(), 0, Config.capacityCondenser));
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

		//heatNStuff
		if(getHeatRate() > 0) {
			if(timer < getMaxTimer()) timer++;
			setMaxTimer(Config.cooldownCondenser / getHeatRate());
		}
		else {
			if(timer > 0) timer--;
			setMaxTimer(Config.cooldownCondenser);
		}

		if(getTemp() > getMaxTemp())
			setTemp(getTemp() - getMaxTemp() > 0f ? getTemp() - 2f * ((float)timer / (float)getMaxTimer() / 2) : getTemp());
		else
			setTemp(getMaxTemp() - getTemp() > 0f ? getTemp() + 2f * ((float)timer / (float)getMaxTimer() / 2) : getTemp());

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
		Dryable result = NTMRegistryManager.CONDENSER_REGISTRY.getItem(getStackInSlot(0));
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

		if (fillTick == 20)
			if (world.getTileEntity(pos.up()) instanceof TileBarrel)
			{
				TileBarrel barrel = (TileBarrel) world.getTileEntity(pos.up());
				if(barrel != null)
					if (barrel.getMode() == null || Objects.equals(barrel.getMode().getName(), "compost")) {
						float amount = 0;
						if (barrel.getMode() != null)
							amount = ((BarrelModeCompost) barrel.getMode()).getFillAmount();
						if (amount <= 1f && getCompostMeter() >= 100f)
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
		int heat = getHeatRate();

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
			return 999f;
		}
	}

	private void fillToNeighborsTank()
	{
		if (fillTick == 20) {
			FluidStack water = new FluidStack(distilled(), Config.fluidTransferAmount);
			if (getTank().getFluidAmount() != 0 && Config.fluidTransferAmount > 0) {
				BlockPos north = getPos().north();
				BlockPos east = getPos().east();
				BlockPos south = getPos().south();
				BlockPos west = getPos().west();

				//Check FluidHandler
				IFluidHandler hnorth = FluidUtil.getFluidHandler(world, north, EnumFacing.SOUTH);
				IFluidHandler heast = FluidUtil.getFluidHandler(world, east, EnumFacing.WEST);
				IFluidHandler hsouth = FluidUtil.getFluidHandler(world, south, EnumFacing.NORTH);
				IFluidHandler hwest = FluidUtil.getFluidHandler(world, west, EnumFacing.EAST);

				if (hnorth != null && world.getBlockState(north) != ModBlocks.CONDENSER.getDefaultState())
					FluidUtil.tryFluidTransfer(hnorth, getTank(), water, true);
				if (heast != null && world.getBlockState(east) != ModBlocks.CONDENSER.getDefaultState())
					FluidUtil.tryFluidTransfer(heast, getTank(), water, true);
				if (hsouth != null && world.getBlockState(south) != ModBlocks.CONDENSER.getDefaultState())
					FluidUtil.tryFluidTransfer(hsouth, getTank(), water, true);
				if (hwest != null && world.getBlockState(west) != ModBlocks.CONDENSER.getDefaultState())
					FluidUtil.tryFluidTransfer(hwest, getTank(), water, true);
			}
		}
	}

	private void dry()
	{
		ItemStack material = getStackInSlot(0);

		if(NTMRegistryManager.COMPOST_REGISTRY.containsItem(material))
		{
			float waste = NTMRegistryManager.COMPOST_REGISTRY.getItem(material).getValue() * 1000;
			if(compostMeter <= getMaxCompost() - waste) compostMeter += waste;
		}
		if(NTMRegistryManager.CONDENSER_REGISTRY.containsItem(material))
		{
			int amount = NTMRegistryManager.CONDENSER_REGISTRY.getItem(material).getValue();
			if(amount > 0) getTank().fill(new FluidStack(distilled(), amount), true);

			material.shrink(1);
		}
	}

	private void fillToItemSlot()
	{
		ItemStack input  = getStackInSlot(2);
		ItemStack output = getStackInSlot(1);

		if(!output.isEmpty()) return;
		if(input.isEmpty()) return;
		if(getTank().getFluidAmount() == 0) return;

		ItemStack copy = input.copy();
		copy.setCount(1);

		IFluidHandlerItem input_handler = FluidUtil.getFluidHandler(copy);

		if (input_handler != null)
		{
			FluidStack f = FluidUtil.tryFluidTransfer(input_handler, getTank(), Integer.MAX_VALUE, false);
			if (f == null) return;

			FluidUtil.tryFluidTransfer(input_handler, getTank(), Integer.MAX_VALUE, true);

			ItemStack container = input_handler.getContainer();

			setInventorySlotContents(1, container);
			decrStackSize(2, 1);
		}

		if(BlocksItems.enableDistilledWater) return;

		if(getStackInSlot(2).getItem() == Items.GLASS_BOTTLE && getStackInSlot(1).isEmpty())
			if(getTank().getFluidAmount() >= 250)
			{
				getTank().drain(250, true);

				setInventorySlotContents(1, TankUtil.WATER_BOTTLE.copy());
				decrStackSize(2, 1);
			}
	}

	private int getHeatRate() {
		BlockPos posBelow = pos.add(0, -1, 0);
		IBlockState stateBelow = getWorld().getBlockState(posBelow);

		// Try to match metadata
		int heat = NTMRegistryManager.HEAT_REGISTRY.getHeatAmount(new BlockInfo(stateBelow));

		// Try to match without metadata
		if(heat == 0 && !Item.getItemFromBlock(stateBelow.getBlock()).getHasSubtypes())
			heat = NTMRegistryManager.HEAT_REGISTRY.getHeatAmount(new BlockInfo(stateBelow.getBlock()));

		if(heat != 0) return heat;

		TileEntity tile = getWorld().getTileEntity(posBelow);

		if(tile != null && tile.hasCapability(CapabilityHeatManager.HEAT_CAPABILITY, EnumFacing.UP))
			return Objects.requireNonNull(tile.getCapability(CapabilityHeatManager.HEAT_CAPABILITY, EnumFacing.UP)).getHeatRate();

		if(world.provider.doesWaterVaporize()) return 1;

		if(stateBelow == Blocks.AIR.getDefaultState())
			return 0;

		return 0;
	}

	@Override
	public boolean isItemValidForSlot(final int index, final ItemStack stack)
	{
		IFluidHandlerItem handler = FluidUtil.getFluidHandler(stack);
		if(getStackInSlot(index).getCount() == getStackInSlot(index).getMaxStackSize()) return false;

		switch (index) {
		case 0: return NTMRegistryManager.CONDENSER_REGISTRY.containsItem(stack);
		case 1: return false;
		case 2:
			if(stack.getItem() == Items.GLASS_BOTTLE && !BlocksItems.enableDistilledWater) return true;
			if(handler == null) return false;
			if(FluidUtil.tryFluidTransfer(handler, getTank(), Integer.MAX_VALUE, false) == null) return false;
		}

		return true;
	}

	@Override
	public boolean isItemValidForSlotToExtract(final int index, final ItemStack itemStack)
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

	public float getTemp() {
		return temp;
	}

	public void setTemp(float temp) {
		temp *= 100;
		int itemp = (int)temp;
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