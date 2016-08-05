package mod.nethertweaks.world;
 
import java.util.Random;

import mod.nethertweaks.blocks.NTMBlocks;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;
 
public class WorldGeneratorNetherTweaksMod implements IWorldGenerator {
 
    private void generateNether(World world, Random random, int x, int z) {
        erzGenerationNether(NTMBlocks.blockBasic, world, random, x, z, 16, 16, 10, 6 + random.nextInt(4), 1, 127);
    }
 
    public void erzGenerationNether(Block block, World world, Random random, int posX, int posZ, int maxX, int maxZ, int maxAderLaenge, int spawnChancen, int minY, int maxY){
         
        int differenzMinMaxY = maxY - minY;
        for(int i = 0; i < spawnChancen; i++){
            int positionX = posX + random.nextInt(maxX);
            int positionY = minY + random.nextInt(differenzMinMaxY);
            int positionZ = posZ + random.nextInt(maxZ);
            //(new WorldGenMinable(block, maxAderLaenge, Blocks.NETHERRACK)).generate(world, random, positionX, positionY, positionZ);
            new WorldGenMinable(Blocks.NETHERRACK.getDefaultState(), 1).generate(world, random, new BlockPos(positionX, positionY, positionZ));
        }
        
        for(int i = 0; i < 15; i++) // 15 is rarity
    	{
    	    int randPosX=posX + random.nextInt(16);
    	    int randPosY=random.nextInt(250); //Max Y coordinate
    	    int randPosZ=posZ + random.nextInt(16);
    	    (new WorldGenNetherTree(false)).generate(world, random, new BlockPos(randPosX, randPosY, randPosZ));
    	}
         
    }

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider) {
		// TODO Auto-generated method stub
		switch(world.provider.getDimension()){
        case -1: generateNether(world, random, chunkX * 16, chunkZ * 16);
        }
	}
}