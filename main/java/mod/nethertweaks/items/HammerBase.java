package mod.nethertweaks.items;

import javax.annotation.Nullable;

import com.google.common.collect.Sets;

import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.interfaces.IHammer;
import mod.nethertweaks.interfaces.INames;
import mod.nethertweaks.registries.manager.NTMRegistryManager;
import mod.nethertweaks.registries.registries.HammerRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.ResourceLocation;

public class HammerBase extends ItemTool implements IHammer  {

	private int miningLevel;

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
    public boolean isHammer(ItemStack stack) {
        return true;
    }

    @Override
    public int getMiningLevel(ItemStack stack) {
        return miningLevel;
    }

    @Override
    public float getDestroySpeed(@Nullable ItemStack stack, IBlockState state) {
        return NTMRegistryManager.HAMMER_REGISTRY.isRegistered(state) ? this.efficiency : 1.0F;
    }

    @Override
    public boolean canHarvestBlock(IBlockState state) {
        return NTMRegistryManager.HAMMER_REGISTRY.isRegistered(state);
    }
}
