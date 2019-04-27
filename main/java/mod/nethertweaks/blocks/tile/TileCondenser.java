package mod.nethertweaks.blocks.tile;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import mod.nethertweaks.Config;
import mod.nethertweaks.blocks.container.ContainerCondenser;
import mod.nethertweaks.handler.BucketNFluidHandler;
import mod.nethertweaks.interfaces.INames;
import mod.nethertweaks.network.MessageNBTUpdate;
import mod.nethertweaks.network.NetworkHandlerNTM;
import mod.nethertweaks.registries.registries.CompostRegistry;
import mod.nethertweaks.registries.registries.CondenserRegistry;
import mod.nethertweaks.registry.types.Dryable;
import mod.sfhcore.helper.StackUtils;
import mod.sfhcore.tileentities.TEBaseFluidInventory;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
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

public class TileCondenser extends TEBaseFluidInventory implements net.minecraftforge.fluids.capability.IFluidHandler {
	
	private List<Fluid> lf = new ArrayList<Fluid>();
	
    public TileCondenser() {
		super(3, INames.TECONDENSER, 16000);
		this.maxworkTime = Config.dryTimeCondenser;
		this.lf.add(FluidRegistry.WATER);
		setAcceptedFluids(lf);
	}

	@Override
    public void update() {
		this.world = getWorld();
		if(!world.isRemote) {
			NetworkHandlerNTM.INSTANCE.sendToAll(new MessageNBTUpdate(this));
			if (this.tank.amount >= 1000)
			{
				if (!this.getStackInSlot(1).isEmpty())
				{
					if (this.getStackInSlot(1).getCount() == 1)
					{
						IFluidHandlerItem outHandler = FluidUtil.getFluidHandler(this.getStackInSlot(1));
						if (this.getStackInSlot(1).getItem().equals(Items.BUCKET))
						{
							this.setInventorySlotContents(1, new ItemStack(Items.WATER_BUCKET));
							this.drain(1000,  true); 
						}
						else if (outHandler != null)
						{
							if(outHandler.fill(new FluidStack(this.tank.getFluid(), 1000), false) >= 1000)
							{
								outHandler.fill(new FluidStack(this.tank.getFluid(), 1000), true);
								this.drain(1000,  true); 
							}
						}
					}
				}
			}
			if(checkInv()) {
				this.workTime++;
				if (this.workTime >= this.maxworkTime)
				{
					this.workTime = 0;
					dry();
				}
			}
		}
	}
	
	public void dry()
	{
		ItemStack material = this.getStackInSlot(0);
		ItemStack bucket = this.getStackInSlot(1);
		int filledinothertank = 0;
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
		
		amount = amount - filledinothertank;
		
		if (amount > 0)
		{
			this.tank.amount += amount;
		}
		
		StackUtils.substractFromStackSize(material, 1);
		return;
	}
	
	/**
	 * @param tank IFluidHandler of Block to with the Water should go
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
	
	public boolean checkHeatSource()
	{
		World world = getWorld();
		Block block = world.getBlockState(pos.add(0, -1, 0)).getBlock();
		if (world.isBlockLoaded(pos))
		{
			if (block.getDefaultState().getMaterial() == Material.FIRE)
			{
				maxworkTime = ((maxworkTime / 10) * 9);
				return true;
			}
			if (block.getDefaultState().getMaterial() == Material.LAVA)
			{
				if (block instanceof BlockFluidClassic)
				{
					int lavatime = ((maxworkTime / 10) * 8);
					int lavaheat = FluidRegistry.LAVA.getTemperature();
					int blockheat = BlockFluidClassic.getTemperature(world, pos);
					if (maxworkTime > lavatime)
					{
						int heattime = lavatime * Math.floorDiv(lavaheat, blockheat);
						if (lavatime > heattime)
						{
							maxworkTime = heattime;
						}
						else
						{
							maxworkTime = lavatime;
						}
					}
				}
				return true;
			}
		}
		return false;
	}
	
	public boolean checkInv()
	{
		if (!this.getStackInSlot(0).isEmpty())
		{
			if(CondenserRegistry.containsItem(machineItemStacks.get(0)))
			{
				Dryable result = CondenserRegistry.getDryable(machineItemStacks.get(0));
				if (result != null)
				{
					if(this.MAX_CAPACITY > this.tank.amount)
					{
						if (checkHeatSource())
						{
							return true;
						}
					}
				}
			}
		}
		workTime = 0;
		return false;
	}
	
	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		if (index == 0)
		{
			if (CondenserRegistry.containsItem(stack))
			{
				return true;
			}
		}
		else if (index == 1)
		{
			ItemStack slot = this.getStackInSlot(1);
			if ((slot.getCount() + stack.getCount()) > 1)
			{
				return false;
			}
			if (stack.getItem().equals(Items.BUCKET))
			{
				return true;
			}
			else if (stack.getItem().equals(Items.WATER_BUCKET))
			{
				return true;
			}
			IFluidHandlerItem handler = FluidUtil.getFluidHandler(stack);
			if(handler != null)
			{
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean isItemValidForSlotToExtract(int index, ItemStack itemStack) {
		if (index == 1)
		{
			return true;
		}
		return false;
	}
	
    public String getGuiID()
    {
        return "nethertweaksmod:GuiCondenser";
    }
 
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
    {
        return new ContainerCondenser(playerInventory, this);
    }
}