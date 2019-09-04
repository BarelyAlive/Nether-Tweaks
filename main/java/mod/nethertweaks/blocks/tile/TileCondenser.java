package mod.nethertweaks.blocks.tile;

import mod.nethertweaks.INames;
import mod.nethertweaks.barrel.IBarrelMode;
import mod.nethertweaks.barrel.modes.compost.BarrelModeCompost;
import mod.nethertweaks.blocks.container.ContainerCondenser;
import mod.nethertweaks.capabilities.CapabilityHeatManager;
import mod.nethertweaks.config.Config;
import mod.nethertweaks.handler.BlockHandler;
import mod.nethertweaks.handler.BucketNFluidHandler;
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
	private float maxCompost = 8000;

	private static Fluid distilled()
	{
		return Config.enableDistilledWater ? BucketNFluidHandler.FLUIDDISTILLEDWATER : FluidRegistry.WATER;
	}
	
    public TileCondenser() {
		super(3, INames.TE_CONDENSER, new FluidTankSingle(distilled(), 0, Config.capacityCondenser));
		this.setMaxworkTime(Config.dryTimeCondenser);
		this.setMaxCompost(Config.capacityCondenser);
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
				
		if(getHeatRate() > 0) {
			if(timer < getMaxTimer()) timer++;
			setMaxTimer(Config.cooldownCondenser / getHeatRate());
		}			
		else {
			if(timer > 0) timer--;
			setMaxTimer(Config.cooldownCondenser);
		}
		
		setTemp(20f + 979f * ((float)timer / (float)getMaxTimer()));
		
		if(getTemp() > 100f)
			setMaxworkTime((int) (Config.dryTimeCondenser / (getTemp() / 100f)));
		else
			setMaxworkTime(Config.dryTimeCondenser);
		
		if(canDry()) work();
		else this.setWorkTime(0);
		
		//WEnn alles durch ist
		if(this.getWorkTime() >= this.getMaxworkTime())
		{
			this.setWorkTime(0);
			dry();
		}
		if(fillTick >= 20) fillTick = 0;
	}
	
	private boolean canDry()
	{
		if(getTemp() < 100f) return false;
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
			if(getBarrel(getPos()))
			{
				if (fillTick == 20) {
					TileBarrel barrel = (TileBarrel) world.getTileEntity(pos.up());
										
					if (barrel.getMode() == null || barrel.getMode().getName() == "compost") {
						float amount = 0;
						if (barrel.getMode() != null) {
							amount = ((BarrelModeCompost) barrel.getMode()).getFillAmount();
						}
						if (amount <= 1f && getCompostMeter() >= 100f)
						{
							if(barrel.getMode() == null) barrel.setMode("compost");
							((BarrelModeCompost) barrel.getMode()).setFillAmount(amount + 0.1f);
							setCompostMeter(getCompostMeter() - 100f);
							barrel.markDirtyClient();
						}
					} 
					
				}
			}
    	if(Config.autoOutputItems) {
			insertToInventory(pos.north(), EnumFacing.SOUTH);
			insertToInventory(pos.south(), EnumFacing.NORTH);
			insertToInventory(pos.west(), EnumFacing.EAST);
			insertToInventory(pos.east(), EnumFacing.WEST);
		}
	}
	
	private boolean getBarrel(BlockPos pos)
	{
		TileBarrel barrel = (TileBarrel) world.getTileEntity(pos.up());
		return barrel != null;
	}
	
	private void fillToNeighborsTank()
	{
		if (fillTick == 20) {
			FluidStack water = new FluidStack(distilled(), Config.fluidOutputAmount);
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
		}
	}
	
	private void dry()
	{
		ItemStack material = this.getStackInSlot(0);
		
		if(NTMRegistryManager.COMPOST_REGISTRY.containsItem(material))
		{
			float waste = NTMRegistryManager.COMPOST_REGISTRY.getItem(material).getValue() * 1000;
			if(compostMeter <= (getMaxCompost() - waste)) compostMeter += waste;
		}
		if(NTMRegistryManager.CONDENSER_REGISTRY.containsItem(material))
		{
			int amount = NTMRegistryManager.CONDENSER_REGISTRY.getItem(material).getValue();
			if(amount > 0) this.getTank().fill(new FluidStack(distilled(), amount), true);
			
			material.shrink(1);
		}
	}
	
	private void fillToItemSlot()
	{
		ItemStack input  = this.getStackInSlot(2);
    	ItemStack output = this.getStackInSlot(1);
    	
    	if(!output.isEmpty()) return;
    	if(input.isEmpty()) return;
    	if(this.getTank().getFluidAmount() == 0) return;
    	
    	ItemStack copy = input.copy();
    	copy.setCount(1);
    	
		IFluidHandlerItem input_handler = FluidUtil.getFluidHandler(copy);
		
		if (input_handler != null)
		{
			FluidStack f = FluidUtil.tryFluidTransfer(input_handler, this.getTank(), Integer.MAX_VALUE, false);
			if (f == null) return;
			
			FluidUtil.tryFluidTransfer(input_handler, this.getTank(), Integer.MAX_VALUE, true);
			
			ItemStack container = input_handler.getContainer();
			
			this.setInventorySlotContents(1, container);
			this.decrStackSize(2, 1);
		}
		
		if(Config.enableDistilledWater) return;
		
		if(getStackInSlot(2).getItem() == Items.GLASS_BOTTLE && this.getStackInSlot(1).isEmpty())
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
        
        if(world.provider.doesWaterVaporize()) return 1;
        
        if(stateBelow == Blocks.AIR.getDefaultState()) {
            return 0;
        }

        return 0;
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
			if(stack.getItem() == Items.GLASS_BOTTLE && !Config.enableDistilledWater) return true;
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
	
    @Override
	public String getGuiID()
    {
        return "nethertweaksmod:gui_condenser";
    }
 
    @Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
    {
        return new ContainerCondenser(playerInventory, this);
    }
    
    @Override
    public void readFromNBT(NBTTagCompound nbt) {
    	fillTick = nbt.getInteger("fillTick");
    	setTemp(nbt.getFloat("temperature"));
    	timer = nbt.getInteger("timer");
    	setMaxTimer(nbt.getInteger("maxTimer"));
    	setCompostMeter(nbt.getFloat("compostMeter"));
    	super.readFromNBT(nbt);
    }
    
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
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
		this.temp = temp;
	}
	
	public int getMaxTimer() {
		return maxTimer;
	}

	public void setMaxTimer(int maxTimer) {
		this.maxTimer = maxTimer;
	}
	
	public float getCompostMeter() {
		return compostMeter;
	}

	public void setCompostMeter(float compostMeter) {
		this.compostMeter = compostMeter;
	}
	
	public float getMaxCompost() {
		return maxCompost;
	}

	public void setMaxCompost(float maxCompost) {
		this.maxCompost = maxCompost;
	}
}