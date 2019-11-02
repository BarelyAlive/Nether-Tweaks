package mod.nethertweaks.world;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Nonnull;

import mod.sfhcore.vars.PlayerPosition;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;

public class WorldSpawnLocation {

	//The initial Location when entering a world
	private static PlayerPosition initialPosition;

	public static PlayerPosition getInitialPosition() {
		return initialPosition;
	}

	public static void setInitialPosition(@Nonnull final PlayerPosition pos) {
		initialPosition = pos;
	}

	//This is for the players™ and their individual spawns locations
	private static Map<UUID, PlayerPosition> lastSpawnLocations = new HashMap<>();
	private static Map<BlockPos, BonfireInfo> bonfire_info = new HashMap<>();

	public static void setLastSpawnLocations(final Map<UUID, PlayerPosition> map)
	{
		lastSpawnLocations = map;
	}

	public static Map<UUID, PlayerPosition> getLastSpawnLocations()
	{
		return lastSpawnLocations;
	}

	public static void setBonfireInfo(final Map<BlockPos, BonfireInfo> map)
	{
		setBonfire_info(map);
	}

	public static Map<BlockPos, BonfireInfo> getBonfireInfo()
	{
		return bonfire_info;
	}

	public static PlayerPosition setNewSpawnLocation(@Nonnull final EntityPlayer player)
	{
		final UUID playerID = EntityPlayer.getUUID(player.getGameProfile());

        if(lastSpawnLocations.containsKey(playerID))
			lastSpawnLocations.replace(playerID, new PlayerPosition(player.getPosition()));
		else
			lastSpawnLocations.put(playerID, new PlayerPosition(player.getPosition()));

		return new PlayerPosition(player.getPosition());
	}

	public static PlayerPosition resetSpawnLocation(@Nonnull final EntityPlayer player)
	{
		final UUID playerID = EntityPlayer.getUUID(player.getGameProfile());

        if(lastSpawnLocations.containsKey(playerID))
		{
			PlayerPosition pos = lastSpawnLocations.get(playerID);

			pos = getInitialPosition();
			return pos;
		}

		return new PlayerPosition(player.getPosition());
	}

	public static void setBonfire_info(final Map<BlockPos, BonfireInfo> bonfire_info) {
		WorldSpawnLocation.bonfire_info = bonfire_info;
	}

}
