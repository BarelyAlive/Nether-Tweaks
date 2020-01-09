package mod.nethertweaks.recipes.defaults;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import mod.nethertweaks.Constants;
import mod.nethertweaks.block.Sieve.MeshType;
import mod.nethertweaks.config.Config;
import mod.nethertweaks.init.ModBlocks;
import mod.nethertweaks.init.ModFluids;
import mod.nethertweaks.init.ModItems;
import mod.nethertweaks.init.OreHandler;
import mod.nethertweaks.item.ItemChunk;
import mod.nethertweaks.registry.ingredient.OreIngredientStoring;
import mod.nethertweaks.registry.registries.BarrelLiquidBlacklistRegistry;
import mod.nethertweaks.registry.registries.CompostRegistry;
import mod.nethertweaks.registry.registries.CondenserRegistry;
import mod.nethertweaks.registry.registries.CrucibleRegistry;
import mod.nethertweaks.registry.registries.DrinkRegistry;
import mod.nethertweaks.registry.registries.FluidBlockTransformerRegistry;
import mod.nethertweaks.registry.registries.FluidItemFluidRegistry;
import mod.nethertweaks.registry.registries.FluidOnTopRegistry;
import mod.nethertweaks.registry.registries.FluidTransformRegistry;
import mod.nethertweaks.registry.registries.HammerRegistry;
import mod.nethertweaks.registry.registries.HeatRegistry;
import mod.nethertweaks.registry.registries.HellmartRegistry;
import mod.nethertweaks.registry.registries.MilkEntityRegistry;
import mod.nethertweaks.registry.registries.SieveRegistry;
import mod.nethertweaks.registry.registries.base.types.HammerReward;
import mod.sfhcore.handler.BucketHandler;
import mod.sfhcore.helper.NameHelper;
import mod.sfhcore.texturing.Color;
import mod.sfhcore.util.BlockInfo;
import mod.sfhcore.util.ItemInfo;
import mod.sfhcore.util.TankUtil;
import mod.sfhcore.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.oredict.OreDictionary;

public class NTM implements IRecipeDefaults
{
    @Override
	public String getMODID() {
		return Constants.MOD_ID;
	}

	@Override
	public void registerCompost(final CompostRegistry registry)
	{
		final BlockInfo dirtState = new BlockInfo(Blocks.DIRT);

		registry.register(new ItemInfo(Items.ROTTEN_FLESH), 0.1f, dirtState, new Color("C45631"));
		registry.register(new ItemInfo(Items.SPIDER_EYE), 0.08f, dirtState, new Color("963E44"));
		registry.register(new ItemInfo(Items.WHEAT), 0.08f, dirtState, new Color("E3E162"));
		registry.register(new ItemInfo(Items.WHEAT_SEEDS), 0.08f, dirtState, new Color("35A82A"));
		registry.register(new ItemInfo(Items.BREAD), 0.16f, dirtState, new Color("D1AF60"));
		registry.register(new BlockInfo(Blocks.BROWN_MUSHROOM), 0.10f, dirtState, new Color("CFBFB6"));
		registry.register(new BlockInfo(Blocks.RED_MUSHROOM), 0.10f, dirtState, new Color("D6A8A5"));
		registry.register(new ItemInfo(Items.PUMPKIN_PIE), 0.16f, dirtState, new Color("E39A6D"));
		registry.register(new ItemInfo(Items.PORKCHOP), 0.2f, dirtState, new Color("FFA091"));
		registry.register(new ItemInfo(Items.BEEF), 0.2f, dirtState, new Color("FF4242"));
		registry.register(new ItemInfo(Items.CHICKEN), 0.2f, dirtState, new Color("FFE8E8"));
		registry.register(new ItemInfo(Items.APPLE), 0.10f, dirtState, new Color("FFF68F"));
		registry.register(new ItemInfo(Items.MELON), 0.04f, dirtState, new Color("FF443B"));
		registry.register(new BlockInfo(Blocks.MELON_BLOCK), 1.0f / 6, dirtState, new Color("FF443B"));
		registry.register(new BlockInfo(Blocks.PUMPKIN), 1.0f / 6, dirtState, new Color("FFDB66"));
		registry.register(new BlockInfo(Blocks.LIT_PUMPKIN), 1.0f / 6, dirtState, new Color("FFDB66"));
		registry.register(new BlockInfo(Blocks.CACTUS), 0.10f, dirtState, new Color("DEFFB5"));
		registry.register(new ItemInfo(Items.CARROT), 0.08f, dirtState, new Color("FF9B0F"));
		registry.register(new ItemInfo(Items.POTATO), 0.08f, dirtState, new Color("FFF1B5"));
		registry.register(new ItemInfo(Items.BAKED_POTATO), 0.08f, dirtState, new Color("FFF1B5"));
		registry.register(new ItemInfo(Items.POISONOUS_POTATO), 0.08f, dirtState, new Color("E0FF8A"));
		registry.register(new BlockInfo(Blocks.WATERLILY.getDefaultState()), 0.10f, dirtState, new Color("269900"));
		registry.register(new BlockInfo(Blocks.TALLGRASS, 1), 0.08f, dirtState, new Color("23630E"));
		registry.register(new BlockInfo(Blocks.TALLGRASS, 2), 0.08f, dirtState, new Color("23630E"));
		registry.register(new ItemInfo(Items.EGG), 0.08f, dirtState, new Color("FFFA66"));
		registry.register(new ItemInfo(Items.NETHER_WART), 0.10f, dirtState, new Color("FF2B52"));
		registry.register(new ItemInfo(Items.REEDS), 0.08f, dirtState, new Color("9BFF8A"));
		registry.register(new ItemInfo(Items.STRING), 0.04f, dirtState, Util.whiteColor);

		//Register any missed organic items
		registry.register("vine", 0.10f, dirtState, new Color("23630E"));
		registry.register("listAllfruit", 0.10f, dirtState, new Color("35A82A"));
		registry.register("listAllveggie", 0.10f, dirtState, new Color("FFF1B5"));
		registry.register("listAllGrain", 0.08f, dirtState, new Color("E3E162"));
		registry.register("listAllseed", 0.08f, dirtState, new Color("35A82A"));
		registry.register("listAllmeatraw", 0.15f, dirtState, new Color("FFA091"));
		registry.register("treeSapling", 0.125f, dirtState, new Color("4DA83B"));
		registry.register("treeLeaves", 0.125f, dirtState, new Color("1E932D"));
		registry.register("flower", 0.1f, dirtState, new Color("708D13"));
		registry.register("fish", 0.15f, dirtState, new Color("4A7090"));
		registry.register("listAllmeatcooked", 0.20f, dirtState, new Color("8D0002"));

		// Misc. Modded Items
		registry.register("dustAsh", 0.125f, dirtState, new Color("C0C0C0"));
	}

