package mod.nethertweaks.handler;

import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

import mod.nethertweaks.interfaces.IHammer;
import mod.nethertweaks.registries.manager.NTMRegistryManager;
import mod.nethertweaks.util.ItemUtil;

public class HandlerHammer
{
    @SubscribeEvent(priority = EventPriority.LOW)
    public void hammer(BlockEvent.HarvestDropsEvent event)
    {
        if (event.getWorld().isRemote || event.getHarvester() == null || event.isSilkTouching())
            return;

        ItemStack held = event.getHarvester().getHeldItemMainhand();

        if (!ItemUtil.isHammer(held))
            return;

        List<ItemStack> rewards = NTMRegistryManager.HAMMER_REGISTRY.getRewardDrops(event.getWorld().rand, event.getState(), ((IHammer) held.getItem()).getMiningLevel(held), event.getFortuneLevel());

        if (rewards != null && rewards.size() > 0)
        {
            event.getDrops().clear();
            event.setDropChance(1.0F);
            event.getDrops().addAll(rewards);
        }
    }
}
