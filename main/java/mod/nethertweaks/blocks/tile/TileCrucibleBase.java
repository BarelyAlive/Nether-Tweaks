package mod.nethertweaks.blocks.tile;

import javax.annotation.Nonnull;

import mod.nethertweaks.handler.CrucibleItemHandler;
import mod.nethertweaks.registries.registries.CrucibleRegistry;
import mod.nethertweaks.registry.types.Meltable;
import mod.sfhcore.blocks.tiles.TileBase;
import mod.sfhcore.client.renderers.SpriteColor;
import mod.sfhcore.fluid.FluidTankBase;
import mod.sfhcore.texturing.Color;
import mod.sfhcore.util.BlockInfo;
import mod.sfhcore.util.ItemInfo;
import mod.sfhcore.util.Util;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;

public abstract class TileCrucibleBase extends TileBase implements ITickable {
    public static final int MAX_ITEMS = 4;

    protected FluidTankBase tank;
    protected int solidAmount;

    private ItemInfo currentItem = ItemInfo.EMPTY;
    protected int ticksSinceLast = 0;

    protected CrucibleItemHandler itemHandler;
    protected CrucibleRegistry crucibleRegistry;

    public FluidTankBase getTank() {
		return tank;
	}

	public int getSolidAmount() {
		return solidAmount;
	}

	public ItemInfo getCurrentItem() {
		return currentItem;
	}

	public CrucibleItemHandler getItemHandler() {
		return itemHandler;
	}

	public CrucibleRegistry getCrucibleRegistry() {
		return crucibleRegistry;
	}

	public TileCrucibleBase(CrucibleRegistry crucibleRegistry) {
        tank = new FluidTankBase(4 * Fluid.BUCKET_VOLUME, this);
        tank.setCanFill(false);

        itemHandler = new CrucibleItemHandler(this, crucibleRegistry);
        this.crucibleRegistry = crucibleRegistry;
    }

    @Override
    public abstract void update();

    public abstract int getHeatRate();

    /**
     * Returns array of FLUID color and Item Color
     * ITEMCOLOR is index 0
     * FLUIDCOLOR is index 1
     */
    @SideOnly(Side.CLIENT)
    public SpriteColor[] getSpriteAndColor() {
        SpriteColor[] spriteColors = new SpriteColor[2];

        int noItems = itemHandler.getStackInSlot(0).isEmpty() ? 0 : itemHandler.getStackInSlot(0).getCount();
        if (noItems == 0 && !getCurrentItem().isValid() && tank.getFluidAmount() == 0) //Empty!
            return spriteColors;

        FluidStack fluid = tank.getFluid();
        if (fluid != null && fluid.amount > 0) //Nothing being melted.
        {
            Color color = new Color(fluid.getFluid().getColor(), false);
            spriteColors[1] = new SpriteColor(Util.getTextureFromFluidStack(fluid), color);
        }

        IBlockState block = null;
        Color color = Util.whiteColor;

        if (getCurrentItem().isValid()) {
            Meltable meltable = crucibleRegistry.getMeltable(getCurrentItem());
            BlockInfo override = meltable.getTextureOverride();
            if (override.isValid())
                block = override.getBlockState();
            else block = getCurrentItem().getBlockState();
            color = new Color(Minecraft.getMinecraft().getBlockColors().colorMultiplier(block, world, pos, 0), true);
        }
        if (block != null) {
            spriteColors[0] = new SpriteColor(Util.getTextureFromBlockState(block), color);
        }

        return spriteColors;
    }

