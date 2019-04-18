package mod.nethertweaks.barrel.modes.block;

import java.util.List;

import mod.nethertweaks.barrel.IBarrelMode;
import mod.nethertweaks.blocks.tile.TileBarrel;
import mod.nethertweaks.network.MessageBarrelModeUpdate;
import mod.nethertweaks.network.NetworkHandlerNTM;
import mod.sfhcore.helper.StackUtils;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemStackHandler;

public class BarrelModeBlock implements IBarrelMode {

	private ItemInfo block;

	public ItemInfo getBlock() {
		return block;
	}

	public void setBlock(ItemInfo block) {
		this.block = block;
	}

	private BarrelItemHandlerBlock handler = new BarrelItemHandlerBlock(null);

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		if (block != null) {
			tag.setString("block", block.toString());
		}
		if (handler.getStackInSlot(0) != null) {
			handler.getStackInSlot(0).writeToNBT(tag);
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		if (tag.hasKey("block")) {
			block = new ItemInfo(tag.getString("block"));
		}

		handler.setStackInSlot(0, new ItemStack(tag));
	}

	@Override
	public boolean isTriggerItemStack(ItemStack stack) {
		return false;
	}

	@Override
	public boolean isTriggerFluidStack(FluidStack stack) {
		return false;
	}

	@Override
	public String getName() {
		return "block";
	}

	@Override
	public List<String> getWailaTooltip(TileBarrel barrel, List<String> currenttip) {
		if (handler.getStackInSlot(0) != null)
			currenttip.add(handler.getStackInSlot(0).getDisplayName());
		return currenttip;
	}

	@Override
	public boolean onBlockActivated(World world, TileBarrel barrel,
			BlockPos pos, IBlockState state, EntityPlayer player,
			EnumFacing side, float hitX, float hitY, float hitZ) {
		if (handler.getStackInSlot(0) != null) {
			Util.dropItemInWorld(barrel, player, handler.getStackInSlot(0), 0.02);
			handler.setBarrel(barrel);
			handler.setStackInSlot(0, ItemStack.EMPTY);
			barrel.setMode("null");
			NetworkHandlerNTM.sendToAllAround(new MessageBarrelModeUpdate("null", barrel.getPos()), barrel);
			return true;
		}
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public TextureAtlasSprite getTextureForRender(TileBarrel barrel) {
		handler.setBarrel(barrel);
		ItemStack stack = handler.getStackInSlot(0);
		if (stack == null || Block.getBlockFromItem(stack.getItem()) == null)
			return Util.getTextureFromBlockState(null);

		return Util.getTextureFromBlockState(Block.getBlockFromItem(stack.getItem()).getStateFromMeta(stack.getItemDamage()));
	}

	@Override
	public Color getColorForRender() {
		return Util.whiteColor;
	}

	@Override
	public float getFilledLevelForRender(TileBarrel barrel) {
		return 1;
	}

	@Override
	public void update(TileBarrel barrel) {
	}

	@Override
	public boolean addItem(ItemStack stack, TileBarrel barrel) {
		handler.setBarrel(barrel);
		if (handler.getStackInSlot(0) == null) {
			handler.insertItem(0, stack, false);
			return true;
		}
		return false;
	}

	@Override
	public ItemStackHandler getHandler(TileBarrel barrel) {
		handler.setBarrel(barrel);
		return handler;
	}

	@Override
	public FluidTank getFluidHandler(TileBarrel barrel) {
		return null;
	}

	@Override
	public boolean canFillWithFluid(TileBarrel barrel) {
		return false;
	}

}