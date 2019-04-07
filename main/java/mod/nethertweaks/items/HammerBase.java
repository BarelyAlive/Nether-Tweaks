package mod.nethertweaks.items;

import com.google.common.collect.Sets;

import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.interfaces.IHammer;
import mod.nethertweaks.interfaces.INames;
import mod.nethertweaks.registry.HammerRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.ResourceLocation;

public class HammerBase extends ItemTool implements IHammer  {

	int miningLevel;

	public HammerBase(ResourceLocation loc, int maxUses, ToolMaterial material)
	{
		super(material, Sets.newHashSet(new Block[]{}));
		this.setUnlocalizedName(loc.getResourcePath());
		this.setRegistryName(loc);
		this.setMaxDamage(maxUses);
		this.setCreativeTab(NetherTweaksMod.tabNTM);
		this.miningLevel = material.getHarvestLevel();
	}

	@Override
	public boolean isHammer(ItemStack stack) 
	{
		return true;
	}

	@Override
	public int getMiningLevel(ItemStack stack) 
	{
		return miningLevel;
	}
	
	@Override
	 public boolean canHarvestBlock(IBlockState state)
    {
        return HammerRegistry.registered(state.getBlock());
    }
	
	@Override
	public float getDestroySpeed(ItemStack stack, IBlockState state)
    {
        return HammerRegistry.registered(state.getBlock()) ? this.efficiency : 1.0F;
    }
	
	private String getNameByMaterial(ToolMaterial tool)
	{
		if(tool == ToolMaterial.DIAMOND)
		{
			return INames.HAMMERDIAMOND;
		}
		if(tool == ToolMaterial.IRON)
		{
			return INames.HAMMERIRON;
		}
		if(tool == ToolMaterial.GOLD)
		{
			return INames.HAMMERGOLD;
		}
		if(tool == ToolMaterial.STONE)
		{
			return INames.HAMMERSTONE;
		}
		if(tool == ToolMaterial.WOOD)
		{
			return INames.HAMMERWOOD;
		}
		return null;
	}
}
