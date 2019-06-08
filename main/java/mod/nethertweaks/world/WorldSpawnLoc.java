package mod.nethertweaks.world;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Nonnull;

import mod.sfhcore.vars.PlayerPosition;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;

public class WorldSpawnLoc {
	
	//The initial Location when entering a world
	private static PlayerPosition initialPosition;
	
	public static PlayerPosition getInitialPosition() {
		return initialPosition;
	}

	public static void setInitialPosition(@Nonnull PlayerPosition pos) {
		initialPosition = pos;
	}

	//This is for the players™ and their individual spawns locations
	public static Map<UUID, PlayerPosition> spawnLocas = new HashMap<UUID, PlayerPosition>();
	
	public static void setSpawnLocations(Map<UUID, PlayerPosition> map)
	{
		spawnLocas = map;
	}
	
	public static Map<UUID, PlayerPosition> getSpawnLocations()
	{
		return spawnLocas;
	}
	
	public static PlayerPosition setNewSpawnLocation(@Nonnull EntityPlayer player)
	{
		UUID playerID = player.getUUID(player.getGameProfile());
		
		if(playerID == null) playerID = player.getOfflineUUID(player.getName());
		
		if(spawnLocas.containsKey(playerID))
		{
			spawnLocas.replace(playerID, new PlayerPosition(player.getPosition()));
		}
		else
			spawnLocas.put(playerID, new PlayerPosition(player.getPosition()));
		
		return new PlayerPosition(player.getPosition());
	}
	
	public static PlayerPosition resetSpawnLocation(@Nonnull EntityPlayer player)
	{
		UUID playerID = player.getUUID(player.getGameProfile());
		
		if(playerID == null) playerID = player.getOfflineUUID(player.getName());
		
		if(spawnLocas.containsKey(playerID))
		{
			PlayerPosition pos = spawnLocas.get(playerID);
			pos = getInitialPosition();
			return pos;
		}
		
		return new PlayerPosition(player.getPosition());
	}
	
}