	@Override
	public void registerHellmart(final HellmartRegistry registry)
	{
		final ItemInfo ice = new ItemInfo(Blocks.ICE);

		//Dolls
		registry.register(new ItemInfo(ModItems.DOLL_BAT), ice, 3);
		registry.register(new ItemInfo(ModItems.DOLL_CHICKEN), ice, 10);
		registry.register(new ItemInfo(ModItems.DOLL_COW), ice, 25);
		registry.register(new ItemInfo(ModItems.DOLL_DONKEY), ice, 10);
		registry.register(new ItemInfo(ModItems.DOLL_HORSE), ice, 15);
		registry.register(new ItemInfo(ModItems.DOLL_LLAMA), ice, 10);
		registry.register(new ItemInfo(ModItems.DOLL_MULE), ice, 10);
		registry.register(new ItemInfo(ModItems.DOLL_OCELOT), ice, 5);
		registry.register(new ItemInfo(ModItems.DOLL_PARROT), ice, 3);
		registry.register(new ItemInfo(ModItems.DOLL_PIG), ice, 10);
		registry.register(new ItemInfo(ModItems.DOLL_POLAR_BEAR), ice, 3);
		registry.register(new ItemInfo(ModItems.DOLL_RABBIT), ice, 10);
		registry.register(new ItemInfo(ModItems.DOLL_RED_MOOSHROOM), ice, 35);
		registry.register(new ItemInfo(ModItems.DOLL_SHEEP), ice, 20);
		registry.register(new ItemInfo(ModItems.DOLL_VILLAGER), ice, 50);
		registry.register(new ItemInfo(ModItems.DOLL_WOLF), ice, 5);

		final Ingredient ingredient = new OreIngredientStoring("treeSapling");
		for(final ItemStack ore : ingredient.getMatchingStacks())
			registry.register(new ItemInfo(ore), ice, 3);
	}

