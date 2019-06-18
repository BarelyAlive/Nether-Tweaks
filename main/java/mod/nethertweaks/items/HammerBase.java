package mod.nethertweaks.items;

import javax.annotation.Nullable;

import com.google.common.collect.Sets;

import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.api.IHammer;
import mod.nethertweaks.handler.HammerHandler;
import mod.nethertweaks.registries.manager.NTMRegistryManager;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class HammerBase extends ItemTool implements IHammer  {

	private int miningLevel;

	public HammerBase(int maxUses, ToolMaterial material)
	{
		super(material, Sets.newHashSet(new Block[]{}));
		this.setRegistryName(new ResourceLocation(NetherTweaksMod.MODID, getName(material)));
		this.setMaxDamage(maxUses);
		this.setCreativeTab(NetherTweaksMod.TABNTM);
		this.miningLevel = material.getHarvestLevel();
	}
	
	@Override
	public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, EntityPlayer player) {
		if(itemstack.getItemDamage() == itemstack.getMaxDamage())
			HammerHandler.setHammer(itemstack.copy());
		
		return super.onBlockStartBreak(itemstack, pos, player);
	}
	
	private String getName(ToolMaterial m)
	{		
		return "hammer_" + m.name();
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
