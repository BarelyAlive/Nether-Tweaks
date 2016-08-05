package mod.nethertweaks.world;

import java.util.Random;

import mod.nethertweaks.Config;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldProviderHell;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldSettings;
import net.minecraftforge.common.DimensionManager;

public class WorldProviderNetherNTM extends WorldProviderHell {
	
	@Override
	public int getRespawnDimension(EntityPlayerMP player) {
		return -1;
	}
	
	@Override
	public boolean canRespawnHere() {
		return true;
	}
	
	@Override
	public boolean canCoordinateBeSpawn(int x, int z) {
		return true;
	}
	
	@Override
	public boolean doesWaterVaporize() {
		return Config.iwantvanillaWater;
	}
	
	@Override
	public String getWelcomeMessage() {
		String message = "Welcome to your Doom!";
		return message;
	}
	
	@Override
	public void onPlayerAdded(EntityPlayerMP player) {
		player.changeDimension(-1);
	}
	
	@Override
	public void setSpawnPoint(BlockPos pos){
		if(this.worldObj.getWorldType() instanceof WorldTypeHellworld){
			worldObj.getWorldInfo().setSpawn(new BlockPos(0,50,0));
		}
		else{
			worldObj.getWorldInfo().setSpawn(pos);
		}
    }
	
	@Override
	public BlockPos getSpawnPoint(){
        if(this.worldObj.getWorldType() instanceof WorldTypeHellworld){
        	return new BlockPos(0, 51, 0);
		}
		else{
			net.minecraft.world.storage.WorldInfo info = worldObj.getWorldInfo();
	        return new BlockPos(info.getSpawnX(), info.getSpawnY(), info.getSpawnZ());
		}
	}
	
	@Override
	public BlockPos getSpawnCoordinate(){
		if(this.worldObj.getWorldType() instanceof WorldTypeHellworld){
			return new BlockPos(0,51,0);
		}
		else{
			return null;
		}
	}
	
}