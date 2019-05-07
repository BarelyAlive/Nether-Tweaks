/*package mod.nethertweaks.registries.manager;

import java.util.LinkedHashMap;
import java.util.Map;

import mod.nethertweaks.api.ICondenserDefaultRegistryProvider;
import mod.nethertweaks.api.IHellmartDefaultRegistryProvider;
import mod.nethertweaks.blocks.Sieve.MeshType;
import mod.nethertweaks.handler.BlockHandler;
import mod.nethertweaks.handler.ItemHandler;
import mod.nethertweaks.items.ItemOre;
import mod.nethertweaks.items.ItemPebble;
import mod.nethertweaks.registries.registries.CompostRegistry;
import mod.nethertweaks.registries.registries.CondenserRegistry;
import mod.nethertweaks.registries.registries.FluidBlockTransformerRegistry;
import mod.nethertweaks.registries.registries.FluidOnTopRegistry;
import mod.nethertweaks.registries.registries.FluidTransformRegistry;
import mod.nethertweaks.registries.registries.HammerRegistry;
import mod.nethertweaks.registries.registries.HellmartRegistry;
import mod.nethertweaks.registries.registries.SieveRegistry;
import mod.nethertweaks.registry.types.HellmartData;
import mod.sfhcore.texturing.Color;
import mod.sfhcore.util.BlockInfo;
import mod.sfhcore.util.ItemInfo;
import mod.sfhcore.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.OreDictionary.OreRegisterEvent;

public class NTMDefaultRecipes3 implements IHammerDefaultRegistryProvider, ICondenserDefaultRegistryProvider, ISieveDefaultRegistryProvider, ICompostDefaultRegistryProvider,
											IFluidBlockDefaultRegistryProvider, IFluidOnTopDefaultRegistryProvider, IFluidTransformDefaultRegistryProvider, IHellmartDefaultRegistryProvider{

	private final IBlockState dirt = Blocks.DIRT.getDefaultState();
	private final IBlockState gravel = Blocks.GRAVEL.getDefaultState();
	private final IBlockState sand = Blocks.SAND.getDefaultState();
	private final IBlockState dust = BlockHandler.DUST.getDefaultState();
	
	public NTMDefaultRecipes3()
	{
		NTMRegistryManager.registerHammerDefaultRecipeHandler(this);
		NTMRegistryManager.registerCondenserDefaultRecipeHandler(this);
		NTMRegistryManager.registerSieveDefaultRecipeHandler(this);
		NTMRegistryManager.registerCompostDefaultRecipeHandler(this);
		NTMRegistryManager.registerFluidBlockDefaultRecipeHandler(this);
		NTMRegistryManager.registerFluidTransformDefaultRecipeHandler(this);
		NTMRegistryManager.registerFluidOnTopDefaultRecipeHandler(this);
		NTMRegistryManager.registerHellmartDefaultRecipeHandler(this);
		
		registerCompostRecipeDefaults();
		registerCondenserRecipeDefaults();
		registerFluidBlockRecipeDefaults();
		registerFluidOnTopRecipeDefaults();
		registerFluidTransformRecipeDefaults();
		registerHammerRecipeDefaults();
		registerHellmartRecipeDefaults();
		registerSieveRecipeDefaults();
	}

	@Override
	public void registerSieveRecipeDefaults()
	{		
		SieveRegistry.register(dirt, ItemPebble.getPebbleStack("stone"), 1f, MeshType.STRING.getID());
		SieveRegistry.register(dirt, ItemPebble.getPebbleStack("stone"), 1f, MeshType.STRING.getID());
		SieveRegistry.register(dirt, ItemPebble.getPebbleStack("stone"), 0.5f, MeshType.STRING.getID());
		SieveRegistry.register(dirt, ItemPebble.getPebbleStack("stone"), 0.5f, MeshType.STRING.getID());
		SieveRegistry.register(dirt, ItemPebble.getPebbleStack("stone"), 0.1f, MeshType.STRING.getID());
		SieveRegistry.register(dirt, ItemPebble.getPebbleStack("stone"), 0.1f, MeshType.STRING.getID());

		SieveRegistry.register(dirt, ItemPebble.getPebbleStack("granite"), 0.5f, MeshType.STRING.getID());
		SieveRegistry.register(dirt, ItemPebble.getPebbleStack("granite"), 0.1f, MeshType.STRING.getID());

		SieveRegistry.register(dirt, ItemPebble.getPebbleStack("diorite"), 0.5f, MeshType.STRING.getID());
		SieveRegistry.register(dirt, ItemPebble.getPebbleStack("diorite"), 0.1f, MeshType.STRING.getID());

		SieveRegistry.register(dirt, ItemPebble.getPebbleStack("andesite"), 0.5f, MeshType.STRING.getID());
		SieveRegistry.register(dirt, ItemPebble.getPebbleStack("andesite"), 0.1f, MeshType.STRING.getID());

		SieveRegistry.register(dirt, new ItemInfo(Items.WHEAT_SEEDS, 0), 0.7f, MeshType.STRING.getID());
		SieveRegistry.register(dirt, new ItemInfo(Items.MELON_SEEDS, 0), 0.35f, MeshType.STRING.getID());
		SieveRegistry.register(dirt, new ItemInfo(Items.PUMPKIN_SEEDS, 0), 0.35f, MeshType.STRING.getID());

		SieveRegistry.register(sand, new ItemInfo(Items.DYE, 3), 0.03f, MeshType.STRING.getID());
		SieveRegistry.register(sand, new ItemInfo(Items.PRISMARINE_SHARD, 0), 0.02f, MeshType.DIAMOND.getID());

		SieveRegistry.register(gravel, new ItemInfo(Items.FLINT, 0), 0.25f, MeshType.FLINT.getID());
		SieveRegistry.register(gravel, new ItemInfo(Items.COAL, 0), 0.125f, MeshType.FLINT.getID());
		SieveRegistry.register(gravel, new ItemInfo(Items.DYE, 4), 0.05f, MeshType.FLINT.getID());

		SieveRegistry.register(gravel, new ItemInfo(Items.DIAMOND, 0), 0.008f, MeshType.IRON.getID());
		SieveRegistry.register(gravel, new ItemInfo(Items.EMERALD, 0), 0.008f, MeshType.IRON.getID());

		SieveRegistry.register(gravel, new ItemInfo(Items.DIAMOND, 0), 0.016f, MeshType.DIAMOND.getID());
		SieveRegistry.register(gravel, new ItemInfo(Items.EMERALD, 0), 0.016f, MeshType.DIAMOND.getID());

		SieveRegistry.register(dust, new ItemInfo(Items.DYE, 15), 0.2f, MeshType.STRING.getID());
		SieveRegistry.register(dust, new ItemInfo(Items.GUNPOWDER, 0), 0.07f, MeshType.STRING.getID());

		SieveRegistry.register(dust, new ItemInfo(Items.REDSTONE, 0), 0.125f, MeshType.IRON.getID());
		SieveRegistry.register(dust, new ItemInfo(Items.REDSTONE, 0), 0.25f, MeshType.DIAMOND.getID());

		SieveRegistry.register(dust, new ItemInfo(Items.GLOWSTONE_DUST, 0), 0.0625f, MeshType.IRON.getID());
		SieveRegistry.register(dust, new ItemInfo(Items.BLAZE_POWDER, 0), 0.05f, MeshType.IRON.getID());
		
		SieveRegistry.register(dust, new ItemInfo(Items.GLOWSTONE_DUST, 0), 0.125f, MeshType.DIAMOND.getID());
		SieveRegistry.register(dust, new ItemInfo(Items.BLAZE_POWDER, 0), 0.1f, MeshType.DIAMOND.getID());

		//Ores
		SieveRegistry.register(gravel, new ItemStack(ItemHandler.ITEMBASE, 1, 1), 0.1f, MeshType.FLINT.getID());
		SieveRegistry.register(gravel, new ItemStack(ItemHandler.ITEMBASE, 1, 1), 0.15f, MeshType.IRON.getID());
		SieveRegistry.register(gravel, new ItemStack(ItemHandler.ITEMBASE, 1, 1), 0.25f, MeshType.DIAMOND.getID());
		
		SieveRegistry.register(dust, new ItemStack(ItemHandler.OREMATERIAL, 1, 0), 0.05f, MeshType.FLINT.getID());
		SieveRegistry.register(dust, new ItemStack(ItemHandler.OREMATERIAL, 1, 0), 0.075f, MeshType.IRON.getID());
		
		SieveRegistry.register(dust, new ItemStack(ItemHandler.OREMATERIAL, 1, 1), 0.05f, MeshType.FLINT.getID());
		SieveRegistry.register(dust, new ItemStack(ItemHandler.OREMATERIAL, 1, 1), 0.075f, MeshType.IRON.getID());
		
		SieveRegistry.register(dust, new ItemStack(ItemHandler.OREMATERIAL, 1, 2), 0.05f, MeshType.FLINT.getID());
		SieveRegistry.register(dust, new ItemStack(ItemHandler.OREMATERIAL, 1, 2), 0.075f, MeshType.IRON.getID());
		
		SieveRegistry.register(dust, new ItemStack(ItemHandler.OREMATERIAL, 1, 3), 0.05f, MeshType.FLINT.getID());
		SieveRegistry.register(dust, new ItemStack(ItemHandler.OREMATERIAL, 1, 3), 0.75f, MeshType.IRON.getID());
		
		SieveRegistry.register(dust, new ItemStack(ItemHandler.OREMATERIAL, 1, 4), 0.05f, MeshType.IRON.getID());
		SieveRegistry.register(dust, new ItemStack(ItemHandler.OREMATERIAL, 1, 4), 0.10f, MeshType.DIAMOND.getID());
		
		SieveRegistry.register(dust, new ItemStack(ItemHandler.OREMATERIAL, 1, 1), 0.15f, MeshType.DIAMOND.getID());
		
		SieveRegistry.register(dust, new ItemStack(ItemHandler.OREMATERIAL, 1, 1), 0.05f, MeshType.IRON.getID());
		SieveRegistry.register(dust, new ItemStack(ItemHandler.OREMATERIAL, 1, 1), 0.10f, MeshType.DIAMOND.getID());
		
		SieveRegistry.register(dust, new ItemStack(ItemHandler.OREMATERIAL, 1, 1), 0.05f, MeshType.IRON.getID());
		SieveRegistry.register(dust, new ItemStack(ItemHandler.OREMATERIAL, 1, 1), 0.10f, MeshType.DIAMOND.getID());
		
		SieveRegistry.register(dirt, new ItemInfo(ItemHandler.SEED, 0), 0.05f, MeshType.STRING.getID());
		SieveRegistry.register(dirt, new ItemInfo(ItemHandler.SEED, 1), 0.05f, MeshType.STRING.getID());
		SieveRegistry.register(dirt, new ItemInfo(ItemHandler.SEED, 2), 0.05f, MeshType.STRING.getID());
		
		if(Item.REGISTRY.containsKey(new ResourceLocation("minefactoryreloaded", "rubberwood.sapling"))){
			ItemStack rubber = new ItemStack(Block.REGISTRY.getObject(new ResourceLocation("minefactoryreloaded", "rubberwood.sapling")));
			SieveRegistry.register(dirt, new ItemInfo(rubber), 0.05f, MeshType.FLINT.getID());
            SieveRegistry.register(dirt, new ItemInfo(rubber), 0.05f, MeshType.IRON.getID());
            SieveRegistry.register(dirt, new ItemInfo(rubber), 0.05f, MeshType.DIAMOND.getID());
		}
		
		getLeavesSapling().forEach((leaves, sapling) -> {
            BlockLeaves blockLeaves = ((BlockLeaves) leaves.getBlock());
            float chance = 20.0f / 100f;

            SieveRegistry.register(leaves, new ItemInfo(sapling.getBlockState()), Math.min(chance * 1, 1.0f), MeshType.STRING.getID());
            SieveRegistry.register(leaves, new ItemInfo(sapling.getBlockState()), Math.min(chance * 2, 1.0f), MeshType.FLINT.getID());
            SieveRegistry.register(leaves, new ItemInfo(sapling.getBlockState()), Math.min(chance * 3, 1.0f), MeshType.IRON.getID());
            SieveRegistry.register(leaves, new ItemInfo(sapling.getBlockState()), Math.min(chance * 4, 1.0f), MeshType.DIAMOND.getID());

            //Apple
            SieveRegistry.register(leaves, new ItemInfo(Items.APPLE, 0), 0.05f, MeshType.STRING.getID());
            SieveRegistry.register(leaves, new ItemInfo(Items.APPLE, 0), 0.10f, MeshType.FLINT.getID());
            SieveRegistry.register(leaves, new ItemInfo(Items.APPLE, 0), 0.15f, MeshType.IRON.getID());
            SieveRegistry.register(leaves, new ItemInfo(Items.APPLE, 0), 0.20f, MeshType.DIAMOND.getID());

            //Golden Apple
            SieveRegistry.register(leaves, new ItemInfo(Items.GOLDEN_APPLE, 0), 0.001f, MeshType.STRING.getID());
            SieveRegistry.register(leaves, new ItemInfo(Items.GOLDEN_APPLE, 0), 0.003f, MeshType.FLINT.getID());
            SieveRegistry.register(leaves, new ItemInfo(Items.GOLDEN_APPLE, 0), 0.005f, MeshType.IRON.getID());
            SieveRegistry.register(leaves, new ItemInfo(Items.GOLDEN_APPLE, 0), 0.01f, MeshType.DIAMOND.getID());
		});
	}

	@Override
	public void registerHammerRecipeDefaults()
	{
		HammerRegistry.register(Blocks.NETHERRACK.getDefaultState(), new ItemStack(Blocks.GRAVEL, 1), 0, 1.0F, 0.0F);
		HammerRegistry.register(Blocks.STONE.getDefaultState(), new ItemStack(Blocks.COBBLESTONE, 1), 0, 1.0F, 0.0F);
		HammerRegistry.register(Blocks.COBBLESTONE.getDefaultState(), new ItemStack(Blocks.GRAVEL, 1), 0, 1.0F, 0.0F);
		HammerRegistry.register(Blocks.GRAVEL.getDefaultState(), new ItemStack(Blocks.SAND, 1), 0, 1.0F, 0.0F);
		HammerRegistry.register(Blocks.SAND.getDefaultState(), new ItemStack(BlockHandler.DUST, 1), 0, 1.0F, 0.0F);

        // Hammer concrete into concrete powder
        for (int meta = 0; meta < 16; meta++)
        	HammerRegistry.register(BlockInfo.getStateFromMeta(Blocks.CONCRETE, meta), new ItemStack(Blocks.CONCRETE_POWDER, 1, meta), 1, 1.0f, 0.0f);
	}


	@Override
	public void registerCondenserRecipeDefaults()
	{
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
		
		for(int i = 0; i < 6; i++)
			CondenserRegistry.register(new ItemStack(Blocks.SAPLING, 1, i), 125);
		for(int i = 0; i < 4; i++)
			CondenserRegistry.register(new ItemStack(Blocks.LEAVES, 1, i), 125);
		for(int i = 0; i < 2; i++)
			CondenserRegistry.register(new ItemStack(Blocks.LEAVES2, 1, i), 125);
		for(ItemStack sap : OreDictionary.getOres("treeSapling"))
			CondenserRegistry.register(sap, 125);
		for(ItemStack sap : OreDictionary.getOres("treeLeaves"))
			CondenserRegistry.register(sap, 125);
		for(ItemStack sap : OreDictionary.getOres("vine"))
			CondenserRegistry.register(sap, 125);
		for (ItemStack stack: OreDictionary.getOres("listAllfruit"))
			CondenserRegistry.register(stack, 63);
		for (ItemStack stack: OreDictionary.getOres("listAllveggie"))
			CondenserRegistry.register(stack, 63);
		for (ItemStack stack: OreDictionary.getOres("listAllGrain"))
			CondenserRegistry.register(stack, 42);
		for (ItemStack stack: OreDictionary.getOres("listAllseed"))
			CondenserRegistry.register(stack, 42);
		for (ItemStack stack: OreDictionary.getOres("listAllmeatraw"))
			CondenserRegistry.register(stack, 63);
	}
	
	@Override
	public void registerCompostRecipeDefaults()
	{
		CompostRegistry.register(Items.ROTTEN_FLESH, 0, 0.1f, dirt, new Color("C45631"));

		CompostRegistry.register(Blocks.SAPLING, 0, 0.125f, dirt, new Color("35A82A"));
		CompostRegistry.register(Blocks.SAPLING, 1, 0.125f, dirt, new Color("2E8042"));
		CompostRegistry.register(Blocks.SAPLING, 2, 0.125f, dirt, new Color("6CC449"));
		CompostRegistry.register(Blocks.SAPLING, 3, 0.125f, dirt, new Color("22A116"));
		CompostRegistry.register(Blocks.SAPLING, 4, 0.125f, dirt, new Color("B8C754"));
		CompostRegistry.register(Blocks.SAPLING, 5, 0.125f, dirt, new Color("378030"));

		CompostRegistry.register(Blocks.LEAVES, 0, 0.125f, dirt, new Color("35A82A"));
		CompostRegistry.register(Blocks.LEAVES, 1, 0.125f, dirt, new Color("2E8042"));
		CompostRegistry.register(Blocks.LEAVES, 2, 0.125f, dirt, new Color("6CC449"));
		CompostRegistry.register(Blocks.LEAVES, 3, 0.125f, dirt, new Color("22A116"));
		CompostRegistry.register(Blocks.LEAVES2, 0, 0.125f, dirt, new Color("B8C754"));
		CompostRegistry.register(Blocks.LEAVES2, 1, 0.125f, dirt, new Color("378030"));

		CompostRegistry.register(Items.SPIDER_EYE, 0, 0.08f, dirt, new Color("963E44"));

		CompostRegistry.register(Items.WHEAT, 0, 0.08f, dirt, new Color("E3E162"));	
		CompostRegistry.register(Items.WHEAT_SEEDS, 0, 0.08f, dirt, new Color("35A82A"));
		CompostRegistry.register(Items.BREAD, 0, 0.16f, dirt, new Color("D1AF60"));

		CompostRegistry.register(Blocks.YELLOW_FLOWER, 0, 0.10f, dirt, new Color("FFF461"));
		CompostRegistry.register(Blocks.RED_FLOWER, 0, 0.10f, dirt, new Color("FF1212"));
		CompostRegistry.register(Blocks.RED_FLOWER, 1, 0.10f, dirt, new Color("33CFFF"));
		CompostRegistry.register(Blocks.RED_FLOWER, 2, 0.10f, dirt, new Color("F59DFA"));
		CompostRegistry.register(Blocks.RED_FLOWER, 3, 0.10f, dirt, new Color("E3E3E3"));
		CompostRegistry.register(Blocks.RED_FLOWER, 4, 0.10f, dirt, new Color("FF3D12"));
		CompostRegistry.register(Blocks.RED_FLOWER, 5, 0.10f, dirt, new Color("FF7E29"));
		CompostRegistry.register(Blocks.RED_FLOWER, 6, 0.10f, dirt, new Color("FFFFFF"));
		CompostRegistry.register(Blocks.RED_FLOWER, 7, 0.10f, dirt, new Color("F5C4FF"));
		CompostRegistry.register(Blocks.RED_FLOWER, 8, 0.10f, dirt, new Color("E9E9E9"));

		CompostRegistry.register(Blocks.DOUBLE_PLANT, 0, 0.10f, dirt, new Color("FFDD00"));
		CompostRegistry.register(Blocks.DOUBLE_PLANT, 1, 0.10f, dirt, new Color("FCC7F0"));
		CompostRegistry.register(Blocks.DOUBLE_PLANT, 4, 0.10f, dirt, new Color("FF1212"));
		CompostRegistry.register(Blocks.DOUBLE_PLANT, 5, 0.10f, dirt, new Color("F3D2FC"));

		CompostRegistry.register(Blocks.BROWN_MUSHROOM, 0, 0.10f, dirt, new Color("CFBFB6"));
		CompostRegistry.register(Blocks.RED_MUSHROOM, 0, 0.10f, dirt, new Color("D6A8A5"));

		CompostRegistry.register(Items.PUMPKIN_PIE, 0, 0.16f, dirt, new Color("E39A6D"));

		CompostRegistry.register(Items.PORKCHOP, 0, 0.2f, dirt, new Color("FFA091"));
		CompostRegistry.register(Items.COOKED_PORKCHOP, 0, 0.2f, dirt, new Color("FFFDBD"));

		CompostRegistry.register(Items.BEEF, 0, 0.2f, dirt, new Color("FF4242"));
		CompostRegistry.register(Items.COOKED_BEEF, 0, 0.2f, dirt, new Color("80543D"));

		CompostRegistry.register(Items.CHICKEN, 0, 0.2f, dirt, new Color("FFE8E8"));
		CompostRegistry.register(Items.COOKED_CHICKEN, 0, 0.2f, dirt, new Color("FA955F"));

		CompostRegistry.register(Items.FISH, 0, 0.15f, dirt, new Color("6DCFB3"));
		CompostRegistry.register(Items.COOKED_FISH, 0, 0.15f, dirt, new Color("D8EBE5"));

		CompostRegistry.register(Items.FISH, 1, 0.15f, dirt, new Color("FF2E4A"));
		CompostRegistry.register(Items.COOKED_FISH, 1, 0.15f, dirt, new Color("E87A3F"));

		CompostRegistry.register(Items.FISH, 2, 0.15f, dirt, new Color("FF771C"));
		CompostRegistry.register(Items.FISH, 3, 0.15f, dirt, new Color("DBFAFF"));

		CompostRegistry.register(Items.APPLE, 0, 0.10f, dirt, new Color("FFF68F"));
		CompostRegistry.register(Items.MELON, 0, 0.04f, dirt, new Color("FF443B"));
		CompostRegistry.register(Blocks.MELON_BLOCK, 0, 1.0f / 6, dirt, new Color("FF443B"));
		CompostRegistry.register(Blocks.PUMPKIN, 0, 1.0f / 6, dirt, new Color("FFDB66"));
		CompostRegistry.register(Blocks.LIT_PUMPKIN, 0, 1.0f / 6, dirt, new Color("FFDB66"));

		CompostRegistry.register(Blocks.CACTUS, 0, 0.10f, dirt, new Color("DEFFB5"));

		CompostRegistry.register(Items.CARROT, 0, 0.08f, dirt, new Color("FF9B0F"));
		CompostRegistry.register(Items.POTATO, 0, 0.08f, dirt, new Color("FFF1B5"));
		CompostRegistry.register(Items.BAKED_POTATO, 0, 0.08f, dirt, new Color("FFF1B5"));
		CompostRegistry.register(Items.POISONOUS_POTATO, 0, 0.08f, dirt, new Color("E0FF8A"));

		CompostRegistry.register(Blocks.WATERLILY, 0, 0.10f, dirt, new Color("269900"));
		CompostRegistry.register(Blocks.VINE, 0, 0.10f, dirt, new Color("23630E"));
		CompostRegistry.register(Blocks.TALLGRASS, 1, 0.08f, dirt, new Color("23630E"));
		CompostRegistry.register(Items.EGG, 0, 0.08f, dirt, new Color("FFFA66"));
		CompostRegistry.register(Items.NETHER_WART, 0, 0.10f, dirt, new Color("FF2B52"));
		CompostRegistry.register(Items.REEDS, 0, 0.08f, dirt, new Color("9BFF8A"));
		CompostRegistry.register(BlockHandler.MEANVINE, 0, 0.10f, dirt, new Color("800080"));
		CompostRegistry.register(Items.STRING, 0, 0.04f, dirt, Util.whiteColor);
		//NTM
		CompostRegistry.register(ItemHandler.COOKEDJERKY, 0, 0.2f, dirt, new Color("FF4242"));
		
		for (ItemStack stack: OreDictionary.getOres("listAllfruit")) {
			//Register any missed items
			CompostRegistry.register(stack.getItem(), stack.getItemDamage(), 0.10f, dirt, new Color("35A82A"));
		}
		for (ItemStack stack: OreDictionary.getOres("listAllveggie")) {
			//Register any missed items
			CompostRegistry.register(stack.getItem(), stack.getItemDamage(), 0.10f, dirt, new Color("FFF1B5"));
		}
		for (ItemStack stack: OreDictionary.getOres("listAllGrain")) {
			//Register any missed items
			CompostRegistry.register(stack.getItem(), stack.getItemDamage(), 0.08f, dirt, new Color("E3E162"));
		}
		for (ItemStack stack: OreDictionary.getOres("listAllseed")) {
			//Register any missed items
			CompostRegistry.register(stack.getItem(), stack.getItemDamage(), 0.08f, dirt, new Color("35A82A"));
		}
		for (ItemStack stack: OreDictionary.getOres("listAllmeatraw")) {
			//Register any missed items
			CompostRegistry.register(stack.getItem(), stack.getItemDamage(), 0.15f, dirt, new Color("FFA091"));
		}
		for (ItemStack stack: OreDictionary.getOres("vine")) {
			//Register any missed items
			CompostRegistry.register(stack.getItem(), stack.getItemDamage(), 0.10f, dirt, new Color("23630E"));
		}
		for (ItemStack stack: OreDictionary.getOres("string")) {
			//Register any missed items
			CompostRegistry.register(stack.getItem(), stack.getItemDamage(), 0.04f, dirt, Util.whiteColor);
		}
	}
	
	@Override
	public void registerFluidBlockRecipeDefaults()
	{
		FluidBlockTransformerRegistry.register(FluidRegistry.WATER, new ItemInfo(new ItemStack(BlockHandler.DUST)), new ItemInfo(new ItemStack(Blocks.CLAY)));
		FluidBlockTransformerRegistry.register(FluidRegistry.LAVA, new ItemInfo(new ItemStack(Items.REDSTONE)), new ItemInfo(new ItemStack(Blocks.NETHERRACK)));
		FluidBlockTransformerRegistry.register(FluidRegistry.LAVA, new ItemInfo(new ItemStack(Items.GLOWSTONE_DUST)), new ItemInfo(new ItemStack(Blocks.END_STONE)));
	}
	
	@Override
	public void registerFluidTransformRecipeDefaults()
	{
	}
	
	@Override
	public void registerFluidOnTopRecipeDefaults()
	{
		FluidOnTopRegistry.register(FluidRegistry.LAVA, FluidRegistry.WATER, new ItemInfo(Blocks.OBSIDIAN.getDefaultState()));
		FluidOnTopRegistry.register(FluidRegistry.WATER, FluidRegistry.LAVA, new ItemInfo(Blocks.COBBLESTONE.getDefaultState()));
	}
	
	@Override
	public void registerHellmartRecipeDefaults()
	{
		for (int i = 0; i < 14; i++) {
			HellmartRegistry.register(i, new HellmartData(new ItemStack(ItemHandler.DOLL, 1, i), new ItemStack(Items.EMERALD), 10));
		}
	}
	
	private static Map<BlockInfo, BlockInfo> getLeavesSapling(){
        Map<BlockInfo, BlockInfo> saplingMap = new LinkedHashMap<>();
        saplingMap.put(new BlockInfo(Blocks.LEAVES, 0), new BlockInfo(Blocks.SAPLING, 0));
        saplingMap.put(new BlockInfo(Blocks.LEAVES, 1), new BlockInfo(Blocks.SAPLING, 1));
        saplingMap.put(new BlockInfo(Blocks.LEAVES, 2), new BlockInfo(Blocks.SAPLING, 2));
        saplingMap.put(new BlockInfo(Blocks.LEAVES, 3), new BlockInfo(Blocks.SAPLING, 3));
        saplingMap.put(new BlockInfo(Blocks.LEAVES2, 0), new BlockInfo(Blocks.SAPLING, 4));
        saplingMap.put(new BlockInfo(Blocks.LEAVES2, 1), new BlockInfo(Blocks.SAPLING, 5));

        return saplingMap;
	}
}*/