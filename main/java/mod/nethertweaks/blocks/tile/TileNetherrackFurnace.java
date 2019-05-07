package mod.nethertweaks.blocks.tile;

import javax.annotation.Nullable;

import mod.nethertweaks.Config;
import mod.nethertweaks.blocks.NetherrackFurnace;
import mod.nethertweaks.blocks.container.ContainerNetherrackFurnace;
import mod.nethertweaks.handler.BlockHandler;
import mod.nethertweaks.interfaces.INames;
import mod.sfhcore.blocks.tiles.TileInventory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerFurnace;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.walkers.ItemStackDataLists;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemStackHandler;

public class TileNetherrackFurnace extends TileInventory{
    
	
    public TileNetherrackFurnace() {
		super(2, INames.TENETHERRACKFURNACE);
		this.maxworkTime = Config.burnTimeFurnace;
	}

	@Override
    public void update()
	{
		if(world.isRemote) return;
		markDirtyClient();
        NetherrackFurnace.setState(isWorking(), this.world, this.pos);
        if (!canSmelt()) return;
        
        ++this.workTime;		
        if (this.workTime >= maxworkTime)
        {
            smeltItem();
            this.workTime = 0;
        }
    }
    
    public boolean checkHeatSource(){ 
    	Block block = world.getBlockState(pos.add(0, -1, 0)).getBlock();
		IBlockState state = block.getDefaultState();
		
		if(!world.isBlockLoaded(pos)) return false;
		
		if (state.getMaterial() == Material.FIRE)
		{
			maxworkTime = ((maxworkTime / 10) * 9);
			return true;
		}
		
		if(!(block instanceof BlockFluidClassic)) return false;
		
		int blockheat = BlockFluidClassic.getTemperature(world, pos);
		int lavaheat = FluidRegistry.LAVA.getTemperature();
		if(blockheat < lavaheat) return false;
		
		int lavatime = ((maxworkTime / 10) * 8);
		
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
		return true;
	}

    /**
     * Returns true if the furnace can smelt an item, i.e. has a source item, destination stack isn't full, etc.
     * Aaaaaaaaaaaaaaaaaaaand if there's a source of heat beneath it
     */
    private boolean canSmelt()
    {
    	if(!checkHeatSource()) return false;
    	if (((ItemStack)this.machineItemStacks.get(0)).isEmpty()) return false;           
        ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(this.machineItemStacks.get(0));
        if (itemstack.isEmpty()) return false;       
        ItemStack itemstack1 = this.machineItemStacks.get(1);
        if(itemstack1.isEmpty()) return true;       
        if(!itemstack1.isItemEqual(itemstack)) return false;
        if (itemstack1.getCount() + itemstack.getCount() <= this.getInventoryStackLimit()
        		&& itemstack1.getCount() + itemstack.getCount() <= itemstack1.getMaxStackSize())  // Forge fix: make furnace respect stack sizes in furnace recipes
            return true;
        
            return itemstack1.getCount() + itemstack.getCount() <= itemstack.getMaxStackSize(); // Forge fix: make furnace respect stack sizes in furnace recipes
    }

    /**
     * Turn one item from the furnace source stack into the appropriate smelted item in the furnace result stack
     */
    public void smeltItem()
    {
        ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(this.machineItemStacks.get(0));
        
        if (this.machineItemStacks.get(1).isEmpty())
        {
            this.machineItemStacks.set(1, itemstack.copy());
        }
        else if (this.machineItemStacks.get(1).getItem() == itemstack.getItem())
        {
            machineItemStacks.get(1).grow(itemstack.getCount());
        }

        if (this.machineItemStacks.get(0).getCount() <= 0)
        {
            this.machineItemStacks.set(0, ItemStack.EMPTY);
        }
        else
        {
            this.machineItemStacks.get(0).shrink(1);
        }
    }
	
	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		ItemStack slot;
		ItemStack result;
		switch(index)
		{
		case 0:
			slot = this.getStackInSlot(1);
			if(slot.getCount() == slot.getMaxStackSize()) return false;
				
			result = FurnaceRecipes.instance().getSmeltingResult(stack);
			if(result == ItemStack.EMPTY) return false;
			return true;
		}
		return false;
	}
	
	@Override
	public boolean isItemValidForSlotToExtract(int index, ItemStack itemStack) {
		if (index == 1) return true;
		return false;
	}
	    
	@Override
    public String getGuiID()
    {
        return "nethertweaksmod:GuiNetherrackFurnace";
    }
    
    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
    {
        return new ContainerNetherrackFurnace(playerInventory, this);
    }
}