package mod.nethertweaks.handler;

import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.interfaces.INames;
import mod.nethertweaks.items.ItemOre;
import mod.sfhcore.Constants;
import mod.sfhcore.Registry;
import mod.sfhcore.blocks.CubeFalling;
import mod.sfhcore.items.CustomItem;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.OreDictionary.OreRegisterEvent;

public class OreHandler implements INames{
    
    public static final Block OREDUST = new CubeFalling(16, Material.SAND, 2.0F, 0.4f, NetherTweaksMod.tabNTM, new ResourceLocation(NetherTweaksMod.MODID, INames.OREDUST));
    
    public static final Item oreMaterial = new CustomItem(null, 64, NetherTweaksMod.tabNTM, true, 16, new ResourceLocation(NetherTweaksMod.MODID, OREMATERIAL));
    
    public static void init() {
    	registerOres();
    }
    
	private static void registerOres(){
		
		Registry.registerBlock(OREDUST);		
	}
}
