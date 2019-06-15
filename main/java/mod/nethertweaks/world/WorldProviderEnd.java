package mod.nethertweaks.world;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkGeneratorEnd;
import net.minecraft.world.gen.ChunkGeneratorOverworld;
import net.minecraft.world.gen.IChunkGenerator;

public class WorldProviderEnd extends net.minecraft.world.WorldProviderEnd {
	@Override
	public boolean doesWaterVaporize() {
		return true;
	}

	@Override
	public IChunkGenerator createChunkGenerator() {
		return new ChunkGeneratorEnd(this.world, false, this.getSeed(), null);
	}

	@Override
	public DimensionType getDimensionType() {
		return DimensionType.THE_END;
	}

}
