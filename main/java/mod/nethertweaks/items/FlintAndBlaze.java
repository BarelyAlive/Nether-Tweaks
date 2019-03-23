package mod.nethertweaks.items;

import mod.nethertweaks.INames;
import mod.nethertweaks.NetherTweaksMod;
import net.minecraft.block.material.Material;
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
    	this.setUnlocalizedName(INames.FLINTNBLAZE);
        this.setMaxStackSize(1);
        this.setMaxDamage(128);
        this.setCreativeTab(NetherTweaksMod.tabNetherTweaksMod);
    }

}