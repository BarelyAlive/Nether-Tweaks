package mod.nethertweaks.recipes.defaults;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.blocks.Barrel;
import mod.nethertweaks.blocks.Sieve.MeshType;
import mod.nethertweaks.config.Config;
import mod.nethertweaks.handler.BlockHandler;
import mod.nethertweaks.handler.BucketNFluidHandler;
import mod.nethertweaks.handler.ItemHandler;
import mod.nethertweaks.handler.OreHandler;
import mod.nethertweaks.items.ItemChunk;
import mod.nethertweaks.registries.ingredient.OreIngredientStoring;
import mod.nethertweaks.registries.registries.BarrelLiquidBlacklistRegistry;
import mod.nethertweaks.registries.registries.CompostRegistry;
import mod.nethertweaks.registries.registries.CondenserRegistry;
import mod.nethertweaks.registries.registries.CrucibleRegistry;
import mod.nethertweaks.registries.registries.FluidBlockTransformerRegistry;
import mod.nethertweaks.registries.registries.FluidItemFluidRegistry;
import mod.nethertweaks.registries.registries.FluidOnTopRegistry;
import mod.nethertweaks.registries.registries.FluidTransformRegistry;
import mod.nethertweaks.registries.registries.HammerRegistry;
import mod.nethertweaks.registries.registries.HeatRegistry;
import mod.nethertweaks.registries.registries.HellmartRegistry;
import mod.nethertweaks.registries.registries.MilkEntityRegistry;
import mod.nethertweaks.registries.registries.OreRegistry;
import mod.nethertweaks.registries.registries.SieveRegistry;
import mod.nethertweaks.registry.types.HammerReward;
import mod.sfhcore.helper.NameHelper;
import mod.sfhcore.texturing.Color;
import mod.sfhcore.util.BlockInfo;
import mod.sfhcore.util.ItemInfo;
import mod.sfhcore.util.OreDictUtil;
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
import net.minecraftforge.oredict.OreDictionary;

public class NTM implements IRecipeDefaults
{
    private final String MODID = NetherTweaksMod.MODID;

    @Override
	public String getMODID() {
		return MODID;
	}

