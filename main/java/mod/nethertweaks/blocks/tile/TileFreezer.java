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
		
		if(!world.isBlockPowered(pos) && timer > 0) timer--;
		
		if(world.isBlockPowered(pos) && timer < maxTimer) timer++;
		
		setTemp(20f - 50f * ((float)timer / (float)maxTimer));
		
		if(getTemp() < 0)
			setMaxworkTime((int) (Config.freezeTimeFreezer * (1 - (getTemp() / -90f ))));
		else
			setMaxworkTime(Config.freezeTimeFreezer);
		
		if(canFreeze())	work();
		else this.setWorkTime(0);

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
		if(getTemp() > 0) return false;
        if(this.getTank().getFluidAmount() < 1000) return false;
        if(this.getStackInSlot(0).getCount() == ice().getMaxStackSize()) return false;
        return true;
    }

    private void freezeItem()
    {
    	this.getTank().drain(1000, true);

        if(this.getStackInSlot(0).isEmpty())
            this.setInventorySlotContents(0, new ItemStack(ice().getItem()));

        else if(this.getStackInSlot(0).getCount() >= 1)
        	this.getStackInSlot(0).grow(1);

        fillFromItem();
    }

	private void fillFromItem()
	{
		ItemStack input  = this.getStackInSlot(2);
		ItemStack output = this.getStackInSlot(1);

		if(input.isEmpty()) return;
		if(output.getCount() == output.getMaxStackSize()) return;

		ItemStack copy = input.copy();
		
		IFluidHandlerItem handler = FluidUtil.getFluidHandler(copy);

		if(handler != null)
		{
    		FluidStack f = FluidUtil.tryFluidTransfer(this.getTank(), handler, Integer.MAX_VALUE, false);
    		if (f == null) return;
    		
    		//Z.b. der leere bucket bei nem Wassereimer
    		ItemStack containerItem = handler.getContainer();

    		if (!output.isEmpty() && !ItemStack.areItemsEqual(output, containerItem)) return;

			FluidUtil.tryFluidTransfer(this.getTank(), handler, Integer.MAX_VALUE, true);

			//Das veränderte Fluid Item
			ItemStack container = handler.getContainer();
			container.setCount(output.getCount()+1);
			
			this.setInventorySlotContents(1, container);
			this.getStackInSlot(2).shrink(1);
		}

		if(ItemStack.areItemStacksEqual(this.getStackInSlot(2), TankUtil.WATER_BOTTLE))
		{
			if(emptyRoom() >= 250)
			{
       			this.getTank().fill(new FluidStack(FluidRegistry.WATER, 250), true);

       			ItemStack bottles = new ItemStack(Items.GLASS_BOTTLE, output.getCount()+1);
       			
   				setInventorySlotContents(1, bottles);
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
    
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
    	compound.setFloat("temperature", getTemp());
    	compound.setInteger("timer", timer);
    	return super.writeToNBT(compound);
    }
    
    @Override
    public void readFromNBT(NBTTagCompound compound) {
    	super.readFromNBT(compound);
    	setTemp(compound.getFloat("temperature"));
    	timer = compound.getInteger("timer");
    }

	public float getTemp() {
		return temp;
	}

	public void setTemp(float temp) {
		this.temp = temp;
	}
}
