package mod.nethertweaks.api;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import mod.nethertweaks.items.ItemChunk;
import mod.nethertweaks.items.ItemOre;
import mod.nethertweaks.registry.types.DynOre;
import mod.nethertweaks.registry.types.Ore;
import mod.sfhcore.texturing.Color;
import mod.sfhcore.util.ItemInfo;

public interface IDynOreRegistry extends IRegistryList<DynOre>
{
	List<ItemChunk> itemOreRegistry = new ArrayList<ItemChunk>();

    /**
     * A black list of ores to not register
     */
	Set<ItemChunk> sieveBlackList = new HashSet<ItemChunk>();

    /**
     * Registers a new chunk
     * generated by NTM.
     *
     * @param chunk 
     * @param ingot
     * @param color
     * @param rarity
     * 
     * @return Ore, containing the base Ore object.
     */
	DynOre register(@Nonnull String id, @Nonnull String name, @Nonnull ItemInfo ingot, int rarity, int color);

	@Nullable ItemChunk getOreItem(String name);
    boolean isRegistered(String name);
}