	@Override
	public void registerCondenser(final CondenserRegistry registry)
	{
		//Blood to water
		for(Fluid f : FluidRegistry.getBucketFluids())
			if(f.getName().equals("blood") || f.getName().equals("lifeEssence"))
			{
				registry.register(new ItemInfo(FluidUtil.getFilledBucket(new FluidStack(f, 1000))), 523);
				registry.register(new ItemInfo(BucketHandler.getBucketFromFluid(f, "wood")), 523);
				registry.register(new ItemInfo(BucketHandler.getBucketFromFluid(f, "stone")), 523);
			}

		//F00D
		registry.register(new ItemInfo(Items.ROTTEN_FLESH), 90);
		registry.register(new ItemInfo(Items.APPLE), 80);
		registry.register(new ItemInfo(Items.CHORUS_FRUIT), 42);
		registry.register(new ItemInfo(Items.CHORUS_FRUIT_POPPED), 42);
		registry.register(new ItemInfo(Items.CARROT), 42);
		registry.register(new ItemInfo(Items.EGG), 42);
		//fish
		registry.register(new ItemInfo(Items.FISH), 63);
		//cooked fish
		registry.register(new ItemInfo(Items.COOKED_FISH), 50);
		//salmon
		registry.register(new ItemInfo(Items.FISH, 1), 63);
		//cooked salmon
		registry.register(new ItemInfo(Items.COOKED_FISH, 1), 50);
		//clownfish
		registry.register(new ItemInfo(Items.FISH, 2), 63);
		//blowfish
		registry.register(new ItemInfo(Items.FISH, 3), 63);

		registry.register(new ItemInfo(Items.MELON), 38);
		registry.register(new BlockInfo(Blocks.MELON_BLOCK), 350);
		registry.register(new ItemInfo(Items.POISONOUS_POTATO), 42);
		registry.register(new ItemInfo(Items.PORKCHOP), 63);
		registry.register(new ItemInfo(Items.COOKED_PORKCHOP), 50);
		registry.register(new ItemInfo(Items.POTATO), 42);
		registry.register(new ItemInfo(Items.BAKED_POTATO), 33);
		registry.register(new ItemInfo(Items.BEEF), 63);
		registry.register(new ItemInfo(Items.COOKED_BEEF), 50);
		registry.register(new ItemInfo(Items.CHICKEN), 63);
		registry.register(new ItemInfo(Items.COOKED_CHICKEN), 50);
		registry.register(new ItemInfo(Items.RABBIT_STEW), 100);
		registry.register(new ItemInfo(Items.RABBIT), 63);
		registry.register(new ItemInfo(Items.COOKED_RABBIT), 50);
		registry.register(new ItemInfo(Items.MUTTON), 63);
		registry.register(new ItemInfo(Items.COOKED_MUTTON), 50);
		registry.register(new ItemInfo(Items.MAGMA_CREAM), 68);
		registry.register(new ItemInfo(Items.SLIME_BALL), 45);
		registry.register(new BlockInfo(Blocks.SLIME_BLOCK), 405);
		registry.register(new BlockInfo(Blocks.PUMPKIN), 250);
		registry.register(new BlockInfo(Blocks.CACTUS), 300);
		registry.register(new BlockInfo(Blocks.BROWN_MUSHROOM), 63);
		registry.register(new BlockInfo(Blocks.RED_MUSHROOM), 63);

		registry.register("treeSapling", 100);
		registry.register("treeLeaves", 100);
		registry.register("vine", 125);
		registry.register("listAllfruit", 80);
		registry.register("listAllveggie", 80);
		registry.register("listAllGrain", 42);
		registry.register("listAllseed", 42);
		registry.register("listAllmeatraw", 63);
		registry.register("listAllmeatcooked", 63);
		registry.register("listAllfishraw", 63);
		registry.register("listAllfishcooked", 63);
		registry.register("listAllfishfresh", 63);
	}

	@Override
	public void registerDrink(final DrinkRegistry registry)
	{
		registry.register(TankUtil.WATER_BOTTLE, 3, 1.4f, 0f);
		registry.register(new ItemInfo(Items.MILK_BUCKET), 8, 3.4f, 0f);
		registry.register(new ItemInfo(Items.MUSHROOM_STEW), 7, 1.2f, 0.4f);
		registry.register(new ItemInfo(Items.RABBIT_STEW), 7, 2.0f, 0f);
		registry.register(new ItemInfo(Items.BEETROOT_SOUP), 8, 1.2f, 0f);
	}

