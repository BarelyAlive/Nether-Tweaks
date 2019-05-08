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
import mod.sfhcore.network.MessageNBTUpdate;
import mod.sfhcore.network.NetworkHandler;
import mod.sfhcore.util.BlockInfo;
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

public class TileCondenser extends TileFluidInventory implements net.minecraftforge.fluids.capability.IFluidHandler {
	
	private static List<Fluid> lf = new ArrayList<Fluid>();
	
	static
	{
		lf.add(FluidRegistry.WATER);
	}
	
    public TileCondenser() {
		super(3, INames.TECONDENSER, 16000);
		this.maxworkTime = Config.dryTimeCondenser;
		setAcceptedFluids(lf);
	}

	@Override
    public void update()
	{
		if(world.isRemote) return;
		markDirtyClient();
		if(!checkInv()) return;
		
		this.workTime++;
		if (this.workTime >= this.maxworkTime)
		{
			this.workTime = 0;
			dry();
		}
	}
	
	public void dry()
	{
		ItemStack material = this.getStackInSlot(0);
		ItemStack bucket = this.getStackInSlot(1);
		int filledinothertank = 0;
		if(CondenserRegistry.getDryable(material) == null) return;
		int amount = CondenserRegistry.getDryable(material).getValue();
		if (this.world.getTileEntity(this.getPos().east()) instanceof IFluidHandler)
		{
			IFluidHandler handler = (IFluidHandler) this.world.getTileEntity(this.getPos().east());
			filledinothertank = this.fillTankInBlock(handler, amount);
		}
		else if (this.world.getTileEntity(this.getPos().south()) instanceof IFluidHandler)
		{
			IFluidHandler handler = (IFluidHandler) this.world.getTileEntity(this.getPos().south());
			filledinothertank = this.fillTankInBlock(handler, amount);
		}
		else if (this.world.getTileEntity(this.getPos().west()) instanceof IFluidHandler)
		{
			IFluidHandler handler = (IFluidHandler) this.world.getTileEntity(this.getPos().west());
			filledinothertank = this.fillTankInBlock(handler, amount);
		}
		else if (this.world.getTileEntity(this.getPos().north()) instanceof IFluidHandler)
		{
			IFluidHandler handler = (IFluidHandler) this.world.getTileEntity(this.getPos().north());
			filledinothertank = this.fillTankInBlock(handler, amount);
		}
		
		amount -= filledinothertank;
		
		if (amount > 0)
		{
			this.tank.amount += amount;
		}
		
		material.shrink(1);
		return;
	}
	
	private void fillToItemSlot()
	{
		ItemStack slot1 = this.getStackInSlot(1);
		if(slot1.isEmpty()) return;
		if(FluidUtil.getFluidHandler(slot1) == null) return;
		if(!hasAcceptedFluids(FluidUtil.getFluidContained(slot1).getFluid())) return;
    	if(this.fillable() == 0) return;
    	if(FluidUtil.getFluidHandler(slot1).fill(this.getFluid(), false) < this.getFluidAmount()) return;
    	if (this.getStackInSlot(1).getCount() != 1) return;
    	
		IFluidHandlerItem outHandler = FluidUtil.getFluidHandler(this.getStackInSlot(1));
		if (this.getStackInSlot(1).getItem().equals(Items.BUCKET))
		{
			this.setInventorySlotContents(1, new ItemStack(Items.WATER_BUCKET));
			this.drain(1000,  true); 
		}
		else if(outHandler.fill(new FluidStack(this.tank.getFluid(), 1000), false) >= 1000)
		{
			outHandler.fill(new FluidStack(this.tank.getFluid(), 1000), true);
			this.drain(1000,  true); 
		}
	}
	
	/**
	 * @param tank IFluidHandler of the Block to which the Water should go
	 * @param amount of what should be transfered
	 * @return amount of what amount is transfered
	 */
	private int fillTankInBlock (IFluidHandler tank, int amount)
	{
		int return_amount = tank.fill(new FluidStack(FluidRegistry.WATER, amount), false);
		if (return_amount == amount)
		{
			tank.fill(new FluidStack(FluidRegistry.WATER, amount), true);
			return amount;
		}
		else if (return_amount != 0)
		{
			tank.fill(new FluidStack(FluidRegistry.WATER, return_amount), true);
			return return_amount;
		}
		return 0;
	}
	
	public int getHeatRate() {
        BlockPos posBelow = pos.add(0, -1, 0);
        IBlockState stateBelow = getWorld().getBlockState(posBelow);

        if (stateBelow == Blocks.AIR.getDefaultState()) {
            return 0;
        }

        // Try to match metadata
        int heat = NTMRegistryManager.HEAT_REGISTRY.getHeatAmount(new BlockInfo(stateBelow));

        // Try to match without metadata
        if (heat == 0 && !Item.getItemFromBlock(stateBelow.getBlock()).getHasSubtypes())
            heat = NTMRegistryManager.HEAT_REGISTRY.getHeatAmount(new BlockInfo(stateBelow.getBlock()));

        if (heat != 0)
            return heat;

        TileEntity tile = getWorld().getTileEntity(posBelow);

        if (tile != null && tile.hasCapability(CapabilityHeatManager.HEAT_CAPABILITY, EnumFacing.UP)) {
            return tile.getCapability(CapabilityHeatManager.HEAT_CAPABILITY, EnumFacing.UP).getHeatRate();
        }

        return 0;
    }
	
	private boolean checkInv()
	{
		if(this.getStackInSlot(0).isEmpty()) return false;
		if(!CondenserRegistry.containsItem(machineItemStacks.get(0))) return false;
		
		Dryable result = CondenserRegistry.getDryable(machineItemStacks.get(0));
		if (result == null) return false;
		if(this.fillable() == 0) return false;
		if(getMaxWorktime() <= 0) return false;
		
		return true;
	}
	
	private int getMaxWorktime()
	{
		int heat = getHeatRate();
				if(heat < 1 && heat > 0)
			this.maxworkTime *= this.maxworkTime;
		if(heat >= 1)
			this.maxworkTime = this.maxworkTime /= heat;
		
		return 0;	
	}
	
	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		IFluidHandlerItem handler = FluidUtil.getFluidHandler(stack);
		if(handler == null) return false;
		if(FluidUtil.getFluidContained(stack).getFluid() != FluidRegistry.WATER) return false;
		if(this.getStackInSlot(1).getCount() == stack.getMaxStackSize()) return false;
		
		if (index == 0)
			if (!CondenserRegistry.containsItem(stack)) return false;
		
		return true;
	}
	
	@Override
	public boolean isItemValidForSlotToExtract(int index, ItemStack itemStack)
	{
		if (index != 1) return false;
		return true;
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