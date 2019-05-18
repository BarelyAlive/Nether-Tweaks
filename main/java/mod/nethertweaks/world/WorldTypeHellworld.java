package mod.nethertweaks.world;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldType;

public class WorldTypeHellworld extends WorldType
{	
	public WorldTypeHellworld(String par2Str)
	{
		super(par2Str);
	}
	
	@Override
	public int getMinimumSpawnHeight(World world)
	{
		return 1;
	}
	
	@Override
	public int getSpawnFuzz(WorldServer world, MinecraftServer server)
	{
		return 100;
	}
}
