package mod.nethertweaks.world;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.minecraft.util.math.BlockPos;

public class WorldSpawnLoc {
	
	public static  Map<UUID, BlockPos> spawnLocas = new HashMap<UUID, BlockPos>();
	
	public static void setSpawnLocations(Map<UUID, BlockPos> spawn)
	{
		spawnLocas = spawn;
	}
	
	public static Map<UUID, BlockPos> getSpawnLocations()
	{
		return spawnLocas;
	}
	
}
