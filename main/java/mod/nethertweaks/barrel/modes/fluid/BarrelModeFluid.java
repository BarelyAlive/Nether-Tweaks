package mod.nethertweaks.barrel.modes.fluid;

import java.util.List;
import java.util.Objects;

import mod.nethertweaks.barrel.BarrelFluidHandler;
import mod.nethertweaks.barrel.IBarrelMode;
import mod.nethertweaks.blocks.tile.TileBarrel;
import mod.nethertweaks.network.MessageBarrelModeUpdate;
import mod.nethertweaks.registries.manager.NTMRegistryManager;
import mod.nethertweaks.registry.types.FluidTransformer;
import mod.sfhcore.network.NetworkHandler;
import mod.sfhcore.texturing.Color;
import mod.sfhcore.util.BlockInfo;
import mod.sfhcore.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemStackHandler;

public class BarrelModeFluid implements IBarrelMode {

	private final BarrelItemHandlerFluid handler;
	public int workTime;
	public int maxWorkTime = 1200;
	public Color impossFluidColor = new Color(25, 75, 75, 255);


	public BarrelModeFluid() {
		handler = new BarrelItemHandlerFluid(null);
		workTime = 0;
	}

	@Override
	public void writeToNBT(final NBTTagCompound tag) {
		tag.setInteger("maxWorkTime", maxWorkTime);
		tag.setInteger("workTime", workTime);
	}

	@Override
	public void readFromNBT(final NBTTagCompound tag) {
		maxWorkTime = tag.getInteger("maxWorkTime");
		workTime = tag.getInteger("workTime");
	}

	@Override
	public boolean isTriggerItemStack(final ItemStack stack) {
		return false;
	}

	@Override
	public boolean isTriggerFluidStack(final FluidStack stack) {
		return stack != null;
	}

	@Override
	public String getName() {
		return "fluid";
	}

	@Override
	public List<String> getWailaTooltip(final TileBarrel barrel, final List<String> currenttip) {
		if (barrel.getTank().getFluid() != null) {
			currenttip.add(barrel.getTank().getFluid().getLocalizedName());
			if (workTime > 0)
				currenttip.add("Transformation: " + (float)((double)maxWorkTime / (double)workTime) * 100.0F + " %");
			else
				currenttip.add("Amount: " + barrel.getTank().getFluidAmount() + "mb");
		} else
			currenttip.add("Empty");

		return currenttip;
	}

	@Override
	public void onBlockActivated(final World world, final TileBarrel barrel, final BlockPos pos, final IBlockState state, final EntityPlayer player, final EnumHand hand, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
		ItemStack stack = player.getHeldItem(hand);

		if (!stack.isEmpty()) {
			ItemStack remainder = getHandler(barrel).insertItem(0, stack, false);

			int size = remainder.isEmpty() ? 0 : remainder.getCount();

			if (stack.getItem().hasContainerItem(stack)) {
				ItemStack container = stack.getItem().getContainerItem(stack);

				// Should always be 1 but LET'S JUST MAKE SURE
				container.setCount(stack.getCount() - size);

				if (!player.inventory.addItemStackToInventory(container))
					player.getEntityWorld().spawnEntity(new EntityItem(player.getEntityWorld(), player.posX, player.posY, player.posZ, container));
			}

			player.setHeldItem(hand, remainder);
		}

	}

	@Override
	@SideOnly(Side.CLIENT)
	public TextureAtlasSprite getTextureForRender(final TileBarrel barrel) {
		/*
    	if (workTime > 0)
    	{
	    	if (((float)(((double)this.maxWorkTime) / ((double)this.maxWorkTime))) < 0.5F)
	    	{
	    		return Util.getTextureFromFluidStack(barrel.getTank().getFluid());
	    	}
	    	else
	    	{
	            String transformFluidItem = NTMRegistryManager.FLUID_ITEM_FLUID_REGISTRY.getFluidForTransformation(barrel.getTank().getFluid().getFluid(), barrel.getItemHandler().getStackInSlot(0));
	            if(transformFluidItem != null)
	            {
	            	return Util.getTextureFromFluidStack(FluidRegistry.getFluidStack(transformFluidItem, barrel.getTank().getCapacity()));
	            }
	    	}
    	}
		 */
		return Util.getTextureFromFluidStack(barrel.getTank().getFluid());
	}

	@Override
	public Color getColorForRender() {
		if (workTime == 0)
			return Util.whiteColor;
		else
			return Color.average(Util.whiteColor, Util.greenColor,  //new Color(0.09f, 0.29f, 0.29f, 1f),
					2 * Math.abs((float)((double)workTime / (double)maxWorkTime)));

	}

