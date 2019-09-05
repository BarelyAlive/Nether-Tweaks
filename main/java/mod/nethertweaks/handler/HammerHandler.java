package mod.nethertweaks.handler;

import java.util.List;

import javax.annotation.Nullable;

import mod.nethertweaks.api.IHammer;
import mod.nethertweaks.registries.manager.NTMRegistryManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HammerHandler
{
	private static ItemStack hammer = ItemStack.EMPTY;
	
    public static ItemStack getHammer() {
		return hammer;
	}

	public static void setHammer(ItemStack hammer) {
		HammerHandler.hammer = hammer;
	}

	@SubscribeEvent(priority = EventPriority.LOW)
    public void hammer(BlockEvent.HarvestDropsEvent event)
    {
        if (event.getWorld().isRemote || event.getHarvester() == null || event.isSilkTouching())
            return;
        
        ItemStack held = ItemStack.EMPTY;

        if(!hammer.isEmpty())
        {
        	held =  hammer;
        }
        else
        	held = event.getHarvester().getHeldItemMainhand();

        if (!isHammer(held))
            return;

        List<ItemStack> rewards = NTMRegistryManager.HAMMER_REGISTRY.getRewardDrops(event.getWorld().rand, event.getState(), ((IHammer) held.getItem()).getMiningLevel(held), event.getFortuneLevel());

        if (rewards != null && rewards.size() > 0)
        {
            event.getDrops().clear();
            event.setDropChance(1.0F);
            event.getDrops().addAll(rewards);
        }
        
        hammer =  ItemStack.EMPTY;
    }
    
    public static boolean isHammer(@Nullable ItemStack stack)
	{
	    if (stack.isEmpty())
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
