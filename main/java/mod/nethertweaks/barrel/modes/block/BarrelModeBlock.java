package mod.nethertweaks.barrel.modes.block;

import java.util.List;

import mod.nethertweaks.barrel.IBarrelMode;
import mod.nethertweaks.block.tile.TileBarrel;
import mod.nethertweaks.network.MessageBarrelModeUpdate;
import mod.sfhcore.network.NetworkHandler;
import mod.sfhcore.texturing.Color;
import mod.sfhcore.util.ItemInfo;
import mod.sfhcore.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemStackHandler;

public class BarrelModeBlock implements IBarrelMode {

	private final BarrelItemHandlerBlock handler = new BarrelItemHandlerBlock(null);
	private ItemInfo block;

	public ItemInfo getBlock() {
		return block;
	}

	public void setBlock(final ItemInfo block) {
		this.block = block;
	}

	@Override
	public void writeToNBT(final NBTTagCompound tag) {
		if (block != null)
			tag.setString("block", block.toString());
		if (!handler.getStackInSlot(0).isEmpty())
			handler.getStackInSlot(0).writeToNBT(tag);
	}

	@Override
	public void readFromNBT(final NBTTagCompound tag) {
		if (tag.hasKey("block"))
			block = new ItemInfo(tag.getString("block"));

		handler.setStackInSlot(0, new ItemStack(tag));
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
		return "block";
	}

	@Override
	public List<String> getWailaTooltip(final TileBarrel barrel, final List<String> currenttip) {
		if (!handler.getStackInSlot(0).isEmpty())
			currenttip.add(handler.getStackInSlot(0).getDisplayName());
		return currenttip;
	}

	@Override
	public void onBlockActivated(final World world, final TileBarrel barrel, final BlockPos pos, final IBlockState state, final EntityPlayer player, final EnumHand hand, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
		if (!handler.getStackInSlot(0).isEmpty()) {
			Util.dropItemInWorld(barrel, player, handler.getStackInSlot(0), 0.02);
			handler.setBarrel(barrel);
			handler.setStackInSlot(0, ItemStack.EMPTY);
			barrel.setMode("null");
			NetworkHandler.sendToAllAround(new MessageBarrelModeUpdate("null", barrel.getPos()), barrel);
			barrel.markDirty();
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	@SideOnly(Side.CLIENT)
	public TextureAtlasSprite getTextureForRender(final TileBarrel barrel) {
		handler.setBarrel(barrel);
		ItemStack stack = handler.getStackInSlot(0);
		if (stack.isEmpty())
			return Util.getTextureFromBlockState(Blocks.AIR.getDefaultState());
		return Util.getTextureFromBlockState(Block.getBlockFromItem(stack.getItem()).getStateFromMeta(stack.getItemDamage()));
	}

	@Override
	public Color getColorForRender() {
		return Util.whiteColor;
	}

	@Override
	public float getFilledLevelForRender(final TileBarrel barrel) {
		return 0.9375F;
	}

	@Override
	public void update(final TileBarrel barrel) {
	}

	@Override
	public void addItem(final ItemStack stack, final TileBarrel barrel) {
		handler.setBarrel(barrel);
		if (handler.getStackInSlot(0).isEmpty())
			handler.insertItem(0, stack, false);
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