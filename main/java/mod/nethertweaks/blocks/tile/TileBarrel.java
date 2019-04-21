package mod.nethertweaks.blocks.tile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import mod.nethertweaks.Config;
import mod.nethertweaks.barrel.BarrelFluidHandler;
import mod.nethertweaks.barrel.BarrelItemHandler;
import mod.nethertweaks.barrel.IBarrelMode;
import mod.nethertweaks.blocks.BlockBarrel;
import mod.nethertweaks.handler.BlockHandler;
import mod.nethertweaks.modules.MooFluidsEtc;
import mod.nethertweaks.modules.MooFluid.*;
import mod.nethertweaks.network.MessageBarrelModeUpdate;
import mod.nethertweaks.network.MessageCheckLight;
import mod.nethertweaks.network.NetworkHandlerNTM;
import mod.nethertweaks.registries.registries.BarrelModeRegistry;
import mod.nethertweaks.registries.registries.BarrelModeRegistry.TriggerType;
import mod.nethertweaks.registry.types.Milkable;
import mod.nethertweaks.util.TankUtil;

import java.util.ArrayList;

import static mod.nethertweaks.registries.manager.NTMRegistryManager.MILK_ENTITY_REGISTRY;

public class TileBarrel extends BaseTileEntity implements ITickable {

    private IBarrelMode mode;
    private BarrelItemHandler itemHandler;
    private BarrelFluidHandler tank;
    private int tier;
    private long entityWalkCooldown; //The time after which the barrel will attempt to milk an Entity. Based on the world clock

    public IBarrelMode getMode() {
		return mode;
	}

	public BarrelItemHandler getItemHandler() {
		return itemHandler;
	}

	public BarrelFluidHandler getTank() {
		return tank;
	}

	public int getTier() {
		return tier;
	}

	public long getEntityWalkCooldown() {
		return entityWalkCooldown;
	}

	public TileBarrel() {
        this((BlockBarrel) BlockHandler.BARREL);
    }

    public TileBarrel(BlockBarrel block) {
        this.tier = block.getTier();
        this.blockType = block;
        itemHandler = new BarrelItemHandler(this);
        tank = new BarrelFluidHandler(this);
        this.entityWalkCooldown = 0;
    }

    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (mode == null || mode.getName().equals("fluid")) {
            if (TankUtil.drainWaterFromBottle(this, player, tank))
                return true;

            if (tank != null && TankUtil.drainWaterIntoBottle(this, player, tank))
                return true;


            ItemStack stack = player.getHeldItem(hand);

            IFluidHandler fluidHandler = getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side);
            boolean result = false;
            if (fluidHandler != null) result = FluidUtil.interactWithFluidHandler(player, hand, fluidHandler);

            if (result) {
                if (!player.isCreative()) {
                    stack.shrink(1);
                }

                NetworkHandlerNTM.sendNBTUpdate(this);
                markDirty();
                if (getBlockType().getLightValue(state, world, pos) != world.getLight(pos)) {
                    world.checkLight(pos);
                    NetworkHandlerNTM.sendToAllAround(new MessageCheckLight(pos), this);
                }

                return true;
            }

