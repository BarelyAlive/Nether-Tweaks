package mod.nethertweaks.barrel.modes.compost;

import java.util.List;

import mod.nethertweaks.barrel.IBarrelMode;
import mod.nethertweaks.blocks.tile.TileBarrel;
import mod.nethertweaks.config.Config;
import mod.nethertweaks.network.MessageBarrelModeUpdate;
import mod.nethertweaks.network.MessageCompostUpdate;
import mod.nethertweaks.registries.manager.NTMRegistryManager;
import mod.nethertweaks.registry.types.Compostable;
import mod.sfhcore.network.NetworkHandler;
import mod.sfhcore.texturing.Color;
import mod.sfhcore.util.ItemInfo;
import mod.sfhcore.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemStackHandler;

public class BarrelModeCompost implements IBarrelMode {

	private final Color whiteColor = new Color(1f, 1f, 1f, 1f);
	private final BarrelItemHandlerCompost handler;
	private float fillAmount = 0;
	private Color color = new Color("EEA96D");
	private Color originalColor;
	private float progress = 0;
	private IBlockState compostState;

	public float getFillAmount() {
		return fillAmount;
	}

	public void setFillAmount(final float fillAmount) {
		this.fillAmount = fillAmount;
	}

	public Color getOriginalColor() {
		return originalColor;
	}

	public void setOriginalColor(final Color originalColor) {
		this.originalColor = originalColor;
	}

	public float getProgress() {
		return progress;
	}

	public void setProgress(final float progress) {
		this.progress = progress;
	}

	public IBlockState getCompostState() {
		return compostState;
	}

	public void setCompostState(final IBlockState compostState) {
		this.compostState = compostState;
	}

	public void setColor(final Color color) {
		this.color = color;
	}

	public BarrelModeCompost() {
		handler = new BarrelItemHandlerCompost(null);
	}

	@Override
	public void onBlockActivated(final World world, final TileBarrel barrel, final BlockPos pos, final IBlockState state, final EntityPlayer player, final EnumHand hand, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
		if (fillAmount == 0)
			if (!player.getHeldItem(hand).isEmpty()) {
				ItemInfo info = new ItemInfo(player.getHeldItem(hand));
				if (NTMRegistryManager.COMPOST_REGISTRY.containsItem(info)) {
					Compostable comp = NTMRegistryManager.COMPOST_REGISTRY.getItem(info);
					compostState = comp.getCompostBlock().getBlockState();
					NetworkHandler.sendNBTUpdate(barrel);
				}
			}
		if (fillAmount < 1 && compostState != null) {
			if (!player.getHeldItem(hand).isEmpty()) {
				ItemStack stack = player.getHeldItem(hand);
				ItemInfo info = new ItemInfo(player.getHeldItem(hand));
				Compostable comp = NTMRegistryManager.COMPOST_REGISTRY.getItem(info);

				if (!comp.getCompostBlock().isValid()) return;

				IBlockState testState = comp.getCompostBlock().getBlockState();

				if (NTMRegistryManager.COMPOST_REGISTRY.containsItem(info) && compostState.equals(testState)) {
					Compostable compost = NTMRegistryManager.COMPOST_REGISTRY.getItem(info);
					Color compColor = compost.getColor();

					boolean isFirst = fillAmount == 0;

					fillAmount += compost.getValue();
					if (fillAmount > 1)
						fillAmount = 1;
					if (!player.capabilities.isCreativeMode)
						player.getHeldItem(hand).shrink(1);
					NetworkHandler.sendToAllAround(new MessageCompostUpdate(fillAmount, compColor, stack, progress, comp.getValue(), barrel.getPos(), isFirst), barrel);
					barrel.markDirty();
				}
			}
		} else if (progress >= 1) {
			Util.dropItemInWorld(barrel, player, new ItemStack(compostState == null ? Blocks.AIR : compostState.getBlock(), 1, compostState == null ? 0 : compostState.getBlock().getMetaFromState(compostState)), 0.02f);
			removeItem(barrel);
		}

	}

	public void removeItem(final TileBarrel barrel) {
		progress = 0;
		fillAmount = 0;
		color = new Color("EEA96D");
		handler.setStackInSlot(0, ItemStack.EMPTY);
		compostState = null;
		NetworkHandler.sendToAllAround(new MessageCompostUpdate(fillAmount, color, ItemStack.EMPTY, progress,0.0f,  barrel.getPos(), false), barrel);
		barrel.setMode("null");
		IBlockState state = barrel.getWorld().getBlockState(barrel.getPos());
		NetworkHandler.sendToAllAround(new MessageBarrelModeUpdate("null", barrel.getPos()), barrel);
		barrel.getWorld().setBlockState(barrel.getPos(), state);
	}

