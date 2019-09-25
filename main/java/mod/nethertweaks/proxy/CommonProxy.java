package mod.nethertweaks.proxy;

import java.util.HashMap;
import java.util.UUID;

import mod.nethertweaks.modules.thirst.ThirstStats;
import net.minecraft.entity.player.EntityPlayer;

public class CommonProxy {

	public HashMap<UUID, ThirstStats> loadedPlayers = new HashMap<>();

	public void preInit() {}

	public void init() {}

	public ThirstStats getStatsByUUID(final UUID uuid) {
		ThirstStats stats = loadedPlayers.get(uuid);
		if (stats == null) {
			System.out.println("[Nether Tweaks Mod] Error: Attempted to access non-existent player with UUID: " + uuid);
			return null;
		}
		return stats;
	}

	public void registerPlayer(final EntityPlayer player, final ThirstStats stats) {
		UUID uuid = player.getUniqueID();
		if (loadedPlayers.containsKey(uuid))
			// Player already loaded from previous login session where the
			// server was not closed since the players last login.
			return;
		loadedPlayers.put(uuid, stats);
	}
}
