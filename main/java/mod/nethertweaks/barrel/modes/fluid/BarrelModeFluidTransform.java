package mod.nethertweaks.barrel.modes.fluid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mod.nethertweaks.barrel.IBarrelMode;
import mod.nethertweaks.block.tile.TileBarrel;
import mod.nethertweaks.registry.manager.NTMRegistryManager;
import mod.nethertweaks.registry.registries.base.types.FluidTransformer;
import mod.sfhcore.network.NetworkHandler;
import mod.sfhcore.texturing.Color;
import mod.sfhcore.util.BlockInfo;
import mod.sfhcore.util.Util;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.player.EntityPlayer;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemStackHandler;

public class BarrelModeFluidTransform implements IBarrelMode {

	private final BarrelItemHandlerFluid handler = new BarrelItemHandlerFluid(null);
	private FluidStack inputStack, outputStack;
	private ItemStack inputItemStack;
	private float progress = 0;
	private FluidTransformer transformer;

	public FluidStack getInputStack() {
		return inputStack;
	}

	public void setInputStack(final FluidStack inputStack) {
		this.inputStack = inputStack;
	}

	public FluidStack getOutputStack() {
		return outputStack;
	}

	public void setOutputStack(final FluidStack outputStack) {
		this.outputStack = outputStack;
	}

	public FluidTransformer getTransformer() {
		return transformer;
	}

	public void setTransformer(final FluidTransformer transformer) {
		this.transformer = transformer;
	}

	public float getProgress() {
		return progress;
	}

	@Override
	public void writeToNBT(final NBTTagCompound tag) {
		if (inputStack != null) {
			final NBTTagCompound inputTag = inputStack.writeToNBT(new NBTTagCompound());
			tag.setTag("inputTag", inputTag);
		}

		if (outputStack != null) {
			final NBTTagCompound outputTag = outputStack.writeToNBT(new NBTTagCompound());
			tag.setTag("outputTag", outputTag);
		}

		tag.setFloat("progress", progress);
	}

	@Override
	public void readFromNBT(final NBTTagCompound tag) {
		if (tag.hasKey("inputTag")) {
			final NBTTagCompound inputTag = (NBTTagCompound) tag.getTag("inputTag");
			inputStack = FluidStack.loadFluidStackFromNBT(inputTag);
		}
		if (tag.hasKey("outputTag")) {
			final NBTTagCompound outputTag = (NBTTagCompound) tag.getTag("outputTag");
			outputStack = FluidStack.loadFluidStackFromNBT(outputTag);
		}
		if (tag.hasKey("progress"))
			progress = tag.getFloat("progress");

	}

	@Override
	public boolean isTriggerItemStack(final ItemStack stack) {
		return false;
	}

	@Override
	public boolean isTriggerFluidStack(final FluidStack stack) {
		return false;
	}

	@Override
	public String getName() {
		return "fluidTransform";
	}

	@Override
	public void onBlockActivated(final World world, final TileBarrel barrel, final BlockPos pos, final IBlockState state, final EntityPlayer player, final EnumHand hand, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
	}

	@Override
	@SideOnly(Side.CLIENT)
	public TextureAtlasSprite getTextureForRender(final TileBarrel barrel) {
		if (progress < 0.5)
			return Util.getTextureFromFluidStack(inputStack);
		else
			return Util.getTextureFromFluidStack(outputStack);
	}

	@Override
	public Color getColorForRender() {
		return Color.average(Util.blackColor, Util.whiteColor,
				2 * Math.abs(progress - 0.5F));
	}

	@Override
	public float getFilledLevelForRender(final TileBarrel barrel) {
		return 0.9375F;
	}

	@Override
	public void update(final TileBarrel barrel) {
		if (transformer == null && inputStack != null && inputStack.getFluid() != null && outputStack != null && outputStack.getFluid() != null)
			transformer = NTMRegistryManager.FLUID_TRANSFORM_REGISTRY.getFluidTransformer(inputStack
					.getFluid().getName(), outputStack.getFluid().getName());
		if (transformer == null)
			return;
		if (progress < 1) {
			final int numberOfBlocks = Util.getNumSurroundingBlocksAtLeastOneOf(
					transformer.getTransformingBlocks(),
					barrel.getPos().add(0, -1, 0), barrel.getWorld())
					+ Util.getNumSurroundingBlocksAtLeastOneOf(
							transformer.getTransformingBlocks(),
							barrel.getPos(), barrel.getWorld());
			if (numberOfBlocks > 0) {
				progress += numberOfBlocks * 1.0 / transformer.getDuration();

				if (barrel.getWorld().rand.nextDouble() < 0.005) {
					boolean spawned = false;
					final ArrayList<BlockInfo> blockList = new ArrayList<>(
							Arrays.asList(transformer.getTransformingBlocks()));
					for (int xShift = -1; xShift <= 1; xShift++)
						for (int zShift = -1; zShift <= 1; zShift++)
							if (!spawned) {
								final BlockPos testPos = barrel.getPos().add(xShift,
										-1, zShift);
								if (blockList.contains(new BlockInfo(barrel
										.getWorld().getBlockState(testPos)))
										&& barrel.getWorld().isAirBlock(
												testPos.add(0, 1, 0))) {
									final BlockInfo[] toSpawn = transformer
											.getBlocksToSpawn();
									if (toSpawn.length > 0) {
										barrel.getWorld()
										.setBlockState(
												testPos.add(0, 1, 0),
												toSpawn[barrel
												        .getWorld().rand
												        .nextInt(toSpawn.length)]
												        		.getBlockState());
										spawned = true;
									}
								}
							}
				}
			}
			NetworkHandler.sendNBTUpdate(barrel);
		}

		if (progress >= 1) {
			barrel.setMode("fluid");
			final FluidTank tank = barrel.getMode().getFluidHandler(barrel);
			final Fluid fluid = FluidRegistry.getFluid(transformer.getOutputFluid());
			tank.setFluid(new FluidStack(fluid, 1000));
			NetworkHandler.sendNBTUpdate(barrel);
		}
	}

	@Override
	public void addItem(final ItemStack stack, final TileBarrel barrel) {
		handler.setBarrel(barrel);
		if(handler.getStackInSlot(0).isEmpty())
			handler.insertItem(0, stack, false);
	}

	@Override
	public ItemStackHandler getHandler(final TileBarrel barrel) {
		return null;
	}

	@Override
	public FluidTank getFluidHandler(final TileBarrel barrel) {
		return null;
	}

	@Override
	public boolean canFillWithFluid(final TileBarrel barrel) {
		return false;
	}

	@Override
	public List<String> getWailaTooltip(final TileBarrel barrel,
			final List<String> currenttip) {
		currenttip.add("Transforming: " + Math.round(progress * 100) + "%");
		return currenttip;
	}

}