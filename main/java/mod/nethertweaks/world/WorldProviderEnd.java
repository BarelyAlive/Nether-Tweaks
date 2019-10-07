package mod.nethertweaks.world;

import net.minecraft.world.DimensionType;
import net.minecraft.world.gen.ChunkGeneratorEnd;
import net.minecraft.world.gen.IChunkGenerator;

public class WorldProviderEnd extends net.minecraft.world.WorldProviderEnd {
	@Override
	public boolean doesWaterVaporize() {
		return true;
	}

	@Override
	public IChunkGenerator createChunkGenerator() {
		return new ChunkGeneratorEnd(world, false, getSeed(), null);
	}

	@Override
	public DimensionType getDimensionType() {
		return DimensionType.THE_END;
	}

}
