package mod.nethertweaks.items;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Lists;

import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.blocks.Sieve.MeshType;
import mod.nethertweaks.entities.ProjectileStone;
import mod.nethertweaks.handler.ItemHandler;
import mod.sfhcore.proxy.IVariantProvider;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemPebble extends Item implements IVariantProvider
{
    private static List<String> names = Lists.newArrayList("stone", "granite", "diorite", "andesite");
    
    public ItemPebble()
    {
        this.setHasSubtypes(true);
        this.setUnlocalizedName("item_pebble");
        this.setRegistryName(NetherTweaksMod.MODID, "item_pebble");
        this.setCreativeTab(NetherTweaksMod.tabNTM);
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
    	for (int i = 0; i < names.size(); i++)
        {
            items.add(new ItemStack(this, 1, i));
        }
    }
    
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
    	ItemStack stack = playerIn.getHeldItem(handIn);
    	
    	stack.setCount(stack.getCount()-1);
        
        worldIn.playSound(playerIn, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
        
        if (!worldIn.isRemote)
        {
            ItemStack thrown = stack.copy();
            thrown.setCount(1);
            
            ProjectileStone projectile = new ProjectileStone(worldIn, playerIn);
            projectile.setStack(thrown);
            projectile.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 4.0F, 1.0F);
            worldIn.spawnEntity(projectile);
        }
        
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
    }
    
    public static ItemStack getPebbleStack(String name)
    {
        return new ItemStack(ItemHandler.PEBBLE, 1, names.indexOf(name));
    }
    
    @Override
	public List<Pair<Integer, String>> getVariants()
    {
        List<Pair<Integer, String>> ret = new ArrayList<Pair<Integer, String>>();
        
        for(int i = 0; i < names.size(); i++)
		{
			ret.add(new ImmutablePair<Integer, String>(i, "type=" + names.get(i)));
		}
        return ret;
    }
}