            //Check for more fluid
            IFluidHandler tank = getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side);
            FluidStack bucketStack = FluidUtil.getFluidContained(stack);

            if (tank == null) {
                return false;
            }

            FluidStack tankStack = tank.drain(Integer.MAX_VALUE, false);
            if (bucketStack != null && tankStack != null
                    && bucketStack.getFluid() == tankStack.getFluid()
                    && tank.fill(FluidUtil.getFluidContained(stack), false) != 0) {
                tank.drain(Fluid.BUCKET_VOLUME, true);
                if (fluidHandler != null)
                    result = FluidUtil.interactWithFluidHandler(player, hand, fluidHandler);

                if (result && !player.isCreative()) {
                    stack.shrink(1);
                }

                NetworkHandlerNTM.sendNBTUpdate(this);
            }
        }

        if (mode == null) {
            if (!player.getHeldItem(hand).isEmpty()) {
                ItemStack stack = player.getHeldItem(hand);
                ArrayList<IBarrelMode> modes = BarrelModeRegistry.getModes(TriggerType.ITEM);
                if (modes == null)
                    return false;
                for (IBarrelMode possibleMode : modes) {
                    if (possibleMode.isTriggerItemStack(stack)) {
                        setMode(possibleMode.getName());
                        NetworkHandlerNTM.sendToAllAround(new MessageBarrelModeUpdate(mode.getName(), this.pos), this);
                        mode.onBlockActivated(world, this, pos, state, player, hand, side, hitX, hitY, hitZ);
                        this.markDirty();
                        this.getWorld().setBlockState(pos, state);

                        if (getBlockType().getLightValue(state, world, pos) != world.getLight(pos)) {
                            world.checkLight(pos);
                            NetworkHandlerNTM.sendToAllAround(new MessageCheckLight(pos), this);
                        }

                        return true;
                    }
                }
            }
        } else {
            mode.onBlockActivated(world, this, pos, state, player, hand, side, hitX, hitY, hitZ);
            markDirty();

            if (getBlockType().getLightValue(state, world, pos) != world.getLight(pos)) {
                world.checkLight(pos);
                NetworkHandlerNTM.sendToAllAround(new MessageCheckLight(pos), this);
            }

            return true;
        }

        return true;
    }

    @Override
    public void update() {
        if (getWorld().isRemote)
            return;

        if (Config.shouldBarrelsFillWithRain && (mode == null || mode.getName().equalsIgnoreCase("fluid"))) {
            BlockPos plusY = new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ());
            if (getWorld().isRainingAt(plusY)) {
                FluidStack stack = new FluidStack(FluidRegistry.WATER, 2);
                tank.fill(stack, true);
            }
        }
        if (mode != null)
            mode.update(this);

        if (getBlockType().getLightValue(getWorld().getBlockState(pos), getWorld(), pos) != getWorld().getLight(pos)) {
            getWorld().checkLight(pos);
            NetworkHandlerNTM.sendToAllAround(new MessageCheckLight(pos), this);
        }
    }

    @Override
    @Nonnull
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        tank.writeToNBT(tag);

        if (mode != null) {
            NBTTagCompound barrelModeTag = new NBTTagCompound();
            mode.writeToNBT(barrelModeTag);
            barrelModeTag.setString("name", mode.getName());
            tag.setTag("mode", barrelModeTag);
        }

        NBTTagCompound handlerTag = itemHandler.serializeNBT();
        tag.setTag("itemHandler", handlerTag);
        tag.setInteger("barrelTier", tier);

        return super.writeToNBT(tag);

    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        tank.readFromNBT(tag);
        if (tag.hasKey("mode")) {
            NBTTagCompound barrelModeTag = (NBTTagCompound) tag.getTag("mode");
            this.setMode(barrelModeTag.getString("name"));
            if (mode != null)
                mode.readFromNBT(barrelModeTag);
        }

        if (tag.hasKey("itemHandler")) {
            itemHandler.deserializeNBT((NBTTagCompound) tag.getTag("itemHandler"));
        }

        if (tag.hasKey("barrelTier")) {
            tier = tag.getInteger("barrelTier");
        }
        super.readFromNBT(tag);
    }

    public void setMode(String modeName) {
        try {
            if (modeName.equals("null"))
                mode = null;
            else
                mode = BarrelModeRegistry.getModeByName(modeName).getClass().newInstance();
            this.markDirty();
        } catch (Exception e) {
            e.printStackTrace(); //Naughty
        }
    }

    public void setMode(IBarrelMode mode) {
        this.mode = mode;
        this.markDirty();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
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

    public void entityOnTop(World world, Entity entityIn) {
        long currentTime = world.getTotalWorldTime(); //Get the current time, shouldn't be affected by in-game /time command
        if (currentTime < this.entityWalkCooldown) return; // Cooldown hasn't elapsed, do nothing

        if(MooFluidsEtc.isLoaded() && Config.enableMooFluid){
            IAbstractCow cow = AbstractCowFactory.getCow(entityIn);
            if(cow != null){
                this.moofluidsEntityWalk(world, cow);
                return;
            }
        }

        Milkable milk = MILK_ENTITY_REGISTRY.getMilkable(entityIn);
        if (milk == null) return; // Not a valid recipe

        // Attempt to add the fluid if it is a valid fluid
        Fluid result = FluidRegistry.getFluid(milk.getResult());
        if (result != null)
            this.tank.fill(new FluidStack(result, milk.getAmount()), true);

        //Set the new cooldown time
        this.entityWalkCooldown = currentTime + milk.getCoolDown();
    }

    private void moofluidsEntityWalk(World world, IAbstractCow cow) {
        Fluid result = cow.getFluid();
        if(result == null || !MooFluidsEtc.fluidIsAllowed(result))
            return;

        // Amount to fill
        int amount = Math.min(tank.getCapacity() - tank.getFluidAmount(), Config.fillAmount);

        // Cow needs to cool down more
        if(cow.getAvailableFluid() < amount)
            return;

        // Do fill
        int filled = this.tank.fill(new FluidStack(result, amount), true);

        // Reset everyone's timers
        if(filled == 0)
            return;

        int appliedcooldown = cow.addCooldownEquivalent(filled);
        this.entityWalkCooldown = world.getTotalWorldTime() + appliedcooldown;
    }

    @Override
    public boolean hasFastRenderer() {
        return true;
    }
}