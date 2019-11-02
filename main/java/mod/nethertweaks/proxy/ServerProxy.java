package mod.nethertweaks.proxy;

import java.util.HashMap;
import java.util.UUID;

import mod.nethertweaks.modules.thirst.ThirstStats;
import mod.sfhcore.util.LogUtil;
import net.minecraft.entity.player.EntityPlayer;

public class ServerProxy implements IProxy{

	public final static HashMap<UUID, ThirstStats> LOADED_PLAYERS = new HashMap<>();

	@Override
	public void preInit() {}

	@Override
	public void init() {}

	@Override
	public void postInit() {}

	public static ThirstStats getStatsByUUID(final UUID uuid) {
		final ThirstStats stats = LOADED_PLAYERS.get(uuid);
		if (stats == null) {
			LogUtil.error("[Nether Tweaks Mod] Error: Attempted to access non-existent player with UUID: " + uuid);
			return null;
		}
		return stats;
	}

	public static void registerPlayer(final EntityPlayer player, final ThirstStats stats) {
		final UUID uuid = player.getUniqueID();
		if (LOADED_PLAYERS.containsKey(uuid))
			// Player already loaded from previous login session where the
			// server was not closed since the players last login.
			return;
		LOADED_PLAYERS.put(uuid, stats);
	}
}
