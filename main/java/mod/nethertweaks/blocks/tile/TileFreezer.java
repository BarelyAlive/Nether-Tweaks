package mod.nethertweaks.blocks.tile;

import mod.nethertweaks.INames;
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
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;

public class TileFreezer extends TileFluidInventory
{
	final ItemStack ice = new ItemStack(Blocks.ICE, 1);

	public TileFreezer() {
		super(3, INames.TE_FREEZER, new FluidTankSingle(FluidRegistry.WATER, 0, Config.capacityFreezer));
		this.setMaxworkTime(Config.freezeTimeFreezer);
	}

    @Override
	public void update()
    {
    	if(world.isRemote) return;

    	checkInputOutput();
    	fillFromItem();

		NetworkHandler.sendNBTUpdate(this);

		if(!canFreeze())
		{
			this.setWorkTime(0);
			return;
		}

		work();

		if(getWorkTime() >= this.getMaxworkTime())
		{
			this.setWorkTime(0);
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
		if(!world.isBlockPowered(pos)) return false;
        if(this.getTank().getFluidAmount() < 1000) return false;
        if(this.getStackInSlot(0).getCount() == ice.getMaxStackSize()) return false;
        return true;
    }

    private void freezeItem()
    {
    	this.getTank().drain(1000, true);

        if(this.getStackInSlot(0).isEmpty())
            this.setInventorySlotContents(0, new ItemStack(ice.getItem()));

        else if(this.getStackInSlot(0).getCount() >= 1)
        	this.getStackInSlot(0).grow(1);

        fillFromItem();
    }

	private void fillFromItem()
	{
		ItemStack input  = this.getStackInSlot(2);
		ItemStack output = this.getStackInSlot(1);

		if(input.isEmpty()) return;

		IFluidHandlerItem handler = FluidUtil.getFluidHandler(input);

		if(handler != null)
		{
    		FluidStack f = FluidUtil.tryFluidTransfer(this.getTank(), handler, Integer.MAX_VALUE, false);
    		if (f == null) return;

    		if (!output.isEmpty() && !ItemStack.areItemsEqual(output, handler.getContainer().getItem().getContainerItem(handler.getContainer()))) return;

			FluidUtil.tryFluidTransfer(this.getTank(), handler, Integer.MAX_VALUE, true);

			System.out.println(handler.getContainer());

			if(output.isEmpty())
			{
				this.setInventorySlotContents(1, new ItemStack(handler.getContainer().getItem()));
				this.getStackInSlot(2).shrink(1);
				System.out.println(getStackInSlot(2));
			}
			if(ItemStack.areItemsEqual(output, handler.getContainer()))
			{
				this.getStackInSlot(1).grow(1);
				this.decrStackSize(2, 1);
			}
		}

		if(ItemStack.areItemStacksEqual(this.getStackInSlot(2), TankUtil.WATER_BOTTLE))
		{
			if(getTank().getFluidAmount() < getTank().getCapacity())
			{
       			this.getTank().fill(new FluidStack(FluidRegistry.WATER, 250), true);

       			if(output.isEmpty())
				setInventorySlotContents(1, new ItemStack(Items.GLASS_BOTTLE));

       			else if(ItemStack.areItemsEqual(output, new ItemStack(Items.GLASS_BOTTLE)))
				this.getStackInSlot(1).grow(1);

				this.decrStackSize(2, 1);
			}
		}
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		IFluidHandlerItem handler = FluidUtil.getFluidHandler(stack);
		if(this.getStackInSlot(index).getCount() == this.getStackInSlot(index).getMaxStackSize()) return false;

		switch (index) {
		case 0: return false;
		case 1: return false;
		case 2:
			if(ItemStack.areItemStacksEqual(stack, TankUtil.WATER_BOTTLE)) return true;
			if(handler ==  null) return false;
			if(FluidUtil.tryFluidTransfer(this.getTank(), handler, Integer.MAX_VALUE, false) == null) return false;
		}

		return true;
	}

	@Override
	public boolean isItemValidForSlotToExtract(int index, ItemStack itemStack) {
		return index != 2;
	}

	@Override
	public String getGuiID()
    {
        return "nethertweaksmod:gui_freezer";
    }

    @Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
    {
        return new ContainerFreezer(playerInventory, this);
    }
}
