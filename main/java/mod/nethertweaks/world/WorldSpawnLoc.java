package mod.nethertweaks.world;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import mod.sfhcore.vars.PlayerPosition;
import net.minecraft.util.math.BlockPos;

public class WorldSpawnLoc {
	
	public static  Map<UUID, PlayerPosition> spawnLocas = new HashMap<UUID, PlayerPosition>();
	
	public static void setSpawnLocations(Map<UUID, PlayerPosition> map)
	{
		spawnLocas = map;
	}
	
	public static Map<UUID, PlayerPosition> getSpawnLocations()
	{
		return spawnLocas;
	}
	
}
