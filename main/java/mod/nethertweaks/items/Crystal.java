package mod.nethertweaks.items;

import java.util.List;

import mod.nethertweaks.INames;
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
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
    	ItemStack itemstack = playerIn.getHeldItem(handIn);
    	String name = itemstack.getItem().getRegistryName().getResourcePath();
    	
    	switch (name) {	
		case ENDER_CRYSTAL:
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
		default:
			return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
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
