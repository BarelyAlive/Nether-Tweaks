package mod.nethertweaks.world;

import net.minecraft.client.multiplayer.ChunkProviderClient;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldProviderHell;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.FlatGeneratorInfo;
import net.minecraftforge.common.DimensionManager;

public class WorldTypeHellworld extends WorldType {
	
	public WorldTypeHellworld(String par2Str) {
		super(par2Str);
	}	
	
	@Override
	public int getMinimumSpawnHeight(World world) {
		return 1;
	}
	
	@Override
	public int getSpawnFuzz(WorldServer world, MinecraftServer server) {
		return 100;
	}
		
	
}
