package mod.nethertweaks.items;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import mod.nethertweaks.INames;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.blocks.Sieve.MeshType;
import mod.sfhcore.helper.NameHelper;
import mod.sfhcore.proxy.IVariantProvider;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemMesh extends Item implements IVariantProvider{
		
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
	        case INames.STRING_MESH:
	            return 15;
	        case INames.FLINT_MESH:
	            return 7;
	        case INames.IRON_MESH:
	            return 14;
	        case INames.DIAMOND_MESH:
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

	@Override
	public List<Pair<Integer, String>> getVariants()
    {
        List<Pair<Integer, String>> ret = new ArrayList<Pair<Integer, String>>();
        
        for(int i = 1; i < MeshType.values().length -1; i++)
		{
			ret.add(new ImmutablePair<Integer, String>(0, "inventory"));
		}
        return ret;
    }
}
