package mod.nethertweaks.barrel.modes.mobspawn;

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

import java.util.List;

import mod.nethertweaks.barrel.IBarrelMode;
import mod.nethertweaks.blocks.tile.TileBarrel;
import mod.nethertweaks.items.ItemDoll;
import mod.nethertweaks.network.MessageBarrelModeUpdate;
import mod.nethertweaks.network.NetworkHandlerNTM;
import mod.sfhcore.texturing.Color;
import mod.sfhcore.util.Util;

public class BarrelModeMobSpawn implements IBarrelMode {

    private float progress = 0;

    private ItemStack dollStack;

    public void setDollStack(ItemStack dollStack) {
		this.dollStack = dollStack;
	}

	@Override
    public void writeToNBT(NBTTagCompound tag) {
        tag.setFloat("progress", progress);

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
    public void onBlockActivated(World world, TileBarrel barrel, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getTextureForRender(TileBarrel barrel) {
        if (dollStack == null || dollStack.isEmpty())
            return null;

        ItemDoll doll = (ItemDoll) dollStack.getItem();

        return Util.getTextureFromFluid(doll.getSpawnFluid(dollStack));
    }

    @Override
    public Color getColorForRender() {
        return Util.whiteColor;
    }

    @Override
    public float getFilledLevelForRender(TileBarrel barrel) {
        return 0.9375F;
    }

    @Override
    public void update(TileBarrel barrel) {
        if (progress < 1) {
            progress += 1.0 / 600;
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
    public void addItem(ItemStack stack, TileBarrel barrel) {
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
        currenttip.add("Spawning: " + Math.round(100 * progress) + "%");
        return currenttip;
    }

}