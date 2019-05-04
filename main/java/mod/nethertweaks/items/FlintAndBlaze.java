package mod.nethertweaks.items;

import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.interfaces.INames;
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
    	this.setRegistryName(NetherTweaksMod.MODID, INames.FLINTNBLAZE);
        this.setMaxStackSize(1);
        this.setMaxDamage(256);
        this.setCreativeTab(NetherTweaksMod.TABNTM);
    }

}