	@Override
    public void registerCompost(CompostRegistry registry)
	{
        BlockInfo dirtState = new BlockInfo(Blocks.DIRT);
        
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
        registry.register(new BlockInfo(Blocks.VINE.getDefaultState()), 0.10f, dirtState, new Color("23630E"));
        registry.register(new BlockInfo(Blocks.TALLGRASS, 1), 0.08f, dirtState, new Color("23630E"));
        registry.register(new BlockInfo(Blocks.TALLGRASS, 2), 0.08f, dirtState, new Color("23630E"));
        registry.register(new ItemInfo(Items.EGG), 0.08f, dirtState, new Color("FFFA66"));
        registry.register(new ItemInfo(Items.NETHER_WART), 0.10f, dirtState, new Color("FF2B52"));
        registry.register(new ItemInfo(Items.REEDS), 0.08f, dirtState, new Color("9BFF8A"));
        registry.register(new ItemInfo(Items.STRING), 0.04f, dirtState, Util.whiteColor);

        //Register any missed organic items
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
    public void registerHellmart(HellmartRegistry registry)
    {
    	ItemInfo ice = new ItemInfo(Blocks.ICE);
    	
    	//Dolls
		registry.register(new ItemInfo(ItemHandler.DOLL_BAT), ice, 3);
		registry.register(new ItemInfo(ItemHandler.DOLL_CHICKEN), ice, 3);
		registry.register(new ItemInfo(ItemHandler.DOLL_COW), ice, 3);
		registry.register(new ItemInfo(ItemHandler.DOLL_DONKEY), ice, 3);
		registry.register(new ItemInfo(ItemHandler.DOLL_HORSE), ice, 3);
		registry.register(new ItemInfo(ItemHandler.DOLL_LLAMA), ice, 3);
		registry.register(new ItemInfo(ItemHandler.DOLL_MULE), ice, 3);
		registry.register(new ItemInfo(ItemHandler.DOLL_OCELOT), ice, 3);
		registry.register(new ItemInfo(ItemHandler.DOLL_PARROT), ice, 3);
		registry.register(new ItemInfo(ItemHandler.DOLL_PIG), ice, 3);
		registry.register(new ItemInfo(ItemHandler.DOLL_POLAR_BEAR), ice, 3);
		registry.register(new ItemInfo(ItemHandler.DOLL_RABBIT), ice, 3);
		registry.register(new ItemInfo(ItemHandler.DOLL_RED_MOOSHROOM), ice, 3);
		registry.register(new ItemInfo(ItemHandler.DOLL_SHEEP), ice, 3);
		registry.register(new ItemInfo(ItemHandler.DOLL_VILLAGER), ice, 3);
		registry.register(new ItemInfo(ItemHandler.DOLL_WOLF), ice, 3);
    	
    	Ingredient ingredient = new OreIngredientStoring("treeSapling");
    	for(ItemStack ore : ingredient.getMatchingStacks())
    		registry.register(new ItemInfo(ore), ice, 3);
    }
    
    @Override
    public void registerCondenser(CondenserRegistry registry)
    {
    	registry.register(new ItemInfo(Items.ROTTEN_FLESH), 90);
		registry.register(new ItemInfo(Items.APPLE), 80);
		registry.register(new ItemInfo(Items.CHORUS_FRUIT), 42);
		registry.register(new ItemInfo(Items.CHORUS_FRUIT_POPPED), 42);
		registry.register(new ItemInfo(Items.CARROT), 42);
		registry.register(new ItemInfo(Items.EGG), 42);	
		//fish
		registry.register(new ItemInfo(Items.FISH), 63);
		//cooked fish
		registry.register(new ItemInfo(Items.COOKED_FISH), 63);
		//salmon
		registry.register(new ItemInfo(Items.FISH, 1), 63);
		//cooked salmon
		registry.register(new ItemInfo(Items.COOKED_FISH, 1), 63);		
		//clownfish
		registry.register(new ItemInfo(Items.FISH, 2), 63);
		//blowfish
		registry.register(new ItemInfo(Items.FISH, 3), 63);
		
		registry.register(new ItemInfo(Items.MELON), 38);
		registry.register(new BlockInfo(Blocks.MELON_BLOCK), 350);	
		registry.register(new ItemInfo(Items.POISONOUS_POTATO), 42);
		registry.register(new ItemInfo(Items.PORKCHOP), 63);
		registry.register(new ItemInfo(Items.COOKED_PORKCHOP), 63);	
		registry.register(new ItemInfo(Items.POTATO), 42);	
		registry.register(new ItemInfo(Items.BAKED_POTATO), 42);
		registry.register(new ItemInfo(Items.BEEF), 63);	
		registry.register(new ItemInfo(Items.COOKED_BEEF), 63);	
		registry.register(new ItemInfo(Items.CHICKEN), 63);	
		registry.register(new ItemInfo(Items.COOKED_CHICKEN), 63);
		registry.register(new ItemInfo(Items.RABBIT_STEW), 100);
		registry.register(new ItemInfo(Items.RABBIT), 63);
		registry.register(new ItemInfo(Items.COOKED_RABBIT), 63);
		registry.register(new ItemInfo(Items.MUTTON), 63);
		registry.register(new ItemInfo(Items.COOKED_MUTTON), 63);
		registry.register(new ItemInfo(Items.MAGMA_CREAM), 68);
		registry.register(new ItemInfo(Items.SLIME_BALL), 45);
		registry.register(new BlockInfo(Blocks.SLIME_BLOCK), 405);
		registry.register(new BlockInfo(Blocks.PUMPKIN), 250);
		registry.register(new BlockInfo(Blocks.CACTUS), 300);
		registry.register(new BlockInfo(Blocks.BROWN_MUSHROOM), 63);
		registry.register(new BlockInfo(Blocks.RED_MUSHROOM), 63);
		
		//Back to water 1:1
        registry.register(new ItemInfo(Items.SNOWBALL), 250);
		registry.register(new BlockInfo(Blocks.PACKED_ICE), 9000);
        registry.register(new BlockInfo(Blocks.ICE), 1000);
        registry.register(new BlockInfo(Blocks.SNOW), 1000);
        registry.register(new BlockInfo(Blocks.SNOW_LAYER), 125); //Blame Mojang for their unfair recipe
        
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
    public void registerSieve(SieveRegistry registry)
    {
        //DIRT
        registry.register("dirt", new ItemInfo(ItemHandler.PEBBLE_STONE), getDropChance(1f), MeshType.STRING.getName());
        registry.register("dirt", new ItemInfo(ItemHandler.PEBBLE_STONE), getDropChance(1f), MeshType.STRING.getName());
        registry.register("dirt", new ItemInfo(ItemHandler.PEBBLE_STONE), getDropChance(0.5f), MeshType.STRING.getName());
        registry.register("dirt", new ItemInfo(ItemHandler.PEBBLE_STONE), getDropChance(0.5f), MeshType.STRING.getName());
        registry.register("dirt", new ItemInfo(ItemHandler.PEBBLE_STONE), getDropChance(0.1f), MeshType.STRING.getName());
        registry.register("dirt", new ItemInfo(ItemHandler.PEBBLE_STONE), getDropChance(0.1f), MeshType.STRING.getName());

        registry.register("dirt", new ItemInfo(ItemHandler.PEBBLE_GRANITE), getDropChance(0.5f), MeshType.STRING.getName());
        registry.register("dirt", new ItemInfo(ItemHandler.PEBBLE_GRANITE), getDropChance(0.1f), MeshType.STRING.getName());

        registry.register("dirt", new ItemInfo(ItemHandler.PEBBLE_DIORITE), getDropChance(0.5f), MeshType.STRING.getName());
        registry.register("dirt", new ItemInfo(ItemHandler.PEBBLE_DIORITE), getDropChance(0.1f), MeshType.STRING.getName());

        registry.register("dirt", new ItemInfo(ItemHandler.PEBBLE_ANDESITE), getDropChance(0.5f), MeshType.STRING.getName());
        registry.register("dirt", new ItemInfo(ItemHandler.PEBBLE_ANDESITE), getDropChance(0.1f), MeshType.STRING.getName());

        registry.register("dirt", new ItemInfo(Items.WHEAT_SEEDS), getDropChance(0.7f), MeshType.STRING.getName());
        registry.register("dirt", new ItemInfo(Items.MELON_SEEDS), getDropChance(0.35f), MeshType.STRING.getName());
        registry.register("dirt", new ItemInfo(Items.PUMPKIN_SEEDS), getDropChance(0.35f), MeshType.STRING.getName());

        registry.register("dirt", new ItemInfo(ItemHandler.MUSHROOM_SPORES), getDropChance(0.05f), MeshType.STRING.getName());
        registry.register("dirt", new ItemInfo(ItemHandler.GRASS_SEEDS), getDropChance(0.05f), MeshType.STRING.getName());

        //SAND
        registry.register("sand", new ItemInfo(Items.DYE, 3), getDropChance(0.03f), MeshType.STRING.getName());
        registry.register("sand", new ItemInfo(Items.PRISMARINE_SHARD), getDropChance(0.02f), MeshType.DIAMOND.getName());
        
        registry.register("sand", new ItemInfo(ItemHandler.SALT), getDropChance(0.02f), MeshType.STRING.getName());
        registry.register("sand", new ItemInfo(ItemHandler.SALT), getDropChance(0.04f), MeshType.FLINT.getName());
        registry.register("sand", new ItemInfo(ItemHandler.SALT), getDropChance(0.08f), MeshType.IRON.getName());
        registry.register("sand", new ItemInfo(ItemHandler.SALT), getDropChance(0.10f), MeshType.DIAMOND.getName());

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

        registry.register(new ItemStack(BlockHandler.DUST), new ItemInfo(Items.DYE, 15), getDropChance(0.2f), MeshType.STRING.getName());
        registry.register(new ItemStack(BlockHandler.DUST), new ItemInfo(Items.GUNPOWDER), getDropChance(0.07f), MeshType.STRING.getName());

        registry.register(new ItemStack(BlockHandler.DUST), new ItemInfo(Items.REDSTONE), getDropChance(0.125f), MeshType.IRON.getName());
        registry.register(new ItemStack(BlockHandler.DUST), new ItemInfo(Items.REDSTONE), getDropChance(0.25f), MeshType.DIAMOND.getName());

        registry.register(new ItemStack(BlockHandler.DUST), new ItemInfo(Items.GLOWSTONE_DUST), getDropChance(0.0625f), MeshType.IRON.getName());
        registry.register(new ItemStack(BlockHandler.DUST), new ItemInfo(Items.BLAZE_POWDER), getDropChance(0.05f), MeshType.IRON.getName());
        	
        //Damit Saplinge erfasst werden, die auch von ihren Leaves gedroppt werden sollen
        for(ItemStack leaves : OreDictionary.getOres("treeLeaves"))
        {
        	Block leafBlock = Block.getBlockFromItem(leaves.getItem());
        	IBlockState state = leafBlock.getDefaultState();
        	Random rand = new Random();      	
        	Item sapling = leafBlock.getItemDropped(state, rand, 0);
        	
        	if (Block.getBlockFromItem(sapling) instanceof IPlantable)
        	{
        		boolean hc = NameHelper.getModID(sapling) == "harvestcraft";
        		
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
        for (ItemChunk ore : OreHandler.mod_chunks.values()) {
				ItemInfo info = new ItemInfo(ore);
				String name = ore.getOreName();
				switch (name) {
				case "iron":
					registry.register("gravel", info.copy(), getDropChance(0.1f/4.0f), MeshType.FLINT.getName());
					registry.register("gravel", info.copy(), getDropChance(0.15f/4.0f), MeshType.IRON.getName());
					registry.register("gravel", info.copy(), getDropChance(0.25f/4.0f), MeshType.DIAMOND.getName());
					registry.register("sand", info.copy(), getDropChance(0.5f/4.0f), MeshType.DIAMOND.getName());
					break;
				case "gold":
					registry.register(new ItemStack(BlockHandler.DUST), info.copy(), getDropChance(0.25f/4.0f), MeshType.FLINT.getName());
		            registry.register(new ItemStack(BlockHandler.DUST), info.copy(), getDropChance(0.25f/4.0f), MeshType.IRON.getName());
		            registry.register(new ItemStack(BlockHandler.DUST), info.copy(), getDropChance(0.4f/4.0f), MeshType.DIAMOND.getName());
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
        	registry.register("sand", new ItemInfo(ItemHandler.MUSHROOM_SPORES), getDropChance(0.05f), MeshType.STRING.getName());
            registry.register("dirt", new ItemInfo(ItemHandler.GRASS_SEEDS), getDropChance(0.05f), MeshType.STRING.getName());
            registry.register("dirt", new ItemInfo(ItemHandler.CACTUS_SEEDS), getDropChance(0.05f), MeshType.STRING.getName());
            registry.register("dirt", new ItemInfo(ItemHandler.SUGARCANE_SEEDS), getDropChance(0.05f), MeshType.STRING.getName());
        
        getLeavesSapling().forEach((leaves, sapling) ->
        {
            float chance = 20f / 100f;

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
    public void registerHammer(HammerRegistry registry)
    {
    	registry.register("netherrack", new ItemStack(BlockHandler.NETHERRACK_GRAVEL, 1), 0, 1.0F, 0.0F);
    	registry.register(BlockHandler.NETHERRACK_GRAVEL.getDefaultState(), new ItemStack(Blocks.SAND, 1, 1), 0, 1.0F, 0.0F);
    	registry.register("stone", new ItemStack(Blocks.COBBLESTONE, 1), 0, 1.0F, 0.0F);
        registry.register("cobblestone", new ItemStack(Blocks.GRAVEL, 1), 0, 1.0F, 0.0F);
        
        //Yes, I have to do this otherwise i can'split the outputs
        for(ItemStack block : OreDictionary.getOres("gravel"))
        {
        	if(block.getItem() != Item.getItemFromBlock(BlockHandler.NETHERRACK_GRAVEL))
        		registry.register(block, new HammerReward(new ItemStack(Blocks.SAND, 1), 0, 1.0F, 0.0F));
        }
        registry.register("sand", new ItemStack(BlockHandler.DUST, 1), 0, 1.0F, 0.0F);

        // Hammer concrete into concrete powder
        for (int meta = 0; meta < 16; meta++)
            registry.register(BlockInfo.getStateFromMeta(Blocks.CONCRETE, meta), new ItemStack(Blocks.CONCRETE_POWDER, 1, meta), 1, 1.0f, 0.0f);
        
        registry.register(Blocks.GLOWSTONE.getDefaultState(), new ItemStack(Items.GLOWSTONE_DUST, 4), 0, 1.0F, 0.0F);
        registry.register("logWood", new ItemStack(ItemHandler.WOOD_CHIPPINGS, 4), 0, 1.0F, 0.0F);
        registry.register("plankWood", new ItemStack(ItemHandler.WOOD_CHIPPINGS, 1), 0, 1.0F, 0.0F);
    }

    @Override
    public void registerHeat(HeatRegistry registry)
    {
        // Vanilla fluids are weird, the "flowing" variant is simply a temporary state of checking if it can flow.
        // So, once the lava has spread out all the way, it will all actually be "still" lava.
        // Thanks Mojang <3
    	    	
        registry.register(new BlockInfo(Blocks.FLOWING_LAVA), 3);
        registry.register(new BlockInfo(Blocks.LAVA), 3);
        registry.register(new BlockInfo(Blocks.FIRE), 4);
        registry.register(new BlockInfo(Blocks.TORCH), 1);
        registry.register(new BlockInfo(Blocks.MAGMA), 2);
        registry.register(new BlockInfo(Blocks.GLOWSTONE), 2);

        // Hot Fluids
        for(Fluid fluid : FluidRegistry.getRegisteredFluids().values()) {
            if(fluid != FluidRegistry.LAVA) {
                final int T = fluid.getTemperature() / 350; // Value arbitrarily chosen to make it line up with lava's heat value
                if(T > 0 && fluid.getBlock() != null) {
                    registry.register(new BlockInfo(fluid.getBlock()), T);
                }
            }
        }

        registry.register("blockUranium", 20);
        registry.register("blockBlaze", 10);
        registry.register("torch", 1); // Torch OreDict
    }

    @Override
    public void registerBarrelLiquidBlacklist(BarrelLiquidBlacklistRegistry registry) {
        for(Fluid fluid : FluidRegistry.getRegisteredFluids().values()){
            if(fluid.getTemperature() >= Config.woodBarrelMaxTemp)
                registry.register(((Barrel)BlockHandler.BARREL_WOOD).getTier(), fluid);
        }
    }

    @Override
    public void registerFluidOnTop(FluidOnTopRegistry registry)
    {
        registry.register(FluidRegistry.LAVA, FluidRegistry.WATER, new BlockInfo(Blocks.OBSIDIAN.getDefaultState()));
        registry.register(FluidRegistry.WATER, FluidRegistry.LAVA, new BlockInfo(Blocks.STONE.getDefaultState()));
        registry.register(FluidRegistry.LAVA, BucketNFluidHandler.FLUIDLIQUIDIMPOSSIBILITY, new BlockInfo(Blocks.OBSIDIAN.getDefaultState()));
        registry.register(BucketNFluidHandler.FLUIDLIQUIDIMPOSSIBILITY, FluidRegistry.LAVA, new BlockInfo(Blocks.STONE.getDefaultState()));
    }

    @Override
    public void registerOreChunks(OreRegistry registry)
    {
        registry.register("gold", new Color("FFFF00"), new ItemInfo(Items.GOLD_INGOT, 0), OreDictUtil.getOreDictEntry("dustGold"));
        registry.register("iron", new Color("BF8040"), new ItemInfo(Items.IRON_INGOT, 0), OreDictUtil.getOreDictEntry("dustIron"));

//        for(EnumModdedMetals metal : EnumModdedMetals.values()) {
//            if(metal.getRegistryName().equals("aluminum") &&
//                    (!OreDictionary.getOres("oreAluminium").isEmpty() ||
//                            !OreDictionary.getOres("oreAluminum").isEmpty())) {
//                // Blame Humphry Davy
//                registry.register("aluminium", metal.getColor(), metal.getIngot(), metal.getDust());
//            }
//            else if(!OreDictionary.getOres(metal.getOreName()).isEmpty()) {
//                registry.register(metal.getRegistryName(), metal.getColor(), metal.getIngot(), metal.getDust());
//            }
//        }
    }

    @Override
    public void registerFluidTransform(FluidTransformRegistry registry) {
        //registry.register("water", "witchwater", 12000, new BlockInfo[]{new BlockInfo(Blocks.MYCELIUM.getDefaultState())}, new BlockInfo[]{new BlockInfo(Blocks.BROWN_MUSHROOM.getDefaultState()), new BlockInfo(Blocks.RED_MUSHROOM.getDefaultState())});
    }

    @Override
    public void registerFluidBlockTransform(FluidBlockTransformerRegistry registry)
    {
        registry.register(FluidRegistry.WATER, new ItemInfo(BlockHandler.DUST), new ItemInfo(Blocks.CLAY));
        registry.register(BucketNFluidHandler.FLUIDLIQUIDIMPOSSIBILITY, new ItemInfo(BlockHandler.DUST), new ItemInfo(Blocks.CLAY));
        registry.register(FluidRegistry.LAVA, "dustRedstone", new ItemInfo(Blocks.NETHERRACK));
        registry.register(FluidRegistry.LAVA, "dustGlowstone", new ItemInfo(Blocks.END_STONE));

        if(FluidRegistry.isFluidRegistered("milk")){
            registry.register(FluidRegistry.getFluid("milk"), new ItemInfo(Blocks.BROWN_MUSHROOM), new ItemInfo(Blocks.SLIME_BLOCK), "Slime");
            registry.register(FluidRegistry.getFluid("milk"), new ItemInfo(Blocks.RED_MUSHROOM), new ItemInfo(Blocks.SLIME_BLOCK), "Slime");
        }
        else {
            // No milk, fall back to liquid impossibility
            registry.register(BucketNFluidHandler.FLUIDLIQUIDIMPOSSIBILITY, new ItemInfo(Blocks.BROWN_MUSHROOM), new ItemInfo(Blocks.SLIME_BLOCK), "Slime");
            registry.register(BucketNFluidHandler.FLUIDLIQUIDIMPOSSIBILITY, new ItemInfo(Blocks.RED_MUSHROOM), new ItemInfo(Blocks.SLIME_BLOCK), "Slime");
        }

        // Vanilla Concrete
        for (int meta = 0; meta < 16; meta++)
        {
            registry.register(FluidRegistry.WATER, new ItemInfo(Blocks.CONCRETE_POWDER, meta), new ItemInfo(Blocks.CONCRETE, meta));
            registry.register(BucketNFluidHandler.FLUIDLIQUIDIMPOSSIBILITY, new ItemInfo(Blocks.CONCRETE_POWDER, meta), new ItemInfo(Blocks.CONCRETE, meta));
        }
    }

    @Override
    public void registerFluidItemFluid(FluidItemFluidRegistry registry)
    {
    	registry.register(FluidRegistry.WATER, new ItemInfo(ItemHandler.CRYSTAL_OF_LIGHT), BucketNFluidHandler.FLUIDLIQUIDIMPOSSIBILITY, 1000, false);
    	registry.register(BucketNFluidHandler.FLUIDDISTILLEDWATER, new ItemInfo(ItemHandler.SALT), FluidRegistry.WATER, 1000, true);
    }

    @Override
    public void registerCrucibleStone(CrucibleRegistry registry) {
        registry.register("cobblestone", FluidRegistry.LAVA, 250);
        registry.register("stone", FluidRegistry.LAVA, 250);
        registry.register("gravel", FluidRegistry.LAVA, 200);
        registry.register("sand", FluidRegistry.LAVA, 100);
        registry.register(new BlockInfo(BlockHandler.DUST), FluidRegistry.LAVA, 50);

        // 1:1 Back to lava
        registry.register("netherrack", FluidRegistry.LAVA, 1000);
        registry.register("obsidian", FluidRegistry.LAVA, 1000);
    }

    @Override
    public void registerMilk(MilkEntityRegistry registry) {
        registry.register("Cow", "milk", 10, 20);
    }

    private float getDropChance(float chance) {
    	return chance / 100f * Config.normalDropPercent;
    }

    private static Map<BlockInfo, BlockInfo> getLeavesSapling()
    {
        Map<BlockInfo, BlockInfo> saplingMap = new LinkedHashMap<>();
        saplingMap.put(new BlockInfo(Blocks.LEAVES, 0), new BlockInfo(Blocks.SAPLING, 0));
        saplingMap.put(new BlockInfo(Blocks.LEAVES, 1), new BlockInfo(Blocks.SAPLING, 1));
        saplingMap.put(new BlockInfo(Blocks.LEAVES, 2), new BlockInfo(Blocks.SAPLING, 2));
        saplingMap.put(new BlockInfo(Blocks.LEAVES, 3), new BlockInfo(Blocks.SAPLING, 3));
        saplingMap.put(new BlockInfo(Blocks.LEAVES2, 0), new BlockInfo(Blocks.SAPLING, 4));
        saplingMap.put(new BlockInfo(Blocks.LEAVES2, 1), new BlockInfo(Blocks.SAPLING, 5));

        return saplingMap;
    }
}