	@Override
	public float getFilledLevelForRender(final TileBarrel barrel) {
		double amount = barrel.getTank().getFluidAmount();
		return (float) (amount / Fluid.BUCKET_VOLUME * 0.9375F);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void update(final TileBarrel barrel) {
		// Fluids on top.
		if (barrel.getTank().getFluid() != null) {
			FluidTank tank = barrel.getTank();
			if (tank.getFluid() == null || tank.getFluid().amount != tank.getCapacity())
				return;

			Fluid fluidInBarrel = tank.getFluid().getFluid();

			BlockPos barrelPos = barrel.getPos();
			BlockPos pos = new BlockPos(barrelPos.getX(), barrelPos.getY() + 1, barrelPos.getZ());
			Block onTop = barrel.getWorld().getBlockState(pos).getBlock();

			Fluid fluidOnTop = null;
			if (onTop instanceof BlockLiquid)
				fluidOnTop = onTop.getMaterial(barrel.getWorld().getBlockState(pos)) == Material.WATER
				? FluidRegistry.WATER : FluidRegistry.LAVA;

			if (!onTop.equals(Blocks.AIR) && onTop instanceof IFluidBlock)
				fluidOnTop = ((IFluidBlock) onTop).getFluid();

			if (NTMRegistryManager.FLUID_ON_TOP_REGISTRY.isValidRecipe(fluidInBarrel, fluidOnTop)) {
				BlockInfo info = NTMRegistryManager.FLUID_ON_TOP_REGISTRY.getTransformedBlock(fluidInBarrel, Objects.requireNonNull(fluidOnTop));
				tank.drain(tank.getCapacity(), true);
				barrel.setMode("block");
				NetworkHandler.sendToAllAround(new MessageBarrelModeUpdate("block", barrel.getPos()), barrel);

				barrel.getMode().addItem(info.getItemStack(), barrel);

				return;
			}

			// Fluid transforming time!
			if (NTMRegistryManager.FLUID_TRANSFORM_REGISTRY.containsKey(Objects.requireNonNull(barrel.getTank().getFluid()).getFluid().getName())) {
				List<FluidTransformer> transformers = NTMRegistryManager.FLUID_TRANSFORM_REGISTRY
						.getFluidTransformers(barrel.getTank().getFluid().getFluid().getName());

				boolean found = false;
				for (int radius = 0; radius <= 2; radius++) {
					for (FluidTransformer transformer : transformers)
						if (!NTMRegistryManager.BARREL_LIQUID_BLACKLIST_REGISTRY.isBlacklisted(barrel.getTier(), transformer.getOutputFluid())
								&& (Util.isSurroundingBlocksAtLeastOneOf(transformer.getTransformingBlocks(),
										barrel.getPos().add(0, -1, 0), barrel.getWorld(), radius)
										|| Util.isSurroundingBlocksAtLeastOneOf(transformer.getTransformingBlocks(),
												barrel.getPos(), barrel.getWorld(), radius))) {
							// Time to start the process.
							FluidStack fstack = tank.getFluid();
							tank.setFluid(null);

							barrel.setMode("fluidTransform");
							BarrelModeFluidTransform mode = (BarrelModeFluidTransform) barrel.getMode();

							mode.setTransformer(transformer);
							mode.setInputStack(fstack);
							mode.setOutputStack(FluidRegistry.getFluidStack(transformer.getOutputFluid(), 1000));

							NetworkHandler.sendNBTUpdate(barrel);
							found = true;
						}
					if (found) break;
				}
			}
			barrel.getItemHandler().getStackInSlot(0);
			if(barrel.getTank().getFluid() != null)
			{
				String transformFluidItem = NTMRegistryManager.FLUID_ITEM_FLUID_REGISTRY.getFluidForTransformation(barrel.getTank().getFluid().getFluid(), barrel.getItemHandler().getStackInSlot(0));
				if(transformFluidItem != null)
				{
					workTime++;
					if(maxWorkTime < workTime)
					{
						barrel.getMode().getFluidHandler(barrel).drain(Integer.MAX_VALUE, true);
						tank.fill(FluidRegistry.getFluidStack(transformFluidItem, tank.getCapacity()), true);
					}
					NetworkHandler.sendNBTUpdate(barrel);
				}
			}
		}
	}

	@Override
	public void addItem(final ItemStack stack, final TileBarrel barrel) {
		if (workTime != 0)
			return;
		FluidStack fstack = barrel.getMode().getFluidHandler(barrel).getFluid();
		handler.setBarrel(barrel);
		if(handler.getStackInSlot(0).isEmpty())
			handler.insertItem(0, stack, false);
	}

	@Override
	public ItemStackHandler getHandler(final TileBarrel barrel) {
		handler.setBarrel(barrel);
		return handler;
	}

	@Override
	public FluidTank getFluidHandler(final TileBarrel barrel) {
		BarrelFluidHandler handler = barrel.getTank();
		handler.setBarrel(barrel);
		return handler;
	}

	@Override
	public boolean canFillWithFluid(final TileBarrel barrel) {
		return true;
	}

}