	@Override
	public void registerSieve(final SieveRegistry registry)
	{
		//DIRT
		registry.register("dirt", new ItemInfo(ModItems.PEBBLE_STONE), getDropChance(1f), MeshType.STRING.getName());
		registry.register("dirt", new ItemInfo(ModItems.PEBBLE_STONE), getDropChance(1f), MeshType.STRING.getName());
		registry.register("dirt", new ItemInfo(ModItems.PEBBLE_STONE), getDropChance(0.5f), MeshType.STRING.getName());
		registry.register("dirt", new ItemInfo(ModItems.PEBBLE_STONE), getDropChance(0.5f), MeshType.STRING.getName());
		registry.register("dirt", new ItemInfo(ModItems.PEBBLE_STONE), getDropChance(0.1f), MeshType.STRING.getName());
		registry.register("dirt", new ItemInfo(ModItems.PEBBLE_STONE), getDropChance(0.1f), MeshType.STRING.getName());

		registry.register("dirt", new ItemInfo(ModItems.PEBBLE_GRANITE), getDropChance(0.5f), MeshType.STRING.getName());
		registry.register("dirt", new ItemInfo(ModItems.PEBBLE_GRANITE), getDropChance(0.1f), MeshType.STRING.getName());

		registry.register("dirt", new ItemInfo(ModItems.PEBBLE_DIORITE), getDropChance(0.5f), MeshType.STRING.getName());
		registry.register("dirt", new ItemInfo(ModItems.PEBBLE_DIORITE), getDropChance(0.1f), MeshType.STRING.getName());

		registry.register("dirt", new ItemInfo(ModItems.PEBBLE_ANDESITE), getDropChance(0.5f), MeshType.STRING.getName());
		registry.register("dirt", new ItemInfo(ModItems.PEBBLE_ANDESITE), getDropChance(0.1f), MeshType.STRING.getName());

		registry.register("dirt", new ItemInfo(Items.WHEAT_SEEDS), getDropChance(0.7f), MeshType.STRING.getName());
		registry.register("dirt", new ItemInfo(Items.MELON_SEEDS), getDropChance(0.35f), MeshType.STRING.getName());
		registry.register("dirt", new ItemInfo(Items.PUMPKIN_SEEDS), getDropChance(0.35f), MeshType.STRING.getName());
		registry.register("dirt", new ItemInfo(Items.BEETROOT_SEEDS), getDropChance(0.5f), MeshType.STRING.getName());

		//SAND
		registry.register("sand", new ItemInfo(Items.DYE, 3), getDropChance(0.03f), MeshType.STRING.getName());
		registry.register("sand", new ItemInfo(Items.PRISMARINE_SHARD), getDropChance(0.02f), MeshType.DIAMOND.getName());

		registry.register("sand", new ItemInfo(ModItems.SALT), getDropChance(0.02f), MeshType.STRING.getName());
		registry.register("sand", new ItemInfo(ModItems.SALT), getDropChance(0.04f), MeshType.FLINT.getName());
		registry.register("sand", new ItemInfo(ModItems.SALT), getDropChance(0.06f), MeshType.IRON.getName());
		registry.register("sand", new ItemInfo(ModItems.SALT), getDropChance(0.10f), MeshType.DIAMOND.getName());

		// There needs to be a way to get flint without a flint mesh
		registry.register("gravel", new ItemInfo(Items.FLINT), getDropChance(0.25f), MeshType.STRING.getName());
		registry.register("gravel", new ItemInfo(Items.FLINT), getDropChance(0.25f), MeshType.FLINT.getName());
		registry.register("gravel", new ItemInfo(Items.COAL), getDropChance(0.125f), MeshType.FLINT.getName());
		registry.register("gravel", new ItemInfo(Items.DYE, 4), getDropChance(0.05f), MeshType.FLINT.getName());

		registry.register("gravel", new ItemInfo(Items.DIAMOND), getDropChance(0.008f), MeshType.IRON.getName());
		registry.register("gravel", new ItemInfo(Items.EMERALD), getDropChance(0.008f), MeshType.IRON.getName());

		registry.register("gravel", new ItemInfo(Items.DIAMOND), getDropChance(0.016f), MeshType.DIAMOND.getName());
		registry.register("gravel", new ItemInfo(Items.EMERALD), getDropChance(0.016f), MeshType.DIAMOND.getName());

		registry.register(new ItemStack(Blocks.SOUL_SAND), new ItemInfo(Items.QUARTZ), getDropChance(1f), MeshType.FLINT.getName());
		registry.register(new ItemStack(Blocks.SOUL_SAND), new ItemInfo(Items.QUARTZ), getDropChance(0.33f), MeshType.FLINT.getName());

		registry.register(new ItemStack(Blocks.SOUL_SAND), new ItemInfo(Items.NETHER_WART), getDropChance(0.1f), MeshType.STRING.getName());

		registry.register(new ItemStack(Blocks.SOUL_SAND), new ItemInfo(Items.GHAST_TEAR), getDropChance(0.02f), MeshType.DIAMOND.getName());
		registry.register(new ItemStack(Blocks.SOUL_SAND), new ItemInfo(Items.QUARTZ), getDropChance(1f), MeshType.DIAMOND.getName());
		registry.register(new ItemStack(Blocks.SOUL_SAND), new ItemInfo(Items.QUARTZ), getDropChance(0.8f), MeshType.DIAMOND.getName());

		//DUST
		registry.register(new ItemStack(ModBlocks.DUST), new ItemInfo(Items.DYE, 15), getDropChance(0.2f), MeshType.STRING.getName());
		registry.register(new ItemStack(ModBlocks.DUST), new ItemInfo(Items.GUNPOWDER), getDropChance(0.07f), MeshType.STRING.getName());

		registry.register(new ItemStack(ModBlocks.DUST), new ItemInfo(Items.REDSTONE), getDropChance(0.125f), MeshType.IRON.getName());
		registry.register(new ItemStack(ModBlocks.DUST), new ItemInfo(Items.REDSTONE), getDropChance(0.25f), MeshType.DIAMOND.getName());

		registry.register(new ItemStack(ModBlocks.DUST), new ItemInfo(Items.GLOWSTONE_DUST), getDropChance(0.0625f), MeshType.IRON.getName());
		registry.register(new ItemStack(ModBlocks.DUST), new ItemInfo(Items.BLAZE_POWDER), getDropChance(0.05f), MeshType.IRON.getName());

		//Damit Saplinge erfasst werden, die auch von ihren Leaves gedroppt werden sollen
		for(final ItemStack leaves : OreDictionary.getOres("treeLeaves"))
		{
			final Block leafBlock = Block.getBlockFromItem(leaves.getItem());
			final IBlockState state = leafBlock.getDefaultState();
			final Random rand = new Random();
			final Item sapling = leafBlock.getItemDropped(state, rand, 0);

			if (Block.getBlockFromItem(sapling) instanceof IPlantable)
			{
				final boolean hc = Objects.equals(NameHelper.getModID(sapling), "harvestcraft");

				if(hc && Config.enableHarvestcraft)
					registry.register("dirt", new ItemInfo(sapling), getDropChance(0.05f), MeshType.STRING.getName());
				else if(!hc)
					registry.register("dirt", new ItemInfo(sapling), getDropChance(0.05f), MeshType.STRING.getName());

				registry.register(new BlockInfo(state), new ItemInfo(sapling), getDropChance(0.20f), MeshType.STRING.getName());
				registry.register(new BlockInfo(state), new ItemInfo(sapling), getDropChance(0.40f), MeshType.FLINT.getName());
				registry.register(new BlockInfo(state), new ItemInfo(sapling), getDropChance(0.60f), MeshType.IRON.getName());
				registry.register(new BlockInfo(state), new ItemInfo(sapling), getDropChance(0.80f), MeshType.DIAMOND.getName());
			}
		}

		// Custom Ores for other mods
		// All default Ores
		for (final ItemChunk ore : OreHandler.MOD_CHUNKS.values()) {
			final ItemInfo info = new ItemInfo(ore);
			final String name = ore.getOreName();
			switch (name) {
			case "iron":
				registry.register("gravel", info.copy(), getDropChance(0.1f/4.0f), MeshType.FLINT.getName());
				registry.register("gravel", info.copy(), getDropChance(0.15f/4.0f), MeshType.IRON.getName());
				registry.register("gravel", info.copy(), getDropChance(0.25f/4.0f), MeshType.DIAMOND.getName());
				registry.register("sand", info.copy(), getDropChance(0.5f/4.0f), MeshType.DIAMOND.getName());
				break;
			case "gold":
				registry.register(new ItemStack(ModBlocks.DUST), info.copy(), getDropChance(0.25f/4.0f), MeshType.FLINT.getName());
				registry.register(new ItemStack(ModBlocks.DUST), info.copy(), getDropChance(0.25f/4.0f), MeshType.IRON.getName());
				registry.register(new ItemStack(ModBlocks.DUST), info.copy(), getDropChance(0.4f/4.0f), MeshType.DIAMOND.getName());
			case "aluminum":
			case "aluminium":
				registry.register("sand", info.copy(), getDropChance(0.05f/4.0f), MeshType.FLINT.getName());
				registry.register("sand", info.copy(), getDropChance(0.075f/4.0f), MeshType.IRON.getName());
				break;
			case "copper":
				registry.register("gravel", info.copy(), getDropChance(0.05f/4.0f), MeshType.FLINT.getName());
				registry.register("gravel", info.copy(), getDropChance(0.075f/4.0f), MeshType.IRON.getName());
				break;
			case "silver":
				registry.register("sand", info.copy(), getDropChance(0.15f/4.0f), MeshType.DIAMOND.getName());
				break;
			case "tin":
				registry.register("sand", info.copy(), getDropChance(0.05f/4.0f), MeshType.FLINT.getName());
				registry.register("sand", info.copy(), getDropChance(0.075f/4.0f), MeshType.IRON.getName());
				break;
			case "uranium":
				registry.register("gravel", info.copy(), getDropChance(0.05f/4.0f), MeshType.IRON.getName());
				registry.register("gravel", info.copy(), getDropChance(0.10f/4.0f), MeshType.DIAMOND.getName());
				break;
			case "zinc":
				registry.register("sand", info.copy(), getDropChance(0.075f/4.0f), MeshType.IRON.getName());
				registry.register("sand", info.copy(), getDropChance(0.15f/4.0f), MeshType.DIAMOND.getName());
				break;
			default:
				registry.register("gravel", info.copy(), getDropChance(0.05f/4.0f), MeshType.FLINT.getName());
				registry.register("gravel", info.copy(), getDropChance(0.075f/4.0f), MeshType.IRON.getName());
				registry.register("gravel", info.copy(), getDropChance(0.15f/4.0f), MeshType.DIAMOND.getName());
				break;
			}
		}

		// Seeds
		registry.register("dirt", new ItemInfo(ModItems.GRASS_SEEDS), getDropChance(0.05f), MeshType.STRING.getName());
		registry.register("dirt", new ItemInfo(ModItems.SUGARCANE_SEEDS), getDropChance(0.05f), MeshType.STRING.getName());
		registry.register("sand", new ItemInfo(ModItems.MUSHROOM_SPORES), getDropChance(0.05f), MeshType.STRING.getName());
		registry.register("sand", new ItemInfo(ModItems.CACTUS_SEEDS), getDropChance(0.05f), MeshType.STRING.getName());

		getLeavesSapling().forEach((leaves, sapling) ->
		{
			final float chance = 20f / 100f;

			registry.register(leaves, sapling, Math.min(chance * 1, 1.0f), MeshType.STRING.getName());
			registry.register(leaves, sapling, Math.min(chance * 2, 1.0f), MeshType.FLINT.getName());
			registry.register(leaves, sapling, Math.min(chance * 3, 1.0f), MeshType.IRON.getName());
			registry.register(leaves, sapling, Math.min(chance * 4, 1.0f), MeshType.DIAMOND.getName());

			//Apple
			registry.register(leaves, new ItemInfo(Items.APPLE), 0.05f, MeshType.STRING.getName());
			registry.register(leaves, new ItemInfo(Items.APPLE), 0.10f, MeshType.FLINT.getName());
			registry.register(leaves, new ItemInfo(Items.APPLE), 0.15f, MeshType.IRON.getName());
			registry.register(leaves, new ItemInfo(Items.APPLE), 0.20f, MeshType.DIAMOND.getName());

			//Golden Apple
			registry.register(leaves, new ItemInfo(Items.GOLDEN_APPLE), 0.001f, MeshType.STRING.getName());
			registry.register(leaves, new ItemInfo(Items.GOLDEN_APPLE), 0.003f, MeshType.FLINT.getName());
			registry.register(leaves, new ItemInfo(Items.GOLDEN_APPLE), 0.005f, MeshType.IRON.getName());
			registry.register(leaves, new ItemInfo(Items.GOLDEN_APPLE), 0.01f, MeshType.DIAMOND.getName());
		});
	}

