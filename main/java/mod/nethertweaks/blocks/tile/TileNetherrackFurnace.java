package mod.nethertweaks.blocks.tile;

import javax.annotation.Nullable;

import mod.nethertweaks.Config;
import mod.nethertweaks.blocks.NetherrackFurnace;
import mod.nethertweaks.blocks.container.ContainerNetherrackFurnace;
import mod.nethertweaks.capabilities.CapabilityHeatManager;
import mod.nethertweaks.handler.BlockHandler;
import mod.nethertweaks.interfaces.INames;
import mod.nethertweaks.registries.manager.NTMRegistryManager;
import mod.sfhcore.blocks.tiles.TileInventory;
import mod.sfhcore.util.BlockInfo;
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
import net.minecraft.util.math.BlockPos;
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
        if (!canSmelt())
        {
        	this.workTime = 0;
        	return;
        }
        
        ++this.workTime;
        System.out.println(workTime);
        if(this.workTime < maxworkTime) return;
            smeltItem();
            this.workTime = 0;
    }

    /**
     * Returns true if the furnace can smelt an item, i.e. has a source item, destination stack isn't full, etc.
     * Aaaaaaaaaaaaaaaaaaaand if there's a source of heat beneath it
     */
    private boolean canSmelt()
    {
        if(getMaxWorktime() <= 0) return false;
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

	private int getMaxWorktime()
	{
		int heat = getHeatRate();
				if(heat < 1 && heat > 0)
			this.maxworkTime *= this.maxworkTime;
		if(heat >= 1)
			this.maxworkTime = this.maxworkTime /= heat;
		
		return 0;	
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