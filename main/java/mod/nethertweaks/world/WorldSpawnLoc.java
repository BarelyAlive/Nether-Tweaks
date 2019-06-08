package mod.nethertweaks.world;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Nonnull;

import mod.sfhcore.vars.PlayerPosition;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;

public class WorldSpawnLoc {
	
	public static  Map<UUID, PlayerPosition> lastSpawnLocas = new HashMap<UUID, PlayerPosition>();
	public static Map<BlockPos, BonfireInfo> bonfire_info = new HashMap<BlockPos, BonfireInfo>();
	//The initial Location when entering a world
	private static PlayerPosition initialPosition;
	
	public static PlayerPosition getInitialPosition() {
		return initialPosition;
	}

	public static void setInitialPosition(@Nonnull PlayerPosition pos) {
		initialPosition = pos;
	}
	
	public static void setLastSpawnLocations(Map<UUID, PlayerPosition> map)
	{
		lastSpawnLocas = map;
	}
	
	public static Map<UUID, PlayerPosition> getLastSpawnLocations()
	{
		return lastSpawnLocas;
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
		UUID playerID = player.getUUID(player.getGameProfile());
		
		if(playerID == null) playerID = player.getOfflineUUID(player.getName());
		
		if(lastSpawnLocas.containsKey(playerID))
		{
			lastSpawnLocas.replace(playerID, new PlayerPosition(player.getPosition()));
		}
		else
			lastSpawnLocas.put(playerID, new PlayerPosition(player.getPosition()));
		
		return new PlayerPosition(player.getPosition());
	}
	
	public static PlayerPosition resetSpawnLocation(@Nonnull EntityPlayer player)
	{
		UUID playerID = player.getUUID(player.getGameProfile());
		
		if(playerID == null) playerID = player.getOfflineUUID(player.getName());
		
		if(lastSpawnLocas.containsKey(playerID))
		{
			PlayerPosition pos = lastSpawnLocas.get(playerID);
			pos = getInitialPosition();
			return pos;
		}
		
		return new PlayerPosition(player.getPosition());
	}
	
}
