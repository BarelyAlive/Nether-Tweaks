package mod.nethertweaks.items;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import mod.nethertweaks.INames;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.blocks.Sieve.MeshType;
import mod.sfhcore.helper.NameHelper;
import mod.sfhcore.proxy.IVariantProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ItemMesh extends Item{
		
	public ItemMesh(String type) {
		super();
		this.setRegistryName(new ResourceLocation(NetherTweaksMod.MODID, type));
		this.setMaxStackSize(1);
		this.setCreativeTab(NetherTweaksMod.TABNTM);
	}
	
	@Override
	public int getItemEnchantability(ItemStack stack)
	{
	    switch(NameHelper.getName(stack))
	    {
	        case INames.MESH_STRING:
	            return 15;
	        case INames.MESH_FLINT:
	            return 7;
	        case INames.MESH_IRON:
	            return 14;
	        case INames.MESH_DIAMOND:
	            return 10;
	        default:
	            return 0;
	    }
	}
	
	@Override
    public String getUnlocalizedName(ItemStack stack) {
    	return "item." + getRegistryName().getResourcePath();

    }
	
	@Override
	public boolean isEnchantable(ItemStack stack)
	{
	    return true;
	}
	
	@Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book)
    {
        return stack.getCount() == 1;
    }
}
