package mod.nethertweaks.recipes.defaults;

import mod.nethertweaks.items.ItemOre;
import mod.nethertweaks.registries.registries.OreRegistry;
import mod.nethertweaks.registries.registries.SieveRegistry;
import mod.sfhcore.util.BlockInfo;
import mod.sfhcore.util.ItemInfo;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class TinkersConstruct implements IRecipeDefaults
{
    private final String MODID = "tconstruct";

    public void registerSieve(SieveRegistry registry) {
        OreRegistry oreRegistry = ExNihiloRegistryManager.ORE_REGISTRY;
        ItemOre ardite = oreRegistry.getOreItem("ardite");
        if (ardite != null) {
            registry.register(new BlockInfo(ModBlocks.netherrackCrushed), new ItemInfo(ardite), 0.1f, BlockSieve.MeshType.FLINT.getID());
            registry.register(new BlockInfo(ModBlocks.netherrackCrushed), new ItemInfo(ardite), 0.2f, BlockSieve.MeshType.IRON.getID());
            registry.register(new BlockInfo(ModBlocks.netherrackCrushed), new ItemInfo(ardite), 0.3f, BlockSieve.MeshType.DIAMOND.getID());
        }

        ItemOre cobalt = oreRegistry.getOreItem("cobalt");
        if (cobalt != null) {
            registry.register(new BlockInfo(ModBlocks.netherrackCrushed), new ItemInfo(cobalt), 0.1f, BlockSieve.MeshType.FLINT.getID());
            registry.register(new BlockInfo(ModBlocks.netherrackCrushed), new ItemInfo(cobalt), 0.2f, BlockSieve.MeshType.IRON.getID());
            registry.register(new BlockInfo(ModBlocks.netherrackCrushed), new ItemInfo(cobalt), 0.3f, BlockSieve.MeshType.DIAMOND.getID());
        }
        registry.register(new BlockInfo(ModBlocks.netherrackCrushed), new ItemInfo(Items.BLAZE_POWDER), 0.05f, BlockSieve.MeshType.IRON.getID());
    }

    public void registerOreChunks(OreRegistry registry) {
        Item tconstructIngots = Item.getByNameOrId("tconstruct:ingots");
        if (tconstructIngots != null) {
            registry.getSieveBlackList().add(ExNihiloRegistryManager.ORE_REGISTRY.getOreItem("ardite")); //Disables the default sieve recipes
            registry.getSieveBlackList().add(ExNihiloRegistryManager.ORE_REGISTRY.getOreItem("cobalt")); //Disables the default sieve recipes
        }
    }
}
