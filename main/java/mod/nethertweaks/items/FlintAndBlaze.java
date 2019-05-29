package mod.nethertweaks.items;

import java.util.List;

import mod.nethertweaks.INames;
import mod.nethertweaks.NetherTweaksMod;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class FlintAndBlaze extends ItemFlintAndSteel{

    public FlintAndBlaze()
    {
    	this.setRegistryName(NetherTweaksMod.MODID, INames.FLINTNBLAZE);
        this.setMaxStackSize(1);
        this.setMaxDamage(256);
        this.setCreativeTab(NetherTweaksMod.TABNTM);
    }

    @Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
		tooltip.add("Totally LIT!");
	}
}