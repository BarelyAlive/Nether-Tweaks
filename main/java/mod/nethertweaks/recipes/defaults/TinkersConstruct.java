package mod.nethertweaks.recipes.defaults;

import mod.nethertweaks.blocks.Sieve;
import mod.nethertweaks.handler.BlockHandler;
import mod.nethertweaks.items.ItemOre;
import mod.nethertweaks.registries.manager.NTMRegistryManager;
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
        OreRegistry oreRegistry = NTMRegistryManager.ORE_REGISTRY;
        ItemOre ardite = oreRegistry.getOreItem("ardite");
        if (ardite != null) {
            registry.register(new BlockInfo(BlockHandler.NETHERRACKGRAVEL), new ItemInfo(ardite), 0.1f, Sieve.MeshType.FLINT.getID());
            registry.register(new BlockInfo(BlockHandler.NETHERRACKGRAVEL), new ItemInfo(ardite), 0.2f, Sieve.MeshType.IRON.getID());
            registry.register(new BlockInfo(BlockHandler.NETHERRACKGRAVEL), new ItemInfo(ardite), 0.3f, Sieve.MeshType.DIAMOND.getID());
        }

        ItemOre cobalt = oreRegistry.getOreItem("cobalt");
        if (cobalt != null) {
            registry.register(new BlockInfo(BlockHandler.NETHERRACKGRAVEL), new ItemInfo(cobalt), 0.1f, Sieve.MeshType.FLINT.getID());
            registry.register(new BlockInfo(BlockHandler.NETHERRACKGRAVEL), new ItemInfo(cobalt), 0.2f, Sieve.MeshType.IRON.getID());
            registry.register(new BlockInfo(BlockHandler.NETHERRACKGRAVEL), new ItemInfo(cobalt), 0.3f, Sieve.MeshType.DIAMOND.getID());
        }
        registry.register(new BlockInfo(BlockHandler.NETHERRACKGRAVEL), new ItemInfo(Items.BLAZE_POWDER), 0.05f, Sieve.MeshType.IRON.getID());
    }

    public void registerOreChunks(OreRegistry registry) {
        Item tconstructIngots = Item.getByNameOrId("tconstruct:ingots");
        if (tconstructIngots != null) {
            registry.getSieveBlackList().add(NTMRegistryManager.ORE_REGISTRY.getOreItem("ardite")); //Disables the default sieve recipes
            registry.getSieveBlackList().add(NTMRegistryManager.ORE_REGISTRY.getOreItem("cobalt")); //Disables the default sieve recipes
        }
    }

	@Override
	public String getMODID() {
		return null;
	}
}
