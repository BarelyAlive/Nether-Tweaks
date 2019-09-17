package mod.nethertweaks.blocks.tile;

import mod.nethertweaks.Constants;
import mod.nethertweaks.blocks.NetherrackFurnace;
import mod.nethertweaks.blocks.container.ContainerNetherrackFurnace;
import mod.nethertweaks.capabilities.CapabilityHeatManager;
import mod.nethertweaks.config.Config;
import mod.nethertweaks.registries.manager.NTMRegistryManager;
import mod.sfhcore.blocks.tiles.TileInventory;
import mod.sfhcore.network.NetworkHandler;
import mod.sfhcore.util.BlockInfo;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class TileNetherrackFurnace extends TileInventory
{
	public TileNetherrackFurnace() {
		super(2, Constants.TE_NETHERRACKFURNACE);
		setMaxworkTime(Config.burnTimeFurnace);
	}

	@Override
	public void update()
	{
		if (canSmelt()) work();
		else setWorkTime(0);

		checkInputOutput();
		NetherrackFurnace.setState(isWorking(), world, pos);

		if(world.isRemote) return;

		NetworkHandler.sendNBTUpdate(this);

		if(getWorkTime() >= getMaxworkTime())
		{
			setWorkTime(0);
			smeltItem();
		}
	}

	private void checkInputOutput()
	{
		if(world.isRemote) return;

		if(Config.autoExtractItems)
			extractFromInventory(pos.up(), EnumFacing.DOWN);
		if(Config.autoOutputItems) {
			insertToInventory(pos.north(), EnumFacing.SOUTH);
			insertToInventory(pos.south(), EnumFacing.NORTH);
			insertToInventory(pos.west(), EnumFacing.EAST);
			insertToInventory(pos.east(), EnumFacing.WEST);
		}
	}

	/**
	 * Returns true if the furnace can smelt an item, i.e. has a source item, destination stack isn't full, etc.
	 * Aaaaaaaaaaaaaaaaaaaand if there's a source of heat beneath it
	 */
	private boolean canSmelt()
	{
		if(calcMaxWorktime() == 0) return false;
		if(getStackInSlot(0).isEmpty()) return false;
		ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(getStackInSlot(0));
		if(itemstack.isEmpty()) return false;
		ItemStack itemstack1 = getStackInSlot(1);
		if(itemstack1.isEmpty()) return true;
		if(!itemstack1.isItemEqual(itemstack)) return false;
		if(itemstack1.getCount() + itemstack.getCount() <= getInventoryStackLimit()
				&& itemstack1.getCount() + itemstack.getCount() <= itemstack1.getMaxStackSize())  // Forge fix: make furnace respect stack sizes in furnace recipes
			return true;

		return itemstack1.getCount() + itemstack.getCount() <= itemstack.getMaxStackSize(); // Forge fix: make furnace respect stack sizes in furnace recipes
	}

	private int getHeatRate() {
		BlockPos posBelow = pos.add(0, -1, 0);
		IBlockState stateBelow = getWorld().getBlockState(posBelow);

		if (stateBelow == Blocks.AIR.getDefaultState()) return 0;

		// Try to match metadata
		int heat = NTMRegistryManager.HEAT_REGISTRY.getHeatAmount(new BlockInfo(stateBelow));

		// Try to match without metadata
		if (heat == 0 && !Item.getItemFromBlock(stateBelow.getBlock()).getHasSubtypes())
			heat = NTMRegistryManager.HEAT_REGISTRY.getHeatAmount(new BlockInfo(stateBelow.getBlock()));

		if (heat != 0) return heat;

		TileEntity tile = getWorld().getTileEntity(posBelow);

		if (tile != null && tile.hasCapability(CapabilityHeatManager.HEAT_CAPABILITY, EnumFacing.UP))
			return tile.getCapability(CapabilityHeatManager.HEAT_CAPABILITY, EnumFacing.UP).getHeatRate();

		return 0;
	}

	private int calcMaxWorktime()
	{
		int heat = getHeatRate();
		int workTime = 0;
		if (heat != 0)
			workTime = Config.burnTimeFurnace * 3 / heat;
		else
			setWorkTime(0);
		setMaxworkTime(workTime);
		return workTime;
	}

	/**
	 * Turn one item from the furnace source stack into the appropriate smelted item in the furnace result stack
	 */
	public void smeltItem()
	{
		ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(getStackInSlot(0));

		if (getStackInSlot(1).isEmpty())
			setInventorySlotContents(1, itemstack.copy());
		else if (getStackInSlot(1).getItem() == itemstack.getItem())
			getStackInSlot(1).grow(itemstack.getCount());

		getStackInSlot(0).shrink(1);

		if (getStackInSlot(0).getCount() <= 0)
			setInventorySlotContents(0, ItemStack.EMPTY);
	}

	@Override
	public boolean isItemValidForSlot(final int index, final ItemStack stack)
	{
		ItemStack slot;
		ItemStack result;
		if(index == 0) {
			slot = getStackInSlot(1);
			if(slot.getCount() == slot.getMaxStackSize()) return false;

			result = FurnaceRecipes.instance().getSmeltingResult(stack);
			if(result != ItemStack.EMPTY) return true;
		}
		return false;
	}

	@Override
	public boolean isItemValidForSlotToExtract(final int index, final ItemStack itemStack) {
		if (index == 0) return false;
		if (index == 1) return true;
		return false;
	}

	@Override
	public String getGuiID()
	{
		return "nethertweaksmod:gui_netherrack_furnace";
	}

	@Override
	public Container createContainer(final InventoryPlayer playerInventory, final EntityPlayer playerIn)
	{
		return new ContainerNetherrackFurnace(playerInventory, this);
	}
}
