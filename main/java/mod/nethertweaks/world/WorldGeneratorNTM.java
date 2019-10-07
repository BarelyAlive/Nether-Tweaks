package mod.nethertweaks.world;

import java.util.Random;

import mod.nethertweaks.config.BlocksItems;
import mod.nethertweaks.handler.BlockHandler;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class WorldGeneratorNTM implements IWorldGenerator{

	private final WorldGenerator tree = new WorldGenElderTree(true);
	private final WorldGenMinable nrack = new WorldGenMinable(BlockHandler.HELLFAYAH_ORE.getDefaultState(), 20, BlockMatcher.forBlock(Blocks.NETHERRACK));

	public WorldGeneratorNTM() {
	}

	@Override
	public void generate(final Random random, final int chunkX, final int chunkZ, final World world, final IChunkGenerator chunkGenerator,
			final IChunkProvider chunkProvider) {
        if (world.provider.getDimension() == -1) generateNether(world, random, chunkX * 16, chunkZ * 16);
	}

	private void generateNether(final World world, final Random random, final int posX, final int posZ) {
		oreGenerationNether(world, random, posX, posZ, 6 + random.nextInt(4));
	}

	private void oreGenerationNether(final World world, final Random random, final int posX, final int posZ, final int spawnChancen)
	{
		int differenzMinMaxY = 127 - 1;
		if (BlocksItems.enableHellfayah)
			for (int i = 0; i < spawnChancen; i++) {
				int positionX = posX + random.nextInt(16);
				int positionY = 1 + random.nextInt(differenzMinMaxY);
				int positionZ = posZ + random.nextInt(16);
				nrack.generate(world, random, new BlockPos(positionX, positionY, positionZ));
			}
		if (BlocksItems.enableElderTree)
			for (int i = 0; i < 15; i++) // 15 is rarity
			{
				int randPosX = posX + random.nextInt(16) +8;
				int randPosY = random.nextInt(128); //Max Y coordinate
				int randPosZ = posZ + random.nextInt(16) +8;
				tree.generate(world, random, new BlockPos(randPosX, randPosY, randPosZ));
			}
		/*Wichtige Anmerkung der Autorin:
		 * Es muss die spawn position der Bäume
		 * um 8 versetzt werden, da es sonst zu sogenanntem
		 * "cascading world gen lag" kommt.*/
	}
}