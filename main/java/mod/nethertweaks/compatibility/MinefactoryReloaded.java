package mod.nethertweaks.compatibility;

import mod.nethertweaks.blocks.Sieve.MeshType;
import mod.nethertweaks.handler.BlockHandler;
import mod.nethertweaks.handler.RecipeHandler;
import mod.nethertweaks.registry.manager.SieveRegistry;
import mod.sfhcore.util.ItemInfo;
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
		 SieveRegistry.register(Blocks.DIRT.getDefaultState(), new ItemInfo(Item.getItemFromBlock(RUBBERSAPLING), 0), 0.05F, MeshType.STRING.getID());
	}
}
