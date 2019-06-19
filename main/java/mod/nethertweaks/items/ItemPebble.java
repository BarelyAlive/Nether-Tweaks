package mod.nethertweaks.items;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Lists;

import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.entities.ProjectileStone;
import mod.sfhcore.proxy.IVariantProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemPebble extends Item
{    
    public ItemPebble(String type)
    {
        this.setRegistryName(NetherTweaksMod.MODID, type);
        this.setCreativeTab(NetherTweaksMod.TABNTM);
    }
    
    @Override
    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);

        stack.shrink(1);
        world.playSound(player, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

        if (!world.isRemote) {
            ItemStack thrown = stack.copy();
            thrown.setCount(1);

            ProjectileStone projectile = new ProjectileStone(world, player);
            projectile.setStack(thrown);
            projectile.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.5F, 0.5F);
            world.spawnEntity(projectile);
        }

        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }
    
    @Override
    public String getUnlocalizedName(ItemStack stack) {
    	return "item." + getRegistryName().getResourcePath();

    }
}
