package mod.nethertweaks.world;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldType;

public class Hellworld extends WorldType
{
	public Hellworld()
	{
		super("hellworld");
	}
	
	@Override
	public boolean hasInfoNotice() { return true; }

	@Override
	public boolean isVersioned() {
		return true;
	}

	@Override
	public int getVersion() {
		return 2;
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

	public static boolean isHellworld(final World world)
	{
		return world.getWorldType() instanceof Hellworld;
	}
}
