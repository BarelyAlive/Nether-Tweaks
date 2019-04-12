package mod.nethertweaks.items;

import java.util.List;

import com.google.common.collect.Lists;

import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.entities.ProjectileStone;
import mod.nethertweaks.handler.ItemHandler;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemPebble extends Item
{
    private static List<String> names = Lists.newArrayList("stone", "granite", "diorite", "andesite");
    
    public ItemPebble()
    {
        setUnlocalizedName("item_pebble");
        setRegistryName(NetherTweaksMod.MODID, "item_pebble");
        setCreativeTab(NetherTweaksMod.tabNTM);
        setHasSubtypes(true);
        initModel();
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return getUnlocalizedName() + "." + names.get(stack.getItemDamage());
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tabs, List<ItemStack> list)
    {
        for (int i = 0; i < 4; i++)
        {
            list.add(new ItemStack(this, 1, i));
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
            projectile.setPositionAndRotation(playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 0.5F);
            worldIn.spawnEntity(projectile);
        }
        
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
    }
    
    @SideOnly(Side.CLIENT)
    public void initModel()
    {
        for (int i = 0; i < names.size(); i++)
        {
            String variant = "type=" + names.get(i);
            ModelLoader.setCustomModelResourceLocation(this, i, new ModelResourceLocation("nethertweaksmod:item_pebble", variant));
        }
    }
    
    public static ItemStack getPebbleStack(String name)
    {
        return new ItemStack(ItemHandler.PEBBLE, 1, names.indexOf(name));
    }
}
