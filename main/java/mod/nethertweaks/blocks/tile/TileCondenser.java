package mod.nethertweaks.blocks.tile;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import mod.nethertweaks.Config;
import mod.nethertweaks.blocks.container.ContainerCondenser;
import mod.nethertweaks.capabilities.CapabilityHeatManager;
import mod.nethertweaks.handler.BucketNFluidHandler;
import mod.nethertweaks.interfaces.INames;
import mod.nethertweaks.registries.manager.NTMRegistryManager;
import mod.nethertweaks.registries.registries.CompostRegistry;
import mod.nethertweaks.registries.registries.CondenserRegistry;
import mod.nethertweaks.registries.registries.HeatRegistry;
import mod.nethertweaks.registry.types.Dryable;
import mod.sfhcore.blocks.tiles.TileFluidInventory;
import mod.sfhcore.fluid.FluidTankSingle;
import mod.sfhcore.network.MessageNBTUpdate;
import mod.sfhcore.network.NetworkHandler;
import mod.sfhcore.util.BlockInfo;
import mod.sfhcore.util.TankUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.entity.layers.LayerWolfCollar;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerFurnace;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public class TileCondenser extends TileFluidInventory
{	
    public TileCondenser() {
		super(3, INames.TECONDENSER, new FluidTankSingle(FluidRegistry.WATER, 0, 16000));
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
		if(!checkInv())
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
	
	private void checkInputOutput()
	{
		extractFromInventory(pos.up(), EnumFacing.DOWN);
    	insertToInventory(pos.north(), EnumFacing.UP);
    	insertToInventory(pos.south(), EnumFacing.UP);
    	insertToInventory(pos.west(), EnumFacing.UP);
    	insertToInventory(pos.east(), EnumFacing.UP);
    	insertToInventory(pos.north(), EnumFacing.WEST);
    	insertToInventory(pos.south(), EnumFacing.WEST);
    	insertToInventory(pos.west(), EnumFacing.WEST);
    	insertToInventory(pos.east(), EnumFacing.WEST);
    	insertToInventory(pos.north(), EnumFacing.SOUTH);
    	insertToInventory(pos.south(), EnumFacing.SOUTH);
    	insertToInventory(pos.west(), EnumFacing.SOUTH);
    	insertToInventory(pos.east(), EnumFacing.SOUTH);
    	insertToInventory(pos.north(), EnumFacing.NORTH);
    	insertToInventory(pos.south(), EnumFacing.NORTH);
    	insertToInventory(pos.west(), EnumFacing.NORTH);
    	insertToInventory(pos.east(), EnumFacing.NORTH);
    	insertToInventory(pos.north(), EnumFacing.EAST);
    	insertToInventory(pos.south(), EnumFacing.EAST);
    	insertToInventory(pos.west(), EnumFacing.EAST);
    	insertToInventory(pos.east(), EnumFacing.EAST);
	}
	
	private void fillToNeighborsTank()
	{
		FluidStack water = this.getTank().getFluid();
		
		if(water != null) {
			BlockPos north = this.getPos().north();
			BlockPos east = this.getPos().east();
			BlockPos south = this.getPos().south();
			BlockPos west = this.getPos().west();
			
			//Check FluidHandler
			IFluidHandler hnorth = FluidUtil.getFluidHandler(world, north, EnumFacing.NORTH);
			IFluidHandler heast = FluidUtil.getFluidHandler(world, east, EnumFacing.EAST);
			IFluidHandler hsouth = FluidUtil.getFluidHandler(world, south, EnumFacing.SOUTH);
			IFluidHandler hwest = FluidUtil.getFluidHandler(world, west, EnumFacing.WEST);
			if(hnorth != null)
				FluidUtil.tryFluidTransfer(hnorth, this.getTank(), water, true);
			else if(heast != null)
				FluidUtil.tryFluidTransfer(heast, this.getTank(), water, true);
			else if(hsouth != null)
				FluidUtil.tryFluidTransfer(hsouth, this.getTank(), water, true);
			else if(hwest != null)
				FluidUtil.tryFluidTransfer(hwest, this.getTank(), water, true);
		}
	}
	
	private void dry()
	{
		ItemStack material = this.getStackInSlot(0);
		ItemStack bucket = this.getStackInSlot(1);
		if(CondenserRegistry.getDryable(material) == null) return;
		int amount = CondenserRegistry.getDryable(material).getValue();
		
		if(amount > 0)
		{
			this.getTank().fill(new FluidStack(FluidRegistry.WATER, amount), true);
		}
		
		material.shrink(1);
		return;
	}
	
	private void fillToItemSlot()
	{
		ItemStack input = getStackInSlot(2);
    	ItemStack output = getStackInSlot(1);
    	
    	if(output.getCount() == output.getMaxStackSize()) return;
    	if(input.isEmpty()) return;
    	if(this.getTank().getFluidAmount() == 0) return;
    	
		FluidStack input_stack = FluidUtil.getFluidContained(input);
		IFluidHandlerItem input_handler = FluidUtil.getFluidHandler(input);
		
		if(input_handler != null) {
		    	if(!(FluidUtil.getFluidHandler(input).fill(this.getTank().getFluid(), false) <= this.getTank().getFluidAmount())) return;
		    	if(!ItemStack.areItemsEqual(input, output) && output.getMaxStackSize() == output.getCount()) return;
				if(FluidUtil.tryFluidTransfer(input_handler, this.getTank(), this.emptyRoom(), true) == null)
					return;
				if(output.isEmpty()) {
					setInventorySlotContents(1, input_handler.getContainer());
					getStackInSlot(2).shrink(1);
				} else {
					getStackInSlot(1).grow(1);
					getStackInSlot(2).shrink(1);
				}
		}
		else if(getStackInSlot(2).getItem() == Items.GLASS_BOTTLE && (output.isEmpty() || ItemStack.areItemsEqual(output, TankUtil.WATER_BOTTLE)))
		{
			if(this.getTank().getFluid() != null && this.getTank().getFluidAmount() >= 250 && this.getTank().getFluid().getFluid() == FluidRegistry.WATER)
			{
                this.getTank().drain(250, true);
                
                if(output.isEmpty()) {
    				setInventorySlotContents(1, TankUtil.WATER_BOTTLE.copy());
    				getStackInSlot(2).shrink(1);
    			} else if(ItemStack.areItemsEqual(output, TankUtil.WATER_BOTTLE)){
    				getStackInSlot(1).grow(1);
    				getStackInSlot(2).shrink(1);
    			}
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
        
        if(heat != 0)
            return heat;

        TileEntity tile = getWorld().getTileEntity(posBelow);

        if(tile != null && tile.hasCapability(CapabilityHeatManager.HEAT_CAPABILITY, EnumFacing.UP)) {
            return tile.getCapability(CapabilityHeatManager.HEAT_CAPABILITY, EnumFacing.UP).getHeatRate();
        }

        return 0;
    }
	
	private boolean checkInv()
	{
		if(this.getStackInSlot(0).isEmpty()) return false;
		if(!CondenserRegistry.containsItem(getStackInSlot(0))) return false;
		
		Dryable result = CondenserRegistry.getDryable(getStackInSlot(0));
		if(result == null) return false;
		if(this.emptyRoom() == 0) return false;
		if(getMaxworkTime() <= 0) return false;
		
		return true;
	}
	
	private int calcMaxWorktime()
	{
		int heat = getHeatRate();
		int workTime = Config.dryTimeCondenser;
		if(heat != 0) {
			workTime *= 3;
			workTime /= heat;
			this.setMaxworkTime(workTime);
		}
		return workTime;	
	}
	
	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		IFluidHandlerItem handler = FluidUtil.getFluidHandler(stack);
		if(this.getStackInSlot(index).getCount() == this.getStackInSlot(index).getMaxStackSize()) return false;
		
		if(index == 0)
			return CondenserRegistry.containsItem(stack);
		if(index == 1)
			return false;
		if(index == 2)
		{
			if(stack.getItem() == Items.GLASS_BOTTLE) return true;
			if(handler == null) return false;
			if(FluidUtil.tryFluidTransfer(handler, this.getTank(), Integer.MAX_VALUE, false) == null) return false;
		}
			
		return true;
	}
	
	@Override
	public boolean isItemValidForSlotToExtract(int index, ItemStack itemStack)
	{
		if(index == 1) return true;
		return false;
	}
	
    public String getGuiID()
    {
        return "nethertweaksmod:gui_condenser";
    }
 
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
    {
        return new ContainerCondenser(playerInventory, this);
    }
}