package mod.nethertweaks.block.tile;

import static mod.nethertweaks.registry.manager.NTMRegistryManager.MILK_ENTITY_REGISTRY;

import java.util.ArrayList;
import java.util.Objects;

import javax.annotation.Nonnull;

import mod.nethertweaks.barrel.BarrelFluidHandler;
import mod.nethertweaks.barrel.BarrelItemHandler;
import mod.nethertweaks.barrel.IBarrelMode;
import mod.nethertweaks.block.Barrel;
import mod.nethertweaks.config.Config;
import mod.nethertweaks.init.ModBlocks;
import mod.nethertweaks.init.ModItems;
import mod.nethertweaks.modules.MooFluidsEtc;
import mod.nethertweaks.modules.moofluid.AbstractCowFactory;
import mod.nethertweaks.modules.moofluid.IAbstractCow;
import mod.nethertweaks.network.MessageBarrelModeUpdate;
import mod.nethertweaks.registry.registries.BarrelModeRegistry;
import mod.nethertweaks.registry.registries.BarrelModeRegistry.TriggerType;
import mod.nethertweaks.registry.registries.base.types.Milkable;
import mod.sfhcore.blocks.tiles.TileBase;
import mod.sfhcore.network.MessageCheckLight;
import mod.sfhcore.network.NetworkHandler;
import mod.sfhcore.util.TankUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
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
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;

public class TileBarrel extends TileBase implements ITickable {

