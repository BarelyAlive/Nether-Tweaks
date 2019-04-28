package mod.nethertweaks.items;

import mod.nethertweaks.Config;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.handler.BlockHandler;
import mod.nethertweaks.handler.BucketNFluidHandler;
import mod.nethertweaks.handler.ItemHandler;
import mod.nethertweaks.interfaces.INames;
import mod.sfhcore.items.CustomItem;
import net.java.games.input.Keyboard;
import net.minecraft.block.Block;
import net.minecraft.client.audio.Sound;
import net.minecraft.client.audio.SoundManager;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.client.event.sound.SoundEvent;
import net.minecraftforge.client.event.sound.SoundEvent.SoundSourceEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.common.Loader;

public class Crystal extends CustomItem{
		
	public Crystal() {
		super(null, 1, NetherTweaksMod.tabNTM, true, 2, new ResourceLocation(NetherTweaksMod.MODID, INames.CRYSTAL));
	}
	
	@Override
	public boolean hasEffect(ItemStack stack) {
		return true;
	}
	
	/**
     * Called when the equipped item is right clicked.
     */
	@Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
    	ItemStack itemstack = playerIn.getHeldItem(handIn);
    	
    	switch (itemstack.getItemDamage()) {	
		case 1:
			if (Config.enableCrystalEnder) {
				worldIn.playSound((EntityPlayer) null, playerIn.posX, playerIn.posY, playerIn.posZ,
						SoundEvents.ENTITY_ENDERPEARL_THROW, SoundCategory.NEUTRAL, 0.5F,
						0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
				playerIn.getCooldownTracker().setCooldown(this, 20);
				if (!worldIn.isRemote) {
					EntityEnderPearl entityenderpearl = new EntityEnderPearl(worldIn, playerIn);
					entityenderpearl.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 1.0F);
					worldIn.spawnEntity(entityenderpearl);
				}
				playerIn.addStat(StatList.getObjectUseStats(this));
				return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
			}
		default:
			return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
		}
	}
}
