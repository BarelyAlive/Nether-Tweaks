package mod.nethertweaks.world;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldType;

public class WorldTypeHellworld extends WorldType
{
	public WorldTypeHellworld()
	{
		super("hellworld");
	}

	@Override
	public int getMinimumSpawnHeight(final World world)
	{
		return 1;
	}

	@Override
	public int getSpawnFuzz(final WorldServer world, final MinecraftServer server)
	{
		return 32;
	}
}
