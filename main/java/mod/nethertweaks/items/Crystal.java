package mod.nethertweaks.items;

import java.util.List;

import mod.nethertweaks.INames;
import mod.sfhcore.helper.NameHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class Crystal extends Item implements INames{
		
	public Crystal(String name) {
		setCreativeTab(TAB);
		setRegistryName(MODID, name);
	}
	
	@Override
	public boolean hasEffect(ItemStack stack) {
		return true;
	}
	
	/**
     * Called when the equipped item is right clicked.
     */
	@Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
    	ItemStack itemstack = player.getHeldItem(hand);
    	String name = NameHelper.getName(itemstack);
    	
    	switch (name) {	
		case ENDER_CRYSTAL:
				world.playSound((EntityPlayer) null, player.posX, player.posY, player.posZ,
						SoundEvents.ENTITY_ENDERPEARL_THROW, SoundCategory.NEUTRAL, 0.5F,
						0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
				player.getCooldownTracker().setCooldown(this, 20);
				if (!world.isRemote) {
					EntityEnderPearl entityenderpearl = new EntityEnderPearl(world, player);
					entityenderpearl.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.5F, 1.0F);
					world.spawnEntity(entityenderpearl);
				}
				player.addStat(StatList.getObjectUseStats(this));
				return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
		default:
			return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flagIn)
	{
		String name = stack.getItem().getRegistryName().getResourcePath();
		
		switch (name) {
		case CRYSTAL_OF_LIGHT:
			tooltip.add("This crystal enchants water, making it resistant to temperature changes");
			break;
		case ENDER_CRYSTAL:
			tooltip.add("This Crystal allows you to teleport, like Ender Pearls");
			break;
		default:
			break;
		}
	}
}
