package mod.nethertweaks.registry.manager;

import mod.nethertweaks.blocks.Sieve.MeshType;
import mod.nethertweaks.handler.BlockHandler;
import mod.nethertweaks.handler.ItemHandler;
import mod.nethertweaks.items.ItemOre;
import mod.nethertweaks.registry.CondenserRegistry;
import mod.nethertweaks.registry.HammerRegistry;
import mod.nethertweaks.registry.OreRegistry;
import mod.sfhcore.util.ItemInfo;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class NTMDefaultRecipes implements IHammerDefaultRegistryProvider, ICondenserDefaultRegistryProvider, ISieveDefaultRegistryProvider{

	public NTMDefaultRecipes() {
		NTMRegistryManager.registerHammerDefaultRecipeHandler(this);
		NTMRegistryManager.registerCondenserDefaultRecipeHandler(this);
		NTMRegistryManager.registerSieveDefaultRecipeHandler(this);
	}

	@Override
	public void registerSieveRecipeDefaults() {
		SieveRegistry.register(Blocks.DIRT.getDefaultState(), ItemPebble.getPebbleStack("stone"), 1f, MeshType.STRING.getID());
		SieveRegistry.register(Blocks.DIRT.getDefaultState(), ItemPebble.getPebbleStack("stone"), 1f, MeshType.STRING.getID());
		SieveRegistry.register(Blocks.DIRT.getDefaultState(), ItemPebble.getPebbleStack("stone"), 0.5f, MeshType.STRING.getID());
		SieveRegistry.register(Blocks.DIRT.getDefaultState(), ItemPebble.getPebbleStack("stone"), 0.5f, MeshType.STRING.getID());
		SieveRegistry.register(Blocks.DIRT.getDefaultState(), ItemPebble.getPebbleStack("stone"), 0.1f, MeshType.STRING.getID());
		SieveRegistry.register(Blocks.DIRT.getDefaultState(), ItemPebble.getPebbleStack("stone"), 0.1f, MeshType.STRING.getID());

		SieveRegistry.register(Blocks.DIRT.getDefaultState(), ItemPebble.getPebbleStack("granite"), 0.5f, MeshType.STRING.getID());
		SieveRegistry.register(Blocks.DIRT.getDefaultState(), ItemPebble.getPebbleStack("granite"), 0.1f, MeshType.STRING.getID());

		SieveRegistry.register(Blocks.DIRT.getDefaultState(), ItemPebble.getPebbleStack("diorite"), 0.5f, MeshType.STRING.getID());
		SieveRegistry.register(Blocks.DIRT.getDefaultState(), ItemPebble.getPebbleStack("diorite"), 0.1f, MeshType.STRING.getID());

		SieveRegistry.register(Blocks.DIRT.getDefaultState(), ItemPebble.getPebbleStack("andesite"), 0.5f, MeshType.STRING.getID());
		SieveRegistry.register(Blocks.DIRT.getDefaultState(), ItemPebble.getPebbleStack("andesite"), 0.1f, MeshType.STRING.getID());

		SieveRegistry.register(Blocks.DIRT.getDefaultState(), new ItemInfo(Items.WHEAT_SEEDS, 0), 0.7f, MeshType.STRING.getID());
		SieveRegistry.register(Blocks.DIRT.getDefaultState(), new ItemInfo(Items.MELON_SEEDS, 0), 0.35f, MeshType.STRING.getID());
		SieveRegistry.register(Blocks.DIRT.getDefaultState(), new ItemInfo(Items.PUMPKIN_SEEDS, 0), 0.35f, MeshType.STRING.getID());

		SieveRegistry.register(Blocks.SAND.getDefaultState(), new ItemInfo(Items.DYE, 3), 0.03f, MeshType.STRING.getID());

		SieveRegistry.register(Blocks.GRAVEL.getDefaultState(), new ItemInfo(Items.FLINT, 0), 0.25f, MeshType.FLINT.getID());
		SieveRegistry.register(Blocks.GRAVEL.getDefaultState(), new ItemInfo(Items.COAL, 0), 0.125f, MeshType.FLINT.getID());
		SieveRegistry.register(Blocks.GRAVEL.getDefaultState(), new ItemInfo(Items.DYE, 4), 0.05f, MeshType.FLINT.getID());

		SieveRegistry.register(Blocks.GRAVEL.getDefaultState(), new ItemInfo(Items.DIAMOND, 0), 0.008f, MeshType.IRON.getID());
		SieveRegistry.register(Blocks.GRAVEL.getDefaultState(), new ItemInfo(Items.EMERALD, 0), 0.008f, MeshType.IRON.getID());

		SieveRegistry.register(Blocks.GRAVEL.getDefaultState(), new ItemInfo(Items.DIAMOND, 0), 0.016f, MeshType.DIAMOND.getID());
		SieveRegistry.register(Blocks.GRAVEL.getDefaultState(), new ItemInfo(Items.EMERALD, 0), 0.016f, MeshType.DIAMOND.getID());

		SieveRegistry.register(BlockHandler.DUST.getDefaultState(), new ItemInfo(Items.DYE, 15), 0.2f, MeshType.STRING.getID());
		SieveRegistry.register(BlockHandler.DUST.getDefaultState(), new ItemInfo(Items.GUNPOWDER, 0), 0.07f, MeshType.STRING.getID());

		SieveRegistry.register(BlockHandler.DUST.getDefaultState(), new ItemInfo(Items.REDSTONE, 0), 0.125f, MeshType.IRON.getID());
		SieveRegistry.register(BlockHandler.DUST.getDefaultState(), new ItemInfo(Items.REDSTONE, 0), 0.25f, MeshType.DIAMOND.getID());

		SieveRegistry.register(BlockHandler.DUST.getDefaultState(), new ItemInfo(Items.GLOWSTONE_DUST, 0), 0.0625f, MeshType.IRON.getID());
		SieveRegistry.register(BlockHandler.DUST.getDefaultState(), new ItemInfo(Items.BLAZE_POWDER, 0), 0.05f, MeshType.IRON.getID());

		// Ores
		for (ItemOre ore : OreRegistry.getItemOreRegistry())
		{
			SieveRegistry.register(Blocks.GRAVEL.getDefaultState(), new ItemStack(ore, 1, 0), 0.2f, MeshType.FLINT.getID());
			SieveRegistry.register(Blocks.GRAVEL.getDefaultState(), new ItemStack(ore, 1, 0), 0.2f, MeshType.IRON.getID());
			SieveRegistry.register(Blocks.GRAVEL.getDefaultState(), new ItemStack(ore, 1, 0), 0.1f, MeshType.DIAMOND.getID());
		}

		SieveRegistry.register(Blocks.DIRT.getDefaultState(), new ItemInfo(ItemHandler.SEEDGRASS, 0), 0.05f, MeshType.STRING.getID());
		SieveRegistry.register(Blocks.DIRT.getDefaultState(), new ItemInfo(ItemHandler.MUSHROOMSPORES, 0), 0.05f, MeshType.STRING.getID());
		SieveRegistry.register(Blocks.DIRT.getDefaultState(), new ItemInfo(ItemHandler.CACTUSSEEDS, 0), 0.05f, MeshType.STRING.getID());
	}

	@Override
	public void registerHammerRecipeDefaults() {
		HammerRegistry.register(Blocks.NETHERRACK.getDefaultState(), new ItemStack(Blocks.GRAVEL, 1), 0, 1.0F, 0.0F);
		HammerRegistry.register(Blocks.STONE.getDefaultState(), new ItemStack(Blocks.COBBLESTONE, 1), 0, 1.0F, 0.0F);
		HammerRegistry.register(Blocks.COBBLESTONE.getDefaultState(), new ItemStack(Blocks.GRAVEL, 1), 0, 1.0F, 0.0F);
		HammerRegistry.register(Blocks.GRAVEL.getDefaultState(), new ItemStack(Blocks.SAND, 1), 0, 1.0F, 0.0F);
		HammerRegistry.register(Blocks.SAND.getDefaultState(), new ItemStack(BlockHandler.DUST, 1), 0, 1.0F, 0.0F);
	}


	@Override
	public void registerCondenserRecipeDefaults() {
		CondenserRegistry.register(new ItemStack(Items.ROTTEN_FLESH, 1, 0), 112);
		CondenserRegistry.register(new ItemStack(Items.APPLE, 1, 0), 42);
		CondenserRegistry.register(new ItemStack(Items.CHORUS_FRUIT, 1, 0), 42);
		CondenserRegistry.register(new ItemStack(Items.CHORUS_FRUIT_POPPED, 1, 0), 42);
		CondenserRegistry.register(new ItemStack(Items.CARROT, 1, 0), 42);
		CondenserRegistry.register(new ItemStack(Items.EGG, 1, 0), 42);	
		//fish
		CondenserRegistry.register(new ItemStack(Items.FISH, 1, 0), 63);
		//cooked fish
		CondenserRegistry.register(new ItemStack(Items.COOKED_FISH,1, 0), 63);
		
		//salmon
		CondenserRegistry.register(new ItemStack(Items.FISH, 1, 1), 63);
		//cooked salmon
		CondenserRegistry.register(new ItemStack(Items.COOKED_FISH, 1, 1), 63);
			
		//clownfish
		CondenserRegistry.register(new ItemStack(Items.FISH, 1, 2), 63);
		//blowfish
		CondenserRegistry.register(new ItemStack(Items.FISH, 1, 3), 63);	
		
		CondenserRegistry.register(new ItemStack(Blocks.BROWN_MUSHROOM, 1, 0), 63);
		CondenserRegistry.register(new ItemStack(Blocks.RED_MUSHROOM, 1, 0), 63);
		CondenserRegistry.register(new ItemStack(Items.MELON,1, 0), 9);
		CondenserRegistry.register(new ItemStack(Blocks.MELON_BLOCK,1, 0), 1);	
		CondenserRegistry.register(new ItemStack(Items.POISONOUS_POTATO,1, 0), 42);
		CondenserRegistry.register(new ItemStack(Items.PORKCHOP,1, 0), 63);
		CondenserRegistry.register(new ItemStack(Items.COOKED_PORKCHOP,1, 0), 63);	
		CondenserRegistry.register(new ItemStack(Items.POTATO,1, 0), 42);	
		CondenserRegistry.register(new ItemStack(Items.BAKED_POTATO,1, 0), 42);
		CondenserRegistry.register(new ItemStack(Items.BEEF,1, 0), 63);	
		CondenserRegistry.register(new ItemStack(Items.COOKED_BEEF,1, 0), 63);	
		CondenserRegistry.register(new ItemStack(Items.CHICKEN,1, 0), 63);	
		CondenserRegistry.register(new ItemStack(Items.COOKED_CHICKEN,1, 0), 63);
		CondenserRegistry.register(new ItemStack(Items.RABBIT_STEW,1, 0), 1);
		CondenserRegistry.register(new ItemStack(Items.RABBIT,1, 0), 63);
		CondenserRegistry.register(new ItemStack(Items.COOKED_RABBIT,1, 0), 63);
		CondenserRegistry.register(new ItemStack(Items.MUTTON,1, 0), 63);
		CondenserRegistry.register(new ItemStack(Items.COOKED_MUTTON,1, 0), 63);
		CondenserRegistry.register(new ItemStack(Items.MAGMA_CREAM,1, 0), 112);	
		CondenserRegistry.register(new ItemStack(Items.SLIME_BALL,1, 0), 112);	
		CondenserRegistry.register(new ItemStack(Item.getItemFromBlock(Blocks.PUMPKIN),1, 0), 250);	
		CondenserRegistry.register(new ItemStack(Item.getItemFromBlock(Blocks.CACTUS),1, 0), 700);
		
		for(int i = 0; i < 6; i++){
			CondenserRegistry.register(new ItemStack(Blocks.SAPLING, 1, i), 125);
		}
		for(int i = 0; i < 4; i++){
			CondenserRegistry.register(new ItemStack(Blocks.LEAVES, 1, i), 125);
		}
		for(int i = 0; i < 2; i++){
			CondenserRegistry.register(new ItemStack(Blocks.LEAVES2, 1, i), 125);
		}
		
		for(ItemStack sap : OreDictionary.getOres("treeSapling")){
			CondenserRegistry.register(sap, 125);
		}
		
		for(ItemStack sap : OreDictionary.getOres("treeLeaves")){
			CondenserRegistry.register(sap, 125);
		}
		for(ItemStack sap : OreDictionary.getOres("vine")){
			CondenserRegistry.register(sap, 125);
		}
	}
}