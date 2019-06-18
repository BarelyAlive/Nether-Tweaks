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

	public static void setInitialPosition(@Nonnull PlayerPosition pos) {
		initialPosition = pos;
	}

	//This is for the players™ and their individual spawns locations
	public static  Map<UUID, PlayerPosition> lastSpawnLocations = new HashMap<UUID, PlayerPosition>();
	public static Map<BlockPos, BonfireInfo> bonfire_info = new HashMap<BlockPos, BonfireInfo>();
	
	public static void setLastSpawnLocations(Map<UUID, PlayerPosition> map)
	{
		lastSpawnLocations = map;
	}
	
	public static Map<UUID, PlayerPosition> getLastSpawnLocations()
	{
		return lastSpawnLocations;
	}
	
	public static void setBonfireInfo(Map<BlockPos, BonfireInfo> map)
	{
		bonfire_info = map;
	}
	
	public static Map<BlockPos, BonfireInfo> getBonfireInfo()
	{
		return bonfire_info;
	}
	
	public static PlayerPosition setNewSpawnLocation(@Nonnull EntityPlayer player)
	{
		UUID playerID = EntityPlayer.getUUID(player.getGameProfile());
		
		if(playerID == null) playerID = EntityPlayer.getOfflineUUID(player.getName());
		
		if(lastSpawnLocations.containsKey(playerID))
		{
			lastSpawnLocations.replace(playerID, new PlayerPosition(player.getPosition()));
		}
		else
			lastSpawnLocations.put(playerID, new PlayerPosition(player.getPosition()));
		
		return new PlayerPosition(player.getPosition());
	}
	
	public static PlayerPosition resetSpawnLocation(@Nonnull EntityPlayer player)
	{
		UUID playerID = EntityPlayer.getUUID(player.getGameProfile());
		
		if(playerID == null) playerID = EntityPlayer.getOfflineUUID(player.getName());

		if(lastSpawnLocations.containsKey(playerID))
		{
			PlayerPosition pos = lastSpawnLocations.get(playerID);

			pos = getInitialPosition();
			return pos;
		}
		
		return new PlayerPosition(player.getPosition());
	}
	
}
