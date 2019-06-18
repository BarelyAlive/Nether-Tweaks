package mod.nethertweaks.blocks.tile;

import mod.nethertweaks.INames;
import mod.nethertweaks.blocks.container.ContainerCondenser;
import mod.nethertweaks.capabilities.CapabilityHeatManager;
import mod.nethertweaks.config.Config;
import mod.nethertweaks.handler.BlockHandler;
import mod.nethertweaks.registries.manager.NTMRegistryManager;
import mod.nethertweaks.registry.types.Dryable;
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
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;

public class TileCondenser extends TileFluidInventory
{	
	private int fillTick = 0;
	
    public TileCondenser() {
		super(3, INames.TE_CONDENSER, new FluidTankSingle(FluidRegistry.WATER, 0, Config.capacityCondenser));
		this.setMaxworkTime(Config.dryTimeCondenser);
	}

	@Override
    public void update()
	{
		if(world.isRemote) return;
		
    	checkInputOutput();
		fillToItemSlot();
		fillToNeighborsTank();
		
		NetworkHandler.sendNBTUpdate(this);
				
		if(!canDry())
		{
			this.setWorkTime(0);
			return;
		}
		
		work();
		
		if(this.getWorkTime() >= this.getMaxworkTime())
		{
			this.setWorkTime(0);
			dry();
		}
	}
	
