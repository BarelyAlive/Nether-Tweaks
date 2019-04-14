package mod.nethertweaks.items;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.blocks.Sieve.MeshType;
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
	
	private final String name = "item_mesh";
	
	public ItemMesh() {
		super();
		this.setHasSubtypes(true);
		this.setUnlocalizedName(name);
		this.setRegistryName(new ResourceLocation(NetherTweaksMod.MODID, name));
		this.setMaxStackSize(1);
		this.setCreativeTab(NetherTweaksMod.tabNTM);
	}
	
	@Override
	public int getItemEnchantability(ItemStack stack)
	{
	    switch(stack.getMetadata())
	    {
	        case 1:
	            return 15;
	        case 2:
	            return 7;
	        case 3:
	            return 14;
	        case 4:
	            return 10;
	        default:
	            return 0;
	    }
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
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		for (int i = 1 ; i < MeshType.values().length; i++) { //0 is the "none" case.
			items.add(new ItemStack(this, 1, i));
        }
	}

	@Override
	public List<Pair<Integer, String>> getVariants()
    {
        List<Pair<Integer, String>> ret = new ArrayList<Pair<Integer, String>>();
        
        for(int i = 1; i < MeshType.values().length; i++)
		{
			ret.add(new ImmutablePair<Integer, String>(i, "mesh=" + MeshType.getMeshTypeByID(i).getName()));
		}
        return ret;
    }
}
