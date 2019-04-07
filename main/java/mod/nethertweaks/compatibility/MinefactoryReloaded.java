package mod.nethertweaks.compatibility;

import mod.nethertweaks.handler.BlockHandler;
import mod.nethertweaks.handler.NTMSieveHandler;
import mod.nethertweaks.handler.RecipeHandler;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class MinefactoryReloaded {
	
	public static final Block RUBBERSAPLING = Block.REGISTRY.getObject((new ResourceLocation("minefactoryreloaded", "rubberwood.sapling")));

	public static void loadCompatibility()
	{		 
		 if(RUBBERSAPLING != null)
		 NTMSieveHandler.register(Blocks.DIRT, Item.getItemFromBlock(RUBBERSAPLING), 0, 20);
	}
}