	@Override
	@SuppressWarnings("deprecation")
	public void addItem(final ItemStack stack, final TileBarrel barrel) {
		if (fillAmount < 1)
			if (stack != null && !stack.isEmpty()) {
				ItemInfo info = new ItemInfo(stack);
				Compostable comp = NTMRegistryManager.COMPOST_REGISTRY.getItem(info);
				IBlockState testState = comp.getCompostBlock().getBlockState();

				if (NTMRegistryManager.COMPOST_REGISTRY.containsItem(info) && compostState == null)
					compostState = testState;

				if (NTMRegistryManager.COMPOST_REGISTRY.containsItem(info) && compostState.equals(testState)) {
					Compostable compost = NTMRegistryManager.COMPOST_REGISTRY.getItem(info);

					boolean isFirst = fillAmount == 0;
					fillAmount += compost.getValue();
					if (fillAmount > 1)
						fillAmount = 1;
					NetworkHandler.sendToAllAround(new MessageCompostUpdate(fillAmount, comp.getColor(), stack, progress, comp.getValue(), barrel.getPos(), isFirst), barrel);
					// NetworkHandler.sendToAllAround(new MessageCompostUpdate(this.fillAmount, this.color, this.progress, barrel.getPos()), barrel);
					barrel.markDirty();
				}
			}
	}

	@Override
	public void update(final TileBarrel barrel) {
		if (fillAmount >= 1 && progress < 1) {
			if (progress == 0)
				originalColor = color;

			progress += 1.0 / Config.compostingTicks;
			color = Color.average(originalColor, whiteColor, progress);

			// TODO: maybe don't send it _every_ tick
			NetworkHandler.sendToAllAround(new MessageCompostUpdate(fillAmount, color, ItemStack.EMPTY, progress, 0.0f, barrel.getPos(), false), barrel);

			barrel.markDirty();
		}

		if (progress >= 1 && compostState != null) {
			barrel.setMode("block");
			NetworkHandler.sendToAllAround(new MessageBarrelModeUpdate("block", barrel.getPos()), barrel);

			barrel.getMode().addItem(new ItemStack(compostState.getBlock(), 1, compostState.getBlock().getMetaFromState(compostState)), barrel);
		}
	}

	@Override
	public String getName() {
		return "compost";
	}

	@Override
	public List<String> getWailaTooltip(final TileBarrel barrel, final List<String> currenttip) {
		if (compostState != null)
			currenttip.add("Composting " + compostState.getBlock().getLocalizedName());
		if (progress == 0)
			currenttip.add(Math.round(fillAmount * 100) + "% full");
		else
			currenttip.add(Math.round(progress * 100) + "% complete");
		return currenttip;
	}

	@Override
	public boolean isTriggerItemStack(final ItemStack stack) {
		return NTMRegistryManager.COMPOST_REGISTRY.containsItem(stack);
	}

	@Override
	public boolean isTriggerFluidStack(final FluidStack stack) {
		return false;
	}

	@Override
	public void writeToNBT(final NBTTagCompound tag) {
		tag.setFloat("fillAmount", fillAmount);
		tag.setInteger("color", color.toInt());
		if (originalColor != null)
			tag.setInteger("originalColor", originalColor.toInt());
		tag.setFloat("progress", progress);
		if (compostState != null) {
			tag.setString("block", Block.REGISTRY.getNameForObject(compostState.getBlock()).toString());
			tag.setInteger("meta", compostState.getBlock().getMetaFromState(compostState));
		}

	}

	@SuppressWarnings("deprecation")
	@Override
	public void readFromNBT(final NBTTagCompound tag) {
		fillAmount = tag.getFloat("fillAmount");
		color = new Color(tag.getInteger("color"));
		if (tag.hasKey("originalColor"))
			originalColor = new Color(tag.getInteger("originalColor"));
		progress = tag.getFloat("progress");
		if (tag.hasKey("block")) {
			Block block = Block.REGISTRY.getObject(new ResourceLocation(tag.getString("block")));
			compostState = block.getStateFromMeta(tag.getInteger("meta"));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public TextureAtlasSprite getTextureForRender(final TileBarrel barrel) {
		if (compostState == null)
			return Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes()
					.getTexture(Blocks.DIRT.getDefaultState());
		return Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes()
				.getTexture(compostState);
	}

	@Override
	public float getFilledLevelForRender(final TileBarrel barrel) {
		return fillAmount * 0.9375F;
	}

	@Override
	public Color getColorForRender() {
		return color;
	}

	@Override
	public ItemStackHandler getHandler(final TileBarrel barrel) {
		handler.setBarrel(barrel);
		return handler;
	}

	@Override
	public FluidTank getFluidHandler(final TileBarrel barrel) {
		return null;
	}

	@Override
	public boolean canFillWithFluid(final TileBarrel barrel) {
		return false;
	}

}