	@Override
	public void registerHammer(final HammerRegistry registry)
	{
		registry.register("netherrack", new ItemStack(ModBlocks.NETHERRACK_GRAVEL, 1), 0, 1.0F, 0.0F);
		registry.register(ModBlocks.NETHERRACK_GRAVEL.getDefaultState(), new ItemStack(Blocks.SAND, 1, 1), 0, 1.0F, 0.0F);
		registry.register("stone", new ItemStack(Blocks.COBBLESTONE, 1), 0, 1.0F, 0.0F);
		registry.register("cobblestone", new ItemStack(Blocks.GRAVEL, 1), 0, 1.0F, 0.0F);

		//Yes, I have to do this otherwise i can'split the outputs
		for(final ItemStack block : OreDictionary.getOres("gravel"))
			if(block.getItem() != Item.getItemFromBlock(ModBlocks.NETHERRACK_GRAVEL))
				registry.register(block, new HammerReward(new ItemStack(Blocks.SAND, 1), 0, 1.0F, 0.0F));
		registry.register("sand", new ItemStack(ModBlocks.DUST, 1), 0, 1.0F, 0.0F);

		// Hammer concrete into concrete powder
		for (int meta = 0; meta < 16; meta++)
			registry.register(BlockInfo.getStateFromMeta(Blocks.CONCRETE, meta), new ItemStack(Blocks.CONCRETE_POWDER, 1, meta), 1, 1.0f, 0.0f);

		registry.register(Blocks.GLOWSTONE.getDefaultState(), new ItemStack(Items.GLOWSTONE_DUST, 4), 0, 1.0F, 0.0F);
		registry.register("logWood", new ItemStack(ModItems.WOOD_CHIPPINGS, 4), 0, 1.0F, 0.0F);
		registry.register("plankWood", new ItemStack(ModItems.WOOD_CHIPPINGS, 1), 0, 1.0F, 0.0F);
	}