    @SideOnly(Side.CLIENT)
    public float getFilledAmount() {
        int itemCount = itemHandler.getStackInSlot(0).isEmpty() ? 0 : itemHandler.getStackInSlot(0).getCount();
        if (itemCount == 0 && !getCurrentItem().isValid() && tank.getFluidAmount() == 0) //Empty!
            return 0;

        float fluidProportion = ((float) tank.getFluidAmount()) / tank.getCapacity();
        if (itemCount == 0 && !getCurrentItem().isValid()) //Nothing being melted.
            return fluidProportion;

        float solidProportion = ((float) itemCount) / MAX_ITEMS;
        if (getCurrentItem().isValid()) {
            Meltable meltable = crucibleRegistry.getMeltable(getCurrentItem());
            if (meltable != Meltable.getEMPTY())
                solidProportion += ((double) solidAmount) / (MAX_ITEMS * meltable.getAmount());
        }

        return solidProportion > fluidProportion ? solidProportion : fluidProportion;
    }

    @SideOnly(Side.CLIENT)
    public float getFluidProportion() {
        return ((float) tank.getFluidAmount()) / tank.getCapacity();
    }

    @SideOnly(Side.CLIENT)
    public float getSolidProportion() {
        int itemCount = itemHandler.getStackInSlot(0).isEmpty() ? 0 : itemHandler.getStackInSlot(0).getCount();
        float solidProportion = ((float) itemCount) / MAX_ITEMS;
        if (getCurrentItem().isValid()) {
            Meltable meltable = crucibleRegistry.getMeltable(getCurrentItem());
            if (meltable != Meltable.getEMPTY())
                solidProportion += ((double) solidAmount) / (MAX_ITEMS * meltable.getAmount());
        }
        return solidProportion;
    }


    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ, IFluidHandler handler) {
        ItemStack stack = player.getHeldItem(hand);
        if (stack.isEmpty()) {
            return false;
        }

        Boolean result = FluidUtil.interactWithFluidHandler(player, hand, handler);

        if (result) {
            if (!player.isCreative()) {
                stack.shrink(1);
            }

            markDirtyClient();
            return true;
        }

        //Adding a meltable.
        ItemStack addStack = stack.copy();
        addStack.setCount(1);
        ItemStack insertStack = itemHandler.insertItem(0, addStack, true);
        if (!ItemStack.areItemStacksEqual(addStack, insertStack)) {
            itemHandler.insertItem(0, addStack, false);

            if (!getCurrentItem().isValid()) setCurrentItem(new ItemInfo(stack));

            if (!player.isCreative()) stack.shrink(1);

            markDirtyClient();
            return true;
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            // itemHandler.setTe(this);
            return (T) itemHandler;
        }
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
            return (T) tank;

        return super.getCapability(capability, facing);
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ||
                capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY ||
                super.hasCapability(capability, facing);
    }

    @Override
    @Nonnull
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        if (getCurrentItem().isValid()) {
            NBTTagCompound currentItemTag = getCurrentItem().writeToNBT(new NBTTagCompound());
            tag.setTag("currentItem", currentItemTag);
        }

        tag.setInteger("solidAmount", solidAmount);

        NBTTagCompound itemHandlerTag = itemHandler.serializeNBT();
        tag.setTag("itemHandler", itemHandlerTag);

        NBTTagCompound tankTag = tank.writeToNBT(new NBTTagCompound());
        tag.setTag("tank", tankTag);

        return super.writeToNBT(tag);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        if (tag.hasKey("currentItem")) {
            setCurrentItem(ItemInfo.readFromNBT(tag.getCompoundTag("currentItem")));
        } else {
            setCurrentItem(ItemInfo.EMPTY);
        }

        solidAmount = tag.getInteger("solidAmount");

        if (tag.hasKey("itemHandler")) {
            itemHandler.deserializeNBT((NBTTagCompound) tag.getTag("itemHandler"));
        }

        if (tag.hasKey("tank")) {
            tank.readFromNBT((NBTTagCompound) tag.getTag("tank"));
        }

        super.readFromNBT(tag);
    }

    @Override
    public boolean hasFastRenderer() {
        return true;
    }

	public void setCurrentItem(ItemInfo currentItem) {
		this.currentItem = currentItem;
	}
}
