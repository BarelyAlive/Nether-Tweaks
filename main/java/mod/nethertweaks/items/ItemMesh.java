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
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemMesh extends Item implements IVariantProvider{
	
	private final String name = "item_mesh";
	
	public ItemMesh() {
		super();
		this.setHasSubtypes(true);
		this.setUnlocalizedName("item_mesh");
		this.setRegistryName(new ResourceLocation(NetherTweaksMod.MODID, "item_mesh"));
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
	
	public String getName(ItemStack stack)
	{
		return name + "_" + MeshType.getMeshTypeByID(stack.getItemDamage()).getName().toLowerCase();
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
	    return "item." + getName(stack);
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		for (int i = 1 ; i < MeshType.values().length ; i++) { //0 is the "none" case.
			items.add(new ItemStack(this, 1, i));
        }
	}

	@Override
    public List<Pair<Integer, String>> getVariants()
    {
        List<Pair<Integer, String>> ret = new ArrayList<Pair<Integer, String>>();
        for (MeshType type : MeshType.values())
            ret.add(new ImmutablePair<Integer, String>(type.getID(), type.getName().toLowerCase()));
        return ret;
    }
}
