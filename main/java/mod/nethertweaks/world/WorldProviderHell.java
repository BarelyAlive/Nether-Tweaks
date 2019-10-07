package mod.nethertweaks.world;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkGeneratorOverworld;
import net.minecraft.world.gen.IChunkGenerator;

class WorldProviderHell extends WorldProvider {

	@Override
	public boolean canBlockFreeze(final BlockPos pos, final boolean byWater) {
		return false;
	}

	@Override
	public boolean canSnowAt(final BlockPos pos, final boolean checkLight) {
		return false;
	}

	@Override
	public WorldSleepResult canSleepAt(final EntityPlayer player, final BlockPos pos) {
		return WorldSleepResult.DENY;
	}

	@Override
	public boolean hasSkyLight() {
		return false;
	}

	@Override
	public boolean canDoLightning(final Chunk chunk) {
		return false;
	}

	@Override
	public boolean canDoRainSnowIce(final Chunk chunk) {
		return false;
	}

	@Override
	public boolean canRespawnHere() {
		return true;
	}

	@Override
	public boolean doesWaterVaporize() {
		return true;
	}

	@Override
	public IChunkGenerator createChunkGenerator() {
		return new ChunkGeneratorOverworld(world, getSeed(), false, null);
	}

	@Override
	public DimensionType getDimensionType() {
		return DimensionType.OVERWORLD;
	}

}