	@Override
	public void registerHeat(final HeatRegistry registry)
	{
		registry.register(new BlockInfo(Blocks.TORCH), 1);
		registry.register(new BlockInfo(Blocks.MAGMA), 2);
		registry.register(new BlockInfo(Blocks.GLOWSTONE), 2);
		registry.register(new BlockInfo(Blocks.FLOWING_LAVA), 3);
		registry.register(new BlockInfo(Blocks.LAVA), 3);
		registry.register(new BlockInfo(Blocks.FIRE), 4);

		// Hot Fluids
		for(final Fluid fluid : FluidRegistry.getRegisteredFluids().values())
			if(fluid != FluidRegistry.LAVA) {
				final int T = fluid.getTemperature() / 350; // Value arbitrarily chosen to make it line up with lava's heat value
				if(T > 0 && fluid.getBlock() != null)
					registry.register(new BlockInfo(fluid.getBlock()), T);
			}

		registry.register("blockUranium", 20);
		registry.register("blockBlaze", 10);
		registry.register("torch", 1); // Torch OreDict
	}

	@Override
	public void registerBarrelLiquidBlacklist(final BarrelLiquidBlacklistRegistry registry) {
		for(final Fluid fluid : FluidRegistry.getRegisteredFluids().values())
			if(fluid.getTemperature() >= Config.woodBarrelMaxTemp)
				registry.register(ModBlocks.OAK_BARREL.getTier(), fluid);
	}

