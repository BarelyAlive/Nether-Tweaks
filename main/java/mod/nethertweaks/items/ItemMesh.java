package mod.nethertweaks.items;

import java.util.List;

import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.blocks.Sieve.MeshType;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemMesh extends Item {
	
	public ItemMesh() {
		super();
		this.setHasSubtypes(true);
		this.setUnlocalizedName("item_mesh_");
		this.setRegistryName("item_mesh_");
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
	public String getUnlocalizedName(ItemStack stack) {
	    return super.getUnlocalizedName() + "." + stack.getItemDamage();
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		for (int i = 1 ; i < MeshType.values().length ; i++) { //0 is the "none" case.
			items.add(new ItemStack(this, 1, i));
        }
	}

}