	private boolean canDry()
	{
		if(calcMaxWorktime() == 0) return false;
		if(this.getStackInSlot(0).isEmpty()) return false;
		
		if(!NTMRegistryManager.CONDENSER_REGISTRY.containsItem(getStackInSlot(0))) return false;
		Dryable result = NTMRegistryManager.CONDENSER_REGISTRY.getItem(getStackInSlot(0));
		if(result == null) return false;
		if(this.emptyRoom() < result.getValue()) return false;
		
		return true;
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
	
	private void fillToNeighborsTank()
	{
		fillTick++;
		if (fillTick == 20) {
			FluidStack water = new FluidStack(FluidRegistry.WATER, Config.fluidOutputAmount);
			if (this.getTank().getFluidAmount() != 0 && Config.fluidOutputAmount > 0) {
				BlockPos north = this.getPos().north();
				BlockPos east = this.getPos().east();
				BlockPos south = this.getPos().south();
				BlockPos west = this.getPos().west();

				//Check FluidHandler
				IFluidHandler hnorth = FluidUtil.getFluidHandler(world, north, EnumFacing.SOUTH);
				IFluidHandler heast = FluidUtil.getFluidHandler(world, east, EnumFacing.WEST);
				IFluidHandler hsouth = FluidUtil.getFluidHandler(world, south, EnumFacing.NORTH);
				IFluidHandler hwest = FluidUtil.getFluidHandler(world, west, EnumFacing.EAST);

				if (hnorth != null && world.getBlockState(north) != BlockHandler.CONDENSER.getDefaultState())
					FluidUtil.tryFluidTransfer(hnorth, this.getTank(), water, true);
				if (heast != null && world.getBlockState(east) != BlockHandler.CONDENSER.getDefaultState())
					FluidUtil.tryFluidTransfer(heast, this.getTank(), water, true);
				if (hsouth != null && world.getBlockState(south) != BlockHandler.CONDENSER.getDefaultState())
					FluidUtil.tryFluidTransfer(hsouth, this.getTank(), water, true);
				if (hwest != null && world.getBlockState(west) != BlockHandler.CONDENSER.getDefaultState())
					FluidUtil.tryFluidTransfer(hwest, this.getTank(), water, true);
			}
			fillTick = 0;
		}
	}
	
	private void dry()
	{
		ItemStack material = this.getStackInSlot(0);
		if(NTMRegistryManager.CONDENSER_REGISTRY.getItem(material) == null) return;
		int amount = NTMRegistryManager.CONDENSER_REGISTRY.getItem(material).getValue();
		
		if(amount > 0)
			this.getTank().fill(new FluidStack(FluidRegistry.WATER, amount), true);
		
		material.shrink(1);
	}
	
	private void fillToItemSlot()
	{
		ItemStack input  = this.getStackInSlot(2);
    	ItemStack output = this.getStackInSlot(1);
    	
    	if(!output.isEmpty()) return;
    	if(input.isEmpty()) return;
    	if(this.getTank().getFluidAmount() == 0) return;
    	
    	input.setCount(1);
		IFluidHandlerItem input_handler = FluidUtil.getFluidHandler(input);
		
		if (input_handler != null)
		{
			FluidStack f = FluidUtil.tryFluidTransfer(input_handler, this.getTank(), Integer.MAX_VALUE, false);
			if (f == null) return;
			
			FluidUtil.tryFluidTransfer(input_handler, this.getTank(), Integer.MAX_VALUE, true);
			
			this.setInventorySlotContents(1, input_handler.getContainer());
			this.decrStackSize(2, 1);
		}
		
		if(getStackInSlot(2).getItem() == Items.GLASS_BOTTLE && (ItemStack.areItemsEqual(output, TankUtil.WATER_BOTTLE) || this.getStackInSlot(1).isEmpty()))
		{
			if(this.getTank().getFluidAmount() >= 250)
			{
                this.getTank().drain(250, true);
                
				this.setInventorySlotContents(1, TankUtil.WATER_BOTTLE.copy());
				this.decrStackSize(2, 1);
            }
		}
	}
	
	private int getHeatRate() {
        BlockPos posBelow = pos.add(0, -1, 0);
        IBlockState stateBelow = getWorld().getBlockState(posBelow);

        if(stateBelow == Blocks.AIR.getDefaultState()) {
            return 0;
        }

        // Try to match metadata
        int heat = NTMRegistryManager.HEAT_REGISTRY.getHeatAmount(new BlockInfo(stateBelow));

        // Try to match without metadata
        if(heat == 0 && !Item.getItemFromBlock(stateBelow.getBlock()).getHasSubtypes())
            heat = NTMRegistryManager.HEAT_REGISTRY.getHeatAmount(new BlockInfo(stateBelow.getBlock()));
        
        if(heat != 0) return heat;

        TileEntity tile = getWorld().getTileEntity(posBelow);

        if(tile != null && tile.hasCapability(CapabilityHeatManager.HEAT_CAPABILITY, EnumFacing.UP)) {
            return tile.getCapability(CapabilityHeatManager.HEAT_CAPABILITY, EnumFacing.UP).getHeatRate();
        }

        return 0;
    }
	
	private int calcMaxWorktime()
	{
		int heat = getHeatRate();
		int workTime = Config.dryTimeCondenser;
		if (heat != 0) {
			workTime *= 3;
			workTime /= heat;
			this.setMaxworkTime(workTime);
			return workTime;
		}
		else
		{
			this.setWorkTime(0);
			return 0;
		}
	}
	
	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		IFluidHandlerItem handler = FluidUtil.getFluidHandler(stack);
		if(this.getStackInSlot(index).getCount() == this.getStackInSlot(index).getMaxStackSize()) return false;
		
		switch (index) {
		case 0: return NTMRegistryManager.CONDENSER_REGISTRY.containsItem(stack);
		case 1: return false;
		case 2:
			if(stack.getItem() == Items.GLASS_BOTTLE) return true;
			if(handler == null) return false;
			if(FluidUtil.tryFluidTransfer(handler, this.getTank(), Integer.MAX_VALUE, false) == null) return false;
		}
			
		return true;
	}
	
	@Override
	public boolean isItemValidForSlotToExtract(int index, ItemStack itemStack)
	{
		return index == 1;
	}
	
    public String getGuiID()
    {
        return "nethertweaksmod:gui_condenser";
    }
 
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
    {
        return new ContainerCondenser(playerInventory, this);
    }
    
    @Override
    public void readFromNBT(NBTTagCompound compound) {
    	fillTick = compound.getInteger("fillTick");
    	super.readFromNBT(compound);
    }
    
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
    	compound.setInteger("fillTick", fillTick);
    	return super.writeToNBT(compound);
    }
}