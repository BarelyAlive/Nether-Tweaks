package mod.nethertweaks.world;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import mod.sfhcore.vars.PlayerPosition;
import net.minecraft.util.math.BlockPos;

public class WorldSpawnLoc {
	
	public static  Map<UUID, PlayerPosition> lastSpawnLocas = new HashMap<UUID, PlayerPosition>();
	public static Map<BlockPos, BonfireInfo> bonfire_info = new HashMap<BlockPos, BonfireInfo>();
	
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
	
}
