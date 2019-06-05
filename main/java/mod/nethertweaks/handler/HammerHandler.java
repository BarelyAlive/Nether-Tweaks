package mod.nethertweaks.handler;

import java.util.List;

import javax.annotation.Nullable;

import mod.nethertweaks.api.IHammer;
import mod.nethertweaks.registries.manager.NTMRegistryManager;
import mod.sfhcore.util.ItemUtil;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HammerHandler
{
    @SubscribeEvent(priority = EventPriority.LOW)
    public void hammer(BlockEvent.HarvestDropsEvent event)
    {
        if (event.getWorld().isRemote || event.getHarvester() == null || event.isSilkTouching())
            return;

        ItemStack held = event.getHarvester().getHeldItemMainhand();

        if (!isHammer(held))
            return;

        List<ItemStack> rewards = NTMRegistryManager.HAMMER_REGISTRY.getRewardDrops(event.getWorld().rand, event.getState(), ((IHammer) held.getItem()).getMiningLevel(held), event.getFortuneLevel());

        if (rewards != null && rewards.size() > 0)
        {
            event.getDrops().clear();
            event.setDropChance(1.0F);
            event.getDrops().addAll(rewards);
        }
    }
    
    public static boolean isHammer(@Nullable ItemStack stack)
	{
	    if (stack == null)
	        return false;
	
	    if (stack.getItem() == Items.AIR)
	        return false;
	
	    if (stack.getItem() instanceof IHammer)
	        return ((IHammer) stack.getItem()).isHammer(stack);
	
	    return false;
	}
	
	public boolean isHammer(Item item)
	{
	    return isHammer(new ItemStack(item));
	}
}