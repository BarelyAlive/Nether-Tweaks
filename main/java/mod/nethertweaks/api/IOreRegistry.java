package mod.nethertweaks.api;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

import mod.nethertweaks.item.ItemOre;
import mod.nethertweaks.registry.registries.base.types.Ore;
import mod.sfhcore.texturing.Color;
import mod.sfhcore.util.ItemInfo;

public interface IOreRegistry extends IRegistryList<Ore>
{
	List<ItemOre> itemOreRegistry = new ArrayList<>();

	/**
	 * A black list of ores to not register
	 */
	Set<ItemOre> sieveBlackList = new HashSet<>();

	/**
	 * Registers a new custom piece, hunk, dust and potentially ingot to be
	 * generated by NTM.
	 *
	 * @param name  Unique name for ore
	 * @param color Color for the pieces
	 * @param info  Final result for the process. If null, an ingot is generated.
	 * Otherwise, the hunk will be smelted into this.
	 * @return Ore, containing the base Ore object.
	 */
	Ore register(String name, Color color, @Nullable ItemInfo info, @Nullable Map<String, String> translations, @Nullable String oredictName);

	@Nullable ItemOre getOreItem(String name);
	boolean isRegistered(String name);
}
