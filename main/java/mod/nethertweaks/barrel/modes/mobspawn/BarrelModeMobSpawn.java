package mod.nethertweaks.barrel.modes.mobspawn;

import java.util.List;

import mod.nethertweaks.barrel.IBarrelMode;
import mod.nethertweaks.blocks.tile.TileBarrel;
import mod.nethertweaks.items.ItemDoll;
import mod.nethertweaks.network.MessageBarrelModeUpdate;
import mod.nethertweaks.network.NetworkHandlerNTM;
import mod.sfhcore.texturing.Color;
import mod.sfhcore.util.Util;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.items.ItemStackHandler;

public class BarrelModeMobSpawn implements IBarrelMode {
	
	private float progress = 0;
	
	private ItemStack dollStack;

	public void setDollStack(ItemStack dollStack) {
		this.dollStack = dollStack;
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		tag.setFloat("progress",  progress);
		
		NBTTagCompound dollTag = dollStack.writeToNBT(new NBTTagCompound());
		tag.setTag("doll", dollTag);
		
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		progress = tag.getFloat("progress");
		
		dollStack = new ItemStack((NBTTagCompound) tag.getTag("doll"));
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
		return "mobspawn";
	}

	@Override
	public boolean onBlockActivated(World world, TileBarrel barrel, BlockPos pos, IBlockState state,
			EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
		return false;
	}

	@Override
	public TextureAtlasSprite getTextureForRender(TileBarrel barrel) {
		if (dollStack == null)
			return null;
		
		ItemDoll doll = (ItemDoll) dollStack.getItem();
		return Util.getTextureFromBlockState(doll.getSpawnFluid(dollStack).getBlock().getDefaultState());
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
		if (progress < 1) {
			progress += 1.0/600;
			NetworkHandlerNTM.sendNBTUpdate(barrel);
		}
		
		if (progress >= 1) {
			ItemDoll doll = (ItemDoll) dollStack.getItem();
			boolean result = doll.spawnMob(dollStack, barrel.getWorld(), barrel.getPos());
			if (result) {
				barrel.setMode("null");
				NetworkHandlerNTM.sendToAllAround(new MessageBarrelModeUpdate("null", barrel.getPos()), barrel);
			}
		}
		
	}

	@Override
	public boolean addItem(ItemStack stack, TileBarrel barrel) {
		return false;
	}

	@Override
	public ItemStackHandler getHandler(TileBarrel barrel) {
		return null;
	}

	@Override
	public FluidTank getFluidHandler(TileBarrel barrel) {
		return null;
	}

	@Override
	public boolean canFillWithFluid(TileBarrel barrel) {
		return false;
	}

	@Override
	public List<String> getWailaTooltip(TileBarrel barrel, List<String> currenttip) {
		currenttip.add("Spawning: "+Math.round(100*progress)+"%");
		return currenttip;
	}

}
