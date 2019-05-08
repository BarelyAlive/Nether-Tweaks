package mod.nethertweaks.world;
 
import java.util.Random;

import mod.nethertweaks.Config;
import mod.nethertweaks.handler.BlockHandler;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
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

	private int frequency;
	private WorldGenerator tree = new WorldGenNetherTree(true);
	private WorldGenMinable nrack = new WorldGenMinable(BlockHandler.BLOCKBASIC.getDefaultState(), 20, BlockMatcher.forBlock(Blocks.NETHERRACK));
	
	public WorldGeneratorNTM(IBlockState state, int blockCount, int frequency) {
		super();
		this.frequency = frequency;
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider) {
		switch(world.provider.getDimension()){
        case -1: generateNether(world, random, chunkX * 16, chunkZ * 16);
        }		
	}
 
    private void generateNether(World world, Random random, int posX, int posZ) {
        oreGenerationNether(world, random, posX, posZ, 16, 16, 10, 6 + random.nextInt(4), 1, 127);
    }
 
    public void oreGenerationNether(World world, Random random, int posX, int posZ, int maxX, int maxZ, int maxAderLaenge, int spawnChancen, int minY, int maxY)
    {	
        int differenzMinMaxY = maxY - minY;
        if (Config.enableMultiBlock)
        {
			for (int i = 0; i < spawnChancen; i++) {
				int positionX = posX + random.nextInt(maxX);
				int positionY = minY + random.nextInt(differenzMinMaxY);
				int positionZ = posZ + random.nextInt(maxZ);
				nrack.generate(world, random, new BlockPos(positionX, positionY, positionZ));
			} 
		}
		if (Config.enableElderTree) {
			for (int i = 0; i < 15; i++) // 15 is rarity
			{
				int randPosX = posX + random.nextInt(16);
				int randPosY = random.nextInt(128); //Max Y coordinate
				int randPosZ = posZ + random.nextInt(16);
				tree.generate(world, random, new BlockPos(randPosX, randPosY, randPosZ));
			} 
		}
    }
}