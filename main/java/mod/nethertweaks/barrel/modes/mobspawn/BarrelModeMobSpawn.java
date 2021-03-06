package mod.nethertweaks.barrel.modes.mobspawn;

import java.util.List;

import mod.nethertweaks.barrel.IBarrelMode;
import mod.nethertweaks.block.tile.TileBarrel;
import mod.nethertweaks.item.ItemDoll;
import mod.nethertweaks.network.MessageBarrelModeUpdate;
import mod.sfhcore.network.NetworkHandler;
import mod.sfhcore.texturing.Color;
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
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemStackHandler;

public class BarrelModeMobSpawn implements IBarrelMode {

	private float progress = 0;

	private ItemStack dollStack;

	public void setDollStack(final ItemStack dollStack) {
		this.dollStack = dollStack;
	}

	@Override
	public void writeToNBT(final NBTTagCompound tag) {
		tag.setFloat("progress", progress);

		final NBTTagCompound dollTag = dollStack.writeToNBT(new NBTTagCompound());
		tag.setTag("doll", dollTag);

	}

	@Override
	public void readFromNBT(final NBTTagCompound tag) {
		progress = tag.getFloat("progress");

		dollStack = new ItemStack((NBTTagCompound) tag.getTag("doll"));
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
		return "mobspawn";
	}

	@Override
	public void onBlockActivated(final World world, final TileBarrel barrel, final BlockPos pos, final IBlockState state, final EntityPlayer player, final EnumHand hand, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
	}

	@Override
	@SideOnly(Side.CLIENT)
	public TextureAtlasSprite getTextureForRender(final TileBarrel barrel) {
		if (dollStack == null || dollStack.isEmpty())
			return null;

		final ItemDoll doll = (ItemDoll) dollStack.getItem();

		return Util.getTextureFromFluid(doll.getSpawnFluid(dollStack));
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
		if (progress < 1) {
			progress += 1.0 / 600;
			NetworkHandler.sendNBTUpdate(barrel);
		}

		if (progress >= 1) {
			final ItemDoll doll = (ItemDoll) dollStack.getItem();
			final boolean result = doll.spawnMob(dollStack, barrel.getWorld(), barrel.getPos());
			if (result) {
				barrel.setMode("null");
				NetworkHandler.sendToAllAround(new MessageBarrelModeUpdate("null", barrel.getPos()), barrel);
			}
		}

	}

	@Override
	public void addItem(final ItemStack stack, final TileBarrel barrel) {
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
	public List<String> getWailaTooltip(final TileBarrel barrel, final List<String> currenttip) {
		currenttip.add("Spawning: " + Math.round(100 * progress) + "%");
		return currenttip;
	}

}