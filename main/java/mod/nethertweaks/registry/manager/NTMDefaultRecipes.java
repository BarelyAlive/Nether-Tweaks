package mod.nethertweaks.registry.manager;

import mod.nethertweaks.handler.BlockHandler;
import mod.nethertweaks.registry.DryRegistry;
import mod.nethertweaks.registry.HammerRegistry;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class NTMDefaultRecipes implements IHammerDefaultRegistryProvider, ICondenserDefaultRegistryProvider{

	public NTMDefaultRecipes() {
		RegistryManager.registerHammerDefaultRecipeHandler(this);
		RegistryManager.registerCondenserDefaultRecipeHandler(this);
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
		DryRegistry.register(new ItemStack(Items.ROTTEN_FLESH, 1, 0), 112);
		DryRegistry.register(new ItemStack(Items.APPLE, 1, 0), 42);
		DryRegistry.register(new ItemStack(Items.CHORUS_FRUIT, 1, 0), 42);
		DryRegistry.register(new ItemStack(Items.CHORUS_FRUIT_POPPED, 1, 0), 42);
		DryRegistry.register(new ItemStack(Items.CARROT, 1, 0), 42);
		DryRegistry.register(new ItemStack(Items.EGG, 1, 0), 42);	
		//fish
		DryRegistry.register(new ItemStack(Items.FISH, 1, 0), 63);
		//cooked fish
		DryRegistry.register(new ItemStack(Items.COOKED_FISH,1, 0), 63);
		
		//salmon
		DryRegistry.register(new ItemStack(Items.FISH, 1, 1), 63);
		//cooked salmon
		DryRegistry.register(new ItemStack(Items.COOKED_FISH, 1, 1), 63);
			
		//clownfish
		DryRegistry.register(new ItemStack(Items.FISH, 1, 2), 63);
		//blowfish
		DryRegistry.register(new ItemStack(Items.FISH, 1, 3), 63);	
		
		DryRegistry.register(new ItemStack(Blocks.BROWN_MUSHROOM, 1, 0), 63);
		DryRegistry.register(new ItemStack(Blocks.RED_MUSHROOM, 1, 0), 63);
		DryRegistry.register(new ItemStack(Items.MELON,1, 0), 9);
		DryRegistry.register(new ItemStack(Blocks.MELON_BLOCK,1, 0), 1);	
		DryRegistry.register(new ItemStack(Items.POISONOUS_POTATO,1, 0), 42);
		DryRegistry.register(new ItemStack(Items.PORKCHOP,1, 0), 63);
		DryRegistry.register(new ItemStack(Items.COOKED_PORKCHOP,1, 0), 63);	
		DryRegistry.register(new ItemStack(Items.POTATO,1, 0), 42);	
		DryRegistry.register(new ItemStack(Items.BAKED_POTATO,1, 0), 42);
		DryRegistry.register(new ItemStack(Items.BEEF,1, 0), 63);	
		DryRegistry.register(new ItemStack(Items.COOKED_BEEF,1, 0), 63);	
		DryRegistry.register(new ItemStack(Items.CHICKEN,1, 0), 63);	
		DryRegistry.register(new ItemStack(Items.COOKED_CHICKEN,1, 0), 63);
		DryRegistry.register(new ItemStack(Items.RABBIT_STEW,1, 0), 1);
		DryRegistry.register(new ItemStack(Items.RABBIT,1, 0), 63);
		DryRegistry.register(new ItemStack(Items.COOKED_RABBIT,1, 0), 63);
		DryRegistry.register(new ItemStack(Items.MUTTON,1, 0), 63);
		DryRegistry.register(new ItemStack(Items.COOKED_MUTTON,1, 0), 63);
		DryRegistry.register(new ItemStack(Items.MAGMA_CREAM,1, 0), 112);	
		DryRegistry.register(new ItemStack(Items.SLIME_BALL,1, 0), 112);	
		DryRegistry.register(new ItemStack(Item.getItemFromBlock(Blocks.PUMPKIN),1, 0), 250);	
		DryRegistry.register(new ItemStack(Item.getItemFromBlock(Blocks.CACTUS),1, 0), 700);
		
		for(int i = 0; i < 6; i++){
			DryRegistry.register(new ItemStack(Blocks.SAPLING, 1, i), 125);
		}
		for(int i = 0; i < 4; i++){
			DryRegistry.register(new ItemStack(Blocks.LEAVES, 1, i), 125);
		}
		for(int i = 0; i < 2; i++){
			DryRegistry.register(new ItemStack(Blocks.LEAVES2, 1, i), 125);
		}
		
		for(ItemStack sap : OreDictionary.getOres("treeSapling")){
			DryRegistry.register(sap, 125);
		}
		
		for(ItemStack sap : OreDictionary.getOres("treeLeaves")){
			DryRegistry.register(sap, 125);
		}
		for(ItemStack sap : OreDictionary.getOres("vine")){
			DryRegistry.register(sap, 125);
		}
	}
}