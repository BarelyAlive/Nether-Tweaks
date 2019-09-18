package mod.nethertweaks.items;

import javax.annotation.Nullable;

import com.google.common.collect.Sets;

import mod.nethertweaks.Constants;
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

public class Hammer extends ItemTool implements IHammer  {

	private int miningLevel;

	public Hammer(final int maxUses, final ToolMaterial material)
	{
		super(material, Sets.newHashSet(new Block[]{}));
		this.setRegistryName(new ResourceLocation(Constants.MODID, getName(material)));
		setMaxDamage(maxUses);
		setCreativeTab(NetherTweaksMod.TAB);
		miningLevel = material.getHarvestLevel();
	}

	@Override
	public boolean onBlockStartBreak(final ItemStack itemstack, final BlockPos pos, final EntityPlayer player) {
		if(itemstack.getItemDamage() == itemstack.getMaxDamage())
			HammerHandler.setHammer(itemstack.copy());

		return super.onBlockStartBreak(itemstack, pos, player);
	}

	private String getName(final ToolMaterial m)
	{
		return "hammer_" + m.name();
	}

	@Override
	public boolean isHammer(final ItemStack stack) {
		return true;
	}

	@Override
	public int getMiningLevel(final ItemStack stack) {
		return miningLevel;
	}

	@Override
	public float getDestroySpeed(@Nullable final ItemStack stack, final IBlockState state) {
		return NTMRegistryManager.HAMMER_REGISTRY.isRegistered(state) ? efficiency : 1.0F;
	}

	@Override
	public boolean canHarvestBlock(final IBlockState state) {
		return NTMRegistryManager.HAMMER_REGISTRY.isRegistered(state);
	}
}
