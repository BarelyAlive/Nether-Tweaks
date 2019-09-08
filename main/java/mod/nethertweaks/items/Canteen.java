package mod.nethertweaks.items;

import mod.nethertweaks.INames;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.common.logic.ThirstStats;
import mod.nethertweaks.registries.manager.NTMRegistryManager;
import mod.nethertweaks.registry.types.Drinkable;
import mod.sfhcore.items.CustomItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class Canteen extends CustomItem
{
	public Canteen ()
	{
		super(1, NetherTweaksMod.TABNTM, new ResourceLocation(NetherTweaksMod.MODID, INames.CANTEEN));
		setMaxStackSize(1);
		setMaxDamage(3);
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack held = player.getHeldItem(hand);
		int dmg = held.getItemDamage();
		
		if(dmg < 3)
		{
			IFluidHandler handler = FluidUtil.getFluidHandler(worldIn, pos, facing);
			
			if(handler != null)
			{
				FluidStack f = handler.drain(250 * (3 - dmg ), true);
				
				if(f != null)
				{
					int fill = f.amount / 250;
					held.setItemDamage(dmg + fill);
				}
			}
		}
		return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving)
	{
		EntityPlayer player = entityLiving instanceof EntityPlayer ? (EntityPlayer)entityLiving : null;
        if (stack.getItemDamage() > 0) {
			this.onDrinkItem(player, stack);
			stack.damageItem(1, entityLiving);
		}
		return stack;
	}
	
	public void onDrinkItem(EntityPlayer player, ItemStack stack) {
        if (!player.world.isRemote && player != null) {
            ThirstStats stats = NetherTweaksMod.getProxy().getStatsByUUID(player.getUniqueID());
            Drinkable drink = NTMRegistryManager.DRINK_REGISTRY.getItem(stack);
            stats.addStats(drink.getThirstReplenish(), drink.getSaturationReplenish());
            stats.attemptToPoison(drink.getPoisonChance());
            player.addStat(StatList.getObjectUseStats(this));
        }
    }
	
	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return true;
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack)
    {
        return 32;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.DRINK;
    }
}
