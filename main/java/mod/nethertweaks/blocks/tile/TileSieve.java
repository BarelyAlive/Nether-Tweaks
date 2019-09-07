package mod.nethertweaks.blocks.tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.base.Objects;

import mod.nethertweaks.blocks.Sieve;
import mod.nethertweaks.config.Config;
import mod.nethertweaks.enchantments.EnchantmentEfficiency;
import mod.nethertweaks.enchantments.EnchantmentFortune;
import mod.nethertweaks.enchantments.EnchantmentLuckOfTheSea;
import mod.nethertweaks.registries.manager.NTMRegistryManager;
import mod.nethertweaks.registry.types.Siftable;
import mod.sfhcore.blocks.tiles.TileBase;
import mod.sfhcore.helper.NameHelper;
import mod.sfhcore.network.NetworkHandler;
import mod.sfhcore.util.BlockInfo;
import mod.sfhcore.util.Util;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class TileSieve extends TileBase {

	private static final Random rand = new Random();
	@Nonnull
	private BlockInfo currentStack = BlockInfo.EMPTY;
	private byte progress = 0;
	@Nonnull
	private ItemStack meshStack = ItemStack.EMPTY;
	private Sieve.MeshType meshType = Sieve.MeshType.NONE;
	public BlockInfo getCurrentStack() {
		return currentStack;
	}

	public byte getProgress() {
		return progress;
	}

	public ItemStack getMeshStack() {
		return meshStack;
	}

	public Sieve.MeshType getMeshType() {
		return meshType;
	}

	private long lastSieveAction = 0;
	private UUID lastPlayer;

	/**
	 * Sets the mesh type in the sieve.
	 *
	 * @param newMesh The mesh to set
	 * @return true if setting is successful.
	 */
	public boolean setMesh(final ItemStack newMesh) {
		return setMesh(newMesh, false);
	}

	public boolean setMesh(final ItemStack newMesh, final boolean simulate) {
		if (progress != 0 || currentStack.isValid())
			return false;

		if (meshStack.isEmpty()) {
			if (!simulate) {
				meshStack = newMesh.copy();
				meshType = Sieve.MeshType.getMeshTypeByID(NameHelper.getName(newMesh));

				markDirtyClient();
			}
			return true;
		}

		if (newMesh.isEmpty()) {
			//Removing
			if (!simulate) {
				meshStack = ItemStack.EMPTY;
				meshType = Sieve.MeshType.NONE;

				markDirtyClient();
			}
			return true;
		}

		return false;

	}

	public boolean addBlock(final ItemStack stack) {
		if (!currentStack.isValid() && NTMRegistryManager.SIEVE_REGISTRY.canBeSifted(stack)) {
			if (meshStack.isEmpty())
				return false;
			String meshLevel = NameHelper.getName(meshStack);
			for (Siftable siftable : NTMRegistryManager.SIEVE_REGISTRY.getDrops(stack))
				if (("mesh_" + siftable.getMeshLevel()).equals(meshLevel)) {
					currentStack = new BlockInfo(stack);
					markDirtyClient();
					return true;
				}
		}

		return false;
	}

	public void doSieving(final EntityPlayer player, final boolean automatedSieving) {
		if (!world.isRemote) {

			if (!currentStack.isValid())
				return;

			// 4 ticks is the same period of holding down right click
			if (getWorld().getTotalWorldTime() - lastSieveAction < 4 && !automatedSieving)
				return;

			// Really good chance that they're using a macro
			if (!automatedSieving && player != null && getWorld().getTotalWorldTime() - lastSieveAction == 0 && lastPlayer.equals(player.getUniqueID())) {
				if (Config.setFireToMacroUsers)
					player.setFire(1);

				player.sendMessage(new TextComponentString("Bad").setStyle(new Style().setColor(TextFormatting.RED).setBold(true)));
			}

			lastSieveAction = getWorld().getTotalWorldTime();
			if (player != null)
				lastPlayer = player.getUniqueID();

			int efficiency = EnchantmentHelper.getEnchantmentLevel(EnchantmentEfficiency.EFFICIENCY, meshStack);
			efficiency += EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, meshStack);
			if(Config.hasteIncreaseSpeed && player != null && player.isPotionActive(MobEffects.HASTE))
				efficiency *= 1.0F + (player.getActivePotionEffect(MobEffects.HASTE).getAmplifier() + 1) * 0.2F;

			int fortune = EnchantmentHelper.getEnchantmentLevel(EnchantmentFortune.FORTUNE, meshStack);
			fortune += EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, meshStack);
			if (player != null)
				fortune += player.getLuck();

			int luckOfTheSea = EnchantmentHelper.getEnchantmentLevel(EnchantmentLuckOfTheSea.LUCK_OF_THE_SEA, meshStack);
			luckOfTheSea += EnchantmentHelper.getEnchantmentLevel(Enchantments.LUCK_OF_THE_SEA, meshStack);

			if (luckOfTheSea > 0 && player != null)
				luckOfTheSea += player.getLuck();

			progress += 10 + 5 * efficiency;
			markDirtyClient();

			if (progress >= 100) {
				List<ItemStack> drops = NTMRegistryManager.SIEVE_REGISTRY.getRewardDrops(rand, currentStack.getBlockState(), NameHelper.getName(meshStack).substring(5), fortune);

				if (drops == null)
					drops = new ArrayList<>();

				// Fancy math to make the average fish dropped ~ luckOfTheSea / 2 fish, which is what it was before

				int fishToDrop = (int) Math.round(rand.nextGaussian() + luckOfTheSea / 2.0);

				fishToDrop = MathHelper.clamp(fishToDrop, 0, luckOfTheSea);

				for (int i = 0; i < fishToDrop; i++) {
					/*
					 * Gives fish following chances:
					 *  Normal: 43% (3/7)
					 *  Salmon: 29% (2/7)
					 *  Clown:  14% (1/7)
					 *  Puffer: 14% (1/7)
					 */

					int fishMeta = 0;

					switch (rand.nextInt(7)) {
					case 3:
					case 4:
						fishMeta = 1;
						break;
					case 5:
						fishMeta = 2;
						break;
					case 6:
						fishMeta = 3;
						break;
					default:
						break;
					}

					drops.add(new ItemStack(Items.FISH, 1, fishMeta));
				}

				TileEntity teUp = world.getTileEntity(pos.up());
				IItemHandler cap;

				if (teUp != null
						&& teUp.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN)
						&& (cap = teUp.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN)) != null) {

					int slotAmount = cap.getSlots();

					for (ItemStack drop : drops) {
						ItemStack newStack = drop;
						for (int i = 0; i < slotAmount && !newStack.isEmpty(); i++)
							newStack = cap.insertItem(i, newStack, false);

						if (!newStack.isEmpty())
							Util.dropItemInWorld(this, player, newStack, 1);

					}
				} else
					drops.forEach(stack -> Util.dropItemInWorld(this, player, stack, 1));



				resetSieve();
			}
		}
	}

	public boolean isSieveSimilar(final TileSieve sieve) {
		return sieve != null && !meshStack.isEmpty() && !sieve.getMeshStack().isEmpty() && meshStack.getItemDamage() == sieve.getMeshStack().getItemDamage() && progress == sieve.getProgress() && currentStack.isValid() && currentStack.equals(sieve.getCurrentStack());
	}

	public boolean isSieveSimilarToInput(final TileSieve sieve) {
		return !meshStack.isEmpty() && !sieve.getMeshStack().isEmpty() && meshStack.getItemDamage() == sieve.getMeshStack().getItemDamage() && progress == sieve.getProgress() && !sieve.getCurrentStack().isValid();
	}

	private void resetSieve() {
		progress = 0;
		currentStack = BlockInfo.EMPTY;
		markDirtyClient();
	}

	@SideOnly(Side.CLIENT)
	public TextureAtlasSprite getTexture() {
		if (currentStack.isValid())
			return Util.getTextureFromBlockState(currentStack.getBlockState());
		return null;
	}

	@Override
	public boolean shouldRefresh(final World world, final BlockPos pos, @Nonnull final IBlockState oldState, @Nonnull final IBlockState newState) {
		return oldState.getBlock() != newState.getBlock();
	}

	@Override
	@Nonnull
	public NBTTagCompound writeToNBT(final NBTTagCompound tag) {
		if (currentStack.isValid()) {
			NBTTagCompound stackTag = currentStack.writeToNBT(new NBTTagCompound());
			tag.setTag("stack", stackTag);
		}

		if (!meshStack.isEmpty()) {
			NBTTagCompound meshTag = meshStack.writeToNBT(new NBTTagCompound());
			tag.setTag("mesh", meshTag);
		}

		tag.setByte("progress", progress);

		return super.writeToNBT(tag);
	}

	@Override
	public void readFromNBT(final NBTTagCompound tag) {
		if (tag.hasKey("stack"))
			currentStack = BlockInfo.readFromNBT(tag.getCompoundTag("stack"));
		else
			currentStack = BlockInfo.EMPTY;

		if (tag.hasKey("mesh")) {
			meshStack = new ItemStack(tag.getCompoundTag("mesh"));
			meshType = Sieve.MeshType.getMeshTypeByID(NameHelper.getName(meshStack));
		} else {
			meshStack = ItemStack.EMPTY;
			meshType = Sieve.MeshType.NONE;
		}

		progress = tag.getByte("progress");
		super.readFromNBT(tag);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(pos.getX(), pos.getY(), pos.getZ());
	}

	@Override
	public void markDirtyClient() {
		markDirty();
		NetworkHandler.sendNBTUpdate(this);
	}

	//Move to sfh tile base

	@Override
	public void markDirtyChunk() {
		markDirty();
		IBlockState state = getWorld().getBlockState(getPos());
		getWorld().notifyBlockUpdate(getPos(), state, state, 3);
		NetworkHandler.sendNBTUpdate(this);
	}

	@Nullable
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(pos, 0, getUpdateTag());
	}

	@Override
	@Nonnull
	public NBTTagCompound getUpdateTag() {
		return writeToNBT(new NBTTagCompound());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onDataPacket(final NetworkManager net, final SPacketUpdateTileEntity pkt) {
		readFromNBT(pkt.getNbtCompound());
		IBlockState state = world.getBlockState(pos);
		world.notifyBlockUpdate(pos, state, state, 3);
	}
}