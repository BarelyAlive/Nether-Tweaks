package mod.nethertweaks.compatibility;

import mod.nethertweaks.blocks.NTMBlocks;
import mod.nethertweaks.handler.NTMSieveHandler;
import mod.nethertweaks.handler.RecipeHandler;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class MinefactoryReloaded {
	
	public static Block rubberSapling;
	public static boolean MFR;

	public static void loadCompatibility(){
		 rubberSapling = Block.REGISTRY.getObject((new ResourceLocation("MineFactoryReloaded", "rubberwood.sapling")));
		 
		 if(rubberSapling != null)
		 NTMSieveHandler.register(Blocks.DIRT, Item.getItemFromBlock(rubberSapling), 0, 20);
	}
}