	@Override
	public void registerFluidOnTop(final FluidOnTopRegistry registry)
	{
		registry.register(FluidRegistry.LAVA, FluidRegistry.WATER, new BlockInfo(Blocks.OBSIDIAN.getDefaultState()));
		registry.register(FluidRegistry.LAVA, ModFluids.FLUID_BRINE, new BlockInfo(Blocks.OBSIDIAN.getDefaultState()));
		registry.register(FluidRegistry.LAVA, ModFluids.FLUID_DISTILLED_WATER, new BlockInfo(Blocks.OBSIDIAN.getDefaultState()));

		registry.register(FluidRegistry.WATER, FluidRegistry.LAVA, new BlockInfo(Blocks.STONE.getDefaultState()));
		registry.register(ModFluids.FLUID_BRINE, FluidRegistry.LAVA, new BlockInfo(Blocks.STONE.getDefaultState()));
		registry.register(ModFluids.FLUID_DISTILLED_WATER, FluidRegistry.LAVA, new BlockInfo(Blocks.STONE.getDefaultState()));
	}

	@Override
	public void registerFluidTransform(final FluidTransformRegistry registry) {
		//registry.register("water", "witchwater", 12000, new BlockInfo[]{new BlockInfo(Blocks.MYCELIUM.getDefaultState())}, new BlockInfo[]{new BlockInfo(Blocks.BROWN_MUSHROOM.getDefaultState()), new BlockInfo(Blocks.RED_MUSHROOM.getDefaultState())});
	}