	private IBarrelMode mode;
	private final BarrelItemHandler itemHandler;
	private final BarrelFluidHandler tank;
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
		this(ModBlocks.OAK_BARREL);
	}

	public TileBarrel(final Barrel block) {
		tier = block.getTier();
		blockType = block;
		itemHandler = new BarrelItemHandler(this);
		tank = new BarrelFluidHandler(this);
		entityWalkCooldown = 0;
	}

	public boolean onBlockActivated(final World world, final BlockPos pos, final IBlockState state, final EntityPlayer player, final EnumHand hand, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
		if (mode == null || mode.getName().equals("fluid")) {
			if (TankUtil.drainWaterFromBottle(this, player, tank))
				return true;

			if (TankUtil.drainWaterIntoBottle(this, player, tank))
				return true;

			final ItemStack stack = player.getHeldItem(hand);

			final IFluidHandler fluidHandler = getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side);
			boolean result = false;
			if (fluidHandler != null) result = FluidUtil.interactWithFluidHandler(player, hand, fluidHandler);

			if (!result)
				if (Objects.requireNonNull(fluidHandler).getTankProperties().length != 0)
					if (fluidHandler.getTankProperties()[0].getContents() != null)
						if (fluidHandler.getTankProperties()[0].getContents().getFluid() != null)
							if (fluidHandler.getTankProperties()[0].getContents().getFluid() == FluidRegistry.WATER)
								if (Objects.requireNonNull(ModItems.KITCHEN_KNIFE.getRegistryName()).equals(stack.getItem().getRegistryName()))
									getItemHandler().insertItem(0, stack, true);

			if (result) {
				if (!player.isCreative())
					stack.shrink(1);

				NetworkHandler.sendNBTUpdate(this);
				markDirty();
				if (getBlockType().getLightValue(state, world, pos) != world.getLight(pos)) {
					world.checkLight(pos);
					NetworkHandler.sendToAllAround(new MessageCheckLight(pos), this);
				}

				return true;
			}

			//Check for more fluid
			final IFluidHandler tank = getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side);
			final FluidStack bucketStack = FluidUtil.getFluidContained(stack);

			if (tank == null)
				return false;

			final FluidStack tankStack = tank.drain(Integer.MAX_VALUE, false);
			if (bucketStack != null && tankStack != null
					&& bucketStack.getFluid() == tankStack.getFluid()
					&& tank.fill(FluidUtil.getFluidContained(stack), false) != 0) {
				tank.drain(Fluid.BUCKET_VOLUME, true);
				result = FluidUtil.interactWithFluidHandler(player, hand, fluidHandler);

				if (result && !player.isCreative())
					stack.shrink(1);

				NetworkHandler.sendNBTUpdate(this);
			}
		}

		if (mode == null) {
			if (!player.getHeldItem(hand).isEmpty()) {
				final ItemStack stack = player.getHeldItem(hand);
				final ArrayList<IBarrelMode> modes = BarrelModeRegistry.getModes(TriggerType.ITEM);
				if (modes == null)
					return false;
				for (final IBarrelMode possibleMode : modes)
					if (possibleMode.isTriggerItemStack(stack)) {
						setMode(possibleMode.getName());
						NetworkHandler.sendToAllAround(new MessageBarrelModeUpdate(mode.getName(), this.pos), this);
						mode.onBlockActivated(world, this, pos, state, player, hand, side, hitX, hitY, hitZ);
						markDirty();
						getWorld().setBlockState(pos, state);

						if (getBlockType().getLightValue(state, world, pos) != world.getLight(pos)) {
							world.checkLight(pos);
							NetworkHandler.sendToAllAround(new MessageCheckLight(pos), this);
						}

						return true;
					}
			}
		} else {
			mode.onBlockActivated(world, this, pos, state, player, hand, side, hitX, hitY, hitZ);
			markDirty();

			if (getBlockType().getLightValue(state, world, pos) != world.getLight(pos)) {
				world.checkLight(pos);
				NetworkHandler.sendToAllAround(new MessageCheckLight(pos), this);
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
			final BlockPos plusY = new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ());
			if (getWorld().isRainingAt(plusY)) {
				final FluidStack stack = new FluidStack(FluidRegistry.WATER, 2);
				tank.fill(stack, true);
			}
		}
		if (mode != null)
			mode.update(this);

		if (getBlockType().getLightValue(getWorld().getBlockState(pos), getWorld(), pos) != getWorld().getLight(pos)) {
			getWorld().checkLight(pos);
			NetworkHandler.sendToAllAround(new MessageCheckLight(pos), this);
		}
	}

	@Override
	@Nonnull
	public NBTTagCompound writeToNBT(final NBTTagCompound tag) {
		tank.writeToNBT(tag);

		if (mode != null) {
			final NBTTagCompound barrelModeTag = new NBTTagCompound();
			mode.writeToNBT(barrelModeTag);
			barrelModeTag.setString("name", mode.getName());
			tag.setTag("mode", barrelModeTag);
		}

		final NBTTagCompound handlerTag = itemHandler.serializeNBT();
		tag.setTag("itemHandler", handlerTag);
		tag.setInteger("barrelTier", tier);

		return super.writeToNBT(tag);

	}

	@Override
	public void readFromNBT(final NBTTagCompound tag) {
		tank.readFromNBT(tag);
		if (tag.hasKey("mode")) {
			final NBTTagCompound barrelModeTag = (NBTTagCompound) tag.getTag("mode");
			this.setMode(barrelModeTag.getString("name"));
			if (mode != null)
				mode.readFromNBT(barrelModeTag);
		}

		if (tag.hasKey("itemHandler"))
			itemHandler.deserializeNBT((NBTTagCompound) tag.getTag("itemHandler"));

		if (tag.hasKey("barrelTier"))
			tier = tag.getInteger("barrelTier");
		super.readFromNBT(tag);
	}

	public void setMode(final String modeName) {
		try {
			if (modeName.equals("null"))
				mode = null;
			else
				mode = BarrelModeRegistry.getModeByName(modeName).getClass().newInstance();
			markDirty();
		} catch (final Exception e) {
			e.printStackTrace(); //Naughty
		}
	}

	public void setMode(final IBarrelMode mode) {
		this.mode = mode;
		markDirty();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getCapability(@Nonnull final Capability<T> capability, final EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return (T) itemHandler;
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
			return (T) tank;

		return super.getCapability(capability, facing);
	}

	@Override
	public boolean hasCapability(@Nonnull final Capability<?> capability, final EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ||
				capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY ||
				super.hasCapability(capability, facing);
	}

	public void entityOnTop(final World world, final Entity entityIn) {
		final long currentTime = world.getTotalWorldTime(); //Get the current time, shouldn't be affected by in-game /time command
		if (currentTime < entityWalkCooldown) return; // Cooldown hasn't elapsed, do nothing

		if(MooFluidsEtc.isLoaded() && Config.enableMooFluid){
			final IAbstractCow cow = AbstractCowFactory.getCow(entityIn);
			if(cow != null){
				moofluidsEntityWalk(world, cow);
				return;
			}
		}

		final Milkable milk = MILK_ENTITY_REGISTRY.getMilkable(entityIn);
		if (milk == null) return; // Not a valid recipe

		// Attempt to add the fluid if it is a valid fluid
		final Fluid result = FluidRegistry.getFluid(milk.getResult());
		if (result != null)
			tank.fill(new FluidStack(result, milk.getAmount()), true);

		//Set the new cooldown time
		entityWalkCooldown = currentTime + milk.getCoolDown();
	}

	private void moofluidsEntityWalk(final World world, final IAbstractCow cow) {
		final Fluid result = cow.getFluid();
		if(result == null || !MooFluidsEtc.fluidIsAllowed(result))
			return;

		// Amount to fill
		final int amount = Math.min(tank.getCapacity() - tank.getFluidAmount(), Config.fillAmount);

		// Cow needs to cool down more
		if(cow.getAvailableFluid() < amount)
			return;

		// Do fill
		final int filled = tank.fill(new FluidStack(result, amount), true);

		// Reset everyone's timers
		if(filled == 0)
			return;

		final int appliedcooldown = cow.addCooldownEquivalent(filled);
		entityWalkCooldown = world.getTotalWorldTime() + appliedcooldown;
	}

	@Override
	public boolean hasFastRenderer() {
		return true;
	}
}