	@Override
	public void registerFluidBlockTransform(final FluidBlockTransformerRegistry registry)
	{
		registry.register(FluidRegistry.WATER, new ItemInfo(ModBlocks.DUST), new ItemInfo(Blocks.CLAY));
		registry.register(ModFluids.FLUID_BRINE, new ItemInfo(ModBlocks.DUST), new ItemInfo(Blocks.CLAY));
		registry.register(ModFluids.FLUID_DISTILLED_WATER, new ItemInfo(ModBlocks.DUST), new ItemInfo(Blocks.CLAY));
		registry.register(FluidRegistry.LAVA, "dustRedstone", new ItemInfo(Blocks.NETHERRACK));
		registry.register(FluidRegistry.LAVA, "dustGlowstone", new ItemInfo(Blocks.END_STONE));

		if(FluidRegistry.isFluidRegistered("milk")){
			registry.register(FluidRegistry.getFluid("milk"), new ItemInfo(Blocks.BROWN_MUSHROOM), new ItemInfo(Blocks.SLIME_BLOCK), "Slime");
			registry.register(FluidRegistry.getFluid("milk"), new ItemInfo(Blocks.RED_MUSHROOM), new ItemInfo(Blocks.SLIME_BLOCK), "Slime");
		} else
			// No milk, fall back to blood
			for(Fluid f : FluidRegistry.getBucketFluids())
				if(f.getName().equals("blood") || f.getName().equals("lifeEssence"))
				{
					registry.register(f, new ItemInfo(Blocks.BROWN_MUSHROOM), new ItemInfo(Blocks.SLIME_BLOCK), "Slime");
					registry.register(f, new ItemInfo(Blocks.RED_MUSHROOM), new ItemInfo(Blocks.SLIME_BLOCK), "Slime");
				}

		// Vanilla Concrete
		for(int meta = 0; meta < 16; meta++)
		{
			registry.register(FluidRegistry.WATER, new ItemInfo(Blocks.CONCRETE_POWDER, meta), new ItemInfo(Blocks.CONCRETE, meta));
			registry.register(ModFluids.FLUID_DISTILLED_WATER, new ItemInfo(Blocks.CONCRETE_POWDER, meta), new ItemInfo(Blocks.CONCRETE, meta));
			registry.register(ModFluids.FLUID_BRINE, new ItemInfo(Blocks.CONCRETE_POWDER, meta), new ItemInfo(Blocks.CONCRETE, meta));
		}
	}

	@Override
	public void registerFluidItemFluid(final FluidItemFluidRegistry registry)
	{
		registry.register(ModFluids.FLUID_DISTILLED_WATER, "itemSalt", FluidRegistry.WATER, 100, true);
		registry.register(ModFluids.FLUID_DISTILLED_WATER, "dustSalt", FluidRegistry.WATER, 100, true);
		registry.register(ModFluids.FLUID_DISTILLED_WATER, "blockSalt", ModFluids.FLUID_BRINE, 100, true);
	}

	@Override
	public void registerCrucibleStone(final CrucibleRegistry registry) {
		registry.register("cobblestone", FluidRegistry.LAVA, 250);
		registry.register("stone", FluidRegistry.LAVA, 250);
		registry.register("gravel", FluidRegistry.LAVA, 200);
		registry.register("sand", FluidRegistry.LAVA, 100);
		registry.register(new BlockInfo(ModBlocks.DUST), FluidRegistry.LAVA, 50);

		// 1:1 Back to lava
		registry.register("netherrack", FluidRegistry.LAVA, 1000);
		registry.register("obsidian", FluidRegistry.LAVA, 1000);

		//Back to water 1:1
		//registry.register(new ItemInfo(Items.SNOWBALL), FluidRegistry.WATER, 250);
		registry.register(new BlockInfo(Blocks.PACKED_ICE), FluidRegistry.WATER, 9000);
		registry.register(new BlockInfo(Blocks.ICE), FluidRegistry.WATER, 1000);
		registry.register(new BlockInfo(Blocks.SNOW), FluidRegistry.WATER, 1000);
		registry.register(new BlockInfo(Blocks.SNOW_LAYER), FluidRegistry.WATER, 125); //Blame Mojang for their unfair recipe
	}

	@Override
	public void registerMilk(final MilkEntityRegistry registry) {
		registry.register("Cow", "milk", 10, 20);
	}

	private float getDropChance(final float chance) {
		return chance / 100f * Config.normalDropPercent;
	}

	private static Map<BlockInfo, BlockInfo> getLeavesSapling()
	{
		final Map<BlockInfo, BlockInfo> saplingMap = new LinkedHashMap<>();
		saplingMap.put(new BlockInfo(Blocks.LEAVES, 0), new BlockInfo(Blocks.SAPLING, 0));
		saplingMap.put(new BlockInfo(Blocks.LEAVES, 1), new BlockInfo(Blocks.SAPLING, 1));
		saplingMap.put(new BlockInfo(Blocks.LEAVES, 2), new BlockInfo(Blocks.SAPLING, 2));
		saplingMap.put(new BlockInfo(Blocks.LEAVES, 3), new BlockInfo(Blocks.SAPLING, 3));
		saplingMap.put(new BlockInfo(Blocks.LEAVES2, 0), new BlockInfo(Blocks.SAPLING, 4));
		saplingMap.put(new BlockInfo(Blocks.LEAVES2, 1), new BlockInfo(Blocks.SAPLING, 5));

		return saplingMap;
	}
}